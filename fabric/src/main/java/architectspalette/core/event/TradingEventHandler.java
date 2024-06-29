package architectspalette.core.event;

import architectspalette.core.integration.APTrades;
import net.fabricmc.fabric.api.object.builder.v1.trade.TradeOfferHelper;

@SuppressWarnings("UnstableApiUsage")
public class TradingEventHandler {
    public static void registerTrades() {
        APTrades.PROFESSION_MAP.forEach(((profession, levelToTrade) -> {
            levelToTrade.forEach((level, trade) -> {
                TradeOfferHelper.registerVillagerOffers(profession, level, (trades, rebalanced) -> trades.addAll(trade));
            });
        }));

        // (ender) From what I understand this won't work with the rebalanced trades datapacks
        // I'll look in to fixing this later.
        APTrades.WONDERING_TRADES.forEach((level, trades) -> {
            TradeOfferHelper.registerWanderingTraderOffers(level, (oldTrades) -> oldTrades.addAll(trades));
        });

    }
}
