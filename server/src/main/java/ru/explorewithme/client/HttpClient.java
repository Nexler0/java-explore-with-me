package ru.explorewithme.client;

import java.net.URI;
import java.net.http.HttpRequest;

public class HttpClient {

    public static void sendStatistic(String json) {
        java.net.http.HttpClient client = java.net.http.HttpClient.newHttpClient();

        URI url = URI.create("${ewm-statistic.url}/hit");
        HttpRequest request = HttpRequest.newBuilder()
                .uri(url)
                .POST(HttpRequest.BodyPublishers.ofString(json))
                .build();
    }
}
