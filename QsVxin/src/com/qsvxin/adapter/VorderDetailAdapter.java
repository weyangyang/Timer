package com.qsvxin.adapter;

import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.qsvxin.R;
import com.qsvxin.bean.BuyGoodsBean;

public class VorderDetailAdapter extends BaseAdapter {
	
	private Context context;
	private List<BuyGoodsBean>list;
	
	public VorderDetailAdapter(Context context, List<BuyGoodsBean>list) {
		this.context = context;
		this.list = list;
	}

	@Override
	public int getCount() {
		return list.size();
	}

	@Override
	public Object getItem(int arg0) {
		return list.get(arg0);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHolder holder = null;
		if (convertView == null) {
			convertView = LayoutInflater.from(context).inflate(
					R.layout.scrolllistview_item, null);
			holder = new ViewHolder();
			holder.tvComid = (TextView) convertView.findViewById(R.id.tvComid);
			holder.tvNorms = (TextView) convertView.findViewById(R.id.tvNorms);
			holder.tvName = (TextView) convertView
					.findViewById(R.id.tvName);
			holder.tvValue = (TextView) convertView.findViewById(R.id.tvValue);
			holder.tvCount = (TextView) convertView.findViewById(R.id.tvCount);
			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}
		holder.tvName.setText(list.get(position).getStrGoodsNormsName());
		holder.tvComid.setText(list.get(position).getStrGoodsID());
		holder.tvNorms.setText(list.get(position).getStrGoodsNorms());
		holder.tvCount.setText(list.get(position).getStrGoodsCount());
		holder.tvValue.setText(list.get(position).getStrGoodsPrice());
		
		return convertView;
	}
	private class ViewHolder {
		private TextView tvComid;
		private TextView tvNorms;
		private TextView tvName;
		private TextView tvValue;
		private TextView tvCount;
	}
}
