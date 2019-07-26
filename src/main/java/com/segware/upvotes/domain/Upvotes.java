package com.segware.upvotes.domain;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;

/**
 * A Upvotes.
 */
@Entity
@Table(name = "upvotes")
public class Upvotes implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Size(min = 1, max = 250)
    @Column(name = "message", length = 250, nullable = false)
    private String message;

    @NotNull
    @Min(value = 1)
    @Max(value = 250)
    @Column(name = "vote", length = 250, nullable = false)
    private Integer vote;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
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

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public Upvotes message(String message) {
        this.message = message;
        return this;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Upvotes)) {
            return false;
        }
        return id != null && id.equals(((Upvotes) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "Upvotes{" +
            "id=" + getId() +
            ", message='" + getMessage() + "'" +
            "}";
    }
}
