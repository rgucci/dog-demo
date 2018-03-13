package com.russellgutierrez.demo.zuhlke.dog;

import android.support.test.InstrumentationRegistry;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import com.russellgutierrez.demo.zuhlke.dog.data.model.Breed;
import com.russellgutierrez.demo.zuhlke.dog.test.common.TestComponentRule;
import com.russellgutierrez.demo.zuhlke.dog.ui.main.MainActivity;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.RuleChain;
import org.junit.rules.TestRule;
import org.junit.runner.RunWith;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import io.reactivex.Single;

import static android.support.test.espresso.Espresso.onData;
import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withSpinnerText;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.CoreMatchers.anything;
import static org.hamcrest.CoreMatchers.containsString;
import static org.hamcrest.CoreMatchers.is;
import static org.mockito.Mockito.when;

@RunWith(AndroidJUnit4.class)
public class MainActivityTest {

    public final TestComponentRule component =
            new TestComponentRule(InstrumentationRegistry.getTargetContext());
    public final ActivityTestRule<MainActivity> main =
            new ActivityTestRule<>(MainActivity.class, false, false);

    public static final String TITLE_PATTERN = "#{0} {1}";

    // TestComponentRule needs to go first to make sure the Dagger ApplicationTestComponent is set
    // in the Application before any Activity is launched.
    @Rule
    public final TestRule chain = RuleChain.outerRule(component).around(main);

    @Before
    public void setup() {

    }

    @Test
    public void uiTest() {
        List<Breed> breeds = new ArrayList<>();
        breeds.add(Breed.builder()
                .name("dog1")
                .subBreeds(Collections.EMPTY_LIST)
                .build());
        breeds.add(Breed.builder()
                .name("dog2")
                .subBreeds(Breed.from(Arrays.asList(new String[] {"sub1", "sub2"}), "dog2"))
                .build());
        List<String> imagesDog1 = Arrays.asList(new String[] {"http://dummy.com/dog1", "http://dummy.com/dog2"});
        List<String> imagesSub1 = Arrays.asList(new String[] {"http://dummy.com/sub1a", "http://dummy.com/sub1b"});
        List<String> imagesSub2 = Arrays.asList(new String[] {"http://dummy.com/sub2a", "http://dummy.com/sub2b"});

        when(component.getDogService().listAllBreeds())
                .thenReturn(Single.just(breeds));
        when(component.getDogService().listImages("dog1"))
                .thenReturn(Single.just(imagesDog1));
        when(component.getDogService().listImages("dog2", "sub1"))
                .thenReturn(Single.just(imagesSub1));
        when(component.getDogService().listImages("dog2", "sub2"))
                .thenReturn(Single.just(imagesSub2));

        main.launchActivity(null);

        onView(withId(R.id.main))
                .check(matches(isDisplayed()));

        selectBreedFromList(1);
        checkImageView(imagesSub1.get(0));

        onView(withId(R.id.button_next))
                .perform(click());
        checkImageView(imagesSub1.get(1));
    }

    private void selectBreedFromList(int position) {
        onView(withId(R.id.breed_spinner))
                .perform(click());
        onData(anything()).atPosition(position)
                .perform(click());
    }

    private void checkImageView(String image) {
        //TODO test imageview has the "correct" image loaded
        //onView(withId(R.id.imageview));
    }

    @Test
    public void displayError() {
        RuntimeException exception = new RuntimeException("dummy exception");
        when(component.getDogService().listAllBreeds())
                .thenReturn(Single.error(exception));

        main.launchActivity(null);

        onView(withText(exception.getMessage())).check(matches(isDisplayed()));
    }
}
