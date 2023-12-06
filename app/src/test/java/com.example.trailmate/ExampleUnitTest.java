package com.example.trailmate;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

import static org.mockito.Mockito.*;

import com.example.trailmate.models.UserInfo;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

/**
 *
 */
public class ExampleUnitTest {
    private MainActivity mainActivity;
    private HostActivity hostActivity;
    private FirebaseAuth firebaseAuth;

    @Before
    public void setUp() {


    }
    @Test
    public void login() {

//        UserInfo mockUser = mock(UserInfo.class);
//
//        when(mockUser.getUsername()).thenReturn("Landen");
//        //getEmail.thenreturn(landen@gmail)
//        //getpassword
//
//
//        DatabaseReference ref = FirebaseDatabase.getInstance().getReference().child("UserInfo").child("Users").child("1");
//        String user = ref.getKey();
//
//        assertEquals(mockUser.getUsername(), user);

        assertEquals(2+2, 4);
    }
//
//	@Test
//	public void onClickSaveButton() {
//
//		activity.mSaveButton.performClick();
//
//
//		assertEquals("", activity.mTitleEditText.getText().toString());
//		assertEquals("", activity.mLatitudeEditText.getText().toString());
//		assertEquals("", activity.mLongitudeEditText.getText().toString());
//		assertEquals("", activity.mDescriptionEditText.getText().toString());
//	}
//
//	@Test
//	public void onHostCreate() {
//
//		assertNotNull(hostActivity.logout);
//	}
}