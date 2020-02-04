package com.wei.datasearch.bean;

import java.io.Serializable;

/**
 * @Author WCL
 * @Date 2019/6/17 9:59
 * @Version 1.0
 * @Description
 */
public class DataBean implements Serializable {

    private String id;//唯一标识
    private String project;//项目号
    private String model;//产品型号
    private String name;//产品名称
    private String pic;//简图
    private String type;//机台(原:生产类型)
    private String material;//贴膜(原:材质)
    private String land;//厚度
    private String miZhong;//米重
    private String length;//长度
    private String num;//定支数
    private String kg;//理重
    private String color;//颜色名称
    private String pack;//包装名称


    public DataBean(String project, String model, String name, String pic, String type, String material, String land, String miZhong, String length, String num, String kg, String color, String pack) {
        this.id = id;
        this.project = project;
        this.model = model;
        this.name = name;
        this.pic = pic;
        this.type = type;
        this.material = material;
        this.land = land;
        this.miZhong = miZhong;
        this.length = length;
        this.num = num;
        this.kg = kg;
        this.color = color;
        this.pack = pack;
    }

    public DataBean(String id, String project, String model, String name, String pic, String type, String material, String land, String miZhong, String length, String num, String kg, String color, String pack) {
        this.id = id;
        this.project = project;
        this.model = model;
        this.name = name;
        this.pic = pic;
        this.type = type;
        this.material = material;
        this.land = land;
        this.miZhong = miZhong;
        this.length = length;
        this.num = num;
        this.kg = kg;
        this.color = color;
        this.pack = pack;
    }

    //    public DataBean(String model, String name, String pic, String type, String material, String land, String miZhong, String length) {
//        this.model = model;
//        this.name = name;
//        this.pic = pic;
//        this.type = type;
//        this.material = material;
//        this.land = land;
//        this.miZhong = miZhong;
//        this.length = length;
//    }
//
//    public DataBean(String id, String model, String name, String pic, String type, String material, String land, String miZhong, String length) {
//        this.id = id;
//        this.model = model;
//        this.name = name;
//        this.pic = pic;
//        this.type = type;
//        this.material = material;
//        this.land = land;
//        this.miZhong = miZhong;
//        this.length = length;
//    }


    public String getProject() {
        return project;
    }

    public void setProject(String project) {
        this.project = project;
    }

    public String getNum() {
        return num;
    }

    public void setNum(String num) {
        this.num = num;
    }

    public String getKg() {
        return kg;
    }

    public void setKg(String kg) {
        this.kg = kg;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public String getPack() {
        return pack;
    }

    public void setPack(String pack) {
        this.pack = pack;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPic() {
        return pic;
    }

    public void setPic(String pic) {
        this.pic = pic;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getMaterial() {
        return material;
    }

    public void setMaterial(String material) {
        this.material = material;
    }

    public String getLand() {
        return land;
    }

    public void setLand(String land) {
        this.land = land;
    }

    public String getMiZhong() {
        return miZhong;
    }

    public void setMiZhong(String miZhong) {
        this.miZhong = miZhong;
    }

    public String getLength() {
        return length;
    }

    public void setLength(String length) {
        this.length = length;
    }
}
