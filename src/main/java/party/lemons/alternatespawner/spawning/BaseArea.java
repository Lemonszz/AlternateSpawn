package party.lemons.alternatespawner.spawning;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTUtil;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import java.util.UUID;

/**
 * Created by Sam on 12/08/2018.
 */
public class BaseArea
{
	private UUID owner;
	private int radius;
	private BlockPos pos;

	public BaseArea(UUID owner, int radius, BlockPos pos)
	{
		this.owner = owner;
		this.radius = radius;
		this.pos = pos;
	}

	public BaseArea(NBTTagCompound tags)
	{
		readFromNBT(tags);
	}

	public boolean inRadius(BlockPos checkPos)
	{
		return pos.getDistance(checkPos.getX(), checkPos.getY(), checkPos.getZ()) <= radius;
	}

	public void readFromNBT(NBTTagCompound compound)
	{
		owner = compound.getUniqueId("owner");
		radius = compound.getInteger("radius");
		pos = NBTUtil.getPosFromTag(compound.getCompoundTag("pos"));
	}

	public NBTTagCompound writeToNBT()
	{
		NBTTagCompound tagCompound = new NBTTagCompound();

		tagCompound.setUniqueId("owner", owner);
		tagCompound.setInteger("radius", radius);
		tagCompound.setTag("pos", NBTUtil.createPosTag(pos));

		return tagCompound;
	}

	public UUID getOwner()
	{
		return owner;
	}

	public BlockPos getPosition()
	{
		return pos;
	}
}
