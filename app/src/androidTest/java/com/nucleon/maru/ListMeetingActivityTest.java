package com.nucleon.maru;

import android.widget.TimePicker;

import androidx.test.espresso.contrib.RecyclerViewActions;
import androidx.test.espresso.matcher.ViewMatchers;
import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.internal.runner.junit4.AndroidJUnit4ClassRunner;
import androidx.test.platform.app.InstrumentationRegistry;

import com.nucleon.maru.View.ListMeetingActivity;
import com.nucleon.maru.utils.RecyclerViewMatcher;

import org.hamcrest.Matchers;
import org.junit.FixMethodOrder;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.Espresso.openActionBarOverflowOrOptionsMenu;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.typeText;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.RootMatchers.isPlatformPopup;
import static androidx.test.espresso.matcher.ViewMatchers.hasChildCount;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withClassName;
import static androidx.test.espresso.matcher.ViewMatchers.withEffectiveVisibility;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withSubstring;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.core.AllOf.allOf;

@RunWith(AndroidJUnit4ClassRunner.class)
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class ListMeetingActivityTest {

    // Constants
    static final String SUBJECT = "UI Test";
    static final String PARTICIPANTS = "Mike@lamzone.com";
    static final String DATE = "12:00";
    static final String DIFFERENT_DATE = "6:00";

    @Rule
    public ActivityScenarioRule<ListMeetingActivity> rule = new ActivityScenarioRule<>(ListMeetingActivity.class);

    @Test
    public void myMeetingsList_ShouldBeVisibleAndEmpty() {
        // Check visibility of recyclerview
        onView(withId(R.id.list_meetings))
                .check(matches(withEffectiveVisibility(ViewMatchers.Visibility.VISIBLE)));
        // Check if recyclerview is empty
        onView(withId(R.id.list_meetings))
                .check(matches(hasChildCount(0)));
    }

    @Test
    public void myMeetingsList_addAction_shouldCreateItem() {
        // Given : Fill the item form
        onView(withId(R.id.fab_add_meeting))
                .perform(click());
        onView(withId(R.id.edtSubject))
                .perform(typeText(SUBJECT));
        onView(withId(R.id.edtParticipants))
                .perform(typeText(PARTICIPANTS));
        onView(withId(R.id.edtTime))
                .perform(typeText(DATE));
        // When : Perform a click on add button
        onView(withId(R.id.btnAddMeeting))
                .perform(click());
        // Then : The number of items in recyclerview is 1
        onView(allOf(withId(R.id.list_meetings), isDisplayed()))
                .check(matches(hasChildCount(1)));
    }

    @Test
    public void myMeetingsList_deleteAction_shouldRemoveItem() {
        // Given : Create new item (from last test)
        // When : Perform a click on delete item button
        onView(withId(R.id.list_meetings))
                .perform(RecyclerViewActions.actionOnItemAtPosition(0, RecyclerViewMatcher.clickChildViewWithId(R.id.item_list_delete)));
        // Then : The number of items in recyclerview is 0
        onView(allOf(withId(R.id.list_meetings), isDisplayed()))
                .check(matches(hasChildCount(0)));
    }

    @Test
    public void myMeetingsList_filterByDateAction_shouldDisplayOnlyFiltered() {
        // Given : Create two items with different date
        onView(withId(R.id.fab_add_meeting))
                .perform(click());
        onView(withId(R.id.edtSubject))
                .perform(typeText(SUBJECT));
        onView(withId(R.id.edtParticipants))
                .perform(typeText(PARTICIPANTS));
        onView(withId(R.id.edtTime))
                .perform(typeText(DATE));
        onView(withId(R.id.btnAddMeeting))
                .perform(click());
        onView(withId(R.id.fab_add_meeting))
                .perform(click());
        onView(withId(R.id.edtSubject))
                .perform(typeText(SUBJECT));
        onView(withId(R.id.edtParticipants))
                .perform(typeText(PARTICIPANTS));
        onView(withId(R.id.edtTime))
                .perform(typeText(DIFFERENT_DATE));
        onView(withId(R.id.btnAddMeeting))
                .perform(click());
        // When : Perform a click on menu and pick time
        openActionBarOverflowOrOptionsMenu(InstrumentationRegistry.getInstrumentation().getTargetContext());
        onView(withText(R.string.filter_option_1))
                .perform(click());
        onView(withClassName(Matchers.equalTo(TimePicker.class.getName()))).perform(RecyclerViewMatcher.setTime(6,0));
        onView(withText("OK")).perform(click());
        // Then : Recyclerview shows only one item with time filter
        onView(allOf(withId(R.id.list_meetings), isDisplayed()))
                .check(matches(hasChildCount(1)));
        onView(withId(R.id.list_meetings))
                .check(matches(RecyclerViewMatcher.atPositionOnView(0, withSubstring("6h00"), R.id.item_list_title)));
    }

    @Test
    public void myMeetingsList_filterByRoomAction_shouldDisplayOnlyFiltered() {
        // Given : Create two items with different room
        onView(withId(R.id.fab_add_meeting))
                .perform(click());
        onView(withId(R.id.edtSubject))
                .perform(typeText(SUBJECT));
        onView(withId(R.id.edtParticipants))
                .perform(typeText(PARTICIPANTS));
        onView(withId(R.id.edtTime))
                .perform(typeText(DATE));
        onView(withId(R.id.btnAddMeeting))
                .perform(click());
        onView(withId(R.id.fab_add_meeting))
                .perform(click());
        onView(withId(R.id.edtSubject))
                .perform(typeText(SUBJECT));
        onView(withId(R.id.edtParticipants))
                .perform(typeText(PARTICIPANTS));
        onView(withId(R.id.spinner))
                .perform(click());
        onView(withText(containsString("B")))
                .inRoot(isPlatformPopup())
                .perform(click());
        onView(withId(R.id.edtTime))
                .perform(typeText(DATE));
        onView(withId(R.id.btnAddMeeting))
                .perform(click());
        // When : Perform a click on menu and write in search view
        openActionBarOverflowOrOptionsMenu(InstrumentationRegistry.getInstrumentation().getTargetContext());
        onView(withText(R.string.filter_option_2))
                .perform(click());
        onView(withId(androidx.appcompat.R.id.search_src_text))
                .perform(typeText("B"));
        // Then : Recyclerview shows only one item with room filter
        onView(allOf(withId(R.id.list_meetings), isDisplayed()))
                .check(matches(hasChildCount(1)));
        onView(withId(R.id.list_meetings))
                .check(matches(RecyclerViewMatcher.atPositionOnView(0, withSubstring("B"), R.id.item_list_title)));
    }
}
