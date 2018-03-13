package com.russellgutierrez.demo.zuhlke.dog;

import com.google.common.collect.ImmutableList;
import com.russellgutierrez.demo.zuhlke.dog.data.api.remote.DogsService;
import com.russellgutierrez.demo.zuhlke.dog.data.model.Breed;
import com.russellgutierrez.demo.zuhlke.dog.ui.main.MainMvpView;
import com.russellgutierrez.demo.zuhlke.dog.ui.main.MainPresenter;
import com.russellgutierrez.demo.zuhlke.dog.util.RxSchedulersOverrideRule;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.List;

import io.reactivex.Single;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class MainPresenterTest {

    @Mock
    MainMvpView mMainMvpView;

    @Mock
    DogsService mDogsService;

    private MainPresenter mMainPresenter;

    @Rule
    public final RxSchedulersOverrideRule mOverrideSchedulersRule = new RxSchedulersOverrideRule();

    @Before
    public void setUp() {
        mMainPresenter = new MainPresenter(mDogsService);
        mMainPresenter.attachView(mMainMvpView);
    }

    @After
    public void tearDown() {
        mMainPresenter.detachView();
    }

    @Test
    public void displayBreeds() {
        //TODO create a utility for creating dummy test data to avoid hard-coding this in tests
        List<Breed> breeds = Breed.from(ImmutableList.of("dog1", "dog2", "dog3"), null);
        when(mDogsService.listAllBreeds())
                .thenReturn(Single.just(breeds));

        mMainPresenter.loadBreedList();

        verify(mDogsService).listAllBreeds();
        verify(mMainMvpView).showDogBreeds(eq(breeds));
        verify(mMainMvpView, never()).showError(any());
    }

    @Test
    public void selectOneBreed() {
        //TODO create a utility for creating dummy test data to avoid hard-coding this in tests
        List<Breed> breeds = Breed.from(ImmutableList.of("dog1", "dog2", "dog3"), null);
        List<String> images = ImmutableList.of("http://a.com/pic1", "http://b.com/dog.jpg");
        final int position = 1;
        final String selected = breeds.get(position).name();

        when(mDogsService.listAllBreeds())
                .thenReturn(Single.just(breeds));
        when(mDogsService.listImages(selected))
                .thenReturn(Single.just(images));

        mMainPresenter.loadBreedList();
        mMainPresenter.selectBreed(position);

        verify(mDogsService).listImages(breeds.get(position).name());
        verify(mMainMvpView).showSelectedDogBreed(breeds.get(position), images.get(0));
        verify(mMainMvpView, never()).showError(any());
    }

    @Test
    public void nextImage() {
        //TODO create a utility for creating dummy test data to avoid hard-coding this in tests
        List<Breed> breeds = Breed.from(ImmutableList.of("dog1", "dog2", "dog3"), null);
        List<String> images = ImmutableList.of("http://a.com/pic1", "http://b.com/dog.jpg");
        final int position = 1;
        final String selected = breeds.get(position).name();

        when(mDogsService.listAllBreeds())
                .thenReturn(Single.just(breeds));
        when(mDogsService.listImages(selected))
                .thenReturn(Single.just(images));

        mMainPresenter.loadBreedList();
        mMainPresenter.selectBreed(position);
        mMainPresenter.showNextImage();

        verify(mDogsService).listImages(breeds.get(position).name());
        verify(mMainMvpView).showSelectedDogBreed(breeds.get(position), images.get(0));
        verify(mMainMvpView).showSelectedDogBreed(breeds.get(position), images.get(1));
        verify(mMainMvpView, never()).showError(any());
    }

}
