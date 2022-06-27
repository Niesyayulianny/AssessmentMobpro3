package org.d3if1030.converterjarak.ui.jarak_kota

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import org.d3if1030.converterjarak.R
import org.d3if1030.converterjarak.databinding.ItemJarakKotaBinding
import org.d3if1030.converterjarak.model.JarakKota
import org.d3if1030.converterjarak.network.JarakKotaApi

class JarakKotaAdapter : RecyclerView.Adapter<JarakKotaAdapter.ViewHolder>() {
    private val data = mutableListOf<JarakKota>()
    fun updateData(newData: List<JarakKota>) {
        data.clear()
        data.addAll(newData)
        notifyDataSetChanged()
    }
    class ViewHolder(
        private val binding: ItemJarakKotaBinding
    ) : RecyclerView.ViewHolder(binding.root) {
        fun bind(jarakKota: JarakKota) = with(binding) {
            kota.text = jarakKota.nama
            jarak.text = jarakKota.jarak
            Glide.with(imageView.context)
                .load(JarakKotaApi.getJarakKotaUrl(jarakKota.image))
                .error(R.drawable.ic_launcher_foreground)
                .into(imageView)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemJarakKotaBinding.inflate(inflater, parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(data[position])
    }

    override fun getItemCount(): Int {
        return data.size
    }
}