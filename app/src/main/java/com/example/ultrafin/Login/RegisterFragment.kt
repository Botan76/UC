package com.example.ultrafin.Login

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import com.example.ultrafin.MyViewModel
import com.example.ultrafin.R
import com.example.ultrafin.databinding.FragmentHomeBinding
import com.example.ultrafin.databinding.FragmentRegisterBinding


class RegisterFragment : Fragment() {
    var _binding : FragmentRegisterBinding? = null
    val binding get() = _binding!!
    val model by viewModels<LoginViewModel>()


    override fun onCreateView(


        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        //return inflater.inflate(R.layout.fragment_shopping, container, false)
        _binding = FragmentRegisterBinding.inflate(inflater, container, false)
        val view = binding.root
        return view
    }
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val errorObserver = Observer<String> { errorMess ->
            Toast.makeText(requireContext(), errorMess, Toast.LENGTH_LONG).show()
        }
        model.errorMessage.observe(viewLifecycleOwner, errorObserver)

        binding.BTNRegister.setOnClickListener {
            val useremail = binding.ETEmail.text.toString()
            val userpassword = binding.ETPassword.text.toString()
            val permission = binding.ETPermission.text.toString()
           if (permission == ""){
               Toast.makeText(requireContext(), "You need the permission code", Toast.LENGTH_LONG).show()
               hideKeyboard(view)
           }else {
               if (permission == "XUC") {
                   model.register(useremail, userpassword)
                   hideKeyboard(view)
               } else {
                   Toast.makeText(
                       requireContext(),
                       "you permission's code is not correct",
                       Toast.LENGTH_LONG
                   ).show()
                   hideKeyboard(view)
               }
           }
        }







    }
    fun hideKeyboard(view: View){
        val inputMethodManager = view.context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        inputMethodManager.hideSoftInputFromWindow(view.windowToken, 0)
    }
}