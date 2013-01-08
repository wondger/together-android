package com.fedtool.together_android;

import java.io.File;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.provider.MediaStore;

public class Photo {
	public static void startCamera(Activity act, int CAPTURE_CODE) {
		// TODO Auto-generated method stub
		
		String imgPath = "/sdcard/together/avatar.png";
		//����ȷ���ļ���·�����ڣ��������պ��޷���ɻص�
		File vFile = new File(imgPath);

		if(!vFile.exists())
		{
			File vDirPath = vFile.getParentFile(); //new File(vFile.getParent());
			vDirPath.mkdirs();
		}
		
		Uri uri = Uri.fromFile(vFile);
		// mCurrentPhotoFile = new File(PHOTO_DIR,getPhotoFileName());
		// //�õ�ǰʱ���ȡ�õ�ͼƬ����
		Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
		intent.putExtra(MediaStore.EXTRA_OUTPUT, uri);//
		intent.putExtra(MediaStore.EXTRA_VIDEO_QUALITY, 1);
		intent.putExtra(MediaStore.EXTRA_SIZE_LIMIT, 1);
		// Intent intent = new
		// Intent("android.media.action.IMAGE_CAPTURE");
		// Uri fromFile = Uri.fromFile(mCurrentPhotoFile);
		// String string = fromFile.toString();
		// Log.i("gp", string);
		// intent.putExtra(MediaStore.EXTRA_OUTPUT,
		// Uri.fromFile(mCurrentPhotoFile));
		act.startActivityForResult(intent, CAPTURE_CODE);
	}
	
	public static void startGallery(Activity act, int IMAGE_CODE) {
		// TODO Auto-generated method stub
		Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
		intent.setType("image/*");
		act.startActivityForResult(intent, IMAGE_CODE);
	}

}
