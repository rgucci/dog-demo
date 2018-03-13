package com.russellgutierrez.demo.zuhlke.dog.injection.component;

import android.app.Application;
import android.content.Context;

import com.russellgutierrez.demo.zuhlke.dog.data.api.remote.DogsService;
import com.russellgutierrez.demo.zuhlke.dog.injection.ApplicationContext;
import com.russellgutierrez.demo.zuhlke.dog.injection.module.ApplicationModule;

import javax.inject.Singleton;

import dagger.Component;

@Singleton
@Component(modules = ApplicationModule.class)
public interface ApplicationComponent {

    @ApplicationContext
    Context context();

    Application application();

    DogsService dogsService();

}
