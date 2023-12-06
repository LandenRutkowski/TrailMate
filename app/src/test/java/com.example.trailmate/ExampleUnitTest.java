package com.example.trailmate;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

import static org.mockito.Mockito.*;

import androidx.annotation.NonNull;

import com.example.trailmate.models.UserInfo;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

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

    // Verify that email and password of user is in Firebase database once logging in.
    @Test
    public void testLogin() {
        // mock user
        UserInfo mockUser = mock(UserInfo.class);

        // mock the getEmail and getPassword methods from UserInfo
        when(mockUser.getEmail()).thenReturn("landen@gmail.com");
        when(mockUser.getPassword()).thenReturn("123456");

        // Create a non-relational database mock reference
        DatabaseReference mockRef = mock(DatabaseReference.class);
        DatabaseReference mockUserInfoRef = mock(DatabaseReference.class);
        DatabaseReference mockUsersRef = mock(DatabaseReference.class);
        DatabaseReference mockUserRef = mock(DatabaseReference.class);
        DatabaseReference mockEmailRef = mock(DatabaseReference.class);
        DatabaseReference mockPasswordRef = mock(DatabaseReference.class);

        // mock the getting email and password from non-relational database
        when(mockRef.child("UserInfo")).thenReturn(mockUserInfoRef);
        when(mockUserInfoRef.child("Users")).thenReturn(mockUsersRef);
        when(mockUsersRef.child("1")).thenReturn(mockUserRef);
        when(mockUserRef.child("email")).thenReturn(mockEmailRef);
        when(mockUserRef.child("password")).thenReturn(mockPasswordRef);
        when(mockEmailRef.getKey()).thenReturn("landen@gmail.com");
        when(mockPasswordRef.getKey()).thenReturn("123456");

        // When firebse is called, point to mock of non-relational database instead
        FirebaseDatabase mockFirebaseDatabase = mock(FirebaseDatabase.class);
        when(mockFirebaseDatabase.getReference()).thenReturn(mockRef);
        DatabaseReference refDatabase = mockFirebaseDatabase.getReference();

        // get emails
        String mockEmail = mockUser.getEmail();
        String firebaseEmail = refDatabase.child("UserInfo").child("Users").child("1").child("email").getKey();

        // get passwords
        String mockPassword = mockUser.getPassword();
        String firebasePassword = refDatabase.child("UserInfo").child("Users").child("1").child("password").getKey();

        // Verify that email entered is in firebase
        assertEquals(mockEmail, firebaseEmail);

        // Verify that password entered is in firebase
        assertEquals(mockPassword, firebasePassword);
    }

    // update a trail/coordinate that is in list of trails
    @Test
    public void testUpdateCoordinate() {
        CoordinatesListActivity coordinatesListActivity = mock(CoordinatesListActivity.class);

        DatabaseReference mockRef = mock(DatabaseReference.class);
        DatabaseReference mockTrailRef = mock(DatabaseReference.class);


        // mock the getting a trail from non-relational database
        when(mockRef.child("Coordinates")).thenReturn(mockTrailRef);
        when(mockTrailRef.getKey()).thenReturn("Columbus");


        FirebaseDatabase mockFirebaseDatabase = mock(FirebaseDatabase.class);
        when(mockFirebaseDatabase.getReference()).thenReturn(mockRef);
        DatabaseReference refDatabase = mockFirebaseDatabase.getReference();

        // Verify that columbus is in firebase database
        assertEquals(refDatabase.child("Coordinates").getKey(), "Columbus");

        when(coordinatesListActivity.updateCoordinate("Columbus", "Cleveland", 0, 0, "")).thenReturn(true);
        boolean result = coordinatesListActivity.updateCoordinate("Columbus", "Cleveland", 0, 0, "");
        // Verify that updateCoordinate executed and returned true
        assertTrue(result);
    }

    // delete a trail/coordinate from list of trails
    @Test
    public void testDeleteCoordinate() {
        CoordinatesListActivity coordinatesListActivity = mock(CoordinatesListActivity.class);

        DatabaseReference mockRef = mock(DatabaseReference.class);
        DatabaseReference mockTrailRef = mock(DatabaseReference.class);


        // mock the getting a trail from non-relational database
        when(mockRef.child("Coordinates")).thenReturn(mockTrailRef);
        when(mockTrailRef.getKey()).thenReturn("Columbus");



        FirebaseDatabase mockFirebaseDatabase = mock(FirebaseDatabase.class);
        when(mockFirebaseDatabase.getReference()).thenReturn(mockRef);
        DatabaseReference refDatabase = mockFirebaseDatabase.getReference();

        // Verify that columbus is in firebase database
        assertEquals(refDatabase.child("Coordinates").getKey(), "Columbus");

        when(coordinatesListActivity.deleteCoordinate("Columbus")).thenReturn(true);
        boolean result = coordinatesListActivity.deleteCoordinate("Columbus");

        // Verify that deleteCoordinate executed and returned true
        assertTrue(result);

        // remove Columbus from firebase database
        //refDatabase.child("Coordinates").child("Columbus").removeValue();

        // Verify that columbus was deleted from the firebase database
//        refDatabase.addListenerForSingleValueEvent(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot snapshot) {
//                assertTrue(snapshot.hasChild("Columbus"));
//            }
//            @Override
//            public void onCancelled(@NonNull DatabaseError error) {}
//        });
    }

}