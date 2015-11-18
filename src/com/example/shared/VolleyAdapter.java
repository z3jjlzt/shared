package com.example.shared;

import java.util.ArrayList;

import com.android.volley.RequestQueue;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.ImageLoader.ImageCache;
import com.android.volley.toolbox.ImageLoader.ImageContainer;
import com.android.volley.toolbox.ImageLoader.ImageListener;
import com.android.volley.toolbox.Volley;

import android.content.Context;
import android.graphics.Bitmap;
import android.support.v4.util.LruCache;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * @author z3jjlzt
 * never used
 *
 */
public class VolleyAdapter extends BaseAdapter {
	private Context context;
	private ArrayList<String> url;
	private LayoutInflater inf;
	private ImageLoader mImageLoader;
	private RequestQueue mQueue;

	public VolleyAdapter(Context context, ArrayList<String> url) {
		this.context = context;
		this.url = url;
		inf = LayoutInflater.from(context);
		 mQueue = Volley.newRequestQueue(context);
		mImageLoader = new ImageLoader(mQueue, new BitmapCache());
	}

	class BitmapCache implements ImageCache {
		private LruCache<String, Bitmap> cache;

		public BitmapCache() {
			cache = new LruCache<String, Bitmap>(10 * 1024 * 1024) {
				@Override
				protected int sizeOf(String key, Bitmap value) {
					// TODO Auto-generated method stub
					return value.getRowBytes() * value.getHeight();
				}
			};
		}

		@Override
		public Bitmap getBitmap(String url) {
			return cache.get(url);
		}

		@Override
		public void putBitmap(String url, Bitmap bitmap) {
			cache.put(url, bitmap);

		}

	}

	@Override
	public int getCount() {
		return url.size();
	}

	@Override
	public Object getItem(int position) {
		return url.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		viewholder vh = null;
		if (convertView == null) {
			convertView = inf.inflate(R.layout.volleyimageview, null);
			vh = new viewholder();
			vh.textView = (TextView) convertView.findViewById(R.id.textview);
			vh.ImageView = (ImageView) convertView.findViewById(R.id.imageview);
			
			convertView.setTag(vh);
		} else {
			vh = (viewholder) convertView.getTag();
		}
		
		String url = "";
		
		url = this.url.get(position % this.url.size());
		vh.ImageView.setTag(url);
		Log.e("sb", url);
		vh.textView.setText(position + "");
		ImageListener listener = getImageListener(vh.ImageView, R.drawable.ic_launcher, R.drawable.ic_launcher, url);
	mImageLoader.get(url, listener);
		return convertView;
	}

	class viewholder {
		private TextView textView;
		private ImageView ImageView;
	}
	  /**
     * 使用此方法能够解决图片错乱问题
     * @param view
     * @param defaultImageResId
     * @param errorImageResId
     * @param url
     * @return
     */
    public static ImageListener getImageListener(
            final ImageView view,
            final int defaultImageResId,
            final int errorImageResId,
            final String url) {
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
                    //在这里可以设置，如果想得到圆角图片的画，可以对bitmap进行加工，可以给imageview加一个
                    //额外的参数
                    String urlTag = (String) view.getTag();
                    if(urlTag!=null && urlTag.trim().equals(url)){
                        view.setImageBitmap(response.getBitmap());
                    }
                } else if (defaultImageResId != 0) {
                    view.setImageResource(defaultImageResId);
                }
            }
        };
    }

}
