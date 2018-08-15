package party.lemons.alternatespawnsystem.spawning;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.storage.MapStorage;
import net.minecraft.world.storage.WorldSavedData;
import net.minecraftforge.common.util.Constants;
import net.minecraftforge.common.util.FakePlayer;
import party.lemons.alternatespawnsystem.AlternateSpawn;
import party.lemons.alternatespawnsystem.config.AlternateSpawnConfig;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Sam on 12/08/2018.
 */
public class WorldBaseData extends WorldSavedData
{
	private static final String NAME = AlternateSpawn.MODID + "_bases";

	private final List<BaseArea> areas;

	public WorldBaseData()
	{
		super(NAME);

		areas = new ArrayList<>();
	}

	public WorldBaseData(String name)
	{
		this();
	}

	/***
	 * Checks if a BlockPos is within any area
	 * @param pos Position to check
	 * @return is in area
	 */
	public boolean isInArea(BlockPos pos)
	{
		for(BaseArea area : areas)
		{
			if(area.inRadius(pos))
			{
				return true;
			}
		}
		return false;
	}

	/***
	 * Gets the total number of bases a player has
	 * @param player
	 * @return base count
	 */
	public int getPlayerBaseCount(EntityPlayer player)
	{
		int count = 0;

		for(BaseArea area : areas)
		{
			if(area.getOwner().equals(player.getUniqueID()))
				count++;
		}

		return count;
	}

	/***
	 * Create player base at position with radius
	 * @param pos
	 * @param radius
	 * @param player
	 */
	public void createBase(BlockPos pos, int radius, EntityPlayer player)
	{
		if(player instanceof FakePlayer && !AlternateSpawnConfig.ALLOW_FAKE_PLAYERS)
			return;

		BaseArea area = new BaseArea(player.getUniqueID(), radius, pos);
		areas.add(area);

		this.markDirty();
	}

	/***
	 * Removes a base at a position
	 * @param pos
	 */
	public void removeBaseAt(BlockPos pos)
	{
		areas.removeIf(b -> b.getPosition().equals(pos));

		this.markDirty();
	}

	/***
	 * Checks if a player can create a base
	 * @param player
	 * @return if player can create a base
	 */
	public boolean canCreateBase(EntityPlayer player)
	{
		int count = getPlayerBaseCount( player);
		if(count >= AlternateSpawnConfig.MAX_PLAYER_BASES)
			return false;

		return true;
	}

	/***
	 * Checks if there is an active base at a location
	 * @param pos
	 * @return true if active base at location
	 */
	public boolean hasBaseAt(BlockPos pos)
	{
		for(BaseArea area : areas)
		{
			if(area.getPosition().equals(pos))
				return true;
		}

		return false;
	}

	/***
	 * Finds first base registered to a player
	 * Used to deactivate oldest base
	 * @param playerIn
	 * @return
	 */
	public @Nullable BlockPos findFirst(EntityPlayer playerIn)
	{
		for(BaseArea area : areas)
		{
			if(area.getOwner().equals(playerIn.getUniqueID()))
			{
				return area.getPosition();
			}
		}

		return null;
	}

	public static @Nonnull WorldBaseData get(World world)
	{
		MapStorage storage = world.getPerWorldStorage();
		WorldBaseData instance = (WorldBaseData)storage.getOrLoadData(WorldBaseData.class, NAME);

		if(instance == null)
		{
			instance = new WorldBaseData();
			storage.setData(NAME, instance);
		}

		return instance;
	}

	@Override
	public void readFromNBT(NBTTagCompound nbt)
	{
		NBTTagList tagList = nbt.getTagList("areas", Constants.NBT.TAG_COMPOUND);

		//Loop through all saved bases, recreating them with saved nbt
		for(int i = 0; i < tagList.tagCount(); i++)
		{
			NBTTagCompound areaTag = tagList.getCompoundTagAt(i);

			BaseArea area = new BaseArea(areaTag);
			areas.add(area);
		}
	}

	@Override
	public NBTTagCompound writeToNBT(NBTTagCompound compound)
	{
		NBTTagList tagList = new NBTTagList();

		//Serialize all bases into nbt
		for(BaseArea area : areas)
		{
			tagList.appendTag(area.writeToNBT());
		}

		compound.setTag("areas", tagList);
		return compound;
	}
}
