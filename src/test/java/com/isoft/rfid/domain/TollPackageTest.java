package com.isoft.rfid.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.isoft.rfid.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class TollPackageTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(TollPackage.class);
        TollPackage tollPackage1 = new TollPackage();
        tollPackage1.setId(1L);
        TollPackage tollPackage2 = new TollPackage();
        tollPackage2.setId(tollPackage1.getId());
        assertThat(tollPackage1).isEqualTo(tollPackage2);
        tollPackage2.setId(2L);
        assertThat(tollPackage1).isNotEqualTo(tollPackage2);
        tollPackage1.setId(null);
        assertThat(tollPackage1).isNotEqualTo(tollPackage2);
    }
}
