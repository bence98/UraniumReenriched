package csokicraft.forge17.ure.entity;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

import csokicraft.forge17.ure.Vec3i;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.projectile.EntityThrowable;
import net.minecraft.init.Blocks;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EntityDamageSource;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;

public class EntityPlasmaCell extends EntityThrowable{

	public EntityPlasmaCell(World w){
		super(w);
	}
	
	public EntityPlasmaCell(World w, double x, double y, double z){
		super(w, x, y, z);
	}
	
	public EntityPlasmaCell(World w, EntityLivingBase e){
		super(w, e);
	}

	@Override
	protected void onImpact(MovingObjectPosition pos){
		if(!worldObj.isRemote){
			worldObj.playSoundAtEntity(this, "random.fizz", 0.5f, 0.5f);
			Thread th=new ThreadPlasmaAnnihilation(worldObj, pos.blockX, pos.blockY, pos.blockZ, 25, getThrower());
			th.start();
			setDead();
		}
	}

	static class ThreadPlasmaAnnihilation extends Thread{
		/** Sphere's data: center coords & radius */
		protected int x, y, z, r;
		protected World w;
		protected EntityLivingBase p;
		
		public ThreadPlasmaAnnihilation(World wObj, int xPos, int yPos, int zPos, int rLen, EntityLivingBase pEnt){
			x=xPos;
			y=yPos;
			z=zPos;
			r=rLen;
			w=wObj;
			p=pEnt;
		}
		
		@Override
		public void run(){
			loop();
		}
		
		private void loop(){
			long rSq=r*r;
			for(int i=-r;i<+r;i++){
				long iSq=i*i;
				for(int j=-r;j<+r;j++){
					if(y+j>w.getHeight()||y+j<=0)
						continue;
					long jSq=j*j;
					for(int k=-r;k<+r;k++){
						long kSq=k*k;
						if(iSq+jSq+kSq<=rSq){
							while(!HandlerPlasma.inst.addBlockPos(x+i, y+j, z+k))
								Thread.yield();
						}
					}
				}
			}
		}
		
		private void recurse(){
			Queue<Vec3i> q=new LinkedList<>();
			List<Vec3i> visited=new ArrayList<>();
			Vec3i origin=new Vec3i(x, y, z);
			q.offer(origin);
			outer:while(!q.isEmpty()){
				Vec3i v=q.poll();
				if(visited.contains(v))
					continue outer;
				visited.add(v);
				inner:for(ForgeDirection dir:ForgeDirection.VALID_DIRECTIONS){
					Vec3i u=v.neighborAt(dir);
					if(u.y<0||u.y>w.getHeight()||u.distanceFrom(origin)>r)
						continue inner;
					q.offer(u);
				}
				HandlerPlasma.inst.addBlockPos(v.x, v.y, v.z);
			}
		}
	}
}
