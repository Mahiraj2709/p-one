package customer.prinstur.prinstur.utils

import android.annotation.SuppressLint
import android.app.ProgressDialog
import android.content.Context
import android.graphics.Color
import android.graphics.Point
import android.graphics.Typeface
import android.os.Build
import android.support.v7.app.AlertDialog
import android.telephony.TelephonyManager
import android.text.SpannableStringBuilder
import android.text.TextUtils
import android.text.style.ForegroundColorSpan
import android.util.Log
import android.util.Patterns
import android.view.WindowManager
import android.widget.EditText
import com.google.android.gms.internal.zzs
import customer.prinstur.prinstur.R
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*

class CommonMethods {

    internal var dialog: ProgressDialog? = null

    companion object{
        fun getDeviceId(context: Context): String {
            val manager = context.getSystemService(Context.TELEPHONY_SERVICE) as TelephonyManager
            Log.d(zzs.TAG, manager.deviceId)
            return manager.deviceId
        }

        fun isEmailValid(context: Context, email: String): Boolean {
            if (email.isEmpty()) {
                DialogFactory.createSimpleOkDialog(context, context.getString(R.string.message_email_empty)).show()
                return false
            }
            val matcher = Patterns.EMAIL_ADDRESS.matcher(email)
            if (!matcher.matches()) {
                DialogFactory.createSimpleOkDialog(context, context.getString(R.string.message_email_not_valid)).show()
                return false
            }
            return true
        }

        fun isValidPhoneNo(context: Context, phoneNo: String): Boolean {
            if (phoneNo.isEmpty()) {
                DialogFactory.createSimpleOkDialog(context, context.getString(R.string.msg_invalid_phone_no)).show()
                return false
            }
            if (phoneNo.length != 10) {
                DialogFactory.createSimpleOkDialog(context, context.getString(R.string.msg_invalid_phone_no_length)).show()
                return false
            }
            val matcher = Patterns.PHONE.matcher(phoneNo)
            if (!matcher.matches()) {
                DialogFactory.createSimpleOkDialog(context, context.getString(R.string.msg_invalid_phone_no)).show()
                return false
            }
            return true
        }
    }






    fun dismissDialog() {
        dialog!!.dismiss()
    }

    fun errormessageon_Edittext(message: String, view: EditText) {

        val ecolor = Color.parseColor("#ff0000") // whatever color you want
        val estring = message
        val fgcspan = ForegroundColorSpan(ecolor)
        val ssbuilder = SpannableStringBuilder(estring)
        ssbuilder.setSpan(fgcspan, 0, estring.length, 0)
        view.error = ssbuilder
        view.requestFocus()
    }

    /**
     * method used to get current time

     * @return date and time both
     */
    //        formatter.setTimeZone(TimeZone.getTimeZone("UTC"));
    val formattedCurrentTime: String
        get() {
            val formatter = SimpleDateFormat("yyyy-MM-dd hh:mm:ss")
            formatter.timeZone = TimeZone.getTimeZone("UTC")
            val date = Date()
            val dateTime = formatter.format(date)
            return dateTime
        }

