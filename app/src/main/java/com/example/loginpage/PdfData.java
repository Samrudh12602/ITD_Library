package com.example.loginpage;

public class PdfData {
    private String category,name,url;

    public PdfData(String category, String name, String url) {
        this.category = category;
        this.name = name;
        this.url = url;
    }

    public PdfData() {
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
