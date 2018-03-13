package com.russellgutierrez.demo.zuhlke.dog.data.model;

import com.google.common.collect.ImmutableList;

import org.junit.Test;

import java.util.List;

import static junit.framework.Assert.assertEquals;

public class BreedTest {

    @Test
    public void emptyStringListConversion() {
        ImmutableList<String> list = ImmutableList.of();
        List<Breed> result = Breed.from(list, null);
        assertEquals(0, result.size());
    }

    @Test
    public void notSubBreedStringListConversion() {
        ImmutableList<String> list = ImmutableList.of("breed1", "breed2", "breed3");
        List<Breed> result = Breed.from(list, null);
        assertEquals(3, result.size());
        for (int i = 0; i < list.size(); i++) {
            assertEquals(list.get(i), result.get(i).name());
        }
    }

    @Test
    public void subBreedStringListConversion() {
        ImmutableList<String> list = ImmutableList.of("breed1", "breed2", "breed3");
        final String mainBreed = "mainbreed";
        List<Breed> result = Breed.from(list, mainBreed);
        assertEquals(3, result.size());
        for (int i = 0; i < list.size(); i++) {
            Breed breed = result.get(i);
            assertEquals(list.get(i), breed.name());
            assertEquals(mainBreed, breed.mainBreed());
        }
    }
}