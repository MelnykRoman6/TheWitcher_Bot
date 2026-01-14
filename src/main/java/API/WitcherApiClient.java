package API;

import API.models.Item;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.List;

public class WitcherApiClient {

    private final OkHttpClient client = new OkHttpClient();
    //ngrok
    private static final String BASE_URL = " https://subaveragely-soupier-adella.ngrok-free.dev";

    private final Gson gson = new Gson();


    //use T ext. item for use it for all classes
    public <T extends Item> T getItemById(int id, String itemType, Class<T> clazz) throws IOException {
        //since all classes can use it i pass type to go to the right folder
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

            return gson.fromJson(jsonResponse, clazz);
        }
    }
    //same but to obtain the list of all items the button user clicked
    public <T extends Item> List<T> getItemsList(String itemType, Class<T> clazz) throws IOException {
        String fullUrl = BASE_URL + "/" + itemType;
        Request request = new Request.Builder().url(fullUrl).build();

        try (Response response = client.newCall(request).execute()) {
            if (!response.isSuccessful()) throw new IOException("Unexpected code " + response);

            String jsonResponse = response.body().string();

            Type listType = TypeToken.getParameterized(List.class, clazz).getType();
            return gson.fromJson(jsonResponse, listType);
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
