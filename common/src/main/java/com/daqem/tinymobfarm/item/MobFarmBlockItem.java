package com.daqem.tinymobfarm.item;

import com.daqem.tinymobfarm.TinyMobFarm;
import com.daqem.tinymobfarm.block.MobFarmBlock;
import com.mojang.blaze3d.platform.InputConstants;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.component.TooltipDisplay;
import org.jspecify.annotations.NonNull;

import java.util.function.Consumer;

public class MobFarmBlockItem extends BlockItem {

    private final MobFarmBlock block;

    public MobFarmBlockItem(MobFarmBlock block, Properties builder) {
        super(block, builder.stacksTo(64));
        this.block = block;
    }

    @Override
    public void appendHoverText(@NonNull ItemStack itemStack, @NonNull TooltipContext tooltipContext, @NonNull TooltipDisplay tooltipDisplay, @NonNull Consumer<Component> consumer, @NonNull TooltipFlag tooltipFlag) {
        if (InputConstants.isKeyDown(InputConstants.KEY_LSHIFT)
                || InputConstants.isKeyDown(InputConstants.KEY_RSHIFT))
            this.block.getTooltipBuilder(consumer);
        else consumer.accept(TinyMobFarm.translatable("tooltip.hold_shift", ChatFormatting.GRAY));
        super.appendHoverText(itemStack, tooltipContext, tooltipDisplay, consumer, tooltipFlag);
    }
}
