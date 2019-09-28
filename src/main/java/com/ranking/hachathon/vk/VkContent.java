package com.ranking.hachathon.vk;

public class VkContent {
    private VkPostType type;
    private int height;
    private int width;
    private String url;

    public VkPostType getType() {
        return type;
    }

    public void setType(VkPostType type) {
        this.type = type;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
