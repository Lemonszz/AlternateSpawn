package party.lemons.alternatespawnsystem.spawning;

import party.lemons.alternatespawnsystem.config.AlternateSpawnConfig;

/**
 * Created by Sam on 12/08/2018.
 */
public enum FlagType
{
	BASIC,
	GOLD,
	DIAMOND;

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
}
