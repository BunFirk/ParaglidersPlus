package org.eatbun.paragliders.item;

import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.minecraft.item.*;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;
import org.eatbun.paragliders.item.custom.ParagliderBlueItem;
import org.eatbun.paragliders.item.custom.ParagliderDefaultItem;
import org.eatbun.paragliders.item.custom.ParagliderSkyItem;

import static org.eatbun.paragliders.Paragliders.MOD_ID;

public class ModItems {




    public static final Item PARAGLIDER = registerItem("paraglider",
            new ParagliderDefaultItem(new FabricItemSettings().maxCount(1)));

    public static final Item PARAGLIDERSKY = registerItem("paraglidersky",
            new ParagliderSkyItem(new FabricItemSettings().maxCount(1)));

    public static final Item PARAGLIDERBLUE = registerItem("paragliderblue",
            new ParagliderBlueItem(new FabricItemSettings().maxCount(1)));

    public static final Item PARAGLIDERED = registerItem("paragliderred",
            new ParagliderBlueItem(new FabricItemSettings().maxCount(1)));

    public static final Item PARAGLIDERBAD = registerItem("paragliderbad",
            new ParagliderBlueItem(new FabricItemSettings().maxCount(1)));




    private static Item registerItem(String name, Item item){
        return Registry.register(Registries.ITEM, new Identifier(MOD_ID, name), item);
    }

    public static void registerModItems() {
        ItemGroupEvents.modifyEntriesEvent(ItemGroups.COMBAT).register(ent -> {
            ent.add(PARAGLIDER);
            ent.add(PARAGLIDERSKY);
            ent.add(PARAGLIDERBLUE);
            ent.add(PARAGLIDERED);
            ent.add(PARAGLIDERBAD);
        });
    }
}
