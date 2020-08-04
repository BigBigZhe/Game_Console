package lyz.gc.loader;

import java.util.ArrayList;
import java.util.List;

import lyz.gc.items.*;
import net.minecraft.item.Item;

public class ItemsLoader {
	public static List<Item> ITEMS = new ArrayList<>();

	public static final GameConsole GAME_CONSOLE = new GameConsole("gameconsole");
	public static final Staff STAFF = new Staff("staff");
	public static final DebugStaff DEBUG_STAFF = new DebugStaff("debug_staff");
	public static final EntityItem ZF1 = new EntityItem("zf1");
	public static final EntityItem ZF2 = new EntityItem("zf2");
	public static final EntityItem ZF3 = new EntityItem("zf3");
}
