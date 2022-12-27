package com.alodiga.promotions.web.rest;

import com.alodiga.promotions.config.ApplicationProperties;
import com.alodiga.promotions.config.Constants;
import com.alodiga.promotions.domain.Banner;
import com.alodiga.promotions.service.BannerService;
import com.alodiga.promotions.web.rest.errors.BadRequestAlertException;
import com.alodiga.promotions.service.dto.BannerCriteria;
import com.alodiga.promotions.service.BannerQueryService;

import io.github.jhipster.web.util.HeaderUtil;
import io.github.jhipster.web.util.PaginationUtil;
import io.github.jhipster.web.util.ResponseUtil;
import java.io.IOException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.logging.Level;

/**
 * REST controller for managing {@link com.alodiga.promotions.domain.Banner}.
 */
@RestController
@RequestMapping("/api")
public class BannerResource {

    private final Logger log = LoggerFactory.getLogger(BannerResource.class);

    private static final String ENTITY_NAME = "banner";
    
    private ApplicationProperties applicationProperties;

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final BannerService bannerService;

    private final BannerQueryService bannerQueryService;

    public BannerResource(BannerService bannerService, BannerQueryService bannerQueryService, ApplicationProperties applicationProperties) {
        this.bannerService = bannerService;
        this.bannerQueryService = bannerQueryService;
        this.applicationProperties = applicationProperties;
    }

    /**
     * {@code POST  /banners} : Create a new banner.
     *
     * @param banner the banner to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new banner, or with status {@code 400 (Bad Request)} if the banner has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    
    @PostMapping("/banners")
    public ResponseEntity<Banner> createBanner(@Valid @RequestBody Banner banner) throws URISyntaxException {
        log.debug("REST request to save Banner : {}", banner);
        if (banner.getId() != null) {
            throw new BadRequestAlertException("A new banner cannot already have an ID", ENTITY_NAME, "idexists");
        }
        
        ///////////////Se ingresa la imagen y se guarda la descripcion//////////////////////
        try {
            Date date = new Date();
            String time = String.valueOf(date.getTime());
            byte[] bytesImg = banner.getImagen();
            Path rutaCompleta = Paths.get(applicationProperties.getBanner().getSource_image_profile() + "email" + time + Constants.BASE_PROFILE_IMAGE_TEXT);
            Files.write(rutaCompleta, bytesImg);
            //Setea la URL de LA IMAGEN pero deber Guardar un imagen Source Nuevo
            String URL = applicationProperties.getBanner().getAddress_image_profile() + Constants.SPRING_PATH + "email" + time + Constants.BASE_PROFILE_IMAGE_TEXT;
            banner.setDescription(URL);
        } catch (IOException ex) {
            java.util.logging.Logger.getLogger(BannerResource.class.getName()).log(Level.SEVERE, null, ex);
        }
          ///////////////Se ingresa la imagen y se guarda la descripcion//////////////////////
        
        
        
        Banner result = bannerService.save(banner);
        return ResponseEntity.created(new URI("/api/banners/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /banners} : Updates an existing banner.
     *
     * @param banner the banner to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated banner,
     * or with status {@code 400 (Bad Request)} if the banner is not valid,
     * or with status {@code 500 (Internal Server Error)} if the banner couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/banners")
    public ResponseEntity<Banner> updateBanner(@Valid @RequestBody Banner banner) throws URISyntaxException {
        log.debug("REST request to update Banner : {}", banner);
        if (banner.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        
                ///////////////Se ingresa la imagen y se guarda la descripcion//////////////////////
        try {
            Date date = new Date();
            String time = String.valueOf(date.getTime());
            byte[] bytesImg = banner.getImagen();
            Path rutaCompleta = Paths.get(applicationProperties.getBanner().getSource_image_profile() + "email" + time + Constants.BASE_PROFILE_IMAGE_TEXT);
            Files.write(rutaCompleta, bytesImg);
            //Setea la URL de LA IMAGEN pero deber Guardar un imagen Source Nuevo
            String URL = applicationProperties.getBanner().getAddress_image_profile() + Constants.SPRING_PATH + "email" + time + Constants.BASE_PROFILE_IMAGE_TEXT;
            banner.setDescription(URL);
        } catch (IOException ex) {
            java.util.logging.Logger.getLogger(BannerResource.class.getName()).log(Level.SEVERE, null, ex);
        }
          ///////////////Se ingresa la imagen y se guarda la descripcion//////////////////////
        
        
        Banner result = bannerService.save(banner);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, banner.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /banners} : get all the banners.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of banners in body.
     */
    @GetMapping("/banners")
    public ResponseEntity<List<Banner>> getAllBanners(BannerCriteria criteria, Pageable pageable) {
        log.debug("REST request to get Banners by criteria: {}", criteria);
        Page<Banner> page = bannerQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /banners/count} : count all the banners.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/banners/count")
    public ResponseEntity<Long> countBanners(BannerCriteria criteria) {
        log.debug("REST request to count Banners by criteria: {}", criteria);
        return ResponseEntity.ok().body(bannerQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /banners/:id} : get the "id" banner.
     *
     * @param id the id of the banner to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the banner, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/banners/{id}")
    public ResponseEntity<Banner> getBanner(@PathVariable Long id) {
        log.debug("REST request to get Banner : {}", id);
        Optional<Banner> banner = bannerService.findOne(id);
        return ResponseUtil.wrapOrNotFound(banner);
    }

    /**
     * {@code DELETE  /banners/:id} : delete the "id" banner.
     *
     * @param id the id of the banner to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/banners/{id}")
    public ResponseEntity<Void> deleteBanner(@PathVariable Long id) {
        log.debug("REST request to delete Banner : {}", id);
        bannerService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
