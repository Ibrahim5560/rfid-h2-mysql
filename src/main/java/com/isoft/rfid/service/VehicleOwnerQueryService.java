package com.isoft.rfid.service;

import com.isoft.rfid.domain.*; // for static metamodels
import com.isoft.rfid.domain.VehicleOwner;
import com.isoft.rfid.repository.VehicleOwnerRepository;
import com.isoft.rfid.service.criteria.VehicleOwnerCriteria;
import com.isoft.rfid.service.dto.VehicleOwnerDTO;
import com.isoft.rfid.service.mapper.VehicleOwnerMapper;
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
 * Service for executing complex queries for {@link VehicleOwner} entities in the database.
 * The main input is a {@link VehicleOwnerCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link VehicleOwnerDTO} or a {@link Page} of {@link VehicleOwnerDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class VehicleOwnerQueryService extends QueryService<VehicleOwner> {

    private final Logger log = LoggerFactory.getLogger(VehicleOwnerQueryService.class);

    private final VehicleOwnerRepository vehicleOwnerRepository;

    private final VehicleOwnerMapper vehicleOwnerMapper;

    public VehicleOwnerQueryService(VehicleOwnerRepository vehicleOwnerRepository, VehicleOwnerMapper vehicleOwnerMapper) {
        this.vehicleOwnerRepository = vehicleOwnerRepository;
        this.vehicleOwnerMapper = vehicleOwnerMapper;
    }

    /**
     * Return a {@link List} of {@link VehicleOwnerDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<VehicleOwnerDTO> findByCriteria(VehicleOwnerCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<VehicleOwner> specification = createSpecification(criteria);
        return vehicleOwnerMapper.toDto(vehicleOwnerRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link VehicleOwnerDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<VehicleOwnerDTO> findByCriteria(VehicleOwnerCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<VehicleOwner> specification = createSpecification(criteria);
        return vehicleOwnerRepository.findAll(specification, page).map(vehicleOwnerMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(VehicleOwnerCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<VehicleOwner> specification = createSpecification(criteria);
        return vehicleOwnerRepository.count(specification);
    }

    /**
     * Function to convert {@link VehicleOwnerCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<VehicleOwner> createSpecification(VehicleOwnerCriteria criteria) {
        Specification<VehicleOwner> specification = Specification.where(null);
        if (criteria != null) {
            // This has to be called first, because the distinct method returns null
            if (criteria.getDistinct() != null) {
                specification = specification.and(distinct(criteria.getDistinct()));
            }
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), VehicleOwner_.id));
            }
            if (criteria.getCitizenSerial() != null) {
                specification = specification.and(buildStringSpecification(criteria.getCitizenSerial(), VehicleOwner_.citizenSerial));
            }
            if (criteria.getPassportSerial() != null) {
                specification = specification.and(buildStringSpecification(criteria.getPassportSerial(), VehicleOwner_.passportSerial));
            }
            if (criteria.getEntitySerial() != null) {
                specification = specification.and(buildStringSpecification(criteria.getEntitySerial(), VehicleOwner_.entitySerial));
            }
            if (criteria.getFullName() != null) {
                specification = specification.and(buildStringSpecification(criteria.getFullName(), VehicleOwner_.fullName));
            }
            if (criteria.getFirstName() != null) {
                specification = specification.and(buildStringSpecification(criteria.getFirstName(), VehicleOwner_.firstName));
            }
            if (criteria.getMiddleName() != null) {
                specification = specification.and(buildStringSpecification(criteria.getMiddleName(), VehicleOwner_.middleName));
            }
            if (criteria.getGrandName() != null) {
                specification = specification.and(buildStringSpecification(criteria.getGrandName(), VehicleOwner_.grandName));
            }
            if (criteria.getSurname() != null) {
                specification = specification.and(buildStringSpecification(criteria.getSurname(), VehicleOwner_.surname));
            }
            if (criteria.getAka() != null) {
                specification = specification.and(buildStringSpecification(criteria.getAka(), VehicleOwner_.aka));
            }
            if (criteria.getDob() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getDob(), VehicleOwner_.dob));
            }
            if (criteria.getGender() != null) {
                specification = specification.and(buildStringSpecification(criteria.getGender(), VehicleOwner_.gender));
            }
            if (criteria.getDriverLicenseSerial() != null) {
                specification =
                    specification.and(buildStringSpecification(criteria.getDriverLicenseSerial(), VehicleOwner_.driverLicenseSerial));
            }
            if (criteria.getDriverLicenseType() != null) {
                specification =
                    specification.and(buildRangeSpecification(criteria.getDriverLicenseType(), VehicleOwner_.driverLicenseType));
            }
            if (criteria.getJurisdiction() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getJurisdiction(), VehicleOwner_.jurisdiction));
            }
            if (criteria.getUpdated() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getUpdated(), VehicleOwner_.updated));
            }
            if (criteria.getAccount() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getAccount(), VehicleOwner_.account));
            }
        }
        return specification;
    }
}
