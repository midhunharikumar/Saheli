package com.example.saheli_v01.userverify

import android.content.Intent
import android.os.Bundle
import android.provider.MediaStore
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.snackbar.Snackbar
import androidx.appcompat.app.AppCompatActivity
import com.example.saheli_v01.R
import android.widget.Button
import android.widget.Toast
import com.example.saheli_v01.capture.CaptureActivity


class UserVerificationActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_user_verification)
        //setSupportActionBar(findViewById(R.id.toolbar))
        val btn_click_me = findViewById(R.id.button) as Button
        val person_image = findViewById(R.id.button2) as Button
        // set on-click listener
        btn_click_me.setOnClickListener {
            Toast.makeText(this@UserVerificationActivity, "You clicked me.", Toast.LENGTH_SHORT).show()
            val cameraactivity = Intent(applicationContext, CaptureActivity::class.java)

            cameraactivity.putExtra("capture_event_mode", "id_card"); //Your id
            startActivity(cameraactivity)
            //dispatchTakePictureIntent()
        }
        person_image.setOnClickListener {
            Toast.makeText(this@UserVerificationActivity, "You clicked me.", Toast.LENGTH_SHORT).show()
            val cameraactivity = Intent(applicationContext, CaptureActivity::class.java)

            cameraactivity.putExtra("capture_event_mode", "face"); //Your id
            startActivity(cameraactivity)
            //dispatchTakePictureIntent()
        }

    }
    val REQUEST_IMAGE_CAPTURE = 0

    private fun dispatchTakePictureIntent() {
        Intent(MediaStore.ACTION_IMAGE_CAPTURE).also { takePictureIntent ->
            takePictureIntent.resolveActivity(packageManager)?.also {
                startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE)
            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        println(resultCode)
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
            println("I'm b ack")
            //UploadUtility(this).uploadFile(currentPhotoPath)
        }
    }

}