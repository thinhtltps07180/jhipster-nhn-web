package com.nhn.jhipster.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.nhn.jhipster.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

public class PokerProfileTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(PokerProfile.class);
        PokerProfile pokerProfile1 = new PokerProfile();
        pokerProfile1.setId(1L);
        PokerProfile pokerProfile2 = new PokerProfile();
        pokerProfile2.setId(pokerProfile1.getId());
        assertThat(pokerProfile1).isEqualTo(pokerProfile2);
        pokerProfile2.setId(2L);
        assertThat(pokerProfile1).isNotEqualTo(pokerProfile2);
        pokerProfile1.setId(null);
        assertThat(pokerProfile1).isNotEqualTo(pokerProfile2);
    }
}
