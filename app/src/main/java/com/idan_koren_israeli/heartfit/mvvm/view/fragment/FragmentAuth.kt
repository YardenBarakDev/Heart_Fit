package com.idan_koren_israeli.heartfit.mvvm.view.fragment

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.SignInButton
import com.google.android.gms.common.api.ApiException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.GoogleAuthProvider
import com.idan_koren_israeli.heartfit.R
import com.idan_koren_israeli.heartfit.common.CommonUtils
import com.idan_koren_israeli.heartfit.db.firebase.database.DatabaseManager


class FragmentAuth : Fragment() {

    companion object {
        private const val RC_SIGN_IN = 42
        private const val TAG = "FragmentLoginAndRegister"
    }

    lateinit var viewLoginAndRegister: View
    private lateinit var mAuth: FirebaseAuth
    private lateinit var signInButton: SignInButton
    private lateinit var googleSignInClient: GoogleSignInClient
    private lateinit var logoImage: ImageView
    private lateinit var appNameText : TextView
    private lateinit var loadingLayout: ViewGroup

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        viewLoginAndRegister =
            inflater.inflate(R.layout.fragment_auth, container, false)

        findViews()
        loadBackGroundImage()
        if (userLoggedIn()) {
            showLoadingLayout()
            onAuthSuccess(FirebaseAuth.getInstance().currentUser!!)
        } else {
            showRegularLayout()
            val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build()

            googleSignInClient = GoogleSignIn.getClient(requireContext(), gso)

            signInButton.setOnClickListener {
                val signInIntent = googleSignInClient.signInIntent
                startActivityForResult(signInIntent, RC_SIGN_IN)
            }

        }




        return viewLoginAndRegister
    }

    private fun showLoadingLayout() {
        logoImage.visibility = View.GONE
        signInButton.visibility = View.GONE
        appNameText.visibility = View.GONE
        loadingLayout.visibility = View.VISIBLE
    }

    private fun showRegularLayout() {
        logoImage.visibility = View.VISIBLE
        signInButton.visibility = View.VISIBLE
        appNameText.visibility = View.VISIBLE
        loadingLayout.visibility = View.GONE
    }

    private fun loadBackGroundImage() {
        Glide
            .with(requireContext())
            .load(R.mipmap.ic_launcher_foreground)
            .into(logoImage)
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
                    onAuthSuccess(FirebaseAuth.getInstance().currentUser!!)
                } else {
                    // If sign in fails, display a message to the user.
                    CommonUtils.getInstance().showToast("Could not log it, please try again")
                    Log.d(TAG, "signInWithCredential:failure", task.exception)
                }
            }
    }

    private fun onAuthSuccess(fbUser: FirebaseUser){
        DatabaseManager.loadCurrentUser(fbUser){
            findNavController().navigate(R.id.action_fragmentAuth_to_fragmentHome)
        }

    }

    private fun userLoggedIn(): Boolean {
        mAuth = FirebaseAuth.getInstance()
        return mAuth.currentUser != null
    }

    private fun findViews() {
        signInButton = viewLoginAndRegister.findViewById(R.id.sign_in_button)
        logoImage = viewLoginAndRegister.findViewById(R.id.sign_in_coverImage)
        appNameText = viewLoginAndRegister.findViewById(R.id.auth_LBL_app_name)
        loadingLayout =  viewLoginAndRegister.findViewById(R.id.auth_LAY_loading)
    }
}