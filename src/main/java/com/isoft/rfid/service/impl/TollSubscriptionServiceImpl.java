package com.isoft.rfid.service.impl;

import com.isoft.rfid.domain.TollSubscription;
import com.isoft.rfid.repository.TollSubscriptionRepository;
import com.isoft.rfid.service.TollSubscriptionService;
import com.isoft.rfid.service.dto.TollSubscriptionDTO;
import com.isoft.rfid.service.mapper.TollSubscriptionMapper;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link TollSubscription}.
 */
@Service
@Transactional
public class TollSubscriptionServiceImpl implements TollSubscriptionService {

    private final Logger log = LoggerFactory.getLogger(TollSubscriptionServiceImpl.class);

    private final TollSubscriptionRepository tollSubscriptionRepository;

    private final TollSubscriptionMapper tollSubscriptionMapper;

    public TollSubscriptionServiceImpl(
        TollSubscriptionRepository tollSubscriptionRepository,
        TollSubscriptionMapper tollSubscriptionMapper
    ) {
        this.tollSubscriptionRepository = tollSubscriptionRepository;
        this.tollSubscriptionMapper = tollSubscriptionMapper;
    }

    @Override
    public TollSubscriptionDTO save(TollSubscriptionDTO tollSubscriptionDTO) {
        log.debug("Request to save TollSubscription : {}", tollSubscriptionDTO);
        TollSubscription tollSubscription = tollSubscriptionMapper.toEntity(tollSubscriptionDTO);
        tollSubscription = tollSubscriptionRepository.save(tollSubscription);
        return tollSubscriptionMapper.toDto(tollSubscription);
    }

    @Override
    public TollSubscriptionDTO update(TollSubscriptionDTO tollSubscriptionDTO) {
        log.debug("Request to update TollSubscription : {}", tollSubscriptionDTO);
        TollSubscription tollSubscription = tollSubscriptionMapper.toEntity(tollSubscriptionDTO);
        tollSubscription = tollSubscriptionRepository.save(tollSubscription);
        return tollSubscriptionMapper.toDto(tollSubscription);
    }

    @Override
    public Optional<TollSubscriptionDTO> partialUpdate(TollSubscriptionDTO tollSubscriptionDTO) {
        log.debug("Request to partially update TollSubscription : {}", tollSubscriptionDTO);

        return tollSubscriptionRepository
            .findById(tollSubscriptionDTO.getId())
            .map(existingTollSubscription -> {
                tollSubscriptionMapper.partialUpdate(existingTollSubscription, tollSubscriptionDTO);

                return existingTollSubscription;
            })
            .map(tollSubscriptionRepository::save)
            .map(tollSubscriptionMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<TollSubscriptionDTO> findAll(Pageable pageable) {
        log.debug("Request to get all TollSubscriptions");
        return tollSubscriptionRepository.findAll(pageable).map(tollSubscriptionMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<TollSubscriptionDTO> findOne(Long id) {
        log.debug("Request to get TollSubscription : {}", id);
        return tollSubscriptionRepository.findById(id).map(tollSubscriptionMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete TollSubscription : {}", id);
        tollSubscriptionRepository.deleteById(id);
    }
}
