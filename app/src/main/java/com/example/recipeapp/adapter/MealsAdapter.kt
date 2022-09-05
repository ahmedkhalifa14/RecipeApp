package com.example.recipeapp.adapter


import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.recipeapp.R
import com.example.recipeapp.model.Meal


class MealsAdapter : RecyclerView.Adapter<MealsAdapter.MealsViewHolder>() {

    inner class MealsViewHolder(view: View) : RecyclerView.ViewHolder(view)

    private val differCallBack = object : DiffUtil.ItemCallback<Meal>() {
        override fun areItemsTheSame(oldItem: Meal, newItem: Meal): Boolean {
            return oldItem.idMeal == newItem.idMeal
        }

        override fun areContentsTheSame(oldItem: Meal, newItem: Meal): Boolean {
            return oldItem == newItem
        }

    }
    val differ = AsyncListDiffer(this, differCallBack)
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MealsViewHolder {
        return MealsViewHolder(LayoutInflater.from(parent.context)
            .inflate(R.layout.category_item_layout, parent, false))

    }

    override fun onBindViewHolder(holder: MealsViewHolder, position: Int) {
        val meal = differ.currentList[position]
        holder.itemView.apply {
            Glide.with(this).load(meal.strMealThumb).into(findViewById(R.id.img_category))
            findViewById<TextView>(R.id.tvCategoryName).text = meal.strMeal
            setOnClickListener {
                onItemClickListener?.let { it(meal) }
            }

        }
    }

    override fun getItemCount(): Int {
        return differ.currentList.size
    }

    private var onItemClickListener: ((Meal) -> Unit)? = null
    fun setOnItemClickListener(listener: (Meal) -> Unit) {
        onItemClickListener = listener
    }
}