package com.portfolio.utilities;

import org.apache.hc.client5.http.classic.methods.HttpGet;
import org.apache.hc.client5.http.classic.methods.HttpHead;
import org.apache.hc.client5.http.impl.classic.CloseableHttpClient;
import org.apache.hc.client5.http.impl.classic.HttpClients;
import org.apache.hc.core5.http.ClassicHttpResponse;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import java.net.URI;
import java.net.URL;
import java.net.URLConnection;
import java.util.HashMap;
import java.util.Map;

public class ApiHelper {
    private static final Logger logger = LogManager.getLogger(ApiHelper.class);

    /**
     * Check if a URL is reachable
     */
    public static boolean isUrlReachable(String urlString) {
        try {
            URL url = URI.create(urlString).toURL();
            HttpHead httpHead = new HttpHead(urlString);
            try (CloseableHttpClient httpClient = HttpClients.createDefault()) {
                ClassicHttpResponse response = httpClient.executeOpen(null, httpHead, null);
                int statusCode = response.getCode();
                logger.info("URL: " + urlString + " - Status Code: " + statusCode);
                return statusCode >= 200 && statusCode < 400;
            }
        } catch (Exception e) {
            logger.warn("URL not reachable: " + urlString + " - " + e.getMessage());
            return false;
        }
    }

    /**
     * Get HTTP response status code
     */
    public static int getHttpStatusCode(String urlString) {
        try {
            HttpHead httpHead = new HttpHead(urlString);
            try (CloseableHttpClient httpClient = HttpClients.createDefault()) {
                ClassicHttpResponse response = httpClient.executeOpen(null, httpHead, null);
                return response.getCode();
            }
        } catch (Exception e) {
            logger.error("Error getting status code for " + urlString + ": " + e.getMessage());
            return -1;
        }
    }

    /**
     * Check if link is valid (returns 200-399)
     */
    public static boolean isLinkValid(String urlString) {
        int statusCode = getHttpStatusCode(urlString);
        return statusCode >= 200 && statusCode < 400;
    }

    /**
     * Get HTTP headers
     */
    public static Map<String, String> getHttpHeaders(String urlString) {
        Map<String, String> headers = new HashMap<>();
        try {
            URL url = URI.create(urlString).toURL();
            URLConnection connection = url.openConnection();
            connection.getHeaderFields().forEach((key, value) -> {
                if (key != null) {
                    headers.put(key, value.toString());
                }
            });
        } catch (Exception e) {
            logger.error("Error getting headers for " + urlString + ": " + e.getMessage());
        }
        return headers;
    }

    /**
     * Check PDF file - size and content type
     */
    public static Map<String, Object> checkPdfFile(String pdfUrl) {
        Map<String, Object> pdfInfo = new HashMap<>();
        pdfInfo.put("url", pdfUrl);
        pdfInfo.put("isValid", false);
        try {
            URL url = URI.create(pdfUrl).toURL();
            URLConnection connection = url.openConnection();
            connection.connect();

            long fileSize = connection.getContentLengthLong();
            String contentType = connection.getContentType();

            pdfInfo.put("contentType", contentType);
            pdfInfo.put("fileSize", fileSize);
            pdfInfo.put("isValid", contentType != null && contentType.contains("pdf"));

            logger.info("PDF Check - URL: " + pdfUrl + ", Content-Type: " + contentType + ", Size: " + fileSize);
        } catch (Exception e) {
            logger.error("Error checking PDF: " + pdfUrl + " - " + e.getMessage());
            pdfInfo.put("error", e.getMessage());
        }
        return pdfInfo;
    }
}
