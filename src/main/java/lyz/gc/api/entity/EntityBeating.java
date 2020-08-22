package lyz.gc.api.entity;

import net.minecraft.entity.EntityLiving;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.world.World;

public class EntityBeating extends EntityLiving {

    public static final DataParameter<Boolean> AFIRE = EntityDataManager.<Boolean>createKey(EntityBeating.class, DataSerializers.BOOLEAN);

    public EntityBeating(World worldIn) {
        super(worldIn);
    }

    @Override
    protected void entityInit(){
        super.entityInit();
        this.dataManager.register(AFIRE, Boolean.FALSE);
    }
}
