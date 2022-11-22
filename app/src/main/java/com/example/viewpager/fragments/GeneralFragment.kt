package com.example.viewpager.fragments

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView 
import androidx.recyclerview.widget.RecyclerView.VERTICAL
import com.example.viewpager.CheckInternetConnectivity
import com.example.viewpager.MainActivity
import com.example.viewpager.R
import com.example.viewpager.adapters.RecyclerAdapter
import com.example.viewpager.viewmodel.SharedViewModel
import com.facebook.shimmer.ShimmerFrameLayout
import kotlinx.coroutines.MainScope


@Suppress("LABEL_NAME_CLASH")
class GeneralFragment : Fragment() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var recyclerAdapter: RecyclerAdapter
    private lateinit var shimmerFrameLayout: ShimmerFrameLayout
    private lateinit var checkInternet: CheckInternetConnectivity
    private val viewModel: SharedViewModel by lazy {
        ViewModelProvider(this)[SharedViewModel::class.java]
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_general, container, false)

        checkInternet = CheckInternetConnectivity(requireContext())
        recyclerView = view.findViewById(R.id.general_recycler_view)
        shimmerFrameLayout = view.findViewById(R.id.shimmer_frame_layout)
        shimmerFrameLayout.startShimmer()

        val layoutManager = LinearLayoutManager(activity)
        layoutManager.orientation = VERTICAL
        recyclerView.layoutManager = layoutManager

        checkInternet.observe(viewLifecycleOwner) {
            if (it) {
                viewModel.getAllNewsFromSharedViewModel("in","general")
                viewModel.getAllNewsLiveData.observe(viewLifecycleOwner) { news ->
                    if (news == null) {
                        Toast.makeText(context, "Unsuccessful Parsing", Toast.LENGTH_LONG).show()
                        return@observe
                    }
                    shimmerFrameLayout.stopShimmer()
                    shimmerFrameLayout.visibility = View.GONE
                    recyclerView.visibility = View.VISIBLE
                    recyclerAdapter = RecyclerAdapter(requireContext(), news.articles)
                    recyclerView.adapter = recyclerAdapter
                }
            }
            else {
                Toast.makeText(requireContext(),"Make sure you have internet connection",Toast.LENGTH_LONG).show()
            }
        }
        return view
    }
}