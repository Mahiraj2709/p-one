package customer.prinstur.prinstur.app

import android.Manifest
import android.annotation.SuppressLint
import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.Paint
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.MediaStore
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.Toolbar
import android.widget.EditText
import android.widget.TextView
import com.theartofdev.edmodo.cropper.CropImage
import customer.prinstur.prinstur.R
import customer.prinstur.prinstur.data.DataManager
import customer.prinstur.prinstur.data.local.PrefsHelper
import customer.prinstur.prinstur.interfaces.ApplicationMetadata
import customer.prinstur.prinstur.utils.CommonMethods
import customer.prinstur.prinstur.utils.DialogFactory
import kotlinx.android.synthetic.main.activity_register.*
import okhttp3.MediaType
import okhttp3.RequestBody
import java.io.File
import java.io.IOException
import java.util.*

class RegisterActivity : BaseActivity() {
    private val LOGIN_PERMISSIONS_REQUEST = 10
    private val permissions = arrayOf(Manifest.permission.READ_PHONE_STATE, Manifest.permission.ACCESS_FINE_LOCATION)
    private var imageUri: Uri? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            window.statusBarColor = resources.getColor(R.color.colorPrimaryDark)
        }
        val toolbar = findViewById(R.id.toolbar) as Toolbar
        setSupportActionBar(toolbar)
        (findViewById(R.id.tv_toolbarHeader) as TextView).text = getString(R.string.title_register)
        tv_terms_n_condition.setPaintFlags(tv_terms_n_condition.getPaintFlags() or Paint.UNDERLINE_TEXT_FLAG)

        tv_login.setOnClickListener {
            onBackPressed()
        }
        tv_terms_n_condition.setOnClickListener {
            /*val prefsHelper = PrefsHelper(this)
            val requestParams = HashMap<String, String>()
            requestParams.put(ApplicationMetadata.PAGE_IDENTIFIER, ApplicationMetadata.TNC_CUSTOMER)
            requestParams.put(ApplicationMetadata.LANGUAGE, "en")

            val dataManager = DataManager(this)
            dataManager.getStaticPages(requestParams, ApplicationMetadata.TNC_CUSTOMER)*/
            DialogFactory.createSimpleOkDialog(this,"Coming soon...")
        }

        image_profile.setOnClickListener {
            if (CropImage.isExplicitCameraPermissionRequired(this)) {
                requestPermissions(arrayOf(Manifest.permission.CAMERA), CropImage.CAMERA_CAPTURE_PERMISSIONS_REQUEST_CODE)
            } else {
                CropImage.startPickImageActivity(this)
            }
        }

        btn_register.setOnClickListener {
            if (hasPermission(permissions[0])) {
                registerCurrentUser()
            } else {
                requestPermissions(permissions, LOGIN_PERMISSIONS_REQUEST)
            }
        }
    }

    private fun registerCurrentUser() {
        if (validCredentials()) {

            val requestmap:HashMap<String,RequestBody> = HashMap()
            requestmap.put("name", RequestBody.create(MediaType.parse("text/plain"), getString(et_name)))
            requestmap.put("email", RequestBody.create(MediaType.parse("text/plain"), getString(et_emailId)))
            requestmap.put("phone_no", RequestBody.create(MediaType.parse("text/plain"), getString(et_phoneNo)))
            requestmap.put("password", RequestBody.create(MediaType.parse("text/plain"), getString(et_password)))
            if (super.currentLocatoin == null) {
                DialogFactory.createSimpleOkDialog(this, "Request address..")
            }
            requestmap.put("latitude", RequestBody.create(MediaType.parse("text/plain"), super.currentLocatoin?.getLatitude().toString()))
            requestmap.put("longitude", RequestBody.create(MediaType.parse("text/plain"), super.currentLocatoin?.getLongitude().toString()))
            requestmap.put("device_token", RequestBody.create(MediaType.parse("text/plain"), ""))
            requestmap.put("device_id", RequestBody.create(MediaType.parse("text/plain"), CommonMethods.getDeviceId(this)))
            requestmap.put("device_type", RequestBody.create(MediaType.parse("text/plain"), "1"))
            requestmap.put("language", RequestBody.create(MediaType.parse("text/plain"), "en"))

            if (imageUri != null) {
                val imageFile = File(imageUri!!.getPath())
                requestmap.put("profile_pic\"; filename=\"pp.png\" ", RequestBody.create(MediaType.parse("image/*"), imageFile))
            }

            val manager = DataManager(this)
            manager.signUp(requestmap)
        }
    }

    private fun getString(editText: EditText?): String {
        if (editText != null) {
            return editText.text.toString()
        }
        return ""
    }

    @SuppressLint("NewApi")
    override protected fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent) {
        if (requestCode == CropImage.PICK_IMAGE_CHOOSER_REQUEST_CODE && resultCode == Activity.RESULT_OK) {
            val imageUri = CropImage.getPickImageResultUri(this, data)

            // For API >= 23 we need to check specifically that we have permissions to read external storage.
            if (CropImage.isReadExternalStoragePermissionsRequired(this, imageUri)) {
                requestPermissions(arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE), CropImage.PICK_IMAGE_PERMISSIONS_REQUEST_CODE)
            } else {
                // no permissions required or already grunted, can start crop image activity
                startCropImageActivity(imageUri)
            }
        }
        if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE) {
            val result = CropImage.getActivityResult(data)
            if (resultCode == RESULT_OK) {
                imageUri = result.getUri()
                var thePic: Bitmap? = null
                try {
                    //imagePath = picUri.getPath();
                    thePic = MediaStore.Images.Media.getBitmap(this.contentResolver, imageUri)
                } catch (e: IOException) {
                    e.printStackTrace()
                }

                image_profile.setImageBitmap(thePic)
            } else if (resultCode == CropImage.CROP_IMAGE_ACTIVITY_RESULT_ERROR_CODE) {
                val error = result.getError()
            }
        }
    }

    private fun validCredentials(): Boolean {
        if (et_name.text.toString().trim { it <= ' ' }.isEmpty()) {
            DialogFactory.createSimpleOkDialog(this, getString(R.string.msg_invalid_name)).show()
            return false
        } else if (!CommonMethods.isEmailValid(this, et_emailId.text.toString().trim { it <= ' ' })) {
            return false
        } else if (!CommonMethods.isValidPhoneNo(this, et_phoneNo.text.toString().trim { it <= ' ' })) {
            return false
        } else if (et_phoneNo.text.toString().trim { it <= ' ' }.isEmpty()) {
            DialogFactory.createSimpleOkDialog(this, getString(R.string.msg_invalid_phone_no)).show()
            return false
        } else if (et_password.text.toString().isEmpty()) {
            DialogFactory.createSimpleOkDialog(this, getString(R.string.valid_msg_empty_password)).show()
            return false
        } else if (et_password.text.toString().length < 6) {
            DialogFactory.createSimpleOkDialog(this, getString(R.string.msg_password_lenght)).show()
            return false
        } else if (et_confirmPasswd.text.toString().isEmpty()) {
            DialogFactory.createSimpleOkDialog(this, getString(R.string.msg_empty_confirm_password)).show()
            return false
        } else if (et_confirmPasswd.text.toString().length < 6) {
            DialogFactory.createSimpleOkDialog(this, getString(R.string.msg_password_lenght)).show()
            return false
        } else if (et_confirmPasswd.text.toString() != et_password.text.toString()) {
            DialogFactory.createSimpleOkDialog(this, getString(R.string.password_not_match)).show()
            return false
        } else if (!cb_term_n_condition.isChecked) {
            DialogFactory.createSimpleOkDialog(this, getString(R.string.msg_accept_tnc)).show()
            return false
        }
        return true
    }

    private fun startCropImageActivity(imageUri: Uri) {
        CropImage.activity(imageUri)
                .start(this)
    }

    override fun onRequestPermissionsResult(requestCode: Int,
                                   permissions: Array<String>,
                                   grantResults: IntArray) {
        when (requestCode) {
            LOGIN_PERMISSIONS_REQUEST -> if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                registerCurrentUser()
            } else {
                DialogFactory.createSimpleOkDialog(this, getString(R.string.permission_not_accepted_read_phone_state)).show()
            }
            else -> super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        }
    }


}
