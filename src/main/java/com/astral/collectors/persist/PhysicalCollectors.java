package com.astral.collectors.persist;

import com.astral.abstraction.persist.Collector;
import com.astral.collectors.persist.impl.PhysicalCollector;
import com.astral.collectors.persist.serialization.ChunkData;
import com.google.common.collect.Maps;

import lombok.Getter;
import lombok.Setter;
import org.apache.commons.lang.Validate;
import org.bukkit.Chunk;
import org.bukkit.Location;

import java.util.Collections;
import java.util.Map;
import java.util.Optional;

public class PhysicalCollectors {

    private @Setter @Getter boolean valid = true;

    private final Map<ChunkData, PhysicalCollector> collectors = Collections.synchronizedMap(Maps.newHashMap());

    public void createCollector(Location location) {
        Validate.notNull(location, "Location param in collector creation method cannot be null!");
        collectors.put(ChunkData.direct(location.getChunk()), new PhysicalCollector(location));
    }

    public void removeCollector(Location location) {
        Validate.notNull(location, "Location param in collector deletion method cannot be null!");
        Chunk chunk = location.getChunk();
        collectors.remove(ChunkData.values(chunk.getWorld().getName(), chunk.getX(), chunk.getZ()));
    }

    public Optional<PhysicalCollector> getCollector(ChunkData chunkData)  { return Optional.ofNullable(collectors.get(chunkData)); }
    public Optional<PhysicalCollector> getCollector(Location location)    { return getCollector(location.getChunk());              }
    public Optional<PhysicalCollector> getCollector(Chunk chunk)          { return getCollector(ChunkData.direct(chunk));          }

}
