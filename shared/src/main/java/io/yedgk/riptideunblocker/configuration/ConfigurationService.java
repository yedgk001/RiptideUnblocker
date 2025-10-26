package io.yedgk.riptideunblocker.configuration;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

public class ConfigurationService {

    private static final Gson GSON = new GsonBuilder().setPrettyPrinting().create();

    public static PluginConfiguration load(File dataFolder) {
        if (!dataFolder.exists()) dataFolder.mkdirs();
        File configFile = new File(dataFolder, "config.json");
        PluginConfiguration configuration;
        if (!configFile.exists()) {
            configuration = new PluginConfiguration();
            try (FileWriter writer = new FileWriter(configFile)) {
                GSON.toJson(configuration, writer);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        } else {
            try (FileReader reader = new FileReader(configFile)) {
                configuration = GSON.fromJson(reader, ConfigurationService.PluginConfiguration.class);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
        return configuration;
    }

    public static class PluginConfiguration {
        public boolean customVelocity;
        public double velocityValue;
        public int cooldownInSeconds;
        public List<String> disabledWorlds;

        public PluginConfiguration() {
            this.customVelocity = true;
            this.velocityValue = 5.0;
            this.cooldownInSeconds = 0;
            this.disabledWorlds = List.of(
                    "world_nether",
                    "world_the_end"
            );
        }
    }
}
