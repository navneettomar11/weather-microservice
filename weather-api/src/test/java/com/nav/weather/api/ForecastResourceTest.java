package com.nav.weather.api;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.nav.weather.dto.ForecastRequest;
import com.nav.weather.service.owapi.OpenWeatherApiConfig;
import com.nav.weather.service.owapi.OpenWeatherApiService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.web.client.RestTemplate;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@WebMvcTest(ForecastResource.class)
class ForecastResourceTest {

    @Autowired
    private MockMvc mockMvc;

    private ObjectMapper mapper;

    @MockBean
    private OpenWeatherApiService openWeatherApiService;

    @Mock
    private OpenWeatherApiConfig apiConfig;


    @BeforeEach
    public void setup() {
        mapper = new ObjectMapper();
        RestTemplateBuilder templateBuilder = mock(RestTemplateBuilder.class);
        RestTemplate template = mock(RestTemplate.class);
        when(templateBuilder.build()).thenReturn(template);

        apiConfig = mock(OpenWeatherApiConfig.class);
        when(apiConfig.getApiKey()).thenReturn("xxx-xxx-xxxx");
        when(apiConfig.getApiUrl()).thenReturn("http://sample.openweathermap.org/data/2.5/forecast");

        openWeatherApiService = new OpenWeatherApiService(apiConfig, templateBuilder);
    }
    @Test
    @DisplayName("test forecast resource")
    void testForecastResource() throws Exception{

        ForecastRequest request = new ForecastRequest();
        request.setCity("London");
        MvcResult result = mockMvc.perform(
                MockMvcRequestBuilders.get("/api/v1/forecast")
                        .header("Origin","*")
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .param("city", "London"))
                .andDo(print())
                .andExpect(status().isOk())
                .andReturn();

        String content = result.getResponse().getContentAsString();
        assertNotNull(content);
    }

}