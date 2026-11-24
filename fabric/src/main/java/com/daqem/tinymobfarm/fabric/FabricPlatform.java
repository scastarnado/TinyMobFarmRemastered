package com.daqem.tinymobfarm.fabric;

import com.daqem.tinymobfarm.platform.PlatformServices;
import com.mojang.authlib.GameProfile;
import net.fabricmc.loader.api.FabricLoader;
import net.fabricmc.fabric.api.entity.FakePlayer;
import net.fabricmc.fabric.api.transfer.v1.item.ItemStorage;
import net.fabricmc.fabric.api.transfer.v1.item.ItemVariant;
import net.fabricmc.fabric.api.transfer.v1.transaction.Transaction;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.item.ItemStack;

import java.nio.file.Path;

public final class FabricPlatform implements PlatformServices {
    public Path configDirectory() { return FabricLoader.getInstance().getConfigDir(); }

    public ServerPlayer fakePlayer(ServerLevel level, GameProfile profile) {
        return FakePlayer.get(level, profile);
    }

    public ItemStack insertItem(ServerLevel level, BlockPos position, Direction side, ItemStack stack) {
        var storage = ItemStorage.SIDED.find(level, position, side);
        if (stack.isEmpty() || storage == null) return stack;
        try (var transaction = Transaction.openOuter()) {
            int inserted = (int) storage.insert(ItemVariant.of(stack), stack.getCount(), transaction);
            transaction.commit();
            return stack.copyWithCount(stack.getCount() - inserted);
        }
    }
}
