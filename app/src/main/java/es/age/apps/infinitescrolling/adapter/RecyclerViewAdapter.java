package es.age.apps.infinitescrolling.adapter;

import java.util.ArrayList;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;

import es.age.apps.infinitescrolling.DetailActivity;
import es.age.apps.infinitescrolling.R;
import es.age.apps.infinitescrolling.model.City;

/**
 * Created by Adrián García Espinosa on 25/3/16.
 */
public class RecyclerViewAdapter extends
        RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder> {

    private ArrayList<City> arrayList;
    private Context context;

    public class ViewHolder extends RecyclerView.ViewHolder {
        // View holder for list recycler view as we used in listview
        public TextView title;
        public TextView location;
        public ImageView image;
        public View mView;


        public ViewHolder(View view) {
            super(view);

            // SetUp views
            mView = view;
            title = (TextView) view.findViewById(R.id.list_title);
            location = (TextView) view.findViewById(R.id.list_location);
            image = (ImageView) view
                    .findViewById(R.id.list_imageview);

        }

    }

    public RecyclerViewAdapter(Context context,
                               ArrayList<City> arrayList) {
        this.context = context;
        this.arrayList = arrayList;

    }

    @Override
    public int getItemCount() {
        return (null != arrayList ? arrayList.size() : 0);

    }


    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {

        View v = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.item_custom_list, viewGroup, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {

        final City city = arrayList.get(position);

        // setting data over views
        holder.title.setText(city.getTitle());
        holder.location.setText(city.getLocation());
        Glide.with(context)
                .load(city.getImage())
                .centerCrop()
                .into(holder.image);


        holder.mView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(v.getContext(), DetailActivity.class);
                i.putExtra(DetailActivity.EXTRA_NAME, city.getTitle());
                i.putExtra(DetailActivity.EXTRA_LOCATION, city.getLocation());
                i.putExtra(DetailActivity.EXTRA_DESCRIPTION, city.getDescription());
                i.putExtra(DetailActivity.EXTRA_IMAGE, city.getImage());
                v.getContext().startActivity(i);
            }
        });

    }


}
