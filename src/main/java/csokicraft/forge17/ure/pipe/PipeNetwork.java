package csokicraft.forge17.ure.pipe;

import java.util.*;

import csokicraft.forge17.ure.tileentity.TileEntityPipe;
import net.minecraft.client.renderer.texture.ITickable;
import net.minecraftforge.common.util.ForgeDirection;
import net.minecraftforge.fluids.*;

public class PipeNetwork implements ITickable, Iterable<IFluidHandler>{
	protected Map<IFluidHandler, ForgeDirection> tiles;
	protected List<TileEntityPipe> pipes;
	protected boolean active=true;
	
	public PipeNetwork(){
		tiles=new HashMap<>();
		pipes=new ArrayList<>();
		HandlerPipeNet.inst.add(this);
	}
	
	public boolean isActive(){
		return active;
	}
	
	public synchronized void unregister(){
		if(!active) return;
		active=false;
		HandlerPipeNet.inst.remove(this);
		for(TileEntityPipe te:pipes)
			te.setNet(null);
		tiles=null;
		pipes=null;
	}
	
	@Override
	public synchronized void tick(){
		if(!active) return;
		
		for(IFluidHandler fh:tiles.keySet()){
			ForgeDirection dir=tiles.get(fh);
			for(FluidTankInfo info:fh.getTankInfo(dir)){
				for(IFluidHandler dest:tiles.keySet()){
					FluidStack fs=fh.drain(dir, info.fluid, false);
					if(dest.equals(fh)||fs==null||fs.amount==0) continue;
					ForgeDirection destDir=tiles.get(dest);
					int qty=0;
					if((qty=dest.fill(destDir, fs, false))>0){
						FluidStack trans=new FluidStack(fs.getFluid(), qty);
						dest.fill(destDir, fh.drain(dir, trans, true), true);
					}
				}
			}
		}
	}
	
	public synchronized void add(IFluidHandler fh, ForgeDirection dir){
		if(!active) return;
		
		if(fh!=null&&dir!=null)
			tiles.put(fh, dir);
	}
	
	public synchronized void add(TileEntityPipe te){
		if(!active) return;
		
		pipes.add(te);
	}

	@Override
	public Iterator<IFluidHandler> iterator(){
		if(!active) return Collections.emptyListIterator();
		
		return tiles.keySet().iterator();
	}
}
