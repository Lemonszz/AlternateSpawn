package party.lemons.alternatespawnsystem.block.tileentity.render;

import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import party.lemons.alternatespawnsystem.AlternateSpawn;
import party.lemons.alternatespawnsystem.block.AlternateSpawnBlocks;
import party.lemons.alternatespawnsystem.block.BlockFlag;
import party.lemons.alternatespawnsystem.block.tileentity.TileEntityFlag;

import javax.annotation.Nullable;

/**
 * Created by Sam on 13/08/2018.
 */
public class TileEntityFlagRenderer extends TileEntitySpecialRenderer<TileEntityFlag>
{
	private final ModelFlag flagModel = new ModelFlag();

	public void render(TileEntityFlag te, double x, double y, double z, float partialTicks, int destroyStage, float alpha)
	{
		if(!(te.getBlockType() instanceof BlockFlag))
			return;

		BlockFlag flag = (BlockFlag) te.getBlockType();

		boolean worldExists = te.getWorld() != null;
		boolean isStanding =  !flag.isWall();
		int meta = worldExists ? te.getBlockMetadata() : 0;
		long ticks = worldExists ? te.getWorld().getTotalWorldTime() : 0L;
		GlStateManager.pushMatrix();

		if (isStanding)
		{
			GlStateManager.translate((float)x + 0.5F, (float)y + 0.5F, (float)z + 0.5F);
			float f1 = (float)(meta * 360) / 16.0F;
			GlStateManager.rotate(-f1, 0.0F, 1.0F, 0.0F);
		}
		else
		{

			GlStateManager.translate(x, y, z);
			switch(meta)
			{
				case 2:
					GlStateManager.translate(0.5F, 0.5F, 0.5F);
					GlStateManager.rotate(90, 1, 0, 0);
					GlStateManager.rotate(180, 0, 1, 0);
					GlStateManager.rotate(180, 0, 0, 1);
					break;
				case 3:
					GlStateManager.translate(0.5F, 0.5F, 0.5F);
					GlStateManager.rotate(90, 1, 0, 0);
					GlStateManager.rotate(180, 0, 1, 0);
					break;

				case 4:
					GlStateManager.translate(0.5F, 0.5F, 0.5F);
					GlStateManager.rotate(90, 1, 0, 0);
					GlStateManager.rotate(180, 0, 1, 0);
					GlStateManager.rotate(270, 0, 0, 1);
					break;

				case 5:
					GlStateManager.translate(0.5F, 0.5F, 0.5F);
					GlStateManager.rotate(90, 1, 0, 0);
					GlStateManager.rotate(180, 0, 1, 0);
					GlStateManager.rotate(90, 0, 0, 1);
					break;
			}
		}

		BlockPos blockpos = te.getPos();
		float f3 = (float)(blockpos.getX() * 7 + blockpos.getY() * 9 + blockpos.getZ() * 13) + (float)ticks + partialTicks;
		this.flagModel.flagFlag.rotateAngleY = (-0.0125F + 0.01F * MathHelper.cos(f3 * (float)Math.PI * 0.04F)) * (float)Math.PI;
		GlStateManager.enableRescaleNormal();
		ResourceLocation resourcelocation = this.getBannerResourceLocation(te);

		if (resourcelocation != null)
		{
			this.bindTexture(resourcelocation);
			GlStateManager.pushMatrix();
			GlStateManager.translate(0, 1, 0);
			GlStateManager.rotate(180, 1, 0, 0);

			this.flagModel.render();

			GlStateManager.color(te.getRed(), te.getGreen(), te.getBlue(), 1F);
			this.flagModel.renderFlag();
			GlStateManager.popMatrix();
		}

		GlStateManager.color(1.0F, 1.0F, 1.0F, alpha);
		GlStateManager.popMatrix();
	}

	ResourceLocation baseResources = new ResourceLocation(AlternateSpawn.MODID, "textures/flag/flag_base.png");
	ResourceLocation baseGoldResources = new ResourceLocation(AlternateSpawn.MODID, "textures/flag/flag_gold_base.png");
	ResourceLocation baseDiamondResources = new ResourceLocation(AlternateSpawn.MODID, "textures/flag/flag_diamond_base.png");

	@Nullable
	private ResourceLocation getBannerResourceLocation(TileEntityFlag bannerObj)
	{
		if(bannerObj.getBlockType() == AlternateSpawnBlocks.GOLDEN_FLAG || (bannerObj.getBlockType() == AlternateSpawnBlocks.GOLDEN_FLAG_WALL))
			return baseGoldResources;

		if(bannerObj.getBlockType() == AlternateSpawnBlocks.DIAMOND_FLAG || (bannerObj.getBlockType() == AlternateSpawnBlocks.DIAMOND_FLAG_WALL))
		return baseDiamondResources;

		return baseResources;
	}
}