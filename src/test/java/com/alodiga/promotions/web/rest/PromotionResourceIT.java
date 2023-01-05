package com.alodiga.promotions.web.rest;

import com.alodiga.promotions.PromotionsApp;
import com.alodiga.promotions.domain.Promotion;
import com.alodiga.promotions.domain.Currency;
import com.alodiga.promotions.repository.PromotionRepository;
import com.alodiga.promotions.service.PromotionService;
import com.alodiga.promotions.service.dto.PromotionCriteria;
import com.alodiga.promotions.service.PromotionQueryService;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Base64Utils;
import javax.persistence.EntityManager;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.alodiga.promotions.domain.enumeration.PromotionType;
import com.alodiga.promotions.domain.enumeration.TipoTransaction;
/**
 * Integration tests for the {@link PromotionResource} REST controller.
 */
@SpringBootTest(classes = PromotionsApp.class)
@AutoConfigureMockMvc
@WithMockUser
public class PromotionResourceIT {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final PromotionType DEFAULT_PROMOTION_TYPE = PromotionType.REFERRED;
    private static final PromotionType UPDATED_PROMOTION_TYPE = PromotionType.BIRTHDAY;

    private static final Instant DEFAULT_CREATION_DATE = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_CREATION_DATE = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Instant DEFAULT_RESPONSIBLE_DATE = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_RESPONSIBLE_DATE = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Instant DEFAULT_BEGINNING_DATE = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_BEGINNING_DATE = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Instant DEFAULT_ENDING_DATE = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_ENDING_DATE = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Boolean DEFAULT_IS_EXCLUSIVE = false;
    private static final Boolean UPDATED_IS_EXCLUSIVE = true;

    private static final Integer DEFAULT_PRIORITY = 1;
    private static final Integer UPDATED_PRIORITY = 2;
    private static final Integer SMALLER_PRIORITY = 1 - 1;

    private static final String DEFAULT_PROMOTIONAL_TEXT = "AAAAAAAAAA";
    private static final String UPDATED_PROMOTIONAL_TEXT = "BBBBBBBBBB";

    private static final Boolean DEFAULT_ENABLED = false;
    private static final Boolean UPDATED_ENABLED = true;

    private static final Boolean DEFAULT_IS_PERCENT = false;
    private static final Boolean UPDATED_IS_PERCENT = true;

    private static final byte[] DEFAULT_IMAGEN = TestUtil.createByteArray(1, "0");
    private static final byte[] UPDATED_IMAGEN = TestUtil.createByteArray(1, "1");
    private static final String DEFAULT_IMAGEN_CONTENT_TYPE = "image/jpg";
    private static final String UPDATED_IMAGEN_CONTENT_TYPE = "image/png";

    private static final Boolean DEFAULT_IS_AMOUNT = false;
    private static final Boolean UPDATED_IS_AMOUNT = true;

    private static final Float DEFAULT_VALUE = 1F;
    private static final Float UPDATED_VALUE = 2F;
    private static final Float SMALLER_VALUE = 1F - 1F;

    private static final Float DEFAULT_AMOUNT = 1F;
    private static final Float UPDATED_AMOUNT = 2F;
    private static final Float SMALLER_AMOUNT = 1F - 1F;

    private static final TipoTransaction DEFAULT_TRANSACTION_TYPE = TipoTransaction.RETIRO_MANUAL;
    private static final TipoTransaction UPDATED_TRANSACTION_TYPE = TipoTransaction.RECARGA_MANUAL;

    @Autowired
    private PromotionRepository promotionRepository;

    @Autowired
    private PromotionService promotionService;

    @Autowired
    private PromotionQueryService promotionQueryService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restPromotionMockMvc;

    private Promotion promotion;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Promotion createEntity(EntityManager em) {
        Promotion promotion = new Promotion()
            .name(DEFAULT_NAME)
            .promotionType(DEFAULT_PROMOTION_TYPE)
            .creationDate(DEFAULT_CREATION_DATE)
            .responsibleDate(DEFAULT_RESPONSIBLE_DATE)
            .beginningDate(DEFAULT_BEGINNING_DATE)
            .endingDate(DEFAULT_ENDING_DATE)
            .isExclusive(DEFAULT_IS_EXCLUSIVE)
            .priority(DEFAULT_PRIORITY)
            .promotionalText(DEFAULT_PROMOTIONAL_TEXT)
            .enabled(DEFAULT_ENABLED)
            .isPercent(DEFAULT_IS_PERCENT)
            .imagen(DEFAULT_IMAGEN)
            .imagenContentType(DEFAULT_IMAGEN_CONTENT_TYPE)
            .isAmount(DEFAULT_IS_AMOUNT)
            .value(DEFAULT_VALUE)
            .amount(DEFAULT_AMOUNT)
            .transactionType(DEFAULT_TRANSACTION_TYPE);
        return promotion;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Promotion createUpdatedEntity(EntityManager em) {
        Promotion promotion = new Promotion()
            .name(UPDATED_NAME)
            .promotionType(UPDATED_PROMOTION_TYPE)
            .creationDate(UPDATED_CREATION_DATE)
            .responsibleDate(UPDATED_RESPONSIBLE_DATE)
            .beginningDate(UPDATED_BEGINNING_DATE)
            .endingDate(UPDATED_ENDING_DATE)
            .isExclusive(UPDATED_IS_EXCLUSIVE)
            .priority(UPDATED_PRIORITY)
            .promotionalText(UPDATED_PROMOTIONAL_TEXT)
            .enabled(UPDATED_ENABLED)
            .isPercent(UPDATED_IS_PERCENT)
            .imagen(UPDATED_IMAGEN)
            .imagenContentType(UPDATED_IMAGEN_CONTENT_TYPE)
            .isAmount(UPDATED_IS_AMOUNT)
            .value(UPDATED_VALUE)
            .amount(UPDATED_AMOUNT)
            .transactionType(UPDATED_TRANSACTION_TYPE);
        return promotion;
    }

    @BeforeEach
    public void initTest() {
        promotion = createEntity(em);
    }

    @Test
    @Transactional
    public void createPromotion() throws Exception {
        int databaseSizeBeforeCreate = promotionRepository.findAll().size();
        // Create the Promotion
        restPromotionMockMvc.perform(post("/api/promotions")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(promotion)))
            .andExpect(status().isCreated());

        // Validate the Promotion in the database
        List<Promotion> promotionList = promotionRepository.findAll();
        assertThat(promotionList).hasSize(databaseSizeBeforeCreate + 1);
        Promotion testPromotion = promotionList.get(promotionList.size() - 1);
        assertThat(testPromotion.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testPromotion.getPromotionType()).isEqualTo(DEFAULT_PROMOTION_TYPE);
        assertThat(testPromotion.getCreationDate()).isEqualTo(DEFAULT_CREATION_DATE);
        assertThat(testPromotion.getResponsibleDate()).isEqualTo(DEFAULT_RESPONSIBLE_DATE);
        assertThat(testPromotion.getBeginningDate()).isEqualTo(DEFAULT_BEGINNING_DATE);
        assertThat(testPromotion.getEndingDate()).isEqualTo(DEFAULT_ENDING_DATE);
        assertThat(testPromotion.isIsExclusive()).isEqualTo(DEFAULT_IS_EXCLUSIVE);
        assertThat(testPromotion.getPriority()).isEqualTo(DEFAULT_PRIORITY);
        assertThat(testPromotion.getPromotionalText()).isEqualTo(DEFAULT_PROMOTIONAL_TEXT);
        assertThat(testPromotion.isEnabled()).isEqualTo(DEFAULT_ENABLED);
        assertThat(testPromotion.isIsPercent()).isEqualTo(DEFAULT_IS_PERCENT);
        assertThat(testPromotion.getImagen()).isEqualTo(DEFAULT_IMAGEN);
        assertThat(testPromotion.getImagenContentType()).isEqualTo(DEFAULT_IMAGEN_CONTENT_TYPE);
        assertThat(testPromotion.isIsAmount()).isEqualTo(DEFAULT_IS_AMOUNT);
        assertThat(testPromotion.getValue()).isEqualTo(DEFAULT_VALUE);
        assertThat(testPromotion.getAmount()).isEqualTo(DEFAULT_AMOUNT);
        assertThat(testPromotion.getTransactionType()).isEqualTo(DEFAULT_TRANSACTION_TYPE);
    }

