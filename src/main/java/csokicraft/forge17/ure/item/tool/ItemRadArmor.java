package csokicraft.forge17.ure.item.tool;

import net.minecraft.entity.Entity;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemStack;

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
}
