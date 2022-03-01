package fr.iutlyon1.theo.springproject.popup

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import android.util.DisplayMetrics
import android.widget.ImageButton
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintProperties
import androidx.core.app.ActivityCompat
import fr.iutlyon1.theo.springproject.R
import fr.iutlyon1.theo.springproject.entities.Constants
import fr.iutlyon1.theo.springproject.entities.Constants.USER_AVATAR
import java.io.File
import java.io.FileOutputStream


class PopupLayoutPhotos : AppCompatActivity() {

    private lateinit var btnCamera  :ImageButton
    private lateinit var btnGallery :ImageButton
    private var canSavePictureWrite = false
    private var canSavePictureRead = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.popup_layout_photos)

        ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.WRITE_EXTERNAL_STORAGE), 1)
        ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE), 2)


        val displayMetrics = DisplayMetrics()
        windowManager.defaultDisplay.getMetrics(displayMetrics)
        val width = displayMetrics.widthPixels
        window.setLayout((width * 0.82).toInt(), ConstraintProperties.WRAP_CONTENT)

        btnCamera = findViewById(R.id.imageButtonCamera)
        btnGallery = findViewById(R.id.imageButtonGallery)

        btnCamera.setOnClickListener {
            if(canSavePictureWrite && canSavePictureRead) {
                if (this.packageManager.hasSystemFeature(PackageManager.FEATURE_CAMERA_ANY)) {
                    val takePicture = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
                    startActivityForResult(takePicture, Constants.RESULT_CODE_TAKE_PICTURE)
                } else {
                    Toast.makeText(
                        applicationContext,
                        "You don't have a Camera working on your phone",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
            else {
                Toast.makeText(
                    applicationContext,
                    "You didn't authorized the use of your camera",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }

        btnGallery.setOnClickListener {
            val pickPhoto = Intent(
                Intent.ACTION_PICK,
                MediaStore.Images.Media.EXTERNAL_CONTENT_URI
            )
            startActivityForResult(pickPhoto, Constants.RESULT_CODE_PICK_PICTURE)
        }
    }


    override fun onActivityResult(requestCode: Int, resultCode: Int, imageReturnedIntent: Intent?) {
        super.onActivityResult(requestCode, resultCode, imageReturnedIntent)
        var avatarPath : String = ""
        if(imageReturnedIntent != null) {
            if(imageReturnedIntent.getData() != null) {

                try {
                    avatarPath = imageReturnedIntent.getData().toString();
                } catch (e: Exception) {
                    e.printStackTrace();
                }
            }
            else if(imageReturnedIntent.extras != null) {
                val extras: Bundle = imageReturnedIntent.extras!!
                val imageBitmap = extras["data"] as Bitmap


                var outputStream: FileOutputStream
                val storageFolder: File = Environment.getExternalStorageDirectory()
                val folder = File(storageFolder.absolutePath.toString() + "/Avatars")
                folder.mkdirs()

                val fileName = System.currentTimeMillis().toString() + ".png"
                val outFile = File(folder, fileName)


                try {
                    outputStream = FileOutputStream(outFile)
                    imageBitmap.compress(Bitmap.CompressFormat.PNG, 100, outputStream)
                    avatarPath = folder.absolutePath.toString() + "/" + fileName
                    outputStream.flush()
                    outputStream.close()

                } catch (exception: Exception) {
                    exception.printStackTrace()
                }
            }


            val intentResult = Intent()
            intentResult.putExtra(USER_AVATAR, avatarPath)

            setResult(RESULT_OK, intentResult)
            finish()

        }
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<String?>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        when (requestCode) {
            1 -> {
                canSavePictureWrite = (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED)
            }
            2 -> {
                canSavePictureRead = (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED)
            }
        }
    }

}
