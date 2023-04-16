package com.example.ultrafin

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.ultrafin.databinding.FragmentHomeBinding
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import android.content.pm.PackageManager



class HomeFragment : Fragment() {

    var _binding : FragmentHomeBinding? = null
    val binding get() = _binding!!

    val model by viewModels<MyViewModel>()

    override fun onCreateView(


        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        //return inflater.inflate(R.layout.fragment_shopping, container, false)
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        val view = binding.root
        return view
    }
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

///// Logout Button
        binding.BTNLogout.setOnClickListener {
            Firebase.auth.signOut()
        }
///// Contact Button
        binding.homeContact.setOnClickListener {
            findNavController().navigate(R.id.action_homeFragment_to_contactsFragment2)

        }
///// Email Button
        binding.homeEmail.setOnClickListener {
            val intent = context?.packageManager?.getLaunchIntentForPackage("com.microsoft.office.outlook")
            if (intent != null) {
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                intent.addCategory(Intent.CATEGORY_LAUNCHER)
                startActivity(intent)
            } else {
                Toast.makeText(requireContext(), "Outlook app not found.", Toast.LENGTH_SHORT).show()
            }

        }

///// Message
        binding.homeMessage.setOnClickListener {
            findNavController().navigate(R.id.action_homeFragment_to_messageFragment2)
        }
///// Rapor
        binding.homeRaport.setOnClickListener {

            findNavController().navigate(R.id.action_homeFragment_to_boatplaceFragment)
        }



    }
}