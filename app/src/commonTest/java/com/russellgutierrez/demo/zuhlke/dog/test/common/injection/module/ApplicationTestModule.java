package com.russellgutierrez.demo.zuhlke.dog.test.common.injection.module;

import android.app.Application;
import android.content.Context;

import com.russellgutierrez.demo.zuhlke.dog.data.api.remote.DogsEndpoint;
import com.russellgutierrez.demo.zuhlke.dog.data.api.remote.DogsService;
import com.russellgutierrez.demo.zuhlke.dog.injection.ApplicationContext;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

import static org.mockito.Mockito.mock;

/**
 * Provides application-level dependencies for an app running on a testing environment
 * This allows injecting mocks if necessary.
 */
@Module
public class ApplicationTestModule {

    private final Application mApplication;

    public ApplicationTestModule(Application application) {
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

    /************* MOCKS *************/

    @Provides
    DogsEndpoint provideDogsEndpoint() {
        return mock(DogsEndpoint.class);
    }

    @Provides
    @Singleton
    DogsService providesDogsService() {
        return mock(DogsService.class);
    }

}
