package com.boniu.starplan.entity;

/**
 * 通用弹窗模型类
 */
public class NoticeBean {
    private String type= "";
    //类型  1。通用成功，2。通用失败，3。提交审核，4。金币领取，
    private String title = "";//标题
    private String content = "";//内容
    private String goldNum = "";//金币数
    private String clickText = "";//点击按钮文本
    private String clickType = "";//点击按钮事件
    private String taskId = "";//任务id
    private boolean isCanClose = false;//xx图标是否 隐藏

    private String nextText = "";//第二文本
    private String nextType = "";//第二文本点击事件
    private boolean nextShow = true;//第二文本 是否隐藏

    public String getNextText() {
        return nextText;
    }

    public void setNextText(String nextText) {
        this.nextText = nextText;
    }

    public String getNextType() {
        return nextType;
    }

    public void setNextType(String nextType) {
        this.nextType = nextType;
    }

    public boolean isNextShow() {
        return nextShow;
    }

    public void setNextShow(boolean nextShow) {
        this.nextShow = nextShow;
    }

    public String getClickText() {
        return clickText;
    }

    public void setClickText(String clickText) {
        this.clickText = clickText;
    }

    public String getClickType() {
        return clickType;
    }

    public void setClickType(String clickType) {
        this.clickType = clickType;
    }

    public boolean isCanClose() {
        return isCanClose;
    }

    public void setCanClose(boolean canClose) {
        isCanClose = canClose;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getGoldNum() {
        return goldNum;
    }

    public void setGoldNum(String goldNum) {
        this.goldNum = goldNum;
    }


    public String getTaskId() {
        return taskId;
    }

    public void setTaskId(String taskId) {
        this.taskId = taskId;
    }
}
