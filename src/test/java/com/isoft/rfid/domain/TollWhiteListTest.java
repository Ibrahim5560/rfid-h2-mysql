package com.isoft.rfid.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.isoft.rfid.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class TollWhiteListTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(TollWhiteList.class);
        TollWhiteList tollWhiteList1 = new TollWhiteList();
        tollWhiteList1.setId(1L);
        TollWhiteList tollWhiteList2 = new TollWhiteList();
        tollWhiteList2.setId(tollWhiteList1.getId());
        assertThat(tollWhiteList1).isEqualTo(tollWhiteList2);
        tollWhiteList2.setId(2L);
        assertThat(tollWhiteList1).isNotEqualTo(tollWhiteList2);
        tollWhiteList1.setId(null);
        assertThat(tollWhiteList1).isNotEqualTo(tollWhiteList2);
    }
}
