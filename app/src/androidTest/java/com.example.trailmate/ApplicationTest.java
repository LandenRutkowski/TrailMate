package com.example.trailmate;

import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import androidx.annotation.NonNull;
import androidx.test.core.app.ActivityScenario;
import androidx.test.espresso.Espresso;
import androidx.test.espresso.action.ViewActions;
import androidx.test.espresso.matcher.ViewMatchers;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import com.example.trailmate.models.UserInfo;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.HashMap;
import java.util.Map;



/**
 * <a href="http://d.android.com/tools/testing/testing_android.html">Testing Fundamentals</a>
 */
@RunWith(AndroidJUnit4.class)
public class ApplicationTest {

	@Test
	public void testLoginButton() {

		ActivityScenario.launch(MainActivity.class);
		Espresso.onView(ViewMatchers.withId(R.id.idBtnTrails)).perform(ViewActions.click());


	}

	@Test
	public void testLogoutButton() {
		// Assuming there's a button for creating an account with the ID R.id.idBtnSendData
		Espresso.onView(ViewMatchers.withId(R.id.idBtnSendData)).perform(ViewActions.click());


	}

	@Test
	public void testAddTrailSuccessful() {

		Espresso.onView(ViewMatchers.withId(R.id.save_button)).perform(ViewActions.click());

		DatabaseReference dbRef = FirebaseDatabase.getInstance().getReference().child("Coordinates");

		Map<String, Object> trailData = new HashMap<>();
		trailData.put("name", "Your Trail Name");
		trailData.put("location", "Trail Location");
		DatabaseReference newTrailRef = dbRef.push();
		newTrailRef.setValue(trailData);
		String yourTrailId = newTrailRef.getKey();

		dbRef.addListenerForSingleValueEvent(new ValueEventListener() {
			@Override
			public void onDataChange(@NonNull DataSnapshot snapshot) {
				boolean trailAdded = snapshot.hasChild(yourTrailId);

				assert trailAdded;
			}

			@Override
			public void onCancelled(@NonNull DatabaseError error) {

			}
		});
	}
}
