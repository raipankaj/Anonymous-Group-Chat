package hyderabad.com.gdghyderabadchat

import android.os.Bundle
import android.support.transition.TransitionManager
import android.support.v7.app.AppCompatActivity
import kotlinx.android.synthetic.main.placeholders.*

/**
 * Not used, only for demo purpose to show how to use placeholder of constraint layout.
 */
class PlaceholderActivity : AppCompatActivity(){

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.placeholders)

        var counter = 0

        btChange.setOnClickListener {
            counter += 1
            if (counter % 2 == 0) {
                onImageChange(ivMain.id)
            } else {
                onImageChange(ivSecondary.id)
            }
        }
    }

    private fun onImageChange(viewId:Int) {
        TransitionManager.beginDelayedTransition(constraint)
        placeholder.setContentId(viewId)
    }
}