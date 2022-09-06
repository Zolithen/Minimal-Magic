package net.nki.minmagic.item;

import net.minecraft.world.item.Item;
import net.nki.minmagic.init.MMagicItems;

public class ItemAreaBinder extends Item {
    public ItemAreaBinder() {
        super(new Item.Properties().tab(MMagicItems.MMagicTab.instance));
    }
}
