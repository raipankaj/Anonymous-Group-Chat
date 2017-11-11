package hyderabad.com.gdghyderabadchat

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.firebase.ui.auth.AuthUI
import com.firebase.ui.auth.IdpResponse
import com.google.firebase.auth.FirebaseAuth
import java.util.*

/**
 * This activity determines user is already logged in or not.
 * If user is not logged in then show the login screen to the user.
 */
class SignInActivity : AppCompatActivity() {

    companion object {
        val RC_SIGN = 111
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        //If user already logged in then start home screen activity
        if (FirebaseAuth.getInstance().currentUser != null) {
            startActivity(Intent(this@SignInActivity, HomeScreen::class.java))
            finish()
        } else {
            startActivityForResult(
                    AuthUI.getInstance()
                            .createSignInIntentBuilder()
                            .setAvailableProviders(Arrays.asList(AuthUI.IdpConfig.Builder(AuthUI.EMAIL_PROVIDER).build(),
                                    AuthUI.IdpConfig.Builder(AuthUI.GOOGLE_PROVIDER).build()))
                            .build(),
                    RC_SIGN
            )
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == RC_SIGN) {
            val idpResponse= IdpResponse.fromResultIntent(data)

            if (resultCode == Activity.RESULT_OK) {
                if (idpResponse != null) {
                    startActivity(Intent(this@SignInActivity, HomeScreen::class.java))
                }
                finish()
            }
        }
    }
}