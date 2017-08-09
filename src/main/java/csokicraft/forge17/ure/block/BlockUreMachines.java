package csokicraft.forge17.ure.block;

import java.util.*;

import cofh.api.energy.IEnergyHandler;
import cpw.mods.fml.common.Loader;
import csokicraft.forge17.ure.UraniumRE;
import csokicraft.forge17.ure.common.*;
import csokicraft.forge17.ure.item.UREItems;
import csokicraft.forge17.ure.pipe.PipeNetwork;
import csokicraft.forge17.ure.tileentity.*;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.client.resources.I18n;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.*;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.*;
import net.minecraft.world.*;
import net.minecraftforge.common.util.ForgeDirection;
import net.minecraftforge.fluids.*;

import static csokicraft.forge17.ure.UraniumRE.rfAPI;

/** All machines. metas:
  * 0:reactor mk1
  * 1:reactor mk2
  * 2:<future use>
  * 3:uranium cleaner
  * 4:hazardous material recycler
  * 5:deuterium extractor
  * 6:plasma heater
  * 7:water pump
  * 8:pipe
  * 9:iron handle
  * 10:steam turbine
  * 11:advanced turbine
  * 12:RF driver
  * 13:RF modulator
  * 14:electric rotator
  * 15:electric inductor*/
public class BlockUreMachines extends Block implements IMetadataNamable, IRotationInfoProvider{
	Map<String, IIcon> icons=new HashMap<>();

	public BlockUreMachines(){
		super(Material.iron);
		setHarvestLevel("pickaxe", 1);
		setHardness(3.5f);
	}
	
	@Override
	public TileEntity createTileEntity(World world, int metadata){
		switch(metadata){
		case 0:
			return new TileEntityReactorBasic();
		case 1:
			return new TileEntityReactorSteam();
		case 3:
			return new TileEntityUraniumProcessor();
		case 4:
			return new TileEntityHMR();
		case 5:
			return new TileEntityDeutExtractor();
		case 6:
			return new TileEntityPlasmafier();
		case 7:
			return new TileEntityWaterPump();
		case 8:
			return new TileEntityPipe();
		case 9:
			return new TileEntityHandle();
		case 10:
			return new TileEntitySteamTurbine();
		case 11:
			return new TileEntityAdvTurbine();
		case 12:
			if(rfAPI) return new TileEntityRFDriver();
		case 13:
			if(rfAPI) return new TileEntityRFModulator();
		}
		return super.createTileEntity(world, metadata);
	}

	@Override
	public boolean hasTileEntity(int metadata){
		return true;
	}
	
	@Override
	protected String getTextureName(){
		return "UraniumRE:machine_side";
	}
	
	@Override
	public void registerBlockIcons(IIconRegister reg){
		super.registerBlockIcons(reg);
		for(String s:new String[]{
			"cleaner_front", "deutextr_front", "handle_top", "hmr_front",
			"machine_rotor", "plasmafy_front", "pump_side", "reactor1_front",
			"reactor2_front", "rfout_side", "rfin_side", "turbine1_top",
			"turbine2_top", "machine_rf"
		}){
			icons.put(s, reg.registerIcon("UraniumRE:"+s));
		}
	}
	
