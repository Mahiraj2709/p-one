package customer.prinstur.prinstur.app

import android.os.Build
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.Toolbar
import android.widget.TextView
import customer.prinstur.prinstur.R
import customer.prinstur.prinstur.data.DataManager
import customer.prinstur.prinstur.interfaces.ApplicationMetadata
import customer.prinstur.prinstur.utils.CommonMethods
import kotlinx.android.synthetic.main.activity_forgot_password.*
import java.util.*

class ForgotPasswordActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_forgot_password)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            window.statusBarColor = resources.getColor(R.color.colorPrimaryDark)
        }
        val toolbar = findViewById(R.id.toolbar) as Toolbar
        setSupportActionBar(toolbar)
        (findViewById(R.id.tv_toolbarHeader) as TextView).text = getString(R.string.title_forgot_password)

        btn_submit.setOnClickListener {
            if (validEmail()) {
                val manager = DataManager(this)

                val forgotParams = HashMap<String, String>()
                forgotParams.put("email", et_emailId.text.toString())
                forgotParams.put("language", ApplicationMetadata.LANG_ENGLISH)
                manager.forgotPassword(forgotParams)
            }
        }
        tv_login.setOnClickListener {
            onBackPressed()
        }

    }
    private fun validEmail(): Boolean {
        if (!CommonMethods.isEmailValid(this, et_emailId.text.toString())) {
            return false
        }
        return true
    }
}
