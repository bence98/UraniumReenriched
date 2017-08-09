package csokicraft.forge17.ure;

import cpw.mods.fml.common.*;
import cpw.mods.fml.common.Mod.EventHandler;
import cpw.mods.fml.common.Mod.Instance;
import cpw.mods.fml.common.event.*;
import cpw.mods.fml.common.gameevent.TickEvent.WorldTickEvent;
import cpw.mods.fml.common.network.NetworkRegistry;
import cpw.mods.fml.common.registry.GameRegistry;
import csokicraft.forge17.ure.block.*;
import csokicraft.forge17.ure.common.*;
import csokicraft.forge17.ure.entity.HandlerPlasma;
import csokicraft.forge17.ure.item.*;
import csokicraft.forge17.ure.pipe.HandlerPipeNet;
import csokicraft.forge17.ure.recipe.*;
import csokicraft.forge17.ure.recipe.RecyclingRecipes.WeightedItemStackResult;
import csokicraft.forge17.ure.tileentity.*;
import net.minecraft.block.Block;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.init.*;
import net.minecraft.item.*;
import net.minecraft.item.crafting.FurnaceRecipes;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fluids.*;
import net.minecraftforge.oredict.OreDictionary;
import net.minecraftforge.oredict.ShapedOreRecipe;
import net.minecraftforge.oredict.ShapelessOreRecipe;

import static csokicraft.forge17.ure.item.UREItems.*;

import java.util.logging.Logger;

/** Uranium Reenriched mod
  * @author CsokiCraft*/
@Mod(modid="UraniumRE", version="1.0.7 for MC 1.7.10", dependencies="required-after:CsokiCraftUtil")
public class UraniumRE{
	public static CreativeTabs tab=new CreativeTabURE();
	
	public static Item itemCell=new ItemCell().setCreativeTab(tab);
	public static Item itemComponent=new ItemUreComponent().setCreativeTab(tab);
	public static Item itemNuclear=new ItemNuclear().setCreativeTab(tab);
	
	public static Block blockMachine=new BlockUreMachines().setCreativeTab(tab);
	public static Block blockOre=new BlockUreOre().setCreativeTab(tab);
	public static Block blockD2O=new BlockHeavyWater();
	
	public static Fluid heavyWater=new Fluid("heavyWater").setBlock(blockD2O).setUnlocalizedName("tile.ureliq.heavywater"),
						steam=new Fluid("steam").setUnlocalizedName("tile.ureliq.steam");
	public static IFuelHandler fuelHandler=new UreFuelHandler();
	
	public static boolean rfAPI=false;
	public static Logger logger=Logger.getLogger("UraniumRE-Log");
	@Instance
	public static UraniumRE inst;
	@SidedProxy(clientSide="csokicraft.forge17.ure.ClientProxy", serverSide="csokicraft.forge17.ure.CommonProxy")
	public static CommonProxy proxy;
	
	/*public static Item itemBlockMachine = new ItemUreBlock(blockMachine);*/
	
	private void checkForCoFHAPI(){
		try{
			Class.forName("cofh.api.energy.IEnergyHandler");
			rfAPI=true;
			logger.info("Found CoFH API! Yay :3");
		}catch(ClassNotFoundException e){
			logger.info("CoFH API not found, RF blocks won't be added.");
		}
	}
	
	@EventHandler
	public void init(FMLInitializationEvent evt){
		checkForCoFHAPI();
		
		registerItems();
		registerBlocks();
		
		UREItems.init();
		addReactorFuels();
		addReactorInfusion();
		addHazmatRecycling();
		
		registerTEs();
		registerOreDict();
		registerMisc();

		registerCraftRecipes();
		registerFurnaceRecipes();
		
		proxy.registerModels();
	}

