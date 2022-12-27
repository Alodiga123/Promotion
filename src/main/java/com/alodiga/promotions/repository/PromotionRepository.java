package com.alodiga.promotions.repository;

import com.alodiga.promotions.domain.Promotion;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the Promotion entity.
 */
@SuppressWarnings("unused")
@Repository
public interface PromotionRepository extends JpaRepository<Promotion, Long>, JpaSpecificationExecutor<Promotion> {
}
