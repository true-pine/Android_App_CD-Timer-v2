package com.capston.cd_timer_v2.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.widget.Toolbar;

import com.capston.cd_timer_v2.MyApplication;
import com.capston.cd_timer_v2.R;
import com.capston.cd_timer_v2.DragLinearLayout;

public class MainActivity extends AppCompatActivity {

    private MyApplication myApp;
    private ImageView[] skillIv;
    private ImageView[][] spellIv;
    private DragLinearLayout dragLayout;
    private boolean check_realignment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        initVariables();
        setImageViews();

        /*for(RivalPlayerDataModel temp : myApp.playerList) {
            Log.e("eeee", temp.toString());
        }

        for(ChampionDataModel temp : myApp.championList) {
            Log.e("MainActivity", temp.toString());
        }*/
    }

    private void initVariables() {
        myApp = (MyApplication) getApplication();
        skillIv = new ImageView[5];
        spellIv = new ImageView[5][2];

        dragLayout = findViewById(R.id.dragLayout);

        check_realignment = false;
    }

    private void setImageViews() {
        int resId;
        String packageName = getPackageName();
        for(int i = 0; i < myApp.playerList.size(); i++) {
            //상대방 챔피언 확인 및 이미지 설정
            for(int j = 0; j < myApp.championList.size(); j++) {
                if(myApp.playerList.get(i).championKey == Integer.parseInt(myApp.championList.get(j).getKey())) {
                    resId = getResources().getIdentifier("mainActivity_iv_skill" + (i+1), "id", packageName);
                    skillIv[i] = findViewById(resId);
                    skillIv[i].setImageBitmap(myApp.championList.get(j).spellImage);
                    break;
                }
            }
            //상대방 스펠 확인 및 이미지 설정
            for(int k = 0; k < 2; k++) {
                for(int m = 0; m < myApp.spellList.size(); m++) {
                    if(myApp.playerList.get(i).spells[k] == Integer.parseInt(myApp.spellList.get(m).key)) {
                        resId = getResources().getIdentifier("mainActivity_iv_spell" + (i+1) + "_" + (k+1), "id", packageName);
                        spellIv[i][k] = findViewById(resId);
                        spellIv[i][k].setImageBitmap(myApp.spellList.get(m).spellImage);
                        break;
                    }
                }
            }
        }
    }

    @Override
    public void onBackPressed() {
        myApp.resetPlayerData();
        super.onBackPressed();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.toolbar_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.menu_set_item) {
            if(check_realignment) {
                Toast.makeText(myApp, "저장되었습니다", Toast.LENGTH_SHORT).show();
                for(int i = 0; i < dragLayout.getChildCount(); i++) {
                    View child = dragLayout.getChildAt(i);
                    dragLayout.removeViewDraggable(child);
                }
                check_realignment = false;
            } else {
                Toast.makeText(myApp, "순서를 재배치하세요", Toast.LENGTH_SHORT).show();
                for (int i = 0; i < dragLayout.getChildCount(); i++) {
                    View child = dragLayout.getChildAt(i);
                    dragLayout.setViewDraggable(child, child);
                }
                check_realignment = true;
            }

        }
        return true;
    }

    public void onClickImageView(View view) {
        Toast.makeText(myApp, "클릭되었다", Toast.LENGTH_SHORT).show();
    }
}
