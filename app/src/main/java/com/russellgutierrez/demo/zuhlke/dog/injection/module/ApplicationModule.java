package com.russellgutierrez.demo.zuhlke.dog.injection.module;

import android.app.Application;
import android.content.Context;

import com.russellgutierrez.demo.zuhlke.dog.data.api.remote.DogsEndpoint;
import com.russellgutierrez.demo.zuhlke.dog.data.api.remote.DogsService;
import com.russellgutierrez.demo.zuhlke.dog.data.api.remote.DogsServiceImplementation;
import com.russellgutierrez.demo.zuhlke.dog.injection.ApplicationContext;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * Provide application-level dependencies.
 */
@Module
public class ApplicationModule {
    protected final Application mApplication;

    public ApplicationModule(Application application) {
        mApplication = application;
    }

    @Provides
    Application provideApplication() {
        return mApplication;
    }

    @Provides
    @ApplicationContext
    Context provideContext() {
        return mApplication;
    }

    @Provides
    @Singleton
    DogsEndpoint provideDogsEndpoint() {
        return DogsEndpoint.Creator.newDogsEndpoint();
    }

    @Provides
    @Singleton
    DogsService providesDogsService(DogsEndpoint dogsEndpoint) {
        return new DogsServiceImplementation(dogsEndpoint);
    }

}
