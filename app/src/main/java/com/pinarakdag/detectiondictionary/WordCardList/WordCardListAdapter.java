package com.pinarakdag.detectiondictionary.WordCardList;

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


public class WordCardListAdapter extends BaseAdapter {

    private Context context;
    private  int layout;
    private ArrayList<Word> wordsList;

    public WordCardListAdapter(Context context, int layout, ArrayList<Word> wordsList) {
        this.context = context;
        this.layout = layout;
        this.wordsList = wordsList;
    }

    @Override
    public int getCount() {
        return wordsList.size();
    }

    @Override
    public Object getItem(int position) {
        return wordsList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    private class ViewHolder{
        ImageView imageView;
        TextView txtEn, txtTr;
    }

    @Override
    public View getView(int position, View view, ViewGroup viewGroup) {

        View row = view;
        ViewHolder holder = new ViewHolder();

        if(row == null){
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            row = inflater.inflate(layout, null);

            holder.txtEn = (TextView) row.findViewById(R.id.txtEn);
            holder.txtTr = (TextView) row.findViewById(R.id.txtTr);
            holder.imageView = (ImageView) row.findViewById(R.id.imgWord);
            row.setTag(holder);
        }
        else {
            holder = (ViewHolder) row.getTag();
        }

        Word word = wordsList.get(position);

        holder.txtEn.setText(word.getEn());
        holder.txtTr.setText(word.getTr());

        byte[] wordImage = word.getImage();
        Bitmap bitmap = BitmapFactory.decodeByteArray(wordImage, 0, wordImage.length);
        holder.imageView.setImageBitmap(bitmap);

        return row;
    }
}
