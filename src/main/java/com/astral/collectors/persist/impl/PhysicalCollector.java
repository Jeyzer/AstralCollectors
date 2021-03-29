package com.astral.collectors.persist.impl;

import com.astral.abstraction.persist.Collector;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import org.bukkit.Location;

@Getter @RequiredArgsConstructor
public class PhysicalCollector extends Collector {

    private final Location location;

}
