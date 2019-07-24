package com.segware.upvotes.service.impl;

import com.segware.upvotes.service.UpvotesService;
import com.segware.upvotes.domain.Upvotes;
import com.segware.upvotes.repository.UpvotesRepository;
import com.segware.upvotes.service.dto.UpvotesDTO;
import com.segware.upvotes.service.mapper.UpvotesMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link Upvotes}.
 */
@Service
@Transactional
public class UpvotesServiceImpl implements UpvotesService {

    private final Logger log = LoggerFactory.getLogger(UpvotesServiceImpl.class);

    private final UpvotesRepository upvotesRepository;

    private final UpvotesMapper upvotesMapper;

    public UpvotesServiceImpl(UpvotesRepository upvotesRepository, UpvotesMapper upvotesMapper) {
        this.upvotesRepository = upvotesRepository;
        this.upvotesMapper = upvotesMapper;
    }

    /**
     * Save a upvotes.
     *
     * @param upvotesDTO the entity to save.
     * @return the persisted entity.
     */
    @Override
    public UpvotesDTO save(UpvotesDTO upvotesDTO) {
        log.debug("Request to save Upvotes : {}", upvotesDTO);
        Upvotes upvotes = upvotesMapper.toEntity(upvotesDTO);
        upvotes = upvotesRepository.save(upvotes);
        return upvotesMapper.toDto(upvotes);
    }

    /**
     * Get all the upvotes.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public Page<UpvotesDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Upvotes");
        return upvotesRepository.findAll(pageable)
            .map(upvotesMapper::toDto);
    }


    /**
     * Get one upvotes by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<UpvotesDTO> findOne(Long id) {
        log.debug("Request to get Upvotes : {}", id);
        return upvotesRepository.findById(id)
            .map(upvotesMapper::toDto);
    }

    /**
     * Delete the upvotes by id.
     *
     * @param id the id of the entity.
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Upvotes : {}", id);
        upvotesRepository.deleteById(id);
    }
}
