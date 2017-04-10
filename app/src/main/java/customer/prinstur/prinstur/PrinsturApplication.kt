package customer.prinstur.prinstur

import android.app.Application
import com.squareup.otto.Bus

/**
 * Created by admin on 1/27/2017.
 */
class PrinsturApplication : Application() {
    companion object{
        var myBus:Bus? = null
        var isVisible = false

        fun getBus(): Bus {
            if (myBus == null) {
                myBus = Bus(com.squareup.otto.ThreadEnforcer.ANY)
            }
            return myBus as Bus
        }

    }
}