package me.pedro.aguiar.staffchat.main;

import lombok.Getter;
import lombok.SneakyThrows;
import me.pedro.aguiar.staffchat.command.StaffChatCommand;
import net.md_5.bungee.api.plugin.Plugin;
import net.md_5.bungee.config.Configuration;
import net.md_5.bungee.config.ConfigurationProvider;
import net.md_5.bungee.config.YamlConfiguration;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;

public final class GlobalStaffChat extends Plugin {

    @Getter
    private Configuration config;

    @Override
    public void onEnable() {
        /* load config */

        loadConfig();

        /* init command */


        final StaffChatCommand staffChatCommand = new StaffChatCommand(null, null, null);

        new StaffChatCommand("staffchat", this, config.getString("staffchat-format"));
    }

    @Override
    public void onDisable() {}

    @SneakyThrows
    private void loadConfig() {
        if (!getDataFolder().exists())
            getDataFolder().mkdir();

        final File file = new File(getDataFolder(), "config.yml");

        if (!file.exists())
            try (InputStream in = getResourceAsStream("config.yml")) {
                Files.copy(in, file.toPath());
            } catch (IOException e) {
                e.printStackTrace();
            }

         config = ConfigurationProvider.getProvider(YamlConfiguration.class).load(file);
    }
}
