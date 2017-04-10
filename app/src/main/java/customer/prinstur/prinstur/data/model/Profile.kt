package customer.prinstur.prinstur.data.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

/**
 * Created by admin on 1/27/2017.
 */

class Profile{

    @SerializedName("customer_id")
    @Expose
    var id: String? = null

    @SerializedName("name")
    @Expose
    var name: String? = null

    @SerializedName("email")
    @Expose
    var email: String? = null

    @SerializedName("phone_no")
    @Expose
    var phoneNo: String? = null

    @SerializedName("password")
    @Expose
    var password: String? = null

    @SerializedName("stripe_id")
    @Expose
    var stripeId: Any? = null

    @SerializedName("profile_pic")
    @Expose
    var profilePic: Any? = null

    @SerializedName("latitude")
    @Expose
    var latitude: String? = null

    @SerializedName("longitude")
    @Expose
    var longitude: String? = null

    @SerializedName("status")
    @Expose
    var status: String? = null

    @SerializedName("language")
    @Expose
    var language: String? = null

    @SerializedName("add_date")
    @Expose
    var addDate: String? = null

    @SerializedName("mod_date")
    @Expose
    var modDate: String? = null

    @SerializedName("is_deleted")
    @Expose
    var isDeleted: String? = null

}
