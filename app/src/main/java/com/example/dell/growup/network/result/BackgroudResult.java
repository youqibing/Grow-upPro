package com.example.dell.growup.network.result;

import java.io.Serializable;
import java.util.List;


public class BackgroudResult {

    private List<BackgroudResult.BackgroudList> backgroud;

    public List<BackgroudResult.BackgroudList> getBackgroud(){
        return backgroud;
    }

    public void setCharacters(List<BackgroudResult.BackgroudList> backgroud){
        this.backgroud = backgroud;
    }

    public static class BackgroudList implements Serializable {
        String name;
        String describe;
        String image;
        int price;
        int HP;
        int hungry_point;
        int category;
        String com_id;
        int unlock;
        String identifier;

        public String getImage() {
            return image;
        }

        public void setImage(String image) {
            this.image = image;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getDescribe() {
            return describe;
        }

        public void setDescribe(String describe) {
            this.describe = describe;
        }

        public int getPrice() {
            return price;
        }

        public void setPrice(int price) {
            this.price = price;
        }

        public int getHP() {
            return HP;
        }

        public void setHP(int HP) {
            this.HP = HP;
        }

        public int getHungry_point() {
            return hungry_point;
        }

        public void setHungry_point(int hungry_point) {
            this.hungry_point = hungry_point;
        }

        public int getCategory() {
            return category;
        }

        public void setCategory(int category) {
            this.category = category;
        }

        public String getCom_id() {
            return com_id;
        }

        public void setCom_id(String com_id) {
            this.com_id = com_id;
        }

        public int isLocked() {
            return unlock;
        }

        public void setLocked(int unlock) {
            unlock = unlock;
        }

        public String getIdentifier() {
            return identifier;
        }

        public void setIdentifier(String identifier) {
            this.identifier = identifier;
        }


    }
}
