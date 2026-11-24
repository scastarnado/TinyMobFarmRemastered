package com.daqem.tinymobfarm.util;

import com.daqem.tinymobfarm.config.TinyMobFarmConfig;
import com.daqem.tinymobfarm.TinyMobFarm;
import com.daqem.tinymobfarm.item.component.LassoData;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.DoubleTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.resources.Identifier;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntitySpawnReason;
import net.minecraft.world.entity.EntitySpawnRequest;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.level.storage.loot.LootParams;
import net.minecraft.world.level.storage.loot.parameters.LootContextParamSets;
import net.minecraft.world.level.storage.loot.parameters.LootContextParams;

import java.util.ArrayList;
import java.util.List;

public class EntityHelper {

    public static List<ItemStack> generateLoot(ServerLevel level, ItemStack stack) {
        Entity entity = getEntityFromLasso(stack, BlockPos.ZERO, level);
        if (entity == null) return new ArrayList<>();

        ServerPlayer daniel = FakePlayerHelper.getPlayer(level);
        if (daniel == null) {
            throw new IllegalStateException("Failed to retrieve fake player");
        }

        // If lasso looting is allowed, pass the lasso as the weapon to apply its looting level.
        // Otherwise, pass an empty stack so no looting is applied.
        ItemStack weapon = TinyMobFarmConfig.allowLassoLooting.get() ? stack : ItemStack.EMPTY;

        var tableKey = entity.getType().getDefaultLootTable();
        if (tableKey.isEmpty()) return List.of();
        var looting = level.registryAccess().lookupOrThrow(Registries.ENCHANTMENT).getOrThrow(Enchantments.LOOTING);
        ItemStack sword = new ItemStack(Items.DIAMOND_SWORD);
        int lootingLevel = EnchantmentHelper.getItemEnchantmentLevel(looting, weapon);
        if (lootingLevel > 0) sword.enchant(looting, lootingLevel);

        ItemStack previousWeapon = daniel.getMainHandItem();
        try {
            daniel.setItemInHand(InteractionHand.MAIN_HAND, sword);
            var context = new LootParams.Builder(level)
                    .withParameter(LootContextParams.THIS_ENTITY, entity)
                    .withParameter(LootContextParams.ORIGIN, entity.position())
                    .withParameter(LootContextParams.DAMAGE_SOURCE, level.damageSources().playerAttack(daniel))
                    .withParameter(LootContextParams.ATTACKING_ENTITY, daniel)
                    .withParameter(LootContextParams.DIRECT_ATTACKING_ENTITY, daniel)
                    .withParameter(LootContextParams.LAST_DAMAGE_PLAYER, daniel)
                    .create(LootContextParamSets.ENTITY);
            return level.getServer().reloadableRegistries().getLootTable(tableKey.get()).getRandomItems(context);
        } finally {
            daniel.setItemInHand(InteractionHand.MAIN_HAND, previousWeapon);
        }
    }

    public static Entity getEntityFromLasso(ItemStack lasso, BlockPos pos, Level level) {
        if (!lasso.has(TinyMobFarm.LASSO_DATA.get())) return null;
        LassoData data = lasso.get(TinyMobFarm.LASSO_DATA.get());
        if (data == null) return null;
        CompoundTag mobData = data.mobData().copy();
        Identifier id = data.mobId();

        DoubleTag x = DoubleTag.valueOf(pos.getX() + 0.5);
        DoubleTag y = DoubleTag.valueOf(pos.getY());
        DoubleTag z = DoubleTag.valueOf(pos.getZ() + 0.5);
        ListTag mobPos = new ListTag();
        mobPos.add(x);
        mobPos.add(y);
        mobPos.add(z);
        mobData.put("Pos", mobPos);
        mobData.putString("id", id.toString());

        Entity entity = EntityType.loadEntityRecursive(mobData, level, new EntitySpawnRequest(EntitySpawnReason.MOB_SUMMONED, true), e -> e);
        if (entity != null) {
            entity.xRotO = entity.getXRot();
            entity.yRotO = entity.getYRot();
            if (entity instanceof net.minecraft.world.entity.LivingEntity living) {
                living.yBodyRotO = living.yBodyRot;
                living.yHeadRotO = living.yHeadRot;
            }
        }
        return entity;
    }
}
