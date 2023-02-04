package com.isoft.rfid.service;

import com.isoft.rfid.domain.*; // for static metamodels
import com.isoft.rfid.domain.PeakHourTariff;
import com.isoft.rfid.repository.PeakHourTariffRepository;
import com.isoft.rfid.service.criteria.PeakHourTariffCriteria;
import com.isoft.rfid.service.dto.PeakHourTariffDTO;
import com.isoft.rfid.service.mapper.PeakHourTariffMapper;
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
 * Service for executing complex queries for {@link PeakHourTariff} entities in the database.
 * The main input is a {@link PeakHourTariffCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link PeakHourTariffDTO} or a {@link Page} of {@link PeakHourTariffDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class PeakHourTariffQueryService extends QueryService<PeakHourTariff> {

    private final Logger log = LoggerFactory.getLogger(PeakHourTariffQueryService.class);

    private final PeakHourTariffRepository peakHourTariffRepository;

    private final PeakHourTariffMapper peakHourTariffMapper;

    public PeakHourTariffQueryService(PeakHourTariffRepository peakHourTariffRepository, PeakHourTariffMapper peakHourTariffMapper) {
        this.peakHourTariffRepository = peakHourTariffRepository;
        this.peakHourTariffMapper = peakHourTariffMapper;
    }

    /**
     * Return a {@link List} of {@link PeakHourTariffDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<PeakHourTariffDTO> findByCriteria(PeakHourTariffCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<PeakHourTariff> specification = createSpecification(criteria);
        return peakHourTariffMapper.toDto(peakHourTariffRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link PeakHourTariffDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<PeakHourTariffDTO> findByCriteria(PeakHourTariffCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<PeakHourTariff> specification = createSpecification(criteria);
        return peakHourTariffRepository.findAll(specification, page).map(peakHourTariffMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(PeakHourTariffCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<PeakHourTariff> specification = createSpecification(criteria);
        return peakHourTariffRepository.count(specification);
    }

    /**
     * Function to convert {@link PeakHourTariffCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<PeakHourTariff> createSpecification(PeakHourTariffCriteria criteria) {
        Specification<PeakHourTariff> specification = Specification.where(null);
        if (criteria != null) {
            // This has to be called first, because the distinct method returns null
            if (criteria.getDistinct() != null) {
                specification = specification.and(distinct(criteria.getDistinct()));
            }
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), PeakHourTariff_.id));
            }
            if (criteria.getPeakHourFrom() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getPeakHourFrom(), PeakHourTariff_.peakHourFrom));
            }
            if (criteria.getPeakHourTo() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getPeakHourTo(), PeakHourTariff_.peakHourTo));
            }
            if (criteria.getGantry() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getGantry(), PeakHourTariff_.gantry));
            }
            if (criteria.getGantryToll() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getGantryToll(), PeakHourTariff_.gantryToll));
            }
            if (criteria.getActive() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getActive(), PeakHourTariff_.active));
            }
            if (criteria.getUpdated() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getUpdated(), PeakHourTariff_.updated));
            }
        }
        return specification;
    }
}
