package com.isoft.rfid.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import com.isoft.rfid.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class VehicleOwnerDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(VehicleOwnerDTO.class);
        VehicleOwnerDTO vehicleOwnerDTO1 = new VehicleOwnerDTO();
        vehicleOwnerDTO1.setId(1L);
        VehicleOwnerDTO vehicleOwnerDTO2 = new VehicleOwnerDTO();
        assertThat(vehicleOwnerDTO1).isNotEqualTo(vehicleOwnerDTO2);
        vehicleOwnerDTO2.setId(vehicleOwnerDTO1.getId());
        assertThat(vehicleOwnerDTO1).isEqualTo(vehicleOwnerDTO2);
        vehicleOwnerDTO2.setId(2L);
        assertThat(vehicleOwnerDTO1).isNotEqualTo(vehicleOwnerDTO2);
        vehicleOwnerDTO1.setId(null);
        assertThat(vehicleOwnerDTO1).isNotEqualTo(vehicleOwnerDTO2);
    }
}
