package lyz.gc.tileentity;

import lyz.gc.api.entity.EntityBase;
import lyz.gc.entities.ZombieFashion;
import lyz.gc.loader.ItemsLoader;
import lyz.gc.loader.NetWorkLoader;
import lyz.gc.network.MessageTpPlayer;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.play.server.SPacketUpdateTileEntity;
import net.minecraft.server.management.PlayerChunkMapEntry;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ITickable;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.WorldServer;

import javax.annotation.Nullable;
import java.util.Objects;
import java.util.UUID;

public class TileEntityGameCore extends TileEntity implements ITickable {

    private boolean isBegin = false;//是否开始游戏
    private int playerNum = 0;//玩家人数
    private UUID[] names = new UUID[8];//UUID
    private int[] healthy = new int[8];//玩家血量
    private int[][][] map = new int[8][10][9];//每块地图的数据
    private int[] chosenLocation = new int[10];//选秀棋子位置
    private int tick = 0, roundTime = 0, round = 0, liveNum = 0;//20tick = 1roundTime
    private boolean isBeating = false, isReadying = false, isChoosing = false;//游戏状态
    private EntityBase[] chooseEntities = new EntityBase[10];

    public TileEntityGameCore(){ }

    public void setUUID(UUID uuid){
        for (int i = 0; i < 8; i++){
            if (names[i] == null){
                names[i] = uuid;
            }
        }
    }

    public int getPlayerNumber(EntityPlayer player){
        UUID uuid = player.getUniqueID();
        for (int i = 0; i < 8; i++){
            if (names[i] == uuid){
                return i;
            }
        }
        return -1;
    }

    public boolean isBegin() {
        return isBegin;
    }

    public void setPlayerNum(int num){
        playerNum = num;
    }

    public int getPlayerNum(){
        return this.playerNum;
    }

    public void begin() {
        isBegin = true;
        /*EntityPlayer entityPlayer = world.getPlayerEntityByUUID(names[0]);
        BlockPos pos = this.pos;
        MessageTpPlayer message = new MessageTpPlayer(pos, names[0]);
        NetWorkLoader.instance.sendToServer(message);*/
    }

    public boolean addPlayer(EntityPlayer playerIn){
        UUID n = playerIn.getUniqueID();
        for (int i = 0; i < 8; i++){
            if (names[i] == null){
                names[i] = n;
                playerNum++;
                System.out.println("In");
                return true;
            }
            if (Objects.equals(names[i], n)){
                System.out.println("Already");
                return false;
            }
        }
        System.out.println("Full");
        return false;
    }

    public void addName(UUID uuid){
        for (int i = 0; i < 8; i++){
            if (names[i] == null){
                names[i] = uuid;
                playerNum++;
                System.out.println("++");
            }
        }
    }

    @Override
    public void readFromNBT(NBTTagCompound compound){
        super.readFromNBT(compound);
        NBTTagList list = compound.getTagList("UUIDs", 10);
        for (int i = 0; i < 8; i++){
            names[i] = null;
        }
        for (int i = 0; i < list.tagCount(); i++){
            NBTTagCompound nbtTagCompound = list.getCompoundTagAt(i);
            long most = nbtTagCompound.getLong("UUID Most");
            long least = nbtTagCompound.getLong("UUID Least");
            names[i] = new UUID(most, least);
        }
        playerNum = compound.getInteger("playerNum");
    }

    @Override
    public NBTTagCompound writeToNBT(NBTTagCompound compound){
        super.writeToNBT(compound);
        NBTTagList list = new NBTTagList();
        for (int i = 0; i < 8; i++){
            if (names[i] == null){
                break;
            }
            NBTTagCompound compound1 = new NBTTagCompound();
            compound1.setLong("UUID Most", names[i].getMostSignificantBits());
            compound1.setLong("UUID Least", names[i].getLeastSignificantBits());
            list.appendTag(compound1);
        }
        compound.setTag("UUIDs" ,list);
        compound.setInteger("playerNum", playerNum);
        return compound;
    }

