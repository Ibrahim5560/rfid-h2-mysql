package com.isoft.rfid.service;

import com.isoft.rfid.domain.*; // for static metamodels
import com.isoft.rfid.domain.TollPackage;
import com.isoft.rfid.repository.TollPackageRepository;
import com.isoft.rfid.service.criteria.TollPackageCriteria;
import com.isoft.rfid.service.dto.TollPackageDTO;
import com.isoft.rfid.service.mapper.TollPackageMapper;
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
 * Service for executing complex queries for {@link TollPackage} entities in the database.
 * The main input is a {@link TollPackageCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link TollPackageDTO} or a {@link Page} of {@link TollPackageDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class TollPackageQueryService extends QueryService<TollPackage> {

    private final Logger log = LoggerFactory.getLogger(TollPackageQueryService.class);

    private final TollPackageRepository tollPackageRepository;

    private final TollPackageMapper tollPackageMapper;

    public TollPackageQueryService(TollPackageRepository tollPackageRepository, TollPackageMapper tollPackageMapper) {
        this.tollPackageRepository = tollPackageRepository;
        this.tollPackageMapper = tollPackageMapper;
    }

    /**
     * Return a {@link List} of {@link TollPackageDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<TollPackageDTO> findByCriteria(TollPackageCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<TollPackage> specification = createSpecification(criteria);
        return tollPackageMapper.toDto(tollPackageRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link TollPackageDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<TollPackageDTO> findByCriteria(TollPackageCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<TollPackage> specification = createSpecification(criteria);
        return tollPackageRepository.findAll(specification, page).map(tollPackageMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(TollPackageCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<TollPackage> specification = createSpecification(criteria);
        return tollPackageRepository.count(specification);
    }

    /**
     * Function to convert {@link TollPackageCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<TollPackage> createSpecification(TollPackageCriteria criteria) {
        Specification<TollPackage> specification = Specification.where(null);
        if (criteria != null) {
            // This has to be called first, because the distinct method returns null
            if (criteria.getDistinct() != null) {
                specification = specification.and(distinct(criteria.getDistinct()));
            }
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), TollPackage_.id));
            }
            if (criteria.getName() != null) {
                specification = specification.and(buildStringSpecification(criteria.getName(), TollPackage_.name));
            }
            if (criteria.getEngName() != null) {
                specification = specification.and(buildStringSpecification(criteria.getEngName(), TollPackage_.engName));
            }
            if (criteria.getCode() != null) {
                specification = specification.and(buildStringSpecification(criteria.getCode(), TollPackage_.code));
            }
            if (criteria.getActive() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getActive(), TollPackage_.active));
            }
            if (criteria.getUpdated() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getUpdated(), TollPackage_.updated));
            }
            if (criteria.getDurationInDays() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getDurationInDays(), TollPackage_.durationInDays));
            }
            if (criteria.getGantry() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getGantry(), TollPackage_.gantry));
            }
            if (criteria.getVehicleLicenseType() != null) {
                specification =
                    specification.and(buildRangeSpecification(criteria.getVehicleLicenseType(), TollPackage_.vehicleLicenseType));
            }
            if (criteria.getPassageTimes() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getPassageTimes(), TollPackage_.passageTimes));
            }
            if (criteria.getTotalFees() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getTotalFees(), TollPackage_.totalFees));
            }
            if (criteria.getTollSubscriptionId() != null) {
                specification =
                    specification.and(
                        buildSpecification(
                            criteria.getTollSubscriptionId(),
                            root -> root.join(TollPackage_.tollSubscription, JoinType.LEFT).get(TollSubscription_.id)
                        )
                    );
            }
        }
        return specification;
    }
}
