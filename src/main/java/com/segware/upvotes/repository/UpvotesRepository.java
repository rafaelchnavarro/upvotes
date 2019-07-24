package com.segware.upvotes.repository;

import com.segware.upvotes.domain.Upvotes;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the Upvotes entity.
 */
@SuppressWarnings("unused")
@Repository
public interface UpvotesRepository extends JpaRepository<Upvotes, Long> {

}
