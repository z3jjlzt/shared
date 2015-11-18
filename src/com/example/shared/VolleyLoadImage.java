package com.example.shared;

import java.util.ArrayList;

import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.ImageLoader.ImageCache;
import com.android.volley.toolbox.ImageLoader.ImageContainer;
import com.android.volley.toolbox.ImageLoader.ImageListener;
import com.android.volley.toolbox.Volley;
import com.example.shared.MyListview.onItemDeletedListener;
import com.z3jjlzt.utils.MyBaseAdapter;
import com.z3jjlzt.utils.ViewHolder;

import android.app.Activity;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v4.util.LruCache;
import android.widget.ImageView;

/**
 * @author z3jjlzt
 * 图片的ITEM要设置宽高 不然不显示。。
 */
public class VolleyLoadImage extends Activity {

	/**
	 * 列表
	 */
	private MyListview lv;
	/**
	 * 数据源
	 */
	ArrayList<String> list;
	/**
	 * 适配器
	 */
	private MyBaseAdapter<String> adapter;
	/**
	 * 图片缓存
	 */
	private BitmapCache bitmapCache;
	/**
	 * 加载图片类
	 */
	private ImageLoader mImageLoader;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.volleyloadimage);
		initView();
		setData();
		bitmapCache = new BitmapCache();
		mImageLoader = new ImageLoader(Volley.newRequestQueue(this), bitmapCache);
		adapter = new MyBaseAdapter<String>(this, list, R.layout.volleyimageview) {

			@Override
			public void convert(ViewHolder vh, String t, int position) {
				vh.setText(R.id.textview, "image" + position);
				ImageView imageView = vh.getView(R.id.imageview);
				imageView.setTag(t);// 设置图片标识 辅助处理图片错位
				ImageListener listener = getImageListener(imageView, R.drawable.ic_launcher, R.drawable.ic_launcher, t);
				mImageLoader.get(t, listener);// 设置监听 图片错位在这处理
			}
		};
		lv.setAdapter(adapter);
		 lv.setOnItemDeleteListener(new onItemDeletedListener() {
		
		 @Override
		 public void onItemDelete(int pos) {
		 list.remove(pos);
		 adapter.notifyDataSetChanged();
		 }
		 });

	}

	private void initView() {
		lv = (MyListview) findViewById(R.id.lv);
	}

	private void setData() {
		list = new ArrayList<String>();

		for (int i = 0; i < 4; i++) {
			list.add(
					"http://image.baidu.com/search/down?tn=download&word=download&ie=utf8&fr=detail&url=http%3A%2F%2Fimg2.kwcdn.kuwo.cn%2Fstar%2FKuwoArtPic%2F2013%2F22%2F1396932137246_w.jpg&thumburl=http%3A%2F%2Fimg1.imgtn.bdimg.com%2Fit%2Fu%3D4064024103%2C65869690%26fm%3D21%26gp%3D0.jpg");
			list.add(
					"http://image.baidu.com/search/down?tn=download&ipn=dwnl&word=download&ie=utf8&fr=result&url=http%3A%2F%2Fwww.sinaimg.cn%2Fqc%2Fphoto_auto%2Fphoto%2F50%2F11%2F1485011%2F1485011_src.jpg");
			list.add(
					"http://image.baidu.com/search/down?tn=download&ipn=dwnl&word=download&ie=utf8&fr=result&url=http%3A%2F%2Fwww.sinaimg.cn%2Fqc%2Fphoto1%2F2010audiA4allroadquattro%2FU2798P33T148D336506F2100DT20090305133808.jpg");
			list.add(
					"http://image.baidu.com/search/down?tn=download&word=download&ie=utf8&fr=detail&url=http%3A%2F%2Fb.hiphotos.baidu.com%2Fimage%2Fpic%2Fitem%2F5d6034a85edf8db1fa3020bb0d23dd54574e74d6.jpg&thumburl=http%3A%2F%2Fb.hiphotos.baidu.com%2Fimage%2Fh%253D200%2Fsign%3D211b3aacfff2b211fb2e824efa816511%2F5d6034a85edf8db1fa3020bb0d23dd54574e74d6.jpg");
			list.add(
					"http://image.baidu.com/search/down?tn=download&word=download&ie=utf8&fr=detail&url=http%3A%2F%2Fd.hiphotos.baidu.com%2Fimage%2Fpic%2Fitem%2F5fdf8db1cb1349543ab7b4bb524e9258d0094a47.jpg&thumburl=http%3A%2F%2Fd.hiphotos.baidu.com%2Fimage%2Fh%253D200%2Fsign%3Dc866dbfe9713b07ea2bd57083cd69113%2F5fdf8db1cb1349543ab7b4bb524e9258d0094a47.jpg");
			list.add(
					"http://image.baidu.com/search/down?tn=download&word=download&ie=utf8&fr=detail&url=http%3A%2F%2Fe.hiphotos.baidu.com%2Fimage%2Fpic%2Fitem%2F72f082025aafa40fcfb29202af64034f79f019fc.jpg&thumburl=http%3A%2F%2Fe.hiphotos.baidu.com%2Fimage%2Fh%253D200%2Fsign%3Dc57dfee5344e251ffdf7e3f89787c9c2%2F72f082025aafa40fcfb29202af64034f79f019fc.jpg");
		}
	}

	/**
	 * 避免图片错位
	 * 
	 * @param view
	 *            当前选择的Imageview
	 * @param defaultImageResId
	 * @param errorImageResId
	 * @param url
	 * @return
	 */
	public static ImageListener getImageListener(final ImageView view, final int defaultImageResId,
			final int errorImageResId, final String url) {
		return new ImageListener() {
			@Override
			public void onErrorResponse(VolleyError error) {
				if (errorImageResId != 0) {
					view.setImageResource(errorImageResId);
				}
			}

			@Override
			public void onResponse(ImageContainer response, boolean isImmediate) {
				if (response.getBitmap() != null) {
					// 在这里可以设置，如果想得到圆角图片的画，可以对bitmap进行加工，可以给imageview加一个
					// 额外的参数
					String urlTag = (String) view.getTag();
					if (urlTag != null && urlTag.trim().equals(url)) {
						view.setImageBitmap(response.getBitmap());
					}
				} else if (defaultImageResId != 0) {
					view.setImageResource(defaultImageResId);
				}
			}
		};
	}

	class BitmapCache implements ImageCache {
		/**
		 * 缓存 最大设为10m
		 */
		private LruCache<String, Bitmap> lruCache;

		public BitmapCache() {
			lruCache = new LruCache<String, Bitmap>(10 * 1024 * 1024) {
				@Override
				protected int sizeOf(String key, Bitmap value) {
					return value.getRowBytes() * value.getHeight();
				}
			};
		}

		@Override
		public Bitmap getBitmap(String url) {
			return lruCache.get(url);
		}

		@Override
		public void putBitmap(String url, Bitmap bitmap) {
			lruCache.put(url, bitmap);
		}

	}

}
