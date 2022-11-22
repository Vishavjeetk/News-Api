package com.example.viewpager.adapters

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.viewpager.R
import com.example.viewpager.WebView
import com.example.viewpager.models.ArticleModel
import com.google.android.material.internal.ContextUtils
import java.security.AccessController.getContext

class RecyclerAdapter(context: Context, list: List<ArticleModel>): RecyclerView.Adapter<RecyclerAdapter.MyHolder>() {

    private var context: Context
    private var list: List<ArticleModel>
    init {
        this.context = context
        this.list = list
    }
    class MyHolder(view: View): RecyclerView.ViewHolder(view) {
        var title: TextView
        var description: TextView
        var image: ImageView
        var publishedAt: TextView

        init {
            title = view.findViewById(R.id.recycler_item_title)
            description = view.findViewById(R.id.recycler_item_description)
            publishedAt = view.findViewById(R.id.recycler_item_published_at)
            image = view.findViewById(R.id.recycler_item_image)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyHolder {
        return MyHolder(LayoutInflater.from(parent.context).inflate(R.layout.recycler_item,null))
    }

    override fun onBindViewHolder(holder: MyHolder, position: Int) {
        holder.title.text = list[position].title
        holder.description.text = list[position].description
        holder.publishedAt.text = list[position].publishedAt
        Glide.with(context)
            .load(list[position].urlToImage)
            .override(900,620)
            .into(holder.image)
        holder.itemView.setOnClickListener {
            val intent = Intent(context,WebView::class.java)
            intent.putExtra("URL", list[position].url)
            context.startActivity(intent)
        }
    }

    override fun getItemCount(): Int {
        return list.size
    }
}