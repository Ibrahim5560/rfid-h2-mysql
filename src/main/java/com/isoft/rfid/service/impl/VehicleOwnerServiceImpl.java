package com.isoft.rfid.service.impl;

import com.isoft.rfid.domain.VehicleOwner;
import com.isoft.rfid.repository.VehicleOwnerRepository;
import com.isoft.rfid.service.VehicleOwnerService;
import com.isoft.rfid.service.dto.VehicleOwnerDTO;
import com.isoft.rfid.service.mapper.VehicleOwnerMapper;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link VehicleOwner}.
 */
@Service
@Transactional
public class VehicleOwnerServiceImpl implements VehicleOwnerService {

    private final Logger log = LoggerFactory.getLogger(VehicleOwnerServiceImpl.class);

    private final VehicleOwnerRepository vehicleOwnerRepository;

    private final VehicleOwnerMapper vehicleOwnerMapper;

    public VehicleOwnerServiceImpl(VehicleOwnerRepository vehicleOwnerRepository, VehicleOwnerMapper vehicleOwnerMapper) {
        this.vehicleOwnerRepository = vehicleOwnerRepository;
        this.vehicleOwnerMapper = vehicleOwnerMapper;
    }

    @Override
    public VehicleOwnerDTO save(VehicleOwnerDTO vehicleOwnerDTO) {
        log.debug("Request to save VehicleOwner : {}", vehicleOwnerDTO);
        VehicleOwner vehicleOwner = vehicleOwnerMapper.toEntity(vehicleOwnerDTO);
        vehicleOwner = vehicleOwnerRepository.save(vehicleOwner);
        return vehicleOwnerMapper.toDto(vehicleOwner);
    }

    @Override
    public VehicleOwnerDTO update(VehicleOwnerDTO vehicleOwnerDTO) {
        log.debug("Request to update VehicleOwner : {}", vehicleOwnerDTO);
        VehicleOwner vehicleOwner = vehicleOwnerMapper.toEntity(vehicleOwnerDTO);
        vehicleOwner = vehicleOwnerRepository.save(vehicleOwner);
        return vehicleOwnerMapper.toDto(vehicleOwner);
    }

    @Override
    public Optional<VehicleOwnerDTO> partialUpdate(VehicleOwnerDTO vehicleOwnerDTO) {
        log.debug("Request to partially update VehicleOwner : {}", vehicleOwnerDTO);

        return vehicleOwnerRepository
            .findById(vehicleOwnerDTO.getId())
            .map(existingVehicleOwner -> {
                vehicleOwnerMapper.partialUpdate(existingVehicleOwner, vehicleOwnerDTO);

                return existingVehicleOwner;
            })
            .map(vehicleOwnerRepository::save)
            .map(vehicleOwnerMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<VehicleOwnerDTO> findAll(Pageable pageable) {
        log.debug("Request to get all VehicleOwners");
        return vehicleOwnerRepository.findAll(pageable).map(vehicleOwnerMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<VehicleOwnerDTO> findOne(Long id) {
        log.debug("Request to get VehicleOwner : {}", id);
        return vehicleOwnerRepository.findById(id).map(vehicleOwnerMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete VehicleOwner : {}", id);
        vehicleOwnerRepository.deleteById(id);
    }
}
