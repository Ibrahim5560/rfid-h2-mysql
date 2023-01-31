package com.isoft.rfid.service;

import com.isoft.rfid.service.dto.VehicleOwnerDTO;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing {@link com.isoft.rfid.domain.VehicleOwner}.
 */
public interface VehicleOwnerService {
    /**
     * Save a vehicleOwner.
     *
     * @param vehicleOwnerDTO the entity to save.
     * @return the persisted entity.
     */
    VehicleOwnerDTO save(VehicleOwnerDTO vehicleOwnerDTO);

    /**
     * Updates a vehicleOwner.
     *
     * @param vehicleOwnerDTO the entity to update.
     * @return the persisted entity.
     */
    VehicleOwnerDTO update(VehicleOwnerDTO vehicleOwnerDTO);

    /**
     * Partially updates a vehicleOwner.
     *
     * @param vehicleOwnerDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<VehicleOwnerDTO> partialUpdate(VehicleOwnerDTO vehicleOwnerDTO);

    /**
     * Get all the vehicleOwners.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<VehicleOwnerDTO> findAll(Pageable pageable);

    /**
     * Get the "id" vehicleOwner.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<VehicleOwnerDTO> findOne(Long id);

    /**
     * Delete the "id" vehicleOwner.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
