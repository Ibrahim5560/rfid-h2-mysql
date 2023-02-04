package com.isoft.rfid.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import com.isoft.rfid.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class TollWhiteListDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(TollWhiteListDTO.class);
        TollWhiteListDTO tollWhiteListDTO1 = new TollWhiteListDTO();
        tollWhiteListDTO1.setId(1L);
        TollWhiteListDTO tollWhiteListDTO2 = new TollWhiteListDTO();
        assertThat(tollWhiteListDTO1).isNotEqualTo(tollWhiteListDTO2);
        tollWhiteListDTO2.setId(tollWhiteListDTO1.getId());
        assertThat(tollWhiteListDTO1).isEqualTo(tollWhiteListDTO2);
        tollWhiteListDTO2.setId(2L);
        assertThat(tollWhiteListDTO1).isNotEqualTo(tollWhiteListDTO2);
        tollWhiteListDTO1.setId(null);
        assertThat(tollWhiteListDTO1).isNotEqualTo(tollWhiteListDTO2);
    }
}
