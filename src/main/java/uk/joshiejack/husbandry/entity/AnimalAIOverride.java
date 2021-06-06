package uk.joshiejack.husbandry.entity;

import net.minecraft.entity.AgeableEntity;
import net.minecraft.entity.Entity;
import net.minecraftforge.event.entity.EntityJoinWorldEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import uk.joshiejack.husbandry.animals.stats.AnimalStats;
import uk.joshiejack.husbandry.animals.traits.types.IAnimalTrait;
import uk.joshiejack.husbandry.animals.traits.types.IGoalTrait;
import uk.joshiejack.husbandry.entity.ai.HideFromStormGoal;

import java.util.stream.Stream;

import static uk.joshiejack.husbandry.Husbandry.MODID;

@SuppressWarnings("unused")
@Mod.EventBusSubscriber(modid = MODID)
public class AnimalAIOverride {
    @SubscribeEvent
    public static void onEntityJoinedWorld(EntityJoinWorldEvent event) {
        Entity entity = event.getEntity();
        if (!entity.level.isClientSide && entity instanceof AgeableEntity) {
            AnimalStats<?> stats = AnimalStats.getStats(entity);
            if (stats != null) {
                AgeableEntity ageable = ((AgeableEntity) entity);
                //All animals will hide from storms
                ageable.goalSelector.addGoal(4, new HideFromStormGoal(ageable, stats));
                Stream<IGoalTrait> traits = stats.getTraits(IAnimalTrait.Type.AI);
                traits.forEach(trait -> trait.modifyGoals(stats, ageable.goalSelector));
            }
        }
    }
}
