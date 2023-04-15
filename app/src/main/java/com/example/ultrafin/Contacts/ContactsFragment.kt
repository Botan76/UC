package com.example.ultrafin.Contacts

import android.app.AlertDialog
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.ultrafin.Item
import com.example.ultrafin.MyViewModel
import com.example.ultrafin.R
import com.example.ultrafin.databinding.FragmentContactsBinding


class ContactsFragment : Fragment() {
    private lateinit var binding: FragmentContactsBinding

    var originalItemsAdapter = ContactsAdapter()
    val model by activityViewModels<MyViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        originalItemsAdapter.frag = this
        binding = FragmentContactsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val contactRV = binding.contactRV
        binding.contactRV.adapter = originalItemsAdapter
        binding.contactRV.layoutManager = LinearLayoutManager(requireContext())

        contactRV.setItemViewCacheSize(50)


        val shopObserver = Observer<List<Item>> {
            originalItemsAdapter.notifyDataSetChanged()
        }
        model.originalItems.observe(viewLifecycleOwner, shopObserver)
        model.loadContact()

        // Swipe to Delete
        val itemSwipe = object : ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT) {
            override fun onMove(
                recyclerView: RecyclerView, viewHolder: RecyclerView.ViewHolder, target: RecyclerView.ViewHolder): Boolean {
                return false
            }

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {

                showDialog(viewHolder)
            }
        }
        val swape = ItemTouchHelper(itemSwipe)
        swape.attachToRecyclerView(contactRV)
      //send SMS************


      //Go to ADD Contact//***********
        binding.BTNAddcontact.setOnClickListener {
            findNavController().navigate(R.id.action_contactsFragment2_to_addContactFragment)

        }
      //Back Button//*************
       binding.BTNBack.setOnClickListener {
            findNavController().navigate(R.id.action_contactsFragment2_to_homeFragment)

       }
        binding.BTNGotomessage.setOnClickListener {
            findNavController().navigate(R.id.action_contactsFragment2_to_messageFragment)

        }
    }
      //Swape to delete***********
    private fun showDialog(viewHolder: RecyclerView.ViewHolder) {
        val builder = AlertDialog.Builder(activity)
        builder.setTitle("Delete Item")
        builder.setMessage("are you sure to delete contact?")
        builder.setPositiveButton("Conformt") { dialog, which ->
            val position = viewHolder.adapterPosition
            val dddd = originalItemsAdapter.frag.model.originalItems.value!![position]
            model.delectContact(dddd)
        }
        builder.setNegativeButton("Cancel") { dialog, which ->
            val position = viewHolder.adapterPosition
            originalItemsAdapter.notifyItemChanged(position)
        }
        builder.show()
    }
}
