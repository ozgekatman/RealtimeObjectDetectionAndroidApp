package com.pinarakdag.detectiondictionary.WordList;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.pinarakdag.detectiondictionary.R;

import java.util.ArrayList;

public class WordListAdapter extends BaseAdapter {
    private Context context;
    private int layout;
    private ArrayList<Model> wordList;

    public WordListAdapter(Context context, int layout, ArrayList<Model> wordList) {
        this.context = context;
        this.layout = layout;
        this.wordList = wordList;
    }

    @Override
    public int getCount() {
        return wordList.size();
    }

    @Override
    public Object getItem(int position) {
        return wordList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }
    private class ViewHolder{

        TextView txtEng, txtTr;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View row = convertView;
        ViewHolder holder = new ViewHolder();

        if(row==null){
            LayoutInflater inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            row = inflater.inflate(layout,null);
            holder.txtEng = row.findViewById(R.id.txtEng);
            holder.txtTr = row.findViewById(R.id.txtTr);
            row.setTag(holder);
        }
        else{
            holder = (ViewHolder)row.getTag();
        }
        Model model = wordList.get(position);
        holder.txtEng.setText(model.getEn());
        holder.txtTr.setText(model.getTr());


        return row;
    }
}
