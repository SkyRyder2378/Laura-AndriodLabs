package algonquin.cst2335.maye0097;


import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.replaceText;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;


import androidx.test.core.app.ActivityScenario;
import androidx.test.espresso.ViewInteraction;
import androidx.test.ext.junit.rules.ActivityScenarioRule;

import org.junit.Rule;
import org.junit.Test;


public class MainActivityTest {

    @Rule
    public ActivityScenarioRule<MainActivity> mActivityScenarioRule =
            new ActivityScenarioRule<>(MainActivity.class);

    /** This function tests how the application will run if the input password is missing an Upper case Letter.
     *
     */
    @Test
    public void testFindMissingUpperCase(){
        ActivityScenario<MainActivity> scenario = mActivityScenarioRule.getScenario();

        ViewInteraction appCompatEditText = onView(withId(R.id.passwordField));
        appCompatEditText.perform(replaceText("pa$$w0rd"));

        ViewInteraction materialButton = onView(withId(R.id.loginButton));
        materialButton.perform(click());

        ViewInteraction textView = onView(withId(R.id.passwordTextView));
        textView.check(matches(withText("You shall not pass!")));
    }

    /** This function tests how the application will run if the input password is missing a lower case Letter.
     *
     */
    @Test
    public void testFindMissingLowerCase(){
        ActivityScenario<MainActivity> scenario = mActivityScenarioRule.getScenario();

        ViewInteraction appCompatEditText = onView(withId(R.id.passwordField));
        appCompatEditText.perform(replaceText("PA$$W0RD"));

        ViewInteraction materialButton = onView(withId(R.id.loginButton));
        materialButton.perform(click());

        ViewInteraction textView = onView(withId(R.id.passwordTextView));
        textView.check(matches(withText("You shall not pass!")));
    }

    /** This function tests how the application will run if the input password is missing a number.
     *
     */
    @Test
    public void testFindMissingNumber(){
        ActivityScenario<MainActivity> scenario = mActivityScenarioRule.getScenario();

        ViewInteraction appCompatEditText = onView(withId(R.id.passwordField));
        appCompatEditText.perform(replaceText("Pa$$word"));

        ViewInteraction materialButton = onView(withId(R.id.loginButton));
        materialButton.perform(click());

        ViewInteraction textView = onView(withId(R.id.passwordTextView));
        textView.check(matches(withText("You shall not pass!")));
    }

    /** This function tests how the application will run if the input password is missing a special character.
     *
     */
    @Test
    public void testFindMissingSpecialCharacter(){
        ActivityScenario<MainActivity> scenario = mActivityScenarioRule.getScenario();

        ViewInteraction appCompatEditText = onView(withId(R.id.passwordField));
        appCompatEditText.perform(replaceText("Passw0rd"));

        ViewInteraction materialButton = onView(withId(R.id.loginButton));
        materialButton.perform(click());

        ViewInteraction textView = onView(withId(R.id.passwordTextView));
        textView.check(matches(withText("You shall not pass!")));
    }

    /** This function tests how the application will run if the input password is not missing anything.
     *
     */
    @Test
    public void testSuccess(){
        ActivityScenario<MainActivity> scenario = mActivityScenarioRule.getScenario();

        ViewInteraction appCompatEditText = onView(withId(R.id.passwordField));
        appCompatEditText.perform(replaceText("Pa$$w0rd"));

        ViewInteraction materialButton = onView(withId(R.id.loginButton));
        materialButton.perform(click());

        ViewInteraction textView = onView(withId(R.id.passwordTextView));
        textView.check(matches(withText("Your password meets the requirements")));
    }

}
