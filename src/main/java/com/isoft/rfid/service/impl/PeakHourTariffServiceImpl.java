package com.isoft.rfid.service.impl;

import com.isoft.rfid.domain.PeakHourTariff;
import com.isoft.rfid.repository.PeakHourTariffRepository;
import com.isoft.rfid.service.PeakHourTariffService;
import com.isoft.rfid.service.dto.PeakHourTariffDTO;
import com.isoft.rfid.service.mapper.PeakHourTariffMapper;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link PeakHourTariff}.
 */
@Service
@Transactional
public class PeakHourTariffServiceImpl implements PeakHourTariffService {

    private final Logger log = LoggerFactory.getLogger(PeakHourTariffServiceImpl.class);

    private final PeakHourTariffRepository peakHourTariffRepository;

    private final PeakHourTariffMapper peakHourTariffMapper;

    public PeakHourTariffServiceImpl(PeakHourTariffRepository peakHourTariffRepository, PeakHourTariffMapper peakHourTariffMapper) {
        this.peakHourTariffRepository = peakHourTariffRepository;
        this.peakHourTariffMapper = peakHourTariffMapper;
    }

    @Override
    public PeakHourTariffDTO save(PeakHourTariffDTO peakHourTariffDTO) {
        log.debug("Request to save PeakHourTariff : {}", peakHourTariffDTO);
        PeakHourTariff peakHourTariff = peakHourTariffMapper.toEntity(peakHourTariffDTO);
        peakHourTariff = peakHourTariffRepository.save(peakHourTariff);
        return peakHourTariffMapper.toDto(peakHourTariff);
    }

    @Override
    public PeakHourTariffDTO update(PeakHourTariffDTO peakHourTariffDTO) {
        log.debug("Request to update PeakHourTariff : {}", peakHourTariffDTO);
        PeakHourTariff peakHourTariff = peakHourTariffMapper.toEntity(peakHourTariffDTO);
        peakHourTariff = peakHourTariffRepository.save(peakHourTariff);
        return peakHourTariffMapper.toDto(peakHourTariff);
    }

    @Override
    public Optional<PeakHourTariffDTO> partialUpdate(PeakHourTariffDTO peakHourTariffDTO) {
        log.debug("Request to partially update PeakHourTariff : {}", peakHourTariffDTO);

        return peakHourTariffRepository
            .findById(peakHourTariffDTO.getId())
            .map(existingPeakHourTariff -> {
                peakHourTariffMapper.partialUpdate(existingPeakHourTariff, peakHourTariffDTO);

                return existingPeakHourTariff;
            })
            .map(peakHourTariffRepository::save)
            .map(peakHourTariffMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<PeakHourTariffDTO> findAll(Pageable pageable) {
        log.debug("Request to get all PeakHourTariffs");
        return peakHourTariffRepository.findAll(pageable).map(peakHourTariffMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<PeakHourTariffDTO> findOne(Long id) {
        log.debug("Request to get PeakHourTariff : {}", id);
        return peakHourTariffRepository.findById(id).map(peakHourTariffMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete PeakHourTariff : {}", id);
        peakHourTariffRepository.deleteById(id);
    }
}
