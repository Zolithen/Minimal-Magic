package net.nki.minmagic.block.rune.killer;

import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.AABB;
import net.minecraftforge.common.util.FakePlayer;
import net.minecraftforge.common.util.FakePlayerFactory;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandler;
import net.nki.minmagic.block.base.container.TileRunetContainerBase;
import net.nki.minmagic.block.base.noncontainer.BlockRunetBase;
import net.nki.minmagic.init.MMagicBE;

import java.util.List;

public class TileRuneKiller extends TileRunetContainerBase {

    public final double RANGE = 5;

    public static class RuneSupplier extends BlockRunetBase.RuneEntitySupplier implements BlockEntityType.BlockEntitySupplier {
        @Override
        public BlockEntity create(BlockPos p_155268_, BlockState p_155269_) {
            return new TileRuneKiller(p_155268_, p_155269_);
        }
    }

    public TileRuneKiller(BlockPos p_155229_, BlockState p_155230_) {
        super(MMagicBE.RUNES.get("killer").get(), p_155229_, p_155230_, 1);
    }

    @Override
    public void runeAction() {

        IItemHandler inv = this.getLevel().getBlockEntity(this.worldPosition).getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, null).resolve().get();

        ItemStack it = inv.extractItem(0, 1, false);

        List<LivingEntity> list = this.getLevel().getEntitiesOfClass(LivingEntity.class,
                new AABB(this.worldPosition.offset(-RANGE, -RANGE, -RANGE),
                         this.worldPosition.offset(RANGE+1, RANGE+1, RANGE+1)), (p_184067_) -> {
            return true;
        });

        for (LivingEntity i : list) {
            if (!(i instanceof Player) && it.getDamageValue()+1<it.getMaxDamage()) {
                FakePlayer p = FakePlayerFactory.getMinecraft((ServerLevel) this.getLevel());
                p.setItemInHand(InteractionHand.MAIN_HAND, it); // TODO: Maybe conserve the item in main hand of the fake player????
                double dmg = 0;
                for (AttributeModifier a : it.getAttributeModifiers(EquipmentSlot.MAINHAND).get(Attributes.ATTACK_DAMAGE)) {
                    dmg += a.getAmount();
                }

                p.attack(i);
                i.hurt(DamageSource.playerAttack(p), (float) dmg);
                /*if (it.hurt(1, new Random(), null)) {
                    it.shrink(1);
                    it.setDamageValue(0);
                    break;
                }*/

            }
        }

        inv.insertItem(0, it, false);
    }
}
