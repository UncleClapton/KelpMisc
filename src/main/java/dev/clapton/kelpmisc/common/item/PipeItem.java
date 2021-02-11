package dev.clapton.kelpmisc.common.item;

import dev.clapton.kelpmisc.server.ServerEffectHelper;
import dev.clapton.kelpmisc.util.KelpTags;
import net.minecraft.block.BlockState;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.item.*;
import net.minecraft.util.*;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;

import javax.annotation.Nonnull;
import javax.annotation.ParametersAreNonnullByDefault;

public class PipeItem extends Item {
    public static final String USE_TAG = "uses";
    public static final int FUEL_WORTH = 100;
    public static final int MAX_CHARGE = 19;
    public static final int TICKS_PER_CHARGE = 4;

    public PipeItem() {
        super(new Properties()
                .maxStackSize(1)
                .group(KelpItemsGroup.getInstance())
                .isImmuneToFire()
                .rarity(Rarity.EPIC)
        );
    }

    @Nonnull
    @ParametersAreNonnullByDefault
    @Override
    public ActionResult<ItemStack> onItemRightClick(World worldIn, PlayerEntity playerIn, Hand handIn) {
        ItemStack item = playerIn.getHeldItem(handIn);

        playerIn.setActiveHand(handIn);
        return ActionResult.resultSuccess(item);
    }


    @ParametersAreNonnullByDefault
    @Override
    public void onPlayerStoppedUsing(ItemStack stackIn, World worldIn, LivingEntity entityIn, int timeLeft) {
        // Only run on server and only if the entity is a player.
        // This is required because the effects of the pipe's use are applied via server-only world methods
        if (!worldIn.isRemote) {
            if (!(entityIn instanceof ServerPlayerEntity)) {
                return;
            }

            ServerWorld world = (ServerWorld) worldIn;
            ServerPlayerEntity player = (ServerPlayerEntity) entityIn;

            int uses = getUsages(stackIn);
            int chargeLevel = 1 + (Math.min(MAX_CHARGE * TICKS_PER_CHARGE, player.getItemInUseMaxCount()) / TICKS_PER_CHARGE);
            int nextUses = getUsagesAfterUse(stackIn, chargeLevel);

            if (chargeLevel == 0) {
                return;
            }

            if ((uses == FUEL_WORTH || nextUses > FUEL_WORTH)) {
                if (attemptRestock(stackIn, world, player)) {
                    // Update usages. otherwise we commit the original uses which will overflow.
                    nextUses = getUsagesAfterUse(stackIn, chargeLevel);
                } else {
                    return;
                }
            }

            ServerEffectHelper.usePipe(world, player, chargeLevel);


            int ed = (Math.max(chargeLevel, 5) / 5) * 4;
            player.sendMessage(new StringTextComponent(
                    "u: " + uses
                            + " | cl: " + chargeLevel
                            + " | nu: " + nextUses
                            + " | mtx: " + MAX_CHARGE * TICKS_PER_CHARGE
                            + " | iiumc: " + player.getItemInUseMaxCount()
                            + " | ed: " + ed * 20 + " (" + ed + "s)"
            ), Util.DUMMY_UUID);

            if (!player.isCreative()) {
                putUsages(stackIn, nextUses);
            }
        }
    }

    private int getUsagesAfterUse(ItemStack stack, int chargeLevel) {
        return getUsages(stack) + (int) Math.ceil(chargeLevel / 2f);
    }

    private boolean attemptConsumeFuel(ServerPlayerEntity player) {
        if (player.inventory.hasTag(KelpTags.PIPE_FUEL)) {
            for (Item taggedItem : KelpTags.PIPE_FUEL.getAllElements()) {
                ItemStack taggedStack = new ItemStack(taggedItem);
                if (player.inventory.hasItemStack(taggedStack)) {
                    int matchedSlot = player.inventory.getSlotFor(taggedStack);
                    int matchedStackCount = player.inventory.getStackInSlot(matchedSlot).getCount();
                    if (matchedStackCount > 1) {
                        ItemStack newStack = player.inventory.decrStackSize(matchedSlot, matchedStackCount - 1);
                        player.inventory.setInventorySlotContents(matchedSlot, newStack);
                    } else {
                        player.inventory.setInventorySlotContents(matchedSlot, ItemStack.EMPTY);
                    }
                    return true;
                }
            }
        }

        return false;
    }

    private boolean attemptRestock(ItemStack stack, ServerWorld world, ServerPlayerEntity player) {
        if (player.isCreative() || attemptConsumeFuel(player)) {
            int uses = getUsages(stack);
            // Allowing stock to go into negatives is intentional.
            // This is to ensure the user doesn't lose any fuel when filling their pipe.
            int newUses = (-FUEL_WORTH) + uses;
            putUsages(stack, newUses);

            ServerEffectHelper.playPipeRestockSound(world, player);
            return true;
        }

        return false;
    }

    private int getUsages(ItemStack stack) {
        return stack.getOrCreateTag().getInt(USE_TAG);
    }

    private void putUsages(ItemStack stack, int usages) {
        stack.getOrCreateTag().putInt(USE_TAG, usages);
    }

    @Nonnull
    @ParametersAreNonnullByDefault
    @Override
    public ItemStack onItemUseFinish(ItemStack stack, World world, LivingEntity entity) {
        this.onPlayerStoppedUsing(stack, world, entity, 0);
        return stack;
    }

    @ParametersAreNonnullByDefault
    @Override
    public int getUseDuration(ItemStack stack) {
        return MAX_CHARGE * TICKS_PER_CHARGE * 2;
    }

    @Override
    public double getDurabilityForDisplay(ItemStack stack) {
        int uses = getUsages(stack);
        return (double) Math.max(uses, 0) / FUEL_WORTH;
    }

    @Override
    public boolean showDurabilityBar(ItemStack stack) {
        return getUsages(stack) > 0;
    }

    @Nonnull
    @ParametersAreNonnullByDefault
    @Override
    public UseAction getUseAction(ItemStack stack) {
        return UseAction.NONE;
    }

    @Override
    public boolean canContinueUsing(ItemStack oldStack, ItemStack newStack) {
        return true;
    }

    @ParametersAreNonnullByDefault
    @Override
    public boolean canPlayerBreakBlockWhileHolding(BlockState state, World worldIn, BlockPos pos, PlayerEntity player) {
        return false;
    }

    @ParametersAreNonnullByDefault
    @Override
    public boolean isRepairable(ItemStack stack) {
        return false;
    }

    @ParametersAreNonnullByDefault
    @Override
    public boolean isEnchantable(ItemStack stack) {
        return false;
    }
}
