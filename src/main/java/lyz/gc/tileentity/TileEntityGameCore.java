package lyz.gc.tileentity;

import lyz.gc.api.GameMath;
import lyz.gc.api.chess.PlayerInfo;
import lyz.gc.api.entity.EntityBase;
import lyz.gc.entities.ZombieFashion;
import lyz.gc.items.EntityItem;
import lyz.gc.loader.ItemsLoader;
import lyz.gc.loader.NetWorkLoader;
import lyz.gc.network.MessageTpPlayer;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.play.server.SPacketUpdateTileEntity;
import net.minecraft.server.management.PlayerChunkMapEntry;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.ITickable;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.WorldServer;

import javax.annotation.Nullable;
import java.util.Objects;
import java.util.UUID;

public class TileEntityGameCore extends TileEntity implements ITickable {

    private boolean isBegin = false;//是否开始游戏
    private int playerNum = 0;//玩家人数
    private PlayerInfo[] players = new PlayerInfo[8];//玩家信息
    private UUID[] names = new UUID[8];//UUID
    private int[][][] map = new int[8][10][9];//每块地图的数据
    private int[] chosenLocation = new int[10];//选秀棋子位置
    private int tick = 0, roundTime = 0, round = 0, liveNum = 0;//20tick = 1roundTime
    private boolean isBeating = false, isReadying = false, isChoosing = false;//游戏状态
    private EntityBase[] chooseEntities = new EntityBase[10];//选秀棋子
    /*1-1 ~ 1-2 野怪
    * 2-1 ~ 2-3 对抗
    * 2-4 选秀
    * 2-5 ~ 2-6 对抗
    * 2-7 野怪*/

    public TileEntityGameCore(){ }

