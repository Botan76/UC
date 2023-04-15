package com.example.ultrafin.Login

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class LoginViewModel: ViewModel() {
    val errorMessage: MutableLiveData<String> by lazy {
        MutableLiveData<String>()
    }




    fun loginn(username : String, password : String){
        if(username == "") {
            errorMessage.value = "Fyll i epost"
            return
        }
        if(password == "") {
            errorMessage.value = "Fyll i lösenord"
            return
        }
        //Firebase
        Firebase.auth.signInWithEmailAndPassword(username, password)
            .addOnCompleteListener { task ->
                if (task.isSuccessful == false) {
                    errorMessage.value = task.exception!!.localizedMessage!!
            }
        }
    }

    fun register(username : String, password : String){
        if(username == "") {
            errorMessage.value = "Fyll i epost"
            return
        }
        if(password == "") {
            errorMessage.value = "Fyll i lösenord"
            return
        }




        Firebase.auth.createUserWithEmailAndPassword(username, password)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    // Sign in success, update UI with the signed-in user's information
                    // Toast.makeText(requireContext(), "Register ok.", Toast.LENGTH_SHORT).show()
                } else {
                    errorMessage.value = task.exception!!.localizedMessage!!
                }
            }
    }








}