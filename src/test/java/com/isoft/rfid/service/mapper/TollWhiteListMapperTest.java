package com.isoft.rfid.service.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class TollWhiteListMapperTest {

    private TollWhiteListMapper tollWhiteListMapper;

    @BeforeEach
    public void setUp() {
        tollWhiteListMapper = new TollWhiteListMapperImpl();
    }
}
