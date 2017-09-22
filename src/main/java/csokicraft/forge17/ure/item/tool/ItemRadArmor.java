package csokicraft.forge17.ure.item.tool;

import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.world.World;

public class ItemRadArmor extends ItemArmor {

	public ItemRadArmor(ArmorMaterial mat, int renderIdx, int type){
		super(mat, renderIdx, type);
	}

	@Override
	public String getArmorTexture(ItemStack stack, Entity entity, int slot, String type){
		if("leggings".equals(type))
			return "UraniumRE:textures/models/armor/c14_layer_2.png";
		return "UraniumRE:textures/models/armor/c14_layer_1.png";
	}
	
	@Override
	public void onArmorTick(World world, EntityPlayer player, ItemStack itemStack) {
		ItemArmor item=(ItemArmor) itemStack.getItem();
		switch (item.armorType) {
		case 0:
			player.addPotionEffect(new PotionEffect(Potion.nightVision.id, 10));
			break;
		case 1:
			player.removePotionEffect(Potion.poison.id);
			break;
		case 2:
			player.addPotionEffect(new PotionEffect(Potion.resistance.id, 10));
			break;
		}
		super.onArmorTick(world, player, itemStack);
	}
}
