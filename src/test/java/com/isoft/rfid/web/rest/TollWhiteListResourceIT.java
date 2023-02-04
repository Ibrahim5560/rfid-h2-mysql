package com.isoft.rfid.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.isoft.rfid.IntegrationTest;
import com.isoft.rfid.domain.TollWhiteList;
import com.isoft.rfid.repository.TollWhiteListRepository;
import com.isoft.rfid.service.criteria.TollWhiteListCriteria;
import com.isoft.rfid.service.dto.TollWhiteListDTO;
import com.isoft.rfid.service.mapper.TollWhiteListMapper;
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
 * Integration tests for the {@link TollWhiteListResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class TollWhiteListResourceIT {

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

    private static final Integer DEFAULT_GANTRY = 1;
    private static final Integer UPDATED_GANTRY = 2;
    private static final Integer SMALLER_GANTRY = 1 - 1;

    private static final Integer DEFAULT_VEHICLE_LICENSE_TYPE = 1;
    private static final Integer UPDATED_VEHICLE_LICENSE_TYPE = 2;
    private static final Integer SMALLER_VEHICLE_LICENSE_TYPE = 1 - 1;

    private static final Integer DEFAULT_VEHICLE = 1;
    private static final Integer UPDATED_VEHICLE = 2;
    private static final Integer SMALLER_VEHICLE = 1 - 1;

    private static final Integer DEFAULT_VEHICLE_OWNER = 1;
    private static final Integer UPDATED_VEHICLE_OWNER = 2;
    private static final Integer SMALLER_VEHICLE_OWNER = 1 - 1;

    private static final Integer DEFAULT_PASSAGE_TIMES = 1;
    private static final Integer UPDATED_PASSAGE_TIMES = 2;
    private static final Integer SMALLER_PASSAGE_TIMES = 1 - 1;

    private static final String ENTITY_API_URL = "/api/toll-white-lists";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private TollWhiteListRepository tollWhiteListRepository;

    @Autowired
    private TollWhiteListMapper tollWhiteListMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restTollWhiteListMockMvc;

    private TollWhiteList tollWhiteList;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static TollWhiteList createEntity(EntityManager em) {
        TollWhiteList tollWhiteList = new TollWhiteList()
            .name(DEFAULT_NAME)
            .engName(DEFAULT_ENG_NAME)
            .code(DEFAULT_CODE)
            .active(DEFAULT_ACTIVE)
            .updated(DEFAULT_UPDATED)
            .gantry(DEFAULT_GANTRY)
            .vehicleLicenseType(DEFAULT_VEHICLE_LICENSE_TYPE)
            .vehicle(DEFAULT_VEHICLE)
            .vehicleOwner(DEFAULT_VEHICLE_OWNER)
            .passageTimes(DEFAULT_PASSAGE_TIMES);
        return tollWhiteList;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static TollWhiteList createUpdatedEntity(EntityManager em) {
        TollWhiteList tollWhiteList = new TollWhiteList()
            .name(UPDATED_NAME)
            .engName(UPDATED_ENG_NAME)
            .code(UPDATED_CODE)
            .active(UPDATED_ACTIVE)
            .updated(UPDATED_UPDATED)
            .gantry(UPDATED_GANTRY)
            .vehicleLicenseType(UPDATED_VEHICLE_LICENSE_TYPE)
            .vehicle(UPDATED_VEHICLE)
            .vehicleOwner(UPDATED_VEHICLE_OWNER)
            .passageTimes(UPDATED_PASSAGE_TIMES);
        return tollWhiteList;
    }

    @BeforeEach
    public void initTest() {
        tollWhiteList = createEntity(em);
    }

    @Test
    @Transactional
    void createTollWhiteList() throws Exception {
        int databaseSizeBeforeCreate = tollWhiteListRepository.findAll().size();
        // Create the TollWhiteList
        TollWhiteListDTO tollWhiteListDTO = tollWhiteListMapper.toDto(tollWhiteList);
        restTollWhiteListMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(tollWhiteListDTO))
            )
            .andExpect(status().isCreated());

        // Validate the TollWhiteList in the database
        List<TollWhiteList> tollWhiteListList = tollWhiteListRepository.findAll();
        assertThat(tollWhiteListList).hasSize(databaseSizeBeforeCreate + 1);
        TollWhiteList testTollWhiteList = tollWhiteListList.get(tollWhiteListList.size() - 1);
        assertThat(testTollWhiteList.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testTollWhiteList.getEngName()).isEqualTo(DEFAULT_ENG_NAME);
        assertThat(testTollWhiteList.getCode()).isEqualTo(DEFAULT_CODE);
        assertThat(testTollWhiteList.getActive()).isEqualTo(DEFAULT_ACTIVE);
        assertThat(testTollWhiteList.getUpdated()).isEqualTo(DEFAULT_UPDATED);
        assertThat(testTollWhiteList.getGantry()).isEqualTo(DEFAULT_GANTRY);
        assertThat(testTollWhiteList.getVehicleLicenseType()).isEqualTo(DEFAULT_VEHICLE_LICENSE_TYPE);
        assertThat(testTollWhiteList.getVehicle()).isEqualTo(DEFAULT_VEHICLE);
        assertThat(testTollWhiteList.getVehicleOwner()).isEqualTo(DEFAULT_VEHICLE_OWNER);
        assertThat(testTollWhiteList.getPassageTimes()).isEqualTo(DEFAULT_PASSAGE_TIMES);
    }

    @Test
    @Transactional
    void createTollWhiteListWithExistingId() throws Exception {
        // Create the TollWhiteList with an existing ID
        tollWhiteList.setId(1L);
        TollWhiteListDTO tollWhiteListDTO = tollWhiteListMapper.toDto(tollWhiteList);

        int databaseSizeBeforeCreate = tollWhiteListRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restTollWhiteListMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(tollWhiteListDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the TollWhiteList in the database
        List<TollWhiteList> tollWhiteListList = tollWhiteListRepository.findAll();
        assertThat(tollWhiteListList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkActiveIsRequired() throws Exception {
        int databaseSizeBeforeTest = tollWhiteListRepository.findAll().size();
        // set the field null
        tollWhiteList.setActive(null);

        // Create the TollWhiteList, which fails.
        TollWhiteListDTO tollWhiteListDTO = tollWhiteListMapper.toDto(tollWhiteList);

        restTollWhiteListMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(tollWhiteListDTO))
            )
            .andExpect(status().isBadRequest());

        List<TollWhiteList> tollWhiteListList = tollWhiteListRepository.findAll();
        assertThat(tollWhiteListList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkUpdatedIsRequired() throws Exception {
        int databaseSizeBeforeTest = tollWhiteListRepository.findAll().size();
        // set the field null
        tollWhiteList.setUpdated(null);

        // Create the TollWhiteList, which fails.
        TollWhiteListDTO tollWhiteListDTO = tollWhiteListMapper.toDto(tollWhiteList);

        restTollWhiteListMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(tollWhiteListDTO))
            )
            .andExpect(status().isBadRequest());

        List<TollWhiteList> tollWhiteListList = tollWhiteListRepository.findAll();
        assertThat(tollWhiteListList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkPassageTimesIsRequired() throws Exception {
        int databaseSizeBeforeTest = tollWhiteListRepository.findAll().size();
        // set the field null
        tollWhiteList.setPassageTimes(null);

        // Create the TollWhiteList, which fails.
        TollWhiteListDTO tollWhiteListDTO = tollWhiteListMapper.toDto(tollWhiteList);

        restTollWhiteListMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(tollWhiteListDTO))
            )
            .andExpect(status().isBadRequest());

        List<TollWhiteList> tollWhiteListList = tollWhiteListRepository.findAll();
        assertThat(tollWhiteListList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllTollWhiteLists() throws Exception {
        // Initialize the database
        tollWhiteListRepository.saveAndFlush(tollWhiteList);

        // Get all the tollWhiteListList
        restTollWhiteListMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(tollWhiteList.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].engName").value(hasItem(DEFAULT_ENG_NAME)))
            .andExpect(jsonPath("$.[*].code").value(hasItem(DEFAULT_CODE)))
            .andExpect(jsonPath("$.[*].active").value(hasItem(DEFAULT_ACTIVE)))
            .andExpect(jsonPath("$.[*].updated").value(hasItem(DEFAULT_UPDATED.toString())))
            .andExpect(jsonPath("$.[*].gantry").value(hasItem(DEFAULT_GANTRY)))
            .andExpect(jsonPath("$.[*].vehicleLicenseType").value(hasItem(DEFAULT_VEHICLE_LICENSE_TYPE)))
            .andExpect(jsonPath("$.[*].vehicle").value(hasItem(DEFAULT_VEHICLE)))
            .andExpect(jsonPath("$.[*].vehicleOwner").value(hasItem(DEFAULT_VEHICLE_OWNER)))
            .andExpect(jsonPath("$.[*].passageTimes").value(hasItem(DEFAULT_PASSAGE_TIMES)));
    }

    @Test
    @Transactional
    void getTollWhiteList() throws Exception {
        // Initialize the database
        tollWhiteListRepository.saveAndFlush(tollWhiteList);

        // Get the tollWhiteList
        restTollWhiteListMockMvc
            .perform(get(ENTITY_API_URL_ID, tollWhiteList.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(tollWhiteList.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME))
            .andExpect(jsonPath("$.engName").value(DEFAULT_ENG_NAME))
            .andExpect(jsonPath("$.code").value(DEFAULT_CODE))
            .andExpect(jsonPath("$.active").value(DEFAULT_ACTIVE))
            .andExpect(jsonPath("$.updated").value(DEFAULT_UPDATED.toString()))
            .andExpect(jsonPath("$.gantry").value(DEFAULT_GANTRY))
            .andExpect(jsonPath("$.vehicleLicenseType").value(DEFAULT_VEHICLE_LICENSE_TYPE))
            .andExpect(jsonPath("$.vehicle").value(DEFAULT_VEHICLE))
            .andExpect(jsonPath("$.vehicleOwner").value(DEFAULT_VEHICLE_OWNER))
            .andExpect(jsonPath("$.passageTimes").value(DEFAULT_PASSAGE_TIMES));
    }

    @Test
    @Transactional
    void getTollWhiteListsByIdFiltering() throws Exception {
        // Initialize the database
        tollWhiteListRepository.saveAndFlush(tollWhiteList);

        Long id = tollWhiteList.getId();

        defaultTollWhiteListShouldBeFound("id.equals=" + id);
        defaultTollWhiteListShouldNotBeFound("id.notEquals=" + id);

        defaultTollWhiteListShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultTollWhiteListShouldNotBeFound("id.greaterThan=" + id);

        defaultTollWhiteListShouldBeFound("id.lessThanOrEqual=" + id);
        defaultTollWhiteListShouldNotBeFound("id.lessThan=" + id);
    }

    @Test
    @Transactional
    void getAllTollWhiteListsByNameIsEqualToSomething() throws Exception {
        // Initialize the database
        tollWhiteListRepository.saveAndFlush(tollWhiteList);

        // Get all the tollWhiteListList where name equals to DEFAULT_NAME
        defaultTollWhiteListShouldBeFound("name.equals=" + DEFAULT_NAME);

        // Get all the tollWhiteListList where name equals to UPDATED_NAME
        defaultTollWhiteListShouldNotBeFound("name.equals=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    void getAllTollWhiteListsByNameIsInShouldWork() throws Exception {
        // Initialize the database
        tollWhiteListRepository.saveAndFlush(tollWhiteList);

        // Get all the tollWhiteListList where name in DEFAULT_NAME or UPDATED_NAME
        defaultTollWhiteListShouldBeFound("name.in=" + DEFAULT_NAME + "," + UPDATED_NAME);

        // Get all the tollWhiteListList where name equals to UPDATED_NAME
        defaultTollWhiteListShouldNotBeFound("name.in=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    void getAllTollWhiteListsByNameIsNullOrNotNull() throws Exception {
        // Initialize the database
        tollWhiteListRepository.saveAndFlush(tollWhiteList);

        // Get all the tollWhiteListList where name is not null
        defaultTollWhiteListShouldBeFound("name.specified=true");

        // Get all the tollWhiteListList where name is null
        defaultTollWhiteListShouldNotBeFound("name.specified=false");
    }

    @Test
    @Transactional
    void getAllTollWhiteListsByNameContainsSomething() throws Exception {
        // Initialize the database
        tollWhiteListRepository.saveAndFlush(tollWhiteList);

        // Get all the tollWhiteListList where name contains DEFAULT_NAME
        defaultTollWhiteListShouldBeFound("name.contains=" + DEFAULT_NAME);

        // Get all the tollWhiteListList where name contains UPDATED_NAME
        defaultTollWhiteListShouldNotBeFound("name.contains=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    void getAllTollWhiteListsByNameNotContainsSomething() throws Exception {
        // Initialize the database
        tollWhiteListRepository.saveAndFlush(tollWhiteList);

        // Get all the tollWhiteListList where name does not contain DEFAULT_NAME
        defaultTollWhiteListShouldNotBeFound("name.doesNotContain=" + DEFAULT_NAME);

        // Get all the tollWhiteListList where name does not contain UPDATED_NAME
        defaultTollWhiteListShouldBeFound("name.doesNotContain=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    void getAllTollWhiteListsByEngNameIsEqualToSomething() throws Exception {
        // Initialize the database
        tollWhiteListRepository.saveAndFlush(tollWhiteList);

        // Get all the tollWhiteListList where engName equals to DEFAULT_ENG_NAME
        defaultTollWhiteListShouldBeFound("engName.equals=" + DEFAULT_ENG_NAME);

        // Get all the tollWhiteListList where engName equals to UPDATED_ENG_NAME
        defaultTollWhiteListShouldNotBeFound("engName.equals=" + UPDATED_ENG_NAME);
    }

    @Test
    @Transactional
    void getAllTollWhiteListsByEngNameIsInShouldWork() throws Exception {
        // Initialize the database
        tollWhiteListRepository.saveAndFlush(tollWhiteList);

        // Get all the tollWhiteListList where engName in DEFAULT_ENG_NAME or UPDATED_ENG_NAME
        defaultTollWhiteListShouldBeFound("engName.in=" + DEFAULT_ENG_NAME + "," + UPDATED_ENG_NAME);

        // Get all the tollWhiteListList where engName equals to UPDATED_ENG_NAME
        defaultTollWhiteListShouldNotBeFound("engName.in=" + UPDATED_ENG_NAME);
    }

    @Test
    @Transactional
    void getAllTollWhiteListsByEngNameIsNullOrNotNull() throws Exception {
        // Initialize the database
        tollWhiteListRepository.saveAndFlush(tollWhiteList);

        // Get all the tollWhiteListList where engName is not null
        defaultTollWhiteListShouldBeFound("engName.specified=true");

        // Get all the tollWhiteListList where engName is null
        defaultTollWhiteListShouldNotBeFound("engName.specified=false");
    }

    @Test
    @Transactional
    void getAllTollWhiteListsByEngNameContainsSomething() throws Exception {
        // Initialize the database
        tollWhiteListRepository.saveAndFlush(tollWhiteList);

        // Get all the tollWhiteListList where engName contains DEFAULT_ENG_NAME
        defaultTollWhiteListShouldBeFound("engName.contains=" + DEFAULT_ENG_NAME);

        // Get all the tollWhiteListList where engName contains UPDATED_ENG_NAME
        defaultTollWhiteListShouldNotBeFound("engName.contains=" + UPDATED_ENG_NAME);
    }

    @Test
    @Transactional
    void getAllTollWhiteListsByEngNameNotContainsSomething() throws Exception {
        // Initialize the database
        tollWhiteListRepository.saveAndFlush(tollWhiteList);

        // Get all the tollWhiteListList where engName does not contain DEFAULT_ENG_NAME
        defaultTollWhiteListShouldNotBeFound("engName.doesNotContain=" + DEFAULT_ENG_NAME);

        // Get all the tollWhiteListList where engName does not contain UPDATED_ENG_NAME
        defaultTollWhiteListShouldBeFound("engName.doesNotContain=" + UPDATED_ENG_NAME);
    }

    @Test
    @Transactional
    void getAllTollWhiteListsByCodeIsEqualToSomething() throws Exception {
        // Initialize the database
        tollWhiteListRepository.saveAndFlush(tollWhiteList);

        // Get all the tollWhiteListList where code equals to DEFAULT_CODE
        defaultTollWhiteListShouldBeFound("code.equals=" + DEFAULT_CODE);

        // Get all the tollWhiteListList where code equals to UPDATED_CODE
        defaultTollWhiteListShouldNotBeFound("code.equals=" + UPDATED_CODE);
    }

    @Test
    @Transactional
    void getAllTollWhiteListsByCodeIsInShouldWork() throws Exception {
        // Initialize the database
        tollWhiteListRepository.saveAndFlush(tollWhiteList);

        // Get all the tollWhiteListList where code in DEFAULT_CODE or UPDATED_CODE
        defaultTollWhiteListShouldBeFound("code.in=" + DEFAULT_CODE + "," + UPDATED_CODE);

        // Get all the tollWhiteListList where code equals to UPDATED_CODE
        defaultTollWhiteListShouldNotBeFound("code.in=" + UPDATED_CODE);
    }

    @Test
    @Transactional
    void getAllTollWhiteListsByCodeIsNullOrNotNull() throws Exception {
        // Initialize the database
        tollWhiteListRepository.saveAndFlush(tollWhiteList);

        // Get all the tollWhiteListList where code is not null
        defaultTollWhiteListShouldBeFound("code.specified=true");

        // Get all the tollWhiteListList where code is null
        defaultTollWhiteListShouldNotBeFound("code.specified=false");
    }

    @Test
    @Transactional
    void getAllTollWhiteListsByCodeContainsSomething() throws Exception {
        // Initialize the database
        tollWhiteListRepository.saveAndFlush(tollWhiteList);

        // Get all the tollWhiteListList where code contains DEFAULT_CODE
        defaultTollWhiteListShouldBeFound("code.contains=" + DEFAULT_CODE);

        // Get all the tollWhiteListList where code contains UPDATED_CODE
        defaultTollWhiteListShouldNotBeFound("code.contains=" + UPDATED_CODE);
    }

    @Test
    @Transactional
    void getAllTollWhiteListsByCodeNotContainsSomething() throws Exception {
        // Initialize the database
        tollWhiteListRepository.saveAndFlush(tollWhiteList);

        // Get all the tollWhiteListList where code does not contain DEFAULT_CODE
        defaultTollWhiteListShouldNotBeFound("code.doesNotContain=" + DEFAULT_CODE);

        // Get all the tollWhiteListList where code does not contain UPDATED_CODE
        defaultTollWhiteListShouldBeFound("code.doesNotContain=" + UPDATED_CODE);
    }

    @Test
    @Transactional
    void getAllTollWhiteListsByActiveIsEqualToSomething() throws Exception {
        // Initialize the database
        tollWhiteListRepository.saveAndFlush(tollWhiteList);

        // Get all the tollWhiteListList where active equals to DEFAULT_ACTIVE
        defaultTollWhiteListShouldBeFound("active.equals=" + DEFAULT_ACTIVE);

        // Get all the tollWhiteListList where active equals to UPDATED_ACTIVE
        defaultTollWhiteListShouldNotBeFound("active.equals=" + UPDATED_ACTIVE);
    }

    @Test
    @Transactional
    void getAllTollWhiteListsByActiveIsInShouldWork() throws Exception {
        // Initialize the database
        tollWhiteListRepository.saveAndFlush(tollWhiteList);

        // Get all the tollWhiteListList where active in DEFAULT_ACTIVE or UPDATED_ACTIVE
        defaultTollWhiteListShouldBeFound("active.in=" + DEFAULT_ACTIVE + "," + UPDATED_ACTIVE);

        // Get all the tollWhiteListList where active equals to UPDATED_ACTIVE
        defaultTollWhiteListShouldNotBeFound("active.in=" + UPDATED_ACTIVE);
    }

    @Test
    @Transactional
    void getAllTollWhiteListsByActiveIsNullOrNotNull() throws Exception {
        // Initialize the database
        tollWhiteListRepository.saveAndFlush(tollWhiteList);

        // Get all the tollWhiteListList where active is not null
        defaultTollWhiteListShouldBeFound("active.specified=true");

        // Get all the tollWhiteListList where active is null
        defaultTollWhiteListShouldNotBeFound("active.specified=false");
    }

    @Test
    @Transactional
    void getAllTollWhiteListsByActiveIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        tollWhiteListRepository.saveAndFlush(tollWhiteList);

        // Get all the tollWhiteListList where active is greater than or equal to DEFAULT_ACTIVE
        defaultTollWhiteListShouldBeFound("active.greaterThanOrEqual=" + DEFAULT_ACTIVE);

        // Get all the tollWhiteListList where active is greater than or equal to UPDATED_ACTIVE
        defaultTollWhiteListShouldNotBeFound("active.greaterThanOrEqual=" + UPDATED_ACTIVE);
    }

    @Test
    @Transactional
    void getAllTollWhiteListsByActiveIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        tollWhiteListRepository.saveAndFlush(tollWhiteList);

        // Get all the tollWhiteListList where active is less than or equal to DEFAULT_ACTIVE
        defaultTollWhiteListShouldBeFound("active.lessThanOrEqual=" + DEFAULT_ACTIVE);

        // Get all the tollWhiteListList where active is less than or equal to SMALLER_ACTIVE
        defaultTollWhiteListShouldNotBeFound("active.lessThanOrEqual=" + SMALLER_ACTIVE);
    }

    @Test
    @Transactional
    void getAllTollWhiteListsByActiveIsLessThanSomething() throws Exception {
        // Initialize the database
        tollWhiteListRepository.saveAndFlush(tollWhiteList);

        // Get all the tollWhiteListList where active is less than DEFAULT_ACTIVE
        defaultTollWhiteListShouldNotBeFound("active.lessThan=" + DEFAULT_ACTIVE);

        // Get all the tollWhiteListList where active is less than UPDATED_ACTIVE
        defaultTollWhiteListShouldBeFound("active.lessThan=" + UPDATED_ACTIVE);
    }

    @Test
    @Transactional
    void getAllTollWhiteListsByActiveIsGreaterThanSomething() throws Exception {
        // Initialize the database
        tollWhiteListRepository.saveAndFlush(tollWhiteList);

        // Get all the tollWhiteListList where active is greater than DEFAULT_ACTIVE
        defaultTollWhiteListShouldNotBeFound("active.greaterThan=" + DEFAULT_ACTIVE);

        // Get all the tollWhiteListList where active is greater than SMALLER_ACTIVE
        defaultTollWhiteListShouldBeFound("active.greaterThan=" + SMALLER_ACTIVE);
    }

    @Test
    @Transactional
    void getAllTollWhiteListsByUpdatedIsEqualToSomething() throws Exception {
        // Initialize the database
        tollWhiteListRepository.saveAndFlush(tollWhiteList);

        // Get all the tollWhiteListList where updated equals to DEFAULT_UPDATED
        defaultTollWhiteListShouldBeFound("updated.equals=" + DEFAULT_UPDATED);

        // Get all the tollWhiteListList where updated equals to UPDATED_UPDATED
        defaultTollWhiteListShouldNotBeFound("updated.equals=" + UPDATED_UPDATED);
    }

    @Test
    @Transactional
    void getAllTollWhiteListsByUpdatedIsInShouldWork() throws Exception {
        // Initialize the database
        tollWhiteListRepository.saveAndFlush(tollWhiteList);

        // Get all the tollWhiteListList where updated in DEFAULT_UPDATED or UPDATED_UPDATED
        defaultTollWhiteListShouldBeFound("updated.in=" + DEFAULT_UPDATED + "," + UPDATED_UPDATED);

        // Get all the tollWhiteListList where updated equals to UPDATED_UPDATED
        defaultTollWhiteListShouldNotBeFound("updated.in=" + UPDATED_UPDATED);
    }

    @Test
    @Transactional
    void getAllTollWhiteListsByUpdatedIsNullOrNotNull() throws Exception {
        // Initialize the database
        tollWhiteListRepository.saveAndFlush(tollWhiteList);

        // Get all the tollWhiteListList where updated is not null
        defaultTollWhiteListShouldBeFound("updated.specified=true");

        // Get all the tollWhiteListList where updated is null
        defaultTollWhiteListShouldNotBeFound("updated.specified=false");
    }

    @Test
    @Transactional
    void getAllTollWhiteListsByGantryIsEqualToSomething() throws Exception {
        // Initialize the database
        tollWhiteListRepository.saveAndFlush(tollWhiteList);

        // Get all the tollWhiteListList where gantry equals to DEFAULT_GANTRY
        defaultTollWhiteListShouldBeFound("gantry.equals=" + DEFAULT_GANTRY);

        // Get all the tollWhiteListList where gantry equals to UPDATED_GANTRY
        defaultTollWhiteListShouldNotBeFound("gantry.equals=" + UPDATED_GANTRY);
    }

    @Test
    @Transactional
    void getAllTollWhiteListsByGantryIsInShouldWork() throws Exception {
        // Initialize the database
        tollWhiteListRepository.saveAndFlush(tollWhiteList);

        // Get all the tollWhiteListList where gantry in DEFAULT_GANTRY or UPDATED_GANTRY
        defaultTollWhiteListShouldBeFound("gantry.in=" + DEFAULT_GANTRY + "," + UPDATED_GANTRY);

        // Get all the tollWhiteListList where gantry equals to UPDATED_GANTRY
        defaultTollWhiteListShouldNotBeFound("gantry.in=" + UPDATED_GANTRY);
    }

    @Test
    @Transactional
    void getAllTollWhiteListsByGantryIsNullOrNotNull() throws Exception {
        // Initialize the database
        tollWhiteListRepository.saveAndFlush(tollWhiteList);

        // Get all the tollWhiteListList where gantry is not null
        defaultTollWhiteListShouldBeFound("gantry.specified=true");

        // Get all the tollWhiteListList where gantry is null
        defaultTollWhiteListShouldNotBeFound("gantry.specified=false");
    }

    @Test
    @Transactional
    void getAllTollWhiteListsByGantryIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        tollWhiteListRepository.saveAndFlush(tollWhiteList);

        // Get all the tollWhiteListList where gantry is greater than or equal to DEFAULT_GANTRY
        defaultTollWhiteListShouldBeFound("gantry.greaterThanOrEqual=" + DEFAULT_GANTRY);

        // Get all the tollWhiteListList where gantry is greater than or equal to UPDATED_GANTRY
        defaultTollWhiteListShouldNotBeFound("gantry.greaterThanOrEqual=" + UPDATED_GANTRY);
    }

    @Test
    @Transactional
    void getAllTollWhiteListsByGantryIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        tollWhiteListRepository.saveAndFlush(tollWhiteList);

        // Get all the tollWhiteListList where gantry is less than or equal to DEFAULT_GANTRY
        defaultTollWhiteListShouldBeFound("gantry.lessThanOrEqual=" + DEFAULT_GANTRY);

        // Get all the tollWhiteListList where gantry is less than or equal to SMALLER_GANTRY
        defaultTollWhiteListShouldNotBeFound("gantry.lessThanOrEqual=" + SMALLER_GANTRY);
    }

    @Test
    @Transactional
    void getAllTollWhiteListsByGantryIsLessThanSomething() throws Exception {
        // Initialize the database
        tollWhiteListRepository.saveAndFlush(tollWhiteList);

        // Get all the tollWhiteListList where gantry is less than DEFAULT_GANTRY
        defaultTollWhiteListShouldNotBeFound("gantry.lessThan=" + DEFAULT_GANTRY);

        // Get all the tollWhiteListList where gantry is less than UPDATED_GANTRY
        defaultTollWhiteListShouldBeFound("gantry.lessThan=" + UPDATED_GANTRY);
    }

    @Test
    @Transactional
    void getAllTollWhiteListsByGantryIsGreaterThanSomething() throws Exception {
        // Initialize the database
        tollWhiteListRepository.saveAndFlush(tollWhiteList);

        // Get all the tollWhiteListList where gantry is greater than DEFAULT_GANTRY
        defaultTollWhiteListShouldNotBeFound("gantry.greaterThan=" + DEFAULT_GANTRY);

        // Get all the tollWhiteListList where gantry is greater than SMALLER_GANTRY
        defaultTollWhiteListShouldBeFound("gantry.greaterThan=" + SMALLER_GANTRY);
    }

    @Test
    @Transactional
    void getAllTollWhiteListsByVehicleLicenseTypeIsEqualToSomething() throws Exception {
        // Initialize the database
        tollWhiteListRepository.saveAndFlush(tollWhiteList);

        // Get all the tollWhiteListList where vehicleLicenseType equals to DEFAULT_VEHICLE_LICENSE_TYPE
        defaultTollWhiteListShouldBeFound("vehicleLicenseType.equals=" + DEFAULT_VEHICLE_LICENSE_TYPE);

        // Get all the tollWhiteListList where vehicleLicenseType equals to UPDATED_VEHICLE_LICENSE_TYPE
        defaultTollWhiteListShouldNotBeFound("vehicleLicenseType.equals=" + UPDATED_VEHICLE_LICENSE_TYPE);
    }

    @Test
    @Transactional
    void getAllTollWhiteListsByVehicleLicenseTypeIsInShouldWork() throws Exception {
        // Initialize the database
        tollWhiteListRepository.saveAndFlush(tollWhiteList);

        // Get all the tollWhiteListList where vehicleLicenseType in DEFAULT_VEHICLE_LICENSE_TYPE or UPDATED_VEHICLE_LICENSE_TYPE
        defaultTollWhiteListShouldBeFound("vehicleLicenseType.in=" + DEFAULT_VEHICLE_LICENSE_TYPE + "," + UPDATED_VEHICLE_LICENSE_TYPE);

        // Get all the tollWhiteListList where vehicleLicenseType equals to UPDATED_VEHICLE_LICENSE_TYPE
        defaultTollWhiteListShouldNotBeFound("vehicleLicenseType.in=" + UPDATED_VEHICLE_LICENSE_TYPE);
    }

    @Test
    @Transactional
    void getAllTollWhiteListsByVehicleLicenseTypeIsNullOrNotNull() throws Exception {
        // Initialize the database
        tollWhiteListRepository.saveAndFlush(tollWhiteList);

        // Get all the tollWhiteListList where vehicleLicenseType is not null
        defaultTollWhiteListShouldBeFound("vehicleLicenseType.specified=true");

        // Get all the tollWhiteListList where vehicleLicenseType is null
        defaultTollWhiteListShouldNotBeFound("vehicleLicenseType.specified=false");
    }

    @Test
    @Transactional
    void getAllTollWhiteListsByVehicleLicenseTypeIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        tollWhiteListRepository.saveAndFlush(tollWhiteList);

        // Get all the tollWhiteListList where vehicleLicenseType is greater than or equal to DEFAULT_VEHICLE_LICENSE_TYPE
        defaultTollWhiteListShouldBeFound("vehicleLicenseType.greaterThanOrEqual=" + DEFAULT_VEHICLE_LICENSE_TYPE);

        // Get all the tollWhiteListList where vehicleLicenseType is greater than or equal to UPDATED_VEHICLE_LICENSE_TYPE
        defaultTollWhiteListShouldNotBeFound("vehicleLicenseType.greaterThanOrEqual=" + UPDATED_VEHICLE_LICENSE_TYPE);
    }

    @Test
    @Transactional
    void getAllTollWhiteListsByVehicleLicenseTypeIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        tollWhiteListRepository.saveAndFlush(tollWhiteList);

        // Get all the tollWhiteListList where vehicleLicenseType is less than or equal to DEFAULT_VEHICLE_LICENSE_TYPE
        defaultTollWhiteListShouldBeFound("vehicleLicenseType.lessThanOrEqual=" + DEFAULT_VEHICLE_LICENSE_TYPE);

        // Get all the tollWhiteListList where vehicleLicenseType is less than or equal to SMALLER_VEHICLE_LICENSE_TYPE
        defaultTollWhiteListShouldNotBeFound("vehicleLicenseType.lessThanOrEqual=" + SMALLER_VEHICLE_LICENSE_TYPE);
    }

    @Test
    @Transactional
    void getAllTollWhiteListsByVehicleLicenseTypeIsLessThanSomething() throws Exception {
        // Initialize the database
        tollWhiteListRepository.saveAndFlush(tollWhiteList);

        // Get all the tollWhiteListList where vehicleLicenseType is less than DEFAULT_VEHICLE_LICENSE_TYPE
        defaultTollWhiteListShouldNotBeFound("vehicleLicenseType.lessThan=" + DEFAULT_VEHICLE_LICENSE_TYPE);

        // Get all the tollWhiteListList where vehicleLicenseType is less than UPDATED_VEHICLE_LICENSE_TYPE
        defaultTollWhiteListShouldBeFound("vehicleLicenseType.lessThan=" + UPDATED_VEHICLE_LICENSE_TYPE);
    }

    @Test
    @Transactional
    void getAllTollWhiteListsByVehicleLicenseTypeIsGreaterThanSomething() throws Exception {
        // Initialize the database
        tollWhiteListRepository.saveAndFlush(tollWhiteList);

        // Get all the tollWhiteListList where vehicleLicenseType is greater than DEFAULT_VEHICLE_LICENSE_TYPE
        defaultTollWhiteListShouldNotBeFound("vehicleLicenseType.greaterThan=" + DEFAULT_VEHICLE_LICENSE_TYPE);

        // Get all the tollWhiteListList where vehicleLicenseType is greater than SMALLER_VEHICLE_LICENSE_TYPE
        defaultTollWhiteListShouldBeFound("vehicleLicenseType.greaterThan=" + SMALLER_VEHICLE_LICENSE_TYPE);
    }

    @Test
    @Transactional
    void getAllTollWhiteListsByVehicleIsEqualToSomething() throws Exception {
        // Initialize the database
        tollWhiteListRepository.saveAndFlush(tollWhiteList);

        // Get all the tollWhiteListList where vehicle equals to DEFAULT_VEHICLE
        defaultTollWhiteListShouldBeFound("vehicle.equals=" + DEFAULT_VEHICLE);

        // Get all the tollWhiteListList where vehicle equals to UPDATED_VEHICLE
        defaultTollWhiteListShouldNotBeFound("vehicle.equals=" + UPDATED_VEHICLE);
    }

    @Test
    @Transactional
    void getAllTollWhiteListsByVehicleIsInShouldWork() throws Exception {
        // Initialize the database
        tollWhiteListRepository.saveAndFlush(tollWhiteList);

        // Get all the tollWhiteListList where vehicle in DEFAULT_VEHICLE or UPDATED_VEHICLE
        defaultTollWhiteListShouldBeFound("vehicle.in=" + DEFAULT_VEHICLE + "," + UPDATED_VEHICLE);

        // Get all the tollWhiteListList where vehicle equals to UPDATED_VEHICLE
        defaultTollWhiteListShouldNotBeFound("vehicle.in=" + UPDATED_VEHICLE);
    }

    @Test
    @Transactional
    void getAllTollWhiteListsByVehicleIsNullOrNotNull() throws Exception {
        // Initialize the database
        tollWhiteListRepository.saveAndFlush(tollWhiteList);

        // Get all the tollWhiteListList where vehicle is not null
        defaultTollWhiteListShouldBeFound("vehicle.specified=true");

        // Get all the tollWhiteListList where vehicle is null
        defaultTollWhiteListShouldNotBeFound("vehicle.specified=false");
    }

    @Test
    @Transactional
    void getAllTollWhiteListsByVehicleIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        tollWhiteListRepository.saveAndFlush(tollWhiteList);

        // Get all the tollWhiteListList where vehicle is greater than or equal to DEFAULT_VEHICLE
        defaultTollWhiteListShouldBeFound("vehicle.greaterThanOrEqual=" + DEFAULT_VEHICLE);

        // Get all the tollWhiteListList where vehicle is greater than or equal to UPDATED_VEHICLE
        defaultTollWhiteListShouldNotBeFound("vehicle.greaterThanOrEqual=" + UPDATED_VEHICLE);
    }

    @Test
    @Transactional
    void getAllTollWhiteListsByVehicleIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        tollWhiteListRepository.saveAndFlush(tollWhiteList);

        // Get all the tollWhiteListList where vehicle is less than or equal to DEFAULT_VEHICLE
        defaultTollWhiteListShouldBeFound("vehicle.lessThanOrEqual=" + DEFAULT_VEHICLE);

        // Get all the tollWhiteListList where vehicle is less than or equal to SMALLER_VEHICLE
        defaultTollWhiteListShouldNotBeFound("vehicle.lessThanOrEqual=" + SMALLER_VEHICLE);
    }

    @Test
    @Transactional
    void getAllTollWhiteListsByVehicleIsLessThanSomething() throws Exception {
        // Initialize the database
        tollWhiteListRepository.saveAndFlush(tollWhiteList);

        // Get all the tollWhiteListList where vehicle is less than DEFAULT_VEHICLE
        defaultTollWhiteListShouldNotBeFound("vehicle.lessThan=" + DEFAULT_VEHICLE);

        // Get all the tollWhiteListList where vehicle is less than UPDATED_VEHICLE
        defaultTollWhiteListShouldBeFound("vehicle.lessThan=" + UPDATED_VEHICLE);
    }

    @Test
    @Transactional
    void getAllTollWhiteListsByVehicleIsGreaterThanSomething() throws Exception {
        // Initialize the database
        tollWhiteListRepository.saveAndFlush(tollWhiteList);

        // Get all the tollWhiteListList where vehicle is greater than DEFAULT_VEHICLE
        defaultTollWhiteListShouldNotBeFound("vehicle.greaterThan=" + DEFAULT_VEHICLE);

        // Get all the tollWhiteListList where vehicle is greater than SMALLER_VEHICLE
        defaultTollWhiteListShouldBeFound("vehicle.greaterThan=" + SMALLER_VEHICLE);
    }

    @Test
    @Transactional
    void getAllTollWhiteListsByVehicleOwnerIsEqualToSomething() throws Exception {
        // Initialize the database
        tollWhiteListRepository.saveAndFlush(tollWhiteList);

        // Get all the tollWhiteListList where vehicleOwner equals to DEFAULT_VEHICLE_OWNER
        defaultTollWhiteListShouldBeFound("vehicleOwner.equals=" + DEFAULT_VEHICLE_OWNER);

        // Get all the tollWhiteListList where vehicleOwner equals to UPDATED_VEHICLE_OWNER
        defaultTollWhiteListShouldNotBeFound("vehicleOwner.equals=" + UPDATED_VEHICLE_OWNER);
    }

    @Test
    @Transactional
    void getAllTollWhiteListsByVehicleOwnerIsInShouldWork() throws Exception {
        // Initialize the database
        tollWhiteListRepository.saveAndFlush(tollWhiteList);

        // Get all the tollWhiteListList where vehicleOwner in DEFAULT_VEHICLE_OWNER or UPDATED_VEHICLE_OWNER
        defaultTollWhiteListShouldBeFound("vehicleOwner.in=" + DEFAULT_VEHICLE_OWNER + "," + UPDATED_VEHICLE_OWNER);

        // Get all the tollWhiteListList where vehicleOwner equals to UPDATED_VEHICLE_OWNER
        defaultTollWhiteListShouldNotBeFound("vehicleOwner.in=" + UPDATED_VEHICLE_OWNER);
    }

    @Test
    @Transactional
    void getAllTollWhiteListsByVehicleOwnerIsNullOrNotNull() throws Exception {
        // Initialize the database
        tollWhiteListRepository.saveAndFlush(tollWhiteList);

        // Get all the tollWhiteListList where vehicleOwner is not null
        defaultTollWhiteListShouldBeFound("vehicleOwner.specified=true");

        // Get all the tollWhiteListList where vehicleOwner is null
        defaultTollWhiteListShouldNotBeFound("vehicleOwner.specified=false");
    }

    @Test
    @Transactional
    void getAllTollWhiteListsByVehicleOwnerIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        tollWhiteListRepository.saveAndFlush(tollWhiteList);

        // Get all the tollWhiteListList where vehicleOwner is greater than or equal to DEFAULT_VEHICLE_OWNER
        defaultTollWhiteListShouldBeFound("vehicleOwner.greaterThanOrEqual=" + DEFAULT_VEHICLE_OWNER);

        // Get all the tollWhiteListList where vehicleOwner is greater than or equal to UPDATED_VEHICLE_OWNER
        defaultTollWhiteListShouldNotBeFound("vehicleOwner.greaterThanOrEqual=" + UPDATED_VEHICLE_OWNER);
    }

    @Test
    @Transactional
    void getAllTollWhiteListsByVehicleOwnerIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        tollWhiteListRepository.saveAndFlush(tollWhiteList);

        // Get all the tollWhiteListList where vehicleOwner is less than or equal to DEFAULT_VEHICLE_OWNER
        defaultTollWhiteListShouldBeFound("vehicleOwner.lessThanOrEqual=" + DEFAULT_VEHICLE_OWNER);

        // Get all the tollWhiteListList where vehicleOwner is less than or equal to SMALLER_VEHICLE_OWNER
        defaultTollWhiteListShouldNotBeFound("vehicleOwner.lessThanOrEqual=" + SMALLER_VEHICLE_OWNER);
    }

    @Test
    @Transactional
    void getAllTollWhiteListsByVehicleOwnerIsLessThanSomething() throws Exception {
        // Initialize the database
        tollWhiteListRepository.saveAndFlush(tollWhiteList);

        // Get all the tollWhiteListList where vehicleOwner is less than DEFAULT_VEHICLE_OWNER
        defaultTollWhiteListShouldNotBeFound("vehicleOwner.lessThan=" + DEFAULT_VEHICLE_OWNER);

        // Get all the tollWhiteListList where vehicleOwner is less than UPDATED_VEHICLE_OWNER
        defaultTollWhiteListShouldBeFound("vehicleOwner.lessThan=" + UPDATED_VEHICLE_OWNER);
    }

    @Test
    @Transactional
    void getAllTollWhiteListsByVehicleOwnerIsGreaterThanSomething() throws Exception {
        // Initialize the database
        tollWhiteListRepository.saveAndFlush(tollWhiteList);

        // Get all the tollWhiteListList where vehicleOwner is greater than DEFAULT_VEHICLE_OWNER
        defaultTollWhiteListShouldNotBeFound("vehicleOwner.greaterThan=" + DEFAULT_VEHICLE_OWNER);

        // Get all the tollWhiteListList where vehicleOwner is greater than SMALLER_VEHICLE_OWNER
        defaultTollWhiteListShouldBeFound("vehicleOwner.greaterThan=" + SMALLER_VEHICLE_OWNER);
    }

    @Test
    @Transactional
    void getAllTollWhiteListsByPassageTimesIsEqualToSomething() throws Exception {
        // Initialize the database
        tollWhiteListRepository.saveAndFlush(tollWhiteList);

        // Get all the tollWhiteListList where passageTimes equals to DEFAULT_PASSAGE_TIMES
        defaultTollWhiteListShouldBeFound("passageTimes.equals=" + DEFAULT_PASSAGE_TIMES);

        // Get all the tollWhiteListList where passageTimes equals to UPDATED_PASSAGE_TIMES
        defaultTollWhiteListShouldNotBeFound("passageTimes.equals=" + UPDATED_PASSAGE_TIMES);
    }

    @Test
    @Transactional
    void getAllTollWhiteListsByPassageTimesIsInShouldWork() throws Exception {
        // Initialize the database
        tollWhiteListRepository.saveAndFlush(tollWhiteList);

        // Get all the tollWhiteListList where passageTimes in DEFAULT_PASSAGE_TIMES or UPDATED_PASSAGE_TIMES
        defaultTollWhiteListShouldBeFound("passageTimes.in=" + DEFAULT_PASSAGE_TIMES + "," + UPDATED_PASSAGE_TIMES);

        // Get all the tollWhiteListList where passageTimes equals to UPDATED_PASSAGE_TIMES
        defaultTollWhiteListShouldNotBeFound("passageTimes.in=" + UPDATED_PASSAGE_TIMES);
    }

    @Test
    @Transactional
    void getAllTollWhiteListsByPassageTimesIsNullOrNotNull() throws Exception {
        // Initialize the database
        tollWhiteListRepository.saveAndFlush(tollWhiteList);

        // Get all the tollWhiteListList where passageTimes is not null
        defaultTollWhiteListShouldBeFound("passageTimes.specified=true");

        // Get all the tollWhiteListList where passageTimes is null
        defaultTollWhiteListShouldNotBeFound("passageTimes.specified=false");
    }

    @Test
    @Transactional
    void getAllTollWhiteListsByPassageTimesIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        tollWhiteListRepository.saveAndFlush(tollWhiteList);

        // Get all the tollWhiteListList where passageTimes is greater than or equal to DEFAULT_PASSAGE_TIMES
        defaultTollWhiteListShouldBeFound("passageTimes.greaterThanOrEqual=" + DEFAULT_PASSAGE_TIMES);

        // Get all the tollWhiteListList where passageTimes is greater than or equal to UPDATED_PASSAGE_TIMES
        defaultTollWhiteListShouldNotBeFound("passageTimes.greaterThanOrEqual=" + UPDATED_PASSAGE_TIMES);
    }

    @Test
    @Transactional
    void getAllTollWhiteListsByPassageTimesIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        tollWhiteListRepository.saveAndFlush(tollWhiteList);

        // Get all the tollWhiteListList where passageTimes is less than or equal to DEFAULT_PASSAGE_TIMES
        defaultTollWhiteListShouldBeFound("passageTimes.lessThanOrEqual=" + DEFAULT_PASSAGE_TIMES);

        // Get all the tollWhiteListList where passageTimes is less than or equal to SMALLER_PASSAGE_TIMES
        defaultTollWhiteListShouldNotBeFound("passageTimes.lessThanOrEqual=" + SMALLER_PASSAGE_TIMES);
    }

    @Test
    @Transactional
    void getAllTollWhiteListsByPassageTimesIsLessThanSomething() throws Exception {
        // Initialize the database
        tollWhiteListRepository.saveAndFlush(tollWhiteList);

        // Get all the tollWhiteListList where passageTimes is less than DEFAULT_PASSAGE_TIMES
        defaultTollWhiteListShouldNotBeFound("passageTimes.lessThan=" + DEFAULT_PASSAGE_TIMES);

        // Get all the tollWhiteListList where passageTimes is less than UPDATED_PASSAGE_TIMES
        defaultTollWhiteListShouldBeFound("passageTimes.lessThan=" + UPDATED_PASSAGE_TIMES);
    }

    @Test
    @Transactional
    void getAllTollWhiteListsByPassageTimesIsGreaterThanSomething() throws Exception {
        // Initialize the database
        tollWhiteListRepository.saveAndFlush(tollWhiteList);

        // Get all the tollWhiteListList where passageTimes is greater than DEFAULT_PASSAGE_TIMES
        defaultTollWhiteListShouldNotBeFound("passageTimes.greaterThan=" + DEFAULT_PASSAGE_TIMES);

        // Get all the tollWhiteListList where passageTimes is greater than SMALLER_PASSAGE_TIMES
        defaultTollWhiteListShouldBeFound("passageTimes.greaterThan=" + SMALLER_PASSAGE_TIMES);
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultTollWhiteListShouldBeFound(String filter) throws Exception {
        restTollWhiteListMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(tollWhiteList.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].engName").value(hasItem(DEFAULT_ENG_NAME)))
            .andExpect(jsonPath("$.[*].code").value(hasItem(DEFAULT_CODE)))
            .andExpect(jsonPath("$.[*].active").value(hasItem(DEFAULT_ACTIVE)))
            .andExpect(jsonPath("$.[*].updated").value(hasItem(DEFAULT_UPDATED.toString())))
            .andExpect(jsonPath("$.[*].gantry").value(hasItem(DEFAULT_GANTRY)))
            .andExpect(jsonPath("$.[*].vehicleLicenseType").value(hasItem(DEFAULT_VEHICLE_LICENSE_TYPE)))
            .andExpect(jsonPath("$.[*].vehicle").value(hasItem(DEFAULT_VEHICLE)))
            .andExpect(jsonPath("$.[*].vehicleOwner").value(hasItem(DEFAULT_VEHICLE_OWNER)))
            .andExpect(jsonPath("$.[*].passageTimes").value(hasItem(DEFAULT_PASSAGE_TIMES)));

        // Check, that the count call also returns 1
        restTollWhiteListMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultTollWhiteListShouldNotBeFound(String filter) throws Exception {
        restTollWhiteListMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restTollWhiteListMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    void getNonExistingTollWhiteList() throws Exception {
        // Get the tollWhiteList
        restTollWhiteListMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingTollWhiteList() throws Exception {
        // Initialize the database
        tollWhiteListRepository.saveAndFlush(tollWhiteList);

        int databaseSizeBeforeUpdate = tollWhiteListRepository.findAll().size();

        // Update the tollWhiteList
        TollWhiteList updatedTollWhiteList = tollWhiteListRepository.findById(tollWhiteList.getId()).get();
        // Disconnect from session so that the updates on updatedTollWhiteList are not directly saved in db
        em.detach(updatedTollWhiteList);
        updatedTollWhiteList
            .name(UPDATED_NAME)
            .engName(UPDATED_ENG_NAME)
            .code(UPDATED_CODE)
            .active(UPDATED_ACTIVE)
            .updated(UPDATED_UPDATED)
            .gantry(UPDATED_GANTRY)
            .vehicleLicenseType(UPDATED_VEHICLE_LICENSE_TYPE)
            .vehicle(UPDATED_VEHICLE)
            .vehicleOwner(UPDATED_VEHICLE_OWNER)
            .passageTimes(UPDATED_PASSAGE_TIMES);
        TollWhiteListDTO tollWhiteListDTO = tollWhiteListMapper.toDto(updatedTollWhiteList);

        restTollWhiteListMockMvc
            .perform(
                put(ENTITY_API_URL_ID, tollWhiteListDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(tollWhiteListDTO))
            )
            .andExpect(status().isOk());

        // Validate the TollWhiteList in the database
        List<TollWhiteList> tollWhiteListList = tollWhiteListRepository.findAll();
        assertThat(tollWhiteListList).hasSize(databaseSizeBeforeUpdate);
        TollWhiteList testTollWhiteList = tollWhiteListList.get(tollWhiteListList.size() - 1);
        assertThat(testTollWhiteList.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testTollWhiteList.getEngName()).isEqualTo(UPDATED_ENG_NAME);
        assertThat(testTollWhiteList.getCode()).isEqualTo(UPDATED_CODE);
        assertThat(testTollWhiteList.getActive()).isEqualTo(UPDATED_ACTIVE);
        assertThat(testTollWhiteList.getUpdated()).isEqualTo(UPDATED_UPDATED);
        assertThat(testTollWhiteList.getGantry()).isEqualTo(UPDATED_GANTRY);
        assertThat(testTollWhiteList.getVehicleLicenseType()).isEqualTo(UPDATED_VEHICLE_LICENSE_TYPE);
        assertThat(testTollWhiteList.getVehicle()).isEqualTo(UPDATED_VEHICLE);
        assertThat(testTollWhiteList.getVehicleOwner()).isEqualTo(UPDATED_VEHICLE_OWNER);
        assertThat(testTollWhiteList.getPassageTimes()).isEqualTo(UPDATED_PASSAGE_TIMES);
    }

    @Test
    @Transactional
    void putNonExistingTollWhiteList() throws Exception {
        int databaseSizeBeforeUpdate = tollWhiteListRepository.findAll().size();
        tollWhiteList.setId(count.incrementAndGet());

        // Create the TollWhiteList
        TollWhiteListDTO tollWhiteListDTO = tollWhiteListMapper.toDto(tollWhiteList);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restTollWhiteListMockMvc
            .perform(
                put(ENTITY_API_URL_ID, tollWhiteListDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(tollWhiteListDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the TollWhiteList in the database
        List<TollWhiteList> tollWhiteListList = tollWhiteListRepository.findAll();
        assertThat(tollWhiteListList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchTollWhiteList() throws Exception {
        int databaseSizeBeforeUpdate = tollWhiteListRepository.findAll().size();
        tollWhiteList.setId(count.incrementAndGet());

        // Create the TollWhiteList
        TollWhiteListDTO tollWhiteListDTO = tollWhiteListMapper.toDto(tollWhiteList);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restTollWhiteListMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(tollWhiteListDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the TollWhiteList in the database
        List<TollWhiteList> tollWhiteListList = tollWhiteListRepository.findAll();
        assertThat(tollWhiteListList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamTollWhiteList() throws Exception {
        int databaseSizeBeforeUpdate = tollWhiteListRepository.findAll().size();
        tollWhiteList.setId(count.incrementAndGet());

        // Create the TollWhiteList
        TollWhiteListDTO tollWhiteListDTO = tollWhiteListMapper.toDto(tollWhiteList);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restTollWhiteListMockMvc
            .perform(
                put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(tollWhiteListDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the TollWhiteList in the database
        List<TollWhiteList> tollWhiteListList = tollWhiteListRepository.findAll();
        assertThat(tollWhiteListList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateTollWhiteListWithPatch() throws Exception {
        // Initialize the database
        tollWhiteListRepository.saveAndFlush(tollWhiteList);

        int databaseSizeBeforeUpdate = tollWhiteListRepository.findAll().size();

        // Update the tollWhiteList using partial update
        TollWhiteList partialUpdatedTollWhiteList = new TollWhiteList();
        partialUpdatedTollWhiteList.setId(tollWhiteList.getId());

        partialUpdatedTollWhiteList
            .name(UPDATED_NAME)
            .engName(UPDATED_ENG_NAME)
            .code(UPDATED_CODE)
            .gantry(UPDATED_GANTRY)
            .vehicle(UPDATED_VEHICLE)
            .passageTimes(UPDATED_PASSAGE_TIMES);

        restTollWhiteListMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedTollWhiteList.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedTollWhiteList))
            )
            .andExpect(status().isOk());

        // Validate the TollWhiteList in the database
        List<TollWhiteList> tollWhiteListList = tollWhiteListRepository.findAll();
        assertThat(tollWhiteListList).hasSize(databaseSizeBeforeUpdate);
        TollWhiteList testTollWhiteList = tollWhiteListList.get(tollWhiteListList.size() - 1);
        assertThat(testTollWhiteList.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testTollWhiteList.getEngName()).isEqualTo(UPDATED_ENG_NAME);
        assertThat(testTollWhiteList.getCode()).isEqualTo(UPDATED_CODE);
        assertThat(testTollWhiteList.getActive()).isEqualTo(DEFAULT_ACTIVE);
        assertThat(testTollWhiteList.getUpdated()).isEqualTo(DEFAULT_UPDATED);
        assertThat(testTollWhiteList.getGantry()).isEqualTo(UPDATED_GANTRY);
        assertThat(testTollWhiteList.getVehicleLicenseType()).isEqualTo(DEFAULT_VEHICLE_LICENSE_TYPE);
        assertThat(testTollWhiteList.getVehicle()).isEqualTo(UPDATED_VEHICLE);
        assertThat(testTollWhiteList.getVehicleOwner()).isEqualTo(DEFAULT_VEHICLE_OWNER);
        assertThat(testTollWhiteList.getPassageTimes()).isEqualTo(UPDATED_PASSAGE_TIMES);
    }

    @Test
    @Transactional
    void fullUpdateTollWhiteListWithPatch() throws Exception {
        // Initialize the database
        tollWhiteListRepository.saveAndFlush(tollWhiteList);

        int databaseSizeBeforeUpdate = tollWhiteListRepository.findAll().size();

        // Update the tollWhiteList using partial update
        TollWhiteList partialUpdatedTollWhiteList = new TollWhiteList();
        partialUpdatedTollWhiteList.setId(tollWhiteList.getId());

        partialUpdatedTollWhiteList
            .name(UPDATED_NAME)
            .engName(UPDATED_ENG_NAME)
            .code(UPDATED_CODE)
            .active(UPDATED_ACTIVE)
            .updated(UPDATED_UPDATED)
            .gantry(UPDATED_GANTRY)
            .vehicleLicenseType(UPDATED_VEHICLE_LICENSE_TYPE)
            .vehicle(UPDATED_VEHICLE)
            .vehicleOwner(UPDATED_VEHICLE_OWNER)
            .passageTimes(UPDATED_PASSAGE_TIMES);

        restTollWhiteListMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedTollWhiteList.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedTollWhiteList))
            )
            .andExpect(status().isOk());

        // Validate the TollWhiteList in the database
        List<TollWhiteList> tollWhiteListList = tollWhiteListRepository.findAll();
        assertThat(tollWhiteListList).hasSize(databaseSizeBeforeUpdate);
        TollWhiteList testTollWhiteList = tollWhiteListList.get(tollWhiteListList.size() - 1);
        assertThat(testTollWhiteList.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testTollWhiteList.getEngName()).isEqualTo(UPDATED_ENG_NAME);
        assertThat(testTollWhiteList.getCode()).isEqualTo(UPDATED_CODE);
        assertThat(testTollWhiteList.getActive()).isEqualTo(UPDATED_ACTIVE);
        assertThat(testTollWhiteList.getUpdated()).isEqualTo(UPDATED_UPDATED);
        assertThat(testTollWhiteList.getGantry()).isEqualTo(UPDATED_GANTRY);
        assertThat(testTollWhiteList.getVehicleLicenseType()).isEqualTo(UPDATED_VEHICLE_LICENSE_TYPE);
        assertThat(testTollWhiteList.getVehicle()).isEqualTo(UPDATED_VEHICLE);
        assertThat(testTollWhiteList.getVehicleOwner()).isEqualTo(UPDATED_VEHICLE_OWNER);
        assertThat(testTollWhiteList.getPassageTimes()).isEqualTo(UPDATED_PASSAGE_TIMES);
    }

    @Test
    @Transactional
    void patchNonExistingTollWhiteList() throws Exception {
        int databaseSizeBeforeUpdate = tollWhiteListRepository.findAll().size();
        tollWhiteList.setId(count.incrementAndGet());

        // Create the TollWhiteList
        TollWhiteListDTO tollWhiteListDTO = tollWhiteListMapper.toDto(tollWhiteList);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restTollWhiteListMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, tollWhiteListDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(tollWhiteListDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the TollWhiteList in the database
        List<TollWhiteList> tollWhiteListList = tollWhiteListRepository.findAll();
        assertThat(tollWhiteListList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchTollWhiteList() throws Exception {
        int databaseSizeBeforeUpdate = tollWhiteListRepository.findAll().size();
        tollWhiteList.setId(count.incrementAndGet());

        // Create the TollWhiteList
        TollWhiteListDTO tollWhiteListDTO = tollWhiteListMapper.toDto(tollWhiteList);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restTollWhiteListMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(tollWhiteListDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the TollWhiteList in the database
        List<TollWhiteList> tollWhiteListList = tollWhiteListRepository.findAll();
        assertThat(tollWhiteListList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamTollWhiteList() throws Exception {
        int databaseSizeBeforeUpdate = tollWhiteListRepository.findAll().size();
        tollWhiteList.setId(count.incrementAndGet());

        // Create the TollWhiteList
        TollWhiteListDTO tollWhiteListDTO = tollWhiteListMapper.toDto(tollWhiteList);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restTollWhiteListMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(tollWhiteListDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the TollWhiteList in the database
        List<TollWhiteList> tollWhiteListList = tollWhiteListRepository.findAll();
        assertThat(tollWhiteListList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteTollWhiteList() throws Exception {
        // Initialize the database
        tollWhiteListRepository.saveAndFlush(tollWhiteList);

        int databaseSizeBeforeDelete = tollWhiteListRepository.findAll().size();

        // Delete the tollWhiteList
        restTollWhiteListMockMvc
            .perform(delete(ENTITY_API_URL_ID, tollWhiteList.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<TollWhiteList> tollWhiteListList = tollWhiteListRepository.findAll();
        assertThat(tollWhiteListList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
