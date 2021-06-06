package uk.joshiejack.husbandry.client.gui;

import net.minecraft.client.Minecraft;
import net.minecraft.entity.MobEntity;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.text.ITextComponent;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import uk.joshiejack.husbandry.api.HusbandryAPI;
import uk.joshiejack.husbandry.api.IMobStats;
import uk.joshiejack.penguinlib.client.gui.book.Book;
import uk.joshiejack.penguinlib.client.gui.book.page.AbstractMultiPage;

import java.util.List;
import java.util.stream.Collectors;

@OnlyIn(Dist.CLIENT)
public class PageStats extends AbstractMultiPage.Both<IMobStats<MobEntity>> {
    public PageStats(ITextComponent name) {
        super(name, 10);
    }

    @Override
    protected void initEntry(Book book, int x, int y, int id, IMobStats<MobEntity> stats) {
        book.addButton(new MobStatsLabel(stats, x + 20, y + 10 + id * 25, stats.getEntity().getName()));
    }

    @SuppressWarnings("ConstantConditions")
    @Override
    protected List<IMobStats<MobEntity>> getEntries() {
        return Minecraft.getInstance().level.getLoadedEntitiesOfClass(MobEntity.class,
                new AxisAlignedBB(Minecraft.getInstance().player.blockPosition()).inflate(64D)).stream()
                .filter(e -> HusbandryAPI.instance.getStatsForEntity(e) != null)
                .map(s -> HusbandryAPI.instance.getStatsForEntity(s))
                .collect(Collectors.toList());
    }
}
