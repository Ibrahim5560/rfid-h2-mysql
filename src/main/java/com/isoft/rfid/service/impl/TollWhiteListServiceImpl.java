package com.isoft.rfid.service.impl;

import com.isoft.rfid.domain.TollWhiteList;
import com.isoft.rfid.repository.TollWhiteListRepository;
import com.isoft.rfid.service.TollWhiteListService;
import com.isoft.rfid.service.dto.TollWhiteListDTO;
import com.isoft.rfid.service.mapper.TollWhiteListMapper;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link TollWhiteList}.
 */
@Service
@Transactional
public class TollWhiteListServiceImpl implements TollWhiteListService {

    private final Logger log = LoggerFactory.getLogger(TollWhiteListServiceImpl.class);

    private final TollWhiteListRepository tollWhiteListRepository;

    private final TollWhiteListMapper tollWhiteListMapper;

    public TollWhiteListServiceImpl(TollWhiteListRepository tollWhiteListRepository, TollWhiteListMapper tollWhiteListMapper) {
        this.tollWhiteListRepository = tollWhiteListRepository;
        this.tollWhiteListMapper = tollWhiteListMapper;
    }

    @Override
    public TollWhiteListDTO save(TollWhiteListDTO tollWhiteListDTO) {
        log.debug("Request to save TollWhiteList : {}", tollWhiteListDTO);
        TollWhiteList tollWhiteList = tollWhiteListMapper.toEntity(tollWhiteListDTO);
        tollWhiteList = tollWhiteListRepository.save(tollWhiteList);
        return tollWhiteListMapper.toDto(tollWhiteList);
    }

    @Override
    public TollWhiteListDTO update(TollWhiteListDTO tollWhiteListDTO) {
        log.debug("Request to update TollWhiteList : {}", tollWhiteListDTO);
        TollWhiteList tollWhiteList = tollWhiteListMapper.toEntity(tollWhiteListDTO);
        tollWhiteList = tollWhiteListRepository.save(tollWhiteList);
        return tollWhiteListMapper.toDto(tollWhiteList);
    }

    @Override
    public Optional<TollWhiteListDTO> partialUpdate(TollWhiteListDTO tollWhiteListDTO) {
        log.debug("Request to partially update TollWhiteList : {}", tollWhiteListDTO);

        return tollWhiteListRepository
            .findById(tollWhiteListDTO.getId())
            .map(existingTollWhiteList -> {
                tollWhiteListMapper.partialUpdate(existingTollWhiteList, tollWhiteListDTO);

                return existingTollWhiteList;
            })
            .map(tollWhiteListRepository::save)
            .map(tollWhiteListMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<TollWhiteListDTO> findAll(Pageable pageable) {
        log.debug("Request to get all TollWhiteLists");
        return tollWhiteListRepository.findAll(pageable).map(tollWhiteListMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<TollWhiteListDTO> findOne(Long id) {
        log.debug("Request to get TollWhiteList : {}", id);
        return tollWhiteListRepository.findById(id).map(tollWhiteListMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete TollWhiteList : {}", id);
        tollWhiteListRepository.deleteById(id);
    }
}
