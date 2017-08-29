package csokicraft.forge17.ure.tileentity;

import csokicraft.forge17.ure.CommonProxy;
import csokicraft.forge17.ure.common.IHasGui;
import csokicraft.forge17.ure.common.IHasProgress;
import csokicraft.forge17.ure.recipe.IReactorFuelRecipe;
import csokicraft.forge17.ure.recipe.ReactorFuelRecipeItemStack;
import csokicraft.forge17.ure.recipe.ReactorFuelRecipes;
import csokicraft.forge17.ure.recipe.ReactorInfuseRecipes;
import csokicraft.forge17.ure.recipe.ReactorInfuseRecipes.ReactorInfuseRecipe;
import net.minecraft.inventory.ISidedInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.common.util.ForgeDirection;

import static csokicraft.forge17.ure.block.ReactorLeakHandler.*;

import java.util.Random;

public class TileEntityReactorBasic extends TileEntityInv implements IHasProgress, ISidedInventory, IHasGui{
	public static int MAX_RAD=10_000, MAX_CTM=10_000;
	
	public int proc, rad, ctm;
	
	protected int nextLeak=-1;
	
	public TileEntityReactorBasic(){
		slots=new ItemStack[3];
	}

	@Override
	public String getInventoryName(){
		return "tile.uretech.reactor_one.name";
	}

	@Override
	public void updateEntity(){
		if(getWorldObj().isRemote)
			return;
		
		checkFuel();
		checkInfuse();
		
		if(nextLeak<0)
			randomizeLeak();
		else if(nextLeak==0)
			doLeak();
		else
			nextLeak--;
	}
	
	protected void checkFuel(){
		IReactorFuelRecipe fuel=ReactorFuelRecipes.inst.getRecipe(slots[0]);
		if(rad<MAX_RAD&&ctm<MAX_CTM&&fuel!=null&&fuel.getTier()<=getReactorTier()){
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
		for(int x=0;x<LEAK_PASSES;x++){
			if(ctm<LEAK_INTENSITY)
				return;
			int i=worldObj.rand.nextInt(2*LEAK_RADIUS)-LEAK_RADIUS;
			int k=worldObj.rand.nextInt(2*LEAK_RADIUS)-LEAK_RADIUS;
			inner:for(int j=-LEAK_MAX_ELEVATION;j<LEAK_MAX_ELEVATION;j++){
				if(leak(xCoord+i, yCoord+j, zCoord+k, worldObj)){
					ctm-=LEAK_INTENSITY;
					break inner;
				}
			}
		}
		
		randomizeLeak();
	}
	
	protected void randomizeLeak(){
		nextLeak=worldObj.rand.nextInt(LEAK_MAX_DELAY);
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

	/** IInventory */
	@Override
	public boolean isItemValidForSlot(int i, ItemStack is){
		switch(i){
		case 0:
			IReactorFuelRecipe fuel=ReactorFuelRecipes.inst.getRecipe(is);
			return fuel!=null&&fuel.getTier()<=getReactorTier();
		case 1:
			if(getReactorTier()==2)
				return false;
			ReactorInfuseRecipe rec=ReactorInfuseRecipes.inst.getRecipe(is);
			return rec!=null&&rec.getTier()<=getReactorTier();
		}
		return super.isItemValidForSlot(i, is);
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
