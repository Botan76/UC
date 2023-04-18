package com.example.ultrafin.Login

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import com.example.ultrafin.R
import com.example.ultrafin.databinding.FragmentLoginBinding


class LoginFragment : Fragment() {


    val model by viewModels<LoginViewModel>()

    var _binding : FragmentLoginBinding? = null
    val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        //return inflater.inflate(R.layout.fragment_shopping, container, false)
        _binding = FragmentLoginBinding.inflate(inflater, container, false)
        val view = binding.root
        return view
    }
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        (requireActivity() as AppCompatActivity).supportActionBar?.hide()

        val errorObserver = Observer<String> { errorMess ->
            Toast.makeText(requireContext(), errorMess, Toast.LENGTH_LONG).show()
        }
        model.errorMessage.observe(viewLifecycleOwner, errorObserver)

binding.BTNRegister.setOnClickListener {
    requireActivity().supportFragmentManager.beginTransaction().replace(R.id.mainFragCon, RegisterFragment())
        .addToBackStack(null).commit()

}


        binding.BTNLogin.setOnClickListener {
            val useremail = binding.ETEmail.text.toString()
            val userpassword = binding.ETPassword.text.toString()

            model.loginn(useremail, userpassword)
        }





    }
}