package uk.joshiejack.husbandry.client.renderer;

//@SideOnly(Dist.CLIENT)
//@PenguinLoader(value = "trough", side = Dist.CLIENT)
//public class TroughTileEntityRenderer extends AbstractItemTileEntityRenderer<TroughTileEntity> {
//    @Override
//    public void render(@Nonnull TroughTileEntity tile, double x, double y, double z, float partialTicks, int destroyStage, float alpha) {
//        TroughTileEntity master = tile.getMasterBlock();
//        ItemFeed.Feed feed = master.getType();
//        if (feed != null) {
//            ResourceLocation texture = feed == ItemFeed.Feed.FODDER ? HusbandryTextures.FODDER : HusbandryTextures.SLOP;
//            float height = 0.25F + ((float) master.getStack().getCount() / (16 * (master.getMembers() + 1))) * 0.45F;
//            TroughBlock.Section section = tile.getRenderData().getSection(tile.getWorld(), tile.getPos());
//            switch (section) {
//                case SINGLE:
//                    renderFluidPlane(texture, (float) x + 0.5F, (float) y + height, (float) z + 0.5F, 0.75F, 0.75F);
//                    break;
//                case END: {
//                    Direction facing = tile.getRenderData().getFacing(tile.getWorld(), tile.getPos());
//                    if (facing == EnumFacing.NORTH) {
//                        renderFluidPlane(texture, (float) x + 0.45F, (float) y + height, (float) z + 0.5F, 0.9F, 0.75F);
//                    } else if (facing == EnumFacing.SOUTH) {
//                        renderFluidPlane(texture, (float) x + 0.55F, (float) y + height, (float) z + 0.5F, 0.9F, 0.75F);
//                    } else if (facing == EnumFacing.EAST) {
//                        renderFluidPlane(texture, (float) x + 0.5F, (float) y + height, (float) z + 0.45F, 0.75F, 0.9F);
//                    } else if (facing == EnumFacing.WEST) {
//                        renderFluidPlane(texture, (float) x + 0.5F, (float) y + height, (float) z + 0.55F, 0.75F, 0.9F);
//                    }
//                    break;
//                }
//                case MIDDLE: {
//                    Direction facing = tile.getRenderData().getFacing(tile.getWorld(), tile.getPos());
//                    if (facing == EnumFacing.NORTH || facing == EnumFacing.SOUTH) {
//                        renderFluidPlane(texture, (float) x + 0.5F, (float) y + height, (float) z + 0.5F, 1F, 0.75F);
//                    } else if (facing == EnumFacing.EAST || facing == EnumFacing.WEST) {
//                        renderFluidPlane(texture, (float) x + 0.5F, (float) y + height, (float) z + 0.5F, 0.75F, 1F);
//                    }
//                    break;
//                }
//            }
//        }
//    }
//}
