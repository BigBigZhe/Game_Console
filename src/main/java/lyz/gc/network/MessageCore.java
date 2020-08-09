package lyz.gc.network;

import io.netty.buffer.ByteBuf;
import lyz.gc.tileentity.TileEntityGameCore;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.WorldServer;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;
import net.minecraftforge.fml.relauncher.Side;

import java.util.UUID;

public class MessageCore implements IMessage {

    public BlockPos pos;
    public int meta;
    public UUID name;

    public MessageCore(){}

    public MessageCore(BlockPos pos, int meta, UUID name){
        this.pos = pos;
        this.meta = meta;
        this.name = name;
    }

    @Override
    public void fromBytes(ByteBuf buf) {
        pos = BlockPos.fromLong(buf.readLong());
        meta = buf.readInt();
        final long most = buf.readLong();
        final long least = buf.readLong();
        name = new UUID(most, least);
    }

    @Override
    public void toBytes(ByteBuf buf) {
        buf.writeLong(pos.toLong());
        buf.writeInt(meta);
        buf.writeLong(name.getMostSignificantBits());
        buf.writeLong(name.getLeastSignificantBits());
    }

    public static class Handle implements IMessageHandler<MessageCore, IMessage> {
        @Override
        public IMessage onMessage(MessageCore message, MessageContext ctx) {

            if (ctx.side == Side.SERVER) {
                final EntityPlayerMP player = ctx.getServerHandler().player;
                if (player == null) { return null; }

                final WorldServer playerWorldServer = player.getServerWorld();
                playerWorldServer.addScheduledTask(new Runnable() {
                    @Override
                    public void run() {
                        processMessage(message, player);
                    }
                });
            }
            return null;
        }

        private void processMessage(MessageCore message, EntityPlayerMP player) {
            TileEntity tileEntity = player.world.getTileEntity(message.pos);
            TileEntityGameCore tile = (TileEntityGameCore) tileEntity;
            tile.addName(message.name);
            tile.setPlayerNum(message.meta);
        }
    }
}
