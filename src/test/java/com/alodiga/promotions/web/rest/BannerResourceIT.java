//package com.alodiga.promotions.web.rest;
//
//import com.alodiga.promotions.PromotionsApp;
//import com.alodiga.promotions.domain.Banner;
//import com.alodiga.promotions.repository.BannerRepository;
//import com.alodiga.promotions.service.BannerService;
//import com.alodiga.promotions.service.dto.BannerCriteria;
//import com.alodiga.promotions.service.BannerQueryService;
//
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.http.MediaType;
//import org.springframework.security.test.context.support.WithMockUser;
//import org.springframework.test.web.servlet.MockMvc;
//import org.springframework.transaction.annotation.Transactional;
//import org.springframework.util.Base64Utils;
//import javax.persistence.EntityManager;
//import java.util.List;
//
//import static org.assertj.core.api.Assertions.assertThat;
//import static org.hamcrest.Matchers.hasItem;
//import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
//import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
//
//import com.alodiga.promotions.domain.enumeration.PostionBanner;
///**
// * Integration tests for the {@link BannerResource} REST controller.
// */
//@SpringBootTest(classes = PromotionsApp.class)
//@AutoConfigureMockMvc
//@WithMockUser
//public class BannerResourceIT {
//
//    private static final String DEFAULT_NAME = "AAAAAAAAAA";
//    private static final String UPDATED_NAME = "BBBBBBBBBB";
//
//    private static final String DEFAULT_DESCRIPTION = "AAAAAAAAAA";
//    private static final String UPDATED_DESCRIPTION = "BBBBBBBBBB";
//
//    private static final PostionBanner DEFAULT_POSITION = PostionBanner.TOP;
//    private static final PostionBanner UPDATED_POSITION = PostionBanner.CENTER;
//
//    private static final byte[] DEFAULT_IMAGEN = TestUtil.createByteArray(1, "0");
//    private static final byte[] UPDATED_IMAGEN = TestUtil.createByteArray(1, "1");
//    private static final String DEFAULT_IMAGEN_CONTENT_TYPE = "image/jpg";
//    private static final String UPDATED_IMAGEN_CONTENT_TYPE = "image/png";
//
//    @Autowired
//    private BannerRepository bannerRepository;
//
//    @Autowired
//    private BannerService bannerService;
//
//    @Autowired
//    private BannerQueryService bannerQueryService;
//
//    @Autowired
//    private EntityManager em;
//
//    @Autowired
//    private MockMvc restBannerMockMvc;
//
//    private Banner banner;
//
//    /**
//     * Create an entity for this test.
//     *
//     * This is a static method, as tests for other entities might also need it,
//     * if they test an entity which requires the current entity.
//     */
//    public static Banner createEntity(EntityManager em) {
//        Banner banner = new Banner()
//            .name(DEFAULT_NAME)
//            .description(DEFAULT_DESCRIPTION)
//            .position(DEFAULT_POSITION)
//            .imagen(DEFAULT_IMAGEN)
//            .imagenContentType(DEFAULT_IMAGEN_CONTENT_TYPE);
//        return banner;
//    }
//    /**
//     * Create an updated entity for this test.
//     *
//     * This is a static method, as tests for other entities might also need it,
//     * if they test an entity which requires the current entity.
//     */
//    public static Banner createUpdatedEntity(EntityManager em) {
//        Banner banner = new Banner()
//            .name(UPDATED_NAME)
//            .description(UPDATED_DESCRIPTION)
//            .position(UPDATED_POSITION)
//            .imagen(UPDATED_IMAGEN)
//            .imagenContentType(UPDATED_IMAGEN_CONTENT_TYPE);
//        return banner;
//    }
//
//    @BeforeEach
//    public void initTest() {
//        banner = createEntity(em);
//    }
//
//    @Test
//    @Transactional
//    public void createBanner() throws Exception {
//        int databaseSizeBeforeCreate = bannerRepository.findAll().size();
//        // Create the Banner
//        restBannerMockMvc.perform(post("/api/banners")
//            .contentType(MediaType.APPLICATION_JSON)
//            .content(TestUtil.convertObjectToJsonBytes(banner)))
//            .andExpect(status().isCreated());
//
//        // Validate the Banner in the database
//        List<Banner> bannerList = bannerRepository.findAll();
//        assertThat(bannerList).hasSize(databaseSizeBeforeCreate + 1);
//        Banner testBanner = bannerList.get(bannerList.size() - 1);
//        assertThat(testBanner.getName()).isEqualTo(DEFAULT_NAME);
//        assertThat(testBanner.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
//        assertThat(testBanner.getPosition()).isEqualTo(DEFAULT_POSITION);
//        assertThat(testBanner.getImagen()).isEqualTo(DEFAULT_IMAGEN);
//        assertThat(testBanner.getImagenContentType()).isEqualTo(DEFAULT_IMAGEN_CONTENT_TYPE);
//    }
//
//    @Test
//    @Transactional
//    public void createBannerWithExistingId() throws Exception {
//        int databaseSizeBeforeCreate = bannerRepository.findAll().size();
//
//        // Create the Banner with an existing ID
//        banner.setId(1L);
//
//        // An entity with an existing ID cannot be created, so this API call must fail
//        restBannerMockMvc.perform(post("/api/banners")
//            .contentType(MediaType.APPLICATION_JSON)
//            .content(TestUtil.convertObjectToJsonBytes(banner)))
//            .andExpect(status().isBadRequest());
//
//        // Validate the Banner in the database
//        List<Banner> bannerList = bannerRepository.findAll();
//        assertThat(bannerList).hasSize(databaseSizeBeforeCreate);
//    }
//
//
//    @Test
//    @Transactional
//    public void checkNameIsRequired() throws Exception {
//        int databaseSizeBeforeTest = bannerRepository.findAll().size();
//        // set the field null
//        banner.setName(null);
//
//        // Create the Banner, which fails.
//
//
//        restBannerMockMvc.perform(post("/api/banners")
//            .contentType(MediaType.APPLICATION_JSON)
//            .content(TestUtil.convertObjectToJsonBytes(banner)))
//            .andExpect(status().isBadRequest());
//
//        List<Banner> bannerList = bannerRepository.findAll();
//        assertThat(bannerList).hasSize(databaseSizeBeforeTest);
//    }
//
//    @Test
//    @Transactional
//    public void checkDescriptionIsRequired() throws Exception {
//        int databaseSizeBeforeTest = bannerRepository.findAll().size();
//        // set the field null
//        banner.setDescription(null);
//
//        // Create the Banner, which fails.
//
//
//        restBannerMockMvc.perform(post("/api/banners")
//            .contentType(MediaType.APPLICATION_JSON)
//            .content(TestUtil.convertObjectToJsonBytes(banner)))
//            .andExpect(status().isBadRequest());
//
//        List<Banner> bannerList = bannerRepository.findAll();
//        assertThat(bannerList).hasSize(databaseSizeBeforeTest);
//    }
//
//    @Test
//    @Transactional
//    public void checkPositionIsRequired() throws Exception {
//        int databaseSizeBeforeTest = bannerRepository.findAll().size();
//        // set the field null
//        banner.setPosition(null);
//
//        // Create the Banner, which fails.
//
//
//        restBannerMockMvc.perform(post("/api/banners")
//            .contentType(MediaType.APPLICATION_JSON)
//            .content(TestUtil.convertObjectToJsonBytes(banner)))
//            .andExpect(status().isBadRequest());
//
//        List<Banner> bannerList = bannerRepository.findAll();
//        assertThat(bannerList).hasSize(databaseSizeBeforeTest);
//    }
//
//    @Test
//    @Transactional
//    public void getAllBanners() throws Exception {
//        // Initialize the database
//        bannerRepository.saveAndFlush(banner);
//
//        // Get all the bannerList
//        restBannerMockMvc.perform(get("/api/banners?sort=id,desc"))
//            .andExpect(status().isOk())
//            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
//            .andExpect(jsonPath("$.[*].id").value(hasItem(banner.getId().intValue())))
//            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
//            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION)))
//            .andExpect(jsonPath("$.[*].position").value(hasItem(DEFAULT_POSITION.toString())))
//            .andExpect(jsonPath("$.[*].imagenContentType").value(hasItem(DEFAULT_IMAGEN_CONTENT_TYPE)))
//            .andExpect(jsonPath("$.[*].imagen").value(hasItem(Base64Utils.encodeToString(DEFAULT_IMAGEN))));
//    }
//    
//    @Test
//    @Transactional
//    public void getBanner() throws Exception {
//        // Initialize the database
//        bannerRepository.saveAndFlush(banner);
//
//        // Get the banner
//        restBannerMockMvc.perform(get("/api/banners/{id}", banner.getId()))
//            .andExpect(status().isOk())
//            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
//            .andExpect(jsonPath("$.id").value(banner.getId().intValue()))
//            .andExpect(jsonPath("$.name").value(DEFAULT_NAME))
//            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION))
//            .andExpect(jsonPath("$.position").value(DEFAULT_POSITION.toString()))
//            .andExpect(jsonPath("$.imagenContentType").value(DEFAULT_IMAGEN_CONTENT_TYPE))
//            .andExpect(jsonPath("$.imagen").value(Base64Utils.encodeToString(DEFAULT_IMAGEN)));
//    }
//
//
//    @Test
//    @Transactional
//    public void getBannersByIdFiltering() throws Exception {
//        // Initialize the database
//        bannerRepository.saveAndFlush(banner);
//
//        Long id = banner.getId();
//
//        defaultBannerShouldBeFound("id.equals=" + id);
//        defaultBannerShouldNotBeFound("id.notEquals=" + id);
//
//        defaultBannerShouldBeFound("id.greaterThanOrEqual=" + id);
//        defaultBannerShouldNotBeFound("id.greaterThan=" + id);
//
//        defaultBannerShouldBeFound("id.lessThanOrEqual=" + id);
//        defaultBannerShouldNotBeFound("id.lessThan=" + id);
//    }
//
//
//    @Test
//    @Transactional
//    public void getAllBannersByNameIsEqualToSomething() throws Exception {
//        // Initialize the database
//        bannerRepository.saveAndFlush(banner);
//
//        // Get all the bannerList where name equals to DEFAULT_NAME
//        defaultBannerShouldBeFound("name.equals=" + DEFAULT_NAME);
//
//        // Get all the bannerList where name equals to UPDATED_NAME
//        defaultBannerShouldNotBeFound("name.equals=" + UPDATED_NAME);
//    }
//
//    @Test
//    @Transactional
//    public void getAllBannersByNameIsNotEqualToSomething() throws Exception {
//        // Initialize the database
//        bannerRepository.saveAndFlush(banner);
//
//        // Get all the bannerList where name not equals to DEFAULT_NAME
//        defaultBannerShouldNotBeFound("name.notEquals=" + DEFAULT_NAME);
//
//        // Get all the bannerList where name not equals to UPDATED_NAME
//        defaultBannerShouldBeFound("name.notEquals=" + UPDATED_NAME);
//    }
//
//    @Test
//    @Transactional
//    public void getAllBannersByNameIsInShouldWork() throws Exception {
//        // Initialize the database
//        bannerRepository.saveAndFlush(banner);
//
//        // Get all the bannerList where name in DEFAULT_NAME or UPDATED_NAME
//        defaultBannerShouldBeFound("name.in=" + DEFAULT_NAME + "," + UPDATED_NAME);
//
//        // Get all the bannerList where name equals to UPDATED_NAME
//        defaultBannerShouldNotBeFound("name.in=" + UPDATED_NAME);
//    }
//
//    @Test
//    @Transactional
//    public void getAllBannersByNameIsNullOrNotNull() throws Exception {
//        // Initialize the database
//        bannerRepository.saveAndFlush(banner);
//
//        // Get all the bannerList where name is not null
//        defaultBannerShouldBeFound("name.specified=true");
//
//        // Get all the bannerList where name is null
//        defaultBannerShouldNotBeFound("name.specified=false");
//    }
//                @Test
//    @Transactional
//    public void getAllBannersByNameContainsSomething() throws Exception {
//        // Initialize the database
//        bannerRepository.saveAndFlush(banner);
//
//        // Get all the bannerList where name contains DEFAULT_NAME
//        defaultBannerShouldBeFound("name.contains=" + DEFAULT_NAME);
//
//        // Get all the bannerList where name contains UPDATED_NAME
//        defaultBannerShouldNotBeFound("name.contains=" + UPDATED_NAME);
//    }
//
//    @Test
//    @Transactional
//    public void getAllBannersByNameNotContainsSomething() throws Exception {
//        // Initialize the database
//        bannerRepository.saveAndFlush(banner);
//
//        // Get all the bannerList where name does not contain DEFAULT_NAME
//        defaultBannerShouldNotBeFound("name.doesNotContain=" + DEFAULT_NAME);
//
//        // Get all the bannerList where name does not contain UPDATED_NAME
//        defaultBannerShouldBeFound("name.doesNotContain=" + UPDATED_NAME);
//    }
//
//
//    @Test
//    @Transactional
//    public void getAllBannersByDescriptionIsEqualToSomething() throws Exception {
//        // Initialize the database
//        bannerRepository.saveAndFlush(banner);
//
//        // Get all the bannerList where description equals to DEFAULT_DESCRIPTION
//        defaultBannerShouldBeFound("description.equals=" + DEFAULT_DESCRIPTION);
//
//        // Get all the bannerList where description equals to UPDATED_DESCRIPTION
//        defaultBannerShouldNotBeFound("description.equals=" + UPDATED_DESCRIPTION);
//    }
//
//    @Test
//    @Transactional
//    public void getAllBannersByDescriptionIsNotEqualToSomething() throws Exception {
//        // Initialize the database
//        bannerRepository.saveAndFlush(banner);
//
//        // Get all the bannerList where description not equals to DEFAULT_DESCRIPTION
//        defaultBannerShouldNotBeFound("description.notEquals=" + DEFAULT_DESCRIPTION);
//
//        // Get all the bannerList where description not equals to UPDATED_DESCRIPTION
//        defaultBannerShouldBeFound("description.notEquals=" + UPDATED_DESCRIPTION);
//    }
//
//    @Test
//    @Transactional
//    public void getAllBannersByDescriptionIsInShouldWork() throws Exception {
//        // Initialize the database
//        bannerRepository.saveAndFlush(banner);
//
//        // Get all the bannerList where description in DEFAULT_DESCRIPTION or UPDATED_DESCRIPTION
//        defaultBannerShouldBeFound("description.in=" + DEFAULT_DESCRIPTION + "," + UPDATED_DESCRIPTION);
//
//        // Get all the bannerList where description equals to UPDATED_DESCRIPTION
//        defaultBannerShouldNotBeFound("description.in=" + UPDATED_DESCRIPTION);
//    }
//
//    @Test
//    @Transactional
//    public void getAllBannersByDescriptionIsNullOrNotNull() throws Exception {
//        // Initialize the database
//        bannerRepository.saveAndFlush(banner);
//
//        // Get all the bannerList where description is not null
//        defaultBannerShouldBeFound("description.specified=true");
//
//        // Get all the bannerList where description is null
//        defaultBannerShouldNotBeFound("description.specified=false");
//    }
//                @Test
//    @Transactional
//    public void getAllBannersByDescriptionContainsSomething() throws Exception {
//        // Initialize the database
//        bannerRepository.saveAndFlush(banner);
//
//        // Get all the bannerList where description contains DEFAULT_DESCRIPTION
//        defaultBannerShouldBeFound("description.contains=" + DEFAULT_DESCRIPTION);
//
//        // Get all the bannerList where description contains UPDATED_DESCRIPTION
//        defaultBannerShouldNotBeFound("description.contains=" + UPDATED_DESCRIPTION);
//    }
//
//    @Test
//    @Transactional
//    public void getAllBannersByDescriptionNotContainsSomething() throws Exception {
//        // Initialize the database
//        bannerRepository.saveAndFlush(banner);
//
//        // Get all the bannerList where description does not contain DEFAULT_DESCRIPTION
//        defaultBannerShouldNotBeFound("description.doesNotContain=" + DEFAULT_DESCRIPTION);
//
//        // Get all the bannerList where description does not contain UPDATED_DESCRIPTION
//        defaultBannerShouldBeFound("description.doesNotContain=" + UPDATED_DESCRIPTION);
//    }
//
//
//    @Test
//    @Transactional
//    public void getAllBannersByPositionIsEqualToSomething() throws Exception {
//        // Initialize the database
//        bannerRepository.saveAndFlush(banner);
//
//        // Get all the bannerList where position equals to DEFAULT_POSITION
//        defaultBannerShouldBeFound("position.equals=" + DEFAULT_POSITION);
//
//        // Get all the bannerList where position equals to UPDATED_POSITION
//        defaultBannerShouldNotBeFound("position.equals=" + UPDATED_POSITION);
//    }
//
//    @Test
//    @Transactional
//    public void getAllBannersByPositionIsNotEqualToSomething() throws Exception {
//        // Initialize the database
//        bannerRepository.saveAndFlush(banner);
//
//        // Get all the bannerList where position not equals to DEFAULT_POSITION
//        defaultBannerShouldNotBeFound("position.notEquals=" + DEFAULT_POSITION);
//
//        // Get all the bannerList where position not equals to UPDATED_POSITION
//        defaultBannerShouldBeFound("position.notEquals=" + UPDATED_POSITION);
//    }
//
//    @Test
//    @Transactional
//    public void getAllBannersByPositionIsInShouldWork() throws Exception {
//        // Initialize the database
//        bannerRepository.saveAndFlush(banner);
//
//        // Get all the bannerList where position in DEFAULT_POSITION or UPDATED_POSITION
//        defaultBannerShouldBeFound("position.in=" + DEFAULT_POSITION + "," + UPDATED_POSITION);
//
//        // Get all the bannerList where position equals to UPDATED_POSITION
//        defaultBannerShouldNotBeFound("position.in=" + UPDATED_POSITION);
//    }
//
//    @Test
//    @Transactional
//    public void getAllBannersByPositionIsNullOrNotNull() throws Exception {
//        // Initialize the database
//        bannerRepository.saveAndFlush(banner);
//
//        // Get all the bannerList where position is not null
//        defaultBannerShouldBeFound("position.specified=true");
//
//        // Get all the bannerList where position is null
//        defaultBannerShouldNotBeFound("position.specified=false");
//    }
//    /**
//     * Executes the search, and checks that the default entity is returned.
//     */
//    private void defaultBannerShouldBeFound(String filter) throws Exception {
//        restBannerMockMvc.perform(get("/api/banners?sort=id,desc&" + filter))
//            .andExpect(status().isOk())
//            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
//            .andExpect(jsonPath("$.[*].id").value(hasItem(banner.getId().intValue())))
//            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
//            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION)))
//            .andExpect(jsonPath("$.[*].position").value(hasItem(DEFAULT_POSITION.toString())))
//            .andExpect(jsonPath("$.[*].imagenContentType").value(hasItem(DEFAULT_IMAGEN_CONTENT_TYPE)))
//            .andExpect(jsonPath("$.[*].imagen").value(hasItem(Base64Utils.encodeToString(DEFAULT_IMAGEN))));
//
//        // Check, that the count call also returns 1
//        restBannerMockMvc.perform(get("/api/banners/count?sort=id,desc&" + filter))
//            .andExpect(status().isOk())
//            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
//            .andExpect(content().string("1"));
//    }
//
//    /**
//     * Executes the search, and checks that the default entity is not returned.
//     */
//    private void defaultBannerShouldNotBeFound(String filter) throws Exception {
//        restBannerMockMvc.perform(get("/api/banners?sort=id,desc&" + filter))
//            .andExpect(status().isOk())
//            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
//            .andExpect(jsonPath("$").isArray())
//            .andExpect(jsonPath("$").isEmpty());
//
//        // Check, that the count call also returns 0
//        restBannerMockMvc.perform(get("/api/banners/count?sort=id,desc&" + filter))
//            .andExpect(status().isOk())
//            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
//            .andExpect(content().string("0"));
//    }
//
//    @Test
//    @Transactional
//    public void getNonExistingBanner() throws Exception {
//        // Get the banner
//        restBannerMockMvc.perform(get("/api/banners/{id}", Long.MAX_VALUE))
//            .andExpect(status().isNotFound());
//    }
//
//    @Test
//    @Transactional
//    public void updateBanner() throws Exception {
//        // Initialize the database
//        bannerService.save(banner);
//
//        int databaseSizeBeforeUpdate = bannerRepository.findAll().size();
//
//        // Update the banner
//        Banner updatedBanner = bannerRepository.findById(banner.getId()).get();
//        // Disconnect from session so that the updates on updatedBanner are not directly saved in db
//        em.detach(updatedBanner);
//        updatedBanner
//            .name(UPDATED_NAME)
//            .description(UPDATED_DESCRIPTION)
//            .position(UPDATED_POSITION)
//            .imagen(UPDATED_IMAGEN)
//            .imagenContentType(UPDATED_IMAGEN_CONTENT_TYPE);
//
//        restBannerMockMvc.perform(put("/api/banners")
//            .contentType(MediaType.APPLICATION_JSON)
//            .content(TestUtil.convertObjectToJsonBytes(updatedBanner)))
//            .andExpect(status().isOk());
//
//        // Validate the Banner in the database
//        List<Banner> bannerList = bannerRepository.findAll();
//        assertThat(bannerList).hasSize(databaseSizeBeforeUpdate);
//        Banner testBanner = bannerList.get(bannerList.size() - 1);
//        assertThat(testBanner.getName()).isEqualTo(UPDATED_NAME);
//        assertThat(testBanner.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
//        assertThat(testBanner.getPosition()).isEqualTo(UPDATED_POSITION);
//        assertThat(testBanner.getImagen()).isEqualTo(UPDATED_IMAGEN);
//        assertThat(testBanner.getImagenContentType()).isEqualTo(UPDATED_IMAGEN_CONTENT_TYPE);
//    }
//
//    @Test
//    @Transactional
//    public void updateNonExistingBanner() throws Exception {
//        int databaseSizeBeforeUpdate = bannerRepository.findAll().size();
//
//        // If the entity doesn't have an ID, it will throw BadRequestAlertException
//        restBannerMockMvc.perform(put("/api/banners")
//            .contentType(MediaType.APPLICATION_JSON)
//            .content(TestUtil.convertObjectToJsonBytes(banner)))
//            .andExpect(status().isBadRequest());
//
//        // Validate the Banner in the database
//        List<Banner> bannerList = bannerRepository.findAll();
//        assertThat(bannerList).hasSize(databaseSizeBeforeUpdate);
//    }
//
//    @Test
//    @Transactional
//    public void deleteBanner() throws Exception {
//        // Initialize the database
//        bannerService.save(banner);
//
//        int databaseSizeBeforeDelete = bannerRepository.findAll().size();
//
//        // Delete the banner
//        restBannerMockMvc.perform(delete("/api/banners/{id}", banner.getId())
//            .accept(MediaType.APPLICATION_JSON))
//            .andExpect(status().isNoContent());
//
//        // Validate the database contains one less item
//        List<Banner> bannerList = bannerRepository.findAll();
//        assertThat(bannerList).hasSize(databaseSizeBeforeDelete - 1);
//    }
//}