    public int getPlayerNumber(EntityPlayer player){
        UUID uuid = player.getUniqueID();
        for (int i = 0; i < 8; i++){
            if (names[i] == uuid){
                return i;
            }
        }
        return -1;
    }
    //开始游戏
    public void begin() {
        isBegin = true;
        for (PlayerInfo playerInfo: players) {
            playerInfo = new PlayerInfo();
        }
        /*EntityPlayer entityPlayer = world.getPlayerEntityByUUID(names[0]);
        BlockPos pos = this.pos;
        MessageTpPlayer message = new MessageTpPlayer(pos, names[0]);
        NetWorkLoader.instance.sendToServer(message);*/
    }
    //加入游戏//
    public boolean addPlayer(EntityPlayer playerIn){
        UUID n = playerIn.getUniqueID();
        for (int i = 0; i < 8; i++){
            if (names[i] == null){
                names[i] = n;
                playerNum++;
                return true;
            }
            if (Objects.equals(names[i], n)){
                return false;
            }
        }
        return false;
    }
    //加人//
    public void addName(UUID uuid){
        for (int i = 0; i < 8; i++){
            if (names[i] == null){
                names[i] = uuid;
                playerNum++;
                System.out.println("++");
            }
        }
    }
    //主循环
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
    //遍历所有攻击格设置CanMove//
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
        checkChosen();
        /*if (roundTime % 7 == 6){
            clearGlass(roundTime / 7);
        }
        if (roundTime == 32){
            roundTimeChange();
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
            roundTimeChange();
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
            roundTimeChange();
            resetChess();
            tpBack();
        }
    }
    //获胜
    private void win(){
        isBegin = false;
        //TODO
    }
    //重置选秀棋子//
    private void resetChosenChess(){
        chosenLocation = new int[]{0, 36, 72, 108, 144, 180, 216, 252, 288, 324};
        for (int i = 0; i < 10; i++){
            chooseEntities[i] = new ZombieFashion(world);//TODO
            chooseEntities[i].setWeapon(new GameMath().randomWeapon(), 0);
            chooseEntities[i].setCanPick(false);
            chooseEntities[i].setPosition(this.pos.getX() + 2.5 * Math.cos(chosenLocation[i] * Math.PI / 180) + 0.5,
                    4, this.pos.getZ() + 2.5 * Math.sin(chosenLocation[i] * Math.PI / 180) + 0.5);
            world.spawnEntity(chooseEntities[i]);
        }
    }
    //移动选秀棋子//
    private void moveChosen(){
        for (int i = 0; i < 10; i++) {
            chosenLocation[i] += 1;
        }
        for (int i = 0; i < 10; i++){
            if (chooseEntities[i] != null){
                chooseEntities[i].setPosition(this.pos.getX() + 2.5 * Math.cos(chosenLocation[i] * Math.PI / 180) + 0.5,
                    4, this.pos.getZ() + 2.5 * Math.sin(chosenLocation[i] * Math.PI / 180) + 0.5);
            }
        }
    }
    //碰撞选秀棋子
    private void checkChosen(){
        for (int i = 0; i < 8; i++){
            if (names[i] != null){
                EntityPlayer player = world.getPlayerEntityByUUID(names[i]);
                if (player.getHeldItemMainhand().getItem() == ItemsLoader.STAFF){
                    for (int j = 0; j < 10; j++){
                        if (Math.pow(this.pos.getX() + 2.5 * Math.cos(chosenLocation[j]) - player.getPosition().getX(), 2)
                        + Math.pow(this.pos.getZ() + 2.5 * Math.sin(chosenLocation[j]) - player.getPosition().getZ(), 2) <= 0.5
                        && chooseEntities[j] != null){
                            world.removeEntity(chooseEntities[j]);
                            chooseEntities[j] = null;
                            player.setHeldItem(player.getActiveHand(),
                                    new ItemStack(ItemsLoader.ZF1.setOwnPlayer(player, new int[]{0, 1})));//TODO
                        }
                    }
                }
            }
        }
    }
    //清除选秀屏障
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
        for (UUID uuid:names){
            EntityPlayer player = world.getPlayerEntityByUUID(uuid);
            if (player != null){
                ItemStack stack = player.getHeldItemMainhand();
                if (stack.getItem() == ItemsLoader.ZF1){
                    player.setHeldItem(EnumHand.MAIN_HAND, new ItemStack(ItemsLoader.STAFF));
                    int[] xy = ((EntityItem)stack.getItem()).getPos();
                    int[] offsetX = {-21, -4, 13};
                    int offsetY = -8;
                    int[] offsetZ = {-20, -4, 12};
                    /*BlockPos normalPos = this.pos.offset(EnumFacing.EAST, offsetX[i])
                                    .offset(EnumFacing.UP, offsetY).offset(EnumFacing.SOUTH, offsetZ[j]);
                    for (int k = 0; k < 8; k++) {
                        for (int l = 0; l < 9; l++) {
                            BlockPos blockPos = normalPos.offset(EnumFacing.EAST, k).offset(EnumFacing.SOUTH, l);
                            TileEntity entity = world.getTileEntity(blockPos);
                            if (entity instanceof TileEntityAttackBlock){
                                ((TileEntityAttackBlock)entity).setCanMove(move);
                            }
                        }
                    }*/
                }
            }
        }
    }
    //自动进行回合数改变//
    private void roundTimeChange(){
        int m = roundTime % 10;
        if (m == 1 || m == 2 || m == 3 || m == 4 || m == 5 || m == 6){
            if (roundTime == 12){
                roundTime = 21;
                return;
            }
            roundTime++;
        }else{
            roundTime += 4;
        }
    }

    private void resetChess(){
        //TODO
    }

    private void tpChessTo(){
        //TODO
    }
    //tp玩家 参数:位置//
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

    public boolean isBegin() {
        return isBegin;
    }

    public void setPlayerNum(int num){
        playerNum = num;
    }

    public int getPlayerNum(){
        return this.playerNum;
    }
    //数据持久化//////////////////////////////////////////未完
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
}
