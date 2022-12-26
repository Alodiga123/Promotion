package com.alodiga.promotions.service.dto;

import java.io.Serializable;
import java.util.Objects;
import io.github.jhipster.service.Criteria;
import com.alodiga.promotions.domain.enumeration.PromotionType;
import io.github.jhipster.service.filter.BooleanFilter;
import io.github.jhipster.service.filter.DoubleFilter;
import io.github.jhipster.service.filter.Filter;
import io.github.jhipster.service.filter.FloatFilter;
import io.github.jhipster.service.filter.IntegerFilter;
import io.github.jhipster.service.filter.LongFilter;
import io.github.jhipster.service.filter.StringFilter;
import io.github.jhipster.service.filter.InstantFilter;

/**
 * Criteria class for the {@link com.alodiga.promotions.domain.Promotion} entity. This class is used
 * in {@link com.alodiga.promotions.web.rest.PromotionResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /promotions?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class PromotionCriteria implements Serializable, Criteria {
    /**
     * Class for filtering PromotionType
     */
    public static class PromotionTypeFilter extends Filter<PromotionType> {

        public PromotionTypeFilter() {
        }

        public PromotionTypeFilter(PromotionTypeFilter filter) {
            super(filter);
        }

        @Override
        public PromotionTypeFilter copy() {
            return new PromotionTypeFilter(this);
        }

    }

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private StringFilter name;

    private PromotionTypeFilter promotionType;

    private InstantFilter creationDate;

    private InstantFilter responsibleDate;

    private InstantFilter beginningDate;

    private InstantFilter endingDate;

    private BooleanFilter isExclusive;

    private IntegerFilter priority;

    private StringFilter promotionalText;

    private BooleanFilter enabled;

    private BooleanFilter isPercent;

    private BooleanFilter isAmount;

    private FloatFilter value;

    private LongFilter currencyId;

    public PromotionCriteria() {
    }

    public PromotionCriteria(PromotionCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.name = other.name == null ? null : other.name.copy();
        this.promotionType = other.promotionType == null ? null : other.promotionType.copy();
        this.creationDate = other.creationDate == null ? null : other.creationDate.copy();
        this.responsibleDate = other.responsibleDate == null ? null : other.responsibleDate.copy();
        this.beginningDate = other.beginningDate == null ? null : other.beginningDate.copy();
        this.endingDate = other.endingDate == null ? null : other.endingDate.copy();
        this.isExclusive = other.isExclusive == null ? null : other.isExclusive.copy();
        this.priority = other.priority == null ? null : other.priority.copy();
        this.promotionalText = other.promotionalText == null ? null : other.promotionalText.copy();
        this.enabled = other.enabled == null ? null : other.enabled.copy();
        this.isPercent = other.isPercent == null ? null : other.isPercent.copy();
        this.isAmount = other.isAmount == null ? null : other.isAmount.copy();
        this.value = other.value == null ? null : other.value.copy();
        this.currencyId = other.currencyId == null ? null : other.currencyId.copy();
    }

    @Override
    public PromotionCriteria copy() {
        return new PromotionCriteria(this);
    }

    public LongFilter getId() {
        return id;
    }

    public void setId(LongFilter id) {
        this.id = id;
    }

    public StringFilter getName() {
        return name;
    }

    public void setName(StringFilter name) {
        this.name = name;
    }

    public PromotionTypeFilter getPromotionType() {
        return promotionType;
    }

    public void setPromotionType(PromotionTypeFilter promotionType) {
        this.promotionType = promotionType;
    }

    public InstantFilter getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(InstantFilter creationDate) {
        this.creationDate = creationDate;
    }

    public InstantFilter getResponsibleDate() {
        return responsibleDate;
    }

    public void setResponsibleDate(InstantFilter responsibleDate) {
        this.responsibleDate = responsibleDate;
    }

    public InstantFilter getBeginningDate() {
        return beginningDate;
    }

    public void setBeginningDate(InstantFilter beginningDate) {
        this.beginningDate = beginningDate;
    }

    public InstantFilter getEndingDate() {
        return endingDate;
    }

    public void setEndingDate(InstantFilter endingDate) {
        this.endingDate = endingDate;
    }

    public BooleanFilter getIsExclusive() {
        return isExclusive;
    }

    public void setIsExclusive(BooleanFilter isExclusive) {
        this.isExclusive = isExclusive;
    }

    public IntegerFilter getPriority() {
        return priority;
    }

    public void setPriority(IntegerFilter priority) {
        this.priority = priority;
    }

    public StringFilter getPromotionalText() {
        return promotionalText;
    }

    public void setPromotionalText(StringFilter promotionalText) {
        this.promotionalText = promotionalText;
    }

    public BooleanFilter getEnabled() {
        return enabled;
    }

    public void setEnabled(BooleanFilter enabled) {
        this.enabled = enabled;
    }

    public BooleanFilter getIsPercent() {
        return isPercent;
    }

    public void setIsPercent(BooleanFilter isPercent) {
        this.isPercent = isPercent;
    }

    public BooleanFilter getIsAmount() {
        return isAmount;
    }

    public void setIsAmount(BooleanFilter isAmount) {
        this.isAmount = isAmount;
    }

    public FloatFilter getValue() {
        return value;
    }

    public void setValue(FloatFilter value) {
        this.value = value;
    }

    public LongFilter getCurrencyId() {
        return currencyId;
    }

    public void setCurrencyId(LongFilter currencyId) {
        this.currencyId = currencyId;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final PromotionCriteria that = (PromotionCriteria) o;
        return
            Objects.equals(id, that.id) &&
            Objects.equals(name, that.name) &&
            Objects.equals(promotionType, that.promotionType) &&
            Objects.equals(creationDate, that.creationDate) &&
            Objects.equals(responsibleDate, that.responsibleDate) &&
            Objects.equals(beginningDate, that.beginningDate) &&
            Objects.equals(endingDate, that.endingDate) &&
            Objects.equals(isExclusive, that.isExclusive) &&
            Objects.equals(priority, that.priority) &&
            Objects.equals(promotionalText, that.promotionalText) &&
            Objects.equals(enabled, that.enabled) &&
            Objects.equals(isPercent, that.isPercent) &&
            Objects.equals(isAmount, that.isAmount) &&
            Objects.equals(value, that.value) &&
            Objects.equals(currencyId, that.currencyId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
        id,
        name,
        promotionType,
        creationDate,
        responsibleDate,
        beginningDate,
        endingDate,
        isExclusive,
        priority,
        promotionalText,
        enabled,
        isPercent,
        isAmount,
        value,
        currencyId
        );
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "PromotionCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (name != null ? "name=" + name + ", " : "") +
                (promotionType != null ? "promotionType=" + promotionType + ", " : "") +
                (creationDate != null ? "creationDate=" + creationDate + ", " : "") +
                (responsibleDate != null ? "responsibleDate=" + responsibleDate + ", " : "") +
                (beginningDate != null ? "beginningDate=" + beginningDate + ", " : "") +
                (endingDate != null ? "endingDate=" + endingDate + ", " : "") +
                (isExclusive != null ? "isExclusive=" + isExclusive + ", " : "") +
                (priority != null ? "priority=" + priority + ", " : "") +
                (promotionalText != null ? "promotionalText=" + promotionalText + ", " : "") +
                (enabled != null ? "enabled=" + enabled + ", " : "") +
                (isPercent != null ? "isPercent=" + isPercent + ", " : "") +
                (isAmount != null ? "isAmount=" + isAmount + ", " : "") +
                (value != null ? "value=" + value + ", " : "") +
                (currencyId != null ? "currencyId=" + currencyId + ", " : "") +
            "}";
    }

}
