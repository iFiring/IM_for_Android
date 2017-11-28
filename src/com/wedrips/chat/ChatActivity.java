package com.wedrips.chat;

import java.io.File;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Random;

import com.wedrips.R;
import com.wedrips.adapter.ChatFaceAdapter;
import com.wedrips.adapter.ChatMsgAdapter;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.text.Editable;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.TextWatcher;
import android.text.style.ImageSpan;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class ChatActivity extends Activity{
	public static final String Cache_ChatVoice_Path="/sdcard/WeDrips/Cache/Chat/";
	private SharedPreferences loginpreferences;//登录preferences包含键盘高度
	private ImageView backbtn,imgbtn,voicebtn,facebtn,unsendBtn,sendBtn,voice_volume;//按钮
	private InputMethodManager imm;//输入法管理器
	private int keyboardheight=0;//输入法高度
	private GridView facegridview;//表情gridview
	private int[] faces=new int[100];//表情数组
	private LinearLayout facelinear;//facegridview的父布局控件
	private TextView headname;//好友用户名
	private EditText edit;//输入框
	private ListView chatlistview;
	private int i=0;
	private SoundMeter mSensor=new SoundMeter();
	private List<ChatMsgEntity> mDataArrays = new ArrayList<ChatMsgEntity>();
	private boolean btn_vocie = true;
	private int flag = 1;
	private Handler mHandler = new Handler();
	private ChatMsgAdapter mAdapter;
	private String voiceName;
	private long startVoiceT, endVoiceT;
	private String[] msgArray;
//	private String[] dataArray = new String[] { "2016-5-15 8:00","2016-5-15 8:01"};
	private boolean isGroup=false;//判断是否为群聊天
	
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.chat_page);
		
		backbtn=(ImageView) findViewById(R.id.back_btn);
		headname=(TextView) findViewById(R.id.chat_f_name);
		chatlistview=(ListView) findViewById(R.id.chat_listview);
		
		imm =(InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
		File file = new File(Cache_ChatVoice_Path);
		if (!file.exists()) { file.mkdirs(); }
		
		voice_volume = (ImageView) this.findViewById(R.id.volume);
		facelinear=(LinearLayout) findViewById(R.id.face_linear);
		facegridview=(GridView) findViewById(R.id.face_gridview);
		edit=(EditText) findViewById(R.id.bottom_edit);
		sendBtn=(ImageView) findViewById(R.id.bottom_unsend);
		unsendBtn=(ImageView) findViewById(R.id.bottom_send);
		facebtn=(ImageView) findViewById(R.id.bottom_face);
		voicebtn=(ImageView) findViewById(R.id.bottom_voice);
		imgbtn=(ImageView) findViewById(R.id.bottom_img);
		
		loginpreferences=getSharedPreferences("login",Context.MODE_PRIVATE);//从SharedPreferences中获取登录时得到的键盘高度
		keyboardheight=loginpreferences.getInt("keyboardheight",0)!=0?loginpreferences.getInt("keyboardheight",0):(getResources().getDisplayMetrics().heightPixels*3/7);
		
		for(int i=0;i<100;i++){		//将表情资源存入表情数组
			try {
				Field field = R.drawable.class.getDeclaredField("emoji_"+ i);
				int resouseId = Integer.parseInt(field.get(null).toString());
				faces[i]=resouseId;
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		setfaceadapter(keyboardheight);//表情适配
		
		Bundle bundle=getIntent().getExtras();//从ChatList获取用户资源
		headname.setText(bundle.getString("name"));
		msgArray=bundle.getStringArray("msgdata");
		
		sendBtn.setOnClickListener(sendlisten);
		unsendBtn.setOnClickListener(unsendsbacklisten);
		imgbtn.setOnClickListener(imglisten);
		edit.addTextChangedListener(editwatcher);//输入框监听器
		facebtn.setOnClickListener(facebtnlisten);//为表情按钮设置监听器，判断键盘状态来打开关闭表情页
		
		voicebtn.setOnTouchListener(new OnTouchListener() { //按下语音录制按钮时返回false执行父类OnTouch
			public boolean onTouch(View v, MotionEvent event) {
				return false;
			}
		});
		
		backbtn.setOnClickListener(new View.OnClickListener() {public void onClick(View v) {
			imm.hideSoftInputFromWindow(edit.getWindowToken(), 0);
			finish();}});
		
		initData();
	}

	/*--------------------------------表情页监听器-----------------------------------*/
	//为表情设置Adapter的函数
	private void setfaceadapter(int kbheight){
		facegridview.setLayoutParams(new LinearLayout.LayoutParams(kbheight/4*faces.length/4,kbheight));// 设置GirdView布局参数,横向布局的关键
		facegridview.setAdapter(new ChatFaceAdapter(ChatActivity.this,faces,kbheight));
		facegridview.setSelector(new ColorDrawable(Color.TRANSPARENT));// 屏蔽GridView默认点击效果
		facegridview.setColumnWidth(kbheight/4);
		facegridview.setNumColumns(faces.length/4);
		facegridview.setStretchMode(GridView.NO_STRETCH);
		facegridview.setOnItemClickListener(new AdapterView.OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				try {
				Field field = R.drawable.class.getDeclaredField("emoji_"+ position);
				int resouseId = Integer.parseInt(field.get(null).toString());
				
				Bitmap bitmap=BitmapFactory.decodeResource(getResources(), resouseId);
				int rawHeigh = bitmap.getHeight(); int rawWidth = bitmap.getHeight();
				int newHeight = keyboardheight/6; int newWidth = keyboardheight/6;
				float heightScale = ((float) newHeight) / rawHeigh;// 计算缩放因子
				float widthScale = ((float) newWidth) / rawWidth;
				Matrix matrix = new Matrix();// 新建立矩阵
				matrix.postScale(heightScale, widthScale);
				// matrix.postRotate(-30);// 设置图片的旋转角度
				// matrix.postSkew(0.1f, 0.1f);// 设置图片的倾斜
				Bitmap newBitmap = Bitmap.createBitmap(bitmap, 0, 0,rawWidth, rawHeigh, matrix, true);// 将图片大小压缩,压缩后图片的宽和高以及kB大小均会变化
                ImageSpan imageSpan=new ImageSpan(ChatActivity.this, newBitmap); 
                SpannableString spannableString=new SpannableString("#e"+position+"#");   //“image”是图片名称的前缀  
                if(position<=9){
                    spannableString.setSpan(imageSpan, 0, 4, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE); 
                } else{
                    spannableString.setSpan(imageSpan, 0, 5, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE); 
                }
                edit.append(spannableString);
				} catch (Exception e) {  
                    e.printStackTrace();  
                }
			}
		});
	}
	/*--------------------------------聊天页底部监听器-----------------------------------*/
	
	private OnClickListener imglisten=new View.OnClickListener() {
		@Override
		public void onClick(View v) {
			Toast.makeText(ChatActivity.this, "您按了图片按钮", Toast.LENGTH_LONG).show();
		}
	};
	
	private TextWatcher editwatcher=new TextWatcher(){
		@Override
		public void beforeTextChanged(CharSequence s, int start, int count, int after) {
		}
		@Override
		public void onTextChanged(CharSequence s, int start, int before, int count) {
		}
		@Override
		public void afterTextChanged(Editable s) {
			if(s.length()>0){
				unsendBtn.setImageResource(R.drawable.chat_send);
				unsendBtn.setOnClickListener(sendslisten);
			}else{
				unsendBtn.setImageResource(R.drawable.chat_send_back);
				unsendBtn.setOnClickListener(unsendsbacklisten);
			}
		}
		
	};
	private OnClickListener facebtnlisten=new View.OnClickListener() {
		@Override
		public void onClick(View v) {
			if(i%2!=0){
				facelinear.setLayoutParams(new FrameLayout.LayoutParams(FrameLayout.LayoutParams.MATCH_PARENT,keyboardheight));
				edit.setFocusable(true);
				edit.requestFocus();
				imm.showSoftInput(edit, InputMethodManager.SHOW_FORCED);
				i++;
			}else{
				imm.hideSoftInputFromWindow(edit.getWindowToken(),0);
				i++;
			}
		}
	};
	public OnClickListener unsendsbacklisten=new View.OnClickListener() {
		@Override
		public void onClick(View v) {
			findViewById(R.id.bottom_btns).setVisibility(View.VISIBLE);
			voicebtn.setVisibility(View.VISIBLE);
			findViewById(R.id.bottom_text).setVisibility(View.INVISIBLE);
			edit.clearFocus();
			imm.hideSoftInputFromWindow(edit.getWindowToken(),0);
			facelinear.setLayoutParams(new FrameLayout.LayoutParams(FrameLayout.LayoutParams.MATCH_PARENT,0));
		}
	};
	public OnClickListener sendslisten=new View.OnClickListener() {
		@Override
		public void onClick(View v) {
			send();
		}
	};
	public OnClickListener sendlisten=new View.OnClickListener() {
		@Override
		public void onClick(View v) {
			facelinear.setLayoutParams(new FrameLayout.LayoutParams(FrameLayout.LayoutParams.MATCH_PARENT,keyboardheight));
			findViewById(R.id.bottom_btns).setVisibility(View.INVISIBLE);
			voicebtn.setVisibility(View.GONE);
			findViewById(R.id.bottom_text).setVisibility(View.VISIBLE);
			edit.setFocusable(true);
			edit.requestFocus();
			imm.showSoftInput(edit, InputMethodManager.SHOW_FORCED);
		}
	};

	public void initData() {
		for (int i = 0; i < msgArray.length; i++) {
			ChatMsgEntity entity = new ChatMsgEntity();
//			entity.setDate(dataArray[i]);
			if (i % 2 == 0) {
				entity.setName("白富美");
				entity.setMsgType(true);
			} else {
				entity.setName("高富帅");
				entity.setMsgType(false);
			}

			entity.setText(msgArray[i]);
			mDataArrays.add(entity);
		}
		mAdapter = new ChatMsgAdapter(this, mDataArrays,keyboardheight,isGroup);
		chatlistview.setAdapter(mAdapter);
	}

	private void send() {
		String contString = edit.getText().toString();
		if (contString.length() > 0) {
			ChatMsgEntity entity = new ChatMsgEntity();
			entity.setDate(getDate());
			entity.setName("高富帅");
			entity.setMsgType(false);
			entity.setText(contString);
			mDataArrays.add(entity);
			if(mAdapter==null){
				mAdapter = new ChatMsgAdapter(this, mDataArrays,keyboardheight,isGroup);
				chatlistview.setAdapter(mAdapter);
			}
			mAdapter.notifyDataSetChanged();
			edit.setText("");
			chatlistview.setSelection(chatlistview.getCount() - 1);
		}
	}
	//获取消息时间
	private String getDate() {
		Calendar c = Calendar.getInstance();

		String year = String.valueOf(c.get(Calendar.YEAR));
		String month = String.valueOf(c.get(Calendar.MONTH));
		String day = String.valueOf(c.get(Calendar.DAY_OF_MONTH) + 1);
		String hour = String.valueOf(c.get(Calendar.HOUR_OF_DAY));
		String mins = String.valueOf(c.get(Calendar.MINUTE));

		StringBuffer sbBuffer = new StringBuffer();
		sbBuffer.append(year + "-" + month + "-" + day + " " + hour + ":"
				+ mins);

		return sbBuffer.toString();
	}

	//按下语音录制按钮时
	public boolean onTouchEvent(MotionEvent event) {

		if (!Environment.getExternalStorageDirectory().exists()) { //判断SD Card状态
			Toast.makeText(this, "No SDCard", Toast.LENGTH_LONG).show();
			return false;
		}
		System.out.println("0");

		if (btn_vocie) {
			System.out.println("1");
			voicebtn.setImageResource(R.drawable.chat_voice_press);
			int[] location = new int[2];
			voicebtn.getLocationInWindow(location); // 获取在当前窗口内的绝对坐标
			int voicebtn_Y = location[1];
			int voicebtn_X = location[0];
			int[] del_location = new int[2];
			imgbtn.getLocationInWindow(del_location);//获取删除按钮的坐标
			int del_Y = del_location[1];
			int del_x = del_location[0];
			if (event.getAction() == MotionEvent.ACTION_DOWN && flag == 1) {
				System.out.println("2");
				if (event.getY() < del_Y+imgbtn.getHeight()&&event.getY()>del_Y && event.getX() > voicebtn_X&&event.getX()<voicebtn_X+voicebtn.getWidth()) { //判断手势按下的位置是否是语音录制按钮的范围内
					System.out.println("3");
					voicebtn.setImageResource(R.drawable.chat_voice_press);//手势按下的位置在语音录制按钮的范围内
					voice_volume.setVisibility(View.VISIBLE);
					startVoiceT =System.currentTimeMillis();
					voiceName = startVoiceT + ".amr";
					start(voiceName);
					flag = 2;
				}
			} else if (event.getAction() == MotionEvent.ACTION_UP && flag == 2) {
				System.out.println("4");								//松开手势时执行录制完成
				if (event.getY() <= del_Y+imgbtn.getHeight()&&event.getY() >= del_Y
						&& event.getX() >= del_x&& event.getX() <= del_x + imgbtn.getWidth()) { 
					
					System.out.println("4.5");							//滑动到取消按钮时触摸Up
					voicebtn.setImageResource(R.drawable.chat_voice);
					voice_volume.setVisibility(View.GONE);
					imgbtn.setClickable(true);
					imgbtn.setImageResource(R.drawable.chat_photo);
					File file = new File(Cache_ChatVoice_Path+voiceName); //删除取消的录音文件
				    if (file.isFile() && file.exists()) { file.delete(); }
					stop();
					flag = 1;
					return false;
				} else {
					System.out.println("5");							//在按钮内触摸Up
					voicebtn.setImageResource(R.drawable.chat_voice);
					voice_volume.setVisibility(View.GONE);
					endVoiceT =System.currentTimeMillis();
					mHandler.postDelayed(new Runnable() {				//stop()休眠150毫秒，防止出现还没执行完start()就执行stop()而出错!
						public void run() {
							stop();
						}
					},130);
					flag = 1;
					int time = (int) ((endVoiceT - startVoiceT) / 1000);
					if (time < 1) {										//录制时间少于1S
						voicebtn.setImageResource(R.drawable.chat_voice);
						voice_volume.setVisibility(View.GONE);
						imgbtn.setClickable(true);
						imgbtn.setImageResource(R.drawable.chat_photo);
						Toast.makeText(ChatActivity.this, "录音过短", Toast.LENGTH_SHORT).show();
						File file = new File(Cache_ChatVoice_Path+voiceName); //删除1S以内的录音文件
					    if (file.isFile() && file.exists()) { file.delete(); }
						return false;
					}
					ChatMsgEntity entity = new ChatMsgEntity();
					entity.setDate(getDate());
					entity.setName("高富帅");
					entity.setTime(time+"\"");
					entity.setMsgType(false);
					entity.setText(voiceName);
					mDataArrays.add(entity);
					if(mAdapter==null){
						mAdapter = new ChatMsgAdapter(this, mDataArrays,keyboardheight,isGroup);
						chatlistview.setAdapter(mAdapter);
					}
					mAdapter.notifyDataSetChanged();
					chatlistview.setSelection(chatlistview.getCount() - 1);
					imgbtn.setClickable(true);
					imgbtn.setImageResource(R.drawable.chat_photo);
					return false;
				}
			}
			if (event.getY() < voicebtn_Y||event.getX()>voicebtn_X+voicebtn.getWidth()) {
				System.out.println("6");                                     //手势按下的位置在语音录制按钮和取消按钮之外
				voicebtn.setImageResource(R.drawable.chat_voice_press);
				imgbtn.setClickable(false);
				imgbtn.setImageResource(R.drawable.chat_delete);
				
			} else {
				System.out.println("7"); 
				if (event.getY() <= del_Y+imgbtn.getHeight()&&event.getY() >= del_Y
						&& event.getX() >= del_x&& event.getX() <= del_x + imgbtn.getWidth()) {
					System.out.println("6.5");								//手势按下的位置在取消按钮内没Up
					imgbtn.setClickable(false);
					imgbtn.setImageResource(R.drawable.chat_delete_check);
				}else{
					voicebtn.setImageResource(R.drawable.chat_voice_press); //手势按下的位置语音录制按钮内没Up
					imgbtn.setClickable(false);
					imgbtn.setImageResource(R.drawable.chat_delete);
				}
			}
		}
		return super.onTouchEvent(event);
	}
	private int timeCount = 0;
	class MyHandler extends Handler{
	  public MyHandler() {
	        }

	        public MyHandler(Looper L) {
	            super(L);
	        }
	        @Override
	        public void handleMessage(Message msg) {
	         super.handleMessage(msg);
	   if (msg.what == -1) {
	    int minute = timeCount / 60;
	    int second = timeCount % 60;
	    String min = minute >= 10 ? minute + "" : "0" + minute;
	    String sec = second >= 10 ? second + "" : "0" + second;
	    headname.setText(min + ":" + sec);
	   }
	        }
	 }

	private static final int POLL_INTERVAL = 300;

	private Runnable mSleepTask = new Runnable() {
		public void run() {
			stop();
		}
	};
	private Runnable mPollTask = new Runnable() {
		public void run() {
			double amp = mSensor.getAmplitude();
			updateDisplay(amp);
			mHandler.postDelayed(mPollTask, POLL_INTERVAL);

		}
	};

	private void start(String name) {
		mSensor.start(name);
		mHandler.postDelayed(mPollTask, POLL_INTERVAL);
	}

	private void stop() {
		mHandler.removeCallbacks(mSleepTask);
		mHandler.removeCallbacks(mPollTask);
		mSensor.stop();
	}

	private void updateDisplay(double signalEMA) {
		
		switch ((int) signalEMA) {
		case 0:
		case 1:
		case 2:
			voice_volume.setImageResource(R.drawable.volume_0);
			break;
		case 3:
		case 4:
			voice_volume.setImageResource(R.drawable.volume_1);
			
			break;
		case 5:
		case 6:
		case 7:
			voice_volume.setImageResource(R.drawable.volume_2);
			break;
		case 8:
		case 9:
			voice_volume.setImageResource(R.drawable.volume_3);
			break;
		case 10:
		case 11:
		case 12:
			voice_volume.setImageResource(R.drawable.volume_4);
			break;
		default:
			voice_volume.setImageResource(R.drawable.volume_4);
			break;
		}
	}
}
