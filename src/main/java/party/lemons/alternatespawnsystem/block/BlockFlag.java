package party.lemons.alternatespawnsystem.block;

import net.minecraft.block.Block;
import net.minecraft.block.BlockHorizontal;
import net.minecraft.block.material.MapColor;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyDirection;
import net.minecraft.block.properties.PropertyInteger;
import net.minecraft.block.state.BlockFaceShape;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.EnumDyeColor;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.*;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.oredict.DyeUtils;
import party.lemons.alternatespawnsystem.block.tileentity.TileEntityFlag;
import party.lemons.alternatespawnsystem.spawning.FlagType;
import party.lemons.alternatespawnsystem.spawning.WorldBaseData;

import javax.annotation.Nullable;
import java.util.Optional;

/**
 * Created by Sam on 12/08/2018.
 */
public class BlockFlag extends Block
{
	public static final PropertyDirection FACING = BlockHorizontal.FACING;
	public static final PropertyInteger ROTATION = PropertyInteger.create("rotation", 0, 15);
	protected static final AxisAlignedBB STANDING_AABB = new AxisAlignedBB(0.25D, 0.0D, 0.25D, 0.75D, 1.0D, 0.75D);
	private FlagType type;

	public BlockFlag(FlagType type)
	{
		super(Material.CLOTH, MapColor.BLUE);

		if(!isWall())
			this.setDefaultState(this.blockState.getBaseState().withProperty(ROTATION, Integer.valueOf(0)));

		this.type = type;
		this.setCreativeTab(CreativeTabs.DECORATIONS);
	}

	public static Block getWallInstance(Block block)
	{
		if(block == AlternateSpawnBlocks.BASIC_FLAG)
			return AlternateSpawnBlocks.BASIC_FLAG_WALL;

		if(block == AlternateSpawnBlocks.GOLDEN_FLAG)
			return AlternateSpawnBlocks.GOLDEN_FLAG_WALL;

		if(block == AlternateSpawnBlocks.DIAMOND_FLAG)
			return AlternateSpawnBlocks.DIAMOND_FLAG_WALL;

		return Blocks.AIR;
	}

	public EnumBlockRenderType getRenderType(IBlockState state)
	{
		return EnumBlockRenderType.INVISIBLE;
	}

	public FlagType getType()
	{
		return type;
	}

	public void breakBlock(World worldIn, BlockPos pos, IBlockState state)
	{
		WorldBaseData.get(worldIn).removeBaseAt(pos);
		super.breakBlock(worldIn, pos, state);
	}

	public boolean hasTileEntity(IBlockState state)
	{
		return true;
	}
	@Nullable
	public TileEntity createTileEntity(World world, IBlockState state)
	{
		return new TileEntityFlag();
	}

	public BlockFaceShape getBlockFaceShape(IBlockAccess worldIn, IBlockState state, BlockPos pos, EnumFacing face){ return BlockFaceShape.UNDEFINED; }
	public AxisAlignedBB getCollisionBoundingBox(IBlockState blockState, IBlockAccess worldIn, BlockPos pos){ return NULL_AABB; }
	public boolean isFullCube(IBlockState state)
	{
		return false;
	}
	public boolean isPassable(IBlockAccess worldIn, BlockPos pos)
	{
		return true;
	}
	public boolean isOpaqueCube(IBlockState state)
	{
		return false;
	}
	public boolean canSpawnInBlock()
	{
		return true;
	}

	public AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess source, BlockPos pos)
	{
		return STANDING_AABB;
	}

	public IBlockState withRotation(IBlockState state, Rotation rot)
	{
		return state.withProperty(ROTATION, Integer.valueOf(rot.rotate(((Integer)state.getValue(ROTATION)).intValue(), 16)));
	}

	/**
	 * Returns the blockstate with the given mirror of the passed blockstate. If inapplicable, returns the
	 * passed blockstate.
	 * @deprecated call via {@link IBlockState#withMirror(Mirror)} whenever possible. Implementing/overriding is
	 * fine.
	 */
	public IBlockState withMirror(IBlockState state, Mirror mirrorIn)
	{
		return state.withProperty(ROTATION, Integer.valueOf(mirrorIn.mirrorRotation(((Integer)state.getValue(ROTATION)).intValue(), 16)));
	}

	/**
	 * Called when a neighboring block was changed and marks that this state should perform any checks during a
	 * neighbor change. Cases may include when redstone power is updated, cactus blocks popping off due to a
	 * neighboring solid block, etc.
	 */
	public void neighborChanged(IBlockState state, World worldIn, BlockPos pos, Block blockIn, BlockPos fromPos)
	{
		if (!worldIn.getBlockState(pos.down()).getMaterial().isSolid() && !isWall())
		{
			this.dropBlockAsItem(worldIn, pos, state, 0);
			worldIn.setBlockToAir(pos);
		}

		super.neighborChanged(state, worldIn, pos, blockIn, fromPos);
	}

	/**
	 * Convert the given metadata into a BlockState for this Block
	 */
	public IBlockState getStateFromMeta(int meta)
	{
		return this.getDefaultState().withProperty(ROTATION, Integer.valueOf(meta));
	}

	/**
	 * Convert the BlockState into the correct metadata value
	 */
	public int getMetaFromState(IBlockState state)
	{
		return ((Integer)state.getValue(ROTATION)).intValue();
	}

	protected BlockStateContainer createBlockState()
	{
		return new BlockStateContainer(this, new IProperty[] {ROTATION});
	}

	public boolean isWall()
	{
		return false;
	}

	public boolean onBlockActivated(World worldIn, BlockPos pos, IBlockState state, EntityPlayer playerIn, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ)
	{
		if(!playerIn.isSneaking())
		{
			if(WorldBaseData.get(worldIn).hasBaseAt(pos))
			{
				playerIn.sendStatusMessage(new TextComponentTranslation("alternatespawnsystem.message.deactivate"), true);
				WorldBaseData.get(worldIn).removeBaseAt(pos);
			}else
			{
				if(!WorldBaseData.get(worldIn).canCreateBase(playerIn))
				{
					BlockPos removePos = WorldBaseData.get(worldIn).findFirst(playerIn);
					WorldBaseData.get(worldIn).createBase(pos, getType().getFlagSize(), playerIn);
					WorldBaseData.get(worldIn).removeBaseAt(removePos);
					playerIn.sendStatusMessage(new TextComponentTranslation("alternatespawnsystem.message.activateremove"), true);

				}else
				{
					WorldBaseData.get(worldIn).createBase(pos, getType().getFlagSize(), playerIn);
					playerIn.sendStatusMessage(new TextComponentTranslation("alternatespawnsystem.message.activate"), true);
				}
			}
		}
		else
		{
			boolean hasBase = WorldBaseData.get(worldIn).hasBaseAt(pos);
			String translate = hasBase ? "alternatespawnsystem.message.hasbase" : "alternatespawnsystem.message.nobase";
			playerIn.sendStatusMessage(new TextComponentTranslation(translate), true);
		}

		return true;
	}
}