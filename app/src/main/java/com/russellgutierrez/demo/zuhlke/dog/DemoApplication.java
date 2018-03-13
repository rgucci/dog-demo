package com.russellgutierrez.demo.zuhlke.dog;

import android.app.Application;
import android.content.Context;

import com.crashlytics.android.Crashlytics;
import com.russellgutierrez.demo.zuhlke.dog.injection.component.ApplicationComponent;
import com.russellgutierrez.demo.zuhlke.dog.injection.component.DaggerApplicationComponent;
import com.russellgutierrez.demo.zuhlke.dog.injection.module.ApplicationModule;

import io.fabric.sdk.android.Fabric;
import timber.log.Timber;

public class DemoApplication extends Application {

    ApplicationComponent mApplicationComponent;

    @Override
    public void onCreate() {
        super.onCreate();

        if (BuildConfig.DEBUG) {
            Timber.plant(new Timber.DebugTree());
            Fabric.with(this, new Crashlytics());
        }
    }

    public static DemoApplication get(Context context) {
        return (DemoApplication) context.getApplicationContext();
    }

    public ApplicationComponent getComponent() {
        if (mApplicationComponent == null) {
            mApplicationComponent = DaggerApplicationComponent.builder()
                    .applicationModule(new ApplicationModule(this))
                    .build();
        }
        return mApplicationComponent;
    }

    // Needed to replace the component with a test specific one
    public void setComponent(ApplicationComponent applicationComponent) {
        mApplicationComponent = applicationComponent;
    }
}
