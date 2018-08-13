package party.lemons.alternatespawnsystem.block.tileentity.render;

import net.minecraft.client.renderer.tileentity.TileEntityItemStackRenderer;
import net.minecraft.client.renderer.tileentity.TileEntityRendererDispatcher;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import party.lemons.alternatespawnsystem.block.ItemBlockFlag;
import party.lemons.alternatespawnsystem.block.tileentity.TileEntityFlag;

/**
 * Created by Sam on 13/08/2018.
 */
public class TileEntityFlagStackRenderer extends TileEntityItemStackRenderer
{
	private TileEntityFlag flag = new TileEntityFlag();

	public void renderByItem(ItemStack stack)
	{
		int hex = ((ItemBlockFlag)stack.getItem()).getColor(stack);

		int r = (hex & 0xFF0000) >> 16;
		int g = (hex & 0xFF00) >> 8;
		int b = (hex & 0xFF);

		flag.setRgb(r, g, b);
		ItemBlock ib = (ItemBlock) stack.getItem();
		flag.setBlock(ib.getBlock());

		TileEntityRendererDispatcher.instance.render(flag, 0.0D, 0.0D, 0.0D, 0.0F, 1F);
	}
}
