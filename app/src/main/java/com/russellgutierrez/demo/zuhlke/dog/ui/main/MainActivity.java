package com.russellgutierrez.demo.zuhlke.dog.ui.main;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.Spinner;

import com.russellgutierrez.demo.zuhlke.dog.R;
import com.russellgutierrez.demo.zuhlke.dog.data.local.ImageHelper;
import com.russellgutierrez.demo.zuhlke.dog.data.model.Breed;
import com.russellgutierrez.demo.zuhlke.dog.ui.base.BaseActivity;
import com.russellgutierrez.demo.zuhlke.dog.util.DialogFactory;

import org.apache.commons.text.WordUtils;

import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends BaseActivity implements MainMvpView {

    @Inject
    MainPresenter mMainPresenter;

    @Inject
    ImageHelper mImageHelper;

    @BindView(R.id.breed_spinner)
    Spinner mBreedListSpinner;

    @BindView(R.id.imageview)
    ImageView mImageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        activityComponent().inject(this);

        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        mBreedListSpinner.setOnItemSelectedListener(new CustomOnItemSelectedListener(mMainPresenter));

        mMainPresenter.attachView(this);
        mMainPresenter.loadBreedList();
    }

    @Override
    public void showDogBreeds(List<Breed> breeds) {
        CustomArrayAdapter<Breed> adapter = new CustomArrayAdapter<Breed>(this, android.R.layout.simple_spinner_item, breeds) {
            @Override
            protected String buildStringValue(Breed breed) {
                StringBuilder builder = new StringBuilder();
                if (breed.isSubBreed()) {
                    builder.append(breed.mainBreed())
                            .append(", ");
                }
                builder.append(breed.name());
                return WordUtils.capitalize(builder.toString());
            }
        };
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mBreedListSpinner.setAdapter(adapter);
    }

    @Override
    public void showSelectedDogBreed(Breed breed, String imageUrl) {
        mImageHelper.loadImage(mImageView, imageUrl);
    }

    public void showError(Throwable t) {
        DialogFactory.createGenericErrorDialog(this, t.getMessage())
                .show();
    }

    @OnClick(R.id.button_next)
    protected void onNext() {
        mMainPresenter.showNextImage();
    }

    private static class CustomOnItemSelectedListener implements AdapterView.OnItemSelectedListener {

        private final MainPresenter mMainPresenter;

        public CustomOnItemSelectedListener(MainPresenter mainPresenter) {
            mMainPresenter = mainPresenter;
        }

        @Override
        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
            mMainPresenter.selectBreed(position);
        }

        @Override
        public void onNothingSelected(AdapterView<?> parent) {

        }
    }
}
