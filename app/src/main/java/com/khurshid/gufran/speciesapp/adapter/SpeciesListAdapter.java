package com.khurshid.gufran.speciesapp.adapter;

import android.graphics.Color;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.khurshid.gufran.speciesapp.R;
import com.khurshid.gufran.speciesapp.entity.Specie;
import com.khurshid.gufran.speciesapp.management.SpeciesApp;

import java.util.List;

/*
    Code Prepared by **Gufran Khurshid**.
    Sr. Android Developer.
    Email Id : gufran.khurshid@gmail.com
    Skype Id : gufran.khurshid
    Date: **30 December, 2017.**

    Description: *Adapter converts objects list in viewable cards*
     **

    All Rights Reserved.
*/
public class SpeciesListAdapter extends RecyclerView.Adapter {

    private final OnItemClickListener mListener;
    private List<Object> mSpecieList;
    private final int ITEM_VIEW_TYPE_SPECIE = 1;
    private final int ITEM_VIEW_TYPE_LOADING = 2;
    private OnLoadMoreListener mOnLoadMoreListener;
    private boolean isLoading = false;


    public SpeciesListAdapter(List<Object> mSpecieList, OnItemClickListener mListener) {
        this.mSpecieList = mSpecieList;
        this.mListener = mListener;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == ITEM_VIEW_TYPE_SPECIE) {
            return new SpecieViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_specie_view, parent, false));
        } else {
            return new LoadingViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_loading, parent, false));
        }
    }


    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

        if (position >= getItemCount() - 1 && !isLoading && mOnLoadMoreListener != null) {
            isLoading = true;
            mOnLoadMoreListener.onLoadMore();
        }
        if (getItemViewType(position) == ITEM_VIEW_TYPE_SPECIE) {
            SpecieViewHolder specieViewHolder = (SpecieViewHolder) holder;
            Specie specie = (Specie) mSpecieList.get(position);
            specieViewHolder.setClick(specie, position, mListener);
            specieViewHolder.specieNameTV.setText(specie.getName());
            specieViewHolder.classificationTV.setText(specie.getClassification());
            specieViewHolder.designationTV.setText(specie.getDesignation());

            //TO-DO better way is to make a selector , selecting states based on Specie state
            if (specie.getSpecieStatus().equals(Specie.ACTIVE)) {
                specieViewHolder.specieCardView.setBackgroundColor(Color.WHITE);
                specieViewHolder.specieNameTV.setTextColor(SpeciesApp.getAppContext().getResources().getColor(R.color.colorPrimary));
                specieViewHolder.classificationTV.setTextColor(SpeciesApp.getAppContext().getResources().getColor(R.color.colorAccent));
                specieViewHolder.designationTV.setTextColor(SpeciesApp.getAppContext().getResources().getColor(R.color.colorAccent));
            } else {
                specieViewHolder.specieCardView.setBackgroundColor(Color.LTGRAY);
                specieViewHolder.specieNameTV.setTextColor(SpeciesApp.getAppContext().getResources().getColor(android.R.color.black));
                specieViewHolder.classificationTV.setTextColor(SpeciesApp.getAppContext().getResources().getColor(android.R.color.darker_gray));
                specieViewHolder.designationTV.setTextColor(SpeciesApp.getAppContext().getResources().getColor(android.R.color.darker_gray));
            }
        }
    }


    @Override
    public int getItemCount() {
        return mSpecieList.size();
    }

    public synchronized void notifyDataSetChangedManually() {
        super.notifyDataSetChanged();
        isLoading = false;
    }


    @Override
    public int getItemViewType(int position) {
        if (mSpecieList.get(position) instanceof LoadingSpecieEntity) {
            return ITEM_VIEW_TYPE_LOADING;
        } else
            return ITEM_VIEW_TYPE_SPECIE;
    }

    public void setOnLoadMoreListener(OnLoadMoreListener mOnLoadMoreListener) {
        this.mOnLoadMoreListener = mOnLoadMoreListener;
    }


    public class SpecieViewHolder extends RecyclerView.ViewHolder {
        TextView specieNameTV, classificationTV, designationTV;
        CardView specieCardView;

        public SpecieViewHolder(View itemView) {
            super(itemView);
            specieCardView = itemView.findViewById(R.id.specieCardView);
            specieNameTV = itemView.findViewById(R.id.specieNameTV);
            classificationTV = itemView.findViewById(R.id.classificationTV);
            designationTV = itemView.findViewById(R.id.designationTV);
        }


        public void setClick(final Specie specie, final int position, final OnItemClickListener listener) {
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listener.onClick(specie, position);
                }
            });
        }
    }

    public class LoadingViewHolder extends RecyclerView.ViewHolder {
        public ProgressBar progressBar;

        public LoadingViewHolder(View itemView) {
            super(itemView);
            progressBar = itemView.findViewById(R.id.progressBar);
        }
    }

    public interface OnItemClickListener {
        void onClick(Specie specie, int position);
    }

}
