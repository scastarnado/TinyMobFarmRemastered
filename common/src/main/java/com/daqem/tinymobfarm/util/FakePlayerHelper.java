package com.daqem.tinymobfarm.util;

import java.util.UUID;
import com.daqem.tinymobfarm.TinyMobFarm;

import com.mojang.authlib.GameProfile;

import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;

public class FakePlayerHelper {
	private static final GameProfile PROFILE = new GameProfile(UUID.fromString("8c600ac8-8827-4b50-becf-68d686dc65cc"), "[TinyMobFarm]");

	public static ServerPlayer getPlayer(ServerLevel serverLevel) {
		return TinyMobFarm.PLATFORM.fakePlayer(serverLevel, PROFILE);
	}
}
