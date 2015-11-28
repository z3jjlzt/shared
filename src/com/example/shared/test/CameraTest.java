package com.example.shared.test;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

import com.example.shared.R;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.widget.ImageView;


/**
 * @author z3jjlzt
 *2015年11月26日
 *照片贮存在自定义位置 直接取出 避免图片不清晰
 */
public class CameraTest extends Activity {
	private String path = Environment.getExternalStorageDirectory() + "/";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_camera_test);
		camera();
	}

	private void camera() {
		Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

		File file = new File(path + "img");
		Uri uri = Uri.fromFile(file);
		intent.putExtra(MediaStore.EXTRA_OUTPUT, uri);
		startActivityForResult(intent, 1);

	}
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		if(requestCode==1){
			FileInputStream fis= null;
			try {
				fis = new FileInputStream(path+"img");
				Bitmap bitmap = BitmapFactory.decodeStream(fis);
		(	(ImageView)	CameraTest.this.findViewById(R.id.imageView1)).setImageBitmap(bitmap);
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			}finally {
				if(fis!=null){
					try {
						fis.close();
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
			}
			
		}
	}

}
