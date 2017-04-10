package customer.prinstur.prinstur.data

import android.app.Activity
import android.app.ProgressDialog
import android.content.Context
import android.content.Intent
import android.util.Log
import customer.prinstur.prinstur.R
import customer.prinstur.prinstur.app.LoginActivity
import customer.prinstur.prinstur.app.MainActivity
import customer.prinstur.prinstur.data.local.PrefsHelper
import customer.prinstur.prinstur.data.model.ApiResponse
import customer.prinstur.prinstur.data.remote.PrinsturService
import customer.prinstur.prinstur.interfaces.ApplicationMetadata
import customer.prinstur.prinstur.utils.DialogFactory
import customer.prinstur.prinstur.utils.NetworkUtil
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

/**
 * Created by admin on 1/27/2017.
 */
class DataManager {
    private val TAG = DataManager::class.java.simpleName
    private var mApiService: PrinsturService? = null
    private var prefsHelper: PrefsHelper? = null
    private var mContext: Context? = null
    private var mCallback: RequestCallback? = null
    private var onlineMechCallback: OnOnlineMechCallback? = null
    private var mLocationUpdateCallback: LocationUpdateCallback? = null

    constructor(context: Context){
        mContext = context
        mApiService = PrinsturService.Factory.makeFairRepairService(context)
        prefsHelper = PrefsHelper(context)
    }

    interface LocationUpdateCallback {
        //        void locationReceived(CustomerLocation location);
        fun locationReceived()
    }

    fun setmLocationUpdateCallback(callback: LocationUpdateCallback) {
        this.mLocationUpdateCallback = callback
    }

    interface RequestCallback {
        fun Data(data: Any)
    }

    fun setCallback(mCallback: RequestCallback) {
        this.mCallback = mCallback
    }

    interface OnOnlineMechCallback {
        //fun onlineMechanics(onlineMechs: List<Mechanic>)
    }

    fun setOnlineMechCallback(callback: OnOnlineMechCallback) {
        onlineMechCallback = callback
    }

    fun signUp(requestMap: Map<String, RequestBody>) {
        if (!NetworkUtil.isNetworkConnected(mContext)) {
            DialogFactory.createSimpleOkDialog(mContext, mContext?.getString(R.string.no_connectin)).show()
            return
        }
        val progressDialog = ProgressDialog(mContext)
        progressDialog.setMessage(mContext?.getString(R.string.msg_loading))
        progressDialog.show()
        val call = mApiService?.signUp(requestMap)
        call?.enqueue(object : Callback<ApiResponse> {
            override fun onResponse(call: Call<ApiResponse>, response: Response<ApiResponse>) {
                val status = response.body().getResponseStatus()

                if (status == ApplicationMetadata.SUCCESS_RESPONSE_STATUS) {
                    val sessionToken = response.body().getResponseData().getSessionToken()
                    val userInfo = response.body().getResponseData().getUserInfo()
                    prefsHelper?.savePref(ApplicationMetadata.SESSION_TOKEN, sessionToken)
                    prefsHelper?.savePref(ApplicationMetadata.USER_ID, userInfo.id)
                    prefsHelper?.savePref(ApplicationMetadata.USER_NAME, userInfo.name)
                    prefsHelper?.savePref(ApplicationMetadata.USER_EMAIL, userInfo.email)
                    prefsHelper?.savePref(ApplicationMetadata.USER_MOBILE, userInfo.phoneNo)
                    prefsHelper?.savePref(ApplicationMetadata.PASSWORD, userInfo.password)
                    prefsHelper?.savePref(ApplicationMetadata.USER_IMAGE, userInfo.profilePic)
                    prefsHelper?.savePref(ApplicationMetadata.USER_LATITUDE, userInfo.latitude)
                    prefsHelper?.savePref(ApplicationMetadata.USER_LONGITUDE, userInfo.longitude)
                    prefsHelper?.savePref(ApplicationMetadata.APP_LANGUAGE, userInfo.language)
                    prefsHelper?.savePref(ApplicationMetadata.USER_ADD_DATE, userInfo.addDate)
                    prefsHelper?.savePref(ApplicationMetadata.USER_MOD_DATE, userInfo.modDate)
                    prefsHelper?.savePref(ApplicationMetadata.LOGIN, true)

                    //launch home screen activity
                    val intent = Intent(mContext, MainActivity::class.java)
                    intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK
                    mContext?.startActivity(intent)
                    (mContext as Activity).finish()
                } else {
                    DialogFactory.createSimpleOkDialog(mContext, response.body().getResponseMsg()).show()
                }
                progressDialog.dismiss()
            }

            override fun onFailure(call: Call<ApiResponse>, t: Throwable) {
                // Log error here since request failed
                progressDialog.dismiss()
                Log.e(TAG, t.toString())
                DialogFactory.createSimpleOkDialog(mContext, mContext?.getString(R.string.msg_server_error)).show()
            }
        })
    }

