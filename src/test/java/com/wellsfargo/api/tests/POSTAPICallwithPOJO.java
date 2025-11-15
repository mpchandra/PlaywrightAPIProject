package com.wellsfargo.api.tests;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.microsoft.playwright.APIRequest;
import com.microsoft.playwright.APIRequestContext;
import com.microsoft.playwright.APIResponse;
import com.microsoft.playwright.Playwright;
import com.microsoft.playwright.options.RequestOptions;
import data.User;
import org.testng.Assert;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

public class POSTAPICallwithPOJO {
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
        User user = new User("Naveen23133","nv23143@gmail.com","male","active");

        APIResponse postAPIResponse = requestContext.post("https://gorest.co.in/public/v2/users",
                RequestOptions.create()
                        .setHeader("Content-Type","application/json")
                        .setHeader("Authorization","Bearer 99676e73a70bda6c3e454cbcac0fa360ebb1883f417a328445c6b19cceebb1d9")
                        .setData(user)

        );

        System.out.println(postAPIResponse.text());
        Assert.assertEquals(postAPIResponse.status(),201);
        Assert.assertEquals(postAPIResponse.statusText(),"Created");
        String apiResponsetext = postAPIResponse.text();
        System.out.println(apiResponsetext);

        //convert responsetext/json to POJO (Deserialization)
        ObjectMapper objectMapper = new ObjectMapper();
        User actuser = objectMapper.readValue(apiResponsetext, User.class);

        Assert.assertEquals(actuser.getName(), user.getName());
        Assert.assertEquals(actuser.getEmail(), user.getEmail());
        Assert.assertEquals(actuser.getGender(), user.getGender());
        Assert.assertEquals(actuser.getStatus(), user.getStatus());



    }

    @AfterTest
    public void teardown() {
        playwright.close();
    }

}
