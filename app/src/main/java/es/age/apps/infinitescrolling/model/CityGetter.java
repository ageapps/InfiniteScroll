package es.age.apps.infinitescrolling.model;

import android.content.Context;

import java.util.ArrayList;
import java.util.Random;

import es.age.apps.infinitescrolling.R;

/**
 * Created by Adrián García Espinosa on 25/3/16.
 */
public class CityGetter {

    public static int CITY_NUMBER = 15;

    public static ArrayList<City> getCities(Context ctx, int start) {

        String[] names = ctx.getResources().getStringArray(R.array.title);
        String[] locations = ctx.getResources().getStringArray(R.array.location);
        String[] description = ctx.getResources().getStringArray(R.array.description);
        int[] images = {R.drawable.madrid, R.drawable.barcelona, R.drawable.valencia, R.drawable.sevilla,
                R.drawable.zaragoza, R.drawable.malaga, R.drawable.murcia, R.drawable.bilbao

        };
        ArrayList<City> cities = new ArrayList<City>();
        Random random = new Random(start);

        for (int i = 0; i < CITY_NUMBER; i++) {
            int j = random.nextInt(names.length);
            cities.add(new City(names[j], locations[j], description[j], images[j]));
        }
        return cities;

    }
}
