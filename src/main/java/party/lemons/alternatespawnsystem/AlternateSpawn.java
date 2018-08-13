package party.lemons.alternatespawnsystem;

import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import party.lemons.alternatespawnsystem.proxy.IProxy;

/**
 * Created by Sam on 12/08/2018.
 */
@Mod(modid = AlternateSpawn.MODID, version = AlternateSpawn.VERSION, name = AlternateSpawn.NAME, updateJSON = "https://raw.githubusercontent.com/Lemonszz/AlternateSpawn/master/update.json")
public class AlternateSpawn
{
	public static final String MODID = "alternatespawnsystem";
	public static final String VERSION = "0.0.3";
	public static final String NAME = "A.S.S.";

	@SidedProxy(clientSide = "party.lemons.alternatespawnsystem.proxy.ClientProxy", serverSide = "party.lemons.alternatespawnsystem.proxy.ServerProxy")
	public static IProxy proxy;

	@Mod.EventHandler
	public void onPreInit(FMLPreInitializationEvent event)
	{
		proxy.registerSided();
	}
}

