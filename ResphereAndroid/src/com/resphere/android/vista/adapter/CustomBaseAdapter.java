package com.resphere.android.vista.adapter;

import java.util.List;

import com.resphere.android.vista.R;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class CustomBaseAdapter extends BaseAdapter {

	Context context;
    List<FilaItem> filaItems;
 
    public CustomBaseAdapter(Context context, List<FilaItem> items) {
        this.context = context;
        this.filaItems = items;
    }
    
    /*private view holder class*/
    private class ViewHolder {
        ImageView imageView;
        TextView txtTitle;
        TextView txtDesc;
    }
    
	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public Object getItem(int arg0) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public long getItemId(int arg0) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup arg2) {
		// TODO Auto-generated method stub
		ViewHolder holder = null;
		LayoutInflater mInflater = (LayoutInflater)context.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
		if (convertView == null) {
            convertView = mInflater.inflate(R.layout.inicio, null);
            holder = new ViewHolder();
            holder.txtDesc = (TextView) convertView.findViewById(R.id.desc);
            holder.txtTitle = (TextView) convertView.findViewById(R.id.title);
            holder.imageView = (ImageView) convertView.findViewById(R.id.icon);
            convertView.setTag(holder);
        }
        else {
            holder = (ViewHolder) convertView.getTag();
        }
		
		FilaItem filaItem = (FilaItem) getItem(position);
		 
        holder.txtDesc.setText(filaItem.getDesc());
        holder.txtTitle.setText(filaItem.getTitle());
        holder.imageView.setImageResource(filaItem.getImageId());
 
        return convertView;
	}

}
