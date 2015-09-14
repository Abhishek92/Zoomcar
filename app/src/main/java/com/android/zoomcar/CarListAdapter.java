package com.android.zoomcar;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.zoomcar.api.CarDetailsModel;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by hp pc on 12-09-2015.
 */
public class CarListAdapter extends RecyclerView.Adapter<CarListAdapter.ViewHolder>/* implements Filterable*/ {

    private List<CarDetailsModel.Car> carList;
    private List<CarDetailsModel.Car> backupList;
    private Context mContext;
    private OnItemClickListener listener;

    public CarListAdapter(Context context, List<CarDetailsModel.Car> carList) {
        this.carList = carList;
        this.backupList = carList;
        this.mContext = context;
    }

    public void setOnItemClickListener(OnItemClickListener listener)
    {
        this.listener = listener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(
                R.layout.car_list_item, parent, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        CarDetailsModel.Car data = carList.get(position);
        Picasso.with(mContext).load(data.getImage())
                .into(holder.carImage);
        holder.carName.setText(data.getName());
        holder.carSeats.setText(data.getSeater()+" Seater");

    }

    @Override
    public int getItemCount() {
        return carList.size();
    }

    public void flushFilter(){
        carList=new ArrayList<>();
        carList.addAll(backupList);
        notifyDataSetChanged();
    }

    public void setFilter(String queryText) {

        carList = new ArrayList<>();
        queryText = queryText.toLowerCase();
        for (CarDetailsModel.Car item: backupList) {
            if (item.getName().toLowerCase().contains(queryText))
                carList.add(item);
        }
        notifyDataSetChanged();
    }



    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        protected ImageView carImage;
        protected TextView carName;
        protected TextView carSeats;

        public ViewHolder(View itemView) {
            super(itemView);
            this.carImage = (ImageView) itemView.findViewById(R.id.carImage);
            this.carName = (TextView) itemView.findViewById(R.id.carName);
            this.carSeats = (TextView) itemView.findViewById(R.id.seater);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            if(listener != null)
                listener.onItemClick(view, getAdapterPosition());
        }
    }

    public interface OnItemClickListener{
        public void onItemClick(View v, int position);
    }
}
