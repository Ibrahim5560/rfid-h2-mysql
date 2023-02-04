package com.isoft.rfid.web.rest;

import com.isoft.rfid.repository.PeakHourTariffRepository;
import com.isoft.rfid.service.PeakHourTariffQueryService;
import com.isoft.rfid.service.PeakHourTariffService;
import com.isoft.rfid.service.criteria.PeakHourTariffCriteria;
import com.isoft.rfid.service.dto.PeakHourTariffDTO;
import com.isoft.rfid.web.rest.errors.BadRequestAlertException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import tech.jhipster.web.util.HeaderUtil;
import tech.jhipster.web.util.PaginationUtil;
import tech.jhipster.web.util.ResponseUtil;

/**
 * REST controller for managing {@link com.isoft.rfid.domain.PeakHourTariff}.
 */
@RestController
@RequestMapping("/api")
public class PeakHourTariffResource {

    private final Logger log = LoggerFactory.getLogger(PeakHourTariffResource.class);

    private static final String ENTITY_NAME = "peakHourTariff";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final PeakHourTariffService peakHourTariffService;

    private final PeakHourTariffRepository peakHourTariffRepository;

    private final PeakHourTariffQueryService peakHourTariffQueryService;

    public PeakHourTariffResource(
        PeakHourTariffService peakHourTariffService,
        PeakHourTariffRepository peakHourTariffRepository,
        PeakHourTariffQueryService peakHourTariffQueryService
    ) {
        this.peakHourTariffService = peakHourTariffService;
        this.peakHourTariffRepository = peakHourTariffRepository;
        this.peakHourTariffQueryService = peakHourTariffQueryService;
    }

    /**
     * {@code POST  /peak-hour-tariffs} : Create a new peakHourTariff.
     *
     * @param peakHourTariffDTO the peakHourTariffDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new peakHourTariffDTO, or with status {@code 400 (Bad Request)} if the peakHourTariff has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/peak-hour-tariffs")
    public ResponseEntity<PeakHourTariffDTO> createPeakHourTariff(@Valid @RequestBody PeakHourTariffDTO peakHourTariffDTO)
        throws URISyntaxException {
        log.debug("REST request to save PeakHourTariff : {}", peakHourTariffDTO);
        if (peakHourTariffDTO.getId() != null) {
            throw new BadRequestAlertException("A new peakHourTariff cannot already have an ID", ENTITY_NAME, "idexists");
        }
        PeakHourTariffDTO result = peakHourTariffService.save(peakHourTariffDTO);
        return ResponseEntity
            .created(new URI("/api/peak-hour-tariffs/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /peak-hour-tariffs/:id} : Updates an existing peakHourTariff.
     *
     * @param id the id of the peakHourTariffDTO to save.
     * @param peakHourTariffDTO the peakHourTariffDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated peakHourTariffDTO,
     * or with status {@code 400 (Bad Request)} if the peakHourTariffDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the peakHourTariffDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/peak-hour-tariffs/{id}")
    public ResponseEntity<PeakHourTariffDTO> updatePeakHourTariff(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody PeakHourTariffDTO peakHourTariffDTO
    ) throws URISyntaxException {
        log.debug("REST request to update PeakHourTariff : {}, {}", id, peakHourTariffDTO);
        if (peakHourTariffDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, peakHourTariffDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!peakHourTariffRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        PeakHourTariffDTO result = peakHourTariffService.update(peakHourTariffDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, peakHourTariffDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /peak-hour-tariffs/:id} : Partial updates given fields of an existing peakHourTariff, field will ignore if it is null
     *
     * @param id the id of the peakHourTariffDTO to save.
     * @param peakHourTariffDTO the peakHourTariffDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated peakHourTariffDTO,
     * or with status {@code 400 (Bad Request)} if the peakHourTariffDTO is not valid,
     * or with status {@code 404 (Not Found)} if the peakHourTariffDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the peakHourTariffDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/peak-hour-tariffs/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<PeakHourTariffDTO> partialUpdatePeakHourTariff(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody PeakHourTariffDTO peakHourTariffDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update PeakHourTariff partially : {}, {}", id, peakHourTariffDTO);
        if (peakHourTariffDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, peakHourTariffDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!peakHourTariffRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<PeakHourTariffDTO> result = peakHourTariffService.partialUpdate(peakHourTariffDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, peakHourTariffDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /peak-hour-tariffs} : get all the peakHourTariffs.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of peakHourTariffs in body.
     */
    @GetMapping("/peak-hour-tariffs")
    public ResponseEntity<List<PeakHourTariffDTO>> getAllPeakHourTariffs(
        PeakHourTariffCriteria criteria,
        @org.springdoc.api.annotations.ParameterObject Pageable pageable
    ) {
        log.debug("REST request to get PeakHourTariffs by criteria: {}", criteria);
        Page<PeakHourTariffDTO> page = peakHourTariffQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /peak-hour-tariffs/count} : count all the peakHourTariffs.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/peak-hour-tariffs/count")
    public ResponseEntity<Long> countPeakHourTariffs(PeakHourTariffCriteria criteria) {
        log.debug("REST request to count PeakHourTariffs by criteria: {}", criteria);
        return ResponseEntity.ok().body(peakHourTariffQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /peak-hour-tariffs/:id} : get the "id" peakHourTariff.
     *
     * @param id the id of the peakHourTariffDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the peakHourTariffDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/peak-hour-tariffs/{id}")
    public ResponseEntity<PeakHourTariffDTO> getPeakHourTariff(@PathVariable Long id) {
        log.debug("REST request to get PeakHourTariff : {}", id);
        Optional<PeakHourTariffDTO> peakHourTariffDTO = peakHourTariffService.findOne(id);
        return ResponseUtil.wrapOrNotFound(peakHourTariffDTO);
    }

    /**
     * {@code DELETE  /peak-hour-tariffs/:id} : delete the "id" peakHourTariff.
     *
     * @param id the id of the peakHourTariffDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/peak-hour-tariffs/{id}")
    public ResponseEntity<Void> deletePeakHourTariff(@PathVariable Long id) {
        log.debug("REST request to delete PeakHourTariff : {}", id);
        peakHourTariffService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
