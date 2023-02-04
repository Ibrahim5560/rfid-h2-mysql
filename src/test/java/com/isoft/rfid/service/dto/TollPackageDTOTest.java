package com.isoft.rfid.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import com.isoft.rfid.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class TollPackageDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(TollPackageDTO.class);
        TollPackageDTO tollPackageDTO1 = new TollPackageDTO();
        tollPackageDTO1.setId(1L);
        TollPackageDTO tollPackageDTO2 = new TollPackageDTO();
        assertThat(tollPackageDTO1).isNotEqualTo(tollPackageDTO2);
        tollPackageDTO2.setId(tollPackageDTO1.getId());
        assertThat(tollPackageDTO1).isEqualTo(tollPackageDTO2);
        tollPackageDTO2.setId(2L);
        assertThat(tollPackageDTO1).isNotEqualTo(tollPackageDTO2);
        tollPackageDTO1.setId(null);
        assertThat(tollPackageDTO1).isNotEqualTo(tollPackageDTO2);
    }
}
