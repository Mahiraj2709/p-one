package customer.prinstur.prinstur.app

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import customer.prinstur.prinstur.R
import kotlinx.android.synthetic.main.activity_fare_calculate.*

class FareCalculateActivity : AppCompatActivity() {
    private var p:Float = 0.0f
    private var r:Float = 0f
    private var n:Float = 0f
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_fare_calculate)
        btn_calculate.setOnClickListener { calculate() }

    }

    private fun calculate():Float{
        p = et_amount.text.toString().toFloat()
        r = et_rate.text.toString().toFloat()
        n = et_time.text.toString().toFloat()

        var i = (r.div(12)).div(100)

        var value = p * ((Math.pow((1+ i).toDouble(),(n * 12).toDouble()) - 1).div(i)) * (1+ i)

        tv_amount.text = value.toString()
        return value.toFloat()
    }
}
