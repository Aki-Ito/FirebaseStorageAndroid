package app.ito.akki.firebasestorageandroid

import android.content.Intent
import android.graphics.BitmapFactory
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.ImageButton
import android.widget.Toast
import com.google.android.material.textfield.TextInputEditText
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.ktx.storage

class MainActivity : AppCompatActivity() {
    var storageRef = Firebase.storage.reference
    var imagesRef = storageRef.child("images")
    var selectedPhotoUri: Uri?= null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val tappedButton = findViewById<Button>(R.id.tappedButton)
        val profileImageButton = findViewById<ImageButton>(R.id.imageButton)

        fun selectPhoto() {
            val intent = Intent(Intent.ACTION_OPEN_DOCUMENT).apply {
                addCategory(Intent.CATEGORY_OPENABLE)
                type = "image/*"
            }
            startActivityForResult(intent, READ_REQUEST_CODE)
        }



        profileImageButton.setOnClickListener{
            selectPhoto()
        }


        tappedButton.setOnClickListener {

                            profileImageButton.isDrawingCacheEnabled = true
                            profileImageButton.buildDrawingCache()


                            var uploadPhoto = imagesRef.child("sample.jpg")
                            if (selectedPhotoUri != null){
                                uploadPhoto.putFile(selectedPhotoUri!!)
                                    .addOnSuccessListener {
                                        Log.d("success", "image uploaded")
                                    }
                                    .addOnFailureListener {
                                        Log.d("RegisterActivity", "${it.message}")
                                    }
                            }

            val intent = Intent(this, DisplayImageActivity::class.java)
            startActivity(intent)
                        }


    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode != RESULT_OK) {
            return
        }
        when (requestCode) {
            READ_REQUEST_CODE -> {
                try {
                    data?.data?.also { uri ->
                        val inputStream = contentResolver?.openInputStream(uri)
                        val image = BitmapFactory.decodeStream(inputStream)
                        val profileImageButton = findViewById<ImageButton>(R.id.imageButton)
                        profileImageButton.setImageBitmap(image)
                        selectedPhotoUri = data?.data
                    }
                } catch (e: Exception) {
                    Toast.makeText(this, "エラーが発生しました", Toast.LENGTH_LONG).show()
                }
            }
        }
    }

    companion object {
        private const val READ_REQUEST_CODE: Int = 42
    }
}