package com.rymtaruk.hospital.ui.search

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import androidx.recyclerview.widget.RecyclerView
import com.rymtaruk.hospital.databinding.ItemHospitalBinding
import com.rymtaruk.hospital.model.HospitalData

class SearchAdapter : RecyclerView.Adapter<SearchAdapter.ViewHolder>(), Filterable {
    private var _items: MutableList<HospitalData>? = null
    private var _itemsTemp: MutableList<HospitalData>? = null

    var items: MutableList<HospitalData>
        get() {
            if (_items == null) {
                _items = ArrayList(0)
            }
            return _items as MutableList<HospitalData>
        }
        set(value) {
            _items = value
        }

    val itemsTemp: MutableList<HospitalData>
        get() {
            if (_itemsTemp == null)
                _itemsTemp = ArrayList(items)
            return _itemsTemp as MutableList<HospitalData>
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            ItemHospitalBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val hospitalData = items[position]

        holder.name.text = hospitalData.name
        holder.address.text = hospitalData.address
        holder.phone.text = hospitalData.phone
    }

    override fun getItemCount(): Int {
        return items.size
    }

    class ViewHolder(view: ItemHospitalBinding) : RecyclerView.ViewHolder(view.root) {
        val name = view.tvName
        val address = view.tvAddress
        val phone = view.tvPhone
    }

    override fun getFilter(): Filter {
        return filtered
    }

    @Suppress("UNCHECKED_CAST")
    private val filtered = object : Filter() {
        override fun performFiltering(p0: CharSequence?): FilterResults {
            val filteredList = ArrayList<HospitalData>()
            if (p0 == null || p0.isEmpty()) {
                filteredList.addAll(itemsTemp)
            } else {
                val filterPattern = p0.toString().lowercase().trim()
                for (data in itemsTemp) {
                    if (data.name.isNotBlank()) {
                        if (data.name.lowercase().contains(filterPattern)) {
                            filteredList.add(data)
                        }
                    }
                }
            }
            val results = FilterResults()
            results.values = filteredList
            return results
        }

        @SuppressLint("NotifyDataSetChanged")
        override fun publishResults(p0: CharSequence?, p1: FilterResults?) {
            items.clear()
            items.addAll(p1!!.values as MutableList<HospitalData>)
            notifyDataSetChanged()
        }
    }
}