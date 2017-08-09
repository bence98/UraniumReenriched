package csokicraft.forge17.ure.entity;

import java.util.*;

import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.common.gameevent.TickEvent.Phase;
import cpw.mods.fml.common.gameevent.TickEvent.WorldTickEvent;
import csokicraft.forge17.ure.Vec3i;
import net.minecraft.entity.Entity;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EntityDamageSource;
import net.minecraft.util.Vec3;

public class HandlerPlasma{
	public static HandlerPlasma inst=new HandlerPlasma();

	protected LinkedList<Vec3i> blocks;
	
	protected HandlerPlasma(){
		blocks=new LinkedList<>();
	}
	
	public synchronized boolean addBlockPos(int x, int y, int z){
		return blocks.offer(new Vec3i(x, y, z));
	}
	
	public synchronized Vec3i popBlockPos(){
		return blocks.poll();
	}
	
	public synchronized int getPosCount(){
		return blocks.size();
	}
	
	@SubscribeEvent
	public void onTick(WorldTickEvent e){
		if(!e.world.isRemote&&e.phase==Phase.END){
			int c=getPosCount();
			for(int i=0;i<c;i++){
				Vec3i v=popBlockPos();
				if(v==null) break;
				int x=v.x,
					y=v.y,
					z=v.z;
				if(!e.world.setBlockToAir(x, y, z)){
					if(!e.world.isAirBlock(x, y, z))
						System.out.println("Couldn't set block at "+v);
					else
						System.out.println("Block was air at "+v);
				}
				List<Entity> l=e.world.getEntitiesWithinAABB(Entity.class, AxisAlignedBB.getBoundingBox(x-1, y-1, z-1, x+1, y+1, z+1));
				for(Entity ent:l){
					ent.attackEntityFrom(DamageSource.generic.setExplosion(), 2000);
					if(ent.isEntityAlive()&&!ent.isEntityInvulnerable()) ent.setDead();
				}
			}
		}
	}
}
