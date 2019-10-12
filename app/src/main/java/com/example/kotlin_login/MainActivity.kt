package com.example.kotlin_login

import android.content.DialogInterface
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.activity_sing_up.*

class MainActivity : AppCompatActivity() {

    private lateinit var firebaseAuth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        firebaseAuth = FirebaseAuth.getInstance()

        btn_NewUser.setOnClickListener() {
            startActivity(Intent(applicationContext, SingUP::class.java))
            finish()
        }


        btnLogin.setOnClickListener() {
            logIn()
        }

        btn_ForgotPassword.setOnClickListener() {

            val builder = AlertDialog.Builder(this)
            builder.setTitle("Forgot Password")
            val view = layoutInflater.inflate(R.layout.layout_forgot_password, null)
            val userName = view.findViewById<EditText>(R.id.et_ForgotPassoword)
            builder.setView(view)

            builder.setPositiveButton("Reset", DialogInterface.OnClickListener { _, _ ->
                forgotPassword(userName)
            })

            builder.setNegativeButton("Close", DialogInterface.OnClickListener { _, _ ->

            })
            builder.show()

        }
    }


    private fun forgotPassword(userName: EditText) {
        if(et_Login_Email.text.toString().isEmpty()){
            Toast.makeText(this, "Please Enter Details", Toast.LENGTH_LONG).show()
        } else {
            firebaseAuth.sendPasswordResetEmail(userName.text.toString())
                .addOnCompleteListener { task ->
                    Toast.makeText(this, "Mail sent ", Toast.LENGTH_SHORT).show()
                }
        }
    }


    private fun logIn() {
        if(et_Login_Email.text.toString().isEmpty() || et_LogIN_Password.text.toString().isEmpty()){
           Toast.makeText(this, "Please Enter Details", Toast.LENGTH_SHORT).show()
        }
        else {
            firebaseAuth.signInWithEmailAndPassword(
                et_Login_Email.text.toString(),
                et_LogIN_Password.text.toString()
            ).addOnCompleteListener { task ->

                if (task.isSuccessful) {
                    val user = firebaseAuth.currentUser
                    updateUI(user)
                } else {
                    Toast.makeText(this, task.exception.toString(), Toast.LENGTH_SHORT).show()
                }
            }
        }

    }

    private fun updateUI(user: FirebaseUser?) {
        if (user != null) {
            if (user.isEmailVerified) {
                startActivity(Intent(applicationContext, Home::class.java))
                finish()
            } else {
                Toast.makeText(this, "Please Verify your Mail", Toast.LENGTH_SHORT).show()
            }
        } else {
            Toast.makeText(this, "Login Failed...", Toast.LENGTH_SHORT).show()
        }

    }
}


