package com.gbc.comp3074.a1_samin_qureshi_pierre_eid;

import android.content.Context;

import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.platform.app.InstrumentationRegistry;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.closeSoftKeyboard;
import static androidx.test.espresso.action.ViewActions.typeText;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static org.junit.Assert.assertEquals;

@RunWith(AndroidJUnit4.class)
public class MainActivityInstrumentedTest {

    @Rule
    public ActivityScenarioRule<MainActivity> activityRule =
            new ActivityScenarioRule<>(MainActivity.class);

    @Test
    public void useAppContext() {
        Context appContext = InstrumentationRegistry.getInstrumentation().getTargetContext();
        assertEquals("com.gbc.comp3074.a1_samin_qureshi_pierre_eid", appContext.getPackageName());
    }

    @Test
    public void testUIElementsAreDisplayed() {
        onView(withId(R.id.etHoursWorked)).check(matches(isDisplayed()));
        onView(withId(R.id.etHourlyRate)).check(matches(isDisplayed()));
        onView(withId(R.id.btnCalculate)).check(matches(isDisplayed()));
        onView(withId(R.id.tvPay)).check(matches(isDisplayed()));
        onView(withId(R.id.tvOvertimePay)).check(matches(isDisplayed()));
        onView(withId(R.id.tvTotalPay)).check(matches(isDisplayed()));
        onView(withId(R.id.tvTax)).check(matches(isDisplayed()));
    }

    @Test
    public void testCalculation_NoOvertime() {
        onView(withId(R.id.etHoursWorked))
                .perform(typeText("35"), closeSoftKeyboard());
        onView(withId(R.id.etHourlyRate))
                .perform(typeText("20"), closeSoftKeyboard());

        onView(withId(R.id.btnCalculate)).perform(click());

        onView(withId(R.id.tvPay)).check(matches(withText("$700.00")));
        onView(withId(R.id.tvOvertimePay)).check(matches(withText("$0.00")));
        onView(withId(R.id.tvTotalPay)).check(matches(withText("$700.00")));
        onView(withId(R.id.tvTax)).check(matches(withText("$126.00")));
    }

    @Test
    public void testCalculation_WithOvertime() {
        onView(withId(R.id.etHoursWorked))
                .perform(typeText("45"), closeSoftKeyboard());
        onView(withId(R.id.etHourlyRate))
                .perform(typeText("20"), closeSoftKeyboard());

        onView(withId(R.id.btnCalculate)).perform(click());

        onView(withId(R.id.tvPay)).check(matches(withText("$800.00")));
        onView(withId(R.id.tvOvertimePay)).check(matches(withText("$150.00")));
        onView(withId(R.id.tvTotalPay)).check(matches(withText("$950.00")));
        onView(withId(R.id.tvTax)).check(matches(withText("$171.00")));
    }

    @Test
    public void testCalculation_Exactly40Hours() {
        onView(withId(R.id.etHoursWorked))
                .perform(typeText("40"), closeSoftKeyboard());
        onView(withId(R.id.etHourlyRate))
                .perform(typeText("25"), closeSoftKeyboard());

        onView(withId(R.id.btnCalculate)).perform(click());

        onView(withId(R.id.tvPay)).check(matches(withText("$1,000.00")));
        onView(withId(R.id.tvOvertimePay)).check(matches(withText("$0.00")));
        onView(withId(R.id.tvTotalPay)).check(matches(withText("$1,000.00")));
        onView(withId(R.id.tvTax)).check(matches(withText("$180.00")));
    }

    @Test
    public void testEmptyInputValidation() {
        onView(withId(R.id.btnCalculate)).perform(click());
        // App should show error dialog or toast
        // This test verifies the app doesn't crash on empty input
    }

    @Test
    public void testButtonClickable() {
        onView(withId(R.id.btnCalculate))
                .check(matches(isDisplayed()))
                .perform(click());
    }
}