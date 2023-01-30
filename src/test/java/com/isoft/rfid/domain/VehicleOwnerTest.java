package com.isoft.rfid.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.isoft.rfid.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class VehicleOwnerTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(VehicleOwner.class);
        VehicleOwner vehicleOwner1 = new VehicleOwner();
        vehicleOwner1.setId(1L);
        VehicleOwner vehicleOwner2 = new VehicleOwner();
        vehicleOwner2.setId(vehicleOwner1.getId());
        assertThat(vehicleOwner1).isEqualTo(vehicleOwner2);
        vehicleOwner2.setId(2L);
        assertThat(vehicleOwner1).isNotEqualTo(vehicleOwner2);
        vehicleOwner1.setId(null);
        assertThat(vehicleOwner1).isNotEqualTo(vehicleOwner2);
    }
}
