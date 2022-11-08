package dev.larsq.moviedb.entity;

import java.util.Arrays;

public enum Category {
    TV_SHOW("TV Show"),
    MOVIE("Movie");

    private final String label;

    Category(String label) {
        this.label = label;
    }

    public static Category fromCode(String type) {
        return Arrays.stream(Category.values()).filter(category -> category.label.equals(type)).findFirst().orElse(null);
    }

    public String getLabel() {
        return label;
    }
}
