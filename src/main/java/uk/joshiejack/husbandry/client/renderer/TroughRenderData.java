package uk.joshiejack.husbandry.client.renderer;

//public class TroughRenderData {
//    private TroughBlock.Section section;
//    private Direction facing;
//
//    public void reset() {
//        section = null;
//        facing = null;
//    }
//
//    public TroughBlock.Section getSection(World world, BlockPos pos) {
//        if (section != null) return section;
//        else {
//            BlockState state = world.getBlockState(pos);
//            BlockState actualState = state.getActualState(world, pos);
//            section = actualState.getValue(HusbandryBlocks.TROUGH.property);
//        }
//
//        return section;
//    }
//
//    public Direction getFacing(World world, BlockPos pos) {
//        if (facing != null) return facing;
//        else {
//            BlockState state = world.getBlockState(pos);
//            BlockState actualState = state.getActualState(world, pos);
//            facing = actualState.getValue(BlockMultiTileRotatable.FACING);
//        }
//
//        return facing;
//    }
//}
