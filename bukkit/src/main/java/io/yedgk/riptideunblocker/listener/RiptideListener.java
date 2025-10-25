package io.yedgk.riptideunblocker.listener;

import io.yedgk.riptideunblocker.configuration.ConfigurationService;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.util.Vector;

public class RiptideListener implements Listener {

    private final ConfigurationService.PluginConfiguration pluginConfiguration;

    public RiptideListener(ConfigurationService.PluginConfiguration pluginConfiguration) {
        this.pluginConfiguration = pluginConfiguration;
    }

    @EventHandler
    public void onPlayerInteract(PlayerInteractEvent event) {
        Player player = event.getPlayer();
        if (event.getAction() != Action.RIGHT_CLICK_AIR && event.getAction() != Action.RIGHT_CLICK_BLOCK) return;

        ItemStack item = player.getInventory().getItemInMainHand();
        if (item.getType() != Material.TRIDENT) return;
        if (item.getEnchantments().isEmpty()) return;

        int riptideLevel = item.getEnchantmentLevel(Enchantment.RIPTIDE);
        if (riptideLevel <= 0) return;
        if (player.hasCooldown(Material.TRIDENT)) return;

        double velocityMultiplier;
        if (pluginConfiguration.customVelocity) {
            velocityMultiplier = pluginConfiguration.velocityValue;
        } else {
            switch (riptideLevel) {
                case 2:
                    velocityMultiplier = 2.0;
                    break;
                case 3:
                    velocityMultiplier = 2.5;
                    break;
                default:
                    velocityMultiplier = 1.5;
                    break;
            }
        }
        Vector direction = player.getLocation().getDirection().normalize().multiply(velocityMultiplier);
        player.setVelocity(direction);
        Sound sound;
        switch (riptideLevel) {
            case 1:
                sound = Sound.ITEM_TRIDENT_RIPTIDE_1;
                break;
            case 2:
                sound = Sound.ITEM_TRIDENT_RIPTIDE_2;
                break;
            default:
                sound = Sound.ITEM_TRIDENT_RIPTIDE_3;
                break;
        }
        player.getWorld().playSound(player.getLocation(), sound, 1f, 1f);
        player.setCooldown(Material.TRIDENT, pluginConfiguration.cooldownInSeconds);
    }
}
