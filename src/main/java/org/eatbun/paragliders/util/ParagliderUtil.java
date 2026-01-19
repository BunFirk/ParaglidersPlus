package org.eatbun.paragliders.util;

import net.minecraft.entity.player.PlayerEntity;
import org.eatbun.paragliders.item.custom.ParagliderDefaultItem;

public class ParagliderUtil {

    public static boolean isParagliding(PlayerEntity player) {
        return player.isUsingItem() &&
                (
                        player.getMainHandStack().getItem() instanceof ParagliderDefaultItem ||
                                player.getOffHandStack().getItem() instanceof ParagliderDefaultItem
                );
    }
}