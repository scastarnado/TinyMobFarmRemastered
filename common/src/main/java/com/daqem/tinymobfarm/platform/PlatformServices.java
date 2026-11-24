package com.daqem.tinymobfarm.platform;

import com.mojang.authlib.GameProfile;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.item.ItemStack;

import java.nio.file.Path;

public interface PlatformServices {
    Path configDirectory();
    ServerPlayer fakePlayer(ServerLevel level, GameProfile profile);
    ItemStack insertItem(ServerLevel level, BlockPos position, Direction side, ItemStack stack);
}
