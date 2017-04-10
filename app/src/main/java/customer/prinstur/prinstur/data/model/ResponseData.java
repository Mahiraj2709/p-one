
package customer.prinstur.prinstur.data.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ResponseData {

    @SerializedName("user_info")
    @Expose
    private UserInfo userInfo;
    @SerializedName("profile")
    @Expose
    private Profile profile;
    @SerializedName("sess_tok")
    @Expose
    private String sessionToken;

    @SerializedName("session_token")
    @Expose
    private String sessionTokenLogin;
    @SerializedName("fileuploaderror")
    @Expose
    private Object fileuploaderror;

    @SerializedName("content")
    private String staticContent;

    /**
     * 
     * @return
     *     The userInfo
     */
    public UserInfo getUserInfo() {
        return userInfo;
    }

    /**
     * 
     * @param userInfo
     *     The user_info
     */
    public void setUserInfo(UserInfo userInfo) {
        this.userInfo = userInfo;
    }

    /**
     * 
     * @return
     *     The sessionToken
     */
    public String getSessionToken() {
        return sessionToken;
    }

    /**
     * 
     * @param sessionToken
     *     The session_token
     */
    public void setSessionToken(String sessionToken) {
        this.sessionToken = sessionToken;
    }

    /**
     * 
     * @return
     *     The fileuploaderror
     */
    public Object getFileuploaderror() {
        return fileuploaderror;
    }

    /**
     * 
     * @param fileuploaderror
     *     The fileuploaderror
     */
    public void setFileuploaderror(Object fileuploaderror) {
        this.fileuploaderror = fileuploaderror;
    }

    public String getStaticContent() {
        return staticContent;
    }

    public void setStaticContent(String staticContent) {
        this.staticContent = staticContent;
    }

    public Profile getProfile() {
        return profile;
    }

    public void setProfile(Profile profile) {
        this.profile = profile;
    }

    public String getSessionTokenLogin() {
        return sessionTokenLogin;
    }

    public void setSessionTokenLogin(String sessionTokenLogin) {
        this.sessionTokenLogin = sessionTokenLogin;
    }
}
