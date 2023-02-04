package com.isoft.rfid.service.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class TollPackageMapperTest {

    private TollPackageMapper tollPackageMapper;

    @BeforeEach
    public void setUp() {
        tollPackageMapper = new TollPackageMapperImpl();
    }
}
