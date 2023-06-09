package com.example.birra_2

import android.app.Activity
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.app.ActivityOptionsCompat
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.Birra_2.R
import com.example.Birra_2.databinding.ItemBeerBinding
import com.squareup.picasso.Picasso

class Adapter : ListAdapter<Beer, Holder>(object : DiffUtil.ItemCallback<Beer>(){
    override fun areItemsTheSame(oldItem: Beer, newItem: Beer): Boolean {

        return oldItem.id==newItem.id
    }
    //controlla se le due classi sono uguali basandosi su tutta la classe, si controlla l'id
    override fun areContentsTheSame(oldItem: Beer, newItem: Beer): Boolean {
        return oldItem==newItem //qui si controllano tutti gli attributi della classe e non solo la memoria per accertarsi se sono due elementi uguali
    }
}) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        return Holder(ItemBeerBinding.inflate(LayoutInflater.from(parent.context)))
    } //crea un istanza "Holder" (definita poi) che poi verrata spostata, nell'Activity main, all'interno del recycler view

    override fun onBindViewHolder(holder: Holder, position: Int) {
        holder.bind(getItem(position))

        holder.itemView.setOnClickListener {
            val intent = Intent(holder.itemView.context, BeerInfo::class.java)
            intent.putExtra("item_id", getItem(position).id)
            intent.putExtra("item_image", getItem(position).image_url)
            intent.putExtra("item_transition_name", "transition_$position")

            val options = ActivityOptionsCompat.makeSceneTransitionAnimation(
                holder.itemView.context as Activity,
                holder.itemView.findViewById(R.id.imgBeer),
                "transition_$position"
            )

            holder.itemView.context.startActivity(intent, options.toBundle())
        }
    } //quando l'elemento poi verrà visto nello schermo
}

class Holder(private val binding: ItemBeerBinding) :RecyclerView.ViewHolder(binding.root){
    fun bind(beer : Beer){
        binding.header.text=beer.name
        binding.desc.text=beer.tagline
        Picasso.get()
            .load(beer.image_url)
            .resize(130, 500) // Imposta la dimensione desiderata
            .centerCrop() // Effettua il ridimensionamento con taglio al centro
            .into(binding.imgBeer)
    }
    //imposta l'xml
}