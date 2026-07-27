package net.tnemc.item.component.impl;
/*
 * The New Item Library
 * Copyright (C) 2022 - 2026 Daniel "creatorfromhell" Vidmar
 *
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public
 * License as published by the Free Software Foundation; either
 * version 3 of the License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with this program; if not, write to the Free Software Foundation,
 * Inc., 51 Franklin Street, Fifth Floor, Boston, MA  02110-1301, USA.
 */

import net.tnemc.item.AbstractItemStack;
import net.tnemc.item.JSONHelper;
import net.tnemc.item.component.SerialComponent;
import net.tnemc.item.platform.ItemPlatform;
import org.json.simple.JSONObject;

import java.util.Objects;

/**
 * AttackRangeComponent
 * @see <a href="https://minecraft.wiki/w/Data_component_format#cooking_fuel">Reference</a>
 * @author creatorfromhell
 * @since 0.2.0.0
 */
public abstract class CookingFuelComponent<I extends AbstractItemStack<T>, T> implements SerialComponent<I, T> {

    protected String burnTime = "minecraft:cooking/time_coal";
    protected String speedMultiplier = "minecraft:cooking/speed_default";

    public CookingFuelComponent() {

    }

    public CookingFuelComponent(final String burnTime, final String speedMultiplier) {

        this.burnTime = burnTime;
        this.speedMultiplier = speedMultiplier;
    }

    /**
     * @return the type of component this is.
     *
     * @since 0.2.0.0
     */
    @Override
    public String identifier() {

        return "cooking_fuel";
    }

    /**
     * Converts the {@link SerialComponent} to a JSON object.
     *
     * @return The JSONObject representing this {@link SerialComponent}.
     *
     * @since 0.2.0.0
     */
    @Override
    public JSONObject toJSON() {

        final JSONObject json = new JSONObject();

        json.put("burnTime", burnTime);
        json.put("speedMultiplier", speedMultiplier);

        return json;
    }

    /**
     * Reads JSON data and converts it back to a {@link SerialComponent} object.
     *
     * @param json The JSONHelper instance of the json data.
     *
     * @since 0.2.0.0
     */
    @Override
    public void readJSON(final JSONHelper json, final ItemPlatform<I, T, ?> platform) {

        this.burnTime = json.getString("burnTime");
        this.speedMultiplier = json.getString("speedMultiplier");
    }

    /**
     * Used to determine if some data is equal to this data. This means that it has to be an exact
     * copy of this data. For instance, book copies will return false when compared to the original.
     *
     * @param component The component to compare.
     *
     * @return True if similar, otherwise false.
     *
     * @since 0.2.0.0
     */
    @Override
    public boolean similar(final SerialComponent<?, ?> component) {

        if(!(component instanceof final CookingFuelComponent<?, ?> other)) return false;

        return burnTime.equals(other.burnTime) && speedMultiplier.equals(other.speedMultiplier);
    }

    @Override
    public int hashCode() {

        return Objects.hash(burnTime, speedMultiplier);
    }

    public String burnTime() {

        return burnTime;
    }

    public void burnTime(final String burnTime) {

        this.burnTime = burnTime;
    }

    public String speedMultiplier() {

        return speedMultiplier;
    }

    public void speedMultiplier(final String speedMultiplier) {

        this.speedMultiplier = speedMultiplier;
    }
}