package com.example.shared.utils;

import java.util.ArrayList;

import com.example.shared.R;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.RecyclerView.ViewHolder;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * @author z3jjlzt 2015��12��4��
 * @param <T>
 *            VH����
 * @param <K>
 *            LIST����
 */
public abstract class LztRecycleViewAdapter<T extends ViewHolder, K> extends RecyclerView.Adapter<ViewHolder> {
	private Context context;
	private ArrayList<K> list;

	public LztRecycleViewAdapter(Context context, ArrayList<K> list) {
		this.context = context;
		this.list = list;
	}

	@Override
	public int getItemCount() {
		return list.size();
	}

	@Override
	public void onBindViewHolder(ViewHolder arg0, int arg1) {
		BindVh((T) arg0, arg1);
	}

	@Override
	public T onCreateViewHolder(ViewGroup arg0, int arg1) {
		View v = LayoutInflater.from(context).inflate(R.layout.item2, null, false);
		return createVh(v);
	}

	/**
	 * ��onCreateViewHolder�е���
	 * 
	 * @param v
	 *            viewholder��������ͼ
	 * @return
	 */
	public abstract T createVh(View v);

	/**
	 * ��onBindViewHolder�е���
	 * 
	 * @param viewholder
	 *            VH
	 * @param position
	 *            λ��
	 */
	public abstract void BindVh(T viewholder, int position);

}
