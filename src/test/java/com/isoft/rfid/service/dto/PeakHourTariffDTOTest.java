package com.isoft.rfid.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import com.isoft.rfid.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class PeakHourTariffDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(PeakHourTariffDTO.class);
        PeakHourTariffDTO peakHourTariffDTO1 = new PeakHourTariffDTO();
        peakHourTariffDTO1.setId(1L);
        PeakHourTariffDTO peakHourTariffDTO2 = new PeakHourTariffDTO();
        assertThat(peakHourTariffDTO1).isNotEqualTo(peakHourTariffDTO2);
        peakHourTariffDTO2.setId(peakHourTariffDTO1.getId());
        assertThat(peakHourTariffDTO1).isEqualTo(peakHourTariffDTO2);
        peakHourTariffDTO2.setId(2L);
        assertThat(peakHourTariffDTO1).isNotEqualTo(peakHourTariffDTO2);
        peakHourTariffDTO1.setId(null);
        assertThat(peakHourTariffDTO1).isNotEqualTo(peakHourTariffDTO2);
    }
}
