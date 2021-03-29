package com.astral.collectors.persist.serialization;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor @Getter
public class ResultData {

    private final ChunkData chunkData;
    private final ItemData itemData;
    private final byte amount;

}
