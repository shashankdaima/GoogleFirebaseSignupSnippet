package com.example.loginandfirestone

import android.annotation.SuppressLint
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.FirebaseFirestoreException
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.google.mlkit.vision.face.Face
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.tasks.await

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

        fun saveSomeCollection(data: List<Pair<Long, Face>>): Flow<Resource<Unit>> = flow {
            emit(Resource.Loading())
            try {
                val ref = db.collection("data")
                    .document()
                ref.set(
                    hashMapOf(
                        "userID" to user["userId"]
                    )
                ).await()
                val featureCollectionRef = ref.collection("feature")
                for (item in data) {
                    val featureRef = featureCollectionRef.document(item.first.toString())
                    featureRef.set(
                        hashMapOf(
                            "smilingProbability" to item.second.smilingProbability,
                            "leftEyeOpenProbability" to item.second.leftEyeOpenProbability,
                            "rightEyeOpenProbability" to item.second.rightEyeOpenProbability,
                            "eulerX" to item.second.headEulerAngleX,
                            "eulerY" to item.second.headEulerAngleY,
                            "eulerZ" to item.second.headEulerAngleZ,
                        )
                    ).await()
                }
                emit(Resource.Success(data = Unit))
            } catch (e: FirebaseFirestoreException) {
                emit(Resource.Error(message = e.message.toString()))
            }
        }
    }

}