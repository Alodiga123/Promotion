package com.alodiga.promotions.web.rest;

import com.alodiga.promotions.PromotionsApp;
import com.alodiga.promotions.domain.Currency;
import com.alodiga.promotions.repository.CurrencyRepository;
import com.alodiga.promotions.service.CurrencyService;
import com.alodiga.promotions.service.dto.CurrencyCriteria;
import com.alodiga.promotions.service.CurrencyQueryService;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;
import javax.persistence.EntityManager;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Integration tests for the {@link CurrencyResource} REST controller.
 */
@SpringBootTest(classes = PromotionsApp.class)
@AutoConfigureMockMvc
@WithMockUser
public class CurrencyResourceIT {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBBBBBBB";

    private static final String DEFAULT_SIMBOL = "AAAAAAAAAA";
    private static final String UPDATED_SIMBOL = "BBBBBBBBBB";

    @Autowired
    private CurrencyRepository currencyRepository;

    @Autowired
    private CurrencyService currencyService;

    @Autowired
    private CurrencyQueryService currencyQueryService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restCurrencyMockMvc;

    private Currency currency;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Currency createEntity(EntityManager em) {
        Currency currency = new Currency()
            .name(DEFAULT_NAME)
            .description(DEFAULT_DESCRIPTION)
            .simbol(DEFAULT_SIMBOL);
        return currency;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Currency createUpdatedEntity(EntityManager em) {
        Currency currency = new Currency()
            .name(UPDATED_NAME)
            .description(UPDATED_DESCRIPTION)
            .simbol(UPDATED_SIMBOL);
        return currency;
    }

    @BeforeEach
    public void initTest() {
        currency = createEntity(em);
    }

    @Test
    @Transactional
    public void createCurrency() throws Exception {
        int databaseSizeBeforeCreate = currencyRepository.findAll().size();
        // Create the Currency
        restCurrencyMockMvc.perform(post("/api/currencies")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(currency)))
            .andExpect(status().isCreated());

        // Validate the Currency in the database
        List<Currency> currencyList = currencyRepository.findAll();
        assertThat(currencyList).hasSize(databaseSizeBeforeCreate + 1);
        Currency testCurrency = currencyList.get(currencyList.size() - 1);
        assertThat(testCurrency.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testCurrency.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
        assertThat(testCurrency.getSimbol()).isEqualTo(DEFAULT_SIMBOL);
    }

    @Test
    @Transactional
    public void createCurrencyWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = currencyRepository.findAll().size();

        // Create the Currency with an existing ID
        currency.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restCurrencyMockMvc.perform(post("/api/currencies")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(currency)))
            .andExpect(status().isBadRequest());

        // Validate the Currency in the database
        List<Currency> currencyList = currencyRepository.findAll();
        assertThat(currencyList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = currencyRepository.findAll().size();
        // set the field null
        currency.setName(null);

        // Create the Currency, which fails.


        restCurrencyMockMvc.perform(post("/api/currencies")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(currency)))
            .andExpect(status().isBadRequest());

