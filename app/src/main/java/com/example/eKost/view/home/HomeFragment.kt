package com.example.eKost.view.home

import android.os.Bundle
import android.os.Handler
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.eKost.R
import com.example.eKost.adapter.KostAdapter
import com.example.eKost.model.datakost.ResultsItem
import com.example.eKost.network.NetworkConnection
import kotlinx.android.synthetic.main.fragment_home.*

class HomeFragment : Fragment() {

    private lateinit var adapter: KostAdapter
    private lateinit var homeViewModel: HomeViewModel

    companion object {
        private val EXTRA_STATE = "extra_state"
    }

    private val TAG = HomeFragment::class.java.simpleName

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_home, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        showLoading(true)
        adapter = KostAdapter()
        showRecycler()

        homeViewModel = ViewModelProvider(this,
            ViewModelProvider.NewInstanceFactory()).get(HomeViewModel::class.java)

        if (savedInstanceState == null) {
            observeDataKost()
        } else {
            val listData: ArrayList<ResultsItem> =
                savedInstanceState.getParcelableArrayList<ResultsItem>(EXTRA_STATE
                ) as ArrayList<ResultsItem>
            adapter.setData(listData)
            showLoading(false)
        }

//        adapter.setOnItemClickCallBack(object : KostAdapter.OnItemClickCallBack {
//            override fun OnItemClicked(resultsItem: ResultsItem?) {
//                // Pindah Ke Detail Activity dengan membawa id
//                val toDetailKostActivity =
//                    KostFragmentDirections.actionKostFragmentToDetailKostActivity()
//                toDetailKostActivity.idKost = resultsItem?.idkos?.toInt() as Int
//                view.findNavController().navigate(toDetailKostActivity)
//            }
//        })

        val networkConnection = NetworkConnection(requireContext().applicationContext)
        networkConnection.observe(viewLifecycleOwner, Observer { isConnected ->
            if (isConnected) {
                // masih direncanakan
            } else {
                // masih direncanakan
                showLoading(false)
            }
        })

        swipeRefresh.setColorSchemeResources(R.color.colorAccent, R.color.colorPrimaryLight)
        swipeRefresh.setOnRefreshListener {
            Handler().postDelayed({
                swipeRefresh.isRefreshing = false
                observeDataKost()
            }, 3000)
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putParcelableArrayList(EXTRA_STATE, adapter.getData())
    }

    private fun observeDataKost() {
        homeViewModel.getKost(activity?.applicationContext).observe(this.viewLifecycleOwner, Observer { resultsItem ->
            if (resultsItem != null) {
                adapter.setData(resultsItem)
                showLoading(false)
            } else {
                showLoading(false)
            }
        })
    }

    fun showRecycler() {
        adapter.notifyDataSetChanged()

        recyclerView.layoutManager = LinearLayoutManager(activity)
        recyclerView.adapter = adapter
        recyclerView.setHasFixedSize(true)
    }

    private fun showLoading(state: Boolean) {
        if (state) {
            progressBar.visibility = View.VISIBLE
        } else {
            progressBar.visibility = View.GONE
        }
    }
}