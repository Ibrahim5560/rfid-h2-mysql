package com.isoft.rfid.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.isoft.rfid.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class TollSubscriptionTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(TollSubscription.class);
        TollSubscription tollSubscription1 = new TollSubscription();
        tollSubscription1.setId(1L);
        TollSubscription tollSubscription2 = new TollSubscription();
        tollSubscription2.setId(tollSubscription1.getId());
        assertThat(tollSubscription1).isEqualTo(tollSubscription2);
        tollSubscription2.setId(2L);
        assertThat(tollSubscription1).isNotEqualTo(tollSubscription2);
        tollSubscription1.setId(null);
        assertThat(tollSubscription1).isNotEqualTo(tollSubscription2);
    }
}