        List<Currency> currencyList = currencyRepository.findAll();
        assertThat(currencyList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkDescriptionIsRequired() throws Exception {
        int databaseSizeBeforeTest = currencyRepository.findAll().size();
        // set the field null
        currency.setDescription(null);

        // Create the Currency, which fails.


        restCurrencyMockMvc.perform(post("/api/currencies")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(currency)))
            .andExpect(status().isBadRequest());

        List<Currency> currencyList = currencyRepository.findAll();
        assertThat(currencyList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkSimbolIsRequired() throws Exception {
        int databaseSizeBeforeTest = currencyRepository.findAll().size();
        // set the field null
        currency.setSimbol(null);

        // Create the Currency, which fails.


        restCurrencyMockMvc.perform(post("/api/currencies")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(currency)))
            .andExpect(status().isBadRequest());

        List<Currency> currencyList = currencyRepository.findAll();
        assertThat(currencyList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllCurrencies() throws Exception {
        // Initialize the database
        currencyRepository.saveAndFlush(currency);

        // Get all the currencyList
        restCurrencyMockMvc.perform(get("/api/currencies?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(currency.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION)))
            .andExpect(jsonPath("$.[*].simbol").value(hasItem(DEFAULT_SIMBOL)));
    }
    
    @Test
    @Transactional
    public void getCurrency() throws Exception {
        // Initialize the database
        currencyRepository.saveAndFlush(currency);

        // Get the currency
        restCurrencyMockMvc.perform(get("/api/currencies/{id}", currency.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(currency.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION))
            .andExpect(jsonPath("$.simbol").value(DEFAULT_SIMBOL));
    }


    @Test
    @Transactional
    public void getCurrenciesByIdFiltering() throws Exception {
        // Initialize the database
        currencyRepository.saveAndFlush(currency);

        Long id = currency.getId();

        defaultCurrencyShouldBeFound("id.equals=" + id);
        defaultCurrencyShouldNotBeFound("id.notEquals=" + id);

        defaultCurrencyShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultCurrencyShouldNotBeFound("id.greaterThan=" + id);

        defaultCurrencyShouldBeFound("id.lessThanOrEqual=" + id);
        defaultCurrencyShouldNotBeFound("id.lessThan=" + id);
    }


    @Test
    @Transactional
    public void getAllCurrenciesByNameIsEqualToSomething() throws Exception {
        // Initialize the database
        currencyRepository.saveAndFlush(currency);

        // Get all the currencyList where name equals to DEFAULT_NAME
        defaultCurrencyShouldBeFound("name.equals=" + DEFAULT_NAME);

        // Get all the currencyList where name equals to UPDATED_NAME
        defaultCurrencyShouldNotBeFound("name.equals=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    public void getAllCurrenciesByNameIsNotEqualToSomething() throws Exception {
        // Initialize the database
        currencyRepository.saveAndFlush(currency);

        // Get all the currencyList where name not equals to DEFAULT_NAME
        defaultCurrencyShouldNotBeFound("name.notEquals=" + DEFAULT_NAME);

        // Get all the currencyList where name not equals to UPDATED_NAME
        defaultCurrencyShouldBeFound("name.notEquals=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    public void getAllCurrenciesByNameIsInShouldWork() throws Exception {
        // Initialize the database
        currencyRepository.saveAndFlush(currency);

        // Get all the currencyList where name in DEFAULT_NAME or UPDATED_NAME
        defaultCurrencyShouldBeFound("name.in=" + DEFAULT_NAME + "," + UPDATED_NAME);

        // Get all the currencyList where name equals to UPDATED_NAME
        defaultCurrencyShouldNotBeFound("name.in=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    public void getAllCurrenciesByNameIsNullOrNotNull() throws Exception {
        // Initialize the database
        currencyRepository.saveAndFlush(currency);

        // Get all the currencyList where name is not null
        defaultCurrencyShouldBeFound("name.specified=true");

        // Get all the currencyList where name is null
        defaultCurrencyShouldNotBeFound("name.specified=false");
    }
                @Test
    @Transactional
    public void getAllCurrenciesByNameContainsSomething() throws Exception {
        // Initialize the database
        currencyRepository.saveAndFlush(currency);

        // Get all the currencyList where name contains DEFAULT_NAME
        defaultCurrencyShouldBeFound("name.contains=" + DEFAULT_NAME);

        // Get all the currencyList where name contains UPDATED_NAME
        defaultCurrencyShouldNotBeFound("name.contains=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    public void getAllCurrenciesByNameNotContainsSomething() throws Exception {
        // Initialize the database
        currencyRepository.saveAndFlush(currency);

        // Get all the currencyList where name does not contain DEFAULT_NAME
        defaultCurrencyShouldNotBeFound("name.doesNotContain=" + DEFAULT_NAME);

        // Get all the currencyList where name does not contain UPDATED_NAME
        defaultCurrencyShouldBeFound("name.doesNotContain=" + UPDATED_NAME);
    }


    @Test
    @Transactional
    public void getAllCurrenciesByDescriptionIsEqualToSomething() throws Exception {
        // Initialize the database
        currencyRepository.saveAndFlush(currency);

        // Get all the currencyList where description equals to DEFAULT_DESCRIPTION
        defaultCurrencyShouldBeFound("description.equals=" + DEFAULT_DESCRIPTION);

        // Get all the currencyList where description equals to UPDATED_DESCRIPTION
        defaultCurrencyShouldNotBeFound("description.equals=" + UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    public void getAllCurrenciesByDescriptionIsNotEqualToSomething() throws Exception {
        // Initialize the database
        currencyRepository.saveAndFlush(currency);

        // Get all the currencyList where description not equals to DEFAULT_DESCRIPTION
        defaultCurrencyShouldNotBeFound("description.notEquals=" + DEFAULT_DESCRIPTION);

        // Get all the currencyList where description not equals to UPDATED_DESCRIPTION
        defaultCurrencyShouldBeFound("description.notEquals=" + UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    public void getAllCurrenciesByDescriptionIsInShouldWork() throws Exception {
        // Initialize the database
        currencyRepository.saveAndFlush(currency);

        // Get all the currencyList where description in DEFAULT_DESCRIPTION or UPDATED_DESCRIPTION
        defaultCurrencyShouldBeFound("description.in=" + DEFAULT_DESCRIPTION + "," + UPDATED_DESCRIPTION);

        // Get all the currencyList where description equals to UPDATED_DESCRIPTION
        defaultCurrencyShouldNotBeFound("description.in=" + UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    public void getAllCurrenciesByDescriptionIsNullOrNotNull() throws Exception {
        // Initialize the database
        currencyRepository.saveAndFlush(currency);

        // Get all the currencyList where description is not null
        defaultCurrencyShouldBeFound("description.specified=true");

        // Get all the currencyList where description is null
        defaultCurrencyShouldNotBeFound("description.specified=false");
    }
                @Test
    @Transactional
    public void getAllCurrenciesByDescriptionContainsSomething() throws Exception {
        // Initialize the database
        currencyRepository.saveAndFlush(currency);

        // Get all the currencyList where description contains DEFAULT_DESCRIPTION
        defaultCurrencyShouldBeFound("description.contains=" + DEFAULT_DESCRIPTION);

        // Get all the currencyList where description contains UPDATED_DESCRIPTION
        defaultCurrencyShouldNotBeFound("description.contains=" + UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    public void getAllCurrenciesByDescriptionNotContainsSomething() throws Exception {
        // Initialize the database
        currencyRepository.saveAndFlush(currency);

        // Get all the currencyList where description does not contain DEFAULT_DESCRIPTION
        defaultCurrencyShouldNotBeFound("description.doesNotContain=" + DEFAULT_DESCRIPTION);

        // Get all the currencyList where description does not contain UPDATED_DESCRIPTION
        defaultCurrencyShouldBeFound("description.doesNotContain=" + UPDATED_DESCRIPTION);
    }


    @Test
    @Transactional
    public void getAllCurrenciesBySimbolIsEqualToSomething() throws Exception {
        // Initialize the database
        currencyRepository.saveAndFlush(currency);

        // Get all the currencyList where simbol equals to DEFAULT_SIMBOL
        defaultCurrencyShouldBeFound("simbol.equals=" + DEFAULT_SIMBOL);

        // Get all the currencyList where simbol equals to UPDATED_SIMBOL
        defaultCurrencyShouldNotBeFound("simbol.equals=" + UPDATED_SIMBOL);
    }

    @Test
    @Transactional
    public void getAllCurrenciesBySimbolIsNotEqualToSomething() throws Exception {
        // Initialize the database
        currencyRepository.saveAndFlush(currency);

        // Get all the currencyList where simbol not equals to DEFAULT_SIMBOL
        defaultCurrencyShouldNotBeFound("simbol.notEquals=" + DEFAULT_SIMBOL);

        // Get all the currencyList where simbol not equals to UPDATED_SIMBOL
        defaultCurrencyShouldBeFound("simbol.notEquals=" + UPDATED_SIMBOL);
    }

    @Test
    @Transactional
    public void getAllCurrenciesBySimbolIsInShouldWork() throws Exception {
        // Initialize the database
        currencyRepository.saveAndFlush(currency);

        // Get all the currencyList where simbol in DEFAULT_SIMBOL or UPDATED_SIMBOL
        defaultCurrencyShouldBeFound("simbol.in=" + DEFAULT_SIMBOL + "," + UPDATED_SIMBOL);

        // Get all the currencyList where simbol equals to UPDATED_SIMBOL
        defaultCurrencyShouldNotBeFound("simbol.in=" + UPDATED_SIMBOL);
    }

    @Test
    @Transactional
    public void getAllCurrenciesBySimbolIsNullOrNotNull() throws Exception {
        // Initialize the database
        currencyRepository.saveAndFlush(currency);

        // Get all the currencyList where simbol is not null
        defaultCurrencyShouldBeFound("simbol.specified=true");

        // Get all the currencyList where simbol is null
        defaultCurrencyShouldNotBeFound("simbol.specified=false");
    }
                @Test
    @Transactional
    public void getAllCurrenciesBySimbolContainsSomething() throws Exception {
        // Initialize the database
        currencyRepository.saveAndFlush(currency);

        // Get all the currencyList where simbol contains DEFAULT_SIMBOL
        defaultCurrencyShouldBeFound("simbol.contains=" + DEFAULT_SIMBOL);

        // Get all the currencyList where simbol contains UPDATED_SIMBOL
        defaultCurrencyShouldNotBeFound("simbol.contains=" + UPDATED_SIMBOL);
    }

    @Test
    @Transactional
    public void getAllCurrenciesBySimbolNotContainsSomething() throws Exception {
        // Initialize the database
        currencyRepository.saveAndFlush(currency);

        // Get all the currencyList where simbol does not contain DEFAULT_SIMBOL
        defaultCurrencyShouldNotBeFound("simbol.doesNotContain=" + DEFAULT_SIMBOL);

        // Get all the currencyList where simbol does not contain UPDATED_SIMBOL
        defaultCurrencyShouldBeFound("simbol.doesNotContain=" + UPDATED_SIMBOL);
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultCurrencyShouldBeFound(String filter) throws Exception {
        restCurrencyMockMvc.perform(get("/api/currencies?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(currency.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION)))
            .andExpect(jsonPath("$.[*].simbol").value(hasItem(DEFAULT_SIMBOL)));

        // Check, that the count call also returns 1
        restCurrencyMockMvc.perform(get("/api/currencies/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultCurrencyShouldNotBeFound(String filter) throws Exception {
        restCurrencyMockMvc.perform(get("/api/currencies?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restCurrencyMockMvc.perform(get("/api/currencies/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    public void getNonExistingCurrency() throws Exception {
        // Get the currency
        restCurrencyMockMvc.perform(get("/api/currencies/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateCurrency() throws Exception {
        // Initialize the database
        currencyService.save(currency);

        int databaseSizeBeforeUpdate = currencyRepository.findAll().size();

        // Update the currency
        Currency updatedCurrency = currencyRepository.findById(currency.getId()).get();
        // Disconnect from session so that the updates on updatedCurrency are not directly saved in db
        em.detach(updatedCurrency);
        updatedCurrency
            .name(UPDATED_NAME)
            .description(UPDATED_DESCRIPTION)
            .simbol(UPDATED_SIMBOL);

        restCurrencyMockMvc.perform(put("/api/currencies")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(updatedCurrency)))
            .andExpect(status().isOk());

        // Validate the Currency in the database
        List<Currency> currencyList = currencyRepository.findAll();
        assertThat(currencyList).hasSize(databaseSizeBeforeUpdate);
        Currency testCurrency = currencyList.get(currencyList.size() - 1);
        assertThat(testCurrency.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testCurrency.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testCurrency.getSimbol()).isEqualTo(UPDATED_SIMBOL);
    }

    @Test
    @Transactional
    public void updateNonExistingCurrency() throws Exception {
        int databaseSizeBeforeUpdate = currencyRepository.findAll().size();

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restCurrencyMockMvc.perform(put("/api/currencies")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(currency)))
            .andExpect(status().isBadRequest());

        // Validate the Currency in the database
        List<Currency> currencyList = currencyRepository.findAll();
        assertThat(currencyList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteCurrency() throws Exception {
        // Initialize the database
        currencyService.save(currency);

        int databaseSizeBeforeDelete = currencyRepository.findAll().size();

        // Delete the currency
        restCurrencyMockMvc.perform(delete("/api/currencies/{id}", currency.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Currency> currencyList = currencyRepository.findAll();
        assertThat(currencyList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
