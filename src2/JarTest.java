import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class JarTest {
    public static void main(String[] args) {
        OkHttpClient client = new OkHttpClient();

        Request request = new Request.Builder()
                .url("https://httpbin.org/get") // Simple public API for testing
                .build();

        try (Response response = client.newCall(request).execute()) {
            if (response.isSuccessful()) {
                System.out.println("✅ Connected! Response:\n" + response.body().string());
            } else {
                System.out.println("❌ Failed with code: " + response.code());
            }
        } catch (Exception e) {
            System.err.println("❌ Exception during request:");
            e.printStackTrace();
        }
    }
}
