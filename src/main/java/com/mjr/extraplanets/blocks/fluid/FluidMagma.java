package com.mjr.extraplanets.blocks.fluid;

import java.util.Random;

import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.BlockPos;
import net.minecraft.util.DamageSource;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.fluids.BlockFluidClassic;
import net.minecraftforge.fluids.IFluidBlock;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class FluidMagma extends BlockFluidClassic implements IFluidBlock
{
    public FluidMagma(String assetName)
    {
		super(ExtraPlanets_Fluids.magma_fluid, Material.lava);
        this.setQuantaPerBlock(9);
        this.setLightLevel(0.1F);
        this.needsRandomTick = true;
        this.setUnlocalizedName(assetName);
    }

    @Override
    public void onEntityCollidedWithBlock(World worldIn, BlockPos pos, Entity entityIn){
		if ((entityIn instanceof EntityLivingBase)) {
			if (worldIn.getTotalWorldTime() % 8 == 0 && entityIn instanceof EntityLivingBase && !((EntityLivingBase) entityIn).isEntityUndead()) {
				((EntityLivingBase) entityIn).attackEntityFrom(DamageSource.lava, 4.0F);
				((EntityLivingBase) entityIn).setFire(15);
			}
		}
	}

    @Override
    public boolean canDisplace(IBlockAccess world, BlockPos pos)
    {
        if (world.getBlockState(pos).getBlock().getMaterial().isLiquid())
        {
            return false;
        }
        return super.canDisplace(world, pos);
    }

    @Override
    public boolean displaceIfPossible(World world, BlockPos pos)
    {
        if (world.getBlockState(pos).getBlock().getMaterial().isLiquid())
        {
            return false;
        }
        return super.displaceIfPossible(world, pos);
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void randomDisplayTick(World worldIn, BlockPos pos, IBlockState state, Random rand)
    {
        super.randomDisplayTick(worldIn, pos, state, rand);

        if (rand.nextInt(1200) == 0)
        {
            worldIn.playSound(pos.getX() + 0.5F, pos.getY() + 0.5F, pos.getZ() + 0.5F, "liquid.lava", rand.nextFloat() * 0.25F + 0.75F, 0.00001F + rand.nextFloat() * 0.5F, false);
        }
        if (rand.nextInt(10) == 0)
        {
            if (World.doesBlockHaveSolidTopSurface(worldIn, pos.down()) && !worldIn.getBlockState(pos.down(2)).getBlock().getMaterial().blocksMovement())
            {
               //GalacticraftPlanets.spawnParticle("", new Vector3(pos.getX() + rand.nextFloat(), pos.getY() - 1.05D, pos.getZ() + rand.nextFloat()), new Vector3(0, 0, 0));
            }
        }
    }
}