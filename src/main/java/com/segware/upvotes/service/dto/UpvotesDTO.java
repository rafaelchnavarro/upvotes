package com.segware.upvotes.service.dto;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link com.segware.upvotes.domain.Upvotes} entity.
 */
public class UpvotesDTO implements Serializable {

    private Long id;

    @NotNull
    @Size(min = 1, max = 250)
    private String message;

    private Integer vote;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Integer getVote() { return vote; }

    public void setVote(Integer vote) { this.vote = vote; }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UpvotesDTO that = (UpvotesDTO) o;
        return Objects.equals(id, that.id) &&
            Objects.equals(message, that.message) &&
            Objects.equals(vote, that.vote);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, message, vote);
    }

    @Override
    public String toString() {
        return "UpvotesDTO{" +
            "id=" + id +
            ", message='" + message + '\'' +
            ", vote=" + vote +
            '}';
    }
}
