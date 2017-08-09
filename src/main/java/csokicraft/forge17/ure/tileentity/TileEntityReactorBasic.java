package csokicraft.forge17.ure.tileentity;

import csokicraft.forge17.ure.CommonProxy;
import csokicraft.forge17.ure.common.IHasGui;
import csokicraft.forge17.ure.common.IHasProgress;
import csokicraft.forge17.ure.recipe.IReactorFuelRecipe;
import csokicraft.forge17.ure.recipe.ReactorFuelRecipe;
import csokicraft.forge17.ure.recipe.ReactorFuelRecipes;
import csokicraft.forge17.ure.recipe.ReactorInfuseRecipes;
import csokicraft.forge17.ure.recipe.ReactorInfuseRecipes.ReactorInfuseRecipe;
import net.minecraft.inventory.ISidedInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.common.util.ForgeDirection;

public class TileEntityReactorBasic extends TileEntityInv implements IHasProgress, ISidedInventory, IHasGui{
	protected int proc, rad, ctm;
	
	public TileEntityReactorBasic(){
		slots=new ItemStack[3];
	}

	@Override
	public String getInventoryName(){
		return "tile.uretech.reactor_one.name";
	}

	@Override
	public void updateEntity(){
		checkFuel();
		checkInfuse();
		
		if(ctm>0)
			doLeak();
	}
	
	protected void checkFuel(){
		IReactorFuelRecipe fuel=ReactorFuelRecipes.inst.getRecipe(slots[0]);
		if(fuel!=null&&fuel.getTier()<=getReactorTier()){
			rad+=fuel.getPower();
			ctm+=fuel.getWaste();
			slots[0].stackSize--;
		}
		if(slots[0]!=null&&slots[0].stackSize<=0) slots[0]=null;
	}
	
	protected void checkInfuse(){
		ReactorInfuseRecipe rec=ReactorInfuseRecipes.inst.getRecipe(slots[1]);
		if(rad>0&&rec!=null&&rec.getTier()<=getReactorTier()&&outputValid(rec.getOutput())){
			if(proc>=rec.getPower()){
				slots[1].stackSize--;
				if(slots[2]==null) slots[2]=rec.getOutput().copy();
				else slots[2].stackSize+=rec.getOutput().stackSize;
				proc-=rec.getPower();
			}else{
				rad--;
				proc++;
			}
		}else proc=0;
		if(slots[1]!=null&&slots[1].stackSize<=0) slots[1]=null;
	}
	
	protected void doLeak(){
		
	}

	private boolean outputValid(ItemStack out){
		return slots[2]==null||(slots[2].isItemEqual(out)&&slots[2].stackSize+out.stackSize<slots[2].getMaxStackSize());
	}

	public int getReactorTier(){
		return 1;
	}

	@Override
	public void writeToNBT(NBTTagCompound nbt){
		nbt.setInteger("radiation", rad);
		nbt.setInteger("contamination", ctm);
		nbt.setInteger("proc", proc);
		super.writeToNBT(nbt);
	}
	
	@Override
	public void readFromNBT(NBTTagCompound nbt){
		super.readFromNBT(nbt);
		rad=nbt.getInteger("radiation");
		ctm=nbt.getInteger("contamination");
		proc=nbt.getInteger("proc");
	}
	
	public int getRadiation(){
		return rad;
	}
	
	public int getContamination(){
		return ctm;
	}

	/** IHasProgress */
	
	@Override
	public int getProgress(){
		return proc;
	}

	@Override
	public int getCycleSize(){
		ReactorInfuseRecipe rec=ReactorInfuseRecipes.inst.getRecipe(slots[1]);
		if(rec!=null) return rec.getPower();
		return -1;
	}

	/** ISidedInventory */
	
	@Override
	public int[] getAccessibleSlotsFromSide(int s){
		if(s==ForgeDirection.UP.ordinal())
			return new int[]{1};
		if(s==ForgeDirection.DOWN.ordinal())
			return new int[]{2};
		return new int[]{0};
	}

	@Override
	public boolean canInsertItem(int i, ItemStack is, int s){
		if(s==ForgeDirection.DOWN.ordinal()) return false;
		if(s==ForgeDirection.UP.ordinal()) return i==1;
		return i==0;
	}

	@Override
	public boolean canExtractItem(int i, ItemStack is, int s){
		return s==ForgeDirection.DOWN.ordinal()&&i==2;
	}
	
	/** IHasGui */
	
	@Override
	public int GetGuiID(){
		return CommonProxy.GUIID_REACTOR;
	}
}