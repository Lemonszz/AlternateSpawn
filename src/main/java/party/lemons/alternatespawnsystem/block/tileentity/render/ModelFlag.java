package party.lemons.alternatespawnsystem.block.tileentity.render;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;

/**
 * ModelFlag - Lemons
 * Created using Tabula 7.0.0
 */
public class ModelFlag extends ModelBase
{
	public ModelRenderer flagBase;
	public ModelRenderer flagPole;
	public ModelRenderer flagFlag;

	public ModelFlag() {
		this.textureWidth = 64;
		this.textureHeight = 32;
		this.flagPole = new ModelRenderer(this, 0, 0);
		this.flagPole.setRotationPoint(0.0F, 12.0F, 0.0F);
		this.flagPole.addBox(-0.5F, 0.0F, -0.5F, 1, 10, 1, 0.0F);
		this.flagBase = new ModelRenderer(this, 0, 14);
		this.flagBase.setRotationPoint(0.0F, 22.0F, 0.0F);
		this.flagBase.addBox(-1.0F, 0.0F, -1.0F, 2, 2, 2, 0.0F);
		this.flagFlag = new ModelRenderer(this, 17, 1);
		this.flagFlag.setRotationPoint(0.0F, 12.2F, 0.0F);
		this.flagFlag.addBox(0.0F, 0.0F, 0.5F, 0, 5, 8, 0.0F);
	}

	@Override
	public void render(Entity entity, float f, float f1, float f2, float f3, float f4, float f5) {
		this.flagBase.render(f5);
	}

	public void setRotateAngle(ModelRenderer modelRenderer, float x, float y, float z) {
		modelRenderer.rotateAngleX = x;
		modelRenderer.rotateAngleY = y;
		modelRenderer.rotateAngleZ = z;
	}

	public void render()
	{
		this.flagBase.render(0.0625F);
		this.flagPole.render(0.0625F);
	}

	public void renderFlag()
	{
		this.flagFlag.render(0.0625F);
	}
}
