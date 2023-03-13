package e_card.e_cardaddress.adresschange.videoplayer

import android.app.Dialog
import android.content.ContentValues
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import android.util.Log
import android.view.MenuItem
import android.widget.Button
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.view.GravityCompat
import androidx.documentfile.provider.DocumentFile
import androidx.drawerlayout.widget.DrawerLayout
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import e_card.e_cardaddress.adresschange.videoplayer.Model.Model_status
import e_card.e_cardaddress.adresschange.videoplayer.databinding.ActivityThirdPageBinding
import java.io.File
import java.io.FileOutputStream
import java.io.IOException
import java.io.OutputStream
import android.os.storage.StorageManager as StorageManager1

class third_Page : AppCompatActivity() {

    lateinit var binding: ActivityThirdPageBinding

    lateinit var rvStatusList: RecyclerView
    lateinit var statusList: ArrayList<Model_status>
    lateinit var statusAdapter: Status_Adapter
    lateinit var drawerLayout: DrawerLayout
    lateinit var actionBarDrawerToggle: ActionBarDrawerToggle
    private val permissionList = mutableListOf<String>()

    @RequiresApi(Build.VERSION_CODES.Q)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityThirdPageBinding.inflate(layoutInflater)
        setContentView(binding.root)

        /**
         *      Requesting For Permission
         */

        askForPermission()
        checkMangeAllPermisison()

//                drawer
        binding.drowerBtn.setOnClickListener {
            binding.myDrawerLayout.openDrawer(GravityCompat.START)
        }
//
        supportActionBar?.title = "All Status"
        statusList = ArrayList()

