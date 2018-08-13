package party.lemons.alternatespawnsystem.block.tileentity;

import net.minecraft.block.Block;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.play.server.SPacketUpdateTileEntity;
import net.minecraft.tileentity.TileEntity;

import java.util.UUID;

/**
 * Created by Sam on 12/08/2018.
 */
public class TileEntityFlag extends TileEntity
{
	private UUID owner;
	private float red, green, blue;

	public TileEntityFlag()
	{
		red = 1F;
		green = 1F;
		blue = 1F;
	}

	public void setRgb(float red, float green, float blue)
	{
		this.red = red / 255F;
		this.green = green / 255F;
		this.blue = blue / 255F;

		this.markDirty();
	}

	public float getBlue()
	{
		return blue;
	}

	public float getGreen()
	{
		return green;
	}

	public float getRed()
	{
		return red;
	}

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

		this.red = compound.getFloat("red");
		this.green = compound.getFloat("green");
		this.blue = compound.getFloat("blue");

		super.readFromNBT(compound);
	}

	@Override
	public NBTTagCompound writeToNBT(NBTTagCompound compound)
	{
		if(owner != null)
			compound.setUniqueId("owner", owner);

		compound.setFloat("red", red);
		compound.setFloat("green", green);
		compound.setFloat("blue", blue);

		return super.writeToNBT(compound);
	}

	@Override
	public NBTTagCompound getUpdateTag() {
		return writeToNBT(new NBTTagCompound());
	}

	@Override
	public SPacketUpdateTileEntity getUpdatePacket() {
		NBTTagCompound nbtTag = new NBTTagCompound();
		this.writeToNBT(nbtTag);
		return new SPacketUpdateTileEntity(getPos(), 1, nbtTag);
	}

	@Override
	public void onDataPacket(NetworkManager net, SPacketUpdateTileEntity packet) {
		this.readFromNBT(packet.getNbtCompound());
	}

	//used for the itemstack renderer to know what type of block this is
	public void setBlock(Block block)
	{
		this.blockType = block;
	}
}
