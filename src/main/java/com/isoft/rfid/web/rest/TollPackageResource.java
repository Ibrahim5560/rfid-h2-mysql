package com.isoft.rfid.web.rest;

import com.isoft.rfid.repository.TollPackageRepository;
import com.isoft.rfid.service.TollPackageQueryService;
import com.isoft.rfid.service.TollPackageService;
import com.isoft.rfid.service.criteria.TollPackageCriteria;
import com.isoft.rfid.service.dto.TollPackageDTO;
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
 * REST controller for managing {@link com.isoft.rfid.domain.TollPackage}.
 */
@RestController
@RequestMapping("/api")
public class TollPackageResource {

    private final Logger log = LoggerFactory.getLogger(TollPackageResource.class);

    private static final String ENTITY_NAME = "tollPackage";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final TollPackageService tollPackageService;

    private final TollPackageRepository tollPackageRepository;

    private final TollPackageQueryService tollPackageQueryService;

    public TollPackageResource(
        TollPackageService tollPackageService,
        TollPackageRepository tollPackageRepository,
        TollPackageQueryService tollPackageQueryService
    ) {
        this.tollPackageService = tollPackageService;
        this.tollPackageRepository = tollPackageRepository;
        this.tollPackageQueryService = tollPackageQueryService;
    }

    /**
     * {@code POST  /toll-packages} : Create a new tollPackage.
     *
     * @param tollPackageDTO the tollPackageDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new tollPackageDTO, or with status {@code 400 (Bad Request)} if the tollPackage has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/toll-packages")
    public ResponseEntity<TollPackageDTO> createTollPackage(@Valid @RequestBody TollPackageDTO tollPackageDTO) throws URISyntaxException {
        log.debug("REST request to save TollPackage : {}", tollPackageDTO);
        if (tollPackageDTO.getId() != null) {
            throw new BadRequestAlertException("A new tollPackage cannot already have an ID", ENTITY_NAME, "idexists");
        }
        TollPackageDTO result = tollPackageService.save(tollPackageDTO);
        return ResponseEntity
            .created(new URI("/api/toll-packages/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /toll-packages/:id} : Updates an existing tollPackage.
     *
     * @param id the id of the tollPackageDTO to save.
     * @param tollPackageDTO the tollPackageDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated tollPackageDTO,
     * or with status {@code 400 (Bad Request)} if the tollPackageDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the tollPackageDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/toll-packages/{id}")
    public ResponseEntity<TollPackageDTO> updateTollPackage(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody TollPackageDTO tollPackageDTO
    ) throws URISyntaxException {
        log.debug("REST request to update TollPackage : {}, {}", id, tollPackageDTO);
        if (tollPackageDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, tollPackageDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!tollPackageRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        TollPackageDTO result = tollPackageService.update(tollPackageDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, tollPackageDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /toll-packages/:id} : Partial updates given fields of an existing tollPackage, field will ignore if it is null
     *
     * @param id the id of the tollPackageDTO to save.
     * @param tollPackageDTO the tollPackageDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated tollPackageDTO,
     * or with status {@code 400 (Bad Request)} if the tollPackageDTO is not valid,
     * or with status {@code 404 (Not Found)} if the tollPackageDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the tollPackageDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/toll-packages/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<TollPackageDTO> partialUpdateTollPackage(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody TollPackageDTO tollPackageDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update TollPackage partially : {}, {}", id, tollPackageDTO);
        if (tollPackageDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, tollPackageDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!tollPackageRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<TollPackageDTO> result = tollPackageService.partialUpdate(tollPackageDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, tollPackageDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /toll-packages} : get all the tollPackages.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of tollPackages in body.
     */
    @GetMapping("/toll-packages")
    public ResponseEntity<List<TollPackageDTO>> getAllTollPackages(
        TollPackageCriteria criteria,
        @org.springdoc.api.annotations.ParameterObject Pageable pageable
    ) {
        log.debug("REST request to get TollPackages by criteria: {}", criteria);
        Page<TollPackageDTO> page = tollPackageQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /toll-packages/count} : count all the tollPackages.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/toll-packages/count")
    public ResponseEntity<Long> countTollPackages(TollPackageCriteria criteria) {
        log.debug("REST request to count TollPackages by criteria: {}", criteria);
        return ResponseEntity.ok().body(tollPackageQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /toll-packages/:id} : get the "id" tollPackage.
     *
     * @param id the id of the tollPackageDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the tollPackageDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/toll-packages/{id}")
    public ResponseEntity<TollPackageDTO> getTollPackage(@PathVariable Long id) {
        log.debug("REST request to get TollPackage : {}", id);
        Optional<TollPackageDTO> tollPackageDTO = tollPackageService.findOne(id);
        return ResponseUtil.wrapOrNotFound(tollPackageDTO);
    }

    /**
     * {@code DELETE  /toll-packages/:id} : delete the "id" tollPackage.
     *
     * @param id the id of the tollPackageDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/toll-packages/{id}")
    public ResponseEntity<Void> deleteTollPackage(@PathVariable Long id) {
        log.debug("REST request to delete TollPackage : {}", id);
        tollPackageService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
