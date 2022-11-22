package com.example.viewpager

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.SignInButton
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.FirebaseAuth


class LoginActivity : AppCompatActivity() {

    private lateinit var signInIntent: Intent
    private lateinit var activityResultLauncher: ActivityResultLauncher<Intent>
    private lateinit var email: EditText
    private lateinit var password: EditText
    private lateinit var button: TextView
    private lateinit var signUp: TextView
    private lateinit var googleSignIn: SignInButton
    private lateinit var auth: FirebaseAuth



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setTheme(R.style.Theme_App_Login)
        setContentView(R.layout.activity_login)

        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestEmail()
            .requestIdToken("630732151727-d3525cjd86v276n1q2murvf1gi0f52md.apps.googleusercontent.com")
            .build()

        val mGoogleSignInClient = GoogleSignIn.getClient(this, gso)

        email = findViewById(R.id.email_edit)
        password = findViewById(R.id.password_edit)
        button = findViewById(R.id.login_button)
        signUp = findViewById(R.id.new_user_text)
        googleSignIn = findViewById(R.id.google_button)
        auth = FirebaseAuth.getInstance()

        signUp.setOnClickListener{
            val intent = Intent(this,RegisterActivity::class.java)
            startActivity(intent)
        }

        googleSignIn.setOnClickListener {
            signInIntent = mGoogleSignInClient.signInIntent
            activityResultLauncher.launch(signInIntent)
        }

        activityResultLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
            val task: Task<GoogleSignInAccount> = GoogleSignIn.getSignedInAccountFromIntent(it.data)
            handleSignInResult(task)
        }

        button.setOnClickListener {
            auth.signInWithEmailAndPassword(email.text.toString(),password.text.toString())
                .addOnCompleteListener(this) {
                    if (it.isSuccessful) {
                        Toast.makeText(this,"Signed In",Toast.LENGTH_LONG).show()
                        val intent = Intent(this,MainActivity::class.java)
                        startActivity(intent)
                        finish()
                    }
                    else {
                        Toast.makeText(this,"Failed",Toast.LENGTH_SHORT).show()
                    }
                }
        }
    }

    private fun handleSignInResult(task: Task<GoogleSignInAccount>) {
        try {
            val account: GoogleSignInAccount = task.getResult(ApiException::class.java)
            // Signed in successfully, show authenticated UI.
            val intent = Intent(this,MainActivity::class.java)
            intent.putExtra("Email","${account.email}")
            startActivity(intent)
            finish()
        } catch (e: ApiException) {
            // The ApiException status code indicates the detailed failure reason.
            // Please refer to the GoogleSignInStatusCodes class reference for more information.
            Log.w("SignInResult", "signInResult:failed code= $e")
        }
    }

    override fun onBackPressed() {
        this.finishAffinity()
    }

    override fun onStart() {
        super.onStart()
        val account = GoogleSignIn.getLastSignedInAccount(this)
        Log.d("Account", "onStart: $account")
        if (account != null) {
            val intent = Intent(this,MainActivity::class.java)
            startActivity(intent)
        }
    }
}