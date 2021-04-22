package com.bumie.funbee;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.Date;

public class Model {
    @Expose
    @SerializedName("name")
    private String name;
    @Expose
    @SerializedName("display_name")
    private String display_name;
    @Expose
    @SerializedName("image_url")
    private String image_url;
    @Expose
    @SerializedName("conversation_id")
    private String conversation_id;
    @Expose
    @SerializedName("user_id")
    private String user_id;
    @Expose
    @SerializedName("user_name")
    private String user_name;
    @Expose
    @SerializedName("member_id")
    private String member_id;
    @Expose
    @SerializedName("id")
    private String id;
    public String message;
    public int messageType;
    public Date messageTime = new Date();

    public Model(String name) {
        this.name = name;
    }

    public Model(String message, int messageType) {
        this.message = message;
        this.messageType = messageType;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDisplay_name() {
        return display_name;
    }

    public void setDisplay_name(String display_name) {
        this.display_name = display_name;
    }

    public String getImage_url() {
        return image_url;
    }

    public void setImage_url(String image_url) {
        this.image_url = image_url;
    }

    public String getConversation_id() {
        return conversation_id;
    }

    public void setConversation_id(String conversation_id) {
        this.conversation_id = conversation_id;
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public String getUser_name() {
        return user_name;
    }

    public void setUser_name(String user_name) {
        this.user_name = user_name;
    }

    public String getMember_id() {
        return member_id;
    }

    public void setMember_id(String member_id) {
        this.member_id = member_id;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
