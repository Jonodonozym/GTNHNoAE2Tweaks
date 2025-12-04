package com.jdz.noae.mixins;

import java.util.Arrays;
import java.util.List;
import java.util.Set;

import javax.annotation.Nonnull;

import com.gtnewhorizon.gtnhmixins.ILateMixinLoader;
import com.gtnewhorizon.gtnhmixins.LateMixin;

@LateMixin
public class LateMixinsLoader implements ILateMixinLoader {

    @Override
    public String getMixinConfig() {
        return "mixins.gtnhnoaetweaks.late.json";
    }

    @Nonnull
    @Override
    public List<String> getMixins(Set<String> loadedMods) {
        return Arrays.asList(
            "InputSeparationIndustrialPress",
            "InputSeparationPreciseAssembler",
            "NoAEEOH",
            "NoAEGorge",
            "LinkedInputBusAALCompat");
    }
}
