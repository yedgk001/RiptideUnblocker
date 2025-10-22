package io.yedgk.riptideunblocker;

import io.yedgk.riptideunblocker.configuration.ConfigurationService;
import io.yedgk.riptideunblocker.listener.RiptideListener;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.loader.api.FabricLoader;

import java.io.File;

public class RiptideUnblockerMod implements ModInitializer {
    @Override
    public void onInitialize() {
        RiptideListener riptideListener = new RiptideListener(ConfigurationService.load(new File(FabricLoader.getInstance().getConfigDir().toFile(), "RiptideUnblocker")));
        riptideListener.register();
    }
}
