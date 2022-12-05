package org.kn.hw.restclient;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.kn.hw.model.Historical;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class BPIRestClient {

    private static final String BASE_URL = "https://api.coindesk.com/v1/bpi/";
    private final RestTemplate restTemplate = new RestTemplate();
    private final ObjectMapper mapper = new ObjectMapper();

    public String getCurrentBitcoinRate(String currency) throws Exception {
        ResponseEntity<String> response =
                restTemplate.getForEntity(BASE_URL + "currentprice/" + currency + ".json", String.class);
        return mapper.readTree(response.getBody())
                .get("bpi")
                .get(currency.toUpperCase())
                .get("rate")
                .textValue();
    }

    public double historicalLowest(String currency) throws Exception {
        return historicalData(currency).stream().mapToDouble(Double::valueOf)
                .min()
                .getAsDouble();
    }

    public double historicalHighest(String currency) throws Exception {
        return historicalData(currency)
                .stream().mapToDouble(Double::valueOf)
                .max()
                .getAsDouble();
    }

    public List<Double> historicalData(String currency) throws JsonProcessingException, NoSuchFieldException {
        LocalDate today = LocalDate.now();
        int period = 150; //days

        //You can change 150 with 90, but often there is no data for the last 90 days.
        String url = BASE_URL + "historical/close.json?start=" + today.minusDays(period) + "&end=" + today + "&currency=" + currency;

        ResponseEntity<String> response = restTemplate.getForEntity(url, String.class);

        Historical h = mapper.readValue(response.getBody(), Historical.class);

        if (h.getBpi() == null) throw new NoSuchFieldException("No BPI data for the last " + period + " days.");

        List<Double> values = new ArrayList<>();

        h.getBpi()
                .fieldNames()
                .forEachRemaining(f -> values.add(h.getBpi().get(f).doubleValue()));

        return values;
    }
}
