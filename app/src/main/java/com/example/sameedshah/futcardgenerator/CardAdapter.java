package com.example.sameedshah.futcardgenerator;

import android.content.Context;
import android.content.res.Resources;
import android.media.Image;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

public class CardAdapter extends BaseAdapter {

    Context c;
    ArrayList<CardModel> arrayList;

     CardAdapter(Context c){
        this.c = c;
        arrayList = new ArrayList<>();
        Resources res = c.getResources();
        String[] names = res.getStringArray(R.array.cards_name);
//         int[] images = {R.drawable.gold_thumb,R.drawable.silver_thumb,R.drawable.bronze_thumb,
//                 R.drawable.totw_gold_thumb,R.drawable.tots_silver_thumb,R.drawable.totw_bronze_thumb,
//                 R.drawable.champions_thumb,R.drawable.icon_card_thumb,R.drawable.otw_thumb
//          };



         for(int i = 0; i<names.length; i++){
            arrayList.add(new CardModel(names[i]));
        }
    }

    @Override
    public int getCount() {
        return arrayList.size();
    }

    @Override
    public Object getItem(int i) {
        return arrayList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {

        LayoutInflater inflater = (LayoutInflater) c.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        View view1 = inflater.inflate(R.layout.custom_list_view,viewGroup,false);

        TextView textCard = view1.findViewById(R.id.textCard);
        //ImageView imageView = view1.findViewById(R.id.card_image);



        CardModel temp_obj = arrayList.get(i);
        textCard.setText(temp_obj.name);




        return view1;
    }
}
