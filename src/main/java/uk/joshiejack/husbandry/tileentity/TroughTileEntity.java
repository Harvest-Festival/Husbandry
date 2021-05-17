package uk.joshiejack.husbandry.tileentity;

//@PenguinLoader("trough")
//public class TroughTileEntity extends ConnectableTileEntity<TroughTileEntity> {
//    private final TroughRenderData renderData = new TroughRenderData();
//    public TroughTileEntity() {
//        super(2);
//    }
//
//    public TroughRenderData getRenderData() {
//        return renderData;
//    }
//
//    @Override
//    public boolean isStackValidForInsertion(int slot, ItemStack stack) {
//        if (stack.getItem() != HusbandryItems.FEED) return false;
//        else {
//            ItemFeed.Feed feed = HusbandryItems.FEED.getEnumFromStack(stack);
//            return feed == ItemFeed.Feed.FODDER || feed == ItemFeed.Feed.SLOP;
//        }
//    }
//
//    @Override
//    public boolean isSlotValidForExtraction(int slot, int amount) {
//        return false;
//    }
//
//    public ItemFeed.Feed getType() {
//        ItemStack inventory = getMasterBlock().getStack();
//        if (inventory.isEmpty()) return null;
//        else {
//            return HusbandryItems.FEED.getEnumFromStack(inventory);
//        }
//    }
//
//    public void consume() {
//        ConnectableTileEntity<?> master = getMasterBlock();
//        master.getStack().shrink(1);
//        PenguinNetwork.sendToNearby(master, new SetInventorySlotPacket(master.getPos(), 0, master.getStack()));
//    }
//
//    @Override
//    protected ValidatedStackHandler createHandler(int size) {
//        return new ValidatedStackHandler(this, size) {
//            @Override
//            public int getSlotLimit(int slot) {
//                return (1 + members) * 16;
//            }
//        };
//    }
//
//    @Override
//    protected boolean isSameTile(BlockPos pos) {
//        return world.getTileEntity(pos) instanceof TroughTileEntity;
//    }
//
//    @Override
//    public void onSurroundingsChanged() {
//        super.onSurroundingsChanged();
//        renderData.reset();
//    }
//
//    @Override
//    protected boolean onMasterClicked(PlayerEntity player, Hand hand) {
//        ItemStack held = player.getItemInHand(hand);
//        if (isStackValidForInsertion(0, held)) {
//            ItemStack result = ItemHandlerHelper.insertItem(handler, held, false);
//            if (result.isEmpty() || result.getCount() != held.getCount()) {
//                player.setHeldItem(hand, result);
//                markDirty();
//                return true;
//            }
//        }
//
//        return false;
//    }
//}
