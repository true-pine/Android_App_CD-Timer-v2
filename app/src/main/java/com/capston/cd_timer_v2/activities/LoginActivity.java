package com.capston.cd_timer_v2.activities;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.capston.cd_timer_v2.CustomProgressDialog;
import com.capston.cd_timer_v2.MyApplication;
import com.capston.cd_timer_v2.R;
import com.capston.cd_timer_v2.data.RivalPlayerDataModel;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.ArrayList;

public class LoginActivity extends AppCompatActivity {

    private EditText nickname;
    private Button button;

    private CustomProgressDialog dialog;
    private Handler handler;

    private MyApplication myApp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        nickname = (EditText)findViewById(R.id.loginActivity_et_nickname);
        button = (Button)findViewById(R.id.loginActivity_btn_ok);

        dialog = new CustomProgressDialog(LoginActivity.this, "게임정보 불러오는 중", 20);
        handler = new Handler(new Handler.Callback() {
            @Override
            public boolean handleMessage(@NonNull Message msg) {
                dialog.dismiss();
                if(!myApp.playerList.isEmpty()) {
                    startActivity(new Intent(LoginActivity.this, MainActivity.class));
                    return true;
                }
                return false;
            }
        });

        myApp = (MyApplication)getApplication();

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                nickname.clearFocus();
                dialog.show();

                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        String url = "https://kr.api.riotgames.com/lol/summoner/v4/summoners/by-name/" +
                                nickname.getText().toString() +
                                "?api_key=" + getString(R.string.API_KEY);

                        url = url.replaceAll(" ", "");

                        String json = getJsonByURL(url);

                        if(json != null) {
                            try {
                                //소환사 id
                                JSONObject jsonObject = new JSONObject(json);
                                String summonerId = jsonObject.getString("id");
                                //소환사 ID로 현재 진행중인 게임의 정보를 가져온다.
                                String gameInfo = getJsonByURL("https://kr.api.riotgames.com/lol/spectator/v4/active-games/by-summoner/" +
                                        summonerId + "?api_key=" +
                                        getString(R.string.API_KEY));
                                if (gameInfo != null) {
                                    //게임의 소환사 정보 불러오기
                                    jsonObject = new JSONObject(gameInfo);
                                    JSONArray jsonArray = jsonObject.getJSONArray("participants");
                                    for (int i = 0; i < jsonArray.length(); i++) {
                                        JSONObject playerObject = jsonArray.getJSONObject(i);
                                        int teamId = playerObject.getInt("teamId");
                                        int spell1Key = playerObject.getInt("spell1Id");
                                        int spell2Key = playerObject.getInt("spell2Id");
                                        int championKey = playerObject.getInt("championId");
                                        String summonerName = playerObject.getString("summonerName");
                                        summonerName = summonerName.replaceAll(" ", "");

                                        int[] spells = {spell1Key, spell2Key};
                                        myApp.playerList.add(new RivalPlayerDataModel(teamId, spells, championKey, summonerName));
                                    }
                                }

                            } catch (JSONException e) {
                                e.printStackTrace();
                            }

                            //상대 플레이어 정보 탐색
                            int teamId = 0;
                            String name = nickname.getText().toString().replaceAll(" ", "");
                            for (RivalPlayerDataModel temp : myApp.playerList) {
                                if (name.equals(temp.summonerName)) {
                                    teamId = temp.teamId;
                                    break;
                                }
                            }
                            for (RivalPlayerDataModel temp : new ArrayList<>(myApp.playerList)) {
                                if (temp.teamId == teamId) {
                                    myApp.playerList.remove(temp);
                                }
                            }
                            handler.sendEmptyMessage(0);
                        } else {
                            handler.sendEmptyMessage(0);
                        }
                    }
                }).start();
            }
        });
    }

    @Nullable
    private String getJsonByURL(String url) {
        InputStream is = null;
        try {
            is = new URL(url).openStream();
            BufferedReader br = new BufferedReader(new InputStreamReader(is, "UTF-8"));
            String str;
            StringBuffer buffer = new StringBuffer();
            while ((str = br.readLine()) != null) {
                buffer.append(str);
            }
            return buffer.toString();
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }
}
