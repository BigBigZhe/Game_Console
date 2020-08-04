package lyz.gc.network;

import io.netty.buffer.ByteBuf;
import lyz.gc.tileentity.TileEntityGameCore;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.WorldServer;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;
import net.minecraftforge.fml.relauncher.Side;

import java.util.UUID;

public class MessageTpPlayer implements IMessage {

    public BlockPos pos;
    public UUID name;

    public MessageTpPlayer(){}

    public MessageTpPlayer(BlockPos pos, UUID name){
        this.pos = pos;
        this.name = name;
    }

    @Override
    public void fromBytes(ByteBuf buf) {
        pos = BlockPos.fromLong(buf.readLong());
        final long most = buf.readLong();
        final long least = buf.readLong();
        name = new UUID(most, least);
    }

    @Override
    public void toBytes(ByteBuf buf) {
        buf.writeLong(pos.toLong());
        buf.writeLong(name.getMostSignificantBits());
        buf.writeLong(name.getLeastSignificantBits());
    }

    public static class Handle implements IMessageHandler<MessageTpPlayer, IMessage> {
        @Override
        public IMessage onMessage(MessageTpPlayer message, MessageContext ctx) {

            if (ctx.side == Side.SERVER) {
                final EntityPlayerMP player = ctx.getServerHandler().player;
                if (player == null) { return null; }

                final WorldServer playerWorldServer = player.getServerWorld();
                playerWorldServer.addScheduledTask(new Runnable() {
                    @Override
                    public void run() {
                        processMessage(message, playerWorldServer);
                    }
                });
            }
            return null;
        }

        private void processMessage(MessageTpPlayer message, WorldServer playerWorldServer) {
            BlockPos pos = message.pos;
            Entity entity = playerWorldServer.getEntityFromUuid(message.name);
            entity.setPositionAndRotation(pos.getX() + 14, pos.getY() - 6, pos.getZ() + 14, 0, 0);
            /*EntityPlayer player = playerWorldServer.getPlayerEntityByUUID(message.name);
            player.setPositionAndRotation(pos.getX() + 14, pos.getY() - 6, pos.getZ() + 14, 0, 0);*/
        }
    }
}