    //login
    fun login(loginRequest: Map<String, String>) {
        if (!NetworkUtil.isNetworkConnected(mContext)) {
            DialogFactory.createSimpleOkDialog(mContext, mContext?.getString(R.string.no_connectin)).show()
            return
        }
        val progressDialog = ProgressDialog(mContext)
        progressDialog.setMessage(mContext?.getString(R.string.msg_loading))
        progressDialog.show()
        val call = mApiService?.login(loginRequest)
        call?.enqueue(object : Callback<ApiResponse> {
            override fun onResponse(call: Call<ApiResponse>, response: Response<ApiResponse>) {
                val status = response.body().getResponseStatus()
                if (status == ApplicationMetadata.SUCCESS_RESPONSE_STATUS) {
                    val sessionToken = response.body().getResponseData().sessionTokenLogin
                    val userInfo = response.body().getResponseData().profile
                    prefsHelper?.savePref(ApplicationMetadata.SESSION_TOKEN, sessionToken)
                    prefsHelper?.savePref(ApplicationMetadata.USER_ID, userInfo.id)
                    prefsHelper?.savePref(ApplicationMetadata.USER_NAME, userInfo.name)
                    prefsHelper?.savePref(ApplicationMetadata.USER_EMAIL, userInfo.email)
                    prefsHelper?.savePref(ApplicationMetadata.USER_MOBILE, userInfo.phoneNo)
                    prefsHelper?.savePref(ApplicationMetadata.PASSWORD, userInfo.password)
                    prefsHelper?.savePref(ApplicationMetadata.USER_IMAGE, userInfo.profilePic)
                    prefsHelper?.savePref(ApplicationMetadata.STRIPE_ID, userInfo.stripeId)
                    prefsHelper?.savePref(ApplicationMetadata.USER_LATITUDE, userInfo.latitude)
                    prefsHelper?.savePref(ApplicationMetadata.USER_LONGITUDE, userInfo.longitude)
                    prefsHelper?.savePref(ApplicationMetadata.APP_LANGUAGE, userInfo.language)
                    prefsHelper?.savePref(ApplicationMetadata.USER_ADD_DATE, userInfo.addDate)
                    prefsHelper?.savePref(ApplicationMetadata.USER_MOD_DATE, userInfo.modDate)
                    prefsHelper?.savePref(ApplicationMetadata.LOGIN, true)

                    //launch home screen activity
                    val intent = Intent(mContext, MainActivity::class.java)
                    intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK
                    mContext?.startActivity(intent)
                    (mContext as Activity).finish()
                } else {
                    DialogFactory.createSimpleOkDialog(mContext,response.body().getResponseMsg()).show()
                }
                progressDialog.dismiss()
            }

            override fun onFailure(call: Call<ApiResponse>, t: Throwable) {
                // Log error here since request failed
                Log.e(TAG, t.toString())
                progressDialog.dismiss()
                DialogFactory.createSimpleOkDialog(mContext, mContext?.getString(R.string.msg_server_error)).show()
            }
        })
    }

