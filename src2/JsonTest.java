import org.json.JSONObject;

public class JsonTest {
    public static void main(String[] args) {
        JSONObject json = new JSONObject();
        json.put("name", "LibraryBot");
        json.put("version", "1.0");

        System.out.println("✅ JSON: " + json.toString());
    }
}
