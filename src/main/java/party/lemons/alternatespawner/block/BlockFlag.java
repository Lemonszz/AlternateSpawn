package party.lemons.alternatespawner.block;

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
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityBanner;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.Mirror;
import net.minecraft.util.Rotation;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import party.lemons.alternatespawner.AlternateSpawn;
import party.lemons.alternatespawner.block.tileentity.TileEntityFlag;
import party.lemons.alternatespawner.spawning.FlagType;
import party.lemons.alternatespawner.spawning.WorldBaseData;

import javax.annotation.Nullable;

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
		this.setDefaultState(this.blockState.getBaseState().withProperty(ROTATION, Integer.valueOf(0)));

		this.type = type;
		this.setCreativeTab(CreativeTabs.DECORATIONS);
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

	public IBlockState withMirror(IBlockState state, Mirror mirrorIn)
	{
		return state.withProperty(ROTATION, Integer.valueOf(mirrorIn.mirrorRotation(((Integer)state.getValue(ROTATION)).intValue(), 16)));
	}

	public void neighborChanged(IBlockState state, World worldIn, BlockPos pos, Block blockIn, BlockPos fromPos)
	{
		if(!worldIn.getBlockState(pos.down()).getMaterial().isSolid())
		{
			this.dropBlockAsItem(worldIn, pos, state, 0);
			worldIn.setBlockToAir(pos);
		}
	}

	public IBlockState getStateFromMeta(int meta)
	{
		return this.getDefaultState().withProperty(ROTATION, Integer.valueOf(meta));
	}
	public int getMetaFromState(IBlockState state)
	{
		return state.getValue(ROTATION).intValue();
	}

	protected BlockStateContainer createBlockState()
	{
		return new BlockStateContainer(this, ROTATION);
	}
}