	private void addReactorFuels(){
		ReactorFuelRecipes.inst.registerRecipe("ingotUranium", 200, 10, null, 1);
		ReactorFuelRecipes.inst.registerRecipe(rad_fe, 110, 80, null, 1);
		ReactorFuelRecipes.inst.registerRecipe(carbon14, 110, 60, null, 1);
		ReactorFuelRecipes.inst.registerRecipe(cell_ur, 300, 5, cell_dep, 2);
		ReactorFuelRecipes.inst.registerRecipe("oreUranium", 300, 100, null, 1);
		ReactorFuelRecipes.inst.registerRecipe("ingotPlutonium", 120, 5, null, 2);
	}
	
	private void addReactorInfusion(){
		ReactorInfuseRecipes.inst.registerRecipe(new ItemStack(Items.coal), 100, carbon14, 1);
		ReactorInfuseRecipes.inst.registerRecipe(new ItemStack(Items.iron_ingot), 100, rad_fe, 1);
		ReactorInfuseRecipes.inst.registerRecipe(new ItemStack(Items.diamond), 100, c14_cry, 1);
		ReactorInfuseRecipes.inst.registerRecipe(new ItemStack(Items.stick), 50, mut_stck, 1);
	}
	
	private void addHazmatRecycling(){
		RecyclingRecipes.inst.registerRecipe(cell_dep,
				new WeightedItemStackResult(cell_emp, 1),
				new WeightedItemStackResult(nucl_goo, .8),
				new WeightedItemStackResult(ur_tnd, .3),
				new WeightedItemStackResult(pu_tnd, .05)
			);
		RecyclingRecipes.inst.registerRecipe(nucl_goo,
				new WeightedItemStackResult(ur_tnd, .8),
				new WeightedItemStackResult(pb_tnd, .3),
				new WeightedItemStackResult(fe_tnd, .05)
			);
	}

	private void registerItems(){
		GameRegistry.registerItem(itemCell, "itemCell");
		GameRegistry.registerItem(itemComponent, "itemComponent");
		GameRegistry.registerItem(itemNuclear, "itemNuclear");
	}
	
	private void registerBlocks(){
		GameRegistry.registerBlock(blockMachine, ItemUreBlock.class, "blockMachine");
		GameRegistry.registerBlock(blockOre, ItemUreBlock.class, "blockOre");
		GameRegistry.registerBlock(blockD2O, "blockHeavywater");
	}
	
	private void registerTEs(){
		GameRegistry.registerTileEntity(TileEntityReactorBasic.class, "UraniumRE:reactorOne");
		GameRegistry.registerTileEntity(TileEntityReactorSteam.class, "UraniumRE:reactorTwo");
		GameRegistry.registerTileEntity(TileEntityUraniumProcessor.class, "UraniumRE:uraniumCleaner");
		GameRegistry.registerTileEntity(TileEntityHMR.class, "UraniumRE:hazmatRecycler");
		GameRegistry.registerTileEntity(TileEntityDeutExtractor.class, "UraniumRE:deutExtractor");
		GameRegistry.registerTileEntity(TileEntityPlasmafier.class, "UraniumRE:plasmaHeater");
		GameRegistry.registerTileEntity(TileEntityWaterPump.class, "UraniumRE:waterPump");
		GameRegistry.registerTileEntity(TileEntityHandle.class, "UraniumRE:ironHandle");
		GameRegistry.registerTileEntity(TileEntityPipe.class, "UraniumRE:fluidPipe");
		GameRegistry.registerTileEntity(TileEntitySteamTurbine.class, "UraniumRE:turbineOne");
		GameRegistry.registerTileEntity(TileEntityAdvTurbine.class, "UraniumRE:turbineTwo");
		if(rfAPI){
			GameRegistry.registerTileEntity(TileEntityRFDriver.class, "UraniumRE:rfIn");
			GameRegistry.registerTileEntity(TileEntityRFModulator.class, "UraniumRE:rfOut");
		}
	}
	
	private void addEventHandler(Object hndl){
		MinecraftForge.EVENT_BUS.register(hndl);
		FMLCommonHandler.instance().bus().register(hndl);
	}
	
