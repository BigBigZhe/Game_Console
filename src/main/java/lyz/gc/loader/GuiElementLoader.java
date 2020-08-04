package lyz.gc.loader;

import javax.annotation.Nullable;

import lyz.gc.ModMain;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.network.IGuiHandler;
import net.minecraftforge.fml.common.network.NetworkRegistry;

public class GuiElementLoader implements IGuiHandler{
	
	public GuiElementLoader()
    {
        NetworkRegistry.INSTANCE.registerGuiHandler(ModMain.instance, this);
    }
	
	@Nullable
	@Override
	public Object getServerGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) {
		/*BlockPos pos = new BlockPos(x, y, z);
		TileEntity tileEntity = world.getTileEntity(pos);
		if (ID == 2){
			TileEntityElectronicProcessing tile = (TileEntityElectronicProcessing)tileEntity;
			return new ContainerElectronicProcessing(player.inventory, tile);
		}
		System.out.print("NULL");*/
		return null;
	}

	@Nullable
	@Override
	public Object getClientGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) {
		/*BlockPos pos = new BlockPos(x, y, z);
		TileEntity tileEntity = world.getTileEntity(pos);
		if (ID == 2){
			TileEntityElectronicProcessing tile = (TileEntityElectronicProcessing)tileEntity;
			return new GuiElectronicProcessing(player.inventory, tile);
		}
		System.out.print("NULL");*/
		return null;
	}

}
