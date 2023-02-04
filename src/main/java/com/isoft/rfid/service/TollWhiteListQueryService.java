package com.isoft.rfid.service;

import com.isoft.rfid.domain.*; // for static metamodels
import com.isoft.rfid.domain.TollWhiteList;
import com.isoft.rfid.repository.TollWhiteListRepository;
import com.isoft.rfid.service.criteria.TollWhiteListCriteria;
import com.isoft.rfid.service.dto.TollWhiteListDTO;
import com.isoft.rfid.service.mapper.TollWhiteListMapper;
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
 * Service for executing complex queries for {@link TollWhiteList} entities in the database.
 * The main input is a {@link TollWhiteListCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link TollWhiteListDTO} or a {@link Page} of {@link TollWhiteListDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class TollWhiteListQueryService extends QueryService<TollWhiteList> {

    private final Logger log = LoggerFactory.getLogger(TollWhiteListQueryService.class);

    private final TollWhiteListRepository tollWhiteListRepository;

    private final TollWhiteListMapper tollWhiteListMapper;

    public TollWhiteListQueryService(TollWhiteListRepository tollWhiteListRepository, TollWhiteListMapper tollWhiteListMapper) {
        this.tollWhiteListRepository = tollWhiteListRepository;
        this.tollWhiteListMapper = tollWhiteListMapper;
    }

    /**
     * Return a {@link List} of {@link TollWhiteListDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<TollWhiteListDTO> findByCriteria(TollWhiteListCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<TollWhiteList> specification = createSpecification(criteria);
        return tollWhiteListMapper.toDto(tollWhiteListRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link TollWhiteListDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<TollWhiteListDTO> findByCriteria(TollWhiteListCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<TollWhiteList> specification = createSpecification(criteria);
        return tollWhiteListRepository.findAll(specification, page).map(tollWhiteListMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(TollWhiteListCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<TollWhiteList> specification = createSpecification(criteria);
        return tollWhiteListRepository.count(specification);
    }

    /**
     * Function to convert {@link TollWhiteListCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<TollWhiteList> createSpecification(TollWhiteListCriteria criteria) {
        Specification<TollWhiteList> specification = Specification.where(null);
        if (criteria != null) {
            // This has to be called first, because the distinct method returns null
            if (criteria.getDistinct() != null) {
                specification = specification.and(distinct(criteria.getDistinct()));
            }
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), TollWhiteList_.id));
            }
            if (criteria.getName() != null) {
                specification = specification.and(buildStringSpecification(criteria.getName(), TollWhiteList_.name));
            }
            if (criteria.getEngName() != null) {
                specification = specification.and(buildStringSpecification(criteria.getEngName(), TollWhiteList_.engName));
            }
            if (criteria.getCode() != null) {
                specification = specification.and(buildStringSpecification(criteria.getCode(), TollWhiteList_.code));
            }
            if (criteria.getActive() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getActive(), TollWhiteList_.active));
            }
            if (criteria.getUpdated() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getUpdated(), TollWhiteList_.updated));
            }
            if (criteria.getGantry() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getGantry(), TollWhiteList_.gantry));
            }
            if (criteria.getVehicleLicenseType() != null) {
                specification =
                    specification.and(buildRangeSpecification(criteria.getVehicleLicenseType(), TollWhiteList_.vehicleLicenseType));
            }
            if (criteria.getVehicle() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getVehicle(), TollWhiteList_.vehicle));
            }
            if (criteria.getVehicleOwner() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getVehicleOwner(), TollWhiteList_.vehicleOwner));
            }
            if (criteria.getPassageTimes() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getPassageTimes(), TollWhiteList_.passageTimes));
            }
        }
        return specification;
    }
}
