package com.khurshid.gufran.speciesapp.fragments

import android.os.Bundle
import android.support.v4.widget.SwipeRefreshLayout
import android.support.v7.widget.LinearLayoutManager
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.khurshid.gufran.speciesapp.R
import com.khurshid.gufran.speciesapp.adapter.LoadingSpecieEntity
import com.khurshid.gufran.speciesapp.adapter.SpeciesListAdapter
import com.khurshid.gufran.speciesapp.communication.retrofit.response.ServerResponse
import com.khurshid.gufran.speciesapp.dao.SpeciesDaoImpl
import com.khurshid.gufran.speciesapp.entity.Specie
import com.khurshid.gufran.speciesapp.presenter.SpeciesPresenter
import com.khurshid.gufran.speciesapp.util.ConverterUtil
import com.khurshid.gufran.speciesapp.view.SpecieView
import kotlinx.android.synthetic.main.fragment_species_list.*


/*
    Code Prepared by **Gufran Khurshid**.
    Sr. Android Developer.
    Email Id : gufran.khurshid@gmail.com
    Skype Id : gufran.khurshid
    Date: **30 December, 2017.**

    Description: **The main fragment that will show the species list**

    All Rights Reserved.
*/
class SpeciesListFragment : BaseFragment(), SpecieView {

    private lateinit var mPresenter: SpeciesPresenter
    private lateinit var mAdapter: SpeciesListAdapter
    private lateinit var mSpeciesList: MutableList<Any>
    private var mNextPageCount: String? = "1"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mSpeciesList = mutableListOf()
        retainInstance = true // this will make sure fragment will not get recreated on device configuration changes e.g. screen rotation , etc
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

        mAdapter = SpeciesListAdapter(mSpeciesList, SpeciesListAdapter.OnItemClickListener { specie, position ->
            toggleSpecieState(specie)
            mAdapter.notifyItemChanged(position)
        })

        speciesRecyclerView.adapter = mAdapter

        mPresenter = SpeciesPresenter(SpeciesDaoImpl(activity), this)
        mPresenter.getSpeciesList(mNextPageCount)


        mAdapter.setOnLoadMoreListener()
        {
            speciesRecyclerView.post(Runnable {
                if (mNextPageCount != null)
                    mPresenter.getSpeciesList(mNextPageCount)
                else
                    Toast.makeText(activity, getString(R.string.message_no_more_data), Toast.LENGTH_SHORT).show()
            })
        }

        swipeRefreshContainer.setOnRefreshListener(SwipeRefreshLayout.OnRefreshListener
        {
            mSpeciesList.clear()
            mNextPageCount = "1"
            mPresenter.getSpeciesList(mNextPageCount)
        })
    }

    override fun toggleSpecieState(specie: Specie) {
        mPresenter.changeSpecieState(specie)
    }


    override fun showWait() {
        if (mSpeciesList.size == 0) {
            progressBar.visibility = View.VISIBLE
            swipeRefreshContainer.visibility = View.GONE
        } else {
            mSpeciesList.add(LoadingSpecieEntity())
            mAdapter.notifyItemInserted(mSpeciesList.size - 1)
            progressBar.visibility = View.GONE
            speciesRecyclerView.visibility = View.VISIBLE
        }
    }

    override fun removeWait() {
        progressBar.visibility = View.GONE
        swipeRefreshContainer.visibility = View.VISIBLE
    }

    override fun onFailure(failureMessage: String?) {
        Toast.makeText(activity, getString(R.string.message_data_failed), Toast.LENGTH_SHORT).show()
        swipeRefreshContainer.isRefreshing = false
        removeLoadAtBottom()
    }

    override fun getSpeciesList(serverResponse: ServerResponse?) {
        swipeRefreshContainer.isRefreshing = false

        removeLoadAtBottom()

        if (!serverResponse!!.next.isNullOrBlank()) {
            mNextPageCount = ConverterUtil.getNextPageNumber(serverResponse!!.next)
        } else {
            mNextPageCount = null
        }


        if (serverResponse.species != null && serverResponse.species.size > 0) {
            synchronized(mSpeciesList) { mSpeciesList.addAll(serverResponse.species) }
        } else {
            Toast.makeText(activity, getString(R.string.message_no_more_data), Toast.LENGTH_SHORT).show()
        }

        mAdapter.notifyDataSetChangedManually()
    }

    private fun removeLoadAtBottom() {
        synchronized(mSpeciesList) {
            if (mSpeciesList.size != 0 && (mSpeciesList.get(mSpeciesList.size - 1) is LoadingSpecieEntity)) {
                mSpeciesList.removeAt(mSpeciesList.size - 1)
                mAdapter.notifyDataSetChangedManually()
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        mPresenter.onStop()
    }

}