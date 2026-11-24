package com.daqem.tinymobfarm.fabric;

import com.daqem.tinymobfarm.TinyMobFarm;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.event.player.UseEntityCallback;
import com.daqem.tinymobfarm.event.MobInteractionEvent;

public class TinyMobFarmCommonFabric implements ModInitializer {

    @Override
    public void onInitialize() {
        TinyMobFarm.PLATFORM = new FabricPlatform();
        TinyMobFarm.init();
        TinyMobFarm.registerFabric();
        UseEntityCallback.EVENT.register((player, world, hand, entity, hit) -> MobInteractionEvent.interact(player, entity, hand));
    }

}
