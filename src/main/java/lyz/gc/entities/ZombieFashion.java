package lyz.gc.entities;

import lyz.gc.api.entity.EntityBase;
import lyz.gc.loader.ItemsLoader;
import net.minecraft.world.World;

public class ZombieFashion extends EntityBase {

    public ZombieFashion(World worldIn) {
        super(worldIn, ItemsLoader.ZF1);
        this.setNoGravity(true);
        this.setNoAI(true);
    }

}
