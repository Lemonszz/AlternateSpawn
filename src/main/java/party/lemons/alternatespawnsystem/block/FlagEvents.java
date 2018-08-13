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
	//Apparently there's no method that has the player that placed a block, so I'm doing it here
	//TODO: find out if im dumb and there's actually something that's called
	@SubscribeEvent
	public static void onBlockPlace(BlockEvent.PlaceEvent event)
	{
		if(!event.getWorld().isRemote && event.getPlacedBlock().getBlock() instanceof BlockFlag)
		{
			BlockFlag flag = (BlockFlag) event.getPlacedBlock().getBlock();
			WorldBaseData data = WorldBaseData.get(event.getWorld());

			//Attempt to create a base
			if(data.canCreateBase(event.getPlayer()))
			{
				data.createBase(event.getPos(), flag.getType().getFlagSize(), event.getPlayer());
				event.getPlayer().sendStatusMessage(new TextComponentTranslation("alternatespawnsystem.message.create.success"), true);
			}
			else
			{
				event.getPlayer().sendStatusMessage(new TextComponentTranslation("alternatespawnsystem.message.create.fail.amount"), true);
			}
		}
	}

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
