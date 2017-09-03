package csokicraft.forge17.ure.item;

import cpw.mods.fml.common.eventhandler.Event.Result;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import csokicraft.forge17.ure.block.BlockHeavyWater;
import net.minecraft.block.Block;
import net.minecraft.util.MovingObjectPosition;
import net.minecraftforge.event.entity.player.FillBucketEvent;

public class HandlerDeutBucket{
	public static HandlerDeutBucket inst=new HandlerDeutBucket();

	@SubscribeEvent
	public void fillBucket(FillBucketEvent evt){
		MovingObjectPosition pos=evt.target;
		Block b=evt.world.getBlock(pos.blockX, pos.blockY, pos.blockZ);
		if(b instanceof BlockHeavyWater){
			evt.result=UREItems.heavyw_b.copy();
			evt.world.setBlockToAir(pos.blockX, pos.blockY, pos.blockZ);
			evt.setResult(Result.ALLOW);
		}
	}
}