    @Test
    @Transactional
    public void createPromotionWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = promotionRepository.findAll().size();

        // Create the Promotion with an existing ID
        promotion.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restPromotionMockMvc.perform(post("/api/promotions")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(promotion)))
            .andExpect(status().isBadRequest());

        // Validate the Promotion in the database
        List<Promotion> promotionList = promotionRepository.findAll();
        assertThat(promotionList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = promotionRepository.findAll().size();
        // set the field null
        promotion.setName(null);

        // Create the Promotion, which fails.


        restPromotionMockMvc.perform(post("/api/promotions")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(promotion)))
            .andExpect(status().isBadRequest());

        List<Promotion> promotionList = promotionRepository.findAll();
        assertThat(promotionList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkPromotionTypeIsRequired() throws Exception {
        int databaseSizeBeforeTest = promotionRepository.findAll().size();
        // set the field null
        promotion.setPromotionType(null);

        // Create the Promotion, which fails.


        restPromotionMockMvc.perform(post("/api/promotions")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(promotion)))
            .andExpect(status().isBadRequest());

        List<Promotion> promotionList = promotionRepository.findAll();
        assertThat(promotionList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkIsExclusiveIsRequired() throws Exception {
        int databaseSizeBeforeTest = promotionRepository.findAll().size();
        // set the field null
        promotion.setIsExclusive(null);

        // Create the Promotion, which fails.


        restPromotionMockMvc.perform(post("/api/promotions")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(promotion)))
            .andExpect(status().isBadRequest());

        List<Promotion> promotionList = promotionRepository.findAll();
        assertThat(promotionList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkPriorityIsRequired() throws Exception {
        int databaseSizeBeforeTest = promotionRepository.findAll().size();
        // set the field null
        promotion.setPriority(null);

        // Create the Promotion, which fails.


        restPromotionMockMvc.perform(post("/api/promotions")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(promotion)))
            .andExpect(status().isBadRequest());

        List<Promotion> promotionList = promotionRepository.findAll();
        assertThat(promotionList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkPromotionalTextIsRequired() throws Exception {
        int databaseSizeBeforeTest = promotionRepository.findAll().size();
        // set the field null
        promotion.setPromotionalText(null);

        // Create the Promotion, which fails.


        restPromotionMockMvc.perform(post("/api/promotions")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(promotion)))
            .andExpect(status().isBadRequest());

        List<Promotion> promotionList = promotionRepository.findAll();
        assertThat(promotionList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkEnabledIsRequired() throws Exception {
        int databaseSizeBeforeTest = promotionRepository.findAll().size();
        // set the field null
        promotion.setEnabled(null);

        // Create the Promotion, which fails.


        restPromotionMockMvc.perform(post("/api/promotions")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(promotion)))
            .andExpect(status().isBadRequest());

        List<Promotion> promotionList = promotionRepository.findAll();
        assertThat(promotionList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllPromotions() throws Exception {
        // Initialize the database
        promotionRepository.saveAndFlush(promotion);

        // Get all the promotionList
        restPromotionMockMvc.perform(get("/api/promotions?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(promotion.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].promotionType").value(hasItem(DEFAULT_PROMOTION_TYPE.toString())))
            .andExpect(jsonPath("$.[*].creationDate").value(hasItem(DEFAULT_CREATION_DATE.toString())))
            .andExpect(jsonPath("$.[*].responsibleDate").value(hasItem(DEFAULT_RESPONSIBLE_DATE.toString())))
            .andExpect(jsonPath("$.[*].beginningDate").value(hasItem(DEFAULT_BEGINNING_DATE.toString())))
            .andExpect(jsonPath("$.[*].endingDate").value(hasItem(DEFAULT_ENDING_DATE.toString())))
            .andExpect(jsonPath("$.[*].isExclusive").value(hasItem(DEFAULT_IS_EXCLUSIVE.booleanValue())))
            .andExpect(jsonPath("$.[*].priority").value(hasItem(DEFAULT_PRIORITY)))
            .andExpect(jsonPath("$.[*].promotionalText").value(hasItem(DEFAULT_PROMOTIONAL_TEXT)))
            .andExpect(jsonPath("$.[*].enabled").value(hasItem(DEFAULT_ENABLED.booleanValue())))
            .andExpect(jsonPath("$.[*].isPercent").value(hasItem(DEFAULT_IS_PERCENT.booleanValue())))
            .andExpect(jsonPath("$.[*].imagenContentType").value(hasItem(DEFAULT_IMAGEN_CONTENT_TYPE)))
            .andExpect(jsonPath("$.[*].imagen").value(hasItem(Base64Utils.encodeToString(DEFAULT_IMAGEN))))
            .andExpect(jsonPath("$.[*].isAmount").value(hasItem(DEFAULT_IS_AMOUNT.booleanValue())))
            .andExpect(jsonPath("$.[*].value").value(hasItem(DEFAULT_VALUE.doubleValue())))
            .andExpect(jsonPath("$.[*].amount").value(hasItem(DEFAULT_AMOUNT.doubleValue())))
            .andExpect(jsonPath("$.[*].transactionType").value(hasItem(DEFAULT_TRANSACTION_TYPE.toString())));
    }
    
    @Test
    @Transactional
    public void getPromotion() throws Exception {
        // Initialize the database
        promotionRepository.saveAndFlush(promotion);

        // Get the promotion
        restPromotionMockMvc.perform(get("/api/promotions/{id}", promotion.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(promotion.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME))
            .andExpect(jsonPath("$.promotionType").value(DEFAULT_PROMOTION_TYPE.toString()))
            .andExpect(jsonPath("$.creationDate").value(DEFAULT_CREATION_DATE.toString()))
            .andExpect(jsonPath("$.responsibleDate").value(DEFAULT_RESPONSIBLE_DATE.toString()))
            .andExpect(jsonPath("$.beginningDate").value(DEFAULT_BEGINNING_DATE.toString()))
            .andExpect(jsonPath("$.endingDate").value(DEFAULT_ENDING_DATE.toString()))
            .andExpect(jsonPath("$.isExclusive").value(DEFAULT_IS_EXCLUSIVE.booleanValue()))
            .andExpect(jsonPath("$.priority").value(DEFAULT_PRIORITY))
            .andExpect(jsonPath("$.promotionalText").value(DEFAULT_PROMOTIONAL_TEXT))
            .andExpect(jsonPath("$.enabled").value(DEFAULT_ENABLED.booleanValue()))
            .andExpect(jsonPath("$.isPercent").value(DEFAULT_IS_PERCENT.booleanValue()))
            .andExpect(jsonPath("$.imagenContentType").value(DEFAULT_IMAGEN_CONTENT_TYPE))
            .andExpect(jsonPath("$.imagen").value(Base64Utils.encodeToString(DEFAULT_IMAGEN)))
            .andExpect(jsonPath("$.isAmount").value(DEFAULT_IS_AMOUNT.booleanValue()))
            .andExpect(jsonPath("$.value").value(DEFAULT_VALUE.doubleValue()))
            .andExpect(jsonPath("$.amount").value(DEFAULT_AMOUNT.doubleValue()))
            .andExpect(jsonPath("$.transactionType").value(DEFAULT_TRANSACTION_TYPE.toString()));
    }


    @Test
    @Transactional
    public void getPromotionsByIdFiltering() throws Exception {
        // Initialize the database
        promotionRepository.saveAndFlush(promotion);

        Long id = promotion.getId();

        defaultPromotionShouldBeFound("id.equals=" + id);
        defaultPromotionShouldNotBeFound("id.notEquals=" + id);

        defaultPromotionShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultPromotionShouldNotBeFound("id.greaterThan=" + id);

        defaultPromotionShouldBeFound("id.lessThanOrEqual=" + id);
        defaultPromotionShouldNotBeFound("id.lessThan=" + id);
    }


    @Test
    @Transactional
    public void getAllPromotionsByNameIsEqualToSomething() throws Exception {
        // Initialize the database
        promotionRepository.saveAndFlush(promotion);

        // Get all the promotionList where name equals to DEFAULT_NAME
        defaultPromotionShouldBeFound("name.equals=" + DEFAULT_NAME);

        // Get all the promotionList where name equals to UPDATED_NAME
        defaultPromotionShouldNotBeFound("name.equals=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    public void getAllPromotionsByNameIsNotEqualToSomething() throws Exception {
        // Initialize the database
        promotionRepository.saveAndFlush(promotion);

        // Get all the promotionList where name not equals to DEFAULT_NAME
        defaultPromotionShouldNotBeFound("name.notEquals=" + DEFAULT_NAME);

        // Get all the promotionList where name not equals to UPDATED_NAME
        defaultPromotionShouldBeFound("name.notEquals=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    public void getAllPromotionsByNameIsInShouldWork() throws Exception {
        // Initialize the database
        promotionRepository.saveAndFlush(promotion);

        // Get all the promotionList where name in DEFAULT_NAME or UPDATED_NAME
        defaultPromotionShouldBeFound("name.in=" + DEFAULT_NAME + "," + UPDATED_NAME);

        // Get all the promotionList where name equals to UPDATED_NAME
        defaultPromotionShouldNotBeFound("name.in=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    public void getAllPromotionsByNameIsNullOrNotNull() throws Exception {
        // Initialize the database
        promotionRepository.saveAndFlush(promotion);

        // Get all the promotionList where name is not null
        defaultPromotionShouldBeFound("name.specified=true");

        // Get all the promotionList where name is null
        defaultPromotionShouldNotBeFound("name.specified=false");
    }
                @Test
    @Transactional
    public void getAllPromotionsByNameContainsSomething() throws Exception {
        // Initialize the database
        promotionRepository.saveAndFlush(promotion);

        // Get all the promotionList where name contains DEFAULT_NAME
        defaultPromotionShouldBeFound("name.contains=" + DEFAULT_NAME);

        // Get all the promotionList where name contains UPDATED_NAME
        defaultPromotionShouldNotBeFound("name.contains=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    public void getAllPromotionsByNameNotContainsSomething() throws Exception {
        // Initialize the database
        promotionRepository.saveAndFlush(promotion);

        // Get all the promotionList where name does not contain DEFAULT_NAME
        defaultPromotionShouldNotBeFound("name.doesNotContain=" + DEFAULT_NAME);

        // Get all the promotionList where name does not contain UPDATED_NAME
        defaultPromotionShouldBeFound("name.doesNotContain=" + UPDATED_NAME);
    }


    @Test
    @Transactional
    public void getAllPromotionsByPromotionTypeIsEqualToSomething() throws Exception {
        // Initialize the database
        promotionRepository.saveAndFlush(promotion);

        // Get all the promotionList where promotionType equals to DEFAULT_PROMOTION_TYPE
        defaultPromotionShouldBeFound("promotionType.equals=" + DEFAULT_PROMOTION_TYPE);

        // Get all the promotionList where promotionType equals to UPDATED_PROMOTION_TYPE
        defaultPromotionShouldNotBeFound("promotionType.equals=" + UPDATED_PROMOTION_TYPE);
    }

    @Test
    @Transactional
    public void getAllPromotionsByPromotionTypeIsNotEqualToSomething() throws Exception {
        // Initialize the database
        promotionRepository.saveAndFlush(promotion);

        // Get all the promotionList where promotionType not equals to DEFAULT_PROMOTION_TYPE
        defaultPromotionShouldNotBeFound("promotionType.notEquals=" + DEFAULT_PROMOTION_TYPE);

        // Get all the promotionList where promotionType not equals to UPDATED_PROMOTION_TYPE
        defaultPromotionShouldBeFound("promotionType.notEquals=" + UPDATED_PROMOTION_TYPE);
    }

    @Test
    @Transactional
    public void getAllPromotionsByPromotionTypeIsInShouldWork() throws Exception {
        // Initialize the database
        promotionRepository.saveAndFlush(promotion);

        // Get all the promotionList where promotionType in DEFAULT_PROMOTION_TYPE or UPDATED_PROMOTION_TYPE
        defaultPromotionShouldBeFound("promotionType.in=" + DEFAULT_PROMOTION_TYPE + "," + UPDATED_PROMOTION_TYPE);

        // Get all the promotionList where promotionType equals to UPDATED_PROMOTION_TYPE
        defaultPromotionShouldNotBeFound("promotionType.in=" + UPDATED_PROMOTION_TYPE);
    }

    @Test
    @Transactional
    public void getAllPromotionsByPromotionTypeIsNullOrNotNull() throws Exception {
        // Initialize the database
        promotionRepository.saveAndFlush(promotion);

        // Get all the promotionList where promotionType is not null
        defaultPromotionShouldBeFound("promotionType.specified=true");

        // Get all the promotionList where promotionType is null
        defaultPromotionShouldNotBeFound("promotionType.specified=false");
    }

    @Test
    @Transactional
    public void getAllPromotionsByCreationDateIsEqualToSomething() throws Exception {
        // Initialize the database
        promotionRepository.saveAndFlush(promotion);

        // Get all the promotionList where creationDate equals to DEFAULT_CREATION_DATE
        defaultPromotionShouldBeFound("creationDate.equals=" + DEFAULT_CREATION_DATE);

        // Get all the promotionList where creationDate equals to UPDATED_CREATION_DATE
        defaultPromotionShouldNotBeFound("creationDate.equals=" + UPDATED_CREATION_DATE);
    }

    @Test
    @Transactional
    public void getAllPromotionsByCreationDateIsNotEqualToSomething() throws Exception {
        // Initialize the database
        promotionRepository.saveAndFlush(promotion);

        // Get all the promotionList where creationDate not equals to DEFAULT_CREATION_DATE
        defaultPromotionShouldNotBeFound("creationDate.notEquals=" + DEFAULT_CREATION_DATE);

        // Get all the promotionList where creationDate not equals to UPDATED_CREATION_DATE
        defaultPromotionShouldBeFound("creationDate.notEquals=" + UPDATED_CREATION_DATE);
    }

    @Test
    @Transactional
    public void getAllPromotionsByCreationDateIsInShouldWork() throws Exception {
        // Initialize the database
        promotionRepository.saveAndFlush(promotion);

        // Get all the promotionList where creationDate in DEFAULT_CREATION_DATE or UPDATED_CREATION_DATE
        defaultPromotionShouldBeFound("creationDate.in=" + DEFAULT_CREATION_DATE + "," + UPDATED_CREATION_DATE);

        // Get all the promotionList where creationDate equals to UPDATED_CREATION_DATE
        defaultPromotionShouldNotBeFound("creationDate.in=" + UPDATED_CREATION_DATE);
    }

    @Test
    @Transactional
    public void getAllPromotionsByCreationDateIsNullOrNotNull() throws Exception {
        // Initialize the database
        promotionRepository.saveAndFlush(promotion);

        // Get all the promotionList where creationDate is not null
        defaultPromotionShouldBeFound("creationDate.specified=true");

        // Get all the promotionList where creationDate is null
        defaultPromotionShouldNotBeFound("creationDate.specified=false");
    }

    @Test
    @Transactional
    public void getAllPromotionsByResponsibleDateIsEqualToSomething() throws Exception {
        // Initialize the database
        promotionRepository.saveAndFlush(promotion);

        // Get all the promotionList where responsibleDate equals to DEFAULT_RESPONSIBLE_DATE
        defaultPromotionShouldBeFound("responsibleDate.equals=" + DEFAULT_RESPONSIBLE_DATE);

        // Get all the promotionList where responsibleDate equals to UPDATED_RESPONSIBLE_DATE
        defaultPromotionShouldNotBeFound("responsibleDate.equals=" + UPDATED_RESPONSIBLE_DATE);
    }

    @Test
    @Transactional
    public void getAllPromotionsByResponsibleDateIsNotEqualToSomething() throws Exception {
        // Initialize the database
        promotionRepository.saveAndFlush(promotion);

        // Get all the promotionList where responsibleDate not equals to DEFAULT_RESPONSIBLE_DATE
        defaultPromotionShouldNotBeFound("responsibleDate.notEquals=" + DEFAULT_RESPONSIBLE_DATE);

        // Get all the promotionList where responsibleDate not equals to UPDATED_RESPONSIBLE_DATE
        defaultPromotionShouldBeFound("responsibleDate.notEquals=" + UPDATED_RESPONSIBLE_DATE);
    }

    @Test
    @Transactional
    public void getAllPromotionsByResponsibleDateIsInShouldWork() throws Exception {
        // Initialize the database
        promotionRepository.saveAndFlush(promotion);

        // Get all the promotionList where responsibleDate in DEFAULT_RESPONSIBLE_DATE or UPDATED_RESPONSIBLE_DATE
        defaultPromotionShouldBeFound("responsibleDate.in=" + DEFAULT_RESPONSIBLE_DATE + "," + UPDATED_RESPONSIBLE_DATE);

        // Get all the promotionList where responsibleDate equals to UPDATED_RESPONSIBLE_DATE
        defaultPromotionShouldNotBeFound("responsibleDate.in=" + UPDATED_RESPONSIBLE_DATE);
    }

    @Test
    @Transactional
    public void getAllPromotionsByResponsibleDateIsNullOrNotNull() throws Exception {
        // Initialize the database
        promotionRepository.saveAndFlush(promotion);

        // Get all the promotionList where responsibleDate is not null
        defaultPromotionShouldBeFound("responsibleDate.specified=true");

        // Get all the promotionList where responsibleDate is null
        defaultPromotionShouldNotBeFound("responsibleDate.specified=false");
    }

    @Test
    @Transactional
    public void getAllPromotionsByBeginningDateIsEqualToSomething() throws Exception {
        // Initialize the database
        promotionRepository.saveAndFlush(promotion);

        // Get all the promotionList where beginningDate equals to DEFAULT_BEGINNING_DATE
        defaultPromotionShouldBeFound("beginningDate.equals=" + DEFAULT_BEGINNING_DATE);

        // Get all the promotionList where beginningDate equals to UPDATED_BEGINNING_DATE
        defaultPromotionShouldNotBeFound("beginningDate.equals=" + UPDATED_BEGINNING_DATE);
    }

    @Test
    @Transactional
    public void getAllPromotionsByBeginningDateIsNotEqualToSomething() throws Exception {
        // Initialize the database
        promotionRepository.saveAndFlush(promotion);

        // Get all the promotionList where beginningDate not equals to DEFAULT_BEGINNING_DATE
        defaultPromotionShouldNotBeFound("beginningDate.notEquals=" + DEFAULT_BEGINNING_DATE);

        // Get all the promotionList where beginningDate not equals to UPDATED_BEGINNING_DATE
        defaultPromotionShouldBeFound("beginningDate.notEquals=" + UPDATED_BEGINNING_DATE);
    }

    @Test
    @Transactional
    public void getAllPromotionsByBeginningDateIsInShouldWork() throws Exception {
        // Initialize the database
        promotionRepository.saveAndFlush(promotion);

        // Get all the promotionList where beginningDate in DEFAULT_BEGINNING_DATE or UPDATED_BEGINNING_DATE
        defaultPromotionShouldBeFound("beginningDate.in=" + DEFAULT_BEGINNING_DATE + "," + UPDATED_BEGINNING_DATE);

        // Get all the promotionList where beginningDate equals to UPDATED_BEGINNING_DATE
        defaultPromotionShouldNotBeFound("beginningDate.in=" + UPDATED_BEGINNING_DATE);
    }

    @Test
    @Transactional
    public void getAllPromotionsByBeginningDateIsNullOrNotNull() throws Exception {
        // Initialize the database
        promotionRepository.saveAndFlush(promotion);

        // Get all the promotionList where beginningDate is not null
        defaultPromotionShouldBeFound("beginningDate.specified=true");

        // Get all the promotionList where beginningDate is null
        defaultPromotionShouldNotBeFound("beginningDate.specified=false");
    }

    @Test
    @Transactional
    public void getAllPromotionsByEndingDateIsEqualToSomething() throws Exception {
        // Initialize the database
        promotionRepository.saveAndFlush(promotion);

        // Get all the promotionList where endingDate equals to DEFAULT_ENDING_DATE
        defaultPromotionShouldBeFound("endingDate.equals=" + DEFAULT_ENDING_DATE);

        // Get all the promotionList where endingDate equals to UPDATED_ENDING_DATE
        defaultPromotionShouldNotBeFound("endingDate.equals=" + UPDATED_ENDING_DATE);
    }

    @Test
    @Transactional
    public void getAllPromotionsByEndingDateIsNotEqualToSomething() throws Exception {
        // Initialize the database
        promotionRepository.saveAndFlush(promotion);

        // Get all the promotionList where endingDate not equals to DEFAULT_ENDING_DATE
        defaultPromotionShouldNotBeFound("endingDate.notEquals=" + DEFAULT_ENDING_DATE);

        // Get all the promotionList where endingDate not equals to UPDATED_ENDING_DATE
        defaultPromotionShouldBeFound("endingDate.notEquals=" + UPDATED_ENDING_DATE);
    }

    @Test
    @Transactional
    public void getAllPromotionsByEndingDateIsInShouldWork() throws Exception {
        // Initialize the database
        promotionRepository.saveAndFlush(promotion);

        // Get all the promotionList where endingDate in DEFAULT_ENDING_DATE or UPDATED_ENDING_DATE
        defaultPromotionShouldBeFound("endingDate.in=" + DEFAULT_ENDING_DATE + "," + UPDATED_ENDING_DATE);

        // Get all the promotionList where endingDate equals to UPDATED_ENDING_DATE
        defaultPromotionShouldNotBeFound("endingDate.in=" + UPDATED_ENDING_DATE);
    }

    @Test
    @Transactional
    public void getAllPromotionsByEndingDateIsNullOrNotNull() throws Exception {
        // Initialize the database
        promotionRepository.saveAndFlush(promotion);

        // Get all the promotionList where endingDate is not null
        defaultPromotionShouldBeFound("endingDate.specified=true");

        // Get all the promotionList where endingDate is null
        defaultPromotionShouldNotBeFound("endingDate.specified=false");
    }

    @Test
    @Transactional
    public void getAllPromotionsByIsExclusiveIsEqualToSomething() throws Exception {
        // Initialize the database
        promotionRepository.saveAndFlush(promotion);

        // Get all the promotionList where isExclusive equals to DEFAULT_IS_EXCLUSIVE
        defaultPromotionShouldBeFound("isExclusive.equals=" + DEFAULT_IS_EXCLUSIVE);

        // Get all the promotionList where isExclusive equals to UPDATED_IS_EXCLUSIVE
        defaultPromotionShouldNotBeFound("isExclusive.equals=" + UPDATED_IS_EXCLUSIVE);
    }

    @Test
    @Transactional
    public void getAllPromotionsByIsExclusiveIsNotEqualToSomething() throws Exception {
        // Initialize the database
        promotionRepository.saveAndFlush(promotion);

        // Get all the promotionList where isExclusive not equals to DEFAULT_IS_EXCLUSIVE
        defaultPromotionShouldNotBeFound("isExclusive.notEquals=" + DEFAULT_IS_EXCLUSIVE);

        // Get all the promotionList where isExclusive not equals to UPDATED_IS_EXCLUSIVE
        defaultPromotionShouldBeFound("isExclusive.notEquals=" + UPDATED_IS_EXCLUSIVE);
    }

    @Test
    @Transactional
    public void getAllPromotionsByIsExclusiveIsInShouldWork() throws Exception {
        // Initialize the database
        promotionRepository.saveAndFlush(promotion);

        // Get all the promotionList where isExclusive in DEFAULT_IS_EXCLUSIVE or UPDATED_IS_EXCLUSIVE
        defaultPromotionShouldBeFound("isExclusive.in=" + DEFAULT_IS_EXCLUSIVE + "," + UPDATED_IS_EXCLUSIVE);

        // Get all the promotionList where isExclusive equals to UPDATED_IS_EXCLUSIVE
        defaultPromotionShouldNotBeFound("isExclusive.in=" + UPDATED_IS_EXCLUSIVE);
    }

    @Test
    @Transactional
    public void getAllPromotionsByIsExclusiveIsNullOrNotNull() throws Exception {
        // Initialize the database
        promotionRepository.saveAndFlush(promotion);

        // Get all the promotionList where isExclusive is not null
        defaultPromotionShouldBeFound("isExclusive.specified=true");

        // Get all the promotionList where isExclusive is null
        defaultPromotionShouldNotBeFound("isExclusive.specified=false");
    }

    @Test
    @Transactional
    public void getAllPromotionsByPriorityIsEqualToSomething() throws Exception {
        // Initialize the database
        promotionRepository.saveAndFlush(promotion);

        // Get all the promotionList where priority equals to DEFAULT_PRIORITY
        defaultPromotionShouldBeFound("priority.equals=" + DEFAULT_PRIORITY);

        // Get all the promotionList where priority equals to UPDATED_PRIORITY
        defaultPromotionShouldNotBeFound("priority.equals=" + UPDATED_PRIORITY);
    }

    @Test
    @Transactional
    public void getAllPromotionsByPriorityIsNotEqualToSomething() throws Exception {
        // Initialize the database
        promotionRepository.saveAndFlush(promotion);

        // Get all the promotionList where priority not equals to DEFAULT_PRIORITY
        defaultPromotionShouldNotBeFound("priority.notEquals=" + DEFAULT_PRIORITY);

        // Get all the promotionList where priority not equals to UPDATED_PRIORITY
        defaultPromotionShouldBeFound("priority.notEquals=" + UPDATED_PRIORITY);
    }

    @Test
    @Transactional
    public void getAllPromotionsByPriorityIsInShouldWork() throws Exception {
        // Initialize the database
        promotionRepository.saveAndFlush(promotion);

        // Get all the promotionList where priority in DEFAULT_PRIORITY or UPDATED_PRIORITY
        defaultPromotionShouldBeFound("priority.in=" + DEFAULT_PRIORITY + "," + UPDATED_PRIORITY);

        // Get all the promotionList where priority equals to UPDATED_PRIORITY
        defaultPromotionShouldNotBeFound("priority.in=" + UPDATED_PRIORITY);
    }

    @Test
    @Transactional
    public void getAllPromotionsByPriorityIsNullOrNotNull() throws Exception {
        // Initialize the database
        promotionRepository.saveAndFlush(promotion);

        // Get all the promotionList where priority is not null
        defaultPromotionShouldBeFound("priority.specified=true");

        // Get all the promotionList where priority is null
        defaultPromotionShouldNotBeFound("priority.specified=false");
    }

    @Test
    @Transactional
    public void getAllPromotionsByPriorityIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        promotionRepository.saveAndFlush(promotion);

        // Get all the promotionList where priority is greater than or equal to DEFAULT_PRIORITY
        defaultPromotionShouldBeFound("priority.greaterThanOrEqual=" + DEFAULT_PRIORITY);

        // Get all the promotionList where priority is greater than or equal to UPDATED_PRIORITY
        defaultPromotionShouldNotBeFound("priority.greaterThanOrEqual=" + UPDATED_PRIORITY);
    }

    @Test
    @Transactional
    public void getAllPromotionsByPriorityIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        promotionRepository.saveAndFlush(promotion);

        // Get all the promotionList where priority is less than or equal to DEFAULT_PRIORITY
        defaultPromotionShouldBeFound("priority.lessThanOrEqual=" + DEFAULT_PRIORITY);

        // Get all the promotionList where priority is less than or equal to SMALLER_PRIORITY
        defaultPromotionShouldNotBeFound("priority.lessThanOrEqual=" + SMALLER_PRIORITY);
    }

    @Test
    @Transactional
    public void getAllPromotionsByPriorityIsLessThanSomething() throws Exception {
        // Initialize the database
        promotionRepository.saveAndFlush(promotion);

        // Get all the promotionList where priority is less than DEFAULT_PRIORITY
        defaultPromotionShouldNotBeFound("priority.lessThan=" + DEFAULT_PRIORITY);

        // Get all the promotionList where priority is less than UPDATED_PRIORITY
        defaultPromotionShouldBeFound("priority.lessThan=" + UPDATED_PRIORITY);
    }

    @Test
    @Transactional
    public void getAllPromotionsByPriorityIsGreaterThanSomething() throws Exception {
        // Initialize the database
        promotionRepository.saveAndFlush(promotion);

        // Get all the promotionList where priority is greater than DEFAULT_PRIORITY
        defaultPromotionShouldNotBeFound("priority.greaterThan=" + DEFAULT_PRIORITY);

        // Get all the promotionList where priority is greater than SMALLER_PRIORITY
        defaultPromotionShouldBeFound("priority.greaterThan=" + SMALLER_PRIORITY);
    }


    @Test
    @Transactional
    public void getAllPromotionsByPromotionalTextIsEqualToSomething() throws Exception {
        // Initialize the database
        promotionRepository.saveAndFlush(promotion);

        // Get all the promotionList where promotionalText equals to DEFAULT_PROMOTIONAL_TEXT
        defaultPromotionShouldBeFound("promotionalText.equals=" + DEFAULT_PROMOTIONAL_TEXT);

        // Get all the promotionList where promotionalText equals to UPDATED_PROMOTIONAL_TEXT
        defaultPromotionShouldNotBeFound("promotionalText.equals=" + UPDATED_PROMOTIONAL_TEXT);
    }

    @Test
    @Transactional
    public void getAllPromotionsByPromotionalTextIsNotEqualToSomething() throws Exception {
        // Initialize the database
        promotionRepository.saveAndFlush(promotion);

        // Get all the promotionList where promotionalText not equals to DEFAULT_PROMOTIONAL_TEXT
        defaultPromotionShouldNotBeFound("promotionalText.notEquals=" + DEFAULT_PROMOTIONAL_TEXT);

        // Get all the promotionList where promotionalText not equals to UPDATED_PROMOTIONAL_TEXT
        defaultPromotionShouldBeFound("promotionalText.notEquals=" + UPDATED_PROMOTIONAL_TEXT);
    }

    @Test
    @Transactional
    public void getAllPromotionsByPromotionalTextIsInShouldWork() throws Exception {
        // Initialize the database
        promotionRepository.saveAndFlush(promotion);

        // Get all the promotionList where promotionalText in DEFAULT_PROMOTIONAL_TEXT or UPDATED_PROMOTIONAL_TEXT
        defaultPromotionShouldBeFound("promotionalText.in=" + DEFAULT_PROMOTIONAL_TEXT + "," + UPDATED_PROMOTIONAL_TEXT);

        // Get all the promotionList where promotionalText equals to UPDATED_PROMOTIONAL_TEXT
        defaultPromotionShouldNotBeFound("promotionalText.in=" + UPDATED_PROMOTIONAL_TEXT);
    }

    @Test
    @Transactional
    public void getAllPromotionsByPromotionalTextIsNullOrNotNull() throws Exception {
        // Initialize the database
        promotionRepository.saveAndFlush(promotion);

        // Get all the promotionList where promotionalText is not null
        defaultPromotionShouldBeFound("promotionalText.specified=true");

        // Get all the promotionList where promotionalText is null
        defaultPromotionShouldNotBeFound("promotionalText.specified=false");
    }
                @Test
    @Transactional
    public void getAllPromotionsByPromotionalTextContainsSomething() throws Exception {
        // Initialize the database
        promotionRepository.saveAndFlush(promotion);

        // Get all the promotionList where promotionalText contains DEFAULT_PROMOTIONAL_TEXT
        defaultPromotionShouldBeFound("promotionalText.contains=" + DEFAULT_PROMOTIONAL_TEXT);

        // Get all the promotionList where promotionalText contains UPDATED_PROMOTIONAL_TEXT
        defaultPromotionShouldNotBeFound("promotionalText.contains=" + UPDATED_PROMOTIONAL_TEXT);
    }

    @Test
    @Transactional
    public void getAllPromotionsByPromotionalTextNotContainsSomething() throws Exception {
        // Initialize the database
        promotionRepository.saveAndFlush(promotion);

        // Get all the promotionList where promotionalText does not contain DEFAULT_PROMOTIONAL_TEXT
        defaultPromotionShouldNotBeFound("promotionalText.doesNotContain=" + DEFAULT_PROMOTIONAL_TEXT);

        // Get all the promotionList where promotionalText does not contain UPDATED_PROMOTIONAL_TEXT
        defaultPromotionShouldBeFound("promotionalText.doesNotContain=" + UPDATED_PROMOTIONAL_TEXT);
    }


    @Test
    @Transactional
    public void getAllPromotionsByEnabledIsEqualToSomething() throws Exception {
        // Initialize the database
        promotionRepository.saveAndFlush(promotion);

        // Get all the promotionList where enabled equals to DEFAULT_ENABLED
        defaultPromotionShouldBeFound("enabled.equals=" + DEFAULT_ENABLED);

        // Get all the promotionList where enabled equals to UPDATED_ENABLED
        defaultPromotionShouldNotBeFound("enabled.equals=" + UPDATED_ENABLED);
    }

    @Test
    @Transactional
    public void getAllPromotionsByEnabledIsNotEqualToSomething() throws Exception {
        // Initialize the database
        promotionRepository.saveAndFlush(promotion);

        // Get all the promotionList where enabled not equals to DEFAULT_ENABLED
        defaultPromotionShouldNotBeFound("enabled.notEquals=" + DEFAULT_ENABLED);

        // Get all the promotionList where enabled not equals to UPDATED_ENABLED
        defaultPromotionShouldBeFound("enabled.notEquals=" + UPDATED_ENABLED);
    }

    @Test
    @Transactional
    public void getAllPromotionsByEnabledIsInShouldWork() throws Exception {
        // Initialize the database
        promotionRepository.saveAndFlush(promotion);

        // Get all the promotionList where enabled in DEFAULT_ENABLED or UPDATED_ENABLED
        defaultPromotionShouldBeFound("enabled.in=" + DEFAULT_ENABLED + "," + UPDATED_ENABLED);

        // Get all the promotionList where enabled equals to UPDATED_ENABLED
        defaultPromotionShouldNotBeFound("enabled.in=" + UPDATED_ENABLED);
    }

    @Test
    @Transactional
    public void getAllPromotionsByEnabledIsNullOrNotNull() throws Exception {
        // Initialize the database
        promotionRepository.saveAndFlush(promotion);

        // Get all the promotionList where enabled is not null
        defaultPromotionShouldBeFound("enabled.specified=true");

        // Get all the promotionList where enabled is null
        defaultPromotionShouldNotBeFound("enabled.specified=false");
    }

    @Test
    @Transactional
    public void getAllPromotionsByIsPercentIsEqualToSomething() throws Exception {
        // Initialize the database
        promotionRepository.saveAndFlush(promotion);

        // Get all the promotionList where isPercent equals to DEFAULT_IS_PERCENT
        defaultPromotionShouldBeFound("isPercent.equals=" + DEFAULT_IS_PERCENT);

        // Get all the promotionList where isPercent equals to UPDATED_IS_PERCENT
        defaultPromotionShouldNotBeFound("isPercent.equals=" + UPDATED_IS_PERCENT);
    }

    @Test
    @Transactional
    public void getAllPromotionsByIsPercentIsNotEqualToSomething() throws Exception {
        // Initialize the database
        promotionRepository.saveAndFlush(promotion);

        // Get all the promotionList where isPercent not equals to DEFAULT_IS_PERCENT
        defaultPromotionShouldNotBeFound("isPercent.notEquals=" + DEFAULT_IS_PERCENT);

        // Get all the promotionList where isPercent not equals to UPDATED_IS_PERCENT
        defaultPromotionShouldBeFound("isPercent.notEquals=" + UPDATED_IS_PERCENT);
    }

    @Test
    @Transactional
    public void getAllPromotionsByIsPercentIsInShouldWork() throws Exception {
        // Initialize the database
        promotionRepository.saveAndFlush(promotion);

        // Get all the promotionList where isPercent in DEFAULT_IS_PERCENT or UPDATED_IS_PERCENT
        defaultPromotionShouldBeFound("isPercent.in=" + DEFAULT_IS_PERCENT + "," + UPDATED_IS_PERCENT);

        // Get all the promotionList where isPercent equals to UPDATED_IS_PERCENT
        defaultPromotionShouldNotBeFound("isPercent.in=" + UPDATED_IS_PERCENT);
    }

    @Test
    @Transactional
    public void getAllPromotionsByIsPercentIsNullOrNotNull() throws Exception {
        // Initialize the database
        promotionRepository.saveAndFlush(promotion);

        // Get all the promotionList where isPercent is not null
        defaultPromotionShouldBeFound("isPercent.specified=true");

        // Get all the promotionList where isPercent is null
        defaultPromotionShouldNotBeFound("isPercent.specified=false");
    }

    @Test
    @Transactional
    public void getAllPromotionsByIsAmountIsEqualToSomething() throws Exception {
        // Initialize the database
        promotionRepository.saveAndFlush(promotion);

        // Get all the promotionList where isAmount equals to DEFAULT_IS_AMOUNT
        defaultPromotionShouldBeFound("isAmount.equals=" + DEFAULT_IS_AMOUNT);

        // Get all the promotionList where isAmount equals to UPDATED_IS_AMOUNT
        defaultPromotionShouldNotBeFound("isAmount.equals=" + UPDATED_IS_AMOUNT);
    }

    @Test
    @Transactional
    public void getAllPromotionsByIsAmountIsNotEqualToSomething() throws Exception {
        // Initialize the database
        promotionRepository.saveAndFlush(promotion);

        // Get all the promotionList where isAmount not equals to DEFAULT_IS_AMOUNT
        defaultPromotionShouldNotBeFound("isAmount.notEquals=" + DEFAULT_IS_AMOUNT);

        // Get all the promotionList where isAmount not equals to UPDATED_IS_AMOUNT
        defaultPromotionShouldBeFound("isAmount.notEquals=" + UPDATED_IS_AMOUNT);
    }

    @Test
    @Transactional
    public void getAllPromotionsByIsAmountIsInShouldWork() throws Exception {
        // Initialize the database
        promotionRepository.saveAndFlush(promotion);

        // Get all the promotionList where isAmount in DEFAULT_IS_AMOUNT or UPDATED_IS_AMOUNT
        defaultPromotionShouldBeFound("isAmount.in=" + DEFAULT_IS_AMOUNT + "," + UPDATED_IS_AMOUNT);

        // Get all the promotionList where isAmount equals to UPDATED_IS_AMOUNT
        defaultPromotionShouldNotBeFound("isAmount.in=" + UPDATED_IS_AMOUNT);
    }

    @Test
    @Transactional
    public void getAllPromotionsByIsAmountIsNullOrNotNull() throws Exception {
        // Initialize the database
        promotionRepository.saveAndFlush(promotion);

        // Get all the promotionList where isAmount is not null
        defaultPromotionShouldBeFound("isAmount.specified=true");

        // Get all the promotionList where isAmount is null
        defaultPromotionShouldNotBeFound("isAmount.specified=false");
    }

    @Test
    @Transactional
    public void getAllPromotionsByValueIsEqualToSomething() throws Exception {
        // Initialize the database
        promotionRepository.saveAndFlush(promotion);

        // Get all the promotionList where value equals to DEFAULT_VALUE
        defaultPromotionShouldBeFound("value.equals=" + DEFAULT_VALUE);

        // Get all the promotionList where value equals to UPDATED_VALUE
        defaultPromotionShouldNotBeFound("value.equals=" + UPDATED_VALUE);
    }

    @Test
    @Transactional
    public void getAllPromotionsByValueIsNotEqualToSomething() throws Exception {
        // Initialize the database
        promotionRepository.saveAndFlush(promotion);

        // Get all the promotionList where value not equals to DEFAULT_VALUE
        defaultPromotionShouldNotBeFound("value.notEquals=" + DEFAULT_VALUE);

        // Get all the promotionList where value not equals to UPDATED_VALUE
        defaultPromotionShouldBeFound("value.notEquals=" + UPDATED_VALUE);
    }

    @Test
    @Transactional
    public void getAllPromotionsByValueIsInShouldWork() throws Exception {
        // Initialize the database
        promotionRepository.saveAndFlush(promotion);

        // Get all the promotionList where value in DEFAULT_VALUE or UPDATED_VALUE
        defaultPromotionShouldBeFound("value.in=" + DEFAULT_VALUE + "," + UPDATED_VALUE);

        // Get all the promotionList where value equals to UPDATED_VALUE
        defaultPromotionShouldNotBeFound("value.in=" + UPDATED_VALUE);
    }

    @Test
    @Transactional
    public void getAllPromotionsByValueIsNullOrNotNull() throws Exception {
        // Initialize the database
        promotionRepository.saveAndFlush(promotion);

        // Get all the promotionList where value is not null
        defaultPromotionShouldBeFound("value.specified=true");

        // Get all the promotionList where value is null
        defaultPromotionShouldNotBeFound("value.specified=false");
    }

    @Test
    @Transactional
    public void getAllPromotionsByValueIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        promotionRepository.saveAndFlush(promotion);

        // Get all the promotionList where value is greater than or equal to DEFAULT_VALUE
        defaultPromotionShouldBeFound("value.greaterThanOrEqual=" + DEFAULT_VALUE);

        // Get all the promotionList where value is greater than or equal to UPDATED_VALUE
        defaultPromotionShouldNotBeFound("value.greaterThanOrEqual=" + UPDATED_VALUE);
    }

    @Test
    @Transactional
    public void getAllPromotionsByValueIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        promotionRepository.saveAndFlush(promotion);

        // Get all the promotionList where value is less than or equal to DEFAULT_VALUE
        defaultPromotionShouldBeFound("value.lessThanOrEqual=" + DEFAULT_VALUE);

        // Get all the promotionList where value is less than or equal to SMALLER_VALUE
        defaultPromotionShouldNotBeFound("value.lessThanOrEqual=" + SMALLER_VALUE);
    }

    @Test
    @Transactional
    public void getAllPromotionsByValueIsLessThanSomething() throws Exception {
        // Initialize the database
        promotionRepository.saveAndFlush(promotion);

        // Get all the promotionList where value is less than DEFAULT_VALUE
        defaultPromotionShouldNotBeFound("value.lessThan=" + DEFAULT_VALUE);

        // Get all the promotionList where value is less than UPDATED_VALUE
        defaultPromotionShouldBeFound("value.lessThan=" + UPDATED_VALUE);
    }

    @Test
    @Transactional
    public void getAllPromotionsByValueIsGreaterThanSomething() throws Exception {
        // Initialize the database
        promotionRepository.saveAndFlush(promotion);

        // Get all the promotionList where value is greater than DEFAULT_VALUE
        defaultPromotionShouldNotBeFound("value.greaterThan=" + DEFAULT_VALUE);

        // Get all the promotionList where value is greater than SMALLER_VALUE
        defaultPromotionShouldBeFound("value.greaterThan=" + SMALLER_VALUE);
    }


    @Test
    @Transactional
    public void getAllPromotionsByAmountIsEqualToSomething() throws Exception {
        // Initialize the database
        promotionRepository.saveAndFlush(promotion);

        // Get all the promotionList where amount equals to DEFAULT_AMOUNT
        defaultPromotionShouldBeFound("amount.equals=" + DEFAULT_AMOUNT);

        // Get all the promotionList where amount equals to UPDATED_AMOUNT
        defaultPromotionShouldNotBeFound("amount.equals=" + UPDATED_AMOUNT);
    }

    @Test
    @Transactional
    public void getAllPromotionsByAmountIsNotEqualToSomething() throws Exception {
        // Initialize the database
        promotionRepository.saveAndFlush(promotion);

        // Get all the promotionList where amount not equals to DEFAULT_AMOUNT
        defaultPromotionShouldNotBeFound("amount.notEquals=" + DEFAULT_AMOUNT);

        // Get all the promotionList where amount not equals to UPDATED_AMOUNT
        defaultPromotionShouldBeFound("amount.notEquals=" + UPDATED_AMOUNT);
    }

    @Test
    @Transactional
    public void getAllPromotionsByAmountIsInShouldWork() throws Exception {
        // Initialize the database
        promotionRepository.saveAndFlush(promotion);

        // Get all the promotionList where amount in DEFAULT_AMOUNT or UPDATED_AMOUNT
        defaultPromotionShouldBeFound("amount.in=" + DEFAULT_AMOUNT + "," + UPDATED_AMOUNT);

        // Get all the promotionList where amount equals to UPDATED_AMOUNT
        defaultPromotionShouldNotBeFound("amount.in=" + UPDATED_AMOUNT);
    }

    @Test
    @Transactional
    public void getAllPromotionsByAmountIsNullOrNotNull() throws Exception {
        // Initialize the database
        promotionRepository.saveAndFlush(promotion);

        // Get all the promotionList where amount is not null
        defaultPromotionShouldBeFound("amount.specified=true");

        // Get all the promotionList where amount is null
        defaultPromotionShouldNotBeFound("amount.specified=false");
    }

    @Test
    @Transactional
    public void getAllPromotionsByAmountIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        promotionRepository.saveAndFlush(promotion);

        // Get all the promotionList where amount is greater than or equal to DEFAULT_AMOUNT
        defaultPromotionShouldBeFound("amount.greaterThanOrEqual=" + DEFAULT_AMOUNT);

        // Get all the promotionList where amount is greater than or equal to UPDATED_AMOUNT
        defaultPromotionShouldNotBeFound("amount.greaterThanOrEqual=" + UPDATED_AMOUNT);
    }

    @Test
    @Transactional
    public void getAllPromotionsByAmountIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        promotionRepository.saveAndFlush(promotion);

        // Get all the promotionList where amount is less than or equal to DEFAULT_AMOUNT
        defaultPromotionShouldBeFound("amount.lessThanOrEqual=" + DEFAULT_AMOUNT);

        // Get all the promotionList where amount is less than or equal to SMALLER_AMOUNT
        defaultPromotionShouldNotBeFound("amount.lessThanOrEqual=" + SMALLER_AMOUNT);
    }

    @Test
    @Transactional
    public void getAllPromotionsByAmountIsLessThanSomething() throws Exception {
        // Initialize the database
        promotionRepository.saveAndFlush(promotion);

        // Get all the promotionList where amount is less than DEFAULT_AMOUNT
        defaultPromotionShouldNotBeFound("amount.lessThan=" + DEFAULT_AMOUNT);

        // Get all the promotionList where amount is less than UPDATED_AMOUNT
        defaultPromotionShouldBeFound("amount.lessThan=" + UPDATED_AMOUNT);
    }

    @Test
    @Transactional
    public void getAllPromotionsByAmountIsGreaterThanSomething() throws Exception {
        // Initialize the database
        promotionRepository.saveAndFlush(promotion);

        // Get all the promotionList where amount is greater than DEFAULT_AMOUNT
        defaultPromotionShouldNotBeFound("amount.greaterThan=" + DEFAULT_AMOUNT);

        // Get all the promotionList where amount is greater than SMALLER_AMOUNT
        defaultPromotionShouldBeFound("amount.greaterThan=" + SMALLER_AMOUNT);
    }


    @Test
    @Transactional
    public void getAllPromotionsByTransactionTypeIsEqualToSomething() throws Exception {
        // Initialize the database
        promotionRepository.saveAndFlush(promotion);

        // Get all the promotionList where transactionType equals to DEFAULT_TRANSACTION_TYPE
        defaultPromotionShouldBeFound("transactionType.equals=" + DEFAULT_TRANSACTION_TYPE);

        // Get all the promotionList where transactionType equals to UPDATED_TRANSACTION_TYPE
        defaultPromotionShouldNotBeFound("transactionType.equals=" + UPDATED_TRANSACTION_TYPE);
    }

    @Test
    @Transactional
    public void getAllPromotionsByTransactionTypeIsNotEqualToSomething() throws Exception {
        // Initialize the database
        promotionRepository.saveAndFlush(promotion);

        // Get all the promotionList where transactionType not equals to DEFAULT_TRANSACTION_TYPE
        defaultPromotionShouldNotBeFound("transactionType.notEquals=" + DEFAULT_TRANSACTION_TYPE);

        // Get all the promotionList where transactionType not equals to UPDATED_TRANSACTION_TYPE
        defaultPromotionShouldBeFound("transactionType.notEquals=" + UPDATED_TRANSACTION_TYPE);
    }

    @Test
    @Transactional
    public void getAllPromotionsByTransactionTypeIsInShouldWork() throws Exception {
        // Initialize the database
        promotionRepository.saveAndFlush(promotion);

        // Get all the promotionList where transactionType in DEFAULT_TRANSACTION_TYPE or UPDATED_TRANSACTION_TYPE
        defaultPromotionShouldBeFound("transactionType.in=" + DEFAULT_TRANSACTION_TYPE + "," + UPDATED_TRANSACTION_TYPE);

        // Get all the promotionList where transactionType equals to UPDATED_TRANSACTION_TYPE
        defaultPromotionShouldNotBeFound("transactionType.in=" + UPDATED_TRANSACTION_TYPE);
    }

    @Test
    @Transactional
    public void getAllPromotionsByTransactionTypeIsNullOrNotNull() throws Exception {
        // Initialize the database
        promotionRepository.saveAndFlush(promotion);

        // Get all the promotionList where transactionType is not null
        defaultPromotionShouldBeFound("transactionType.specified=true");

        // Get all the promotionList where transactionType is null
        defaultPromotionShouldNotBeFound("transactionType.specified=false");
    }

    @Test
    @Transactional
    public void getAllPromotionsByCurrencyIsEqualToSomething() throws Exception {
        // Initialize the database
        promotionRepository.saveAndFlush(promotion);
        Currency currency = CurrencyResourceIT.createEntity(em);
        em.persist(currency);
        em.flush();
        promotion.setCurrency(currency);
        promotionRepository.saveAndFlush(promotion);
        Long currencyId = currency.getId();

        // Get all the promotionList where currency equals to currencyId
        defaultPromotionShouldBeFound("currencyId.equals=" + currencyId);

        // Get all the promotionList where currency equals to currencyId + 1
        defaultPromotionShouldNotBeFound("currencyId.equals=" + (currencyId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultPromotionShouldBeFound(String filter) throws Exception {
        restPromotionMockMvc.perform(get("/api/promotions?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(promotion.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].promotionType").value(hasItem(DEFAULT_PROMOTION_TYPE.toString())))
            .andExpect(jsonPath("$.[*].creationDate").value(hasItem(DEFAULT_CREATION_DATE.toString())))
            .andExpect(jsonPath("$.[*].responsibleDate").value(hasItem(DEFAULT_RESPONSIBLE_DATE.toString())))
            .andExpect(jsonPath("$.[*].beginningDate").value(hasItem(DEFAULT_BEGINNING_DATE.toString())))
            .andExpect(jsonPath("$.[*].endingDate").value(hasItem(DEFAULT_ENDING_DATE.toString())))
            .andExpect(jsonPath("$.[*].isExclusive").value(hasItem(DEFAULT_IS_EXCLUSIVE.booleanValue())))
            .andExpect(jsonPath("$.[*].priority").value(hasItem(DEFAULT_PRIORITY)))
            .andExpect(jsonPath("$.[*].promotionalText").value(hasItem(DEFAULT_PROMOTIONAL_TEXT)))
            .andExpect(jsonPath("$.[*].enabled").value(hasItem(DEFAULT_ENABLED.booleanValue())))
            .andExpect(jsonPath("$.[*].isPercent").value(hasItem(DEFAULT_IS_PERCENT.booleanValue())))
            .andExpect(jsonPath("$.[*].imagenContentType").value(hasItem(DEFAULT_IMAGEN_CONTENT_TYPE)))
            .andExpect(jsonPath("$.[*].imagen").value(hasItem(Base64Utils.encodeToString(DEFAULT_IMAGEN))))
            .andExpect(jsonPath("$.[*].isAmount").value(hasItem(DEFAULT_IS_AMOUNT.booleanValue())))
            .andExpect(jsonPath("$.[*].value").value(hasItem(DEFAULT_VALUE.doubleValue())))
            .andExpect(jsonPath("$.[*].amount").value(hasItem(DEFAULT_AMOUNT.doubleValue())))
            .andExpect(jsonPath("$.[*].transactionType").value(hasItem(DEFAULT_TRANSACTION_TYPE.toString())));

        // Check, that the count call also returns 1
        restPromotionMockMvc.perform(get("/api/promotions/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultPromotionShouldNotBeFound(String filter) throws Exception {
        restPromotionMockMvc.perform(get("/api/promotions?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restPromotionMockMvc.perform(get("/api/promotions/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    public void getNonExistingPromotion() throws Exception {
        // Get the promotion
        restPromotionMockMvc.perform(get("/api/promotions/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updatePromotion() throws Exception {
        // Initialize the database
        promotionService.save(promotion);

        int databaseSizeBeforeUpdate = promotionRepository.findAll().size();

        // Update the promotion
        Promotion updatedPromotion = promotionRepository.findById(promotion.getId()).get();
        // Disconnect from session so that the updates on updatedPromotion are not directly saved in db
        em.detach(updatedPromotion);
        updatedPromotion
            .name(UPDATED_NAME)
            .promotionType(UPDATED_PROMOTION_TYPE)
            .creationDate(UPDATED_CREATION_DATE)
            .responsibleDate(UPDATED_RESPONSIBLE_DATE)
            .beginningDate(UPDATED_BEGINNING_DATE)
            .endingDate(UPDATED_ENDING_DATE)
            .isExclusive(UPDATED_IS_EXCLUSIVE)
            .priority(UPDATED_PRIORITY)
            .promotionalText(UPDATED_PROMOTIONAL_TEXT)
            .enabled(UPDATED_ENABLED)
            .isPercent(UPDATED_IS_PERCENT)
            .imagen(UPDATED_IMAGEN)
            .imagenContentType(UPDATED_IMAGEN_CONTENT_TYPE)
            .isAmount(UPDATED_IS_AMOUNT)
            .value(UPDATED_VALUE)
            .amount(UPDATED_AMOUNT)
            .transactionType(UPDATED_TRANSACTION_TYPE);

        restPromotionMockMvc.perform(put("/api/promotions")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(updatedPromotion)))
            .andExpect(status().isOk());

        // Validate the Promotion in the database
        List<Promotion> promotionList = promotionRepository.findAll();
        assertThat(promotionList).hasSize(databaseSizeBeforeUpdate);
        Promotion testPromotion = promotionList.get(promotionList.size() - 1);
        assertThat(testPromotion.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testPromotion.getPromotionType()).isEqualTo(UPDATED_PROMOTION_TYPE);
        assertThat(testPromotion.getCreationDate()).isEqualTo(UPDATED_CREATION_DATE);
        assertThat(testPromotion.getResponsibleDate()).isEqualTo(UPDATED_RESPONSIBLE_DATE);
        assertThat(testPromotion.getBeginningDate()).isEqualTo(UPDATED_BEGINNING_DATE);
        assertThat(testPromotion.getEndingDate()).isEqualTo(UPDATED_ENDING_DATE);
        assertThat(testPromotion.isIsExclusive()).isEqualTo(UPDATED_IS_EXCLUSIVE);
        assertThat(testPromotion.getPriority()).isEqualTo(UPDATED_PRIORITY);
        assertThat(testPromotion.getPromotionalText()).isEqualTo(UPDATED_PROMOTIONAL_TEXT);
        assertThat(testPromotion.isEnabled()).isEqualTo(UPDATED_ENABLED);
        assertThat(testPromotion.isIsPercent()).isEqualTo(UPDATED_IS_PERCENT);
        assertThat(testPromotion.getImagen()).isEqualTo(UPDATED_IMAGEN);
        assertThat(testPromotion.getImagenContentType()).isEqualTo(UPDATED_IMAGEN_CONTENT_TYPE);
        assertThat(testPromotion.isIsAmount()).isEqualTo(UPDATED_IS_AMOUNT);
        assertThat(testPromotion.getValue()).isEqualTo(UPDATED_VALUE);
        assertThat(testPromotion.getAmount()).isEqualTo(UPDATED_AMOUNT);
        assertThat(testPromotion.getTransactionType()).isEqualTo(UPDATED_TRANSACTION_TYPE);
    }

    @Test
    @Transactional
    public void updateNonExistingPromotion() throws Exception {
        int databaseSizeBeforeUpdate = promotionRepository.findAll().size();

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restPromotionMockMvc.perform(put("/api/promotions")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(promotion)))
            .andExpect(status().isBadRequest());

        // Validate the Promotion in the database
        List<Promotion> promotionList = promotionRepository.findAll();
        assertThat(promotionList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deletePromotion() throws Exception {
        // Initialize the database
        promotionService.save(promotion);

        int databaseSizeBeforeDelete = promotionRepository.findAll().size();

        // Delete the promotion
        restPromotionMockMvc.perform(delete("/api/promotions/{id}", promotion.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Promotion> promotionList = promotionRepository.findAll();
        assertThat(promotionList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
