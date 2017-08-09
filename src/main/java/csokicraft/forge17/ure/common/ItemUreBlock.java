package csokicraft.forge17.ure.common;

import java.util.List;

import csokicraft.forge17.ure.UraniumRE;
import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.*;

public class ItemUreBlock extends ItemBlock{

	public ItemUreBlock(Block b){
		super(b);
		setHasSubtypes(true);
	}
	
	@Override
	public int getMetadata(int i){
		return i;
	}

	@Override
	public String getUnlocalizedName(ItemStack is){
		Block b = ((ItemUreBlock) is.getItem()).getBlock();
		if(b instanceof IMetadataNamable)
			return ((IMetadataNamable) b).getUnlocalizedName(is.getItemDamage());
		return b.getUnlocalizedName();
	}
	
	@Override
	public void addInformation(ItemStack is, EntityPlayer p, List l, boolean b){
		Block blk=((ItemUreBlock)is.getItem()).getBlock();
		if(blk instanceof IRotationInfoProvider)
			addRotationInfo(((IRotationInfoProvider) blk).getInfo(is.getItemDamage()), l);
		super.addInformation(is, p, l, b);
	}
	
	private void addRotationInfo(IRotationInfo info, List l){
		if(info==null) return;
		if(info.provides())
			l.add("Generates "+info.amount()+" RW for the block below it");
		else
			l.add("Needs "+info.amount()+ " RW to run");
	}

	public Block getBlock(){
		return field_150939_a;
	}
}
