package party.lemons.alternatespawnsystem.spawning;

import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import party.lemons.alternatespawnsystem.block.AlternateSpawnBlocks;
import party.lemons.alternatespawnsystem.config.AlternateSpawnConfig;

import javax.annotation.Nonnull;

/**
 * Created by Sam on 12/08/2018.
 */
public enum FlagType
{
	BASIC("basic"),
	GOLD("gold"),
	DIAMOND("diamond");

	private String name;

	FlagType(String name)
	{
		this.name = name;
	}

	public String getName()
	{
		return name;
	}

	public int getFlagSize()
	{
		switch(this)
		{
			case GOLD:
				return AlternateSpawnConfig.GOLDEN_FLAG_RANGE;
			case DIAMOND:
				return AlternateSpawnConfig.DIAMOND_FLAG_RANGE;
			default:
				return AlternateSpawnConfig.BASIC_FLAG_RANGE;
		}
	}

	public @Nonnull Block getWallBlock()
	{
		switch(this)
		{
			case BASIC:
				return AlternateSpawnBlocks.BASIC_FLAG_WALL;
			case GOLD:
				return AlternateSpawnBlocks.GOLDEN_FLAG_WALL;
			case DIAMOND:
				return AlternateSpawnBlocks.DIAMOND_FLAG_WALL;
		}

		return Blocks.AIR;
	}

	public @Nonnull Block getGroundBlock()
	{
		switch(this)
		{
			case BASIC:
				return AlternateSpawnBlocks.BASIC_FLAG;
			case GOLD:
				return AlternateSpawnBlocks.GOLDEN_FLAG;
			case DIAMOND:
				return AlternateSpawnBlocks.DIAMOND_FLAG;
		}

		return Blocks.AIR;
	}
}
