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

public record RiptideListener(ConfigurationService.PluginConfiguration pluginConfiguration) implements Listener {

    @EventHandler
    public void onPlayerInteract(PlayerInteractEvent event) {
        Player player = event.getPlayer();
        if (pluginConfiguration.disabledWorlds.contains(player.getWorld().getName())) return;
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
            velocityMultiplier = switch (riptideLevel) {
                case 2 -> 2.0;
                case 3 -> 2.5;
                default -> 1.5;
            };
        }
        Vector direction = player.getLocation().getDirection().normalize().multiply(velocityMultiplier);
        player.setVelocity(direction);
        Sound sound = switch (riptideLevel) {
            case 1 -> Sound.ITEM_TRIDENT_RIPTIDE_1;
            case 2 -> Sound.ITEM_TRIDENT_RIPTIDE_2;
            default -> Sound.ITEM_TRIDENT_RIPTIDE_3;
        };
        player.getWorld().playSound(player.getLocation(), sound, 1f, 1f);
        player.setCooldown(Material.TRIDENT, pluginConfiguration.cooldownInSeconds);
    }
}
