package utils;

import java.nio.charset.StandardCharsets;
import java.util.Base64;

public class Base64Encoder {
    public static String articleToBase64(String articleContent) {
        String base64Article;
        byte[] bytes = articleContent.getBytes(StandardCharsets.UTF_8);
        base64Article = Base64.getEncoder().encodeToString(bytes);

        return base64Article;
    }
}
