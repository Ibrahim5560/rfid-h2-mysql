package com.isoft.rfid.service.impl;

import com.isoft.rfid.domain.Gantry;
import com.isoft.rfid.repository.GantryRepository;
import com.isoft.rfid.service.GantryService;
import com.isoft.rfid.service.dto.GantryDTO;
import com.isoft.rfid.service.mapper.GantryMapper;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link Gantry}.
 */
@Service
@Transactional
public class GantryServiceImpl implements GantryService {

    private final Logger log = LoggerFactory.getLogger(GantryServiceImpl.class);

    private final GantryRepository gantryRepository;

    private final GantryMapper gantryMapper;

    public GantryServiceImpl(GantryRepository gantryRepository, GantryMapper gantryMapper) {
        this.gantryRepository = gantryRepository;
        this.gantryMapper = gantryMapper;
    }

    @Override
    public GantryDTO save(GantryDTO gantryDTO) {
        log.debug("Request to save Gantry : {}", gantryDTO);
        Gantry gantry = gantryMapper.toEntity(gantryDTO);
        gantry = gantryRepository.save(gantry);
        return gantryMapper.toDto(gantry);
    }

    @Override
    public GantryDTO update(GantryDTO gantryDTO) {
        log.debug("Request to update Gantry : {}", gantryDTO);
        Gantry gantry = gantryMapper.toEntity(gantryDTO);
        gantry = gantryRepository.save(gantry);
        return gantryMapper.toDto(gantry);
    }

    @Override
    public Optional<GantryDTO> partialUpdate(GantryDTO gantryDTO) {
        log.debug("Request to partially update Gantry : {}", gantryDTO);

        return gantryRepository
            .findById(gantryDTO.getId())
            .map(existingGantry -> {
                gantryMapper.partialUpdate(existingGantry, gantryDTO);

                return existingGantry;
            })
            .map(gantryRepository::save)
            .map(gantryMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<GantryDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Gantries");
        return gantryRepository.findAll(pageable).map(gantryMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<GantryDTO> findOne(Long id) {
        log.debug("Request to get Gantry : {}", id);
        return gantryRepository.findById(id).map(gantryMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete Gantry : {}", id);
        gantryRepository.deleteById(id);
    }
}
