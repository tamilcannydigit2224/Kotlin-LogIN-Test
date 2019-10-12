package com.example.kotlin_login

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_sing_up.*
import java.util.regex.Pattern

class SingUP : AppCompatActivity() {

    private lateinit var auth : FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sing_up)

        auth = FirebaseAuth.getInstance()

        btnSignUp.setOnClickListener{
            userSignUP()
        }
    }

    private fun userSignUP() {
        if(et_SU_EmailAddress.text.toString().isEmpty() || et_SU_Password.text.toString().isEmpty()){
            Toast.makeText(this, "Please Enter Details", Toast.LENGTH_LONG).show()
        }
        else{
            auth.createUserWithEmailAndPassword(et_SU_EmailAddress.text.toString(), et_SU_Password.text.toString()).addOnCompleteListener { task ->
                if(task.isSuccessful){
                    val user = auth.currentUser

                    user?.sendEmailVerification()
                        ?.addOnCompleteListener { task ->
                            Toast.makeText(this, "Verification Mail sent", Toast.LENGTH_LONG);
                            startActivity(Intent(this, MainActivity::class.java))
                            finish()
                        }
                }
                else{
                    Toast.makeText(this, task.exception.toString(), Toast.LENGTH_LONG).show()
                }
            }
        }
    }
}