	private void registerMisc(){
		GameRegistry.registerFuelHandler(fuelHandler);
		NetworkRegistry.INSTANCE.registerGuiHandler(inst, proxy);
		
		addEventHandler(HandlerPipeNet.inst);
		addEventHandler(HandlerPlasma.inst);
		
		if(FluidRegistry.isFluidRegistered(heavyWater))
			heavyWater=FluidRegistry.getFluid(heavyWater.getName());
		else
			FluidRegistry.registerFluid(heavyWater);
		FluidContainerRegistry.registerFluidContainer(heavyWater, heavyw_b, FluidContainerRegistry.EMPTY_BUCKET);
		
		if(FluidRegistry.isFluidRegistered(steam))
			steam=FluidRegistry.getFluid(steam.getName());
		else
			FluidRegistry.registerFluid(steam);
		FluidContainerRegistry.registerFluidContainer(steam, cell_stm, cell_emp);
	}
	
	private void registerCraftRecipes(){
		//All tiny dust to bigger dust recipes
		for(String s:new String[]{"Iron", "Lead", "Uranium", "Plutonium"}){
			GameRegistry.addRecipe(new ShapedOreRecipe(OreDictionary.getOres("dust"+s).get(0),
					"...", "...", "...",
					'.', "dustTiny"+s));
		}
		
		//Reactors
		GameRegistry.addRecipe(new ShapedOreRecipe(reactor1,
				"lol", "o o", "lol",
				'l', "ingotLead",
				'o', Blocks.obsidian));
		GameRegistry.addRecipe(new ShapedOreRecipe(reactor2,
				"ouo", "ere", "ouo",
				'u', "ingotUranium",
				'e', fl_pipe,
				'r', reactor1,
				'o', Blocks.obsidian));
		
		//Machines
		GameRegistry.addRecipe(new ShapedOreRecipe(ur_clean,
				"lcl", "dhd", "lal",
				'l', "ingotLead",
				'a', Items.paper,
				'd', Items.cauldron,
				'h', Blocks.hopper,
				'c', grnd_cmp));
		GameRegistry.addRecipe(new ShapedOreRecipe(hazmat_r,
				"lll", "lrl", "*g*",
				'l', "ingotLead",
				'r', Items.redstone,
				'g', "gearRadiating",
				'*', "ingotRadiatingIron"));
		GameRegistry.addRecipe(new ShapedOreRecipe(deut_ext,
				"gag", "cdc", "geg",
				'c', "itemCoil",
				'a', Items.paper,
				'g', "gearRadiating",
				'd', Items.cauldron,
				'e', fl_pipe));
		GameRegistry.addRecipe(new ShapedOreRecipe(plasma_h,
				"pgp", "rtr", "pcp",
				'c', "itemCoil",
				'p', "ingotPlutonium",
				'g', "gearRadiating",
				'r', "gemCarbon14",
				't', turbine2));
		GameRegistry.addRecipe(new ShapedOreRecipe(waterpmp,
				"igi", "dpd", "ibi",
				'i', "ingotIron",
				'd', Items.cauldron,
				'p', Blocks.piston,
				'g', "gearRadiating",
				'b', Items.bucket));
		GameRegistry.addRecipe(new ShapedOreRecipe(resize(fl_pipe, 16),
				"lll", "bsb", "lll",
				'l', "ingotLead",
				'b', Items.bucket,
				's', "blockGlass"));
		GameRegistry.addRecipe(new ShapedOreRecipe(handle,
				"ri", "i ", "g ",
				'i', "ingotIron",
				'r', "gemCarbon14",
				'g', "gearRadiating").setMirrored(true));
		GameRegistry.addRecipe(new ShapedOreRecipe(turbine1,
				"iei", "ppp", "igi",
				'i', "ingotIron",
				'e', fl_pipe,
				'p', Blocks.piston,
				'g', "gearRadiating"));
		GameRegistry.addRecipe(new ShapedOreRecipe(turbine2,
				"umu", "dtd", "ugu",
				'u', "ingotUranium",
				'm', waterpmp,
				't', turbine1,
				'd', Items.cauldron,
				'g', "gearRadiating"));
		if(rfAPI){
		GameRegistry.addRecipe(new ShapedOreRecipe(rf_input,
				"iri", "cpc", "igi",
				'i', "ingotIron",
				'r', Items.redstone,
				'p', Blocks.piston,
				'c', Items.comparator,
				'g', "gearRadiating"));
		GameRegistry.addRecipe(new ShapedOreRecipe(rf_outp,
				"igi", "cpc", "iri",
				'i', "ingotIron",
				'r', Items.redstone,
				'p', Blocks.piston,
				'c', Items.comparator,
				'g', "gearRadiating"));
		}
		//Cells
		GameRegistry.addRecipe(new ShapedOreRecipe(resize(cell_emp, 16),
				" l ", "lsl", " l ",
				'l', "ingotLead",
				's', "blockGlass"));
		GameRegistry.addRecipe(new ShapelessOreRecipe(cell_ur,
				cell_emp, "ingotUranium"));
		
		//Materials
		GameRegistry.addRecipe(new ShapedOreRecipe(rad_gear,
				" i ", "i*i", " i ",
				'i', "ingotIron",
				'*', "ingotRadiatingIron"));
		GameRegistry.addRecipe(new ShapedOreRecipe(grnd_cmp,
				"lfl", "fgf", "lfl",
				'l', "ingotLead",
				'f', Items.flint,
				'g', "gearRadiating"));
		GameRegistry.addRecipe(new ShapedOreRecipe(coil,
				"---", "- -", "---",
				'-', "ingotGold"));
	}
	
