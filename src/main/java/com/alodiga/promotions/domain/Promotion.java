package com.alodiga.promotions.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.time.Instant;

import com.alodiga.promotions.domain.enumeration.PromotionType;

/**
 * A Promotion.
 */
@Entity
@Table(name = "promotion")
public class Promotion implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Size(min = 5, max = 45)
    @Column(name = "name", length = 45, nullable = false)
    private String name;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "promotion_type", nullable = false)
    private PromotionType promotionType;

    @Column(name = "creation_date")
    private Instant creationDate;

    @Column(name = "responsible_date")
    private Instant responsibleDate;

    @Column(name = "beginning_date")
    private Instant beginningDate;

    @Column(name = "ending_date")
    private Instant endingDate;

    @NotNull
    @Column(name = "is_exclusive", nullable = false)
    private Boolean isExclusive;

    @NotNull
    @Column(name = "priority", nullable = false)
    private Integer priority;

    @NotNull
    @Column(name = "promotional_text", nullable = false)
    private String promotionalText;

    @NotNull
    @Column(name = "enabled", nullable = false)
    private Boolean enabled;

    @Column(name = "is_percent")
    private Boolean isPercent;

    
    @Lob
    @Column(name = "imagen", nullable = false)
    private byte[] imagen;

    @Column(name = "imagen_content_type", nullable = false)
    private String imagenContentType;

    @Column(name = "is_amount")
    private Boolean isAmount;

    @NotNull
    @DecimalMin(value = "0")
    @DecimalMax(value = "5")
    @Column(name = "value", nullable = false)
    private Float value;

    @ManyToOne
    @JsonIgnoreProperties(value = "promotions", allowSetters = true)
    private Currency currency;

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

    public Promotion name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public PromotionType getPromotionType() {
        return promotionType;
    }

    public Promotion promotionType(PromotionType promotionType) {
        this.promotionType = promotionType;
        return this;
    }

    public void setPromotionType(PromotionType promotionType) {
        this.promotionType = promotionType;
    }

    public Instant getCreationDate() {
        return creationDate;
    }

    public Promotion creationDate(Instant creationDate) {
        this.creationDate = creationDate;
        return this;
    }

    public void setCreationDate(Instant creationDate) {
        this.creationDate = creationDate;
    }

    public Instant getResponsibleDate() {
        return responsibleDate;
    }

    public Promotion responsibleDate(Instant responsibleDate) {
        this.responsibleDate = responsibleDate;
        return this;
    }

    public void setResponsibleDate(Instant responsibleDate) {
        this.responsibleDate = responsibleDate;
    }

    public Instant getBeginningDate() {
        return beginningDate;
    }

    public Promotion beginningDate(Instant beginningDate) {
        this.beginningDate = beginningDate;
        return this;
    }

    public void setBeginningDate(Instant beginningDate) {
        this.beginningDate = beginningDate;
    }

    public Instant getEndingDate() {
        return endingDate;
    }

    public Promotion endingDate(Instant endingDate) {
        this.endingDate = endingDate;
        return this;
    }

    public void setEndingDate(Instant endingDate) {
        this.endingDate = endingDate;
    }

    public Boolean isIsExclusive() {
        return isExclusive;
    }

    public Promotion isExclusive(Boolean isExclusive) {
        this.isExclusive = isExclusive;
        return this;
    }

    public void setIsExclusive(Boolean isExclusive) {
        this.isExclusive = isExclusive;
    }

    public Integer getPriority() {
        return priority;
    }

    public Promotion priority(Integer priority) {
        this.priority = priority;
        return this;
    }

    public void setPriority(Integer priority) {
        this.priority = priority;
    }

    public String getPromotionalText() {
        return promotionalText;
    }

    public Promotion promotionalText(String promotionalText) {
        this.promotionalText = promotionalText;
        return this;
    }

    public void setPromotionalText(String promotionalText) {
        this.promotionalText = promotionalText;
    }

    public Boolean isEnabled() {
        return enabled;
    }

    public Promotion enabled(Boolean enabled) {
        this.enabled = enabled;
        return this;
    }

    public void setEnabled(Boolean enabled) {
        this.enabled = enabled;
    }

    public Boolean isIsPercent() {
        return isPercent;
    }

    public Promotion isPercent(Boolean isPercent) {
        this.isPercent = isPercent;
        return this;
    }

    public void setIsPercent(Boolean isPercent) {
        this.isPercent = isPercent;
    }

    public byte[] getImagen() {
        return imagen;
    }

    public Promotion imagen(byte[] imagen) {
        this.imagen = imagen;
        return this;
    }

    public void setImagen(byte[] imagen) {
        this.imagen = imagen;
    }

    public String getImagenContentType() {
        return imagenContentType;
    }

    public Promotion imagenContentType(String imagenContentType) {
        this.imagenContentType = imagenContentType;
        return this;
    }

    public void setImagenContentType(String imagenContentType) {
        this.imagenContentType = imagenContentType;
    }

    public Boolean isIsAmount() {
        return isAmount;
    }

    public Promotion isAmount(Boolean isAmount) {
        this.isAmount = isAmount;
        return this;
    }

    public void setIsAmount(Boolean isAmount) {
        this.isAmount = isAmount;
    }

    public Float getValue() {
        return value;
    }

    public Promotion value(Float value) {
        this.value = value;
        return this;
    }

    public void setValue(Float value) {
        this.value = value;
    }

    public Currency getCurrency() {
        return currency;
    }

    public Promotion currency(Currency currency) {
        this.currency = currency;
        return this;
    }

    public void setCurrency(Currency currency) {
        this.currency = currency;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Promotion)) {
            return false;
        }
        return id != null && id.equals(((Promotion) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Promotion{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", promotionType='" + getPromotionType() + "'" +
            ", creationDate='" + getCreationDate() + "'" +
            ", responsibleDate='" + getResponsibleDate() + "'" +
            ", beginningDate='" + getBeginningDate() + "'" +
            ", endingDate='" + getEndingDate() + "'" +
            ", isExclusive='" + isIsExclusive() + "'" +
            ", priority=" + getPriority() +
            ", promotionalText='" + getPromotionalText() + "'" +
            ", enabled='" + isEnabled() + "'" +
            ", isPercent='" + isIsPercent() + "'" +
            ", imagen='" + getImagen() + "'" +
            ", imagenContentType='" + getImagenContentType() + "'" +
            ", isAmount='" + isIsAmount() + "'" +
            ", value=" + getValue() +
            "}";
    }
}
