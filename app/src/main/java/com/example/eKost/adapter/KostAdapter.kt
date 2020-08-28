package com.example.eKost.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.example.eKost.R
import com.example.eKost.model.datakost.ResultsItem
import kotlinx.android.synthetic.main.items.view.*

class KostAdapter: RecyclerView.Adapter<KostAdapter.KostViewHolder>() {

    private val data: ArrayList<ResultsItem> = ArrayList()
    private val URL_IMG = "http://192.168.43.42/eKost/gambarkost/"
    private var onItemClickCallBack: OnItemClickCallBack? = null

    fun setData(items: List<ResultsItem>) {
        data.clear()
        data.addAll(items)
        notifyDataSetChanged()
    }

    fun getData(): ArrayList<ResultsItem> {
        return data
    }

    fun setOnItemClickCallBack(onItemClickCallBack: OnItemClickCallBack) {
        this.onItemClickCallBack = onItemClickCallBack
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): KostViewHolder {
        return KostViewHolder(LayoutInflater
            .from(parent.context).inflate(R.layout.items, parent, false))
    }

    override fun onBindViewHolder(holder: KostViewHolder, position: Int) {
        holder.bind(data[position])
    }

    override fun getItemCount(): Int = data.size

    inner class KostViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        fun bind(resultsItem: ResultsItem) {
            with(itemView) {
                Glide.with(itemView.context)
                    .load(URL_IMG + resultsItem.gambar)
                    .apply(RequestOptions()
                        .placeholder(R.drawable.img_not_found)
                        .error(R.drawable.img_not_found)
                    )
                    .into(imageKost)
                owner.text = resultsItem.namakos
                cost.text = resultsItem.harga
                category.text = resultsItem.kategori

                itemView.setOnClickListener { onItemClickCallBack?.OnItemClicked(resultsItem) }
            }
        }
    }

    interface OnItemClickCallBack {
        fun OnItemClicked(resultsItem: ResultsItem?)
    }
}