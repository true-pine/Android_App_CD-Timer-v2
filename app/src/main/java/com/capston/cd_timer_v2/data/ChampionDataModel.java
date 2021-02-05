package com.capston.cd_timer_v2.data;


import android.graphics.Bitmap;

import androidx.annotation.NonNull;

import java.util.Arrays;

public class ChampionDataModel {
    private String id;
    private String key;
    private String name;
    public Bitmap spellImage;
    public int[] cooltime;

    public ChampionDataModel(String id, String key, String name) {
        this.id = id;
        this.key = key;
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public String getKey() {
        return key;
    }

    public String getName() {
        return name;
    }

    @NonNull
    @Override
    public String toString() {
        String cooltime = null;
        try {
            cooltime = Arrays.toString(this.cooltime);
        } catch (NullPointerException e) {
            e.printStackTrace();
        }
        return "id=" + id + " / key=" + key + " / name=" + name + " / cooltime" + cooltime;
    }
}
