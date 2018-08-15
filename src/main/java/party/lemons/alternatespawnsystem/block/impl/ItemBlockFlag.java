package party.lemons.alternatespawnsystem.block.impl;

import net.minecraft.advancements.CriteriaTriggers;
import net.minecraft.block.Block;
import net.minecraft.block.BlockWallSign;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.*;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.text.translation.I18n;
import net.minecraft.world.World;
import party.lemons.alternatespawnsystem.AlternateSpawn;
import party.lemons.alternatespawnsystem.block.tileentity.TileEntityFlag;
import party.lemons.alternatespawnsystem.config.AlternateSpawnConfig;
import party.lemons.alternatespawnsystem.spawning.FlagType;

/**
 * Created by Sam on 13/08/2018.
 */
public class ItemBlockFlag extends ItemBlock
{
	public ItemBlockFlag(Block flag)
	{
		super(flag);

		this.maxStackSize = AlternateSpawnConfig.FLAG_STACK_SIZER;
		this.setCreativeTab(CreativeTabs.DECORATIONS);
	}

	public EnumActionResult onItemUse(EntityPlayer player, World worldIn, BlockPos pos, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ)
	{
		IBlockState state = worldIn.getBlockState(pos);
		boolean flag = state.getBlock().isReplaceable(worldIn, pos);

		if (facing != EnumFacing.DOWN && (state.getMaterial().isSolid() || flag) && (!flag || facing == EnumFacing.UP))
		{
			pos = pos.offset(facing);
			ItemStack itemstack = player.getHeldItem(hand);

			if (player.canPlayerEdit(pos, facing, itemstack) && block.canPlaceBlockAt(worldIn, pos))
			{
				if (worldIn.isRemote)
				{
					return EnumActionResult.SUCCESS;
				}
				else
				{
					pos = flag ? pos.down() : pos;

					IBlockState placeState = block.getDefaultState();

					if (facing == EnumFacing.UP)
					{
						//ground
						int i = MathHelper.floor((double)((player.rotationYaw + 180.0F) * 16.0F / 360.0F) + 0.5D) & 15;
						placeState = block.getDefaultState().withProperty(BlockFlag.ROTATION, Integer.valueOf(i));
					}
					else
					{
						//Wall
						placeState = BlockFlag.getWallInstance(block).getDefaultState().withProperty(BlockWallSign.FACING, facing);
					}
					worldIn.setBlockState(pos, placeState);
					placeState.getBlock().onBlockPlacedBy(worldIn, pos, placeState, player, itemstack);

					if (player instanceof EntityPlayerMP)
					{
						CriteriaTriggers.PLACED_BLOCK.trigger((EntityPlayerMP)player, pos, itemstack);
					}

					///Set colour
					TileEntity te = worldIn.getTileEntity(pos);
					if(te instanceof TileEntityFlag)
					{
						int hex = getColor(itemstack);
						int r = (hex & 0xFF0000) >> 16;
						int g = (hex & 0xFF00) >> 8;
						int b = (hex & 0xFF);

						((TileEntityFlag)te).setRgb(r, g, b);
					}

					itemstack.shrink(1);
					return EnumActionResult.SUCCESS;
				}
			}
			else
			{
				return EnumActionResult.FAIL;
			}
		}
		else
		{
			return EnumActionResult.FAIL;
		}
	}

	public String getItemStackDisplayName(ItemStack stack)
	{
		FlagType type = ((BlockFlag)block).getType();
		String s = "item." + AlternateSpawn.MODID +"." + type.getName() + ".flag";
		return I18n.translateToLocal(s);
	}

	/***
	 * Sets the colour of an itemstack
	 * @param stack
	 * @param color
	 */
	public void setColor(ItemStack stack, int color)
	{
		NBTTagCompound nbttagcompound = stack.getTagCompound();

		if (nbttagcompound == null)
		{
			nbttagcompound = new NBTTagCompound();
			stack.setTagCompound(nbttagcompound);
		}

		NBTTagCompound nbttagcompound1 = nbttagcompound.getCompoundTag("display");

		if (!nbttagcompound.hasKey("display", 10))
		{
			nbttagcompound.setTag("display", nbttagcompound1);
		}

		nbttagcompound1.setInteger("color", color);
	}

	//TODO: Add cauldron washing
	/***
	 * Removes colour from itemstack
	 * @param stack
	 */
	public void removeColor(ItemStack stack)
	{
		NBTTagCompound nbttagcompound = stack.getTagCompound();

		if (nbttagcompound != null)
		{
			NBTTagCompound nbttagcompound1 = nbttagcompound.getCompoundTag("display");

			if(nbttagcompound1.hasKey("color"))
			{
				nbttagcompound1.removeTag("color");
			}
		}
	}

	/***
	 * Gets colour from itemstack
	 * @param stack
	 * @return colour
	 */
	public int getColor(ItemStack stack)
	{
		NBTTagCompound nbttagcompound = stack.getTagCompound();

		if (nbttagcompound != null)
		{
			NBTTagCompound nbttagcompound1 = nbttagcompound.getCompoundTag("display");

			if (nbttagcompound1 != null && nbttagcompound1.hasKey("color", 3))
			{
				return nbttagcompound1.getInteger("color");
			}
		}

		return 0xFFFFFF;
	}

	/***
	 * Does that stack have a colour?
	 * @param stack
	 * @return has colour
	 */
	public boolean hasColor(ItemStack stack)
	{
		NBTTagCompound nbttagcompound = stack.getTagCompound();
		return nbttagcompound != null && nbttagcompound.hasKey("display", 10) ? nbttagcompound.getCompoundTag("display").hasKey("color", 3) : false;
	}

}
