package customer.prinstur.prinstur.service

import android.util.Log

import com.google.firebase.iid.FirebaseInstanceId
import com.google.firebase.iid.FirebaseInstanceIdService

import customer.prinstur.prinstur.data.local.PrefsHelper
import customer.prinstur.prinstur.interfaces.ApplicationMetadata


/**
 * Created by admin on 12/20/2016.
 */
class MyFirebaseInstanceIDService : FirebaseInstanceIdService() {
    override fun onTokenRefresh() {
        // Get updated InstanceID token.
        val refreshedToken = FirebaseInstanceId.getInstance().token
        Log.d(TAG, "Refreshed token: " + refreshedToken!!)

        // If you want to send messages to this application instance or
        // manage this apps subscriptions on the server side, send the
        // Instance ID token to your app server.

        //save this deivice token
        PrefsHelper(baseContext).savePref(ApplicationMetadata.DEVICE_TOKEN, refreshedToken)
    }

    companion object {
        private val TAG = "MyFirebaseIIDService"
    }
}
