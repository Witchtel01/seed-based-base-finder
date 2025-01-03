package org.witchtel.seedBased_base_finder.client;

import com.google.common.reflect.TypeToken;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import net.fabricmc.loader.api.FabricLoader;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.Map;

public class SeedConfig {
    private static final Gson GSON = new GsonBuilder().setPrettyPrinting().create();
    private static final File CONFIG_FILE = new File(FabricLoader.getInstance().getConfigDir().toString(), "seed_based_base_finder_seedlist.json");
    private Map<String, String> serverSeeds = new HashMap<>();

    public SeedConfig() {
        loadConfig();
    }
    public String getSeed(String serverAddress) {
        return serverSeeds.getOrDefault(serverAddress, null);
    }
    public void setSeed(String serverAddress, String seed) {
        serverSeeds.put(serverAddress, seed);
        saveConfig();
    }
    private void loadConfig() {
        try (FileReader reader = new FileReader(CONFIG_FILE)) {
            Type type = new TypeToken<Map<String, String>>() {}.getType();
            serverSeeds = GSON.fromJson(reader, type);
            if (serverSeeds == null) {
                serverSeeds = new HashMap<>();
            }
        } catch (IOException e) {
            try {
                CONFIG_FILE.createNewFile();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
            serverSeeds = new HashMap<>();
            saveConfig();
        }
    }
    private void saveConfig() {
        try (FileWriter writer = new FileWriter(CONFIG_FILE)) {
            GSON.toJson(serverSeeds, writer);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
