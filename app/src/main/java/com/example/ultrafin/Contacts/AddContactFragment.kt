package com.example.ultrafin.Contacts

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.ultrafin.MyViewModel
import com.example.ultrafin.R
import com.example.ultrafin.databinding.FragmentAddContactBinding


class AddContactFragment : Fragment() {
    var _binding : FragmentAddContactBinding? = null
    val binding get() = _binding!!
    val model by viewModels<MyViewModel>()
    private lateinit var originalItemsAdapter: ContactsAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        // Inflate the layout for this fragment
        //return inflater.inflate(R.layout.fragment_shopping, container, false)
        _binding = FragmentAddContactBinding.inflate(inflater, container, false)
        val view = binding.root
        return view
    }
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

    binding.BTNBack2Contact.setOnClickListener {
        findNavController().navigate(R.id.action_addContactFragment_to_contactsFragment2)
    }



     binding.BTNAdd2firebase.setOnClickListener {
         val username = binding.usernameET.text.toString()
         val userpassword = binding.phoneET.text.toString()

         if (username == "" || userpassword == "") {
             Toast.makeText(requireContext(), "fill it", Toast.LENGTH_SHORT).show()
         } else {
             model.addContact(username, userpassword)

             hideKeyboard(view)

             findNavController().navigate(R.id.action_addContactFragment_to_contactsFragment2)
         }
     }
    }

    fun hideKeyboard(view: View){
        val inputMethodManager = view.context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        inputMethodManager.hideSoftInputFromWindow(view.windowToken, 0)
    }
}

