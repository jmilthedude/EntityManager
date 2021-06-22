package net.thedudemc.entitymanager.util;

import java.util.Locale;

public enum CountType {
    ALL,
    HOSTILE,
    PASSIVE,
    WATER,
    AMBIENT,
    FLYING,
    VILLAGER,
    ITEM,
    OTHER,
    PLAYER;

    public static CountType fromString(String value) {
        try {
            return CountType.valueOf(value.toUpperCase(Locale.ENGLISH));
        } catch (IllegalArgumentException e) {
            return null;
        }
    }
}
