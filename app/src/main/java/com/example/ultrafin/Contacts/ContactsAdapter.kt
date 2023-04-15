package com.example.ultrafin.Contacts

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
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase

class ContactsAdapter(): RecyclerView.Adapter<ContactsAdapter.ViewHolder>() {
    lateinit var frag : ContactsFragment
    val database = Firebase.database.reference
    val selectedItems = HashSet<Item>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {

        val view = LayoutInflater.from(parent.context).inflate(R.layout.person_item, parent,false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        frag.model.originalItems.value?.let {
            return it.size
        }
        return 0
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = frag.model.originalItems.value!![position]
        holder.bind(item)




    }
    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        val checkBox1: CheckBox = itemView.findViewById(R.id.checkBox)
        val username1: TextView = itemView.findViewById(R.id.PersonTV)
        val phone1: TextView = itemView.findViewById(R.id.phoneTV)
        fun bind(item: Item) {

            username1.text = item.userName
            phone1.text = item.phoneNum
            checkBox1.setOnCheckedChangeListener { _, isChecked ->

                //val viewModel = ViewModelProvider(itemView.context as ViewModelStoreOwner).get(MyViewModel::class.java)
                if (isChecked) {
                    //frag.model.selectedItems.add(item)
                    //addselect(item)
                    selectedItems.add(item)

                    selectedItems.forEach { item ->
                        val database = Firebase.database
                        val myRef_page = database.getReference("heyPAGE").child(Firebase.auth.currentUser!!.uid)
                        myRef_page.child(item.fbid!!).setValue(item)
                }
                } else {

                  // frag.model.selectedItems.remove(item)
                    //removeselect(item)
                    //selectedItems.remove(item)
                    val database = Firebase.database
                    val myRef_page = database.getReference("heyPAGE").child(Firebase.auth.currentUser!!.uid)
                    myRef_page.child(item.fbid!!).removeValue().addOnCompleteListener {
                        }
                }
            }
        }
    }
    fun addselect(item: Item){
        val database = Firebase.database
        val myRef_page = database.getReference("heyPAGE").child(Firebase.auth.currentUser!!.uid)
        val addnew = Item(item.userName)
        myRef_page.push().setValue(addnew)
    }
    fun removeselect(item:Item){
        val database = Firebase.database
        val myRef_page = database.getReference("heyPAGE").child(Firebase.auth.currentUser!!.uid)
        myRef_page.child(item.fbid!!).removeValue().addOnCompleteListener {

        }
    }


}