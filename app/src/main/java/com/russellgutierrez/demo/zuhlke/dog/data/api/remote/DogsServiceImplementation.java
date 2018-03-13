package com.russellgutierrez.demo.zuhlke.dog.data.api.remote;


import com.russellgutierrez.demo.zuhlke.dog.data.model.Breed;

import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

import io.reactivex.Single;

@Singleton
public class DogsServiceImplementation implements DogsService {

    private final DogsEndpoint mDogsEndpoint;

    @Inject
    public DogsServiceImplementation(DogsEndpoint dogsEndpoint) {
        mDogsEndpoint = dogsEndpoint;
    }

    @Override
    public Single<List<Breed>> listAllBreeds() {
        return mDogsEndpoint.listAllBreeds()
                .map(listAllBreedsResponse -> listAllBreedsResponse.message())
                .flatMapIterable(breedListMap -> breedListMap.entrySet())
                .map(breedKeyValuePair -> Breed.builder().name(breedKeyValuePair.getKey())
                        .subBreeds(Breed.from(breedKeyValuePair.getValue(), breedKeyValuePair.getKey()))
                        .build())
                .toList();
    }

    @Override
    public Single<List<String>> listImages(String breedName) {
        return mDogsEndpoint.listImages(breedName)
                .map(listImagesForBreedResponse -> listImagesForBreedResponse.message())
                .flatMapIterable(strings -> strings)
                .toList();
    }

    @Override
    public Single<List<String>> listImages(String breedName, String subBreedName) {
        return mDogsEndpoint.listImages(breedName, subBreedName)
                .map(listImagesForBreedResponse -> listImagesForBreedResponse.message())
                .flatMapIterable(strings -> strings)
                .toList();
    }
}
