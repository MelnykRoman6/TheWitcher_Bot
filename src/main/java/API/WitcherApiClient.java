package API;

import API.models.Item;
import API.models.Weapon;
import com.google.gson.Gson;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import java.io.IOException;

public class WitcherApiClient {

    private final OkHttpClient client = new OkHttpClient(); //idk
    private static final String BASE_URL = " https://subaveragely-soupier-adella.ngrok-free.dev";

    private final Gson gson = new Gson();


    // Используем <T extends Item>, чтобы метод работал с любым классом, реализующим интерфейс Item
    public <T extends Item> T getItemById(int id, String itemType, Class<T> clazz) throws IOException {

        String endpoint = "/" + itemType + "/" + id;
        System.out.println(endpoint);
        String fullUrl = BASE_URL + endpoint;
        System.out.println(fullUrl);

        Request request = new Request.Builder()
                .url(fullUrl)
                .build();

        try (Response response = client.newCall(request).execute()) {
            if (!response.isSuccessful()) {
                if (response.code() == 404) {
                    return null;
                }
                throw new IOException("API request failed: " + response.code());
            }

            String jsonResponse = response.body().string();
            System.out.println("JSON received: " + jsonResponse);

            // Gson теперь парсит JSON именно в тот класс, который мы передали (clazz)
            return gson.fromJson(jsonResponse, clazz);
        }
    }



    public byte[] downloadImage(String url) throws IOException {
        Request request = new Request.Builder()
                .url(url)
                .build();

        try (Response response = client.newCall(request).execute()) {
            if (!response.isSuccessful()) {
                throw new IOException("It is not possible to download an image: " + response.code() + " URL: " + url);
            }
            return response.body().bytes();
        }
    }


}