	@Override
	public IIcon getIcon(int s, int meta){
		switch(meta){
		case 0:
			if(s==ForgeDirection.SOUTH.ordinal())
				return icons.get("reactor1_front");
			break;
		case 1:
			if(s==ForgeDirection.SOUTH.ordinal())
				return icons.get("reactor2_front");
			break;
		case 3:
			if(s==ForgeDirection.UP.ordinal())
				return icons.get("machine_rotor");
			if(s==ForgeDirection.SOUTH.ordinal())
				return icons.get("cleaner_front");
			break;
		case 4:
			if(s==ForgeDirection.UP.ordinal())
				return icons.get("machine_rotor");
			if(s==ForgeDirection.SOUTH.ordinal())
				return icons.get("hmr_front");
			break;
		case 5:
			if(s==ForgeDirection.UP.ordinal())
				return icons.get("machine_rotor");
			if(s!=ForgeDirection.UP.ordinal()&&s!=ForgeDirection.DOWN.ordinal())
				return icons.get("deutextr_front");
			break;
		case 6:
			if(s==ForgeDirection.UP.ordinal())
				return icons.get("machine_rotor");
			if(s==ForgeDirection.SOUTH.ordinal())
				return icons.get("plasmafy_front");
			break;
		case 7:
			if(s==ForgeDirection.UP.ordinal())
				return icons.get("machine_rotor");
			if(s!=ForgeDirection.UP.ordinal()&&s!=ForgeDirection.DOWN.ordinal())
				return icons.get("pump_side");
			break;
		case 9:
			if(s==ForgeDirection.DOWN.ordinal())
				return icons.get("machine_rotor");
			if(s==ForgeDirection.UP.ordinal())
				return icons.get("handle_top");
			break;
		case 10:
			if(s==ForgeDirection.DOWN.ordinal())
				return icons.get("machine_rotor");
			if(s==ForgeDirection.UP.ordinal())
				return icons.get("turbine1_top");
			break;
		case 11:
			if(s==ForgeDirection.DOWN.ordinal())
				return icons.get("machine_rotor");
			if(s==ForgeDirection.UP.ordinal())
				return icons.get("turbine2_top");
			break;
		case 12:
			if(s==ForgeDirection.DOWN.ordinal())
				return icons.get("machine_rotor");
			if(s==ForgeDirection.UP.ordinal())
				return icons.get("machine_rf");
			return icons.get("rfin_side");
		case 13:
			if(s==ForgeDirection.UP.ordinal())
				return icons.get("machine_rotor");
			if(s==ForgeDirection.DOWN.ordinal())
				return icons.get("machine_rf");
			return icons.get("rfout_side");
		}
		return super.getIcon(meta, s);
	}
	
	@Override
	public void getSubBlocks(Item arg0, CreativeTabs arg1, List l){
		for(int x=0;x<16;x++) l.add(new ItemStack(arg0, 1, x));
	}
	
	@Override
	public int damageDropped(int m){
		return m;
	}
	
	@Override
	public String getUnlocalizedName(int meta){
		String prefix="tile.uretech.", name=null;
		switch(meta){
		case 0:
			name="reactor_one";
			break;
		case 1:
			name="reactor_two";
			break;
		case 2:
			name="reactor_three";
			break;
		case 3:
			name="ur_cleaner";
			break;
		case 4:
			name="hazmat_rec";
			break;
		case 5:
			name="deut_extr";
			break;
		case 6:
			name="plasma_heater";
			break;
		case 7:
			name="pump";
			break;
		case 8:
			name="pipe";
			break;
		case 9:
			name="handle";
			break;
		case 10:
			name="turbine_one";
			break;
		case 11:
			name="turbine_two";
			break;
		case 12:
			name="rf_in";
			break;
		case 13:
			name="rf_out";
			break;
		case 14:
			name="eu_in";
			break;
		case 15:
			name="eu_out";
			break;
		}
		return prefix+name;
	}
	
