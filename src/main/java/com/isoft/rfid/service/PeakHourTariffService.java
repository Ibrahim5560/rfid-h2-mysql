package com.isoft.rfid.service;

import com.isoft.rfid.service.dto.PeakHourTariffDTO;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing {@link com.isoft.rfid.domain.PeakHourTariff}.
 */
public interface PeakHourTariffService {
    /**
     * Save a peakHourTariff.
     *
     * @param peakHourTariffDTO the entity to save.
     * @return the persisted entity.
     */
    PeakHourTariffDTO save(PeakHourTariffDTO peakHourTariffDTO);

    /**
     * Updates a peakHourTariff.
     *
     * @param peakHourTariffDTO the entity to update.
     * @return the persisted entity.
     */
    PeakHourTariffDTO update(PeakHourTariffDTO peakHourTariffDTO);

    /**
     * Partially updates a peakHourTariff.
     *
     * @param peakHourTariffDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<PeakHourTariffDTO> partialUpdate(PeakHourTariffDTO peakHourTariffDTO);

    /**
     * Get all the peakHourTariffs.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<PeakHourTariffDTO> findAll(Pageable pageable);

    /**
     * Get the "id" peakHourTariff.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<PeakHourTariffDTO> findOne(Long id);

    /**
     * Delete the "id" peakHourTariff.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
