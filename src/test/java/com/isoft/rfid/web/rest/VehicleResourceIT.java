package com.isoft.rfid.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.isoft.rfid.IntegrationTest;
import com.isoft.rfid.domain.Vehicle;
import com.isoft.rfid.repository.VehicleRepository;
import com.isoft.rfid.service.criteria.VehicleCriteria;
import com.isoft.rfid.service.dto.VehicleDTO;
import com.isoft.rfid.service.mapper.VehicleMapper;
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
 * Integration tests for the {@link VehicleResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class VehicleResourceIT {

    private static final String DEFAULT_GOVID = "AAAAAAAAAA";
    private static final String UPDATED_GOVID = "BBBBBBBBBB";

    private static final String DEFAULT_PLATE = "AAAAAAAAAA";
    private static final String UPDATED_PLATE = "BBBBBBBBBB";

    private static final String DEFAULT_RFID = "AAAAAAAAAA";
    private static final String UPDATED_RFID = "BBBBBBBBBB";

    private static final Long DEFAULT_DRIVER = 1L;
    private static final Long UPDATED_DRIVER = 2L;
    private static final Long SMALLER_DRIVER = 1L - 1L;

    private static final String DEFAULT_PRODUCER = "AAAAAAAAAA";
    private static final String UPDATED_PRODUCER = "BBBBBBBBBB";

    private static final String DEFAULT_MODEL = "AAAAAAAAAA";
    private static final String UPDATED_MODEL = "BBBBBBBBBB";

    private static final Long DEFAULT_YEAR = 1L;
    private static final Long UPDATED_YEAR = 2L;
    private static final Long SMALLER_YEAR = 1L - 1L;

    private static final String DEFAULT_COLOR = "AAAAAAAAAA";
    private static final String UPDATED_COLOR = "BBBBBBBBBB";

    private static final String DEFAULT_DECALS = "AAAAAAAAAA";
    private static final String UPDATED_DECALS = "BBBBBBBBBB";

    private static final String DEFAULT_SPOILERS = "AAAAAAAAAA";
    private static final String UPDATED_SPOILERS = "BBBBBBBBBB";

    private static final String DEFAULT_GLASS = "AAAAAAAAAA";
    private static final String UPDATED_GLASS = "BBBBBBBBBB";

    private static final String DEFAULT_CHASSIS_SERIAL = "AAAAAAAAAA";
    private static final String UPDATED_CHASSIS_SERIAL = "BBBBBBBBBB";

    private static final String DEFAULT_MOTOR_SERIAL = "AAAAAAAAAA";
    private static final String UPDATED_MOTOR_SERIAL = "BBBBBBBBBB";

    private static final String DEFAULT_VEHICLE_LICENSE_SERIAL = "AAAAAAAAAA";
    private static final String UPDATED_VEHICLE_LICENSE_SERIAL = "BBBBBBBBBB";

    private static final Long DEFAULT_VEHICLE_LICENSE_TYPE = 1L;
    private static final Long UPDATED_VEHICLE_LICENSE_TYPE = 2L;
    private static final Long SMALLER_VEHICLE_LICENSE_TYPE = 1L - 1L;

    private static final Long DEFAULT_VEHICLE_TYPE = 1L;
    private static final Long UPDATED_VEHICLE_TYPE = 2L;
    private static final Long SMALLER_VEHICLE_TYPE = 1L - 1L;

    private static final Integer DEFAULT_WANTED = 1;
    private static final Integer UPDATED_WANTED = 2;
    private static final Integer SMALLER_WANTED = 1 - 1;

    private static final Integer DEFAULT_LICENSE_REVOKED = 1;
    private static final Integer UPDATED_LICENSE_REVOKED = 2;
    private static final Integer SMALLER_LICENSE_REVOKED = 1 - 1;

    private static final Integer DEFAULT_LICENSE_EXPIRED = 1;
    private static final Integer UPDATED_LICENSE_EXPIRED = 2;
    private static final Integer SMALLER_LICENSE_EXPIRED = 1 - 1;

    private static final Long DEFAULT_JURISDICTION = 1L;
    private static final Long UPDATED_JURISDICTION = 2L;
    private static final Long SMALLER_JURISDICTION = 1L - 1L;

    private static final Instant DEFAULT_UPDATED = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_UPDATED = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final String DEFAULT_FUEL_TYPE = "AAAAAAAAAA";
    private static final String UPDATED_FUEL_TYPE = "BBBBBBBBBB";

    private static final Long DEFAULT_MOTOR_CC = 1L;
    private static final Long UPDATED_MOTOR_CC = 2L;
    private static final Long SMALLER_MOTOR_CC = 1L - 1L;

    private static final Instant DEFAULT_LICENSE_ISSUED = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_LICENSE_ISSUED = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Instant DEFAULT_LICENSE_EXPIRES = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_LICENSE_EXPIRES = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Long DEFAULT_WT_KG = 1L;
    private static final Long UPDATED_WT_KG = 2L;
    private static final Long SMALLER_WT_KG = 1L - 1L;

    private static final Integer DEFAULT_PASSENGERS = 1;
    private static final Integer UPDATED_PASSENGERS = 2;
    private static final Integer SMALLER_PASSENGERS = 1 - 1;

    private static final Integer DEFAULT_TRACTOR_PARTS = 1;
    private static final Integer UPDATED_TRACTOR_PARTS = 2;
    private static final Integer SMALLER_TRACTOR_PARTS = 1 - 1;

    private static final Integer DEFAULT_EXTRA_SIZE_PERCENT = 1;
    private static final Integer UPDATED_EXTRA_SIZE_PERCENT = 2;
    private static final Integer SMALLER_EXTRA_SIZE_PERCENT = 1 - 1;

    private static final Long DEFAULT_WT_EXTRA = 1L;
    private static final Long UPDATED_WT_EXTRA = 2L;
    private static final Long SMALLER_WT_EXTRA = 1L - 1L;

    private static final String DEFAULT_BIKE_DATA = "AAAAAAAAAA";
    private static final String UPDATED_BIKE_DATA = "BBBBBBBBBB";

    private static final Long DEFAULT_WANTED_BY = 1L;
    private static final Long UPDATED_WANTED_BY = 2L;
    private static final Long SMALLER_WANTED_BY = 1L - 1L;

    private static final String DEFAULT_WANTED_FOR = "AAAAAAAAAA";
    private static final String UPDATED_WANTED_FOR = "BBBBBBBBBB";

    private static final Long DEFAULT_OFFICE = 1L;
    private static final Long UPDATED_OFFICE = 2L;
    private static final Long SMALLER_OFFICE = 1L - 1L;

    private static final String DEFAULT_STATUS_NAME = "AAAAAAAAAA";
    private static final String UPDATED_STATUS_NAME = "BBBBBBBBBB";

    private static final Instant DEFAULT_STOLEN = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_STOLEN = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final String ENTITY_API_URL = "/api/vehicles";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private VehicleRepository vehicleRepository;

    @Autowired
    private VehicleMapper vehicleMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restVehicleMockMvc;

    private Vehicle vehicle;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Vehicle createEntity(EntityManager em) {
        Vehicle vehicle = new Vehicle()
            .govid(DEFAULT_GOVID)
            .plate(DEFAULT_PLATE)
            .rfid(DEFAULT_RFID)
            .driver(DEFAULT_DRIVER)
            .producer(DEFAULT_PRODUCER)
            .model(DEFAULT_MODEL)
            .year(DEFAULT_YEAR)
            .color(DEFAULT_COLOR)
            .decals(DEFAULT_DECALS)
            .spoilers(DEFAULT_SPOILERS)
            .glass(DEFAULT_GLASS)
            .chassisSerial(DEFAULT_CHASSIS_SERIAL)
            .motorSerial(DEFAULT_MOTOR_SERIAL)
            .vehicleLicenseSerial(DEFAULT_VEHICLE_LICENSE_SERIAL)
            .vehicleLicenseType(DEFAULT_VEHICLE_LICENSE_TYPE)
            .vehicleType(DEFAULT_VEHICLE_TYPE)
            .wanted(DEFAULT_WANTED)
            .licenseRevoked(DEFAULT_LICENSE_REVOKED)
            .licenseExpired(DEFAULT_LICENSE_EXPIRED)
            .jurisdiction(DEFAULT_JURISDICTION)
            .updated(DEFAULT_UPDATED)
            .fuelType(DEFAULT_FUEL_TYPE)
            .motorCc(DEFAULT_MOTOR_CC)
            .licenseIssued(DEFAULT_LICENSE_ISSUED)
            .licenseExpires(DEFAULT_LICENSE_EXPIRES)
            .wtKg(DEFAULT_WT_KG)
            .passengers(DEFAULT_PASSENGERS)
            .tractorParts(DEFAULT_TRACTOR_PARTS)
            .extraSizePercent(DEFAULT_EXTRA_SIZE_PERCENT)
            .wtExtra(DEFAULT_WT_EXTRA)
            .bikeData(DEFAULT_BIKE_DATA)
            .wantedBy(DEFAULT_WANTED_BY)
            .wantedFor(DEFAULT_WANTED_FOR)
            .office(DEFAULT_OFFICE)
            .statusName(DEFAULT_STATUS_NAME)
            .stolen(DEFAULT_STOLEN);
        return vehicle;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Vehicle createUpdatedEntity(EntityManager em) {
        Vehicle vehicle = new Vehicle()
            .govid(UPDATED_GOVID)
            .plate(UPDATED_PLATE)
            .rfid(UPDATED_RFID)
            .driver(UPDATED_DRIVER)
            .producer(UPDATED_PRODUCER)
            .model(UPDATED_MODEL)
            .year(UPDATED_YEAR)
            .color(UPDATED_COLOR)
            .decals(UPDATED_DECALS)
            .spoilers(UPDATED_SPOILERS)
            .glass(UPDATED_GLASS)
            .chassisSerial(UPDATED_CHASSIS_SERIAL)
            .motorSerial(UPDATED_MOTOR_SERIAL)
            .vehicleLicenseSerial(UPDATED_VEHICLE_LICENSE_SERIAL)
            .vehicleLicenseType(UPDATED_VEHICLE_LICENSE_TYPE)
            .vehicleType(UPDATED_VEHICLE_TYPE)
            .wanted(UPDATED_WANTED)
            .licenseRevoked(UPDATED_LICENSE_REVOKED)
            .licenseExpired(UPDATED_LICENSE_EXPIRED)
            .jurisdiction(UPDATED_JURISDICTION)
            .updated(UPDATED_UPDATED)
            .fuelType(UPDATED_FUEL_TYPE)
            .motorCc(UPDATED_MOTOR_CC)
            .licenseIssued(UPDATED_LICENSE_ISSUED)
            .licenseExpires(UPDATED_LICENSE_EXPIRES)
            .wtKg(UPDATED_WT_KG)
            .passengers(UPDATED_PASSENGERS)
            .tractorParts(UPDATED_TRACTOR_PARTS)
            .extraSizePercent(UPDATED_EXTRA_SIZE_PERCENT)
            .wtExtra(UPDATED_WT_EXTRA)
            .bikeData(UPDATED_BIKE_DATA)
            .wantedBy(UPDATED_WANTED_BY)
            .wantedFor(UPDATED_WANTED_FOR)
            .office(UPDATED_OFFICE)
            .statusName(UPDATED_STATUS_NAME)
            .stolen(UPDATED_STOLEN);
        return vehicle;
    }

    @BeforeEach
    public void initTest() {
        vehicle = createEntity(em);
    }

    @Test
    @Transactional
    void createVehicle() throws Exception {
        int databaseSizeBeforeCreate = vehicleRepository.findAll().size();
        // Create the Vehicle
        VehicleDTO vehicleDTO = vehicleMapper.toDto(vehicle);
        restVehicleMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(vehicleDTO)))
            .andExpect(status().isCreated());

        // Validate the Vehicle in the database
        List<Vehicle> vehicleList = vehicleRepository.findAll();
        assertThat(vehicleList).hasSize(databaseSizeBeforeCreate + 1);
        Vehicle testVehicle = vehicleList.get(vehicleList.size() - 1);
        assertThat(testVehicle.getGovid()).isEqualTo(DEFAULT_GOVID);
        assertThat(testVehicle.getPlate()).isEqualTo(DEFAULT_PLATE);
        assertThat(testVehicle.getRfid()).isEqualTo(DEFAULT_RFID);
        assertThat(testVehicle.getDriver()).isEqualTo(DEFAULT_DRIVER);
        assertThat(testVehicle.getProducer()).isEqualTo(DEFAULT_PRODUCER);
        assertThat(testVehicle.getModel()).isEqualTo(DEFAULT_MODEL);
        assertThat(testVehicle.getYear()).isEqualTo(DEFAULT_YEAR);
        assertThat(testVehicle.getColor()).isEqualTo(DEFAULT_COLOR);
        assertThat(testVehicle.getDecals()).isEqualTo(DEFAULT_DECALS);
        assertThat(testVehicle.getSpoilers()).isEqualTo(DEFAULT_SPOILERS);
        assertThat(testVehicle.getGlass()).isEqualTo(DEFAULT_GLASS);
        assertThat(testVehicle.getChassisSerial()).isEqualTo(DEFAULT_CHASSIS_SERIAL);
        assertThat(testVehicle.getMotorSerial()).isEqualTo(DEFAULT_MOTOR_SERIAL);
        assertThat(testVehicle.getVehicleLicenseSerial()).isEqualTo(DEFAULT_VEHICLE_LICENSE_SERIAL);
        assertThat(testVehicle.getVehicleLicenseType()).isEqualTo(DEFAULT_VEHICLE_LICENSE_TYPE);
        assertThat(testVehicle.getVehicleType()).isEqualTo(DEFAULT_VEHICLE_TYPE);
        assertThat(testVehicle.getWanted()).isEqualTo(DEFAULT_WANTED);
        assertThat(testVehicle.getLicenseRevoked()).isEqualTo(DEFAULT_LICENSE_REVOKED);
        assertThat(testVehicle.getLicenseExpired()).isEqualTo(DEFAULT_LICENSE_EXPIRED);
        assertThat(testVehicle.getJurisdiction()).isEqualTo(DEFAULT_JURISDICTION);
        assertThat(testVehicle.getUpdated()).isEqualTo(DEFAULT_UPDATED);
        assertThat(testVehicle.getFuelType()).isEqualTo(DEFAULT_FUEL_TYPE);
        assertThat(testVehicle.getMotorCc()).isEqualTo(DEFAULT_MOTOR_CC);
        assertThat(testVehicle.getLicenseIssued()).isEqualTo(DEFAULT_LICENSE_ISSUED);
        assertThat(testVehicle.getLicenseExpires()).isEqualTo(DEFAULT_LICENSE_EXPIRES);
        assertThat(testVehicle.getWtKg()).isEqualTo(DEFAULT_WT_KG);
        assertThat(testVehicle.getPassengers()).isEqualTo(DEFAULT_PASSENGERS);
        assertThat(testVehicle.getTractorParts()).isEqualTo(DEFAULT_TRACTOR_PARTS);
        assertThat(testVehicle.getExtraSizePercent()).isEqualTo(DEFAULT_EXTRA_SIZE_PERCENT);
        assertThat(testVehicle.getWtExtra()).isEqualTo(DEFAULT_WT_EXTRA);
        assertThat(testVehicle.getBikeData()).isEqualTo(DEFAULT_BIKE_DATA);
        assertThat(testVehicle.getWantedBy()).isEqualTo(DEFAULT_WANTED_BY);
        assertThat(testVehicle.getWantedFor()).isEqualTo(DEFAULT_WANTED_FOR);
        assertThat(testVehicle.getOffice()).isEqualTo(DEFAULT_OFFICE);
        assertThat(testVehicle.getStatusName()).isEqualTo(DEFAULT_STATUS_NAME);
        assertThat(testVehicle.getStolen()).isEqualTo(DEFAULT_STOLEN);
    }

    @Test
    @Transactional
    void createVehicleWithExistingId() throws Exception {
        // Create the Vehicle with an existing ID
        vehicle.setId(1L);
        VehicleDTO vehicleDTO = vehicleMapper.toDto(vehicle);

        int databaseSizeBeforeCreate = vehicleRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restVehicleMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(vehicleDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Vehicle in the database
        List<Vehicle> vehicleList = vehicleRepository.findAll();
        assertThat(vehicleList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkPlateIsRequired() throws Exception {
        int databaseSizeBeforeTest = vehicleRepository.findAll().size();
        // set the field null
        vehicle.setPlate(null);

        // Create the Vehicle, which fails.
        VehicleDTO vehicleDTO = vehicleMapper.toDto(vehicle);

        restVehicleMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(vehicleDTO)))
            .andExpect(status().isBadRequest());

        List<Vehicle> vehicleList = vehicleRepository.findAll();
        assertThat(vehicleList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkRfidIsRequired() throws Exception {
        int databaseSizeBeforeTest = vehicleRepository.findAll().size();
        // set the field null
        vehicle.setRfid(null);

        // Create the Vehicle, which fails.
        VehicleDTO vehicleDTO = vehicleMapper.toDto(vehicle);

        restVehicleMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(vehicleDTO)))
            .andExpect(status().isBadRequest());

        List<Vehicle> vehicleList = vehicleRepository.findAll();
        assertThat(vehicleList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkDriverIsRequired() throws Exception {
        int databaseSizeBeforeTest = vehicleRepository.findAll().size();
        // set the field null
        vehicle.setDriver(null);

        // Create the Vehicle, which fails.
        VehicleDTO vehicleDTO = vehicleMapper.toDto(vehicle);

        restVehicleMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(vehicleDTO)))
            .andExpect(status().isBadRequest());

        List<Vehicle> vehicleList = vehicleRepository.findAll();
        assertThat(vehicleList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkVehicleLicenseTypeIsRequired() throws Exception {
        int databaseSizeBeforeTest = vehicleRepository.findAll().size();
        // set the field null
        vehicle.setVehicleLicenseType(null);

        // Create the Vehicle, which fails.
        VehicleDTO vehicleDTO = vehicleMapper.toDto(vehicle);

        restVehicleMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(vehicleDTO)))
            .andExpect(status().isBadRequest());

        List<Vehicle> vehicleList = vehicleRepository.findAll();
        assertThat(vehicleList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkVehicleTypeIsRequired() throws Exception {
        int databaseSizeBeforeTest = vehicleRepository.findAll().size();
        // set the field null
        vehicle.setVehicleType(null);

        // Create the Vehicle, which fails.
        VehicleDTO vehicleDTO = vehicleMapper.toDto(vehicle);

        restVehicleMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(vehicleDTO)))
            .andExpect(status().isBadRequest());

        List<Vehicle> vehicleList = vehicleRepository.findAll();
        assertThat(vehicleList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkJurisdictionIsRequired() throws Exception {
        int databaseSizeBeforeTest = vehicleRepository.findAll().size();
        // set the field null
        vehicle.setJurisdiction(null);

        // Create the Vehicle, which fails.
        VehicleDTO vehicleDTO = vehicleMapper.toDto(vehicle);

        restVehicleMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(vehicleDTO)))
            .andExpect(status().isBadRequest());

        List<Vehicle> vehicleList = vehicleRepository.findAll();
        assertThat(vehicleList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkUpdatedIsRequired() throws Exception {
        int databaseSizeBeforeTest = vehicleRepository.findAll().size();
        // set the field null
        vehicle.setUpdated(null);

        // Create the Vehicle, which fails.
        VehicleDTO vehicleDTO = vehicleMapper.toDto(vehicle);

        restVehicleMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(vehicleDTO)))
            .andExpect(status().isBadRequest());

        List<Vehicle> vehicleList = vehicleRepository.findAll();
        assertThat(vehicleList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllVehicles() throws Exception {
        // Initialize the database
        vehicleRepository.saveAndFlush(vehicle);

        // Get all the vehicleList
        restVehicleMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(vehicle.getId().intValue())))
            .andExpect(jsonPath("$.[*].govid").value(hasItem(DEFAULT_GOVID)))
            .andExpect(jsonPath("$.[*].plate").value(hasItem(DEFAULT_PLATE)))
            .andExpect(jsonPath("$.[*].rfid").value(hasItem(DEFAULT_RFID)))
            .andExpect(jsonPath("$.[*].driver").value(hasItem(DEFAULT_DRIVER.intValue())))
            .andExpect(jsonPath("$.[*].producer").value(hasItem(DEFAULT_PRODUCER)))
            .andExpect(jsonPath("$.[*].model").value(hasItem(DEFAULT_MODEL)))
            .andExpect(jsonPath("$.[*].year").value(hasItem(DEFAULT_YEAR.intValue())))
            .andExpect(jsonPath("$.[*].color").value(hasItem(DEFAULT_COLOR)))
            .andExpect(jsonPath("$.[*].decals").value(hasItem(DEFAULT_DECALS)))
            .andExpect(jsonPath("$.[*].spoilers").value(hasItem(DEFAULT_SPOILERS)))
            .andExpect(jsonPath("$.[*].glass").value(hasItem(DEFAULT_GLASS)))
            .andExpect(jsonPath("$.[*].chassisSerial").value(hasItem(DEFAULT_CHASSIS_SERIAL)))
            .andExpect(jsonPath("$.[*].motorSerial").value(hasItem(DEFAULT_MOTOR_SERIAL)))
            .andExpect(jsonPath("$.[*].vehicleLicenseSerial").value(hasItem(DEFAULT_VEHICLE_LICENSE_SERIAL)))
            .andExpect(jsonPath("$.[*].vehicleLicenseType").value(hasItem(DEFAULT_VEHICLE_LICENSE_TYPE.intValue())))
            .andExpect(jsonPath("$.[*].vehicleType").value(hasItem(DEFAULT_VEHICLE_TYPE.intValue())))
            .andExpect(jsonPath("$.[*].wanted").value(hasItem(DEFAULT_WANTED)))
            .andExpect(jsonPath("$.[*].licenseRevoked").value(hasItem(DEFAULT_LICENSE_REVOKED)))
            .andExpect(jsonPath("$.[*].licenseExpired").value(hasItem(DEFAULT_LICENSE_EXPIRED)))
            .andExpect(jsonPath("$.[*].jurisdiction").value(hasItem(DEFAULT_JURISDICTION.intValue())))
            .andExpect(jsonPath("$.[*].updated").value(hasItem(DEFAULT_UPDATED.toString())))
            .andExpect(jsonPath("$.[*].fuelType").value(hasItem(DEFAULT_FUEL_TYPE)))
            .andExpect(jsonPath("$.[*].motorCc").value(hasItem(DEFAULT_MOTOR_CC.intValue())))
            .andExpect(jsonPath("$.[*].licenseIssued").value(hasItem(DEFAULT_LICENSE_ISSUED.toString())))
            .andExpect(jsonPath("$.[*].licenseExpires").value(hasItem(DEFAULT_LICENSE_EXPIRES.toString())))
            .andExpect(jsonPath("$.[*].wtKg").value(hasItem(DEFAULT_WT_KG.intValue())))
            .andExpect(jsonPath("$.[*].passengers").value(hasItem(DEFAULT_PASSENGERS)))
            .andExpect(jsonPath("$.[*].tractorParts").value(hasItem(DEFAULT_TRACTOR_PARTS)))
            .andExpect(jsonPath("$.[*].extraSizePercent").value(hasItem(DEFAULT_EXTRA_SIZE_PERCENT)))
            .andExpect(jsonPath("$.[*].wtExtra").value(hasItem(DEFAULT_WT_EXTRA.intValue())))
            .andExpect(jsonPath("$.[*].bikeData").value(hasItem(DEFAULT_BIKE_DATA)))
            .andExpect(jsonPath("$.[*].wantedBy").value(hasItem(DEFAULT_WANTED_BY.intValue())))
            .andExpect(jsonPath("$.[*].wantedFor").value(hasItem(DEFAULT_WANTED_FOR)))
            .andExpect(jsonPath("$.[*].office").value(hasItem(DEFAULT_OFFICE.intValue())))
            .andExpect(jsonPath("$.[*].statusName").value(hasItem(DEFAULT_STATUS_NAME)))
            .andExpect(jsonPath("$.[*].stolen").value(hasItem(DEFAULT_STOLEN.toString())));
    }

    @Test
    @Transactional
    void getVehicle() throws Exception {
        // Initialize the database
        vehicleRepository.saveAndFlush(vehicle);

        // Get the vehicle
        restVehicleMockMvc
            .perform(get(ENTITY_API_URL_ID, vehicle.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(vehicle.getId().intValue()))
            .andExpect(jsonPath("$.govid").value(DEFAULT_GOVID))
            .andExpect(jsonPath("$.plate").value(DEFAULT_PLATE))
            .andExpect(jsonPath("$.rfid").value(DEFAULT_RFID))
            .andExpect(jsonPath("$.driver").value(DEFAULT_DRIVER.intValue()))
            .andExpect(jsonPath("$.producer").value(DEFAULT_PRODUCER))
            .andExpect(jsonPath("$.model").value(DEFAULT_MODEL))
            .andExpect(jsonPath("$.year").value(DEFAULT_YEAR.intValue()))
            .andExpect(jsonPath("$.color").value(DEFAULT_COLOR))
            .andExpect(jsonPath("$.decals").value(DEFAULT_DECALS))
            .andExpect(jsonPath("$.spoilers").value(DEFAULT_SPOILERS))
            .andExpect(jsonPath("$.glass").value(DEFAULT_GLASS))
            .andExpect(jsonPath("$.chassisSerial").value(DEFAULT_CHASSIS_SERIAL))
            .andExpect(jsonPath("$.motorSerial").value(DEFAULT_MOTOR_SERIAL))
            .andExpect(jsonPath("$.vehicleLicenseSerial").value(DEFAULT_VEHICLE_LICENSE_SERIAL))
            .andExpect(jsonPath("$.vehicleLicenseType").value(DEFAULT_VEHICLE_LICENSE_TYPE.intValue()))
            .andExpect(jsonPath("$.vehicleType").value(DEFAULT_VEHICLE_TYPE.intValue()))
            .andExpect(jsonPath("$.wanted").value(DEFAULT_WANTED))
            .andExpect(jsonPath("$.licenseRevoked").value(DEFAULT_LICENSE_REVOKED))
            .andExpect(jsonPath("$.licenseExpired").value(DEFAULT_LICENSE_EXPIRED))
            .andExpect(jsonPath("$.jurisdiction").value(DEFAULT_JURISDICTION.intValue()))
            .andExpect(jsonPath("$.updated").value(DEFAULT_UPDATED.toString()))
            .andExpect(jsonPath("$.fuelType").value(DEFAULT_FUEL_TYPE))
            .andExpect(jsonPath("$.motorCc").value(DEFAULT_MOTOR_CC.intValue()))
            .andExpect(jsonPath("$.licenseIssued").value(DEFAULT_LICENSE_ISSUED.toString()))
            .andExpect(jsonPath("$.licenseExpires").value(DEFAULT_LICENSE_EXPIRES.toString()))
            .andExpect(jsonPath("$.wtKg").value(DEFAULT_WT_KG.intValue()))
            .andExpect(jsonPath("$.passengers").value(DEFAULT_PASSENGERS))
            .andExpect(jsonPath("$.tractorParts").value(DEFAULT_TRACTOR_PARTS))
            .andExpect(jsonPath("$.extraSizePercent").value(DEFAULT_EXTRA_SIZE_PERCENT))
            .andExpect(jsonPath("$.wtExtra").value(DEFAULT_WT_EXTRA.intValue()))
            .andExpect(jsonPath("$.bikeData").value(DEFAULT_BIKE_DATA))
            .andExpect(jsonPath("$.wantedBy").value(DEFAULT_WANTED_BY.intValue()))
            .andExpect(jsonPath("$.wantedFor").value(DEFAULT_WANTED_FOR))
            .andExpect(jsonPath("$.office").value(DEFAULT_OFFICE.intValue()))
            .andExpect(jsonPath("$.statusName").value(DEFAULT_STATUS_NAME))
            .andExpect(jsonPath("$.stolen").value(DEFAULT_STOLEN.toString()));
    }

    @Test
    @Transactional
    void getVehiclesByIdFiltering() throws Exception {
        // Initialize the database
        vehicleRepository.saveAndFlush(vehicle);

        Long id = vehicle.getId();

        defaultVehicleShouldBeFound("id.equals=" + id);
        defaultVehicleShouldNotBeFound("id.notEquals=" + id);

        defaultVehicleShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultVehicleShouldNotBeFound("id.greaterThan=" + id);

        defaultVehicleShouldBeFound("id.lessThanOrEqual=" + id);
        defaultVehicleShouldNotBeFound("id.lessThan=" + id);
    }

    @Test
    @Transactional
    void getAllVehiclesByGovidIsEqualToSomething() throws Exception {
        // Initialize the database
        vehicleRepository.saveAndFlush(vehicle);

        // Get all the vehicleList where govid equals to DEFAULT_GOVID
        defaultVehicleShouldBeFound("govid.equals=" + DEFAULT_GOVID);

        // Get all the vehicleList where govid equals to UPDATED_GOVID
        defaultVehicleShouldNotBeFound("govid.equals=" + UPDATED_GOVID);
    }

    @Test
    @Transactional
    void getAllVehiclesByGovidIsInShouldWork() throws Exception {
        // Initialize the database
        vehicleRepository.saveAndFlush(vehicle);

        // Get all the vehicleList where govid in DEFAULT_GOVID or UPDATED_GOVID
        defaultVehicleShouldBeFound("govid.in=" + DEFAULT_GOVID + "," + UPDATED_GOVID);

        // Get all the vehicleList where govid equals to UPDATED_GOVID
        defaultVehicleShouldNotBeFound("govid.in=" + UPDATED_GOVID);
    }

    @Test
    @Transactional
    void getAllVehiclesByGovidIsNullOrNotNull() throws Exception {
        // Initialize the database
        vehicleRepository.saveAndFlush(vehicle);

        // Get all the vehicleList where govid is not null
        defaultVehicleShouldBeFound("govid.specified=true");

        // Get all the vehicleList where govid is null
        defaultVehicleShouldNotBeFound("govid.specified=false");
    }

    @Test
    @Transactional
    void getAllVehiclesByGovidContainsSomething() throws Exception {
        // Initialize the database
        vehicleRepository.saveAndFlush(vehicle);

        // Get all the vehicleList where govid contains DEFAULT_GOVID
        defaultVehicleShouldBeFound("govid.contains=" + DEFAULT_GOVID);

        // Get all the vehicleList where govid contains UPDATED_GOVID
        defaultVehicleShouldNotBeFound("govid.contains=" + UPDATED_GOVID);
    }

    @Test
    @Transactional
    void getAllVehiclesByGovidNotContainsSomething() throws Exception {
        // Initialize the database
        vehicleRepository.saveAndFlush(vehicle);

        // Get all the vehicleList where govid does not contain DEFAULT_GOVID
        defaultVehicleShouldNotBeFound("govid.doesNotContain=" + DEFAULT_GOVID);

        // Get all the vehicleList where govid does not contain UPDATED_GOVID
        defaultVehicleShouldBeFound("govid.doesNotContain=" + UPDATED_GOVID);
    }

    @Test
    @Transactional
    void getAllVehiclesByPlateIsEqualToSomething() throws Exception {
        // Initialize the database
        vehicleRepository.saveAndFlush(vehicle);

        // Get all the vehicleList where plate equals to DEFAULT_PLATE
        defaultVehicleShouldBeFound("plate.equals=" + DEFAULT_PLATE);

        // Get all the vehicleList where plate equals to UPDATED_PLATE
        defaultVehicleShouldNotBeFound("plate.equals=" + UPDATED_PLATE);
    }

    @Test
    @Transactional
    void getAllVehiclesByPlateIsInShouldWork() throws Exception {
        // Initialize the database
        vehicleRepository.saveAndFlush(vehicle);

        // Get all the vehicleList where plate in DEFAULT_PLATE or UPDATED_PLATE
        defaultVehicleShouldBeFound("plate.in=" + DEFAULT_PLATE + "," + UPDATED_PLATE);

        // Get all the vehicleList where plate equals to UPDATED_PLATE
        defaultVehicleShouldNotBeFound("plate.in=" + UPDATED_PLATE);
    }

    @Test
    @Transactional
    void getAllVehiclesByPlateIsNullOrNotNull() throws Exception {
        // Initialize the database
        vehicleRepository.saveAndFlush(vehicle);

        // Get all the vehicleList where plate is not null
        defaultVehicleShouldBeFound("plate.specified=true");

        // Get all the vehicleList where plate is null
        defaultVehicleShouldNotBeFound("plate.specified=false");
    }

    @Test
    @Transactional
    void getAllVehiclesByPlateContainsSomething() throws Exception {
        // Initialize the database
        vehicleRepository.saveAndFlush(vehicle);

        // Get all the vehicleList where plate contains DEFAULT_PLATE
        defaultVehicleShouldBeFound("plate.contains=" + DEFAULT_PLATE);

        // Get all the vehicleList where plate contains UPDATED_PLATE
        defaultVehicleShouldNotBeFound("plate.contains=" + UPDATED_PLATE);
    }

    @Test
    @Transactional
    void getAllVehiclesByPlateNotContainsSomething() throws Exception {
        // Initialize the database
        vehicleRepository.saveAndFlush(vehicle);

        // Get all the vehicleList where plate does not contain DEFAULT_PLATE
        defaultVehicleShouldNotBeFound("plate.doesNotContain=" + DEFAULT_PLATE);

        // Get all the vehicleList where plate does not contain UPDATED_PLATE
        defaultVehicleShouldBeFound("plate.doesNotContain=" + UPDATED_PLATE);
    }

    @Test
    @Transactional
    void getAllVehiclesByRfidIsEqualToSomething() throws Exception {
        // Initialize the database
        vehicleRepository.saveAndFlush(vehicle);

        // Get all the vehicleList where rfid equals to DEFAULT_RFID
        defaultVehicleShouldBeFound("rfid.equals=" + DEFAULT_RFID);

        // Get all the vehicleList where rfid equals to UPDATED_RFID
        defaultVehicleShouldNotBeFound("rfid.equals=" + UPDATED_RFID);
    }

    @Test
    @Transactional
    void getAllVehiclesByRfidIsInShouldWork() throws Exception {
        // Initialize the database
        vehicleRepository.saveAndFlush(vehicle);

        // Get all the vehicleList where rfid in DEFAULT_RFID or UPDATED_RFID
        defaultVehicleShouldBeFound("rfid.in=" + DEFAULT_RFID + "," + UPDATED_RFID);

        // Get all the vehicleList where rfid equals to UPDATED_RFID
        defaultVehicleShouldNotBeFound("rfid.in=" + UPDATED_RFID);
    }

    @Test
    @Transactional
    void getAllVehiclesByRfidIsNullOrNotNull() throws Exception {
        // Initialize the database
        vehicleRepository.saveAndFlush(vehicle);

        // Get all the vehicleList where rfid is not null
        defaultVehicleShouldBeFound("rfid.specified=true");

        // Get all the vehicleList where rfid is null
        defaultVehicleShouldNotBeFound("rfid.specified=false");
    }

    @Test
    @Transactional
    void getAllVehiclesByRfidContainsSomething() throws Exception {
        // Initialize the database
        vehicleRepository.saveAndFlush(vehicle);

        // Get all the vehicleList where rfid contains DEFAULT_RFID
        defaultVehicleShouldBeFound("rfid.contains=" + DEFAULT_RFID);

        // Get all the vehicleList where rfid contains UPDATED_RFID
        defaultVehicleShouldNotBeFound("rfid.contains=" + UPDATED_RFID);
    }

    @Test
    @Transactional
    void getAllVehiclesByRfidNotContainsSomething() throws Exception {
        // Initialize the database
        vehicleRepository.saveAndFlush(vehicle);

        // Get all the vehicleList where rfid does not contain DEFAULT_RFID
        defaultVehicleShouldNotBeFound("rfid.doesNotContain=" + DEFAULT_RFID);

        // Get all the vehicleList where rfid does not contain UPDATED_RFID
        defaultVehicleShouldBeFound("rfid.doesNotContain=" + UPDATED_RFID);
    }

    @Test
    @Transactional
    void getAllVehiclesByDriverIsEqualToSomething() throws Exception {
        // Initialize the database
        vehicleRepository.saveAndFlush(vehicle);

        // Get all the vehicleList where driver equals to DEFAULT_DRIVER
        defaultVehicleShouldBeFound("driver.equals=" + DEFAULT_DRIVER);

        // Get all the vehicleList where driver equals to UPDATED_DRIVER
        defaultVehicleShouldNotBeFound("driver.equals=" + UPDATED_DRIVER);
    }

    @Test
    @Transactional
    void getAllVehiclesByDriverIsInShouldWork() throws Exception {
        // Initialize the database
        vehicleRepository.saveAndFlush(vehicle);

        // Get all the vehicleList where driver in DEFAULT_DRIVER or UPDATED_DRIVER
        defaultVehicleShouldBeFound("driver.in=" + DEFAULT_DRIVER + "," + UPDATED_DRIVER);

        // Get all the vehicleList where driver equals to UPDATED_DRIVER
        defaultVehicleShouldNotBeFound("driver.in=" + UPDATED_DRIVER);
    }

    @Test
    @Transactional
    void getAllVehiclesByDriverIsNullOrNotNull() throws Exception {
        // Initialize the database
        vehicleRepository.saveAndFlush(vehicle);

        // Get all the vehicleList where driver is not null
        defaultVehicleShouldBeFound("driver.specified=true");

        // Get all the vehicleList where driver is null
        defaultVehicleShouldNotBeFound("driver.specified=false");
    }

    @Test
    @Transactional
    void getAllVehiclesByDriverIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        vehicleRepository.saveAndFlush(vehicle);

        // Get all the vehicleList where driver is greater than or equal to DEFAULT_DRIVER
        defaultVehicleShouldBeFound("driver.greaterThanOrEqual=" + DEFAULT_DRIVER);

        // Get all the vehicleList where driver is greater than or equal to UPDATED_DRIVER
        defaultVehicleShouldNotBeFound("driver.greaterThanOrEqual=" + UPDATED_DRIVER);
    }

    @Test
    @Transactional
    void getAllVehiclesByDriverIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        vehicleRepository.saveAndFlush(vehicle);

        // Get all the vehicleList where driver is less than or equal to DEFAULT_DRIVER
        defaultVehicleShouldBeFound("driver.lessThanOrEqual=" + DEFAULT_DRIVER);

        // Get all the vehicleList where driver is less than or equal to SMALLER_DRIVER
        defaultVehicleShouldNotBeFound("driver.lessThanOrEqual=" + SMALLER_DRIVER);
    }

    @Test
    @Transactional
    void getAllVehiclesByDriverIsLessThanSomething() throws Exception {
        // Initialize the database
        vehicleRepository.saveAndFlush(vehicle);

        // Get all the vehicleList where driver is less than DEFAULT_DRIVER
        defaultVehicleShouldNotBeFound("driver.lessThan=" + DEFAULT_DRIVER);

        // Get all the vehicleList where driver is less than UPDATED_DRIVER
        defaultVehicleShouldBeFound("driver.lessThan=" + UPDATED_DRIVER);
    }

    @Test
    @Transactional
    void getAllVehiclesByDriverIsGreaterThanSomething() throws Exception {
        // Initialize the database
        vehicleRepository.saveAndFlush(vehicle);

        // Get all the vehicleList where driver is greater than DEFAULT_DRIVER
        defaultVehicleShouldNotBeFound("driver.greaterThan=" + DEFAULT_DRIVER);

        // Get all the vehicleList where driver is greater than SMALLER_DRIVER
        defaultVehicleShouldBeFound("driver.greaterThan=" + SMALLER_DRIVER);
    }

    @Test
    @Transactional
    void getAllVehiclesByProducerIsEqualToSomething() throws Exception {
        // Initialize the database
        vehicleRepository.saveAndFlush(vehicle);

        // Get all the vehicleList where producer equals to DEFAULT_PRODUCER
        defaultVehicleShouldBeFound("producer.equals=" + DEFAULT_PRODUCER);

        // Get all the vehicleList where producer equals to UPDATED_PRODUCER
        defaultVehicleShouldNotBeFound("producer.equals=" + UPDATED_PRODUCER);
    }

    @Test
    @Transactional
    void getAllVehiclesByProducerIsInShouldWork() throws Exception {
        // Initialize the database
        vehicleRepository.saveAndFlush(vehicle);

        // Get all the vehicleList where producer in DEFAULT_PRODUCER or UPDATED_PRODUCER
        defaultVehicleShouldBeFound("producer.in=" + DEFAULT_PRODUCER + "," + UPDATED_PRODUCER);

        // Get all the vehicleList where producer equals to UPDATED_PRODUCER
        defaultVehicleShouldNotBeFound("producer.in=" + UPDATED_PRODUCER);
    }

    @Test
    @Transactional
    void getAllVehiclesByProducerIsNullOrNotNull() throws Exception {
        // Initialize the database
        vehicleRepository.saveAndFlush(vehicle);

        // Get all the vehicleList where producer is not null
        defaultVehicleShouldBeFound("producer.specified=true");

        // Get all the vehicleList where producer is null
        defaultVehicleShouldNotBeFound("producer.specified=false");
    }

    @Test
    @Transactional
    void getAllVehiclesByProducerContainsSomething() throws Exception {
        // Initialize the database
        vehicleRepository.saveAndFlush(vehicle);

        // Get all the vehicleList where producer contains DEFAULT_PRODUCER
        defaultVehicleShouldBeFound("producer.contains=" + DEFAULT_PRODUCER);

        // Get all the vehicleList where producer contains UPDATED_PRODUCER
        defaultVehicleShouldNotBeFound("producer.contains=" + UPDATED_PRODUCER);
    }

    @Test
    @Transactional
    void getAllVehiclesByProducerNotContainsSomething() throws Exception {
        // Initialize the database
        vehicleRepository.saveAndFlush(vehicle);

        // Get all the vehicleList where producer does not contain DEFAULT_PRODUCER
        defaultVehicleShouldNotBeFound("producer.doesNotContain=" + DEFAULT_PRODUCER);

        // Get all the vehicleList where producer does not contain UPDATED_PRODUCER
        defaultVehicleShouldBeFound("producer.doesNotContain=" + UPDATED_PRODUCER);
    }

    @Test
    @Transactional
    void getAllVehiclesByModelIsEqualToSomething() throws Exception {
        // Initialize the database
        vehicleRepository.saveAndFlush(vehicle);

        // Get all the vehicleList where model equals to DEFAULT_MODEL
        defaultVehicleShouldBeFound("model.equals=" + DEFAULT_MODEL);

        // Get all the vehicleList where model equals to UPDATED_MODEL
        defaultVehicleShouldNotBeFound("model.equals=" + UPDATED_MODEL);
    }

    @Test
    @Transactional
    void getAllVehiclesByModelIsInShouldWork() throws Exception {
        // Initialize the database
        vehicleRepository.saveAndFlush(vehicle);

        // Get all the vehicleList where model in DEFAULT_MODEL or UPDATED_MODEL
        defaultVehicleShouldBeFound("model.in=" + DEFAULT_MODEL + "," + UPDATED_MODEL);

        // Get all the vehicleList where model equals to UPDATED_MODEL
        defaultVehicleShouldNotBeFound("model.in=" + UPDATED_MODEL);
    }

    @Test
    @Transactional
    void getAllVehiclesByModelIsNullOrNotNull() throws Exception {
        // Initialize the database
        vehicleRepository.saveAndFlush(vehicle);

        // Get all the vehicleList where model is not null
        defaultVehicleShouldBeFound("model.specified=true");

        // Get all the vehicleList where model is null
        defaultVehicleShouldNotBeFound("model.specified=false");
    }

    @Test
    @Transactional
    void getAllVehiclesByModelContainsSomething() throws Exception {
        // Initialize the database
        vehicleRepository.saveAndFlush(vehicle);

        // Get all the vehicleList where model contains DEFAULT_MODEL
        defaultVehicleShouldBeFound("model.contains=" + DEFAULT_MODEL);

        // Get all the vehicleList where model contains UPDATED_MODEL
        defaultVehicleShouldNotBeFound("model.contains=" + UPDATED_MODEL);
    }

    @Test
    @Transactional
    void getAllVehiclesByModelNotContainsSomething() throws Exception {
        // Initialize the database
        vehicleRepository.saveAndFlush(vehicle);

        // Get all the vehicleList where model does not contain DEFAULT_MODEL
        defaultVehicleShouldNotBeFound("model.doesNotContain=" + DEFAULT_MODEL);

        // Get all the vehicleList where model does not contain UPDATED_MODEL
        defaultVehicleShouldBeFound("model.doesNotContain=" + UPDATED_MODEL);
    }

    @Test
    @Transactional
    void getAllVehiclesByYearIsEqualToSomething() throws Exception {
        // Initialize the database
        vehicleRepository.saveAndFlush(vehicle);

        // Get all the vehicleList where year equals to DEFAULT_YEAR
        defaultVehicleShouldBeFound("year.equals=" + DEFAULT_YEAR);

        // Get all the vehicleList where year equals to UPDATED_YEAR
        defaultVehicleShouldNotBeFound("year.equals=" + UPDATED_YEAR);
    }

    @Test
    @Transactional
    void getAllVehiclesByYearIsInShouldWork() throws Exception {
        // Initialize the database
        vehicleRepository.saveAndFlush(vehicle);

        // Get all the vehicleList where year in DEFAULT_YEAR or UPDATED_YEAR
        defaultVehicleShouldBeFound("year.in=" + DEFAULT_YEAR + "," + UPDATED_YEAR);

        // Get all the vehicleList where year equals to UPDATED_YEAR
        defaultVehicleShouldNotBeFound("year.in=" + UPDATED_YEAR);
    }

    @Test
    @Transactional
    void getAllVehiclesByYearIsNullOrNotNull() throws Exception {
        // Initialize the database
        vehicleRepository.saveAndFlush(vehicle);

        // Get all the vehicleList where year is not null
        defaultVehicleShouldBeFound("year.specified=true");

        // Get all the vehicleList where year is null
        defaultVehicleShouldNotBeFound("year.specified=false");
    }

    @Test
    @Transactional
    void getAllVehiclesByYearIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        vehicleRepository.saveAndFlush(vehicle);

        // Get all the vehicleList where year is greater than or equal to DEFAULT_YEAR
        defaultVehicleShouldBeFound("year.greaterThanOrEqual=" + DEFAULT_YEAR);

        // Get all the vehicleList where year is greater than or equal to UPDATED_YEAR
        defaultVehicleShouldNotBeFound("year.greaterThanOrEqual=" + UPDATED_YEAR);
    }

    @Test
    @Transactional
    void getAllVehiclesByYearIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        vehicleRepository.saveAndFlush(vehicle);

        // Get all the vehicleList where year is less than or equal to DEFAULT_YEAR
        defaultVehicleShouldBeFound("year.lessThanOrEqual=" + DEFAULT_YEAR);

        // Get all the vehicleList where year is less than or equal to SMALLER_YEAR
        defaultVehicleShouldNotBeFound("year.lessThanOrEqual=" + SMALLER_YEAR);
    }

    @Test
    @Transactional
    void getAllVehiclesByYearIsLessThanSomething() throws Exception {
        // Initialize the database
        vehicleRepository.saveAndFlush(vehicle);

        // Get all the vehicleList where year is less than DEFAULT_YEAR
        defaultVehicleShouldNotBeFound("year.lessThan=" + DEFAULT_YEAR);

        // Get all the vehicleList where year is less than UPDATED_YEAR
        defaultVehicleShouldBeFound("year.lessThan=" + UPDATED_YEAR);
    }

    @Test
    @Transactional
    void getAllVehiclesByYearIsGreaterThanSomething() throws Exception {
        // Initialize the database
        vehicleRepository.saveAndFlush(vehicle);

        // Get all the vehicleList where year is greater than DEFAULT_YEAR
        defaultVehicleShouldNotBeFound("year.greaterThan=" + DEFAULT_YEAR);

        // Get all the vehicleList where year is greater than SMALLER_YEAR
        defaultVehicleShouldBeFound("year.greaterThan=" + SMALLER_YEAR);
    }

    @Test
    @Transactional
    void getAllVehiclesByColorIsEqualToSomething() throws Exception {
        // Initialize the database
        vehicleRepository.saveAndFlush(vehicle);

        // Get all the vehicleList where color equals to DEFAULT_COLOR
        defaultVehicleShouldBeFound("color.equals=" + DEFAULT_COLOR);

        // Get all the vehicleList where color equals to UPDATED_COLOR
        defaultVehicleShouldNotBeFound("color.equals=" + UPDATED_COLOR);
    }

    @Test
    @Transactional
    void getAllVehiclesByColorIsInShouldWork() throws Exception {
        // Initialize the database
        vehicleRepository.saveAndFlush(vehicle);

        // Get all the vehicleList where color in DEFAULT_COLOR or UPDATED_COLOR
        defaultVehicleShouldBeFound("color.in=" + DEFAULT_COLOR + "," + UPDATED_COLOR);

        // Get all the vehicleList where color equals to UPDATED_COLOR
        defaultVehicleShouldNotBeFound("color.in=" + UPDATED_COLOR);
    }

    @Test
    @Transactional
    void getAllVehiclesByColorIsNullOrNotNull() throws Exception {
        // Initialize the database
        vehicleRepository.saveAndFlush(vehicle);

        // Get all the vehicleList where color is not null
        defaultVehicleShouldBeFound("color.specified=true");

        // Get all the vehicleList where color is null
        defaultVehicleShouldNotBeFound("color.specified=false");
    }

    @Test
    @Transactional
    void getAllVehiclesByColorContainsSomething() throws Exception {
        // Initialize the database
        vehicleRepository.saveAndFlush(vehicle);

        // Get all the vehicleList where color contains DEFAULT_COLOR
        defaultVehicleShouldBeFound("color.contains=" + DEFAULT_COLOR);

        // Get all the vehicleList where color contains UPDATED_COLOR
        defaultVehicleShouldNotBeFound("color.contains=" + UPDATED_COLOR);
    }

    @Test
    @Transactional
    void getAllVehiclesByColorNotContainsSomething() throws Exception {
        // Initialize the database
        vehicleRepository.saveAndFlush(vehicle);

        // Get all the vehicleList where color does not contain DEFAULT_COLOR
        defaultVehicleShouldNotBeFound("color.doesNotContain=" + DEFAULT_COLOR);

        // Get all the vehicleList where color does not contain UPDATED_COLOR
        defaultVehicleShouldBeFound("color.doesNotContain=" + UPDATED_COLOR);
    }

    @Test
    @Transactional
    void getAllVehiclesByDecalsIsEqualToSomething() throws Exception {
        // Initialize the database
        vehicleRepository.saveAndFlush(vehicle);

        // Get all the vehicleList where decals equals to DEFAULT_DECALS
        defaultVehicleShouldBeFound("decals.equals=" + DEFAULT_DECALS);

        // Get all the vehicleList where decals equals to UPDATED_DECALS
        defaultVehicleShouldNotBeFound("decals.equals=" + UPDATED_DECALS);
    }

    @Test
    @Transactional
    void getAllVehiclesByDecalsIsInShouldWork() throws Exception {
        // Initialize the database
        vehicleRepository.saveAndFlush(vehicle);

        // Get all the vehicleList where decals in DEFAULT_DECALS or UPDATED_DECALS
        defaultVehicleShouldBeFound("decals.in=" + DEFAULT_DECALS + "," + UPDATED_DECALS);

        // Get all the vehicleList where decals equals to UPDATED_DECALS
        defaultVehicleShouldNotBeFound("decals.in=" + UPDATED_DECALS);
    }

    @Test
    @Transactional
    void getAllVehiclesByDecalsIsNullOrNotNull() throws Exception {
        // Initialize the database
        vehicleRepository.saveAndFlush(vehicle);

        // Get all the vehicleList where decals is not null
        defaultVehicleShouldBeFound("decals.specified=true");

        // Get all the vehicleList where decals is null
        defaultVehicleShouldNotBeFound("decals.specified=false");
    }

    @Test
    @Transactional
    void getAllVehiclesByDecalsContainsSomething() throws Exception {
        // Initialize the database
        vehicleRepository.saveAndFlush(vehicle);

        // Get all the vehicleList where decals contains DEFAULT_DECALS
        defaultVehicleShouldBeFound("decals.contains=" + DEFAULT_DECALS);

        // Get all the vehicleList where decals contains UPDATED_DECALS
        defaultVehicleShouldNotBeFound("decals.contains=" + UPDATED_DECALS);
    }

    @Test
    @Transactional
    void getAllVehiclesByDecalsNotContainsSomething() throws Exception {
        // Initialize the database
        vehicleRepository.saveAndFlush(vehicle);

        // Get all the vehicleList where decals does not contain DEFAULT_DECALS
        defaultVehicleShouldNotBeFound("decals.doesNotContain=" + DEFAULT_DECALS);

        // Get all the vehicleList where decals does not contain UPDATED_DECALS
        defaultVehicleShouldBeFound("decals.doesNotContain=" + UPDATED_DECALS);
    }

    @Test
    @Transactional
    void getAllVehiclesBySpoilersIsEqualToSomething() throws Exception {
        // Initialize the database
        vehicleRepository.saveAndFlush(vehicle);

        // Get all the vehicleList where spoilers equals to DEFAULT_SPOILERS
        defaultVehicleShouldBeFound("spoilers.equals=" + DEFAULT_SPOILERS);

        // Get all the vehicleList where spoilers equals to UPDATED_SPOILERS
        defaultVehicleShouldNotBeFound("spoilers.equals=" + UPDATED_SPOILERS);
    }

    @Test
    @Transactional
    void getAllVehiclesBySpoilersIsInShouldWork() throws Exception {
        // Initialize the database
        vehicleRepository.saveAndFlush(vehicle);

        // Get all the vehicleList where spoilers in DEFAULT_SPOILERS or UPDATED_SPOILERS
        defaultVehicleShouldBeFound("spoilers.in=" + DEFAULT_SPOILERS + "," + UPDATED_SPOILERS);

        // Get all the vehicleList where spoilers equals to UPDATED_SPOILERS
        defaultVehicleShouldNotBeFound("spoilers.in=" + UPDATED_SPOILERS);
    }

    @Test
    @Transactional
    void getAllVehiclesBySpoilersIsNullOrNotNull() throws Exception {
        // Initialize the database
        vehicleRepository.saveAndFlush(vehicle);

        // Get all the vehicleList where spoilers is not null
        defaultVehicleShouldBeFound("spoilers.specified=true");

        // Get all the vehicleList where spoilers is null
        defaultVehicleShouldNotBeFound("spoilers.specified=false");
    }

    @Test
    @Transactional
    void getAllVehiclesBySpoilersContainsSomething() throws Exception {
        // Initialize the database
        vehicleRepository.saveAndFlush(vehicle);

        // Get all the vehicleList where spoilers contains DEFAULT_SPOILERS
        defaultVehicleShouldBeFound("spoilers.contains=" + DEFAULT_SPOILERS);

        // Get all the vehicleList where spoilers contains UPDATED_SPOILERS
        defaultVehicleShouldNotBeFound("spoilers.contains=" + UPDATED_SPOILERS);
    }

    @Test
    @Transactional
    void getAllVehiclesBySpoilersNotContainsSomething() throws Exception {
        // Initialize the database
        vehicleRepository.saveAndFlush(vehicle);

        // Get all the vehicleList where spoilers does not contain DEFAULT_SPOILERS
        defaultVehicleShouldNotBeFound("spoilers.doesNotContain=" + DEFAULT_SPOILERS);

        // Get all the vehicleList where spoilers does not contain UPDATED_SPOILERS
        defaultVehicleShouldBeFound("spoilers.doesNotContain=" + UPDATED_SPOILERS);
    }

    @Test
    @Transactional
    void getAllVehiclesByGlassIsEqualToSomething() throws Exception {
        // Initialize the database
        vehicleRepository.saveAndFlush(vehicle);

        // Get all the vehicleList where glass equals to DEFAULT_GLASS
        defaultVehicleShouldBeFound("glass.equals=" + DEFAULT_GLASS);

        // Get all the vehicleList where glass equals to UPDATED_GLASS
        defaultVehicleShouldNotBeFound("glass.equals=" + UPDATED_GLASS);
    }

    @Test
    @Transactional
    void getAllVehiclesByGlassIsInShouldWork() throws Exception {
        // Initialize the database
        vehicleRepository.saveAndFlush(vehicle);

        // Get all the vehicleList where glass in DEFAULT_GLASS or UPDATED_GLASS
        defaultVehicleShouldBeFound("glass.in=" + DEFAULT_GLASS + "," + UPDATED_GLASS);

        // Get all the vehicleList where glass equals to UPDATED_GLASS
        defaultVehicleShouldNotBeFound("glass.in=" + UPDATED_GLASS);
    }

    @Test
    @Transactional
    void getAllVehiclesByGlassIsNullOrNotNull() throws Exception {
        // Initialize the database
        vehicleRepository.saveAndFlush(vehicle);

        // Get all the vehicleList where glass is not null
        defaultVehicleShouldBeFound("glass.specified=true");

        // Get all the vehicleList where glass is null
        defaultVehicleShouldNotBeFound("glass.specified=false");
    }

    @Test
    @Transactional
    void getAllVehiclesByGlassContainsSomething() throws Exception {
        // Initialize the database
        vehicleRepository.saveAndFlush(vehicle);

        // Get all the vehicleList where glass contains DEFAULT_GLASS
        defaultVehicleShouldBeFound("glass.contains=" + DEFAULT_GLASS);

        // Get all the vehicleList where glass contains UPDATED_GLASS
        defaultVehicleShouldNotBeFound("glass.contains=" + UPDATED_GLASS);
    }

    @Test
    @Transactional
    void getAllVehiclesByGlassNotContainsSomething() throws Exception {
        // Initialize the database
        vehicleRepository.saveAndFlush(vehicle);

        // Get all the vehicleList where glass does not contain DEFAULT_GLASS
        defaultVehicleShouldNotBeFound("glass.doesNotContain=" + DEFAULT_GLASS);

        // Get all the vehicleList where glass does not contain UPDATED_GLASS
        defaultVehicleShouldBeFound("glass.doesNotContain=" + UPDATED_GLASS);
    }

    @Test
    @Transactional
    void getAllVehiclesByChassisSerialIsEqualToSomething() throws Exception {
        // Initialize the database
        vehicleRepository.saveAndFlush(vehicle);

        // Get all the vehicleList where chassisSerial equals to DEFAULT_CHASSIS_SERIAL
        defaultVehicleShouldBeFound("chassisSerial.equals=" + DEFAULT_CHASSIS_SERIAL);

        // Get all the vehicleList where chassisSerial equals to UPDATED_CHASSIS_SERIAL
        defaultVehicleShouldNotBeFound("chassisSerial.equals=" + UPDATED_CHASSIS_SERIAL);
    }

    @Test
    @Transactional
    void getAllVehiclesByChassisSerialIsInShouldWork() throws Exception {
        // Initialize the database
        vehicleRepository.saveAndFlush(vehicle);

        // Get all the vehicleList where chassisSerial in DEFAULT_CHASSIS_SERIAL or UPDATED_CHASSIS_SERIAL
        defaultVehicleShouldBeFound("chassisSerial.in=" + DEFAULT_CHASSIS_SERIAL + "," + UPDATED_CHASSIS_SERIAL);

        // Get all the vehicleList where chassisSerial equals to UPDATED_CHASSIS_SERIAL
        defaultVehicleShouldNotBeFound("chassisSerial.in=" + UPDATED_CHASSIS_SERIAL);
    }

    @Test
    @Transactional
    void getAllVehiclesByChassisSerialIsNullOrNotNull() throws Exception {
        // Initialize the database
        vehicleRepository.saveAndFlush(vehicle);

        // Get all the vehicleList where chassisSerial is not null
        defaultVehicleShouldBeFound("chassisSerial.specified=true");

        // Get all the vehicleList where chassisSerial is null
        defaultVehicleShouldNotBeFound("chassisSerial.specified=false");
    }

    @Test
    @Transactional
    void getAllVehiclesByChassisSerialContainsSomething() throws Exception {
        // Initialize the database
        vehicleRepository.saveAndFlush(vehicle);

        // Get all the vehicleList where chassisSerial contains DEFAULT_CHASSIS_SERIAL
        defaultVehicleShouldBeFound("chassisSerial.contains=" + DEFAULT_CHASSIS_SERIAL);

        // Get all the vehicleList where chassisSerial contains UPDATED_CHASSIS_SERIAL
        defaultVehicleShouldNotBeFound("chassisSerial.contains=" + UPDATED_CHASSIS_SERIAL);
    }

    @Test
    @Transactional
    void getAllVehiclesByChassisSerialNotContainsSomething() throws Exception {
        // Initialize the database
        vehicleRepository.saveAndFlush(vehicle);

        // Get all the vehicleList where chassisSerial does not contain DEFAULT_CHASSIS_SERIAL
        defaultVehicleShouldNotBeFound("chassisSerial.doesNotContain=" + DEFAULT_CHASSIS_SERIAL);

        // Get all the vehicleList where chassisSerial does not contain UPDATED_CHASSIS_SERIAL
        defaultVehicleShouldBeFound("chassisSerial.doesNotContain=" + UPDATED_CHASSIS_SERIAL);
    }

    @Test
    @Transactional
    void getAllVehiclesByMotorSerialIsEqualToSomething() throws Exception {
        // Initialize the database
        vehicleRepository.saveAndFlush(vehicle);

        // Get all the vehicleList where motorSerial equals to DEFAULT_MOTOR_SERIAL
        defaultVehicleShouldBeFound("motorSerial.equals=" + DEFAULT_MOTOR_SERIAL);

        // Get all the vehicleList where motorSerial equals to UPDATED_MOTOR_SERIAL
        defaultVehicleShouldNotBeFound("motorSerial.equals=" + UPDATED_MOTOR_SERIAL);
    }

    @Test
    @Transactional
    void getAllVehiclesByMotorSerialIsInShouldWork() throws Exception {
        // Initialize the database
        vehicleRepository.saveAndFlush(vehicle);

        // Get all the vehicleList where motorSerial in DEFAULT_MOTOR_SERIAL or UPDATED_MOTOR_SERIAL
        defaultVehicleShouldBeFound("motorSerial.in=" + DEFAULT_MOTOR_SERIAL + "," + UPDATED_MOTOR_SERIAL);

        // Get all the vehicleList where motorSerial equals to UPDATED_MOTOR_SERIAL
        defaultVehicleShouldNotBeFound("motorSerial.in=" + UPDATED_MOTOR_SERIAL);
    }

    @Test
    @Transactional
    void getAllVehiclesByMotorSerialIsNullOrNotNull() throws Exception {
        // Initialize the database
        vehicleRepository.saveAndFlush(vehicle);

        // Get all the vehicleList where motorSerial is not null
        defaultVehicleShouldBeFound("motorSerial.specified=true");

        // Get all the vehicleList where motorSerial is null
        defaultVehicleShouldNotBeFound("motorSerial.specified=false");
    }

    @Test
    @Transactional
    void getAllVehiclesByMotorSerialContainsSomething() throws Exception {
        // Initialize the database
        vehicleRepository.saveAndFlush(vehicle);

        // Get all the vehicleList where motorSerial contains DEFAULT_MOTOR_SERIAL
        defaultVehicleShouldBeFound("motorSerial.contains=" + DEFAULT_MOTOR_SERIAL);

        // Get all the vehicleList where motorSerial contains UPDATED_MOTOR_SERIAL
        defaultVehicleShouldNotBeFound("motorSerial.contains=" + UPDATED_MOTOR_SERIAL);
    }

    @Test
    @Transactional
    void getAllVehiclesByMotorSerialNotContainsSomething() throws Exception {
        // Initialize the database
        vehicleRepository.saveAndFlush(vehicle);

        // Get all the vehicleList where motorSerial does not contain DEFAULT_MOTOR_SERIAL
        defaultVehicleShouldNotBeFound("motorSerial.doesNotContain=" + DEFAULT_MOTOR_SERIAL);

        // Get all the vehicleList where motorSerial does not contain UPDATED_MOTOR_SERIAL
        defaultVehicleShouldBeFound("motorSerial.doesNotContain=" + UPDATED_MOTOR_SERIAL);
    }

    @Test
    @Transactional
    void getAllVehiclesByVehicleLicenseSerialIsEqualToSomething() throws Exception {
        // Initialize the database
        vehicleRepository.saveAndFlush(vehicle);

        // Get all the vehicleList where vehicleLicenseSerial equals to DEFAULT_VEHICLE_LICENSE_SERIAL
        defaultVehicleShouldBeFound("vehicleLicenseSerial.equals=" + DEFAULT_VEHICLE_LICENSE_SERIAL);

        // Get all the vehicleList where vehicleLicenseSerial equals to UPDATED_VEHICLE_LICENSE_SERIAL
        defaultVehicleShouldNotBeFound("vehicleLicenseSerial.equals=" + UPDATED_VEHICLE_LICENSE_SERIAL);
    }

    @Test
    @Transactional
    void getAllVehiclesByVehicleLicenseSerialIsInShouldWork() throws Exception {
        // Initialize the database
        vehicleRepository.saveAndFlush(vehicle);

        // Get all the vehicleList where vehicleLicenseSerial in DEFAULT_VEHICLE_LICENSE_SERIAL or UPDATED_VEHICLE_LICENSE_SERIAL
        defaultVehicleShouldBeFound("vehicleLicenseSerial.in=" + DEFAULT_VEHICLE_LICENSE_SERIAL + "," + UPDATED_VEHICLE_LICENSE_SERIAL);

        // Get all the vehicleList where vehicleLicenseSerial equals to UPDATED_VEHICLE_LICENSE_SERIAL
        defaultVehicleShouldNotBeFound("vehicleLicenseSerial.in=" + UPDATED_VEHICLE_LICENSE_SERIAL);
    }

    @Test
    @Transactional
    void getAllVehiclesByVehicleLicenseSerialIsNullOrNotNull() throws Exception {
        // Initialize the database
        vehicleRepository.saveAndFlush(vehicle);

        // Get all the vehicleList where vehicleLicenseSerial is not null
        defaultVehicleShouldBeFound("vehicleLicenseSerial.specified=true");

        // Get all the vehicleList where vehicleLicenseSerial is null
        defaultVehicleShouldNotBeFound("vehicleLicenseSerial.specified=false");
    }

    @Test
    @Transactional
    void getAllVehiclesByVehicleLicenseSerialContainsSomething() throws Exception {
        // Initialize the database
        vehicleRepository.saveAndFlush(vehicle);

        // Get all the vehicleList where vehicleLicenseSerial contains DEFAULT_VEHICLE_LICENSE_SERIAL
        defaultVehicleShouldBeFound("vehicleLicenseSerial.contains=" + DEFAULT_VEHICLE_LICENSE_SERIAL);

        // Get all the vehicleList where vehicleLicenseSerial contains UPDATED_VEHICLE_LICENSE_SERIAL
        defaultVehicleShouldNotBeFound("vehicleLicenseSerial.contains=" + UPDATED_VEHICLE_LICENSE_SERIAL);
    }

    @Test
    @Transactional
    void getAllVehiclesByVehicleLicenseSerialNotContainsSomething() throws Exception {
        // Initialize the database
        vehicleRepository.saveAndFlush(vehicle);

        // Get all the vehicleList where vehicleLicenseSerial does not contain DEFAULT_VEHICLE_LICENSE_SERIAL
        defaultVehicleShouldNotBeFound("vehicleLicenseSerial.doesNotContain=" + DEFAULT_VEHICLE_LICENSE_SERIAL);

        // Get all the vehicleList where vehicleLicenseSerial does not contain UPDATED_VEHICLE_LICENSE_SERIAL
        defaultVehicleShouldBeFound("vehicleLicenseSerial.doesNotContain=" + UPDATED_VEHICLE_LICENSE_SERIAL);
    }

    @Test
    @Transactional
    void getAllVehiclesByVehicleLicenseTypeIsEqualToSomething() throws Exception {
        // Initialize the database
        vehicleRepository.saveAndFlush(vehicle);

        // Get all the vehicleList where vehicleLicenseType equals to DEFAULT_VEHICLE_LICENSE_TYPE
        defaultVehicleShouldBeFound("vehicleLicenseType.equals=" + DEFAULT_VEHICLE_LICENSE_TYPE);

        // Get all the vehicleList where vehicleLicenseType equals to UPDATED_VEHICLE_LICENSE_TYPE
        defaultVehicleShouldNotBeFound("vehicleLicenseType.equals=" + UPDATED_VEHICLE_LICENSE_TYPE);
    }

    @Test
    @Transactional
    void getAllVehiclesByVehicleLicenseTypeIsInShouldWork() throws Exception {
        // Initialize the database
        vehicleRepository.saveAndFlush(vehicle);

        // Get all the vehicleList where vehicleLicenseType in DEFAULT_VEHICLE_LICENSE_TYPE or UPDATED_VEHICLE_LICENSE_TYPE
        defaultVehicleShouldBeFound("vehicleLicenseType.in=" + DEFAULT_VEHICLE_LICENSE_TYPE + "," + UPDATED_VEHICLE_LICENSE_TYPE);

        // Get all the vehicleList where vehicleLicenseType equals to UPDATED_VEHICLE_LICENSE_TYPE
        defaultVehicleShouldNotBeFound("vehicleLicenseType.in=" + UPDATED_VEHICLE_LICENSE_TYPE);
    }

    @Test
    @Transactional
    void getAllVehiclesByVehicleLicenseTypeIsNullOrNotNull() throws Exception {
        // Initialize the database
        vehicleRepository.saveAndFlush(vehicle);

        // Get all the vehicleList where vehicleLicenseType is not null
        defaultVehicleShouldBeFound("vehicleLicenseType.specified=true");

        // Get all the vehicleList where vehicleLicenseType is null
        defaultVehicleShouldNotBeFound("vehicleLicenseType.specified=false");
    }

    @Test
    @Transactional
    void getAllVehiclesByVehicleLicenseTypeIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        vehicleRepository.saveAndFlush(vehicle);

        // Get all the vehicleList where vehicleLicenseType is greater than or equal to DEFAULT_VEHICLE_LICENSE_TYPE
        defaultVehicleShouldBeFound("vehicleLicenseType.greaterThanOrEqual=" + DEFAULT_VEHICLE_LICENSE_TYPE);

        // Get all the vehicleList where vehicleLicenseType is greater than or equal to UPDATED_VEHICLE_LICENSE_TYPE
        defaultVehicleShouldNotBeFound("vehicleLicenseType.greaterThanOrEqual=" + UPDATED_VEHICLE_LICENSE_TYPE);
    }

    @Test
    @Transactional
    void getAllVehiclesByVehicleLicenseTypeIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        vehicleRepository.saveAndFlush(vehicle);

        // Get all the vehicleList where vehicleLicenseType is less than or equal to DEFAULT_VEHICLE_LICENSE_TYPE
        defaultVehicleShouldBeFound("vehicleLicenseType.lessThanOrEqual=" + DEFAULT_VEHICLE_LICENSE_TYPE);

        // Get all the vehicleList where vehicleLicenseType is less than or equal to SMALLER_VEHICLE_LICENSE_TYPE
        defaultVehicleShouldNotBeFound("vehicleLicenseType.lessThanOrEqual=" + SMALLER_VEHICLE_LICENSE_TYPE);
    }

    @Test
    @Transactional
    void getAllVehiclesByVehicleLicenseTypeIsLessThanSomething() throws Exception {
        // Initialize the database
        vehicleRepository.saveAndFlush(vehicle);

        // Get all the vehicleList where vehicleLicenseType is less than DEFAULT_VEHICLE_LICENSE_TYPE
        defaultVehicleShouldNotBeFound("vehicleLicenseType.lessThan=" + DEFAULT_VEHICLE_LICENSE_TYPE);

        // Get all the vehicleList where vehicleLicenseType is less than UPDATED_VEHICLE_LICENSE_TYPE
        defaultVehicleShouldBeFound("vehicleLicenseType.lessThan=" + UPDATED_VEHICLE_LICENSE_TYPE);
    }

    @Test
    @Transactional
    void getAllVehiclesByVehicleLicenseTypeIsGreaterThanSomething() throws Exception {
        // Initialize the database
        vehicleRepository.saveAndFlush(vehicle);

        // Get all the vehicleList where vehicleLicenseType is greater than DEFAULT_VEHICLE_LICENSE_TYPE
        defaultVehicleShouldNotBeFound("vehicleLicenseType.greaterThan=" + DEFAULT_VEHICLE_LICENSE_TYPE);

        // Get all the vehicleList where vehicleLicenseType is greater than SMALLER_VEHICLE_LICENSE_TYPE
        defaultVehicleShouldBeFound("vehicleLicenseType.greaterThan=" + SMALLER_VEHICLE_LICENSE_TYPE);
    }

    @Test
    @Transactional
    void getAllVehiclesByVehicleTypeIsEqualToSomething() throws Exception {
        // Initialize the database
        vehicleRepository.saveAndFlush(vehicle);

        // Get all the vehicleList where vehicleType equals to DEFAULT_VEHICLE_TYPE
        defaultVehicleShouldBeFound("vehicleType.equals=" + DEFAULT_VEHICLE_TYPE);

        // Get all the vehicleList where vehicleType equals to UPDATED_VEHICLE_TYPE
        defaultVehicleShouldNotBeFound("vehicleType.equals=" + UPDATED_VEHICLE_TYPE);
    }

    @Test
    @Transactional
    void getAllVehiclesByVehicleTypeIsInShouldWork() throws Exception {
        // Initialize the database
        vehicleRepository.saveAndFlush(vehicle);

        // Get all the vehicleList where vehicleType in DEFAULT_VEHICLE_TYPE or UPDATED_VEHICLE_TYPE
        defaultVehicleShouldBeFound("vehicleType.in=" + DEFAULT_VEHICLE_TYPE + "," + UPDATED_VEHICLE_TYPE);

        // Get all the vehicleList where vehicleType equals to UPDATED_VEHICLE_TYPE
        defaultVehicleShouldNotBeFound("vehicleType.in=" + UPDATED_VEHICLE_TYPE);
    }

    @Test
    @Transactional
    void getAllVehiclesByVehicleTypeIsNullOrNotNull() throws Exception {
        // Initialize the database
        vehicleRepository.saveAndFlush(vehicle);

        // Get all the vehicleList where vehicleType is not null
        defaultVehicleShouldBeFound("vehicleType.specified=true");

        // Get all the vehicleList where vehicleType is null
        defaultVehicleShouldNotBeFound("vehicleType.specified=false");
    }

    @Test
    @Transactional
    void getAllVehiclesByVehicleTypeIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        vehicleRepository.saveAndFlush(vehicle);

        // Get all the vehicleList where vehicleType is greater than or equal to DEFAULT_VEHICLE_TYPE
        defaultVehicleShouldBeFound("vehicleType.greaterThanOrEqual=" + DEFAULT_VEHICLE_TYPE);

        // Get all the vehicleList where vehicleType is greater than or equal to UPDATED_VEHICLE_TYPE
        defaultVehicleShouldNotBeFound("vehicleType.greaterThanOrEqual=" + UPDATED_VEHICLE_TYPE);
    }

    @Test
    @Transactional
    void getAllVehiclesByVehicleTypeIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        vehicleRepository.saveAndFlush(vehicle);

        // Get all the vehicleList where vehicleType is less than or equal to DEFAULT_VEHICLE_TYPE
        defaultVehicleShouldBeFound("vehicleType.lessThanOrEqual=" + DEFAULT_VEHICLE_TYPE);

        // Get all the vehicleList where vehicleType is less than or equal to SMALLER_VEHICLE_TYPE
        defaultVehicleShouldNotBeFound("vehicleType.lessThanOrEqual=" + SMALLER_VEHICLE_TYPE);
    }

    @Test
    @Transactional
    void getAllVehiclesByVehicleTypeIsLessThanSomething() throws Exception {
        // Initialize the database
        vehicleRepository.saveAndFlush(vehicle);

        // Get all the vehicleList where vehicleType is less than DEFAULT_VEHICLE_TYPE
        defaultVehicleShouldNotBeFound("vehicleType.lessThan=" + DEFAULT_VEHICLE_TYPE);

        // Get all the vehicleList where vehicleType is less than UPDATED_VEHICLE_TYPE
        defaultVehicleShouldBeFound("vehicleType.lessThan=" + UPDATED_VEHICLE_TYPE);
    }

    @Test
    @Transactional
    void getAllVehiclesByVehicleTypeIsGreaterThanSomething() throws Exception {
        // Initialize the database
        vehicleRepository.saveAndFlush(vehicle);

        // Get all the vehicleList where vehicleType is greater than DEFAULT_VEHICLE_TYPE
        defaultVehicleShouldNotBeFound("vehicleType.greaterThan=" + DEFAULT_VEHICLE_TYPE);

        // Get all the vehicleList where vehicleType is greater than SMALLER_VEHICLE_TYPE
        defaultVehicleShouldBeFound("vehicleType.greaterThan=" + SMALLER_VEHICLE_TYPE);
    }

    @Test
    @Transactional
    void getAllVehiclesByWantedIsEqualToSomething() throws Exception {
        // Initialize the database
        vehicleRepository.saveAndFlush(vehicle);

        // Get all the vehicleList where wanted equals to DEFAULT_WANTED
        defaultVehicleShouldBeFound("wanted.equals=" + DEFAULT_WANTED);

        // Get all the vehicleList where wanted equals to UPDATED_WANTED
        defaultVehicleShouldNotBeFound("wanted.equals=" + UPDATED_WANTED);
    }

    @Test
    @Transactional
    void getAllVehiclesByWantedIsInShouldWork() throws Exception {
        // Initialize the database
        vehicleRepository.saveAndFlush(vehicle);

        // Get all the vehicleList where wanted in DEFAULT_WANTED or UPDATED_WANTED
        defaultVehicleShouldBeFound("wanted.in=" + DEFAULT_WANTED + "," + UPDATED_WANTED);

        // Get all the vehicleList where wanted equals to UPDATED_WANTED
        defaultVehicleShouldNotBeFound("wanted.in=" + UPDATED_WANTED);
    }

    @Test
    @Transactional
    void getAllVehiclesByWantedIsNullOrNotNull() throws Exception {
        // Initialize the database
        vehicleRepository.saveAndFlush(vehicle);

        // Get all the vehicleList where wanted is not null
        defaultVehicleShouldBeFound("wanted.specified=true");

        // Get all the vehicleList where wanted is null
        defaultVehicleShouldNotBeFound("wanted.specified=false");
    }

    @Test
    @Transactional
    void getAllVehiclesByWantedIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        vehicleRepository.saveAndFlush(vehicle);

        // Get all the vehicleList where wanted is greater than or equal to DEFAULT_WANTED
        defaultVehicleShouldBeFound("wanted.greaterThanOrEqual=" + DEFAULT_WANTED);

        // Get all the vehicleList where wanted is greater than or equal to UPDATED_WANTED
        defaultVehicleShouldNotBeFound("wanted.greaterThanOrEqual=" + UPDATED_WANTED);
    }

    @Test
    @Transactional
    void getAllVehiclesByWantedIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        vehicleRepository.saveAndFlush(vehicle);

        // Get all the vehicleList where wanted is less than or equal to DEFAULT_WANTED
        defaultVehicleShouldBeFound("wanted.lessThanOrEqual=" + DEFAULT_WANTED);

        // Get all the vehicleList where wanted is less than or equal to SMALLER_WANTED
        defaultVehicleShouldNotBeFound("wanted.lessThanOrEqual=" + SMALLER_WANTED);
    }

    @Test
    @Transactional
    void getAllVehiclesByWantedIsLessThanSomething() throws Exception {
        // Initialize the database
        vehicleRepository.saveAndFlush(vehicle);

        // Get all the vehicleList where wanted is less than DEFAULT_WANTED
        defaultVehicleShouldNotBeFound("wanted.lessThan=" + DEFAULT_WANTED);

        // Get all the vehicleList where wanted is less than UPDATED_WANTED
        defaultVehicleShouldBeFound("wanted.lessThan=" + UPDATED_WANTED);
    }

    @Test
    @Transactional
    void getAllVehiclesByWantedIsGreaterThanSomething() throws Exception {
        // Initialize the database
        vehicleRepository.saveAndFlush(vehicle);

        // Get all the vehicleList where wanted is greater than DEFAULT_WANTED
        defaultVehicleShouldNotBeFound("wanted.greaterThan=" + DEFAULT_WANTED);

        // Get all the vehicleList where wanted is greater than SMALLER_WANTED
        defaultVehicleShouldBeFound("wanted.greaterThan=" + SMALLER_WANTED);
    }

    @Test
    @Transactional
    void getAllVehiclesByLicenseRevokedIsEqualToSomething() throws Exception {
        // Initialize the database
        vehicleRepository.saveAndFlush(vehicle);

        // Get all the vehicleList where licenseRevoked equals to DEFAULT_LICENSE_REVOKED
        defaultVehicleShouldBeFound("licenseRevoked.equals=" + DEFAULT_LICENSE_REVOKED);

        // Get all the vehicleList where licenseRevoked equals to UPDATED_LICENSE_REVOKED
        defaultVehicleShouldNotBeFound("licenseRevoked.equals=" + UPDATED_LICENSE_REVOKED);
    }

    @Test
    @Transactional
    void getAllVehiclesByLicenseRevokedIsInShouldWork() throws Exception {
        // Initialize the database
        vehicleRepository.saveAndFlush(vehicle);

        // Get all the vehicleList where licenseRevoked in DEFAULT_LICENSE_REVOKED or UPDATED_LICENSE_REVOKED
        defaultVehicleShouldBeFound("licenseRevoked.in=" + DEFAULT_LICENSE_REVOKED + "," + UPDATED_LICENSE_REVOKED);

        // Get all the vehicleList where licenseRevoked equals to UPDATED_LICENSE_REVOKED
        defaultVehicleShouldNotBeFound("licenseRevoked.in=" + UPDATED_LICENSE_REVOKED);
    }

    @Test
    @Transactional
    void getAllVehiclesByLicenseRevokedIsNullOrNotNull() throws Exception {
        // Initialize the database
        vehicleRepository.saveAndFlush(vehicle);

        // Get all the vehicleList where licenseRevoked is not null
        defaultVehicleShouldBeFound("licenseRevoked.specified=true");

        // Get all the vehicleList where licenseRevoked is null
        defaultVehicleShouldNotBeFound("licenseRevoked.specified=false");
    }

    @Test
    @Transactional
    void getAllVehiclesByLicenseRevokedIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        vehicleRepository.saveAndFlush(vehicle);

        // Get all the vehicleList where licenseRevoked is greater than or equal to DEFAULT_LICENSE_REVOKED
        defaultVehicleShouldBeFound("licenseRevoked.greaterThanOrEqual=" + DEFAULT_LICENSE_REVOKED);

        // Get all the vehicleList where licenseRevoked is greater than or equal to UPDATED_LICENSE_REVOKED
        defaultVehicleShouldNotBeFound("licenseRevoked.greaterThanOrEqual=" + UPDATED_LICENSE_REVOKED);
    }

    @Test
    @Transactional
    void getAllVehiclesByLicenseRevokedIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        vehicleRepository.saveAndFlush(vehicle);

        // Get all the vehicleList where licenseRevoked is less than or equal to DEFAULT_LICENSE_REVOKED
        defaultVehicleShouldBeFound("licenseRevoked.lessThanOrEqual=" + DEFAULT_LICENSE_REVOKED);

        // Get all the vehicleList where licenseRevoked is less than or equal to SMALLER_LICENSE_REVOKED
        defaultVehicleShouldNotBeFound("licenseRevoked.lessThanOrEqual=" + SMALLER_LICENSE_REVOKED);
    }

    @Test
    @Transactional
    void getAllVehiclesByLicenseRevokedIsLessThanSomething() throws Exception {
        // Initialize the database
        vehicleRepository.saveAndFlush(vehicle);

        // Get all the vehicleList where licenseRevoked is less than DEFAULT_LICENSE_REVOKED
        defaultVehicleShouldNotBeFound("licenseRevoked.lessThan=" + DEFAULT_LICENSE_REVOKED);

        // Get all the vehicleList where licenseRevoked is less than UPDATED_LICENSE_REVOKED
        defaultVehicleShouldBeFound("licenseRevoked.lessThan=" + UPDATED_LICENSE_REVOKED);
    }

    @Test
    @Transactional
    void getAllVehiclesByLicenseRevokedIsGreaterThanSomething() throws Exception {
        // Initialize the database
        vehicleRepository.saveAndFlush(vehicle);

        // Get all the vehicleList where licenseRevoked is greater than DEFAULT_LICENSE_REVOKED
        defaultVehicleShouldNotBeFound("licenseRevoked.greaterThan=" + DEFAULT_LICENSE_REVOKED);

        // Get all the vehicleList where licenseRevoked is greater than SMALLER_LICENSE_REVOKED
        defaultVehicleShouldBeFound("licenseRevoked.greaterThan=" + SMALLER_LICENSE_REVOKED);
    }

    @Test
    @Transactional
    void getAllVehiclesByLicenseExpiredIsEqualToSomething() throws Exception {
        // Initialize the database
        vehicleRepository.saveAndFlush(vehicle);

        // Get all the vehicleList where licenseExpired equals to DEFAULT_LICENSE_EXPIRED
        defaultVehicleShouldBeFound("licenseExpired.equals=" + DEFAULT_LICENSE_EXPIRED);

        // Get all the vehicleList where licenseExpired equals to UPDATED_LICENSE_EXPIRED
        defaultVehicleShouldNotBeFound("licenseExpired.equals=" + UPDATED_LICENSE_EXPIRED);
    }

    @Test
    @Transactional
    void getAllVehiclesByLicenseExpiredIsInShouldWork() throws Exception {
        // Initialize the database
        vehicleRepository.saveAndFlush(vehicle);

        // Get all the vehicleList where licenseExpired in DEFAULT_LICENSE_EXPIRED or UPDATED_LICENSE_EXPIRED
        defaultVehicleShouldBeFound("licenseExpired.in=" + DEFAULT_LICENSE_EXPIRED + "," + UPDATED_LICENSE_EXPIRED);

        // Get all the vehicleList where licenseExpired equals to UPDATED_LICENSE_EXPIRED
        defaultVehicleShouldNotBeFound("licenseExpired.in=" + UPDATED_LICENSE_EXPIRED);
    }

    @Test
    @Transactional
    void getAllVehiclesByLicenseExpiredIsNullOrNotNull() throws Exception {
        // Initialize the database
        vehicleRepository.saveAndFlush(vehicle);

        // Get all the vehicleList where licenseExpired is not null
        defaultVehicleShouldBeFound("licenseExpired.specified=true");

        // Get all the vehicleList where licenseExpired is null
        defaultVehicleShouldNotBeFound("licenseExpired.specified=false");
    }

    @Test
    @Transactional
    void getAllVehiclesByLicenseExpiredIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        vehicleRepository.saveAndFlush(vehicle);

        // Get all the vehicleList where licenseExpired is greater than or equal to DEFAULT_LICENSE_EXPIRED
        defaultVehicleShouldBeFound("licenseExpired.greaterThanOrEqual=" + DEFAULT_LICENSE_EXPIRED);

        // Get all the vehicleList where licenseExpired is greater than or equal to UPDATED_LICENSE_EXPIRED
        defaultVehicleShouldNotBeFound("licenseExpired.greaterThanOrEqual=" + UPDATED_LICENSE_EXPIRED);
    }

    @Test
    @Transactional
    void getAllVehiclesByLicenseExpiredIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        vehicleRepository.saveAndFlush(vehicle);

        // Get all the vehicleList where licenseExpired is less than or equal to DEFAULT_LICENSE_EXPIRED
        defaultVehicleShouldBeFound("licenseExpired.lessThanOrEqual=" + DEFAULT_LICENSE_EXPIRED);

        // Get all the vehicleList where licenseExpired is less than or equal to SMALLER_LICENSE_EXPIRED
        defaultVehicleShouldNotBeFound("licenseExpired.lessThanOrEqual=" + SMALLER_LICENSE_EXPIRED);
    }

    @Test
    @Transactional
    void getAllVehiclesByLicenseExpiredIsLessThanSomething() throws Exception {
        // Initialize the database
        vehicleRepository.saveAndFlush(vehicle);

        // Get all the vehicleList where licenseExpired is less than DEFAULT_LICENSE_EXPIRED
        defaultVehicleShouldNotBeFound("licenseExpired.lessThan=" + DEFAULT_LICENSE_EXPIRED);

        // Get all the vehicleList where licenseExpired is less than UPDATED_LICENSE_EXPIRED
        defaultVehicleShouldBeFound("licenseExpired.lessThan=" + UPDATED_LICENSE_EXPIRED);
    }

    @Test
    @Transactional
    void getAllVehiclesByLicenseExpiredIsGreaterThanSomething() throws Exception {
        // Initialize the database
        vehicleRepository.saveAndFlush(vehicle);

        // Get all the vehicleList where licenseExpired is greater than DEFAULT_LICENSE_EXPIRED
        defaultVehicleShouldNotBeFound("licenseExpired.greaterThan=" + DEFAULT_LICENSE_EXPIRED);

        // Get all the vehicleList where licenseExpired is greater than SMALLER_LICENSE_EXPIRED
        defaultVehicleShouldBeFound("licenseExpired.greaterThan=" + SMALLER_LICENSE_EXPIRED);
    }

    @Test
    @Transactional
    void getAllVehiclesByJurisdictionIsEqualToSomething() throws Exception {
        // Initialize the database
        vehicleRepository.saveAndFlush(vehicle);

        // Get all the vehicleList where jurisdiction equals to DEFAULT_JURISDICTION
        defaultVehicleShouldBeFound("jurisdiction.equals=" + DEFAULT_JURISDICTION);

        // Get all the vehicleList where jurisdiction equals to UPDATED_JURISDICTION
        defaultVehicleShouldNotBeFound("jurisdiction.equals=" + UPDATED_JURISDICTION);
    }

    @Test
    @Transactional
    void getAllVehiclesByJurisdictionIsInShouldWork() throws Exception {
        // Initialize the database
        vehicleRepository.saveAndFlush(vehicle);

        // Get all the vehicleList where jurisdiction in DEFAULT_JURISDICTION or UPDATED_JURISDICTION
        defaultVehicleShouldBeFound("jurisdiction.in=" + DEFAULT_JURISDICTION + "," + UPDATED_JURISDICTION);

        // Get all the vehicleList where jurisdiction equals to UPDATED_JURISDICTION
        defaultVehicleShouldNotBeFound("jurisdiction.in=" + UPDATED_JURISDICTION);
    }

    @Test
    @Transactional
    void getAllVehiclesByJurisdictionIsNullOrNotNull() throws Exception {
        // Initialize the database
        vehicleRepository.saveAndFlush(vehicle);

        // Get all the vehicleList where jurisdiction is not null
        defaultVehicleShouldBeFound("jurisdiction.specified=true");

        // Get all the vehicleList where jurisdiction is null
        defaultVehicleShouldNotBeFound("jurisdiction.specified=false");
    }

    @Test
    @Transactional
    void getAllVehiclesByJurisdictionIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        vehicleRepository.saveAndFlush(vehicle);

        // Get all the vehicleList where jurisdiction is greater than or equal to DEFAULT_JURISDICTION
        defaultVehicleShouldBeFound("jurisdiction.greaterThanOrEqual=" + DEFAULT_JURISDICTION);

        // Get all the vehicleList where jurisdiction is greater than or equal to UPDATED_JURISDICTION
        defaultVehicleShouldNotBeFound("jurisdiction.greaterThanOrEqual=" + UPDATED_JURISDICTION);
    }

    @Test
    @Transactional
    void getAllVehiclesByJurisdictionIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        vehicleRepository.saveAndFlush(vehicle);

        // Get all the vehicleList where jurisdiction is less than or equal to DEFAULT_JURISDICTION
        defaultVehicleShouldBeFound("jurisdiction.lessThanOrEqual=" + DEFAULT_JURISDICTION);

        // Get all the vehicleList where jurisdiction is less than or equal to SMALLER_JURISDICTION
        defaultVehicleShouldNotBeFound("jurisdiction.lessThanOrEqual=" + SMALLER_JURISDICTION);
    }

    @Test
    @Transactional
    void getAllVehiclesByJurisdictionIsLessThanSomething() throws Exception {
        // Initialize the database
        vehicleRepository.saveAndFlush(vehicle);

        // Get all the vehicleList where jurisdiction is less than DEFAULT_JURISDICTION
        defaultVehicleShouldNotBeFound("jurisdiction.lessThan=" + DEFAULT_JURISDICTION);

        // Get all the vehicleList where jurisdiction is less than UPDATED_JURISDICTION
        defaultVehicleShouldBeFound("jurisdiction.lessThan=" + UPDATED_JURISDICTION);
    }

    @Test
    @Transactional
    void getAllVehiclesByJurisdictionIsGreaterThanSomething() throws Exception {
        // Initialize the database
        vehicleRepository.saveAndFlush(vehicle);

        // Get all the vehicleList where jurisdiction is greater than DEFAULT_JURISDICTION
        defaultVehicleShouldNotBeFound("jurisdiction.greaterThan=" + DEFAULT_JURISDICTION);

        // Get all the vehicleList where jurisdiction is greater than SMALLER_JURISDICTION
        defaultVehicleShouldBeFound("jurisdiction.greaterThan=" + SMALLER_JURISDICTION);
    }

    @Test
    @Transactional
    void getAllVehiclesByUpdatedIsEqualToSomething() throws Exception {
        // Initialize the database
        vehicleRepository.saveAndFlush(vehicle);

        // Get all the vehicleList where updated equals to DEFAULT_UPDATED
        defaultVehicleShouldBeFound("updated.equals=" + DEFAULT_UPDATED);

        // Get all the vehicleList where updated equals to UPDATED_UPDATED
        defaultVehicleShouldNotBeFound("updated.equals=" + UPDATED_UPDATED);
    }

    @Test
    @Transactional
    void getAllVehiclesByUpdatedIsInShouldWork() throws Exception {
        // Initialize the database
        vehicleRepository.saveAndFlush(vehicle);

        // Get all the vehicleList where updated in DEFAULT_UPDATED or UPDATED_UPDATED
        defaultVehicleShouldBeFound("updated.in=" + DEFAULT_UPDATED + "," + UPDATED_UPDATED);

        // Get all the vehicleList where updated equals to UPDATED_UPDATED
        defaultVehicleShouldNotBeFound("updated.in=" + UPDATED_UPDATED);
    }

    @Test
    @Transactional
    void getAllVehiclesByUpdatedIsNullOrNotNull() throws Exception {
        // Initialize the database
        vehicleRepository.saveAndFlush(vehicle);

        // Get all the vehicleList where updated is not null
        defaultVehicleShouldBeFound("updated.specified=true");

        // Get all the vehicleList where updated is null
        defaultVehicleShouldNotBeFound("updated.specified=false");
    }

    @Test
    @Transactional
    void getAllVehiclesByFuelTypeIsEqualToSomething() throws Exception {
        // Initialize the database
        vehicleRepository.saveAndFlush(vehicle);

        // Get all the vehicleList where fuelType equals to DEFAULT_FUEL_TYPE
        defaultVehicleShouldBeFound("fuelType.equals=" + DEFAULT_FUEL_TYPE);

        // Get all the vehicleList where fuelType equals to UPDATED_FUEL_TYPE
        defaultVehicleShouldNotBeFound("fuelType.equals=" + UPDATED_FUEL_TYPE);
    }

    @Test
    @Transactional
    void getAllVehiclesByFuelTypeIsInShouldWork() throws Exception {
        // Initialize the database
        vehicleRepository.saveAndFlush(vehicle);

        // Get all the vehicleList where fuelType in DEFAULT_FUEL_TYPE or UPDATED_FUEL_TYPE
        defaultVehicleShouldBeFound("fuelType.in=" + DEFAULT_FUEL_TYPE + "," + UPDATED_FUEL_TYPE);

        // Get all the vehicleList where fuelType equals to UPDATED_FUEL_TYPE
        defaultVehicleShouldNotBeFound("fuelType.in=" + UPDATED_FUEL_TYPE);
    }

    @Test
    @Transactional
    void getAllVehiclesByFuelTypeIsNullOrNotNull() throws Exception {
        // Initialize the database
        vehicleRepository.saveAndFlush(vehicle);

        // Get all the vehicleList where fuelType is not null
        defaultVehicleShouldBeFound("fuelType.specified=true");

        // Get all the vehicleList where fuelType is null
        defaultVehicleShouldNotBeFound("fuelType.specified=false");
    }

    @Test
    @Transactional
    void getAllVehiclesByFuelTypeContainsSomething() throws Exception {
        // Initialize the database
        vehicleRepository.saveAndFlush(vehicle);

        // Get all the vehicleList where fuelType contains DEFAULT_FUEL_TYPE
        defaultVehicleShouldBeFound("fuelType.contains=" + DEFAULT_FUEL_TYPE);

        // Get all the vehicleList where fuelType contains UPDATED_FUEL_TYPE
        defaultVehicleShouldNotBeFound("fuelType.contains=" + UPDATED_FUEL_TYPE);
    }

    @Test
    @Transactional
    void getAllVehiclesByFuelTypeNotContainsSomething() throws Exception {
        // Initialize the database
        vehicleRepository.saveAndFlush(vehicle);

        // Get all the vehicleList where fuelType does not contain DEFAULT_FUEL_TYPE
        defaultVehicleShouldNotBeFound("fuelType.doesNotContain=" + DEFAULT_FUEL_TYPE);

        // Get all the vehicleList where fuelType does not contain UPDATED_FUEL_TYPE
        defaultVehicleShouldBeFound("fuelType.doesNotContain=" + UPDATED_FUEL_TYPE);
    }

    @Test
    @Transactional
    void getAllVehiclesByMotorCcIsEqualToSomething() throws Exception {
        // Initialize the database
        vehicleRepository.saveAndFlush(vehicle);

        // Get all the vehicleList where motorCc equals to DEFAULT_MOTOR_CC
        defaultVehicleShouldBeFound("motorCc.equals=" + DEFAULT_MOTOR_CC);

        // Get all the vehicleList where motorCc equals to UPDATED_MOTOR_CC
        defaultVehicleShouldNotBeFound("motorCc.equals=" + UPDATED_MOTOR_CC);
    }

    @Test
    @Transactional
    void getAllVehiclesByMotorCcIsInShouldWork() throws Exception {
        // Initialize the database
        vehicleRepository.saveAndFlush(vehicle);

        // Get all the vehicleList where motorCc in DEFAULT_MOTOR_CC or UPDATED_MOTOR_CC
        defaultVehicleShouldBeFound("motorCc.in=" + DEFAULT_MOTOR_CC + "," + UPDATED_MOTOR_CC);

        // Get all the vehicleList where motorCc equals to UPDATED_MOTOR_CC
        defaultVehicleShouldNotBeFound("motorCc.in=" + UPDATED_MOTOR_CC);
    }

    @Test
    @Transactional
    void getAllVehiclesByMotorCcIsNullOrNotNull() throws Exception {
        // Initialize the database
        vehicleRepository.saveAndFlush(vehicle);

        // Get all the vehicleList where motorCc is not null
        defaultVehicleShouldBeFound("motorCc.specified=true");

        // Get all the vehicleList where motorCc is null
        defaultVehicleShouldNotBeFound("motorCc.specified=false");
    }

    @Test
    @Transactional
    void getAllVehiclesByMotorCcIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        vehicleRepository.saveAndFlush(vehicle);

        // Get all the vehicleList where motorCc is greater than or equal to DEFAULT_MOTOR_CC
        defaultVehicleShouldBeFound("motorCc.greaterThanOrEqual=" + DEFAULT_MOTOR_CC);

        // Get all the vehicleList where motorCc is greater than or equal to UPDATED_MOTOR_CC
        defaultVehicleShouldNotBeFound("motorCc.greaterThanOrEqual=" + UPDATED_MOTOR_CC);
    }

    @Test
    @Transactional
    void getAllVehiclesByMotorCcIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        vehicleRepository.saveAndFlush(vehicle);

        // Get all the vehicleList where motorCc is less than or equal to DEFAULT_MOTOR_CC
        defaultVehicleShouldBeFound("motorCc.lessThanOrEqual=" + DEFAULT_MOTOR_CC);

        // Get all the vehicleList where motorCc is less than or equal to SMALLER_MOTOR_CC
        defaultVehicleShouldNotBeFound("motorCc.lessThanOrEqual=" + SMALLER_MOTOR_CC);
    }

    @Test
    @Transactional
    void getAllVehiclesByMotorCcIsLessThanSomething() throws Exception {
        // Initialize the database
        vehicleRepository.saveAndFlush(vehicle);

        // Get all the vehicleList where motorCc is less than DEFAULT_MOTOR_CC
        defaultVehicleShouldNotBeFound("motorCc.lessThan=" + DEFAULT_MOTOR_CC);

        // Get all the vehicleList where motorCc is less than UPDATED_MOTOR_CC
        defaultVehicleShouldBeFound("motorCc.lessThan=" + UPDATED_MOTOR_CC);
    }

    @Test
    @Transactional
    void getAllVehiclesByMotorCcIsGreaterThanSomething() throws Exception {
        // Initialize the database
        vehicleRepository.saveAndFlush(vehicle);

        // Get all the vehicleList where motorCc is greater than DEFAULT_MOTOR_CC
        defaultVehicleShouldNotBeFound("motorCc.greaterThan=" + DEFAULT_MOTOR_CC);

        // Get all the vehicleList where motorCc is greater than SMALLER_MOTOR_CC
        defaultVehicleShouldBeFound("motorCc.greaterThan=" + SMALLER_MOTOR_CC);
    }

    @Test
    @Transactional
    void getAllVehiclesByLicenseIssuedIsEqualToSomething() throws Exception {
        // Initialize the database
        vehicleRepository.saveAndFlush(vehicle);

        // Get all the vehicleList where licenseIssued equals to DEFAULT_LICENSE_ISSUED
        defaultVehicleShouldBeFound("licenseIssued.equals=" + DEFAULT_LICENSE_ISSUED);

        // Get all the vehicleList where licenseIssued equals to UPDATED_LICENSE_ISSUED
        defaultVehicleShouldNotBeFound("licenseIssued.equals=" + UPDATED_LICENSE_ISSUED);
    }

    @Test
    @Transactional
    void getAllVehiclesByLicenseIssuedIsInShouldWork() throws Exception {
        // Initialize the database
        vehicleRepository.saveAndFlush(vehicle);

        // Get all the vehicleList where licenseIssued in DEFAULT_LICENSE_ISSUED or UPDATED_LICENSE_ISSUED
        defaultVehicleShouldBeFound("licenseIssued.in=" + DEFAULT_LICENSE_ISSUED + "," + UPDATED_LICENSE_ISSUED);

        // Get all the vehicleList where licenseIssued equals to UPDATED_LICENSE_ISSUED
        defaultVehicleShouldNotBeFound("licenseIssued.in=" + UPDATED_LICENSE_ISSUED);
    }

    @Test
    @Transactional
    void getAllVehiclesByLicenseIssuedIsNullOrNotNull() throws Exception {
        // Initialize the database
        vehicleRepository.saveAndFlush(vehicle);

        // Get all the vehicleList where licenseIssued is not null
        defaultVehicleShouldBeFound("licenseIssued.specified=true");

        // Get all the vehicleList where licenseIssued is null
        defaultVehicleShouldNotBeFound("licenseIssued.specified=false");
    }

    @Test
    @Transactional
    void getAllVehiclesByLicenseExpiresIsEqualToSomething() throws Exception {
        // Initialize the database
        vehicleRepository.saveAndFlush(vehicle);

        // Get all the vehicleList where licenseExpires equals to DEFAULT_LICENSE_EXPIRES
        defaultVehicleShouldBeFound("licenseExpires.equals=" + DEFAULT_LICENSE_EXPIRES);

        // Get all the vehicleList where licenseExpires equals to UPDATED_LICENSE_EXPIRES
        defaultVehicleShouldNotBeFound("licenseExpires.equals=" + UPDATED_LICENSE_EXPIRES);
    }

    @Test
    @Transactional
    void getAllVehiclesByLicenseExpiresIsInShouldWork() throws Exception {
        // Initialize the database
        vehicleRepository.saveAndFlush(vehicle);

        // Get all the vehicleList where licenseExpires in DEFAULT_LICENSE_EXPIRES or UPDATED_LICENSE_EXPIRES
        defaultVehicleShouldBeFound("licenseExpires.in=" + DEFAULT_LICENSE_EXPIRES + "," + UPDATED_LICENSE_EXPIRES);

        // Get all the vehicleList where licenseExpires equals to UPDATED_LICENSE_EXPIRES
        defaultVehicleShouldNotBeFound("licenseExpires.in=" + UPDATED_LICENSE_EXPIRES);
    }

    @Test
    @Transactional
    void getAllVehiclesByLicenseExpiresIsNullOrNotNull() throws Exception {
        // Initialize the database
        vehicleRepository.saveAndFlush(vehicle);

        // Get all the vehicleList where licenseExpires is not null
        defaultVehicleShouldBeFound("licenseExpires.specified=true");

        // Get all the vehicleList where licenseExpires is null
        defaultVehicleShouldNotBeFound("licenseExpires.specified=false");
    }

    @Test
    @Transactional
    void getAllVehiclesByWtKgIsEqualToSomething() throws Exception {
        // Initialize the database
        vehicleRepository.saveAndFlush(vehicle);

        // Get all the vehicleList where wtKg equals to DEFAULT_WT_KG
        defaultVehicleShouldBeFound("wtKg.equals=" + DEFAULT_WT_KG);

        // Get all the vehicleList where wtKg equals to UPDATED_WT_KG
        defaultVehicleShouldNotBeFound("wtKg.equals=" + UPDATED_WT_KG);
    }

    @Test
    @Transactional
    void getAllVehiclesByWtKgIsInShouldWork() throws Exception {
        // Initialize the database
        vehicleRepository.saveAndFlush(vehicle);

        // Get all the vehicleList where wtKg in DEFAULT_WT_KG or UPDATED_WT_KG
        defaultVehicleShouldBeFound("wtKg.in=" + DEFAULT_WT_KG + "," + UPDATED_WT_KG);

        // Get all the vehicleList where wtKg equals to UPDATED_WT_KG
        defaultVehicleShouldNotBeFound("wtKg.in=" + UPDATED_WT_KG);
    }

    @Test
    @Transactional
    void getAllVehiclesByWtKgIsNullOrNotNull() throws Exception {
        // Initialize the database
        vehicleRepository.saveAndFlush(vehicle);

        // Get all the vehicleList where wtKg is not null
        defaultVehicleShouldBeFound("wtKg.specified=true");

        // Get all the vehicleList where wtKg is null
        defaultVehicleShouldNotBeFound("wtKg.specified=false");
    }

    @Test
    @Transactional
    void getAllVehiclesByWtKgIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        vehicleRepository.saveAndFlush(vehicle);

        // Get all the vehicleList where wtKg is greater than or equal to DEFAULT_WT_KG
        defaultVehicleShouldBeFound("wtKg.greaterThanOrEqual=" + DEFAULT_WT_KG);

        // Get all the vehicleList where wtKg is greater than or equal to UPDATED_WT_KG
        defaultVehicleShouldNotBeFound("wtKg.greaterThanOrEqual=" + UPDATED_WT_KG);
    }

    @Test
    @Transactional
    void getAllVehiclesByWtKgIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        vehicleRepository.saveAndFlush(vehicle);

        // Get all the vehicleList where wtKg is less than or equal to DEFAULT_WT_KG
        defaultVehicleShouldBeFound("wtKg.lessThanOrEqual=" + DEFAULT_WT_KG);

        // Get all the vehicleList where wtKg is less than or equal to SMALLER_WT_KG
        defaultVehicleShouldNotBeFound("wtKg.lessThanOrEqual=" + SMALLER_WT_KG);
    }

    @Test
    @Transactional
    void getAllVehiclesByWtKgIsLessThanSomething() throws Exception {
        // Initialize the database
        vehicleRepository.saveAndFlush(vehicle);

        // Get all the vehicleList where wtKg is less than DEFAULT_WT_KG
        defaultVehicleShouldNotBeFound("wtKg.lessThan=" + DEFAULT_WT_KG);

        // Get all the vehicleList where wtKg is less than UPDATED_WT_KG
        defaultVehicleShouldBeFound("wtKg.lessThan=" + UPDATED_WT_KG);
    }

    @Test
    @Transactional
    void getAllVehiclesByWtKgIsGreaterThanSomething() throws Exception {
        // Initialize the database
        vehicleRepository.saveAndFlush(vehicle);

        // Get all the vehicleList where wtKg is greater than DEFAULT_WT_KG
        defaultVehicleShouldNotBeFound("wtKg.greaterThan=" + DEFAULT_WT_KG);

        // Get all the vehicleList where wtKg is greater than SMALLER_WT_KG
        defaultVehicleShouldBeFound("wtKg.greaterThan=" + SMALLER_WT_KG);
    }

    @Test
    @Transactional
    void getAllVehiclesByPassengersIsEqualToSomething() throws Exception {
        // Initialize the database
        vehicleRepository.saveAndFlush(vehicle);

        // Get all the vehicleList where passengers equals to DEFAULT_PASSENGERS
        defaultVehicleShouldBeFound("passengers.equals=" + DEFAULT_PASSENGERS);

        // Get all the vehicleList where passengers equals to UPDATED_PASSENGERS
        defaultVehicleShouldNotBeFound("passengers.equals=" + UPDATED_PASSENGERS);
    }

    @Test
    @Transactional
    void getAllVehiclesByPassengersIsInShouldWork() throws Exception {
        // Initialize the database
        vehicleRepository.saveAndFlush(vehicle);

        // Get all the vehicleList where passengers in DEFAULT_PASSENGERS or UPDATED_PASSENGERS
        defaultVehicleShouldBeFound("passengers.in=" + DEFAULT_PASSENGERS + "," + UPDATED_PASSENGERS);

        // Get all the vehicleList where passengers equals to UPDATED_PASSENGERS
        defaultVehicleShouldNotBeFound("passengers.in=" + UPDATED_PASSENGERS);
    }

    @Test
    @Transactional
    void getAllVehiclesByPassengersIsNullOrNotNull() throws Exception {
        // Initialize the database
        vehicleRepository.saveAndFlush(vehicle);

        // Get all the vehicleList where passengers is not null
        defaultVehicleShouldBeFound("passengers.specified=true");

        // Get all the vehicleList where passengers is null
        defaultVehicleShouldNotBeFound("passengers.specified=false");
    }

    @Test
    @Transactional
    void getAllVehiclesByPassengersIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        vehicleRepository.saveAndFlush(vehicle);

        // Get all the vehicleList where passengers is greater than or equal to DEFAULT_PASSENGERS
        defaultVehicleShouldBeFound("passengers.greaterThanOrEqual=" + DEFAULT_PASSENGERS);

        // Get all the vehicleList where passengers is greater than or equal to UPDATED_PASSENGERS
        defaultVehicleShouldNotBeFound("passengers.greaterThanOrEqual=" + UPDATED_PASSENGERS);
    }

    @Test
    @Transactional
    void getAllVehiclesByPassengersIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        vehicleRepository.saveAndFlush(vehicle);

        // Get all the vehicleList where passengers is less than or equal to DEFAULT_PASSENGERS
        defaultVehicleShouldBeFound("passengers.lessThanOrEqual=" + DEFAULT_PASSENGERS);

        // Get all the vehicleList where passengers is less than or equal to SMALLER_PASSENGERS
        defaultVehicleShouldNotBeFound("passengers.lessThanOrEqual=" + SMALLER_PASSENGERS);
    }

    @Test
    @Transactional
    void getAllVehiclesByPassengersIsLessThanSomething() throws Exception {
        // Initialize the database
        vehicleRepository.saveAndFlush(vehicle);

        // Get all the vehicleList where passengers is less than DEFAULT_PASSENGERS
        defaultVehicleShouldNotBeFound("passengers.lessThan=" + DEFAULT_PASSENGERS);

        // Get all the vehicleList where passengers is less than UPDATED_PASSENGERS
        defaultVehicleShouldBeFound("passengers.lessThan=" + UPDATED_PASSENGERS);
    }

    @Test
    @Transactional
    void getAllVehiclesByPassengersIsGreaterThanSomething() throws Exception {
        // Initialize the database
        vehicleRepository.saveAndFlush(vehicle);

        // Get all the vehicleList where passengers is greater than DEFAULT_PASSENGERS
        defaultVehicleShouldNotBeFound("passengers.greaterThan=" + DEFAULT_PASSENGERS);

        // Get all the vehicleList where passengers is greater than SMALLER_PASSENGERS
        defaultVehicleShouldBeFound("passengers.greaterThan=" + SMALLER_PASSENGERS);
    }

    @Test
    @Transactional
    void getAllVehiclesByTractorPartsIsEqualToSomething() throws Exception {
        // Initialize the database
        vehicleRepository.saveAndFlush(vehicle);

        // Get all the vehicleList where tractorParts equals to DEFAULT_TRACTOR_PARTS
        defaultVehicleShouldBeFound("tractorParts.equals=" + DEFAULT_TRACTOR_PARTS);

        // Get all the vehicleList where tractorParts equals to UPDATED_TRACTOR_PARTS
        defaultVehicleShouldNotBeFound("tractorParts.equals=" + UPDATED_TRACTOR_PARTS);
    }

    @Test
    @Transactional
    void getAllVehiclesByTractorPartsIsInShouldWork() throws Exception {
        // Initialize the database
        vehicleRepository.saveAndFlush(vehicle);

        // Get all the vehicleList where tractorParts in DEFAULT_TRACTOR_PARTS or UPDATED_TRACTOR_PARTS
        defaultVehicleShouldBeFound("tractorParts.in=" + DEFAULT_TRACTOR_PARTS + "," + UPDATED_TRACTOR_PARTS);

        // Get all the vehicleList where tractorParts equals to UPDATED_TRACTOR_PARTS
        defaultVehicleShouldNotBeFound("tractorParts.in=" + UPDATED_TRACTOR_PARTS);
    }

    @Test
    @Transactional
    void getAllVehiclesByTractorPartsIsNullOrNotNull() throws Exception {
        // Initialize the database
        vehicleRepository.saveAndFlush(vehicle);

        // Get all the vehicleList where tractorParts is not null
        defaultVehicleShouldBeFound("tractorParts.specified=true");

        // Get all the vehicleList where tractorParts is null
        defaultVehicleShouldNotBeFound("tractorParts.specified=false");
    }

    @Test
    @Transactional
    void getAllVehiclesByTractorPartsIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        vehicleRepository.saveAndFlush(vehicle);

        // Get all the vehicleList where tractorParts is greater than or equal to DEFAULT_TRACTOR_PARTS
        defaultVehicleShouldBeFound("tractorParts.greaterThanOrEqual=" + DEFAULT_TRACTOR_PARTS);

        // Get all the vehicleList where tractorParts is greater than or equal to UPDATED_TRACTOR_PARTS
        defaultVehicleShouldNotBeFound("tractorParts.greaterThanOrEqual=" + UPDATED_TRACTOR_PARTS);
    }

    @Test
    @Transactional
    void getAllVehiclesByTractorPartsIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        vehicleRepository.saveAndFlush(vehicle);

        // Get all the vehicleList where tractorParts is less than or equal to DEFAULT_TRACTOR_PARTS
        defaultVehicleShouldBeFound("tractorParts.lessThanOrEqual=" + DEFAULT_TRACTOR_PARTS);

        // Get all the vehicleList where tractorParts is less than or equal to SMALLER_TRACTOR_PARTS
        defaultVehicleShouldNotBeFound("tractorParts.lessThanOrEqual=" + SMALLER_TRACTOR_PARTS);
    }

    @Test
    @Transactional
    void getAllVehiclesByTractorPartsIsLessThanSomething() throws Exception {
        // Initialize the database
        vehicleRepository.saveAndFlush(vehicle);

        // Get all the vehicleList where tractorParts is less than DEFAULT_TRACTOR_PARTS
        defaultVehicleShouldNotBeFound("tractorParts.lessThan=" + DEFAULT_TRACTOR_PARTS);

        // Get all the vehicleList where tractorParts is less than UPDATED_TRACTOR_PARTS
        defaultVehicleShouldBeFound("tractorParts.lessThan=" + UPDATED_TRACTOR_PARTS);
    }

    @Test
    @Transactional
    void getAllVehiclesByTractorPartsIsGreaterThanSomething() throws Exception {
        // Initialize the database
        vehicleRepository.saveAndFlush(vehicle);

        // Get all the vehicleList where tractorParts is greater than DEFAULT_TRACTOR_PARTS
        defaultVehicleShouldNotBeFound("tractorParts.greaterThan=" + DEFAULT_TRACTOR_PARTS);

        // Get all the vehicleList where tractorParts is greater than SMALLER_TRACTOR_PARTS
        defaultVehicleShouldBeFound("tractorParts.greaterThan=" + SMALLER_TRACTOR_PARTS);
    }

    @Test
    @Transactional
    void getAllVehiclesByExtraSizePercentIsEqualToSomething() throws Exception {
        // Initialize the database
        vehicleRepository.saveAndFlush(vehicle);

        // Get all the vehicleList where extraSizePercent equals to DEFAULT_EXTRA_SIZE_PERCENT
        defaultVehicleShouldBeFound("extraSizePercent.equals=" + DEFAULT_EXTRA_SIZE_PERCENT);

        // Get all the vehicleList where extraSizePercent equals to UPDATED_EXTRA_SIZE_PERCENT
        defaultVehicleShouldNotBeFound("extraSizePercent.equals=" + UPDATED_EXTRA_SIZE_PERCENT);
    }

    @Test
    @Transactional
    void getAllVehiclesByExtraSizePercentIsInShouldWork() throws Exception {
        // Initialize the database
        vehicleRepository.saveAndFlush(vehicle);

        // Get all the vehicleList where extraSizePercent in DEFAULT_EXTRA_SIZE_PERCENT or UPDATED_EXTRA_SIZE_PERCENT
        defaultVehicleShouldBeFound("extraSizePercent.in=" + DEFAULT_EXTRA_SIZE_PERCENT + "," + UPDATED_EXTRA_SIZE_PERCENT);

        // Get all the vehicleList where extraSizePercent equals to UPDATED_EXTRA_SIZE_PERCENT
        defaultVehicleShouldNotBeFound("extraSizePercent.in=" + UPDATED_EXTRA_SIZE_PERCENT);
    }

    @Test
    @Transactional
    void getAllVehiclesByExtraSizePercentIsNullOrNotNull() throws Exception {
        // Initialize the database
        vehicleRepository.saveAndFlush(vehicle);

        // Get all the vehicleList where extraSizePercent is not null
        defaultVehicleShouldBeFound("extraSizePercent.specified=true");

        // Get all the vehicleList where extraSizePercent is null
        defaultVehicleShouldNotBeFound("extraSizePercent.specified=false");
    }

    @Test
    @Transactional
    void getAllVehiclesByExtraSizePercentIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        vehicleRepository.saveAndFlush(vehicle);

        // Get all the vehicleList where extraSizePercent is greater than or equal to DEFAULT_EXTRA_SIZE_PERCENT
        defaultVehicleShouldBeFound("extraSizePercent.greaterThanOrEqual=" + DEFAULT_EXTRA_SIZE_PERCENT);

        // Get all the vehicleList where extraSizePercent is greater than or equal to UPDATED_EXTRA_SIZE_PERCENT
        defaultVehicleShouldNotBeFound("extraSizePercent.greaterThanOrEqual=" + UPDATED_EXTRA_SIZE_PERCENT);
    }

    @Test
    @Transactional
    void getAllVehiclesByExtraSizePercentIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        vehicleRepository.saveAndFlush(vehicle);

        // Get all the vehicleList where extraSizePercent is less than or equal to DEFAULT_EXTRA_SIZE_PERCENT
        defaultVehicleShouldBeFound("extraSizePercent.lessThanOrEqual=" + DEFAULT_EXTRA_SIZE_PERCENT);

        // Get all the vehicleList where extraSizePercent is less than or equal to SMALLER_EXTRA_SIZE_PERCENT
        defaultVehicleShouldNotBeFound("extraSizePercent.lessThanOrEqual=" + SMALLER_EXTRA_SIZE_PERCENT);
    }

    @Test
    @Transactional
    void getAllVehiclesByExtraSizePercentIsLessThanSomething() throws Exception {
        // Initialize the database
        vehicleRepository.saveAndFlush(vehicle);

        // Get all the vehicleList where extraSizePercent is less than DEFAULT_EXTRA_SIZE_PERCENT
        defaultVehicleShouldNotBeFound("extraSizePercent.lessThan=" + DEFAULT_EXTRA_SIZE_PERCENT);

        // Get all the vehicleList where extraSizePercent is less than UPDATED_EXTRA_SIZE_PERCENT
        defaultVehicleShouldBeFound("extraSizePercent.lessThan=" + UPDATED_EXTRA_SIZE_PERCENT);
    }

    @Test
    @Transactional
    void getAllVehiclesByExtraSizePercentIsGreaterThanSomething() throws Exception {
        // Initialize the database
        vehicleRepository.saveAndFlush(vehicle);

        // Get all the vehicleList where extraSizePercent is greater than DEFAULT_EXTRA_SIZE_PERCENT
        defaultVehicleShouldNotBeFound("extraSizePercent.greaterThan=" + DEFAULT_EXTRA_SIZE_PERCENT);

        // Get all the vehicleList where extraSizePercent is greater than SMALLER_EXTRA_SIZE_PERCENT
        defaultVehicleShouldBeFound("extraSizePercent.greaterThan=" + SMALLER_EXTRA_SIZE_PERCENT);
    }

    @Test
    @Transactional
    void getAllVehiclesByWtExtraIsEqualToSomething() throws Exception {
        // Initialize the database
        vehicleRepository.saveAndFlush(vehicle);

        // Get all the vehicleList where wtExtra equals to DEFAULT_WT_EXTRA
        defaultVehicleShouldBeFound("wtExtra.equals=" + DEFAULT_WT_EXTRA);

        // Get all the vehicleList where wtExtra equals to UPDATED_WT_EXTRA
        defaultVehicleShouldNotBeFound("wtExtra.equals=" + UPDATED_WT_EXTRA);
    }

    @Test
    @Transactional
    void getAllVehiclesByWtExtraIsInShouldWork() throws Exception {
        // Initialize the database
        vehicleRepository.saveAndFlush(vehicle);

        // Get all the vehicleList where wtExtra in DEFAULT_WT_EXTRA or UPDATED_WT_EXTRA
        defaultVehicleShouldBeFound("wtExtra.in=" + DEFAULT_WT_EXTRA + "," + UPDATED_WT_EXTRA);

        // Get all the vehicleList where wtExtra equals to UPDATED_WT_EXTRA
        defaultVehicleShouldNotBeFound("wtExtra.in=" + UPDATED_WT_EXTRA);
    }

    @Test
    @Transactional
    void getAllVehiclesByWtExtraIsNullOrNotNull() throws Exception {
        // Initialize the database
        vehicleRepository.saveAndFlush(vehicle);

        // Get all the vehicleList where wtExtra is not null
        defaultVehicleShouldBeFound("wtExtra.specified=true");

        // Get all the vehicleList where wtExtra is null
        defaultVehicleShouldNotBeFound("wtExtra.specified=false");
    }

    @Test
    @Transactional
    void getAllVehiclesByWtExtraIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        vehicleRepository.saveAndFlush(vehicle);

        // Get all the vehicleList where wtExtra is greater than or equal to DEFAULT_WT_EXTRA
        defaultVehicleShouldBeFound("wtExtra.greaterThanOrEqual=" + DEFAULT_WT_EXTRA);

        // Get all the vehicleList where wtExtra is greater than or equal to UPDATED_WT_EXTRA
        defaultVehicleShouldNotBeFound("wtExtra.greaterThanOrEqual=" + UPDATED_WT_EXTRA);
    }

    @Test
    @Transactional
    void getAllVehiclesByWtExtraIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        vehicleRepository.saveAndFlush(vehicle);

        // Get all the vehicleList where wtExtra is less than or equal to DEFAULT_WT_EXTRA
        defaultVehicleShouldBeFound("wtExtra.lessThanOrEqual=" + DEFAULT_WT_EXTRA);

        // Get all the vehicleList where wtExtra is less than or equal to SMALLER_WT_EXTRA
        defaultVehicleShouldNotBeFound("wtExtra.lessThanOrEqual=" + SMALLER_WT_EXTRA);
    }

    @Test
    @Transactional
    void getAllVehiclesByWtExtraIsLessThanSomething() throws Exception {
        // Initialize the database
        vehicleRepository.saveAndFlush(vehicle);

        // Get all the vehicleList where wtExtra is less than DEFAULT_WT_EXTRA
        defaultVehicleShouldNotBeFound("wtExtra.lessThan=" + DEFAULT_WT_EXTRA);

        // Get all the vehicleList where wtExtra is less than UPDATED_WT_EXTRA
        defaultVehicleShouldBeFound("wtExtra.lessThan=" + UPDATED_WT_EXTRA);
    }

    @Test
    @Transactional
    void getAllVehiclesByWtExtraIsGreaterThanSomething() throws Exception {
        // Initialize the database
        vehicleRepository.saveAndFlush(vehicle);

        // Get all the vehicleList where wtExtra is greater than DEFAULT_WT_EXTRA
        defaultVehicleShouldNotBeFound("wtExtra.greaterThan=" + DEFAULT_WT_EXTRA);

        // Get all the vehicleList where wtExtra is greater than SMALLER_WT_EXTRA
        defaultVehicleShouldBeFound("wtExtra.greaterThan=" + SMALLER_WT_EXTRA);
    }

    @Test
    @Transactional
    void getAllVehiclesByBikeDataIsEqualToSomething() throws Exception {
        // Initialize the database
        vehicleRepository.saveAndFlush(vehicle);

        // Get all the vehicleList where bikeData equals to DEFAULT_BIKE_DATA
        defaultVehicleShouldBeFound("bikeData.equals=" + DEFAULT_BIKE_DATA);

        // Get all the vehicleList where bikeData equals to UPDATED_BIKE_DATA
        defaultVehicleShouldNotBeFound("bikeData.equals=" + UPDATED_BIKE_DATA);
    }

    @Test
    @Transactional
    void getAllVehiclesByBikeDataIsInShouldWork() throws Exception {
        // Initialize the database
        vehicleRepository.saveAndFlush(vehicle);

        // Get all the vehicleList where bikeData in DEFAULT_BIKE_DATA or UPDATED_BIKE_DATA
        defaultVehicleShouldBeFound("bikeData.in=" + DEFAULT_BIKE_DATA + "," + UPDATED_BIKE_DATA);

        // Get all the vehicleList where bikeData equals to UPDATED_BIKE_DATA
        defaultVehicleShouldNotBeFound("bikeData.in=" + UPDATED_BIKE_DATA);
    }

    @Test
    @Transactional
    void getAllVehiclesByBikeDataIsNullOrNotNull() throws Exception {
        // Initialize the database
        vehicleRepository.saveAndFlush(vehicle);

        // Get all the vehicleList where bikeData is not null
        defaultVehicleShouldBeFound("bikeData.specified=true");

        // Get all the vehicleList where bikeData is null
        defaultVehicleShouldNotBeFound("bikeData.specified=false");
    }

    @Test
    @Transactional
    void getAllVehiclesByBikeDataContainsSomething() throws Exception {
        // Initialize the database
        vehicleRepository.saveAndFlush(vehicle);

        // Get all the vehicleList where bikeData contains DEFAULT_BIKE_DATA
        defaultVehicleShouldBeFound("bikeData.contains=" + DEFAULT_BIKE_DATA);

        // Get all the vehicleList where bikeData contains UPDATED_BIKE_DATA
        defaultVehicleShouldNotBeFound("bikeData.contains=" + UPDATED_BIKE_DATA);
    }

    @Test
    @Transactional
    void getAllVehiclesByBikeDataNotContainsSomething() throws Exception {
        // Initialize the database
        vehicleRepository.saveAndFlush(vehicle);

        // Get all the vehicleList where bikeData does not contain DEFAULT_BIKE_DATA
        defaultVehicleShouldNotBeFound("bikeData.doesNotContain=" + DEFAULT_BIKE_DATA);

        // Get all the vehicleList where bikeData does not contain UPDATED_BIKE_DATA
        defaultVehicleShouldBeFound("bikeData.doesNotContain=" + UPDATED_BIKE_DATA);
    }

    @Test
    @Transactional
    void getAllVehiclesByWantedByIsEqualToSomething() throws Exception {
        // Initialize the database
        vehicleRepository.saveAndFlush(vehicle);

        // Get all the vehicleList where wantedBy equals to DEFAULT_WANTED_BY
        defaultVehicleShouldBeFound("wantedBy.equals=" + DEFAULT_WANTED_BY);

        // Get all the vehicleList where wantedBy equals to UPDATED_WANTED_BY
        defaultVehicleShouldNotBeFound("wantedBy.equals=" + UPDATED_WANTED_BY);
    }

    @Test
    @Transactional
    void getAllVehiclesByWantedByIsInShouldWork() throws Exception {
        // Initialize the database
        vehicleRepository.saveAndFlush(vehicle);

        // Get all the vehicleList where wantedBy in DEFAULT_WANTED_BY or UPDATED_WANTED_BY
        defaultVehicleShouldBeFound("wantedBy.in=" + DEFAULT_WANTED_BY + "," + UPDATED_WANTED_BY);

        // Get all the vehicleList where wantedBy equals to UPDATED_WANTED_BY
        defaultVehicleShouldNotBeFound("wantedBy.in=" + UPDATED_WANTED_BY);
    }

    @Test
    @Transactional
    void getAllVehiclesByWantedByIsNullOrNotNull() throws Exception {
        // Initialize the database
        vehicleRepository.saveAndFlush(vehicle);

        // Get all the vehicleList where wantedBy is not null
        defaultVehicleShouldBeFound("wantedBy.specified=true");

        // Get all the vehicleList where wantedBy is null
        defaultVehicleShouldNotBeFound("wantedBy.specified=false");
    }

    @Test
    @Transactional
    void getAllVehiclesByWantedByIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        vehicleRepository.saveAndFlush(vehicle);

        // Get all the vehicleList where wantedBy is greater than or equal to DEFAULT_WANTED_BY
        defaultVehicleShouldBeFound("wantedBy.greaterThanOrEqual=" + DEFAULT_WANTED_BY);

        // Get all the vehicleList where wantedBy is greater than or equal to UPDATED_WANTED_BY
        defaultVehicleShouldNotBeFound("wantedBy.greaterThanOrEqual=" + UPDATED_WANTED_BY);
    }

    @Test
    @Transactional
    void getAllVehiclesByWantedByIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        vehicleRepository.saveAndFlush(vehicle);

        // Get all the vehicleList where wantedBy is less than or equal to DEFAULT_WANTED_BY
        defaultVehicleShouldBeFound("wantedBy.lessThanOrEqual=" + DEFAULT_WANTED_BY);

        // Get all the vehicleList where wantedBy is less than or equal to SMALLER_WANTED_BY
        defaultVehicleShouldNotBeFound("wantedBy.lessThanOrEqual=" + SMALLER_WANTED_BY);
    }

    @Test
    @Transactional
    void getAllVehiclesByWantedByIsLessThanSomething() throws Exception {
        // Initialize the database
        vehicleRepository.saveAndFlush(vehicle);

        // Get all the vehicleList where wantedBy is less than DEFAULT_WANTED_BY
        defaultVehicleShouldNotBeFound("wantedBy.lessThan=" + DEFAULT_WANTED_BY);

        // Get all the vehicleList where wantedBy is less than UPDATED_WANTED_BY
        defaultVehicleShouldBeFound("wantedBy.lessThan=" + UPDATED_WANTED_BY);
    }

    @Test
    @Transactional
    void getAllVehiclesByWantedByIsGreaterThanSomething() throws Exception {
        // Initialize the database
        vehicleRepository.saveAndFlush(vehicle);

        // Get all the vehicleList where wantedBy is greater than DEFAULT_WANTED_BY
        defaultVehicleShouldNotBeFound("wantedBy.greaterThan=" + DEFAULT_WANTED_BY);

        // Get all the vehicleList where wantedBy is greater than SMALLER_WANTED_BY
        defaultVehicleShouldBeFound("wantedBy.greaterThan=" + SMALLER_WANTED_BY);
    }

    @Test
    @Transactional
    void getAllVehiclesByWantedForIsEqualToSomething() throws Exception {
        // Initialize the database
        vehicleRepository.saveAndFlush(vehicle);

        // Get all the vehicleList where wantedFor equals to DEFAULT_WANTED_FOR
        defaultVehicleShouldBeFound("wantedFor.equals=" + DEFAULT_WANTED_FOR);

        // Get all the vehicleList where wantedFor equals to UPDATED_WANTED_FOR
        defaultVehicleShouldNotBeFound("wantedFor.equals=" + UPDATED_WANTED_FOR);
    }

    @Test
    @Transactional
    void getAllVehiclesByWantedForIsInShouldWork() throws Exception {
        // Initialize the database
        vehicleRepository.saveAndFlush(vehicle);

        // Get all the vehicleList where wantedFor in DEFAULT_WANTED_FOR or UPDATED_WANTED_FOR
        defaultVehicleShouldBeFound("wantedFor.in=" + DEFAULT_WANTED_FOR + "," + UPDATED_WANTED_FOR);

        // Get all the vehicleList where wantedFor equals to UPDATED_WANTED_FOR
        defaultVehicleShouldNotBeFound("wantedFor.in=" + UPDATED_WANTED_FOR);
    }

    @Test
    @Transactional
    void getAllVehiclesByWantedForIsNullOrNotNull() throws Exception {
        // Initialize the database
        vehicleRepository.saveAndFlush(vehicle);

        // Get all the vehicleList where wantedFor is not null
        defaultVehicleShouldBeFound("wantedFor.specified=true");

        // Get all the vehicleList where wantedFor is null
        defaultVehicleShouldNotBeFound("wantedFor.specified=false");
    }

    @Test
    @Transactional
    void getAllVehiclesByWantedForContainsSomething() throws Exception {
        // Initialize the database
        vehicleRepository.saveAndFlush(vehicle);

        // Get all the vehicleList where wantedFor contains DEFAULT_WANTED_FOR
        defaultVehicleShouldBeFound("wantedFor.contains=" + DEFAULT_WANTED_FOR);

        // Get all the vehicleList where wantedFor contains UPDATED_WANTED_FOR
        defaultVehicleShouldNotBeFound("wantedFor.contains=" + UPDATED_WANTED_FOR);
    }

    @Test
    @Transactional
    void getAllVehiclesByWantedForNotContainsSomething() throws Exception {
        // Initialize the database
        vehicleRepository.saveAndFlush(vehicle);

        // Get all the vehicleList where wantedFor does not contain DEFAULT_WANTED_FOR
        defaultVehicleShouldNotBeFound("wantedFor.doesNotContain=" + DEFAULT_WANTED_FOR);

        // Get all the vehicleList where wantedFor does not contain UPDATED_WANTED_FOR
        defaultVehicleShouldBeFound("wantedFor.doesNotContain=" + UPDATED_WANTED_FOR);
    }

    @Test
    @Transactional
    void getAllVehiclesByOfficeIsEqualToSomething() throws Exception {
        // Initialize the database
        vehicleRepository.saveAndFlush(vehicle);

        // Get all the vehicleList where office equals to DEFAULT_OFFICE
        defaultVehicleShouldBeFound("office.equals=" + DEFAULT_OFFICE);

        // Get all the vehicleList where office equals to UPDATED_OFFICE
        defaultVehicleShouldNotBeFound("office.equals=" + UPDATED_OFFICE);
    }

    @Test
    @Transactional
    void getAllVehiclesByOfficeIsInShouldWork() throws Exception {
        // Initialize the database
        vehicleRepository.saveAndFlush(vehicle);

        // Get all the vehicleList where office in DEFAULT_OFFICE or UPDATED_OFFICE
        defaultVehicleShouldBeFound("office.in=" + DEFAULT_OFFICE + "," + UPDATED_OFFICE);

        // Get all the vehicleList where office equals to UPDATED_OFFICE
        defaultVehicleShouldNotBeFound("office.in=" + UPDATED_OFFICE);
    }

    @Test
    @Transactional
    void getAllVehiclesByOfficeIsNullOrNotNull() throws Exception {
        // Initialize the database
        vehicleRepository.saveAndFlush(vehicle);

        // Get all the vehicleList where office is not null
        defaultVehicleShouldBeFound("office.specified=true");

        // Get all the vehicleList where office is null
        defaultVehicleShouldNotBeFound("office.specified=false");
    }

    @Test
    @Transactional
    void getAllVehiclesByOfficeIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        vehicleRepository.saveAndFlush(vehicle);

        // Get all the vehicleList where office is greater than or equal to DEFAULT_OFFICE
        defaultVehicleShouldBeFound("office.greaterThanOrEqual=" + DEFAULT_OFFICE);

        // Get all the vehicleList where office is greater than or equal to UPDATED_OFFICE
        defaultVehicleShouldNotBeFound("office.greaterThanOrEqual=" + UPDATED_OFFICE);
    }

    @Test
    @Transactional
    void getAllVehiclesByOfficeIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        vehicleRepository.saveAndFlush(vehicle);

        // Get all the vehicleList where office is less than or equal to DEFAULT_OFFICE
        defaultVehicleShouldBeFound("office.lessThanOrEqual=" + DEFAULT_OFFICE);

        // Get all the vehicleList where office is less than or equal to SMALLER_OFFICE
        defaultVehicleShouldNotBeFound("office.lessThanOrEqual=" + SMALLER_OFFICE);
    }

    @Test
    @Transactional
    void getAllVehiclesByOfficeIsLessThanSomething() throws Exception {
        // Initialize the database
        vehicleRepository.saveAndFlush(vehicle);

        // Get all the vehicleList where office is less than DEFAULT_OFFICE
        defaultVehicleShouldNotBeFound("office.lessThan=" + DEFAULT_OFFICE);

        // Get all the vehicleList where office is less than UPDATED_OFFICE
        defaultVehicleShouldBeFound("office.lessThan=" + UPDATED_OFFICE);
    }

    @Test
    @Transactional
    void getAllVehiclesByOfficeIsGreaterThanSomething() throws Exception {
        // Initialize the database
        vehicleRepository.saveAndFlush(vehicle);

        // Get all the vehicleList where office is greater than DEFAULT_OFFICE
        defaultVehicleShouldNotBeFound("office.greaterThan=" + DEFAULT_OFFICE);

        // Get all the vehicleList where office is greater than SMALLER_OFFICE
        defaultVehicleShouldBeFound("office.greaterThan=" + SMALLER_OFFICE);
    }

    @Test
    @Transactional
    void getAllVehiclesByStatusNameIsEqualToSomething() throws Exception {
        // Initialize the database
        vehicleRepository.saveAndFlush(vehicle);

        // Get all the vehicleList where statusName equals to DEFAULT_STATUS_NAME
        defaultVehicleShouldBeFound("statusName.equals=" + DEFAULT_STATUS_NAME);

        // Get all the vehicleList where statusName equals to UPDATED_STATUS_NAME
        defaultVehicleShouldNotBeFound("statusName.equals=" + UPDATED_STATUS_NAME);
    }

    @Test
    @Transactional
    void getAllVehiclesByStatusNameIsInShouldWork() throws Exception {
        // Initialize the database
        vehicleRepository.saveAndFlush(vehicle);

        // Get all the vehicleList where statusName in DEFAULT_STATUS_NAME or UPDATED_STATUS_NAME
        defaultVehicleShouldBeFound("statusName.in=" + DEFAULT_STATUS_NAME + "," + UPDATED_STATUS_NAME);

        // Get all the vehicleList where statusName equals to UPDATED_STATUS_NAME
        defaultVehicleShouldNotBeFound("statusName.in=" + UPDATED_STATUS_NAME);
    }

    @Test
    @Transactional
    void getAllVehiclesByStatusNameIsNullOrNotNull() throws Exception {
        // Initialize the database
        vehicleRepository.saveAndFlush(vehicle);

        // Get all the vehicleList where statusName is not null
        defaultVehicleShouldBeFound("statusName.specified=true");

        // Get all the vehicleList where statusName is null
        defaultVehicleShouldNotBeFound("statusName.specified=false");
    }

    @Test
    @Transactional
    void getAllVehiclesByStatusNameContainsSomething() throws Exception {
        // Initialize the database
        vehicleRepository.saveAndFlush(vehicle);

        // Get all the vehicleList where statusName contains DEFAULT_STATUS_NAME
        defaultVehicleShouldBeFound("statusName.contains=" + DEFAULT_STATUS_NAME);

        // Get all the vehicleList where statusName contains UPDATED_STATUS_NAME
        defaultVehicleShouldNotBeFound("statusName.contains=" + UPDATED_STATUS_NAME);
    }

    @Test
    @Transactional
    void getAllVehiclesByStatusNameNotContainsSomething() throws Exception {
        // Initialize the database
        vehicleRepository.saveAndFlush(vehicle);

        // Get all the vehicleList where statusName does not contain DEFAULT_STATUS_NAME
        defaultVehicleShouldNotBeFound("statusName.doesNotContain=" + DEFAULT_STATUS_NAME);

        // Get all the vehicleList where statusName does not contain UPDATED_STATUS_NAME
        defaultVehicleShouldBeFound("statusName.doesNotContain=" + UPDATED_STATUS_NAME);
    }

    @Test
    @Transactional
    void getAllVehiclesByStolenIsEqualToSomething() throws Exception {
        // Initialize the database
        vehicleRepository.saveAndFlush(vehicle);

        // Get all the vehicleList where stolen equals to DEFAULT_STOLEN
        defaultVehicleShouldBeFound("stolen.equals=" + DEFAULT_STOLEN);

        // Get all the vehicleList where stolen equals to UPDATED_STOLEN
        defaultVehicleShouldNotBeFound("stolen.equals=" + UPDATED_STOLEN);
    }

    @Test
    @Transactional
    void getAllVehiclesByStolenIsInShouldWork() throws Exception {
        // Initialize the database
        vehicleRepository.saveAndFlush(vehicle);

        // Get all the vehicleList where stolen in DEFAULT_STOLEN or UPDATED_STOLEN
        defaultVehicleShouldBeFound("stolen.in=" + DEFAULT_STOLEN + "," + UPDATED_STOLEN);

        // Get all the vehicleList where stolen equals to UPDATED_STOLEN
        defaultVehicleShouldNotBeFound("stolen.in=" + UPDATED_STOLEN);
    }

    @Test
    @Transactional
    void getAllVehiclesByStolenIsNullOrNotNull() throws Exception {
        // Initialize the database
        vehicleRepository.saveAndFlush(vehicle);

        // Get all the vehicleList where stolen is not null
        defaultVehicleShouldBeFound("stolen.specified=true");

        // Get all the vehicleList where stolen is null
        defaultVehicleShouldNotBeFound("stolen.specified=false");
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultVehicleShouldBeFound(String filter) throws Exception {
        restVehicleMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(vehicle.getId().intValue())))
            .andExpect(jsonPath("$.[*].govid").value(hasItem(DEFAULT_GOVID)))
            .andExpect(jsonPath("$.[*].plate").value(hasItem(DEFAULT_PLATE)))
            .andExpect(jsonPath("$.[*].rfid").value(hasItem(DEFAULT_RFID)))
            .andExpect(jsonPath("$.[*].driver").value(hasItem(DEFAULT_DRIVER.intValue())))
            .andExpect(jsonPath("$.[*].producer").value(hasItem(DEFAULT_PRODUCER)))
            .andExpect(jsonPath("$.[*].model").value(hasItem(DEFAULT_MODEL)))
            .andExpect(jsonPath("$.[*].year").value(hasItem(DEFAULT_YEAR.intValue())))
            .andExpect(jsonPath("$.[*].color").value(hasItem(DEFAULT_COLOR)))
            .andExpect(jsonPath("$.[*].decals").value(hasItem(DEFAULT_DECALS)))
            .andExpect(jsonPath("$.[*].spoilers").value(hasItem(DEFAULT_SPOILERS)))
            .andExpect(jsonPath("$.[*].glass").value(hasItem(DEFAULT_GLASS)))
            .andExpect(jsonPath("$.[*].chassisSerial").value(hasItem(DEFAULT_CHASSIS_SERIAL)))
            .andExpect(jsonPath("$.[*].motorSerial").value(hasItem(DEFAULT_MOTOR_SERIAL)))
            .andExpect(jsonPath("$.[*].vehicleLicenseSerial").value(hasItem(DEFAULT_VEHICLE_LICENSE_SERIAL)))
            .andExpect(jsonPath("$.[*].vehicleLicenseType").value(hasItem(DEFAULT_VEHICLE_LICENSE_TYPE.intValue())))
            .andExpect(jsonPath("$.[*].vehicleType").value(hasItem(DEFAULT_VEHICLE_TYPE.intValue())))
            .andExpect(jsonPath("$.[*].wanted").value(hasItem(DEFAULT_WANTED)))
            .andExpect(jsonPath("$.[*].licenseRevoked").value(hasItem(DEFAULT_LICENSE_REVOKED)))
            .andExpect(jsonPath("$.[*].licenseExpired").value(hasItem(DEFAULT_LICENSE_EXPIRED)))
            .andExpect(jsonPath("$.[*].jurisdiction").value(hasItem(DEFAULT_JURISDICTION.intValue())))
            .andExpect(jsonPath("$.[*].updated").value(hasItem(DEFAULT_UPDATED.toString())))
            .andExpect(jsonPath("$.[*].fuelType").value(hasItem(DEFAULT_FUEL_TYPE)))
            .andExpect(jsonPath("$.[*].motorCc").value(hasItem(DEFAULT_MOTOR_CC.intValue())))
            .andExpect(jsonPath("$.[*].licenseIssued").value(hasItem(DEFAULT_LICENSE_ISSUED.toString())))
            .andExpect(jsonPath("$.[*].licenseExpires").value(hasItem(DEFAULT_LICENSE_EXPIRES.toString())))
            .andExpect(jsonPath("$.[*].wtKg").value(hasItem(DEFAULT_WT_KG.intValue())))
            .andExpect(jsonPath("$.[*].passengers").value(hasItem(DEFAULT_PASSENGERS)))
            .andExpect(jsonPath("$.[*].tractorParts").value(hasItem(DEFAULT_TRACTOR_PARTS)))
            .andExpect(jsonPath("$.[*].extraSizePercent").value(hasItem(DEFAULT_EXTRA_SIZE_PERCENT)))
            .andExpect(jsonPath("$.[*].wtExtra").value(hasItem(DEFAULT_WT_EXTRA.intValue())))
            .andExpect(jsonPath("$.[*].bikeData").value(hasItem(DEFAULT_BIKE_DATA)))
            .andExpect(jsonPath("$.[*].wantedBy").value(hasItem(DEFAULT_WANTED_BY.intValue())))
            .andExpect(jsonPath("$.[*].wantedFor").value(hasItem(DEFAULT_WANTED_FOR)))
            .andExpect(jsonPath("$.[*].office").value(hasItem(DEFAULT_OFFICE.intValue())))
            .andExpect(jsonPath("$.[*].statusName").value(hasItem(DEFAULT_STATUS_NAME)))
            .andExpect(jsonPath("$.[*].stolen").value(hasItem(DEFAULT_STOLEN.toString())));

        // Check, that the count call also returns 1
        restVehicleMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultVehicleShouldNotBeFound(String filter) throws Exception {
        restVehicleMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restVehicleMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    void getNonExistingVehicle() throws Exception {
        // Get the vehicle
        restVehicleMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingVehicle() throws Exception {
        // Initialize the database
        vehicleRepository.saveAndFlush(vehicle);

        int databaseSizeBeforeUpdate = vehicleRepository.findAll().size();

        // Update the vehicle
        Vehicle updatedVehicle = vehicleRepository.findById(vehicle.getId()).get();
        // Disconnect from session so that the updates on updatedVehicle are not directly saved in db
        em.detach(updatedVehicle);
        updatedVehicle
            .govid(UPDATED_GOVID)
            .plate(UPDATED_PLATE)
            .rfid(UPDATED_RFID)
            .driver(UPDATED_DRIVER)
            .producer(UPDATED_PRODUCER)
            .model(UPDATED_MODEL)
            .year(UPDATED_YEAR)
            .color(UPDATED_COLOR)
            .decals(UPDATED_DECALS)
            .spoilers(UPDATED_SPOILERS)
            .glass(UPDATED_GLASS)
            .chassisSerial(UPDATED_CHASSIS_SERIAL)
            .motorSerial(UPDATED_MOTOR_SERIAL)
            .vehicleLicenseSerial(UPDATED_VEHICLE_LICENSE_SERIAL)
            .vehicleLicenseType(UPDATED_VEHICLE_LICENSE_TYPE)
            .vehicleType(UPDATED_VEHICLE_TYPE)
            .wanted(UPDATED_WANTED)
            .licenseRevoked(UPDATED_LICENSE_REVOKED)
            .licenseExpired(UPDATED_LICENSE_EXPIRED)
            .jurisdiction(UPDATED_JURISDICTION)
            .updated(UPDATED_UPDATED)
            .fuelType(UPDATED_FUEL_TYPE)
            .motorCc(UPDATED_MOTOR_CC)
            .licenseIssued(UPDATED_LICENSE_ISSUED)
            .licenseExpires(UPDATED_LICENSE_EXPIRES)
            .wtKg(UPDATED_WT_KG)
            .passengers(UPDATED_PASSENGERS)
            .tractorParts(UPDATED_TRACTOR_PARTS)
            .extraSizePercent(UPDATED_EXTRA_SIZE_PERCENT)
            .wtExtra(UPDATED_WT_EXTRA)
            .bikeData(UPDATED_BIKE_DATA)
            .wantedBy(UPDATED_WANTED_BY)
            .wantedFor(UPDATED_WANTED_FOR)
            .office(UPDATED_OFFICE)
            .statusName(UPDATED_STATUS_NAME)
            .stolen(UPDATED_STOLEN);
        VehicleDTO vehicleDTO = vehicleMapper.toDto(updatedVehicle);

        restVehicleMockMvc
            .perform(
                put(ENTITY_API_URL_ID, vehicleDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(vehicleDTO))
            )
            .andExpect(status().isOk());

        // Validate the Vehicle in the database
        List<Vehicle> vehicleList = vehicleRepository.findAll();
        assertThat(vehicleList).hasSize(databaseSizeBeforeUpdate);
        Vehicle testVehicle = vehicleList.get(vehicleList.size() - 1);
        assertThat(testVehicle.getGovid()).isEqualTo(UPDATED_GOVID);
        assertThat(testVehicle.getPlate()).isEqualTo(UPDATED_PLATE);
        assertThat(testVehicle.getRfid()).isEqualTo(UPDATED_RFID);
        assertThat(testVehicle.getDriver()).isEqualTo(UPDATED_DRIVER);
        assertThat(testVehicle.getProducer()).isEqualTo(UPDATED_PRODUCER);
        assertThat(testVehicle.getModel()).isEqualTo(UPDATED_MODEL);
        assertThat(testVehicle.getYear()).isEqualTo(UPDATED_YEAR);
        assertThat(testVehicle.getColor()).isEqualTo(UPDATED_COLOR);
        assertThat(testVehicle.getDecals()).isEqualTo(UPDATED_DECALS);
        assertThat(testVehicle.getSpoilers()).isEqualTo(UPDATED_SPOILERS);
        assertThat(testVehicle.getGlass()).isEqualTo(UPDATED_GLASS);
        assertThat(testVehicle.getChassisSerial()).isEqualTo(UPDATED_CHASSIS_SERIAL);
        assertThat(testVehicle.getMotorSerial()).isEqualTo(UPDATED_MOTOR_SERIAL);
        assertThat(testVehicle.getVehicleLicenseSerial()).isEqualTo(UPDATED_VEHICLE_LICENSE_SERIAL);
        assertThat(testVehicle.getVehicleLicenseType()).isEqualTo(UPDATED_VEHICLE_LICENSE_TYPE);
        assertThat(testVehicle.getVehicleType()).isEqualTo(UPDATED_VEHICLE_TYPE);
        assertThat(testVehicle.getWanted()).isEqualTo(UPDATED_WANTED);
        assertThat(testVehicle.getLicenseRevoked()).isEqualTo(UPDATED_LICENSE_REVOKED);
        assertThat(testVehicle.getLicenseExpired()).isEqualTo(UPDATED_LICENSE_EXPIRED);
        assertThat(testVehicle.getJurisdiction()).isEqualTo(UPDATED_JURISDICTION);
        assertThat(testVehicle.getUpdated()).isEqualTo(UPDATED_UPDATED);
        assertThat(testVehicle.getFuelType()).isEqualTo(UPDATED_FUEL_TYPE);
        assertThat(testVehicle.getMotorCc()).isEqualTo(UPDATED_MOTOR_CC);
        assertThat(testVehicle.getLicenseIssued()).isEqualTo(UPDATED_LICENSE_ISSUED);
        assertThat(testVehicle.getLicenseExpires()).isEqualTo(UPDATED_LICENSE_EXPIRES);
        assertThat(testVehicle.getWtKg()).isEqualTo(UPDATED_WT_KG);
        assertThat(testVehicle.getPassengers()).isEqualTo(UPDATED_PASSENGERS);
        assertThat(testVehicle.getTractorParts()).isEqualTo(UPDATED_TRACTOR_PARTS);
        assertThat(testVehicle.getExtraSizePercent()).isEqualTo(UPDATED_EXTRA_SIZE_PERCENT);
        assertThat(testVehicle.getWtExtra()).isEqualTo(UPDATED_WT_EXTRA);
        assertThat(testVehicle.getBikeData()).isEqualTo(UPDATED_BIKE_DATA);
        assertThat(testVehicle.getWantedBy()).isEqualTo(UPDATED_WANTED_BY);
        assertThat(testVehicle.getWantedFor()).isEqualTo(UPDATED_WANTED_FOR);
        assertThat(testVehicle.getOffice()).isEqualTo(UPDATED_OFFICE);
        assertThat(testVehicle.getStatusName()).isEqualTo(UPDATED_STATUS_NAME);
        assertThat(testVehicle.getStolen()).isEqualTo(UPDATED_STOLEN);
    }

    @Test
    @Transactional
    void putNonExistingVehicle() throws Exception {
        int databaseSizeBeforeUpdate = vehicleRepository.findAll().size();
        vehicle.setId(count.incrementAndGet());

        // Create the Vehicle
        VehicleDTO vehicleDTO = vehicleMapper.toDto(vehicle);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restVehicleMockMvc
            .perform(
                put(ENTITY_API_URL_ID, vehicleDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(vehicleDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Vehicle in the database
        List<Vehicle> vehicleList = vehicleRepository.findAll();
        assertThat(vehicleList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchVehicle() throws Exception {
        int databaseSizeBeforeUpdate = vehicleRepository.findAll().size();
        vehicle.setId(count.incrementAndGet());

        // Create the Vehicle
        VehicleDTO vehicleDTO = vehicleMapper.toDto(vehicle);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restVehicleMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(vehicleDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Vehicle in the database
        List<Vehicle> vehicleList = vehicleRepository.findAll();
        assertThat(vehicleList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamVehicle() throws Exception {
        int databaseSizeBeforeUpdate = vehicleRepository.findAll().size();
        vehicle.setId(count.incrementAndGet());

        // Create the Vehicle
        VehicleDTO vehicleDTO = vehicleMapper.toDto(vehicle);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restVehicleMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(vehicleDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Vehicle in the database
        List<Vehicle> vehicleList = vehicleRepository.findAll();
        assertThat(vehicleList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateVehicleWithPatch() throws Exception {
        // Initialize the database
        vehicleRepository.saveAndFlush(vehicle);

        int databaseSizeBeforeUpdate = vehicleRepository.findAll().size();

        // Update the vehicle using partial update
        Vehicle partialUpdatedVehicle = new Vehicle();
        partialUpdatedVehicle.setId(vehicle.getId());

        partialUpdatedVehicle
            .driver(UPDATED_DRIVER)
            .producer(UPDATED_PRODUCER)
            .year(UPDATED_YEAR)
            .color(UPDATED_COLOR)
            .spoilers(UPDATED_SPOILERS)
            .chassisSerial(UPDATED_CHASSIS_SERIAL)
            .motorSerial(UPDATED_MOTOR_SERIAL)
            .wanted(UPDATED_WANTED)
            .updated(UPDATED_UPDATED)
            .fuelType(UPDATED_FUEL_TYPE)
            .motorCc(UPDATED_MOTOR_CC)
            .licenseIssued(UPDATED_LICENSE_ISSUED)
            .licenseExpires(UPDATED_LICENSE_EXPIRES)
            .extraSizePercent(UPDATED_EXTRA_SIZE_PERCENT)
            .wantedBy(UPDATED_WANTED_BY);

        restVehicleMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedVehicle.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedVehicle))
            )
            .andExpect(status().isOk());

        // Validate the Vehicle in the database
        List<Vehicle> vehicleList = vehicleRepository.findAll();
        assertThat(vehicleList).hasSize(databaseSizeBeforeUpdate);
        Vehicle testVehicle = vehicleList.get(vehicleList.size() - 1);
        assertThat(testVehicle.getGovid()).isEqualTo(DEFAULT_GOVID);
        assertThat(testVehicle.getPlate()).isEqualTo(DEFAULT_PLATE);
        assertThat(testVehicle.getRfid()).isEqualTo(DEFAULT_RFID);
        assertThat(testVehicle.getDriver()).isEqualTo(UPDATED_DRIVER);
        assertThat(testVehicle.getProducer()).isEqualTo(UPDATED_PRODUCER);
        assertThat(testVehicle.getModel()).isEqualTo(DEFAULT_MODEL);
        assertThat(testVehicle.getYear()).isEqualTo(UPDATED_YEAR);
        assertThat(testVehicle.getColor()).isEqualTo(UPDATED_COLOR);
        assertThat(testVehicle.getDecals()).isEqualTo(DEFAULT_DECALS);
        assertThat(testVehicle.getSpoilers()).isEqualTo(UPDATED_SPOILERS);
        assertThat(testVehicle.getGlass()).isEqualTo(DEFAULT_GLASS);
        assertThat(testVehicle.getChassisSerial()).isEqualTo(UPDATED_CHASSIS_SERIAL);
        assertThat(testVehicle.getMotorSerial()).isEqualTo(UPDATED_MOTOR_SERIAL);
        assertThat(testVehicle.getVehicleLicenseSerial()).isEqualTo(DEFAULT_VEHICLE_LICENSE_SERIAL);
        assertThat(testVehicle.getVehicleLicenseType()).isEqualTo(DEFAULT_VEHICLE_LICENSE_TYPE);
        assertThat(testVehicle.getVehicleType()).isEqualTo(DEFAULT_VEHICLE_TYPE);
        assertThat(testVehicle.getWanted()).isEqualTo(UPDATED_WANTED);
        assertThat(testVehicle.getLicenseRevoked()).isEqualTo(DEFAULT_LICENSE_REVOKED);
        assertThat(testVehicle.getLicenseExpired()).isEqualTo(DEFAULT_LICENSE_EXPIRED);
        assertThat(testVehicle.getJurisdiction()).isEqualTo(DEFAULT_JURISDICTION);
        assertThat(testVehicle.getUpdated()).isEqualTo(UPDATED_UPDATED);
        assertThat(testVehicle.getFuelType()).isEqualTo(UPDATED_FUEL_TYPE);
        assertThat(testVehicle.getMotorCc()).isEqualTo(UPDATED_MOTOR_CC);
        assertThat(testVehicle.getLicenseIssued()).isEqualTo(UPDATED_LICENSE_ISSUED);
        assertThat(testVehicle.getLicenseExpires()).isEqualTo(UPDATED_LICENSE_EXPIRES);
        assertThat(testVehicle.getWtKg()).isEqualTo(DEFAULT_WT_KG);
        assertThat(testVehicle.getPassengers()).isEqualTo(DEFAULT_PASSENGERS);
        assertThat(testVehicle.getTractorParts()).isEqualTo(DEFAULT_TRACTOR_PARTS);
        assertThat(testVehicle.getExtraSizePercent()).isEqualTo(UPDATED_EXTRA_SIZE_PERCENT);
        assertThat(testVehicle.getWtExtra()).isEqualTo(DEFAULT_WT_EXTRA);
        assertThat(testVehicle.getBikeData()).isEqualTo(DEFAULT_BIKE_DATA);
        assertThat(testVehicle.getWantedBy()).isEqualTo(UPDATED_WANTED_BY);
        assertThat(testVehicle.getWantedFor()).isEqualTo(DEFAULT_WANTED_FOR);
        assertThat(testVehicle.getOffice()).isEqualTo(DEFAULT_OFFICE);
        assertThat(testVehicle.getStatusName()).isEqualTo(DEFAULT_STATUS_NAME);
        assertThat(testVehicle.getStolen()).isEqualTo(DEFAULT_STOLEN);
    }

    @Test
    @Transactional
    void fullUpdateVehicleWithPatch() throws Exception {
        // Initialize the database
        vehicleRepository.saveAndFlush(vehicle);

        int databaseSizeBeforeUpdate = vehicleRepository.findAll().size();

        // Update the vehicle using partial update
        Vehicle partialUpdatedVehicle = new Vehicle();
        partialUpdatedVehicle.setId(vehicle.getId());

        partialUpdatedVehicle
            .govid(UPDATED_GOVID)
            .plate(UPDATED_PLATE)
            .rfid(UPDATED_RFID)
            .driver(UPDATED_DRIVER)
            .producer(UPDATED_PRODUCER)
            .model(UPDATED_MODEL)
            .year(UPDATED_YEAR)
            .color(UPDATED_COLOR)
            .decals(UPDATED_DECALS)
            .spoilers(UPDATED_SPOILERS)
            .glass(UPDATED_GLASS)
            .chassisSerial(UPDATED_CHASSIS_SERIAL)
            .motorSerial(UPDATED_MOTOR_SERIAL)
            .vehicleLicenseSerial(UPDATED_VEHICLE_LICENSE_SERIAL)
            .vehicleLicenseType(UPDATED_VEHICLE_LICENSE_TYPE)
            .vehicleType(UPDATED_VEHICLE_TYPE)
            .wanted(UPDATED_WANTED)
            .licenseRevoked(UPDATED_LICENSE_REVOKED)
            .licenseExpired(UPDATED_LICENSE_EXPIRED)
            .jurisdiction(UPDATED_JURISDICTION)
            .updated(UPDATED_UPDATED)
            .fuelType(UPDATED_FUEL_TYPE)
            .motorCc(UPDATED_MOTOR_CC)
            .licenseIssued(UPDATED_LICENSE_ISSUED)
            .licenseExpires(UPDATED_LICENSE_EXPIRES)
            .wtKg(UPDATED_WT_KG)
            .passengers(UPDATED_PASSENGERS)
            .tractorParts(UPDATED_TRACTOR_PARTS)
            .extraSizePercent(UPDATED_EXTRA_SIZE_PERCENT)
            .wtExtra(UPDATED_WT_EXTRA)
            .bikeData(UPDATED_BIKE_DATA)
            .wantedBy(UPDATED_WANTED_BY)
            .wantedFor(UPDATED_WANTED_FOR)
            .office(UPDATED_OFFICE)
            .statusName(UPDATED_STATUS_NAME)
            .stolen(UPDATED_STOLEN);

        restVehicleMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedVehicle.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedVehicle))
            )
            .andExpect(status().isOk());

        // Validate the Vehicle in the database
        List<Vehicle> vehicleList = vehicleRepository.findAll();
        assertThat(vehicleList).hasSize(databaseSizeBeforeUpdate);
        Vehicle testVehicle = vehicleList.get(vehicleList.size() - 1);
        assertThat(testVehicle.getGovid()).isEqualTo(UPDATED_GOVID);
        assertThat(testVehicle.getPlate()).isEqualTo(UPDATED_PLATE);
        assertThat(testVehicle.getRfid()).isEqualTo(UPDATED_RFID);
        assertThat(testVehicle.getDriver()).isEqualTo(UPDATED_DRIVER);
        assertThat(testVehicle.getProducer()).isEqualTo(UPDATED_PRODUCER);
        assertThat(testVehicle.getModel()).isEqualTo(UPDATED_MODEL);
        assertThat(testVehicle.getYear()).isEqualTo(UPDATED_YEAR);
        assertThat(testVehicle.getColor()).isEqualTo(UPDATED_COLOR);
        assertThat(testVehicle.getDecals()).isEqualTo(UPDATED_DECALS);
        assertThat(testVehicle.getSpoilers()).isEqualTo(UPDATED_SPOILERS);
        assertThat(testVehicle.getGlass()).isEqualTo(UPDATED_GLASS);
        assertThat(testVehicle.getChassisSerial()).isEqualTo(UPDATED_CHASSIS_SERIAL);
        assertThat(testVehicle.getMotorSerial()).isEqualTo(UPDATED_MOTOR_SERIAL);
        assertThat(testVehicle.getVehicleLicenseSerial()).isEqualTo(UPDATED_VEHICLE_LICENSE_SERIAL);
        assertThat(testVehicle.getVehicleLicenseType()).isEqualTo(UPDATED_VEHICLE_LICENSE_TYPE);
        assertThat(testVehicle.getVehicleType()).isEqualTo(UPDATED_VEHICLE_TYPE);
        assertThat(testVehicle.getWanted()).isEqualTo(UPDATED_WANTED);
        assertThat(testVehicle.getLicenseRevoked()).isEqualTo(UPDATED_LICENSE_REVOKED);
        assertThat(testVehicle.getLicenseExpired()).isEqualTo(UPDATED_LICENSE_EXPIRED);
        assertThat(testVehicle.getJurisdiction()).isEqualTo(UPDATED_JURISDICTION);
        assertThat(testVehicle.getUpdated()).isEqualTo(UPDATED_UPDATED);
        assertThat(testVehicle.getFuelType()).isEqualTo(UPDATED_FUEL_TYPE);
        assertThat(testVehicle.getMotorCc()).isEqualTo(UPDATED_MOTOR_CC);
        assertThat(testVehicle.getLicenseIssued()).isEqualTo(UPDATED_LICENSE_ISSUED);
        assertThat(testVehicle.getLicenseExpires()).isEqualTo(UPDATED_LICENSE_EXPIRES);
        assertThat(testVehicle.getWtKg()).isEqualTo(UPDATED_WT_KG);
        assertThat(testVehicle.getPassengers()).isEqualTo(UPDATED_PASSENGERS);
        assertThat(testVehicle.getTractorParts()).isEqualTo(UPDATED_TRACTOR_PARTS);
        assertThat(testVehicle.getExtraSizePercent()).isEqualTo(UPDATED_EXTRA_SIZE_PERCENT);
        assertThat(testVehicle.getWtExtra()).isEqualTo(UPDATED_WT_EXTRA);
        assertThat(testVehicle.getBikeData()).isEqualTo(UPDATED_BIKE_DATA);
        assertThat(testVehicle.getWantedBy()).isEqualTo(UPDATED_WANTED_BY);
        assertThat(testVehicle.getWantedFor()).isEqualTo(UPDATED_WANTED_FOR);
        assertThat(testVehicle.getOffice()).isEqualTo(UPDATED_OFFICE);
        assertThat(testVehicle.getStatusName()).isEqualTo(UPDATED_STATUS_NAME);
        assertThat(testVehicle.getStolen()).isEqualTo(UPDATED_STOLEN);
    }

    @Test
    @Transactional
    void patchNonExistingVehicle() throws Exception {
        int databaseSizeBeforeUpdate = vehicleRepository.findAll().size();
        vehicle.setId(count.incrementAndGet());

        // Create the Vehicle
        VehicleDTO vehicleDTO = vehicleMapper.toDto(vehicle);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restVehicleMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, vehicleDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(vehicleDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Vehicle in the database
        List<Vehicle> vehicleList = vehicleRepository.findAll();
        assertThat(vehicleList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchVehicle() throws Exception {
        int databaseSizeBeforeUpdate = vehicleRepository.findAll().size();
        vehicle.setId(count.incrementAndGet());

        // Create the Vehicle
        VehicleDTO vehicleDTO = vehicleMapper.toDto(vehicle);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restVehicleMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(vehicleDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Vehicle in the database
        List<Vehicle> vehicleList = vehicleRepository.findAll();
        assertThat(vehicleList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamVehicle() throws Exception {
        int databaseSizeBeforeUpdate = vehicleRepository.findAll().size();
        vehicle.setId(count.incrementAndGet());

        // Create the Vehicle
        VehicleDTO vehicleDTO = vehicleMapper.toDto(vehicle);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restVehicleMockMvc
            .perform(
                patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(vehicleDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the Vehicle in the database
        List<Vehicle> vehicleList = vehicleRepository.findAll();
        assertThat(vehicleList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteVehicle() throws Exception {
        // Initialize the database
        vehicleRepository.saveAndFlush(vehicle);

        int databaseSizeBeforeDelete = vehicleRepository.findAll().size();

        // Delete the vehicle
        restVehicleMockMvc
            .perform(delete(ENTITY_API_URL_ID, vehicle.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Vehicle> vehicleList = vehicleRepository.findAll();
        assertThat(vehicleList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
