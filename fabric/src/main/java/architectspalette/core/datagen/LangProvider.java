package architectspalette.core.datagen;

import architectspalette.core.registry.util.BlockNode;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricLanguageProvider;
import net.minecraft.core.HolderLookup;

import java.util.concurrent.CompletableFuture;

public class LangProvider extends FabricLanguageProvider {
    public LangProvider(FabricDataOutput dataOutput, CompletableFuture<HolderLookup.Provider> registryLookup) {
        super(dataOutput, registryLookup);
    }

    @Override
    public void generateTranslations(HolderLookup.Provider registryLookup, TranslationBuilder builder) {
        BlockNode.forAllBaseNodes((node) -> {
            node.forEach(n -> {
                builder.add(n.get(), format(n.getName()));
            });
        });
    }

    private static String format(String name) {
        String filtered = name.replaceAll("_", " ");
        String upper = name.toUpperCase();
        int len = filtered.length();

        char[] buffer = new char[len];
        //Set first character to uppercase
        buffer[0] = upper.charAt(0);
        for (int i = 1; i < len; i++) {
            //Check for a space preceding this character
            if (filtered.charAt(i - 1) == ' ') {
                //Use uppercase after spaces
                buffer[i] = upper.charAt(i);
            }
            //Use normal text
            else {
                buffer[i] = filtered.charAt(i);
            }
        }
        return new String(buffer);
    }
}
