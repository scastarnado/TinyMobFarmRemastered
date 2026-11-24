package com.daqem.tinymobfarm.mixin;

import com.daqem.tinymobfarm.MobFarmType;
import com.daqem.tinymobfarm.TinyMobFarm;
import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import net.minecraft.core.Holder;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.item.crafting.RecipeMap;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

import java.util.stream.Stream;

@Mixin(RecipeMap.class)
public class RecipeMapMixin {
    @ModifyExpressionValue(method = "create", at = @At(value = "INVOKE",
            target = "Lnet/minecraft/core/HolderLookup;listElements()Ljava/util/stream/Stream;"))
    private static Stream<Holder.Reference<Recipe<?>>> tinymobfarm$filterRecipes(Stream<Holder.Reference<Recipe<?>>> recipes) {
        return recipes.filter(recipe -> {
            var id = recipe.key().identifier();
            if (!id.getNamespace().equals(TinyMobFarm.MOD_ID)) return true;
            for (MobFarmType type : MobFarmType.values()) {
                if (id.getPath().equals(type.getRegistryName())) return type.isEnabled();
            }
            return true;
        });
    }
}
