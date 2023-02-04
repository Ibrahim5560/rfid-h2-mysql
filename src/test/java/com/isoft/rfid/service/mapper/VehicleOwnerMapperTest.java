package com.isoft.rfid.service.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class VehicleOwnerMapperTest {

    private VehicleOwnerMapper vehicleOwnerMapper;

    @BeforeEach
    public void setUp() {
        vehicleOwnerMapper = new VehicleOwnerMapperImpl();
    }
}
