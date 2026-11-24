package com.daqem.tinymobfarm.neoforge;

import com.daqem.tinymobfarm.platform.PlatformServices;
import com.mojang.authlib.GameProfile;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.item.ItemStack;
import net.neoforged.fml.loading.FMLPaths;
import net.neoforged.neoforge.common.util.FakePlayerFactory;
import net.neoforged.neoforge.capabilities.Capabilities;
import net.neoforged.neoforge.transfer.item.ItemResource;
import net.neoforged.neoforge.transfer.transaction.Transaction;

import java.nio.file.Path;

public final class NeoForgePlatform implements PlatformServices {
    public Path configDirectory() { return FMLPaths.CONFIGDIR.get(); }

    public ServerPlayer fakePlayer(ServerLevel level, GameProfile profile) {
        return FakePlayerFactory.get(level, profile);
    }

    public ItemStack insertItem(ServerLevel level, BlockPos position, Direction side, ItemStack stack) {
        var storage = level.getCapability(Capabilities.Item.BLOCK, position, side);
        if (stack.isEmpty() || storage == null) return stack;
        try (var transaction = Transaction.open(null)) {
            int inserted = (int) storage.insert(ItemResource.of(stack), stack.getCount(), transaction);
            transaction.commit();
            return stack.copyWithCount(stack.getCount() - inserted);
        }
    }
}
