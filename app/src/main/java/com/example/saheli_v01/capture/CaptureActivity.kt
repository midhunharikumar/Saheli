package com.example.saheli_v01.capture

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.FileProvider
import java.io.File
import java.io.IOException
import java.text.SimpleDateFormat
import java.util.*

class CaptureActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val bundle = intent.extras
        var capture_event_mode = "id_card"
        println(bundle?.get("capture_event_mode"))
        if (bundle != null) {
            capture_event_mode = bundle?.get("capture_event_mode") as String
        }
        if (capture_event_mode == "id_card") {
            dispatchTakePictureIntent(0)
        }else if(capture_event_mode== "face") {
            dispatchTakePictureIntent(1)
        }
    }
    val REQUEST_IMAGE_CAPTURE = 0
    lateinit var currentPhotoPath: String
    private fun dispatchTakePictureIntent(mode: Int) {
        Intent(MediaStore.ACTION_IMAGE_CAPTURE).also { takePictureIntent ->
            // Ensure that there's a camera activity to handle the intent
            takePictureIntent.resolveActivity(packageManager)?.also {
                // Create the File where the photo should go
                val photoFile: File? = try {
                    createImageFile()
                } catch (ex: IOException) {
                    // Error occurred while creating the File
                    null
                }
                println(photoFile)
                // Continue only if the File was successfully created
                var REQUEST_TAKE_PHOTO=0
                photoFile?.also {
                    val photoURI: Uri? = FileProvider.getUriForFile(
                        this,
                        "com.example.android.fileprovider",
                        it
                    )
                    takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI)
                    takePictureIntent.putExtra("android.intent.extras.CAMERA_FACING",mode);
                    startActivityForResult(takePictureIntent, REQUEST_TAKE_PHOTO)
                }
            }
        }
    }
    @Throws(IOException::class)
    private fun createImageFile(): File {
        // Create an image file name
        val timeStamp: String = SimpleDateFormat("yyyyMMdd_HHmmss").format(Date())
        val storageDir: File? = getExternalFilesDir(Environment.DIRECTORY_PICTURES)
        println(storageDir)
        return File.createTempFile(
            "JPEG_${timeStamp}_", /* prefix */
            ".jpg", /* suffix */
            storageDir /* directory */
        ).apply {
            // Save a file: path for use with ACTION_VIEW intents
            currentPhotoPath = absolutePath
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        println(resultCode)
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
            println(currentPhotoPath)
            UploadUtility(this).uploadFile(File(currentPhotoPath))
            finish();

        }
    }

}