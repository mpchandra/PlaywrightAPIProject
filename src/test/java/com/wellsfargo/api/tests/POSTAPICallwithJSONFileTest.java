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

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.HashMap;
import java.util.Map;

public class POSTAPICallwithJSONFileTest {
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
        byte[] arr=null;
        File file = new File("./src/test/data/createuser.json");
        arr = Files.readAllBytes(file.toPath());

        APIResponse postAPIResponse = requestContext.post("https://gorest.co.in/public/v2/users",
                RequestOptions.create()
                        .setHeader("Content-Type","application/json")
                        .setHeader("Authorization","Bearer 99676e73a70bda6c3e454cbcac0fa360ebb1883f417a328445c6b19cceebb1d9")
                        .setData(arr)

        );

        System.out.println(postAPIResponse.text());
        Assert.assertEquals(postAPIResponse.status(),201);
        Assert.assertEquals(postAPIResponse.statusText(),"Created");
        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode jsonNode = objectMapper.readTree(postAPIResponse.body());
        String userid = jsonNode.get("id").asText();


    }

    @AfterTest
    public void teardown() {
        playwright.close();
    }

}
