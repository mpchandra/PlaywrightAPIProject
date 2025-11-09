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
import java.util.Map;

public class GetAPICallapiResponseDispose {
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
    public void getUsersAPIResponseDisposeTest() throws IOException {

        APIResponse apiResponse = requestContext.get("https://gorest.co.in/public/v2/users");

        System.out.println("-----------------API Response before Dispose-------------------");
        System.out.println(apiResponse.status());
        System.out.println(apiResponse.statusText());
        System.out.println(apiResponse.url());
        System.out.println(apiResponse.headers());
        System.out.println(apiResponse.text()); //to print response as String


        System.out.println("-----------------API Response after Dispose-------------------");
        apiResponse.dispose(); // remove the apiresponse body alone not url, headers, status code and status text
        System.out.println(apiResponse.status());
        System.out.println(apiResponse.statusText());
        System.out.println(apiResponse.url()); //to print response as String
        System.out.println(apiResponse.headers());

        try {
            System.out.println(apiResponse.text()); //to print response as String
        }
        catch (PlaywrightException e)
        {
            System.out.println("Response body has been disposed" + e);
        }

    }

    @AfterTest
    public void teardown() {
        playwright.close();
    }

}
