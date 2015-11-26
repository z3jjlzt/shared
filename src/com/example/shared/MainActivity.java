package com.example.shared;

import java.util.ArrayList;

import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.ImageLoader.ImageCache;
import com.android.volley.toolbox.ImageLoader.ImageContainer;
import com.android.volley.toolbox.ImageLoader.ImageListener;
import com.android.volley.toolbox.Volley;
import com.example.shared.MyListview.OnReflashListener;
import com.example.shared.MyListview.onItemDeletedListener;
import com.z3jjlzt.utils.MyBaseAdapter;
import com.z3jjlzt.utils.ViewHolder;

import android.app.Activity;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.util.LruCache;
import android.util.Log;
import android.widget.ImageView;

public class MainActivity extends Activity {

	private MyListview lv;
	ArrayList<String> list;
	private MyBaseAdapter adapter;
	private BitmapCache bitmapCache;
	private ImageLoader mImageLoader;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		lv =(MyListview) findViewById(R.id.lv);
		list = new ArrayList<String>();
		String[] images = new String[] {
				"http://img.my.csdn.net/uploads/201407/26/1406383299_1976.jpg",
				"http://img.my.csdn.net/uploads/201407/26/1406383291_6518.jpg",
				"http://img.my.csdn.net/uploads/201407/26/1406383291_8239.jpg",
				"http://img.my.csdn.net/uploads/201407/26/1406383290_9329.jpg",
				"http://img.my.csdn.net/uploads/201407/26/1406383290_1042.jpg",
				"http://img.my.csdn.net/uploads/201407/26/1406383275_3977.jpg",
				"http://img.my.csdn.net/uploads/201407/26/1406383265_8550.jpg",
				"http://img.my.csdn.net/uploads/201407/26/1406383264_3954.jpg",
				"http://img.my.csdn.net/uploads/201407/26/1406383264_4787.jpg",
				"http://img.my.csdn.net/uploads/201407/26/1406383264_8243.jpg",
				"http://img.my.csdn.net/uploads/201407/26/1406383248_3693.jpg",
				"http://img.my.csdn.net/uploads/201407/26/1406383243_5120.jpg",
				"http://img.my.csdn.net/uploads/201407/26/1406383242_3127.jpg",
				"http://img.my.csdn.net/uploads/201407/26/1406383242_9576.jpg",
				"http://img.my.csdn.net/uploads/201407/26/1406383242_1721.jpg",
				"http://img.my.csdn.net/uploads/201407/26/1406383219_5806.jpg",
				"http://img.my.csdn.net/uploads/201407/26/1406383214_7794.jpg",
				"http://img.my.csdn.net/uploads/201407/26/1406383213_4418.jpg",
				"http://img.my.csdn.net/uploads/201407/26/1406383213_3557.jpg",
				"http://img.my.csdn.net/uploads/201407/26/1406383210_8779.jpg",
				"http://img.my.csdn.net/uploads/201407/26/1406383172_4577.jpg",
				"http://img.my.csdn.net/uploads/201407/26/1406383166_3407.jpg",
				"http://img.my.csdn.net/uploads/201407/26/1406383166_2224.jpg",
				"http://img.my.csdn.net/uploads/201407/26/1406383166_7301.jpg",
				"http://img.my.csdn.net/uploads/201407/26/1406383165_7197.jpg",
				"http://img.my.csdn.net/uploads/201407/26/1406383150_8410.jpg",
				"http://img.my.csdn.net/uploads/201407/26/1406383131_3736.jpg",
				"http://img.my.csdn.net/uploads/201407/26/1406383130_5094.jpg",
				"http://img.my.csdn.net/uploads/201407/26/1406383130_7393.jpg",
				"http://img.my.csdn.net/uploads/201407/26/1406383129_8813.jpg",
				"http://img.my.csdn.net/uploads/201407/26/1406383100_3554.jpg",
				"http://img.my.csdn.net/uploads/201407/26/1406383093_7894.jpg",
				"http://img.my.csdn.net/uploads/201407/26/1406383092_2432.jpg",
				"http://img.my.csdn.net/uploads/201407/26/1406383092_3071.jpg",
				"http://img.my.csdn.net/uploads/201407/26/1406383091_3119.jpg",
				"http://img.my.csdn.net/uploads/201407/26/1406383059_6589.jpg",
				"http://img.my.csdn.net/uploads/201407/26/1406383059_8814.jpg",
				"http://img.my.csdn.net/uploads/201407/26/1406383059_2237.jpg",
				"http://img.my.csdn.net/uploads/201407/26/1406383058_4330.jpg",
				"http://img.my.csdn.net/uploads/201407/26/1406383038_3602.jpg",
				"http://img.my.csdn.net/uploads/201407/26/1406382942_3079.jpg",
				"http://img.my.csdn.net/uploads/201407/26/1406382942_8125.jpg",
				"http://img.my.csdn.net/uploads/201407/26/1406382942_4881.jpg",
				"http://img.my.csdn.net/uploads/201407/26/1406382941_4559.jpg",
				"http://img.my.csdn.net/uploads/201407/26/1406382941_3845.jpg",
				"http://img.my.csdn.net/uploads/201407/26/1406382924_8955.jpg",
				"http://img.my.csdn.net/uploads/201407/26/1406382923_2141.jpg",
				"http://img.my.csdn.net/uploads/201407/26/1406382923_8437.jpg",
				"http://img.my.csdn.net/uploads/201407/26/1406382922_6166.jpg",
				"http://img.my.csdn.net/uploads/201407/26/1406382922_4843.jpg",
				"http://img.my.csdn.net/uploads/201407/26/1406382905_5804.jpg",
				"http://img.my.csdn.net/uploads/201407/26/1406382904_3362.jpg",
				"http://img.my.csdn.net/uploads/201407/26/1406382904_2312.jpg",
				"http://img.my.csdn.net/uploads/201407/26/1406382904_4960.jpg",
				"http://img.my.csdn.net/uploads/201407/26/1406382900_2418.jpg",
				"http://img.my.csdn.net/uploads/201407/26/1406382881_4490.jpg",
				"http://img.my.csdn.net/uploads/201407/26/1406382881_5935.jpg",
				"http://img.my.csdn.net/uploads/201407/26/1406382880_3865.jpg",
				"http://img.my.csdn.net/uploads/201407/26/1406382880_4662.jpg",
				"http://img.my.csdn.net/uploads/201407/26/1406382879_2553.jpg",
				"http://img.my.csdn.net/uploads/201407/26/1406382862_5375.jpg",
				"http://img.my.csdn.net/uploads/201407/26/1406382862_1748.jpg",
				"http://img.my.csdn.net/uploads/201407/26/1406382861_7618.jpg",
				"http://img.my.csdn.net/uploads/201407/26/1406382861_8606.jpg",
				"http://img.my.csdn.net/uploads/201407/26/1406382861_8949.jpg",
				"http://img.my.csdn.net/uploads/201407/26/1406382841_9821.jpg",
				"http://img.my.csdn.net/uploads/201407/26/1406382840_6603.jpg",
				"http://img.my.csdn.net/uploads/201407/26/1406382840_2405.jpg",
				"http://img.my.csdn.net/uploads/201407/26/1406382840_6354.jpg",
				"http://img.my.csdn.net/uploads/201407/26/1406382839_5779.jpg",
				"http://img.my.csdn.net/uploads/201407/26/1406382810_7578.jpg",
				"http://img.my.csdn.net/uploads/201407/26/1406382810_2436.jpg",
				"http://img.my.csdn.net/uploads/201407/26/1406382809_3883.jpg",
				"http://img.my.csdn.net/uploads/201407/26/1406382809_6269.jpg",
				"http://img.my.csdn.net/uploads/201407/26/1406382808_4179.jpg",
				"http://img.my.csdn.net/uploads/201407/26/1406382790_8326.jpg",
				"http://img.my.csdn.net/uploads/201407/26/1406382789_7174.jpg",
				"http://img.my.csdn.net/uploads/201407/26/1406382789_5170.jpg",
				"http://img.my.csdn.net/uploads/201407/26/1406382789_4118.jpg",
				"http://img.my.csdn.net/uploads/201407/26/1406382788_9532.jpg",
				"http://img.my.csdn.net/uploads/201407/26/1406382767_3184.jpg",
				"http://img.my.csdn.net/uploads/201407/26/1406382767_4772.jpg",
				"http://img.my.csdn.net/uploads/201407/26/1406382766_4924.jpg",
				"http://img.my.csdn.net/uploads/201407/26/1406382766_5762.jpg",
				"http://img.my.csdn.net/uploads/201407/26/1406382765_7341.jpg"};

	
	
			
		for(int i=0;i<4;i++){
			//list.add(images[i]);
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
	list.add("http://image.baidu.com/search/down?tn=download&word=download&ie=utf8&fr=detail&url=http%3A%2F%2Fe.hiphotos.baidu.com%2Fimage%2Fpic%2Fitem%2F72f082025aafa40fcfb29202af64034f79f019fc.jpg&thumburl=http%3A%2F%2Fe.hiphotos.baidu.com%2Fimage%2Fh%253D200%2Fsign%3Dc57dfee5344e251ffdf7e3f89787c9c2%2F72f082025aafa40fcfb29202af64034f79f019fc.jpg");
		}
		bitmapCache = new BitmapCache();
		mImageLoader = new ImageLoader(Volley.newRequestQueue(this), bitmapCache);
		adapter = new MyBaseAdapter<String>(this, list, R.layout.volleyimageview) {

			@Override
			public void convert(ViewHolder vh, String t,int position) {
				vh.setText(R.id.textview, "aaa"+position);
				ImageView networkImageView = vh.getView(R.id.imageview);
				networkImageView.setTag(t);
				ImageListener listerer = ImageLoader.getImageListener(networkImageView, R.drawable.ic_launcher, R.drawable.ic_launcher);
				mImageLoader.get(t, getImageListener(networkImageView, R.drawable.ic_launcher, R.drawable.ic_launcher, t));
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
		lv.setOnRefreshListener(new OnReflashListener() {
			
			@Override
			public void OnReflash() {
				new AsyncTask<String, Integer, String>() {

					@Override
					protected String doInBackground(String... params) {
						try {
							Thread.sleep(1000);
						} catch (InterruptedException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
						return null;
					}
					@Override
					protected void onPostExecute(String result) {
						lv.onReflashCompleted();
						Log.e("sb", "done");
						super.onPostExecute(result);
					}
				}.execute();
			}
		});
		
		

	}
	 /**
	  * 避免图片错位
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

	class BitmapCache implements ImageCache {
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
