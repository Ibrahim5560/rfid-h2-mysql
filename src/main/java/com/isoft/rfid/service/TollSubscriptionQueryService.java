package com.isoft.rfid.service;

import com.isoft.rfid.domain.*; // for static metamodels
import com.isoft.rfid.domain.TollSubscription;
import com.isoft.rfid.repository.TollSubscriptionRepository;
import com.isoft.rfid.service.criteria.TollSubscriptionCriteria;
import com.isoft.rfid.service.dto.TollSubscriptionDTO;
import com.isoft.rfid.service.mapper.TollSubscriptionMapper;
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
 * Service for executing complex queries for {@link TollSubscription} entities in the database.
 * The main input is a {@link TollSubscriptionCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link TollSubscriptionDTO} or a {@link Page} of {@link TollSubscriptionDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class TollSubscriptionQueryService extends QueryService<TollSubscription> {

    private final Logger log = LoggerFactory.getLogger(TollSubscriptionQueryService.class);

    private final TollSubscriptionRepository tollSubscriptionRepository;

    private final TollSubscriptionMapper tollSubscriptionMapper;

    public TollSubscriptionQueryService(
        TollSubscriptionRepository tollSubscriptionRepository,
        TollSubscriptionMapper tollSubscriptionMapper
    ) {
        this.tollSubscriptionRepository = tollSubscriptionRepository;
        this.tollSubscriptionMapper = tollSubscriptionMapper;
    }

    /**
     * Return a {@link List} of {@link TollSubscriptionDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<TollSubscriptionDTO> findByCriteria(TollSubscriptionCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<TollSubscription> specification = createSpecification(criteria);
        return tollSubscriptionMapper.toDto(tollSubscriptionRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link TollSubscriptionDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<TollSubscriptionDTO> findByCriteria(TollSubscriptionCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<TollSubscription> specification = createSpecification(criteria);
        return tollSubscriptionRepository.findAll(specification, page).map(tollSubscriptionMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(TollSubscriptionCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<TollSubscription> specification = createSpecification(criteria);
        return tollSubscriptionRepository.count(specification);
    }

    /**
     * Function to convert {@link TollSubscriptionCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<TollSubscription> createSpecification(TollSubscriptionCriteria criteria) {
        Specification<TollSubscription> specification = Specification.where(null);
        if (criteria != null) {
            // This has to be called first, because the distinct method returns null
            if (criteria.getDistinct() != null) {
                specification = specification.and(distinct(criteria.getDistinct()));
            }
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), TollSubscription_.id));
            }
            if (criteria.getDateTimeFrom() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getDateTimeFrom(), TollSubscription_.dateTimeFrom));
            }
            if (criteria.getDateTimeTo() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getDateTimeTo(), TollSubscription_.dateTimeTo));
            }
            if (criteria.getVehicle() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getVehicle(), TollSubscription_.vehicle));
            }
            if (criteria.getVehicleOwner() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getVehicleOwner(), TollSubscription_.vehicleOwner));
            }
            if (criteria.getActive() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getActive(), TollSubscription_.active));
            }
            if (criteria.getUpdated() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getUpdated(), TollSubscription_.updated));
            }
            if (criteria.getTollPackageId() != null) {
                specification =
                    specification.and(
                        buildSpecification(
                            criteria.getTollPackageId(),
                            root -> root.join(TollSubscription_.tollPackages, JoinType.LEFT).get(TollPackage_.id)
                        )
                    );
            }
        }
        return specification;
    }
}
