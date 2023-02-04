package com.isoft.rfid.service.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class TollSubscriptionMapperTest {

    private TollSubscriptionMapper tollSubscriptionMapper;

    @BeforeEach
    public void setUp() {
        tollSubscriptionMapper = new TollSubscriptionMapperImpl();
    }
}
