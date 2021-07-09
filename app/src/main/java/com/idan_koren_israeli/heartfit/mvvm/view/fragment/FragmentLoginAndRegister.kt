package com.idan_koren_israeli.heartfit.mvvm.view.fragment

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.SignInButton
import com.google.android.gms.common.api.ApiException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider
import com.idan_koren_israeli.heartfit.R


class FragmentLoginAndRegister : Fragment() {

    companion object{
        private const val RC_SIGN_IN = 42
        private const val TAG = "FragmentLoginAndRegister"
    }
    lateinit var viewLoginAndRegister : View
    private lateinit var mAuth : FirebaseAuth
    private lateinit var sign_in_button : SignInButton
    private lateinit var googleSignInClient : GoogleSignInClient
    private lateinit var sign_in_coverImage : ImageView

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        viewLoginAndRegister = inflater.inflate(R.layout.fragment_login_and_register, container, false)

        findViews()
        loadBackGroundImage()
        if (!checkIfUserIsLogin()){
            findNavController().navigate(R.id.action_fragmentLoginAndRegister_to_fragmentHome)
        }
        else{
            val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build()

            googleSignInClient = GoogleSignIn.getClient(requireContext(), gso)
        }
        sign_in_button.setOnClickListener {
            signIn()
        }

        return viewLoginAndRegister
    }

    private fun loadBackGroundImage() {
        Glide
            .with(requireContext())
            .load(R.drawable.signin_background_image)
            .into(sign_in_coverImage)
    }

    private fun signIn() {
        val signInIntent = googleSignInClient.signInIntent
        startActivityForResult(signInIntent, RC_SIGN_IN)
    }


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        // Result returned from launching the Intent from GoogleSignInApi.getSignInIntent(...);
        if (requestCode == RC_SIGN_IN) {
            val task = GoogleSignIn.getSignedInAccountFromIntent(data)
                try {
                    // Google Sign In was successful, authenticate with Firebase
                    val account = task.getResult(ApiException::class.java)!!
                    Log.d(TAG, "firebaseAuthWithGoogle:" + account.id)
                    Log.d(TAG, "fail 1")
                    firebaseAuthWithGoogle(account.idToken!!)
                } catch (e: ApiException) {
                    // Google Sign In failed, update UI appropriately
                    Log.w(TAG, "Google sign in failed", e)
                }
        }
    }

    private fun firebaseAuthWithGoogle(idToken: String) {
        val credential = GoogleAuthProvider.getCredential(idToken, null)
        mAuth.signInWithCredential(credential)
            .addOnCompleteListener(requireActivity()) { task ->
                if (task.isSuccessful) {
                    // Sign in success, update UI with the signed-in user's information
                    Log.d(TAG, "signInWithCredential:success")
                    findNavController().navigate(R.id.action_fragmentLoginAndRegister_to_fragmentHome)
                } else {
                    // If sign in fails, display a message to the user.
                    Log.d(TAG, "signInWithCredential:failure????????????????")
                    Log.d(TAG, "signInWithCredential:failure", task.exception)
                }
            }
    }

    private fun checkIfUserIsLogin(): Boolean {
        mAuth = FirebaseAuth.getInstance()
        return mAuth.currentUser == null
    }

    private fun findViews() {
        sign_in_button = viewLoginAndRegister.findViewById(R.id.sign_in_button)
        sign_in_coverImage = viewLoginAndRegister.findViewById(R.id.sign_in_coverImage)
    }
}