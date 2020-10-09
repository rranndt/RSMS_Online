package com.belicode.rsmsonline.antrian;

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

public class AntrianAdapter extends BaseAdapter {
    private Context context;
    private List<Antrian> pList;
    private LayoutInflater inflater = null;
    private LruCache<Integer,Bitmap> imageCache;
    private RequestQueue queue;
    public AntrianAdapter(Context context, List<Antrian> list) {
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


        final Antrian p = pList.get(position);
        final AntrianAdapter.ViewHolder holder;
        if(convertView == null) {

            convertView = inflater.inflate(R.layout.tmp_list1,null);
            holder = new AntrianAdapter.ViewHolder();

            holder._judul = (TextView) convertView.findViewById(R.id.tv1);
            holder._detail = (TextView) convertView.findViewById(R.id.tv2);
            holder._tgl = (TextView) convertView.findViewById(R.id.tv3);



            convertView.setTag(holder);
        }
        else {

            holder = (AntrianAdapter.ViewHolder) convertView.getTag();
        }

        holder._judul.setText(""+p.getNama_pasien().toString());
        holder._detail.setText("Nomor Antrian :"+p.getNomor_antrian().toString());
        holder._tgl.setText("Perkiraan :"+p.getDi_layani().toString());

        return convertView;
    }
}
