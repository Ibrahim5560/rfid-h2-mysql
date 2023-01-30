package com.isoft.rfid.web.rest;

import com.isoft.rfid.repository.VehicleOwnerRepository;
import com.isoft.rfid.service.VehicleOwnerQueryService;
import com.isoft.rfid.service.VehicleOwnerService;
import com.isoft.rfid.service.criteria.VehicleOwnerCriteria;
import com.isoft.rfid.service.dto.VehicleOwnerDTO;
import com.isoft.rfid.web.rest.errors.BadRequestAlertException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
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
 * REST controller for managing {@link com.isoft.rfid.domain.VehicleOwner}.
 */
@RestController
@RequestMapping("/api")
public class VehicleOwnerResource {

    private final Logger log = LoggerFactory.getLogger(VehicleOwnerResource.class);

    private static final String ENTITY_NAME = "vehicleOwner";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final VehicleOwnerService vehicleOwnerService;

    private final VehicleOwnerRepository vehicleOwnerRepository;

    private final VehicleOwnerQueryService vehicleOwnerQueryService;

    public VehicleOwnerResource(
        VehicleOwnerService vehicleOwnerService,
        VehicleOwnerRepository vehicleOwnerRepository,
        VehicleOwnerQueryService vehicleOwnerQueryService
    ) {
        this.vehicleOwnerService = vehicleOwnerService;
        this.vehicleOwnerRepository = vehicleOwnerRepository;
        this.vehicleOwnerQueryService = vehicleOwnerQueryService;
    }

    /**
     * {@code POST  /vehicle-owners} : Create a new vehicleOwner.
     *
     * @param vehicleOwnerDTO the vehicleOwnerDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new vehicleOwnerDTO, or with status {@code 400 (Bad Request)} if the vehicleOwner has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/vehicle-owners")
    public ResponseEntity<VehicleOwnerDTO> createVehicleOwner(@RequestBody VehicleOwnerDTO vehicleOwnerDTO) throws URISyntaxException {
        log.debug("REST request to save VehicleOwner : {}", vehicleOwnerDTO);
        if (vehicleOwnerDTO.getId() != null) {
            throw new BadRequestAlertException("A new vehicleOwner cannot already have an ID", ENTITY_NAME, "idexists");
        }
        VehicleOwnerDTO result = vehicleOwnerService.save(vehicleOwnerDTO);
        return ResponseEntity
            .created(new URI("/api/vehicle-owners/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /vehicle-owners/:id} : Updates an existing vehicleOwner.
     *
     * @param id the id of the vehicleOwnerDTO to save.
     * @param vehicleOwnerDTO the vehicleOwnerDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated vehicleOwnerDTO,
     * or with status {@code 400 (Bad Request)} if the vehicleOwnerDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the vehicleOwnerDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/vehicle-owners/{id}")
    public ResponseEntity<VehicleOwnerDTO> updateVehicleOwner(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody VehicleOwnerDTO vehicleOwnerDTO
    ) throws URISyntaxException {
        log.debug("REST request to update VehicleOwner : {}, {}", id, vehicleOwnerDTO);
        if (vehicleOwnerDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, vehicleOwnerDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!vehicleOwnerRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        VehicleOwnerDTO result = vehicleOwnerService.update(vehicleOwnerDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, vehicleOwnerDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /vehicle-owners/:id} : Partial updates given fields of an existing vehicleOwner, field will ignore if it is null
     *
     * @param id the id of the vehicleOwnerDTO to save.
     * @param vehicleOwnerDTO the vehicleOwnerDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated vehicleOwnerDTO,
     * or with status {@code 400 (Bad Request)} if the vehicleOwnerDTO is not valid,
     * or with status {@code 404 (Not Found)} if the vehicleOwnerDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the vehicleOwnerDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/vehicle-owners/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<VehicleOwnerDTO> partialUpdateVehicleOwner(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody VehicleOwnerDTO vehicleOwnerDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update VehicleOwner partially : {}, {}", id, vehicleOwnerDTO);
        if (vehicleOwnerDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, vehicleOwnerDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!vehicleOwnerRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<VehicleOwnerDTO> result = vehicleOwnerService.partialUpdate(vehicleOwnerDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, vehicleOwnerDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /vehicle-owners} : get all the vehicleOwners.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of vehicleOwners in body.
     */
    @GetMapping("/vehicle-owners")
    public ResponseEntity<List<VehicleOwnerDTO>> getAllVehicleOwners(
        VehicleOwnerCriteria criteria,
        @org.springdoc.api.annotations.ParameterObject Pageable pageable
    ) {
        log.debug("REST request to get VehicleOwners by criteria: {}", criteria);
        Page<VehicleOwnerDTO> page = vehicleOwnerQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /vehicle-owners/count} : count all the vehicleOwners.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/vehicle-owners/count")
    public ResponseEntity<Long> countVehicleOwners(VehicleOwnerCriteria criteria) {
        log.debug("REST request to count VehicleOwners by criteria: {}", criteria);
        return ResponseEntity.ok().body(vehicleOwnerQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /vehicle-owners/:id} : get the "id" vehicleOwner.
     *
     * @param id the id of the vehicleOwnerDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the vehicleOwnerDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/vehicle-owners/{id}")
    public ResponseEntity<VehicleOwnerDTO> getVehicleOwner(@PathVariable Long id) {
        log.debug("REST request to get VehicleOwner : {}", id);
        Optional<VehicleOwnerDTO> vehicleOwnerDTO = vehicleOwnerService.findOne(id);
        return ResponseUtil.wrapOrNotFound(vehicleOwnerDTO);
    }

    /**
     * {@code DELETE  /vehicle-owners/:id} : delete the "id" vehicleOwner.
     *
     * @param id the id of the vehicleOwnerDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/vehicle-owners/{id}")
    public ResponseEntity<Void> deleteVehicleOwner(@PathVariable Long id) {
        log.debug("REST request to delete VehicleOwner : {}", id);
        vehicleOwnerService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
