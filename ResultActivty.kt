package com.example.imageexplorer4

import android.app.ProgressDialog
import android.graphics.BitmapFactory
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import android.widget.Toast
import com.bumptech.glide.Glide
import com.example.imageexplorer4.databinding.ActivityMainBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthException
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.storage.FirebaseStorage
import kotlinx.android.synthetic.main.activity_result_activty.*
import java.io.File

class ResultActivty : AppCompatActivity() {

    lateinit var binding: ActivityMainBinding
    private lateinit var imageView4: ImageView
    private lateinit var databaseReference: DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_result_activty)

        val actionBar = supportActionBar
        actionBar!!.title = "Search results"

        actionBar.setDisplayHomeAsUpEnabled(true)

        val userid = FirebaseAuth.getInstance().currentUser!!.uid

        imageView4 = findViewById(R.id.imageView3)
        databaseReference = FirebaseDatabase.getInstance().getReference("userimages")
        databaseReference.child(userid).get()
            .addOnSuccessListener {
                val url = it.child("url").value.toString()
                Glide.with(this).load(url).into(imageView3)
            }
        setContentView(binding.root)
        binding.button4.setOnClickListener {

            val progressDialog = ProgressDialog(this)
            progressDialog.setMessage("Fetching Results...")
            progressDialog.setCancelable(false)
            progressDialog.show()
            val storageRef = FirebaseStorage.getInstance().reference.child("results/result.png")
            val localfile = File.createTempFile("tempImage", "png")
            storageRef.getFile(localfile).addOnSuccessListener {

                if (progressDialog.isShowing)
                    progressDialog.dismiss()
                val bitmap = BitmapFactory.decodeFile((localfile.absolutePath))
                binding.imageView.setImageBitmap(bitmap)

            }.addOnFailureListener {
                if (progressDialog.isShowing)
                    progressDialog.dismiss()
                Toast.makeText(this,"Failed to retrieve data", Toast.LENGTH_SHORT).show()
                //Toast.makeText(this,)
            }
        }


     }
     }
