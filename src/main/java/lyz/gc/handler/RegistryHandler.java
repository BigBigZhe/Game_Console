package lyz.gc.handler;

import lyz.gc.Chars;
import lyz.gc.entities.ZombieFashion;
import lyz.gc.entitiesrender.RenderZombieFashion;
import lyz.gc.loader.BlocksLoader;
import lyz.gc.loader.ItemsLoader;
import net.minecraft.block.Block;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.entity.Entity;
import net.minecraft.item.Item;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.event.ModelRegistryEvent;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.client.registry.RenderingRegistry;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.registry.EntityEntry;
import net.minecraftforge.fml.common.registry.EntityEntryBuilder;

@EventBusSubscriber
public class RegistryHandler {

	public static int entityID = 200;

	@SubscribeEvent
	public static void onBlockRegister(RegistryEvent.Register<Block> event) {
		event.getRegistry().registerAll(BlocksLoader.BLOCKS.toArray(new Block[0]));
	}
	
	@SubscribeEvent
	public static void onItemRegister(RegistryEvent.Register<Item> event) {
		event.getRegistry().registerAll(ItemsLoader.ITEMS.toArray(new Item[0]));
	}

	@SubscribeEvent
	public static void onEntityRegister(RegistryEvent.Register<EntityEntry> event){
		event.getRegistry().register(EntitiesBuild(ZombieFashion.class, "zombie_fashion"));
	}

	@SubscribeEvent
    public static void onModelRegister(ModelRegistryEvent event) {
		for(Item item:ItemsLoader.ITEMS.toArray(new Item[0])) {
        	ModelLoader.setCustomModelResourceLocation(item, 0, new ModelResourceLocation(item.getRegistryName(), "inventory"));
		}
		RenderingRegistry.registerEntityRenderingHandler(ZombieFashion.class, RenderZombieFashion::new);
	}

	private static EntityEntry EntitiesBuild(Class<? extends Entity> entityClass, String entityName){
		return EntityEntryBuilder.create().entity(entityClass)
				.id(new ResourceLocation(Chars.MODID, entityName), entityID++).name(entityName)
				.tracker(80, 3, false).build();
	}

}
