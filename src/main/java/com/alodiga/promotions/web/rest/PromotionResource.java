package com.alodiga.promotions.web.rest;

import com.alodiga.promotions.domain.Promotion;
import com.alodiga.promotions.service.PromotionService;
import com.alodiga.promotions.web.rest.errors.BadRequestAlertException;
import com.alodiga.promotions.service.dto.PromotionCriteria;
import com.alodiga.promotions.service.PromotionQueryService;

import io.github.jhipster.web.util.HeaderUtil;
import io.github.jhipster.web.util.PaginationUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing {@link com.alodiga.promotions.domain.Promotion}.
 */
@RestController
@RequestMapping("/api")
public class PromotionResource {

    private final Logger log = LoggerFactory.getLogger(PromotionResource.class);

    private static final String ENTITY_NAME = "promotion";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final PromotionService promotionService;

    private final PromotionQueryService promotionQueryService;

    public PromotionResource(PromotionService promotionService, PromotionQueryService promotionQueryService) {
        this.promotionService = promotionService;
        this.promotionQueryService = promotionQueryService;
    }

    /**
     * {@code POST  /promotions} : Create a new promotion.
     *
     * @param promotion the promotion to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new promotion, or with status {@code 400 (Bad Request)} if the promotion has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/promotions")
    public ResponseEntity<Promotion> createPromotion(@Valid @RequestBody Promotion promotion) throws URISyntaxException {
        log.debug("REST request to save Promotion : {}", promotion);
        if (promotion.getId() != null) {
            throw new BadRequestAlertException("A new promotion cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Promotion result = promotionService.save(promotion);
        return ResponseEntity.created(new URI("/api/promotions/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /promotions} : Updates an existing promotion.
     *
     * @param promotion the promotion to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated promotion,
     * or with status {@code 400 (Bad Request)} if the promotion is not valid,
     * or with status {@code 500 (Internal Server Error)} if the promotion couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/promotions")
    public ResponseEntity<Promotion> updatePromotion(@Valid @RequestBody Promotion promotion) throws URISyntaxException {
        log.debug("REST request to update Promotion : {}", promotion);
        if (promotion.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        Promotion result = promotionService.save(promotion);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, promotion.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /promotions} : get all the promotions.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of promotions in body.
     */
    @GetMapping("/promotions")
    public ResponseEntity<List<Promotion>> getAllPromotions(PromotionCriteria criteria, Pageable pageable) {
        log.debug("REST request to get Promotions by criteria: {}", criteria);
        Page<Promotion> page = promotionQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /promotions/count} : count all the promotions.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/promotions/count")
    public ResponseEntity<Long> countPromotions(PromotionCriteria criteria) {
        log.debug("REST request to count Promotions by criteria: {}", criteria);
        return ResponseEntity.ok().body(promotionQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /promotions/:id} : get the "id" promotion.
     *
     * @param id the id of the promotion to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the promotion, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/promotions/{id}")
    public ResponseEntity<Promotion> getPromotion(@PathVariable Long id) {
        log.debug("REST request to get Promotion : {}", id);
        Optional<Promotion> promotion = promotionService.findOne(id);
        return ResponseUtil.wrapOrNotFound(promotion);
    }

    /**
     * {@code DELETE  /promotions/:id} : delete the "id" promotion.
     *
     * @param id the id of the promotion to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/promotions/{id}")
    public ResponseEntity<Void> deletePromotion(@PathVariable Long id) {
        log.debug("REST request to delete Promotion : {}", id);
        promotionService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
