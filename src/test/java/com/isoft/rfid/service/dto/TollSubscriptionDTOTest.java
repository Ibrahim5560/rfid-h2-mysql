package com.isoft.rfid.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import com.isoft.rfid.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class TollSubscriptionDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(TollSubscriptionDTO.class);
        TollSubscriptionDTO tollSubscriptionDTO1 = new TollSubscriptionDTO();
        tollSubscriptionDTO1.setId(1L);
        TollSubscriptionDTO tollSubscriptionDTO2 = new TollSubscriptionDTO();
        assertThat(tollSubscriptionDTO1).isNotEqualTo(tollSubscriptionDTO2);
        tollSubscriptionDTO2.setId(tollSubscriptionDTO1.getId());
        assertThat(tollSubscriptionDTO1).isEqualTo(tollSubscriptionDTO2);
        tollSubscriptionDTO2.setId(2L);
        assertThat(tollSubscriptionDTO1).isNotEqualTo(tollSubscriptionDTO2);
        tollSubscriptionDTO1.setId(null);
        assertThat(tollSubscriptionDTO1).isNotEqualTo(tollSubscriptionDTO2);
    }
}
