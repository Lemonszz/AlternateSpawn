package party.lemons.alternatespawnsystem.proxy;

import net.minecraftforge.fml.client.registry.ClientRegistry;
import party.lemons.alternatespawnsystem.block.tileentity.TileEntityFlag;
import party.lemons.alternatespawnsystem.block.tileentity.render.TileEntityFlagRenderer;

/**
 * Created by Sam on 13/08/2018.
 */
public class ClientProxy implements IProxy
{
	@Override
	public void registerSided()
	{
		ClientRegistry.bindTileEntitySpecialRenderer(TileEntityFlag.class, new TileEntityFlagRenderer());
	}
}
