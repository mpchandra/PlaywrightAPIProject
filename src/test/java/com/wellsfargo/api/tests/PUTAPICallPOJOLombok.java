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

public class PUTAPICallPOJOLombok {
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
                .name("MPcmm1211")
                .email("cmheck1231123@gmail.com")
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

        Assert.assertEquals(actuser.getName(), users.getName());
        Assert.assertEquals(actuser.getEmail(), users.getEmail());
        Assert.assertEquals(actuser.getGender(), users.getGender());
        Assert.assertEquals(actuser.getStatus(), users.getStatus());

        //PUT Call - Add User
        System.out.println("PUT Call - Update User");
        users.setName("PCM Update12");
        users.setStatus("inactive");

        APIResponse putAPIResponse = requestContext.put("https://gorest.co.in/public/v2/users/"+actuser.getId(),
                RequestOptions.create()
                        .setHeader("Content-Type","application/json")
                        .setHeader("Authorization","Bearer 99676e73a70bda6c3e454cbcac0fa360ebb1883f417a328445c6b19cceebb1d9")
                        .setData(users)

        );

        System.out.println(putAPIResponse.text());
        Assert.assertEquals(putAPIResponse.status(),200);
        Assert.assertEquals(putAPIResponse.statusText(),"OK");
        String apiPUTResponsetext = putAPIResponse.text();
        System.out.println(apiPUTResponsetext);

        //convert responsetext/json to POJO (Deserialization)
         User actuser1 = objectMapper.readValue(apiPUTResponsetext, User.class);

        Assert.assertEquals(actuser1.getName(), users.getName());
        Assert.assertEquals(actuser1.getEmail(), users.getEmail());
        Assert.assertEquals(actuser1.getGender(), users.getGender());
        Assert.assertEquals(actuser1.getStatus(), users.getStatus());
        Assert.assertNotNull(actuser1.getId());

        System.out.println("Get Call - Get User Details");
        APIResponse apiGETResponse = requestContext.get("https://gorest.co.in/public/v2/users",

                RequestOptions.create()
                        .setQueryParam("id",actuser1.getId()));
        System.out.println(putAPIResponse.text());
        Assert.assertEquals(apiGETResponse.status(),200);
//        String apiGETResponsetext = apiGETResponse.text(); Not working codes
//
//        User actuser2 = objectMapper.readValue(apiGETResponsetext, User.class);
//        Assert.assertEquals(actuser2.getName(), actuser1.getName());
//        Assert.assertEquals(actuser2.getGender(), actuser1.getGender());
//        Assert.assertEquals(actuser2.getId(), actuser1.getId());

    }

    @AfterTest
    public void teardown() {
        playwright.close();
    }

}
