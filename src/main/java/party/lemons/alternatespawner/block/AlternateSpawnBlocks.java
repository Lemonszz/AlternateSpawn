package party.lemons.alternatespawner.block;

import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.registry.GameRegistry;
import party.lemons.alternatespawner.AlternateSpawn;
import party.lemons.alternatespawner.block.tileentity.TileEntityFlag;
import party.lemons.alternatespawner.spawning.FlagType;

/**
 * Created by Sam on 12/08/2018.
 */
@Mod.EventBusSubscriber(modid = AlternateSpawn.MODID)
@GameRegistry.ObjectHolder("alternatespawn")
public class AlternateSpawnBlocks
{
	@GameRegistry.ObjectHolder("basic_flag")
	public static Block BASIC_FLAG = Blocks.AIR;

	@GameRegistry.ObjectHolder("golden_flag")
	public static Block GOLDEN_FLAG = Blocks.AIR;

	@GameRegistry.ObjectHolder("diamond_flag")
	public static Block DIAMOND_FLAG = Blocks.AIR;

	@SubscribeEvent
	public static void onBlockRegister(RegistryEvent.Register<Block> event)
	{
		event.getRegistry().registerAll(
			createFlag(FlagType.BASIC, "basic_flag"),
			createFlag(FlagType.GOLD, "golden_flag"),
			createFlag(FlagType.DIAMOND, "diamond_flag")
		);

		GameRegistry.registerTileEntity(TileEntityFlag.class, AlternateSpawn.MODID + "_flag");
	}

	@SubscribeEvent
	public static void onItemRegister(RegistryEvent.Register<Item> event)
	{
		event.getRegistry().registerAll(
				new ItemBlock(BASIC_FLAG).setRegistryName(AlternateSpawn.MODID, "basic_flag"),
				new ItemBlock(GOLDEN_FLAG).setRegistryName(AlternateSpawn.MODID, "gold_flag"),
				new ItemBlock(DIAMOND_FLAG).setRegistryName(AlternateSpawn.MODID, "diamond_flag")
		);
	}

	public static Block createFlag(FlagType type, String name)
	{
		return new BlockFlag(type).setTranslationKey(AlternateSpawn.MODID + "." + name).setRegistryName(AlternateSpawn.MODID, name);
	}
}
