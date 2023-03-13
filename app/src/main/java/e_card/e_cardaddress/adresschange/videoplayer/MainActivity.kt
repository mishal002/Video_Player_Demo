package e_card.e_cardaddress.adresschange.videoplayer

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.appcompat.app.AppCompatActivity
import e_card.e_cardaddress.adresschange.videoplayer.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    lateinit var binding: ActivityMainBinding
//    val permissionList1 = mutableListOf<String>()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        sleshScreen()
//        checkMangeAllPermisison()
//        askForPermission()


    }

    fun sleshScreen() {
        Handler(Looper.getMainLooper()).postDelayed({
            val intent = Intent(this, First_Page::class.java)
            startActivity(intent)
            finish()
        }, 1000)
    }

//    private fun checkMangeAllPermisison() {
//
//    }
//
//    fun askForPermission() {
//        if (!hasInternet()) permissionList1.add(android.Manifest.permission.INTERNET)
//        if (permissionList1.isEmpty()) else ActivityCompat.requestPermissions(
//            this,
//            permissionList1.toTypedArray(),
//            0
//        )
//
//    }
//
//    fun hasInternet() = ActivityCompat.checkSelfPermission(
//        this,
//        android.Manifest.permission.INTERNET
//    ) == PackageManager.PERMISSION_GRANTED
//
//    override fun onRequestPermissionsResult(
//        requestCode: Int,
//        permissions: Array<out String>,
//        grantResults: IntArray
//    ) {
//        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
//
//        if (requestCode == 0 && permissions.isNotEmpty()) {
//            for (i in permissions.indices) {
////                if (permissions[i]!= ){
////
////                }
//            }
//        }

}