package com.example.counterapp.Pages

import android.Manifest
import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.provider.MediaStore
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.epicture.EpictureApplication
import kotlinx.android.synthetic.main.upload_page.*
import android.graphics.Bitmap
import android.net.Uri
import android.widget.Toast
import androidx.core.content.ContextCompat
import kotlinx.android.synthetic.main.upload_page.view.*
import android.util.Base64
import com.android.volley.Request
import com.example.counterapp.NetworkManager
import com.example.epicture.R
import java.io.ByteArrayOutputStream


class UploadFragment : Fragment() {
    companion object {
        private const val IMAGE_PICK_CODE = 1000
        private const val PERMISSION_CODE = 1001
        lateinit var imageBitmap : Bitmap
        lateinit var imageEncoded : String
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view: View =  inflater.inflate(R.layout.upload_page, container, false)

        view.upload_browse_btn.setOnClickListener {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                if (ContextCompat.checkSelfPermission(EpictureApplication.context, Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_DENIED) {
                    val permissions = arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE)
                    requestPermissions(permissions, PERMISSION_CODE)
                } else {
                    pickImageFromGallery()
                }
            } else {
                pickImageFromGallery()
            }
        }

        view.upload_btn.setOnClickListener {
            if (upload_view.drawable != null) {
                convertToBase64()
                uploadImage()
            } else {
                Toast.makeText(EpictureApplication.context, "Please select an image.", Toast.LENGTH_LONG).show()
            }
        }
        return view
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        when(requestCode){
            PERMISSION_CODE -> {
                if (grantResults.isNotEmpty() && grantResults[0] ==
                    PackageManager.PERMISSION_GRANTED){
                    pickImageFromGallery()
                }
                else{
                    Toast.makeText(EpictureApplication.context, "Permission denied", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (resultCode == Activity.RESULT_OK && requestCode == IMAGE_PICK_CODE){
            val selectFile : Uri? = data?.data
            if (selectFile != null) {
                upload_view.setImageURI(selectFile)
                imageBitmap = MediaStore.Images.Media.getBitmap(EpictureApplication.context.getContentResolver(), selectFile)
            }
        }
    }

    private fun pickImageFromGallery() {
        val intent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
        intent.type = "image/*"
        startActivityForResult(intent, IMAGE_PICK_CODE)
    }

    private fun uploadImage() {
        val settings = EpictureApplication.settings

        val networkManager = NetworkManager(EpictureApplication.context)
        val urlSuffix = "3/upload"
        val header = mutableMapOf("Authorization" to "Bearer " + settings["access_token"])
        val body = mutableMapOf("image" to imageEncoded, "title" to upload_title.text.toString(), "description" to upload_desc.text.toString())

        networkManager.sendRequest(Request.Method.POST, urlSuffix, header, body)

        upload_title.setText("")
        upload_desc.setText("")
        upload_view.setImageDrawable(null)

        Toast.makeText(EpictureApplication.context, "Image successfully uploaded.", Toast.LENGTH_LONG).show()
    }

    private fun convertToBase64() {
        val outputStream = ByteArrayOutputStream()
        imageBitmap.compress(Bitmap.CompressFormat.PNG, 100, outputStream)
        val byteArray = outputStream.toByteArray()

        imageEncoded = Base64.encodeToString(byteArray, Base64.DEFAULT)
    }
}
