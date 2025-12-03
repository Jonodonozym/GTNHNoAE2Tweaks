package com.jdz.noae.mixins.late;

import java.util.ArrayList;

import net.minecraft.item.ItemStack;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;

import gregtech.api.interfaces.tileentity.IGregTechTileEntity;
import tectech.thing.metaTileEntity.multi.MTEForgeOfGods;
import tectech.thing.metaTileEntity.multi.base.TTMultiblockBase;
import tectech.thing.metaTileEntity.multi.godforge_modules.MTEBaseModule;

@Mixin(MTEForgeOfGods.class)
abstract class NoAEGorge extends TTMultiblockBase {

    public NoAEGorge(int aID, String aName, String aNameRegional) {
        super(aID, aName, aNameRegional);
    }

    @Shadow(remap = false)
    boolean isRenderActive;
    @Shadow(remap = false)
    int internalBattery;
    @Shadow(remap = false)
    int ringAmount;
    @Shadow(remap = false)
    public ArrayList<MTEBaseModule> moduleHatches;

    private static final String STRUCTURE_PIECE_MAIN = "main";
    private static final String STRUCTURE_PIECE_SHAFT = "beam_shaft";
    private static final String STRUCTURE_PIECE_FIRST_RING_AIR = "first_ring_air";
    private static final String STRUCTURE_PIECE_SECOND_RING = "second_ring";
    private static final String STRUCTURE_PIECE_SECOND_RING_AIR = "second_ring_air";
    private static final String STRUCTURE_PIECE_THIRD_RING = "third_ring";
    private static final String STRUCTURE_PIECE_THIRD_RING_AIR = "third_ring_air";

    @Shadow(remap = false)
    private void destroyRenderer() {}

    @Shadow(remap = false)
    private void createRenderer() {}

    @Shadow(remap = false)
    private void UpdateRenderer() {}

    @Shadow(remap = false)
    private void destroySecondRing() {}

    @Shadow(remap = false)
    private void destroyThirdRing() {}

    @Shadow(remap = false)
    private void buildSecondRing() {}

    @Shadow(remap = false)
    private void buildThirdRing() {}

    @Shadow(remap = false)
    private boolean isUpgradeActive(int upgradeID) {
        return true;
    }

    @Overwrite(remap = false)
    public boolean checkMachine_EM(IGregTechTileEntity iGregTechTileEntity, ItemStack itemStack) {
        moduleHatches.clear();
        // Check structure of multi
        if (isRenderActive) {
            if (!structureCheck_EM(STRUCTURE_PIECE_SHAFT, 63, 14, 1)
                || !structureCheck_EM(STRUCTURE_PIECE_FIRST_RING_AIR, 63, 14, -59)) {
                destroyRenderer();
                return false;
            }
        } else if (!structureCheck_EM(STRUCTURE_PIECE_MAIN, 63, 14, 1)) {
            return false;
        }

        if (internalBattery != 0 && !isRenderActive) {
            createRenderer();
        }
        // Check there is 1 input bus
        if (mInputBusses.size() != 1) {
            return false;
        }

        // Check there is 1 me output bus
        if (mOutputBusses.size() != 1) {
            return false;
        }

        // Make sure there are no energy hatches
        {
            if (mEnergyHatches.size() > 0) {
                return false;
            }

            if (mExoticEnergyHatches.size() > 0) {
                return false;
            }
        }

        // Make sure there is 1 input hatch
        if (mInputHatches.size() != 1) {
            return false;
        }

        if (isUpgradeActive(26)) {
            if (checkPiece(STRUCTURE_PIECE_SECOND_RING, 55, 11, -67)) {
                ringAmount = 2;
                destroySecondRing();
                UpdateRenderer();
            }
            if (isRenderActive && ringAmount >= 2 && !checkPiece(STRUCTURE_PIECE_SECOND_RING_AIR, 55, 11, -67)) {
                destroyRenderer();
            }
        } else {
            if (ringAmount == 3) {
                buildThirdRing();
            }
            if (ringAmount >= 2) {
                ringAmount = 1;
                UpdateRenderer();
                buildSecondRing();
            }
        }

        if (isUpgradeActive(29)) {
            if (checkPiece(STRUCTURE_PIECE_THIRD_RING, 47, 13, -76)) {
                ringAmount = 3;
                destroyThirdRing();
                UpdateRenderer();
            }
            if (isRenderActive && ringAmount == 3 && !checkPiece(STRUCTURE_PIECE_THIRD_RING_AIR, 47, 13, -76)) {
                destroyRenderer();
            }
        } else {
            if (ringAmount == 3) {
                ringAmount = 2;
                UpdateRenderer();
                buildThirdRing();
            }
        }

        return true;
    }
}
