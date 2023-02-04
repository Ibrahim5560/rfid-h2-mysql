package com.isoft.rfid.service.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class PeakHourTariffMapperTest {

    private PeakHourTariffMapper peakHourTariffMapper;

    @BeforeEach
    public void setUp() {
        peakHourTariffMapper = new PeakHourTariffMapperImpl();
    }
}
