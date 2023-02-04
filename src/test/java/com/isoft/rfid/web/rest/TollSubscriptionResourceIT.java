package com.isoft.rfid.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.isoft.rfid.IntegrationTest;
import com.isoft.rfid.domain.TollPackage;
import com.isoft.rfid.domain.TollSubscription;
import com.isoft.rfid.repository.TollSubscriptionRepository;
import com.isoft.rfid.service.criteria.TollSubscriptionCriteria;
import com.isoft.rfid.service.dto.TollSubscriptionDTO;
import com.isoft.rfid.service.mapper.TollSubscriptionMapper;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Random;
import java.util.concurrent.atomic.AtomicLong;
import javax.persistence.EntityManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

/**
 * Integration tests for the {@link TollSubscriptionResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class TollSubscriptionResourceIT {

    private static final Instant DEFAULT_DATE_TIME_FROM = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_DATE_TIME_FROM = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Integer DEFAULT_VEHICLE = 1;
    private static final Integer UPDATED_VEHICLE = 2;
    private static final Integer SMALLER_VEHICLE = 1 - 1;

    private static final Integer DEFAULT_VEHICLE_OWNER = 1;
    private static final Integer UPDATED_VEHICLE_OWNER = 2;
    private static final Integer SMALLER_VEHICLE_OWNER = 1 - 1;

    private static final Integer DEFAULT_ACTIVE = 1;
    private static final Integer UPDATED_ACTIVE = 2;
    private static final Integer SMALLER_ACTIVE = 1 - 1;

    private static final Instant DEFAULT_UPDATED = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_UPDATED = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final String ENTITY_API_URL = "/api/toll-subscriptions";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private TollSubscriptionRepository tollSubscriptionRepository;

    @Autowired
    private TollSubscriptionMapper tollSubscriptionMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restTollSubscriptionMockMvc;

    private TollSubscription tollSubscription;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static TollSubscription createEntity(EntityManager em) {
        TollSubscription tollSubscription = new TollSubscription()
            .dateTimeFrom(DEFAULT_DATE_TIME_FROM)
            .vehicle(DEFAULT_VEHICLE)
            .vehicleOwner(DEFAULT_VEHICLE_OWNER)
            .active(DEFAULT_ACTIVE)
            .updated(DEFAULT_UPDATED);
        return tollSubscription;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static TollSubscription createUpdatedEntity(EntityManager em) {
        TollSubscription tollSubscription = new TollSubscription()
            .dateTimeFrom(UPDATED_DATE_TIME_FROM)
            .vehicle(UPDATED_VEHICLE)
            .vehicleOwner(UPDATED_VEHICLE_OWNER)
            .active(UPDATED_ACTIVE)
            .updated(UPDATED_UPDATED);
        return tollSubscription;
    }

    @BeforeEach
    public void initTest() {
        tollSubscription = createEntity(em);
    }

    @Test
    @Transactional
    void createTollSubscription() throws Exception {
        int databaseSizeBeforeCreate = tollSubscriptionRepository.findAll().size();
        // Create the TollSubscription
        TollSubscriptionDTO tollSubscriptionDTO = tollSubscriptionMapper.toDto(tollSubscription);
        restTollSubscriptionMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(tollSubscriptionDTO))
            )
            .andExpect(status().isCreated());

        // Validate the TollSubscription in the database
        List<TollSubscription> tollSubscriptionList = tollSubscriptionRepository.findAll();
        assertThat(tollSubscriptionList).hasSize(databaseSizeBeforeCreate + 1);
        TollSubscription testTollSubscription = tollSubscriptionList.get(tollSubscriptionList.size() - 1);
        assertThat(testTollSubscription.getDateTimeFrom()).isEqualTo(DEFAULT_DATE_TIME_FROM);
        assertThat(testTollSubscription.getVehicle()).isEqualTo(DEFAULT_VEHICLE);
        assertThat(testTollSubscription.getVehicleOwner()).isEqualTo(DEFAULT_VEHICLE_OWNER);
        assertThat(testTollSubscription.getActive()).isEqualTo(DEFAULT_ACTIVE);
        assertThat(testTollSubscription.getUpdated()).isEqualTo(DEFAULT_UPDATED);
    }

    @Test
    @Transactional
    void createTollSubscriptionWithExistingId() throws Exception {
        // Create the TollSubscription with an existing ID
        tollSubscription.setId(1L);
        TollSubscriptionDTO tollSubscriptionDTO = tollSubscriptionMapper.toDto(tollSubscription);

        int databaseSizeBeforeCreate = tollSubscriptionRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restTollSubscriptionMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(tollSubscriptionDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the TollSubscription in the database
        List<TollSubscription> tollSubscriptionList = tollSubscriptionRepository.findAll();
        assertThat(tollSubscriptionList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkDateTimeFromIsRequired() throws Exception {
        int databaseSizeBeforeTest = tollSubscriptionRepository.findAll().size();
        // set the field null
        tollSubscription.setDateTimeFrom(null);

        // Create the TollSubscription, which fails.
        TollSubscriptionDTO tollSubscriptionDTO = tollSubscriptionMapper.toDto(tollSubscription);

        restTollSubscriptionMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(tollSubscriptionDTO))
            )
            .andExpect(status().isBadRequest());

        List<TollSubscription> tollSubscriptionList = tollSubscriptionRepository.findAll();
        assertThat(tollSubscriptionList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkActiveIsRequired() throws Exception {
        int databaseSizeBeforeTest = tollSubscriptionRepository.findAll().size();
        // set the field null
        tollSubscription.setActive(null);

        // Create the TollSubscription, which fails.
        TollSubscriptionDTO tollSubscriptionDTO = tollSubscriptionMapper.toDto(tollSubscription);

        restTollSubscriptionMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(tollSubscriptionDTO))
            )
            .andExpect(status().isBadRequest());

        List<TollSubscription> tollSubscriptionList = tollSubscriptionRepository.findAll();
        assertThat(tollSubscriptionList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkUpdatedIsRequired() throws Exception {
        int databaseSizeBeforeTest = tollSubscriptionRepository.findAll().size();
        // set the field null
        tollSubscription.setUpdated(null);

        // Create the TollSubscription, which fails.
        TollSubscriptionDTO tollSubscriptionDTO = tollSubscriptionMapper.toDto(tollSubscription);

        restTollSubscriptionMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(tollSubscriptionDTO))
            )
            .andExpect(status().isBadRequest());

        List<TollSubscription> tollSubscriptionList = tollSubscriptionRepository.findAll();
        assertThat(tollSubscriptionList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllTollSubscriptions() throws Exception {
        // Initialize the database
        tollSubscriptionRepository.saveAndFlush(tollSubscription);

        // Get all the tollSubscriptionList
        restTollSubscriptionMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(tollSubscription.getId().intValue())))
            .andExpect(jsonPath("$.[*].dateTimeFrom").value(hasItem(DEFAULT_DATE_TIME_FROM.toString())))
            .andExpect(jsonPath("$.[*].vehicle").value(hasItem(DEFAULT_VEHICLE)))
            .andExpect(jsonPath("$.[*].vehicleOwner").value(hasItem(DEFAULT_VEHICLE_OWNER)))
            .andExpect(jsonPath("$.[*].active").value(hasItem(DEFAULT_ACTIVE)))
            .andExpect(jsonPath("$.[*].updated").value(hasItem(DEFAULT_UPDATED.toString())));
    }

    @Test
    @Transactional
    void getTollSubscription() throws Exception {
        // Initialize the database
        tollSubscriptionRepository.saveAndFlush(tollSubscription);

        // Get the tollSubscription
        restTollSubscriptionMockMvc
            .perform(get(ENTITY_API_URL_ID, tollSubscription.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(tollSubscription.getId().intValue()))
            .andExpect(jsonPath("$.dateTimeFrom").value(DEFAULT_DATE_TIME_FROM.toString()))
            .andExpect(jsonPath("$.vehicle").value(DEFAULT_VEHICLE))
            .andExpect(jsonPath("$.vehicleOwner").value(DEFAULT_VEHICLE_OWNER))
            .andExpect(jsonPath("$.active").value(DEFAULT_ACTIVE))
            .andExpect(jsonPath("$.updated").value(DEFAULT_UPDATED.toString()));
    }

    @Test
    @Transactional
    void getTollSubscriptionsByIdFiltering() throws Exception {
        // Initialize the database
        tollSubscriptionRepository.saveAndFlush(tollSubscription);

        Long id = tollSubscription.getId();

        defaultTollSubscriptionShouldBeFound("id.equals=" + id);
        defaultTollSubscriptionShouldNotBeFound("id.notEquals=" + id);

        defaultTollSubscriptionShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultTollSubscriptionShouldNotBeFound("id.greaterThan=" + id);

        defaultTollSubscriptionShouldBeFound("id.lessThanOrEqual=" + id);
        defaultTollSubscriptionShouldNotBeFound("id.lessThan=" + id);
    }

    @Test
    @Transactional
    void getAllTollSubscriptionsByDateTimeFromIsEqualToSomething() throws Exception {
        // Initialize the database
        tollSubscriptionRepository.saveAndFlush(tollSubscription);

        // Get all the tollSubscriptionList where dateTimeFrom equals to DEFAULT_DATE_TIME_FROM
        defaultTollSubscriptionShouldBeFound("dateTimeFrom.equals=" + DEFAULT_DATE_TIME_FROM);

        // Get all the tollSubscriptionList where dateTimeFrom equals to UPDATED_DATE_TIME_FROM
        defaultTollSubscriptionShouldNotBeFound("dateTimeFrom.equals=" + UPDATED_DATE_TIME_FROM);
    }

    @Test
    @Transactional
    void getAllTollSubscriptionsByDateTimeFromIsInShouldWork() throws Exception {
        // Initialize the database
        tollSubscriptionRepository.saveAndFlush(tollSubscription);

        // Get all the tollSubscriptionList where dateTimeFrom in DEFAULT_DATE_TIME_FROM or UPDATED_DATE_TIME_FROM
        defaultTollSubscriptionShouldBeFound("dateTimeFrom.in=" + DEFAULT_DATE_TIME_FROM + "," + UPDATED_DATE_TIME_FROM);

        // Get all the tollSubscriptionList where dateTimeFrom equals to UPDATED_DATE_TIME_FROM
        defaultTollSubscriptionShouldNotBeFound("dateTimeFrom.in=" + UPDATED_DATE_TIME_FROM);
    }

    @Test
    @Transactional
    void getAllTollSubscriptionsByDateTimeFromIsNullOrNotNull() throws Exception {
        // Initialize the database
        tollSubscriptionRepository.saveAndFlush(tollSubscription);

        // Get all the tollSubscriptionList where dateTimeFrom is not null
        defaultTollSubscriptionShouldBeFound("dateTimeFrom.specified=true");

        // Get all the tollSubscriptionList where dateTimeFrom is null
        defaultTollSubscriptionShouldNotBeFound("dateTimeFrom.specified=false");
    }

    @Test
    @Transactional
    void getAllTollSubscriptionsByVehicleIsEqualToSomething() throws Exception {
        // Initialize the database
        tollSubscriptionRepository.saveAndFlush(tollSubscription);

        // Get all the tollSubscriptionList where vehicle equals to DEFAULT_VEHICLE
        defaultTollSubscriptionShouldBeFound("vehicle.equals=" + DEFAULT_VEHICLE);

        // Get all the tollSubscriptionList where vehicle equals to UPDATED_VEHICLE
        defaultTollSubscriptionShouldNotBeFound("vehicle.equals=" + UPDATED_VEHICLE);
    }

    @Test
    @Transactional
    void getAllTollSubscriptionsByVehicleIsInShouldWork() throws Exception {
        // Initialize the database
        tollSubscriptionRepository.saveAndFlush(tollSubscription);

        // Get all the tollSubscriptionList where vehicle in DEFAULT_VEHICLE or UPDATED_VEHICLE
        defaultTollSubscriptionShouldBeFound("vehicle.in=" + DEFAULT_VEHICLE + "," + UPDATED_VEHICLE);

        // Get all the tollSubscriptionList where vehicle equals to UPDATED_VEHICLE
        defaultTollSubscriptionShouldNotBeFound("vehicle.in=" + UPDATED_VEHICLE);
    }

    @Test
    @Transactional
    void getAllTollSubscriptionsByVehicleIsNullOrNotNull() throws Exception {
        // Initialize the database
        tollSubscriptionRepository.saveAndFlush(tollSubscription);

        // Get all the tollSubscriptionList where vehicle is not null
        defaultTollSubscriptionShouldBeFound("vehicle.specified=true");

        // Get all the tollSubscriptionList where vehicle is null
        defaultTollSubscriptionShouldNotBeFound("vehicle.specified=false");
    }

    @Test
    @Transactional
    void getAllTollSubscriptionsByVehicleIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        tollSubscriptionRepository.saveAndFlush(tollSubscription);

        // Get all the tollSubscriptionList where vehicle is greater than or equal to DEFAULT_VEHICLE
        defaultTollSubscriptionShouldBeFound("vehicle.greaterThanOrEqual=" + DEFAULT_VEHICLE);

        // Get all the tollSubscriptionList where vehicle is greater than or equal to UPDATED_VEHICLE
        defaultTollSubscriptionShouldNotBeFound("vehicle.greaterThanOrEqual=" + UPDATED_VEHICLE);
    }

    @Test
    @Transactional
    void getAllTollSubscriptionsByVehicleIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        tollSubscriptionRepository.saveAndFlush(tollSubscription);

        // Get all the tollSubscriptionList where vehicle is less than or equal to DEFAULT_VEHICLE
        defaultTollSubscriptionShouldBeFound("vehicle.lessThanOrEqual=" + DEFAULT_VEHICLE);

        // Get all the tollSubscriptionList where vehicle is less than or equal to SMALLER_VEHICLE
        defaultTollSubscriptionShouldNotBeFound("vehicle.lessThanOrEqual=" + SMALLER_VEHICLE);
    }

    @Test
    @Transactional
    void getAllTollSubscriptionsByVehicleIsLessThanSomething() throws Exception {
        // Initialize the database
        tollSubscriptionRepository.saveAndFlush(tollSubscription);

        // Get all the tollSubscriptionList where vehicle is less than DEFAULT_VEHICLE
        defaultTollSubscriptionShouldNotBeFound("vehicle.lessThan=" + DEFAULT_VEHICLE);

        // Get all the tollSubscriptionList where vehicle is less than UPDATED_VEHICLE
        defaultTollSubscriptionShouldBeFound("vehicle.lessThan=" + UPDATED_VEHICLE);
    }

    @Test
    @Transactional
    void getAllTollSubscriptionsByVehicleIsGreaterThanSomething() throws Exception {
        // Initialize the database
        tollSubscriptionRepository.saveAndFlush(tollSubscription);

        // Get all the tollSubscriptionList where vehicle is greater than DEFAULT_VEHICLE
        defaultTollSubscriptionShouldNotBeFound("vehicle.greaterThan=" + DEFAULT_VEHICLE);

        // Get all the tollSubscriptionList where vehicle is greater than SMALLER_VEHICLE
        defaultTollSubscriptionShouldBeFound("vehicle.greaterThan=" + SMALLER_VEHICLE);
    }

    @Test
    @Transactional
    void getAllTollSubscriptionsByVehicleOwnerIsEqualToSomething() throws Exception {
        // Initialize the database
        tollSubscriptionRepository.saveAndFlush(tollSubscription);

        // Get all the tollSubscriptionList where vehicleOwner equals to DEFAULT_VEHICLE_OWNER
        defaultTollSubscriptionShouldBeFound("vehicleOwner.equals=" + DEFAULT_VEHICLE_OWNER);

        // Get all the tollSubscriptionList where vehicleOwner equals to UPDATED_VEHICLE_OWNER
        defaultTollSubscriptionShouldNotBeFound("vehicleOwner.equals=" + UPDATED_VEHICLE_OWNER);
    }

    @Test
    @Transactional
    void getAllTollSubscriptionsByVehicleOwnerIsInShouldWork() throws Exception {
        // Initialize the database
        tollSubscriptionRepository.saveAndFlush(tollSubscription);

        // Get all the tollSubscriptionList where vehicleOwner in DEFAULT_VEHICLE_OWNER or UPDATED_VEHICLE_OWNER
        defaultTollSubscriptionShouldBeFound("vehicleOwner.in=" + DEFAULT_VEHICLE_OWNER + "," + UPDATED_VEHICLE_OWNER);

        // Get all the tollSubscriptionList where vehicleOwner equals to UPDATED_VEHICLE_OWNER
        defaultTollSubscriptionShouldNotBeFound("vehicleOwner.in=" + UPDATED_VEHICLE_OWNER);
    }

    @Test
    @Transactional
    void getAllTollSubscriptionsByVehicleOwnerIsNullOrNotNull() throws Exception {
        // Initialize the database
        tollSubscriptionRepository.saveAndFlush(tollSubscription);

        // Get all the tollSubscriptionList where vehicleOwner is not null
        defaultTollSubscriptionShouldBeFound("vehicleOwner.specified=true");

        // Get all the tollSubscriptionList where vehicleOwner is null
        defaultTollSubscriptionShouldNotBeFound("vehicleOwner.specified=false");
    }

    @Test
    @Transactional
    void getAllTollSubscriptionsByVehicleOwnerIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        tollSubscriptionRepository.saveAndFlush(tollSubscription);

        // Get all the tollSubscriptionList where vehicleOwner is greater than or equal to DEFAULT_VEHICLE_OWNER
        defaultTollSubscriptionShouldBeFound("vehicleOwner.greaterThanOrEqual=" + DEFAULT_VEHICLE_OWNER);

        // Get all the tollSubscriptionList where vehicleOwner is greater than or equal to UPDATED_VEHICLE_OWNER
        defaultTollSubscriptionShouldNotBeFound("vehicleOwner.greaterThanOrEqual=" + UPDATED_VEHICLE_OWNER);
    }

    @Test
    @Transactional
    void getAllTollSubscriptionsByVehicleOwnerIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        tollSubscriptionRepository.saveAndFlush(tollSubscription);

        // Get all the tollSubscriptionList where vehicleOwner is less than or equal to DEFAULT_VEHICLE_OWNER
        defaultTollSubscriptionShouldBeFound("vehicleOwner.lessThanOrEqual=" + DEFAULT_VEHICLE_OWNER);

        // Get all the tollSubscriptionList where vehicleOwner is less than or equal to SMALLER_VEHICLE_OWNER
        defaultTollSubscriptionShouldNotBeFound("vehicleOwner.lessThanOrEqual=" + SMALLER_VEHICLE_OWNER);
    }

    @Test
    @Transactional
    void getAllTollSubscriptionsByVehicleOwnerIsLessThanSomething() throws Exception {
        // Initialize the database
        tollSubscriptionRepository.saveAndFlush(tollSubscription);

        // Get all the tollSubscriptionList where vehicleOwner is less than DEFAULT_VEHICLE_OWNER
        defaultTollSubscriptionShouldNotBeFound("vehicleOwner.lessThan=" + DEFAULT_VEHICLE_OWNER);

        // Get all the tollSubscriptionList where vehicleOwner is less than UPDATED_VEHICLE_OWNER
        defaultTollSubscriptionShouldBeFound("vehicleOwner.lessThan=" + UPDATED_VEHICLE_OWNER);
    }

    @Test
    @Transactional
    void getAllTollSubscriptionsByVehicleOwnerIsGreaterThanSomething() throws Exception {
        // Initialize the database
        tollSubscriptionRepository.saveAndFlush(tollSubscription);

        // Get all the tollSubscriptionList where vehicleOwner is greater than DEFAULT_VEHICLE_OWNER
        defaultTollSubscriptionShouldNotBeFound("vehicleOwner.greaterThan=" + DEFAULT_VEHICLE_OWNER);

        // Get all the tollSubscriptionList where vehicleOwner is greater than SMALLER_VEHICLE_OWNER
        defaultTollSubscriptionShouldBeFound("vehicleOwner.greaterThan=" + SMALLER_VEHICLE_OWNER);
    }

    @Test
    @Transactional
    void getAllTollSubscriptionsByActiveIsEqualToSomething() throws Exception {
        // Initialize the database
        tollSubscriptionRepository.saveAndFlush(tollSubscription);

        // Get all the tollSubscriptionList where active equals to DEFAULT_ACTIVE
        defaultTollSubscriptionShouldBeFound("active.equals=" + DEFAULT_ACTIVE);

        // Get all the tollSubscriptionList where active equals to UPDATED_ACTIVE
        defaultTollSubscriptionShouldNotBeFound("active.equals=" + UPDATED_ACTIVE);
    }

    @Test
    @Transactional
    void getAllTollSubscriptionsByActiveIsInShouldWork() throws Exception {
        // Initialize the database
        tollSubscriptionRepository.saveAndFlush(tollSubscription);

        // Get all the tollSubscriptionList where active in DEFAULT_ACTIVE or UPDATED_ACTIVE
        defaultTollSubscriptionShouldBeFound("active.in=" + DEFAULT_ACTIVE + "," + UPDATED_ACTIVE);

        // Get all the tollSubscriptionList where active equals to UPDATED_ACTIVE
        defaultTollSubscriptionShouldNotBeFound("active.in=" + UPDATED_ACTIVE);
    }

    @Test
    @Transactional
    void getAllTollSubscriptionsByActiveIsNullOrNotNull() throws Exception {
        // Initialize the database
        tollSubscriptionRepository.saveAndFlush(tollSubscription);

        // Get all the tollSubscriptionList where active is not null
        defaultTollSubscriptionShouldBeFound("active.specified=true");

        // Get all the tollSubscriptionList where active is null
        defaultTollSubscriptionShouldNotBeFound("active.specified=false");
    }

    @Test
    @Transactional
    void getAllTollSubscriptionsByActiveIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        tollSubscriptionRepository.saveAndFlush(tollSubscription);

        // Get all the tollSubscriptionList where active is greater than or equal to DEFAULT_ACTIVE
        defaultTollSubscriptionShouldBeFound("active.greaterThanOrEqual=" + DEFAULT_ACTIVE);

        // Get all the tollSubscriptionList where active is greater than or equal to UPDATED_ACTIVE
        defaultTollSubscriptionShouldNotBeFound("active.greaterThanOrEqual=" + UPDATED_ACTIVE);
    }

    @Test
    @Transactional
    void getAllTollSubscriptionsByActiveIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        tollSubscriptionRepository.saveAndFlush(tollSubscription);

        // Get all the tollSubscriptionList where active is less than or equal to DEFAULT_ACTIVE
        defaultTollSubscriptionShouldBeFound("active.lessThanOrEqual=" + DEFAULT_ACTIVE);

        // Get all the tollSubscriptionList where active is less than or equal to SMALLER_ACTIVE
        defaultTollSubscriptionShouldNotBeFound("active.lessThanOrEqual=" + SMALLER_ACTIVE);
    }

    @Test
    @Transactional
    void getAllTollSubscriptionsByActiveIsLessThanSomething() throws Exception {
        // Initialize the database
        tollSubscriptionRepository.saveAndFlush(tollSubscription);

        // Get all the tollSubscriptionList where active is less than DEFAULT_ACTIVE
        defaultTollSubscriptionShouldNotBeFound("active.lessThan=" + DEFAULT_ACTIVE);

        // Get all the tollSubscriptionList where active is less than UPDATED_ACTIVE
        defaultTollSubscriptionShouldBeFound("active.lessThan=" + UPDATED_ACTIVE);
    }

    @Test
    @Transactional
    void getAllTollSubscriptionsByActiveIsGreaterThanSomething() throws Exception {
        // Initialize the database
        tollSubscriptionRepository.saveAndFlush(tollSubscription);

        // Get all the tollSubscriptionList where active is greater than DEFAULT_ACTIVE
        defaultTollSubscriptionShouldNotBeFound("active.greaterThan=" + DEFAULT_ACTIVE);

        // Get all the tollSubscriptionList where active is greater than SMALLER_ACTIVE
        defaultTollSubscriptionShouldBeFound("active.greaterThan=" + SMALLER_ACTIVE);
    }

    @Test
    @Transactional
    void getAllTollSubscriptionsByUpdatedIsEqualToSomething() throws Exception {
        // Initialize the database
        tollSubscriptionRepository.saveAndFlush(tollSubscription);

        // Get all the tollSubscriptionList where updated equals to DEFAULT_UPDATED
        defaultTollSubscriptionShouldBeFound("updated.equals=" + DEFAULT_UPDATED);

        // Get all the tollSubscriptionList where updated equals to UPDATED_UPDATED
        defaultTollSubscriptionShouldNotBeFound("updated.equals=" + UPDATED_UPDATED);
    }

    @Test
    @Transactional
    void getAllTollSubscriptionsByUpdatedIsInShouldWork() throws Exception {
        // Initialize the database
        tollSubscriptionRepository.saveAndFlush(tollSubscription);

        // Get all the tollSubscriptionList where updated in DEFAULT_UPDATED or UPDATED_UPDATED
        defaultTollSubscriptionShouldBeFound("updated.in=" + DEFAULT_UPDATED + "," + UPDATED_UPDATED);

        // Get all the tollSubscriptionList where updated equals to UPDATED_UPDATED
        defaultTollSubscriptionShouldNotBeFound("updated.in=" + UPDATED_UPDATED);
    }

    @Test
    @Transactional
    void getAllTollSubscriptionsByUpdatedIsNullOrNotNull() throws Exception {
        // Initialize the database
        tollSubscriptionRepository.saveAndFlush(tollSubscription);

        // Get all the tollSubscriptionList where updated is not null
        defaultTollSubscriptionShouldBeFound("updated.specified=true");

        // Get all the tollSubscriptionList where updated is null
        defaultTollSubscriptionShouldNotBeFound("updated.specified=false");
    }

    @Test
    @Transactional
    void getAllTollSubscriptionsByTollPackageIsEqualToSomething() throws Exception {
        TollPackage tollPackage;
        if (TestUtil.findAll(em, TollPackage.class).isEmpty()) {
            tollSubscriptionRepository.saveAndFlush(tollSubscription);
            tollPackage = TollPackageResourceIT.createEntity(em);
        } else {
            tollPackage = TestUtil.findAll(em, TollPackage.class).get(0);
        }
        em.persist(tollPackage);
        em.flush();
        tollSubscription.setTollPackage(tollPackage);
        tollSubscriptionRepository.saveAndFlush(tollSubscription);
        Long tollPackageId = tollPackage.getId();

        // Get all the tollSubscriptionList where tollPackage equals to tollPackageId
        defaultTollSubscriptionShouldBeFound("tollPackageId.equals=" + tollPackageId);

        // Get all the tollSubscriptionList where tollPackage equals to (tollPackageId + 1)
        defaultTollSubscriptionShouldNotBeFound("tollPackageId.equals=" + (tollPackageId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultTollSubscriptionShouldBeFound(String filter) throws Exception {
        restTollSubscriptionMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(tollSubscription.getId().intValue())))
            .andExpect(jsonPath("$.[*].dateTimeFrom").value(hasItem(DEFAULT_DATE_TIME_FROM.toString())))
            .andExpect(jsonPath("$.[*].vehicle").value(hasItem(DEFAULT_VEHICLE)))
            .andExpect(jsonPath("$.[*].vehicleOwner").value(hasItem(DEFAULT_VEHICLE_OWNER)))
            .andExpect(jsonPath("$.[*].active").value(hasItem(DEFAULT_ACTIVE)))
            .andExpect(jsonPath("$.[*].updated").value(hasItem(DEFAULT_UPDATED.toString())));

        // Check, that the count call also returns 1
        restTollSubscriptionMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultTollSubscriptionShouldNotBeFound(String filter) throws Exception {
        restTollSubscriptionMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restTollSubscriptionMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    void getNonExistingTollSubscription() throws Exception {
        // Get the tollSubscription
        restTollSubscriptionMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingTollSubscription() throws Exception {
        // Initialize the database
        tollSubscriptionRepository.saveAndFlush(tollSubscription);

        int databaseSizeBeforeUpdate = tollSubscriptionRepository.findAll().size();

        // Update the tollSubscription
        TollSubscription updatedTollSubscription = tollSubscriptionRepository.findById(tollSubscription.getId()).get();
        // Disconnect from session so that the updates on updatedTollSubscription are not directly saved in db
        em.detach(updatedTollSubscription);
        updatedTollSubscription
            .dateTimeFrom(UPDATED_DATE_TIME_FROM)
            .vehicle(UPDATED_VEHICLE)
            .vehicleOwner(UPDATED_VEHICLE_OWNER)
            .active(UPDATED_ACTIVE)
            .updated(UPDATED_UPDATED);
        TollSubscriptionDTO tollSubscriptionDTO = tollSubscriptionMapper.toDto(updatedTollSubscription);

        restTollSubscriptionMockMvc
            .perform(
                put(ENTITY_API_URL_ID, tollSubscriptionDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(tollSubscriptionDTO))
            )
            .andExpect(status().isOk());

        // Validate the TollSubscription in the database
        List<TollSubscription> tollSubscriptionList = tollSubscriptionRepository.findAll();
        assertThat(tollSubscriptionList).hasSize(databaseSizeBeforeUpdate);
        TollSubscription testTollSubscription = tollSubscriptionList.get(tollSubscriptionList.size() - 1);
        assertThat(testTollSubscription.getDateTimeFrom()).isEqualTo(UPDATED_DATE_TIME_FROM);
        assertThat(testTollSubscription.getVehicle()).isEqualTo(UPDATED_VEHICLE);
        assertThat(testTollSubscription.getVehicleOwner()).isEqualTo(UPDATED_VEHICLE_OWNER);
        assertThat(testTollSubscription.getActive()).isEqualTo(UPDATED_ACTIVE);
        assertThat(testTollSubscription.getUpdated()).isEqualTo(UPDATED_UPDATED);
    }

    @Test
    @Transactional
    void putNonExistingTollSubscription() throws Exception {
        int databaseSizeBeforeUpdate = tollSubscriptionRepository.findAll().size();
        tollSubscription.setId(count.incrementAndGet());

        // Create the TollSubscription
        TollSubscriptionDTO tollSubscriptionDTO = tollSubscriptionMapper.toDto(tollSubscription);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restTollSubscriptionMockMvc
            .perform(
                put(ENTITY_API_URL_ID, tollSubscriptionDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(tollSubscriptionDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the TollSubscription in the database
        List<TollSubscription> tollSubscriptionList = tollSubscriptionRepository.findAll();
        assertThat(tollSubscriptionList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchTollSubscription() throws Exception {
        int databaseSizeBeforeUpdate = tollSubscriptionRepository.findAll().size();
        tollSubscription.setId(count.incrementAndGet());

        // Create the TollSubscription
        TollSubscriptionDTO tollSubscriptionDTO = tollSubscriptionMapper.toDto(tollSubscription);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restTollSubscriptionMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(tollSubscriptionDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the TollSubscription in the database
        List<TollSubscription> tollSubscriptionList = tollSubscriptionRepository.findAll();
        assertThat(tollSubscriptionList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamTollSubscription() throws Exception {
        int databaseSizeBeforeUpdate = tollSubscriptionRepository.findAll().size();
        tollSubscription.setId(count.incrementAndGet());

        // Create the TollSubscription
        TollSubscriptionDTO tollSubscriptionDTO = tollSubscriptionMapper.toDto(tollSubscription);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restTollSubscriptionMockMvc
            .perform(
                put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(tollSubscriptionDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the TollSubscription in the database
        List<TollSubscription> tollSubscriptionList = tollSubscriptionRepository.findAll();
        assertThat(tollSubscriptionList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateTollSubscriptionWithPatch() throws Exception {
        // Initialize the database
        tollSubscriptionRepository.saveAndFlush(tollSubscription);

        int databaseSizeBeforeUpdate = tollSubscriptionRepository.findAll().size();

        // Update the tollSubscription using partial update
        TollSubscription partialUpdatedTollSubscription = new TollSubscription();
        partialUpdatedTollSubscription.setId(tollSubscription.getId());

        partialUpdatedTollSubscription.dateTimeFrom(UPDATED_DATE_TIME_FROM).active(UPDATED_ACTIVE);

        restTollSubscriptionMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedTollSubscription.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedTollSubscription))
            )
            .andExpect(status().isOk());

        // Validate the TollSubscription in the database
        List<TollSubscription> tollSubscriptionList = tollSubscriptionRepository.findAll();
        assertThat(tollSubscriptionList).hasSize(databaseSizeBeforeUpdate);
        TollSubscription testTollSubscription = tollSubscriptionList.get(tollSubscriptionList.size() - 1);
        assertThat(testTollSubscription.getDateTimeFrom()).isEqualTo(UPDATED_DATE_TIME_FROM);
        assertThat(testTollSubscription.getVehicle()).isEqualTo(DEFAULT_VEHICLE);
        assertThat(testTollSubscription.getVehicleOwner()).isEqualTo(DEFAULT_VEHICLE_OWNER);
        assertThat(testTollSubscription.getActive()).isEqualTo(UPDATED_ACTIVE);
        assertThat(testTollSubscription.getUpdated()).isEqualTo(DEFAULT_UPDATED);
    }

    @Test
    @Transactional
    void fullUpdateTollSubscriptionWithPatch() throws Exception {
        // Initialize the database
        tollSubscriptionRepository.saveAndFlush(tollSubscription);

        int databaseSizeBeforeUpdate = tollSubscriptionRepository.findAll().size();

        // Update the tollSubscription using partial update
        TollSubscription partialUpdatedTollSubscription = new TollSubscription();
        partialUpdatedTollSubscription.setId(tollSubscription.getId());

        partialUpdatedTollSubscription
            .dateTimeFrom(UPDATED_DATE_TIME_FROM)
            .vehicle(UPDATED_VEHICLE)
            .vehicleOwner(UPDATED_VEHICLE_OWNER)
            .active(UPDATED_ACTIVE)
            .updated(UPDATED_UPDATED);

        restTollSubscriptionMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedTollSubscription.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedTollSubscription))
            )
            .andExpect(status().isOk());

        // Validate the TollSubscription in the database
        List<TollSubscription> tollSubscriptionList = tollSubscriptionRepository.findAll();
        assertThat(tollSubscriptionList).hasSize(databaseSizeBeforeUpdate);
        TollSubscription testTollSubscription = tollSubscriptionList.get(tollSubscriptionList.size() - 1);
        assertThat(testTollSubscription.getDateTimeFrom()).isEqualTo(UPDATED_DATE_TIME_FROM);
        assertThat(testTollSubscription.getVehicle()).isEqualTo(UPDATED_VEHICLE);
        assertThat(testTollSubscription.getVehicleOwner()).isEqualTo(UPDATED_VEHICLE_OWNER);
        assertThat(testTollSubscription.getActive()).isEqualTo(UPDATED_ACTIVE);
        assertThat(testTollSubscription.getUpdated()).isEqualTo(UPDATED_UPDATED);
    }

    @Test
    @Transactional
    void patchNonExistingTollSubscription() throws Exception {
        int databaseSizeBeforeUpdate = tollSubscriptionRepository.findAll().size();
        tollSubscription.setId(count.incrementAndGet());

        // Create the TollSubscription
        TollSubscriptionDTO tollSubscriptionDTO = tollSubscriptionMapper.toDto(tollSubscription);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restTollSubscriptionMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, tollSubscriptionDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(tollSubscriptionDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the TollSubscription in the database
        List<TollSubscription> tollSubscriptionList = tollSubscriptionRepository.findAll();
        assertThat(tollSubscriptionList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchTollSubscription() throws Exception {
        int databaseSizeBeforeUpdate = tollSubscriptionRepository.findAll().size();
        tollSubscription.setId(count.incrementAndGet());

        // Create the TollSubscription
        TollSubscriptionDTO tollSubscriptionDTO = tollSubscriptionMapper.toDto(tollSubscription);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restTollSubscriptionMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(tollSubscriptionDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the TollSubscription in the database
        List<TollSubscription> tollSubscriptionList = tollSubscriptionRepository.findAll();
        assertThat(tollSubscriptionList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamTollSubscription() throws Exception {
        int databaseSizeBeforeUpdate = tollSubscriptionRepository.findAll().size();
        tollSubscription.setId(count.incrementAndGet());

        // Create the TollSubscription
        TollSubscriptionDTO tollSubscriptionDTO = tollSubscriptionMapper.toDto(tollSubscription);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restTollSubscriptionMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(tollSubscriptionDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the TollSubscription in the database
        List<TollSubscription> tollSubscriptionList = tollSubscriptionRepository.findAll();
        assertThat(tollSubscriptionList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteTollSubscription() throws Exception {
        // Initialize the database
        tollSubscriptionRepository.saveAndFlush(tollSubscription);

        int databaseSizeBeforeDelete = tollSubscriptionRepository.findAll().size();

        // Delete the tollSubscription
        restTollSubscriptionMockMvc
            .perform(delete(ENTITY_API_URL_ID, tollSubscription.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<TollSubscription> tollSubscriptionList = tollSubscriptionRepository.findAll();
        assertThat(tollSubscriptionList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
