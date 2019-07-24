package com.segware.upvotes.service.dto;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        UpvotesDTO upvotesDTO = (UpvotesDTO) o;
        if (upvotesDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), upvotesDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "UpvotesDTO{" +
            "id=" + getId() +
            ", message='" + getMessage() + "'" +
            "}";
    }
}
