package e_card.e_cardaddress.adresschange.videoplayer

import android.content.Context
import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import e_card.e_cardaddress.adresschange.videoplayer.Model.Model_status

class Status_Adapter(
    private val context: Context,
    private val modelClass: ArrayList<Model_status>,
    private val clickListener: (Model_status) -> Unit
) : RecyclerView.Adapter<Status_Adapter.ViewData>() {

    class ViewData(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val ivStatus: ImageView = itemView.findViewById(R.id.iv_status)
        val icVideoIcon: ImageView = itemView.findViewById(R.id.ic_video_icon)
        val icVideoCard: CardView = itemView.findViewById(R.id.cv_video_card)

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewData {
        return ViewData(
            LayoutInflater.from(parent.context).inflate(R.layout.status_item, parent, false)
        )
    }

    override fun onBindViewHolder(holder: ViewData, position: Int) {
        if (modelClass[position].fileUri.endsWith("mp4")) {
            holder.icVideoCard.visibility = View.VISIBLE
            holder.icVideoIcon.visibility = View.VISIBLE
        } else {
            holder.icVideoCard.visibility = View.GONE
            holder.icVideoIcon.visibility = View.GONE
        }

        Glide.with(context).load(Uri.parse(modelClass[position].fileUri)).into(holder.ivStatus)
        holder.ivStatus.setOnClickListener {
            clickListener(modelClass[position])
        }
    }

    override fun getItemCount(): Int {
        return modelClass.size
    }
}