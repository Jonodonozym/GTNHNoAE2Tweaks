package com.jdz.noae.mixins.late;

import net.minecraft.item.ItemStack;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

import ggfab.mte.MTELinkedInputBus;
import gregtech.api.metatileentity.implementations.MTEHatchInputBus;

@Mixin(MTELinkedInputBus.class)
public class LinkedInputBusAALCompat extends MTEHatchInputBus {

    private final int LIB_SIZE_INVENTORY = 18;

    public LinkedInputBusAALCompat(int id, String name, String nameRegional, int tier) {
        super(id, name, nameRegional, tier);
    }

    @Shadow(remap = false)
    public ItemStack getStackInSlot(int index) {
        return null;
    }

    @Override
    public ItemStack getFirstStack() {
        for (int index = 1; index < LIB_SIZE_INVENTORY; index++) {
            ItemStack stackInSlot = getStackInSlot(index);
            if (stackInSlot != null) {
                return stackInSlot;
            }
        }
        return null;
    }
}
