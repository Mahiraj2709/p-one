package customer.prinstur.prinstur.data.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

/**
 * Created by admin on 1/27/2017.
 */
class ApiResponse {

    @SerializedName("response_msg")
    @Expose
    private var responseMsg: String? = null
    @SerializedName("response_key")
    @Expose
    private var responseKey: Int? = null
    @SerializedName("response_status")
    @Expose
    private var responseStatus: Int? = null
    @SerializedName("response_invalid")
    @Expose
    private var responseInvalid: Int? = null
    @SerializedName("response_data")
    @Expose
    private var responseData: ResponseData? = null

    /**

     * @return
     * *     The responseMsg
     */
    fun getResponseMsg(): String {
        return responseMsg!!
    }

    /**

     * @param responseMsg
     * *     The response_msg
     */
    fun setResponseMsg(responseMsg: String) {
        this.responseMsg = responseMsg
    }

    /**

     * @return
     * *     The responseKey
     */
    fun getResponseKey(): Int? {
        return responseKey
    }

    /**

     * @param responseKey
     * *     The response_key
     */
    fun setResponseKey(responseKey: Int?) {
        this.responseKey = responseKey
    }

    /**

     * @return
     * *     The responseStatus
     */
    fun getResponseStatus(): Int? {
        return responseStatus
    }

    /**

     * @param responseStatus
     * *     The response_status
     */
    fun setResponseStatus(responseStatus: Int?) {
        this.responseStatus = responseStatus
    }

    /**

     * @return
     * *     The responseInvalid
     */
    fun getResponseInvalid(): Int? {
        return responseInvalid
    }

    /**

     * @param responseInvalid
     * *     The response_invalid
     */
    fun setResponseInvalid(responseInvalid: Int?) {
        this.responseInvalid = responseInvalid
    }

    /**

     * @return
     * *     The responseData
     */
    fun getResponseData(): ResponseData {
        return responseData!!
    }

    /**

     * @param responseData
     * *     The response_data
     */
    fun setResponseData(responseData: ResponseData) {
        this.responseData = responseData
    }
}