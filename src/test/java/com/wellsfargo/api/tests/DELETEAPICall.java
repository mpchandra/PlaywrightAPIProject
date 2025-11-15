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

public class DELETEAPICall {
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
        //POST Call - Add Users
        System.out.println("POST Call - Add Users");
       Users users =  Users.builder()
                .name("MPwecqwmm231211")
                .email("cmhweqw23weck1231123@gmail.com")
                .status("active")
                .gender("female")
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

        //DELETE Call - DELETE User
        System.out.println("DELETE Call - DELETE User");
        APIResponse deleteAPIResponse = requestContext.delete("https://gorest.co.in/public/v2/users/"+actuser.getId(),
                RequestOptions.create()
                        .setHeader("Content-Type","application/json")
                        .setHeader("Authorization","Bearer 99676e73a70bda6c3e454cbcac0fa360ebb1883f417a328445c6b19cceebb1d9")
        );

        System.out.println("DELETE response body ---?" + deleteAPIResponse.text());
        Assert.assertEquals(deleteAPIResponse.status(),204);
        Assert.assertEquals(deleteAPIResponse.statusText(),"No Content");

        //GET CALL -  GEt Users Details
        System.out.println("Get Call - Get User Details");
        APIResponse apiGETResponse = requestContext.get("https://gorest.co.in/public/v2/users/"+actuser.getId());
        System.out.println(apiGETResponse.text());
        Assert.assertEquals(apiGETResponse.status(),404);
        Assert.assertEquals(apiGETResponse.statusText(),"Not Found");
        Assert.assertTrue(apiGETResponse.text().contains("Resource not found"));


    }

    @AfterTest
    public void teardown() {
        playwright.close();
    }

}
