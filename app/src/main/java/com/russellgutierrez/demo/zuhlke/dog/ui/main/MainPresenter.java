package com.russellgutierrez.demo.zuhlke.dog.ui.main;

import android.support.annotation.NonNull;

import com.russellgutierrez.demo.zuhlke.dog.data.api.remote.DogsService;
import com.russellgutierrez.demo.zuhlke.dog.data.model.Breed;
import com.russellgutierrez.demo.zuhlke.dog.injection.ConfigPersistent;
import com.russellgutierrez.demo.zuhlke.dog.ui.base.BasePresenter;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import javax.inject.Inject;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

@ConfigPersistent
public class MainPresenter extends BasePresenter<MainMvpView> {

    private final DogsService mDogsService;
    private final CompositeDisposable mDisposable = new CompositeDisposable();
    private List<Breed> mBreedList = Collections.emptyList();
    private int mSelectedBreed = 0;
    private String[] mImagesForSelectedBreed = {};
    private int mSelectedImage = 0;

    @Inject
    public MainPresenter(DogsService dogsService) {
        mDogsService = dogsService;
    }

    @Override
    public void detachView() {
        super.detachView();
        mDisposable.dispose();
    }

    public void loadBreedList() {
        checkViewAttached();
        mDisposable.add(
                mDogsService.listAllBreeds()
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribeOn(Schedulers.io())
                        .subscribe(this::processListOfBreeds, getMvpView()::showError));
    }

    private void processListOfBreeds(List<Breed> breeds) {
        List<Breed> temp = new ArrayList<>();
        temp.addAll(breeds);
        temp.addAll(expandSubBreeds(breeds));
        mBreedList = temp.stream()
                .filter(breed -> breed.isSubBreed() || breed.subBreeds().size() == 0)
                .sorted(Breed.NameComparator)
                .collect(Collectors.toList());
        getMvpView().showDogBreeds(mBreedList);
    }

    private List<Breed> expandSubBreeds(List<Breed> breeds) {
        List<Breed> temp = new ArrayList<>();
        for (Breed breed : breeds) {
            temp.addAll(breed.subBreeds());
        }
        return temp;
    }

    private void processImageList(List<String> images) {
        mImagesForSelectedBreed = images.toArray(new String[]{});
        mSelectedImage = 0;
        getMvpView().showSelectedDogBreed(mBreedList.get(mSelectedBreed),
                mImagesForSelectedBreed[mSelectedImage]);

    }

    public void selectBreed(int position) {
        mSelectedBreed = position;
        Breed selectedBreed = mBreedList.get(mSelectedBreed);
        loadImagesForBreed(selectedBreed);
    }

    private void loadImagesForBreed(Breed selectedBreed) {
        checkViewAttached();
        mDisposable.add(
                getImagesFromService(selectedBreed));
    }

    @NonNull
    private Disposable getImagesFromService(Breed selectedBreed) {
        if (selectedBreed.isSubBreed()) {
            return mDogsService.listImages(selectedBreed.mainBreed(), selectedBreed.name())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribeOn(Schedulers.io())
                    .subscribe(this::processImageList, getMvpView()::showError);
        } else {
            return mDogsService.listImages(selectedBreed.name())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribeOn(Schedulers.io())
                    .subscribe(this::processImageList, getMvpView()::showError);
        }
    }

    public void showNextImage() {
        if (++mSelectedImage < mImagesForSelectedBreed.length) {
            Breed selectedBreed = mBreedList.get(mSelectedBreed);
            getMvpView().showSelectedDogBreed(selectedBreed, mImagesForSelectedBreed[mSelectedImage]);
        }
    }
}