	@Override
	public boolean onBlockActivated(World w, int x, int y, int z, EntityPlayer p, int side, float f1, float f2, float f3){
		TileEntity te=w.getTileEntity(x, y, z);
		ForgeDirection dir=ForgeDirection.getOrientation(side);
		
		if(te!=null){
			if(w.isRemote) return true;
			
			if(te instanceof IInteractable){
				if(((IInteractable) te).interact(p))
					return true;
			}

			ItemStack held=p.getHeldItem();
			
			if(held!=null&&held.isItemEqual(UREItems.debugger)){
				if(te instanceof IInventory){
					p.addChatMessage(new ChatComponentText(I18n.format("chat.ure.invcontent")));
					IInventory inv=(IInventory) te;
					for(int i=0;i<inv.getSizeInventory();i++){
						ItemStack is=inv.getStackInSlot(i);
						if(is!=null) printItemStack(p, i, is);
					}
				}
				if(te instanceof IFluidHandler){
					p.addChatMessage(new ChatComponentText(I18n.format("chat.ure.flcontent")));
					IFluidHandler fh=(IFluidHandler) te;
					int id=0;
					for(FluidTankInfo info:((IFluidHandler) te).getTankInfo(dir)){
						if(info!=null) printFluidStack(p, id, info.fluid);
						id++;
					}
				}
				if(te instanceof IHasProgress){
					IHasProgress pr=(IHasProgress) te;
					p.addChatMessage(new ChatComponentText(I18n.format("chat.ure.proc", (pr.getProgress()*100/pr.getCycleSize()))));
				}
				if(rfAPI&&te instanceof IEnergyHandler){
					IEnergyHandler eh=(IEnergyHandler) te;
					p.addChatMessage(new ChatComponentText(I18n.format("chat.ure.rf", eh.getEnergyStored(dir), eh.getMaxEnergyStored(dir))));
				}
				if(te instanceof TileEntityPipe){
					p.addChatMessage(new ChatComponentText(I18n.format("chat.ure.pipenet")));
					printPipeNet(p, ((TileEntityPipe) te).getNet());
				}
				if(te instanceof TileEntityReactorBasic){
					TileEntityReactorBasic rea=((TileEntityReactorBasic) te);
					p.addChatMessage(new ChatComponentText(I18n.format("chat.ure.reactor", rea.getRadiation(), rea.getContamination())));
				}
				return true;
			}

			int currentItem=p.inventory.currentItem;
			if(te instanceof IFluidHandler){
				if(FluidContainerRegistry.isFilledContainer(held)){
					fillFluid(p, held, currentItem, (IFluidHandler) te, dir);
					return true;
				}else if(FluidContainerRegistry.isEmptyContainer(held)){
					emptyFluid(p, held, currentItem, (IFluidHandler) te, dir);
					return true;
				}
			}
			
			if(te instanceof IHasGui){
				p.openGui(UraniumRE.inst, ((IHasGui) te).GetGuiID(), w, x, y, z);
				return true;
			}
			
			if(te instanceof IInventory){
				if(held==null){
					retrieveStack(p.inventory, currentItem, (IInventory) te);
				}else{
					placeStack(p.inventory, held, currentItem, (IInventory) te);
				}
			}
		}
		
		return super.onBlockActivated(w, x, y, z, p, side, f1, f2, f3);
	}
	
	protected void updatePipe(IBlockAccess w, int x, int y, int z){
		TileEntity te=w.getTileEntity(x, y, z);
		if(te instanceof TileEntityPipe)
			((TileEntityPipe) te).updateNet();
	}
	
	@Override
	public void onNeighborChange(IBlockAccess world, int x, int y, int z, int tileX, int tileY, int tileZ){
		updatePipe(world, x, y, z);
		super.onNeighborChange(world, x, y, z, tileX, tileY, tileZ);
	}
	
	@Override
	public void onBlockAdded(World w, int x, int y, int z){
		updatePipe(w, x, y, z);
		super.onBlockAdded(w, x, y, z);
	}
	
	protected void printItemStack(EntityPlayer p, int id, ItemStack is){
		p.addChatMessage(new ChatComponentText(" ["+id+"]: "+I18n.format(is.getItem().getUnlocalizedName(is)+".name")+" * "+is.stackSize));
	}
	
	protected void printFluidStack(EntityPlayer p, int id, FluidStack fs){
		p.addChatMessage(new ChatComponentText(" ["+id+"]: "+I18n.format(fs.getFluid().getUnlocalizedName(fs)+".name")+" * "+fs.amount));
	}
	
	protected void printPipeNet(EntityPlayer p, PipeNetwork net){
		for(IFluidHandler h:net){
			p.addChatMessage(new ChatComponentText("<"+h.getClass().getName()+">"));
		}
	}
	
