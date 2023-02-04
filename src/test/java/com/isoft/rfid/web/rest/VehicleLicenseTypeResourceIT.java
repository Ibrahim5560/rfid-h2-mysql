package com.isoft.rfid.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.isoft.rfid.IntegrationTest;
import com.isoft.rfid.domain.VehicleLicenseType;
import com.isoft.rfid.repository.VehicleLicenseTypeRepository;
import com.isoft.rfid.service.criteria.VehicleLicenseTypeCriteria;
import com.isoft.rfid.service.dto.VehicleLicenseTypeDTO;
import com.isoft.rfid.service.mapper.VehicleLicenseTypeMapper;
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
 * Integration tests for the {@link VehicleLicenseTypeResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class VehicleLicenseTypeResourceIT {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final Integer DEFAULT_RANK = 1;
    private static final Integer UPDATED_RANK = 2;
    private static final Integer SMALLER_RANK = 1 - 1;

    private static final String DEFAULT_ENG_NAME = "AAAAAAAAAA";
    private static final String UPDATED_ENG_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_CODE = "AAAAAAAAAA";
    private static final String UPDATED_CODE = "BBBBBBBBBB";

    private static final Instant DEFAULT_UPDATED = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_UPDATED = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Integer DEFAULT_ACTIVE = 1;
    private static final Integer UPDATED_ACTIVE = 2;
    private static final Integer SMALLER_ACTIVE = 1 - 1;

    private static final Double DEFAULT_GANTRY_TOLL = 1D;
    private static final Double UPDATED_GANTRY_TOLL = 2D;
    private static final Double SMALLER_GANTRY_TOLL = 1D - 1D;

    private static final String ENTITY_API_URL = "/api/vehicle-license-types";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private VehicleLicenseTypeRepository vehicleLicenseTypeRepository;

    @Autowired
    private VehicleLicenseTypeMapper vehicleLicenseTypeMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restVehicleLicenseTypeMockMvc;

    private VehicleLicenseType vehicleLicenseType;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static VehicleLicenseType createEntity(EntityManager em) {
        VehicleLicenseType vehicleLicenseType = new VehicleLicenseType()
            .name(DEFAULT_NAME)
            .rank(DEFAULT_RANK)
            .engName(DEFAULT_ENG_NAME)
            .code(DEFAULT_CODE)
            .updated(DEFAULT_UPDATED)
            .active(DEFAULT_ACTIVE)
            .gantryToll(DEFAULT_GANTRY_TOLL);
        return vehicleLicenseType;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static VehicleLicenseType createUpdatedEntity(EntityManager em) {
        VehicleLicenseType vehicleLicenseType = new VehicleLicenseType()
            .name(UPDATED_NAME)
            .rank(UPDATED_RANK)
            .engName(UPDATED_ENG_NAME)
            .code(UPDATED_CODE)
            .updated(UPDATED_UPDATED)
            .active(UPDATED_ACTIVE)
            .gantryToll(UPDATED_GANTRY_TOLL);
        return vehicleLicenseType;
    }

    @BeforeEach
    public void initTest() {
        vehicleLicenseType = createEntity(em);
    }

    @Test
    @Transactional
    void createVehicleLicenseType() throws Exception {
        int databaseSizeBeforeCreate = vehicleLicenseTypeRepository.findAll().size();
        // Create the VehicleLicenseType
        VehicleLicenseTypeDTO vehicleLicenseTypeDTO = vehicleLicenseTypeMapper.toDto(vehicleLicenseType);
        restVehicleLicenseTypeMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(vehicleLicenseTypeDTO))
            )
            .andExpect(status().isCreated());

        // Validate the VehicleLicenseType in the database
        List<VehicleLicenseType> vehicleLicenseTypeList = vehicleLicenseTypeRepository.findAll();
        assertThat(vehicleLicenseTypeList).hasSize(databaseSizeBeforeCreate + 1);
        VehicleLicenseType testVehicleLicenseType = vehicleLicenseTypeList.get(vehicleLicenseTypeList.size() - 1);
        assertThat(testVehicleLicenseType.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testVehicleLicenseType.getRank()).isEqualTo(DEFAULT_RANK);
        assertThat(testVehicleLicenseType.getEngName()).isEqualTo(DEFAULT_ENG_NAME);
        assertThat(testVehicleLicenseType.getCode()).isEqualTo(DEFAULT_CODE);
        assertThat(testVehicleLicenseType.getUpdated()).isEqualTo(DEFAULT_UPDATED);
        assertThat(testVehicleLicenseType.getActive()).isEqualTo(DEFAULT_ACTIVE);
        assertThat(testVehicleLicenseType.getGantryToll()).isEqualTo(DEFAULT_GANTRY_TOLL);
    }

    @Test
    @Transactional
    void createVehicleLicenseTypeWithExistingId() throws Exception {
        // Create the VehicleLicenseType with an existing ID
        vehicleLicenseType.setId(1L);
        VehicleLicenseTypeDTO vehicleLicenseTypeDTO = vehicleLicenseTypeMapper.toDto(vehicleLicenseType);

        int databaseSizeBeforeCreate = vehicleLicenseTypeRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restVehicleLicenseTypeMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(vehicleLicenseTypeDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the VehicleLicenseType in the database
        List<VehicleLicenseType> vehicleLicenseTypeList = vehicleLicenseTypeRepository.findAll();
        assertThat(vehicleLicenseTypeList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkActiveIsRequired() throws Exception {
        int databaseSizeBeforeTest = vehicleLicenseTypeRepository.findAll().size();
        // set the field null
        vehicleLicenseType.setActive(null);

        // Create the VehicleLicenseType, which fails.
        VehicleLicenseTypeDTO vehicleLicenseTypeDTO = vehicleLicenseTypeMapper.toDto(vehicleLicenseType);

        restVehicleLicenseTypeMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(vehicleLicenseTypeDTO))
            )
            .andExpect(status().isBadRequest());

        List<VehicleLicenseType> vehicleLicenseTypeList = vehicleLicenseTypeRepository.findAll();
        assertThat(vehicleLicenseTypeList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkGantryTollIsRequired() throws Exception {
        int databaseSizeBeforeTest = vehicleLicenseTypeRepository.findAll().size();
        // set the field null
        vehicleLicenseType.setGantryToll(null);

        // Create the VehicleLicenseType, which fails.
        VehicleLicenseTypeDTO vehicleLicenseTypeDTO = vehicleLicenseTypeMapper.toDto(vehicleLicenseType);

        restVehicleLicenseTypeMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(vehicleLicenseTypeDTO))
            )
            .andExpect(status().isBadRequest());

        List<VehicleLicenseType> vehicleLicenseTypeList = vehicleLicenseTypeRepository.findAll();
        assertThat(vehicleLicenseTypeList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllVehicleLicenseTypes() throws Exception {
        // Initialize the database
        vehicleLicenseTypeRepository.saveAndFlush(vehicleLicenseType);

        // Get all the vehicleLicenseTypeList
        restVehicleLicenseTypeMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(vehicleLicenseType.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].rank").value(hasItem(DEFAULT_RANK)))
            .andExpect(jsonPath("$.[*].engName").value(hasItem(DEFAULT_ENG_NAME)))
            .andExpect(jsonPath("$.[*].code").value(hasItem(DEFAULT_CODE)))
            .andExpect(jsonPath("$.[*].updated").value(hasItem(DEFAULT_UPDATED.toString())))
            .andExpect(jsonPath("$.[*].active").value(hasItem(DEFAULT_ACTIVE)))
            .andExpect(jsonPath("$.[*].gantryToll").value(hasItem(DEFAULT_GANTRY_TOLL.doubleValue())));
    }

    @Test
    @Transactional
    void getVehicleLicenseType() throws Exception {
        // Initialize the database
        vehicleLicenseTypeRepository.saveAndFlush(vehicleLicenseType);

        // Get the vehicleLicenseType
        restVehicleLicenseTypeMockMvc
            .perform(get(ENTITY_API_URL_ID, vehicleLicenseType.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(vehicleLicenseType.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME))
            .andExpect(jsonPath("$.rank").value(DEFAULT_RANK))
            .andExpect(jsonPath("$.engName").value(DEFAULT_ENG_NAME))
            .andExpect(jsonPath("$.code").value(DEFAULT_CODE))
            .andExpect(jsonPath("$.updated").value(DEFAULT_UPDATED.toString()))
            .andExpect(jsonPath("$.active").value(DEFAULT_ACTIVE))
            .andExpect(jsonPath("$.gantryToll").value(DEFAULT_GANTRY_TOLL.doubleValue()));
    }

    @Test
    @Transactional
    void getVehicleLicenseTypesByIdFiltering() throws Exception {
        // Initialize the database
        vehicleLicenseTypeRepository.saveAndFlush(vehicleLicenseType);

        Long id = vehicleLicenseType.getId();

        defaultVehicleLicenseTypeShouldBeFound("id.equals=" + id);
        defaultVehicleLicenseTypeShouldNotBeFound("id.notEquals=" + id);

        defaultVehicleLicenseTypeShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultVehicleLicenseTypeShouldNotBeFound("id.greaterThan=" + id);

        defaultVehicleLicenseTypeShouldBeFound("id.lessThanOrEqual=" + id);
        defaultVehicleLicenseTypeShouldNotBeFound("id.lessThan=" + id);
    }

    @Test
    @Transactional
    void getAllVehicleLicenseTypesByNameIsEqualToSomething() throws Exception {
        // Initialize the database
        vehicleLicenseTypeRepository.saveAndFlush(vehicleLicenseType);

        // Get all the vehicleLicenseTypeList where name equals to DEFAULT_NAME
        defaultVehicleLicenseTypeShouldBeFound("name.equals=" + DEFAULT_NAME);

        // Get all the vehicleLicenseTypeList where name equals to UPDATED_NAME
        defaultVehicleLicenseTypeShouldNotBeFound("name.equals=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    void getAllVehicleLicenseTypesByNameIsInShouldWork() throws Exception {
        // Initialize the database
        vehicleLicenseTypeRepository.saveAndFlush(vehicleLicenseType);

        // Get all the vehicleLicenseTypeList where name in DEFAULT_NAME or UPDATED_NAME
        defaultVehicleLicenseTypeShouldBeFound("name.in=" + DEFAULT_NAME + "," + UPDATED_NAME);

        // Get all the vehicleLicenseTypeList where name equals to UPDATED_NAME
        defaultVehicleLicenseTypeShouldNotBeFound("name.in=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    void getAllVehicleLicenseTypesByNameIsNullOrNotNull() throws Exception {
        // Initialize the database
        vehicleLicenseTypeRepository.saveAndFlush(vehicleLicenseType);

        // Get all the vehicleLicenseTypeList where name is not null
        defaultVehicleLicenseTypeShouldBeFound("name.specified=true");

        // Get all the vehicleLicenseTypeList where name is null
        defaultVehicleLicenseTypeShouldNotBeFound("name.specified=false");
    }

    @Test
    @Transactional
    void getAllVehicleLicenseTypesByNameContainsSomething() throws Exception {
        // Initialize the database
        vehicleLicenseTypeRepository.saveAndFlush(vehicleLicenseType);

        // Get all the vehicleLicenseTypeList where name contains DEFAULT_NAME
        defaultVehicleLicenseTypeShouldBeFound("name.contains=" + DEFAULT_NAME);

        // Get all the vehicleLicenseTypeList where name contains UPDATED_NAME
        defaultVehicleLicenseTypeShouldNotBeFound("name.contains=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    void getAllVehicleLicenseTypesByNameNotContainsSomething() throws Exception {
        // Initialize the database
        vehicleLicenseTypeRepository.saveAndFlush(vehicleLicenseType);

        // Get all the vehicleLicenseTypeList where name does not contain DEFAULT_NAME
        defaultVehicleLicenseTypeShouldNotBeFound("name.doesNotContain=" + DEFAULT_NAME);

        // Get all the vehicleLicenseTypeList where name does not contain UPDATED_NAME
        defaultVehicleLicenseTypeShouldBeFound("name.doesNotContain=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    void getAllVehicleLicenseTypesByRankIsEqualToSomething() throws Exception {
        // Initialize the database
        vehicleLicenseTypeRepository.saveAndFlush(vehicleLicenseType);

        // Get all the vehicleLicenseTypeList where rank equals to DEFAULT_RANK
        defaultVehicleLicenseTypeShouldBeFound("rank.equals=" + DEFAULT_RANK);

        // Get all the vehicleLicenseTypeList where rank equals to UPDATED_RANK
        defaultVehicleLicenseTypeShouldNotBeFound("rank.equals=" + UPDATED_RANK);
    }

    @Test
    @Transactional
    void getAllVehicleLicenseTypesByRankIsInShouldWork() throws Exception {
        // Initialize the database
        vehicleLicenseTypeRepository.saveAndFlush(vehicleLicenseType);

        // Get all the vehicleLicenseTypeList where rank in DEFAULT_RANK or UPDATED_RANK
        defaultVehicleLicenseTypeShouldBeFound("rank.in=" + DEFAULT_RANK + "," + UPDATED_RANK);

        // Get all the vehicleLicenseTypeList where rank equals to UPDATED_RANK
        defaultVehicleLicenseTypeShouldNotBeFound("rank.in=" + UPDATED_RANK);
    }

    @Test
    @Transactional
    void getAllVehicleLicenseTypesByRankIsNullOrNotNull() throws Exception {
        // Initialize the database
        vehicleLicenseTypeRepository.saveAndFlush(vehicleLicenseType);

        // Get all the vehicleLicenseTypeList where rank is not null
        defaultVehicleLicenseTypeShouldBeFound("rank.specified=true");

        // Get all the vehicleLicenseTypeList where rank is null
        defaultVehicleLicenseTypeShouldNotBeFound("rank.specified=false");
    }

    @Test
    @Transactional
    void getAllVehicleLicenseTypesByRankIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        vehicleLicenseTypeRepository.saveAndFlush(vehicleLicenseType);

        // Get all the vehicleLicenseTypeList where rank is greater than or equal to DEFAULT_RANK
        defaultVehicleLicenseTypeShouldBeFound("rank.greaterThanOrEqual=" + DEFAULT_RANK);

        // Get all the vehicleLicenseTypeList where rank is greater than or equal to UPDATED_RANK
        defaultVehicleLicenseTypeShouldNotBeFound("rank.greaterThanOrEqual=" + UPDATED_RANK);
    }

    @Test
    @Transactional
    void getAllVehicleLicenseTypesByRankIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        vehicleLicenseTypeRepository.saveAndFlush(vehicleLicenseType);

        // Get all the vehicleLicenseTypeList where rank is less than or equal to DEFAULT_RANK
        defaultVehicleLicenseTypeShouldBeFound("rank.lessThanOrEqual=" + DEFAULT_RANK);

        // Get all the vehicleLicenseTypeList where rank is less than or equal to SMALLER_RANK
        defaultVehicleLicenseTypeShouldNotBeFound("rank.lessThanOrEqual=" + SMALLER_RANK);
    }

    @Test
    @Transactional
    void getAllVehicleLicenseTypesByRankIsLessThanSomething() throws Exception {
        // Initialize the database
        vehicleLicenseTypeRepository.saveAndFlush(vehicleLicenseType);

        // Get all the vehicleLicenseTypeList where rank is less than DEFAULT_RANK
        defaultVehicleLicenseTypeShouldNotBeFound("rank.lessThan=" + DEFAULT_RANK);

        // Get all the vehicleLicenseTypeList where rank is less than UPDATED_RANK
        defaultVehicleLicenseTypeShouldBeFound("rank.lessThan=" + UPDATED_RANK);
    }

    @Test
    @Transactional
    void getAllVehicleLicenseTypesByRankIsGreaterThanSomething() throws Exception {
        // Initialize the database
        vehicleLicenseTypeRepository.saveAndFlush(vehicleLicenseType);

        // Get all the vehicleLicenseTypeList where rank is greater than DEFAULT_RANK
        defaultVehicleLicenseTypeShouldNotBeFound("rank.greaterThan=" + DEFAULT_RANK);

        // Get all the vehicleLicenseTypeList where rank is greater than SMALLER_RANK
        defaultVehicleLicenseTypeShouldBeFound("rank.greaterThan=" + SMALLER_RANK);
    }

    @Test
    @Transactional
    void getAllVehicleLicenseTypesByEngNameIsEqualToSomething() throws Exception {
        // Initialize the database
        vehicleLicenseTypeRepository.saveAndFlush(vehicleLicenseType);

        // Get all the vehicleLicenseTypeList where engName equals to DEFAULT_ENG_NAME
        defaultVehicleLicenseTypeShouldBeFound("engName.equals=" + DEFAULT_ENG_NAME);

        // Get all the vehicleLicenseTypeList where engName equals to UPDATED_ENG_NAME
        defaultVehicleLicenseTypeShouldNotBeFound("engName.equals=" + UPDATED_ENG_NAME);
    }

    @Test
    @Transactional
    void getAllVehicleLicenseTypesByEngNameIsInShouldWork() throws Exception {
        // Initialize the database
        vehicleLicenseTypeRepository.saveAndFlush(vehicleLicenseType);

        // Get all the vehicleLicenseTypeList where engName in DEFAULT_ENG_NAME or UPDATED_ENG_NAME
        defaultVehicleLicenseTypeShouldBeFound("engName.in=" + DEFAULT_ENG_NAME + "," + UPDATED_ENG_NAME);

        // Get all the vehicleLicenseTypeList where engName equals to UPDATED_ENG_NAME
        defaultVehicleLicenseTypeShouldNotBeFound("engName.in=" + UPDATED_ENG_NAME);
    }

    @Test
    @Transactional
    void getAllVehicleLicenseTypesByEngNameIsNullOrNotNull() throws Exception {
        // Initialize the database
        vehicleLicenseTypeRepository.saveAndFlush(vehicleLicenseType);

        // Get all the vehicleLicenseTypeList where engName is not null
        defaultVehicleLicenseTypeShouldBeFound("engName.specified=true");

        // Get all the vehicleLicenseTypeList where engName is null
        defaultVehicleLicenseTypeShouldNotBeFound("engName.specified=false");
    }

    @Test
    @Transactional
    void getAllVehicleLicenseTypesByEngNameContainsSomething() throws Exception {
        // Initialize the database
        vehicleLicenseTypeRepository.saveAndFlush(vehicleLicenseType);

        // Get all the vehicleLicenseTypeList where engName contains DEFAULT_ENG_NAME
        defaultVehicleLicenseTypeShouldBeFound("engName.contains=" + DEFAULT_ENG_NAME);

        // Get all the vehicleLicenseTypeList where engName contains UPDATED_ENG_NAME
        defaultVehicleLicenseTypeShouldNotBeFound("engName.contains=" + UPDATED_ENG_NAME);
    }

    @Test
    @Transactional
    void getAllVehicleLicenseTypesByEngNameNotContainsSomething() throws Exception {
        // Initialize the database
        vehicleLicenseTypeRepository.saveAndFlush(vehicleLicenseType);

        // Get all the vehicleLicenseTypeList where engName does not contain DEFAULT_ENG_NAME
        defaultVehicleLicenseTypeShouldNotBeFound("engName.doesNotContain=" + DEFAULT_ENG_NAME);

        // Get all the vehicleLicenseTypeList where engName does not contain UPDATED_ENG_NAME
        defaultVehicleLicenseTypeShouldBeFound("engName.doesNotContain=" + UPDATED_ENG_NAME);
    }

    @Test
    @Transactional
    void getAllVehicleLicenseTypesByCodeIsEqualToSomething() throws Exception {
        // Initialize the database
        vehicleLicenseTypeRepository.saveAndFlush(vehicleLicenseType);

        // Get all the vehicleLicenseTypeList where code equals to DEFAULT_CODE
        defaultVehicleLicenseTypeShouldBeFound("code.equals=" + DEFAULT_CODE);

        // Get all the vehicleLicenseTypeList where code equals to UPDATED_CODE
        defaultVehicleLicenseTypeShouldNotBeFound("code.equals=" + UPDATED_CODE);
    }

    @Test
    @Transactional
    void getAllVehicleLicenseTypesByCodeIsInShouldWork() throws Exception {
        // Initialize the database
        vehicleLicenseTypeRepository.saveAndFlush(vehicleLicenseType);

        // Get all the vehicleLicenseTypeList where code in DEFAULT_CODE or UPDATED_CODE
        defaultVehicleLicenseTypeShouldBeFound("code.in=" + DEFAULT_CODE + "," + UPDATED_CODE);

        // Get all the vehicleLicenseTypeList where code equals to UPDATED_CODE
        defaultVehicleLicenseTypeShouldNotBeFound("code.in=" + UPDATED_CODE);
    }

    @Test
    @Transactional
    void getAllVehicleLicenseTypesByCodeIsNullOrNotNull() throws Exception {
        // Initialize the database
        vehicleLicenseTypeRepository.saveAndFlush(vehicleLicenseType);

        // Get all the vehicleLicenseTypeList where code is not null
        defaultVehicleLicenseTypeShouldBeFound("code.specified=true");

        // Get all the vehicleLicenseTypeList where code is null
        defaultVehicleLicenseTypeShouldNotBeFound("code.specified=false");
    }

    @Test
    @Transactional
    void getAllVehicleLicenseTypesByCodeContainsSomething() throws Exception {
        // Initialize the database
        vehicleLicenseTypeRepository.saveAndFlush(vehicleLicenseType);

        // Get all the vehicleLicenseTypeList where code contains DEFAULT_CODE
        defaultVehicleLicenseTypeShouldBeFound("code.contains=" + DEFAULT_CODE);

        // Get all the vehicleLicenseTypeList where code contains UPDATED_CODE
        defaultVehicleLicenseTypeShouldNotBeFound("code.contains=" + UPDATED_CODE);
    }

    @Test
    @Transactional
    void getAllVehicleLicenseTypesByCodeNotContainsSomething() throws Exception {
        // Initialize the database
        vehicleLicenseTypeRepository.saveAndFlush(vehicleLicenseType);

        // Get all the vehicleLicenseTypeList where code does not contain DEFAULT_CODE
        defaultVehicleLicenseTypeShouldNotBeFound("code.doesNotContain=" + DEFAULT_CODE);

        // Get all the vehicleLicenseTypeList where code does not contain UPDATED_CODE
        defaultVehicleLicenseTypeShouldBeFound("code.doesNotContain=" + UPDATED_CODE);
    }

    @Test
    @Transactional
    void getAllVehicleLicenseTypesByUpdatedIsEqualToSomething() throws Exception {
        // Initialize the database
        vehicleLicenseTypeRepository.saveAndFlush(vehicleLicenseType);

        // Get all the vehicleLicenseTypeList where updated equals to DEFAULT_UPDATED
        defaultVehicleLicenseTypeShouldBeFound("updated.equals=" + DEFAULT_UPDATED);

        // Get all the vehicleLicenseTypeList where updated equals to UPDATED_UPDATED
        defaultVehicleLicenseTypeShouldNotBeFound("updated.equals=" + UPDATED_UPDATED);
    }

    @Test
    @Transactional
    void getAllVehicleLicenseTypesByUpdatedIsInShouldWork() throws Exception {
        // Initialize the database
        vehicleLicenseTypeRepository.saveAndFlush(vehicleLicenseType);

        // Get all the vehicleLicenseTypeList where updated in DEFAULT_UPDATED or UPDATED_UPDATED
        defaultVehicleLicenseTypeShouldBeFound("updated.in=" + DEFAULT_UPDATED + "," + UPDATED_UPDATED);

        // Get all the vehicleLicenseTypeList where updated equals to UPDATED_UPDATED
        defaultVehicleLicenseTypeShouldNotBeFound("updated.in=" + UPDATED_UPDATED);
    }

    @Test
    @Transactional
    void getAllVehicleLicenseTypesByUpdatedIsNullOrNotNull() throws Exception {
        // Initialize the database
        vehicleLicenseTypeRepository.saveAndFlush(vehicleLicenseType);

        // Get all the vehicleLicenseTypeList where updated is not null
        defaultVehicleLicenseTypeShouldBeFound("updated.specified=true");

        // Get all the vehicleLicenseTypeList where updated is null
        defaultVehicleLicenseTypeShouldNotBeFound("updated.specified=false");
    }

    @Test
    @Transactional
    void getAllVehicleLicenseTypesByActiveIsEqualToSomething() throws Exception {
        // Initialize the database
        vehicleLicenseTypeRepository.saveAndFlush(vehicleLicenseType);

        // Get all the vehicleLicenseTypeList where active equals to DEFAULT_ACTIVE
        defaultVehicleLicenseTypeShouldBeFound("active.equals=" + DEFAULT_ACTIVE);

        // Get all the vehicleLicenseTypeList where active equals to UPDATED_ACTIVE
        defaultVehicleLicenseTypeShouldNotBeFound("active.equals=" + UPDATED_ACTIVE);
    }

    @Test
    @Transactional
    void getAllVehicleLicenseTypesByActiveIsInShouldWork() throws Exception {
        // Initialize the database
        vehicleLicenseTypeRepository.saveAndFlush(vehicleLicenseType);

        // Get all the vehicleLicenseTypeList where active in DEFAULT_ACTIVE or UPDATED_ACTIVE
        defaultVehicleLicenseTypeShouldBeFound("active.in=" + DEFAULT_ACTIVE + "," + UPDATED_ACTIVE);

        // Get all the vehicleLicenseTypeList where active equals to UPDATED_ACTIVE
        defaultVehicleLicenseTypeShouldNotBeFound("active.in=" + UPDATED_ACTIVE);
    }

    @Test
    @Transactional
    void getAllVehicleLicenseTypesByActiveIsNullOrNotNull() throws Exception {
        // Initialize the database
        vehicleLicenseTypeRepository.saveAndFlush(vehicleLicenseType);

        // Get all the vehicleLicenseTypeList where active is not null
        defaultVehicleLicenseTypeShouldBeFound("active.specified=true");

        // Get all the vehicleLicenseTypeList where active is null
        defaultVehicleLicenseTypeShouldNotBeFound("active.specified=false");
    }

    @Test
    @Transactional
    void getAllVehicleLicenseTypesByActiveIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        vehicleLicenseTypeRepository.saveAndFlush(vehicleLicenseType);

        // Get all the vehicleLicenseTypeList where active is greater than or equal to DEFAULT_ACTIVE
        defaultVehicleLicenseTypeShouldBeFound("active.greaterThanOrEqual=" + DEFAULT_ACTIVE);

        // Get all the vehicleLicenseTypeList where active is greater than or equal to UPDATED_ACTIVE
        defaultVehicleLicenseTypeShouldNotBeFound("active.greaterThanOrEqual=" + UPDATED_ACTIVE);
    }

    @Test
    @Transactional
    void getAllVehicleLicenseTypesByActiveIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        vehicleLicenseTypeRepository.saveAndFlush(vehicleLicenseType);

        // Get all the vehicleLicenseTypeList where active is less than or equal to DEFAULT_ACTIVE
        defaultVehicleLicenseTypeShouldBeFound("active.lessThanOrEqual=" + DEFAULT_ACTIVE);

        // Get all the vehicleLicenseTypeList where active is less than or equal to SMALLER_ACTIVE
        defaultVehicleLicenseTypeShouldNotBeFound("active.lessThanOrEqual=" + SMALLER_ACTIVE);
    }

    @Test
    @Transactional
    void getAllVehicleLicenseTypesByActiveIsLessThanSomething() throws Exception {
        // Initialize the database
        vehicleLicenseTypeRepository.saveAndFlush(vehicleLicenseType);

        // Get all the vehicleLicenseTypeList where active is less than DEFAULT_ACTIVE
        defaultVehicleLicenseTypeShouldNotBeFound("active.lessThan=" + DEFAULT_ACTIVE);

        // Get all the vehicleLicenseTypeList where active is less than UPDATED_ACTIVE
        defaultVehicleLicenseTypeShouldBeFound("active.lessThan=" + UPDATED_ACTIVE);
    }

    @Test
    @Transactional
    void getAllVehicleLicenseTypesByActiveIsGreaterThanSomething() throws Exception {
        // Initialize the database
        vehicleLicenseTypeRepository.saveAndFlush(vehicleLicenseType);

        // Get all the vehicleLicenseTypeList where active is greater than DEFAULT_ACTIVE
        defaultVehicleLicenseTypeShouldNotBeFound("active.greaterThan=" + DEFAULT_ACTIVE);

        // Get all the vehicleLicenseTypeList where active is greater than SMALLER_ACTIVE
        defaultVehicleLicenseTypeShouldBeFound("active.greaterThan=" + SMALLER_ACTIVE);
    }

    @Test
    @Transactional
    void getAllVehicleLicenseTypesByGantryTollIsEqualToSomething() throws Exception {
        // Initialize the database
        vehicleLicenseTypeRepository.saveAndFlush(vehicleLicenseType);

        // Get all the vehicleLicenseTypeList where gantryToll equals to DEFAULT_GANTRY_TOLL
        defaultVehicleLicenseTypeShouldBeFound("gantryToll.equals=" + DEFAULT_GANTRY_TOLL);

        // Get all the vehicleLicenseTypeList where gantryToll equals to UPDATED_GANTRY_TOLL
        defaultVehicleLicenseTypeShouldNotBeFound("gantryToll.equals=" + UPDATED_GANTRY_TOLL);
    }

    @Test
    @Transactional
    void getAllVehicleLicenseTypesByGantryTollIsInShouldWork() throws Exception {
        // Initialize the database
        vehicleLicenseTypeRepository.saveAndFlush(vehicleLicenseType);

        // Get all the vehicleLicenseTypeList where gantryToll in DEFAULT_GANTRY_TOLL or UPDATED_GANTRY_TOLL
        defaultVehicleLicenseTypeShouldBeFound("gantryToll.in=" + DEFAULT_GANTRY_TOLL + "," + UPDATED_GANTRY_TOLL);

        // Get all the vehicleLicenseTypeList where gantryToll equals to UPDATED_GANTRY_TOLL
        defaultVehicleLicenseTypeShouldNotBeFound("gantryToll.in=" + UPDATED_GANTRY_TOLL);
    }

    @Test
    @Transactional
    void getAllVehicleLicenseTypesByGantryTollIsNullOrNotNull() throws Exception {
        // Initialize the database
        vehicleLicenseTypeRepository.saveAndFlush(vehicleLicenseType);

        // Get all the vehicleLicenseTypeList where gantryToll is not null
        defaultVehicleLicenseTypeShouldBeFound("gantryToll.specified=true");

        // Get all the vehicleLicenseTypeList where gantryToll is null
        defaultVehicleLicenseTypeShouldNotBeFound("gantryToll.specified=false");
    }

    @Test
    @Transactional
    void getAllVehicleLicenseTypesByGantryTollIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        vehicleLicenseTypeRepository.saveAndFlush(vehicleLicenseType);

        // Get all the vehicleLicenseTypeList where gantryToll is greater than or equal to DEFAULT_GANTRY_TOLL
        defaultVehicleLicenseTypeShouldBeFound("gantryToll.greaterThanOrEqual=" + DEFAULT_GANTRY_TOLL);

        // Get all the vehicleLicenseTypeList where gantryToll is greater than or equal to UPDATED_GANTRY_TOLL
        defaultVehicleLicenseTypeShouldNotBeFound("gantryToll.greaterThanOrEqual=" + UPDATED_GANTRY_TOLL);
    }

    @Test
    @Transactional
    void getAllVehicleLicenseTypesByGantryTollIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        vehicleLicenseTypeRepository.saveAndFlush(vehicleLicenseType);

        // Get all the vehicleLicenseTypeList where gantryToll is less than or equal to DEFAULT_GANTRY_TOLL
        defaultVehicleLicenseTypeShouldBeFound("gantryToll.lessThanOrEqual=" + DEFAULT_GANTRY_TOLL);

        // Get all the vehicleLicenseTypeList where gantryToll is less than or equal to SMALLER_GANTRY_TOLL
        defaultVehicleLicenseTypeShouldNotBeFound("gantryToll.lessThanOrEqual=" + SMALLER_GANTRY_TOLL);
    }

    @Test
    @Transactional
    void getAllVehicleLicenseTypesByGantryTollIsLessThanSomething() throws Exception {
        // Initialize the database
        vehicleLicenseTypeRepository.saveAndFlush(vehicleLicenseType);

        // Get all the vehicleLicenseTypeList where gantryToll is less than DEFAULT_GANTRY_TOLL
        defaultVehicleLicenseTypeShouldNotBeFound("gantryToll.lessThan=" + DEFAULT_GANTRY_TOLL);

        // Get all the vehicleLicenseTypeList where gantryToll is less than UPDATED_GANTRY_TOLL
        defaultVehicleLicenseTypeShouldBeFound("gantryToll.lessThan=" + UPDATED_GANTRY_TOLL);
    }

    @Test
    @Transactional
    void getAllVehicleLicenseTypesByGantryTollIsGreaterThanSomething() throws Exception {
        // Initialize the database
        vehicleLicenseTypeRepository.saveAndFlush(vehicleLicenseType);

        // Get all the vehicleLicenseTypeList where gantryToll is greater than DEFAULT_GANTRY_TOLL
        defaultVehicleLicenseTypeShouldNotBeFound("gantryToll.greaterThan=" + DEFAULT_GANTRY_TOLL);

        // Get all the vehicleLicenseTypeList where gantryToll is greater than SMALLER_GANTRY_TOLL
        defaultVehicleLicenseTypeShouldBeFound("gantryToll.greaterThan=" + SMALLER_GANTRY_TOLL);
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultVehicleLicenseTypeShouldBeFound(String filter) throws Exception {
        restVehicleLicenseTypeMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(vehicleLicenseType.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].rank").value(hasItem(DEFAULT_RANK)))
            .andExpect(jsonPath("$.[*].engName").value(hasItem(DEFAULT_ENG_NAME)))
            .andExpect(jsonPath("$.[*].code").value(hasItem(DEFAULT_CODE)))
            .andExpect(jsonPath("$.[*].updated").value(hasItem(DEFAULT_UPDATED.toString())))
            .andExpect(jsonPath("$.[*].active").value(hasItem(DEFAULT_ACTIVE)))
            .andExpect(jsonPath("$.[*].gantryToll").value(hasItem(DEFAULT_GANTRY_TOLL.doubleValue())));

        // Check, that the count call also returns 1
        restVehicleLicenseTypeMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultVehicleLicenseTypeShouldNotBeFound(String filter) throws Exception {
        restVehicleLicenseTypeMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restVehicleLicenseTypeMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    void getNonExistingVehicleLicenseType() throws Exception {
        // Get the vehicleLicenseType
        restVehicleLicenseTypeMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingVehicleLicenseType() throws Exception {
        // Initialize the database
        vehicleLicenseTypeRepository.saveAndFlush(vehicleLicenseType);

        int databaseSizeBeforeUpdate = vehicleLicenseTypeRepository.findAll().size();

        // Update the vehicleLicenseType
        VehicleLicenseType updatedVehicleLicenseType = vehicleLicenseTypeRepository.findById(vehicleLicenseType.getId()).get();
        // Disconnect from session so that the updates on updatedVehicleLicenseType are not directly saved in db
        em.detach(updatedVehicleLicenseType);
        updatedVehicleLicenseType
            .name(UPDATED_NAME)
            .rank(UPDATED_RANK)
            .engName(UPDATED_ENG_NAME)
            .code(UPDATED_CODE)
            .updated(UPDATED_UPDATED)
            .active(UPDATED_ACTIVE)
            .gantryToll(UPDATED_GANTRY_TOLL);
        VehicleLicenseTypeDTO vehicleLicenseTypeDTO = vehicleLicenseTypeMapper.toDto(updatedVehicleLicenseType);

        restVehicleLicenseTypeMockMvc
            .perform(
                put(ENTITY_API_URL_ID, vehicleLicenseTypeDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(vehicleLicenseTypeDTO))
            )
            .andExpect(status().isOk());

        // Validate the VehicleLicenseType in the database
        List<VehicleLicenseType> vehicleLicenseTypeList = vehicleLicenseTypeRepository.findAll();
        assertThat(vehicleLicenseTypeList).hasSize(databaseSizeBeforeUpdate);
        VehicleLicenseType testVehicleLicenseType = vehicleLicenseTypeList.get(vehicleLicenseTypeList.size() - 1);
        assertThat(testVehicleLicenseType.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testVehicleLicenseType.getRank()).isEqualTo(UPDATED_RANK);
        assertThat(testVehicleLicenseType.getEngName()).isEqualTo(UPDATED_ENG_NAME);
        assertThat(testVehicleLicenseType.getCode()).isEqualTo(UPDATED_CODE);
        assertThat(testVehicleLicenseType.getUpdated()).isEqualTo(UPDATED_UPDATED);
        assertThat(testVehicleLicenseType.getActive()).isEqualTo(UPDATED_ACTIVE);
        assertThat(testVehicleLicenseType.getGantryToll()).isEqualTo(UPDATED_GANTRY_TOLL);
    }

    @Test
    @Transactional
    void putNonExistingVehicleLicenseType() throws Exception {
        int databaseSizeBeforeUpdate = vehicleLicenseTypeRepository.findAll().size();
        vehicleLicenseType.setId(count.incrementAndGet());

        // Create the VehicleLicenseType
        VehicleLicenseTypeDTO vehicleLicenseTypeDTO = vehicleLicenseTypeMapper.toDto(vehicleLicenseType);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restVehicleLicenseTypeMockMvc
            .perform(
                put(ENTITY_API_URL_ID, vehicleLicenseTypeDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(vehicleLicenseTypeDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the VehicleLicenseType in the database
        List<VehicleLicenseType> vehicleLicenseTypeList = vehicleLicenseTypeRepository.findAll();
        assertThat(vehicleLicenseTypeList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchVehicleLicenseType() throws Exception {
        int databaseSizeBeforeUpdate = vehicleLicenseTypeRepository.findAll().size();
        vehicleLicenseType.setId(count.incrementAndGet());

        // Create the VehicleLicenseType
        VehicleLicenseTypeDTO vehicleLicenseTypeDTO = vehicleLicenseTypeMapper.toDto(vehicleLicenseType);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restVehicleLicenseTypeMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(vehicleLicenseTypeDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the VehicleLicenseType in the database
        List<VehicleLicenseType> vehicleLicenseTypeList = vehicleLicenseTypeRepository.findAll();
        assertThat(vehicleLicenseTypeList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamVehicleLicenseType() throws Exception {
        int databaseSizeBeforeUpdate = vehicleLicenseTypeRepository.findAll().size();
        vehicleLicenseType.setId(count.incrementAndGet());

        // Create the VehicleLicenseType
        VehicleLicenseTypeDTO vehicleLicenseTypeDTO = vehicleLicenseTypeMapper.toDto(vehicleLicenseType);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restVehicleLicenseTypeMockMvc
            .perform(
                put(ENTITY_API_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(vehicleLicenseTypeDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the VehicleLicenseType in the database
        List<VehicleLicenseType> vehicleLicenseTypeList = vehicleLicenseTypeRepository.findAll();
        assertThat(vehicleLicenseTypeList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateVehicleLicenseTypeWithPatch() throws Exception {
        // Initialize the database
        vehicleLicenseTypeRepository.saveAndFlush(vehicleLicenseType);

        int databaseSizeBeforeUpdate = vehicleLicenseTypeRepository.findAll().size();

        // Update the vehicleLicenseType using partial update
        VehicleLicenseType partialUpdatedVehicleLicenseType = new VehicleLicenseType();
        partialUpdatedVehicleLicenseType.setId(vehicleLicenseType.getId());

        partialUpdatedVehicleLicenseType.name(UPDATED_NAME).code(UPDATED_CODE).updated(UPDATED_UPDATED).gantryToll(UPDATED_GANTRY_TOLL);

        restVehicleLicenseTypeMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedVehicleLicenseType.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedVehicleLicenseType))
            )
            .andExpect(status().isOk());

        // Validate the VehicleLicenseType in the database
        List<VehicleLicenseType> vehicleLicenseTypeList = vehicleLicenseTypeRepository.findAll();
        assertThat(vehicleLicenseTypeList).hasSize(databaseSizeBeforeUpdate);
        VehicleLicenseType testVehicleLicenseType = vehicleLicenseTypeList.get(vehicleLicenseTypeList.size() - 1);
        assertThat(testVehicleLicenseType.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testVehicleLicenseType.getRank()).isEqualTo(DEFAULT_RANK);
        assertThat(testVehicleLicenseType.getEngName()).isEqualTo(DEFAULT_ENG_NAME);
        assertThat(testVehicleLicenseType.getCode()).isEqualTo(UPDATED_CODE);
        assertThat(testVehicleLicenseType.getUpdated()).isEqualTo(UPDATED_UPDATED);
        assertThat(testVehicleLicenseType.getActive()).isEqualTo(DEFAULT_ACTIVE);
        assertThat(testVehicleLicenseType.getGantryToll()).isEqualTo(UPDATED_GANTRY_TOLL);
    }

    @Test
    @Transactional
    void fullUpdateVehicleLicenseTypeWithPatch() throws Exception {
        // Initialize the database
        vehicleLicenseTypeRepository.saveAndFlush(vehicleLicenseType);

        int databaseSizeBeforeUpdate = vehicleLicenseTypeRepository.findAll().size();

        // Update the vehicleLicenseType using partial update
        VehicleLicenseType partialUpdatedVehicleLicenseType = new VehicleLicenseType();
        partialUpdatedVehicleLicenseType.setId(vehicleLicenseType.getId());

        partialUpdatedVehicleLicenseType
            .name(UPDATED_NAME)
            .rank(UPDATED_RANK)
            .engName(UPDATED_ENG_NAME)
            .code(UPDATED_CODE)
            .updated(UPDATED_UPDATED)
            .active(UPDATED_ACTIVE)
            .gantryToll(UPDATED_GANTRY_TOLL);

        restVehicleLicenseTypeMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedVehicleLicenseType.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedVehicleLicenseType))
            )
            .andExpect(status().isOk());

        // Validate the VehicleLicenseType in the database
        List<VehicleLicenseType> vehicleLicenseTypeList = vehicleLicenseTypeRepository.findAll();
        assertThat(vehicleLicenseTypeList).hasSize(databaseSizeBeforeUpdate);
        VehicleLicenseType testVehicleLicenseType = vehicleLicenseTypeList.get(vehicleLicenseTypeList.size() - 1);
        assertThat(testVehicleLicenseType.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testVehicleLicenseType.getRank()).isEqualTo(UPDATED_RANK);
        assertThat(testVehicleLicenseType.getEngName()).isEqualTo(UPDATED_ENG_NAME);
        assertThat(testVehicleLicenseType.getCode()).isEqualTo(UPDATED_CODE);
        assertThat(testVehicleLicenseType.getUpdated()).isEqualTo(UPDATED_UPDATED);
        assertThat(testVehicleLicenseType.getActive()).isEqualTo(UPDATED_ACTIVE);
        assertThat(testVehicleLicenseType.getGantryToll()).isEqualTo(UPDATED_GANTRY_TOLL);
    }

    @Test
    @Transactional
    void patchNonExistingVehicleLicenseType() throws Exception {
        int databaseSizeBeforeUpdate = vehicleLicenseTypeRepository.findAll().size();
        vehicleLicenseType.setId(count.incrementAndGet());

        // Create the VehicleLicenseType
        VehicleLicenseTypeDTO vehicleLicenseTypeDTO = vehicleLicenseTypeMapper.toDto(vehicleLicenseType);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restVehicleLicenseTypeMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, vehicleLicenseTypeDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(vehicleLicenseTypeDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the VehicleLicenseType in the database
        List<VehicleLicenseType> vehicleLicenseTypeList = vehicleLicenseTypeRepository.findAll();
        assertThat(vehicleLicenseTypeList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchVehicleLicenseType() throws Exception {
        int databaseSizeBeforeUpdate = vehicleLicenseTypeRepository.findAll().size();
        vehicleLicenseType.setId(count.incrementAndGet());

        // Create the VehicleLicenseType
        VehicleLicenseTypeDTO vehicleLicenseTypeDTO = vehicleLicenseTypeMapper.toDto(vehicleLicenseType);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restVehicleLicenseTypeMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(vehicleLicenseTypeDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the VehicleLicenseType in the database
        List<VehicleLicenseType> vehicleLicenseTypeList = vehicleLicenseTypeRepository.findAll();
        assertThat(vehicleLicenseTypeList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamVehicleLicenseType() throws Exception {
        int databaseSizeBeforeUpdate = vehicleLicenseTypeRepository.findAll().size();
        vehicleLicenseType.setId(count.incrementAndGet());

        // Create the VehicleLicenseType
        VehicleLicenseTypeDTO vehicleLicenseTypeDTO = vehicleLicenseTypeMapper.toDto(vehicleLicenseType);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restVehicleLicenseTypeMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(vehicleLicenseTypeDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the VehicleLicenseType in the database
        List<VehicleLicenseType> vehicleLicenseTypeList = vehicleLicenseTypeRepository.findAll();
        assertThat(vehicleLicenseTypeList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteVehicleLicenseType() throws Exception {
        // Initialize the database
        vehicleLicenseTypeRepository.saveAndFlush(vehicleLicenseType);

        int databaseSizeBeforeDelete = vehicleLicenseTypeRepository.findAll().size();

        // Delete the vehicleLicenseType
        restVehicleLicenseTypeMockMvc
            .perform(delete(ENTITY_API_URL_ID, vehicleLicenseType.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<VehicleLicenseType> vehicleLicenseTypeList = vehicleLicenseTypeRepository.findAll();
        assertThat(vehicleLicenseTypeList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
