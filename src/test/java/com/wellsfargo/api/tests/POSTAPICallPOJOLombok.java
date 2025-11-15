package com.wellsfargo.api.tests;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.microsoft.playwright.APIRequest;
import com.microsoft.playwright.APIRequestContext;
import com.microsoft.playwright.APIResponse;
import com.microsoft.playwright.Playwright;
import com.microsoft.playwright.options.RequestOptions;
import data.User;
import data.Users;
import org.testng.Assert;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import java.io.IOException;

public class POSTAPICallPOJOLombok {
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

       Users users =  Users.builder()
                .name("Pc")
                .email("check@gmail.com")
                .status("active")
                .gender("male")
                .build();

        APIResponse postAPIResponse = requestContext.post("https://gorest.co.in/public/v2/users",
                RequestOptions.create()
                        .setHeader("Content-Type","application/json")
                        .setHeader("Authorization","Bearer 99676e73a70bda6c3e454cbcac0fa360ebb1883f417a328445c6b19cceebb1d9")
                        .setData(users)

        );

        System.out.println(postAPIResponse.text());
        Assert.assertEquals(postAPIResponse.status(),201);
        Assert.assertEquals(postAPIResponse.statusText(),"Created");
        String apiResponsetext = postAPIResponse.text();
        System.out.println(apiResponsetext);

        //convert responsetext/json to POJO (Deserialization)
        ObjectMapper objectMapper = new ObjectMapper();
        User actuser = objectMapper.readValue(apiResponsetext, User.class);

        Assert.assertEquals(actuser.getName(), users.getName());
        Assert.assertEquals(actuser.getEmail(), users.getEmail());
        Assert.assertEquals(actuser.getGender(), users.getGender());
        Assert.assertEquals(actuser.getStatus(), users.getStatus());



    }

    @AfterTest
    public void teardown() {
        playwright.close();
    }

}
