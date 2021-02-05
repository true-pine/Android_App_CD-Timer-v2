package com.capston.cd_timer_v2.data;

import android.graphics.Bitmap;

import androidx.annotation.NonNull;

public class SummonerSpellDataModel {
    public String id;
    public String cooldown;
    public String key;
    public Bitmap spellImage;

    public SummonerSpellDataModel(String id, String cooldown, String key) {
        this.id = id;
        this.cooldown = cooldown;
        this.key = key;
    }

    @NonNull
    @Override
    public String toString() {
        return id + " / " + cooldown + " / " + key;
    }
}
