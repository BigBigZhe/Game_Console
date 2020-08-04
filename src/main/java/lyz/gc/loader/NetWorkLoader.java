package lyz.gc.loader;

import lyz.gc.Chars;
import lyz.gc.network.*;
import net.minecraftforge.fml.common.network.NetworkRegistry;
import net.minecraftforge.fml.common.network.simpleimpl.SimpleNetworkWrapper;
import net.minecraftforge.fml.relauncher.Side;

public class NetWorkLoader {

    public static SimpleNetworkWrapper instance = NetworkRegistry.INSTANCE.newSimpleChannel(Chars.MODID);

    public NetWorkLoader() {
        instance.registerMessage(MessageCore.Handle.class, MessageCore.class, 1, Side.SERVER);
        instance.registerMessage(MessageTpPlayer.Handle.class, MessageTpPlayer.class, 2, Side.SERVER);
        instance.registerMessage(MessageSpawnEntity.Handle.class, MessageSpawnEntity.class, 3, Side.SERVER);
    }
}
