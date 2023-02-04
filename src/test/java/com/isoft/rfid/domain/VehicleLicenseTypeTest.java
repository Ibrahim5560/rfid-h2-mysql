package com.isoft.rfid.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.isoft.rfid.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class VehicleLicenseTypeTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(VehicleLicenseType.class);
        VehicleLicenseType vehicleLicenseType1 = new VehicleLicenseType();
        vehicleLicenseType1.setId(1L);
        VehicleLicenseType vehicleLicenseType2 = new VehicleLicenseType();
        vehicleLicenseType2.setId(vehicleLicenseType1.getId());
        assertThat(vehicleLicenseType1).isEqualTo(vehicleLicenseType2);
        vehicleLicenseType2.setId(2L);
        assertThat(vehicleLicenseType1).isNotEqualTo(vehicleLicenseType2);
        vehicleLicenseType1.setId(null);
        assertThat(vehicleLicenseType1).isNotEqualTo(vehicleLicenseType2);
    }
}
