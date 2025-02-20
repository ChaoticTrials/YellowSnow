package de.melanx.yellowsnow.data;

import de.melanx.yellowsnow.core.registration.ModBlocks;
import de.melanx.yellowsnow.core.registration.ModItems;
import org.moddingx.libx.datagen.DatagenContext;
import org.moddingx.libx.datagen.provider.recipe.RecipeProviderBase;
import org.moddingx.libx.datagen.provider.recipe.crafting.CompressionExtension;

public class RecipesProvider extends RecipeProviderBase implements CompressionExtension {

    public RecipesProvider(DatagenContext ctx) {
        super(ctx);
    }

    @Override
    protected void setup() {
        this.smallCompress(ModItems.yellowSnowball, ModBlocks.yellowSnowBlock, false);
    }
}
