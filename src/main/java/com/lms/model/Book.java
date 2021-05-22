package com.lms.model;


import com.lms.configs.JavaDateMySqlDate;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class Book {

    private  Integer id;

    @NotNull
    @Size(min = 6)
    private String name;

    @NotNull
    @Size(min = 6)
    private String title;

    @NotNull
    @Size(min = 3)
    private String genre;

    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    private Integer stock;

    private String created_date = new JavaDateMySqlDate().getDateTime();
    private String updated_date = new JavaDateMySqlDate().getDateTime();

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Integer getStock() {
        return stock;
    }

    public void setStock(Integer stock) {
        this.stock = stock;
    }

    public String getCreated_date() {
        return created_date;
    }


    public String getUpdated_date() {
        return updated_date;
    }

}
