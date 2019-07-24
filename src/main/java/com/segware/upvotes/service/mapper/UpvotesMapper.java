package com.segware.upvotes.service.mapper;

import com.segware.upvotes.domain.*;
import com.segware.upvotes.service.dto.UpvotesDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link Upvotes} and its DTO {@link UpvotesDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface UpvotesMapper extends EntityMapper<UpvotesDTO, Upvotes> {



    default Upvotes fromId(Long id) {
        if (id == null) {
            return null;
        }
        Upvotes upvotes = new Upvotes();
        upvotes.setId(id);
        return upvotes;
    }
}
