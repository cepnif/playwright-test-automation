package api.clients;

import com.microsoft.playwright.APIRequestContext;
import com.microsoft.playwright.APIResponse;
import com.microsoft.playwright.options.RequestOptions;
import common.config.ConfigReader;
import org.testng.Assert;

import java.util.Map;

public class HTTPRequestBuilder {

    private final APIRequestContext requestContext;
    private final String baseUrl;

    public HTTPRequestBuilder(APIRequestContext requestContext) {
        this.requestContext = requestContext;
        this.baseUrl = ConfigReader.getApiBaseUrl(); // Read Base URL from config.properties
    }

    /**
     * Generic API request method to handle GET, POST, PUT, DELETE requests.
     *
     * @param method        HTTP method (GET, POST, PUT, DELETE)
     * @param endpoint      API endpoint (e.g., "/store/order/{orderId}")
     * @param headers       Headers (e.g., {"Authorization": "Bearer token"})
     * @param queryParams   Query parameters (e.g., {"status": "placed"})
     * @param pathParams    Path parameters (e.g., {"orderId": "10"})
     * @param requestBody   Request body (JSON format)
     * @return              APIResponse object
     */
    public APIResponse sendRequest(String method, String endpoint,
                                   Map<String, String> headers,
                                   Map<String, String> queryParams,
                                   Map<String, String> pathParams,
                                   String requestBody) {

        // Replace path parameters in the endpoint
        if (pathParams != null) {
            for (Map.Entry<String, String> entry : pathParams.entrySet()) {
                endpoint = endpoint.replace("{" + entry.getKey() + "}", entry.getValue());
            }
        }

        // Append query parameters to the URL
        if (queryParams != null && !queryParams.isEmpty()) {
            StringBuilder queryString = new StringBuilder("?");
            for (Map.Entry<String, String> entry : queryParams.entrySet()) {
                queryString.append(entry.getKey()).append("=").append(entry.getValue()).append("&");
            }
            endpoint += queryString.substring(0, queryString.length() - 1);
        }

        String fullUrl = baseUrl + endpoint; // Construct the full URL

        // ✅ Create request options
        RequestOptions requestOptions = RequestOptions.create();
        if (headers != null) {
            headers.forEach(requestOptions::setHeader);
        }
        if (requestBody != null && !requestBody.isEmpty()) {
            requestOptions.setData(requestBody);
        }

        // ✅ Execute request based on HTTP method
        APIResponse response;
        switch (method.toUpperCase()) {
            case "GET":
                response = requestContext.get(fullUrl, requestOptions);
                break;
            case "POST":
                response = requestContext.post(fullUrl, requestOptions);
                break;
            case "PUT":
                response = requestContext.put(fullUrl, requestOptions);
                break;
            case "DELETE":
                response = requestContext.delete(fullUrl, requestOptions);
                break;
            default:
                throw new IllegalArgumentException("❌ Invalid HTTP Method: " + method);
        }

        // ✅ Log request and response details
        System.out.println("\n🌐 Base URL: " + baseUrl);
        System.out.println("🛠 Endpoint: " + endpoint);
        System.out.println("📩 HTTP Method: " + method);
        System.out.println("📩 Headers: " + headers);
        System.out.println("📩 Query Params: " + queryParams);
        System.out.println("📩 Request Body: " + requestBody);
        System.out.println("📨 Response Status: " + response.status());
        System.out.println("📨 Response Body: " + response.text());

        return response;
    }

    /**
     * Validates the API response status code.
     *
     * @param response       API Response object
     * @param expectedStatus Expected HTTP status code
     */
    public void validateResponse(APIResponse response, int expectedStatus) {
        Assert.assertEquals(response.status(), expectedStatus, "❌ Unexpected HTTP status code!");
    }
}
