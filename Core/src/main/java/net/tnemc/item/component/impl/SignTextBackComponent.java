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

import net.kyori.adventure.text.Component;
import net.tnemc.item.AbstractItemStack;
import net.tnemc.item.JSONHelper;
import net.tnemc.item.component.SerialComponent;
import net.tnemc.item.platform.ItemPlatform;
import org.json.simple.JSONObject;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;

/**
 * SignTextBackComponent
 *
 * @see <a href="https://minecraft.wiki/w/Data_component_format#sign_text_back">Reference</a>
 * @author creatorfromhell
 * @since 0.2.0.0
 */
public abstract class SignTextBackComponent<I extends AbstractItemStack<T>, T> implements SerialComponent<I, T> {

    protected final Component[] lines = new Component[4];
    protected int rgb;
    protected boolean glowing = false;

    public SignTextBackComponent() {

    }

    public SignTextBackComponent(final int rgb, final boolean glowing) {

        this.rgb = rgb;
        this.glowing = glowing;
    }

    /**
     * @return the type of component this is.
     *
     * @since 0.2.0.0
     */
    @Override
    public String identifier() {

        return "sign_text_back";
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

        //TODO: lines json
        json.put("rgb", rgb);
        json.put("glowing", glowing);

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

        //TODO: lines json
        this.rgb = json.getInteger("rgb");
        this.glowing = json.getBoolean("glowing");
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

        if(!(component instanceof final SignTextBackComponent<?, ?> other)) return false;

        return Arrays.equals(lines, other.lines) && rgb == other.rgb && glowing == other.glowing;
    }

    @Override
    public int hashCode() {

        return Objects.hash(Arrays.hashCode(lines), rgb, glowing);
    }

    public Component[] lines() {

        return lines;
    }

    public void lines(final List<Component> lines) {

        for (int i = 0; i < lines.size(); i++) {

            if (i >= 4) break;

            this.lines[i] = lines.get(i);
        }
    }

    public int rgb() {

        return rgb;
    }

    public void rgb(final int rgb) {

        this.rgb = rgb;
    }

    public boolean glowing() {

        return glowing;
    }

    public void glowing(final boolean glowing) {

        this.glowing = glowing;
    }
}