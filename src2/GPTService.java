package ai;

import okhttp3.*;
import org.json.JSONObject;

import java.io.IOException;

public class GPTService {
    private static final String API_KEY = "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";
    private static final String API_URL = "https://api.openai.com/v1/chat/completions";

    private static final OkHttpClient client = new OkHttpClient();

    public static String askGPT(String userMessage) {
        JSONObject message = new JSONObject()
                .put("role", "user")
                .put("content", userMessage);

        JSONObject payload = new JSONObject()
                .put("model", "gpt-3.5-turbo")
                .put("messages", new org.json.JSONArray().put(message))
                .put("temperature", 0.7);

        RequestBody body = RequestBody.create(payload.toString(), MediaType.get("application/json"));

        Request request = new Request.Builder()
                .url(API_URL)
                .addHeader("Authorization", "Bearer " + API_KEY)
                .addHeader("Content-Type", "application/json")
                .post(body)
                .build();

        try (Response response = client.newCall(request).execute()) {
            if (!response.isSuccessful()) {
                return "Error: " + response.code();
            }

            String responseBody = response.body().string();
            JSONObject json = new JSONObject(responseBody);
            return json.getJSONArray("choices")
                    .getJSONObject(0)
                    .getJSONObject("message")
                    .getString("content");
        } catch (IOException e) {
            e.printStackTrace();
            return "Error contacting GPT API.";
        }
    }
}
