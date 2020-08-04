package lyz.gc.loader;

import lyz.gc.Chars;
import lyz.gc.tileentity.TileEntityAttackBlock;
import lyz.gc.tileentity.TileEntityGameCore;
import net.minecraft.block.Block;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.registry.GameRegistry;

@Mod.EventBusSubscriber
public class TileEntitiesLoader {

    @SubscribeEvent
    public static void onBlockRegister(RegistryEvent.Register<Block> event) {
        GameRegistry.registerTileEntity(TileEntityGameCore.class, new ResourceLocation(Chars.MODID, "tile_entity_game_core"));
        GameRegistry.registerTileEntity(TileEntityAttackBlock.class, new ResourceLocation(Chars.MODID, "tile_entity_attack_block"));
    }
}
