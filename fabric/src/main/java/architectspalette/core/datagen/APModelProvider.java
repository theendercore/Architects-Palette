package architectspalette.core.datagen;

import architectspalette.core.registry.APBlocks;
import architectspalette.core.registry.APItems;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricModelProvider;
import net.minecraft.data.models.BlockModelGenerators;
import net.minecraft.data.models.ItemModelGenerators;
import net.minecraft.data.models.model.ModelTemplates;

import java.util.stream.Stream;

import static architectspalette.core.util.ModelHelper.*;

public class APModelProvider extends FabricModelProvider {
    public APModelProvider(FabricDataOutput output) {
        super(output);
    }

    @Override
    public void generateBlockStateModels(BlockModelGenerators gen) {
        makeNodeModels(gen, APBlocks.NEBULITE);
    }

    @Override
    public void generateItemModels(ItemModelGenerators gen) {
        Stream.of(
                APItems.ALGAL_BLEND,
                APItems.ALGAL_BRICK,
                APItems.WITHERED_BONE,
                APItems.ENTWINE_ROD,
                APItems.SUNMETAL_BLEND,
                APItems.SUNMETAL_BRICK,
                APItems.UNOBTANIUM,
                APItems.BRASS_BLEND,
                APItems.NETHER_BRASS,
                APItems.NETHER_BRASS_NUGGET,
                APItems.WARDSTONE_BLEND,
                APItems.WARDSTONE_BRICK,
                APItems.ORACLE_JELLY,
                APItems.CEREBRAL_PLATE
        ).forEach((item) -> gen.generateFlatItem(item.get(), ModelTemplates.FLAT_ITEM));
    }

}
