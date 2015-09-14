package com.android.zoomcar.fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import com.android.zoomcar.BookingDetailAdapter;
import com.android.zoomcar.R;
import com.android.zoomcar.ZoomCarApp;
import com.android.zoomcar.api.BookingDetail;

import io.realm.RealmQuery;
import io.realm.RealmResults;

/**
 * A simple {@link Fragment} subclass.
 */
public class BookingDetailFragment extends Fragment {


    private BookingDetailAdapter mAdapter;
    private ListView mListView;
    private TextView mEmptyText;
    public BookingDetailFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView =  inflater.inflate(R.layout.fragment_booking_detail, container, false);
        mListView = (ListView) rootView.findViewById(R.id.list);
        mEmptyText = (TextView) rootView.findViewById(R.id.text);

        // Build the query looking at all users:
        RealmQuery<BookingDetail> query = ZoomCarApp.getRealmDb().where(BookingDetail.class);
        // Execute the query:
        RealmResults<BookingDetail> result = query.findAll();
        mAdapter = new BookingDetailAdapter(getActivity(), result);
        mListView.setAdapter(mAdapter);
        if(result != null && result.size() > 0)
            mEmptyText.setVisibility(View.GONE);
        else
            mEmptyText.setVisibility(View.VISIBLE);
        return rootView;
    }


}
