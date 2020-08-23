package lyz.gc.entities.beating;

import lyz.gc.api.chess.ChessBase;
import lyz.gc.api.chess.Weapon;
import lyz.gc.api.entity.EntityBeating;
import net.minecraft.world.World;

public class Cow_Gold extends EntityBeating {
    public Cow_Gold(World worldIn) {
        super(worldIn, new int[]{0, 0}, 0, 0, ChessBase.Base, new Weapon[]{Weapon.NONE, Weapon.NONE, Weapon.NONE});
    }
}
