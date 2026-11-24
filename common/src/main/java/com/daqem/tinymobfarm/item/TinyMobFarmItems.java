package com.daqem.tinymobfarm.item;

import com.daqem.tinymobfarm.platform.Registry;
import com.daqem.tinymobfarm.platform.RegistryEntry;
import com.daqem.tinymobfarm.TinyMobFarm;
import com.daqem.tinymobfarm.block.TinyMobFarmBlocks;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.item.Item;

import java.util.function.Function;

public interface TinyMobFarmItems {

    Registry<Item> ITEMS = new Registry<>(BuiltInRegistries.ITEM, TinyMobFarm.MOD_ID);

    RegistryEntry<MobFarmBlockItem> WOODEN_MOB_FARM = blockItem("wood_farm", p -> new MobFarmBlockItem(TinyMobFarmBlocks.WOODEN_MOB_FARM_BLOCK.get(), p));
    RegistryEntry<MobFarmBlockItem> STONE_MOB_FARM = blockItem("stone_farm", p -> new MobFarmBlockItem(TinyMobFarmBlocks.STONE_MOB_FARM_BLOCK.get(), p));
    RegistryEntry<MobFarmBlockItem> IRON_MOB_FARM = blockItem("iron_farm", p -> new MobFarmBlockItem(TinyMobFarmBlocks.IRON_MOB_FARM_BLOCK.get(), p));
    RegistryEntry<MobFarmBlockItem> GOLD_MOB_FARM = blockItem("gold_farm", p -> new MobFarmBlockItem(TinyMobFarmBlocks.GOLD_MOB_FARM_BLOCK.get(), p));
    RegistryEntry<MobFarmBlockItem> DIAMOND_MOB_FARM = blockItem("diamond_farm", p -> new MobFarmBlockItem(TinyMobFarmBlocks.DIAMOND_MOB_FARM_BLOCK.get(), p));
    RegistryEntry<MobFarmBlockItem> EMERALD_MOB_FARM = blockItem("emerald_farm", p -> new MobFarmBlockItem(TinyMobFarmBlocks.EMERALD_MOB_FARM_BLOCK.get(), p));
    RegistryEntry<MobFarmBlockItem> INFERNAL_MOB_FARM = blockItem("inferno_farm", p -> new MobFarmBlockItem(TinyMobFarmBlocks.INFERNAL_MOB_FARM_BLOCK.get(), p));
    RegistryEntry<MobFarmBlockItem> ULTIMATE_MOB_FARM = blockItem("ultimate_farm", p -> new MobFarmBlockItem(TinyMobFarmBlocks.ULTIMATE_MOB_FARM_BLOCK.get(), p));
    RegistryEntry<LassoItem> LASSO = item("lasso", LassoItem::new);

    static <T extends Item> RegistryEntry<T> blockItem(String id, Function<Item.Properties, T> constructor) {
        return ITEMS.register(id, (key) -> constructor.apply(new Item.Properties().setId(key).useBlockDescriptionPrefix()));
    }

    static <T extends Item> RegistryEntry<T> item(String id, Function<Item.Properties, T> constructor) {
        return ITEMS.register(id, (key) -> constructor.apply(new Item.Properties().setId(key)));
    }
}
