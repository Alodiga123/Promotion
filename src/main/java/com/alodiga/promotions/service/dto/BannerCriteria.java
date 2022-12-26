package com.alodiga.promotions.service.dto;

import java.io.Serializable;
import java.util.Objects;
import io.github.jhipster.service.Criteria;
import com.alodiga.promotions.domain.enumeration.PostionBanner;
import io.github.jhipster.service.filter.BooleanFilter;
import io.github.jhipster.service.filter.DoubleFilter;
import io.github.jhipster.service.filter.Filter;
import io.github.jhipster.service.filter.FloatFilter;
import io.github.jhipster.service.filter.IntegerFilter;
import io.github.jhipster.service.filter.LongFilter;
import io.github.jhipster.service.filter.StringFilter;

/**
 * Criteria class for the {@link com.alodiga.promotions.domain.Banner} entity. This class is used
 * in {@link com.alodiga.promotions.web.rest.BannerResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /banners?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class BannerCriteria implements Serializable, Criteria {
    /**
     * Class for filtering PostionBanner
     */
    public static class PostionBannerFilter extends Filter<PostionBanner> {

        public PostionBannerFilter() {
        }

        public PostionBannerFilter(PostionBannerFilter filter) {
            super(filter);
        }

        @Override
        public PostionBannerFilter copy() {
            return new PostionBannerFilter(this);
        }

    }

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private StringFilter name;

    private StringFilter description;

    private PostionBannerFilter position;

    public BannerCriteria() {
    }

    public BannerCriteria(BannerCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.name = other.name == null ? null : other.name.copy();
        this.description = other.description == null ? null : other.description.copy();
        this.position = other.position == null ? null : other.position.copy();
    }

    @Override
    public BannerCriteria copy() {
        return new BannerCriteria(this);
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

    public StringFilter getDescription() {
        return description;
    }

    public void setDescription(StringFilter description) {
        this.description = description;
    }

    public PostionBannerFilter getPosition() {
        return position;
    }

    public void setPosition(PostionBannerFilter position) {
        this.position = position;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final BannerCriteria that = (BannerCriteria) o;
        return
            Objects.equals(id, that.id) &&
            Objects.equals(name, that.name) &&
            Objects.equals(description, that.description) &&
            Objects.equals(position, that.position);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
        id,
        name,
        description,
        position
        );
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "BannerCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (name != null ? "name=" + name + ", " : "") +
                (description != null ? "description=" + description + ", " : "") +
                (position != null ? "position=" + position + ", " : "") +
            "}";
    }

}
