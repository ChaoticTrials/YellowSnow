package de.melanx.yellowsnow;

import net.minecraft.resources.ResourceLocation;
import org.moddingx.libx.annotation.config.RegisterConfig;
import org.moddingx.libx.config.Config;
import org.moddingx.libx.config.validate.IntRange;
import org.moddingx.libx.util.data.ResourceList;

@RegisterConfig
public class ModConfig {

    @Config("If this is turned on, yellow snow is able to spread to other snow")
    public static boolean spreadable = true;

    @Config("Time in ticks a living entity needs to stand on snow to turn it into yellow snow")
    @IntRange(min = 1)
    public static int peeTickDuration = 100;

    @Config({"A list of all living entities which can't pee.",
            "Example: ",
            "\"minecraft:cow\"",
            "\"minecraft:zombie\""
    })
    public static ResourceList forbiddenToPee = new ResourceList(false, builder -> builder.simple(ResourceLocation.fromNamespaceAndPath("minecraft", "player")));
}