    //forgot password
    fun forgotPassword(forgotPasswordRequest: Map<String, String>) {
        if (!NetworkUtil.isNetworkConnected(mContext)) {
            DialogFactory.createSimpleOkDialog(mContext,  mContext?.getString(R.string.no_connectin)).show()
            return
        }
        val progressDialog = ProgressDialog(mContext)
        progressDialog.setMessage(mContext?.getString(R.string.msg_loading))
        progressDialog.show()
        val call = mApiService?.forgotPassword(forgotPasswordRequest)
        call?.enqueue(object : Callback<ApiResponse> {
            override fun onResponse(call: Call<ApiResponse>, response: Response<ApiResponse>) {
                val status = response.body().getResponseStatus()
                if (status == ApplicationMetadata.SUCCESS_RESPONSE_STATUS) {
                    DialogFactory.createSimpleOkDialog(mContext, response.body().getResponseMsg()).show()
                } else {
                    DialogFactory.createSimpleOkDialog(mContext, response.body().getResponseMsg()).show()
                }
                progressDialog.dismiss()
            }

            override fun onFailure(call: Call<ApiResponse>, t: Throwable) {
                // Log error here since request failed
                Log.e(TAG, t.toString())
                progressDialog.dismiss()
                DialogFactory.createSimpleOkDialog(mContext, mContext?.getString(R.string.msg_server_error)).show()
            }
        })
    }

    //Logout user
    fun logout(logoutRequest: Map<String, String>) {
        if (!NetworkUtil.isNetworkConnected(mContext)) {
            DialogFactory.createSimpleOkDialog(mContext, mContext?.getString(R.string.no_connectin)).show()
            return
        }
        val progressDialog = ProgressDialog(mContext)
        progressDialog.setMessage(mContext?.getString(R.string.msg_loading))
        progressDialog.show()
        val call = mApiService?.logout(logoutRequest)
        call?.enqueue(object : Callback<ApiResponse> {
            override fun onResponse(call: Call<ApiResponse>, response: Response<ApiResponse>) {
                val status = response.body().getResponseStatus()
                if (status == ApplicationMetadata.SUCCESS_RESPONSE_STATUS) {
                    val deviveToken = prefsHelper?.getPref<Any>(ApplicationMetadata.DEVICE_TOKEN) as String
                    prefsHelper?.clearAllPref()
                    prefsHelper?.savePref(ApplicationMetadata.DEVICE_TOKEN, deviveToken)
                    val intent = Intent(mContext, LoginActivity::class.java)
                    intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK
                    mContext?.startActivity(intent)
                    (mContext as Activity).finish()
                } else {
                    DialogFactory.createSimpleOkDialog(mContext, response.body().getResponseMsg()).show()
                }
                progressDialog.dismiss()
            }

            override fun onFailure(call: Call<ApiResponse>, t: Throwable) {
                // Log error here since request failed
                Log.e(TAG, t.toString())
                progressDialog.dismiss()
                DialogFactory.createSimpleOkDialog(mContext, mContext?.getString(R.string.msg_server_error)).show()
            }
        })
    }

