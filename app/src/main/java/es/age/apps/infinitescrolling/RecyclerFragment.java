package es.age.apps.infinitescrolling;


import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.RelativeLayout;

import java.util.ArrayList;

import es.age.apps.infinitescrolling.adapter.RecyclerViewAdapter;
import es.age.apps.infinitescrolling.model.City;
import es.age.apps.infinitescrolling.model.CityGetter;



/**
 * Created by Adrián García Espinosa on 25/3/16.
 */
public class RecyclerFragment extends Fragment {

    private View view;
    private LinearLayoutManager layoutManager;
    private RecyclerView recicler;
    private RecyclerViewAdapter recyclerViewAdapter;
    private RelativeLayout progressRecycler;
    private recyclerViewUpdater mRecyclerTask = null;
    private ArrayList<City> cityArrayRecycler;


    // Variables for scroll listener
    private boolean userScrolled = true;
    int pastVisiblesItems, visibleItemCount, totalItemCount;

    public static RecyclerFragment newInstance() {
        RecyclerFragment fragment = new RecyclerFragment();
        return fragment;
    }

    public RecyclerFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_recycler, container, false);

        progressRecycler = (RelativeLayout) view.findViewById(R.id.loading_recyclerview);
        layoutManager = new LinearLayoutManager(getActivity());
        recicler = (RecyclerView) view.findViewById(R.id.recicler);
        recicler.setLayoutManager(layoutManager);

        cityArrayRecycler = CityGetter.getCities(view.getContext(), 1);
        recyclerViewAdapter = new RecyclerViewAdapter(view.getContext(), cityArrayRecycler);
        recicler.setAdapter(recyclerViewAdapter);
        implementScrollListener();
        return view;
    }


    // Implement scroll listener
    private void implementScrollListener() {
        recicler.addOnScrollListener(new RecyclerView.OnScrollListener() {

            @Override
            public void onScrollStateChanged(RecyclerView recyclerView,
                                             int newState) {

                super.onScrollStateChanged(recyclerView, newState);

                // If scroll state is touch scroll then set userScrolled
                // true
                if (newState == AbsListView.OnScrollListener.SCROLL_STATE_TOUCH_SCROLL) {
                    userScrolled = true;

                }
                Log.d("RECYCLER", "onScrollStateChanged: " + userScrolled);
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx,
                                   int dy) {
                Log.d("RECYCLER", "onScroll: ");

                super.onScrolled(recyclerView, dx, dy);
                // Here get the child count, item count and visibleitems
                // from layout manager

                visibleItemCount = layoutManager.getChildCount();
                totalItemCount = layoutManager.getItemCount();
                pastVisiblesItems = layoutManager.findFirstVisibleItemPosition();

                Log.d("RECYCLER", "onScroll: " + userScrolled + " " + pastVisiblesItems + " + " + visibleItemCount + " = " + totalItemCount);
                // Now check if userScrolled is true and also check if
                // the item is end then update recycler view and set
                // userScrolled to false
                if (userScrolled && (visibleItemCount + pastVisiblesItems) == totalItemCount) {
                    Log.d("RECYCLER", "updateRecyclerView: " + userScrolled);
                    userScrolled = false;
                    updateRecyclerView();

                }

            }

        });

    }

    // Method for repopulating recycler view
    private void updateRecyclerView() {
        mRecyclerTask = new recyclerViewUpdater();
        mRecyclerTask.execute();
    }

    private void showProgress(boolean show) {
        if (show) {
            progressRecycler.setVisibility(View.VISIBLE);
        } else {
            progressRecycler.setVisibility(View.GONE);
        }
    }


    /**
     * Represents an asynchronous task used to get Information
     */
    private class recyclerViewUpdater extends AsyncTask<Void, Void, Void> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            showProgress(true);
        }

        @Override
        protected Void doInBackground(Void... params) {

            try {
                // Simulate network access.
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                return null;
            }

            ArrayList<City> cities = CityGetter.getCities(getContext(), 2);

            for (int i = 0; i < cities.size(); i++) {
                cityArrayRecycler.add(cities.get(i));
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void v) {
            recyclerViewAdapter.notifyDataSetChanged();
            mRecyclerTask = null;
            showProgress(false);
        }

        @Override
        protected void onCancelled() {
            mRecyclerTask = null;
            showProgress(false);
        }
    }


}