	private static ItemStack resize(ItemStack s, int i){
		ItemStack ret=s.copy();
		ret.stackSize=i;
		return ret;
	}
	
	private void registerFurnaceRecipes(){
		FurnaceRecipes.smelting().func_151394_a(new ItemStack(blockOre, 1, 0), ur_ingot, 1.0f);
		FurnaceRecipes.smelting().func_151394_a(pb_dust, pb_ingot, 1.0f);
		FurnaceRecipes.smelting().func_151394_a(ur_dust, ur_ingot, 1.0f);
		FurnaceRecipes.smelting().func_151394_a(pu_dust, pu_ingot, 1.0f);
		FurnaceRecipes.smelting().func_151394_a(fe_dust, new ItemStack(Items.iron_ingot), 1.0f);
	}
	
	private void registerOreDict(){
		OreDictionary.registerOre("oreUranium", ur_ore);
		OreDictionary.registerOre("oreLead", pb_ore);
		OreDictionary.registerOre("gearRadiating", rad_gear);
		OreDictionary.registerOre("itemCoil", coil);
		OreDictionary.registerOre("ingotLead", pb_ingot);
		OreDictionary.registerOre("gemCarbon14", c14_cry);
		OreDictionary.registerOre("dustUranium", ur_dust);
		OreDictionary.registerOre("dustTinyUranium", ur_tnd);
		OreDictionary.registerOre("dustLead", pb_dust);
		OreDictionary.registerOre("dustTinyLead", pb_tnd);
		OreDictionary.registerOre("dustIron", fe_dust);
		OreDictionary.registerOre("dustTinyIron", fe_tnd);
		OreDictionary.registerOre("ingotUranium", ur_ingot);
		OreDictionary.registerOre("ingotRadiatingIron", rad_fe);
		OreDictionary.registerOre("fuelCarbon14", carbon14);
		OreDictionary.registerOre("dustPlutonium", pu_dust);
		OreDictionary.registerOre("dustTinyPlutonium", pu_tnd);
		OreDictionary.registerOre("ingotPlutonium", pu_ingot);
		
	}
}
