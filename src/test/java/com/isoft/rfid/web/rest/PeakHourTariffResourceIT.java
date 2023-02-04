package com.isoft.rfid.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.isoft.rfid.IntegrationTest;
import com.isoft.rfid.domain.PeakHourTariff;
import com.isoft.rfid.repository.PeakHourTariffRepository;
import com.isoft.rfid.service.criteria.PeakHourTariffCriteria;
import com.isoft.rfid.service.dto.PeakHourTariffDTO;
import com.isoft.rfid.service.mapper.PeakHourTariffMapper;
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
 * Integration tests for the {@link PeakHourTariffResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class PeakHourTariffResourceIT {

    private static final Instant DEFAULT_PEAK_HOUR_FROM = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_PEAK_HOUR_FROM = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Instant DEFAULT_PEAK_HOUR_TO = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_PEAK_HOUR_TO = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Integer DEFAULT_GANTRY = 1;
    private static final Integer UPDATED_GANTRY = 2;
    private static final Integer SMALLER_GANTRY = 1 - 1;

    private static final Double DEFAULT_GANTRY_TOLL = 1D;
    private static final Double UPDATED_GANTRY_TOLL = 2D;
    private static final Double SMALLER_GANTRY_TOLL = 1D - 1D;

    private static final Integer DEFAULT_ACTIVE = 1;
    private static final Integer UPDATED_ACTIVE = 2;
    private static final Integer SMALLER_ACTIVE = 1 - 1;

    private static final Instant DEFAULT_UPDATED = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_UPDATED = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final String ENTITY_API_URL = "/api/peak-hour-tariffs";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private PeakHourTariffRepository peakHourTariffRepository;

    @Autowired
    private PeakHourTariffMapper peakHourTariffMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restPeakHourTariffMockMvc;

    private PeakHourTariff peakHourTariff;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static PeakHourTariff createEntity(EntityManager em) {
        PeakHourTariff peakHourTariff = new PeakHourTariff()
            .peakHourFrom(DEFAULT_PEAK_HOUR_FROM)
            .peakHourTo(DEFAULT_PEAK_HOUR_TO)
            .gantry(DEFAULT_GANTRY)
            .gantryToll(DEFAULT_GANTRY_TOLL)
            .active(DEFAULT_ACTIVE)
            .updated(DEFAULT_UPDATED);
        return peakHourTariff;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static PeakHourTariff createUpdatedEntity(EntityManager em) {
        PeakHourTariff peakHourTariff = new PeakHourTariff()
            .peakHourFrom(UPDATED_PEAK_HOUR_FROM)
            .peakHourTo(UPDATED_PEAK_HOUR_TO)
            .gantry(UPDATED_GANTRY)
            .gantryToll(UPDATED_GANTRY_TOLL)
            .active(UPDATED_ACTIVE)
            .updated(UPDATED_UPDATED);
        return peakHourTariff;
    }

    @BeforeEach
    public void initTest() {
        peakHourTariff = createEntity(em);
    }

    @Test
    @Transactional
    void createPeakHourTariff() throws Exception {
        int databaseSizeBeforeCreate = peakHourTariffRepository.findAll().size();
        // Create the PeakHourTariff
        PeakHourTariffDTO peakHourTariffDTO = peakHourTariffMapper.toDto(peakHourTariff);
        restPeakHourTariffMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(peakHourTariffDTO))
            )
            .andExpect(status().isCreated());

        // Validate the PeakHourTariff in the database
        List<PeakHourTariff> peakHourTariffList = peakHourTariffRepository.findAll();
        assertThat(peakHourTariffList).hasSize(databaseSizeBeforeCreate + 1);
        PeakHourTariff testPeakHourTariff = peakHourTariffList.get(peakHourTariffList.size() - 1);
        assertThat(testPeakHourTariff.getPeakHourFrom()).isEqualTo(DEFAULT_PEAK_HOUR_FROM);
        assertThat(testPeakHourTariff.getPeakHourTo()).isEqualTo(DEFAULT_PEAK_HOUR_TO);
        assertThat(testPeakHourTariff.getGantry()).isEqualTo(DEFAULT_GANTRY);
        assertThat(testPeakHourTariff.getGantryToll()).isEqualTo(DEFAULT_GANTRY_TOLL);
        assertThat(testPeakHourTariff.getActive()).isEqualTo(DEFAULT_ACTIVE);
        assertThat(testPeakHourTariff.getUpdated()).isEqualTo(DEFAULT_UPDATED);
    }

    @Test
    @Transactional
    void createPeakHourTariffWithExistingId() throws Exception {
        // Create the PeakHourTariff with an existing ID
        peakHourTariff.setId(1L);
        PeakHourTariffDTO peakHourTariffDTO = peakHourTariffMapper.toDto(peakHourTariff);

        int databaseSizeBeforeCreate = peakHourTariffRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restPeakHourTariffMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(peakHourTariffDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the PeakHourTariff in the database
        List<PeakHourTariff> peakHourTariffList = peakHourTariffRepository.findAll();
        assertThat(peakHourTariffList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkPeakHourFromIsRequired() throws Exception {
        int databaseSizeBeforeTest = peakHourTariffRepository.findAll().size();
        // set the field null
        peakHourTariff.setPeakHourFrom(null);

        // Create the PeakHourTariff, which fails.
        PeakHourTariffDTO peakHourTariffDTO = peakHourTariffMapper.toDto(peakHourTariff);

        restPeakHourTariffMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(peakHourTariffDTO))
            )
            .andExpect(status().isBadRequest());

        List<PeakHourTariff> peakHourTariffList = peakHourTariffRepository.findAll();
        assertThat(peakHourTariffList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkPeakHourToIsRequired() throws Exception {
        int databaseSizeBeforeTest = peakHourTariffRepository.findAll().size();
        // set the field null
        peakHourTariff.setPeakHourTo(null);

        // Create the PeakHourTariff, which fails.
        PeakHourTariffDTO peakHourTariffDTO = peakHourTariffMapper.toDto(peakHourTariff);

        restPeakHourTariffMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(peakHourTariffDTO))
            )
            .andExpect(status().isBadRequest());

        List<PeakHourTariff> peakHourTariffList = peakHourTariffRepository.findAll();
        assertThat(peakHourTariffList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkGantryTollIsRequired() throws Exception {
        int databaseSizeBeforeTest = peakHourTariffRepository.findAll().size();
        // set the field null
        peakHourTariff.setGantryToll(null);

        // Create the PeakHourTariff, which fails.
        PeakHourTariffDTO peakHourTariffDTO = peakHourTariffMapper.toDto(peakHourTariff);

        restPeakHourTariffMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(peakHourTariffDTO))
            )
            .andExpect(status().isBadRequest());

        List<PeakHourTariff> peakHourTariffList = peakHourTariffRepository.findAll();
        assertThat(peakHourTariffList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkActiveIsRequired() throws Exception {
        int databaseSizeBeforeTest = peakHourTariffRepository.findAll().size();
        // set the field null
        peakHourTariff.setActive(null);

        // Create the PeakHourTariff, which fails.
        PeakHourTariffDTO peakHourTariffDTO = peakHourTariffMapper.toDto(peakHourTariff);

        restPeakHourTariffMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(peakHourTariffDTO))
            )
            .andExpect(status().isBadRequest());

        List<PeakHourTariff> peakHourTariffList = peakHourTariffRepository.findAll();
        assertThat(peakHourTariffList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkUpdatedIsRequired() throws Exception {
        int databaseSizeBeforeTest = peakHourTariffRepository.findAll().size();
        // set the field null
        peakHourTariff.setUpdated(null);

        // Create the PeakHourTariff, which fails.
        PeakHourTariffDTO peakHourTariffDTO = peakHourTariffMapper.toDto(peakHourTariff);

        restPeakHourTariffMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(peakHourTariffDTO))
            )
            .andExpect(status().isBadRequest());

        List<PeakHourTariff> peakHourTariffList = peakHourTariffRepository.findAll();
        assertThat(peakHourTariffList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllPeakHourTariffs() throws Exception {
        // Initialize the database
        peakHourTariffRepository.saveAndFlush(peakHourTariff);

        // Get all the peakHourTariffList
        restPeakHourTariffMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(peakHourTariff.getId().intValue())))
            .andExpect(jsonPath("$.[*].peakHourFrom").value(hasItem(DEFAULT_PEAK_HOUR_FROM.toString())))
            .andExpect(jsonPath("$.[*].peakHourTo").value(hasItem(DEFAULT_PEAK_HOUR_TO.toString())))
            .andExpect(jsonPath("$.[*].gantry").value(hasItem(DEFAULT_GANTRY)))
            .andExpect(jsonPath("$.[*].gantryToll").value(hasItem(DEFAULT_GANTRY_TOLL.doubleValue())))
            .andExpect(jsonPath("$.[*].active").value(hasItem(DEFAULT_ACTIVE)))
            .andExpect(jsonPath("$.[*].updated").value(hasItem(DEFAULT_UPDATED.toString())));
    }

    @Test
    @Transactional
    void getPeakHourTariff() throws Exception {
        // Initialize the database
        peakHourTariffRepository.saveAndFlush(peakHourTariff);

        // Get the peakHourTariff
        restPeakHourTariffMockMvc
            .perform(get(ENTITY_API_URL_ID, peakHourTariff.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(peakHourTariff.getId().intValue()))
            .andExpect(jsonPath("$.peakHourFrom").value(DEFAULT_PEAK_HOUR_FROM.toString()))
            .andExpect(jsonPath("$.peakHourTo").value(DEFAULT_PEAK_HOUR_TO.toString()))
            .andExpect(jsonPath("$.gantry").value(DEFAULT_GANTRY))
            .andExpect(jsonPath("$.gantryToll").value(DEFAULT_GANTRY_TOLL.doubleValue()))
            .andExpect(jsonPath("$.active").value(DEFAULT_ACTIVE))
            .andExpect(jsonPath("$.updated").value(DEFAULT_UPDATED.toString()));
    }

    @Test
    @Transactional
    void getPeakHourTariffsByIdFiltering() throws Exception {
        // Initialize the database
        peakHourTariffRepository.saveAndFlush(peakHourTariff);

        Long id = peakHourTariff.getId();

        defaultPeakHourTariffShouldBeFound("id.equals=" + id);
        defaultPeakHourTariffShouldNotBeFound("id.notEquals=" + id);

        defaultPeakHourTariffShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultPeakHourTariffShouldNotBeFound("id.greaterThan=" + id);

        defaultPeakHourTariffShouldBeFound("id.lessThanOrEqual=" + id);
        defaultPeakHourTariffShouldNotBeFound("id.lessThan=" + id);
    }

    @Test
    @Transactional
    void getAllPeakHourTariffsByPeakHourFromIsEqualToSomething() throws Exception {
        // Initialize the database
        peakHourTariffRepository.saveAndFlush(peakHourTariff);

        // Get all the peakHourTariffList where peakHourFrom equals to DEFAULT_PEAK_HOUR_FROM
        defaultPeakHourTariffShouldBeFound("peakHourFrom.equals=" + DEFAULT_PEAK_HOUR_FROM);

        // Get all the peakHourTariffList where peakHourFrom equals to UPDATED_PEAK_HOUR_FROM
        defaultPeakHourTariffShouldNotBeFound("peakHourFrom.equals=" + UPDATED_PEAK_HOUR_FROM);
    }

    @Test
    @Transactional
    void getAllPeakHourTariffsByPeakHourFromIsInShouldWork() throws Exception {
        // Initialize the database
        peakHourTariffRepository.saveAndFlush(peakHourTariff);

        // Get all the peakHourTariffList where peakHourFrom in DEFAULT_PEAK_HOUR_FROM or UPDATED_PEAK_HOUR_FROM
        defaultPeakHourTariffShouldBeFound("peakHourFrom.in=" + DEFAULT_PEAK_HOUR_FROM + "," + UPDATED_PEAK_HOUR_FROM);

        // Get all the peakHourTariffList where peakHourFrom equals to UPDATED_PEAK_HOUR_FROM
        defaultPeakHourTariffShouldNotBeFound("peakHourFrom.in=" + UPDATED_PEAK_HOUR_FROM);
    }

    @Test
    @Transactional
    void getAllPeakHourTariffsByPeakHourFromIsNullOrNotNull() throws Exception {
        // Initialize the database
        peakHourTariffRepository.saveAndFlush(peakHourTariff);

        // Get all the peakHourTariffList where peakHourFrom is not null
        defaultPeakHourTariffShouldBeFound("peakHourFrom.specified=true");

        // Get all the peakHourTariffList where peakHourFrom is null
        defaultPeakHourTariffShouldNotBeFound("peakHourFrom.specified=false");
    }

    @Test
    @Transactional
    void getAllPeakHourTariffsByPeakHourToIsEqualToSomething() throws Exception {
        // Initialize the database
        peakHourTariffRepository.saveAndFlush(peakHourTariff);

        // Get all the peakHourTariffList where peakHourTo equals to DEFAULT_PEAK_HOUR_TO
        defaultPeakHourTariffShouldBeFound("peakHourTo.equals=" + DEFAULT_PEAK_HOUR_TO);

        // Get all the peakHourTariffList where peakHourTo equals to UPDATED_PEAK_HOUR_TO
        defaultPeakHourTariffShouldNotBeFound("peakHourTo.equals=" + UPDATED_PEAK_HOUR_TO);
    }

    @Test
    @Transactional
    void getAllPeakHourTariffsByPeakHourToIsInShouldWork() throws Exception {
        // Initialize the database
        peakHourTariffRepository.saveAndFlush(peakHourTariff);

        // Get all the peakHourTariffList where peakHourTo in DEFAULT_PEAK_HOUR_TO or UPDATED_PEAK_HOUR_TO
        defaultPeakHourTariffShouldBeFound("peakHourTo.in=" + DEFAULT_PEAK_HOUR_TO + "," + UPDATED_PEAK_HOUR_TO);

        // Get all the peakHourTariffList where peakHourTo equals to UPDATED_PEAK_HOUR_TO
        defaultPeakHourTariffShouldNotBeFound("peakHourTo.in=" + UPDATED_PEAK_HOUR_TO);
    }

    @Test
    @Transactional
    void getAllPeakHourTariffsByPeakHourToIsNullOrNotNull() throws Exception {
        // Initialize the database
        peakHourTariffRepository.saveAndFlush(peakHourTariff);

        // Get all the peakHourTariffList where peakHourTo is not null
        defaultPeakHourTariffShouldBeFound("peakHourTo.specified=true");

        // Get all the peakHourTariffList where peakHourTo is null
        defaultPeakHourTariffShouldNotBeFound("peakHourTo.specified=false");
    }

    @Test
    @Transactional
    void getAllPeakHourTariffsByGantryIsEqualToSomething() throws Exception {
        // Initialize the database
        peakHourTariffRepository.saveAndFlush(peakHourTariff);

        // Get all the peakHourTariffList where gantry equals to DEFAULT_GANTRY
        defaultPeakHourTariffShouldBeFound("gantry.equals=" + DEFAULT_GANTRY);

        // Get all the peakHourTariffList where gantry equals to UPDATED_GANTRY
        defaultPeakHourTariffShouldNotBeFound("gantry.equals=" + UPDATED_GANTRY);
    }

    @Test
    @Transactional
    void getAllPeakHourTariffsByGantryIsInShouldWork() throws Exception {
        // Initialize the database
        peakHourTariffRepository.saveAndFlush(peakHourTariff);

        // Get all the peakHourTariffList where gantry in DEFAULT_GANTRY or UPDATED_GANTRY
        defaultPeakHourTariffShouldBeFound("gantry.in=" + DEFAULT_GANTRY + "," + UPDATED_GANTRY);

        // Get all the peakHourTariffList where gantry equals to UPDATED_GANTRY
        defaultPeakHourTariffShouldNotBeFound("gantry.in=" + UPDATED_GANTRY);
    }

    @Test
    @Transactional
    void getAllPeakHourTariffsByGantryIsNullOrNotNull() throws Exception {
        // Initialize the database
        peakHourTariffRepository.saveAndFlush(peakHourTariff);

        // Get all the peakHourTariffList where gantry is not null
        defaultPeakHourTariffShouldBeFound("gantry.specified=true");

        // Get all the peakHourTariffList where gantry is null
        defaultPeakHourTariffShouldNotBeFound("gantry.specified=false");
    }

    @Test
    @Transactional
    void getAllPeakHourTariffsByGantryIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        peakHourTariffRepository.saveAndFlush(peakHourTariff);

        // Get all the peakHourTariffList where gantry is greater than or equal to DEFAULT_GANTRY
        defaultPeakHourTariffShouldBeFound("gantry.greaterThanOrEqual=" + DEFAULT_GANTRY);

        // Get all the peakHourTariffList where gantry is greater than or equal to UPDATED_GANTRY
        defaultPeakHourTariffShouldNotBeFound("gantry.greaterThanOrEqual=" + UPDATED_GANTRY);
    }

    @Test
    @Transactional
    void getAllPeakHourTariffsByGantryIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        peakHourTariffRepository.saveAndFlush(peakHourTariff);

        // Get all the peakHourTariffList where gantry is less than or equal to DEFAULT_GANTRY
        defaultPeakHourTariffShouldBeFound("gantry.lessThanOrEqual=" + DEFAULT_GANTRY);

        // Get all the peakHourTariffList where gantry is less than or equal to SMALLER_GANTRY
        defaultPeakHourTariffShouldNotBeFound("gantry.lessThanOrEqual=" + SMALLER_GANTRY);
    }

    @Test
    @Transactional
    void getAllPeakHourTariffsByGantryIsLessThanSomething() throws Exception {
        // Initialize the database
        peakHourTariffRepository.saveAndFlush(peakHourTariff);

        // Get all the peakHourTariffList where gantry is less than DEFAULT_GANTRY
        defaultPeakHourTariffShouldNotBeFound("gantry.lessThan=" + DEFAULT_GANTRY);

        // Get all the peakHourTariffList where gantry is less than UPDATED_GANTRY
        defaultPeakHourTariffShouldBeFound("gantry.lessThan=" + UPDATED_GANTRY);
    }

    @Test
    @Transactional
    void getAllPeakHourTariffsByGantryIsGreaterThanSomething() throws Exception {
        // Initialize the database
        peakHourTariffRepository.saveAndFlush(peakHourTariff);

        // Get all the peakHourTariffList where gantry is greater than DEFAULT_GANTRY
        defaultPeakHourTariffShouldNotBeFound("gantry.greaterThan=" + DEFAULT_GANTRY);

        // Get all the peakHourTariffList where gantry is greater than SMALLER_GANTRY
        defaultPeakHourTariffShouldBeFound("gantry.greaterThan=" + SMALLER_GANTRY);
    }

    @Test
    @Transactional
    void getAllPeakHourTariffsByGantryTollIsEqualToSomething() throws Exception {
        // Initialize the database
        peakHourTariffRepository.saveAndFlush(peakHourTariff);

        // Get all the peakHourTariffList where gantryToll equals to DEFAULT_GANTRY_TOLL
        defaultPeakHourTariffShouldBeFound("gantryToll.equals=" + DEFAULT_GANTRY_TOLL);

        // Get all the peakHourTariffList where gantryToll equals to UPDATED_GANTRY_TOLL
        defaultPeakHourTariffShouldNotBeFound("gantryToll.equals=" + UPDATED_GANTRY_TOLL);
    }

    @Test
    @Transactional
    void getAllPeakHourTariffsByGantryTollIsInShouldWork() throws Exception {
        // Initialize the database
        peakHourTariffRepository.saveAndFlush(peakHourTariff);

        // Get all the peakHourTariffList where gantryToll in DEFAULT_GANTRY_TOLL or UPDATED_GANTRY_TOLL
        defaultPeakHourTariffShouldBeFound("gantryToll.in=" + DEFAULT_GANTRY_TOLL + "," + UPDATED_GANTRY_TOLL);

        // Get all the peakHourTariffList where gantryToll equals to UPDATED_GANTRY_TOLL
        defaultPeakHourTariffShouldNotBeFound("gantryToll.in=" + UPDATED_GANTRY_TOLL);
    }

    @Test
    @Transactional
    void getAllPeakHourTariffsByGantryTollIsNullOrNotNull() throws Exception {
        // Initialize the database
        peakHourTariffRepository.saveAndFlush(peakHourTariff);

        // Get all the peakHourTariffList where gantryToll is not null
        defaultPeakHourTariffShouldBeFound("gantryToll.specified=true");

        // Get all the peakHourTariffList where gantryToll is null
        defaultPeakHourTariffShouldNotBeFound("gantryToll.specified=false");
    }

    @Test
    @Transactional
    void getAllPeakHourTariffsByGantryTollIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        peakHourTariffRepository.saveAndFlush(peakHourTariff);

        // Get all the peakHourTariffList where gantryToll is greater than or equal to DEFAULT_GANTRY_TOLL
        defaultPeakHourTariffShouldBeFound("gantryToll.greaterThanOrEqual=" + DEFAULT_GANTRY_TOLL);

        // Get all the peakHourTariffList where gantryToll is greater than or equal to UPDATED_GANTRY_TOLL
        defaultPeakHourTariffShouldNotBeFound("gantryToll.greaterThanOrEqual=" + UPDATED_GANTRY_TOLL);
    }

    @Test
    @Transactional
    void getAllPeakHourTariffsByGantryTollIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        peakHourTariffRepository.saveAndFlush(peakHourTariff);

        // Get all the peakHourTariffList where gantryToll is less than or equal to DEFAULT_GANTRY_TOLL
        defaultPeakHourTariffShouldBeFound("gantryToll.lessThanOrEqual=" + DEFAULT_GANTRY_TOLL);

        // Get all the peakHourTariffList where gantryToll is less than or equal to SMALLER_GANTRY_TOLL
        defaultPeakHourTariffShouldNotBeFound("gantryToll.lessThanOrEqual=" + SMALLER_GANTRY_TOLL);
    }

    @Test
    @Transactional
    void getAllPeakHourTariffsByGantryTollIsLessThanSomething() throws Exception {
        // Initialize the database
        peakHourTariffRepository.saveAndFlush(peakHourTariff);

        // Get all the peakHourTariffList where gantryToll is less than DEFAULT_GANTRY_TOLL
        defaultPeakHourTariffShouldNotBeFound("gantryToll.lessThan=" + DEFAULT_GANTRY_TOLL);

        // Get all the peakHourTariffList where gantryToll is less than UPDATED_GANTRY_TOLL
        defaultPeakHourTariffShouldBeFound("gantryToll.lessThan=" + UPDATED_GANTRY_TOLL);
    }

    @Test
    @Transactional
    void getAllPeakHourTariffsByGantryTollIsGreaterThanSomething() throws Exception {
        // Initialize the database
        peakHourTariffRepository.saveAndFlush(peakHourTariff);

        // Get all the peakHourTariffList where gantryToll is greater than DEFAULT_GANTRY_TOLL
        defaultPeakHourTariffShouldNotBeFound("gantryToll.greaterThan=" + DEFAULT_GANTRY_TOLL);

        // Get all the peakHourTariffList where gantryToll is greater than SMALLER_GANTRY_TOLL
        defaultPeakHourTariffShouldBeFound("gantryToll.greaterThan=" + SMALLER_GANTRY_TOLL);
    }

    @Test
    @Transactional
    void getAllPeakHourTariffsByActiveIsEqualToSomething() throws Exception {
        // Initialize the database
        peakHourTariffRepository.saveAndFlush(peakHourTariff);

        // Get all the peakHourTariffList where active equals to DEFAULT_ACTIVE
        defaultPeakHourTariffShouldBeFound("active.equals=" + DEFAULT_ACTIVE);

        // Get all the peakHourTariffList where active equals to UPDATED_ACTIVE
        defaultPeakHourTariffShouldNotBeFound("active.equals=" + UPDATED_ACTIVE);
    }

    @Test
    @Transactional
    void getAllPeakHourTariffsByActiveIsInShouldWork() throws Exception {
        // Initialize the database
        peakHourTariffRepository.saveAndFlush(peakHourTariff);

        // Get all the peakHourTariffList where active in DEFAULT_ACTIVE or UPDATED_ACTIVE
        defaultPeakHourTariffShouldBeFound("active.in=" + DEFAULT_ACTIVE + "," + UPDATED_ACTIVE);

        // Get all the peakHourTariffList where active equals to UPDATED_ACTIVE
        defaultPeakHourTariffShouldNotBeFound("active.in=" + UPDATED_ACTIVE);
    }

    @Test
    @Transactional
    void getAllPeakHourTariffsByActiveIsNullOrNotNull() throws Exception {
        // Initialize the database
        peakHourTariffRepository.saveAndFlush(peakHourTariff);

        // Get all the peakHourTariffList where active is not null
        defaultPeakHourTariffShouldBeFound("active.specified=true");

        // Get all the peakHourTariffList where active is null
        defaultPeakHourTariffShouldNotBeFound("active.specified=false");
    }

    @Test
    @Transactional
    void getAllPeakHourTariffsByActiveIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        peakHourTariffRepository.saveAndFlush(peakHourTariff);

        // Get all the peakHourTariffList where active is greater than or equal to DEFAULT_ACTIVE
        defaultPeakHourTariffShouldBeFound("active.greaterThanOrEqual=" + DEFAULT_ACTIVE);

        // Get all the peakHourTariffList where active is greater than or equal to UPDATED_ACTIVE
        defaultPeakHourTariffShouldNotBeFound("active.greaterThanOrEqual=" + UPDATED_ACTIVE);
    }

    @Test
    @Transactional
    void getAllPeakHourTariffsByActiveIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        peakHourTariffRepository.saveAndFlush(peakHourTariff);

        // Get all the peakHourTariffList where active is less than or equal to DEFAULT_ACTIVE
        defaultPeakHourTariffShouldBeFound("active.lessThanOrEqual=" + DEFAULT_ACTIVE);

        // Get all the peakHourTariffList where active is less than or equal to SMALLER_ACTIVE
        defaultPeakHourTariffShouldNotBeFound("active.lessThanOrEqual=" + SMALLER_ACTIVE);
    }

    @Test
    @Transactional
    void getAllPeakHourTariffsByActiveIsLessThanSomething() throws Exception {
        // Initialize the database
        peakHourTariffRepository.saveAndFlush(peakHourTariff);

        // Get all the peakHourTariffList where active is less than DEFAULT_ACTIVE
        defaultPeakHourTariffShouldNotBeFound("active.lessThan=" + DEFAULT_ACTIVE);

        // Get all the peakHourTariffList where active is less than UPDATED_ACTIVE
        defaultPeakHourTariffShouldBeFound("active.lessThan=" + UPDATED_ACTIVE);
    }

    @Test
    @Transactional
    void getAllPeakHourTariffsByActiveIsGreaterThanSomething() throws Exception {
        // Initialize the database
        peakHourTariffRepository.saveAndFlush(peakHourTariff);

        // Get all the peakHourTariffList where active is greater than DEFAULT_ACTIVE
        defaultPeakHourTariffShouldNotBeFound("active.greaterThan=" + DEFAULT_ACTIVE);

        // Get all the peakHourTariffList where active is greater than SMALLER_ACTIVE
        defaultPeakHourTariffShouldBeFound("active.greaterThan=" + SMALLER_ACTIVE);
    }

    @Test
    @Transactional
    void getAllPeakHourTariffsByUpdatedIsEqualToSomething() throws Exception {
        // Initialize the database
        peakHourTariffRepository.saveAndFlush(peakHourTariff);

        // Get all the peakHourTariffList where updated equals to DEFAULT_UPDATED
        defaultPeakHourTariffShouldBeFound("updated.equals=" + DEFAULT_UPDATED);

        // Get all the peakHourTariffList where updated equals to UPDATED_UPDATED
        defaultPeakHourTariffShouldNotBeFound("updated.equals=" + UPDATED_UPDATED);
    }

    @Test
    @Transactional
    void getAllPeakHourTariffsByUpdatedIsInShouldWork() throws Exception {
        // Initialize the database
        peakHourTariffRepository.saveAndFlush(peakHourTariff);

        // Get all the peakHourTariffList where updated in DEFAULT_UPDATED or UPDATED_UPDATED
        defaultPeakHourTariffShouldBeFound("updated.in=" + DEFAULT_UPDATED + "," + UPDATED_UPDATED);

        // Get all the peakHourTariffList where updated equals to UPDATED_UPDATED
        defaultPeakHourTariffShouldNotBeFound("updated.in=" + UPDATED_UPDATED);
    }

    @Test
    @Transactional
    void getAllPeakHourTariffsByUpdatedIsNullOrNotNull() throws Exception {
        // Initialize the database
        peakHourTariffRepository.saveAndFlush(peakHourTariff);

        // Get all the peakHourTariffList where updated is not null
        defaultPeakHourTariffShouldBeFound("updated.specified=true");

        // Get all the peakHourTariffList where updated is null
        defaultPeakHourTariffShouldNotBeFound("updated.specified=false");
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultPeakHourTariffShouldBeFound(String filter) throws Exception {
        restPeakHourTariffMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(peakHourTariff.getId().intValue())))
            .andExpect(jsonPath("$.[*].peakHourFrom").value(hasItem(DEFAULT_PEAK_HOUR_FROM.toString())))
            .andExpect(jsonPath("$.[*].peakHourTo").value(hasItem(DEFAULT_PEAK_HOUR_TO.toString())))
            .andExpect(jsonPath("$.[*].gantry").value(hasItem(DEFAULT_GANTRY)))
            .andExpect(jsonPath("$.[*].gantryToll").value(hasItem(DEFAULT_GANTRY_TOLL.doubleValue())))
            .andExpect(jsonPath("$.[*].active").value(hasItem(DEFAULT_ACTIVE)))
            .andExpect(jsonPath("$.[*].updated").value(hasItem(DEFAULT_UPDATED.toString())));

        // Check, that the count call also returns 1
        restPeakHourTariffMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultPeakHourTariffShouldNotBeFound(String filter) throws Exception {
        restPeakHourTariffMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restPeakHourTariffMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    void getNonExistingPeakHourTariff() throws Exception {
        // Get the peakHourTariff
        restPeakHourTariffMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingPeakHourTariff() throws Exception {
        // Initialize the database
        peakHourTariffRepository.saveAndFlush(peakHourTariff);

        int databaseSizeBeforeUpdate = peakHourTariffRepository.findAll().size();

        // Update the peakHourTariff
        PeakHourTariff updatedPeakHourTariff = peakHourTariffRepository.findById(peakHourTariff.getId()).get();
        // Disconnect from session so that the updates on updatedPeakHourTariff are not directly saved in db
        em.detach(updatedPeakHourTariff);
        updatedPeakHourTariff
            .peakHourFrom(UPDATED_PEAK_HOUR_FROM)
            .peakHourTo(UPDATED_PEAK_HOUR_TO)
            .gantry(UPDATED_GANTRY)
            .gantryToll(UPDATED_GANTRY_TOLL)
            .active(UPDATED_ACTIVE)
            .updated(UPDATED_UPDATED);
        PeakHourTariffDTO peakHourTariffDTO = peakHourTariffMapper.toDto(updatedPeakHourTariff);

        restPeakHourTariffMockMvc
            .perform(
                put(ENTITY_API_URL_ID, peakHourTariffDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(peakHourTariffDTO))
            )
            .andExpect(status().isOk());

        // Validate the PeakHourTariff in the database
        List<PeakHourTariff> peakHourTariffList = peakHourTariffRepository.findAll();
        assertThat(peakHourTariffList).hasSize(databaseSizeBeforeUpdate);
        PeakHourTariff testPeakHourTariff = peakHourTariffList.get(peakHourTariffList.size() - 1);
        assertThat(testPeakHourTariff.getPeakHourFrom()).isEqualTo(UPDATED_PEAK_HOUR_FROM);
        assertThat(testPeakHourTariff.getPeakHourTo()).isEqualTo(UPDATED_PEAK_HOUR_TO);
        assertThat(testPeakHourTariff.getGantry()).isEqualTo(UPDATED_GANTRY);
        assertThat(testPeakHourTariff.getGantryToll()).isEqualTo(UPDATED_GANTRY_TOLL);
        assertThat(testPeakHourTariff.getActive()).isEqualTo(UPDATED_ACTIVE);
        assertThat(testPeakHourTariff.getUpdated()).isEqualTo(UPDATED_UPDATED);
    }

    @Test
    @Transactional
    void putNonExistingPeakHourTariff() throws Exception {
        int databaseSizeBeforeUpdate = peakHourTariffRepository.findAll().size();
        peakHourTariff.setId(count.incrementAndGet());

        // Create the PeakHourTariff
        PeakHourTariffDTO peakHourTariffDTO = peakHourTariffMapper.toDto(peakHourTariff);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restPeakHourTariffMockMvc
            .perform(
                put(ENTITY_API_URL_ID, peakHourTariffDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(peakHourTariffDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the PeakHourTariff in the database
        List<PeakHourTariff> peakHourTariffList = peakHourTariffRepository.findAll();
        assertThat(peakHourTariffList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchPeakHourTariff() throws Exception {
        int databaseSizeBeforeUpdate = peakHourTariffRepository.findAll().size();
        peakHourTariff.setId(count.incrementAndGet());

        // Create the PeakHourTariff
        PeakHourTariffDTO peakHourTariffDTO = peakHourTariffMapper.toDto(peakHourTariff);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPeakHourTariffMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(peakHourTariffDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the PeakHourTariff in the database
        List<PeakHourTariff> peakHourTariffList = peakHourTariffRepository.findAll();
        assertThat(peakHourTariffList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamPeakHourTariff() throws Exception {
        int databaseSizeBeforeUpdate = peakHourTariffRepository.findAll().size();
        peakHourTariff.setId(count.incrementAndGet());

        // Create the PeakHourTariff
        PeakHourTariffDTO peakHourTariffDTO = peakHourTariffMapper.toDto(peakHourTariff);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPeakHourTariffMockMvc
            .perform(
                put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(peakHourTariffDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the PeakHourTariff in the database
        List<PeakHourTariff> peakHourTariffList = peakHourTariffRepository.findAll();
        assertThat(peakHourTariffList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdatePeakHourTariffWithPatch() throws Exception {
        // Initialize the database
        peakHourTariffRepository.saveAndFlush(peakHourTariff);

        int databaseSizeBeforeUpdate = peakHourTariffRepository.findAll().size();

        // Update the peakHourTariff using partial update
        PeakHourTariff partialUpdatedPeakHourTariff = new PeakHourTariff();
        partialUpdatedPeakHourTariff.setId(peakHourTariff.getId());

        partialUpdatedPeakHourTariff.active(UPDATED_ACTIVE);

        restPeakHourTariffMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedPeakHourTariff.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedPeakHourTariff))
            )
            .andExpect(status().isOk());

        // Validate the PeakHourTariff in the database
        List<PeakHourTariff> peakHourTariffList = peakHourTariffRepository.findAll();
        assertThat(peakHourTariffList).hasSize(databaseSizeBeforeUpdate);
        PeakHourTariff testPeakHourTariff = peakHourTariffList.get(peakHourTariffList.size() - 1);
        assertThat(testPeakHourTariff.getPeakHourFrom()).isEqualTo(DEFAULT_PEAK_HOUR_FROM);
        assertThat(testPeakHourTariff.getPeakHourTo()).isEqualTo(DEFAULT_PEAK_HOUR_TO);
        assertThat(testPeakHourTariff.getGantry()).isEqualTo(DEFAULT_GANTRY);
        assertThat(testPeakHourTariff.getGantryToll()).isEqualTo(DEFAULT_GANTRY_TOLL);
        assertThat(testPeakHourTariff.getActive()).isEqualTo(UPDATED_ACTIVE);
        assertThat(testPeakHourTariff.getUpdated()).isEqualTo(DEFAULT_UPDATED);
    }

    @Test
    @Transactional
    void fullUpdatePeakHourTariffWithPatch() throws Exception {
        // Initialize the database
        peakHourTariffRepository.saveAndFlush(peakHourTariff);

        int databaseSizeBeforeUpdate = peakHourTariffRepository.findAll().size();

        // Update the peakHourTariff using partial update
        PeakHourTariff partialUpdatedPeakHourTariff = new PeakHourTariff();
        partialUpdatedPeakHourTariff.setId(peakHourTariff.getId());

        partialUpdatedPeakHourTariff
            .peakHourFrom(UPDATED_PEAK_HOUR_FROM)
            .peakHourTo(UPDATED_PEAK_HOUR_TO)
            .gantry(UPDATED_GANTRY)
            .gantryToll(UPDATED_GANTRY_TOLL)
            .active(UPDATED_ACTIVE)
            .updated(UPDATED_UPDATED);

        restPeakHourTariffMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedPeakHourTariff.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedPeakHourTariff))
            )
            .andExpect(status().isOk());

        // Validate the PeakHourTariff in the database
        List<PeakHourTariff> peakHourTariffList = peakHourTariffRepository.findAll();
        assertThat(peakHourTariffList).hasSize(databaseSizeBeforeUpdate);
        PeakHourTariff testPeakHourTariff = peakHourTariffList.get(peakHourTariffList.size() - 1);
        assertThat(testPeakHourTariff.getPeakHourFrom()).isEqualTo(UPDATED_PEAK_HOUR_FROM);
        assertThat(testPeakHourTariff.getPeakHourTo()).isEqualTo(UPDATED_PEAK_HOUR_TO);
        assertThat(testPeakHourTariff.getGantry()).isEqualTo(UPDATED_GANTRY);
        assertThat(testPeakHourTariff.getGantryToll()).isEqualTo(UPDATED_GANTRY_TOLL);
        assertThat(testPeakHourTariff.getActive()).isEqualTo(UPDATED_ACTIVE);
        assertThat(testPeakHourTariff.getUpdated()).isEqualTo(UPDATED_UPDATED);
    }

    @Test
    @Transactional
    void patchNonExistingPeakHourTariff() throws Exception {
        int databaseSizeBeforeUpdate = peakHourTariffRepository.findAll().size();
        peakHourTariff.setId(count.incrementAndGet());

        // Create the PeakHourTariff
        PeakHourTariffDTO peakHourTariffDTO = peakHourTariffMapper.toDto(peakHourTariff);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restPeakHourTariffMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, peakHourTariffDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(peakHourTariffDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the PeakHourTariff in the database
        List<PeakHourTariff> peakHourTariffList = peakHourTariffRepository.findAll();
        assertThat(peakHourTariffList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchPeakHourTariff() throws Exception {
        int databaseSizeBeforeUpdate = peakHourTariffRepository.findAll().size();
        peakHourTariff.setId(count.incrementAndGet());

        // Create the PeakHourTariff
        PeakHourTariffDTO peakHourTariffDTO = peakHourTariffMapper.toDto(peakHourTariff);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPeakHourTariffMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(peakHourTariffDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the PeakHourTariff in the database
        List<PeakHourTariff> peakHourTariffList = peakHourTariffRepository.findAll();
        assertThat(peakHourTariffList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamPeakHourTariff() throws Exception {
        int databaseSizeBeforeUpdate = peakHourTariffRepository.findAll().size();
        peakHourTariff.setId(count.incrementAndGet());

        // Create the PeakHourTariff
        PeakHourTariffDTO peakHourTariffDTO = peakHourTariffMapper.toDto(peakHourTariff);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPeakHourTariffMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(peakHourTariffDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the PeakHourTariff in the database
        List<PeakHourTariff> peakHourTariffList = peakHourTariffRepository.findAll();
        assertThat(peakHourTariffList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deletePeakHourTariff() throws Exception {
        // Initialize the database
        peakHourTariffRepository.saveAndFlush(peakHourTariff);

        int databaseSizeBeforeDelete = peakHourTariffRepository.findAll().size();

        // Delete the peakHourTariff
        restPeakHourTariffMockMvc
            .perform(delete(ENTITY_API_URL_ID, peakHourTariff.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<PeakHourTariff> peakHourTariffList = peakHourTariffRepository.findAll();
        assertThat(peakHourTariffList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
