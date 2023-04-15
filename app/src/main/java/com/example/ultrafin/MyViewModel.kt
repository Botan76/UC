package com.example.ultrafin

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.*
import com.google.firebase.database.ktx.database
import com.google.firebase.database.ktx.getValue
import com.google.firebase.ktx.Firebase

class MyViewModel : ViewModel() {
    val originalItems: MutableLiveData<List<Item>> by lazy {
        MutableLiveData<List<Item>>()
    }
    var selectedItems = mutableListOf<Item>()

   /*fun addSelected(item: Item) {
        val currentSelected = selectedItems ?: emptyList()
        selectedItems = currentSelected + item
    }

    fun removeSelected(item: Item) {
        val currentSelected = selectedItems.value ?: emptyList()
        selectedItems.value = currentSelected - item
    }
*/

    fun addContact(usernameET: String, phoneET: String ) {
        val tempShopitem = Item(usernameET, phoneET, null)
//.child(Firebase.auth.currentUser!!.uid)
        val database = Firebase.database
        val shopRef = database.getReference("ultraList")
        shopRef.push().setValue(tempShopitem).addOnCompleteListener {
            loadContact()
        }
    }


    fun loadContact() {

        val database = Firebase.database
        val shopRef = database.getReference("ultraList")
        shopRef.get().addOnSuccessListener {
            val shoplist = mutableListOf<Item>()
            it.children.forEach { childsnap ->
                val tempShop = childsnap.getValue<Item>()!!
                tempShop.fbid = childsnap.key
                shoplist.add(tempShop)
            }
            originalItems.value = shoplist
        }
    }

    fun delectContact(delitem: Item){
        val database = Firebase.database
        val myRef_page = database.getReference("ultraList")
        myRef_page.child(delitem.fbid!!).removeValue().addOnCompleteListener {
            loadContact()
        }
    }

////////////////////////////////////Message
    fun addmesgContact(usernameET: String? ) {
        val tempShopitem = Item(usernameET)
        val database = Firebase.database
        val shopRef = database.getReference("mesageList")
        shopRef.push().setValue(tempShopitem).addOnCompleteListener {
            loadmesagContact()
        }
    }
    fun loadmesagContact() {

        val database = Firebase.database
        val shopRef = database.getReference("heyPAGE")
        shopRef.get().addOnSuccessListener {
            val shoplist = mutableListOf<Item>()
            it.children.forEach { childsnap ->
                val tempShop = childsnap.getValue<Item>()!!
                tempShop.fbid = childsnap.key
                shoplist.add(tempShop)
            }
            selectedItems = shoplist
        }
    }
    fun delectmesagContact(delitem: Item){
        val database = Firebase.database
        val myRef_page = database.getReference("mesageList")
        myRef_page.child(delitem.fbid!!).removeValue().addOnCompleteListener {
            loadmesagContact()
        }
    }


}