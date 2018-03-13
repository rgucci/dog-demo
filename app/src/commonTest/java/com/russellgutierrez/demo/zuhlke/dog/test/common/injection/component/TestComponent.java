package com.russellgutierrez.demo.zuhlke.dog.test.common.injection.component;

import com.russellgutierrez.demo.zuhlke.dog.injection.component.ApplicationComponent;
import com.russellgutierrez.demo.zuhlke.dog.test.common.injection.module.ApplicationTestModule;

import javax.inject.Singleton;

import dagger.Component;

@Singleton
@Component(modules = ApplicationTestModule.class)
public interface TestComponent extends ApplicationComponent {

}
