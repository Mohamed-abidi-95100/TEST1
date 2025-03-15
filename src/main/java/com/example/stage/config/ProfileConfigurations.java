package com.example.stage.config;

import com.example.stage.model.ProfileConfig;
import java.util.Map;
import java.util.HashMap;

public class ProfileConfigurations {
    private Map<String, ProfileConfig> configurations;

    public ProfileConfigurations() {
        configurations = new HashMap<>();
        initializeBusinessConfigs();
        initializeMasterConfigs();
    }

    private void initializeBusinessConfigs() {
        ProfileConfig businessConfig = new ProfileConfig();
        businessConfig.setName("Business");
        businessConfig.setRequiredSkills(new String[]{"Management", "Communication", "Strategy"});
        configurations.put("business", businessConfig);
    }

    private void initializeMasterConfigs() {
        ProfileConfig masterConfig = new ProfileConfig();
        masterConfig.setName("Master");
        masterConfig.setRequiredSkills(new String[]{"Research", "Analysis", "Academic Writing"});
        configurations.put("master", masterConfig);
    }

    public ProfileConfig getConfig(String profile) {
        return configurations.get(profile.toLowerCase());
    }

    public Map<String, ProfileConfig> getAllConfigurations() {
        return new HashMap<>(configurations);
    }
}