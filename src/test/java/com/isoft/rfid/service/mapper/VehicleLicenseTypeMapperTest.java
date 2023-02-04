package com.isoft.rfid.service.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class VehicleLicenseTypeMapperTest {

    private VehicleLicenseTypeMapper vehicleLicenseTypeMapper;

    @BeforeEach
    public void setUp() {
        vehicleLicenseTypeMapper = new VehicleLicenseTypeMapperImpl();
    }
}