    /**
     * method used to show alert dialog

     * @param string get alert message
     */
    fun showAlert(context: Context, title: String, string: String) {
        // TODO Auto-generated method stub
        val alert = AlertDialog.Builder(context)
        alert.setTitle(title)
        alert.setMessage(string)
        alert.setCancelable(false)
        alert.setPositiveButton(context.getString(R.string.dialog_action_ok)) { dialog, which -> // TODO Auto-generated method stub }
            alert.show()
        }

        /**
         * method used to validate email

         * @param email get email
         * *
         * @return true or false
         */
        fun isValidEmail(email: String): Boolean {
            return !TextUtils.isEmpty(email) && Patterns.EMAIL_ADDRESS.matcher(email).matches()
        }

        /**
         * method used to get age from date of birth

         * @param _month month
         * *
         * @param _day   day
         * *
         * @param _year  year
         * *
         * @return age
         */
        fun getAge(_month: Int, _day: Int, _year: Int): Int {

            val cal = GregorianCalendar()
            val year: Int
            val month: Int
            val date: Int
            var age: Int

            year = cal.get(Calendar.YEAR)
            month = cal.get(Calendar.MONTH)
            date = cal.get(Calendar.DAY_OF_MONTH)
            cal.set(_year, _month, _day)
            age = year - cal.get(Calendar.YEAR)
            if (month < cal.get(Calendar.MONTH) || month == cal.get(Calendar.MONTH) && date < cal.get(Calendar.DAY_OF_MONTH)) {
                --age
            }
            if (age < 0)
                throw IllegalArgumentException("Age < 0")
            return age
        }

        /**
         * method used to change date format

         * @param date date
         * *
         * @return change format date
         */
        fun changeDateFormat(date: String): String {
            var date = date
            val input = SimpleDateFormat("dd/MM/yyyy")
            val output = SimpleDateFormat("MM-dd-yyyy")
            try {
                val oneWayTripDate = input.parse(date)  // parse input
                date = output.format(oneWayTripDate)    // format output
            } catch (e: ParseException) {
                e.printStackTrace()
            }

            return date
        }

        /**
         * method used to change the font

         * @param context get context
         * *
         * @return changed font
         */
        fun headerFont(context: Context): Typeface {
            val headerFont = Typeface.createFromAsset(context.assets, "font/Lato-Regular.ttf")

            return headerFont
        }

        /**
         * method used to change the font

         * @param context get context
         * *
         * @return changed font
         */
        fun boldFont(context: Context): Typeface {
            val headerFont = Typeface.createFromAsset(context.assets, "font/Lato-Bold.ttf")

            return headerFont
        }

        /**
         * method used to change the font

         * @param context get context
         * *
         * @return changed font
         */
        fun normalText(context: Context): Typeface {
            val headerFont = Typeface.createFromAsset(context.assets, "font/Lato-Light.ttf")

            return headerFont
        }

        /**
         * method used to remove a comma from last in string

         * @param str contain comma seprated string
         * *
         * @return string
         */


        @SuppressLint("NewApi")
        fun getWidth(mContext: Context): Int {
            var width = 0
            val wm = mContext.getSystemService(Context.WINDOW_SERVICE) as WindowManager
            val display = wm.defaultDisplay
            if (Build.VERSION.SDK_INT > 12) {
                val size = Point()
                display.getSize(size)
                width = size.x
            } else {
                width = display.width  // deprecated
            }
            return width
        }

        @SuppressLint("NewApi")
        fun getHeight(mContext: Context): Int {
            var height = 0
            val wm = mContext.getSystemService(Context.WINDOW_SERVICE) as WindowManager
            val display = wm.defaultDisplay
            if (Build.VERSION.SDK_INT > 12) {
                val size = Point()
                display.getSize(size)
                height = size.y
            } else {
                height = display.height  // deprecated
            }
            return height
        }


        /**
         * method used to get distance in kilometers

         * @param distance contain distance
         * *
         * @return distance in kilometer
         */
        fun getDistanceInKilometers(distance: Double): Double {
            return distance * 1.60934
        }

        /**
         * method used to get distance in meters

         * @param distance contain distance
         * *
         * @return distance in meter
         */
        fun getDistanceInMeters(distance: Double): Double {
            return distance * 1609.34
        }

        /**
         * method used to get distance in meters

         * @param distance contain distance
         * *
         * @return distance in meter
         */
        fun metersToMiles(distance: Double): Double {
            return distance / 1609.34
        }

        /**
         * method used to convert date format

         * @param oldDate contain old date
         * *
         * @return converted date
         */
        fun getConvertedDate(oldDate: String): String {
            var date: Date? = null
            val parser = SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
            parser.timeZone = TimeZone.getTimeZone("UTC")
            val write = SimpleDateFormat("dd-MMM-yyyy")
            write.timeZone = TimeZone.getDefault()
            try {
                date = parser.parse(oldDate)
            } catch (e: ParseException) {
                e.printStackTrace()
            }

            return write.format(date)
        }
    }
}
