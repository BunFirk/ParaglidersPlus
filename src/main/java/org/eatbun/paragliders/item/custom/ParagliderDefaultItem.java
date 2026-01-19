package org.eatbun.paragliders.item.custom;

import net.minecraft.block.Blocks;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Hand;
import net.minecraft.util.UseAction;
import net.minecraft.world.World;

public class ParagliderDefaultItem extends Item {

    public ParagliderDefaultItem(Settings settings) {
        super(settings);
    }

    @Override
    public void inventoryTick(ItemStack stack, World world, Entity entity, int slot, boolean selected) {
        if (!(entity instanceof PlayerEntity player)) return;

        boolean holding =
                player.getMainHandStack() == stack ||
                        player.getOffHandStack() == stack;

        boolean shouldGlide =
                holding &&
                        !player.isOnGround() &&
                        !player.isTouchingWater() &&
                        !player.isInLava() &&
                        !player.isFallFlying();

        // синхронизация анимки
        if (!world.isClient) {
            if (shouldGlide) {
                if (!player.isUsingItem()) {
                    player.setCurrentHand(
                            player.getMainHandStack() == stack
                                    ? Hand.MAIN_HAND
                                    : Hand.OFF_HAND
                    );
                }
            } else {
                if (player.isUsingItem()) {
                    player.stopUsingItem();
                }
            }
        }

        if (!shouldGlide) return;


        // ЛОГИКА ПОЛЁТА
        double vx = player.getVelocity().x;
        double vy = player.getVelocity().y;
        double vz = player.getVelocity().z;

        boolean overLava = false;
        boolean nearLava = false;

        for (int i = 1; i <= 2; i++) {
            if (world.getBlockState(player.getBlockPos().down(i)).isOf(Blocks.LAVA)
                    || world.getBlockState(player.getBlockPos().down(i)).isOf(Blocks.MAGMA_BLOCK)) {
                overLava = true;
                break;
            }
        }

        for (int i = 1; i <= 3; i++) {
            if (world.getBlockState(player.getBlockPos().down(i)).isOf(Blocks.LAVA)
                    || world.getBlockState(player.getBlockPos().down(i)).isOf(Blocks.MAGMA_BLOCK)) {
                nearLava = true;
                break;
            }
        }

        if (overLava) {
            player.setVelocity(vx, 0.15, vz);
        } else if (nearLava) {
            player.setVelocity(vx, 0, vz);
        } else if (vy < 0) {
            player.setVelocity(vx * 1.03, -0.39, vz * 1.03);

            /*
            if (world.isClient && player.age % 4 == 0) { // раз в 4 тика
                world.addParticle(
                        ParticleTypes.CLOUD,
                        player.getX() - 1,
                        player.getY() + 2,
                        player.getZ() + 2,
                        -vx * 0.1,
                        -0.02,
                        -vz * 0.1
                );
            }*/
        }

        player.fallDistance = 0;
    }

    @Override
    public UseAction getUseAction(ItemStack stack) {
        return UseAction.BLOCK; // ТОЛЬКО ДЛЯ СИНХРОНИЗАЦИИ
    }

    @Override
    public int getMaxUseTime(ItemStack stack) {
        return 72000;
    }
}