package lyz.gc.loader;

import java.util.ArrayList;
import java.util.List;

import lyz.gc.blocks.*;
import lyz.gc.blocks.functionblock.AlternativeBlock;
import lyz.gc.blocks.functionblock.AttackBlock;
import net.minecraft.block.Block;

public class BlocksLoader {
	public static final List<Block> BLOCKS = new ArrayList<>();

	public static final AlternativeBlock ALTERNATIVE_BLOCK = new AlternativeBlock("alternative_block");
	public static final AttackBlock ATTACK_BLOCK = new AttackBlock("attack_block");

	public static final GameCoreBlock GAME_CORE_BLOCK = new GameCoreBlock("game_core_block");
	public static final NormalBlock NORMAL_BLOCK = new NormalBlock("normal_block");
	public static final ShopBlock SHOP_BLOCK = new ShopBlock("shop_block");
}
