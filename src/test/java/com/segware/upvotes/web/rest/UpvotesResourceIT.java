package com.segware.upvotes.web.rest;

import com.segware.upvotes.UpvotesApp;
import com.segware.upvotes.domain.Upvotes;
import com.segware.upvotes.repository.UpvotesRepository;
import com.segware.upvotes.service.UpvotesService;
import com.segware.upvotes.service.dto.UpvotesDTO;
import com.segware.upvotes.service.mapper.UpvotesMapper;
import com.segware.upvotes.web.rest.errors.ExceptionTranslator;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.Validator;

import javax.persistence.EntityManager;
import java.util.List;

import static com.segware.upvotes.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Integration tests for the {@Link UpvotesResource} REST controller.
 */
@SpringBootTest(classes = UpvotesApp.class)
public class UpvotesResourceIT {

    private static final String DEFAULT_MESSAGE = "AAAAAAAAAA";
    private static final String UPDATED_MESSAGE = "BBBBBBBBBB";

    @Autowired
    private UpvotesRepository upvotesRepository;

    @Autowired
    private UpvotesMapper upvotesMapper;

    @Autowired
    private UpvotesService upvotesService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    @Autowired
    private Validator validator;

    private MockMvc restUpvotesMockMvc;

    private Upvotes upvotes;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final UpvotesResource upvotesResource = new UpvotesResource(upvotesService);
        this.restUpvotesMockMvc = MockMvcBuilders.standaloneSetup(upvotesResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setConversionService(createFormattingConversionService())
            .setMessageConverters(jacksonMessageConverter)
            .setValidator(validator).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Upvotes createEntity(EntityManager em) {
        Upvotes upvotes = new Upvotes()
            .message(DEFAULT_MESSAGE);
        return upvotes;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Upvotes createUpdatedEntity(EntityManager em) {
        Upvotes upvotes = new Upvotes()
            .message(UPDATED_MESSAGE);
        return upvotes;
    }

    @BeforeEach
    public void initTest() {
        upvotes = createEntity(em);
    }

    @Test
    @Transactional
    public void createUpvotes() throws Exception {
        int databaseSizeBeforeCreate = upvotesRepository.findAll().size();

        // Create the Upvotes
        UpvotesDTO upvotesDTO = upvotesMapper.toDto(upvotes);
        restUpvotesMockMvc.perform(post("/api/upvotes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(upvotesDTO)))
            .andExpect(status().isCreated());

        // Validate the Upvotes in the database
        List<Upvotes> upvotesList = upvotesRepository.findAll();
        assertThat(upvotesList).hasSize(databaseSizeBeforeCreate + 1);
        Upvotes testUpvotes = upvotesList.get(upvotesList.size() - 1);
        assertThat(testUpvotes.getMessage()).isEqualTo(DEFAULT_MESSAGE);
    }

    @Test
    @Transactional
    public void createUpvotesWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = upvotesRepository.findAll().size();

        // Create the Upvotes with an existing ID
        upvotes.setId(1L);
        UpvotesDTO upvotesDTO = upvotesMapper.toDto(upvotes);

        // An entity with an existing ID cannot be created, so this API call must fail
        restUpvotesMockMvc.perform(post("/api/upvotes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(upvotesDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Upvotes in the database
        List<Upvotes> upvotesList = upvotesRepository.findAll();
        assertThat(upvotesList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkMessageIsRequired() throws Exception {
        int databaseSizeBeforeTest = upvotesRepository.findAll().size();
        // set the field null
        upvotes.setMessage(null);

        // Create the Upvotes, which fails.
        UpvotesDTO upvotesDTO = upvotesMapper.toDto(upvotes);

        restUpvotesMockMvc.perform(post("/api/upvotes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(upvotesDTO)))
            .andExpect(status().isBadRequest());

        List<Upvotes> upvotesList = upvotesRepository.findAll();
        assertThat(upvotesList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllUpvotes() throws Exception {
        // Initialize the database
        upvotesRepository.saveAndFlush(upvotes);

        // Get all the upvotesList
        restUpvotesMockMvc.perform(get("/api/upvotes?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(upvotes.getId().intValue())))
            .andExpect(jsonPath("$.[*].message").value(hasItem(DEFAULT_MESSAGE.toString())));
    }
    
    @Test
    @Transactional
    public void getUpvotes() throws Exception {
        // Initialize the database
        upvotesRepository.saveAndFlush(upvotes);

        // Get the upvotes
        restUpvotesMockMvc.perform(get("/api/upvotes/{id}", upvotes.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(upvotes.getId().intValue()))
            .andExpect(jsonPath("$.message").value(DEFAULT_MESSAGE.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingUpvotes() throws Exception {
        // Get the upvotes
        restUpvotesMockMvc.perform(get("/api/upvotes/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateUpvotes() throws Exception {
        // Initialize the database
        upvotesRepository.saveAndFlush(upvotes);

        int databaseSizeBeforeUpdate = upvotesRepository.findAll().size();

        // Update the upvotes
        Upvotes updatedUpvotes = upvotesRepository.findById(upvotes.getId()).get();
        // Disconnect from session so that the updates on updatedUpvotes are not directly saved in db
        em.detach(updatedUpvotes);
        updatedUpvotes
            .message(UPDATED_MESSAGE);
        UpvotesDTO upvotesDTO = upvotesMapper.toDto(updatedUpvotes);

        restUpvotesMockMvc.perform(put("/api/upvotes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(upvotesDTO)))
            .andExpect(status().isOk());

        // Validate the Upvotes in the database
        List<Upvotes> upvotesList = upvotesRepository.findAll();
        assertThat(upvotesList).hasSize(databaseSizeBeforeUpdate);
        Upvotes testUpvotes = upvotesList.get(upvotesList.size() - 1);
        assertThat(testUpvotes.getMessage()).isEqualTo(UPDATED_MESSAGE);
    }

    @Test
    @Transactional
    public void updateNonExistingUpvotes() throws Exception {
        int databaseSizeBeforeUpdate = upvotesRepository.findAll().size();

        // Create the Upvotes
        UpvotesDTO upvotesDTO = upvotesMapper.toDto(upvotes);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restUpvotesMockMvc.perform(put("/api/upvotes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(upvotesDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Upvotes in the database
        List<Upvotes> upvotesList = upvotesRepository.findAll();
        assertThat(upvotesList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteUpvotes() throws Exception {
        // Initialize the database
        upvotesRepository.saveAndFlush(upvotes);

        int databaseSizeBeforeDelete = upvotesRepository.findAll().size();

        // Delete the upvotes
        restUpvotesMockMvc.perform(delete("/api/upvotes/{id}", upvotes.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Upvotes> upvotesList = upvotesRepository.findAll();
        assertThat(upvotesList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Upvotes.class);
        Upvotes upvotes1 = new Upvotes();
        upvotes1.setId(1L);
        Upvotes upvotes2 = new Upvotes();
        upvotes2.setId(upvotes1.getId());
        assertThat(upvotes1).isEqualTo(upvotes2);
        upvotes2.setId(2L);
        assertThat(upvotes1).isNotEqualTo(upvotes2);
        upvotes1.setId(null);
        assertThat(upvotes1).isNotEqualTo(upvotes2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(UpvotesDTO.class);
        UpvotesDTO upvotesDTO1 = new UpvotesDTO();
        upvotesDTO1.setId(1L);
        UpvotesDTO upvotesDTO2 = new UpvotesDTO();
        assertThat(upvotesDTO1).isNotEqualTo(upvotesDTO2);
        upvotesDTO2.setId(upvotesDTO1.getId());
        assertThat(upvotesDTO1).isEqualTo(upvotesDTO2);
        upvotesDTO2.setId(2L);
        assertThat(upvotesDTO1).isNotEqualTo(upvotesDTO2);
        upvotesDTO1.setId(null);
        assertThat(upvotesDTO1).isNotEqualTo(upvotesDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(upvotesMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(upvotesMapper.fromId(null)).isNull();
    }
}
