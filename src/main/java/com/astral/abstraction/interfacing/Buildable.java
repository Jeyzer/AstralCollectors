package com.astral.abstraction.interfacing;

public interface Buildable<E> {

    void build(boolean scratch);
    boolean isBuilt();

    E getBuilt();

}