    /*//get static content
    fun getStaticPages(requestMap: Map<String, String>, type: String) {
        if (!NetworkUtil.isNetworkConnected(mContext)) {
            DialogFactory.createSimpleOkErrorDialog(mContext, R.string.title_attention, R.string.no_connectin).show()
            return
        }
        val progressDialog = ProgressDialog(mContext)
        progressDialog.setMessage(mContext.getString(R.string.msg_loading))
        progressDialog.show()
        val call = mApiService.getStaticPages(requestMap)
        call.enqueue(object : Callback<ApiResponse>() {
            fun onResponse(call: Call<ApiResponse>, response: Response<ApiResponse>) {
                val status = response.body().getResponseStatus()
                if (status == ApplicationMetadata.SUCCESS_RESPONSE_STATUS) {
                    val content = response.body().getResponseData().getStaticContent()

                    if (type == ApplicationMetadata.ABOUT_CUSTOMER) {
                        //launch about us fragment
                        val newFragment = SupportFragment.newInstance(content)
                        (mContext as MainActivity).addFragmentToStack(newFragment, "support")
                    } else if (type == ApplicationMetadata.TNC_CUSTOMER) {
                        //show tnc dialog
                        val customerDetailFragment = TermsNConditionDialogFragment.newInstance(content)
                        customerDetailFragment.show((mContext as RegisterActivity).getSupportFragmentManager(), "terms_n_condition")
                    }
                } else {
                    progressDialog.dismiss()
                    DialogFactory.createSimpleOkErrorDialog(mContext, response.body().getResponseMsg()).show()
                }
                progressDialog.dismiss()
            }

            fun onFailure(call: Call<ApiResponse>, t: Throwable) {
                // Log error here since request failed
                progressDialog.dismiss()
                Log.e(TAG, t.toString())
                DialogFactory.createSimpleOkErrorDialog(mContext, R.string.title_attention, R.string.msg_server_error).show()
            }
        })
    }

    //get profile of the user
    fun getProfile(requestMap: Map<String, String>) {
        if (!NetworkUtil.isNetworkConnected(mContext)) {
            DialogFactory.createSimpleOkErrorDialog(mContext, R.string.title_attention, R.string.no_connectin).show()
            return
        }
        val progressDialog = ProgressDialog(mContext)
        progressDialog.setMessage(mContext.getString(R.string.msg_loading))
        progressDialog.show()
        val call = mApiService.getProfile(requestMap)
        call.enqueue(object : Callback<ApiResponse>() {
            fun onResponse(call: Call<ApiResponse>, response: Response<ApiResponse>) {
                val status = response.body().getResponseStatus()
                if (status == ApplicationMetadata.SUCCESS_RESPONSE_STATUS) {
                    val userInfo = response.body().getResponseData().getUserInfo()
                    prefsHelper?.savePref(ApplicationMetadata.USER_NAME, userInfo.getName())
                    prefsHelper?.savePref(ApplicationMetadata.USER_EMAIL, userInfo.getEmail())
                    prefsHelper?.savePref(ApplicationMetadata.USER_MOBILE, userInfo.getPhoneNo())
                    prefsHelper?.savePref(ApplicationMetadata.PASSWORD, userInfo.getPassword())
                    prefsHelper?.savePref(ApplicationMetadata.USER_IMAGE, userInfo.getProfilePic())
                    prefsHelper?.savePref(ApplicationMetadata.STRIPE_ID, userInfo.getStripeId())
                    prefsHelper?.savePref(ApplicationMetadata.USER_LATITUDE, userInfo.getLatitude())
                    prefsHelper?.savePref(ApplicationMetadata.USER_LONGITUDE, userInfo.getLongitude())
                    prefsHelper?.savePref(ApplicationMetadata.APP_LANGUAGE, userInfo.getLanguage())
                    prefsHelper?.savePref(ApplicationMetadata.USER_ADD_DATE, userInfo.getAddDate())
                    prefsHelper?.savePref(ApplicationMetadata.USER_MOD_DATE, userInfo.getModDate())

                    val newFragment = MyProfileFragment.newInstance(2)
                    (mContext as MainActivity).addFragmentToStack(newFragment, "my_profile")
                } else {
                    progressDialog.dismiss()
                    DialogFactory.createSimpleOkErrorDialog(mContext, response.body().getResponseMsg()).show()
                }
                progressDialog.dismiss()
            }

            fun onFailure(call: Call<ApiResponse>, t: Throwable) {
                // Log error here since request failed
                progressDialog.dismiss()
                Log.e(TAG, t.toString())
                DialogFactory.createSimpleOkErrorDialog(mContext, R.string.title_attention, R.string.msg_server_error).show()
            }
        })
    }

    //edit profile of the user
    fun editProfile(requestMap: Map<String, RequestBody>) {
        if (!NetworkUtil.isNetworkConnected(mContext)) {
            DialogFactory.createSimpleOkErrorDialog(mContext, R.string.title_attention, R.string.no_connectin).show()
            return
        }
        val progressDialog = ProgressDialog(mContext)
        progressDialog.setMessage(mContext.getString(R.string.msg_loading))
        progressDialog.show()
        val call = mApiService.editProfile(requestMap)
        call.enqueue(object : Callback<ApiResponse>() {
            fun onResponse(call: Call<ApiResponse>, response: Response<ApiResponse>) {
                val status = response.body().getResponseStatus()
                if (status == ApplicationMetadata.SUCCESS_RESPONSE_STATUS) {
                    val userInfo = response.body().getResponseData().getUserInfo()
                    prefsHelper.savePref(ApplicationMetadata.USER_NAME, userInfo.getName())
                    prefsHelper.savePref(ApplicationMetadata.USER_EMAIL, userInfo.getEmail())
                    prefsHelper.savePref(ApplicationMetadata.USER_MOBILE, userInfo.getPhoneNo())
                    prefsHelper.savePref(ApplicationMetadata.PASSWORD, userInfo.getPassword())
                    prefsHelper.savePref(ApplicationMetadata.USER_IMAGE, userInfo.getProfilePic())
                    prefsHelper.savePref(ApplicationMetadata.STRIPE_ID, userInfo.getStripeId())
                    prefsHelper.savePref(ApplicationMetadata.USER_LATITUDE, userInfo.getLatitude())
                    prefsHelper.savePref(ApplicationMetadata.USER_LONGITUDE, userInfo.getLongitude())
                    prefsHelper.savePref(ApplicationMetadata.APP_LANGUAGE, userInfo.getLanguage())
                    prefsHelper.savePref(ApplicationMetadata.USER_ADD_DATE, userInfo.getAddDate())
                    prefsHelper.savePref(ApplicationMetadata.USER_MOD_DATE, userInfo.getModDate())

                    val newFragment = MyProfileFragment.newInstance(2)
                    (mContext as MainActivity).addFragmentToStack(newFragment, "my_profile")
                    (mContext as MainActivity).loadData()

                } else {
                    progressDialog.dismiss()
                    DialogFactory.createSimpleOkErrorDialog(mContext, response.body().getResponseMsg()).show()
                }
                progressDialog.dismiss()
            }

            fun onFailure(call: Call<ApiResponse>, t: Throwable) {
                // Log error here since request failed
                progressDialog.dismiss()
                Log.e(TAG, t.toString())
                DialogFactory.createSimpleOkErrorDialog(mContext, R.string.title_attention, R.string.msg_server_error).show()
            }
        })
    }

    //reset password
    fun resetPassword(requestMap: Map<String, String>) {
        if (!NetworkUtil.isNetworkConnected(mContext)) {
            DialogFactory.createSimpleOkErrorDialog(mContext, R.string.title_attention, R.string.no_connectin).show()
            return
        }
        val progressDialog = ProgressDialog(mContext)
        progressDialog.setMessage(mContext.getString(R.string.msg_loading))
        progressDialog.show()
        val call = mApiService.resetPassword(requestMap)
        call.enqueue(object : Callback<ApiResponse>() {
            fun onResponse(call: Call<ApiResponse>, response: Response<ApiResponse>) {
                val status = response.body().getResponseStatus()
                if (status == ApplicationMetadata.SUCCESS_RESPONSE_STATUS) {
                    DialogFactory.createSimpleOkErrorDialog(mContext, response.body().getResponseMsg()).show()

                    val intent = Intent(mContext, LoginActivity::class.java)
                    intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK
                    mContext.startActivity(intent)
                    (mContext as Activity).finish()
                } else {
                    progressDialog.dismiss()
                    DialogFactory.createSimpleOkErrorDialog(mContext, response.body().getResponseMsg()).show()
                }
                progressDialog.dismiss()
            }

            fun onFailure(call: Call<ApiResponse>, t: Throwable) {
                // Log error here since request failed
                progressDialog.dismiss()
                Log.e(TAG, t.toString())
                DialogFactory.createSimpleOkErrorDialog(mContext, R.string.title_attention, R.string.msg_server_error).show()
            }
        })
    }

    // get all services for customer
    fun resetGetAllServices(requestMap: Map<String, String>) {
        if (!NetworkUtil.isNetworkConnected(mContext)) {
            DialogFactory.createSimpleOkErrorDialog(mContext, R.string.title_attention, R.string.no_connectin).show()
            return
        }
        val progressDialog = ProgressDialog(mContext)
        progressDialog.setMessage(mContext.getString(R.string.msg_loading))
        progressDialog.show()
        val call = mApiService.resetGetAllServices(requestMap)
        call.enqueue(object : Callback<ApiResponse>() {
            fun onResponse(call: Call<ApiResponse>, response: Response<ApiResponse>) {
                val status = response.body().getResponseStatus()
                if (status == ApplicationMetadata.SUCCESS_RESPONSE_STATUS) {

                    val serviceList = response.body().getResponseData().getServices()
                    mCallback!!.Data(serviceList)
                } else {
                    progressDialog.dismiss()
                    DialogFactory.createSimpleOkErrorDialog(mContext, response.body().getResponseMsg()).show()
                }
                progressDialog.dismiss()
            }

            fun onFailure(call: Call<ApiResponse>, t: Throwable) {
                // Log error here since request failed
                progressDialog.dismiss()
                Log.e(TAG, t.toString())
                DialogFactory.createSimpleOkErrorDialog(mContext, R.string.title_attention, R.string.msg_server_error).show()
            }
        })
    }

    // get all services for customer
    fun getOnlineMechs(requestMap: Map<String, String>) {
        if (!NetworkUtil.isNetworkConnected(mContext)) {
            DialogFactory.createSimpleOkErrorDialog(mContext, R.string.title_attention, R.string.no_connectin).show()
            return
        }
        val call = mApiService.getMechByServiceType(requestMap)
        call.enqueue(object : Callback<ApiResponse>() {
            fun onResponse(call: Call<ApiResponse>, response: Response<ApiResponse>) {
                val status = response.body().getResponseStatus()
                if (status == ApplicationMetadata.SUCCESS_RESPONSE_STATUS) {

                    val mechanicList = response.body().getResponseData().getOnlineMech()
                    onlineMechCallback!!.onlineMechanics(mechanicList)

                } else {
                    DialogFactory.createSimpleOkErrorDialog(mContext, response.body().getResponseMsg()).show()
                }
            }

            fun onFailure(call: Call<ApiResponse>, t: Throwable) {
                // Log error here since request failed
                Log.e(TAG, t.toString())
                DialogFactory.createSimpleOkErrorDialog(mContext, R.string.title_attention, R.string.msg_server_error).show()
            }
        })
    }

    // send request to the mechs
    fun sendRequest(requestMap: Map<String, String>) {
        if (!NetworkUtil.isNetworkConnected(mContext)) {
            DialogFactory.createSimpleOkErrorDialog(mContext, R.string.title_attention, R.string.no_connectin).show()
            return
        }
        val progressDialog = ProgressDialog(mContext)
        progressDialog.setMessage(mContext.getString(R.string.msg_loading))
        progressDialog.show()
        val call = mApiService.getSendRequest(requestMap)
        call.enqueue(object : Callback<ApiResponse>() {
            fun onResponse(call: Call<ApiResponse>, response: Response<ApiResponse>) {
                val status = response.body().getResponseStatus()
                if (status == ApplicationMetadata.SUCCESS_RESPONSE_STATUS) {
                    mCallback!!.Data(Any())
                    DialogFactory.createSimpleOkSuccessDialog(mContext, R.string.title_success, response.body().getResponseMsg()).show()
                } else {
                    progressDialog.dismiss()
                    DialogFactory.createSimpleOkErrorDialog(mContext, response.body().getResponseMsg()).show()
                }
                progressDialog.dismiss()
            }

            fun onFailure(call: Call<ApiResponse>, t: Throwable) {
                // Log error here since request failed
                progressDialog.dismiss()
                Log.e(TAG, t.toString())
                DialogFactory.createSimpleOkErrorDialog(mContext, R.string.title_attention, R.string.msg_server_error).show()
            }
        })
    }

    // Get mechanic detail
    fun getMechanicDetail(requestMap: Map<String, String>) {
        if (!NetworkUtil.isNetworkConnected(mContext)) {
            DialogFactory.createSimpleOkErrorDialog(mContext, R.string.title_attention, R.string.no_connectin).show()
            return
        }
        val progressDialog = ProgressDialog(mContext)
        progressDialog.setMessage(mContext.getString(R.string.msg_loading))
        progressDialog.show()
        val call = mApiService.getMechanicDetail(requestMap)
        call.enqueue(object : Callback<ApiResponse>() {
            fun onResponse(call: Call<ApiResponse>, response: Response<ApiResponse>) {
                val status = response.body().getResponseStatus()
                if (status == ApplicationMetadata.SUCCESS_RESPONSE_STATUS) {
                    val mechanicDetail = response.body().getResponseData().getMechanicDetail()
                    mCallback!!.Data(mechanicDetail)
                } else {
                    progressDialog.dismiss()
                    DialogFactory.createSimpleOkErrorDialog(mContext, response.body().getResponseMsg()).show()
                }
                progressDialog.dismiss()
            }

            fun onFailure(call: Call<ApiResponse>, t: Throwable) {
                // Log error here since request failed
                progressDialog.dismiss()
                Log.e(TAG, t.toString())
                DialogFactory.createSimpleOkErrorDialog(mContext, R.string.title_attention, R.string.msg_server_error).show()
            }
        })
    }

    // Add reivew for the mechanic
    fun rateMechanic(requestMap: Map<String, String>) {
        if (!NetworkUtil.isNetworkConnected(mContext)) {
            DialogFactory.createSimpleOkErrorDialog(mContext, R.string.title_attention, R.string.no_connectin).show()
            return
        }
        val progressDialog = ProgressDialog(mContext)
        progressDialog.setMessage(mContext.getString(R.string.msg_loading))
        progressDialog.show()
        val call = mApiService.rateMechanic(requestMap)
        call.enqueue(object : Callback<ApiResponse>() {
            fun onResponse(call: Call<ApiResponse>, response: Response<ApiResponse>) {
                val status = response.body().getResponseStatus()
                if (status == ApplicationMetadata.SUCCESS_RESPONSE_STATUS) {
                    DialogFactory.createSimpleOkSuccessDialog(mContext, R.string.title_success, response.body().getResponseMsg()).show()
                } else {
                    progressDialog.dismiss()
                    DialogFactory.createSimpleOkErrorDialog(mContext, response.body().getResponseMsg()).show()
                }
                progressDialog.dismiss()
            }

            fun onFailure(call: Call<ApiResponse>, t: Throwable) {
                // Log error here since request failed
                progressDialog.dismiss()
                Log.e(TAG, t.toString())
                DialogFactory.createSimpleOkErrorDialog(mContext, R.string.title_attention, R.string.msg_server_error).show()
            }
        })
    }

    // Accept offer by the client
    fun acceptOffer(requestMap: Map<String, String>) {
        if (!NetworkUtil.isNetworkConnected(mContext)) {
            DialogFactory.createSimpleOkErrorDialog(mContext, R.string.title_attention, R.string.no_connectin).show()
            return
        }
        val progressDialog = ProgressDialog(mContext)
        progressDialog.setMessage(mContext.getString(R.string.msg_loading))
        progressDialog.show()
        val call = mApiService.acceptOffer(requestMap)
        call.enqueue(object : Callback<ApiResponse>() {
            fun onResponse(call: Call<ApiResponse>, response: Response<ApiResponse>) {
                val status = response.body().getResponseStatus()
                if (status == ApplicationMetadata.SUCCESS_RESPONSE_STATUS) {
                    mCallback!!.Data(Any())
                } else {
                    progressDialog.dismiss()
                    DialogFactory.createSimpleOkErrorDialog(mContext, response.body().getResponseMsg()).show()
                }
                progressDialog.dismiss()
            }

            fun onFailure(call: Call<ApiResponse>, t: Throwable) {
                // Log error here since request failed
                progressDialog.dismiss()
                Log.e(TAG, t.toString())
                DialogFactory.createSimpleOkErrorDialog(mContext, R.string.title_attention, R.string.msg_server_error).show()
            }
        })
    }

    // Cancel mechanic request
    fun cancelRequest(requestMap: Map<String, String>) {
        if (!NetworkUtil.isNetworkConnected(mContext)) {
            DialogFactory.createSimpleOkErrorDialog(mContext, R.string.title_attention, R.string.no_connectin).show()
            return
        }
        val progressDialog = ProgressDialog(mContext)
        progressDialog.setMessage(mContext.getString(R.string.msg_loading))
        progressDialog.show()
        val call = mApiService.cancelRequest(requestMap)
        call.enqueue(object : Callback<ApiResponse>() {
            fun onResponse(call: Call<ApiResponse>, response: Response<ApiResponse>) {
                val status = response.body().getResponseStatus()
                if (status == ApplicationMetadata.SUCCESS_RESPONSE_STATUS) {
                    mCallback!!.Data(Any())
                    DialogFactory.createSimpleOkSuccessDialog(mContext, R.string.title_success, response.body().getResponseMsg()).show()
                } else {
                    progressDialog.dismiss()
                    DialogFactory.createSimpleOkErrorDialog(mContext, response.body().getResponseMsg()).show()
                }
                progressDialog.dismiss()
            }

            fun onFailure(call: Call<ApiResponse>, t: Throwable) {
                // Log error here since request failed
                progressDialog.dismiss()
                Log.e(TAG, t.toString())
                DialogFactory.createSimpleOkErrorDialog(mContext, R.string.title_attention, R.string.msg_server_error).show()
            }
        })
    }

    // Cancel mechanic request
    fun updateLatLng(requestMap: Map<String, String>) {
        if (!NetworkUtil.isNetworkConnected(mContext)) {
            DialogFactory.createSimpleOkErrorDialog(mContext, R.string.title_attention, R.string.no_connectin).show()
            return
        }
        val call = mApiService.updateLatLng(requestMap)
        call.enqueue(object : Callback<ApiResponse>() {
            fun onResponse(call: Call<ApiResponse>, response: Response<ApiResponse>) {
                val status = response.body().getResponseStatus()
                if (status == ApplicationMetadata.SUCCESS_RESPONSE_STATUS) {
                    mLocationUpdateCallback!!.locationReceived()
                } else {
                }
            }

            fun onFailure(call: Call<ApiResponse>, t: Throwable) {
                // Log error here since request failed
                Log.e(TAG, t.toString())
                DialogFactory.createSimpleOkErrorDialog(mContext, R.string.title_attention, R.string.msg_server_error).show()
            }
        })
    }*/
}