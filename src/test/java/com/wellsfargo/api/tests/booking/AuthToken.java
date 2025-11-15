package com.wellsfargo.api.tests.booking;

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
import java.util.HashMap;
import java.util.Map;

public class AuthToken {

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
    public void TokenPOSTAPIResponseTest() throws IOException {
        //POST Call

        APIResponse TokenpostAPIResponse = requestContext.post("https://restful-booker.herokuapp.com/auth",
                RequestOptions.create()
                        .setHeader("Content-Type","application/json")
                        .setHeader("Authorization","Bearer 99676e73a70bda6c3e454cbcac0fa360ebb1883f417a328445c6b19cceebb1d9")
                        .setData("{\n" +
                              "    \"username\" : \"admin\",\n" +
                              "    \"password\" : \"password123\"\n" +
                              "}")


        );

        System.out.println(TokenpostAPIResponse.text());
        Assert.assertEquals(TokenpostAPIResponse.status(),200);
        Assert.assertEquals(TokenpostAPIResponse.statusText(),"OK");


    }

    @AfterTest
    public void teardown() {
        playwright.close();
    }

}
