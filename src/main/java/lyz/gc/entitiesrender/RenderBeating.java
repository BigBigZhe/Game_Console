package lyz.gc.entitiesrender;

import lyz.gc.Chars;
import lyz.gc.api.entity.EntityBeating;
import lyz.gc.api.entity.EntityModelBase;
import lyz.gc.entities.beating.Cow_Gold;
import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.util.ResourceLocation;

import javax.annotation.Nullable;

public class RenderBeating extends RenderLiving<EntityBeating> {
    public RenderBeating(RenderManager renderManager) {
        super(renderManager, new EntityModelBase(), 0.5F);
    }

    @Override
    public void doRender(EntityBeating entity, double x, double y, double z, float entityYaw, float partialTicks) {
        super.doRender(entity, x, y, z, entityYaw, partialTicks);
    }

    @Nullable
    @Override
    protected ResourceLocation getEntityTexture(EntityBeating entity) {
        return new ResourceLocation( Chars.MODID + ":" + "textures/entity/golden_chicken.png");
    }
}
