package com.russellgutierrez.demo.zuhlke.dog.data.model;

import android.support.annotation.Nullable;

import com.google.auto.value.AutoValue;
import com.google.common.collect.ImmutableList;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@AutoValue
public abstract class Breed {

    public abstract String name();

    @Nullable
    public abstract List<Breed> subBreeds();

    @Nullable
    public abstract String mainBreed();

    public boolean isSubBreed() {
        return mainBreed() != null;
    }

    public static Builder builder() {
        return new AutoValue_Breed.Builder();
    }

    @AutoValue.Builder
    public abstract static class Builder {
        public abstract Builder name(String name);

        public abstract Builder subBreeds(List<Breed> subBreeds);

        public abstract Builder mainBreed(String mainBreed);

        public abstract Breed build();
    }

    public static List<Breed> from(List<String> names, String mainBreed) {
        if (names.size() == 0) {
            return new ArrayList<>();
        }

        return names.stream()
                .map(name -> Breed.builder().name(name)
                        .mainBreed(mainBreed)
                        .subBreeds(Collections.EMPTY_LIST).build())
                .collect(Collectors.toList());
    }

    public static final Comparator<Breed> NameComparator  = (breed1, breed2) -> {
        String val1 = breed1.name();
        String val2 = breed2.name();

        if (breed1.isSubBreed()) {
            val1 = breed1.mainBreed() + "|" + breed1.name();
        }

        if (breed2.isSubBreed()) {
            val2 = breed2.mainBreed() + "|" + breed2.name();
        }
        return val1.compareTo(val2);
    };
}
