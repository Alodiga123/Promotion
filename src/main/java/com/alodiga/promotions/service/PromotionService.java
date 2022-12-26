package com.alodiga.promotions.service;

import com.alodiga.promotions.domain.Promotion;
import com.alodiga.promotions.repository.PromotionRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link Promotion}.
 */
@Service
@Transactional
public class PromotionService {

    private final Logger log = LoggerFactory.getLogger(PromotionService.class);

    private final PromotionRepository promotionRepository;

    public PromotionService(PromotionRepository promotionRepository) {
        this.promotionRepository = promotionRepository;
    }

    /**
     * Save a promotion.
     *
     * @param promotion the entity to save.
     * @return the persisted entity.
     */
    public Promotion save(Promotion promotion) {
        log.debug("Request to save Promotion : {}", promotion);
        return promotionRepository.save(promotion);
    }

    /**
     * Get all the promotions.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<Promotion> findAll(Pageable pageable) {
        log.debug("Request to get all Promotions");
        return promotionRepository.findAll(pageable);
    }


    /**
     * Get one promotion by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<Promotion> findOne(Long id) {
        log.debug("Request to get Promotion : {}", id);
        return promotionRepository.findById(id);
    }

    /**
     * Delete the promotion by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete Promotion : {}", id);
        promotionRepository.deleteById(id);
    }
}
