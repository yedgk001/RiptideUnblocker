package io.yedgk.riptideunblocker;

import io.yedgk.riptideunblocker.configuration.ConfigurationService;
import io.yedgk.riptideunblocker.listener.RiptideListener;
import org.bukkit.plugin.java.JavaPlugin;

public class RiptideUnblockerPlugin extends JavaPlugin {

    @Override
    public void onEnable() {
        this.getServer().getPluginManager().registerEvents(new RiptideListener(ConfigurationService.load(this.getDataFolder())), this);
    }
}
