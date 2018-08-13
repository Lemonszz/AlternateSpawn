package party.lemons.alternatespawnsystem.block;

import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.oredict.OreDictionary;
import party.lemons.alternatespawnsystem.AlternateSpawn;
import party.lemons.alternatespawnsystem.block.tileentity.TileEntityFlag;
import party.lemons.alternatespawnsystem.spawning.FlagType;

/**
 * Created by Sam on 12/08/2018.
 */
@Mod.EventBusSubscriber(modid = AlternateSpawn.MODID)
@GameRegistry.ObjectHolder("alternatespawnsystem")
public class AlternateSpawnBlocks
{
	public static final Block BASIC_FLAG = Blocks.AIR;
	public static final Block GOLDEN_FLAG = Blocks.AIR;
	public static final Block DIAMOND_FLAG = Blocks.AIR;
	public static final Block BASIC_FLAG_WALL = Blocks.AIR;
	public static final Block GOLDEN_FLAG_WALL = Blocks.AIR;
	public static final Block DIAMOND_FLAG_WALL = Blocks.AIR;

	@SubscribeEvent
	public static void onBlockRegister(RegistryEvent.Register<Block> event)
	{
		event.getRegistry().registerAll(
			createFlag(FlagType.BASIC, "basic_flag", false),
			createFlag(FlagType.GOLD, "golden_flag", false),
			createFlag(FlagType.DIAMOND, "diamond_flag", false),
			createFlag(FlagType.BASIC, "basic_flag", true),
			createFlag(FlagType.GOLD, "golden_flag", true),
			createFlag(FlagType.DIAMOND, "diamond_flag", true)
		);

		GameRegistry.registerTileEntity(TileEntityFlag.class, AlternateSpawn.MODID + "_flag");
	}

	@SubscribeEvent
	public static void onItemRegister(RegistryEvent.Register<Item> event)
	{
		event.getRegistry().registerAll(
				new ItemBlockFlag(BASIC_FLAG).setRegistryName(AlternateSpawn.MODID, "basic_flag"),
				new ItemBlockFlag(GOLDEN_FLAG).setRegistryName(AlternateSpawn.MODID, "gold_flag"),
				new ItemBlockFlag(DIAMOND_FLAG).setRegistryName(AlternateSpawn.MODID, "diamond_flag")
		);

		for(int i = 0; i < 16; i++)
			OreDictionary.registerOre("blockWool", new ItemStack(Blocks.WOOL, 1, i));
	}

	public static Block createFlag(FlagType type, String name, boolean wall)
	{
		if(wall)
			return new BlockFlagWall(type).setTranslationKey(AlternateSpawn.MODID + "." + name).setRegistryName(AlternateSpawn.MODID, name + "_wall");

		return new BlockFlag(type).setTranslationKey(AlternateSpawn.MODID + "." + name).setRegistryName(AlternateSpawn.MODID, name);
	}
}
