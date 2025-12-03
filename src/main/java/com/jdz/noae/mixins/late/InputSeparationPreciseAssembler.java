package com.jdz.noae.mixins.late;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;

import goodgenerator.blocks.tileEntity.MTEPreciseAssembler;
import gregtech.api.metatileentity.implementations.MTEMultiBlockBase;

@Mixin(MTEPreciseAssembler.class)
abstract class InputSeparationPreciseAssembler extends MTEMultiBlockBase {

    public InputSeparationPreciseAssembler(int aID, String aName, String aNameRegional) {
        super(aID, aName, aNameRegional);
    }

    public boolean supportsInputSeparation() {
        return true;
    }

    @Overwrite(remap = false)
    public boolean isInputSeparationEnabled() {
        return super.isInputSeparationEnabled();
    }
}
