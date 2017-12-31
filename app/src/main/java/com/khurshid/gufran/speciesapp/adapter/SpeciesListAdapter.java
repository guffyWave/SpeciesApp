package com.khurshid.gufran.speciesapp.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.khurshid.gufran.speciesapp.R;
import com.khurshid.gufran.speciesapp.entity.Specie;

import java.util.List;

/**
 * Created by gufran on 30/12/17.
 */

public class SpeciesListAdapter extends RecyclerView.Adapter {

    private final OnItemClickListener listener;
    private List<Object> specieList;
    private final int ITEM_VIEW_TYPE_SPECIE = 1;
    private final int ITEM_VIEW_TYPE_LOADING = 2;
    private OnLoadMoreListener mOnLoadMoreListener;
    private boolean isLoading = false;


    public SpeciesListAdapter(List<Object> specieList, OnItemClickListener listener) {
        this.specieList = specieList;
        this.listener = listener;
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
            Specie specie = (Specie) specieList.get(position);
            specieViewHolder.click(specie, listener);
            specieViewHolder.specieNameTV.setText(specie.getName());
            specieViewHolder.classificationTV.setText(specie.getClassification());
            specieViewHolder.designationTV.setText(specie.getDesignation());
        }
    }


    @Override
    public int getItemCount() {
        return specieList.size();
    }

    public void notifyDataSetChangedManually() {
        super.notifyDataSetChanged();
        isLoading = false;
    }


    @Override
    public int getItemViewType(int position) {
        if (specieList.get(position) instanceof LoadSpecieEntity) {
            return ITEM_VIEW_TYPE_LOADING;
        } else
            return ITEM_VIEW_TYPE_SPECIE;
    }

    public void setOnLoadMoreListener(OnLoadMoreListener mOnLoadMoreListener) {
        this.mOnLoadMoreListener = mOnLoadMoreListener;
    }


    public class SpecieViewHolder extends RecyclerView.ViewHolder {
        TextView specieNameTV, classificationTV, designationTV;

        public SpecieViewHolder(View itemView) {
            super(itemView);
            specieNameTV = (TextView) itemView.findViewById(R.id.specieNameTV);
            classificationTV = (TextView) itemView.findViewById(R.id.classificationTV);
            designationTV = (TextView) itemView.findViewById(R.id.designationTV);
        }


        public void click(final Specie specie, final OnItemClickListener listener) {
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listener.onClick(specie);
                }
            });
        }
    }

    public class LoadingViewHolder extends RecyclerView.ViewHolder {
        public ProgressBar progressBar;

        public LoadingViewHolder(View itemView) {
            super(itemView);
            progressBar = (ProgressBar) itemView.findViewById(R.id.progressBar);
        }
    }

    public interface OnItemClickListener {
        void onClick(Specie specie);
    }

}
