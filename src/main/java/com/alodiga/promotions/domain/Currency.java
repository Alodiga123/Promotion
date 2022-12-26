package com.alodiga.promotions.domain;


import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;

/**
 * A Currency.
 */
@Entity
@Table(name = "currency")
public class Currency implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Size(min = 5, max = 45)
    @Column(name = "name", length = 45, nullable = false)
    private String name;

    @NotNull
    @Size(min = 5, max = 80)
    @Column(name = "description", length = 80, nullable = false)
    private String description;

    @NotNull
    @Column(name = "simbol", nullable = false)
    private String simbol;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public Currency name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public Currency description(String description) {
        this.description = description;
        return this;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getSimbol() {
        return simbol;
    }

    public Currency simbol(String simbol) {
        this.simbol = simbol;
        return this;
    }

    public void setSimbol(String simbol) {
        this.simbol = simbol;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Currency)) {
            return false;
        }
        return id != null && id.equals(((Currency) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Currency{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", description='" + getDescription() + "'" +
            ", simbol='" + getSimbol() + "'" +
            "}";
    }
}
