package com.isoft.rfid.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.isoft.rfid.IntegrationTest;
import com.isoft.rfid.domain.TollPackage;
import com.isoft.rfid.domain.TollSubscription;
import com.isoft.rfid.repository.TollPackageRepository;
import com.isoft.rfid.service.criteria.TollPackageCriteria;
import com.isoft.rfid.service.dto.TollPackageDTO;
import com.isoft.rfid.service.mapper.TollPackageMapper;
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
 * Integration tests for the {@link TollPackageResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class TollPackageResourceIT {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_ENG_NAME = "AAAAAAAAAA";
    private static final String UPDATED_ENG_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_CODE = "AAAAAAAAAA";
    private static final String UPDATED_CODE = "BBBBBBBBBB";

    private static final Integer DEFAULT_ACTIVE = 1;
    private static final Integer UPDATED_ACTIVE = 2;
    private static final Integer SMALLER_ACTIVE = 1 - 1;

    private static final Instant DEFAULT_UPDATED = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_UPDATED = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Integer DEFAULT_DURATION_IN_DAYS = 1;
    private static final Integer UPDATED_DURATION_IN_DAYS = 2;
    private static final Integer SMALLER_DURATION_IN_DAYS = 1 - 1;

    private static final Integer DEFAULT_GANTRY = 1;
    private static final Integer UPDATED_GANTRY = 2;
    private static final Integer SMALLER_GANTRY = 1 - 1;

    private static final Integer DEFAULT_VEHICLE_LICENSE_TYPE = 1;
    private static final Integer UPDATED_VEHICLE_LICENSE_TYPE = 2;
    private static final Integer SMALLER_VEHICLE_LICENSE_TYPE = 1 - 1;

    private static final Integer DEFAULT_PASSAGE_TIMES = 1;
    private static final Integer UPDATED_PASSAGE_TIMES = 2;
    private static final Integer SMALLER_PASSAGE_TIMES = 1 - 1;

    private static final Double DEFAULT_TOTAL_FEES = 1D;
    private static final Double UPDATED_TOTAL_FEES = 2D;
    private static final Double SMALLER_TOTAL_FEES = 1D - 1D;

    private static final String ENTITY_API_URL = "/api/toll-packages";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private TollPackageRepository tollPackageRepository;

    @Autowired
    private TollPackageMapper tollPackageMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restTollPackageMockMvc;

    private TollPackage tollPackage;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static TollPackage createEntity(EntityManager em) {
        TollPackage tollPackage = new TollPackage()
            .name(DEFAULT_NAME)
            .engName(DEFAULT_ENG_NAME)
            .code(DEFAULT_CODE)
            .active(DEFAULT_ACTIVE)
            .updated(DEFAULT_UPDATED)
            .durationInDays(DEFAULT_DURATION_IN_DAYS)
            .gantry(DEFAULT_GANTRY)
            .vehicleLicenseType(DEFAULT_VEHICLE_LICENSE_TYPE)
            .passageTimes(DEFAULT_PASSAGE_TIMES)
            .totalFees(DEFAULT_TOTAL_FEES);
        return tollPackage;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static TollPackage createUpdatedEntity(EntityManager em) {
        TollPackage tollPackage = new TollPackage()
            .name(UPDATED_NAME)
            .engName(UPDATED_ENG_NAME)
            .code(UPDATED_CODE)
            .active(UPDATED_ACTIVE)
            .updated(UPDATED_UPDATED)
            .durationInDays(UPDATED_DURATION_IN_DAYS)
            .gantry(UPDATED_GANTRY)
            .vehicleLicenseType(UPDATED_VEHICLE_LICENSE_TYPE)
            .passageTimes(UPDATED_PASSAGE_TIMES)
            .totalFees(UPDATED_TOTAL_FEES);
        return tollPackage;
    }

    @BeforeEach
    public void initTest() {
        tollPackage = createEntity(em);
    }

    @Test
    @Transactional
    void createTollPackage() throws Exception {
        int databaseSizeBeforeCreate = tollPackageRepository.findAll().size();
        // Create the TollPackage
        TollPackageDTO tollPackageDTO = tollPackageMapper.toDto(tollPackage);
        restTollPackageMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(tollPackageDTO))
            )
            .andExpect(status().isCreated());

        // Validate the TollPackage in the database
        List<TollPackage> tollPackageList = tollPackageRepository.findAll();
        assertThat(tollPackageList).hasSize(databaseSizeBeforeCreate + 1);
        TollPackage testTollPackage = tollPackageList.get(tollPackageList.size() - 1);
        assertThat(testTollPackage.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testTollPackage.getEngName()).isEqualTo(DEFAULT_ENG_NAME);
        assertThat(testTollPackage.getCode()).isEqualTo(DEFAULT_CODE);
        assertThat(testTollPackage.getActive()).isEqualTo(DEFAULT_ACTIVE);
        assertThat(testTollPackage.getUpdated()).isEqualTo(DEFAULT_UPDATED);
        assertThat(testTollPackage.getDurationInDays()).isEqualTo(DEFAULT_DURATION_IN_DAYS);
        assertThat(testTollPackage.getGantry()).isEqualTo(DEFAULT_GANTRY);
        assertThat(testTollPackage.getVehicleLicenseType()).isEqualTo(DEFAULT_VEHICLE_LICENSE_TYPE);
        assertThat(testTollPackage.getPassageTimes()).isEqualTo(DEFAULT_PASSAGE_TIMES);
        assertThat(testTollPackage.getTotalFees()).isEqualTo(DEFAULT_TOTAL_FEES);
    }

    @Test
    @Transactional
    void createTollPackageWithExistingId() throws Exception {
        // Create the TollPackage with an existing ID
        tollPackage.setId(1L);
        TollPackageDTO tollPackageDTO = tollPackageMapper.toDto(tollPackage);

        int databaseSizeBeforeCreate = tollPackageRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restTollPackageMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(tollPackageDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the TollPackage in the database
        List<TollPackage> tollPackageList = tollPackageRepository.findAll();
        assertThat(tollPackageList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkActiveIsRequired() throws Exception {
        int databaseSizeBeforeTest = tollPackageRepository.findAll().size();
        // set the field null
        tollPackage.setActive(null);

        // Create the TollPackage, which fails.
        TollPackageDTO tollPackageDTO = tollPackageMapper.toDto(tollPackage);

        restTollPackageMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(tollPackageDTO))
            )
            .andExpect(status().isBadRequest());

        List<TollPackage> tollPackageList = tollPackageRepository.findAll();
        assertThat(tollPackageList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkUpdatedIsRequired() throws Exception {
        int databaseSizeBeforeTest = tollPackageRepository.findAll().size();
        // set the field null
        tollPackage.setUpdated(null);

        // Create the TollPackage, which fails.
        TollPackageDTO tollPackageDTO = tollPackageMapper.toDto(tollPackage);

        restTollPackageMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(tollPackageDTO))
            )
            .andExpect(status().isBadRequest());

        List<TollPackage> tollPackageList = tollPackageRepository.findAll();
        assertThat(tollPackageList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkPassageTimesIsRequired() throws Exception {
        int databaseSizeBeforeTest = tollPackageRepository.findAll().size();
        // set the field null
        tollPackage.setPassageTimes(null);

        // Create the TollPackage, which fails.
        TollPackageDTO tollPackageDTO = tollPackageMapper.toDto(tollPackage);

        restTollPackageMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(tollPackageDTO))
            )
            .andExpect(status().isBadRequest());

        List<TollPackage> tollPackageList = tollPackageRepository.findAll();
        assertThat(tollPackageList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkTotalFeesIsRequired() throws Exception {
        int databaseSizeBeforeTest = tollPackageRepository.findAll().size();
        // set the field null
        tollPackage.setTotalFees(null);

        // Create the TollPackage, which fails.
        TollPackageDTO tollPackageDTO = tollPackageMapper.toDto(tollPackage);

        restTollPackageMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(tollPackageDTO))
            )
            .andExpect(status().isBadRequest());

        List<TollPackage> tollPackageList = tollPackageRepository.findAll();
        assertThat(tollPackageList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllTollPackages() throws Exception {
        // Initialize the database
        tollPackageRepository.saveAndFlush(tollPackage);

        // Get all the tollPackageList
        restTollPackageMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(tollPackage.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].engName").value(hasItem(DEFAULT_ENG_NAME)))
            .andExpect(jsonPath("$.[*].code").value(hasItem(DEFAULT_CODE)))
            .andExpect(jsonPath("$.[*].active").value(hasItem(DEFAULT_ACTIVE)))
            .andExpect(jsonPath("$.[*].updated").value(hasItem(DEFAULT_UPDATED.toString())))
            .andExpect(jsonPath("$.[*].durationInDays").value(hasItem(DEFAULT_DURATION_IN_DAYS)))
            .andExpect(jsonPath("$.[*].gantry").value(hasItem(DEFAULT_GANTRY)))
            .andExpect(jsonPath("$.[*].vehicleLicenseType").value(hasItem(DEFAULT_VEHICLE_LICENSE_TYPE)))
            .andExpect(jsonPath("$.[*].passageTimes").value(hasItem(DEFAULT_PASSAGE_TIMES)))
            .andExpect(jsonPath("$.[*].totalFees").value(hasItem(DEFAULT_TOTAL_FEES.doubleValue())));
    }

    @Test
    @Transactional
    void getTollPackage() throws Exception {
        // Initialize the database
        tollPackageRepository.saveAndFlush(tollPackage);

        // Get the tollPackage
        restTollPackageMockMvc
            .perform(get(ENTITY_API_URL_ID, tollPackage.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(tollPackage.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME))
            .andExpect(jsonPath("$.engName").value(DEFAULT_ENG_NAME))
            .andExpect(jsonPath("$.code").value(DEFAULT_CODE))
            .andExpect(jsonPath("$.active").value(DEFAULT_ACTIVE))
            .andExpect(jsonPath("$.updated").value(DEFAULT_UPDATED.toString()))
            .andExpect(jsonPath("$.durationInDays").value(DEFAULT_DURATION_IN_DAYS))
            .andExpect(jsonPath("$.gantry").value(DEFAULT_GANTRY))
            .andExpect(jsonPath("$.vehicleLicenseType").value(DEFAULT_VEHICLE_LICENSE_TYPE))
            .andExpect(jsonPath("$.passageTimes").value(DEFAULT_PASSAGE_TIMES))
            .andExpect(jsonPath("$.totalFees").value(DEFAULT_TOTAL_FEES.doubleValue()));
    }

    @Test
    @Transactional
    void getTollPackagesByIdFiltering() throws Exception {
        // Initialize the database
        tollPackageRepository.saveAndFlush(tollPackage);

        Long id = tollPackage.getId();

        defaultTollPackageShouldBeFound("id.equals=" + id);
        defaultTollPackageShouldNotBeFound("id.notEquals=" + id);

        defaultTollPackageShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultTollPackageShouldNotBeFound("id.greaterThan=" + id);

        defaultTollPackageShouldBeFound("id.lessThanOrEqual=" + id);
        defaultTollPackageShouldNotBeFound("id.lessThan=" + id);
    }

    @Test
    @Transactional
    void getAllTollPackagesByNameIsEqualToSomething() throws Exception {
        // Initialize the database
        tollPackageRepository.saveAndFlush(tollPackage);

        // Get all the tollPackageList where name equals to DEFAULT_NAME
        defaultTollPackageShouldBeFound("name.equals=" + DEFAULT_NAME);

        // Get all the tollPackageList where name equals to UPDATED_NAME
        defaultTollPackageShouldNotBeFound("name.equals=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    void getAllTollPackagesByNameIsInShouldWork() throws Exception {
        // Initialize the database
        tollPackageRepository.saveAndFlush(tollPackage);

        // Get all the tollPackageList where name in DEFAULT_NAME or UPDATED_NAME
        defaultTollPackageShouldBeFound("name.in=" + DEFAULT_NAME + "," + UPDATED_NAME);

        // Get all the tollPackageList where name equals to UPDATED_NAME
        defaultTollPackageShouldNotBeFound("name.in=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    void getAllTollPackagesByNameIsNullOrNotNull() throws Exception {
        // Initialize the database
        tollPackageRepository.saveAndFlush(tollPackage);

        // Get all the tollPackageList where name is not null
        defaultTollPackageShouldBeFound("name.specified=true");

        // Get all the tollPackageList where name is null
        defaultTollPackageShouldNotBeFound("name.specified=false");
    }

    @Test
    @Transactional
    void getAllTollPackagesByNameContainsSomething() throws Exception {
        // Initialize the database
        tollPackageRepository.saveAndFlush(tollPackage);

        // Get all the tollPackageList where name contains DEFAULT_NAME
        defaultTollPackageShouldBeFound("name.contains=" + DEFAULT_NAME);

        // Get all the tollPackageList where name contains UPDATED_NAME
        defaultTollPackageShouldNotBeFound("name.contains=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    void getAllTollPackagesByNameNotContainsSomething() throws Exception {
        // Initialize the database
        tollPackageRepository.saveAndFlush(tollPackage);

        // Get all the tollPackageList where name does not contain DEFAULT_NAME
        defaultTollPackageShouldNotBeFound("name.doesNotContain=" + DEFAULT_NAME);

        // Get all the tollPackageList where name does not contain UPDATED_NAME
        defaultTollPackageShouldBeFound("name.doesNotContain=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    void getAllTollPackagesByEngNameIsEqualToSomething() throws Exception {
        // Initialize the database
        tollPackageRepository.saveAndFlush(tollPackage);

        // Get all the tollPackageList where engName equals to DEFAULT_ENG_NAME
        defaultTollPackageShouldBeFound("engName.equals=" + DEFAULT_ENG_NAME);

        // Get all the tollPackageList where engName equals to UPDATED_ENG_NAME
        defaultTollPackageShouldNotBeFound("engName.equals=" + UPDATED_ENG_NAME);
    }

    @Test
    @Transactional
    void getAllTollPackagesByEngNameIsInShouldWork() throws Exception {
        // Initialize the database
        tollPackageRepository.saveAndFlush(tollPackage);

        // Get all the tollPackageList where engName in DEFAULT_ENG_NAME or UPDATED_ENG_NAME
        defaultTollPackageShouldBeFound("engName.in=" + DEFAULT_ENG_NAME + "," + UPDATED_ENG_NAME);

        // Get all the tollPackageList where engName equals to UPDATED_ENG_NAME
        defaultTollPackageShouldNotBeFound("engName.in=" + UPDATED_ENG_NAME);
    }

    @Test
    @Transactional
    void getAllTollPackagesByEngNameIsNullOrNotNull() throws Exception {
        // Initialize the database
        tollPackageRepository.saveAndFlush(tollPackage);

        // Get all the tollPackageList where engName is not null
        defaultTollPackageShouldBeFound("engName.specified=true");

        // Get all the tollPackageList where engName is null
        defaultTollPackageShouldNotBeFound("engName.specified=false");
    }

    @Test
    @Transactional
    void getAllTollPackagesByEngNameContainsSomething() throws Exception {
        // Initialize the database
        tollPackageRepository.saveAndFlush(tollPackage);

        // Get all the tollPackageList where engName contains DEFAULT_ENG_NAME
        defaultTollPackageShouldBeFound("engName.contains=" + DEFAULT_ENG_NAME);

        // Get all the tollPackageList where engName contains UPDATED_ENG_NAME
        defaultTollPackageShouldNotBeFound("engName.contains=" + UPDATED_ENG_NAME);
    }

    @Test
    @Transactional
    void getAllTollPackagesByEngNameNotContainsSomething() throws Exception {
        // Initialize the database
        tollPackageRepository.saveAndFlush(tollPackage);

        // Get all the tollPackageList where engName does not contain DEFAULT_ENG_NAME
        defaultTollPackageShouldNotBeFound("engName.doesNotContain=" + DEFAULT_ENG_NAME);

        // Get all the tollPackageList where engName does not contain UPDATED_ENG_NAME
        defaultTollPackageShouldBeFound("engName.doesNotContain=" + UPDATED_ENG_NAME);
    }

    @Test
    @Transactional
    void getAllTollPackagesByCodeIsEqualToSomething() throws Exception {
        // Initialize the database
        tollPackageRepository.saveAndFlush(tollPackage);

        // Get all the tollPackageList where code equals to DEFAULT_CODE
        defaultTollPackageShouldBeFound("code.equals=" + DEFAULT_CODE);

        // Get all the tollPackageList where code equals to UPDATED_CODE
        defaultTollPackageShouldNotBeFound("code.equals=" + UPDATED_CODE);
    }

    @Test
    @Transactional
    void getAllTollPackagesByCodeIsInShouldWork() throws Exception {
        // Initialize the database
        tollPackageRepository.saveAndFlush(tollPackage);

        // Get all the tollPackageList where code in DEFAULT_CODE or UPDATED_CODE
        defaultTollPackageShouldBeFound("code.in=" + DEFAULT_CODE + "," + UPDATED_CODE);

        // Get all the tollPackageList where code equals to UPDATED_CODE
        defaultTollPackageShouldNotBeFound("code.in=" + UPDATED_CODE);
    }

    @Test
    @Transactional
    void getAllTollPackagesByCodeIsNullOrNotNull() throws Exception {
        // Initialize the database
        tollPackageRepository.saveAndFlush(tollPackage);

        // Get all the tollPackageList where code is not null
        defaultTollPackageShouldBeFound("code.specified=true");

        // Get all the tollPackageList where code is null
        defaultTollPackageShouldNotBeFound("code.specified=false");
    }

    @Test
    @Transactional
    void getAllTollPackagesByCodeContainsSomething() throws Exception {
        // Initialize the database
        tollPackageRepository.saveAndFlush(tollPackage);

        // Get all the tollPackageList where code contains DEFAULT_CODE
        defaultTollPackageShouldBeFound("code.contains=" + DEFAULT_CODE);

        // Get all the tollPackageList where code contains UPDATED_CODE
        defaultTollPackageShouldNotBeFound("code.contains=" + UPDATED_CODE);
    }

    @Test
    @Transactional
    void getAllTollPackagesByCodeNotContainsSomething() throws Exception {
        // Initialize the database
        tollPackageRepository.saveAndFlush(tollPackage);

        // Get all the tollPackageList where code does not contain DEFAULT_CODE
        defaultTollPackageShouldNotBeFound("code.doesNotContain=" + DEFAULT_CODE);

        // Get all the tollPackageList where code does not contain UPDATED_CODE
        defaultTollPackageShouldBeFound("code.doesNotContain=" + UPDATED_CODE);
    }

    @Test
    @Transactional
    void getAllTollPackagesByActiveIsEqualToSomething() throws Exception {
        // Initialize the database
        tollPackageRepository.saveAndFlush(tollPackage);

        // Get all the tollPackageList where active equals to DEFAULT_ACTIVE
        defaultTollPackageShouldBeFound("active.equals=" + DEFAULT_ACTIVE);

        // Get all the tollPackageList where active equals to UPDATED_ACTIVE
        defaultTollPackageShouldNotBeFound("active.equals=" + UPDATED_ACTIVE);
    }

    @Test
    @Transactional
    void getAllTollPackagesByActiveIsInShouldWork() throws Exception {
        // Initialize the database
        tollPackageRepository.saveAndFlush(tollPackage);

        // Get all the tollPackageList where active in DEFAULT_ACTIVE or UPDATED_ACTIVE
        defaultTollPackageShouldBeFound("active.in=" + DEFAULT_ACTIVE + "," + UPDATED_ACTIVE);

        // Get all the tollPackageList where active equals to UPDATED_ACTIVE
        defaultTollPackageShouldNotBeFound("active.in=" + UPDATED_ACTIVE);
    }

    @Test
    @Transactional
    void getAllTollPackagesByActiveIsNullOrNotNull() throws Exception {
        // Initialize the database
        tollPackageRepository.saveAndFlush(tollPackage);

        // Get all the tollPackageList where active is not null
        defaultTollPackageShouldBeFound("active.specified=true");

        // Get all the tollPackageList where active is null
        defaultTollPackageShouldNotBeFound("active.specified=false");
    }

    @Test
    @Transactional
    void getAllTollPackagesByActiveIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        tollPackageRepository.saveAndFlush(tollPackage);

        // Get all the tollPackageList where active is greater than or equal to DEFAULT_ACTIVE
        defaultTollPackageShouldBeFound("active.greaterThanOrEqual=" + DEFAULT_ACTIVE);

        // Get all the tollPackageList where active is greater than or equal to UPDATED_ACTIVE
        defaultTollPackageShouldNotBeFound("active.greaterThanOrEqual=" + UPDATED_ACTIVE);
    }

    @Test
    @Transactional
    void getAllTollPackagesByActiveIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        tollPackageRepository.saveAndFlush(tollPackage);

        // Get all the tollPackageList where active is less than or equal to DEFAULT_ACTIVE
        defaultTollPackageShouldBeFound("active.lessThanOrEqual=" + DEFAULT_ACTIVE);

        // Get all the tollPackageList where active is less than or equal to SMALLER_ACTIVE
        defaultTollPackageShouldNotBeFound("active.lessThanOrEqual=" + SMALLER_ACTIVE);
    }

    @Test
    @Transactional
    void getAllTollPackagesByActiveIsLessThanSomething() throws Exception {
        // Initialize the database
        tollPackageRepository.saveAndFlush(tollPackage);

        // Get all the tollPackageList where active is less than DEFAULT_ACTIVE
        defaultTollPackageShouldNotBeFound("active.lessThan=" + DEFAULT_ACTIVE);

        // Get all the tollPackageList where active is less than UPDATED_ACTIVE
        defaultTollPackageShouldBeFound("active.lessThan=" + UPDATED_ACTIVE);
    }

    @Test
    @Transactional
    void getAllTollPackagesByActiveIsGreaterThanSomething() throws Exception {
        // Initialize the database
        tollPackageRepository.saveAndFlush(tollPackage);

        // Get all the tollPackageList where active is greater than DEFAULT_ACTIVE
        defaultTollPackageShouldNotBeFound("active.greaterThan=" + DEFAULT_ACTIVE);

        // Get all the tollPackageList where active is greater than SMALLER_ACTIVE
        defaultTollPackageShouldBeFound("active.greaterThan=" + SMALLER_ACTIVE);
    }

    @Test
    @Transactional
    void getAllTollPackagesByUpdatedIsEqualToSomething() throws Exception {
        // Initialize the database
        tollPackageRepository.saveAndFlush(tollPackage);

        // Get all the tollPackageList where updated equals to DEFAULT_UPDATED
        defaultTollPackageShouldBeFound("updated.equals=" + DEFAULT_UPDATED);

        // Get all the tollPackageList where updated equals to UPDATED_UPDATED
        defaultTollPackageShouldNotBeFound("updated.equals=" + UPDATED_UPDATED);
    }

    @Test
    @Transactional
    void getAllTollPackagesByUpdatedIsInShouldWork() throws Exception {
        // Initialize the database
        tollPackageRepository.saveAndFlush(tollPackage);

        // Get all the tollPackageList where updated in DEFAULT_UPDATED or UPDATED_UPDATED
        defaultTollPackageShouldBeFound("updated.in=" + DEFAULT_UPDATED + "," + UPDATED_UPDATED);

        // Get all the tollPackageList where updated equals to UPDATED_UPDATED
        defaultTollPackageShouldNotBeFound("updated.in=" + UPDATED_UPDATED);
    }

    @Test
    @Transactional
    void getAllTollPackagesByUpdatedIsNullOrNotNull() throws Exception {
        // Initialize the database
        tollPackageRepository.saveAndFlush(tollPackage);

        // Get all the tollPackageList where updated is not null
        defaultTollPackageShouldBeFound("updated.specified=true");

        // Get all the tollPackageList where updated is null
        defaultTollPackageShouldNotBeFound("updated.specified=false");
    }

    @Test
    @Transactional
    void getAllTollPackagesByDurationInDaysIsEqualToSomething() throws Exception {
        // Initialize the database
        tollPackageRepository.saveAndFlush(tollPackage);

        // Get all the tollPackageList where durationInDays equals to DEFAULT_DURATION_IN_DAYS
        defaultTollPackageShouldBeFound("durationInDays.equals=" + DEFAULT_DURATION_IN_DAYS);

        // Get all the tollPackageList where durationInDays equals to UPDATED_DURATION_IN_DAYS
        defaultTollPackageShouldNotBeFound("durationInDays.equals=" + UPDATED_DURATION_IN_DAYS);
    }

    @Test
    @Transactional
    void getAllTollPackagesByDurationInDaysIsInShouldWork() throws Exception {
        // Initialize the database
        tollPackageRepository.saveAndFlush(tollPackage);

        // Get all the tollPackageList where durationInDays in DEFAULT_DURATION_IN_DAYS or UPDATED_DURATION_IN_DAYS
        defaultTollPackageShouldBeFound("durationInDays.in=" + DEFAULT_DURATION_IN_DAYS + "," + UPDATED_DURATION_IN_DAYS);

        // Get all the tollPackageList where durationInDays equals to UPDATED_DURATION_IN_DAYS
        defaultTollPackageShouldNotBeFound("durationInDays.in=" + UPDATED_DURATION_IN_DAYS);
    }

    @Test
    @Transactional
    void getAllTollPackagesByDurationInDaysIsNullOrNotNull() throws Exception {
        // Initialize the database
        tollPackageRepository.saveAndFlush(tollPackage);

        // Get all the tollPackageList where durationInDays is not null
        defaultTollPackageShouldBeFound("durationInDays.specified=true");

        // Get all the tollPackageList where durationInDays is null
        defaultTollPackageShouldNotBeFound("durationInDays.specified=false");
    }

    @Test
    @Transactional
    void getAllTollPackagesByDurationInDaysIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        tollPackageRepository.saveAndFlush(tollPackage);

        // Get all the tollPackageList where durationInDays is greater than or equal to DEFAULT_DURATION_IN_DAYS
        defaultTollPackageShouldBeFound("durationInDays.greaterThanOrEqual=" + DEFAULT_DURATION_IN_DAYS);

        // Get all the tollPackageList where durationInDays is greater than or equal to UPDATED_DURATION_IN_DAYS
        defaultTollPackageShouldNotBeFound("durationInDays.greaterThanOrEqual=" + UPDATED_DURATION_IN_DAYS);
    }

    @Test
    @Transactional
    void getAllTollPackagesByDurationInDaysIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        tollPackageRepository.saveAndFlush(tollPackage);

        // Get all the tollPackageList where durationInDays is less than or equal to DEFAULT_DURATION_IN_DAYS
        defaultTollPackageShouldBeFound("durationInDays.lessThanOrEqual=" + DEFAULT_DURATION_IN_DAYS);

        // Get all the tollPackageList where durationInDays is less than or equal to SMALLER_DURATION_IN_DAYS
        defaultTollPackageShouldNotBeFound("durationInDays.lessThanOrEqual=" + SMALLER_DURATION_IN_DAYS);
    }

    @Test
    @Transactional
    void getAllTollPackagesByDurationInDaysIsLessThanSomething() throws Exception {
        // Initialize the database
        tollPackageRepository.saveAndFlush(tollPackage);

        // Get all the tollPackageList where durationInDays is less than DEFAULT_DURATION_IN_DAYS
        defaultTollPackageShouldNotBeFound("durationInDays.lessThan=" + DEFAULT_DURATION_IN_DAYS);

        // Get all the tollPackageList where durationInDays is less than UPDATED_DURATION_IN_DAYS
        defaultTollPackageShouldBeFound("durationInDays.lessThan=" + UPDATED_DURATION_IN_DAYS);
    }

    @Test
    @Transactional
    void getAllTollPackagesByDurationInDaysIsGreaterThanSomething() throws Exception {
        // Initialize the database
        tollPackageRepository.saveAndFlush(tollPackage);

        // Get all the tollPackageList where durationInDays is greater than DEFAULT_DURATION_IN_DAYS
        defaultTollPackageShouldNotBeFound("durationInDays.greaterThan=" + DEFAULT_DURATION_IN_DAYS);

        // Get all the tollPackageList where durationInDays is greater than SMALLER_DURATION_IN_DAYS
        defaultTollPackageShouldBeFound("durationInDays.greaterThan=" + SMALLER_DURATION_IN_DAYS);
    }

    @Test
    @Transactional
    void getAllTollPackagesByGantryIsEqualToSomething() throws Exception {
        // Initialize the database
        tollPackageRepository.saveAndFlush(tollPackage);

        // Get all the tollPackageList where gantry equals to DEFAULT_GANTRY
        defaultTollPackageShouldBeFound("gantry.equals=" + DEFAULT_GANTRY);

        // Get all the tollPackageList where gantry equals to UPDATED_GANTRY
        defaultTollPackageShouldNotBeFound("gantry.equals=" + UPDATED_GANTRY);
    }

    @Test
    @Transactional
    void getAllTollPackagesByGantryIsInShouldWork() throws Exception {
        // Initialize the database
        tollPackageRepository.saveAndFlush(tollPackage);

        // Get all the tollPackageList where gantry in DEFAULT_GANTRY or UPDATED_GANTRY
        defaultTollPackageShouldBeFound("gantry.in=" + DEFAULT_GANTRY + "," + UPDATED_GANTRY);

        // Get all the tollPackageList where gantry equals to UPDATED_GANTRY
        defaultTollPackageShouldNotBeFound("gantry.in=" + UPDATED_GANTRY);
    }

    @Test
    @Transactional
    void getAllTollPackagesByGantryIsNullOrNotNull() throws Exception {
        // Initialize the database
        tollPackageRepository.saveAndFlush(tollPackage);

        // Get all the tollPackageList where gantry is not null
        defaultTollPackageShouldBeFound("gantry.specified=true");

        // Get all the tollPackageList where gantry is null
        defaultTollPackageShouldNotBeFound("gantry.specified=false");
    }

    @Test
    @Transactional
    void getAllTollPackagesByGantryIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        tollPackageRepository.saveAndFlush(tollPackage);

        // Get all the tollPackageList where gantry is greater than or equal to DEFAULT_GANTRY
        defaultTollPackageShouldBeFound("gantry.greaterThanOrEqual=" + DEFAULT_GANTRY);

        // Get all the tollPackageList where gantry is greater than or equal to UPDATED_GANTRY
        defaultTollPackageShouldNotBeFound("gantry.greaterThanOrEqual=" + UPDATED_GANTRY);
    }

    @Test
    @Transactional
    void getAllTollPackagesByGantryIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        tollPackageRepository.saveAndFlush(tollPackage);

        // Get all the tollPackageList where gantry is less than or equal to DEFAULT_GANTRY
        defaultTollPackageShouldBeFound("gantry.lessThanOrEqual=" + DEFAULT_GANTRY);

        // Get all the tollPackageList where gantry is less than or equal to SMALLER_GANTRY
        defaultTollPackageShouldNotBeFound("gantry.lessThanOrEqual=" + SMALLER_GANTRY);
    }

    @Test
    @Transactional
    void getAllTollPackagesByGantryIsLessThanSomething() throws Exception {
        // Initialize the database
        tollPackageRepository.saveAndFlush(tollPackage);

        // Get all the tollPackageList where gantry is less than DEFAULT_GANTRY
        defaultTollPackageShouldNotBeFound("gantry.lessThan=" + DEFAULT_GANTRY);

        // Get all the tollPackageList where gantry is less than UPDATED_GANTRY
        defaultTollPackageShouldBeFound("gantry.lessThan=" + UPDATED_GANTRY);
    }

    @Test
    @Transactional
    void getAllTollPackagesByGantryIsGreaterThanSomething() throws Exception {
        // Initialize the database
        tollPackageRepository.saveAndFlush(tollPackage);

        // Get all the tollPackageList where gantry is greater than DEFAULT_GANTRY
        defaultTollPackageShouldNotBeFound("gantry.greaterThan=" + DEFAULT_GANTRY);

        // Get all the tollPackageList where gantry is greater than SMALLER_GANTRY
        defaultTollPackageShouldBeFound("gantry.greaterThan=" + SMALLER_GANTRY);
    }

    @Test
    @Transactional
    void getAllTollPackagesByVehicleLicenseTypeIsEqualToSomething() throws Exception {
        // Initialize the database
        tollPackageRepository.saveAndFlush(tollPackage);

        // Get all the tollPackageList where vehicleLicenseType equals to DEFAULT_VEHICLE_LICENSE_TYPE
        defaultTollPackageShouldBeFound("vehicleLicenseType.equals=" + DEFAULT_VEHICLE_LICENSE_TYPE);

        // Get all the tollPackageList where vehicleLicenseType equals to UPDATED_VEHICLE_LICENSE_TYPE
        defaultTollPackageShouldNotBeFound("vehicleLicenseType.equals=" + UPDATED_VEHICLE_LICENSE_TYPE);
    }

    @Test
    @Transactional
    void getAllTollPackagesByVehicleLicenseTypeIsInShouldWork() throws Exception {
        // Initialize the database
        tollPackageRepository.saveAndFlush(tollPackage);

        // Get all the tollPackageList where vehicleLicenseType in DEFAULT_VEHICLE_LICENSE_TYPE or UPDATED_VEHICLE_LICENSE_TYPE
        defaultTollPackageShouldBeFound("vehicleLicenseType.in=" + DEFAULT_VEHICLE_LICENSE_TYPE + "," + UPDATED_VEHICLE_LICENSE_TYPE);

        // Get all the tollPackageList where vehicleLicenseType equals to UPDATED_VEHICLE_LICENSE_TYPE
        defaultTollPackageShouldNotBeFound("vehicleLicenseType.in=" + UPDATED_VEHICLE_LICENSE_TYPE);
    }

    @Test
    @Transactional
    void getAllTollPackagesByVehicleLicenseTypeIsNullOrNotNull() throws Exception {
        // Initialize the database
        tollPackageRepository.saveAndFlush(tollPackage);

        // Get all the tollPackageList where vehicleLicenseType is not null
        defaultTollPackageShouldBeFound("vehicleLicenseType.specified=true");

        // Get all the tollPackageList where vehicleLicenseType is null
        defaultTollPackageShouldNotBeFound("vehicleLicenseType.specified=false");
    }

    @Test
    @Transactional
    void getAllTollPackagesByVehicleLicenseTypeIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        tollPackageRepository.saveAndFlush(tollPackage);

        // Get all the tollPackageList where vehicleLicenseType is greater than or equal to DEFAULT_VEHICLE_LICENSE_TYPE
        defaultTollPackageShouldBeFound("vehicleLicenseType.greaterThanOrEqual=" + DEFAULT_VEHICLE_LICENSE_TYPE);

        // Get all the tollPackageList where vehicleLicenseType is greater than or equal to UPDATED_VEHICLE_LICENSE_TYPE
        defaultTollPackageShouldNotBeFound("vehicleLicenseType.greaterThanOrEqual=" + UPDATED_VEHICLE_LICENSE_TYPE);
    }

    @Test
    @Transactional
    void getAllTollPackagesByVehicleLicenseTypeIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        tollPackageRepository.saveAndFlush(tollPackage);

        // Get all the tollPackageList where vehicleLicenseType is less than or equal to DEFAULT_VEHICLE_LICENSE_TYPE
        defaultTollPackageShouldBeFound("vehicleLicenseType.lessThanOrEqual=" + DEFAULT_VEHICLE_LICENSE_TYPE);

        // Get all the tollPackageList where vehicleLicenseType is less than or equal to SMALLER_VEHICLE_LICENSE_TYPE
        defaultTollPackageShouldNotBeFound("vehicleLicenseType.lessThanOrEqual=" + SMALLER_VEHICLE_LICENSE_TYPE);
    }

    @Test
    @Transactional
    void getAllTollPackagesByVehicleLicenseTypeIsLessThanSomething() throws Exception {
        // Initialize the database
        tollPackageRepository.saveAndFlush(tollPackage);

        // Get all the tollPackageList where vehicleLicenseType is less than DEFAULT_VEHICLE_LICENSE_TYPE
        defaultTollPackageShouldNotBeFound("vehicleLicenseType.lessThan=" + DEFAULT_VEHICLE_LICENSE_TYPE);

        // Get all the tollPackageList where vehicleLicenseType is less than UPDATED_VEHICLE_LICENSE_TYPE
        defaultTollPackageShouldBeFound("vehicleLicenseType.lessThan=" + UPDATED_VEHICLE_LICENSE_TYPE);
    }

    @Test
    @Transactional
    void getAllTollPackagesByVehicleLicenseTypeIsGreaterThanSomething() throws Exception {
        // Initialize the database
        tollPackageRepository.saveAndFlush(tollPackage);

        // Get all the tollPackageList where vehicleLicenseType is greater than DEFAULT_VEHICLE_LICENSE_TYPE
        defaultTollPackageShouldNotBeFound("vehicleLicenseType.greaterThan=" + DEFAULT_VEHICLE_LICENSE_TYPE);

        // Get all the tollPackageList where vehicleLicenseType is greater than SMALLER_VEHICLE_LICENSE_TYPE
        defaultTollPackageShouldBeFound("vehicleLicenseType.greaterThan=" + SMALLER_VEHICLE_LICENSE_TYPE);
    }

    @Test
    @Transactional
    void getAllTollPackagesByPassageTimesIsEqualToSomething() throws Exception {
        // Initialize the database
        tollPackageRepository.saveAndFlush(tollPackage);

        // Get all the tollPackageList where passageTimes equals to DEFAULT_PASSAGE_TIMES
        defaultTollPackageShouldBeFound("passageTimes.equals=" + DEFAULT_PASSAGE_TIMES);

        // Get all the tollPackageList where passageTimes equals to UPDATED_PASSAGE_TIMES
        defaultTollPackageShouldNotBeFound("passageTimes.equals=" + UPDATED_PASSAGE_TIMES);
    }

    @Test
    @Transactional
    void getAllTollPackagesByPassageTimesIsInShouldWork() throws Exception {
        // Initialize the database
        tollPackageRepository.saveAndFlush(tollPackage);

        // Get all the tollPackageList where passageTimes in DEFAULT_PASSAGE_TIMES or UPDATED_PASSAGE_TIMES
        defaultTollPackageShouldBeFound("passageTimes.in=" + DEFAULT_PASSAGE_TIMES + "," + UPDATED_PASSAGE_TIMES);

        // Get all the tollPackageList where passageTimes equals to UPDATED_PASSAGE_TIMES
        defaultTollPackageShouldNotBeFound("passageTimes.in=" + UPDATED_PASSAGE_TIMES);
    }

    @Test
    @Transactional
    void getAllTollPackagesByPassageTimesIsNullOrNotNull() throws Exception {
        // Initialize the database
        tollPackageRepository.saveAndFlush(tollPackage);

        // Get all the tollPackageList where passageTimes is not null
        defaultTollPackageShouldBeFound("passageTimes.specified=true");

        // Get all the tollPackageList where passageTimes is null
        defaultTollPackageShouldNotBeFound("passageTimes.specified=false");
    }

    @Test
    @Transactional
    void getAllTollPackagesByPassageTimesIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        tollPackageRepository.saveAndFlush(tollPackage);

        // Get all the tollPackageList where passageTimes is greater than or equal to DEFAULT_PASSAGE_TIMES
        defaultTollPackageShouldBeFound("passageTimes.greaterThanOrEqual=" + DEFAULT_PASSAGE_TIMES);

        // Get all the tollPackageList where passageTimes is greater than or equal to UPDATED_PASSAGE_TIMES
        defaultTollPackageShouldNotBeFound("passageTimes.greaterThanOrEqual=" + UPDATED_PASSAGE_TIMES);
    }

    @Test
    @Transactional
    void getAllTollPackagesByPassageTimesIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        tollPackageRepository.saveAndFlush(tollPackage);

        // Get all the tollPackageList where passageTimes is less than or equal to DEFAULT_PASSAGE_TIMES
        defaultTollPackageShouldBeFound("passageTimes.lessThanOrEqual=" + DEFAULT_PASSAGE_TIMES);

        // Get all the tollPackageList where passageTimes is less than or equal to SMALLER_PASSAGE_TIMES
        defaultTollPackageShouldNotBeFound("passageTimes.lessThanOrEqual=" + SMALLER_PASSAGE_TIMES);
    }

    @Test
    @Transactional
    void getAllTollPackagesByPassageTimesIsLessThanSomething() throws Exception {
        // Initialize the database
        tollPackageRepository.saveAndFlush(tollPackage);

        // Get all the tollPackageList where passageTimes is less than DEFAULT_PASSAGE_TIMES
        defaultTollPackageShouldNotBeFound("passageTimes.lessThan=" + DEFAULT_PASSAGE_TIMES);

        // Get all the tollPackageList where passageTimes is less than UPDATED_PASSAGE_TIMES
        defaultTollPackageShouldBeFound("passageTimes.lessThan=" + UPDATED_PASSAGE_TIMES);
    }

    @Test
    @Transactional
    void getAllTollPackagesByPassageTimesIsGreaterThanSomething() throws Exception {
        // Initialize the database
        tollPackageRepository.saveAndFlush(tollPackage);

        // Get all the tollPackageList where passageTimes is greater than DEFAULT_PASSAGE_TIMES
        defaultTollPackageShouldNotBeFound("passageTimes.greaterThan=" + DEFAULT_PASSAGE_TIMES);

        // Get all the tollPackageList where passageTimes is greater than SMALLER_PASSAGE_TIMES
        defaultTollPackageShouldBeFound("passageTimes.greaterThan=" + SMALLER_PASSAGE_TIMES);
    }

    @Test
    @Transactional
    void getAllTollPackagesByTotalFeesIsEqualToSomething() throws Exception {
        // Initialize the database
        tollPackageRepository.saveAndFlush(tollPackage);

        // Get all the tollPackageList where totalFees equals to DEFAULT_TOTAL_FEES
        defaultTollPackageShouldBeFound("totalFees.equals=" + DEFAULT_TOTAL_FEES);

        // Get all the tollPackageList where totalFees equals to UPDATED_TOTAL_FEES
        defaultTollPackageShouldNotBeFound("totalFees.equals=" + UPDATED_TOTAL_FEES);
    }

    @Test
    @Transactional
    void getAllTollPackagesByTotalFeesIsInShouldWork() throws Exception {
        // Initialize the database
        tollPackageRepository.saveAndFlush(tollPackage);

        // Get all the tollPackageList where totalFees in DEFAULT_TOTAL_FEES or UPDATED_TOTAL_FEES
        defaultTollPackageShouldBeFound("totalFees.in=" + DEFAULT_TOTAL_FEES + "," + UPDATED_TOTAL_FEES);

        // Get all the tollPackageList where totalFees equals to UPDATED_TOTAL_FEES
        defaultTollPackageShouldNotBeFound("totalFees.in=" + UPDATED_TOTAL_FEES);
    }

    @Test
    @Transactional
    void getAllTollPackagesByTotalFeesIsNullOrNotNull() throws Exception {
        // Initialize the database
        tollPackageRepository.saveAndFlush(tollPackage);

        // Get all the tollPackageList where totalFees is not null
        defaultTollPackageShouldBeFound("totalFees.specified=true");

        // Get all the tollPackageList where totalFees is null
        defaultTollPackageShouldNotBeFound("totalFees.specified=false");
    }

    @Test
    @Transactional
    void getAllTollPackagesByTotalFeesIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        tollPackageRepository.saveAndFlush(tollPackage);

        // Get all the tollPackageList where totalFees is greater than or equal to DEFAULT_TOTAL_FEES
        defaultTollPackageShouldBeFound("totalFees.greaterThanOrEqual=" + DEFAULT_TOTAL_FEES);

        // Get all the tollPackageList where totalFees is greater than or equal to UPDATED_TOTAL_FEES
        defaultTollPackageShouldNotBeFound("totalFees.greaterThanOrEqual=" + UPDATED_TOTAL_FEES);
    }

    @Test
    @Transactional
    void getAllTollPackagesByTotalFeesIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        tollPackageRepository.saveAndFlush(tollPackage);

        // Get all the tollPackageList where totalFees is less than or equal to DEFAULT_TOTAL_FEES
        defaultTollPackageShouldBeFound("totalFees.lessThanOrEqual=" + DEFAULT_TOTAL_FEES);

        // Get all the tollPackageList where totalFees is less than or equal to SMALLER_TOTAL_FEES
        defaultTollPackageShouldNotBeFound("totalFees.lessThanOrEqual=" + SMALLER_TOTAL_FEES);
    }

    @Test
    @Transactional
    void getAllTollPackagesByTotalFeesIsLessThanSomething() throws Exception {
        // Initialize the database
        tollPackageRepository.saveAndFlush(tollPackage);

        // Get all the tollPackageList where totalFees is less than DEFAULT_TOTAL_FEES
        defaultTollPackageShouldNotBeFound("totalFees.lessThan=" + DEFAULT_TOTAL_FEES);

        // Get all the tollPackageList where totalFees is less than UPDATED_TOTAL_FEES
        defaultTollPackageShouldBeFound("totalFees.lessThan=" + UPDATED_TOTAL_FEES);
    }

    @Test
    @Transactional
    void getAllTollPackagesByTotalFeesIsGreaterThanSomething() throws Exception {
        // Initialize the database
        tollPackageRepository.saveAndFlush(tollPackage);

        // Get all the tollPackageList where totalFees is greater than DEFAULT_TOTAL_FEES
        defaultTollPackageShouldNotBeFound("totalFees.greaterThan=" + DEFAULT_TOTAL_FEES);

        // Get all the tollPackageList where totalFees is greater than SMALLER_TOTAL_FEES
        defaultTollPackageShouldBeFound("totalFees.greaterThan=" + SMALLER_TOTAL_FEES);
    }

    @Test
    @Transactional
    void getAllTollPackagesByTollSubscriptionIsEqualToSomething() throws Exception {
        TollSubscription tollSubscription;
        if (TestUtil.findAll(em, TollSubscription.class).isEmpty()) {
            tollPackageRepository.saveAndFlush(tollPackage);
            tollSubscription = TollSubscriptionResourceIT.createEntity(em);
        } else {
            tollSubscription = TestUtil.findAll(em, TollSubscription.class).get(0);
        }
        em.persist(tollSubscription);
        em.flush();
        tollPackage.setTollSubscription(tollSubscription);
        tollPackageRepository.saveAndFlush(tollPackage);
        Long tollSubscriptionId = tollSubscription.getId();

        // Get all the tollPackageList where tollSubscription equals to tollSubscriptionId
        defaultTollPackageShouldBeFound("tollSubscriptionId.equals=" + tollSubscriptionId);

        // Get all the tollPackageList where tollSubscription equals to (tollSubscriptionId + 1)
        defaultTollPackageShouldNotBeFound("tollSubscriptionId.equals=" + (tollSubscriptionId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultTollPackageShouldBeFound(String filter) throws Exception {
        restTollPackageMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(tollPackage.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].engName").value(hasItem(DEFAULT_ENG_NAME)))
            .andExpect(jsonPath("$.[*].code").value(hasItem(DEFAULT_CODE)))
            .andExpect(jsonPath("$.[*].active").value(hasItem(DEFAULT_ACTIVE)))
            .andExpect(jsonPath("$.[*].updated").value(hasItem(DEFAULT_UPDATED.toString())))
            .andExpect(jsonPath("$.[*].durationInDays").value(hasItem(DEFAULT_DURATION_IN_DAYS)))
            .andExpect(jsonPath("$.[*].gantry").value(hasItem(DEFAULT_GANTRY)))
            .andExpect(jsonPath("$.[*].vehicleLicenseType").value(hasItem(DEFAULT_VEHICLE_LICENSE_TYPE)))
            .andExpect(jsonPath("$.[*].passageTimes").value(hasItem(DEFAULT_PASSAGE_TIMES)))
            .andExpect(jsonPath("$.[*].totalFees").value(hasItem(DEFAULT_TOTAL_FEES.doubleValue())));

        // Check, that the count call also returns 1
        restTollPackageMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultTollPackageShouldNotBeFound(String filter) throws Exception {
        restTollPackageMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restTollPackageMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    void getNonExistingTollPackage() throws Exception {
        // Get the tollPackage
        restTollPackageMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingTollPackage() throws Exception {
        // Initialize the database
        tollPackageRepository.saveAndFlush(tollPackage);

        int databaseSizeBeforeUpdate = tollPackageRepository.findAll().size();

        // Update the tollPackage
        TollPackage updatedTollPackage = tollPackageRepository.findById(tollPackage.getId()).get();
        // Disconnect from session so that the updates on updatedTollPackage are not directly saved in db
        em.detach(updatedTollPackage);
        updatedTollPackage
            .name(UPDATED_NAME)
            .engName(UPDATED_ENG_NAME)
            .code(UPDATED_CODE)
            .active(UPDATED_ACTIVE)
            .updated(UPDATED_UPDATED)
            .durationInDays(UPDATED_DURATION_IN_DAYS)
            .gantry(UPDATED_GANTRY)
            .vehicleLicenseType(UPDATED_VEHICLE_LICENSE_TYPE)
            .passageTimes(UPDATED_PASSAGE_TIMES)
            .totalFees(UPDATED_TOTAL_FEES);
        TollPackageDTO tollPackageDTO = tollPackageMapper.toDto(updatedTollPackage);

        restTollPackageMockMvc
            .perform(
                put(ENTITY_API_URL_ID, tollPackageDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(tollPackageDTO))
            )
            .andExpect(status().isOk());

        // Validate the TollPackage in the database
        List<TollPackage> tollPackageList = tollPackageRepository.findAll();
        assertThat(tollPackageList).hasSize(databaseSizeBeforeUpdate);
        TollPackage testTollPackage = tollPackageList.get(tollPackageList.size() - 1);
        assertThat(testTollPackage.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testTollPackage.getEngName()).isEqualTo(UPDATED_ENG_NAME);
        assertThat(testTollPackage.getCode()).isEqualTo(UPDATED_CODE);
        assertThat(testTollPackage.getActive()).isEqualTo(UPDATED_ACTIVE);
        assertThat(testTollPackage.getUpdated()).isEqualTo(UPDATED_UPDATED);
        assertThat(testTollPackage.getDurationInDays()).isEqualTo(UPDATED_DURATION_IN_DAYS);
        assertThat(testTollPackage.getGantry()).isEqualTo(UPDATED_GANTRY);
        assertThat(testTollPackage.getVehicleLicenseType()).isEqualTo(UPDATED_VEHICLE_LICENSE_TYPE);
        assertThat(testTollPackage.getPassageTimes()).isEqualTo(UPDATED_PASSAGE_TIMES);
        assertThat(testTollPackage.getTotalFees()).isEqualTo(UPDATED_TOTAL_FEES);
    }

    @Test
    @Transactional
    void putNonExistingTollPackage() throws Exception {
        int databaseSizeBeforeUpdate = tollPackageRepository.findAll().size();
        tollPackage.setId(count.incrementAndGet());

        // Create the TollPackage
        TollPackageDTO tollPackageDTO = tollPackageMapper.toDto(tollPackage);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restTollPackageMockMvc
            .perform(
                put(ENTITY_API_URL_ID, tollPackageDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(tollPackageDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the TollPackage in the database
        List<TollPackage> tollPackageList = tollPackageRepository.findAll();
        assertThat(tollPackageList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchTollPackage() throws Exception {
        int databaseSizeBeforeUpdate = tollPackageRepository.findAll().size();
        tollPackage.setId(count.incrementAndGet());

        // Create the TollPackage
        TollPackageDTO tollPackageDTO = tollPackageMapper.toDto(tollPackage);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restTollPackageMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(tollPackageDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the TollPackage in the database
        List<TollPackage> tollPackageList = tollPackageRepository.findAll();
        assertThat(tollPackageList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamTollPackage() throws Exception {
        int databaseSizeBeforeUpdate = tollPackageRepository.findAll().size();
        tollPackage.setId(count.incrementAndGet());

        // Create the TollPackage
        TollPackageDTO tollPackageDTO = tollPackageMapper.toDto(tollPackage);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restTollPackageMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(tollPackageDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the TollPackage in the database
        List<TollPackage> tollPackageList = tollPackageRepository.findAll();
        assertThat(tollPackageList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateTollPackageWithPatch() throws Exception {
        // Initialize the database
        tollPackageRepository.saveAndFlush(tollPackage);

        int databaseSizeBeforeUpdate = tollPackageRepository.findAll().size();

        // Update the tollPackage using partial update
        TollPackage partialUpdatedTollPackage = new TollPackage();
        partialUpdatedTollPackage.setId(tollPackage.getId());

        partialUpdatedTollPackage
            .code(UPDATED_CODE)
            .active(UPDATED_ACTIVE)
            .vehicleLicenseType(UPDATED_VEHICLE_LICENSE_TYPE)
            .totalFees(UPDATED_TOTAL_FEES);

        restTollPackageMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedTollPackage.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedTollPackage))
            )
            .andExpect(status().isOk());

        // Validate the TollPackage in the database
        List<TollPackage> tollPackageList = tollPackageRepository.findAll();
        assertThat(tollPackageList).hasSize(databaseSizeBeforeUpdate);
        TollPackage testTollPackage = tollPackageList.get(tollPackageList.size() - 1);
        assertThat(testTollPackage.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testTollPackage.getEngName()).isEqualTo(DEFAULT_ENG_NAME);
        assertThat(testTollPackage.getCode()).isEqualTo(UPDATED_CODE);
        assertThat(testTollPackage.getActive()).isEqualTo(UPDATED_ACTIVE);
        assertThat(testTollPackage.getUpdated()).isEqualTo(DEFAULT_UPDATED);
        assertThat(testTollPackage.getDurationInDays()).isEqualTo(DEFAULT_DURATION_IN_DAYS);
        assertThat(testTollPackage.getGantry()).isEqualTo(DEFAULT_GANTRY);
        assertThat(testTollPackage.getVehicleLicenseType()).isEqualTo(UPDATED_VEHICLE_LICENSE_TYPE);
        assertThat(testTollPackage.getPassageTimes()).isEqualTo(DEFAULT_PASSAGE_TIMES);
        assertThat(testTollPackage.getTotalFees()).isEqualTo(UPDATED_TOTAL_FEES);
    }

    @Test
    @Transactional
    void fullUpdateTollPackageWithPatch() throws Exception {
        // Initialize the database
        tollPackageRepository.saveAndFlush(tollPackage);

        int databaseSizeBeforeUpdate = tollPackageRepository.findAll().size();

        // Update the tollPackage using partial update
        TollPackage partialUpdatedTollPackage = new TollPackage();
        partialUpdatedTollPackage.setId(tollPackage.getId());

        partialUpdatedTollPackage
            .name(UPDATED_NAME)
            .engName(UPDATED_ENG_NAME)
            .code(UPDATED_CODE)
            .active(UPDATED_ACTIVE)
            .updated(UPDATED_UPDATED)
            .durationInDays(UPDATED_DURATION_IN_DAYS)
            .gantry(UPDATED_GANTRY)
            .vehicleLicenseType(UPDATED_VEHICLE_LICENSE_TYPE)
            .passageTimes(UPDATED_PASSAGE_TIMES)
            .totalFees(UPDATED_TOTAL_FEES);

        restTollPackageMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedTollPackage.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedTollPackage))
            )
            .andExpect(status().isOk());

        // Validate the TollPackage in the database
        List<TollPackage> tollPackageList = tollPackageRepository.findAll();
        assertThat(tollPackageList).hasSize(databaseSizeBeforeUpdate);
        TollPackage testTollPackage = tollPackageList.get(tollPackageList.size() - 1);
        assertThat(testTollPackage.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testTollPackage.getEngName()).isEqualTo(UPDATED_ENG_NAME);
        assertThat(testTollPackage.getCode()).isEqualTo(UPDATED_CODE);
        assertThat(testTollPackage.getActive()).isEqualTo(UPDATED_ACTIVE);
        assertThat(testTollPackage.getUpdated()).isEqualTo(UPDATED_UPDATED);
        assertThat(testTollPackage.getDurationInDays()).isEqualTo(UPDATED_DURATION_IN_DAYS);
        assertThat(testTollPackage.getGantry()).isEqualTo(UPDATED_GANTRY);
        assertThat(testTollPackage.getVehicleLicenseType()).isEqualTo(UPDATED_VEHICLE_LICENSE_TYPE);
        assertThat(testTollPackage.getPassageTimes()).isEqualTo(UPDATED_PASSAGE_TIMES);
        assertThat(testTollPackage.getTotalFees()).isEqualTo(UPDATED_TOTAL_FEES);
    }

    @Test
    @Transactional
    void patchNonExistingTollPackage() throws Exception {
        int databaseSizeBeforeUpdate = tollPackageRepository.findAll().size();
        tollPackage.setId(count.incrementAndGet());

        // Create the TollPackage
        TollPackageDTO tollPackageDTO = tollPackageMapper.toDto(tollPackage);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restTollPackageMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, tollPackageDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(tollPackageDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the TollPackage in the database
        List<TollPackage> tollPackageList = tollPackageRepository.findAll();
        assertThat(tollPackageList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchTollPackage() throws Exception {
        int databaseSizeBeforeUpdate = tollPackageRepository.findAll().size();
        tollPackage.setId(count.incrementAndGet());

        // Create the TollPackage
        TollPackageDTO tollPackageDTO = tollPackageMapper.toDto(tollPackage);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restTollPackageMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(tollPackageDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the TollPackage in the database
        List<TollPackage> tollPackageList = tollPackageRepository.findAll();
        assertThat(tollPackageList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamTollPackage() throws Exception {
        int databaseSizeBeforeUpdate = tollPackageRepository.findAll().size();
        tollPackage.setId(count.incrementAndGet());

        // Create the TollPackage
        TollPackageDTO tollPackageDTO = tollPackageMapper.toDto(tollPackage);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restTollPackageMockMvc
            .perform(
                patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(tollPackageDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the TollPackage in the database
        List<TollPackage> tollPackageList = tollPackageRepository.findAll();
        assertThat(tollPackageList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteTollPackage() throws Exception {
        // Initialize the database
        tollPackageRepository.saveAndFlush(tollPackage);

        int databaseSizeBeforeDelete = tollPackageRepository.findAll().size();

        // Delete the tollPackage
        restTollPackageMockMvc
            .perform(delete(ENTITY_API_URL_ID, tollPackage.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<TollPackage> tollPackageList = tollPackageRepository.findAll();
        assertThat(tollPackageList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
