package com.mjr.extraplanets.inventory.machines;

import micdoodle8.mods.galacticraft.api.item.IItemElectric;
import micdoodle8.mods.galacticraft.core.energy.EnergyUtil;
import micdoodle8.mods.galacticraft.core.inventory.SlotSpecific;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;

import com.mjr.extraplanets.items.ExtraPlanets_Items;
import com.mjr.extraplanets.tileEntities.machines.TileEntityBasicDecrystallizer;

public class ContainerBasicDecrystallizer extends Container {
	private final TileEntityBasicDecrystallizer tileEntity;

	public ContainerBasicDecrystallizer(InventoryPlayer par1InventoryPlayer, TileEntityBasicDecrystallizer tileEntity, EntityPlayer player) {
		this.tileEntity = tileEntity;

		// Electric Input Slot
		this.addSlotToContainer(new SlotSpecific(tileEntity, 0, 7, 7, IItemElectric.class));

		// Input Slot
		this.addSlotToContainer(new Slot(tileEntity, 1, 82, 35));

		// Output Tank Slot
		this.addSlotToContainer(new Slot(tileEntity, 2, 153, 7));
		int var3;

		for (var3 = 0; var3 < 3; ++var3) {
			for (int var4 = 0; var4 < 9; ++var4) {
				this.addSlotToContainer(new Slot(par1InventoryPlayer, var4 + var3 * 9 + 9, 8 + var4 * 18, 104 + var3 * 18 - 18));
			}
		}

		for (var3 = 0; var3 < 9; ++var3) {
			this.addSlotToContainer(new Slot(par1InventoryPlayer, var3, 8 + var3 * 18, 144));
		}

		tileEntity.openInventory(player);
	}

	@Override
	public void onContainerClosed(EntityPlayer entityplayer) {
		super.onContainerClosed(entityplayer);
		this.tileEntity.closeInventory(entityplayer);
	}

	@Override
	public boolean canInteractWith(EntityPlayer entityPlayer) {
		return this.tileEntity.isUsableByPlayer(entityPlayer);
	}

	/**
	 * Called to transfer a stack from one inventory to the other eg. when shift clicking.
	 */
	@Override
	public ItemStack transferStackInSlot(EntityPlayer entityPlayer, int par1) {
		ItemStack var2 = ItemStack.EMPTY;
		final Slot slot = this.inventorySlots.get(par1);

		if (slot != null && slot.getHasStack()) {
			final ItemStack var4 = slot.getStack();
			var2 = var4.copy();

			if (par1 < 3) {
				if (!this.mergeItemStack(var4, 3, 39, true)) {
					return ItemStack.EMPTY;
				}

				if (par1 == 2) {
					slot.onSlotChange(var4, var2);
				}
			} else {
				if (EnergyUtil.isElectricItem(var4.getItem())) {
					if (!this.mergeItemStack(var4, 0, 1, false)) {
						return ItemStack.EMPTY;
					}
				} else if (var4.getItem() == ExtraPlanets_Items.IODIDE_SALT) {
					if (!this.mergeItemStack(var4, 1, 2, false)) {
						return ItemStack.EMPTY;
					}
				} else {
					if (par1 < 30) {
						if (!this.mergeItemStack(var4, 30, 39, false)) {
							return ItemStack.EMPTY;
						}
					} else if (!this.mergeItemStack(var4, 3, 30, false)) {
						return ItemStack.EMPTY;
					}
				}
			}

			if (var4.getCount() == 0) {
				slot.putStack(ItemStack.EMPTY);
			} else {
				slot.onSlotChanged();
			}

			if (var4.getCount() == var2.getCount()) {
				return ItemStack.EMPTY;
			}

			slot.onTake(entityPlayer, var4);
		}

		return var2;
	}
}