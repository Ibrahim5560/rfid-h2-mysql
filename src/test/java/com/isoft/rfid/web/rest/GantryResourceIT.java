package com.isoft.rfid.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.isoft.rfid.IntegrationTest;
import com.isoft.rfid.domain.Gantry;
import com.isoft.rfid.repository.GantryRepository;
import com.isoft.rfid.service.criteria.GantryCriteria;
import com.isoft.rfid.service.dto.GantryDTO;
import com.isoft.rfid.service.mapper.GantryMapper;
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
 * Integration tests for the {@link GantryResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class GantryResourceIT {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final Integer DEFAULT_ROUTE = 1;
    private static final Integer UPDATED_ROUTE = 2;
    private static final Integer SMALLER_ROUTE = 1 - 1;

    private static final Integer DEFAULT_LANES = 1;
    private static final Integer UPDATED_LANES = 2;
    private static final Integer SMALLER_LANES = 1 - 1;

    private static final Double DEFAULT_LONGITUDE = 1D;
    private static final Double UPDATED_LONGITUDE = 2D;
    private static final Double SMALLER_LONGITUDE = 1D - 1D;

    private static final Double DEFAULT_LAT = 1D;
    private static final Double UPDATED_LAT = 2D;
    private static final Double SMALLER_LAT = 1D - 1D;

    private static final Integer DEFAULT_JURISDICTION = 1;
    private static final Integer UPDATED_JURISDICTION = 2;
    private static final Integer SMALLER_JURISDICTION = 1 - 1;

    private static final Integer DEFAULT_GANTRY_TYPE = 1;
    private static final Integer UPDATED_GANTRY_TYPE = 2;
    private static final Integer SMALLER_GANTRY_TYPE = 1 - 1;

    private static final Integer DEFAULT_GANTRY_SET = 1;
    private static final Integer UPDATED_GANTRY_SET = 2;
    private static final Integer SMALLER_GANTRY_SET = 1 - 1;

    private static final Integer DEFAULT_ACTIVE = 1;
    private static final Integer UPDATED_ACTIVE = 2;
    private static final Integer SMALLER_ACTIVE = 1 - 1;

    private static final String DEFAULT_PASSWORD = "AAAAAAAAAA";
    private static final String UPDATED_PASSWORD = "BBBBBBBBBB";

    private static final Instant DEFAULT_UPDATED = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_UPDATED = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final String DEFAULT_IP = "AAAAAAAAAA";
    private static final String UPDATED_IP = "BBBBBBBBBB";

    private static final Double DEFAULT_TOLL = 1D;
    private static final Double UPDATED_TOLL = 2D;
    private static final Double SMALLER_TOLL = 1D - 1D;

    private static final Integer DEFAULT_PASSAGE_TIMES = 1;
    private static final Integer UPDATED_PASSAGE_TIMES = 2;
    private static final Integer SMALLER_PASSAGE_TIMES = 1 - 1;

    private static final String ENTITY_API_URL = "/api/gantries";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private GantryRepository gantryRepository;

    @Autowired
    private GantryMapper gantryMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restGantryMockMvc;

    private Gantry gantry;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Gantry createEntity(EntityManager em) {
        Gantry gantry = new Gantry()
            .name(DEFAULT_NAME)
            .route(DEFAULT_ROUTE)
            .lanes(DEFAULT_LANES)
            .longitude(DEFAULT_LONGITUDE)
            .lat(DEFAULT_LAT)
            .jurisdiction(DEFAULT_JURISDICTION)
            .gantryType(DEFAULT_GANTRY_TYPE)
            .gantrySet(DEFAULT_GANTRY_SET)
            .active(DEFAULT_ACTIVE)
            .password(DEFAULT_PASSWORD)
            .updated(DEFAULT_UPDATED)
            .ip(DEFAULT_IP)
            .toll(DEFAULT_TOLL)
            .passageTimes(DEFAULT_PASSAGE_TIMES);
        return gantry;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Gantry createUpdatedEntity(EntityManager em) {
        Gantry gantry = new Gantry()
            .name(UPDATED_NAME)
            .route(UPDATED_ROUTE)
            .lanes(UPDATED_LANES)
            .longitude(UPDATED_LONGITUDE)
            .lat(UPDATED_LAT)
            .jurisdiction(UPDATED_JURISDICTION)
            .gantryType(UPDATED_GANTRY_TYPE)
            .gantrySet(UPDATED_GANTRY_SET)
            .active(UPDATED_ACTIVE)
            .password(UPDATED_PASSWORD)
            .updated(UPDATED_UPDATED)
            .ip(UPDATED_IP)
            .toll(UPDATED_TOLL)
            .passageTimes(UPDATED_PASSAGE_TIMES);
        return gantry;
    }

    @BeforeEach
    public void initTest() {
        gantry = createEntity(em);
    }

    @Test
    @Transactional
    void createGantry() throws Exception {
        int databaseSizeBeforeCreate = gantryRepository.findAll().size();
        // Create the Gantry
        GantryDTO gantryDTO = gantryMapper.toDto(gantry);
        restGantryMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(gantryDTO)))
            .andExpect(status().isCreated());

        // Validate the Gantry in the database
        List<Gantry> gantryList = gantryRepository.findAll();
        assertThat(gantryList).hasSize(databaseSizeBeforeCreate + 1);
        Gantry testGantry = gantryList.get(gantryList.size() - 1);
        assertThat(testGantry.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testGantry.getRoute()).isEqualTo(DEFAULT_ROUTE);
        assertThat(testGantry.getLanes()).isEqualTo(DEFAULT_LANES);
        assertThat(testGantry.getLongitude()).isEqualTo(DEFAULT_LONGITUDE);
        assertThat(testGantry.getLat()).isEqualTo(DEFAULT_LAT);
        assertThat(testGantry.getJurisdiction()).isEqualTo(DEFAULT_JURISDICTION);
        assertThat(testGantry.getGantryType()).isEqualTo(DEFAULT_GANTRY_TYPE);
        assertThat(testGantry.getGantrySet()).isEqualTo(DEFAULT_GANTRY_SET);
        assertThat(testGantry.getActive()).isEqualTo(DEFAULT_ACTIVE);
        assertThat(testGantry.getPassword()).isEqualTo(DEFAULT_PASSWORD);
        assertThat(testGantry.getUpdated()).isEqualTo(DEFAULT_UPDATED);
        assertThat(testGantry.getIp()).isEqualTo(DEFAULT_IP);
        assertThat(testGantry.getToll()).isEqualTo(DEFAULT_TOLL);
        assertThat(testGantry.getPassageTimes()).isEqualTo(DEFAULT_PASSAGE_TIMES);
    }

    @Test
    @Transactional
    void createGantryWithExistingId() throws Exception {
        // Create the Gantry with an existing ID
        gantry.setId(1L);
        GantryDTO gantryDTO = gantryMapper.toDto(gantry);

        int databaseSizeBeforeCreate = gantryRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restGantryMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(gantryDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Gantry in the database
        List<Gantry> gantryList = gantryRepository.findAll();
        assertThat(gantryList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkTollIsRequired() throws Exception {
        int databaseSizeBeforeTest = gantryRepository.findAll().size();
        // set the field null
        gantry.setToll(null);

        // Create the Gantry, which fails.
        GantryDTO gantryDTO = gantryMapper.toDto(gantry);

        restGantryMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(gantryDTO)))
            .andExpect(status().isBadRequest());

        List<Gantry> gantryList = gantryRepository.findAll();
        assertThat(gantryList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkPassageTimesIsRequired() throws Exception {
        int databaseSizeBeforeTest = gantryRepository.findAll().size();
        // set the field null
        gantry.setPassageTimes(null);

        // Create the Gantry, which fails.
        GantryDTO gantryDTO = gantryMapper.toDto(gantry);

        restGantryMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(gantryDTO)))
            .andExpect(status().isBadRequest());

        List<Gantry> gantryList = gantryRepository.findAll();
        assertThat(gantryList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllGantries() throws Exception {
        // Initialize the database
        gantryRepository.saveAndFlush(gantry);

        // Get all the gantryList
        restGantryMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(gantry.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].route").value(hasItem(DEFAULT_ROUTE)))
            .andExpect(jsonPath("$.[*].lanes").value(hasItem(DEFAULT_LANES)))
            .andExpect(jsonPath("$.[*].longitude").value(hasItem(DEFAULT_LONGITUDE.doubleValue())))
            .andExpect(jsonPath("$.[*].lat").value(hasItem(DEFAULT_LAT.doubleValue())))
            .andExpect(jsonPath("$.[*].jurisdiction").value(hasItem(DEFAULT_JURISDICTION)))
            .andExpect(jsonPath("$.[*].gantryType").value(hasItem(DEFAULT_GANTRY_TYPE)))
            .andExpect(jsonPath("$.[*].gantrySet").value(hasItem(DEFAULT_GANTRY_SET)))
            .andExpect(jsonPath("$.[*].active").value(hasItem(DEFAULT_ACTIVE)))
            .andExpect(jsonPath("$.[*].password").value(hasItem(DEFAULT_PASSWORD)))
            .andExpect(jsonPath("$.[*].updated").value(hasItem(DEFAULT_UPDATED.toString())))
            .andExpect(jsonPath("$.[*].ip").value(hasItem(DEFAULT_IP)))
            .andExpect(jsonPath("$.[*].toll").value(hasItem(DEFAULT_TOLL.doubleValue())))
            .andExpect(jsonPath("$.[*].passageTimes").value(hasItem(DEFAULT_PASSAGE_TIMES)));
    }

    @Test
    @Transactional
    void getGantry() throws Exception {
        // Initialize the database
        gantryRepository.saveAndFlush(gantry);

        // Get the gantry
        restGantryMockMvc
            .perform(get(ENTITY_API_URL_ID, gantry.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(gantry.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME))
            .andExpect(jsonPath("$.route").value(DEFAULT_ROUTE))
            .andExpect(jsonPath("$.lanes").value(DEFAULT_LANES))
            .andExpect(jsonPath("$.longitude").value(DEFAULT_LONGITUDE.doubleValue()))
            .andExpect(jsonPath("$.lat").value(DEFAULT_LAT.doubleValue()))
            .andExpect(jsonPath("$.jurisdiction").value(DEFAULT_JURISDICTION))
            .andExpect(jsonPath("$.gantryType").value(DEFAULT_GANTRY_TYPE))
            .andExpect(jsonPath("$.gantrySet").value(DEFAULT_GANTRY_SET))
            .andExpect(jsonPath("$.active").value(DEFAULT_ACTIVE))
            .andExpect(jsonPath("$.password").value(DEFAULT_PASSWORD))
            .andExpect(jsonPath("$.updated").value(DEFAULT_UPDATED.toString()))
            .andExpect(jsonPath("$.ip").value(DEFAULT_IP))
            .andExpect(jsonPath("$.toll").value(DEFAULT_TOLL.doubleValue()))
            .andExpect(jsonPath("$.passageTimes").value(DEFAULT_PASSAGE_TIMES));
    }

    @Test
    @Transactional
    void getGantriesByIdFiltering() throws Exception {
        // Initialize the database
        gantryRepository.saveAndFlush(gantry);

        Long id = gantry.getId();

        defaultGantryShouldBeFound("id.equals=" + id);
        defaultGantryShouldNotBeFound("id.notEquals=" + id);

        defaultGantryShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultGantryShouldNotBeFound("id.greaterThan=" + id);

        defaultGantryShouldBeFound("id.lessThanOrEqual=" + id);
        defaultGantryShouldNotBeFound("id.lessThan=" + id);
    }

    @Test
    @Transactional
    void getAllGantriesByNameIsEqualToSomething() throws Exception {
        // Initialize the database
        gantryRepository.saveAndFlush(gantry);

        // Get all the gantryList where name equals to DEFAULT_NAME
        defaultGantryShouldBeFound("name.equals=" + DEFAULT_NAME);

        // Get all the gantryList where name equals to UPDATED_NAME
        defaultGantryShouldNotBeFound("name.equals=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    void getAllGantriesByNameIsInShouldWork() throws Exception {
        // Initialize the database
        gantryRepository.saveAndFlush(gantry);

        // Get all the gantryList where name in DEFAULT_NAME or UPDATED_NAME
        defaultGantryShouldBeFound("name.in=" + DEFAULT_NAME + "," + UPDATED_NAME);

        // Get all the gantryList where name equals to UPDATED_NAME
        defaultGantryShouldNotBeFound("name.in=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    void getAllGantriesByNameIsNullOrNotNull() throws Exception {
        // Initialize the database
        gantryRepository.saveAndFlush(gantry);

        // Get all the gantryList where name is not null
        defaultGantryShouldBeFound("name.specified=true");

        // Get all the gantryList where name is null
        defaultGantryShouldNotBeFound("name.specified=false");
    }

    @Test
    @Transactional
    void getAllGantriesByNameContainsSomething() throws Exception {
        // Initialize the database
        gantryRepository.saveAndFlush(gantry);

        // Get all the gantryList where name contains DEFAULT_NAME
        defaultGantryShouldBeFound("name.contains=" + DEFAULT_NAME);

        // Get all the gantryList where name contains UPDATED_NAME
        defaultGantryShouldNotBeFound("name.contains=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    void getAllGantriesByNameNotContainsSomething() throws Exception {
        // Initialize the database
        gantryRepository.saveAndFlush(gantry);

        // Get all the gantryList where name does not contain DEFAULT_NAME
        defaultGantryShouldNotBeFound("name.doesNotContain=" + DEFAULT_NAME);

        // Get all the gantryList where name does not contain UPDATED_NAME
        defaultGantryShouldBeFound("name.doesNotContain=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    void getAllGantriesByRouteIsEqualToSomething() throws Exception {
        // Initialize the database
        gantryRepository.saveAndFlush(gantry);

        // Get all the gantryList where route equals to DEFAULT_ROUTE
        defaultGantryShouldBeFound("route.equals=" + DEFAULT_ROUTE);

        // Get all the gantryList where route equals to UPDATED_ROUTE
        defaultGantryShouldNotBeFound("route.equals=" + UPDATED_ROUTE);
    }

    @Test
    @Transactional
    void getAllGantriesByRouteIsInShouldWork() throws Exception {
        // Initialize the database
        gantryRepository.saveAndFlush(gantry);

        // Get all the gantryList where route in DEFAULT_ROUTE or UPDATED_ROUTE
        defaultGantryShouldBeFound("route.in=" + DEFAULT_ROUTE + "," + UPDATED_ROUTE);

        // Get all the gantryList where route equals to UPDATED_ROUTE
        defaultGantryShouldNotBeFound("route.in=" + UPDATED_ROUTE);
    }

    @Test
    @Transactional
    void getAllGantriesByRouteIsNullOrNotNull() throws Exception {
        // Initialize the database
        gantryRepository.saveAndFlush(gantry);

        // Get all the gantryList where route is not null
        defaultGantryShouldBeFound("route.specified=true");

        // Get all the gantryList where route is null
        defaultGantryShouldNotBeFound("route.specified=false");
    }

    @Test
    @Transactional
    void getAllGantriesByRouteIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        gantryRepository.saveAndFlush(gantry);

        // Get all the gantryList where route is greater than or equal to DEFAULT_ROUTE
        defaultGantryShouldBeFound("route.greaterThanOrEqual=" + DEFAULT_ROUTE);

        // Get all the gantryList where route is greater than or equal to UPDATED_ROUTE
        defaultGantryShouldNotBeFound("route.greaterThanOrEqual=" + UPDATED_ROUTE);
    }

    @Test
    @Transactional
    void getAllGantriesByRouteIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        gantryRepository.saveAndFlush(gantry);

        // Get all the gantryList where route is less than or equal to DEFAULT_ROUTE
        defaultGantryShouldBeFound("route.lessThanOrEqual=" + DEFAULT_ROUTE);

        // Get all the gantryList where route is less than or equal to SMALLER_ROUTE
        defaultGantryShouldNotBeFound("route.lessThanOrEqual=" + SMALLER_ROUTE);
    }

    @Test
    @Transactional
    void getAllGantriesByRouteIsLessThanSomething() throws Exception {
        // Initialize the database
        gantryRepository.saveAndFlush(gantry);

        // Get all the gantryList where route is less than DEFAULT_ROUTE
        defaultGantryShouldNotBeFound("route.lessThan=" + DEFAULT_ROUTE);

        // Get all the gantryList where route is less than UPDATED_ROUTE
        defaultGantryShouldBeFound("route.lessThan=" + UPDATED_ROUTE);
    }

    @Test
    @Transactional
    void getAllGantriesByRouteIsGreaterThanSomething() throws Exception {
        // Initialize the database
        gantryRepository.saveAndFlush(gantry);

        // Get all the gantryList where route is greater than DEFAULT_ROUTE
        defaultGantryShouldNotBeFound("route.greaterThan=" + DEFAULT_ROUTE);

        // Get all the gantryList where route is greater than SMALLER_ROUTE
        defaultGantryShouldBeFound("route.greaterThan=" + SMALLER_ROUTE);
    }

    @Test
    @Transactional
    void getAllGantriesByLanesIsEqualToSomething() throws Exception {
        // Initialize the database
        gantryRepository.saveAndFlush(gantry);

        // Get all the gantryList where lanes equals to DEFAULT_LANES
        defaultGantryShouldBeFound("lanes.equals=" + DEFAULT_LANES);

        // Get all the gantryList where lanes equals to UPDATED_LANES
        defaultGantryShouldNotBeFound("lanes.equals=" + UPDATED_LANES);
    }

    @Test
    @Transactional
    void getAllGantriesByLanesIsInShouldWork() throws Exception {
        // Initialize the database
        gantryRepository.saveAndFlush(gantry);

        // Get all the gantryList where lanes in DEFAULT_LANES or UPDATED_LANES
        defaultGantryShouldBeFound("lanes.in=" + DEFAULT_LANES + "," + UPDATED_LANES);

        // Get all the gantryList where lanes equals to UPDATED_LANES
        defaultGantryShouldNotBeFound("lanes.in=" + UPDATED_LANES);
    }

    @Test
    @Transactional
    void getAllGantriesByLanesIsNullOrNotNull() throws Exception {
        // Initialize the database
        gantryRepository.saveAndFlush(gantry);

        // Get all the gantryList where lanes is not null
        defaultGantryShouldBeFound("lanes.specified=true");

        // Get all the gantryList where lanes is null
        defaultGantryShouldNotBeFound("lanes.specified=false");
    }

    @Test
    @Transactional
    void getAllGantriesByLanesIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        gantryRepository.saveAndFlush(gantry);

        // Get all the gantryList where lanes is greater than or equal to DEFAULT_LANES
        defaultGantryShouldBeFound("lanes.greaterThanOrEqual=" + DEFAULT_LANES);

        // Get all the gantryList where lanes is greater than or equal to UPDATED_LANES
        defaultGantryShouldNotBeFound("lanes.greaterThanOrEqual=" + UPDATED_LANES);
    }

    @Test
    @Transactional
    void getAllGantriesByLanesIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        gantryRepository.saveAndFlush(gantry);

        // Get all the gantryList where lanes is less than or equal to DEFAULT_LANES
        defaultGantryShouldBeFound("lanes.lessThanOrEqual=" + DEFAULT_LANES);

        // Get all the gantryList where lanes is less than or equal to SMALLER_LANES
        defaultGantryShouldNotBeFound("lanes.lessThanOrEqual=" + SMALLER_LANES);
    }

    @Test
    @Transactional
    void getAllGantriesByLanesIsLessThanSomething() throws Exception {
        // Initialize the database
        gantryRepository.saveAndFlush(gantry);

        // Get all the gantryList where lanes is less than DEFAULT_LANES
        defaultGantryShouldNotBeFound("lanes.lessThan=" + DEFAULT_LANES);

        // Get all the gantryList where lanes is less than UPDATED_LANES
        defaultGantryShouldBeFound("lanes.lessThan=" + UPDATED_LANES);
    }

    @Test
    @Transactional
    void getAllGantriesByLanesIsGreaterThanSomething() throws Exception {
        // Initialize the database
        gantryRepository.saveAndFlush(gantry);

        // Get all the gantryList where lanes is greater than DEFAULT_LANES
        defaultGantryShouldNotBeFound("lanes.greaterThan=" + DEFAULT_LANES);

        // Get all the gantryList where lanes is greater than SMALLER_LANES
        defaultGantryShouldBeFound("lanes.greaterThan=" + SMALLER_LANES);
    }

    @Test
    @Transactional
    void getAllGantriesByLongitudeIsEqualToSomething() throws Exception {
        // Initialize the database
        gantryRepository.saveAndFlush(gantry);

        // Get all the gantryList where longitude equals to DEFAULT_LONGITUDE
        defaultGantryShouldBeFound("longitude.equals=" + DEFAULT_LONGITUDE);

        // Get all the gantryList where longitude equals to UPDATED_LONGITUDE
        defaultGantryShouldNotBeFound("longitude.equals=" + UPDATED_LONGITUDE);
    }

    @Test
    @Transactional
    void getAllGantriesByLongitudeIsInShouldWork() throws Exception {
        // Initialize the database
        gantryRepository.saveAndFlush(gantry);

        // Get all the gantryList where longitude in DEFAULT_LONGITUDE or UPDATED_LONGITUDE
        defaultGantryShouldBeFound("longitude.in=" + DEFAULT_LONGITUDE + "," + UPDATED_LONGITUDE);

        // Get all the gantryList where longitude equals to UPDATED_LONGITUDE
        defaultGantryShouldNotBeFound("longitude.in=" + UPDATED_LONGITUDE);
    }

    @Test
    @Transactional
    void getAllGantriesByLongitudeIsNullOrNotNull() throws Exception {
        // Initialize the database
        gantryRepository.saveAndFlush(gantry);

        // Get all the gantryList where longitude is not null
        defaultGantryShouldBeFound("longitude.specified=true");

        // Get all the gantryList where longitude is null
        defaultGantryShouldNotBeFound("longitude.specified=false");
    }

    @Test
    @Transactional
    void getAllGantriesByLongitudeIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        gantryRepository.saveAndFlush(gantry);

        // Get all the gantryList where longitude is greater than or equal to DEFAULT_LONGITUDE
        defaultGantryShouldBeFound("longitude.greaterThanOrEqual=" + DEFAULT_LONGITUDE);

        // Get all the gantryList where longitude is greater than or equal to UPDATED_LONGITUDE
        defaultGantryShouldNotBeFound("longitude.greaterThanOrEqual=" + UPDATED_LONGITUDE);
    }

    @Test
    @Transactional
    void getAllGantriesByLongitudeIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        gantryRepository.saveAndFlush(gantry);

        // Get all the gantryList where longitude is less than or equal to DEFAULT_LONGITUDE
        defaultGantryShouldBeFound("longitude.lessThanOrEqual=" + DEFAULT_LONGITUDE);

        // Get all the gantryList where longitude is less than or equal to SMALLER_LONGITUDE
        defaultGantryShouldNotBeFound("longitude.lessThanOrEqual=" + SMALLER_LONGITUDE);
    }

    @Test
    @Transactional
    void getAllGantriesByLongitudeIsLessThanSomething() throws Exception {
        // Initialize the database
        gantryRepository.saveAndFlush(gantry);

        // Get all the gantryList where longitude is less than DEFAULT_LONGITUDE
        defaultGantryShouldNotBeFound("longitude.lessThan=" + DEFAULT_LONGITUDE);

        // Get all the gantryList where longitude is less than UPDATED_LONGITUDE
        defaultGantryShouldBeFound("longitude.lessThan=" + UPDATED_LONGITUDE);
    }

    @Test
    @Transactional
    void getAllGantriesByLongitudeIsGreaterThanSomething() throws Exception {
        // Initialize the database
        gantryRepository.saveAndFlush(gantry);

        // Get all the gantryList where longitude is greater than DEFAULT_LONGITUDE
        defaultGantryShouldNotBeFound("longitude.greaterThan=" + DEFAULT_LONGITUDE);

        // Get all the gantryList where longitude is greater than SMALLER_LONGITUDE
        defaultGantryShouldBeFound("longitude.greaterThan=" + SMALLER_LONGITUDE);
    }

    @Test
    @Transactional
    void getAllGantriesByLatIsEqualToSomething() throws Exception {
        // Initialize the database
        gantryRepository.saveAndFlush(gantry);

        // Get all the gantryList where lat equals to DEFAULT_LAT
        defaultGantryShouldBeFound("lat.equals=" + DEFAULT_LAT);

        // Get all the gantryList where lat equals to UPDATED_LAT
        defaultGantryShouldNotBeFound("lat.equals=" + UPDATED_LAT);
    }

    @Test
    @Transactional
    void getAllGantriesByLatIsInShouldWork() throws Exception {
        // Initialize the database
        gantryRepository.saveAndFlush(gantry);

        // Get all the gantryList where lat in DEFAULT_LAT or UPDATED_LAT
        defaultGantryShouldBeFound("lat.in=" + DEFAULT_LAT + "," + UPDATED_LAT);

        // Get all the gantryList where lat equals to UPDATED_LAT
        defaultGantryShouldNotBeFound("lat.in=" + UPDATED_LAT);
    }

    @Test
    @Transactional
    void getAllGantriesByLatIsNullOrNotNull() throws Exception {
        // Initialize the database
        gantryRepository.saveAndFlush(gantry);

        // Get all the gantryList where lat is not null
        defaultGantryShouldBeFound("lat.specified=true");

        // Get all the gantryList where lat is null
        defaultGantryShouldNotBeFound("lat.specified=false");
    }

    @Test
    @Transactional
    void getAllGantriesByLatIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        gantryRepository.saveAndFlush(gantry);

        // Get all the gantryList where lat is greater than or equal to DEFAULT_LAT
        defaultGantryShouldBeFound("lat.greaterThanOrEqual=" + DEFAULT_LAT);

        // Get all the gantryList where lat is greater than or equal to UPDATED_LAT
        defaultGantryShouldNotBeFound("lat.greaterThanOrEqual=" + UPDATED_LAT);
    }

    @Test
    @Transactional
    void getAllGantriesByLatIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        gantryRepository.saveAndFlush(gantry);

        // Get all the gantryList where lat is less than or equal to DEFAULT_LAT
        defaultGantryShouldBeFound("lat.lessThanOrEqual=" + DEFAULT_LAT);

        // Get all the gantryList where lat is less than or equal to SMALLER_LAT
        defaultGantryShouldNotBeFound("lat.lessThanOrEqual=" + SMALLER_LAT);
    }

    @Test
    @Transactional
    void getAllGantriesByLatIsLessThanSomething() throws Exception {
        // Initialize the database
        gantryRepository.saveAndFlush(gantry);

        // Get all the gantryList where lat is less than DEFAULT_LAT
        defaultGantryShouldNotBeFound("lat.lessThan=" + DEFAULT_LAT);

        // Get all the gantryList where lat is less than UPDATED_LAT
        defaultGantryShouldBeFound("lat.lessThan=" + UPDATED_LAT);
    }

    @Test
    @Transactional
    void getAllGantriesByLatIsGreaterThanSomething() throws Exception {
        // Initialize the database
        gantryRepository.saveAndFlush(gantry);

        // Get all the gantryList where lat is greater than DEFAULT_LAT
        defaultGantryShouldNotBeFound("lat.greaterThan=" + DEFAULT_LAT);

        // Get all the gantryList where lat is greater than SMALLER_LAT
        defaultGantryShouldBeFound("lat.greaterThan=" + SMALLER_LAT);
    }

    @Test
    @Transactional
    void getAllGantriesByJurisdictionIsEqualToSomething() throws Exception {
        // Initialize the database
        gantryRepository.saveAndFlush(gantry);

        // Get all the gantryList where jurisdiction equals to DEFAULT_JURISDICTION
        defaultGantryShouldBeFound("jurisdiction.equals=" + DEFAULT_JURISDICTION);

        // Get all the gantryList where jurisdiction equals to UPDATED_JURISDICTION
        defaultGantryShouldNotBeFound("jurisdiction.equals=" + UPDATED_JURISDICTION);
    }

    @Test
    @Transactional
    void getAllGantriesByJurisdictionIsInShouldWork() throws Exception {
        // Initialize the database
        gantryRepository.saveAndFlush(gantry);

        // Get all the gantryList where jurisdiction in DEFAULT_JURISDICTION or UPDATED_JURISDICTION
        defaultGantryShouldBeFound("jurisdiction.in=" + DEFAULT_JURISDICTION + "," + UPDATED_JURISDICTION);

        // Get all the gantryList where jurisdiction equals to UPDATED_JURISDICTION
        defaultGantryShouldNotBeFound("jurisdiction.in=" + UPDATED_JURISDICTION);
    }

    @Test
    @Transactional
    void getAllGantriesByJurisdictionIsNullOrNotNull() throws Exception {
        // Initialize the database
        gantryRepository.saveAndFlush(gantry);

        // Get all the gantryList where jurisdiction is not null
        defaultGantryShouldBeFound("jurisdiction.specified=true");

        // Get all the gantryList where jurisdiction is null
        defaultGantryShouldNotBeFound("jurisdiction.specified=false");
    }

    @Test
    @Transactional
    void getAllGantriesByJurisdictionIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        gantryRepository.saveAndFlush(gantry);

        // Get all the gantryList where jurisdiction is greater than or equal to DEFAULT_JURISDICTION
        defaultGantryShouldBeFound("jurisdiction.greaterThanOrEqual=" + DEFAULT_JURISDICTION);

        // Get all the gantryList where jurisdiction is greater than or equal to UPDATED_JURISDICTION
        defaultGantryShouldNotBeFound("jurisdiction.greaterThanOrEqual=" + UPDATED_JURISDICTION);
    }

    @Test
    @Transactional
    void getAllGantriesByJurisdictionIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        gantryRepository.saveAndFlush(gantry);

        // Get all the gantryList where jurisdiction is less than or equal to DEFAULT_JURISDICTION
        defaultGantryShouldBeFound("jurisdiction.lessThanOrEqual=" + DEFAULT_JURISDICTION);

        // Get all the gantryList where jurisdiction is less than or equal to SMALLER_JURISDICTION
        defaultGantryShouldNotBeFound("jurisdiction.lessThanOrEqual=" + SMALLER_JURISDICTION);
    }

    @Test
    @Transactional
    void getAllGantriesByJurisdictionIsLessThanSomething() throws Exception {
        // Initialize the database
        gantryRepository.saveAndFlush(gantry);

        // Get all the gantryList where jurisdiction is less than DEFAULT_JURISDICTION
        defaultGantryShouldNotBeFound("jurisdiction.lessThan=" + DEFAULT_JURISDICTION);

        // Get all the gantryList where jurisdiction is less than UPDATED_JURISDICTION
        defaultGantryShouldBeFound("jurisdiction.lessThan=" + UPDATED_JURISDICTION);
    }

    @Test
    @Transactional
    void getAllGantriesByJurisdictionIsGreaterThanSomething() throws Exception {
        // Initialize the database
        gantryRepository.saveAndFlush(gantry);

        // Get all the gantryList where jurisdiction is greater than DEFAULT_JURISDICTION
        defaultGantryShouldNotBeFound("jurisdiction.greaterThan=" + DEFAULT_JURISDICTION);

        // Get all the gantryList where jurisdiction is greater than SMALLER_JURISDICTION
        defaultGantryShouldBeFound("jurisdiction.greaterThan=" + SMALLER_JURISDICTION);
    }

    @Test
    @Transactional
    void getAllGantriesByGantryTypeIsEqualToSomething() throws Exception {
        // Initialize the database
        gantryRepository.saveAndFlush(gantry);

        // Get all the gantryList where gantryType equals to DEFAULT_GANTRY_TYPE
        defaultGantryShouldBeFound("gantryType.equals=" + DEFAULT_GANTRY_TYPE);

        // Get all the gantryList where gantryType equals to UPDATED_GANTRY_TYPE
        defaultGantryShouldNotBeFound("gantryType.equals=" + UPDATED_GANTRY_TYPE);
    }

    @Test
    @Transactional
    void getAllGantriesByGantryTypeIsInShouldWork() throws Exception {
        // Initialize the database
        gantryRepository.saveAndFlush(gantry);

        // Get all the gantryList where gantryType in DEFAULT_GANTRY_TYPE or UPDATED_GANTRY_TYPE
        defaultGantryShouldBeFound("gantryType.in=" + DEFAULT_GANTRY_TYPE + "," + UPDATED_GANTRY_TYPE);

        // Get all the gantryList where gantryType equals to UPDATED_GANTRY_TYPE
        defaultGantryShouldNotBeFound("gantryType.in=" + UPDATED_GANTRY_TYPE);
    }

    @Test
    @Transactional
    void getAllGantriesByGantryTypeIsNullOrNotNull() throws Exception {
        // Initialize the database
        gantryRepository.saveAndFlush(gantry);

        // Get all the gantryList where gantryType is not null
        defaultGantryShouldBeFound("gantryType.specified=true");

        // Get all the gantryList where gantryType is null
        defaultGantryShouldNotBeFound("gantryType.specified=false");
    }

    @Test
    @Transactional
    void getAllGantriesByGantryTypeIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        gantryRepository.saveAndFlush(gantry);

        // Get all the gantryList where gantryType is greater than or equal to DEFAULT_GANTRY_TYPE
        defaultGantryShouldBeFound("gantryType.greaterThanOrEqual=" + DEFAULT_GANTRY_TYPE);

        // Get all the gantryList where gantryType is greater than or equal to UPDATED_GANTRY_TYPE
        defaultGantryShouldNotBeFound("gantryType.greaterThanOrEqual=" + UPDATED_GANTRY_TYPE);
    }

    @Test
    @Transactional
    void getAllGantriesByGantryTypeIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        gantryRepository.saveAndFlush(gantry);

        // Get all the gantryList where gantryType is less than or equal to DEFAULT_GANTRY_TYPE
        defaultGantryShouldBeFound("gantryType.lessThanOrEqual=" + DEFAULT_GANTRY_TYPE);

        // Get all the gantryList where gantryType is less than or equal to SMALLER_GANTRY_TYPE
        defaultGantryShouldNotBeFound("gantryType.lessThanOrEqual=" + SMALLER_GANTRY_TYPE);
    }

    @Test
    @Transactional
    void getAllGantriesByGantryTypeIsLessThanSomething() throws Exception {
        // Initialize the database
        gantryRepository.saveAndFlush(gantry);

        // Get all the gantryList where gantryType is less than DEFAULT_GANTRY_TYPE
        defaultGantryShouldNotBeFound("gantryType.lessThan=" + DEFAULT_GANTRY_TYPE);

        // Get all the gantryList where gantryType is less than UPDATED_GANTRY_TYPE
        defaultGantryShouldBeFound("gantryType.lessThan=" + UPDATED_GANTRY_TYPE);
    }

    @Test
    @Transactional
    void getAllGantriesByGantryTypeIsGreaterThanSomething() throws Exception {
        // Initialize the database
        gantryRepository.saveAndFlush(gantry);

        // Get all the gantryList where gantryType is greater than DEFAULT_GANTRY_TYPE
        defaultGantryShouldNotBeFound("gantryType.greaterThan=" + DEFAULT_GANTRY_TYPE);

        // Get all the gantryList where gantryType is greater than SMALLER_GANTRY_TYPE
        defaultGantryShouldBeFound("gantryType.greaterThan=" + SMALLER_GANTRY_TYPE);
    }

    @Test
    @Transactional
    void getAllGantriesByGantrySetIsEqualToSomething() throws Exception {
        // Initialize the database
        gantryRepository.saveAndFlush(gantry);

        // Get all the gantryList where gantrySet equals to DEFAULT_GANTRY_SET
        defaultGantryShouldBeFound("gantrySet.equals=" + DEFAULT_GANTRY_SET);

        // Get all the gantryList where gantrySet equals to UPDATED_GANTRY_SET
        defaultGantryShouldNotBeFound("gantrySet.equals=" + UPDATED_GANTRY_SET);
    }

    @Test
    @Transactional
    void getAllGantriesByGantrySetIsInShouldWork() throws Exception {
        // Initialize the database
        gantryRepository.saveAndFlush(gantry);

        // Get all the gantryList where gantrySet in DEFAULT_GANTRY_SET or UPDATED_GANTRY_SET
        defaultGantryShouldBeFound("gantrySet.in=" + DEFAULT_GANTRY_SET + "," + UPDATED_GANTRY_SET);

        // Get all the gantryList where gantrySet equals to UPDATED_GANTRY_SET
        defaultGantryShouldNotBeFound("gantrySet.in=" + UPDATED_GANTRY_SET);
    }

    @Test
    @Transactional
    void getAllGantriesByGantrySetIsNullOrNotNull() throws Exception {
        // Initialize the database
        gantryRepository.saveAndFlush(gantry);

        // Get all the gantryList where gantrySet is not null
        defaultGantryShouldBeFound("gantrySet.specified=true");

        // Get all the gantryList where gantrySet is null
        defaultGantryShouldNotBeFound("gantrySet.specified=false");
    }

    @Test
    @Transactional
    void getAllGantriesByGantrySetIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        gantryRepository.saveAndFlush(gantry);

        // Get all the gantryList where gantrySet is greater than or equal to DEFAULT_GANTRY_SET
        defaultGantryShouldBeFound("gantrySet.greaterThanOrEqual=" + DEFAULT_GANTRY_SET);

        // Get all the gantryList where gantrySet is greater than or equal to UPDATED_GANTRY_SET
        defaultGantryShouldNotBeFound("gantrySet.greaterThanOrEqual=" + UPDATED_GANTRY_SET);
    }

    @Test
    @Transactional
    void getAllGantriesByGantrySetIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        gantryRepository.saveAndFlush(gantry);

        // Get all the gantryList where gantrySet is less than or equal to DEFAULT_GANTRY_SET
        defaultGantryShouldBeFound("gantrySet.lessThanOrEqual=" + DEFAULT_GANTRY_SET);

        // Get all the gantryList where gantrySet is less than or equal to SMALLER_GANTRY_SET
        defaultGantryShouldNotBeFound("gantrySet.lessThanOrEqual=" + SMALLER_GANTRY_SET);
    }

    @Test
    @Transactional
    void getAllGantriesByGantrySetIsLessThanSomething() throws Exception {
        // Initialize the database
        gantryRepository.saveAndFlush(gantry);

        // Get all the gantryList where gantrySet is less than DEFAULT_GANTRY_SET
        defaultGantryShouldNotBeFound("gantrySet.lessThan=" + DEFAULT_GANTRY_SET);

        // Get all the gantryList where gantrySet is less than UPDATED_GANTRY_SET
        defaultGantryShouldBeFound("gantrySet.lessThan=" + UPDATED_GANTRY_SET);
    }

    @Test
    @Transactional
    void getAllGantriesByGantrySetIsGreaterThanSomething() throws Exception {
        // Initialize the database
        gantryRepository.saveAndFlush(gantry);

        // Get all the gantryList where gantrySet is greater than DEFAULT_GANTRY_SET
        defaultGantryShouldNotBeFound("gantrySet.greaterThan=" + DEFAULT_GANTRY_SET);

        // Get all the gantryList where gantrySet is greater than SMALLER_GANTRY_SET
        defaultGantryShouldBeFound("gantrySet.greaterThan=" + SMALLER_GANTRY_SET);
    }

    @Test
    @Transactional
    void getAllGantriesByActiveIsEqualToSomething() throws Exception {
        // Initialize the database
        gantryRepository.saveAndFlush(gantry);

        // Get all the gantryList where active equals to DEFAULT_ACTIVE
        defaultGantryShouldBeFound("active.equals=" + DEFAULT_ACTIVE);

        // Get all the gantryList where active equals to UPDATED_ACTIVE
        defaultGantryShouldNotBeFound("active.equals=" + UPDATED_ACTIVE);
    }

    @Test
    @Transactional
    void getAllGantriesByActiveIsInShouldWork() throws Exception {
        // Initialize the database
        gantryRepository.saveAndFlush(gantry);

        // Get all the gantryList where active in DEFAULT_ACTIVE or UPDATED_ACTIVE
        defaultGantryShouldBeFound("active.in=" + DEFAULT_ACTIVE + "," + UPDATED_ACTIVE);

        // Get all the gantryList where active equals to UPDATED_ACTIVE
        defaultGantryShouldNotBeFound("active.in=" + UPDATED_ACTIVE);
    }

    @Test
    @Transactional
    void getAllGantriesByActiveIsNullOrNotNull() throws Exception {
        // Initialize the database
        gantryRepository.saveAndFlush(gantry);

        // Get all the gantryList where active is not null
        defaultGantryShouldBeFound("active.specified=true");

        // Get all the gantryList where active is null
        defaultGantryShouldNotBeFound("active.specified=false");
    }

    @Test
    @Transactional
    void getAllGantriesByActiveIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        gantryRepository.saveAndFlush(gantry);

        // Get all the gantryList where active is greater than or equal to DEFAULT_ACTIVE
        defaultGantryShouldBeFound("active.greaterThanOrEqual=" + DEFAULT_ACTIVE);

        // Get all the gantryList where active is greater than or equal to UPDATED_ACTIVE
        defaultGantryShouldNotBeFound("active.greaterThanOrEqual=" + UPDATED_ACTIVE);
    }

    @Test
    @Transactional
    void getAllGantriesByActiveIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        gantryRepository.saveAndFlush(gantry);

        // Get all the gantryList where active is less than or equal to DEFAULT_ACTIVE
        defaultGantryShouldBeFound("active.lessThanOrEqual=" + DEFAULT_ACTIVE);

        // Get all the gantryList where active is less than or equal to SMALLER_ACTIVE
        defaultGantryShouldNotBeFound("active.lessThanOrEqual=" + SMALLER_ACTIVE);
    }

    @Test
    @Transactional
    void getAllGantriesByActiveIsLessThanSomething() throws Exception {
        // Initialize the database
        gantryRepository.saveAndFlush(gantry);

        // Get all the gantryList where active is less than DEFAULT_ACTIVE
        defaultGantryShouldNotBeFound("active.lessThan=" + DEFAULT_ACTIVE);

        // Get all the gantryList where active is less than UPDATED_ACTIVE
        defaultGantryShouldBeFound("active.lessThan=" + UPDATED_ACTIVE);
    }

    @Test
    @Transactional
    void getAllGantriesByActiveIsGreaterThanSomething() throws Exception {
        // Initialize the database
        gantryRepository.saveAndFlush(gantry);

        // Get all the gantryList where active is greater than DEFAULT_ACTIVE
        defaultGantryShouldNotBeFound("active.greaterThan=" + DEFAULT_ACTIVE);

        // Get all the gantryList where active is greater than SMALLER_ACTIVE
        defaultGantryShouldBeFound("active.greaterThan=" + SMALLER_ACTIVE);
    }

    @Test
    @Transactional
    void getAllGantriesByPasswordIsEqualToSomething() throws Exception {
        // Initialize the database
        gantryRepository.saveAndFlush(gantry);

        // Get all the gantryList where password equals to DEFAULT_PASSWORD
        defaultGantryShouldBeFound("password.equals=" + DEFAULT_PASSWORD);

        // Get all the gantryList where password equals to UPDATED_PASSWORD
        defaultGantryShouldNotBeFound("password.equals=" + UPDATED_PASSWORD);
    }

    @Test
    @Transactional
    void getAllGantriesByPasswordIsInShouldWork() throws Exception {
        // Initialize the database
        gantryRepository.saveAndFlush(gantry);

        // Get all the gantryList where password in DEFAULT_PASSWORD or UPDATED_PASSWORD
        defaultGantryShouldBeFound("password.in=" + DEFAULT_PASSWORD + "," + UPDATED_PASSWORD);

        // Get all the gantryList where password equals to UPDATED_PASSWORD
        defaultGantryShouldNotBeFound("password.in=" + UPDATED_PASSWORD);
    }

    @Test
    @Transactional
    void getAllGantriesByPasswordIsNullOrNotNull() throws Exception {
        // Initialize the database
        gantryRepository.saveAndFlush(gantry);

        // Get all the gantryList where password is not null
        defaultGantryShouldBeFound("password.specified=true");

        // Get all the gantryList where password is null
        defaultGantryShouldNotBeFound("password.specified=false");
    }

    @Test
    @Transactional
    void getAllGantriesByPasswordContainsSomething() throws Exception {
        // Initialize the database
        gantryRepository.saveAndFlush(gantry);

        // Get all the gantryList where password contains DEFAULT_PASSWORD
        defaultGantryShouldBeFound("password.contains=" + DEFAULT_PASSWORD);

        // Get all the gantryList where password contains UPDATED_PASSWORD
        defaultGantryShouldNotBeFound("password.contains=" + UPDATED_PASSWORD);
    }

    @Test
    @Transactional
    void getAllGantriesByPasswordNotContainsSomething() throws Exception {
        // Initialize the database
        gantryRepository.saveAndFlush(gantry);

        // Get all the gantryList where password does not contain DEFAULT_PASSWORD
        defaultGantryShouldNotBeFound("password.doesNotContain=" + DEFAULT_PASSWORD);

        // Get all the gantryList where password does not contain UPDATED_PASSWORD
        defaultGantryShouldBeFound("password.doesNotContain=" + UPDATED_PASSWORD);
    }

    @Test
    @Transactional
    void getAllGantriesByUpdatedIsEqualToSomething() throws Exception {
        // Initialize the database
        gantryRepository.saveAndFlush(gantry);

        // Get all the gantryList where updated equals to DEFAULT_UPDATED
        defaultGantryShouldBeFound("updated.equals=" + DEFAULT_UPDATED);

        // Get all the gantryList where updated equals to UPDATED_UPDATED
        defaultGantryShouldNotBeFound("updated.equals=" + UPDATED_UPDATED);
    }

    @Test
    @Transactional
    void getAllGantriesByUpdatedIsInShouldWork() throws Exception {
        // Initialize the database
        gantryRepository.saveAndFlush(gantry);

        // Get all the gantryList where updated in DEFAULT_UPDATED or UPDATED_UPDATED
        defaultGantryShouldBeFound("updated.in=" + DEFAULT_UPDATED + "," + UPDATED_UPDATED);

        // Get all the gantryList where updated equals to UPDATED_UPDATED
        defaultGantryShouldNotBeFound("updated.in=" + UPDATED_UPDATED);
    }

    @Test
    @Transactional
    void getAllGantriesByUpdatedIsNullOrNotNull() throws Exception {
        // Initialize the database
        gantryRepository.saveAndFlush(gantry);

        // Get all the gantryList where updated is not null
        defaultGantryShouldBeFound("updated.specified=true");

        // Get all the gantryList where updated is null
        defaultGantryShouldNotBeFound("updated.specified=false");
    }

    @Test
    @Transactional
    void getAllGantriesByIpIsEqualToSomething() throws Exception {
        // Initialize the database
        gantryRepository.saveAndFlush(gantry);

        // Get all the gantryList where ip equals to DEFAULT_IP
        defaultGantryShouldBeFound("ip.equals=" + DEFAULT_IP);

        // Get all the gantryList where ip equals to UPDATED_IP
        defaultGantryShouldNotBeFound("ip.equals=" + UPDATED_IP);
    }

    @Test
    @Transactional
    void getAllGantriesByIpIsInShouldWork() throws Exception {
        // Initialize the database
        gantryRepository.saveAndFlush(gantry);

        // Get all the gantryList where ip in DEFAULT_IP or UPDATED_IP
        defaultGantryShouldBeFound("ip.in=" + DEFAULT_IP + "," + UPDATED_IP);

        // Get all the gantryList where ip equals to UPDATED_IP
        defaultGantryShouldNotBeFound("ip.in=" + UPDATED_IP);
    }

    @Test
    @Transactional
    void getAllGantriesByIpIsNullOrNotNull() throws Exception {
        // Initialize the database
        gantryRepository.saveAndFlush(gantry);

        // Get all the gantryList where ip is not null
        defaultGantryShouldBeFound("ip.specified=true");

        // Get all the gantryList where ip is null
        defaultGantryShouldNotBeFound("ip.specified=false");
    }

    @Test
    @Transactional
    void getAllGantriesByIpContainsSomething() throws Exception {
        // Initialize the database
        gantryRepository.saveAndFlush(gantry);

        // Get all the gantryList where ip contains DEFAULT_IP
        defaultGantryShouldBeFound("ip.contains=" + DEFAULT_IP);

        // Get all the gantryList where ip contains UPDATED_IP
        defaultGantryShouldNotBeFound("ip.contains=" + UPDATED_IP);
    }

    @Test
    @Transactional
    void getAllGantriesByIpNotContainsSomething() throws Exception {
        // Initialize the database
        gantryRepository.saveAndFlush(gantry);

        // Get all the gantryList where ip does not contain DEFAULT_IP
        defaultGantryShouldNotBeFound("ip.doesNotContain=" + DEFAULT_IP);

        // Get all the gantryList where ip does not contain UPDATED_IP
        defaultGantryShouldBeFound("ip.doesNotContain=" + UPDATED_IP);
    }

    @Test
    @Transactional
    void getAllGantriesByTollIsEqualToSomething() throws Exception {
        // Initialize the database
        gantryRepository.saveAndFlush(gantry);

        // Get all the gantryList where toll equals to DEFAULT_TOLL
        defaultGantryShouldBeFound("toll.equals=" + DEFAULT_TOLL);

        // Get all the gantryList where toll equals to UPDATED_TOLL
        defaultGantryShouldNotBeFound("toll.equals=" + UPDATED_TOLL);
    }

    @Test
    @Transactional
    void getAllGantriesByTollIsInShouldWork() throws Exception {
        // Initialize the database
        gantryRepository.saveAndFlush(gantry);

        // Get all the gantryList where toll in DEFAULT_TOLL or UPDATED_TOLL
        defaultGantryShouldBeFound("toll.in=" + DEFAULT_TOLL + "," + UPDATED_TOLL);

        // Get all the gantryList where toll equals to UPDATED_TOLL
        defaultGantryShouldNotBeFound("toll.in=" + UPDATED_TOLL);
    }

    @Test
    @Transactional
    void getAllGantriesByTollIsNullOrNotNull() throws Exception {
        // Initialize the database
        gantryRepository.saveAndFlush(gantry);

        // Get all the gantryList where toll is not null
        defaultGantryShouldBeFound("toll.specified=true");

        // Get all the gantryList where toll is null
        defaultGantryShouldNotBeFound("toll.specified=false");
    }

    @Test
    @Transactional
    void getAllGantriesByTollIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        gantryRepository.saveAndFlush(gantry);

        // Get all the gantryList where toll is greater than or equal to DEFAULT_TOLL
        defaultGantryShouldBeFound("toll.greaterThanOrEqual=" + DEFAULT_TOLL);

        // Get all the gantryList where toll is greater than or equal to UPDATED_TOLL
        defaultGantryShouldNotBeFound("toll.greaterThanOrEqual=" + UPDATED_TOLL);
    }

    @Test
    @Transactional
    void getAllGantriesByTollIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        gantryRepository.saveAndFlush(gantry);

        // Get all the gantryList where toll is less than or equal to DEFAULT_TOLL
        defaultGantryShouldBeFound("toll.lessThanOrEqual=" + DEFAULT_TOLL);

        // Get all the gantryList where toll is less than or equal to SMALLER_TOLL
        defaultGantryShouldNotBeFound("toll.lessThanOrEqual=" + SMALLER_TOLL);
    }

    @Test
    @Transactional
    void getAllGantriesByTollIsLessThanSomething() throws Exception {
        // Initialize the database
        gantryRepository.saveAndFlush(gantry);

        // Get all the gantryList where toll is less than DEFAULT_TOLL
        defaultGantryShouldNotBeFound("toll.lessThan=" + DEFAULT_TOLL);

        // Get all the gantryList where toll is less than UPDATED_TOLL
        defaultGantryShouldBeFound("toll.lessThan=" + UPDATED_TOLL);
    }

    @Test
    @Transactional
    void getAllGantriesByTollIsGreaterThanSomething() throws Exception {
        // Initialize the database
        gantryRepository.saveAndFlush(gantry);

        // Get all the gantryList where toll is greater than DEFAULT_TOLL
        defaultGantryShouldNotBeFound("toll.greaterThan=" + DEFAULT_TOLL);

        // Get all the gantryList where toll is greater than SMALLER_TOLL
        defaultGantryShouldBeFound("toll.greaterThan=" + SMALLER_TOLL);
    }

    @Test
    @Transactional
    void getAllGantriesByPassageTimesIsEqualToSomething() throws Exception {
        // Initialize the database
        gantryRepository.saveAndFlush(gantry);

        // Get all the gantryList where passageTimes equals to DEFAULT_PASSAGE_TIMES
        defaultGantryShouldBeFound("passageTimes.equals=" + DEFAULT_PASSAGE_TIMES);

        // Get all the gantryList where passageTimes equals to UPDATED_PASSAGE_TIMES
        defaultGantryShouldNotBeFound("passageTimes.equals=" + UPDATED_PASSAGE_TIMES);
    }

    @Test
    @Transactional
    void getAllGantriesByPassageTimesIsInShouldWork() throws Exception {
        // Initialize the database
        gantryRepository.saveAndFlush(gantry);

        // Get all the gantryList where passageTimes in DEFAULT_PASSAGE_TIMES or UPDATED_PASSAGE_TIMES
        defaultGantryShouldBeFound("passageTimes.in=" + DEFAULT_PASSAGE_TIMES + "," + UPDATED_PASSAGE_TIMES);

        // Get all the gantryList where passageTimes equals to UPDATED_PASSAGE_TIMES
        defaultGantryShouldNotBeFound("passageTimes.in=" + UPDATED_PASSAGE_TIMES);
    }

    @Test
    @Transactional
    void getAllGantriesByPassageTimesIsNullOrNotNull() throws Exception {
        // Initialize the database
        gantryRepository.saveAndFlush(gantry);

        // Get all the gantryList where passageTimes is not null
        defaultGantryShouldBeFound("passageTimes.specified=true");

        // Get all the gantryList where passageTimes is null
        defaultGantryShouldNotBeFound("passageTimes.specified=false");
    }

    @Test
    @Transactional
    void getAllGantriesByPassageTimesIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        gantryRepository.saveAndFlush(gantry);

        // Get all the gantryList where passageTimes is greater than or equal to DEFAULT_PASSAGE_TIMES
        defaultGantryShouldBeFound("passageTimes.greaterThanOrEqual=" + DEFAULT_PASSAGE_TIMES);

        // Get all the gantryList where passageTimes is greater than or equal to UPDATED_PASSAGE_TIMES
        defaultGantryShouldNotBeFound("passageTimes.greaterThanOrEqual=" + UPDATED_PASSAGE_TIMES);
    }

    @Test
    @Transactional
    void getAllGantriesByPassageTimesIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        gantryRepository.saveAndFlush(gantry);

        // Get all the gantryList where passageTimes is less than or equal to DEFAULT_PASSAGE_TIMES
        defaultGantryShouldBeFound("passageTimes.lessThanOrEqual=" + DEFAULT_PASSAGE_TIMES);

        // Get all the gantryList where passageTimes is less than or equal to SMALLER_PASSAGE_TIMES
        defaultGantryShouldNotBeFound("passageTimes.lessThanOrEqual=" + SMALLER_PASSAGE_TIMES);
    }

    @Test
    @Transactional
    void getAllGantriesByPassageTimesIsLessThanSomething() throws Exception {
        // Initialize the database
        gantryRepository.saveAndFlush(gantry);

        // Get all the gantryList where passageTimes is less than DEFAULT_PASSAGE_TIMES
        defaultGantryShouldNotBeFound("passageTimes.lessThan=" + DEFAULT_PASSAGE_TIMES);

        // Get all the gantryList where passageTimes is less than UPDATED_PASSAGE_TIMES
        defaultGantryShouldBeFound("passageTimes.lessThan=" + UPDATED_PASSAGE_TIMES);
    }

    @Test
    @Transactional
    void getAllGantriesByPassageTimesIsGreaterThanSomething() throws Exception {
        // Initialize the database
        gantryRepository.saveAndFlush(gantry);

        // Get all the gantryList where passageTimes is greater than DEFAULT_PASSAGE_TIMES
        defaultGantryShouldNotBeFound("passageTimes.greaterThan=" + DEFAULT_PASSAGE_TIMES);

        // Get all the gantryList where passageTimes is greater than SMALLER_PASSAGE_TIMES
        defaultGantryShouldBeFound("passageTimes.greaterThan=" + SMALLER_PASSAGE_TIMES);
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultGantryShouldBeFound(String filter) throws Exception {
        restGantryMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(gantry.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].route").value(hasItem(DEFAULT_ROUTE)))
            .andExpect(jsonPath("$.[*].lanes").value(hasItem(DEFAULT_LANES)))
            .andExpect(jsonPath("$.[*].longitude").value(hasItem(DEFAULT_LONGITUDE.doubleValue())))
            .andExpect(jsonPath("$.[*].lat").value(hasItem(DEFAULT_LAT.doubleValue())))
            .andExpect(jsonPath("$.[*].jurisdiction").value(hasItem(DEFAULT_JURISDICTION)))
            .andExpect(jsonPath("$.[*].gantryType").value(hasItem(DEFAULT_GANTRY_TYPE)))
            .andExpect(jsonPath("$.[*].gantrySet").value(hasItem(DEFAULT_GANTRY_SET)))
            .andExpect(jsonPath("$.[*].active").value(hasItem(DEFAULT_ACTIVE)))
            .andExpect(jsonPath("$.[*].password").value(hasItem(DEFAULT_PASSWORD)))
            .andExpect(jsonPath("$.[*].updated").value(hasItem(DEFAULT_UPDATED.toString())))
            .andExpect(jsonPath("$.[*].ip").value(hasItem(DEFAULT_IP)))
            .andExpect(jsonPath("$.[*].toll").value(hasItem(DEFAULT_TOLL.doubleValue())))
            .andExpect(jsonPath("$.[*].passageTimes").value(hasItem(DEFAULT_PASSAGE_TIMES)));

        // Check, that the count call also returns 1
        restGantryMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultGantryShouldNotBeFound(String filter) throws Exception {
        restGantryMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restGantryMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    void getNonExistingGantry() throws Exception {
        // Get the gantry
        restGantryMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingGantry() throws Exception {
        // Initialize the database
        gantryRepository.saveAndFlush(gantry);

        int databaseSizeBeforeUpdate = gantryRepository.findAll().size();

        // Update the gantry
        Gantry updatedGantry = gantryRepository.findById(gantry.getId()).get();
        // Disconnect from session so that the updates on updatedGantry are not directly saved in db
        em.detach(updatedGantry);
        updatedGantry
            .name(UPDATED_NAME)
            .route(UPDATED_ROUTE)
            .lanes(UPDATED_LANES)
            .longitude(UPDATED_LONGITUDE)
            .lat(UPDATED_LAT)
            .jurisdiction(UPDATED_JURISDICTION)
            .gantryType(UPDATED_GANTRY_TYPE)
            .gantrySet(UPDATED_GANTRY_SET)
            .active(UPDATED_ACTIVE)
            .password(UPDATED_PASSWORD)
            .updated(UPDATED_UPDATED)
            .ip(UPDATED_IP)
            .toll(UPDATED_TOLL)
            .passageTimes(UPDATED_PASSAGE_TIMES);
        GantryDTO gantryDTO = gantryMapper.toDto(updatedGantry);

        restGantryMockMvc
            .perform(
                put(ENTITY_API_URL_ID, gantryDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(gantryDTO))
            )
            .andExpect(status().isOk());

        // Validate the Gantry in the database
        List<Gantry> gantryList = gantryRepository.findAll();
        assertThat(gantryList).hasSize(databaseSizeBeforeUpdate);
        Gantry testGantry = gantryList.get(gantryList.size() - 1);
        assertThat(testGantry.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testGantry.getRoute()).isEqualTo(UPDATED_ROUTE);
        assertThat(testGantry.getLanes()).isEqualTo(UPDATED_LANES);
        assertThat(testGantry.getLongitude()).isEqualTo(UPDATED_LONGITUDE);
        assertThat(testGantry.getLat()).isEqualTo(UPDATED_LAT);
        assertThat(testGantry.getJurisdiction()).isEqualTo(UPDATED_JURISDICTION);
        assertThat(testGantry.getGantryType()).isEqualTo(UPDATED_GANTRY_TYPE);
        assertThat(testGantry.getGantrySet()).isEqualTo(UPDATED_GANTRY_SET);
        assertThat(testGantry.getActive()).isEqualTo(UPDATED_ACTIVE);
        assertThat(testGantry.getPassword()).isEqualTo(UPDATED_PASSWORD);
        assertThat(testGantry.getUpdated()).isEqualTo(UPDATED_UPDATED);
        assertThat(testGantry.getIp()).isEqualTo(UPDATED_IP);
        assertThat(testGantry.getToll()).isEqualTo(UPDATED_TOLL);
        assertThat(testGantry.getPassageTimes()).isEqualTo(UPDATED_PASSAGE_TIMES);
    }

    @Test
    @Transactional
    void putNonExistingGantry() throws Exception {
        int databaseSizeBeforeUpdate = gantryRepository.findAll().size();
        gantry.setId(count.incrementAndGet());

        // Create the Gantry
        GantryDTO gantryDTO = gantryMapper.toDto(gantry);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restGantryMockMvc
            .perform(
                put(ENTITY_API_URL_ID, gantryDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(gantryDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Gantry in the database
        List<Gantry> gantryList = gantryRepository.findAll();
        assertThat(gantryList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchGantry() throws Exception {
        int databaseSizeBeforeUpdate = gantryRepository.findAll().size();
        gantry.setId(count.incrementAndGet());

        // Create the Gantry
        GantryDTO gantryDTO = gantryMapper.toDto(gantry);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restGantryMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(gantryDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Gantry in the database
        List<Gantry> gantryList = gantryRepository.findAll();
        assertThat(gantryList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamGantry() throws Exception {
        int databaseSizeBeforeUpdate = gantryRepository.findAll().size();
        gantry.setId(count.incrementAndGet());

        // Create the Gantry
        GantryDTO gantryDTO = gantryMapper.toDto(gantry);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restGantryMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(gantryDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Gantry in the database
        List<Gantry> gantryList = gantryRepository.findAll();
        assertThat(gantryList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateGantryWithPatch() throws Exception {
        // Initialize the database
        gantryRepository.saveAndFlush(gantry);

        int databaseSizeBeforeUpdate = gantryRepository.findAll().size();

        // Update the gantry using partial update
        Gantry partialUpdatedGantry = new Gantry();
        partialUpdatedGantry.setId(gantry.getId());

        partialUpdatedGantry
            .name(UPDATED_NAME)
            .jurisdiction(UPDATED_JURISDICTION)
            .gantryType(UPDATED_GANTRY_TYPE)
            .gantrySet(UPDATED_GANTRY_SET)
            .active(UPDATED_ACTIVE)
            .password(UPDATED_PASSWORD);

        restGantryMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedGantry.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedGantry))
            )
            .andExpect(status().isOk());

        // Validate the Gantry in the database
        List<Gantry> gantryList = gantryRepository.findAll();
        assertThat(gantryList).hasSize(databaseSizeBeforeUpdate);
        Gantry testGantry = gantryList.get(gantryList.size() - 1);
        assertThat(testGantry.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testGantry.getRoute()).isEqualTo(DEFAULT_ROUTE);
        assertThat(testGantry.getLanes()).isEqualTo(DEFAULT_LANES);
        assertThat(testGantry.getLongitude()).isEqualTo(DEFAULT_LONGITUDE);
        assertThat(testGantry.getLat()).isEqualTo(DEFAULT_LAT);
        assertThat(testGantry.getJurisdiction()).isEqualTo(UPDATED_JURISDICTION);
        assertThat(testGantry.getGantryType()).isEqualTo(UPDATED_GANTRY_TYPE);
        assertThat(testGantry.getGantrySet()).isEqualTo(UPDATED_GANTRY_SET);
        assertThat(testGantry.getActive()).isEqualTo(UPDATED_ACTIVE);
        assertThat(testGantry.getPassword()).isEqualTo(UPDATED_PASSWORD);
        assertThat(testGantry.getUpdated()).isEqualTo(DEFAULT_UPDATED);
        assertThat(testGantry.getIp()).isEqualTo(DEFAULT_IP);
        assertThat(testGantry.getToll()).isEqualTo(DEFAULT_TOLL);
        assertThat(testGantry.getPassageTimes()).isEqualTo(DEFAULT_PASSAGE_TIMES);
    }

    @Test
    @Transactional
    void fullUpdateGantryWithPatch() throws Exception {
        // Initialize the database
        gantryRepository.saveAndFlush(gantry);

        int databaseSizeBeforeUpdate = gantryRepository.findAll().size();

        // Update the gantry using partial update
        Gantry partialUpdatedGantry = new Gantry();
        partialUpdatedGantry.setId(gantry.getId());

        partialUpdatedGantry
            .name(UPDATED_NAME)
            .route(UPDATED_ROUTE)
            .lanes(UPDATED_LANES)
            .longitude(UPDATED_LONGITUDE)
            .lat(UPDATED_LAT)
            .jurisdiction(UPDATED_JURISDICTION)
            .gantryType(UPDATED_GANTRY_TYPE)
            .gantrySet(UPDATED_GANTRY_SET)
            .active(UPDATED_ACTIVE)
            .password(UPDATED_PASSWORD)
            .updated(UPDATED_UPDATED)
            .ip(UPDATED_IP)
            .toll(UPDATED_TOLL)
            .passageTimes(UPDATED_PASSAGE_TIMES);

        restGantryMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedGantry.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedGantry))
            )
            .andExpect(status().isOk());

        // Validate the Gantry in the database
        List<Gantry> gantryList = gantryRepository.findAll();
        assertThat(gantryList).hasSize(databaseSizeBeforeUpdate);
        Gantry testGantry = gantryList.get(gantryList.size() - 1);
        assertThat(testGantry.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testGantry.getRoute()).isEqualTo(UPDATED_ROUTE);
        assertThat(testGantry.getLanes()).isEqualTo(UPDATED_LANES);
        assertThat(testGantry.getLongitude()).isEqualTo(UPDATED_LONGITUDE);
        assertThat(testGantry.getLat()).isEqualTo(UPDATED_LAT);
        assertThat(testGantry.getJurisdiction()).isEqualTo(UPDATED_JURISDICTION);
        assertThat(testGantry.getGantryType()).isEqualTo(UPDATED_GANTRY_TYPE);
        assertThat(testGantry.getGantrySet()).isEqualTo(UPDATED_GANTRY_SET);
        assertThat(testGantry.getActive()).isEqualTo(UPDATED_ACTIVE);
        assertThat(testGantry.getPassword()).isEqualTo(UPDATED_PASSWORD);
        assertThat(testGantry.getUpdated()).isEqualTo(UPDATED_UPDATED);
        assertThat(testGantry.getIp()).isEqualTo(UPDATED_IP);
        assertThat(testGantry.getToll()).isEqualTo(UPDATED_TOLL);
        assertThat(testGantry.getPassageTimes()).isEqualTo(UPDATED_PASSAGE_TIMES);
    }

    @Test
    @Transactional
    void patchNonExistingGantry() throws Exception {
        int databaseSizeBeforeUpdate = gantryRepository.findAll().size();
        gantry.setId(count.incrementAndGet());

        // Create the Gantry
        GantryDTO gantryDTO = gantryMapper.toDto(gantry);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restGantryMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, gantryDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(gantryDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Gantry in the database
        List<Gantry> gantryList = gantryRepository.findAll();
        assertThat(gantryList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchGantry() throws Exception {
        int databaseSizeBeforeUpdate = gantryRepository.findAll().size();
        gantry.setId(count.incrementAndGet());

        // Create the Gantry
        GantryDTO gantryDTO = gantryMapper.toDto(gantry);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restGantryMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(gantryDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Gantry in the database
        List<Gantry> gantryList = gantryRepository.findAll();
        assertThat(gantryList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamGantry() throws Exception {
        int databaseSizeBeforeUpdate = gantryRepository.findAll().size();
        gantry.setId(count.incrementAndGet());

        // Create the Gantry
        GantryDTO gantryDTO = gantryMapper.toDto(gantry);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restGantryMockMvc
            .perform(
                patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(gantryDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the Gantry in the database
        List<Gantry> gantryList = gantryRepository.findAll();
        assertThat(gantryList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteGantry() throws Exception {
        // Initialize the database
        gantryRepository.saveAndFlush(gantry);

        int databaseSizeBeforeDelete = gantryRepository.findAll().size();

        // Delete the gantry
        restGantryMockMvc
            .perform(delete(ENTITY_API_URL_ID, gantry.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Gantry> gantryList = gantryRepository.findAll();
        assertThat(gantryList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
