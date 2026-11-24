package com.daqem.tinymobfarm.neoforge;

import com.daqem.tinymobfarm.TinyMobFarm;
import net.neoforged.fml.common.Mod;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.common.NeoForge;
import net.neoforged.neoforge.event.entity.player.PlayerInteractEvent;
import net.neoforged.neoforge.registries.RegisterEvent;
import com.daqem.tinymobfarm.event.MobInteractionEvent;
import com.daqem.tinymobfarm.platform.Registry;
import net.minecraft.world.InteractionResult;

@Mod(TinyMobFarm.MOD_ID)
public class TinyMobFarmNeoForge {

    public TinyMobFarmNeoForge(IEventBus modBus) {
        TinyMobFarm.PLATFORM = new NeoForgePlatform();
        TinyMobFarm.init();
        modBus.addListener((RegisterEvent event) -> Registry.registerMatching(event.getRegistry()));
        NeoForge.EVENT_BUS.addListener((PlayerInteractEvent.EntityInteract event) -> {
            var result = MobInteractionEvent.interact(event.getEntity(), event.getTarget(), event.getHand());
            if (result == InteractionResult.SUCCESS) {
                event.setCancellationResult(result);
                event.setCanceled(true);
            }
        });
    }
}
