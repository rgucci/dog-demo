- MainPresenter: what is the purpose of mDisposable?

MainPresenter calls the APIs using DogService, which returns RX "observables". 
The API calls are asynchronous, so MainPresenter needs to subscribe to these "observables" 
to be notified once the API has finished executing (or returns an error). 
If the API completes (either successfully or with an error), then everything is fine,
and MainPresenter will update the view (MainActivity).
Due to the nature of the Activity lifecycle in Android, it can happen that the activity will be paused/destroyed/etc.
If this happens while the API has not yet completed, then the subscriber (MainPresenter) will still be notified once
the API call has completed, so there will be problems and probably exceptions when the MainPresenter tries to update the view that has already been paused/destroyed/etc.
So mDisposable is used to keep track of all the subscriptions made by MainPresenter so that we can un-subscribe
when the activity is destroyed.

- MainPresenter: what is the purpose of .observeOn method and .subscribeOn method in getImageFromServcice method?

.observeOn and .subscribeOn are used in all of the RX subscriptions in the presenter.

.observeOn specifies which thread the subscriber (presenter updating the view) will run on.
In this case, we specify that it should run in my ain the Android thread, since this is the only thread allowed to update the UI.
This is similar to calling Activity.runOnUiThread or using Looper.getMainLooper()

.subscribeOn specifies which thread the API call will run on. 
In this case, we specify that it should run in a background thread (asynchronous).

- BaseActivity: why is sComponentMap is needed to store ConfigPersistentComponent? Can we just store the component in activity directly?

We need to store  configPersistentComponent and make sure that it is available as long as the activity is available.
We cannot just store it in the activity instance because during configuration changes (when the screen orientation changes, locale change, keyboard change, etc.),
the activity will be restarted (calling onDestroy(), onCreate()), so that the previous value will be lost.
So we need to store it in a static field (survives config changes) that is available for all activities, each activity uses a unique id to 
store/get its own component.

- Why is RxJava is used in the project?

RXJava helps us to write code that is simple, easy to understand and easy to maintain.
For the task of calling an asynchronous REST API using HTTP, processing the results, then displaying the results, and handling exceptions,
without using RX, it would require (one possible solution) using AsyncTask, callback listener classes, exception handlers, etc.
And this will typically require a lot of related classes even for some simple tasks.

Using RX, all of this is simplified:
1. easy do asynchronous or not, just specify the scheduler you want it .observeOn, .subscribeOn
1. exception handling is made easier, the exceptions can be passed to the subscribers to handle
1. easier to do processing logic all in the same part of the code: mapping from json to business objects, 
applying filters
1. easier to combine different sources of data, for example, using Rx it is easy to combine the result of 2 api calls (i.e. merge, combineLatest, zip)

- What are the purpose of @ActivityContext and @ApplicationContext annotations?

These annotations are both used by Dagger2 for dependency injection to determine 
which specific objects to use during injection, and who is allowed to usy these injected objects.

@ApplicationContext is used to specify that an object is to be used application-wide.

@ActivityContext is used to specify that an object is to be used with an instance of an activity only.

Both are also used to determine which object to inject, in case it is available from different source.
For example Context is available from both ApplicationComponent and ActivityComponent.
Using the correct annotation makes sure that we get the correct Context injected (Application or Activity instance).

There is also a 3rd annotation @PerActivity, that specifies the scope of the object.
In this case, @PerActivity is used in ActivityComponent, to tell Dagger2 that we want each activity 
to get its own instance of ActivityComponent
