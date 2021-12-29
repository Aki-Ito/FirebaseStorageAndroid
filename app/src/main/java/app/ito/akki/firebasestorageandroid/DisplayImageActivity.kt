package app.ito.akki.firebasestorageandroid

import android.content.Intent
import android.graphics.BitmapFactory
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.ktx.storage
import kotlinx.android.synthetic.main.activity_display_image.*

class DisplayImageActivity : AppCompatActivity() {

    var storageRef = Firebase.storage.reference

    var imagesRef = storageRef.child("images")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_display_image)



        button.setOnClickListener {
            val imageView = findViewById<ImageView>(R.id.imageView)
            imageView.scaleType = ImageView.ScaleType.CENTER_CROP

            val downLoadPhoto = imagesRef.child("sample.jpg")

            val ONE_MEGABYTE: Long = 1024 * 1024
            downLoadPhoto.getBytes(ONE_MEGABYTE).addOnSuccessListener {
                // Data for "images/island.jpg" is returned, use this as needed
                val bitmap = BitmapFactory.decodeByteArray(it, 0,  it.size)
                imageView.setImageBitmap(bitmap)
            }.addOnFailureListener {
                // Handle any errors
            }
        }
    }
}