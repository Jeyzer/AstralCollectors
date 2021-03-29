package com.astral.collectors.persist.impl;

import com.astral.abstraction.persist.Collector;
import com.astral.collectors.persist.serialization.ChunkData;

import com.google.common.collect.Sets;

import java.util.Set;

public class VirtualCollector extends Collector {

    private final Set<ChunkData> collectors = Sets.newHashSet();

    public void addToMap(String world, int x, int z) {
        collectors.add(ChunkData.values(world, x, z));
    }

    public Set<ChunkData> getChunks() {
        return Sets.newHashSet(collectors);
    }

}
