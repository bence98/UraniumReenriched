package csokicraft.forge17.ure.item;

import net.minecraft.item.ItemStack;
import static csokicraft.forge17.ure.UraniumRE.*;

public class UREItems{
	public static ItemStack rad_gear, grnd_cmp, coil, pb_ingot, c14_cry,
			ur_dust, ur_tnd, pb_dust, pb_tnd, fe_dust, fe_tnd,
			heavyw_b, pu_dust, pu_tnd, pu_ingot, nucl_goo, debugger, mut_stck,
			ur_ingot, rad_fe, carbon14, cell_emp, cell_ur, cell_dep,
			cell_deu, cell_pla, cell_stm, reactor1, reactor2, reactor3,
			ur_clean, hazmat_r, deut_ext, plasma_h, waterpmp, fl_pipe,
			handle, turbine1, turbine2, rf_input, rf_outp,
			eu_input, eu_outp, ur_ore, pb_ore;
	
	public static void init(){
		rad_gear=new ItemStack(itemComponent, 1,  0);
		grnd_cmp=new ItemStack(itemComponent, 1,  1);
		coil    =new ItemStack(itemComponent, 1,  2);
		pb_ingot=new ItemStack(itemComponent, 1,  3);
		c14_cry =new ItemStack(itemComponent, 1,  4);
		ur_dust =new ItemStack(itemComponent, 1,  5);
		ur_tnd  =new ItemStack(itemComponent, 1,  6);
		pb_dust =new ItemStack(itemComponent, 1,  7);
		pb_tnd  =new ItemStack(itemComponent, 1,  8);
		fe_dust =new ItemStack(itemComponent, 1,  9);
		fe_tnd  =new ItemStack(itemComponent, 1, 10);
		heavyw_b=new ItemStack(itemComponent, 1, 11);
		pu_dust =new ItemStack(itemComponent, 1, 12);
		pu_tnd  =new ItemStack(itemComponent, 1, 13);
		mut_stck=new ItemStack(itemComponent, 1, 14);
		nucl_goo=new ItemStack(itemComponent, 1, 15);
		debugger=new ItemStack(itemComponent, 1, 16);
		
		ur_ingot=new ItemStack(itemNuclear, 1, 0);
		rad_fe  =new ItemStack(itemNuclear, 1, 1);
		carbon14=new ItemStack(itemNuclear, 1, 2);
		pu_ingot=new ItemStack(itemNuclear, 1, 3);
		
		cell_emp=new ItemStack(itemCell, 1, 0);
		cell_ur =new ItemStack(itemCell, 1, 1);
		cell_dep=new ItemStack(itemCell, 1, 2);
		cell_deu=new ItemStack(itemCell, 1, 3);
		cell_pla=new ItemStack(itemCell, 1, 4);
		cell_stm=new ItemStack(itemCell, 1, 5);
		
		reactor1=new ItemStack(blockMachine, 1,  0);
		reactor2=new ItemStack(blockMachine, 1,  1);
		reactor3=new ItemStack(blockMachine, 1,  2);
		ur_clean=new ItemStack(blockMachine, 1,  3);
		hazmat_r=new ItemStack(blockMachine, 1,  4);
		deut_ext=new ItemStack(blockMachine, 1,  5);
		plasma_h=new ItemStack(blockMachine, 1,  6);
		waterpmp=new ItemStack(blockMachine, 1,  7);
		fl_pipe =new ItemStack(blockMachine, 1,  8);
		handle  =new ItemStack(blockMachine, 1,  9);
		turbine1=new ItemStack(blockMachine, 1, 10);
		turbine2=new ItemStack(blockMachine, 1, 11);
		rf_input=new ItemStack(blockMachine, 1, 12);
		rf_outp =new ItemStack(blockMachine, 1, 13);
		eu_input=new ItemStack(blockMachine, 1, 14);
		eu_outp =new ItemStack(blockMachine, 1, 15);
		
		ur_ore  =new ItemStack(blockOre, 1, 0);
		pb_ore  =new ItemStack(blockOre, 1, 1);
	}
}
