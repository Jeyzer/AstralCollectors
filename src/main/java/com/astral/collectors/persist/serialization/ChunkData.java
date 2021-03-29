package com.astral.collectors.persist.serialization;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang.Validate;
import org.bukkit.Chunk;

import java.util.Objects;

@RequiredArgsConstructor(access = AccessLevel.PRIVATE) @Getter
public class ChunkData {

    private final String world;
    private final int x, z;

    @Override
    public int hashCode() {
        return Objects.hash(world, x, z);
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this) return true;
        if (!(obj instanceof ChunkData)) return false;
        ChunkData chunkData = (ChunkData) obj;
        return chunkData.world.equals(world) && chunkData.x == x && chunkData.z == z;
    }

    public static ChunkData direct(Chunk chunk) {
        Validate.notNull(chunk, "Cannot get data from null chunk");
        return new ChunkData(chunk.getWorld().getName(), chunk.getX(), chunk.getZ());
    }

    public static ChunkData values(String world, int x, int z) {
        Validate.notEmpty(world, "World name cannot be null");
        return new ChunkData(world, x, z);
    }

}
