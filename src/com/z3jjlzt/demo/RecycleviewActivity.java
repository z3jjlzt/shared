package com.z3jjlzt.demo;

import java.io.File;
import java.util.ArrayList;

import com.example.shared.R;
import com.example.shared.R.drawable;
import com.example.shared.R.id;
import com.example.shared.R.layout;
import com.example.shared.utils.LztRecycleViewAdapter;
import com.nostra13.universalimageloader.cache.disc.impl.UnlimitedDiskCache;
import com.nostra13.universalimageloader.cache.disc.naming.Md5FileNameGenerator;
import com.nostra13.universalimageloader.cache.memory.impl.UsingFreqLimitedMemoryCache;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Environment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.RecyclerView.LayoutManager;
import android.support.v7.widget.RecyclerView.OnScrollListener;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.Log;
import android.view.View;
import android.widget.AbsListView;
import android.widget.ImageView;
import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * @author z3jjlzt 2015年12月6日 imageloader使用
 */
public class RecycleviewActivity extends Activity {
	@Bind(R.id.list)
	RecyclerView recycleView;

	ArrayList<String> list;

	ImageLoader imageLoader;
	ImageLoaderConfiguration config;
	DisplayImageOptions options;// 设置

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_recycleview);
		ButterKnife.bind(this);
		setData();
		todo();
	}

	@SuppressWarnings("deprecation")
	private void todo() {
		imageLoader = ImageLoader.getInstance();
		options = new DisplayImageOptions.Builder().resetViewBeforeLoading(true)
				.showImageOnLoading(R.drawable.ic_launcher).showImageOnFail(R.drawable.ic_launcher).cacheInMemory(true)
				.cacheOnDisk(true).bitmapConfig(Bitmap.Config.RGB_565).build();
		config = new ImageLoaderConfiguration.Builder(this).memoryCacheExtraOptions(480, 800).threadPoolSize(3)// 线程池内加载的数量
				.threadPriority(Thread.NORM_PRIORITY - 2).denyCacheImageMultipleSizesInMemory()
				.memoryCache(new UsingFreqLimitedMemoryCache(5 * 1024 * 1024)).memoryCacheSize(5 * 1024 * 1024)
				.discCacheSize(50 * 1024 * 1024).discCacheFileNameGenerator(new Md5FileNameGenerator())// 将保存的时候的URI名称用MD5
																										// 加密
				.defaultDisplayImageOptions(options).discCacheFileCount(100) // 缓存的文件数量
				.discCache(new UnlimitedDiskCache(getDiskCacheDir(this, "sb")))// 自定义缓存路径
				.build();// 开始构建

		imageLoader.init(config);
		LayoutManager manager = new StaggeredGridLayoutManager(3, LinearLayoutManager.VERTICAL);
		recycleView.setLayoutManager(manager);
		recycleView.setOnScrollListener(new OnScrollListener() {
			@Override
			public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
				super.onScrolled(recyclerView, dx, dy);
			}

			@Override
			public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
				switch (newState) {
				case 0:
					imageLoader.resume();
				//	Log.e("sb", "0");
					break;
				case 1:

					imageLoader.pause();
					//Log.e("sb", "1");
					break;
				case 2:

					imageLoader.pause();
					//Log.e("sb", "2");

					break;
				}

			}

		});
		recycleView.setAdapter(new LztRecycleViewAdapter<Vh, String>(this, list) {

			@Override
			public Vh createVh(View v) {
				return new Vh(v);
			}

			@Override
			public void BindVh(Vh viewholder, int position) {
				final ImageView iv = viewholder.imageView;

				imageLoader.displayImage(list.get(position), iv);
			}
		});

	}

	private void setData() {
		list = new ArrayList<String>();
		String[] images = new String[] { "http://img.my.csdn.net/uploads/201407/26/1406383299_1976.jpg",
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
				"http://img.my.csdn.net/uploads/201407/26/1406382765_7341.jpg" };

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
		for (int i = 0; i < images.length; i++) {
			list.add(images[i]);
		}
	}

	private File getDiskCacheDir(Context context, String name) {
		String cachePath;
		if (Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState())
				|| !Environment.isExternalStorageRemovable()) {
			cachePath = context.getExternalCacheDir().getPath();
		} else {
			cachePath = context.getExternalCacheDir().getPath();

		}
		Log.e("sb", cachePath);
		return new File(cachePath + File.separator + name);
	}

	class Vh extends RecyclerView.ViewHolder {
		@Bind(R.id.imageview)
		ImageView imageView;

		public Vh(View itemView) {
			super(itemView);
			ButterKnife.bind(this, itemView);
		}

	}

}
