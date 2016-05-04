package com.detrav.proxies;

import com.detrav.DetravScannerMod;
import com.detrav.enums.DetravItemList;
import com.detrav.enums.DetravSimpleItems;
import com.detrav.gui.DetravGuiProPick;
import com.detrav.gui.DetravRepairToolGui;
import com.detrav.gui.containers.DetravPortableChargerContainer;
import com.detrav.gui.DetravPortableChargerGui;
import com.detrav.gui.containers.DetravRepairToolContainer;
import com.detrav.items.DetravMetaGeneratedTool01;
import cpw.mods.fml.common.network.IGuiHandler;
import gregtech.api.GregTech_API;
import gregtech.api.enums.*;
import gregtech.api.util.GT_ModHandler;
import gregtech.api.util.GT_OreDictUnificator;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

/**
 * Created by wital_000 on 19.03.2016.
 */
public class CommonProxy implements IGuiHandler {

    public void onLoad() {

    }

    public void onPostLoad() {
        long tBits = GT_ModHandler.RecipeBits.DO_NOT_CHECK_FOR_COLLISIONS | GT_ModHandler.RecipeBits.BUFFERED
                | GT_ModHandler.RecipeBits.ONLY_ADD_IF_RESULT_IS_NOT_NULL | GT_ModHandler.RecipeBits.NOT_REMOVABLE;
        for (Materials aMaterial : Materials.VALUES) {
            if ((aMaterial.mUnificatable) && (aMaterial.mMaterialInto == aMaterial)) {
                if (!aMaterial.contains(SubTag.NO_SMASHING)) {
                    GT_ModHandler.addCraftingRecipe(GT_OreDictUnificator.get(DetravSimpleItems.toolHeadProPick.get(aMaterial), null, 1L), tBits, new Object[]{"PI", "fh",
                            Character.valueOf('P'), OrePrefixes.plate.get(aMaterial), Character.valueOf('I'), OrePrefixes.ingot.get(aMaterial)});
                }
            }
        }
        ;
        GT_ModHandler.addCraftingRecipe(
                DetravMetaGeneratedTool01.INSTANCE.getToolWithStatsPlus(2,1,Materials._NULL,Materials._NULL,null,0),
                GT_ModHandler.RecipeBits.DISMANTLEABLE | GT_ModHandler.RecipeBits.NOT_REMOVABLE | GT_ModHandler.RecipeBits.REVERSIBLE | GT_ModHandler.RecipeBits.BUFFERED,
                new Object[]{"dwx", "hMc", "fsr", Character.valueOf('M'), OrePrefixes.ingot.get(Materials.Steel)});

        if (!GregTech_API.sSpecialFile.get(ConfigCategories.general, "DisableFlintTools", false)) {
            GT_ModHandler.addCraftingRecipe(DetravMetaGeneratedTool01.INSTANCE.getToolWithStats(0, 1, Materials.Flint, Materials.Wood, null), GT_ModHandler.RecipeBits.NOT_REMOVABLE, new Object[]{"FF", "SS", Character.valueOf('S'), OrePrefixes.stick.get(Materials.Wood), Character.valueOf('F'), new ItemStack(Items.flint, 1)});
        }

        //boiler recipes

        GT_ModHandler.addCraftingRecipe(DetravItemList.Solar_Boiler_Low.get(1L, new Object[0]), GT_ModHandler.RecipeBits.NOT_REMOVABLE | GT_ModHandler.RecipeBits.REVERSIBLE | GT_ModHandler.RecipeBits.BUFFERED, new Object[]{"GGG", "BBB", "PMP", Character.valueOf('M'), ItemList.Machine_Bronze_Boiler, Character.valueOf('P'), OrePrefixes.pipeSmall.get(Materials.Bronze), Character.valueOf('B'), OrePrefixes.dye.get(Materials.Black), Character.valueOf('G'), new ItemStack(Blocks.glass, 1)});
        GT_ModHandler.addCraftingRecipe(DetravItemList.Solar_Boiler_Medium.get(1L, new Object[0]), GT_ModHandler.RecipeBits.NOT_REMOVABLE | GT_ModHandler.RecipeBits.REVERSIBLE | GT_ModHandler.RecipeBits.BUFFERED, new Object[]{"GGG", "NNN", "PMP", Character.valueOf('M'), ItemList.Machine_Steel_Boiler, Character.valueOf('P'), OrePrefixes.pipeSmall.get(Materials.Steel), Character.valueOf('N'), OrePrefixes.dust.get(Materials.Nickel), Character.valueOf('G'), new ItemStack(Blocks.glass, 1)});
        GT_ModHandler.addCraftingRecipe(DetravItemList.Solar_Boiler_High.get(1L, new Object[0]), GT_ModHandler.RecipeBits.NOT_REMOVABLE | GT_ModHandler.RecipeBits.REVERSIBLE | GT_ModHandler.RecipeBits.BUFFERED, new Object[]{"GGG", "RRR", "PMP", Character.valueOf('M'), ItemList.Hull_HV, Character.valueOf('P'), OrePrefixes.pipeSmall.get(Materials.StainlessSteel), Character.valueOf('R'), OrePrefixes.dust.get(Materials.Rutile), Character.valueOf('G'), new ItemStack(Blocks.glass, 1)});

    }

    @Override
    public Object getServerGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) {
        switch (ID) {
            case DetravGuiProPick.GUI_ID:
                return null;
            case DetravPortableChargerGui.GUI_ID:
                return new DetravPortableChargerContainer(player.inventory,world,player.getCurrentEquippedItem());
            case DetravRepairToolGui.GUI_ID:
                return new DetravRepairToolContainer(player.inventory,world,player.getCurrentEquippedItem());
            default:
                return null;
        }
    }

    @Override
    public Object getClientGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) {
        switch (ID) {
            case DetravGuiProPick.GUI_ID:
                return new DetravGuiProPick();
            case DetravPortableChargerGui.GUI_ID:
                return new DetravPortableChargerGui(player.inventory,world,player.getCurrentEquippedItem());
            case DetravRepairToolGui.GUI_ID:
                return new DetravRepairToolGui(player.inventory,world,player.getCurrentEquippedItem());
            default:
                return null;
        }
    }


    public void openProPickGui()
    {
        //just Client code
    }

    public void openPortableChargerGui(EntityPlayer player)
    {
        player.openGui(DetravScannerMod.instance, DetravPortableChargerGui.GUI_ID,player.worldObj,(int)player.posX,(int)player.posY,(int)player.posZ);
    }

    public void openRepairToolGui(EntityPlayer player)
    {
        player.openGui(DetravScannerMod.instance, DetravRepairToolGui.GUI_ID,player.worldObj,(int)player.posX,(int)player.posY,(int)player.posZ);
    }

    public void onPreInit()
    {

    }
}
