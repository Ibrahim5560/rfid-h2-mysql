package com.isoft.rfid.service.impl;

import com.isoft.rfid.domain.TollPackage;
import com.isoft.rfid.repository.TollPackageRepository;
import com.isoft.rfid.service.TollPackageService;
import com.isoft.rfid.service.dto.TollPackageDTO;
import com.isoft.rfid.service.mapper.TollPackageMapper;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link TollPackage}.
 */
@Service
@Transactional
public class TollPackageServiceImpl implements TollPackageService {

    private final Logger log = LoggerFactory.getLogger(TollPackageServiceImpl.class);

    private final TollPackageRepository tollPackageRepository;

    private final TollPackageMapper tollPackageMapper;

    public TollPackageServiceImpl(TollPackageRepository tollPackageRepository, TollPackageMapper tollPackageMapper) {
        this.tollPackageRepository = tollPackageRepository;
        this.tollPackageMapper = tollPackageMapper;
    }

    @Override
    public TollPackageDTO save(TollPackageDTO tollPackageDTO) {
        log.debug("Request to save TollPackage : {}", tollPackageDTO);
        TollPackage tollPackage = tollPackageMapper.toEntity(tollPackageDTO);
        tollPackage = tollPackageRepository.save(tollPackage);
        return tollPackageMapper.toDto(tollPackage);
    }

    @Override
    public TollPackageDTO update(TollPackageDTO tollPackageDTO) {
        log.debug("Request to update TollPackage : {}", tollPackageDTO);
        TollPackage tollPackage = tollPackageMapper.toEntity(tollPackageDTO);
        tollPackage = tollPackageRepository.save(tollPackage);
        return tollPackageMapper.toDto(tollPackage);
    }

    @Override
    public Optional<TollPackageDTO> partialUpdate(TollPackageDTO tollPackageDTO) {
        log.debug("Request to partially update TollPackage : {}", tollPackageDTO);

        return tollPackageRepository
            .findById(tollPackageDTO.getId())
            .map(existingTollPackage -> {
                tollPackageMapper.partialUpdate(existingTollPackage, tollPackageDTO);

                return existingTollPackage;
            })
            .map(tollPackageRepository::save)
            .map(tollPackageMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<TollPackageDTO> findAll(Pageable pageable) {
        log.debug("Request to get all TollPackages");
        return tollPackageRepository.findAll(pageable).map(tollPackageMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<TollPackageDTO> findOne(Long id) {
        log.debug("Request to get TollPackage : {}", id);
        return tollPackageRepository.findById(id).map(tollPackageMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete TollPackage : {}", id);
        tollPackageRepository.deleteById(id);
    }
}
