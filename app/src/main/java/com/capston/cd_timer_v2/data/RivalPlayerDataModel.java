package com.capston.cd_timer_v2.data;

import androidx.annotation.NonNull;

public class  RivalPlayerDataModel {
    public int teamId;
    public int[] spells;
    public int championKey;
    public String summonerName;

    public RivalPlayerDataModel(int teamId, int[] spells, int championKey, String summonerName) {
        this.teamId = teamId;
        this.spells = spells;
        this.championKey = championKey;
        this.summonerName = summonerName;
    }

    @NonNull
    @Override
    public String toString() {
        return teamId + " / " + championKey + " / " + spells[0] + ", " + spells[1];
    }
}
