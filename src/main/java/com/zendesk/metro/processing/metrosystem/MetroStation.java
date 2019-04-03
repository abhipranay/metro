package com.zendesk.metro.processing.metrosystem;

import java.util.Objects;

public class MetroStation {
    private String name;
    private String originalName;

    public MetroStation(String name) {
        this.name = name.toLowerCase();
        this.originalName = name;
    }

    public String getOriginalName() {
        return originalName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        MetroStation that = (MetroStation) o;
        return Objects.equals(name, that.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name);
    }
}
