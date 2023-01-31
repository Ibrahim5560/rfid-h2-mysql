package com.isoft.rfid.service;

import com.isoft.rfid.domain.*; // for static metamodels
import com.isoft.rfid.domain.Vehicle;
import com.isoft.rfid.repository.VehicleRepository;
import com.isoft.rfid.service.criteria.VehicleCriteria;
import com.isoft.rfid.service.dto.VehicleDTO;
import com.isoft.rfid.service.mapper.VehicleMapper;
import java.util.List;
import javax.persistence.criteria.JoinType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tech.jhipster.service.QueryService;

/**
 * Service for executing complex queries for {@link Vehicle} entities in the database.
 * The main input is a {@link VehicleCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link VehicleDTO} or a {@link Page} of {@link VehicleDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class VehicleQueryService extends QueryService<Vehicle> {

    private final Logger log = LoggerFactory.getLogger(VehicleQueryService.class);

    private final VehicleRepository vehicleRepository;

    private final VehicleMapper vehicleMapper;

    public VehicleQueryService(VehicleRepository vehicleRepository, VehicleMapper vehicleMapper) {
        this.vehicleRepository = vehicleRepository;
        this.vehicleMapper = vehicleMapper;
    }

    /**
     * Return a {@link List} of {@link VehicleDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<VehicleDTO> findByCriteria(VehicleCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<Vehicle> specification = createSpecification(criteria);
        return vehicleMapper.toDto(vehicleRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link VehicleDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<VehicleDTO> findByCriteria(VehicleCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<Vehicle> specification = createSpecification(criteria);
        return vehicleRepository.findAll(specification, page).map(vehicleMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(VehicleCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<Vehicle> specification = createSpecification(criteria);
        return vehicleRepository.count(specification);
    }

    /**
     * Function to convert {@link VehicleCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<Vehicle> createSpecification(VehicleCriteria criteria) {
        Specification<Vehicle> specification = Specification.where(null);
        if (criteria != null) {
            // This has to be called first, because the distinct method returns null
            if (criteria.getDistinct() != null) {
                specification = specification.and(distinct(criteria.getDistinct()));
            }
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), Vehicle_.id));
            }
            if (criteria.getGovid() != null) {
                specification = specification.and(buildStringSpecification(criteria.getGovid(), Vehicle_.govid));
            }
            if (criteria.getPlate() != null) {
                specification = specification.and(buildStringSpecification(criteria.getPlate(), Vehicle_.plate));
            }
            if (criteria.getRfid() != null) {
                specification = specification.and(buildStringSpecification(criteria.getRfid(), Vehicle_.rfid));
            }
            if (criteria.getDriver() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getDriver(), Vehicle_.driver));
            }
            if (criteria.getProducer() != null) {
                specification = specification.and(buildStringSpecification(criteria.getProducer(), Vehicle_.producer));
            }
            if (criteria.getModel() != null) {
                specification = specification.and(buildStringSpecification(criteria.getModel(), Vehicle_.model));
            }
            if (criteria.getYear() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getYear(), Vehicle_.year));
            }
            if (criteria.getColor() != null) {
                specification = specification.and(buildStringSpecification(criteria.getColor(), Vehicle_.color));
            }
            if (criteria.getDecals() != null) {
                specification = specification.and(buildStringSpecification(criteria.getDecals(), Vehicle_.decals));
            }
            if (criteria.getSpoilers() != null) {
                specification = specification.and(buildStringSpecification(criteria.getSpoilers(), Vehicle_.spoilers));
            }
            if (criteria.getGlass() != null) {
                specification = specification.and(buildStringSpecification(criteria.getGlass(), Vehicle_.glass));
            }
            if (criteria.getChassisSerial() != null) {
                specification = specification.and(buildStringSpecification(criteria.getChassisSerial(), Vehicle_.chassisSerial));
            }
            if (criteria.getMotorSerial() != null) {
                specification = specification.and(buildStringSpecification(criteria.getMotorSerial(), Vehicle_.motorSerial));
            }
            if (criteria.getVehicleLicenseSerial() != null) {
                specification =
                    specification.and(buildStringSpecification(criteria.getVehicleLicenseSerial(), Vehicle_.vehicleLicenseSerial));
            }
            if (criteria.getVehicleLicenseType() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getVehicleLicenseType(), Vehicle_.vehicleLicenseType));
            }
            if (criteria.getVehicleType() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getVehicleType(), Vehicle_.vehicleType));
            }
            if (criteria.getWanted() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getWanted(), Vehicle_.wanted));
            }
            if (criteria.getLicenseRevoked() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getLicenseRevoked(), Vehicle_.licenseRevoked));
            }
            if (criteria.getLicenseExpired() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getLicenseExpired(), Vehicle_.licenseExpired));
            }
            if (criteria.getJurisdiction() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getJurisdiction(), Vehicle_.jurisdiction));
            }
            if (criteria.getUpdated() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getUpdated(), Vehicle_.updated));
            }
            if (criteria.getFuelType() != null) {
                specification = specification.and(buildStringSpecification(criteria.getFuelType(), Vehicle_.fuelType));
            }
            if (criteria.getMotorCc() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getMotorCc(), Vehicle_.motorCc));
            }
            if (criteria.getLicenseIssued() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getLicenseIssued(), Vehicle_.licenseIssued));
            }
            if (criteria.getLicenseExpires() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getLicenseExpires(), Vehicle_.licenseExpires));
            }
            if (criteria.getWtKg() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getWtKg(), Vehicle_.wtKg));
            }
            if (criteria.getPassengers() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getPassengers(), Vehicle_.passengers));
            }
            if (criteria.getTractorParts() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getTractorParts(), Vehicle_.tractorParts));
            }
            if (criteria.getExtraSizePercent() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getExtraSizePercent(), Vehicle_.extraSizePercent));
            }
            if (criteria.getWtExtra() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getWtExtra(), Vehicle_.wtExtra));
            }
            if (criteria.getBikeData() != null) {
                specification = specification.and(buildStringSpecification(criteria.getBikeData(), Vehicle_.bikeData));
            }
            if (criteria.getWantedBy() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getWantedBy(), Vehicle_.wantedBy));
            }
            if (criteria.getWantedFor() != null) {
                specification = specification.and(buildStringSpecification(criteria.getWantedFor(), Vehicle_.wantedFor));
            }
            if (criteria.getOffice() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getOffice(), Vehicle_.office));
            }
            if (criteria.getStatusName() != null) {
                specification = specification.and(buildStringSpecification(criteria.getStatusName(), Vehicle_.statusName));
            }
            if (criteria.getStolen() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getStolen(), Vehicle_.stolen));
            }
            if (criteria.getGantryToll() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getGantryToll(), Vehicle_.gantryToll));
            }
        }
        return specification;
    }
}
