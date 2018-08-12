package party.lemons.alternatespawner.block.tileentity;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;

import java.util.UUID;

/**
 * Created by Sam on 12/08/2018.
 */
public class TileEntityFlag extends TileEntity
{
	private UUID owner;

	public UUID getOwner()
	{
		return owner;
	}

	public void setOwner(UUID owner)
	{
		this.owner = owner;
	}

	@Override
	public void readFromNBT(NBTTagCompound compound)
	{
		if(compound.hasKey("owner"))
			owner = compound.getUniqueId("owner");

		super.readFromNBT(compound);
	}

	@Override
	public NBTTagCompound writeToNBT(NBTTagCompound compound)
	{
		if(owner != null)
			compound.setUniqueId("owner", owner);

		return super.writeToNBT(compound);
	}
}
