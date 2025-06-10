import okio.ByteString;

public class ByteStringTest {
    public static void main(String[] args) {
        ByteString b = ByteString.encodeUtf8("test");
        System.out.println(b.base64());
    }
}
