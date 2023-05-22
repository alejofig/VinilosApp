import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RatingBar
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.mobiles.vinilosapp.R
import com.mobiles.vinilosapp.models.Comment

class CommentAdapter(private val comments: List<Comment>) : RecyclerView.Adapter<CommentAdapter.CommentViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CommentViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.comment_item, parent, false)
        return CommentViewHolder(view)
    }

    override fun onBindViewHolder(holder: CommentViewHolder, position: Int) {
        val comment = comments[position]
        holder.bind(comment)
    }

    override fun getItemCount(): Int {
        return comments.size
    }

    inner class CommentViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val descripcionTextView: TextView = itemView.findViewById(R.id.descripcionTextView)
        private val ratingBar: RatingBar = itemView.findViewById(R.id.ratingBar)

        fun bind(comment: Comment) {
            descripcionTextView.text = comment.description
            ratingBar.rating = comment.rating.toFloat()
        }
    }
}
