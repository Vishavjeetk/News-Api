package com.example.viewpager

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import org.w3c.dom.Text

class RegisterActivity : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth
    private lateinit var email: EditText
    private lateinit var password: EditText
    private lateinit var register: TextView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setTheme(R.style.Theme_App_Login)
        setContentView(R.layout.activity_register)

        email = findViewById(R.id.email_edit)
        password = findViewById(R.id.password_edit)
        register = findViewById(R.id.Register_button)

        auth = FirebaseAuth.getInstance()

        register.setOnClickListener {
            auth.createUserWithEmailAndPassword(email.text.toString(),password.text.toString())
                .addOnCompleteListener(this) {
                    if (it.isSuccessful) {
                        Toast.makeText(this,"Sign Up Successful",Toast.LENGTH_LONG).show()
                        val intent = Intent(this,MainActivity::class.java)
                        startActivity(intent)
                    }
                    else {
                        Toast.makeText(this,"Failed Signup",Toast.LENGTH_LONG).show()
                    }
                }
        }

    }
}