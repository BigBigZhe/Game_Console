package lyz.gc.network;

import io.netty.buffer.ByteBuf;
import lyz.gc.api.entity.EntityBase;
import lyz.gc.entities.ZombieFashion;
import lyz.gc.tileentity.TileEntityGameCore;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.WorldServer;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;
import net.minecraftforge.fml.relauncher.Side;

import java.util.UUID;

public class MessageSpawnEntity implements IMessage {

    public BlockPos pos;
    public int meta;

    public MessageSpawnEntity(){}

    public MessageSpawnEntity(BlockPos pos, int meta){
        this.pos = pos;
        this.meta = meta;
    }

    @Override
    public void fromBytes(ByteBuf buf) {
        pos = BlockPos.fromLong(buf.readLong());
        meta = buf.readInt();
    }

    @Override
    public void toBytes(ByteBuf buf) {
        buf.writeLong(pos.toLong());
        buf.writeInt(meta);
    }

    public static class Handle implements IMessageHandler<MessageSpawnEntity, IMessage> {
        @Override
        public IMessage onMessage(MessageSpawnEntity message, MessageContext ctx) {

            if (ctx.side == Side.SERVER) {
                final EntityPlayerMP player = ctx.getServerHandler().player;
                if (player == null) { return null; }

                final WorldServer playerWorldServer = player.getServerWorld();
                playerWorldServer.addScheduledTask(new Runnable() {
                    @Override
                    public void run() {
                        processMessage(message, playerWorldServer, player);
                    }
                });
            }
            return null;
        }

        private void processMessage(MessageSpawnEntity message, WorldServer worldServer, EntityPlayerMP playerMP) {
            EntityBase entity = null;
            int meta = message.meta;
            if (meta == 0){
                entity = new ZombieFashion(worldServer);
                entity.setPlayer(playerMP);
            }
            entity.setLocationAndAngles(message.pos.getX() + 0.5, message.pos.getY() + 1, message.pos.getZ() + 0.5, 0.0F, 0.0F);
            worldServer.spawnEntity(entity);
        }
    }
}
