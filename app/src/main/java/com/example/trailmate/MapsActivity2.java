package com.example.trailmate;

import androidx.fragment.app.Fragment;

import timber.log.Timber;

/**
 * Based on adamcchampion.
 * Created by Muhammad Abedeljaber and Landen Rutkowski, 2023/10/05.
 */

import android.app.Activity;
import android.content.Context;
import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import androidx.appcompat.widget.SearchView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.content.res.AppCompatResources;

import com.mapbox.android.core.permissions.PermissionsListener;
import com.mapbox.android.core.permissions.PermissionsManager;
import com.mapbox.android.gestures.MoveGestureDetector;
import com.mapbox.geojson.Point;
import com.mapbox.maps.CameraOptions;
import com.mapbox.maps.MapView;
import com.mapbox.maps.MapboxMap;
import com.mapbox.maps.Style;
import com.mapbox.maps.extension.observable.eventdata.StyleDataLoadedEventData;
import com.mapbox.maps.plugin.LocationPuck2D;
import com.mapbox.maps.plugin.Plugin;
import com.mapbox.maps.plugin.delegates.listeners.OnStyleDataLoadedListener;
import com.mapbox.maps.plugin.gestures.GesturesPlugin;
import com.mapbox.maps.plugin.gestures.OnMoveListener;
import com.mapbox.maps.plugin.locationcomponent.LocationComponentPlugin;
import com.mapbox.maps.plugin.locationcomponent.OnIndicatorBearingChangedListener;
import com.mapbox.maps.plugin.locationcomponent.OnIndicatorPositionChangedListener;
import com.mapbox.maps.plugin.locationcomponent.generated.LocationComponentSettings;

import java.io.IOException;
import java.util.List;

import kotlin.Unit;
import kotlin.jvm.functions.Function1;

/**
 * Created by acc on June 30, 2023.
 *
 * I modified this example based on <a href="https://docs.mapbox.com/android/maps/examples/location-tracking/">a
 *   Mapbox example app/a>.
 */
public class MapsActivity2 extends Fragment implements PermissionsListener, OnStyleDataLoadedListener,
        OnIndicatorBearingChangedListener, OnIndicatorPositionChangedListener, OnMoveListener {

    private final PermissionsManager mPermissionsManager = new PermissionsManager(this);
    private MapView mMapView;
    private LocationPuck2D mLocationPuck;
    private LocationComponentPlugin mLocationPlugin;
    private GesturesPlugin mGesturesPlugin;
    private final String TAG = getClass().getSimpleName();
    private SearchView mSearchView;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        Timber.tag(TAG).d("onCreateView");
        super.onCreateView(inflater, container, savedInstanceState);
        setHasOptionsMenu(true);

        View v = inflater.inflate(R.layout.activity_maps, container, false);

        mMapView = (MapView) v.findViewById(R.id.map_view_location);
        MapboxMap mMapboxMap = mMapView.getMapboxMap();
        setupMap(mMapboxMap);

        mSearchView = (SearchView) v.findViewById(R.id.search_view);
        mSearchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                searchLocation(query);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });


        return v;
    }

    private void searchLocation(String location) {
        Geocoder geocoder = new Geocoder(requireContext());
        List<Address> addresses = null;
        try {
            addresses = geocoder.getFromLocationName(location, 1);
        } catch (IOException e) {
            e.printStackTrace();
        }
        Address address = addresses.get(0);
        double lat = address.getLatitude();
        double lng = address.getLongitude();
        Point point = Point.fromLngLat(lng, lat);
        onIndicatorPositionChanged(point);
    }

