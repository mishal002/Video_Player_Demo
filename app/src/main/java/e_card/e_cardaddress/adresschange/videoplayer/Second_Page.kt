package e_card.e_cardaddress.adresschange.videoplayer

import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.GravityCompat
import e_card.e_cardaddress.adresschange.videoplayer.databinding.ActivitySecondPageBinding

class Second_Page : AppCompatActivity() {


    lateinit var binding: ActivitySecondPageBinding

    lateinit var actionBarDrawerToggle: ActionBarDrawerToggle
//    lateinit var drawerLayout: DrawerLayout

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySecondPageBinding.inflate(layoutInflater)
        setContentView(binding.root)
// drawer
        binding.drowerBtn.setOnClickListener {
            binding.myDrawerLayout.openDrawer(GravityCompat.START)
        }
//
        binding.whatsappVideoBtn.setOnClickListener {
            val i = Intent(this, third_Page::class.java)
            startActivity(i)
        }
    }

//    fun drawer() {
//        drawerLayout = findViewById(R.id.my_drawer_layout)
//        actionBarDrawerToggle =
//            ActionBarDrawerToggle(this, drawerLayout, R.string.nav_open, R.string.nav_close)
//
//        drawerLayout.addDrawerListener(actionBarDrawerToggle)
//        actionBarDrawerToggle.syncState()
//
//        supportActionBar?.setDisplayHomeAsUpEnabled(true)
//    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return if (actionBarDrawerToggle.onOptionsItemSelected(item)) {
            true
        } else super.onOptionsItemSelected(item)
    }
}