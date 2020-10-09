package com.belicode.rsmsonline.pasien;

import android.content.Context;
import android.graphics.Bitmap;
import android.util.LruCache;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;
import com.belicode.rsmsonline.R;

import java.util.List;

public class PasienAdapter extends BaseAdapter {
    private Context context;
    private List<Pasien> pList;
    private LayoutInflater inflater = null;
    private LruCache<Integer,Bitmap> imageCache;
    private RequestQueue queue;
    public PasienAdapter(Context context, List<Pasien> list) {
        this.context = context;
        this.pList = list;
        inflater = LayoutInflater.from(context);
        final int maxMemory = (int) (Runtime.getRuntime().maxMemory() / 1024);
        final int cacheSize = maxMemory / 8;
        imageCache = new LruCache<>(cacheSize);
        queue = Volley.newRequestQueue(context);
    }

    public class ViewHolder {

        TextView _judul;
        TextView _detail;
        TextView _tgl;
        // ImageView _P_Image;

    }

    @Override
    public int getCount() {
        return pList.size();
    }

    @Override
    public Object getItem(int position) {

        return pList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup viewGroup) {


        final Pasien p = pList.get(position);
        final ViewHolder holder;
        if(convertView == null) {

            convertView = inflater.inflate(R.layout.template_list,null);
            holder = new ViewHolder();

            holder._judul = (TextView) convertView.findViewById(R.id.tv1);
            holder._detail = (TextView) convertView.findViewById(R.id.tv2);



            convertView.setTag(holder);
        }
        else {

            holder = (ViewHolder) convertView.getTag();
        }

        holder._judul.setText(""+p.getId_pasien().toString());
        holder._detail.setText("Nama : "+p.getNama_pasien().toString());

       return convertView;
    }
}
