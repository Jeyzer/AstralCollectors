package com.astral.collectors.persist.serialization;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import org.apache.commons.lang3.Validate;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

import java.util.Objects;

@RequiredArgsConstructor(access = AccessLevel.PRIVATE) @Getter
public class ItemData {
    
    private final Material material;
    private final Short subId;

    @Override
    public int hashCode() {
        return Objects.hash(material, subId);
    }

    @Override
    public boolean equals(Object object) {
        if (object == this) return true;
        if (!(object instanceof ItemData)) return false;
        ItemData itemData = (ItemData) object;
        return itemData.material == material && itemData.subId.shortValue() == subId.shortValue();
    }

    @Override
    public String toString() {
        return material.toString() + ", " + subId;
    }

    public static ItemData values(Material material, short subId) {
        return new ItemData(material, subId);
    }

    public static ItemData direct(ItemStack itemStack) {
        Validate.notNull(itemStack, "ItemStack in ItemData static method of creation cannot be null!");
        return new ItemData(itemStack.getType(), itemStack.getDurability());
    }

}
