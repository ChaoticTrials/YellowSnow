package de.melanx.yellowsnow.core.registration;

import net.minecraft.network.chat.Component;
import net.minecraft.world.item.CreativeModeTab;
import org.moddingx.libx.creativetab.CreativeTabX;
import org.moddingx.libx.mod.ModX;

public class YellowTab extends CreativeTabX {

    public YellowTab(ModX mod) {
        super(mod);
    }

    @Override
    protected void addItems(TabContext ctx) {
        this.addModItems(ctx);
    }

    @Override
    protected void buildTab(CreativeModeTab.Builder builder) {
        builder.icon(ModItems.yellowSnowball::getDefaultInstance)
                .title(Component.literal("Yellow Snow"));
    }
}
