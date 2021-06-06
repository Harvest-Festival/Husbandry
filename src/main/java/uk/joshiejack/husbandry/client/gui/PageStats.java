package uk.joshiejack.husbandry.client.gui;

import net.minecraft.client.Minecraft;
import net.minecraft.entity.MobEntity;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.text.ITextComponent;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import uk.joshiejack.husbandry.entity.stats.MobStats;
import uk.joshiejack.penguinlib.client.gui.book.Book;
import uk.joshiejack.penguinlib.client.gui.book.page.AbstractMultiPage;

import java.util.List;
import java.util.stream.Collectors;

@OnlyIn(Dist.CLIENT)
public class PageStats extends AbstractMultiPage.Both<MobStats<?>> {
    public PageStats(ITextComponent name) {
        super(name, 10);
    }

    @Override
    protected void initEntry(Book book, int x, int y, int id, MobStats<?> stats) {
        book.addButton(new MobStatsLabel(stats, x + 20, y + 10 + id * 25, stats.getEntity().getName()));
    }

    @SuppressWarnings("ConstantConditions")
    @Override
    protected List<MobStats<?>> getEntries() {
        return Minecraft.getInstance().level.getLoadedEntitiesOfClass(MobEntity.class,
                new AxisAlignedBB(Minecraft.getInstance().player.blockPosition()).inflate(64D)).stream()
                .filter(e -> MobStats.getStats(e) != null)
                .map(MobStats::getStats)
                .collect(Collectors.toList());
    }
}
