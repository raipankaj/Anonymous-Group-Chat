package hyderabad.com.gdghyderabadchat.adapter

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import hyderabad.com.gdghyderabadchat.R
import hyderabad.com.gdghyderabadchat.model.Message
import kotlinx.android.synthetic.main.recycler_item.view.*

class PostedMessageAdapter(private val mListOfMessage: List<Message>)
    : RecyclerView.Adapter<PostedMessageAdapter.ContentViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): ContentViewHolder {

        return ContentViewHolder(LayoutInflater.from(parent?.context)
                .inflate(R.layout.recycler_item, parent, false))
    }

    override fun onBindViewHolder(holder: ContentViewHolder?, position: Int) {
        val questions = mListOfMessage[position]

        holder?.let {
            questions.apply {
                holder.postText.text = postMessage
            }
        }
    }

    override fun getItemCount(): Int {
        return mListOfMessage.size
    }

    inner class ContentViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val postText: TextView = itemView.tvPost
    }
}