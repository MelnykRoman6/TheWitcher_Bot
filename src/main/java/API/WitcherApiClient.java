package API;

import API.models.Weapon;
import com.google.gson.Gson;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import java.io.IOException;

public class WitcherApiClient {

    private final OkHttpClient client = new OkHttpClient(); //idk
    private static final String BASE_URL = "http://localhost:5000";

    private final Gson gson = new Gson();


    public Weapon getWeaponById(int id) throws IOException {
        String endpoint = "/Weapon/" + id;
        String fullUrl = BASE_URL + endpoint;
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
            System.out.println(jsonResponse);
            return gson.fromJson(jsonResponse, Weapon.class);
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
