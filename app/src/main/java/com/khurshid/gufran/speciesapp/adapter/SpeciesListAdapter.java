package com.khurshid.gufran.speciesapp.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.TextView;

import com.khurshid.gufran.speciesapp.R;
import com.khurshid.gufran.speciesapp.entity.Specie;

import java.util.List;

/**
 * Created by gufran on 30/12/17.
 */

public class SpeciesListAdapter extends RecyclerView.Adapter<SpeciesListAdapter.ViewHolder> {

    private final OnItemClickListener listener;
    private List<Specie> specieList;
    private Context context;


    public SpeciesListAdapter(Context context, List<Specie> specieList, OnItemClickListener listener) {
        this.specieList = specieList;
        this.listener = listener;
        this.context = context;
    }

    @Override
    public SpeciesListAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_specie_view, parent,false);
        return new ViewHolder(view);
    }


    @Override
    public void onBindViewHolder(SpeciesListAdapter.ViewHolder holder, int position) {
        holder.click(specieList.get(position), listener);
        holder.specieNameTV.setText(specieList.get(position).getName());
        holder.classificationTV.setText(specieList.get(position).getClassification());
        holder.designationTV.setText(specieList.get(position).getDesignation());
    }


    @Override
    public int getItemCount() {
        return specieList.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView specieNameTV, classificationTV, designationTV;

        public ViewHolder(View itemView) {
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

    public interface OnItemClickListener {
        void onClick(Specie specie);
    }


}
