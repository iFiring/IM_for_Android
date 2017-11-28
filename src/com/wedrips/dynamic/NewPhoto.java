package com.wedrips.dynamic;

import java.io.File;
import java.io.FileNotFoundException;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.wedrips.R;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TableLayout.LayoutParams;

public class NewPhoto extends Fragment {
	private ImageView newphoto, newcamera;
	private LinearLayout medialayout;
	public static final String MEDIA_FILE_PATH="/sdcard/WeDrips/Media/";
	public int windowswidth;//屏幕宽度
	private static final int PHOTO_REQUEST_TAKEPHOTO = 1;// 拍照
	private static final int PHOTO_REQUEST_GALLERY = 2;// 相册
	private static final int PHOTO_REQUEST_CUT = 3;// 裁剪
	private File file=null;
	private Uri cameraimguri=null;//文件路径
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View root = inflater.inflate(R.layout.new_photo_ed, null);
		windowswidth=getResources().getDisplayMetrics().widthPixels;//getActivity().getWindowManager().getDefaultDisplay().getWidth();
		medialayout = (LinearLayout) root.findViewById(R.id.media_layout);
		medialayout.setLayoutParams(new LinearLayout.LayoutParams(LayoutParams.MATCH_PARENT,windowswidth));// 将控件高度设置成屏幕宽度
		newphoto = (ImageView) root.findViewById(R.id.new_photo);
		newcamera = (ImageView) root.findViewById(R.id.new_camera);
		File mediafile=new File(MEDIA_FILE_PATH);
		if(!mediafile.exists()){
			mediafile.mkdirs();
		}//判断文件夹是否存在，不存在即创建
		newphoto.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				file=new File(MEDIA_FILE_PATH, getPhotoFileName());
				cameraimguri=Uri.fromFile(file);
				Intent intent = new Intent(Intent.ACTION_PICK, null);
				intent.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, "image/*");
				startActivityForResult(intent, PHOTO_REQUEST_GALLERY);
			}
		});
		newcamera.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				file=new File(MEDIA_FILE_PATH, getPhotoFileName());
				cameraimguri=Uri.fromFile(file);
				Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);// 调用系统的拍照功能
				intent.putExtra(MediaStore.EXTRA_OUTPUT, cameraimguri);// 指定拍照后照片的储存路径
				startActivityForResult(intent, PHOTO_REQUEST_TAKEPHOTO);
			}
		});
		return root;
	}

	public void onActivityResult(int requestCode, int resultCode, Intent data) {

		switch (requestCode) {
		case PHOTO_REQUEST_TAKEPHOTO:
			startPhotoCrop(cameraimguri, windowswidth);
			break;

		case PHOTO_REQUEST_GALLERY:
			if (data != null)
				startPhotoCrop(data.getData(), windowswidth);
			break;

		case PHOTO_REQUEST_CUT:
			if (data != null)
				setPicToView(data);
			break;
		}
		super.onActivityResult(requestCode, resultCode, data);

	}

	// 裁剪图片
	private void startPhotoCrop(Uri uri, int size) {
		Intent intent = new Intent("com.android.camera.action.CROP");
		intent.setDataAndType(uri, "image/*");
		//if(file.exists()){file.delete();}
		// crop为true是设置在开启的intent中设置显示的view可以剪裁
		intent.putExtra("crop", "true");

		// aspectX aspectY 是宽高的比例
		intent.putExtra("aspectX", 1);//默认图片剪裁起始位置x值
		intent.putExtra("aspectY", 1);//默认图片剪裁起始位置y值

		// outputX,outputY 是剪裁图片的宽高
		intent.putExtra("outputX", size);// 默认图片剪裁终止位置x值
		intent.putExtra("outputY", size);// 默认图片剪裁终止位置y值
		intent.putExtra("scale", true);
		intent.putExtra("return-data", false);//取消返回模糊的Bitmap对象
		intent.putExtra(MediaStore.EXTRA_OUTPUT, cameraimguri);
		intent.putExtra("outputFormat", Bitmap.CompressFormat.PNG.toString());
		intent.putExtra("noFaceDetection", true);

		startActivityForResult(intent, PHOTO_REQUEST_CUT);
	}

	// 将进行剪裁后的图片显示到UI界面上
	private void setPicToView(Intent picdata){
		Bundle bundle = picdata.getExtras();
		if (bundle != null) {
			Bitmap img = null;
			try {
				img = BitmapFactory.decodeStream(getActivity().getContentResolver().openInputStream(cameraimguri));
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			}
			Drawable drawable = new BitmapDrawable(null, img);
			medialayout.setBackgroundDrawable(drawable);// 改成setBackground()低版本不兼容
		}
	}

	// 使用系统当前日期加以调整作为照片的名称
	private String getPhotoFileName() {
		Date date = new Date(System.currentTimeMillis());
		SimpleDateFormat dateFormat = new SimpleDateFormat("'IMG'_yyyyMMdd_HHmmss");
		return dateFormat.format(date) + ".png";
	}
}