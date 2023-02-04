package com.isoft.rfid.web.rest;

import com.isoft.rfid.repository.TollSubscriptionRepository;
import com.isoft.rfid.service.TollSubscriptionQueryService;
import com.isoft.rfid.service.TollSubscriptionService;
import com.isoft.rfid.service.criteria.TollSubscriptionCriteria;
import com.isoft.rfid.service.dto.TollSubscriptionDTO;
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
 * REST controller for managing {@link com.isoft.rfid.domain.TollSubscription}.
 */
@RestController
@RequestMapping("/api")
public class TollSubscriptionResource {

    private final Logger log = LoggerFactory.getLogger(TollSubscriptionResource.class);

    private static final String ENTITY_NAME = "tollSubscription";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final TollSubscriptionService tollSubscriptionService;

    private final TollSubscriptionRepository tollSubscriptionRepository;

    private final TollSubscriptionQueryService tollSubscriptionQueryService;

    public TollSubscriptionResource(
        TollSubscriptionService tollSubscriptionService,
        TollSubscriptionRepository tollSubscriptionRepository,
        TollSubscriptionQueryService tollSubscriptionQueryService
    ) {
        this.tollSubscriptionService = tollSubscriptionService;
        this.tollSubscriptionRepository = tollSubscriptionRepository;
        this.tollSubscriptionQueryService = tollSubscriptionQueryService;
    }

    /**
     * {@code POST  /toll-subscriptions} : Create a new tollSubscription.
     *
     * @param tollSubscriptionDTO the tollSubscriptionDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new tollSubscriptionDTO, or with status {@code 400 (Bad Request)} if the tollSubscription has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/toll-subscriptions")
    public ResponseEntity<TollSubscriptionDTO> createTollSubscription(@Valid @RequestBody TollSubscriptionDTO tollSubscriptionDTO)
        throws URISyntaxException {
        log.debug("REST request to save TollSubscription : {}", tollSubscriptionDTO);
        if (tollSubscriptionDTO.getId() != null) {
            throw new BadRequestAlertException("A new tollSubscription cannot already have an ID", ENTITY_NAME, "idexists");
        }
        TollSubscriptionDTO result = tollSubscriptionService.save(tollSubscriptionDTO);
        return ResponseEntity
            .created(new URI("/api/toll-subscriptions/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /toll-subscriptions/:id} : Updates an existing tollSubscription.
     *
     * @param id the id of the tollSubscriptionDTO to save.
     * @param tollSubscriptionDTO the tollSubscriptionDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated tollSubscriptionDTO,
     * or with status {@code 400 (Bad Request)} if the tollSubscriptionDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the tollSubscriptionDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/toll-subscriptions/{id}")
    public ResponseEntity<TollSubscriptionDTO> updateTollSubscription(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody TollSubscriptionDTO tollSubscriptionDTO
    ) throws URISyntaxException {
        log.debug("REST request to update TollSubscription : {}, {}", id, tollSubscriptionDTO);
        if (tollSubscriptionDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, tollSubscriptionDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!tollSubscriptionRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        TollSubscriptionDTO result = tollSubscriptionService.update(tollSubscriptionDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, tollSubscriptionDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /toll-subscriptions/:id} : Partial updates given fields of an existing tollSubscription, field will ignore if it is null
     *
     * @param id the id of the tollSubscriptionDTO to save.
     * @param tollSubscriptionDTO the tollSubscriptionDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated tollSubscriptionDTO,
     * or with status {@code 400 (Bad Request)} if the tollSubscriptionDTO is not valid,
     * or with status {@code 404 (Not Found)} if the tollSubscriptionDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the tollSubscriptionDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/toll-subscriptions/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<TollSubscriptionDTO> partialUpdateTollSubscription(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody TollSubscriptionDTO tollSubscriptionDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update TollSubscription partially : {}, {}", id, tollSubscriptionDTO);
        if (tollSubscriptionDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, tollSubscriptionDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!tollSubscriptionRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<TollSubscriptionDTO> result = tollSubscriptionService.partialUpdate(tollSubscriptionDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, tollSubscriptionDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /toll-subscriptions} : get all the tollSubscriptions.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of tollSubscriptions in body.
     */
    @GetMapping("/toll-subscriptions")
    public ResponseEntity<List<TollSubscriptionDTO>> getAllTollSubscriptions(
        TollSubscriptionCriteria criteria,
        @org.springdoc.api.annotations.ParameterObject Pageable pageable
    ) {
        log.debug("REST request to get TollSubscriptions by criteria: {}", criteria);
        Page<TollSubscriptionDTO> page = tollSubscriptionQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /toll-subscriptions/count} : count all the tollSubscriptions.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/toll-subscriptions/count")
    public ResponseEntity<Long> countTollSubscriptions(TollSubscriptionCriteria criteria) {
        log.debug("REST request to count TollSubscriptions by criteria: {}", criteria);
        return ResponseEntity.ok().body(tollSubscriptionQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /toll-subscriptions/:id} : get the "id" tollSubscription.
     *
     * @param id the id of the tollSubscriptionDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the tollSubscriptionDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/toll-subscriptions/{id}")
    public ResponseEntity<TollSubscriptionDTO> getTollSubscription(@PathVariable Long id) {
        log.debug("REST request to get TollSubscription : {}", id);
        Optional<TollSubscriptionDTO> tollSubscriptionDTO = tollSubscriptionService.findOne(id);
        return ResponseUtil.wrapOrNotFound(tollSubscriptionDTO);
    }

    /**
     * {@code DELETE  /toll-subscriptions/:id} : delete the "id" tollSubscription.
     *
     * @param id the id of the tollSubscriptionDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/toll-subscriptions/{id}")
    public ResponseEntity<Void> deleteTollSubscription(@PathVariable Long id) {
        log.debug("REST request to delete TollSubscription : {}", id);
        tollSubscriptionService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
