package com.jdz.noae.mixins.late;

import net.minecraft.item.ItemStack;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

import gregtech.api.interfaces.tileentity.IGregTechTileEntity;
import gregtech.common.tileentities.machines.MTEHatchInputBusME;
import tectech.thing.metaTileEntity.multi.MTEEyeOfHarmony;
import tectech.thing.metaTileEntity.multi.base.TTMultiblockBase;

@Mixin(MTEEyeOfHarmony.class)
abstract class NoAEEOH extends TTMultiblockBase {

    protected NoAEEOH(int aID, String aName, String aNameRegional) {
        super(aID, aName, aNameRegional);
    }

    @Shadow(remap = false)
    int spacetimeCompressionFieldMetadata;
    @Shadow(remap = false)
    int timeAccelerationFieldMetadata;
    @Shadow(remap = false)
    int stabilisationFieldMetadata;

    public boolean checkMachine_EM(IGregTechTileEntity iGregTechTileEntity, ItemStack itemStack) {

        spacetimeCompressionFieldMetadata = -1;
        timeAccelerationFieldMetadata = -1;
        stabilisationFieldMetadata = -1;

        // Check structure of multi.
        if (!structureCheck_EM("main", 16, 16, 0)) {
            return false;
        }

        // Make sure there are no Crafting Input Buffers/Buses/Slaves.
        if (!mDualInputHatches.isEmpty()) {
            return false;
        }

        // Check if there is at least 1 output hatch
        {
            if (mOutputHatches.size() == 0) {
                return false;
            }
        }

        // Check there is 1 input bus, and it is not a stocking input bus.
        {
            if (mInputBusses.size() != 1) {
                return false;
            }

            if (mInputBusses.get(0) instanceof MTEHatchInputBusME) {
                return false;
            }
        }

        // Make sure there are no energy hatches.
        {
            if (!mEnergyHatches.isEmpty()) {
                return false;
            }

            if (!mExoticEnergyHatches.isEmpty()) {
                return false;
            }
        }

        // Make sure there are 2 input hatches.
        return mInputHatches.size() == 2;
    }
}
