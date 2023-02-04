package com.isoft.rfid.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.isoft.rfid.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class PeakHourTariffTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(PeakHourTariff.class);
        PeakHourTariff peakHourTariff1 = new PeakHourTariff();
        peakHourTariff1.setId(1L);
        PeakHourTariff peakHourTariff2 = new PeakHourTariff();
        peakHourTariff2.setId(peakHourTariff1.getId());
        assertThat(peakHourTariff1).isEqualTo(peakHourTariff2);
        peakHourTariff2.setId(2L);
        assertThat(peakHourTariff1).isNotEqualTo(peakHourTariff2);
        peakHourTariff1.setId(null);
        assertThat(peakHourTariff1).isNotEqualTo(peakHourTariff2);
    }
}
