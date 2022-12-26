package com.alodiga.promotions.service;

import java.util.List;

import javax.persistence.criteria.JoinType;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import io.github.jhipster.service.QueryService;

import com.alodiga.promotions.domain.Promotion;
import com.alodiga.promotions.domain.*; // for static metamodels
import com.alodiga.promotions.repository.PromotionRepository;
import com.alodiga.promotions.service.dto.PromotionCriteria;

/**
 * Service for executing complex queries for {@link Promotion} entities in the database.
 * The main input is a {@link PromotionCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link Promotion} or a {@link Page} of {@link Promotion} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class PromotionQueryService extends QueryService<Promotion> {

    private final Logger log = LoggerFactory.getLogger(PromotionQueryService.class);

    private final PromotionRepository promotionRepository;

    public PromotionQueryService(PromotionRepository promotionRepository) {
        this.promotionRepository = promotionRepository;
    }

    /**
     * Return a {@link List} of {@link Promotion} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<Promotion> findByCriteria(PromotionCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<Promotion> specification = createSpecification(criteria);
        return promotionRepository.findAll(specification);
    }

    /**
     * Return a {@link Page} of {@link Promotion} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<Promotion> findByCriteria(PromotionCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<Promotion> specification = createSpecification(criteria);
        return promotionRepository.findAll(specification, page);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(PromotionCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<Promotion> specification = createSpecification(criteria);
        return promotionRepository.count(specification);
    }

    /**
     * Function to convert {@link PromotionCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<Promotion> createSpecification(PromotionCriteria criteria) {
        Specification<Promotion> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), Promotion_.id));
            }
            if (criteria.getName() != null) {
                specification = specification.and(buildStringSpecification(criteria.getName(), Promotion_.name));
            }
            if (criteria.getPromotionType() != null) {
                specification = specification.and(buildSpecification(criteria.getPromotionType(), Promotion_.promotionType));
            }
            if (criteria.getCreationDate() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getCreationDate(), Promotion_.creationDate));
            }
            if (criteria.getResponsibleDate() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getResponsibleDate(), Promotion_.responsibleDate));
            }
            if (criteria.getBeginningDate() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getBeginningDate(), Promotion_.beginningDate));
            }
            if (criteria.getEndingDate() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getEndingDate(), Promotion_.endingDate));
            }
            if (criteria.getIsExclusive() != null) {
                specification = specification.and(buildSpecification(criteria.getIsExclusive(), Promotion_.isExclusive));
            }
            if (criteria.getPriority() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getPriority(), Promotion_.priority));
            }
            if (criteria.getPromotionalText() != null) {
                specification = specification.and(buildStringSpecification(criteria.getPromotionalText(), Promotion_.promotionalText));
            }
            if (criteria.getEnabled() != null) {
                specification = specification.and(buildSpecification(criteria.getEnabled(), Promotion_.enabled));
            }
            if (criteria.getIsPercent() != null) {
                specification = specification.and(buildSpecification(criteria.getIsPercent(), Promotion_.isPercent));
            }
            if (criteria.getIsAmount() != null) {
                specification = specification.and(buildSpecification(criteria.getIsAmount(), Promotion_.isAmount));
            }
            if (criteria.getValue() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getValue(), Promotion_.value));
            }
            if (criteria.getCurrencyId() != null) {
                specification = specification.and(buildSpecification(criteria.getCurrencyId(),
                    root -> root.join(Promotion_.currency, JoinType.LEFT).get(Currency_.id)));
            }
        }
        return specification;
    }
}
