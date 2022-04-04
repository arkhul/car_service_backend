package com.kodilla.car_service.controller;

import com.kodilla.car_service.dto.CostDto;
import com.kodilla.car_service.mapper.CostMapper;
import com.kodilla.car_service.service.CostService;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.web.SpringJUnitWebConfig;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.math.BigDecimal;
import java.util.List;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

@SpringJUnitWebConfig
@WebMvcTest(CostController.class)
class CostControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private CostController costController;

    @MockBean
    private CostMapper costMapper;

    @MockBean
    private CostService costService;

    @Test
    void getCostsTest() throws Exception {
        // Given
        when(costController.getCosts()).thenReturn(
                List.of(new CostDto(1L, new BigDecimal("100"), new BigDecimal("200"),
                                new BigDecimal("300"))));

        // When & Then
        mockMvc
                .perform(get("/v1/costs")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.jsonPath("$", Matchers.hasSize(1)))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].id", Matchers.is(1)))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].partsCost", Matchers.is(100)))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].labourCost", Matchers.is(200)))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].totalCost", Matchers.is(300)));
    }
}