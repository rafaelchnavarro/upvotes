package com.segware.upvotes.web.rest;

import com.segware.upvotes.service.UpvotesService;
import com.segware.upvotes.web.rest.errors.BadRequestAlertException;
import com.segware.upvotes.service.dto.UpvotesDTO;

import io.github.jhipster.web.util.HeaderUtil;
import io.github.jhipster.web.util.PaginationUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.util.MultiValueMap;
import org.springframework.web.util.UriComponentsBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;

import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing {@link com.segware.upvotes.domain.Upvotes}.
 */
@RestController
@RequestMapping("/api")
public class UpvotesResource {

    private final Logger log = LoggerFactory.getLogger(UpvotesResource.class);

    private static final String ENTITY_NAME = "upvotes";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final UpvotesService upvotesService;

    public UpvotesResource(UpvotesService upvotesService) {
        this.upvotesService = upvotesService;
    }

    /**
     * {@code POST  /upvotes} : Create a new upvotes.
     *
     * @param upvotesDTO the upvotesDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new upvotesDTO, or with status {@code 400 (Bad Request)} if the upvotes has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/upvotes")
    public ResponseEntity<UpvotesDTO> createUpvotes(@Valid @RequestBody UpvotesDTO upvotesDTO) throws URISyntaxException {
        log.debug("REST request to save Upvotes : {}", upvotesDTO);
        if (upvotesDTO.getId() != null) {
            throw new BadRequestAlertException("A new upvotes cannot already have an ID", ENTITY_NAME, "idexists");
        }
        UpvotesDTO result = upvotesService.save(upvotesDTO);
        return ResponseEntity.created(new URI("/api/upvotes/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /upvotes} : Updates an existing upvotes.
     *
     * @param upvotesDTO the upvotesDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated upvotesDTO,
     * or with status {@code 400 (Bad Request)} if the upvotesDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the upvotesDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/upvotes")
    public ResponseEntity<UpvotesDTO> updateUpvotes(@Valid @RequestBody UpvotesDTO upvotesDTO) throws URISyntaxException {
        log.debug("REST request to update Upvotes : {}", upvotesDTO);
        if (upvotesDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        UpvotesDTO result = upvotesService.save(upvotesDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, upvotesDTO.getId().toString()))
            .body(result);
    }

    @PostMapping("/upvotes/vote")
    public ResponseEntity<UpvotesDTO> vote(@RequestParam Long messageId, @RequestParam Integer vote) throws URISyntaxException {
        log.debug("REST request to votes : {}", messageId);
        if (messageId == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }

        Optional<UpvotesDTO> optional = upvotesService.findOne(messageId);
        UpvotesDTO upvotesDTO = optional.get();

        upvotesDTO.setVote(vote);

        UpvotesDTO result = upvotesService.save(upvotesDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, upvotesDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /upvotes} : get all the upvotes.
     *
     * @param pageable the pagination information.
     * @param queryParams a {@link MultiValueMap} query parameters.
     * @param uriBuilder a {@link UriComponentsBuilder} URI builder.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of upvotes in body.
     */
    @GetMapping("/upvotes")
    public ResponseEntity<List<UpvotesDTO>> getAllUpvotes(Pageable pageable, @RequestParam MultiValueMap<String, String> queryParams, UriComponentsBuilder uriBuilder) {
        log.debug("REST request to get a page of Upvotes");
        Page<UpvotesDTO> page = upvotesService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(uriBuilder.queryParams(queryParams), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /upvotes/:id} : get the "id" upvotes.
     *
     * @param id the id of the upvotesDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the upvotesDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/upvotes/{id}")
    public ResponseEntity<UpvotesDTO> getUpvotes(@PathVariable Long id) {
        log.debug("REST request to get Upvotes : {}", id);
        Optional<UpvotesDTO> upvotesDTO = upvotesService.findOne(id);
        return ResponseUtil.wrapOrNotFound(upvotesDTO);
    }

    /**
     * {@code DELETE  /upvotes/:id} : delete the "id" upvotes.
     *
     * @param id the id of the upvotesDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/upvotes/{id}")
    public ResponseEntity<Void> deleteUpvotes(@PathVariable Long id) {
        log.debug("REST request to delete Upvotes : {}", id);
        upvotesService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
