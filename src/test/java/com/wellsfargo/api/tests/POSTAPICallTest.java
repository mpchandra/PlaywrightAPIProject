package com.wellsfargo.api.tests;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.microsoft.playwright.*;
import com.microsoft.playwright.options.RequestOptions;
import org.testng.Assert;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class POSTAPICallTest {
    Playwright playwright;
    APIRequest request;
    APIRequestContext requestContext;

    @BeforeTest
    public void setup() {
        playwright = Playwright.create();
        request = playwright.request();
        requestContext = request.newContext();
    }

    @Test
    public void POSTAPIResponseTest() throws IOException {
        //POST Call
        Map<String, Object> data = new HashMap<String, Object>();
        data.put("name","pcm1234");
        data.put("email","pc1234@resisolve.com");
        data.put("gender","female");
        data.put("status","active");
        APIResponse postAPIResponse = requestContext.post("https://gorest.co.in/public/v2/users",
                RequestOptions.create()
                        .setHeader("Content-Type","application/json")
                        .setHeader("Authorization","Bearer 99676e73a70bda6c3e454cbcac0fa360ebb1883f417a328445c6b19cceebb1d9")
//                      .setData("{\n" +   //one way of setting request body
//                                "    \"name\": \"PoornaChandra11\",\n" +
//                                "    \"email\": \"poorna1c1@redi.example\",\n" +
//                                "    \"gender\": \"male\",\n" +
//                                "    \"status\": \"active\"\n" +
//                                "}")
                        .setData(data)

        );

        System.out.println(postAPIResponse.text());
        Assert.assertEquals(postAPIResponse.status(),201);
        Assert.assertEquals(postAPIResponse.statusText(),"Created");
        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode jsonNode = objectMapper.readTree(postAPIResponse.body());
        String userid = jsonNode.get("id").asText();

        //GET Call
        APIResponse getAPIResponse = requestContext.get("https://gorest.co.in/public/v2/users/"+userid,
                RequestOptions.create()
                        .setHeader("Content-Type","application/json")
                        .setHeader("Authorization","Bearer 99676e73a70bda6c3e454cbcac0fa360ebb1883f417a328445c6b19cceebb1d9"));

        JsonNode jsonNode1 = objectMapper.readTree(getAPIResponse.body());
        System.out.println(jsonNode1.toPrettyString());

        Assert.assertEquals(getAPIResponse.status(),200);
        Assert.assertEquals(getAPIResponse.statusText(),"OK");


    }

    @AfterTest
    public void teardown() {
        playwright.close();
    }

}
