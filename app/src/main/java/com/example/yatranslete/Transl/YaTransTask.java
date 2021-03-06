package com.example.yatranslete.Transl;

import android.os.AsyncTask;

import com.example.yatranslete.MainActivity;
import com.google.gson.Gson;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class YaTransTask extends AsyncTask<Request, Void, Response> {
    final MainActivity activity;

    // передаём ссылку на активность, чтобы отобразить результат перевода
    public YaTransTask(MainActivity activity) {
        this.activity = activity;
    }

    public Response requestToServer(Request req) {
        Gson gson = new Gson();

        String API_URL = "https://translate.yandex.net/api/v1.5/tr.json/translate";
        try {
            URL url = new URL(API_URL);
            HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setDoOutput(true); // setting POST method
            OutputStream out = urlConnection.getOutputStream();

            // сериализованный объект-запрос пишем в поток
            out.write(req.toByteArray());
            InputStream stream = urlConnection.getInputStream();
            Response response = gson.fromJson(new InputStreamReader(stream), Response.class);
            return response;

        } catch (Exception e) { return null; }

    }

    @Override
    protected Response doInBackground(Request... requests) {
        Request r = requests[0];
        return requestToServer(r);
    }

    @Override
    protected void onPostExecute(Response response) {
        if(response != null)
        activity.displayResult(response.toString()); else
            activity.displayResult("Введите текст!");
    }
}
