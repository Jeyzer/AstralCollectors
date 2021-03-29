package com.astral.abstraction.persist;

import com.astral.collectors.persist.serialization.ItemData;
import com.google.common.collect.Maps;

import org.apache.commons.lang.mutable.MutableLong;
import org.bukkit.Material;

import java.util.Map;

public abstract class Collector {

    protected final Map<ItemData, MutableLong> collection = Maps.newConcurrentMap();

    public MutableLong getAmount(Material material, short subId) {
        return collection.get(ItemData.values(material, subId));
    }

    public MutableLong getAmount(ItemData itemData) {
        return collection.get(itemData);
    }

}
