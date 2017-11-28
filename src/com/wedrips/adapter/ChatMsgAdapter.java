package com.wedrips.adapter;

import java.lang.reflect.Field;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.wedrips.R;
import com.wedrips.chat.ChatActivity;
import com.wedrips.chat.ChatMsgEntity;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.media.MediaPlayer;
import android.media.MediaPlayer.OnCompletionListener;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.style.ImageSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class ChatMsgAdapter extends BaseAdapter {
	public static final String Cache_ChatVoice_Path="/sdcard/WeDrips/Cache/Chat/";
	public static interface IMsgViewType {
		int IMVT_COM_MSG = 0;
		int IMVT_TO_MSG = 1;
	}

	private static final String TAG = ChatMsgAdapter.class.getSimpleName();

	private List<ChatMsgEntity> coll;
	private int keyboardheight;
	public Context mContext;
	private boolean isGroup;
	private LayoutInflater mInflater;
	private MediaPlayer mMediaPlayer= new MediaPlayer();

	public ChatMsgAdapter(Context context, List<ChatMsgEntity> coll,int keyboardheight, boolean isGroup) {
		this.mContext = context;
		this.keyboardheight=keyboardheight;
		this.isGroup=isGroup;
		this.coll = coll;
		mInflater = LayoutInflater.from(context);
	}

	public int getCount() {
		return coll.size();
	}

	public Object getItem(int position) {
		return coll.get(position);
	}

	public long getItemId(int position) {
		return position;
	}

	public int getItemViewType(int position) {
		// TODO Auto-generated method stub
		ChatMsgEntity entity = coll.get(position);

		if (entity.getMsgType()) {
			return IMsgViewType.IMVT_COM_MSG;
		} else {
			return IMsgViewType.IMVT_TO_MSG;
		}

	}

	public int getViewTypeCount() {
		// TODO Auto-generated method stub
		return 2;
	}

	public View getView(int position, View convertView, ViewGroup parent) {

		final ChatMsgEntity entity = coll.get(position);
		boolean isComMsg = entity.getMsgType();

		ViewHolder viewHolder = null;
		if (convertView == null) {
			if (isComMsg) {
				convertView = mInflater.inflate(R.layout.chat_msg_left, null);
			} else {
				convertView = mInflater.inflate(R.layout.chat_msg_right, null);
			}
			viewHolder = new ViewHolder();
			viewHolder.tvSendTime = (TextView) convertView.findViewById(R.id.tv_sendtime);
			viewHolder.tvUserName = (TextView) convertView.findViewById(R.id.tv_username);
			if(isGroup){
				viewHolder.tvUserName.setVisibility(View.VISIBLE);
				viewHolder.tvUserName.setText(entity.getName());
			}else{
				viewHolder.tvUserName.setVisibility(View.GONE);
			}
			viewHolder.tvContent = (TextView) convertView.findViewById(R.id.tv_chatcontent);
			viewHolder.tvTime = (TextView) convertView.findViewById(R.id.voice_time);
			viewHolder.isComMsg = isComMsg;
			convertView.setTag(viewHolder);
		} else {
			viewHolder = (ViewHolder) convertView.getTag();
		}

		viewHolder.tvSendTime.setText(entity.getDate());
		
		if (entity.getText().contains(".amr")) {
			viewHolder.tvContent.setText("");
			viewHolder.tvContent.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.chatto_voice_playing, 0);
			viewHolder.tvTime.setText(entity.getTime());
		} else {
			viewHolder.tvTime.setText("");
			// 对内容做处理
			SpannableStringBuilder sp = span(viewHolder.tvContent,coll.get(position).text);
			viewHolder.tvContent.setCompoundDrawablesWithIntrinsicBounds(0, 0, 0, 0);
			viewHolder.tvContent.setText(sp);
		}
		viewHolder.tvContent.setOnClickListener(new OnClickListener() {
			
			public void onClick(View v) {
				if (entity.getText().contains(".amr")) {
					playMusic(Cache_ChatVoice_Path+entity.getText()) ;
				}
			}
		});
		
		return convertView;
	}

	static class ViewHolder {
		public TextView tvSendTime;
		public TextView tvUserName;
		public TextView tvContent;
		public TextView tvTime;
		public boolean isComMsg = true;
	}
	
	private SpannableStringBuilder span(final TextView textview,
			String content){
		SpannableStringBuilder sp = new SpannableStringBuilder(content);
		String regex = "#e\\d{1,2}#";//表情的正则表达式
		Pattern p = Pattern.compile(regex);
		Matcher m = p.matcher(content);
		Field field;
		while (m.find()) {
			String tempText = m.group();
			String position = tempText.substring("#e".length(),tempText.length() - "#".length());
			try {
				field = R.drawable.class.getDeclaredField("emoji_"+ position);
				int resouseId = Integer.parseInt(field.get(null).toString());
				Bitmap bitmap=BitmapFactory.decodeResource(mContext.getResources(), resouseId);
				int rawHeigh = bitmap.getHeight(); int rawWidth = bitmap.getHeight();//压缩图片大小
				int newHeight = keyboardheight/6; int newWidth = keyboardheight/6;
				float heightScale = ((float) newHeight) / rawHeigh;// 计算缩放因子
				float widthScale = ((float) newWidth) / rawWidth;
				Matrix matrix = new Matrix();// 新建立矩阵
				matrix.postScale(heightScale, widthScale);
				Bitmap newBitmap = Bitmap.createBitmap(bitmap, 0, 0,rawWidth, rawHeigh, matrix, true);
				ImageSpan imageSpan=new ImageSpan(mContext, newBitmap); 
				sp.setSpan(imageSpan, m.start(), m.end(),Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);//配置ImageSpan
			} catch (Exception e) {
				e.printStackTrace();
				}
		}
		return sp;
	}
	
	private void playMusic(String name) {
		if(name!=null){
			try {
				if (mMediaPlayer.isPlaying()) {
					mMediaPlayer.stop();
				}
				mMediaPlayer.reset();
				mMediaPlayer.setDataSource(name);
				mMediaPlayer.prepare();
				mMediaPlayer.start();
				mMediaPlayer.setOnCompletionListener(new OnCompletionListener() {
					public void onCompletion(MediaPlayer mp) {

					}
				});

			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	private void stop() {

	}

}
