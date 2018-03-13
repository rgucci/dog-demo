package com.russellgutierrez.demo.zuhlke.dog.data.api.remote;

import com.russellgutierrez.demo.zuhlke.dog.data.model.Breed;

import java.util.List;

import io.reactivex.Single;

public interface DogsService {
    Single<List<Breed>> listAllBreeds();
    Single<List<String>> listImages(String breedName);
    Single<List<String>> listImages(String breedName, String subBreedName);
}
