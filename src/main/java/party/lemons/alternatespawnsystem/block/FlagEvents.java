package party.lemons.alternatespawnsystem.block;

import net.minecraft.item.crafting.IRecipe;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.event.world.BlockEvent;
import net.minecraftforge.event.world.WorldEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import party.lemons.alternatespawnsystem.AlternateSpawn;
import party.lemons.alternatespawnsystem.crafting.RecipeFlagDying;
import party.lemons.alternatespawnsystem.spawning.WorldBaseData;

/**
 * Created by Sam on 12/08/2018.
 */
@Mod.EventBusSubscriber(modid = AlternateSpawn.MODID)
public class FlagEvents
{
	@SubscribeEvent
	public static void worldLoad(WorldEvent.Load event)
	{
		//Forcefully load data
		WorldBaseData.get(event.getWorld());
	}

	@SubscribeEvent
	public static void onRegisterRecipie(RegistryEvent.Register<IRecipe> event)
	{
		event.getRegistry().register(new RecipeFlagDying().setRegistryName(AlternateSpawn.MODID, "dye_flag"));
	}
}
