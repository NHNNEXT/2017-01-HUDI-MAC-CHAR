package com.zimincom.mafiaonline;

import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.closeSoftKeyboard;
import static android.support.test.espresso.action.ViewActions.replaceText;
import static android.support.test.espresso.action.ViewActions.typeText;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;

/**
 * Created by Zimincom on 2017. 4. 23..
 */

@RunWith(AndroidJUnit4.class)
public class NewRoomTest {

    @Rule
    public ActivityTestRule<LoginActivity> mActivityRule = new ActivityTestRule<LoginActivity>(LoginActivity.class);

    @Test
    public void loginAndCreateRoom() {
        onView(withId(R.id.password))
                .perform(typeText("1234"),closeSoftKeyboard());
        onView(withId(R.id.login))
                .perform(click());

        onView(withId(R.id.create_room))
                .perform(click());

        onView(withId(R.id.room_title))
                .perform(typeText("roomTest"))
                .check(matches(isDisplayed()));

        onView(withText("확인"))
                .perform(click());

        onView(withId(R.id.message_input))
                .perform(click(),replaceText("안녕하세요? 새로운 유저입니다"),closeSoftKeyboard());

        onView(withId(R.id.send_button))
                .perform(click());

    }



}
