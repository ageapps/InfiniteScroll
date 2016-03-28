package es.age.apps.infinitescrolling;


import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.ListView;
import android.widget.RelativeLayout;

import java.util.ArrayList;

import es.age.apps.infinitescrolling.adapter.ListViewAdapter;
import es.age.apps.infinitescrolling.model.City;
import es.age.apps.infinitescrolling.model.CityGetter;



/**
 * Created by Adrián García Espinosa on 25/3/16.
 */
public class ListViewFragment extends Fragment {

    private View view;
    private ListView listView;
    private RelativeLayout progressList;
    private ArrayList<City> cityArrayList;
    private ListViewAdapter listViewAdapter;
    private listViewUpdater mListTask;

    public ListViewFragment() {
        // Required empty public constructor
    }

    public static ListViewFragment newInstance() {
        ListViewFragment fragment = new ListViewFragment();
        return fragment;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_listview, container, false);

        listView = (ListView) view.findViewById(R.id.listView);
        cityArrayList = CityGetter.getCities(view.getContext(),1);
        listViewAdapter = new ListViewAdapter(view.getContext(), cityArrayList);
        listView.setAdapter(listViewAdapter);

        progressList = (RelativeLayout) view.findViewById(R.id.loading_listview);

        implementScrollListener();

        return view;

    }

    // Implement scroll listener
    private void implementScrollListener() {
        listView.setOnScrollListener(new AbsListView.OnScrollListener() {
            boolean userScrolled = false;

            @Override
            public void onScrollStateChanged(AbsListView arg0, int scrollState) {
                // If scroll state is touch scroll then set userScrolled
                // true
                if (scrollState == AbsListView.OnScrollListener.SCROLL_STATE_TOUCH_SCROLL) {
                    userScrolled = true;


                }
                Log.d("LISTVIEW", "onScrollStateChanged: " + userScrolled);
            }

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem,
                                 int visibleItemCount, int totalItemCount) {
                // Now check if userScrolled is true and also check if
                // the item is end then update list view and set
                // userScrolled to false
                Log.d("LISTVIEW", "onScroll: " + userScrolled + " " + firstVisibleItem + " + " + visibleItemCount + " = " + totalItemCount);
                if (firstVisibleItem + visibleItemCount == totalItemCount) {
                    Log.d("LISTVIEW", "updateRecyclerView: " + userScrolled);
                    userScrolled = false;
                    updateListView();
                }
            }
        });
    }

    // Method for repopulating ListView
    private void updateListView() {
        mListTask = new listViewUpdater();
        mListTask.execute();
    }

    private void showProgress(boolean show) {
        if (show) {
            progressList.setVisibility(View.VISIBLE);
        } else {
            progressList.setVisibility(View.GONE);
        }
    }

    /**
     * Represents an asynchronous task used to get Information
     */
    private class listViewUpdater extends AsyncTask<Void, Void, Void> {

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
                cityArrayList.add(cities.get(i));
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void v) {
            listViewAdapter.notifyDataSetChanged();
            mListTask = null;
            showProgress(false);
        }

        @Override
        protected void onCancelled() {
            mListTask = null;
            showProgress(false);
        }
    }




}
