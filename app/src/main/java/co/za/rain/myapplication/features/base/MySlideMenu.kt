
package co.za.rain.myapplication.features.base
/*
import android.app.Activity
import android.graphics.Typeface
import android.os.Build
import android.os.Bundle
import android.view.*
import android.view.animation.Animation
import android.widget.FrameLayout
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.widget.Toolbar
import androidx.coordinatorlayout.widget.CoordinatorLayout
import androidx.core.view.GravityCompat
import androidx.databinding.DataBindingUtil.setContentView
import androidx.drawerlayout.widget.DrawerLayout
import co.za.rain.myapplication.R
import co.za.rain.myapplication.extensions.blinkView
import co.za.rain.myapplication.features.base.activity.BaseActivity
import com.google.android.material.navigation.NavigationView

interface MySlideMenu : NavigationView.OnNavigationItemSelectedListener {
    var activity: Activity
    var menu2: Int
    var mainLayout: ViewStub?
    var currentActivityCL: CoordinatorLayout? 
    var toolbar: Toolbar? 
    var slideMenu: Menu? 
    var navigationView: NavigationView? 
    var parentLayout: View? 
    var placeHolderView: FrameLayout? 
    var layoutResource: Int
    var sideMenu: Int
    var selectedItem: MenuItem? 
    var drawer: DrawerLayout? 
    var btnMenuTriggerImgBtn: ImageButton? 
    var currentStatusBarHeight:Int

     fun init() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            activity.window.setFlags(
                WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS,
                WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
        }

        currentStatusBarHeight = getStatusBarHeight()
    }

    fun getStatusBarHeight(): Int {
        var result = 0
        val resourceId = activity.resources.getIdentifier("status_bar_height", "dimen", "android")
        if (resourceId > 0) {
            result = activity.resources.getDimensionPixelSize(resourceId)
        }
        return result
    }

    open fun onMenuItemClicked(view: View) {
        view.blinkView(0.5f, 1.0f, 100, 2, Animation.ABSOLUTE, 0, {
            drawer?.openDrawer(GravityCompat.START)
        })
        // btnMenuTriggerImgBtn?.visibility = View.INVISIBLE
    }

    fun onBackPressed() {
        val drawer = activity.findViewById<DrawerLayout>(R.id.drawer_layout)
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START)
        }
    }

    fun setcurrentActivityCoordinator() {
        currentActivityCL = activity.findViewById(R.id.clCurrentActivity)
    }

    fun initializeActivityViews() {
        setContentView(R.layout.base_drawer_layout)
        initViews()
    }

    fun setMainLayout(pageLayout: Int) {
        mainLayout = findViewById(R.id.pageLayout)
        mainLayout?.layoutResource = pageLayout
        layoutResource = pageLayout
    }

     fun setSlideMenuDependencies(activity: BaseActivity, title: String, pageLayout: Int, sideMenuRef: Int, showIcon: Boolean, showPlaceholderView: Boolean) {
        setcurrentActivityCoordinator()
        setMainLayout(pageLayout)
        toolbar = activity.findViewById(R.id.tbActionbar)

        val mTitle = toolbar?.findViewById<TextView>(R.id.toolbar_title)
        val logo = toolbar?.findViewById<ImageView>(R.id.toolbar_logo)

        if (title.isEmpty()) {
            mTitle?.visibility = View.GONE
        } else {
            mTitle?.typeface = Typeface.create("sans-serif-thin", Typeface.NORMAL)
            mTitle?.text = title

            mTitle?.visibility = View.VISIBLE
        }

        if (showIcon)
            logo?.visibility = View.VISIBLE
        else
            logo?.visibility = View.GONE

        toolbar?.title = ""

        activity.setSupportActionBar(toolbar)

        placeHolderView = activity.findViewById(R.id.flPlaceHolder)

        if (showPlaceholderView) {
            placeHolderView?.visibility = View.VISIBLE
        }

        navigationView = activity.findViewById(R.id.nav_view) as NavigationView
        navigationView?.setNavigationItemSelectedListener(activity as NavigationView.OnNavigationItemSelectedListener)
        navigationView?.inflateMenu(sideMenuRef)
        slideMenu = navigationView?.menu


        drawer = activity.findViewById(R.id.drawer_layout) as DrawerLayout
        val toggle = ActionBarDrawerToggle(activity, drawer, toolbar,
            R.string.navigation_drawer_open,
            R.string.navigation_drawer_close)
        toggle.syncState()

        drawer?.addDrawerListener(toggle)
        drawer?.addDrawerListener(object: DrawerLayout.DrawerListener{
            override fun onDrawerStateChanged(p0: Int) {
            }

            override fun onDrawerSlide(p0: View, p1: Float) {
            }

            override fun onDrawerClosed(p0: View) {
                if (selectedItem == null)
                    return

                handleSlideMenuItemClicked(selectedItem!!)
            }

            override fun onDrawerOpened(p0: View) {

            }
        })

        parentLayout = mainLayout?.inflate()
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        selectedItem = item
        drawer?.closeDrawers()
        return true
    }

    open fun handleSlideMenuItemClicked(item: MenuItem): Boolean {
        this.selectedItem 
        return false
    }

    fun hidePlaceHoderView() {
        placeHolderView?.visibility = View.GONE
    }
}

*/