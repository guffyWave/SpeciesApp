package com.khurshid.gufran.speciesapp.fragments

import android.os.Bundle
import android.support.v4.widget.SwipeRefreshLayout
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.khurshid.gufran.speciesapp.R
import com.khurshid.gufran.speciesapp.adapter.SpeciesListAdapter
import com.khurshid.gufran.speciesapp.communication.retrofit.response.ServerResponse
import com.khurshid.gufran.speciesapp.dao.SpeciesDaoImpl
import com.khurshid.gufran.speciesapp.entity.Specie
import com.khurshid.gufran.speciesapp.presenter.SpeciesPresenter
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

    lateinit var presenter: SpeciesPresenter;
    lateinit var adapter: SpeciesListAdapter;
    lateinit var speciesList: MutableList<Specie>;
    private var currentPage = 1;

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        speciesList = mutableListOf()
    }

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        rootView = inflater?.inflate(R.layout.fragment_species_list,
                container, false)
        return rootView;
    }


    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val layoutManager = LinearLayoutManager(activity);
        layoutManager.orientation = LinearLayoutManager.VERTICAL
        speciesRecyclerView.layoutManager = layoutManager
        speciesRecyclerView.setHasFixedSize(true)

        adapter = SpeciesListAdapter(activity, speciesList, SpeciesListAdapter.OnItemClickListener { specie ->
            Toast.makeText(activity, specie.name + " clicked !", Toast.LENGTH_LONG).show()
        })

        speciesRecyclerView.adapter = adapter

        presenter = SpeciesPresenter(SpeciesDaoImpl(activity), this);
        presenter.getSpeciesList("1")

        swipeRefreshContainer.setOnRefreshListener(SwipeRefreshLayout.OnRefreshListener {
            presenter.getSpeciesList("1")
        })
    }


    override fun showWait() {
        progressBar.visibility = View.VISIBLE
        swipeRefreshContainer.visibility = View.GONE
    }

    override fun removeWait() {
        progressBar.visibility = View.GONE
        swipeRefreshContainer.visibility = View.VISIBLE
    }

    override fun onFailure(failureMessage: String?) {
        Toast.makeText(activity, "Failed to load data", Toast.LENGTH_LONG).show()
        swipeRefreshContainer.isRefreshing = false
    }

    override fun getSpeciesList(serverResponse: ServerResponse?) {
        swipeRefreshContainer.isRefreshing = false
        speciesList.clear()
        //  speciesList = serverResponse!!.species
        speciesList.addAll(serverResponse!!.species)
        adapter.notifyDataSetChanged()
    }

    override fun onDestroy() {
        super.onDestroy()
        presenter.onStop()
    }

}