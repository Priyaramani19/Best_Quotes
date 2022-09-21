package com.example.quotesapp.Screen.CategoryScreen.Controller

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.quotesapp.Screen.CategoryScreen.ModelClass.ModelData
import com.example.quotesapp.R
import com.example.quotesapp.Screen.CategoryScreen.Screen.MainActivity
import com.example.quotesapp.Screen.QuotesScreen.Screen.QuotesActivity
import java.util.ArrayList

class MyCatAdapter(
    val mainActivity: MainActivity,
    val list: ArrayList<ModelData>,
    val images: Array<Int>
) : RecyclerView.Adapter<MyCatAdapter.ViewData>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewData {
        var view = LayoutInflater.from(mainActivity).inflate(R.layout.cat_item,parent,false)
        return ViewData(view)
    }

    override fun onBindViewHolder(holder: ViewData, position: Int) {
        holder.catTxt.text = list[position].name
        holder.catImg.setImageResource(images[position])

        holder.catImg.setOnClickListener {
            var intent = Intent(mainActivity, QuotesActivity::class.java)
            intent.putExtra("n1",list[position]._id)
            intent.putExtra("n2",list[position].name)
            mainActivity.startActivity(intent)
        }
    }

    override fun getItemCount(): Int {
        return list.size
    }

    class ViewData(itemView: View) : RecyclerView.ViewHolder(itemView){
        var catTxt = itemView.findViewById<TextView>(R.id.catTxt)
        var catImg = itemView.findViewById<ImageView>(R.id.catImg)
    }

}
