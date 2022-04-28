package de.superchat.crm.placeholder.impl;

import com.fasterxml.jackson.databind.JsonNode;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

@Service
public class BitcoinPriceService {

    /**
     * obtains current bitcoin price
     *
     * @return bitcoin price in string
     */
    public Double getCurrentBitcoinPrice() throws RestClientException {
        String url = "https://rest.coinapi.io/v1/exchangerate/BTC/USD?apikey=D9FB4411-46E1-47B5-9472-20B57B55745F";
        RestTemplate restTemplate = new RestTemplate();
        JsonNode result = restTemplate.getForEntity(url, JsonNode.class).getBody();
        if (result != null && result.has("rate"))
            return result.get("rate").asDouble();
        else throw new RestClientException("Error on fetching data for bitcoin price");


    }
}
