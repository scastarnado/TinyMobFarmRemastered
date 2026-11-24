package com.daqem.tinymobfarm;

import com.daqem.tinymobfarm.platform.Registry;
import com.daqem.tinymobfarm.platform.RegistryEntry;
import com.daqem.tinymobfarm.platform.PlatformServices;
import com.daqem.tinymobfarm.block.TinyMobFarmBlocks;
import com.daqem.tinymobfarm.block.MobFarmBlock;
import com.daqem.tinymobfarm.blockentity.MobFarmBlockEntity;
import com.daqem.tinymobfarm.client.gui.MobFarmMenu;
import com.daqem.tinymobfarm.config.TinyMobFarmConfig;
import com.daqem.tinymobfarm.item.TinyMobFarmItems;
import com.daqem.tinymobfarm.item.component.LassoData;
import net.minecraft.ChatFormatting;
import net.minecraft.core.component.DataComponentType;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.resources.Identifier;
import net.minecraft.world.flag.FeatureFlags;
import net.minecraft.world.inventory.MenuType;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.entity.BlockEntityType;

import java.util.Set;
import java.util.List;

public class TinyMobFarm {
    public static final String MOD_ID = "tinymobfarm";
    public static final org.slf4j.Logger LOGGER = org.slf4j.LoggerFactory.getLogger(MOD_ID);
    public static PlatformServices PLATFORM;

    public static final Registry<CreativeModeTab> TABS = new Registry<>(BuiltInRegistries.CREATIVE_MODE_TAB, MOD_ID);
    public static final RegistryEntry<CreativeModeTab> TINY_MOB_FARM_TAB = TABS.register("tab", () ->
            CreativeModeTab.builder(CreativeModeTab.Row.TOP, 0)
                    .title(Component.translatable("itemGroup.tiny_mob_farm"))
                    .icon(() -> new ItemStack(TinyMobFarmItems.WOODEN_MOB_FARM.get()))
                    .displayItems((parameters, output) -> {
                        for (var entry : List.of(TinyMobFarmItems.WOODEN_MOB_FARM, TinyMobFarmItems.STONE_MOB_FARM,
                                TinyMobFarmItems.IRON_MOB_FARM, TinyMobFarmItems.GOLD_MOB_FARM,
                                TinyMobFarmItems.DIAMOND_MOB_FARM, TinyMobFarmItems.EMERALD_MOB_FARM,
                                TinyMobFarmItems.INFERNAL_MOB_FARM, TinyMobFarmItems.ULTIMATE_MOB_FARM)) {
                            if (((MobFarmBlock) entry.get().getBlock()).getMobFarmType().isEnabled()) {
                                output.accept(entry.get());
                            }
                        }
                        output.accept(TinyMobFarmItems.LASSO.get());
                    }).build()
    );

    public static final Registry<MenuType<?>> MENUS = new Registry<>(BuiltInRegistries.MENU, MOD_ID);
    public static final RegistryEntry<MenuType<MobFarmMenu>> MOB_FARM_CONTAINER = MENUS.register("mob_farm_menu", () -> new MenuType<>(MobFarmMenu::new, FeatureFlags.VANILLA_SET));

    public static final Registry<BlockEntityType<?>> BLOCK_ENTITIES = new Registry<>(BuiltInRegistries.BLOCK_ENTITY_TYPE, MOD_ID);
    public static final RegistryEntry<BlockEntityType<MobFarmBlockEntity>> MOB_FARM_TILE_ENTITY = BLOCK_ENTITIES.register("mob_farm_block_entity", () -> new BlockEntityType<>(MobFarmBlockEntity::new, Set.of(
            TinyMobFarmBlocks.WOODEN_MOB_FARM_BLOCK.get(), TinyMobFarmBlocks.STONE_MOB_FARM_BLOCK.get(), TinyMobFarmBlocks.IRON_MOB_FARM_BLOCK.get(), TinyMobFarmBlocks.GOLD_MOB_FARM_BLOCK.get(), TinyMobFarmBlocks.DIAMOND_MOB_FARM_BLOCK.get(),
            TinyMobFarmBlocks.EMERALD_MOB_FARM_BLOCK.get(), TinyMobFarmBlocks.INFERNAL_MOB_FARM_BLOCK.get(), TinyMobFarmBlocks.ULTIMATE_MOB_FARM_BLOCK.get())));

    public static final Registry<DataComponentType<?>> COMPONENTS = new Registry<>(BuiltInRegistries.DATA_COMPONENT_TYPE, MOD_ID);
    public static final RegistryEntry<DataComponentType<LassoData>> LASSO_DATA = COMPONENTS.register("lasso_data", () -> DataComponentType.<LassoData>builder().persistent(LassoData.CODEC).networkSynchronized(LassoData.STREAM_CODEC).build());


    public static void init() {
        TinyMobFarmConfig.init();
        // Force shared definitions to initialize before loader registration events.
        var blocks = TinyMobFarmBlocks.BLOCKS;
        var items = TinyMobFarmItems.ITEMS;
    }

    public static void registerFabric() {
        TinyMobFarmBlocks.BLOCKS.register();
        TinyMobFarmItems.ITEMS.register();
        MENUS.register();
        BLOCK_ENTITIES.register();
        COMPONENTS.register();
        TABS.register();
    }

    public static MutableComponent translatable(String s) {
        return Component.translatable(MOD_ID + "." + s);
    }

    public static MutableComponent translatable(String s, Object... objects) {
        return Component.translatable(MOD_ID + "." + s, objects);
    }

    public static MutableComponent translatable(String s, ChatFormatting color) {
        return translatable(s).withStyle(color);
    }

    public static MutableComponent translatable(String s, ChatFormatting color, Object... objects) {
        return translatable(s, objects).withStyle(color);
    }

    public static Component literal(String str) {
        return Component.literal(str);
    }

    public static Identifier getId(String str) {
        return Identifier.fromNamespaceAndPath(MOD_ID, str);
    }
}
