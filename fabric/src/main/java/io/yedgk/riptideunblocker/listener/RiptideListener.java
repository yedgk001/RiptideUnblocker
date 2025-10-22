package io.yedgk.riptideunblocker.listener;

import io.yedgk.riptideunblocker.configuration.PluginConfiguration;
import net.fabricmc.fabric.api.event.player.UseItemCallback;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.enchantment.Enchantments;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.ActionResult;

public record RiptideListener(PluginConfiguration pluginConfiguration) {

    public void register() {
        UseItemCallback.EVENT.register((player, world, hand) -> {
            if (!(player instanceof ServerPlayerEntity serverPlayer)) return ActionResult.PASS;
            ItemStack item = serverPlayer.getStackInHand(hand);
            if (!item.isOf(Items.TRIDENT)) return ActionResult.PASS;
            int riptideLevel = EnchantmentHelper.getLevel(world.getRegistryManager().getOrThrow(RegistryKeys.ENCHANTMENT).getOrThrow(Enchantments.RIPTIDE), item);
            if (riptideLevel <= 0) return ActionResult.PASS;
            if (serverPlayer.getItemCooldownManager().isCoolingDown(item)) return ActionResult.PASS;
            double velocity = switch (riptideLevel) {
                case 2 -> 2.0;
                case 3 -> 2.5;
                default -> 1.5;
            };
            if (pluginConfiguration.customVelocity) velocity = pluginConfiguration.velocityValue;

            serverPlayer.addVelocity(serverPlayer.getRotationVector().multiply(velocity));
            serverPlayer.velocityModified = true;

            var sound = switch (riptideLevel) {
                case 1 -> SoundEvents.ITEM_TRIDENT_RIPTIDE_1;
                case 2 -> SoundEvents.ITEM_TRIDENT_RIPTIDE_2;
                default -> SoundEvents.ITEM_TRIDENT_RIPTIDE_3;
            };

            world.playSound(null,
                    serverPlayer.getX(),
                    serverPlayer.getY(),
                    serverPlayer.getZ(),
                    sound,
                    SoundCategory.PLAYERS, 1.0F, 1.0F);
            serverPlayer.getItemCooldownManager().set(item, pluginConfiguration.cooldownInSeconds * 20);
            return ActionResult.SUCCESS;
        });
    }
}
