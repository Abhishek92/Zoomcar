package com.android.zoomcar.fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.zoomcar.MainActivity;
import com.android.zoomcar.R;
import com.android.zoomcar.Util;
import com.android.zoomcar.ZoomCarApp;
import com.android.zoomcar.api.BookingDetail;
import com.android.zoomcar.api.CarDetailsModel;
import com.squareup.picasso.Picasso;

import de.greenrobot.event.EventBus;
import io.realm.Realm;

/**
 * A simple {@link Fragment} subclass.
 */
public class CarDetailFragment extends Fragment implements View.OnClickListener {


    private EditText dateEt;
    private Button bookNow;
    private CarDetailsModel.Car data;

    public CarDetailFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_car_detail, container, false);
        data = EventBus.getDefault().removeStickyEvent(CarDetailsModel.Car.class);
        ((TextView)rootView.findViewById(R.id.carName)).setText(data.getName());
        ((TextView) rootView.findViewById(R.id.seater)).setText("Seater: " + data.getSeater());
        ((TextView) rootView.findViewById(R.id.type)).setText("Type: "+data.getType());
        ((TextView) rootView.findViewById(R.id.rating)).setText("Rating: "+data.getRating());
        ((TextView) rootView.findViewById(R.id.rate)).setText("Hourly Rate: " + data.getHourlyRate());
        String ac = data.getAc().equals("1") ? "Yes" : "No";
        ((TextView) rootView.findViewById(R.id.ac)).setText("AC: "+ac);

        Picasso.with(getActivity()).load(data.getImage())
                .into((ImageView) rootView.findViewById(R.id.carImage));

        dateEt = (EditText) rootView.findViewById(R.id.dateEt);
        bookNow = (Button) rootView.findViewById(R.id.bookCar);

        dateEt.setOnClickListener(this);
        bookNow.setOnClickListener(this);
        return rootView;
    }


    @Override
    public void onClick(View view) {
        if(R.id.dateEt == view.getId())
            Util.datePickerDialog(getActivity(),dateEt,0);
        else if(R.id.bookCar == view.getId())
        {
            if(!TextUtils.isEmpty(dateEt.getText().toString()))
                savaData(dateEt.getText().toString());
            else
                dateEt.setError("Booking date is required");
        }
    }

    private void savaData(final String date)
    {
        ZoomCarApp.getRealmDb().executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                BookingDetail bookingDetail = realm.createObject(BookingDetail.class);
                bookingDetail.setId(System.currentTimeMillis());
                bookingDetail.setDateTime(date);
                bookingDetail.setCarImage(data.getImage());
                bookingDetail.setCarName(data.getName());

                Toast.makeText(getActivity(),"Car booked!",Toast.LENGTH_LONG).show();
            }
        });
    }
}
