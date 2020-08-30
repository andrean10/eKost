package com.example.eKost.view.home

import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.eKost.R
import com.example.eKost.adapter.KostAdapter
import com.example.eKost.model.datakost.ResultsItem
import com.example.eKost.network.NetworkConnection
import kotlinx.android.synthetic.main.fragment_kost.*

class KostFragment : Fragment() {

    private lateinit var adapter: KostAdapter
    private lateinit var kostViewModel: KostViewModel

    companion object {
        private val EXTRA_STATE = "extra_state"
    }

    private val TAG = KostFragment::class.java.simpleName

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_kost, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        showLoading(true)
        adapter = KostAdapter()
        showRecycler()

        kostViewModel = ViewModelProvider(
            this,
            ViewModelProvider.NewInstanceFactory()
        ).get(KostViewModel::class.java)

        if (savedInstanceState == null) {
            observeDataKost()
        } else {
            val listData: ArrayList<ResultsItem> =
                savedInstanceState.getParcelableArrayList<ResultsItem>(
                    EXTRA_STATE
                ) as ArrayList<ResultsItem>
            adapter.setData(listData)
            showLoading(false)
        }

        adapter.setOnItemClickCallBack(object : KostAdapter.OnItemClickCallBack {
            override fun OnItemClicked(resultsItem: ResultsItem?) {
                val toDetailKostActivity =
                    KostFragmentDirections.actionNavigationKostToDetailKostActivity()
                toDetailKostActivity.idKost = resultsItem?.idkos?.toInt() as Int

                Log.d(TAG, "OnItemClicked: ${toDetailKostActivity.idKost}")

                view.findNavController().navigate(toDetailKostActivity)
            }
        })

        val networkConnection = NetworkConnection(requireContext().applicationContext)
        networkConnection.observe(viewLifecycleOwner, { isConnected ->
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

        search.setOnClickListener {
            view.findNavController().navigate(R.id.action_navigation_kost_to_navigation_search)
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putParcelableArrayList(EXTRA_STATE, adapter.getData())
    }

    private fun observeDataKost() {
        kostViewModel.getKost(activity?.applicationContext).observe(this.viewLifecycleOwner,
            { resultsItem ->
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