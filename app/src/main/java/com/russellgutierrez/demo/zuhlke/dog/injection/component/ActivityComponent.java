package com.russellgutierrez.demo.zuhlke.dog.injection.component;

import android.content.Context;

import com.russellgutierrez.demo.zuhlke.dog.injection.ActivityContext;
import com.russellgutierrez.demo.zuhlke.dog.injection.PerActivity;
import com.russellgutierrez.demo.zuhlke.dog.injection.module.ActivityModule;
import com.russellgutierrez.demo.zuhlke.dog.ui.main.MainActivity;

import dagger.Subcomponent;

/**
 * This component inject dependencies to all Activities across the application
 */
@PerActivity
@Subcomponent(modules = ActivityModule.class)
public interface ActivityComponent {

    void inject(MainActivity mainActivity);

    @ActivityContext Context context();

}
