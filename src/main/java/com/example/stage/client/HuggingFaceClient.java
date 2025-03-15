package com.example.stage.client;

import com.example.stage.config.HuggingFaceConfig;
import okhttp3.*;
import org.springframework.stereotype.Component;
import java.io.IOException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Component
public class HuggingFaceClient {
    private static final Logger logger = LoggerFactory.getLogger(HuggingFaceClient.class);
    private final OkHttpClient client;
    private final HuggingFaceConfig config;
    private static final MediaType JSON = MediaType.get("application/json; charset=utf-8");

    public HuggingFaceClient(HuggingFaceConfig config) {
        this.client = new OkHttpClient.Builder()
                .build();
        this.config = config;
    }

    public String generateText(String prompt) throws IOException {
        try {
            RequestBody body = RequestBody.create(
                    String.format("{\"inputs\":\"%s\"}", prompt.replace("\"", "\\\"")),
                    JSON
            );

            Request request = new Request.Builder()
                    .url(config.getApiUrl() + config.getModelId())
                    .post(body)
                    .addHeader("Authorization", "Bearer " + config.getApiKey())
                    .build();

            try (Response response = client.newCall(request).execute()) {
                if (!response.isSuccessful()) {
                    logger.error("API call failed with code: {}", response.code());
                    throw new IOException("Unexpected code " + response);
                }
                return response.body().string();
            }
        } catch (Exception e) {
            logger.error("Error generating text: {}", e.getMessage());
            throw new IOException("Failed to generate text", e);
        }
    }
}