package com.khurshid.gufran.speciesapp.fragments

import android.os.Bundle
import android.support.v4.widget.SwipeRefreshLayout
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.khurshid.gufran.speciesapp.R
import com.khurshid.gufran.speciesapp.adapter.LoadSpecieEntity
import com.khurshid.gufran.speciesapp.adapter.SpeciesListAdapter
import com.khurshid.gufran.speciesapp.communication.retrofit.response.ServerResponse
import com.khurshid.gufran.speciesapp.dao.SpeciesDaoImpl
import com.khurshid.gufran.speciesapp.presenter.SpeciesPresenter
import com.khurshid.gufran.speciesapp.util.DummyUtil
import com.khurshid.gufran.speciesapp.view.SpecieView
import kotlinx.android.synthetic.main.fragment_species_list.*


/*
    Code Prepared by **Gufran Khurshid**.
    Sr. Android Developer.
    Email Id : gufran.khurshid@gmail.com
    Skype Id : gufran.khurshid
    Date: **30 December, 2017.**

    Description: **BaseFragment is used to initialize common variables and call common methods**

    All Rights Reserved.
*/
class SpeciesListFragment : BaseFragment(), SpecieView {

    lateinit var presenter: SpeciesPresenter
    lateinit var adapter: SpeciesListAdapter
    lateinit var speciesList: MutableList<Any>
    private var nextPageCount: String? = "1"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        speciesList = mutableListOf()
    }

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        rootView = inflater?.inflate(R.layout.fragment_species_list,
                container, false)
        return rootView
    }


    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val layoutManager = LinearLayoutManager(activity)
        layoutManager.orientation = LinearLayoutManager.VERTICAL
        speciesRecyclerView.layoutManager = layoutManager
        speciesRecyclerView.setHasFixedSize(true)

        adapter = SpeciesListAdapter(speciesList, SpeciesListAdapter.OnItemClickListener { specie ->
            Toast.makeText(activity, specie.name + " clicked !", Toast.LENGTH_LONG).show()
        })

        speciesRecyclerView.adapter = adapter

        presenter = SpeciesPresenter(SpeciesDaoImpl(activity), this)
        presenter.getSpeciesList(nextPageCount)


        adapter.setOnLoadMoreListener {
            speciesRecyclerView.post(Runnable {
                if (nextPageCount != null)
                    presenter.getSpeciesList(nextPageCount)
                else
                    Toast.makeText(activity, "No more data to show !", Toast.LENGTH_LONG).show()
            })
        }

        swipeRefreshContainer.setOnRefreshListener(SwipeRefreshLayout.OnRefreshListener {
            speciesList.clear()
            nextPageCount = "1"
            presenter.getSpeciesList(nextPageCount)
        })
    }


    override fun showWait() {
        if (speciesList.size == 0) {
            progressBar.visibility = View.VISIBLE
            swipeRefreshContainer.visibility = View.GONE
        } else {
            speciesList.add(LoadSpecieEntity());
            adapter.notifyItemInserted(speciesList.size - 1);
            progressBar.setVisibility(View.GONE);
            speciesRecyclerView.setVisibility(View.VISIBLE);
        }
    }

    override fun removeWait() {
        progressBar.visibility = View.GONE
        swipeRefreshContainer.visibility = View.VISIBLE
    }

    override fun onFailure(failureMessage: String?) {
        Toast.makeText(activity, "Failed to load data !", Toast.LENGTH_LONG).show()
        swipeRefreshContainer.isRefreshing = false
        removeLoadAtBottom()
    }

    override fun getSpeciesList(serverResponse: ServerResponse?) {
        swipeRefreshContainer.isRefreshing = false

        removeLoadAtBottom()

        if (!serverResponse!!.next.isNullOrBlank()) {
            nextPageCount = DummyUtil.getNextPageNumber(serverResponse!!.next)
        } else {
            nextPageCount = null
        }


        if (serverResponse != null && serverResponse.species != null && serverResponse!!.species.size > 0) {
            speciesList.addAll(serverResponse!!.species)
        } else {
            Toast.makeText(activity, "No more data to show !", Toast.LENGTH_LONG).show()
        }

        adapter.notifyDataSetChangedManually()
    }

    private fun removeLoadAtBottom() {
        if (speciesList.size != 0 && (speciesList.get(speciesList.size - 1) is LoadSpecieEntity)) {
            speciesList.removeAt(speciesList.size - 1)
            adapter.notifyDataSetChangedManually()
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        presenter.onStop()
    }

}