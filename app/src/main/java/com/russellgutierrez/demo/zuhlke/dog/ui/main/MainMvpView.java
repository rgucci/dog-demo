package com.russellgutierrez.demo.zuhlke.dog.ui.main;

import com.russellgutierrez.demo.zuhlke.dog.data.model.Breed;
import com.russellgutierrez.demo.zuhlke.dog.ui.base.MvpView;

import java.util.List;

public interface MainMvpView extends MvpView {

    void showDogBreeds(List<Breed> breeds);
    void showSelectedDogBreed(Breed breed, String imageUrl);

    void showError(Throwable t);
}
