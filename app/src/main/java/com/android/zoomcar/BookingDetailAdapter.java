package com.android.zoomcar;

import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.zoomcar.api.BookingDetail;
import com.squareup.picasso.Picasso;

import io.realm.RealmResults;

/**
 * Created by hp pc on 12-09-2015.
 */
public class BookingDetailAdapter extends ArrayAdapter<BookingDetail> {
    private RealmResults<BookingDetail> data;
    private Context mContext;

    public BookingDetailAdapter(Context context, RealmResults<BookingDetail> data) {
        super(context, R.layout.booking_detail_list_item, data);
        // TODO Auto-generated constructor stub
        this.mContext = context;
        this.data = data;
    }

    @Override
    public BookingDetail getItem(int position) {
        return data.get(position);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final ViewHolder holder;
        if (convertView == null) {
            convertView = LayoutInflater.from(mContext).inflate(
                    R.layout.booking_detail_list_item, parent, false);
            holder = new ViewHolder();
            holder.carName = (TextView) convertView.findViewById(R.id.carName);
            holder.bookingDate = (TextView) convertView.findViewById(R.id.bookingDate);
            holder.imageView = (ImageView) convertView.findViewById(R.id.carImage);

            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        BookingDetail _objModel = data.get(position);


        holder.carName.setText(_objModel.getCarName());
        holder.bookingDate.setText("Booked on: "+_objModel.getDateTime());
        Picasso.with(mContext).load(_objModel.getCarImage())
                .into(holder.imageView);
        return convertView;

    }

    class ViewHolder {
        TextView carName;
        TextView bookingDate;
        ImageView imageView;
    }
}
