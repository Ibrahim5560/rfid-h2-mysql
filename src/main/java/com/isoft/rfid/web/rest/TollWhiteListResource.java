package com.isoft.rfid.web.rest;

import com.isoft.rfid.repository.TollWhiteListRepository;
import com.isoft.rfid.service.TollWhiteListQueryService;
import com.isoft.rfid.service.TollWhiteListService;
import com.isoft.rfid.service.criteria.TollWhiteListCriteria;
import com.isoft.rfid.service.dto.TollWhiteListDTO;
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
 * REST controller for managing {@link com.isoft.rfid.domain.TollWhiteList}.
 */
@RestController
@RequestMapping("/api")
public class TollWhiteListResource {

    private final Logger log = LoggerFactory.getLogger(TollWhiteListResource.class);

    private static final String ENTITY_NAME = "tollWhiteList";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final TollWhiteListService tollWhiteListService;

    private final TollWhiteListRepository tollWhiteListRepository;

    private final TollWhiteListQueryService tollWhiteListQueryService;

    public TollWhiteListResource(
        TollWhiteListService tollWhiteListService,
        TollWhiteListRepository tollWhiteListRepository,
        TollWhiteListQueryService tollWhiteListQueryService
    ) {
        this.tollWhiteListService = tollWhiteListService;
        this.tollWhiteListRepository = tollWhiteListRepository;
        this.tollWhiteListQueryService = tollWhiteListQueryService;
    }

    /**
     * {@code POST  /toll-white-lists} : Create a new tollWhiteList.
     *
     * @param tollWhiteListDTO the tollWhiteListDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new tollWhiteListDTO, or with status {@code 400 (Bad Request)} if the tollWhiteList has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/toll-white-lists")
    public ResponseEntity<TollWhiteListDTO> createTollWhiteList(@Valid @RequestBody TollWhiteListDTO tollWhiteListDTO)
        throws URISyntaxException {
        log.debug("REST request to save TollWhiteList : {}", tollWhiteListDTO);
        if (tollWhiteListDTO.getId() != null) {
            throw new BadRequestAlertException("A new tollWhiteList cannot already have an ID", ENTITY_NAME, "idexists");
        }
        TollWhiteListDTO result = tollWhiteListService.save(tollWhiteListDTO);
        return ResponseEntity
            .created(new URI("/api/toll-white-lists/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /toll-white-lists/:id} : Updates an existing tollWhiteList.
     *
     * @param id the id of the tollWhiteListDTO to save.
     * @param tollWhiteListDTO the tollWhiteListDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated tollWhiteListDTO,
     * or with status {@code 400 (Bad Request)} if the tollWhiteListDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the tollWhiteListDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/toll-white-lists/{id}")
    public ResponseEntity<TollWhiteListDTO> updateTollWhiteList(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody TollWhiteListDTO tollWhiteListDTO
    ) throws URISyntaxException {
        log.debug("REST request to update TollWhiteList : {}, {}", id, tollWhiteListDTO);
        if (tollWhiteListDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, tollWhiteListDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!tollWhiteListRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        TollWhiteListDTO result = tollWhiteListService.update(tollWhiteListDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, tollWhiteListDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /toll-white-lists/:id} : Partial updates given fields of an existing tollWhiteList, field will ignore if it is null
     *
     * @param id the id of the tollWhiteListDTO to save.
     * @param tollWhiteListDTO the tollWhiteListDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated tollWhiteListDTO,
     * or with status {@code 400 (Bad Request)} if the tollWhiteListDTO is not valid,
     * or with status {@code 404 (Not Found)} if the tollWhiteListDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the tollWhiteListDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/toll-white-lists/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<TollWhiteListDTO> partialUpdateTollWhiteList(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody TollWhiteListDTO tollWhiteListDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update TollWhiteList partially : {}, {}", id, tollWhiteListDTO);
        if (tollWhiteListDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, tollWhiteListDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!tollWhiteListRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<TollWhiteListDTO> result = tollWhiteListService.partialUpdate(tollWhiteListDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, tollWhiteListDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /toll-white-lists} : get all the tollWhiteLists.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of tollWhiteLists in body.
     */
    @GetMapping("/toll-white-lists")
    public ResponseEntity<List<TollWhiteListDTO>> getAllTollWhiteLists(
        TollWhiteListCriteria criteria,
        @org.springdoc.api.annotations.ParameterObject Pageable pageable
    ) {
        log.debug("REST request to get TollWhiteLists by criteria: {}", criteria);
        Page<TollWhiteListDTO> page = tollWhiteListQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /toll-white-lists/count} : count all the tollWhiteLists.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/toll-white-lists/count")
    public ResponseEntity<Long> countTollWhiteLists(TollWhiteListCriteria criteria) {
        log.debug("REST request to count TollWhiteLists by criteria: {}", criteria);
        return ResponseEntity.ok().body(tollWhiteListQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /toll-white-lists/:id} : get the "id" tollWhiteList.
     *
     * @param id the id of the tollWhiteListDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the tollWhiteListDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/toll-white-lists/{id}")
    public ResponseEntity<TollWhiteListDTO> getTollWhiteList(@PathVariable Long id) {
        log.debug("REST request to get TollWhiteList : {}", id);
        Optional<TollWhiteListDTO> tollWhiteListDTO = tollWhiteListService.findOne(id);
        return ResponseUtil.wrapOrNotFound(tollWhiteListDTO);
    }

    /**
     * {@code DELETE  /toll-white-lists/:id} : delete the "id" tollWhiteList.
     *
     * @param id the id of the tollWhiteListDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/toll-white-lists/{id}")
    public ResponseEntity<Void> deleteTollWhiteList(@PathVariable Long id) {
        log.debug("REST request to delete TollWhiteList : {}", id);
        tollWhiteListService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
