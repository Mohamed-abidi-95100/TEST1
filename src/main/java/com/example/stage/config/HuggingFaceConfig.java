package com.example.stage.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(prefix = "huggingface")
public class HuggingFaceConfig {
    private String apiKey = "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";
    private String apiUrl = "https://api-inference.huggingface.co/models/";
    private String modelId = "gpt2";

    public String getApiKey() {
        return apiKey;
    }

    public void setApiKey(String apiKey) {
        this.apiKey = apiKey;
    }

    public String getApiUrl() {
        return apiUrl;
    }

    public void setApiUrl(String apiUrl) {
        this.apiUrl = apiUrl;
    }

    public String getModelId() {
        return modelId;
    }

    public void setModelId(String modelId) {
        this.modelId = modelId;
    }

    // Helper method to get the complete API URL
    public String getCompleteApiUrl() {
        return apiUrl + modelId;
    }
}