	protected static void giveItemStack(EntityPlayer p, ItemStack is){
		EntityItem ent=new EntityItem(p.getEntityWorld(), p.posX, p.posY, p.posZ, is);
		ent.delayBeforeCanPickup=0;
		ent.func_145797_a(p.getCommandSenderName());
		p.getEntityWorld().spawnEntityInWorld(ent);
	}
	
	protected void fillFluid(EntityPlayer p, ItemStack held, int currentItem, IFluidHandler fh, ForgeDirection dir){
		FluidStack fs=FluidContainerRegistry.getFluidForFilledItem(held);
		if(fh.fill(dir, fs, false)==fs.amount){
			fh.fill(dir, fs, true);
			if(!p.capabilities.isCreativeMode){
				held.stackSize--;
				ItemStack empty=FluidContainerRegistry.drainFluidContainer(held);
				if(held.stackSize<=0){
					p.inventory.setInventorySlotContents(currentItem, empty);
				}else{
					giveItemStack(p, empty);
				}
				p.inventory.markDirty();
			}
		}
	}
	
	protected void emptyFluid(EntityPlayer p, ItemStack held, int currentItem, IFluidHandler fh, ForgeDirection dir){
		for(FluidTankInfo info:fh.getTankInfo(dir)){
			Fluid fl=info.fluid.getFluid();
			if(fh.canDrain(dir, fl)){
				int cap=FluidContainerRegistry.getContainerCapacity(info.fluid, held);
				if(FluidContainerRegistry.isBucket(held)) cap=FluidContainerRegistry.BUCKET_VOLUME;
				FluidStack fs=fh.drain(dir, new FluidStack(fl, cap), false);
				if(fs!=null&&fs.amount==cap){
					fs=fh.drain(dir, fs, true);
					ItemStack filled=FluidContainerRegistry.fillFluidContainer(fs, held);
					held.stackSize--;
					if(held.stackSize<=0)
						p.inventory.setInventorySlotContents(currentItem, filled);
					else
						giveItemStack(p, filled);
					return;
				}
			}
		}
	}
	
	protected void placeStack(InventoryPlayer pInv, ItemStack held, int currentItem, IInventory inv){
		for(int i=0;i<inv.getSizeInventory();i++){
			if(inv.isItemValidForSlot(i, held)){
				ItemStack slot=inv.getStackInSlot(i);
				if(slot==null){
					inv.setInventorySlotContents(i, held.copy());
					held.stackSize=0;
					break;
				}
				if(slot.isItemEqual(held)){
					int sum=slot.stackSize+held.stackSize;
					slot.stackSize=Math.min(sum, slot.getMaxStackSize());
					held.stackSize=sum-slot.stackSize;
					if(held.stackSize==0) break;
				}
			}
		}
		if(held.stackSize==0){
			pInv.setInventorySlotContents(currentItem, null);
		}
	}
	
	protected void retrieveStack(InventoryPlayer pInv, int currentItem, IInventory inv){
		for(int i=inv.getSizeInventory()-1;i>=0;i--){
			ItemStack slot=inv.getStackInSlot(i);
			if(slot!=null){
				pInv.setInventorySlotContents(currentItem, slot.copy());
				inv.setInventorySlotContents(i, null);
				break;
			}
		}
	}

	@Override
	public IRotationInfo getInfo(int meta){
		switch (meta){
		case 3:
			return new RotationInfo(false, 1);
		case 4:
			return new RotationInfo(false, 2);
		case 5:
			return new RotationInfo(false, 5);
		case 6:
			return new RotationInfo(false, 10);
		case 7:
			return new RotationInfo(false, 1);
		case 9:
			return new RotationInfo(true, 1);
		case 10:
			return new RotationInfo(true, 5);
		case 11:
			return new RotationInfo(true, 20);
		case 12:
			return new RotationInfo(true, 10);
		case 13:
			return new RotationInfo(false, 1);
		case 14:
			return new RotationInfo(true, 0);
		case 15:
			return new RotationInfo(false, 0);
		}
		return null;
	}
}
