import clients.S3Client;
import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyRequestEvent;
import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyResponseEvent;
import utils.Base64Encoder;

import java.util.HashMap;
import java.util.Map;

public class Handler {
    public APIGatewayProxyResponseEvent handleRequest(APIGatewayProxyRequestEvent event, Context context) {
        APIGatewayProxyResponseEvent response;
        try {
            Map<String, String> queryStringParameters = event.getQueryStringParameters();
            String file = getArticleFile(queryStringParameters.get("articleId"));
            response = buildResponse(200, file);

            return response;
        } catch (Exception e) {
            response = buildResponse(500, "Error");

            return response;
        }
    }

    public String getArticleFile(String articleId) throws Exception {
        String articleContent = S3Client.getArticleFile(articleId);
        return Base64Encoder.articleToBase64(articleContent);
    }

    public APIGatewayProxyResponseEvent buildResponse(Integer statusCode, String body) {
        Map<String, String> headers = new HashMap<String, String>() {{
            put("Content-Type", "application/json");
            put("Access-Control-Allow-Headers", "Authorization,Content-Type,X-Api-Key");
            put("Access-Control-Allow-Origin", "*");
        }};
        APIGatewayProxyResponseEvent response = new APIGatewayProxyResponseEvent();
        response.setHeaders(headers);
        response.setBody(body);
        response.setStatusCode(statusCode);

        return response;
    }
}
