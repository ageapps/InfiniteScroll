package es.age.apps.infinitescrolling.adapter;

import java.util.ArrayList;


import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import es.age.apps.infinitescrolling.DetailActivity;
import es.age.apps.infinitescrolling.R;
import es.age.apps.infinitescrolling.model.City;


/**
 * Created by Adrián García Espinosa on 25/3/16.
 */
public class ListViewAdapter extends BaseAdapter {
    private ArrayList<City> arrayList;
    private Context context;

    public ListViewAdapter(Context context, ArrayList<City> arrayList) {
        this.context = context;
        this.arrayList = arrayList;
    }

    // View Holder
    private class ViewHolder {
        public TextView title;
        public TextView location;
        public ImageView image;
    }

    @Override
    public int getCount() {

        return (null != arrayList ? arrayList.size() : 0);
    }

    @Override
    public Object getItem(int position) {

        return arrayList.get(position);
    }

    @Override
    public long getItemId(int position) {

        return position;
    }

    @Override
    public View getView(int position, View view, ViewGroup parent) {
        ViewHolder holder = null;

        // Inflater for custom layout
        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        if (view == null) {
            view = inflater.inflate(R.layout.item_custom_list, parent, false);

            holder = new ViewHolder();

            holder.title = (TextView) view.findViewById(R.id.list_title);
            holder.location = (TextView) view
                    .findViewById(R.id.list_location);
            holder.image = (ImageView) view
                    .findViewById(R.id.list_imageview);

            view.setTag(holder);

        } else {
            holder = (ViewHolder) view.getTag();
        }

        final City city = arrayList.get(position);

        // Set Up views
        holder.title.setText(city.getTitle());
        holder.location.setText(city.getLocation());
        Glide.with(view.getContext())
                .load(city.getImage())
                .centerCrop()
                .into(holder.image);

        view.setOnClickListener(new View.OnClickListener() {
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

        return view;
    }

}
