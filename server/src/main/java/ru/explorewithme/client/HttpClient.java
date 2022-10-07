package ru.explorewithme.client;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import lombok.extern.slf4j.Slf4j;
import ru.explorewithme.event.statisticDto.StatisticDtoShort;
import ru.explorewithme.exception.ForbiddenException;

import java.io.IOException;
import java.lang.reflect.Type;
import java.net.URI;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.List;

@Slf4j
public class HttpClient {

    public static void sendStatistic(String json) {
        java.net.http.HttpClient client = java.net.http.HttpClient.newHttpClient();

        URI url = URI.create("http://statistic:9090" + "/hit");
        HttpRequest request = HttpRequest.newBuilder()
                .uri(url)
                .POST(HttpRequest.BodyPublishers.ofString(json))
                .header("Content-Type", "application/json")
                .build();
        try {
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            log.info("HttpClient sendStatistic: {}", response);
        } catch (IOException | InterruptedException e) {
            throw new ForbiddenException("Сервер не отвечает");
        }
    }

    public static Integer getViews(List<String> uris, Boolean unique) {
        java.net.http.HttpClient client = java.net.http.HttpClient.newBuilder().build();
        Gson gson = new GsonBuilder().create();

        URI uri = URI.create("http://statistic:9090" + "/stats?" +
                "uris=" + uris.toString().substring(1, uris.toString().length() - 1) +
                "&unique=" + unique);
        log.debug("HttpClient URI: {}", uri);
        HttpRequest request = HttpRequest.newBuilder()
                .GET()
                .uri(uri)
                .build();
        String result = "";

        try {
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            result = response.body();
        } catch (InterruptedException | IOException e) {
            throw new ForbiddenException("Ошибка ответа сервера статистики");
        }
        log.info("HttpClient getViews: {}", result);
        Type statisticDtoType = new TypeToken<List<StatisticDtoShort>>() {
        }.getType();
        List<StatisticDtoShort> statistic = gson.fromJson(result, statisticDtoType);
        return statistic.get(0).getHits();
    }
}
