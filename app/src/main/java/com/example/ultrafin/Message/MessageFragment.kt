package com.example.ultrafin.Message

import android.app.AlertDialog
import android.app.DatePickerDialog
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.ultrafin.Contacts.ContactsAdapter
import com.example.ultrafin.Item
import com.example.ultrafin.MyViewModel
import com.example.ultrafin.R
import com.example.ultrafin.databinding.FragmentMessage2Binding
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.ktx.database
import com.google.firebase.database.ktx.getValue
import com.google.firebase.ktx.Firebase
import java.text.DateFormat
import java.text.SimpleDateFormat
import java.util.*

class MessageFragment : Fragment() {

    var _binding: FragmentMessage2Binding? = null
    val binding get() = _binding!!

    private lateinit var tvDataPicker: TextView
    private lateinit var btnDataPicker : Button

    var messageAdapter = MessageAdapter()
    val model by activityViewModels<MyViewModel>()


    override fun onCreateView(


        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        messageAdapter.fragmesg = this
        // Inflate the layout for this fragment
        //return inflater.inflate(R.layout.fragment_shopping, container, false)
        _binding = FragmentMessage2Binding.inflate(inflater, container, false)
        val view = binding.root
        return view
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val messageRV = binding.messageRV
        messageRV.adapter = messageAdapter
        binding.messageRV.layoutManager = LinearLayoutManager(requireContext())

        loadcontaaa()


   


//Back Button//*************
        binding.BTNBackMessage.setOnClickListener {
            findNavController().navigate(R.id.action_messageFragment_to_homeFragment2)
        }
// Clean Buttom
        binding.BTNClearList.setOnClickListener {
            model.selectedItems.clear()
            val database = Firebase.database
            val myRef_page = database.getReference("heyPAGE").child(Firebase.auth.currentUser!!.uid)
            myRef_page.removeValue()
            loadcontaaa()


        }


//Select date

        val calendar = Calendar.getInstance().time
        val dataformat = DateFormat.getDateInstance().format(calendar)


        //
        tvDataPicker = binding.newDateTV
        btnDataPicker = binding.BTNSelectdate

        val myCalendar = Calendar.getInstance()
        val datePicker = DatePickerDialog.OnDateSetListener { view, year, month, dayofMonth ->
            myCalendar.set(Calendar.YEAR, year)
            myCalendar.set(Calendar.MONTH, month)
            myCalendar.set(Calendar.DAY_OF_MONTH, dayofMonth)
            updateLable(myCalendar)
        }

        btnDataPicker.setOnClickListener {
            DatePickerDialog(requireContext(),datePicker,myCalendar.get(Calendar.YEAR), myCalendar.get(Calendar.MONTH), myCalendar.get(Calendar.DAY_OF_MONTH)).show()
        }

// Send tomorrow message
        binding.BTNTomorowWork.setOnClickListener {

            val checkedContacts = messageAdapter.getCheckedContacts()
            val phoneNumbers = checkedContacts.mapNotNull { if (!it.userName?.isBlank()!!) it.phoneNum else null }.joinToString(";")
            val intent = if (phoneNumbers.isNotBlank()) {
                Intent(Intent.ACTION_SENDTO).apply {
                    data = Uri.parse("smsto:$phoneNumbers")
                    putExtra("sms_body", "Hej,\n6:45 på kontoret\n7:00 på Värnhem\n\nMvh")
                }
            } else {
                null
            }
            intent?.let { startActivity(it) }

            }
// Send Time Work
        binding.BTNTimeWork.setOnClickListener {
            val finishTime = binding.newDateTV.text.toString()
            val checkedContacts = messageAdapter.getCheckedContacts()
            val phoneNumbers = checkedContacts.mapNotNull { if (!it.userName?.isBlank()!!) it.phoneNum else null }.joinToString(";")
            val intent = if (phoneNumbers.isNotBlank()) {
                Intent(Intent.ACTION_SENDTO).apply {
                    data = Uri.parse("smsto:$phoneNumbers")
                    putExtra("sms_body", finishTime + "\nBåt 1 (7:15 - 9:15)\nBåt 2 (11:15 - 13:15)")
                }
            } else {
                null
            }
            intent?.let { startActivity(it) }
        }

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
        swape.attachToRecyclerView(messageRV)
    }
    private fun showDialog(viewHolder: RecyclerView.ViewHolder) {
        val builder = AlertDialog.Builder(activity)
        builder.setTitle("Delete Item")
        builder.setMessage("are you sure to delete contact?")
        builder.setPositiveButton("Conformt") { dialog, which ->
            val position = viewHolder.adapterPosition
            val dddd = messageAdapter.fragmesg.model.selectedItems[position]
            model.selectedItems.remove(dddd)

        }
        builder.setNegativeButton("Cancel") { dialog, which ->
            val position = viewHolder.adapterPosition
            messageAdapter.notifyItemChanged(position)
        }
        builder.show()
    }
    private fun updateLable(myCalendar: Calendar) {
        val myFormat = "dd-MM-yyyy"
        val sdf = SimpleDateFormat(myFormat, Locale.GERMAN)
        tvDataPicker.setText(sdf.format(myCalendar.time))
    }
    fun loadcontaaa(){
        val database = Firebase.database
        val shopRef = database.getReference("heyPAGE").child(Firebase.auth.currentUser!!.uid)
        shopRef.get().addOnSuccessListener {
            val alluser = mutableListOf<Item>()
            it.children.forEach{childsnap->
                alluser.add(childsnap.getValue<Item>()!!)
            }
            messageAdapter.selectedItems = alluser
            messageAdapter.notifyDataSetChanged()

        }
    }
}