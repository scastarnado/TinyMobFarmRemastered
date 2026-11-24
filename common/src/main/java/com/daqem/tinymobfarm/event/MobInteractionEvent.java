package com.daqem.tinymobfarm.event;

import com.daqem.tinymobfarm.item.LassoItem;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;

public class MobInteractionEvent {

    public static InteractionResult interact(Player player, Entity entity, InteractionHand hand) {
        ItemStack stack = player.getItemInHand(hand);
        if (stack.getItem() instanceof LassoItem lassoItem && entity instanceof LivingEntity target) {
            if (lassoItem.interactMob(stack, player, target) == InteractionResult.SUCCESS) {
                return InteractionResult.SUCCESS;
            }
        }
        return InteractionResult.PASS;
    }
}