    public void syncToTrackingClients() {
        if (!this.world.isRemote) {
            SPacketUpdateTileEntity packet = this.getUpdatePacket();
            PlayerChunkMapEntry trackingEntry = ((WorldServer)this.world).getPlayerChunkMap().getEntry(this.pos.getX() >> 4, this.pos.getZ() >> 4);
            if (trackingEntry != null) {
                for (EntityPlayerMP player : trackingEntry.getWatchingPlayers()) {
                    player.connection.sendPacket(packet);
                }
            }
        }
    }

    @Override
    @Nullable
    public SPacketUpdateTileEntity getUpdatePacket() {
        NBTTagCompound updateTagDescribingTileEntityState = getUpdateTag();
        final int METADATA = 0;
        return new SPacketUpdateTileEntity(this.pos, METADATA, updateTagDescribingTileEntityState);
    }

    @Override
    public void onDataPacket(NetworkManager net, SPacketUpdateTileEntity pkt) {
        NBTTagCompound updateTagDescribingTileEntityState = pkt.getNbtCompound();
        handleUpdateTag(updateTagDescribingTileEntityState);
    }

    @Override
    public NBTTagCompound getUpdateTag() {
        NBTTagCompound nbtTagCompound = new NBTTagCompound();
        writeToNBT(nbtTagCompound);
        return nbtTagCompound;
    }

    @Override
    public void handleUpdateTag(NBTTagCompound tag)
    {
        this.readFromNBT(tag);
    }

    @Override
    public void update() {
        tick++;
        if (tick == 20){
            roundTime++;
            tick = 0;
        }
        if (playerNum == 1){
            win();
        }
        if (!world.isRemote && isBegin){
            choose();
            /*int index = round % 10;
            if (index == 4){
                choose();
            }
            else if (index < 4 && index > 0){
                beat();
            }
            else if (index == 9){
                ready();
            }*/

        }
    }

    private void pickUpTp(){
        int[][] offSet = {{5, -6, 0}, {-5, -6, 0}, {4, -6, 4}, {-4, -6, -4},
                          {0, -6, 5}, {0, -6, -5}, {-4, -6, 4}, {4, -6, -4}};
        for (int i = 0; i < 8; i++){
            BlockPos pos = this.pos.add(offSet[i][0], offSet[i][1], offSet[i][2]);
            MessageTpPlayer message = new MessageTpPlayer(pos, names[i]);
            NetWorkLoader.instance.sendToServer(message);
        }
    }
    //遍历所有攻击格设置CanMove
    private void setCanMoveChess(boolean move){
        int[] offsetX = {-21, -4, 13};
        int offsetY = -8;
        int[] offsetZ = {-20, -4, 12};
        for (int i = 0; i < 3; i++){
            for (int j = 0; j < 3; j++){
                if (i == 1 && j == 1){ continue; }
                BlockPos normalPos = this.pos.offset(EnumFacing.EAST, offsetX[i])
                        .offset(EnumFacing.UP, offsetY).offset(EnumFacing.SOUTH, offsetZ[j]);
                for (int k = 0; k < 8; k++) {
                    for (int l = 0; l < 9; l++) {
                        BlockPos blockPos = normalPos.offset(EnumFacing.EAST, k).offset(EnumFacing.SOUTH, l);
                        TileEntity entity = world.getTileEntity(blockPos);
                        if (entity instanceof TileEntityAttackBlock){
                            ((TileEntityAttackBlock)entity).setCanMove(move);
                        }
                    }
                }
            }
        }
    }
    //选秀阶段
    private void choose(){
        if (!isChoosing){
            roundTime = 0;//重置时间
            tick = 0;
            isChoosing = true;//设置状态
            //setCanMoveChess(false);//棋子不可选取
            //tpChoose();//tp玩家
            resetChosenChess();//重置棋子
        }
        moveChosen();
        //checkChosen();
        /*if (roundTime % 7 == 6){
            clearGlass(roundTime / 7);
        }
        if (roundTime == 32){
            roundTimeChange(0);
            isChoosing = false;
            addChess();
            tpBack();
        }*/
    }
    //准备阶段
    private void ready(){
        if (!isReadying){
            roundTime = 0;
            tick = 0;
            isReadying = true;
            setCanMoveChess(true);
        }
        if (roundTime == 20){
            roundTimeChange(2);
            tpChessTo();
            doTolerant();
        }
    }
    //对战阶段
    private void beat(){
        if (!isBeating){
            roundTime = 0;
            tick = 0;
            isBeating = true;
            setCanMoveChess(false);
        }
        boolean t = chessRun();
        if (t){
            isBeating = false;
            roundTimeChange(1);
            resetChess();
            tpBack();
        }
    }

