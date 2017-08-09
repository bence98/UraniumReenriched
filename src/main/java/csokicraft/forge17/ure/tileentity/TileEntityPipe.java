package csokicraft.forge17.ure.tileentity;

import java.util.*;
import java.util.Map.Entry;

import javax.vecmath.Vector3d;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import csokicraft.forge17.ure.pipe.PipeNetwork;
import csokicraft.util.mcforge.UtilMcForge;
import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.common.util.ForgeDirection;
import net.minecraftforge.fluids.IFluidHandler;

public class TileEntityPipe extends TileEntity{
	protected PipeNetwork net;
	
	public void updateNet(){
		if(worldObj.isRemote) return;
		
		PipeNetwork nova=new PipeNetwork();
		Queue<Entry<TileEntity, ForgeDirection>> q=new LinkedList<>();
		List<Entry<TileEntity, ForgeDirection>> done=new ArrayList<>();
		q.addAll(neighbors(this));
		nova.add(this);
		while(!q.isEmpty()){
			Entry<TileEntity, ForgeDirection> e=q.poll();
			if(done.contains(e)||this.equals(e.getKey())) continue;
			
			TileEntity te=e.getKey();
			if(te instanceof TileEntityPipe){
				nova.add(((TileEntityPipe) te).setNet(nova));
				q.addAll(neighbors(te));
			}
			else if(te instanceof IFluidHandler)
				nova.add((IFluidHandler) te, e.getValue());
			
			done.add(e);
		}
		setNet(nova);
	}
	
	public TileEntityPipe setNet(PipeNetwork to){
		if(net!=null&&net!=to) net.unregister();
		net=to;
		return this;
	}
	/*
	@Override
	public void invalidate(){
		if(net!=null) net.unregister();
		super.invalidate();
	}
	
	@Override
	public void onChunkUnload(){
		if(net!=null) net.unregister();
		super.onChunkUnload();
	}
	*/
	
	@SideOnly(Side.CLIENT)
	public PipeNetwork getNet(){
		if(net==null||!net.isActive()) updateNet();
		return net;
	}
	
	public static Set<Entry<TileEntity, ForgeDirection>> neighbors(TileEntity te){
		Map<TileEntity, ForgeDirection> lst=new HashMap<>();
		for(int i=0;i<6;i++){
			ForgeDirection dir=ForgeDirection.getOrientation(i);
			Vector3d vec=UtilMcForge.getPosAtSide(new Vector3d(te.xCoord, te.yCoord, te.zCoord), dir);
			TileEntity n=te.getWorldObj().getTileEntity((int)vec.x, (int)vec.y, (int)vec.z);
			if(n!=null) lst.put(n, dir.getOpposite());
		}
		return lst.entrySet();
	}
}
