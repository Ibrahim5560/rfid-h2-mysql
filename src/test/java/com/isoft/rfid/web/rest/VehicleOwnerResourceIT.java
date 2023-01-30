package com.isoft.rfid.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.isoft.rfid.IntegrationTest;
import com.isoft.rfid.domain.VehicleOwner;
import com.isoft.rfid.repository.VehicleOwnerRepository;
import com.isoft.rfid.service.criteria.VehicleOwnerCriteria;
import com.isoft.rfid.service.dto.VehicleOwnerDTO;
import com.isoft.rfid.service.mapper.VehicleOwnerMapper;
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
 * Integration tests for the {@link VehicleOwnerResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class VehicleOwnerResourceIT {

    private static final String DEFAULT_CITIZEN_SERIAL = "AAAAAAAAAA";
    private static final String UPDATED_CITIZEN_SERIAL = "BBBBBBBBBB";

    private static final String DEFAULT_PASSPORT_SERIAL = "AAAAAAAAAA";
    private static final String UPDATED_PASSPORT_SERIAL = "BBBBBBBBBB";

    private static final String DEFAULT_ENTITY_SERIAL = "AAAAAAAAAA";
    private static final String UPDATED_ENTITY_SERIAL = "BBBBBBBBBB";

    private static final String DEFAULT_FULL_NAME = "AAAAAAAAAA";
    private static final String UPDATED_FULL_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_FIRST_NAME = "AAAAAAAAAA";
    private static final String UPDATED_FIRST_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_MIDDLE_NAME = "AAAAAAAAAA";
    private static final String UPDATED_MIDDLE_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_GRAND_NAME = "AAAAAAAAAA";
    private static final String UPDATED_GRAND_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_SURNAME = "AAAAAAAAAA";
    private static final String UPDATED_SURNAME = "BBBBBBBBBB";

    private static final String DEFAULT_AKA = "AAAAAAAAAA";
    private static final String UPDATED_AKA = "BBBBBBBBBB";

    private static final Instant DEFAULT_DOB = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_DOB = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final String DEFAULT_GENDER = "AAAAAAAAAA";
    private static final String UPDATED_GENDER = "BBBBBBBBBB";

    private static final String DEFAULT_DRIVER_LICENSE_SERIAL = "AAAAAAAAAA";
    private static final String UPDATED_DRIVER_LICENSE_SERIAL = "BBBBBBBBBB";

    private static final Long DEFAULT_DRIVER_LICENSE_TYPE = 1L;
    private static final Long UPDATED_DRIVER_LICENSE_TYPE = 2L;
    private static final Long SMALLER_DRIVER_LICENSE_TYPE = 1L - 1L;

    private static final Long DEFAULT_JURISDICTION = 1L;
    private static final Long UPDATED_JURISDICTION = 2L;
    private static final Long SMALLER_JURISDICTION = 1L - 1L;

    private static final Instant DEFAULT_UPDATED = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_UPDATED = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Double DEFAULT_ACCOUNT = 1D;
    private static final Double UPDATED_ACCOUNT = 2D;
    private static final Double SMALLER_ACCOUNT = 1D - 1D;

    private static final String ENTITY_API_URL = "/api/vehicle-owners";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private VehicleOwnerRepository vehicleOwnerRepository;

    @Autowired
    private VehicleOwnerMapper vehicleOwnerMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restVehicleOwnerMockMvc;

    private VehicleOwner vehicleOwner;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static VehicleOwner createEntity(EntityManager em) {
        VehicleOwner vehicleOwner = new VehicleOwner()
            .citizenSerial(DEFAULT_CITIZEN_SERIAL)
            .passportSerial(DEFAULT_PASSPORT_SERIAL)
            .entitySerial(DEFAULT_ENTITY_SERIAL)
            .fullName(DEFAULT_FULL_NAME)
            .firstName(DEFAULT_FIRST_NAME)
            .middleName(DEFAULT_MIDDLE_NAME)
            .grandName(DEFAULT_GRAND_NAME)
            .surname(DEFAULT_SURNAME)
            .aka(DEFAULT_AKA)
            .dob(DEFAULT_DOB)
            .gender(DEFAULT_GENDER)
            .driverLicenseSerial(DEFAULT_DRIVER_LICENSE_SERIAL)
            .driverLicenseType(DEFAULT_DRIVER_LICENSE_TYPE)
            .jurisdiction(DEFAULT_JURISDICTION)
            .updated(DEFAULT_UPDATED)
            .account(DEFAULT_ACCOUNT);
        return vehicleOwner;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static VehicleOwner createUpdatedEntity(EntityManager em) {
        VehicleOwner vehicleOwner = new VehicleOwner()
            .citizenSerial(UPDATED_CITIZEN_SERIAL)
            .passportSerial(UPDATED_PASSPORT_SERIAL)
            .entitySerial(UPDATED_ENTITY_SERIAL)
            .fullName(UPDATED_FULL_NAME)
            .firstName(UPDATED_FIRST_NAME)
            .middleName(UPDATED_MIDDLE_NAME)
            .grandName(UPDATED_GRAND_NAME)
            .surname(UPDATED_SURNAME)
            .aka(UPDATED_AKA)
            .dob(UPDATED_DOB)
            .gender(UPDATED_GENDER)
            .driverLicenseSerial(UPDATED_DRIVER_LICENSE_SERIAL)
            .driverLicenseType(UPDATED_DRIVER_LICENSE_TYPE)
            .jurisdiction(UPDATED_JURISDICTION)
            .updated(UPDATED_UPDATED)
            .account(UPDATED_ACCOUNT);
        return vehicleOwner;
    }

    @BeforeEach
    public void initTest() {
        vehicleOwner = createEntity(em);
    }

    @Test
    @Transactional
    void createVehicleOwner() throws Exception {
        int databaseSizeBeforeCreate = vehicleOwnerRepository.findAll().size();
        // Create the VehicleOwner
        VehicleOwnerDTO vehicleOwnerDTO = vehicleOwnerMapper.toDto(vehicleOwner);
        restVehicleOwnerMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(vehicleOwnerDTO))
            )
            .andExpect(status().isCreated());

        // Validate the VehicleOwner in the database
        List<VehicleOwner> vehicleOwnerList = vehicleOwnerRepository.findAll();
        assertThat(vehicleOwnerList).hasSize(databaseSizeBeforeCreate + 1);
        VehicleOwner testVehicleOwner = vehicleOwnerList.get(vehicleOwnerList.size() - 1);
        assertThat(testVehicleOwner.getCitizenSerial()).isEqualTo(DEFAULT_CITIZEN_SERIAL);
        assertThat(testVehicleOwner.getPassportSerial()).isEqualTo(DEFAULT_PASSPORT_SERIAL);
        assertThat(testVehicleOwner.getEntitySerial()).isEqualTo(DEFAULT_ENTITY_SERIAL);
        assertThat(testVehicleOwner.getFullName()).isEqualTo(DEFAULT_FULL_NAME);
        assertThat(testVehicleOwner.getFirstName()).isEqualTo(DEFAULT_FIRST_NAME);
        assertThat(testVehicleOwner.getMiddleName()).isEqualTo(DEFAULT_MIDDLE_NAME);
        assertThat(testVehicleOwner.getGrandName()).isEqualTo(DEFAULT_GRAND_NAME);
        assertThat(testVehicleOwner.getSurname()).isEqualTo(DEFAULT_SURNAME);
        assertThat(testVehicleOwner.getAka()).isEqualTo(DEFAULT_AKA);
        assertThat(testVehicleOwner.getDob()).isEqualTo(DEFAULT_DOB);
        assertThat(testVehicleOwner.getGender()).isEqualTo(DEFAULT_GENDER);
        assertThat(testVehicleOwner.getDriverLicenseSerial()).isEqualTo(DEFAULT_DRIVER_LICENSE_SERIAL);
        assertThat(testVehicleOwner.getDriverLicenseType()).isEqualTo(DEFAULT_DRIVER_LICENSE_TYPE);
        assertThat(testVehicleOwner.getJurisdiction()).isEqualTo(DEFAULT_JURISDICTION);
        assertThat(testVehicleOwner.getUpdated()).isEqualTo(DEFAULT_UPDATED);
        assertThat(testVehicleOwner.getAccount()).isEqualTo(DEFAULT_ACCOUNT);
    }

    @Test
    @Transactional
    void createVehicleOwnerWithExistingId() throws Exception {
        // Create the VehicleOwner with an existing ID
        vehicleOwner.setId(1L);
        VehicleOwnerDTO vehicleOwnerDTO = vehicleOwnerMapper.toDto(vehicleOwner);

        int databaseSizeBeforeCreate = vehicleOwnerRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restVehicleOwnerMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(vehicleOwnerDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the VehicleOwner in the database
        List<VehicleOwner> vehicleOwnerList = vehicleOwnerRepository.findAll();
        assertThat(vehicleOwnerList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllVehicleOwners() throws Exception {
        // Initialize the database
        vehicleOwnerRepository.saveAndFlush(vehicleOwner);

        // Get all the vehicleOwnerList
        restVehicleOwnerMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(vehicleOwner.getId().intValue())))
            .andExpect(jsonPath("$.[*].citizenSerial").value(hasItem(DEFAULT_CITIZEN_SERIAL)))
            .andExpect(jsonPath("$.[*].passportSerial").value(hasItem(DEFAULT_PASSPORT_SERIAL)))
            .andExpect(jsonPath("$.[*].entitySerial").value(hasItem(DEFAULT_ENTITY_SERIAL)))
            .andExpect(jsonPath("$.[*].fullName").value(hasItem(DEFAULT_FULL_NAME)))
            .andExpect(jsonPath("$.[*].firstName").value(hasItem(DEFAULT_FIRST_NAME)))
            .andExpect(jsonPath("$.[*].middleName").value(hasItem(DEFAULT_MIDDLE_NAME)))
            .andExpect(jsonPath("$.[*].grandName").value(hasItem(DEFAULT_GRAND_NAME)))
            .andExpect(jsonPath("$.[*].surname").value(hasItem(DEFAULT_SURNAME)))
            .andExpect(jsonPath("$.[*].aka").value(hasItem(DEFAULT_AKA)))
            .andExpect(jsonPath("$.[*].dob").value(hasItem(DEFAULT_DOB.toString())))
            .andExpect(jsonPath("$.[*].gender").value(hasItem(DEFAULT_GENDER)))
            .andExpect(jsonPath("$.[*].driverLicenseSerial").value(hasItem(DEFAULT_DRIVER_LICENSE_SERIAL)))
            .andExpect(jsonPath("$.[*].driverLicenseType").value(hasItem(DEFAULT_DRIVER_LICENSE_TYPE.intValue())))
            .andExpect(jsonPath("$.[*].jurisdiction").value(hasItem(DEFAULT_JURISDICTION.intValue())))
            .andExpect(jsonPath("$.[*].updated").value(hasItem(DEFAULT_UPDATED.toString())))
            .andExpect(jsonPath("$.[*].account").value(hasItem(DEFAULT_ACCOUNT.doubleValue())));
    }

    @Test
    @Transactional
    void getVehicleOwner() throws Exception {
        // Initialize the database
        vehicleOwnerRepository.saveAndFlush(vehicleOwner);

        // Get the vehicleOwner
        restVehicleOwnerMockMvc
            .perform(get(ENTITY_API_URL_ID, vehicleOwner.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(vehicleOwner.getId().intValue()))
            .andExpect(jsonPath("$.citizenSerial").value(DEFAULT_CITIZEN_SERIAL))
            .andExpect(jsonPath("$.passportSerial").value(DEFAULT_PASSPORT_SERIAL))
            .andExpect(jsonPath("$.entitySerial").value(DEFAULT_ENTITY_SERIAL))
            .andExpect(jsonPath("$.fullName").value(DEFAULT_FULL_NAME))
            .andExpect(jsonPath("$.firstName").value(DEFAULT_FIRST_NAME))
            .andExpect(jsonPath("$.middleName").value(DEFAULT_MIDDLE_NAME))
            .andExpect(jsonPath("$.grandName").value(DEFAULT_GRAND_NAME))
            .andExpect(jsonPath("$.surname").value(DEFAULT_SURNAME))
            .andExpect(jsonPath("$.aka").value(DEFAULT_AKA))
            .andExpect(jsonPath("$.dob").value(DEFAULT_DOB.toString()))
            .andExpect(jsonPath("$.gender").value(DEFAULT_GENDER))
            .andExpect(jsonPath("$.driverLicenseSerial").value(DEFAULT_DRIVER_LICENSE_SERIAL))
            .andExpect(jsonPath("$.driverLicenseType").value(DEFAULT_DRIVER_LICENSE_TYPE.intValue()))
            .andExpect(jsonPath("$.jurisdiction").value(DEFAULT_JURISDICTION.intValue()))
            .andExpect(jsonPath("$.updated").value(DEFAULT_UPDATED.toString()))
            .andExpect(jsonPath("$.account").value(DEFAULT_ACCOUNT.doubleValue()));
    }

    @Test
    @Transactional
    void getVehicleOwnersByIdFiltering() throws Exception {
        // Initialize the database
        vehicleOwnerRepository.saveAndFlush(vehicleOwner);

        Long id = vehicleOwner.getId();

        defaultVehicleOwnerShouldBeFound("id.equals=" + id);
        defaultVehicleOwnerShouldNotBeFound("id.notEquals=" + id);

        defaultVehicleOwnerShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultVehicleOwnerShouldNotBeFound("id.greaterThan=" + id);

        defaultVehicleOwnerShouldBeFound("id.lessThanOrEqual=" + id);
        defaultVehicleOwnerShouldNotBeFound("id.lessThan=" + id);
    }

    @Test
    @Transactional
    void getAllVehicleOwnersByCitizenSerialIsEqualToSomething() throws Exception {
        // Initialize the database
        vehicleOwnerRepository.saveAndFlush(vehicleOwner);

        // Get all the vehicleOwnerList where citizenSerial equals to DEFAULT_CITIZEN_SERIAL
        defaultVehicleOwnerShouldBeFound("citizenSerial.equals=" + DEFAULT_CITIZEN_SERIAL);

        // Get all the vehicleOwnerList where citizenSerial equals to UPDATED_CITIZEN_SERIAL
        defaultVehicleOwnerShouldNotBeFound("citizenSerial.equals=" + UPDATED_CITIZEN_SERIAL);
    }

    @Test
    @Transactional
    void getAllVehicleOwnersByCitizenSerialIsInShouldWork() throws Exception {
        // Initialize the database
        vehicleOwnerRepository.saveAndFlush(vehicleOwner);

        // Get all the vehicleOwnerList where citizenSerial in DEFAULT_CITIZEN_SERIAL or UPDATED_CITIZEN_SERIAL
        defaultVehicleOwnerShouldBeFound("citizenSerial.in=" + DEFAULT_CITIZEN_SERIAL + "," + UPDATED_CITIZEN_SERIAL);

        // Get all the vehicleOwnerList where citizenSerial equals to UPDATED_CITIZEN_SERIAL
        defaultVehicleOwnerShouldNotBeFound("citizenSerial.in=" + UPDATED_CITIZEN_SERIAL);
    }

    @Test
    @Transactional
    void getAllVehicleOwnersByCitizenSerialIsNullOrNotNull() throws Exception {
        // Initialize the database
        vehicleOwnerRepository.saveAndFlush(vehicleOwner);

        // Get all the vehicleOwnerList where citizenSerial is not null
        defaultVehicleOwnerShouldBeFound("citizenSerial.specified=true");

        // Get all the vehicleOwnerList where citizenSerial is null
        defaultVehicleOwnerShouldNotBeFound("citizenSerial.specified=false");
    }

    @Test
    @Transactional
    void getAllVehicleOwnersByCitizenSerialContainsSomething() throws Exception {
        // Initialize the database
        vehicleOwnerRepository.saveAndFlush(vehicleOwner);

        // Get all the vehicleOwnerList where citizenSerial contains DEFAULT_CITIZEN_SERIAL
        defaultVehicleOwnerShouldBeFound("citizenSerial.contains=" + DEFAULT_CITIZEN_SERIAL);

        // Get all the vehicleOwnerList where citizenSerial contains UPDATED_CITIZEN_SERIAL
        defaultVehicleOwnerShouldNotBeFound("citizenSerial.contains=" + UPDATED_CITIZEN_SERIAL);
    }

    @Test
    @Transactional
    void getAllVehicleOwnersByCitizenSerialNotContainsSomething() throws Exception {
        // Initialize the database
        vehicleOwnerRepository.saveAndFlush(vehicleOwner);

        // Get all the vehicleOwnerList where citizenSerial does not contain DEFAULT_CITIZEN_SERIAL
        defaultVehicleOwnerShouldNotBeFound("citizenSerial.doesNotContain=" + DEFAULT_CITIZEN_SERIAL);

        // Get all the vehicleOwnerList where citizenSerial does not contain UPDATED_CITIZEN_SERIAL
        defaultVehicleOwnerShouldBeFound("citizenSerial.doesNotContain=" + UPDATED_CITIZEN_SERIAL);
    }

    @Test
    @Transactional
    void getAllVehicleOwnersByPassportSerialIsEqualToSomething() throws Exception {
        // Initialize the database
        vehicleOwnerRepository.saveAndFlush(vehicleOwner);

        // Get all the vehicleOwnerList where passportSerial equals to DEFAULT_PASSPORT_SERIAL
        defaultVehicleOwnerShouldBeFound("passportSerial.equals=" + DEFAULT_PASSPORT_SERIAL);

        // Get all the vehicleOwnerList where passportSerial equals to UPDATED_PASSPORT_SERIAL
        defaultVehicleOwnerShouldNotBeFound("passportSerial.equals=" + UPDATED_PASSPORT_SERIAL);
    }

    @Test
    @Transactional
    void getAllVehicleOwnersByPassportSerialIsInShouldWork() throws Exception {
        // Initialize the database
        vehicleOwnerRepository.saveAndFlush(vehicleOwner);

        // Get all the vehicleOwnerList where passportSerial in DEFAULT_PASSPORT_SERIAL or UPDATED_PASSPORT_SERIAL
        defaultVehicleOwnerShouldBeFound("passportSerial.in=" + DEFAULT_PASSPORT_SERIAL + "," + UPDATED_PASSPORT_SERIAL);

        // Get all the vehicleOwnerList where passportSerial equals to UPDATED_PASSPORT_SERIAL
        defaultVehicleOwnerShouldNotBeFound("passportSerial.in=" + UPDATED_PASSPORT_SERIAL);
    }

    @Test
    @Transactional
    void getAllVehicleOwnersByPassportSerialIsNullOrNotNull() throws Exception {
        // Initialize the database
        vehicleOwnerRepository.saveAndFlush(vehicleOwner);

        // Get all the vehicleOwnerList where passportSerial is not null
        defaultVehicleOwnerShouldBeFound("passportSerial.specified=true");

        // Get all the vehicleOwnerList where passportSerial is null
        defaultVehicleOwnerShouldNotBeFound("passportSerial.specified=false");
    }

    @Test
    @Transactional
    void getAllVehicleOwnersByPassportSerialContainsSomething() throws Exception {
        // Initialize the database
        vehicleOwnerRepository.saveAndFlush(vehicleOwner);

        // Get all the vehicleOwnerList where passportSerial contains DEFAULT_PASSPORT_SERIAL
        defaultVehicleOwnerShouldBeFound("passportSerial.contains=" + DEFAULT_PASSPORT_SERIAL);

        // Get all the vehicleOwnerList where passportSerial contains UPDATED_PASSPORT_SERIAL
        defaultVehicleOwnerShouldNotBeFound("passportSerial.contains=" + UPDATED_PASSPORT_SERIAL);
    }

    @Test
    @Transactional
    void getAllVehicleOwnersByPassportSerialNotContainsSomething() throws Exception {
        // Initialize the database
        vehicleOwnerRepository.saveAndFlush(vehicleOwner);

        // Get all the vehicleOwnerList where passportSerial does not contain DEFAULT_PASSPORT_SERIAL
        defaultVehicleOwnerShouldNotBeFound("passportSerial.doesNotContain=" + DEFAULT_PASSPORT_SERIAL);

        // Get all the vehicleOwnerList where passportSerial does not contain UPDATED_PASSPORT_SERIAL
        defaultVehicleOwnerShouldBeFound("passportSerial.doesNotContain=" + UPDATED_PASSPORT_SERIAL);
    }

    @Test
    @Transactional
    void getAllVehicleOwnersByEntitySerialIsEqualToSomething() throws Exception {
        // Initialize the database
        vehicleOwnerRepository.saveAndFlush(vehicleOwner);

        // Get all the vehicleOwnerList where entitySerial equals to DEFAULT_ENTITY_SERIAL
        defaultVehicleOwnerShouldBeFound("entitySerial.equals=" + DEFAULT_ENTITY_SERIAL);

        // Get all the vehicleOwnerList where entitySerial equals to UPDATED_ENTITY_SERIAL
        defaultVehicleOwnerShouldNotBeFound("entitySerial.equals=" + UPDATED_ENTITY_SERIAL);
    }

    @Test
    @Transactional
    void getAllVehicleOwnersByEntitySerialIsInShouldWork() throws Exception {
        // Initialize the database
        vehicleOwnerRepository.saveAndFlush(vehicleOwner);

        // Get all the vehicleOwnerList where entitySerial in DEFAULT_ENTITY_SERIAL or UPDATED_ENTITY_SERIAL
        defaultVehicleOwnerShouldBeFound("entitySerial.in=" + DEFAULT_ENTITY_SERIAL + "," + UPDATED_ENTITY_SERIAL);

        // Get all the vehicleOwnerList where entitySerial equals to UPDATED_ENTITY_SERIAL
        defaultVehicleOwnerShouldNotBeFound("entitySerial.in=" + UPDATED_ENTITY_SERIAL);
    }

    @Test
    @Transactional
    void getAllVehicleOwnersByEntitySerialIsNullOrNotNull() throws Exception {
        // Initialize the database
        vehicleOwnerRepository.saveAndFlush(vehicleOwner);

        // Get all the vehicleOwnerList where entitySerial is not null
        defaultVehicleOwnerShouldBeFound("entitySerial.specified=true");

        // Get all the vehicleOwnerList where entitySerial is null
        defaultVehicleOwnerShouldNotBeFound("entitySerial.specified=false");
    }

    @Test
    @Transactional
    void getAllVehicleOwnersByEntitySerialContainsSomething() throws Exception {
        // Initialize the database
        vehicleOwnerRepository.saveAndFlush(vehicleOwner);

        // Get all the vehicleOwnerList where entitySerial contains DEFAULT_ENTITY_SERIAL
        defaultVehicleOwnerShouldBeFound("entitySerial.contains=" + DEFAULT_ENTITY_SERIAL);

        // Get all the vehicleOwnerList where entitySerial contains UPDATED_ENTITY_SERIAL
        defaultVehicleOwnerShouldNotBeFound("entitySerial.contains=" + UPDATED_ENTITY_SERIAL);
    }

    @Test
    @Transactional
    void getAllVehicleOwnersByEntitySerialNotContainsSomething() throws Exception {
        // Initialize the database
        vehicleOwnerRepository.saveAndFlush(vehicleOwner);

        // Get all the vehicleOwnerList where entitySerial does not contain DEFAULT_ENTITY_SERIAL
        defaultVehicleOwnerShouldNotBeFound("entitySerial.doesNotContain=" + DEFAULT_ENTITY_SERIAL);

        // Get all the vehicleOwnerList where entitySerial does not contain UPDATED_ENTITY_SERIAL
        defaultVehicleOwnerShouldBeFound("entitySerial.doesNotContain=" + UPDATED_ENTITY_SERIAL);
    }

    @Test
    @Transactional
    void getAllVehicleOwnersByFullNameIsEqualToSomething() throws Exception {
        // Initialize the database
        vehicleOwnerRepository.saveAndFlush(vehicleOwner);

        // Get all the vehicleOwnerList where fullName equals to DEFAULT_FULL_NAME
        defaultVehicleOwnerShouldBeFound("fullName.equals=" + DEFAULT_FULL_NAME);

        // Get all the vehicleOwnerList where fullName equals to UPDATED_FULL_NAME
        defaultVehicleOwnerShouldNotBeFound("fullName.equals=" + UPDATED_FULL_NAME);
    }

    @Test
    @Transactional
    void getAllVehicleOwnersByFullNameIsInShouldWork() throws Exception {
        // Initialize the database
        vehicleOwnerRepository.saveAndFlush(vehicleOwner);

        // Get all the vehicleOwnerList where fullName in DEFAULT_FULL_NAME or UPDATED_FULL_NAME
        defaultVehicleOwnerShouldBeFound("fullName.in=" + DEFAULT_FULL_NAME + "," + UPDATED_FULL_NAME);

        // Get all the vehicleOwnerList where fullName equals to UPDATED_FULL_NAME
        defaultVehicleOwnerShouldNotBeFound("fullName.in=" + UPDATED_FULL_NAME);
    }

    @Test
    @Transactional
    void getAllVehicleOwnersByFullNameIsNullOrNotNull() throws Exception {
        // Initialize the database
        vehicleOwnerRepository.saveAndFlush(vehicleOwner);

        // Get all the vehicleOwnerList where fullName is not null
        defaultVehicleOwnerShouldBeFound("fullName.specified=true");

        // Get all the vehicleOwnerList where fullName is null
        defaultVehicleOwnerShouldNotBeFound("fullName.specified=false");
    }

    @Test
    @Transactional
    void getAllVehicleOwnersByFullNameContainsSomething() throws Exception {
        // Initialize the database
        vehicleOwnerRepository.saveAndFlush(vehicleOwner);

        // Get all the vehicleOwnerList where fullName contains DEFAULT_FULL_NAME
        defaultVehicleOwnerShouldBeFound("fullName.contains=" + DEFAULT_FULL_NAME);

        // Get all the vehicleOwnerList where fullName contains UPDATED_FULL_NAME
        defaultVehicleOwnerShouldNotBeFound("fullName.contains=" + UPDATED_FULL_NAME);
    }

    @Test
    @Transactional
    void getAllVehicleOwnersByFullNameNotContainsSomething() throws Exception {
        // Initialize the database
        vehicleOwnerRepository.saveAndFlush(vehicleOwner);

        // Get all the vehicleOwnerList where fullName does not contain DEFAULT_FULL_NAME
        defaultVehicleOwnerShouldNotBeFound("fullName.doesNotContain=" + DEFAULT_FULL_NAME);

        // Get all the vehicleOwnerList where fullName does not contain UPDATED_FULL_NAME
        defaultVehicleOwnerShouldBeFound("fullName.doesNotContain=" + UPDATED_FULL_NAME);
    }

    @Test
    @Transactional
    void getAllVehicleOwnersByFirstNameIsEqualToSomething() throws Exception {
        // Initialize the database
        vehicleOwnerRepository.saveAndFlush(vehicleOwner);

        // Get all the vehicleOwnerList where firstName equals to DEFAULT_FIRST_NAME
        defaultVehicleOwnerShouldBeFound("firstName.equals=" + DEFAULT_FIRST_NAME);

        // Get all the vehicleOwnerList where firstName equals to UPDATED_FIRST_NAME
        defaultVehicleOwnerShouldNotBeFound("firstName.equals=" + UPDATED_FIRST_NAME);
    }

    @Test
    @Transactional
    void getAllVehicleOwnersByFirstNameIsInShouldWork() throws Exception {
        // Initialize the database
        vehicleOwnerRepository.saveAndFlush(vehicleOwner);

        // Get all the vehicleOwnerList where firstName in DEFAULT_FIRST_NAME or UPDATED_FIRST_NAME
        defaultVehicleOwnerShouldBeFound("firstName.in=" + DEFAULT_FIRST_NAME + "," + UPDATED_FIRST_NAME);

        // Get all the vehicleOwnerList where firstName equals to UPDATED_FIRST_NAME
        defaultVehicleOwnerShouldNotBeFound("firstName.in=" + UPDATED_FIRST_NAME);
    }

    @Test
    @Transactional
    void getAllVehicleOwnersByFirstNameIsNullOrNotNull() throws Exception {
        // Initialize the database
        vehicleOwnerRepository.saveAndFlush(vehicleOwner);

        // Get all the vehicleOwnerList where firstName is not null
        defaultVehicleOwnerShouldBeFound("firstName.specified=true");

        // Get all the vehicleOwnerList where firstName is null
        defaultVehicleOwnerShouldNotBeFound("firstName.specified=false");
    }

    @Test
    @Transactional
    void getAllVehicleOwnersByFirstNameContainsSomething() throws Exception {
        // Initialize the database
        vehicleOwnerRepository.saveAndFlush(vehicleOwner);

        // Get all the vehicleOwnerList where firstName contains DEFAULT_FIRST_NAME
        defaultVehicleOwnerShouldBeFound("firstName.contains=" + DEFAULT_FIRST_NAME);

        // Get all the vehicleOwnerList where firstName contains UPDATED_FIRST_NAME
        defaultVehicleOwnerShouldNotBeFound("firstName.contains=" + UPDATED_FIRST_NAME);
    }

    @Test
    @Transactional
    void getAllVehicleOwnersByFirstNameNotContainsSomething() throws Exception {
        // Initialize the database
        vehicleOwnerRepository.saveAndFlush(vehicleOwner);

        // Get all the vehicleOwnerList where firstName does not contain DEFAULT_FIRST_NAME
        defaultVehicleOwnerShouldNotBeFound("firstName.doesNotContain=" + DEFAULT_FIRST_NAME);

        // Get all the vehicleOwnerList where firstName does not contain UPDATED_FIRST_NAME
        defaultVehicleOwnerShouldBeFound("firstName.doesNotContain=" + UPDATED_FIRST_NAME);
    }

    @Test
    @Transactional
    void getAllVehicleOwnersByMiddleNameIsEqualToSomething() throws Exception {
        // Initialize the database
        vehicleOwnerRepository.saveAndFlush(vehicleOwner);

        // Get all the vehicleOwnerList where middleName equals to DEFAULT_MIDDLE_NAME
        defaultVehicleOwnerShouldBeFound("middleName.equals=" + DEFAULT_MIDDLE_NAME);

        // Get all the vehicleOwnerList where middleName equals to UPDATED_MIDDLE_NAME
        defaultVehicleOwnerShouldNotBeFound("middleName.equals=" + UPDATED_MIDDLE_NAME);
    }

    @Test
    @Transactional
    void getAllVehicleOwnersByMiddleNameIsInShouldWork() throws Exception {
        // Initialize the database
        vehicleOwnerRepository.saveAndFlush(vehicleOwner);

        // Get all the vehicleOwnerList where middleName in DEFAULT_MIDDLE_NAME or UPDATED_MIDDLE_NAME
        defaultVehicleOwnerShouldBeFound("middleName.in=" + DEFAULT_MIDDLE_NAME + "," + UPDATED_MIDDLE_NAME);

        // Get all the vehicleOwnerList where middleName equals to UPDATED_MIDDLE_NAME
        defaultVehicleOwnerShouldNotBeFound("middleName.in=" + UPDATED_MIDDLE_NAME);
    }

    @Test
    @Transactional
    void getAllVehicleOwnersByMiddleNameIsNullOrNotNull() throws Exception {
        // Initialize the database
        vehicleOwnerRepository.saveAndFlush(vehicleOwner);

        // Get all the vehicleOwnerList where middleName is not null
        defaultVehicleOwnerShouldBeFound("middleName.specified=true");

        // Get all the vehicleOwnerList where middleName is null
        defaultVehicleOwnerShouldNotBeFound("middleName.specified=false");
    }

    @Test
    @Transactional
    void getAllVehicleOwnersByMiddleNameContainsSomething() throws Exception {
        // Initialize the database
        vehicleOwnerRepository.saveAndFlush(vehicleOwner);

        // Get all the vehicleOwnerList where middleName contains DEFAULT_MIDDLE_NAME
        defaultVehicleOwnerShouldBeFound("middleName.contains=" + DEFAULT_MIDDLE_NAME);

        // Get all the vehicleOwnerList where middleName contains UPDATED_MIDDLE_NAME
        defaultVehicleOwnerShouldNotBeFound("middleName.contains=" + UPDATED_MIDDLE_NAME);
    }

    @Test
    @Transactional
    void getAllVehicleOwnersByMiddleNameNotContainsSomething() throws Exception {
        // Initialize the database
        vehicleOwnerRepository.saveAndFlush(vehicleOwner);

        // Get all the vehicleOwnerList where middleName does not contain DEFAULT_MIDDLE_NAME
        defaultVehicleOwnerShouldNotBeFound("middleName.doesNotContain=" + DEFAULT_MIDDLE_NAME);

        // Get all the vehicleOwnerList where middleName does not contain UPDATED_MIDDLE_NAME
        defaultVehicleOwnerShouldBeFound("middleName.doesNotContain=" + UPDATED_MIDDLE_NAME);
    }

    @Test
    @Transactional
    void getAllVehicleOwnersByGrandNameIsEqualToSomething() throws Exception {
        // Initialize the database
        vehicleOwnerRepository.saveAndFlush(vehicleOwner);

        // Get all the vehicleOwnerList where grandName equals to DEFAULT_GRAND_NAME
        defaultVehicleOwnerShouldBeFound("grandName.equals=" + DEFAULT_GRAND_NAME);

        // Get all the vehicleOwnerList where grandName equals to UPDATED_GRAND_NAME
        defaultVehicleOwnerShouldNotBeFound("grandName.equals=" + UPDATED_GRAND_NAME);
    }

    @Test
    @Transactional
    void getAllVehicleOwnersByGrandNameIsInShouldWork() throws Exception {
        // Initialize the database
        vehicleOwnerRepository.saveAndFlush(vehicleOwner);

        // Get all the vehicleOwnerList where grandName in DEFAULT_GRAND_NAME or UPDATED_GRAND_NAME
        defaultVehicleOwnerShouldBeFound("grandName.in=" + DEFAULT_GRAND_NAME + "," + UPDATED_GRAND_NAME);

        // Get all the vehicleOwnerList where grandName equals to UPDATED_GRAND_NAME
        defaultVehicleOwnerShouldNotBeFound("grandName.in=" + UPDATED_GRAND_NAME);
    }

    @Test
    @Transactional
    void getAllVehicleOwnersByGrandNameIsNullOrNotNull() throws Exception {
        // Initialize the database
        vehicleOwnerRepository.saveAndFlush(vehicleOwner);

        // Get all the vehicleOwnerList where grandName is not null
        defaultVehicleOwnerShouldBeFound("grandName.specified=true");

        // Get all the vehicleOwnerList where grandName is null
        defaultVehicleOwnerShouldNotBeFound("grandName.specified=false");
    }

    @Test
    @Transactional
    void getAllVehicleOwnersByGrandNameContainsSomething() throws Exception {
        // Initialize the database
        vehicleOwnerRepository.saveAndFlush(vehicleOwner);

        // Get all the vehicleOwnerList where grandName contains DEFAULT_GRAND_NAME
        defaultVehicleOwnerShouldBeFound("grandName.contains=" + DEFAULT_GRAND_NAME);

        // Get all the vehicleOwnerList where grandName contains UPDATED_GRAND_NAME
        defaultVehicleOwnerShouldNotBeFound("grandName.contains=" + UPDATED_GRAND_NAME);
    }

    @Test
    @Transactional
    void getAllVehicleOwnersByGrandNameNotContainsSomething() throws Exception {
        // Initialize the database
        vehicleOwnerRepository.saveAndFlush(vehicleOwner);

        // Get all the vehicleOwnerList where grandName does not contain DEFAULT_GRAND_NAME
        defaultVehicleOwnerShouldNotBeFound("grandName.doesNotContain=" + DEFAULT_GRAND_NAME);

        // Get all the vehicleOwnerList where grandName does not contain UPDATED_GRAND_NAME
        defaultVehicleOwnerShouldBeFound("grandName.doesNotContain=" + UPDATED_GRAND_NAME);
    }

    @Test
    @Transactional
    void getAllVehicleOwnersBySurnameIsEqualToSomething() throws Exception {
        // Initialize the database
        vehicleOwnerRepository.saveAndFlush(vehicleOwner);

        // Get all the vehicleOwnerList where surname equals to DEFAULT_SURNAME
        defaultVehicleOwnerShouldBeFound("surname.equals=" + DEFAULT_SURNAME);

        // Get all the vehicleOwnerList where surname equals to UPDATED_SURNAME
        defaultVehicleOwnerShouldNotBeFound("surname.equals=" + UPDATED_SURNAME);
    }

    @Test
    @Transactional
    void getAllVehicleOwnersBySurnameIsInShouldWork() throws Exception {
        // Initialize the database
        vehicleOwnerRepository.saveAndFlush(vehicleOwner);

        // Get all the vehicleOwnerList where surname in DEFAULT_SURNAME or UPDATED_SURNAME
        defaultVehicleOwnerShouldBeFound("surname.in=" + DEFAULT_SURNAME + "," + UPDATED_SURNAME);

        // Get all the vehicleOwnerList where surname equals to UPDATED_SURNAME
        defaultVehicleOwnerShouldNotBeFound("surname.in=" + UPDATED_SURNAME);
    }

    @Test
    @Transactional
    void getAllVehicleOwnersBySurnameIsNullOrNotNull() throws Exception {
        // Initialize the database
        vehicleOwnerRepository.saveAndFlush(vehicleOwner);

        // Get all the vehicleOwnerList where surname is not null
        defaultVehicleOwnerShouldBeFound("surname.specified=true");

        // Get all the vehicleOwnerList where surname is null
        defaultVehicleOwnerShouldNotBeFound("surname.specified=false");
    }

    @Test
    @Transactional
    void getAllVehicleOwnersBySurnameContainsSomething() throws Exception {
        // Initialize the database
        vehicleOwnerRepository.saveAndFlush(vehicleOwner);

        // Get all the vehicleOwnerList where surname contains DEFAULT_SURNAME
        defaultVehicleOwnerShouldBeFound("surname.contains=" + DEFAULT_SURNAME);

        // Get all the vehicleOwnerList where surname contains UPDATED_SURNAME
        defaultVehicleOwnerShouldNotBeFound("surname.contains=" + UPDATED_SURNAME);
    }

    @Test
    @Transactional
    void getAllVehicleOwnersBySurnameNotContainsSomething() throws Exception {
        // Initialize the database
        vehicleOwnerRepository.saveAndFlush(vehicleOwner);

        // Get all the vehicleOwnerList where surname does not contain DEFAULT_SURNAME
        defaultVehicleOwnerShouldNotBeFound("surname.doesNotContain=" + DEFAULT_SURNAME);

        // Get all the vehicleOwnerList where surname does not contain UPDATED_SURNAME
        defaultVehicleOwnerShouldBeFound("surname.doesNotContain=" + UPDATED_SURNAME);
    }

    @Test
    @Transactional
    void getAllVehicleOwnersByAkaIsEqualToSomething() throws Exception {
        // Initialize the database
        vehicleOwnerRepository.saveAndFlush(vehicleOwner);

        // Get all the vehicleOwnerList where aka equals to DEFAULT_AKA
        defaultVehicleOwnerShouldBeFound("aka.equals=" + DEFAULT_AKA);

        // Get all the vehicleOwnerList where aka equals to UPDATED_AKA
        defaultVehicleOwnerShouldNotBeFound("aka.equals=" + UPDATED_AKA);
    }

    @Test
    @Transactional
    void getAllVehicleOwnersByAkaIsInShouldWork() throws Exception {
        // Initialize the database
        vehicleOwnerRepository.saveAndFlush(vehicleOwner);

        // Get all the vehicleOwnerList where aka in DEFAULT_AKA or UPDATED_AKA
        defaultVehicleOwnerShouldBeFound("aka.in=" + DEFAULT_AKA + "," + UPDATED_AKA);

        // Get all the vehicleOwnerList where aka equals to UPDATED_AKA
        defaultVehicleOwnerShouldNotBeFound("aka.in=" + UPDATED_AKA);
    }

    @Test
    @Transactional
    void getAllVehicleOwnersByAkaIsNullOrNotNull() throws Exception {
        // Initialize the database
        vehicleOwnerRepository.saveAndFlush(vehicleOwner);

        // Get all the vehicleOwnerList where aka is not null
        defaultVehicleOwnerShouldBeFound("aka.specified=true");

        // Get all the vehicleOwnerList where aka is null
        defaultVehicleOwnerShouldNotBeFound("aka.specified=false");
    }

    @Test
    @Transactional
    void getAllVehicleOwnersByAkaContainsSomething() throws Exception {
        // Initialize the database
        vehicleOwnerRepository.saveAndFlush(vehicleOwner);

        // Get all the vehicleOwnerList where aka contains DEFAULT_AKA
        defaultVehicleOwnerShouldBeFound("aka.contains=" + DEFAULT_AKA);

        // Get all the vehicleOwnerList where aka contains UPDATED_AKA
        defaultVehicleOwnerShouldNotBeFound("aka.contains=" + UPDATED_AKA);
    }

    @Test
    @Transactional
    void getAllVehicleOwnersByAkaNotContainsSomething() throws Exception {
        // Initialize the database
        vehicleOwnerRepository.saveAndFlush(vehicleOwner);

        // Get all the vehicleOwnerList where aka does not contain DEFAULT_AKA
        defaultVehicleOwnerShouldNotBeFound("aka.doesNotContain=" + DEFAULT_AKA);

        // Get all the vehicleOwnerList where aka does not contain UPDATED_AKA
        defaultVehicleOwnerShouldBeFound("aka.doesNotContain=" + UPDATED_AKA);
    }

    @Test
    @Transactional
    void getAllVehicleOwnersByDobIsEqualToSomething() throws Exception {
        // Initialize the database
        vehicleOwnerRepository.saveAndFlush(vehicleOwner);

        // Get all the vehicleOwnerList where dob equals to DEFAULT_DOB
        defaultVehicleOwnerShouldBeFound("dob.equals=" + DEFAULT_DOB);

        // Get all the vehicleOwnerList where dob equals to UPDATED_DOB
        defaultVehicleOwnerShouldNotBeFound("dob.equals=" + UPDATED_DOB);
    }

    @Test
    @Transactional
    void getAllVehicleOwnersByDobIsInShouldWork() throws Exception {
        // Initialize the database
        vehicleOwnerRepository.saveAndFlush(vehicleOwner);

        // Get all the vehicleOwnerList where dob in DEFAULT_DOB or UPDATED_DOB
        defaultVehicleOwnerShouldBeFound("dob.in=" + DEFAULT_DOB + "," + UPDATED_DOB);

        // Get all the vehicleOwnerList where dob equals to UPDATED_DOB
        defaultVehicleOwnerShouldNotBeFound("dob.in=" + UPDATED_DOB);
    }

    @Test
    @Transactional
    void getAllVehicleOwnersByDobIsNullOrNotNull() throws Exception {
        // Initialize the database
        vehicleOwnerRepository.saveAndFlush(vehicleOwner);

        // Get all the vehicleOwnerList where dob is not null
        defaultVehicleOwnerShouldBeFound("dob.specified=true");

        // Get all the vehicleOwnerList where dob is null
        defaultVehicleOwnerShouldNotBeFound("dob.specified=false");
    }

    @Test
    @Transactional
    void getAllVehicleOwnersByGenderIsEqualToSomething() throws Exception {
        // Initialize the database
        vehicleOwnerRepository.saveAndFlush(vehicleOwner);

        // Get all the vehicleOwnerList where gender equals to DEFAULT_GENDER
        defaultVehicleOwnerShouldBeFound("gender.equals=" + DEFAULT_GENDER);

        // Get all the vehicleOwnerList where gender equals to UPDATED_GENDER
        defaultVehicleOwnerShouldNotBeFound("gender.equals=" + UPDATED_GENDER);
    }

    @Test
    @Transactional
    void getAllVehicleOwnersByGenderIsInShouldWork() throws Exception {
        // Initialize the database
        vehicleOwnerRepository.saveAndFlush(vehicleOwner);

        // Get all the vehicleOwnerList where gender in DEFAULT_GENDER or UPDATED_GENDER
        defaultVehicleOwnerShouldBeFound("gender.in=" + DEFAULT_GENDER + "," + UPDATED_GENDER);

        // Get all the vehicleOwnerList where gender equals to UPDATED_GENDER
        defaultVehicleOwnerShouldNotBeFound("gender.in=" + UPDATED_GENDER);
    }

    @Test
    @Transactional
    void getAllVehicleOwnersByGenderIsNullOrNotNull() throws Exception {
        // Initialize the database
        vehicleOwnerRepository.saveAndFlush(vehicleOwner);

        // Get all the vehicleOwnerList where gender is not null
        defaultVehicleOwnerShouldBeFound("gender.specified=true");

        // Get all the vehicleOwnerList where gender is null
        defaultVehicleOwnerShouldNotBeFound("gender.specified=false");
    }

    @Test
    @Transactional
    void getAllVehicleOwnersByGenderContainsSomething() throws Exception {
        // Initialize the database
        vehicleOwnerRepository.saveAndFlush(vehicleOwner);

        // Get all the vehicleOwnerList where gender contains DEFAULT_GENDER
        defaultVehicleOwnerShouldBeFound("gender.contains=" + DEFAULT_GENDER);

        // Get all the vehicleOwnerList where gender contains UPDATED_GENDER
        defaultVehicleOwnerShouldNotBeFound("gender.contains=" + UPDATED_GENDER);
    }

    @Test
    @Transactional
    void getAllVehicleOwnersByGenderNotContainsSomething() throws Exception {
        // Initialize the database
        vehicleOwnerRepository.saveAndFlush(vehicleOwner);

        // Get all the vehicleOwnerList where gender does not contain DEFAULT_GENDER
        defaultVehicleOwnerShouldNotBeFound("gender.doesNotContain=" + DEFAULT_GENDER);

        // Get all the vehicleOwnerList where gender does not contain UPDATED_GENDER
        defaultVehicleOwnerShouldBeFound("gender.doesNotContain=" + UPDATED_GENDER);
    }

    @Test
    @Transactional
    void getAllVehicleOwnersByDriverLicenseSerialIsEqualToSomething() throws Exception {
        // Initialize the database
        vehicleOwnerRepository.saveAndFlush(vehicleOwner);

        // Get all the vehicleOwnerList where driverLicenseSerial equals to DEFAULT_DRIVER_LICENSE_SERIAL
        defaultVehicleOwnerShouldBeFound("driverLicenseSerial.equals=" + DEFAULT_DRIVER_LICENSE_SERIAL);

        // Get all the vehicleOwnerList where driverLicenseSerial equals to UPDATED_DRIVER_LICENSE_SERIAL
        defaultVehicleOwnerShouldNotBeFound("driverLicenseSerial.equals=" + UPDATED_DRIVER_LICENSE_SERIAL);
    }

    @Test
    @Transactional
    void getAllVehicleOwnersByDriverLicenseSerialIsInShouldWork() throws Exception {
        // Initialize the database
        vehicleOwnerRepository.saveAndFlush(vehicleOwner);

        // Get all the vehicleOwnerList where driverLicenseSerial in DEFAULT_DRIVER_LICENSE_SERIAL or UPDATED_DRIVER_LICENSE_SERIAL
        defaultVehicleOwnerShouldBeFound("driverLicenseSerial.in=" + DEFAULT_DRIVER_LICENSE_SERIAL + "," + UPDATED_DRIVER_LICENSE_SERIAL);

        // Get all the vehicleOwnerList where driverLicenseSerial equals to UPDATED_DRIVER_LICENSE_SERIAL
        defaultVehicleOwnerShouldNotBeFound("driverLicenseSerial.in=" + UPDATED_DRIVER_LICENSE_SERIAL);
    }

    @Test
    @Transactional
    void getAllVehicleOwnersByDriverLicenseSerialIsNullOrNotNull() throws Exception {
        // Initialize the database
        vehicleOwnerRepository.saveAndFlush(vehicleOwner);

        // Get all the vehicleOwnerList where driverLicenseSerial is not null
        defaultVehicleOwnerShouldBeFound("driverLicenseSerial.specified=true");

        // Get all the vehicleOwnerList where driverLicenseSerial is null
        defaultVehicleOwnerShouldNotBeFound("driverLicenseSerial.specified=false");
    }

    @Test
    @Transactional
    void getAllVehicleOwnersByDriverLicenseSerialContainsSomething() throws Exception {
        // Initialize the database
        vehicleOwnerRepository.saveAndFlush(vehicleOwner);

        // Get all the vehicleOwnerList where driverLicenseSerial contains DEFAULT_DRIVER_LICENSE_SERIAL
        defaultVehicleOwnerShouldBeFound("driverLicenseSerial.contains=" + DEFAULT_DRIVER_LICENSE_SERIAL);

        // Get all the vehicleOwnerList where driverLicenseSerial contains UPDATED_DRIVER_LICENSE_SERIAL
        defaultVehicleOwnerShouldNotBeFound("driverLicenseSerial.contains=" + UPDATED_DRIVER_LICENSE_SERIAL);
    }

    @Test
    @Transactional
    void getAllVehicleOwnersByDriverLicenseSerialNotContainsSomething() throws Exception {
        // Initialize the database
        vehicleOwnerRepository.saveAndFlush(vehicleOwner);

        // Get all the vehicleOwnerList where driverLicenseSerial does not contain DEFAULT_DRIVER_LICENSE_SERIAL
        defaultVehicleOwnerShouldNotBeFound("driverLicenseSerial.doesNotContain=" + DEFAULT_DRIVER_LICENSE_SERIAL);

        // Get all the vehicleOwnerList where driverLicenseSerial does not contain UPDATED_DRIVER_LICENSE_SERIAL
        defaultVehicleOwnerShouldBeFound("driverLicenseSerial.doesNotContain=" + UPDATED_DRIVER_LICENSE_SERIAL);
    }

    @Test
    @Transactional
    void getAllVehicleOwnersByDriverLicenseTypeIsEqualToSomething() throws Exception {
        // Initialize the database
        vehicleOwnerRepository.saveAndFlush(vehicleOwner);

        // Get all the vehicleOwnerList where driverLicenseType equals to DEFAULT_DRIVER_LICENSE_TYPE
        defaultVehicleOwnerShouldBeFound("driverLicenseType.equals=" + DEFAULT_DRIVER_LICENSE_TYPE);

        // Get all the vehicleOwnerList where driverLicenseType equals to UPDATED_DRIVER_LICENSE_TYPE
        defaultVehicleOwnerShouldNotBeFound("driverLicenseType.equals=" + UPDATED_DRIVER_LICENSE_TYPE);
    }

    @Test
    @Transactional
    void getAllVehicleOwnersByDriverLicenseTypeIsInShouldWork() throws Exception {
        // Initialize the database
        vehicleOwnerRepository.saveAndFlush(vehicleOwner);

        // Get all the vehicleOwnerList where driverLicenseType in DEFAULT_DRIVER_LICENSE_TYPE or UPDATED_DRIVER_LICENSE_TYPE
        defaultVehicleOwnerShouldBeFound("driverLicenseType.in=" + DEFAULT_DRIVER_LICENSE_TYPE + "," + UPDATED_DRIVER_LICENSE_TYPE);

        // Get all the vehicleOwnerList where driverLicenseType equals to UPDATED_DRIVER_LICENSE_TYPE
        defaultVehicleOwnerShouldNotBeFound("driverLicenseType.in=" + UPDATED_DRIVER_LICENSE_TYPE);
    }

    @Test
    @Transactional
    void getAllVehicleOwnersByDriverLicenseTypeIsNullOrNotNull() throws Exception {
        // Initialize the database
        vehicleOwnerRepository.saveAndFlush(vehicleOwner);

        // Get all the vehicleOwnerList where driverLicenseType is not null
        defaultVehicleOwnerShouldBeFound("driverLicenseType.specified=true");

        // Get all the vehicleOwnerList where driverLicenseType is null
        defaultVehicleOwnerShouldNotBeFound("driverLicenseType.specified=false");
    }

    @Test
    @Transactional
    void getAllVehicleOwnersByDriverLicenseTypeIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        vehicleOwnerRepository.saveAndFlush(vehicleOwner);

        // Get all the vehicleOwnerList where driverLicenseType is greater than or equal to DEFAULT_DRIVER_LICENSE_TYPE
        defaultVehicleOwnerShouldBeFound("driverLicenseType.greaterThanOrEqual=" + DEFAULT_DRIVER_LICENSE_TYPE);

        // Get all the vehicleOwnerList where driverLicenseType is greater than or equal to UPDATED_DRIVER_LICENSE_TYPE
        defaultVehicleOwnerShouldNotBeFound("driverLicenseType.greaterThanOrEqual=" + UPDATED_DRIVER_LICENSE_TYPE);
    }

    @Test
    @Transactional
    void getAllVehicleOwnersByDriverLicenseTypeIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        vehicleOwnerRepository.saveAndFlush(vehicleOwner);

        // Get all the vehicleOwnerList where driverLicenseType is less than or equal to DEFAULT_DRIVER_LICENSE_TYPE
        defaultVehicleOwnerShouldBeFound("driverLicenseType.lessThanOrEqual=" + DEFAULT_DRIVER_LICENSE_TYPE);

        // Get all the vehicleOwnerList where driverLicenseType is less than or equal to SMALLER_DRIVER_LICENSE_TYPE
        defaultVehicleOwnerShouldNotBeFound("driverLicenseType.lessThanOrEqual=" + SMALLER_DRIVER_LICENSE_TYPE);
    }

    @Test
    @Transactional
    void getAllVehicleOwnersByDriverLicenseTypeIsLessThanSomething() throws Exception {
        // Initialize the database
        vehicleOwnerRepository.saveAndFlush(vehicleOwner);

        // Get all the vehicleOwnerList where driverLicenseType is less than DEFAULT_DRIVER_LICENSE_TYPE
        defaultVehicleOwnerShouldNotBeFound("driverLicenseType.lessThan=" + DEFAULT_DRIVER_LICENSE_TYPE);

        // Get all the vehicleOwnerList where driverLicenseType is less than UPDATED_DRIVER_LICENSE_TYPE
        defaultVehicleOwnerShouldBeFound("driverLicenseType.lessThan=" + UPDATED_DRIVER_LICENSE_TYPE);
    }

    @Test
    @Transactional
    void getAllVehicleOwnersByDriverLicenseTypeIsGreaterThanSomething() throws Exception {
        // Initialize the database
        vehicleOwnerRepository.saveAndFlush(vehicleOwner);

        // Get all the vehicleOwnerList where driverLicenseType is greater than DEFAULT_DRIVER_LICENSE_TYPE
        defaultVehicleOwnerShouldNotBeFound("driverLicenseType.greaterThan=" + DEFAULT_DRIVER_LICENSE_TYPE);

        // Get all the vehicleOwnerList where driverLicenseType is greater than SMALLER_DRIVER_LICENSE_TYPE
        defaultVehicleOwnerShouldBeFound("driverLicenseType.greaterThan=" + SMALLER_DRIVER_LICENSE_TYPE);
    }

    @Test
    @Transactional
    void getAllVehicleOwnersByJurisdictionIsEqualToSomething() throws Exception {
        // Initialize the database
        vehicleOwnerRepository.saveAndFlush(vehicleOwner);

        // Get all the vehicleOwnerList where jurisdiction equals to DEFAULT_JURISDICTION
        defaultVehicleOwnerShouldBeFound("jurisdiction.equals=" + DEFAULT_JURISDICTION);

        // Get all the vehicleOwnerList where jurisdiction equals to UPDATED_JURISDICTION
        defaultVehicleOwnerShouldNotBeFound("jurisdiction.equals=" + UPDATED_JURISDICTION);
    }

    @Test
    @Transactional
    void getAllVehicleOwnersByJurisdictionIsInShouldWork() throws Exception {
        // Initialize the database
        vehicleOwnerRepository.saveAndFlush(vehicleOwner);

        // Get all the vehicleOwnerList where jurisdiction in DEFAULT_JURISDICTION or UPDATED_JURISDICTION
        defaultVehicleOwnerShouldBeFound("jurisdiction.in=" + DEFAULT_JURISDICTION + "," + UPDATED_JURISDICTION);

        // Get all the vehicleOwnerList where jurisdiction equals to UPDATED_JURISDICTION
        defaultVehicleOwnerShouldNotBeFound("jurisdiction.in=" + UPDATED_JURISDICTION);
    }

    @Test
    @Transactional
    void getAllVehicleOwnersByJurisdictionIsNullOrNotNull() throws Exception {
        // Initialize the database
        vehicleOwnerRepository.saveAndFlush(vehicleOwner);

        // Get all the vehicleOwnerList where jurisdiction is not null
        defaultVehicleOwnerShouldBeFound("jurisdiction.specified=true");

        // Get all the vehicleOwnerList where jurisdiction is null
        defaultVehicleOwnerShouldNotBeFound("jurisdiction.specified=false");
    }

    @Test
    @Transactional
    void getAllVehicleOwnersByJurisdictionIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        vehicleOwnerRepository.saveAndFlush(vehicleOwner);

        // Get all the vehicleOwnerList where jurisdiction is greater than or equal to DEFAULT_JURISDICTION
        defaultVehicleOwnerShouldBeFound("jurisdiction.greaterThanOrEqual=" + DEFAULT_JURISDICTION);

        // Get all the vehicleOwnerList where jurisdiction is greater than or equal to UPDATED_JURISDICTION
        defaultVehicleOwnerShouldNotBeFound("jurisdiction.greaterThanOrEqual=" + UPDATED_JURISDICTION);
    }

    @Test
    @Transactional
    void getAllVehicleOwnersByJurisdictionIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        vehicleOwnerRepository.saveAndFlush(vehicleOwner);

        // Get all the vehicleOwnerList where jurisdiction is less than or equal to DEFAULT_JURISDICTION
        defaultVehicleOwnerShouldBeFound("jurisdiction.lessThanOrEqual=" + DEFAULT_JURISDICTION);

        // Get all the vehicleOwnerList where jurisdiction is less than or equal to SMALLER_JURISDICTION
        defaultVehicleOwnerShouldNotBeFound("jurisdiction.lessThanOrEqual=" + SMALLER_JURISDICTION);
    }

    @Test
    @Transactional
    void getAllVehicleOwnersByJurisdictionIsLessThanSomething() throws Exception {
        // Initialize the database
        vehicleOwnerRepository.saveAndFlush(vehicleOwner);

        // Get all the vehicleOwnerList where jurisdiction is less than DEFAULT_JURISDICTION
        defaultVehicleOwnerShouldNotBeFound("jurisdiction.lessThan=" + DEFAULT_JURISDICTION);

        // Get all the vehicleOwnerList where jurisdiction is less than UPDATED_JURISDICTION
        defaultVehicleOwnerShouldBeFound("jurisdiction.lessThan=" + UPDATED_JURISDICTION);
    }

    @Test
    @Transactional
    void getAllVehicleOwnersByJurisdictionIsGreaterThanSomething() throws Exception {
        // Initialize the database
        vehicleOwnerRepository.saveAndFlush(vehicleOwner);

        // Get all the vehicleOwnerList where jurisdiction is greater than DEFAULT_JURISDICTION
        defaultVehicleOwnerShouldNotBeFound("jurisdiction.greaterThan=" + DEFAULT_JURISDICTION);

        // Get all the vehicleOwnerList where jurisdiction is greater than SMALLER_JURISDICTION
        defaultVehicleOwnerShouldBeFound("jurisdiction.greaterThan=" + SMALLER_JURISDICTION);
    }

    @Test
    @Transactional
    void getAllVehicleOwnersByUpdatedIsEqualToSomething() throws Exception {
        // Initialize the database
        vehicleOwnerRepository.saveAndFlush(vehicleOwner);

        // Get all the vehicleOwnerList where updated equals to DEFAULT_UPDATED
        defaultVehicleOwnerShouldBeFound("updated.equals=" + DEFAULT_UPDATED);

        // Get all the vehicleOwnerList where updated equals to UPDATED_UPDATED
        defaultVehicleOwnerShouldNotBeFound("updated.equals=" + UPDATED_UPDATED);
    }

    @Test
    @Transactional
    void getAllVehicleOwnersByUpdatedIsInShouldWork() throws Exception {
        // Initialize the database
        vehicleOwnerRepository.saveAndFlush(vehicleOwner);

        // Get all the vehicleOwnerList where updated in DEFAULT_UPDATED or UPDATED_UPDATED
        defaultVehicleOwnerShouldBeFound("updated.in=" + DEFAULT_UPDATED + "," + UPDATED_UPDATED);

        // Get all the vehicleOwnerList where updated equals to UPDATED_UPDATED
        defaultVehicleOwnerShouldNotBeFound("updated.in=" + UPDATED_UPDATED);
    }

    @Test
    @Transactional
    void getAllVehicleOwnersByUpdatedIsNullOrNotNull() throws Exception {
        // Initialize the database
        vehicleOwnerRepository.saveAndFlush(vehicleOwner);

        // Get all the vehicleOwnerList where updated is not null
        defaultVehicleOwnerShouldBeFound("updated.specified=true");

        // Get all the vehicleOwnerList where updated is null
        defaultVehicleOwnerShouldNotBeFound("updated.specified=false");
    }

    @Test
    @Transactional
    void getAllVehicleOwnersByAccountIsEqualToSomething() throws Exception {
        // Initialize the database
        vehicleOwnerRepository.saveAndFlush(vehicleOwner);

        // Get all the vehicleOwnerList where account equals to DEFAULT_ACCOUNT
        defaultVehicleOwnerShouldBeFound("account.equals=" + DEFAULT_ACCOUNT);

        // Get all the vehicleOwnerList where account equals to UPDATED_ACCOUNT
        defaultVehicleOwnerShouldNotBeFound("account.equals=" + UPDATED_ACCOUNT);
    }

    @Test
    @Transactional
    void getAllVehicleOwnersByAccountIsInShouldWork() throws Exception {
        // Initialize the database
        vehicleOwnerRepository.saveAndFlush(vehicleOwner);

        // Get all the vehicleOwnerList where account in DEFAULT_ACCOUNT or UPDATED_ACCOUNT
        defaultVehicleOwnerShouldBeFound("account.in=" + DEFAULT_ACCOUNT + "," + UPDATED_ACCOUNT);

        // Get all the vehicleOwnerList where account equals to UPDATED_ACCOUNT
        defaultVehicleOwnerShouldNotBeFound("account.in=" + UPDATED_ACCOUNT);
    }

    @Test
    @Transactional
    void getAllVehicleOwnersByAccountIsNullOrNotNull() throws Exception {
        // Initialize the database
        vehicleOwnerRepository.saveAndFlush(vehicleOwner);

        // Get all the vehicleOwnerList where account is not null
        defaultVehicleOwnerShouldBeFound("account.specified=true");

        // Get all the vehicleOwnerList where account is null
        defaultVehicleOwnerShouldNotBeFound("account.specified=false");
    }

    @Test
    @Transactional
    void getAllVehicleOwnersByAccountIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        vehicleOwnerRepository.saveAndFlush(vehicleOwner);

        // Get all the vehicleOwnerList where account is greater than or equal to DEFAULT_ACCOUNT
        defaultVehicleOwnerShouldBeFound("account.greaterThanOrEqual=" + DEFAULT_ACCOUNT);

        // Get all the vehicleOwnerList where account is greater than or equal to UPDATED_ACCOUNT
        defaultVehicleOwnerShouldNotBeFound("account.greaterThanOrEqual=" + UPDATED_ACCOUNT);
    }

    @Test
    @Transactional
    void getAllVehicleOwnersByAccountIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        vehicleOwnerRepository.saveAndFlush(vehicleOwner);

        // Get all the vehicleOwnerList where account is less than or equal to DEFAULT_ACCOUNT
        defaultVehicleOwnerShouldBeFound("account.lessThanOrEqual=" + DEFAULT_ACCOUNT);

        // Get all the vehicleOwnerList where account is less than or equal to SMALLER_ACCOUNT
        defaultVehicleOwnerShouldNotBeFound("account.lessThanOrEqual=" + SMALLER_ACCOUNT);
    }

    @Test
    @Transactional
    void getAllVehicleOwnersByAccountIsLessThanSomething() throws Exception {
        // Initialize the database
        vehicleOwnerRepository.saveAndFlush(vehicleOwner);

        // Get all the vehicleOwnerList where account is less than DEFAULT_ACCOUNT
        defaultVehicleOwnerShouldNotBeFound("account.lessThan=" + DEFAULT_ACCOUNT);

        // Get all the vehicleOwnerList where account is less than UPDATED_ACCOUNT
        defaultVehicleOwnerShouldBeFound("account.lessThan=" + UPDATED_ACCOUNT);
    }

    @Test
    @Transactional
    void getAllVehicleOwnersByAccountIsGreaterThanSomething() throws Exception {
        // Initialize the database
        vehicleOwnerRepository.saveAndFlush(vehicleOwner);

        // Get all the vehicleOwnerList where account is greater than DEFAULT_ACCOUNT
        defaultVehicleOwnerShouldNotBeFound("account.greaterThan=" + DEFAULT_ACCOUNT);

        // Get all the vehicleOwnerList where account is greater than SMALLER_ACCOUNT
        defaultVehicleOwnerShouldBeFound("account.greaterThan=" + SMALLER_ACCOUNT);
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultVehicleOwnerShouldBeFound(String filter) throws Exception {
        restVehicleOwnerMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(vehicleOwner.getId().intValue())))
            .andExpect(jsonPath("$.[*].citizenSerial").value(hasItem(DEFAULT_CITIZEN_SERIAL)))
            .andExpect(jsonPath("$.[*].passportSerial").value(hasItem(DEFAULT_PASSPORT_SERIAL)))
            .andExpect(jsonPath("$.[*].entitySerial").value(hasItem(DEFAULT_ENTITY_SERIAL)))
            .andExpect(jsonPath("$.[*].fullName").value(hasItem(DEFAULT_FULL_NAME)))
            .andExpect(jsonPath("$.[*].firstName").value(hasItem(DEFAULT_FIRST_NAME)))
            .andExpect(jsonPath("$.[*].middleName").value(hasItem(DEFAULT_MIDDLE_NAME)))
            .andExpect(jsonPath("$.[*].grandName").value(hasItem(DEFAULT_GRAND_NAME)))
            .andExpect(jsonPath("$.[*].surname").value(hasItem(DEFAULT_SURNAME)))
            .andExpect(jsonPath("$.[*].aka").value(hasItem(DEFAULT_AKA)))
            .andExpect(jsonPath("$.[*].dob").value(hasItem(DEFAULT_DOB.toString())))
            .andExpect(jsonPath("$.[*].gender").value(hasItem(DEFAULT_GENDER)))
            .andExpect(jsonPath("$.[*].driverLicenseSerial").value(hasItem(DEFAULT_DRIVER_LICENSE_SERIAL)))
            .andExpect(jsonPath("$.[*].driverLicenseType").value(hasItem(DEFAULT_DRIVER_LICENSE_TYPE.intValue())))
            .andExpect(jsonPath("$.[*].jurisdiction").value(hasItem(DEFAULT_JURISDICTION.intValue())))
            .andExpect(jsonPath("$.[*].updated").value(hasItem(DEFAULT_UPDATED.toString())))
            .andExpect(jsonPath("$.[*].account").value(hasItem(DEFAULT_ACCOUNT.doubleValue())));

        // Check, that the count call also returns 1
        restVehicleOwnerMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultVehicleOwnerShouldNotBeFound(String filter) throws Exception {
        restVehicleOwnerMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restVehicleOwnerMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    void getNonExistingVehicleOwner() throws Exception {
        // Get the vehicleOwner
        restVehicleOwnerMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingVehicleOwner() throws Exception {
        // Initialize the database
        vehicleOwnerRepository.saveAndFlush(vehicleOwner);

        int databaseSizeBeforeUpdate = vehicleOwnerRepository.findAll().size();

        // Update the vehicleOwner
        VehicleOwner updatedVehicleOwner = vehicleOwnerRepository.findById(vehicleOwner.getId()).get();
        // Disconnect from session so that the updates on updatedVehicleOwner are not directly saved in db
        em.detach(updatedVehicleOwner);
        updatedVehicleOwner
            .citizenSerial(UPDATED_CITIZEN_SERIAL)
            .passportSerial(UPDATED_PASSPORT_SERIAL)
            .entitySerial(UPDATED_ENTITY_SERIAL)
            .fullName(UPDATED_FULL_NAME)
            .firstName(UPDATED_FIRST_NAME)
            .middleName(UPDATED_MIDDLE_NAME)
            .grandName(UPDATED_GRAND_NAME)
            .surname(UPDATED_SURNAME)
            .aka(UPDATED_AKA)
            .dob(UPDATED_DOB)
            .gender(UPDATED_GENDER)
            .driverLicenseSerial(UPDATED_DRIVER_LICENSE_SERIAL)
            .driverLicenseType(UPDATED_DRIVER_LICENSE_TYPE)
            .jurisdiction(UPDATED_JURISDICTION)
            .updated(UPDATED_UPDATED)
            .account(UPDATED_ACCOUNT);
        VehicleOwnerDTO vehicleOwnerDTO = vehicleOwnerMapper.toDto(updatedVehicleOwner);

        restVehicleOwnerMockMvc
            .perform(
                put(ENTITY_API_URL_ID, vehicleOwnerDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(vehicleOwnerDTO))
            )
            .andExpect(status().isOk());

        // Validate the VehicleOwner in the database
        List<VehicleOwner> vehicleOwnerList = vehicleOwnerRepository.findAll();
        assertThat(vehicleOwnerList).hasSize(databaseSizeBeforeUpdate);
        VehicleOwner testVehicleOwner = vehicleOwnerList.get(vehicleOwnerList.size() - 1);
        assertThat(testVehicleOwner.getCitizenSerial()).isEqualTo(UPDATED_CITIZEN_SERIAL);
        assertThat(testVehicleOwner.getPassportSerial()).isEqualTo(UPDATED_PASSPORT_SERIAL);
        assertThat(testVehicleOwner.getEntitySerial()).isEqualTo(UPDATED_ENTITY_SERIAL);
        assertThat(testVehicleOwner.getFullName()).isEqualTo(UPDATED_FULL_NAME);
        assertThat(testVehicleOwner.getFirstName()).isEqualTo(UPDATED_FIRST_NAME);
        assertThat(testVehicleOwner.getMiddleName()).isEqualTo(UPDATED_MIDDLE_NAME);
        assertThat(testVehicleOwner.getGrandName()).isEqualTo(UPDATED_GRAND_NAME);
        assertThat(testVehicleOwner.getSurname()).isEqualTo(UPDATED_SURNAME);
        assertThat(testVehicleOwner.getAka()).isEqualTo(UPDATED_AKA);
        assertThat(testVehicleOwner.getDob()).isEqualTo(UPDATED_DOB);
        assertThat(testVehicleOwner.getGender()).isEqualTo(UPDATED_GENDER);
        assertThat(testVehicleOwner.getDriverLicenseSerial()).isEqualTo(UPDATED_DRIVER_LICENSE_SERIAL);
        assertThat(testVehicleOwner.getDriverLicenseType()).isEqualTo(UPDATED_DRIVER_LICENSE_TYPE);
        assertThat(testVehicleOwner.getJurisdiction()).isEqualTo(UPDATED_JURISDICTION);
        assertThat(testVehicleOwner.getUpdated()).isEqualTo(UPDATED_UPDATED);
        assertThat(testVehicleOwner.getAccount()).isEqualTo(UPDATED_ACCOUNT);
    }

    @Test
    @Transactional
    void putNonExistingVehicleOwner() throws Exception {
        int databaseSizeBeforeUpdate = vehicleOwnerRepository.findAll().size();
        vehicleOwner.setId(count.incrementAndGet());

        // Create the VehicleOwner
        VehicleOwnerDTO vehicleOwnerDTO = vehicleOwnerMapper.toDto(vehicleOwner);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restVehicleOwnerMockMvc
            .perform(
                put(ENTITY_API_URL_ID, vehicleOwnerDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(vehicleOwnerDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the VehicleOwner in the database
        List<VehicleOwner> vehicleOwnerList = vehicleOwnerRepository.findAll();
        assertThat(vehicleOwnerList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchVehicleOwner() throws Exception {
        int databaseSizeBeforeUpdate = vehicleOwnerRepository.findAll().size();
        vehicleOwner.setId(count.incrementAndGet());

        // Create the VehicleOwner
        VehicleOwnerDTO vehicleOwnerDTO = vehicleOwnerMapper.toDto(vehicleOwner);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restVehicleOwnerMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(vehicleOwnerDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the VehicleOwner in the database
        List<VehicleOwner> vehicleOwnerList = vehicleOwnerRepository.findAll();
        assertThat(vehicleOwnerList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamVehicleOwner() throws Exception {
        int databaseSizeBeforeUpdate = vehicleOwnerRepository.findAll().size();
        vehicleOwner.setId(count.incrementAndGet());

        // Create the VehicleOwner
        VehicleOwnerDTO vehicleOwnerDTO = vehicleOwnerMapper.toDto(vehicleOwner);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restVehicleOwnerMockMvc
            .perform(
                put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(vehicleOwnerDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the VehicleOwner in the database
        List<VehicleOwner> vehicleOwnerList = vehicleOwnerRepository.findAll();
        assertThat(vehicleOwnerList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateVehicleOwnerWithPatch() throws Exception {
        // Initialize the database
        vehicleOwnerRepository.saveAndFlush(vehicleOwner);

        int databaseSizeBeforeUpdate = vehicleOwnerRepository.findAll().size();

        // Update the vehicleOwner using partial update
        VehicleOwner partialUpdatedVehicleOwner = new VehicleOwner();
        partialUpdatedVehicleOwner.setId(vehicleOwner.getId());

        partialUpdatedVehicleOwner
            .passportSerial(UPDATED_PASSPORT_SERIAL)
            .entitySerial(UPDATED_ENTITY_SERIAL)
            .fullName(UPDATED_FULL_NAME)
            .firstName(UPDATED_FIRST_NAME)
            .grandName(UPDATED_GRAND_NAME)
            .surname(UPDATED_SURNAME)
            .dob(UPDATED_DOB)
            .gender(UPDATED_GENDER)
            .driverLicenseSerial(UPDATED_DRIVER_LICENSE_SERIAL)
            .driverLicenseType(UPDATED_DRIVER_LICENSE_TYPE)
            .updated(UPDATED_UPDATED)
            .account(UPDATED_ACCOUNT);

        restVehicleOwnerMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedVehicleOwner.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedVehicleOwner))
            )
            .andExpect(status().isOk());

        // Validate the VehicleOwner in the database
        List<VehicleOwner> vehicleOwnerList = vehicleOwnerRepository.findAll();
        assertThat(vehicleOwnerList).hasSize(databaseSizeBeforeUpdate);
        VehicleOwner testVehicleOwner = vehicleOwnerList.get(vehicleOwnerList.size() - 1);
        assertThat(testVehicleOwner.getCitizenSerial()).isEqualTo(DEFAULT_CITIZEN_SERIAL);
        assertThat(testVehicleOwner.getPassportSerial()).isEqualTo(UPDATED_PASSPORT_SERIAL);
        assertThat(testVehicleOwner.getEntitySerial()).isEqualTo(UPDATED_ENTITY_SERIAL);
        assertThat(testVehicleOwner.getFullName()).isEqualTo(UPDATED_FULL_NAME);
        assertThat(testVehicleOwner.getFirstName()).isEqualTo(UPDATED_FIRST_NAME);
        assertThat(testVehicleOwner.getMiddleName()).isEqualTo(DEFAULT_MIDDLE_NAME);
        assertThat(testVehicleOwner.getGrandName()).isEqualTo(UPDATED_GRAND_NAME);
        assertThat(testVehicleOwner.getSurname()).isEqualTo(UPDATED_SURNAME);
        assertThat(testVehicleOwner.getAka()).isEqualTo(DEFAULT_AKA);
        assertThat(testVehicleOwner.getDob()).isEqualTo(UPDATED_DOB);
        assertThat(testVehicleOwner.getGender()).isEqualTo(UPDATED_GENDER);
        assertThat(testVehicleOwner.getDriverLicenseSerial()).isEqualTo(UPDATED_DRIVER_LICENSE_SERIAL);
        assertThat(testVehicleOwner.getDriverLicenseType()).isEqualTo(UPDATED_DRIVER_LICENSE_TYPE);
        assertThat(testVehicleOwner.getJurisdiction()).isEqualTo(DEFAULT_JURISDICTION);
        assertThat(testVehicleOwner.getUpdated()).isEqualTo(UPDATED_UPDATED);
        assertThat(testVehicleOwner.getAccount()).isEqualTo(UPDATED_ACCOUNT);
    }

    @Test
    @Transactional
    void fullUpdateVehicleOwnerWithPatch() throws Exception {
        // Initialize the database
        vehicleOwnerRepository.saveAndFlush(vehicleOwner);

        int databaseSizeBeforeUpdate = vehicleOwnerRepository.findAll().size();

        // Update the vehicleOwner using partial update
        VehicleOwner partialUpdatedVehicleOwner = new VehicleOwner();
        partialUpdatedVehicleOwner.setId(vehicleOwner.getId());

        partialUpdatedVehicleOwner
            .citizenSerial(UPDATED_CITIZEN_SERIAL)
            .passportSerial(UPDATED_PASSPORT_SERIAL)
            .entitySerial(UPDATED_ENTITY_SERIAL)
            .fullName(UPDATED_FULL_NAME)
            .firstName(UPDATED_FIRST_NAME)
            .middleName(UPDATED_MIDDLE_NAME)
            .grandName(UPDATED_GRAND_NAME)
            .surname(UPDATED_SURNAME)
            .aka(UPDATED_AKA)
            .dob(UPDATED_DOB)
            .gender(UPDATED_GENDER)
            .driverLicenseSerial(UPDATED_DRIVER_LICENSE_SERIAL)
            .driverLicenseType(UPDATED_DRIVER_LICENSE_TYPE)
            .jurisdiction(UPDATED_JURISDICTION)
            .updated(UPDATED_UPDATED)
            .account(UPDATED_ACCOUNT);

        restVehicleOwnerMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedVehicleOwner.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedVehicleOwner))
            )
            .andExpect(status().isOk());

        // Validate the VehicleOwner in the database
        List<VehicleOwner> vehicleOwnerList = vehicleOwnerRepository.findAll();
        assertThat(vehicleOwnerList).hasSize(databaseSizeBeforeUpdate);
        VehicleOwner testVehicleOwner = vehicleOwnerList.get(vehicleOwnerList.size() - 1);
        assertThat(testVehicleOwner.getCitizenSerial()).isEqualTo(UPDATED_CITIZEN_SERIAL);
        assertThat(testVehicleOwner.getPassportSerial()).isEqualTo(UPDATED_PASSPORT_SERIAL);
        assertThat(testVehicleOwner.getEntitySerial()).isEqualTo(UPDATED_ENTITY_SERIAL);
        assertThat(testVehicleOwner.getFullName()).isEqualTo(UPDATED_FULL_NAME);
        assertThat(testVehicleOwner.getFirstName()).isEqualTo(UPDATED_FIRST_NAME);
        assertThat(testVehicleOwner.getMiddleName()).isEqualTo(UPDATED_MIDDLE_NAME);
        assertThat(testVehicleOwner.getGrandName()).isEqualTo(UPDATED_GRAND_NAME);
        assertThat(testVehicleOwner.getSurname()).isEqualTo(UPDATED_SURNAME);
        assertThat(testVehicleOwner.getAka()).isEqualTo(UPDATED_AKA);
        assertThat(testVehicleOwner.getDob()).isEqualTo(UPDATED_DOB);
        assertThat(testVehicleOwner.getGender()).isEqualTo(UPDATED_GENDER);
        assertThat(testVehicleOwner.getDriverLicenseSerial()).isEqualTo(UPDATED_DRIVER_LICENSE_SERIAL);
        assertThat(testVehicleOwner.getDriverLicenseType()).isEqualTo(UPDATED_DRIVER_LICENSE_TYPE);
        assertThat(testVehicleOwner.getJurisdiction()).isEqualTo(UPDATED_JURISDICTION);
        assertThat(testVehicleOwner.getUpdated()).isEqualTo(UPDATED_UPDATED);
        assertThat(testVehicleOwner.getAccount()).isEqualTo(UPDATED_ACCOUNT);
    }

    @Test
    @Transactional
    void patchNonExistingVehicleOwner() throws Exception {
        int databaseSizeBeforeUpdate = vehicleOwnerRepository.findAll().size();
        vehicleOwner.setId(count.incrementAndGet());

        // Create the VehicleOwner
        VehicleOwnerDTO vehicleOwnerDTO = vehicleOwnerMapper.toDto(vehicleOwner);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restVehicleOwnerMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, vehicleOwnerDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(vehicleOwnerDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the VehicleOwner in the database
        List<VehicleOwner> vehicleOwnerList = vehicleOwnerRepository.findAll();
        assertThat(vehicleOwnerList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchVehicleOwner() throws Exception {
        int databaseSizeBeforeUpdate = vehicleOwnerRepository.findAll().size();
        vehicleOwner.setId(count.incrementAndGet());

        // Create the VehicleOwner
        VehicleOwnerDTO vehicleOwnerDTO = vehicleOwnerMapper.toDto(vehicleOwner);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restVehicleOwnerMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(vehicleOwnerDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the VehicleOwner in the database
        List<VehicleOwner> vehicleOwnerList = vehicleOwnerRepository.findAll();
        assertThat(vehicleOwnerList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamVehicleOwner() throws Exception {
        int databaseSizeBeforeUpdate = vehicleOwnerRepository.findAll().size();
        vehicleOwner.setId(count.incrementAndGet());

        // Create the VehicleOwner
        VehicleOwnerDTO vehicleOwnerDTO = vehicleOwnerMapper.toDto(vehicleOwner);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restVehicleOwnerMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(vehicleOwnerDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the VehicleOwner in the database
        List<VehicleOwner> vehicleOwnerList = vehicleOwnerRepository.findAll();
        assertThat(vehicleOwnerList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteVehicleOwner() throws Exception {
        // Initialize the database
        vehicleOwnerRepository.saveAndFlush(vehicleOwner);

        int databaseSizeBeforeDelete = vehicleOwnerRepository.findAll().size();

        // Delete the vehicleOwner
        restVehicleOwnerMockMvc
            .perform(delete(ENTITY_API_URL_ID, vehicleOwner.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<VehicleOwner> vehicleOwnerList = vehicleOwnerRepository.findAll();
        assertThat(vehicleOwnerList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
