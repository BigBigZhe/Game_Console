package lyz.gc.proxy;

import lyz.gc.loader.NetWorkLoader;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

@EventBusSubscriber
public class MyCommonProxy {
	
	public void preInit(FMLPreInitializationEvent event)
    {
          //new GuiElementLoader();
          new NetWorkLoader();
    }

    public void init(FMLInitializationEvent event)
    {

    }

    public void postInit(FMLPostInitializationEvent event)
    {

    }
}
