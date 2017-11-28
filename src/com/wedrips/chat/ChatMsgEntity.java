package com.wedrips.chat;

public class ChatMsgEntity {
    private static final String TAG = ChatMsgEntity.class.getSimpleName();

    public String name;

    public String date;

    public String text;
    
    public String voice_time;
    
    public String getTime() {
        return voice_time;
    }

    public void setTime(String time) {
        this.voice_time = time;
    }
    
	private boolean isComMeg = true;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public boolean getMsgType() {
        return isComMeg;
    }

    public void setMsgType(boolean isComMsg) {
    	isComMeg = isComMsg;
    }

    public ChatMsgEntity() {
    }

    public ChatMsgEntity(String name, String date, String text, boolean isComMsg) {
        super();
        this.name = name;
        this.date = date;
        this.text = text;
        this.isComMeg = isComMsg;
    }

}
