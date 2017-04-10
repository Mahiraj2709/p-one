package customer.prinstur.prinstur.app

import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.Toolbar
import android.widget.EditText
import customer.prinstur.prinstur.R
import customer.prinstur.prinstur.data.DataManager
import customer.prinstur.prinstur.data.local.PrefsHelper
import customer.prinstur.prinstur.interfaces.ApplicationMetadata
import customer.prinstur.prinstur.utils.CommonMethods
import customer.prinstur.prinstur.utils.DialogFactory
import droidninja.filepicker.FilePickerBuilder
import kotlinx.android.synthetic.main.activity_login.*
import java.util.*

class LoginActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            window.statusBarColor = resources.getColor(R.color.colorPrimaryDark)
        }

        val toolbar = findViewById(R.id.toolbar) as Toolbar
        setSupportActionBar(toolbar)

        tv_toolbarHeader.text = getString(R.string.title_login)
        tv_forgotPassword.setOnClickListener {
            val loginIntent = Intent(this, ForgotPasswordActivity::class.java)
            startActivity(loginIntent)
        }
        tv_registerNow.setOnClickListener {
            val loginIntent = Intent(this, RegisterActivity::class.java)
            startActivity(loginIntent)
        }
        btn_login.setOnClickListener {
            //loginUser()
            FilePickerBuilder.getInstance().setMaxCount(10)
                    //.setSelectedFiles(filePaths)
                    .setActivityTheme(R.style.AppTheme)
                    .pickFile(this);
        }

        //check if already rated
        if (!PrefsHelper(this).isPrefExists(ApplicationMetadata.RATE_REVIEW)) {
            DialogFactory.createRatingDialog(this).show()
        }
    }

    private fun loginUser() {
        if (validCredentdial()) {
            val loginDetails = HashMap<String, String>()
            loginDetails.put("email", getString(et_emailId))
            loginDetails.put("password", getString(et_password))
            loginDetails.put("device_type", "1")
//            loginDetails.put("device_token", if (PrefsHelper(this).getPref(ApplicationMetadata.DEVICE_TOKEN) != null) PrefsHelper(this).getPref(ApplicationMetadata.DEVICE_TOKEN) else "")
            loginDetails.put("device_token", "na")
            loginDetails.put("device_id", CommonMethods.getDeviceId(this))

            loginDetails.put("latitude",  "0.0")
            loginDetails.put("longitude", "0.0")
            loginDetails.put("language", ApplicationMetadata.LANG_ENGLISH)

            val dataManager = DataManager(this)
            dataManager.login(loginDetails)
        }
    }

    private fun validCredentdial(): Boolean {
        if (!CommonMethods.isEmailValid(this, et_emailId.text.toString().trim { it <= ' ' })) {
            return false
        } else if (et_password.text.toString().isEmpty()) {
            DialogFactory.createSimpleOkDialog(this,getString(R.string.valid_msg_empty_password)).show()
            return false
        } else if (et_password.text.toString().length < 6) {
            DialogFactory.createSimpleOkDialog(this,getString(R.string.msg_password_lenght)).show()
            return false
        }
        return true
    }

    private fun getString(editText: EditText?): String {
        if (editText != null) {
            return editText.text.toString()
        }
        return ""
    }
}
