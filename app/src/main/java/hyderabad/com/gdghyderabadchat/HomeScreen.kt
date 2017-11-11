package hyderabad.com.gdghyderabadchat

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.*
import hyderabad.com.gdghyderabadchat.adapter.PostedMessageAdapter
import hyderabad.com.gdghyderabadchat.model.Message
import hyderabad.com.gdghyderabadchat.utils.setEmpty
import kotlinx.android.synthetic.main.activity_home.*

/**
 * Main screen to show all the received messages and write messages to the database.
 */
class HomeScreen : AppCompatActivity() {

    private lateinit var mCollectionReference: CollectionReference
    private lateinit var mAdapter: PostedMessageAdapter
    private var mMessageList: ArrayList<Message> = ArrayList()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        //Collection reference under which all documents will be stored
        mCollectionReference = FirebaseFirestore.getInstance()
                .collection(AppConstants.FirebaseCollection.POST)

        rvMain.layoutManager = LinearLayoutManager(applicationContext)

        mAdapter = PostedMessageAdapter(mMessageList)
        rvMain.adapter = mAdapter

        writeMessage()
        readMessage()
    }

    /** Write the document in collection named "post" */
    private fun writeMessage() {
        btSend.setOnClickListener {
            val msg = etMessage.text.trim().toString()
            if (msg.isNotEmpty()) {
                val message = Message()
                message.postMessage = msg
                message.userName = FirebaseAuth.getInstance().currentUser?.displayName ?: getString(R.string.name_not_found)
                message.timestamp = System.currentTimeMillis()

                mCollectionReference.add(message)
                        .addOnCompleteListener(this) { task ->
                            if (!task.isSuccessful) {
                                Toast.makeText(applicationContext,
                                        getString(R.string.error_while_sending_data),
                                        Toast.LENGTH_SHORT).show()
                            }
                        }

                etMessage.setEmpty()
            }
        }
    }

    /** Fetch all the document from the collection named "post" and display it's content.
     * */
    private fun readMessage() {
        mCollectionReference
                .orderBy(AppConstants.FirebaseCollection.TIMESTAMP)
                .addSnapshotListener { querySnapshot, _ ->
                    if (querySnapshot != null && !querySnapshot.isEmpty) {

                        for (doc in querySnapshot.documentChanges) {
                            when (doc.type) {
                            //Initially called for all the document later called only when new document is added.
                                DocumentChange.Type.ADDED -> {
                                    mMessageList.add(doc.document.toObject(Message::class.java))
                                    mAdapter.notifyItemInserted(mMessageList.size)
                                    rvMain.smoothScrollToPosition(mMessageList.size)
                                }

                            //Called only when existing document is modified
                                DocumentChange.Type.MODIFIED -> {
                                }

                            //Called only when existing document is deleted
                                DocumentChange.Type.REMOVED -> {
                                }
                            }
                        }
                    }
                }
    }
}