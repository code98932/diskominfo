package com.example.diskominfo.adapter

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.diskominfo.ItemClickListener
import com.example.diskominfo.Model.Item
import com.example.diskominfo.R

class FeedViewHoler(itemView: View):RecyclerView.ViewHolder(itemView),



    View.OnClickListener,View.OnLongClickListener {

    var txtTitle: TextView
    var description: TextView



    private var itemClickListener: ItemClickListener? = null

    init {
        txtTitle = itemView.findViewById(R.id.txt_title) as TextView
        description = itemView.findViewById(R.id.info) as TextView

        itemView.setOnClickListener(this)
        itemView.setOnLongClickListener(this)
    }

    fun setItemClickListener(itemClickListener: ItemClickListener) {
        this.itemClickListener = itemClickListener

    }

    override fun onClick(v: View?) {
        itemClickListener!!.onClick(v, adapterPosition, false)

    }

    override fun onLongClick(v: View?): Boolean {
        itemClickListener!!.onClick(v, adapterPosition, true)
        return true
    }
}
class FeedAdapter(private val rssObjects: Item,private val mContext:Context) : RecyclerView.Adapter<FeedViewHoler>() {

    private val inlater: LayoutInflater
    init {
        inlater = LayoutInflater.from(mContext)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FeedViewHoler {
        val itemView = inlater.inflate(R.layout.row,parent,false)
        return FeedViewHoler(itemView)
    }

    override fun onBindViewHolder(holder: FeedViewHoler, position: Int) {

        holder.txtTitle.text = rssObjects.items[position].title
        holder.description.text = rssObjects.items[position].description



        holder.setItemClickListener(ItemClickListener{view, position, isLongCLick ->

            if (!isLongCLick) {
                val browserIntent = Intent(Intent.ACTION_VIEW, Uri.parse(rssObjects.items[position].guid))
                browserIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK) // tambahin ini
                mContext.startActivity(browserIntent)
            }

        })
    }


    override fun getItemCount(): Int {
        return rssObjects.items.size

    }
}