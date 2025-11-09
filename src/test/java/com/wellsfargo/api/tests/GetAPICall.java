package com.wellsfargo.api.tests;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.microsoft.playwright.APIRequest;
import com.microsoft.playwright.APIRequestContext;
import com.microsoft.playwright.APIResponse;
import com.microsoft.playwright.Playwright;
import com.microsoft.playwright.options.RequestOptions;
import org.testng.Assert;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import java.io.IOException;
import java.util.Map;

public class GetAPICall {
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
    public void getUsersAPIwithQueryParam() throws IOException {
        APIResponse apiResponse = requestContext.get("https://gorest.co.in/public/v2/users",

                RequestOptions.create()
                        .setQueryParam("id","8229724")
                        .setQueryParam("gender", "female"));

        int statusCode = apiResponse.status();
        System.out.println("GET REquest with Query Param " + statusCode);
        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode jsonResponse =  objectMapper.readTree(apiResponse.body());
        System.out.println(jsonResponse.get(0));

    }

    @Test
    public void getUsersAPITest() throws IOException {

    APIResponse apiResponse = requestContext.get("https://gorest.co.in/public/v2/users");

    System.out.println("-----------------API Response-------------------");
    System.out.println(apiResponse.status());
    System.out.println(apiResponse.statusText());
    System.out.println(apiResponse.text()); //to print response as String
    Assert.assertEquals(apiResponse.status(), 200);
    Assert.assertEquals(apiResponse.ok(), true); //apiResponse.ok() - returns true if status code is 200-299

    Map<String, String> headersmap = apiResponse.headers();
    Assert.assertEquals(headersmap.get("x-content-type-options"), "nosniff");

     ObjectMapper objectMapper = new ObjectMapper();
     JsonNode jsonResponse =  objectMapper.readTree(apiResponse.body());
     System.out.println(jsonResponse.toPrettyString());

     int maleCount = 0;
     for (JsonNode node : jsonResponse) {           //for (DataType variable : collection) {  // use variable   }
            String gender = node.path("gender").asText(); //used node.path
            if (gender.equalsIgnoreCase("male")) {
                maleCount++;
            }
        }
        System.out.println("Number of male records: " + maleCount);
    }

    @AfterTest
    public void teardown() {
        playwright.close();
    }

}
