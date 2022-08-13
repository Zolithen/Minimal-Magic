package net.nki.minmagic.block.rune.killer;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.NonNullList;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.ContainerHelper;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.WorldlyContainer;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.entity.RandomizableContainerBlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.AABB;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.util.FakePlayer;
import net.minecraftforge.common.util.FakePlayerFactory;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.energy.CapabilityEnergy;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.wrapper.SidedInvWrapper;
import net.nki.minmagic.MMagic;
import net.nki.minmagic.block.base.IRunetTile;
import net.nki.minmagic.block.base.container.TileRunetContainerBase;
import net.nki.minmagic.block.base.noncontainer.BlockRunetBase;
import net.nki.minmagic.block.rune.envy.TileRuneEnvy;
import net.nki.minmagic.block.rune.materialization.TileRuneMaterialization;
import net.nki.minmagic.init.MMagicBE;

import javax.annotation.Nullable;
import java.util.List;
import java.util.Random;
import java.util.stream.IntStream;

public class TileRuneKiller extends TileRunetContainerBase {

    public final double RANGE = 5;

    public static class RuneSupplier extends BlockRunetBase.RuneEntitySupplier implements BlockEntityType.BlockEntitySupplier {
        @Override
        public BlockEntity create(BlockPos p_155268_, BlockState p_155269_) {
            return new TileRuneKiller(p_155268_, p_155269_);
        }
    }
    private final LazyOptional<? extends IItemHandler>[] handlers = SidedInvWrapper.create(this, Direction.values());
    public TileRuneKiller(BlockPos p_155229_, BlockState p_155230_) {
        super(MMagicBE.RUNES.get("killer").get(), p_155229_, p_155230_);
    }

    @Override
    protected Component getDefaultName() {
        return new TextComponent("rune_killer");
    }

    @Override
    public Component getDisplayName() {
        return new TextComponent("Killer Rune");
    }

    @Override
    protected AbstractContainerMenu createMenu(int p_58627_, Inventory p_58628_) {
        return null;
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

        //if (it.getCount() > 0 ) {
        inv.insertItem(0, it, false);
    }
}
