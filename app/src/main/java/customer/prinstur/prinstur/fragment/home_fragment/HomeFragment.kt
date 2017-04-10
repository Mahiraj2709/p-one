package customer.prinstur.prinstur.fragment.home_fragment

import android.content.Context
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.InflateException
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.android.gms.maps.GoogleMap
import customer.prinstur.prinstur.PrinsturApplication
import customer.prinstur.prinstur.R
import customer.prinstur.prinstur.interfaces.ApplicationMetadata


/**
 * Created by admin on 11/22/2016.
 */

class HomeFragment : Fragment() {
    private var map: GoogleMap? = null
    private var presenter: HomePresenter? = null

    private var mapType = ApplicationMetadata.SHOW_ALL_MECH

    override fun onAttach(context: Context?) {
        super.onAttach(context)
    }

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        if (homeView != null) {
            val parent = homeView!!.parent as ViewGroup
            parent?.removeView(homeView)
        } else {
            PrinsturApplication.getBus().register(this)
        }
        try {
            homeView = inflater!!.inflate(R.layout.content_main, container, false)
        } catch (e: InflateException) {
            /* map is already there, just return view as it is */
        }
        return view
    }

    override fun onStart() {
        super.onStart()

    }

    /*@OnClick(R.id.ll_searchLocation)
    fun launchSearchLocation() {
        presenter!!.searchLocation()
    }

    @OnClick(R.id.iv_currentLocation)
    fun moveToMyLocation() {
        presenter!!.moveToCurrentLocation()
    }*/


    /*override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (requestCode == PLACE_AUTOCOMPLETE_REQUEST_CODE) {
            if (resultCode == RESULT_OK) {
                val place = PlaceAutocomplete.getPlace(activity, data!!)
                Log.i(TAG, "Place: " + place.name)
                //set the text view with this location
                presenter!!.setSearchPlace(place)
            } else if (resultCode == PlaceAutocomplete.RESULT_ERROR) {
                val status = PlaceAutocomplete.getStatus(activity, data!!)
                // TODO: Handle the error.
                Log.i(TAG, status.statusMessage)

            } else if (resultCode == RESULT_CANCELED) {
                // The user canceled the operation.
            }

            when (requestCode) {
            // Check for the integer request code originally supplied to startResolutionForResult().
                REQUEST_CHECK_SETTINGS -> when (resultCode) {
                    RESULT_OK -> {
                        Log.e("Settings", "Result OK")
                        Toast.makeText(activity, "GPS is Enabled in your device", Toast.LENGTH_SHORT).show()
                    }
                    RESULT_CANCELED -> {
                        Log.e("Settings", "Result Cancel")
                        Toast.makeText(activity, "GPS is disabaled in your device", Toast.LENGTH_SHORT).show()
                    }
                }//startLocationUpdates();
            }
        }
    }

    override fun onRequestPermissionsResult(requestCode: Int,
                                            permissions: Array<String>,
                                            grantResults: IntArray) {
        when (requestCode) {
            REQUEST_PERMISSION_ACCESS_LOCATION -> if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                presenter!!.enableCurrentLocation()
            } else {
                *//*DialogFactory.createSimpleOkErrorDialog(activity,
                        R.string.title_permissions,
                        R.string.permission_not_accepted_access_location).show()*//*
            }
            else -> super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        }
    }*/

    override fun onResume() {
        super.onResume()
        //presenter!!.onResume()
    }

    override fun onPause() {
        super.onPause()
        //presenter!!.onPause()
    }

    override fun onStop() {
        super.onStop()
        //presenter!!.onStop()
    }

    /*@OnClick(R.id.tv_sendRequest)
    fun sendRequest() {
        //check if the service list is there

        presenter!!.openSendRequest()

    }*/

    override fun onDestroy() {
        super.onDestroy()
    }

    /*fun updateMapForOnlineMech(onlineMechs: ArrayList<Mechanic>) {
        //mapFragment.showOnlineMechanicOnMap(onlineMechs);
    }*/



    /*override fun setMapType(mapType: Int) {
        //map type
    }

    override fun setCurrentLocation(currentLocation: LatLng) {
        if (map != null) {
            map!!.animateCamera(CameraUpdateFactory.newLatLngZoom(currentLocation, ApplicationMetadata.MAP_ZOOM_VALUE))
        }
    }

    override fun changeAddress(address: String) {
        activity.runOnUiThread { tv_locationName.setText(address) }
    }

    //when map is moved hide all view
    override fun hideViews() {
       *//* ll_bottomBar.animate().translationY(200)
        tv_sendRequest.animate().translationY(200)
        iv_currentLocation.animate().translationY(200)*//*
    }

    //when map is idle show all view
    override fun showViews() {
        *//*ll_bottomBar.animate().translationY(0)
        tv_sendRequest.animate().translationY(0)
        iv_currentLocation.animate().translationY(0)*//*
    }

    override fun showAddressLoadingProgressBar() {
        pb_addressLoading.setVisibility(View.VISIBLE)
        tv_place_marker.setVisibility(View.GONE)
    }

    override fun hideAddressLoadingProgressBar() {
        activity.runOnUiThread {
            pb_addressLoading.setVisibility(View.GONE)
            tv_place_marker.setVisibility(View.VISIBLE)
        }
    }


    override fun initialSetup() {
        val locationUtils = LocationUtils(activity)
        locationUtils.showSettingDialog()

        //receiveNotification(testAcceptedUser());
        val notificationData = activity.intent.getStringExtra(ApplicationMetadata.NOTIFICATION_DATA)
        val notificationType = activity.intent.getIntExtra(ApplicationMetadata.NOTIFICATION_TYPE, -1)
        if (notificationData != null && notificationType == ApplicationMetadata.NOTIFICATION_REQ_ACCEPTED) {
           *//* presenter!!.setRequestAcceptedMech(Gson().fromJson<AllMechanic>(notificationData, AllMechanic::class.java!!))
            mapType = ApplicationMetadata.SHOW_MECH_REQUEST*//*
            //setMapType(mapType);
        } else {

        }
    }*/

    /*override fun searchLocation() {
        try {
            val intent = PlaceAutocomplete.IntentBuilder(PlaceAutocomplete.MODE_OVERLAY)
                    .build(activity)
            startActivityForResult(intent, PLACE_AUTOCOMPLETE_REQUEST_CODE)
        } catch (e: GooglePlayServicesRepairableException) {
            // TODO: Handle the error.
        } catch (e: GooglePlayServicesNotAvailableException) {
            // TODO: Handle the error.
        }

    }*/

    /*override fun enableMapCurrentLocation() {
        map!!.isMyLocationEnabled = true
        map!!.uiSettings.isMyLocationButtonEnabled = false
        iv_currentLocation.setVisibility(View.VISIBLE)
    }*/

    /*override fun showOnlineMechanics(onlineManics: List<Mechanic>) {
        //show mechanic on the map
        if (map != null) {
            map!!.clear()

            //add inner circle
            map!!.addCircle(CircleOptions()
                    .center(Globals.getUserLatLng())
                    .radius(300.0)
                    .strokeWidth(0f)
                    .fillColor(resources.getColor(R.color.colorMapCircle)))

            //add outer circle
            map!!.addCircle(CircleOptions()
                    .center(Globals.getUserLatLng())
                    .radius(600.0)
                    .strokeWidth(0f)
                    .fillColor(resources.getColor(R.color.colorMapCircle)))

            for (mech in onlineManics) {
                try {
                    val latLng = LatLng(java.lang.Double.parseDouble(mech.getLat()), java.lang.Double.parseDouble(mech.getLng()))
                    val marker = MarkerOptions().position(latLng).title(
                            (if (mech.getAvgRating() != null) mech.getAvgRating() else "0") + ":" + mech.getHourlyServiceCharges())
                    // Changing marker icon
                    marker.icon(BitmapDescriptorFactory.fromResource(R.drawable.ic_map_icon))
                    map!!.addMarker(marker)
                } catch (e: NumberFormatException) {
                    //the parseDouble failed and you need to handle it here
                    Log.e(TAG, e.toString())
                }

            }
        }
    }

    override fun showAcceptedMechanics(allMechanic: AllMechanic) {

        if (map != null) {
            map!!.clear()
        }
        val latLngs = ArrayList<LatLng>()
        for (mech in allMechanic.mechanicList) {
            Log.i(TAG, "" + allMechanic.mechanicList.size())
            val mechLatLng = LatLng(java.lang.Double.parseDouble(mech.latitude), java.lang.Double.parseDouble(mech.longitude))
            latLngs.add(mechLatLng)
            if (map != null) {
                //add inner circle
                map!!.addMarker(MarkerOptions().position(mechLatLng).icon(BitmapDescriptorFactory.fromResource(R.drawable.ic_map_icon)).title((if (mech.avg_rate != null) mech.avg_rate else "0") + ":" + mech.offer_price + ":" + mech.app_provider_id + ":" + allMechanic.request_id))
                map!!.setOnMarkerClickListener { false }
            }
        }

        val centerLatLng = LocationUtils.computeCentroid(latLngs)
        map!!.addCircle(CircleOptions()
                .center(centerLatLng)
                .radius(300.0)
                .strokeWidth(0f)
                .fillColor(resources.getColor(R.color.colorMapCircle)))

        //add outer circle
        map!!.addCircle(CircleOptions()
                .center(centerLatLng)
                .radius(600.0)
                .strokeWidth(0f)
                .fillColor(resources.getColor(R.color.colorMapCircle)))
        map!!.animateCamera(CameraUpdateFactory.newLatLngZoom(centerLatLng, ApplicationMetadata.MAP_ZOOM_VALUE))
    }

    override fun setServices(serviceList: List<Service>) {

        Log.i(TAG, serviceList.size.toString() + "")
        mServiceAdapter = AvailableServicesAdapter(serviceList, context, 0, this@HomeFragment)
        mServiceAdapter.setItemClickListener(object : AvailableServicesAdapter.MyClickListerer() {

            fun onItemClick(position: Int, serviceId: String, view: View) {
                presenter!!.setSelectedServiceId(serviceId)
                presenter!!.getOnlineMechanics()
            }
        })
        rv_servicesView.setAdapter(mServiceAdapter)
    }

    override fun showTimer(time: Int) {
        val anim = ObjectAnimator.ofInt(donut_progress, "progress", time * 60)
        anim.interpolator = DecelerateInterpolator()
        anim.duration = (time * 60 * 1000).toLong()
        anim.start()
        ll_searchLocation.setVisibility(View.GONE)
        circleProgress.setVisibility(View.VISIBLE)
    }

    override fun hideSentRequestTime() {
        activity.runOnUiThread {
            ll_searchLocation.setVisibility(View.VISIBLE)
            circleProgress.setVisibility(View.GONE)
        }
    }

    override fun updateSentRequestTime(time: Int) {
        activity.runOnUiThread { tv_totalTime!!.text = time.toString() + "" }
    }

    override fun onCameraIdle() {
        presenter!!.onCameraIdle(map!!.cameraPosition.target)
    }


    override fun onCameraMoveStarted(i: Int) {
        presenter!!.onCameraMove()
    }

    override fun onMarkerClick(marker: Marker): Boolean {
        marker.showInfoWindow()
        return true
    }

    override fun enableSendRequest() {
        activity.runOnUiThread { tv_sendRequest.setVisibility(View.VISIBLE) }
    }

    override fun disableSendRequest() {
        tv_sendRequest.setVisibility(View.GONE)
    }

    override fun onInfoWindowClick(marker: Marker) {
        presenter!!.infoWindowClicked(marker)
    }

    //receive notification from the customer
    @Subscribe
    fun receiveNotification(allMechanic: AllMechanic) {
        //request has been accepted
        DialogFactory.createAlertDialog(context, allMechanic.message)
        presenter!!.setRequestAcceptedMech(allMechanic)
        mapType = ApplicationMetadata.SHOW_MECH_REQUEST*//**//*
        presenter!!.setMapType(mapType)
    }

    private fun testAcceptedUser(): AllMechanic {
        val allMechanic = AllMechanic()
        allMechanic.total_offer = "1"
        allMechanic.type = "2"
        allMechanic.request_id = "3"
        allMechanic.message = "We found 1 offers for your request"

        val mechanicList = ArrayList<AllMechanic.Mechanic>()
        val mechanic = AllMechanic().Mechanic()
        mechanic.app_provider_id = "1"
        mechanic.latitude = "28.5410496"
        mechanic.avg_rate = "4"
        mechanic.offer_price = "55.00"
        mechanic.longitude = "77.3985685"
        mechanicList.add(mechanic)

        allMechanic.mechanicList = mechanicList
        return allMechanic
    }

    */

    companion object {
        private val PLACE_AUTOCOMPLETE_REQUEST_CODE = 1
        private val REQUEST_CHECK_SETTINGS = 11
        private val REQUEST_PERMISSION_ACCESS_LOCATION = 12
        private val TAG = HomeFragment::class.java.simpleName
        var homeView: View? = null

        fun newInstance(args: Int): HomeFragment {
            val fragment = HomeFragment()
            val data = Bundle()
            data.putInt("args", args)
            fragment.arguments = data
            return fragment
        }
    }
}
