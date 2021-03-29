package com.astral.collectors.persist;

import com.astral.collectors.persist.impl.VirtualCollector;
import com.google.common.collect.Maps;
import com.massivecraft.factions.Faction;

import java.util.Map;
import java.util.Optional;

public class VirtualCollectors {

    private final Map<Short, VirtualCollector> collectors = Maps.newConcurrentMap();

    public Optional<VirtualCollector> getCollector(Faction faction) {
        return faction.isWilderness() || faction.isWarZone() || faction.isSafeZone() ? Optional.empty() : Optional.ofNullable(collectors.get(getId(faction)));
    }

    private short getId(Faction faction) {
        return Short.parseShort(faction.getId());
    }

    public void createCollector(Faction faction) {
        collectors.put(getId(faction), new VirtualCollector());
    }

}
