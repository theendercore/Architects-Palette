package architectspalette.core.api;

import net.minecraft.client.renderer.block.model.BlockModel;

public interface APCustomModelDataAccessor {
    static BlockModel setData(BlockModel model, CustomModelLoader.APCustomModelData data) {
        var modelAccess = (APCustomModelDataAccessor) model;
        modelAccess.ap$setCustomModelData(data);
        return (BlockModel) modelAccess;
    }

    static CustomModelLoader.APCustomModelData getData(BlockModel model) {
        var modelAccess = (APCustomModelDataAccessor) model;
        return modelAccess.ap$getCustomModelData();
    }

    CustomModelLoader.APCustomModelData ap$getCustomModelData();

    void ap$setCustomModelData(CustomModelLoader.APCustomModelData ap$customModelData);
}
