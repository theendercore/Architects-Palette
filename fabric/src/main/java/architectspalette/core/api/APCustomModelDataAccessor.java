package architectspalette.core.api;

import net.minecraft.client.renderer.block.model.BlockModel;

public interface APCustomModelDataAccessor {
    static BlockModel setData(BlockModel model, CustomModelLoader.APCustomModelData data) {
        var modelAccess = (APCustomModelDataAccessor) model;
        modelAccess.architects_palette$setCustomModelData(data);
        return (BlockModel) modelAccess;
    }

    static CustomModelLoader.APCustomModelData getData(BlockModel model) {
        var modelAccess = (APCustomModelDataAccessor) model;
        return modelAccess.architects_palette$getCustomModelData();
    }

    CustomModelLoader.APCustomModelData architects_palette$getCustomModelData();

    void architects_palette$setCustomModelData(CustomModelLoader.APCustomModelData ap$customModelData);
}
