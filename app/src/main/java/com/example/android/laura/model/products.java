package com.example.android.laura.model;

public class products
{
    private String name,description,image;
    public products()
    {

    }
    public products(String pname,String des,String im)
    {
        name=pname;
        description=des;
        image=im;
    }

    public String getDescription() {
        return description;
    }

    public String getImage() {
        return image;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
       public void setDescription(String description)
       {
           this.description=description;
       }

    public void setImage(String image) {
        this.image = image;
    }
}
