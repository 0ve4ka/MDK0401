package org.example;


import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileWriter;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;

public final class APIS {
    public static void  dogApi () {
        String BASE_URL = "https://dog.ceo/api/breeds/image/random";

        try{
            URL url = new URL(BASE_URL);
            HttpURLConnection con = (HttpURLConnection) url.openConnection();
            con.setRequestMethod("GET");
            int er = con.getResponseCode();
            if (er >= 400){
                System.out.println("ERROR"+er);
                return;
            }
            BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
            StringBuilder response = new StringBuilder();
            String line;
            while((line=in.readLine())!= null)
                response.append(line);

            in.close();
            con.disconnect();

            JSONObject text = new JSONObject(response.toString());
            String result = text.getString("message");
            System.out.println(result);
        }
        catch (Exception e){
            System.out.println(e.getMessage());

        }

    }

    public static void catFactsApi(){
        try{
            String BASE_URL = "https://catfact.ninja/fact";
            URL url = new URL(BASE_URL);
            HttpURLConnection con = (HttpURLConnection) url.openConnection();
            con.setRequestMethod("GET");

            int er = con.getResponseCode();
            if (er >=400){
                System.out.println("ERROR"+er);
                return;
            }

            BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
            String line;
            StringBuilder responce = new StringBuilder();
            while((line = in.readLine())!=null)
                responce.append(line);

            in.close();
            con.disconnect();

            JSONObject fact = new JSONObject(responce.toString());
            String result = fact.getString("fact");

            System.out.println(result);
            result = translaterEng_Ru(result);
            System.out.println(result);
            result = translaterRu_Eng(result);
            System.out.println(result);

        }
        catch (Exception e){
            System.out.println(e.getMessage());
        }


    }

    public static String translaterEng_Ru(String text){
        StringBuilder result = new StringBuilder();
        try {
            String TRANSLATE_API_URL = "https://translate.googleapis.com/translate_a/single?client=gtx&sl=en&tl=ru&dt=t&q=";
            String urlString = TRANSLATE_API_URL + URLEncoder.encode(text, "UTF-8");
            URL url = new URL(urlString);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            String line;
            while((line = in.readLine())!=null)
                result.append(line);

            in.close();
            conn.disconnect();


//                System.out.println(result);
        }
        catch (Exception e){
            System.out.println(e.getMessage());
        }
        return new JSONArray(result.toString()).getJSONArray(0).getJSONArray(0).getString(0);
    }

    public static String translaterRu_Eng(String text){
        StringBuilder result = new StringBuilder();
        try {
            String TRANSLATE_API_URL = "https://translate.googleapis.com/translate_a/single?client=gtx&sl=ru&tl=en&dt=t&q=";
            String urlString = TRANSLATE_API_URL + URLEncoder.encode(text, "UTF-8");
            URL url = new URL(urlString);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            String line;
            while((line = in.readLine())!=null)
                result.append(line);

            in.close();
            conn.disconnect();


//                System.out.println(result);
        }
        catch (Exception e){
            System.out.println(e.getMessage());
        }
        return new JSONArray(result.toString()).getJSONArray(0).getJSONArray(0).getString(0);
    }
    public static void jokeApi(){
        String BASE_URL = "https://official-joke-api.appspot.com/random_joke";
        try {
            URL url = new URL(BASE_URL);
            HttpURLConnection con = (HttpURLConnection) url.openConnection();
            con.setRequestMethod("GET");
            int er = con.getResponseCode();
            if (er >=400){
                System.out.println("ERROR"+er);
                return;
            }
            BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
            String line;
            StringBuilder responce = new StringBuilder();
            while((line = in.readLine())!=null)
                responce.append(line);

            in.close();
            con.disconnect();


//            System.out.println(responce);
            JSONObject text = new JSONObject(responce.toString());
            String type = text.getString("type");
            String setup = text.getString("setup");
            String punchline = text.getString("punchline");

            System.out.println(type);
            System.out.println(translaterEng_Ru(type));
            System.out.println(setup);
            System.out.println(translaterEng_Ru(setup));
            System.out.println(punchline);
            System.out.println(translaterEng_Ru(punchline));



        }
        catch (Exception e){
            System.out.println(e.getMessage());
        }

    }

    public static void btcAPI(){
        String BASE_URL = "https://api.coindesk.com/v1/bpi/currentprice.json";
        String link = "H:/Test.txt";  //Your link
        try {
            FileWriter writer = new FileWriter(link,false);
            URL url = new URL(BASE_URL);
            HttpURLConnection con = (HttpURLConnection) url.openConnection();
            con.setRequestMethod("GET");
            BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
            String line;
            StringBuilder responce = new StringBuilder();
            while((line = in.readLine())!= null)
                responce.append(line);

            in.close();
            con.disconnect();

            JSONObject first = new JSONObject(responce.toString());
            String coin = "Coin: "+first.getString("chartName");
            JSONObject bpi = first.getJSONObject("bpi");
            JSONObject usd = bpi.getJSONObject("USD");
            String currency = "currency: " + usd.getString("code");
            String price = "price: " + usd.getDouble("rate_float");
            System.out.println(coin);
            System.out.println(currency);
            System.out.println(price);

            writer.write(coin);
            writer.append('\n');
            writer.write(currency);
            writer.append('\n');
            writer.write(price);
            writer.flush();

        }
        catch (Exception e){
            System.out.println(e.getMessage());
        }

    }


}