    private void win(){
        isBegin = false;
        //TODO
    }
    //重置选秀棋子
    private void resetChosenChess(){
        chosenLocation = new int[]{0, 36, 72, 108, 144, 180, -36, -72, -108, -144};
        for (int i = 0; i < 10; i++){
            chooseEntities[i] = new ZombieFashion(world);//TODO
            chooseEntities[i].setPosition(this.pos.getX() + 2.5 * Math.cos(chosenLocation[i] * Math.PI / 180),
                    5, this.pos.getY() + 2.5 * Math.sin(chosenLocation[i] * Math.PI / 180));
            world.spawnEntity(chooseEntities[i]);
        }
    }
    //移动选秀棋子
    private void moveChosen(){
        for (int i = 0; i < 10; i++) {
            chosenLocation[i] += 1;
        }
        for (int i = 0; i < 10; i++){
            chooseEntities[i].setPosition(this.pos.getX() + 2.5 * Math.cos(chosenLocation[i] * Math.PI / 180),
                    5, this.pos.getZ() + 2.5 * Math.sin(chosenLocation[i] * Math.PI / 180));
        }
    }
    //碰撞选秀棋子
    private void checkChosen(){
        for (int i = 0; i < 8; i++){
            if (names[i] != null){
                EntityPlayer player = world.getPlayerEntityByUUID(names[i]);
                if (player.getHeldItemMainhand().getItem() == ItemsLoader.STAFF){
                    for (int j = 0; j < 10; j++){
                        if (Math.pow(chosenLocation[j] - player.getPosition().getX(), 2)
                        + Math.pow(chosenLocation[j] - player.getPosition().getZ(), 2) <= 1){
                            //TODO
                        }
                    }
                }
            }
        }
    }

    private void clearGlass(int index){
        int glassLocation[][] = {{5, -6, 0},{5, -6, 0},{5, -6, 0}, {-5, -6, 0}, {4, -6, 4},
                {-4, -6, -4}, {0, -6, 5}, {0, -6, -5}, {-4, -6, 4}, {4, -6, -4}};//TODO
        for (int i = -1; i < 2; i++){
            for (int j = -1; j < 2; j++){
                world.setBlockToAir(new BlockPos(pos.getX() + glassLocation[index][0] + i,
                        pos.getY() + glassLocation[index][1],
                        pos.getZ() + glassLocation[index][2] + j));
                world.setBlockToAir(new BlockPos(pos.getX() + glassLocation[index][0] + i,
                        pos.getY() + glassLocation[index][1],
                        pos.getZ() + glassLocation[index][2] + 1 + j));
            }
        }
    }

    private boolean chessRun(){
        return false;//TODO
    }

    private void doTolerant(){
        //TODO
    }

    private void roundTimeChange(int mode){
        //TODO
    }

    private void addChess(){
        //TODO
    }

    private void resetChess(){
        //TODO
    }

    private void tpChessTo(){
        //TODO
    }
    //tp玩家 参数:位置
    private void tpPlayers(int[][] locate){
        for (int i = 0; i < 8; i++){
            UUID u = names[i];
            if (u != null){
                EntityPlayer player = world.getPlayerEntityByUUID(u);
                player.setPosition(pos.getX() + locate[i][0], pos.getY() + locate[i][1], pos.getZ() + locate[i][2]);
            }
        }
    }
    //tp玩家去选秀
    private void tpChoose(){
        int[][] offSet = {{5, -6, 0}, {-5, -6, 0}, {4, -6, 4}, {-4, -6, -4},
                {0, -6, 5}, {0, -6, -5}, {-4, -6, 4}, {4, -6, -4}};
        tpPlayers(offSet);
    }
    //tp玩家回平台
    private void tpBack(){
        int[][] offSet = {{5, -6, 0}, {-5, -6, 0}, {4, -6, 4}, {-4, -6, -4},
                {0, -6, 5}, {0, -6, -5}, {-4, -6, 4}, {4, -6, -4}};//TODO
        tpPlayers(offSet);
    }
}
