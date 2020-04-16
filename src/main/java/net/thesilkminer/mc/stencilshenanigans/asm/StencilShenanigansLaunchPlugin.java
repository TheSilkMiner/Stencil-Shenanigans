package net.thesilkminer.mc.stencilshenanigans.asm;

import com.google.common.collect.ImmutableSet;
import net.thesilkminer.mc.fermion.asm.api.PluginMetadata;
import net.thesilkminer.mc.fermion.asm.prefab.AbstractLaunchPlugin;

import javax.annotation.Nonnull;
import java.util.Set;

public final class StencilShenanigansLaunchPlugin extends AbstractLaunchPlugin {
    public StencilShenanigansLaunchPlugin() {
        super("stencilshenanigans.asm");
        this.registerTransformer(new FramebufferTransformer(this));
    }

    @Override
    protected void populateMetadata(@Nonnull final PluginMetadata.Builder builder) {
        builder.setName("Stencil Shenanigans ASM")
                .setVersion("1.0.0")
                .setDescription("ASM patch that enables stencils for Minecraft")
                .setCredits("Forge for being lazy fucks, Funwayguy for the original patch file")
                .addAuthor("TheSilkMiner")
                .addAuthor("Funwayguy")
                .setLogoPath("stencil_shenanigans_logo.png");
    }

    @Nonnull
    @Override
    public Set<String> getRootPackages() {
        return ImmutableSet.of("net.thesilkminer.mc.stencilshenanigans.asm");
    }
}
