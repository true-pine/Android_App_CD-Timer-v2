package com.capston.cd_timer_v2.activities;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.WindowManager;

import com.capston.cd_timer_v2.CustomProgressDialog;
import com.capston.cd_timer_v2.MyApplication;
import com.capston.cd_timer_v2.R;
import com.capston.cd_timer_v2.data.ChampionDataModel;
import com.capston.cd_timer_v2.data.SummonerSpellDataModel;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.ArrayList;
import java.util.Iterator;

public class SplashActivity extends AppCompatActivity {

    private Handler handler;
    private CustomProgressDialog dialog;
    private MyApplication mApp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        initializeVariable();

        dialog = new CustomProgressDialog(SplashActivity.this, "챔피언정보 불러오는 중", 18);
        dialog.show();

        readGameData();
    }

    //롤 데이터 불러오기
    private void readGameData() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                String json = getJsonByURL(getString(R.string.DATA_DRAGON) + "/data/ko_KR/champion.json");
                if (json != null) {

                    //챔피언 이름 및 키 불러오기
                    try {
                        JSONObject jsonObject = new JSONObject(json);
                        jsonObject = jsonObject.getJSONObject("data");
                        Iterator keys = jsonObject.keys();
                        while(keys.hasNext()) {
                            String s = keys.next().toString();
                            JSONObject temp = jsonObject.getJSONObject(s);
                            String id = temp.getString("id");
                            String key = temp.getString("key");
                            String name = temp.getString("name");
                            mApp.championList.add(new ChampionDataModel(id, key, name));
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                    //챔피언 궁 이미지 및 쿨타임 불러오기
                    for (int i = 0; i < mApp.championList.size(); i++) {
                        String spellName;
                        json = getJsonByURL(getString(R.string.DATA_DRAGON) + "/data/ko_KR/champion/" + mApp.championList.get(i).getId() + ".json");
                        if (json != null) {
                            try {
                                JSONObject jsonObject = new JSONObject(json);
                                //data -> (챔피언 이름) -> spells -> 3번 인덱스 JSONObject 찾는 과정
                                jsonObject = jsonObject.getJSONObject("data");
                                jsonObject = jsonObject.getJSONObject(mApp.championList.get(i).getId());
                                JSONArray jsonArray = jsonObject.getJSONArray("spells");
                                jsonObject = jsonArray.getJSONObject(3);
                                //궁 스킬 이름
                                spellName = jsonObject.getString("id");
                                //궁 쿨타임 입력 (주의사항 : 궁을 찍을 수 있는 최대치가 챔피언마다 달라서 JSONArray의 길이가 다름)
                                jsonArray = jsonObject.getJSONArray("cooldown");
                                mApp.championList.get(i).cooltime = new int[jsonArray.length()];
                                for (int j = 0; j < jsonArray.length(); j++) {
                                    mApp.championList.get(i).cooltime[j] = jsonArray.getInt(j);
                                }
                                //궁 이미지 추출 및 대입
                                URL uurl = new URL(getString(R.string.DATA_DRAGON) + "/img/spell/" + spellName + ".png");
                                InputStream is = uurl.openStream();
                                mApp.championList.get(i).spellImage = BitmapFactory.decodeStream(is);
                            } catch (JSONException | IOException e) {
                                e.printStackTrace();
                            }
                        }
                    }
                }

                //소환사 주문 정보 불러오기
                json = getJsonByURL(getString(R.string.DATA_DRAGON) + "/data/ko_KR/summoner.json");
                if(json != null) {
                    try {
                        JSONObject jsonObject = new JSONObject(json);
                        jsonObject = jsonObject.getJSONObject("data");
                        Iterator keys = jsonObject.keys();
                        while(keys.hasNext()) {
                            String spellName = keys.next().toString();
                            JSONObject spell = jsonObject.getJSONObject(spellName);
                            String id = spell.getString("id");
                            String cooldown = spell.getString("cooldownBurn");
                            String key = spell.getString("key");
                            mApp.spellList.add(new SummonerSpellDataModel(id, cooldown, key));
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                    for(int i = 0; i < mApp.spellList.size(); i++) {
                        try {
                            URL url = new URL(getString(R.string.DATA_DRAGON) + "/img/spell/" + mApp.spellList.get(i).id + ".png");
                            InputStream is = url.openStream();
                            mApp.spellList.get(i).spellImage = BitmapFactory.decodeStream(is);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }

                dialog.dismiss();
                handler.sendEmptyMessage(0);
            }
        }).start();
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
            return null;
        }
    }

    private void initializeVariable() {
        mApp = (MyApplication) getApplication();
        handler = new Handler(new Handler.Callback() {
            @Override
            public boolean handleMessage(@NonNull Message msg) {
                startActivity(new Intent(SplashActivity.this, LoginActivity.class));
                finish();
                return true;
            }
        });
    }
}