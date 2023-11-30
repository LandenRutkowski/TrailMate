package com.example.trailmate;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import android.os.Looper;
import android.support.test.rule.ActivityTestRule;
import android.transition.Transition;

import androidx.annotation.NonNull;
import androidx.test.core.app.ActivityScenario;
import androidx.test.espresso.Espresso;
import androidx.test.espresso.ViewAction;
import androidx.test.espresso.ViewAssertion;
import androidx.test.espresso.action.ViewActions;
import androidx.test.espresso.assertion.ViewAssertions;
import androidx.test.espresso.matcher.ViewMatchers;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.platform.app.InstrumentationRegistry;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;


import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.HashMap;
import java.util.Map;

/**
 * <a href="http://d.android.com/tools/testing/testing_android.html">Testing Fundamentals</a>
 */
@RunWith(AndroidJUnit4.class)
public class ApplicationTest {
//	private AddCoordinatesActivity activity = new AddCoordinatesActivity();
//
//	@Test
//	public void onCreate() {
//
//
//		assertNotNull(activity.mTitleEditText);
//		assertNotNull(activity.mLatitudeEditText);
//		assertNotNull(activity.mLongitudeEditText);
//		assertNotNull(activity.mDescriptionEditText);
//
//		assertNotNull(activity.mSaveButton);
//
//
//		assertNotNull(activity.mDatabase);
//
//
//		assertNotNull(activity.mRef);
//	}

	private FirebaseAuth firebaseAuth;

	@Before
	public void setUp() {
		// Initialize FirebaseAuth instance
		firebaseAuth = FirebaseAuth.getInstance();

		// Launch MainActivity before each test
		ActivityScenario.launch(MainActivity.class);
	}

	@After
	public void tearDown() {
		// Sign out the user after each test
		signOut();
	}

	@Test
	public void testLoginButton() {
		// Assuming there's a button for logging in with the ID R.id.idBtnTrails
		Espresso.onView(ViewMatchers.withId(R.id.idBtnTrails)).perform(ViewActions.click());


	}

	@Test
	public void testCreateAccountButton() {
		// Assuming there's a button for creating an account with the ID R.id.idBtnSendData
		Espresso.onView(ViewMatchers.withId(R.id.idBtnSendData)).perform(ViewActions.click());


	}

	private void signOut() {
		// Sign out the current user
		firebaseAuth.signOut();
	}

//	@Test
//	public void testAddTrailSuccessful() {
//
//		Espresso.onView(ViewMatchers.withId(R.id.save_button)).perform(ViewActions.click());
//
//		DatabaseReference dbRef = FirebaseDatabase.getInstance().getReference().child("Coordinates");
//
//		Map<String, Object> trailData = new HashMap<>();
//		trailData.put("name", "Your Trail Name");
//		trailData.put("location", "Trail Location");
//		DatabaseReference newTrailRef = dbRef.push();
//		newTrailRef.setValue(trailData);
//		String yourTrailId = newTrailRef.getKey();
//
//		dbRef.addListenerForSingleValueEvent(new ValueEventListener() {
//			@Override
//			public void onDataChange(@NonNull DataSnapshot snapshot) {
//				boolean trailAdded = snapshot.hasChild(yourTrailId);
//
//				assert trailAdded;
//			}
//
//			@Override
//			public void onCancelled(@NonNull DatabaseError error) {
//
//			}
//		});
//	}

//	@Test
//	public void testDisplayTrailSuccessful() {
//
//		Espresso.onView(ViewMatchers.withId(R.id.draw_path)).perform(ViewActions.click());
//
//		Espresso.onView(ViewMatchers.withId(R.id.list_view)).check(matches(isDisplayed()));
//	}


}
