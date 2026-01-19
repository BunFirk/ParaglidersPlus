package org.eatbun.paragliders.mixin.client;

import net.minecraft.client.model.ModelPart;
import net.minecraft.client.render.entity.model.BipedEntityModel;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import org.eatbun.paragliders.item.ModItems;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(BipedEntityModel.class)
public abstract class BipedEntityModelMixin<T extends LivingEntity> {

    @Shadow @Final
    public ModelPart rightArm;

    @Shadow @Final
    public ModelPart leftArm;

    @Inject(
            method = "setAngles(Lnet/minecraft/entity/LivingEntity;FFFFF)V",
            at = @At("TAIL")
    )
    private void paragliders$raiseArms(
            T entity,
            float limbAngle,
            float limbDistance,
            float animationProgress,
            float headYaw,
            float headPitch,
            CallbackInfo ci
    ) {
        // ТОЛЬКО игроки
        if (!(entity instanceof PlayerEntity player)) return;

        ItemStack main = player.getMainHandStack();
        ItemStack off  = player.getOffHandStack();

        boolean holdingParaglider =
                main.isOf(ModItems.PARAGLIDER) ||
                        off.isOf(ModItems.PARAGLIDER) ||
                main.isOf(ModItems.PARAGLIDERSKY) ||
                        off.isOf(ModItems.PARAGLIDERSKY) ||
                main.isOf(ModItems.PARAGLIDERBLUE) ||
                        off.isOf(ModItems.PARAGLIDERBLUE) ||
                main.isOf(ModItems.PARAGLIDERED) ||
                        off.isOf(ModItems.PARAGLIDERED) ||
                main.isOf(ModItems.PARAGLIDERBAD) ||
                        off.isOf(ModItems.PARAGLIDERBAD);

        if (holdingParaglider && !player.isTouchingWater() && !player.isInLava()) {
            rightArm.pitch = -3.2F;
            leftArm.pitch  = -3.2F;

            rightArm.yaw = 0.0F;
            leftArm.yaw  = 0.0F;
        }
    }
}