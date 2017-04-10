package customer.prinstur.prinstur.app

import android.os.Bundle
import android.support.design.widget.NavigationView
import android.support.v4.app.Fragment
import android.support.v4.view.GravityCompat
import android.support.v4.widget.DrawerLayout
import android.support.v7.app.ActionBarDrawerToggle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.Toolbar
import android.util.Log
import android.view.MenuItem
import customer.prinstur.prinstur.R
import customer.prinstur.prinstur.fragment.home_fragment.HomeFragment
import customer.prinstur.prinstur.utils.DialogFactory
import customer.prinstur.prinstur.utils.ViewUtil

class MainActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {
    private val TAG = "MainActivity"
    private var mStackLevel = 1
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val toolbar = findViewById(R.id.toolbar) as Toolbar
        setSupportActionBar(toolbar)


        val drawer = findViewById(R.id.drawer_layout) as DrawerLayout
        val toggle = ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close)
        drawer.setDrawerListener(toggle)
        toggle.syncState()

        val navigationView = findViewById(R.id.nav_view) as NavigationView
        navigationView.setNavigationItemSelectedListener(this)

        //fragment transections
        if (savedInstanceState == null) {
            Log.i(TAG,"fragment added")
            val newFragment = HomeFragment.newInstance(mStackLevel)
           addFragmentToStack(newFragment,"home_fragment")
        } else {
            mStackLevel = savedInstanceState.getInt("level")
        }
    }

    override fun onBackPressed() {
        val drawer = findViewById(R.id.drawer_layout) as DrawerLayout
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START)
        } else {
            super.onBackPressed()
        }
    }

    fun addFragmentToStack(fragment: Fragment, tag: String) {

        //hide soft keyboard if visible
        ViewUtil.hideKeyboard(this)
        mStackLevel++
        // Add the fragment to the activity, pushing this transaction
        // on to the back stack.
        val transaction = supportFragmentManager.beginTransaction()
        /*
        if (lastFragment != null)
            transaction.remove(lastFragment);
*/
        transaction.add(R.id.fl_container, fragment, tag)
        transaction.addToBackStack(null)
        transaction.commit()

        //        lastFragment = fragment;
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        // Handle navigation view item clicks here.
        val id = item.itemId

        if (id == R.id.nav_home) {
            DialogFactory.createSimpleOkDialog(this,"Comming Soon").show()
        } else if (id == R.id.nav_myProfile) {
            DialogFactory.createSimpleOkDialog(this,"Comming Soon").show()
        } else if (id == R.id.nav_serviceHistory) {
            DialogFactory.createSimpleOkDialog(this,"Comming Soon").show()
        } else if (id == R.id.nav_resetPassword) {
            DialogFactory.createSimpleOkDialog(this,"Comming Soon").show()
        } else if (id == R.id.nav_support) {
            DialogFactory.createSimpleOkDialog(this,"Comming Soon").show()
        } else if (id == R.id.nav_logout) {
            DialogFactory.createLogoutDialog(this, R.string.logout, R.string.logout_confirm).show()
        }

        val drawer = findViewById(R.id.drawer_layout) as DrawerLayout
        drawer.closeDrawer(GravityCompat.START)
        return true
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putInt("level", mStackLevel)
    }
}