//    @Override
//    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
//        super.onCreateOptionsMenu(menu, inflater);
//        inflater.inflate(R.menu.menu_maps, menu);
//    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        final int itemId = item.getItemId();
        final Activity activity = requireActivity();
        if (itemId == R.id.menu_showcurrentlocation) {
            if (!PermissionsManager.areLocationPermissionsGranted(requireContext())) {
                mPermissionsManager.requestLocationPermissions(activity);
            } else {
                MapboxMap map = mMapView.getMapboxMap();
                setupMap(map);
            }
        }
        return false;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        mGesturesPlugin.removeOnMoveListener(this);
        mGesturesPlugin = null;
        mLocationPlugin.removeOnIndicatorBearingChangedListener(this);
        mLocationPlugin.removeOnIndicatorPositionChangedListener(this);
        mLocationPlugin = null;
        MapboxMap map = mMapView.getMapboxMap();
        map.removeOnStyleDataLoadedListener(this);
        mMapView = null;
    }

    @Override
    public void onExplanationNeeded(List<String> permissionsToExplain) {
        final Context ctx = requireContext();
        Toast.makeText(ctx, "You must enable location permissions", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onPermissionResult(boolean granted) {
        if (granted) {
            Timber.d(TAG, "Location permission granted");
            Toast.makeText(requireContext(), "User granted location permission", Toast.LENGTH_SHORT).show();
            initLocation();
            setupGesturesListener();
        } else {
            Timber.d(TAG, "User denied location permission");
            Toast.makeText(requireContext(), "User granted location permission", Toast.LENGTH_SHORT).show();
            onCameraTrackingDismissed();
        }
    }

    private void setupMap(MapboxMap map) {
        CameraOptions cameraOptions = new CameraOptions.Builder()
                .zoom(14.0)
                .build();
        map.setCamera(cameraOptions);
        map.loadStyleUri(Style.MAPBOX_STREETS);
        map.addOnStyleDataLoadedListener(this);
    }


    private void initLocation() {
        final Activity activity = requireActivity();
        if (mLocationPuck == null) {
            mLocationPuck = new LocationPuck2D();
            mLocationPuck.setBearingImage(AppCompatResources.getDrawable(activity, com.mapbox.navigation.dropin.R.drawable.mapbox_navigation_puck_icon2));
            mLocationPuck.setShadowImage(AppCompatResources.getDrawable(activity, com.mapbox.navigation.ui.base.R.drawable.mapbox_ic_navigation));
        }

        mLocationPlugin = mMapView.getPlugin(Plugin.MAPBOX_LOCATION_COMPONENT_PLUGIN_ID);
        if (mLocationPlugin != null) {
            mLocationPlugin.updateSettings(new Function1<LocationComponentSettings, Unit>() {
                @Override
                public Unit invoke(LocationComponentSettings locationComponentSettings) {
                    locationComponentSettings.setEnabled(true);
                    locationComponentSettings.setLocationPuck(mLocationPuck);
                    return null;
                }
            });
            mLocationPlugin.addOnIndicatorBearingChangedListener(this);
            mLocationPlugin.addOnIndicatorPositionChangedListener(this);
        }
    }

    private void setupGesturesListener() {
        mGesturesPlugin = mMapView.getPlugin(Plugin.MAPBOX_GESTURES_PLUGIN_ID);
        if (mGesturesPlugin != null) {
            mGesturesPlugin.addOnMoveListener(this);
        }
    }

    @Override
    public boolean onMove(@NonNull MoveGestureDetector moveGestureDetector) {
        return false;
    }

    @Override
    public void onMoveBegin(@NonNull MoveGestureDetector moveGestureDetector) {
        onCameraTrackingDismissed();
    }

    @Override
    public void onMoveEnd(@NonNull MoveGestureDetector moveGestureDetector) {
        // Nothing here
    }

    @Override
    public void onIndicatorBearingChanged(double v) {

    }

    @Override
    public void onIndicatorPositionChanged(@NonNull Point point) {
        MapboxMap map = mMapView.getMapboxMap();
        CameraOptions.Builder builder = new CameraOptions.Builder();
        map.setCamera(builder.center(point).build());
        mGesturesPlugin.setFocalPoint(map.pixelForCoordinate(point));
    }

    @Override
    public void onStyleDataLoaded(@NonNull StyleDataLoadedEventData styleDataLoadedEventData) {
        initLocation();
        setupGesturesListener();
    }

    private void onCameraTrackingDismissed() {
        Timber.tag(TAG).d("onCameraTrackingDismissed()");
        Toast.makeText(requireActivity(), "onCameraTrackingDismissed", Toast.LENGTH_SHORT).show();
        if (mLocationPlugin != null) {
            mLocationPlugin.removeOnIndicatorPositionChangedListener(this);
            mLocationPlugin.removeOnIndicatorBearingChangedListener(this);
        }
        if (mGesturesPlugin != null) {
            mGesturesPlugin.removeOnMoveListener(this);
        }
    }
}
