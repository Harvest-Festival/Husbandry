package uk.joshiejack.husbandry.block;

/* public class TroughBlock extends AbstractPenguinBlock {
    public static final AxisAlignedBB FENCE_COLLISION =  new AxisAlignedBB(0D, 0D, 0D, 1D, 1.5D, 1D);
    private static final AxisAlignedBB TROUGH_AABB =  new AxisAlignedBB(0D, 0D, 0D, 1D, 0.75D, 1D);

    public TroughBlock() {
        super(AbstractBlock.Properties.of(Material.WOOD).strength(1.5F).sound(SoundType.WOOD));
        hasInventory = true;
    }

    @SuppressWarnings("deprecation")
    @Override
    public void addCollisionBoxToList(@Nonnull BlockState state, @Nonnull World world, @Nonnull BlockPos pos,
                                      @Nonnull AxisAlignedBB entityBox, @Nonnull List<AxisAlignedBB> collidingBoxes,
                                      @Nullable Entity entityIn, boolean isActualState) {
        if (entityIn instanceof EntityPlayer) addCollisionBoxToList(pos, entityBox, collidingBoxes, TROUGH_AABB);
        else addCollisionBoxToList(pos, entityBox, collidingBoxes, FENCE_COLLISION);
    }

    @SuppressWarnings("deprecation")
    @Nonnull
    @Override
    public AxisAlignedBB getBoundingBox(@Nonnull BlockState state, @Nonnull IBlockAccess world, @Nonnull BlockPos pos) {
        return TROUGH_AABB;
    }

    @Override
    @Nonnull
    public BlockState getActualState(@Nonnull BlockState state, IBlockAccess world, BlockPos pos) {
        TileEntity tile = world instanceof ChunkCache ? ((ChunkCache)world).getTileEntity(pos, Chunk.EnumCreateEntityType.CHECK) : world.getTileEntity(pos);
        if (tile instanceof TroughTileEntity) {
            boolean north = isTrough(NORTH, world, pos);
            boolean south = isTrough(SOUTH, world, pos);
            if (north && !south) return state.withProperty(property, Section.END).withProperty(FACING, EAST);
            if (south && !north) return state.withProperty(property, Section.END).withProperty(FACING, WEST);
            if (south) return state.withProperty(property, Section.MIDDLE).withProperty(FACING, EAST);

            boolean east = isTrough(EAST, world, pos);
            boolean west = isTrough(WEST, world, pos);
            if (west && east) return state.withProperty(property, Section.MIDDLE).withProperty(FACING, SOUTH);
            if (east) return state.withProperty(property, Section.END).withProperty(FACING, SOUTH);
            if (west) return state.withProperty(property, Section.END).withProperty(FACING, NORTH);

            return state.withProperty(property, Section.SINGLE);
        }

        return state;
    }

    @SuppressWarnings("ConstantConditions")
    private boolean isTrough(Direction facing, IBlockAccess world, BlockPos pos) {
        return world.getBlockState(pos.offset(facing)).getBlock() == this && (((TroughTileEntity) world.getTileEntity(pos)).getMasterBlock() == ((TroughTileEntity) world.getTileEntity(pos.offset(facing))).getMasterBlock());
    }

    @SuppressWarnings("ConstantConditions")
    @Override
    public void onBlockPlacedBy(World world, BlockPos pos, BlockState state, EntityLivingBase placer, ItemStack stack) {
        super.onBlockPlacedBy(world, pos, state, placer, stack);
        ((TroughTileEntity)world.getTileEntity(pos)).onBlockPlaced();
    }

    @SuppressWarnings("ConstantConditions")
    @Override
    public void breakBlock(@Nonnull World world, @Nonnull BlockPos pos, @Nonnull BlockState state) {
        ((TroughTileEntity)world.getTileEntity(pos)).onBlockRemoved();
        super.breakBlock(world, pos, state);
    }

    @Nonnull
    @Override
    public TileEntity createTileEntity(@Nonnull World world, @Nonnull BlockState state) {
        return new TroughTileEntity();
    }

    @Nonnull
    @Override
    protected ItemStack getCreativeStack(Section section) {
        return section == Section.SINGLE ? super.getCreativeStack(section) : ItemStack.EMPTY;
    }

    public enum Section implements IStringSerializable {
        SINGLE, END, MIDDLE;

        @Override
        public @Nonnull String getName() {
            return toString().toLowerCase(Locale.ENGLISH);
        }
    }
} */