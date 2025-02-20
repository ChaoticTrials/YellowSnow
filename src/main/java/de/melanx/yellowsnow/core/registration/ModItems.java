package de.melanx.yellowsnow.core.registration;

import de.melanx.yellowsnow.YellowSnow;
import de.melanx.yellowsnow.items.YellowSnowballItem;
import net.minecraft.world.item.Item;
import org.moddingx.libx.annotation.registration.RegisterClass;

@RegisterClass(registry = "ITEM")
public class ModItems {

    public static final Item yellowSnowball = new YellowSnowballItem(YellowSnow.getInstance(), new Item.Properties().stacksTo(16));
}
