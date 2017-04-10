package customer.prinstur.prinstur.app

import android.Manifest
import android.annotation.TargetApi
import android.content.pm.PackageManager
import android.location.Location
import android.os.Build
import android.os.Bundle
import android.support.v4.app.ActivityCompat
import android.support.v7.app.AppCompatActivity
import android.util.Log
import com.google.android.gms.common.ConnectionResult
import com.google.android.gms.common.api.GoogleApiClient
import com.google.android.gms.location.LocationListener
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationServices
import customer.prinstur.prinstur.PrinsturApplication
import customer.prinstur.prinstur.utils.LocationUtils
import java.text.DateFormat
import java.util.*


/**
 * Created by admin on 11/22/2016.
 */

open class BaseActivity : AppCompatActivity(), GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener, LocationListener {
    private var mGoogleApiClient: GoogleApiClient? = null
    private var mLocationRequest: LocationRequest? = null
    public var currentLocatoin: Location? = null
    private var mLastUpdateTime: String? = null

    @TargetApi(Build.VERSION_CODES.M)
    fun requestPermissionsSafely(permissions: Array<String>, requestCode: Int) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            requestPermissions(permissions, requestCode)
        }
    }

    @TargetApi(Build.VERSION_CODES.M)
    fun hasPermission(permission: String): Boolean {
        return Build.VERSION.SDK_INT < Build.VERSION_CODES.M || checkSelfPermission(permission) == PackageManager.PERMISSION_GRANTED
    }

    public override fun onStart() {
        super.onStart()
        val locationUtils = LocationUtils(this)
        locationUtils.showSettingDialog()
        buildGoogleApiClient()
        mGoogleApiClient!!.connect()
    }

    public override fun onPause() {
        super.onPause()
        PrinsturApplication.isVisible = false
        stopLocationUpdates()
    }

    protected fun stopLocationUpdates() {
        if (mGoogleApiClient!!.isConnected)
            LocationServices.FusedLocationApi.removeLocationUpdates(
                    mGoogleApiClient, this)
        Log.d(TAG, "Location update stopped .......................")
    }

    public override fun onResume() {
        super.onResume()
        PrinsturApplication.isVisible = true
        if (mGoogleApiClient!!.isConnected) {
            startLocationUpdates()
            Log.d(TAG, "Location update resumed .....................")
        }
    }

    public override fun onStop() {
        super.onStop()
        if (mGoogleApiClient != null && mGoogleApiClient!!.isConnected) {
            mGoogleApiClient!!.disconnect()
        }
    }


    protected fun createLocationRequest() {
        mLocationRequest = LocationRequest()
        mLocationRequest!!.interval = INTERVAL
        mLocationRequest!!.fastestInterval = FASTEST_INTERVAL
        mLocationRequest!!.priority = LocationRequest.PRIORITY_HIGH_ACCURACY
    }

    override fun onLocationChanged(location: Location) {
        Log.i(TAG, location.latitude.toString() + " longitude " + location.longitude)
        Log.d(TAG, "Firing onLocationChanged..............................................")
        currentLocatoin = location
        mLastUpdateTime = DateFormat.getTimeInstance().format(Date())
        Log.i(TAG, "last update time for location is" + mLastUpdateTime!!)

        //move map to the current location
        //moveToLatLng();
    }

    override fun onConnected(bundle: Bundle?) {
        Log.i(TAG, "Connected")
        startLocationUpdates()
    }

    protected fun startLocationUpdates() {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return
        }

        if (mGoogleApiClient!!.isConnected) {
            val pendingResult = LocationServices.FusedLocationApi.requestLocationUpdates(
                    mGoogleApiClient, mLocationRequest, this)
            Log.d(TAG, "Location update started ..............: ")
        }

    }

    override fun onConnectionSuspended(i: Int) {

    }

    override fun onConnectionFailed(connectionResult: ConnectionResult) {

    }

    @Synchronized protected fun buildGoogleApiClient() {
        createLocationRequest()
        mGoogleApiClient = GoogleApiClient.Builder(this)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(LocationServices.API)
                .build()
    }

    companion object {

        private val TAG = BaseActivity::class.java.simpleName
        //send LatLng to the calling class
        private val INTERVAL = (1000 * 60 * 1).toLong() //1 minute
        private val FASTEST_INTERVAL = (1000 * 60 * 1).toLong() // 1 minute
    }
}
