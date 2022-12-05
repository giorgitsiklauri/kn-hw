package org.kn.hw.restclient;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class BPIRestClientTest {

    private static RestTemplate restTemplate;
    private static final String BASE_URL = "https://api.coindesk.com/v1/bpi/";

    @BeforeAll
    public static void setUp() {
        restTemplate = new RestTemplate();
    }

    @Test
    public void getCurrentBitcoinRateThrowsExceptionWithInvalidCurrency() {
        String invalidUrl = BASE_URL + "currentprice/euz.json";

        assertThrows(RestClientException.class, () -> restTemplate.getForEntity(invalidUrl, String.class));
    }

    @Test
    public void getCurrentBitcoinRateReturns200WithValidCurrency() {
        String url = BASE_URL + "currentprice/eur.json";

        ResponseEntity<String> response = restTemplate.getForEntity(url, String.class);

        assertEquals(response.getStatusCodeValue(), 200);
    }

    @Test
    public void getCurrentBitcoinRateHasBodyInTheResponse() {
        String url = BASE_URL + "currentprice/eur.json";

        ResponseEntity<String> response = restTemplate.getForEntity(url, String.class);

        assertNotEquals(response.getBody(), null);
    }

    @Test
    public void getCurrentBitcoinRateReturnsNonNullBPI() throws JsonProcessingException {
        String url = BASE_URL + "currentprice/eur.json";

        ResponseEntity<String> response = restTemplate.getForEntity(url, String.class);
        JsonNode node = new ObjectMapper().readTree(response.getBody()).get("bpi");

        assertNotEquals(node, null);
    }

    @Test
    public void getCurrentBitcoinRateReturnsJSONWithExpectedData() {
        String url = BASE_URL + "currentprice/eur.json";

        ResponseEntity<String> response = restTemplate.getForEntity(url, String.class);
        String json = response.getBody();

        assertTrue(json.contains("bpi"));
        assertTrue(json.contains("disclaimer"));
        assertTrue(json.contains("time"));
    }

    @Test
    public void historicalDataWithInvalidCurrencyThrowsException() {
        BPIRestClient restClient = new BPIRestClient();

        assertThrows(Exception.class, () -> restClient.historicalData("vvv"));
    }

    //N.B. This integration test will not pass if the period (in historicalData())
    // is short and no data is received from the REST endpoint.
    @Test
    public void historicalDataWithValidCurrencyReturnsData() throws NoSuchFieldException, JsonProcessingException {
        BPIRestClient restClient = new BPIRestClient();

        List<Double> rates = restClient.historicalData("eur");

        assertTrue(rates.size() > 0);
    }
}