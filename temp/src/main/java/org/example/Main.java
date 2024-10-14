package org.example;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Scanner;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.CompletableFuture;

public class Main {
    private static final String API_KEY = "91f9b885d15f6c96dc70008b1c134102";
    private static final String BASE_URL = "https://api.openweathermap.org/data/2.5/weather";

    public static void main(String[] args) throws ExecutionException, InterruptedException{
        Scanner scan = new Scanner(System.in);

        CompletableFuture<Void> future = CompletableFuture.runAsync(() -> {
            try {
                while (true) {
                    System.out.println("Если хотите покинуть программу введите слово 'Выход'");
                    System.out.print("Введите город: ");
                    String city = scan.nextLine();
                    if (city.equals("Выход") || city.equals("выход"))
                        return;
                    String urlString = BASE_URL + "?q=" + city + "&appid=" + API_KEY + "&units=metric&lang=ru";
                    URL url = new URL(urlString);
                    HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                    conn.setRequestMethod("GET");

                    int responseCode = conn.getResponseCode();
                    if (responseCode == 404) {
                        System.out.println("ERROR404 \nГород не найден. Пожалуйста, попробуйте ещё раз!");
                        continue;
//                        return;
                    }
                    else if (responseCode == 400){
                        System.out.println("ERROR400 \nНе правильно сформирован запрос! Пожалуйста, проверьте запрос и повторите попытку.");
                        continue;
                    }
                    else if (responseCode == 401){
                        System.out.println("ERROR401 \nНе правильно сформирован API - ключ или есть проблемы с аутентификацией! Пожалуйста, проверьте API - ключ и повторите попытку.");
                        continue;
                    }
                    else if (responseCode == 500){
                        System.out.println("ERROR500 \nНа сревере временные проблемы! Пожалуйста, попробуйте позже.");
                        return;
                    }
                    BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
                    String imputLine;
                    StringBuilder response = new StringBuilder();
                    while ((imputLine = in.readLine()) != null) {
                        response.append(imputLine);
                    }
                    in.close();
                    conn.disconnect();

//                    System.out.println(response);

                    JSONObject jsoneResponse = new JSONObject(response.toString());
                    JSONObject main = jsoneResponse.getJSONObject("main");
                    JSONObject wind = jsoneResponse.getJSONObject("wind");
                    JSONObject weather = jsoneResponse.getJSONArray("weather").getJSONObject(0);
                    double temp = main.getDouble("temp");
                    int humidity = main.getInt("humidity");
                    double speed = wind.getDouble("speed");
                    String description = weather.getString("description");


                    System.out.println("Погода в " + city + ":");
                    System.out.println("Описание: " + description);
                    System.out.println("Температура: " + temp + "C");
                    System.out.println("Влажность: " + humidity);
                    System.out.println("Скорость ветра: " + speed + "м/с");
                }

            } catch(Exception e){
                System.out.println("Ошибка: " + e.getMessage());
            }
        });
        future.get();
    }
}
