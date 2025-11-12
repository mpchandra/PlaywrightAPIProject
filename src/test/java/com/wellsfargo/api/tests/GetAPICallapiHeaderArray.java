package com.wellsfargo.api.tests;

import com.microsoft.playwright.*;
import com.microsoft.playwright.options.HttpHeader;
import org.testng.Assert;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import java.io.IOException;
import java.util.List;

public class GetAPICallapiHeaderArray {
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
    public void getUsersAPIHeadersArrayTest() throws IOException {

        APIResponse apiResponse = requestContext.get("https://gorest.co.in/public/v2/users");
        System.out.println(apiResponse.status());
        List<HttpHeader> headersArray = apiResponse.headersArray();
        headersArray.forEach(headarr -> System.out.println(headarr.name+","+headarr.value));

    }

    @AfterTest
    public void teardown() {
        playwright.close();
    }

}
