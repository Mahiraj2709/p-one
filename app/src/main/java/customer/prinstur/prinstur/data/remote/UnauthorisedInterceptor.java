package customer.prinstur.prinstur.data.remote;

import android.content.Context;

import com.squareup.otto.Bus;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.Response;

public class UnauthorisedInterceptor implements Interceptor {
    Context mContent = null;
    Bus eventBus;

    @Override
    public Response intercept(Chain chain) throws IOException {
        return null;
    }
}
