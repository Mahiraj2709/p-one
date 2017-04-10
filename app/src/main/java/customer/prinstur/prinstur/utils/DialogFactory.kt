package customer.prinstur.prinstur.utils

import android.app.Activity
import android.app.Dialog
import android.content.Context
import android.support.annotation.StringRes
import android.support.v7.app.AlertDialog
import android.widget.TextView
import customer.prinstur.prinstur.R
import customer.prinstur.prinstur.app.MainActivity
import customer.prinstur.prinstur.data.DataManager
import customer.prinstur.prinstur.data.local.PrefsHelper
import customer.prinstur.prinstur.interfaces.ApplicationMetadata
import java.util.*

class DialogFactory {

    companion object {
        fun createSimpleOkDialog(context: Context?, message: String?): Dialog {
            val alertDialog = AlertDialog.Builder(context!!).create()
            alertDialog.setCanceledOnTouchOutside(false)
            val view = (context as Activity).layoutInflater.inflate(R.layout.alert_dialog, null)
            var tv_message: TextView = view.findViewById(R.id.tv_notificationMsg) as TextView
            tv_message.text = message
            view.findViewById(R.id.btn_dialog_ok).setOnClickListener { alertDialog.dismiss() }
            alertDialog.setView(view)

            return alertDialog
        }


        fun createLogoutDialog(context: Context, title: String, message: String): Dialog {
            val alertDialog = AlertDialog.Builder(context)
                    .setTitle(title)
                    .setMessage(message)
                    .setNegativeButton(R.string.dialog_no, null)
                    .setPositiveButton(R.string.dialog_yes) { dialog, which ->
                        val dataManager = DataManager(context)
                        val prefsHelper = PrefsHelper(context)
                        val requestParams = HashMap<String, String>()
                        requestParams.put("device_type", "1")
                        requestParams.put(ApplicationMetadata.SESSION_TOKEN, prefsHelper.getPref<Any>(ApplicationMetadata.SESSION_TOKEN) as String)
                        requestParams.put("language", "en")
                        dataManager.logout(requestParams)
                    }
            return alertDialog.create()
        }

        fun createLogoutDialog(context: Context,
                               @StringRes titleResource: Int,
                               @StringRes messageResource: Int): Dialog {

            return createLogoutDialog(context,
                    context.getString(titleResource),
                    context.getString(messageResource))
        }

        fun createExitDialog(context: Context) {
            val alertDialog = android.app.AlertDialog.Builder(context)
            // Setting Dialog Title
            alertDialog.setIcon(R.mipmap.ic_launcher)
                    .setTitle(R.string.app_name)
                    .setMessage("Do you want to exit?")

            alertDialog.setPositiveButton("YES") { dialog, which -> (context as MainActivity).finish() }

            alertDialog.setNegativeButton("NO") { dialog, which -> dialog.cancel() }

            alertDialog.show()
        }

        fun createComingSoonDialog(context: Context) {
            val alertDialog = android.app.AlertDialog.Builder(context)
            // Setting Dialog Title
            alertDialog.setIcon(R.mipmap.ic_launcher)
                    .setTitle(R.string.app_name)
                    .setMessage("Coming Soon.")

            alertDialog.setPositiveButton("OK", null)


            alertDialog.show()
        }

        fun createRatingDialog(context: Context): AlertDialog {
            val alertDialog = AlertDialog.Builder(context!!).create()
            alertDialog.setCanceledOnTouchOutside(false)
            val view = (context as Activity).layoutInflater.inflate(R.layout.rate_reiview_dialog, null)
            view.findViewById(R.id.btn_rateNow).setOnClickListener {
                AppUtils.launchPlayStore(context)
                PrefsHelper(context).savePref(ApplicationMetadata.RATE_REVIEW,true)
                alertDialog.dismiss()
            }
            view.findViewById(R.id.btn_later).setOnClickListener {
                alertDialog.dismiss()
            }
            view.findViewById(R.id.btn_noThanks).setOnClickListener {
                PrefsHelper(context).savePref(ApplicationMetadata.RATE_REVIEW,true)
                alertDialog.dismiss()
            }
            alertDialog.setView(view)
            return alertDialog
        }
    }

}
