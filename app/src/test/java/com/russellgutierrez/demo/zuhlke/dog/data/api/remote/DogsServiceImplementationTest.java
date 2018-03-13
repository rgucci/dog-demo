package com.russellgutierrez.demo.zuhlke.dog.data.api.remote;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import com.russellgutierrez.demo.zuhlke.dog.data.api.model.ListAllBreedsResponse;
import com.russellgutierrez.demo.zuhlke.dog.data.api.model.ListImagesForBreedResponse;
import com.russellgutierrez.demo.zuhlke.dog.data.model.Breed;
import com.russellgutierrez.demo.zuhlke.dog.util.RxSchedulersOverrideRule;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.List;
import java.util.stream.Collectors;

import io.reactivex.Observable;
import io.reactivex.observers.TestObserver;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class DogsServiceImplementationTest {
    @Rule
    public final RxSchedulersOverrideRule mOverrideSchedulersRule = new RxSchedulersOverrideRule();

    @Mock
    DogsEndpoint mDogsEndpoint;

    DogsService mDogsService;

    @Before
    public void setUp() throws Exception {
        mDogsService = new DogsServiceImplementation(mDogsEndpoint);
    }

    @Test
    public void listAllBreeds() throws Exception {
        ImmutableMap<String, List<String>> breeds = ImmutableMap.of(
                "main1", ImmutableList.of("sub1", "sub2"),
                "main2", ImmutableList.of("sub3"));
        when(mDogsEndpoint.listAllBreeds())
                .thenReturn(Observable.just(ListAllBreedsResponse.builder()
                        .status("success")
                        .message(breeds)
                        .build()));

        TestObserver<List<Breed>> result = new TestObserver<>();
        mDogsService.listAllBreeds().subscribe(result);
        verify(mDogsEndpoint).listAllBreeds();
        result.assertNoErrors();

        List<Breed> list = result.values().get(0);
        assertEquals(breeds.size(), list.size());
        for (String key : breeds.keySet()) {
            assertEquals(1,
                    list.stream()
                            .filter(breed -> key.equals(breed.name()))
                            .count());
        }
        for (Breed breed : list) {
            assertEquals(breeds.get(breed.name()),
                    breed.subBreeds().stream()
                            .map(breed1 -> breed1.name())
                            .collect(Collectors.toList()));
        }
    }

    @Test
    public void listImagesEmpty() throws Exception {
        final String breedName = "dummy";
        ImmutableList<String> images = ImmutableList.of();
        when(mDogsEndpoint.listImages(anyString()))
                .thenReturn(Observable.just(ListImagesForBreedResponse.builder()
                        .status("success")
                        .message(images)
                        .build()));

        TestObserver<List<String>> result = new TestObserver<>();
        mDogsService.listImages(breedName).subscribe(result);

        verify(mDogsEndpoint).listImages(breedName);
        result.assertNoErrors();

        assertTrue(result.values().get(0).isEmpty());
    }

    @Test
    public void listImages() throws Exception {
        final String breedName = "dummy";
        ImmutableList<String> images = ImmutableList.of(
                "http://somedummyimage.com/abc.jpeg",
                "http://somedummyimage.net/123.jpeg"
        );
        when(mDogsEndpoint.listImages(anyString()))
                .thenReturn(Observable.just(ListImagesForBreedResponse.builder()
                        .status("success")
                        .message(images)
                        .build()));

        TestObserver<List<String>> result = new TestObserver<>();
        mDogsService.listImages(breedName).subscribe(result);

        verify(mDogsEndpoint).listImages(breedName);
        result.assertNoErrors();

        assertEquals(images, result.values().get(0));
    }

    @Test
    public void listImagesForSubBreedEmpty() throws Exception {
        final String breedName = "dummy";
        final String subBreedName = "foo";
        ImmutableList<String> images = ImmutableList.of();
        when(mDogsEndpoint.listImages(anyString(), anyString()))
                .thenReturn(Observable.just(ListImagesForBreedResponse.builder()
                        .status("success")
                        .message(images)
                        .build()));

        TestObserver<List<String>> result = new TestObserver<>();
        mDogsService.listImages(breedName, subBreedName).subscribe(result);

        verify(mDogsEndpoint).listImages(breedName, subBreedName);
        result.assertNoErrors();

        assertTrue(result.values().get(0).isEmpty());
    }

    @Test
    public void listImagesForSubBreed() throws Exception {
        final String breedName = "dummy";
        final String subBreedName = "foo";
        ImmutableList<String> images = ImmutableList.of(
                "http://somedummyimage.com/abc.jpeg",
                "http://somedummyimage.net/123.jpeg"
        );
        when(mDogsEndpoint.listImages(anyString(), anyString()))
                .thenReturn(Observable.just(ListImagesForBreedResponse.builder()
                        .status("success")
                        .message(images)
                        .build()));

        TestObserver<List<String>> result = new TestObserver<>();
        mDogsService.listImages(breedName, subBreedName).subscribe(result);

        verify(mDogsEndpoint).listImages(breedName, subBreedName);
        result.assertNoErrors();

        assertEquals(images, result.values().get(0));
    }

}