package com.example.ultrafin.Message

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.TextView
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelStoreOwner
import androidx.recyclerview.widget.RecyclerView
import com.example.ultrafin.Item
import com.example.ultrafin.MyViewModel
import com.example.ultrafin.R

class MessageAdapter: RecyclerView.Adapter<MessageAdapter.ViewHolder>() {
    lateinit var fragmesg : MessageFragment
    var selectedItems = mutableListOf<Item>()

    class ViewHolder (view: View) : RecyclerView.ViewHolder(view){
        val username1 : TextView
        val password1 : TextView
        init {
            username1 = view.findViewById<TextView>(R.id.PersonTV)
            password1 = view.findViewById<TextView>(R.id.phoneTV)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {

        val view = LayoutInflater.from(parent.context).inflate(R.layout.mesgperson_item, parent,false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return selectedItems.size
        //return fragmesg.model.selectedItems.size

    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
       // val item = fragmesg.model.selectedItems[position]
        //val item = selectedItems[position]
        //holder.bind(item)
        holder.username1.text = (position+1).toString() + ". " + selectedItems[position].userName
        holder.password1.text =  selectedItems[position].phoneNum






    }
    /*inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        val username1: TextView = itemView.findViewById<TextView>(R.id.PersonTV)
        val phone1: TextView = itemView.findViewById<TextView>(R.id.phoneTV)
        fun bind(item: Item) {

            username1.text = (position+1).toString()+ ". "+item.userName
            phone1.text = item.phoneNum

        }
    }*/
    fun getCheckedContacts(): List<Item> {
        return selectedItems.toList()
    }


}