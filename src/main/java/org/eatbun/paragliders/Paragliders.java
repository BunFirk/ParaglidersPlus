package org.eatbun.paragliders;

import net.fabricmc.api.ModInitializer;

import static org.eatbun.paragliders.item.ModItems.registerModItems;

public class Paragliders implements ModInitializer {

    public static final String MOD_ID = "paragliders";

    @Override
    public void onInitialize() {
        registerModItems();
    }
}
