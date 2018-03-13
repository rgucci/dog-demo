package com.russellgutierrez.demo.zuhlke.dog.injection;

import com.russellgutierrez.demo.zuhlke.dog.injection.component.ConfigPersistentComponent;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

import javax.inject.Scope;

/**
 * A scoping annotation to permit dependencies conform to the life of the
 * {@link ConfigPersistentComponent}
 */
@Scope
@Retention(RetentionPolicy.RUNTIME)
public @interface ConfigPersistent {
}
