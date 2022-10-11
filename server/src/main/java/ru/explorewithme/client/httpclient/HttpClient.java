package ru.explorewithme.client.httpclient;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import lombok.extern.slf4j.Slf4j;
import ru.explorewithme.dto.statisticDto.StatisticDtoShort;
import ru.explorewithme.exception.ForbiddenException;

import java.io.IOException;
import java.lang.reflect.Type;
import java.net.URI;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.List;

/**
 * Клиент для обращения к сервису статистики
 *
 * @see HttpClient
 */
@Slf4j
public class HttpClient {
    /**
     * Отправление статистики на сервер
     *
     * @param json тело запроса
     */
    public static void sendStatistic(String uriServer, String json) {
        java.net.http.HttpClient client = java.net.http.HttpClient.newHttpClient();
        URI uri = URI.create(uriServer + "/hit");
        HttpRequest request = HttpRequest.newBuilder()
                .uri(uri)
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

    /**
     * Получение просмотров с серверс статистики
     *
     * @param uris   Список uri для поиска
     * @param unique статус уникальности, поиск по уникальным IP
     */
    public static Integer getViews(String uriServer, List<String> uris, Boolean unique) {
        java.net.http.HttpClient client = java.net.http.HttpClient.newBuilder().build();
        Gson gson = new GsonBuilder().create();

        URI uri = URI.create(uriServer + "/stats?" +
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
