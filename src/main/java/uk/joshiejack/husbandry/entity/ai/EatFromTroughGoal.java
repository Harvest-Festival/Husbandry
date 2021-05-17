package uk.joshiejack.husbandry.entity.ai;

/*
public class EatFromTroughGoal extends AbstractMoveToBlockGoal {
    private final ITag.INamedTag<Item> feed;

    public EatFromTroughGoal(CreatureEntity entity, AnimalStats<?> stats, ITag.INamedTag<Item> feed) {
        super(entity, stats, BESIDE, 16);
        this.feed = feed;
    }

    @Override
    public boolean canUse() {
        return !stats.hasEaten() && entity.getRandom().nextInt(5) == 0 && super.shouldExecute();
    }

    @Override
    protected boolean isValidTarget(@Nonnull IWorldReader world, @Nonnull BlockPos pos) {
        TileEntity tile = world.getTileEntity(pos);
        if (tile instanceof TroughTileEntity) {
            return ((TroughTileEntity)tile).getType() == feed;
        } else return false;
    }

    @Override
    public void tick() {
        super.tick();
        entity.getLookHelper().setLookPosition((double) destinationBlock.getX() + 0.5D, destinationBlock.getY() + 1,
                (double) destinationBlock.getZ() + 0.5D, 10.0F, (float) entity.getVerticalFaceSpeed());

        if (isNearDestination()) {
            TileEntity tile = entity.world.getTileEntity(destinationBlock);
            if (tile instanceof TroughTileEntity) {
                ((TroughTileEntity) tile).consume();
                stats.feed();
                entity.playLivingSound();
                timeoutCounter = 9999;
            }
        }
    }
} */