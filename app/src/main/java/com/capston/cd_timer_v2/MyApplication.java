package com.capston.cd_timer_v2;

import android.app.Application;

import com.capston.cd_timer_v2.data.ChampionDataModel;
import com.capston.cd_timer_v2.data.RivalPlayerDataModel;
import com.capston.cd_timer_v2.data.SummonerSpellDataModel;

import java.util.ArrayList;

public class MyApplication extends Application {
    public ArrayList<ChampionDataModel> championList;
    public ArrayList<SummonerSpellDataModel> spellList;
    public ArrayList<RivalPlayerDataModel> playerList;

    @Override
    public void onCreate() {
        super.onCreate();

        championList = new ArrayList<>();
        spellList = new ArrayList<>();
        playerList = new ArrayList<>();
    }

    public void resetPlayerData() {
        playerList = new ArrayList<>();
    }
}
