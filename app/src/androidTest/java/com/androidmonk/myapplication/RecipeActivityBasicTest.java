package com.androidmonk.myapplication;

import androidx.test.espresso.IdlingRegistry;
import androidx.test.espresso.IdlingResource;
import androidx.test.espresso.contrib.RecyclerViewActions;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.rule.ActivityTestRule;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static com.androidmonk.myapplication.utils.Constant.POSITION_ZERO;
import static com.androidmonk.myapplication.utils.Constant.RECIPE_NAME_AT_ZERO;

@RunWith(AndroidJUnit4.class)
public class RecipeActivityBasicTest {

    private IdlingResource mIdlingResource;

    @Rule
    public ActivityTestRule<MainActivity> mActivityTestRule = new ActivityTestRule<>(MainActivity.class);

    @Before
    public void registerIdlingResource(){
        mIdlingResource = mActivityTestRule.getActivity().getIdlingResource();
        IdlingRegistry.getInstance().register(mIdlingResource);
    }

    @Test
    public void scrollToPosition_CheckRecipeName(){
        onView(withId(R.id.itemList)).perform(RecyclerViewActions.scrollToPosition(POSITION_ZERO));
        onView(withText(RECIPE_NAME_AT_ZERO)).check(matches(isDisplayed()));
    }

    @After
    public void unregisterIdlingResource(){
        if (mIdlingResource != null){
            IdlingRegistry.getInstance().unregister(mIdlingResource);
        }
    }

}
