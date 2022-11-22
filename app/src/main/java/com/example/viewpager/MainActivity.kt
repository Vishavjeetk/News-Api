package com.example.viewpager

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.viewpager2.widget.ViewPager2
import com.bumptech.glide.Glide
import com.example.viewpager.adapters.ViewPagerAdapter
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.material.internal.ContextUtils.getActivity
import com.google.android.material.navigation.NavigationView
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import com.google.firebase.auth.FirebaseAuth


class MainActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {

    private lateinit var viewPagerAdapter: ViewPagerAdapter
    private lateinit var navigationView: NavigationView
    private lateinit var drawer: DrawerLayout
    private lateinit var toolbar: Toolbar
    private lateinit var viewPager2: ViewPager2
    private lateinit var tabLayout: TabLayout
    private lateinit var auth: FirebaseAuth
    private val item = arrayOf("General","Business","Technology","Science","Health","Sports","Entertainment")

    private lateinit var mGoogleSignInClient: GoogleSignInClient
    private var account: GoogleSignInAccount? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setTheme(R.style.Theme_ViewPager)
        setContentView(R.layout.activity_main)
        setSupportActionBar(findViewById(R.id.toolbar))

        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestEmail()
            .requestIdToken("630732151727-d3525cjd86v276n1q2murvf1gi0f52md.apps.googleusercontent.com")
            .build()

        mGoogleSignInClient = GoogleSignIn.getClient(this, gso)
        account = GoogleSignIn.getLastSignedInAccount(this)

        drawer = findViewById(R.id.drawer_layout)
        toolbar = findViewById(R.id.toolbar)
        val drawerToggle = ActionBarDrawerToggle(this,drawer,toolbar,R.string.navigation_drawer_open,
            R.string.navigation_drawer_close)
        drawer.addDrawerListener(drawerToggle)
        drawerToggle.syncState()

        val checkInternet = CheckInternetConnectivity(this)
        checkInternet.observe(this) {
            if (it) {
                Log.d("Check Internet", "onCreate: Internet Available")
            }
            else
                Log.d("Check Internet", "onCreate: Internet Lost")
        }

        navigationView = findViewById(R.id.drawer_navigation_view)
        navigationView.setNavigationItemSelectedListener(this)
        auth = FirebaseAuth.getInstance()
        val name = navigationView.getHeaderView(0).findViewById<TextView>(R.id.drawer_header_profile_name)
        val emailId = navigationView.getHeaderView(0).findViewById<TextView>(R.id.drawer_header_profile_email_id)
        val profileImage = navigationView.getHeaderView(0).findViewById<ImageView>(R.id.drawer_header_profile_image)
        name.text = "1"
        name.text = auth.currentUser?.displayName
        emailId.text = auth.currentUser?.email
        if (account != null) {
            Glide.with(this)
                .load(account!!.photoUrl)
                .into(profileImage)
            emailId.text = account?.email
            name.text = account?.givenName
        }
        Log.d("Current User", "onCreate: ${auth.currentUser?.email}")


        viewPagerAdapter = ViewPagerAdapter(this)
        tabLayout = findViewById(R.id.tabLayout)
        viewPager2 = findViewById(R.id.viewPager)
        viewPager2.adapter = viewPagerAdapter
        auth = FirebaseAuth.getInstance()

        TabLayoutMediator(tabLayout,viewPager2) {
            tab,position ->
            tab.text = item[position]
        }.attach()

    }

    override fun onStart() {
        super.onStart()

        val user = auth.currentUser
        if (user == null && account == null) {
            val intent = Intent(this,LoginActivity::class.java)
            startActivity(intent)
        }
    }

    override fun onBackPressed() {
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START)
        }
        else {
            this.finishAffinity()
            super.onBackPressed()
        }
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        if(item.itemId == R.id.logOut) {
            if (auth.currentUser != null) {
                auth.signOut()
            }
            else {
                mGoogleSignInClient.signOut().addOnCompleteListener {
                    val intent = Intent(this, LoginActivity::class.java)
                    startActivity(intent)
                }
            }
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
        }
        return true
    }
}