        val result = readDataFromprefs()
        if (result) {

            val sh = getSharedPreferences("DATA_PATH", MODE_PRIVATE)
            val uriPath = sh.getString("PATH", "")

            contentResolver.takePersistableUriPermission(
                Uri.parse(uriPath), Intent.FLAG_GRANT_READ_URI_PERMISSION
            )

            if (uriPath != null) {
                val fileDoc = DocumentFile.fromTreeUri(applicationContext, Uri.parse(uriPath))
                for (file: DocumentFile in fileDoc!!.listFiles()) {
                    if (!file.name!!.endsWith("media")) {
                        val modelClass = Model_status(file.name!!, file.uri.toString())
                        statusList.add(modelClass)

                        Log.e("TAG", "onCreate: ++++++++++${statusList}")
                    }
                }
                setUpRecyclerView(statusList)
            }
        } else {
            getFolderPermission()
        }
    }

    private fun checkMangeAllPermisison() {

    }

    @RequiresApi(Build.VERSION_CODES.Q)
    private fun getFolderPermission() {
//        val storageManager = application.getSystemService(Context.STORAGE_SERVICE) as StorageManager1
//        val intent = storageManager.primaryStorageVolume.createOpenDocumentTreeIntent()
//        val taregetDirectory = "Android%2Fmedia%2Fcom.whatsapp%2Fwhatsapp%2FMedia%2F.Statues"
//        var uri = intent.getParcelableExtra<Uri>("android.provider.extra.INITIAL_URI") as Uri
//        var scheme = uri.toString()
//        scheme = scheme.replace("/root/", "/tree/")
//        scheme += "%A$taregetDirectory"
//        uri = Uri.parse(scheme)
//        intent.putExtra("android.provider.extra.INTIIAL_URI", uri)
//        intent.putExtra("android.provider.extra.SHOW_ADVANCED", true)
//        startActivityForResult(intent, 1234)


    }

    fun askForPermission() {

        if (!hasReadInternalStorage()) permissionList.add(android.Manifest.permission.READ_EXTERNAL_STORAGE)
        if (!hasWriteInternalStorage()) permissionList.add(android.Manifest.permission.WRITE_EXTERNAL_STORAGE)
        if (!hasInternet()) permissionList.add(android.Manifest.permission.INTERNET)
        if (permissionList.isEmpty()) else ActivityCompat.requestPermissions(
            this,
            permissionList.toTypedArray(),
            0
        )

    }

    fun hasReadInternalStorage() = ActivityCompat.checkSelfPermission(
        this,
        android.Manifest.permission.READ_EXTERNAL_STORAGE
    ) == PackageManager.PERMISSION_GRANTED

    fun hasWriteInternalStorage() = ActivityCompat.checkSelfPermission(
        this,
        android.Manifest.permission.WRITE_EXTERNAL_STORAGE
    ) == PackageManager.PERMISSION_GRANTED

    fun hasInternet() = ActivityCompat.checkSelfPermission(
        this,
        android.Manifest.permission.INTERNET
    ) == PackageManager.PERMISSION_GRANTED

    //
    fun readDataFromprefs(): Boolean {
        val sh = getSharedPreferences("DATA_PATH", MODE_PRIVATE)
        val uriPath = sh.getString("PATH", "")
        if (uriPath != null) {
            return false
        }
        return true
    }

    //
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, requestCode, data)

        if (resultCode == RESULT_OK) {
            val treeUri = data?.data

            val sharedPreferences = getSharedPreferences("DATA_PATH", MODE_PRIVATE)
            val myEdit = sharedPreferences.edit()
            myEdit.putString("PATH", treeUri.toString())
            myEdit.apply()
        }
    }

    //   Set Up Rv View
    private fun setUpRecyclerView(statusList: java.util.ArrayList<Model_status>) {
        statusAdapter = applicationContext?.let {
            Status_Adapter(
                it, statusList
            ) { selectedStatusItem: Model_status ->
                listItemClicked(selectedStatusItem)
            }
        }!!
        rvStatusList.apply {
            setHasFixedSize(true)
            layoutManager = StaggeredGridLayoutManager(2, LinearLayoutManager.VERTICAL)
            adapter = statusAdapter

        }
    }

    private fun listItemClicked(status: Model_status) {
        val dialog = Dialog(this@third_Page)
        dialog.setContentView(R.layout.custome_dailog)
        dialog.show()

        val btdownload = dialog.findViewById<Button>(R.id.bt_download)
        btdownload.setOnClickListener {
            dialog.dismiss()
            saveFile(status)
        }
    }

    private fun saveFile(status: Model_status) {
        if (status.fileUri.endsWith(".mp3")) {

            val inputStrem = contentResolver.openInputStream(Uri.parse(status.fileUri))
            val filename = "${System.currentTimeMillis()}.mp4"
            try {
                val value = ContentValues()
                value.put(MediaStore.MediaColumns.DISPLAY_NAME, filename)
                value.put(MediaStore.MediaColumns.MIME_TYPE, "video/mp4")
                value.put(
                    MediaStore.MediaColumns.RELATIVE_PATH,
                    Environment.DIRECTORY_DOCUMENTS + "video/mp4"
                )
                val uri = contentResolver.insert(
                    MediaStore.Files.getContentUri("external"), value
                )
                val outputStream: OutputStream = uri?.let { contentResolver.openOutputStream(it) }!!
                if (inputStrem != null) {
                    outputStream.write(inputStrem.readBytes())
                }
                outputStream.close()
                Toast.makeText(applicationContext, "VIDEO SAVED", Toast.LENGTH_SHORT).show()

            } catch (e: IOException) {
                Toast.makeText(applicationContext, "FAILED", Toast.LENGTH_SHORT).show()

            }
        } else {
            val bitmap =
                MediaStore.Images.Media.getBitmap(this.contentResolver, Uri.parse(status.fileUri))
            val fileName = "${System.currentTimeMillis()}.jpg"
            var fos: OutputStream? = null
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
                contentResolver.also { resolver ->
                    val contentValues = ContentValues().apply {
                        put(MediaStore.MediaColumns.DISPLAY_NAME, fileName)
                        put(MediaStore.MediaColumns.MIME_TYPE, "image/jpg")
                        put(MediaStore.MediaColumns.RELATIVE_PATH, Environment.DIRECTORY_PICTURES)
                    }
                    val imageUri: Uri? =
                        resolver.insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, contentValues)
                    fos = imageUri?.let { resolver.openOutputStream(it) }
                }
            } else {
                val imagesDir =
                    Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES)
                val image = File(imagesDir, fileName)
                fos = FileOutputStream(image)
            }
            fos?.use {
                bitmap.compress(Bitmap.CompressFormat.JPEG, 100, it)
                Toast.makeText(applicationContext, "Image Saved", Toast.LENGTH_SHORT).show()
            }

        }
    }


    //
    fun drawer() {
        actionBarDrawerToggle =
            ActionBarDrawerToggle(this, drawerLayout, R.string.nav_open, R.string.nav_close)
        drawerLayout.addDrawerListener(actionBarDrawerToggle)
        actionBarDrawerToggle.syncState()
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }

    //
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return if (actionBarDrawerToggle.onOptionsItemSelected(item)) {
            true
        } else super.onOptionsItemSelected(item)
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)

        if (requestCode == 0 && permissions.isNotEmpty()) {
            for (i in permissions.indices) {
//                if (permissions[i]!= ){
//
//                }
            }
        }
    }


}