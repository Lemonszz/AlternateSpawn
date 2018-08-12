package party.lemons.alternatespawner.config;

import net.minecraftforge.common.config.Config;
import party.lemons.alternatespawner.AlternateSpawn;

/**
 * Created by Sam on 12/08/2018.
 */
@Config(modid = AlternateSpawn.MODID)
public class AlternateSpawnConfig
{
	@Config.Comment("Should new players see a message regarding the new mechanics?")
	@Config.Name("Send New Players Information")
	public static boolean DO_LOGIN_MESSAGE = true;

	@Config.Comment("Player wont see the login message after they've player for this many minutes")
	@Config.Name("New Player Max Minutes")
	@Config.RangeInt(min = 0)
	public static int LOGIN_MESSAGE_MAX_TIME = 60;

	@Config.Name("Basic Flag Range")
	@Config.RangeInt(min = 1)
	public static int BASIC_FLAG_RANGE = 30;

	@Config.Name("Golden Flag Range")
	@Config.RangeInt(min = 1)
	public static int GOLDEN_FLAG_RANGE = 60;

	@Config.Name("Diamond Flag Range")
	@Config.RangeInt(min = 1)
	public static int DIAMOND_FLAG_RANGE = 100;

	@Config.Name("Per Player Max Bases")
	@Config.RangeInt(min = 0)
	public static int MAX_PLAYER_BASES = 4;
}
