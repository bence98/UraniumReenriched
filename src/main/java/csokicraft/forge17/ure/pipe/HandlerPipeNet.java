package csokicraft.forge17.ure.pipe;

import java.util.*;

import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.common.gameevent.TickEvent.*;

public class HandlerPipeNet{
	public static HandlerPipeNet inst=new HandlerPipeNet();
	
	protected List<PipeNetwork> nets;
	
	protected HandlerPipeNet(){
		nets=new LinkedList<>();
	}
	
	public synchronized void add(PipeNetwork net){
		nets.add(net);
	}
	
	public synchronized void remove(PipeNetwork net){
		nets.remove(net);
	}
	
	@SubscribeEvent
	public void onTick(WorldTickEvent e){
		if(!e.world.isRemote&&e.phase==Phase.START)
			tickAll();
	}

	private synchronized void tickAll(){
		for(PipeNetwork net:nets)
			net.tick();
	}
}
