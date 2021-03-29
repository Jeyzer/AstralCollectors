package com.astral.abstraction.interfacing.menus;

import com.astral.abstraction.interfacing.Buildable;
import com.astral.abstraction.interfacing.Nominable;
import org.bukkit.inventory.Inventory;

public interface Menu extends Nominable, Buildable<Inventory> {

    String getTitle();

    default int getSize() { return 1; }

    Button[] getButtons();

    Button getButton(int i);

    void setFirstEmpty(Button button);

    void setEmptyRange(int start, int end, Button button);

}
