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

public class BookingTest  {

    Playwright playwright;
    APIRequest request;
    APIRequestContext requestContext;

    private static String token=null;

    @BeforeTest
    public void setup() throws IOException {
        playwright = Playwright.create();
        request = playwright.request();
        requestContext = request.newContext();
        APIResponse TokenpostAPIResponse = requestContext.post("https://restful-booker.herokuapp.com/auth",
                RequestOptions.create()
                        .setHeader("Content-Type","application/json")
                        .setHeader("Authorization","Bearer 99676e73a70bda6c3e454cbcac0fa360ebb1883f417a328445c6b19cceebb1d9")
                        .setData("{\n" +
                                "    \"username\" : \"admin\",\n" +
                                "    \"password\" : \"password123\"\n" +
                                "}")
        );

        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode jsonNode = objectMapper.readTree(TokenpostAPIResponse.body());
        token = jsonNode.get("token").asText();
        System.out.println("TOKEN ---?"+token);

    }

    @Test
    public void BookingDeleteAPIResponseTest() throws IOException {
        //DElete Call

        APIResponse bookingdeleteAPIResponse = requestContext.delete("https://restful-booker.herokuapp.com/booking/4",
                RequestOptions.create()
                        .setHeader("Content-Type","application/json")
                        .setHeader("Cookie","token="+token)
        );

        System.out.println(bookingdeleteAPIResponse.text());
        Assert.assertEquals(bookingdeleteAPIResponse.status(),201);
        Assert.assertEquals(bookingdeleteAPIResponse.statusText(),"Created");

    }

    @AfterTest
    public void teardown() {
        playwright.close();
    }
}
