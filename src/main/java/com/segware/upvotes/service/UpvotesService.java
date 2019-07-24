package com.segware.upvotes.service;

import com.segware.upvotes.service.dto.UpvotesDTO;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing {@link com.segware.upvotes.domain.Upvotes}.
 */
public interface UpvotesService {

    /**
     * Save a upvotes.
     *
     * @param upvotesDTO the entity to save.
     * @return the persisted entity.
     */
    UpvotesDTO save(UpvotesDTO upvotesDTO);

    /**
     * Get all the upvotes.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<UpvotesDTO> findAll(Pageable pageable);


    /**
     * Get the "id" upvotes.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<UpvotesDTO> findOne(Long id);

    /**
     * Delete the "id" upvotes.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
