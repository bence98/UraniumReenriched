package csokicraft.forge17.ure.item.tool;

import csokicraft.forge17.ure.UraniumRE;
import net.minecraft.entity.*;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.*;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;

public class ItemRadSword extends ItemSword{

	public ItemRadSword(){
		super(UraniumRE.radTool);
	}

	@Override
	public boolean onLeftClickEntity(ItemStack stack, EntityPlayer player, Entity entity){
		if(entity instanceof EntityLivingBase){
			EntityLivingBase ent=(EntityLivingBase) entity;
			ent.addPotionEffect(new PotionEffect(Potion.poison.id, 30, 2));
		}
		return false;
	}
}
