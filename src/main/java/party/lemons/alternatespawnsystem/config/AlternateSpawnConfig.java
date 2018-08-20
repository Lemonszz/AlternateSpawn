package party.lemons.alternatespawnsystem.config;

import net.minecraftforge.common.config.Config;
import net.minecraftforge.common.config.ConfigManager;
import net.minecraftforge.fml.client.event.ConfigChangedEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import party.lemons.alternatespawnsystem.AlternateSpawn;
import party.lemons.alternatespawnsystem.spawning.FlagType;

/**
 * Created by Sam on 12/08/2018.
 */
@Mod.EventBusSubscriber(modid = AlternateSpawn.MODID)
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

	@Config.Name("Max Flag Stack size")
	@Config.RangeInt(min = 1, max = 64)
	@Config.RequiresMcRestart
	public static int FLAG_STACK_SIZER = 16;

	@Config.Name("Allow Fake Players")
	public static boolean ALLOW_FAKE_PLAYERS = false;

	@Config.Name("Allow Sunlight Spawning")
	public static boolean ALLOW_SUNLIGHT_SPAWNS = false;

	@SubscribeEvent
	public static void onConfigReload(ConfigChangedEvent.OnConfigChangedEvent event)
	{
		if(event.getModID().equals(AlternateSpawn.MODID))
		{
			ConfigManager.sync(AlternateSpawn.MODID, Config.Type.INSTANCE);

			//TODO: automate this or something
			FlagType.BASIC.setFlagSize(BASIC_FLAG_RANGE);
			FlagType.GOLD.setFlagSize(GOLDEN_FLAG_RANGE);
			FlagType.DIAMOND.setFlagSize(DIAMOND_FLAG_RANGE);
		}
	}

}
