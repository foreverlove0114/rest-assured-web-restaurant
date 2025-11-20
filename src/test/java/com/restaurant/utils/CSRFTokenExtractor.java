package com.restaurant.utils;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

public class CSRFTokenExtractor {

    public static String extractCSRFToken(String htmlContent) {
        Document doc = Jsoup.parse(htmlContent);
        Elements csrfInput = doc.select("input[name=csrf_token]");
        return csrfInput.isEmpty() ? null : csrfInput.first().attr("value");
    }

    public static String extractFromResponse(io.restassured.response.Response response) {
        return extractCSRFToken(response.getBody().asString());
    }
}
