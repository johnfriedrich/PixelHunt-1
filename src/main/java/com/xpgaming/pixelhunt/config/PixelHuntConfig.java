package com.xpgaming.pixelhunt.config;

import org.spongepowered.configurate.ConfigurateException;
import org.spongepowered.configurate.ConfigurationNode;
import org.spongepowered.configurate.objectmapping.ConfigSerializable;
import org.spongepowered.configurate.yaml.YamlConfigurationLoader;

import java.io.File;
import java.nio.file.Paths;

@ConfigSerializable
public class PixelHuntConfig {

    private static final YamlConfigurationLoader CONFIG_LOADER = YamlConfigurationLoader.builder()
            .path(Paths.get("config" + File.separator + "PixelHunt" + File.separator + "config.yml"))
            .build();

    private static PixelHuntConfig instance;
    private static ConfigurationNode configNode;

    static {
        try {
            configNode = CONFIG_LOADER.load();
            instance = configNode.get(PixelHuntConfig.class);
        } catch (ConfigurateException e) {
            e.printStackTrace();
        }
    }

    public static PixelHuntConfig getInstance() {
        return instance;
    }

    public static ConfigurationNode getConfigNode() {
        return configNode;
    }

    private int guiHeight = 3;
    private String guiName = "PixelHunt";
    private String backgroundItem = "minecraft:stained_glass_pane";
    private int backgroundItemDamage = 8;

    public int getGuiHeight() {
        return this.guiHeight;
    }

    public String getGuiName() {
        return this.guiName;
    }

    public String getBackgroundItem() {
        return this.backgroundItem;
    }

    public int getBackgroundItemDamage() {
        return this.backgroundItemDamage;
    }
}
