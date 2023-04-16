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
import com.example.ultrafin.databinding.FragmentBoatplaceBinding
import com.example.ultrafin.databinding.FragmentHomeBinding


class BoatplaceFragment : Fragment() {


    var _binding : FragmentBoatplaceBinding? = null
    val binding get() = _binding!!


    override fun onCreateView(


        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        //return inflater.inflate(R.layout.fragment_shopping, container, false)
        _binding = FragmentBoatplaceBinding.inflate(inflater, container, false)
        val view = binding.root
        return view
    }
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        binding.finnpartner.setOnClickListener {
            val url = "https://www.marinetraffic.com/en/ais/home/shipid:327305/zoom:10"
            val intent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
            startActivity(intent)
            }
        binding.finntrader.setOnClickListener {
            val url = "https://www.marinetraffic.com/en/ais/home/shipid:37900/zoom:14"
            val intent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
            startActivity(intent)
        }
        binding.finnfellow.setOnClickListener{
            val url = "https://www.marinetraffic.com/en/ais/home/shipid:5588/zoom:14"
            val intent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
            startActivity(intent)
        }
        binding.smeralda.setOnClickListener {
            val url = "https://www.marinetraffic.com/en/ais/home/shipid:210984/zoom:14"
            val intent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
            startActivity(intent)
        }

        binding.europalink.setOnClickListener {
            val url = "https://www.marinetraffic.com/en/ais/home/shipid:281955/zoom:10"
            val intent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
            startActivity(intent)
        }




    // back button
        binding.backtohome.setOnClickListener {
            findNavController().navigate(R.id.action_boatplaceFragment_to_homeFragment)
        }
    }

}