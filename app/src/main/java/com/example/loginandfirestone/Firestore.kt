package com.example.loginandfirestone

import android.annotation.SuppressLint
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.FirebaseFirestoreException
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.tasks.await
import java.util.*
import kotlin.collections.HashMap

class Firestore {

    companion object {
        val currentUser = Firebase.auth.currentUser!!

        @SuppressLint("StaticFieldLeak")
        val db = Firebase.firestore
        val user = hashMapOf(
            "userId" to currentUser.uid,
            "userName" to currentUser.email
        )

        fun saveUsertoFirestore(user: HashMap<String, String?>): Flow<Resource<Unit>> = flow {
            emit(Resource.Loading())
            try {
                val ref = db.collection("users")
                    .document(currentUser.uid)

                ref.set(user)
                    .await()
                emit(Resource.Success(data = Unit))
            } catch (e: FirebaseFirestoreException) {
                emit(Resource.Error(message = e.message.toString()))
            }
        }
    }

}