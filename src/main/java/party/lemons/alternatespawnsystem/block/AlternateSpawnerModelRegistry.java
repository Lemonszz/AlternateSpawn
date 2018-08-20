package party.lemons.alternatespawnsystem.block;

import net.minecraft.block.Block;
import net.minecraft.block.properties.IProperty;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.client.renderer.block.statemap.StateMap;
import net.minecraft.item.Item;
import net.minecraftforge.client.event.ModelRegistryEvent;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import party.lemons.alternatespawnsystem.AlternateSpawn;
import party.lemons.alternatespawnsystem.block.impl.BlockFlag;
import party.lemons.alternatespawnsystem.block.impl.BlockFlagWall;
import party.lemons.alternatespawnsystem.block.tileentity.render.TileEntityFlagStackRenderer;

/**
 * Created by Sam on 13/08/2018.
 */
@Mod.EventBusSubscriber(modid = AlternateSpawn.MODID, value = Side.CLIENT)
public class AlternateSpawnerModelRegistry
{
	@SubscribeEvent
	public static void onRegisterModel(ModelRegistryEvent event)
	{
		registerFlagModel(AlternateSpawnBlocks.BASIC_FLAG);
		registerFlagModel(AlternateSpawnBlocks.GOLDEN_FLAG);
		registerFlagModel(AlternateSpawnBlocks.DIAMOND_FLAG);

		setInvisibleModel(AlternateSpawnBlocks.DIAMOND_FLAG_WALL, BlockFlagWall.FACING);
		setInvisibleModel(AlternateSpawnBlocks.GOLDEN_FLAG_WALL, BlockFlagWall.FACING);
		setInvisibleModel(AlternateSpawnBlocks.BASIC_FLAG_WALL, BlockFlagWall.FACING);
	}

	@SideOnly(Side.CLIENT) 	//TODO: Apparently was crashing for some people without annotation.
	public static void registerFlagModel(Block block)
	{
		ModelLoader.setCustomModelResourceLocation(Item.getItemFromBlock(block),
				0, new ModelResourceLocation(block.getRegistryName(), "inventory"));

		setInvisibleModel(block, BlockFlag.ROTATION);
		Item.getItemFromBlock(block).setTileEntityItemStackRenderer(new TileEntityFlagStackRenderer());
	}

	public static void setInvisibleModel(Block block, IProperty<?>... ignores)
	{
		ModelLoader.setCustomStateMapper(block, new StateMap.Builder().ignore(ignores).build());
	}

}
