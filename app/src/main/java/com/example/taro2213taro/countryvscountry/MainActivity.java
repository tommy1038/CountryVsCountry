package com.example.taro2213taro.countryvscountry;

import android.content.ClipData;
import android.graphics.drawable.Drawable;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.PopupMenu;
import android.text.Layout;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import static android.content.ClipData.*;

public class MainActivity extends AppCompatActivity {

    static final String PROBLEM = "配置できません";

    // モード変数
    int doing = 0;

    // オブジェクトを指定するためのID (0 = "friendly_lighttank")
    int objectID;

    // 現在いるx座標，y座標
    int numberX;
    int numberY;

    // 変更前の一時保存のx座標，y座標
    int beforeX;
    int beforeY;

    // カーソル画像
    Drawable cursorDrawable;
    // 暗くする画像
    Drawable dark;

    // 戦略フィールドで用いる変数 (3 × 7 のフィールド)
    ImageView[][] imageViews = new ImageView[3][7];
    ImageView[][] backImageViews = new ImageView[3][7];
    ImageView terrain; // TODO

    // gridの画像(枠のみ))
    Drawable grid;

    // Drawableの画像
    Drawable friendlyCapitalDrawable;
    Drawable friendlyFactoryDrawable;
    Drawable enemyCapitalDrawable;
    Drawable friendlyLighttankDrawable;

    Drawable enemyLighttankDrawable;
    Drawable friendlyTankkillerDrawable;
    Drawable friendlyInfantryDrawable;

//    Drawable friendlyCapitalblackDrawable;
//    Drawable friendlylighttankblackDrawable;

//    Drawable friendlytenkkillerblackDrwable;
//    Drawable friendlyinfantryblackDrawable;

    TextView setting;

    // TODO 使用用途の確認
    String STR;
    int INT;

    //Drawable test[][];
    PopupMenu madelandObject;
    PopupMenu moveObject;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //terrain = new ImageView;

        // TODO 使用用途の確認
        setting = findViewById(R.id.textview);

        int[] backIds = {
                R.id.back00, R.id.back01, R.id.back02, R.id.back03, R.id.back04, R.id.back05, R.id.back06,
                R.id.back10, R.id.back11, R.id.back12, R.id.back13, R.id.back14, R.id.back15, R.id.back16,
                R.id.back20, R.id.back21, R.id.back22, R.id.back23, R.id.back24, R.id.back25, R.id.back26
        };

        int[] ids = {
                R.id.imageView00, R.id.imageView01, R.id.imageView02, R.id.imageView03, R.id.imageView04, R.id.imageView05, R.id.imageView06,
                R.id.imageView10, R.id.imageView11, R.id.imageView12, R.id.imageView13, R.id.imageView14, R.id.imageView15, R.id.imageView16,
                R.id.imageView20, R.id.imageView21, R.id.imageView22, R.id.imageView23, R.id.imageView24, R.id.imageView25, R.id.imageView26
        };


        // 比較用Drawableに代入
        friendlyCapitalDrawable = ContextCompat.getDrawable(MainActivity.this, R.drawable.friendly_capital);
        friendlyFactoryDrawable = ContextCompat.getDrawable(MainActivity.this,R.drawable.friendly_factory);
        enemyCapitalDrawable = ContextCompat.getDrawable(MainActivity.this, R.drawable.enemy_capital);
        friendlyLighttankDrawable = ContextCompat.getDrawable(MainActivity.this, R.drawable.friendly_lighttank);
        friendlyInfantryDrawable = ContextCompat.getDrawable(MainActivity.this,R.drawable.friendly_infantry);
        enemyLighttankDrawable = ContextCompat.getDrawable(MainActivity.this,R.drawable.enemy_lighttank);
        friendlyTankkillerDrawable = ContextCompat.getDrawable(MainActivity.this,R.drawable.friendly_tankkiller);

        grid = ContextCompat.getDrawable(MainActivity.this, R.drawable.grid);

        // 関連付けおよび初期状態の作成
        int i = 0;

        for (int tate = 0; tate < 3; tate++) {
            for (int yoko = 0; yoko < 7; yoko++) {
                imageViews[tate][yoko] = findViewById(ids[i]);
                imageViews[tate][yoko].setBackgroundResource(R.drawable.grid);
                i++;
            }
        }

        // 関連付けおよび初期状態の作成
        int backi = 0;

        for (int tate = 0; tate < 3; tate++) {
            for (int yoko = 0; yoko < 7; yoko++) {
                backImageViews[tate][yoko] = findViewById(backIds[backi]);
                backImageViews[tate][yoko].setBackgroundResource(R.drawable.grid);
                backi++;
            }
        }

        // この文で全てのマス目に対して，gridの画像をいれている
//        for (int tate = 0; tate < 3; tate++) {
//            for (int yoko = 0; yoko < 7; yoko++) {
//                //imageViews[tate][yoko] = findViewById(ids[i]);
//                imageViews[tate][yoko].setBackgroundResource(R.drawable.grid);
//            }
//        }

        // この文で全てのマス目に対して，gridの画像をいれている
//        for (int tate = 0; tate < 3; tate++) {
//            for (int yoko = 0; yoko < 7; yoko++) {
//                //imageViews[tate][yoko] = findViewById(ids[i]);
//                backImageViews[tate][yoko].setBackgroundResource(R.drawable.grid);
//            }
//        }

        // 初期配置

        // Drawable型で，R.drawable.friendly_capitalの画像を取得する
        // Drawable friendlyCapitalDrawable = ContextCompat.getDrawable(MainActivity.this,R.drawable.friendly_capital);
        // R.drawable.friendly_capitalの画像をimageView[0][0]にセットする

        // 自/他に一つずつ，capitalの設置
        backImageViews[1][0].setBackgroundResource(R.drawable.friendly_capital);
        backImageViews[1][6].setBackgroundResource(R.drawable.enemy_capital);

        // 自/他に3つずつ，factoryの設置
        backImageViews[0][6].setBackgroundResource(R.drawable.enemy_factory);
        backImageViews[1][5].setBackgroundResource(R.drawable.enemy_factory);
        backImageViews[2][6].setBackgroundResource(R.drawable.enemy_factory);

        backImageViews[0][0].setBackgroundResource(R.drawable.friendly_factory);
        backImageViews[1][1].setBackgroundResource(R.drawable.friendly_factory);
        backImageViews[2][0].setBackgroundResource(R.drawable.friendly_factory);

        // 自/他に1つずつ，lighttankの設置
        imageViews[1][1].setBackgroundResource(R.drawable.friendly_lighttank);
        imageViews[1][5].setBackgroundResource(R.drawable.enemy_lighttank);

        // TODO terrainの設置の意図の把握
        terrain = imageViews[0][0];


        // testImageにimageView[0][0]の背景画像を代入する
        //Drawable testImage = imageViews[0][0].getBackground();

        // グリッド内の背景画像が何か確かめるコード

        /*
        for(int tate = 0; tate < 3; tate++){

            for(int yoko = 0;yoko < 7;yoko++){
                //imageViews[tate][yoko] = findViewById(ids[i]);
                Drawable DrawableImages = imageViews[tate][yoko].getBackground();
                Drawable.ConstantState constantStateDrawableFriendlyCapitalA = friendlyCapitalDrawable.getConstantState();
                Drawable.ConstantState constantStateDrawableFriendlyCapitalB = DrawableImages.getConstantState();

                if(constantStateDrawableFriendlyCapitalA.equals(constantStateDrawableFriendlyCapitalB)) {
                    // do something
                    Log.d("result","2つのDrawableは同じ"+"["+tate+"]"+"["+yoko+"]");
                } else {
                    // do something else
                    Log.d("result","2つのDrawableは違う"+"["+tate+"]"+"["+yoko+"]");
                }
                i++;
            }
        }
        */

        numberX = 0;
        numberY = 0;

        // 左上に@drawable/friendly_capitalを設置する
//        imageViews[0][0].setBackgroundResource(R.drawable.friendly_capital);
//        Bitmap bmp = BitmapFactory.decodeResource(getResources(), R.drawable.friendly_capital);
//        imageViews[0][0].setImageBitmap(bmp)


        // 指定した画像のDrawableインスタンスを取得
        // 矢印の画像をこちら

        cursorDrawable = ContextCompat.getDrawable(MainActivity.this, R.drawable.cursor);
        dark = ContextCompat.getDrawable(MainActivity.this,R.drawable.grid_blackout);

        // imageView[0][0]の位置に，カーソルの配置
        imageViews[0][0].setImageDrawable(cursorDrawable);

    }

    // 右を押したときの処理
    public void moveRight(View view) {
        Log.d("right","ok");
        // TODO 数の制限は必須
        if(numberX == 6){
//            Log.d("what","ok");
            imageViews[numberY][numberX].setImageDrawable(null);
            numberX = 0;
//            Log.d("what","ok");
            imageViews[numberY][numberX].setImageDrawable(cursorDrawable);
        } else {
            imageViews[numberY][numberX].setImageDrawable(null);
            numberX++;
            imageViews[numberY][numberX].setImageDrawable(cursorDrawable);
        }

    }

    // 左を押したときの処理
    public void moveLeft(View view){
        // TODO 数の制限は必須
        if(numberX == 0){
            imageViews[numberY][numberX].setImageDrawable(null);
            numberX = 6;
            imageViews[numberY][numberX].setImageDrawable(cursorDrawable);
        }else {
            imageViews[numberY][numberX].setImageDrawable(null);
            numberX--;
            imageViews[numberY][numberX].setImageDrawable(cursorDrawable);
        }

    }

    // 上を押したときの処理
    public void moveUp(View view) {
        // TODO 数の制限は必須
        if(numberY == 0) {
            imageViews[numberY][numberX].setImageDrawable(null);
            numberY = 2;
            imageViews[numberY][numberX].setImageDrawable(cursorDrawable);
        } else {
            imageViews[numberY][numberX].setImageDrawable(null);
            numberY--;
            imageViews[numberY][numberX].setImageDrawable(cursorDrawable);
        }
    }

    // 下を押したときの処理
    public void moveDown(View view){
        // TODO 数の制限は必須
        if(numberY == 2) {
            imageViews[numberY][numberX].setImageDrawable(null);
            numberY = 0;
            imageViews[numberY][numberX].setImageDrawable(cursorDrawable);
        } else {
            imageViews[numberY][numberX].setImageDrawable(null);
            numberY++;
            imageViews[numberY][numberX].setImageDrawable(cursorDrawable);

        }
    }

    public void push(View v){

        // 2層のImageViewの取得
        Drawable drawableImages = imageViews[numberY][numberX].getBackground();
        Drawable backDrawableImages = backImageViews[numberY][numberX].getBackground();

        // ConstantState型の取得
        final Drawable.ConstantState constantStateDrawableGridA = grid.getConstantState();
//        final Drawable.ConstantState constantStateDrawableGridB = backDrawableImages.getConstantState();
//        final Drawable.ConstantState constateStateDrawableGridC = drawableImages.getConstantState();

        final Drawable.ConstantState constantStateDrawableFriendlyCapitalA      = friendlyCapitalDrawable.getConstantState();
        final Drawable.ConstantState constantStateDrawableFriendlyFactoryA      = friendlyFactoryDrawable.getConstantState();
        final Drawable.ConstantState constantStateDrawableFriendlyLighttankA    = friendlyLighttankDrawable.getConstantState();
        final Drawable.ConstantState constantStateDrawableFriendlyInfantryA     = friendlyInfantryDrawable.getConstantState();
        final Drawable.ConstantState constantStateDrawableFriendlyTankkillerA   = friendlyTankkillerDrawable.getConstantState();

        // TODO この２つわからん
        final Drawable.ConstantState constantStateNumber            = drawableImages.getConstantState();
        final Drawable.ConstantState constantStateNumberBack        = backDrawableImages.getConstantState();
        final Drawable.ConstantState constantStateEnemyLighttank    = enemyLighttankDrawable.getConstantState();

        // final Drawable.ConstantState constantStateDrawableFriendlyinfantryA = friendlyinfantryDrawable.getConstantState();
        // final Drawable.ConstantState constantStateDrawableFriendlyinfantryB = DrawableImages.getConstantState();


        if(doing == 0){

            Log.d("number","X:" + numberX + "Y:" + numberY);

            beforeX = numberX;
            beforeY = numberY;

            Log.d("before","X:" + beforeX + "Y:" + beforeY);

            // FriendlyLighttank と 現在の画像との比較
            if(constantStateDrawableFriendlyLighttankA.equals(constantStateNumber)){

               final PopupMenu popup = new PopupMenu(this,setting);

               popup.getMenuInflater().inflate(R.menu.lighttankpopup,popup.getMenu());
               popup.show();
               popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                   public boolean onMenuItemClick(MenuItem item) {
//                       Log.d("popup","onMenuItemClick");
                       if(item.getItemId() == R.id.item01){
                            // 未実装
                       }
                       if(item.getItemId() == R.id.item02){
                           for (int tate = 0; tate < 3; tate++) {
                               for (int yoko = 0; yoko < 7; yoko++) {

                                   int seigenX = 0;
                                   int seigenY = 0;

                                   seigenX = numberX - yoko;
                                   seigenY = numberY - tate;

                                   // 正負反転
                                   if (seigenX <= 0) {
                                       seigenX = seigenX * (-1);
                                   }
                                   if(seigenY <= 0){
                                       seigenY = seigenY * (-1);
                                   }

                                   // 足し合わせて4以上には，darkをかける
                                   if(seigenX + seigenY >= 4){
                                       imageViews[tate][yoko].setImageDrawable(dark);

                                       Log.d("check","dark");
                                       Log.d("position","x : " + tate + "y : " + yoko);

//                                       if(constantStateNumber == constantStateDrawableGridA) {

                                       // 現在のimageViewがgrid(空)なら
                                       if(constantStateNumber.equals(constantStateDrawableGridA)) {
                                           Log.d("障害物なし","(x,y) = (" + tate + "," + yoko + ")");

                                       } else {
                                           //障害物有り
//                                           imageViews[tate][yoko].setImageDrawable(dark);
                                           Log.d("障害物あり","(x,y) = (" + tate + "," + yoko + ")");
                                       }


                                   }
                                   /*
                                   if (numberY + numberX + 3<tate + yoko) {
                                       imageViews[tate][yoko].setImageDrawable(dark);
                                   }
                                   if (numberY + numberX -3>tate+yoko) {
                                       imageViews[tate][yoko].setImageDrawable(dark);
                                   }
                                   */
                                   else {

                                   }
                               }
                           }
                           doing = 1; // モードの切り替え
                           objectID = 0; // TODO objectIDについて聞く

                          //imageViews[0][0];

                           Log.d("動作","移動");

                           for(int tate = 0; tate < 3; tate++){
                               for(int yoko = 0;yoko < 7;yoko++){
                                   //imageViews[tate][yoko] = findViewById(ids[i]);
                               }
                           }

                       }
                       if(item.getItemId() == R.id.item03){
                            // 未実装
                       }
                       return false;
                   }
               });

           } else if(constantStateDrawableFriendlyCapitalA.equals(constantStateNumberBack)) {
               // do something
               // TODO ここの仕組みの把握
               if(constantStateDrawableGridA.equals(constantStateNumberBack)){
                   final PopupMenu popup = new PopupMenu(this, setting);

                   popup.getMenuInflater().inflate(R.menu.capitalpopup, popup.getMenu());
                   popup.show();
                   popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                       public boolean onMenuItemClick(MenuItem item) {

                           if (item.getItemId() == R.id.item01) {

                           }

                           if (item.getItemId() == R.id.item02) {

                               madelandObject = new PopupMenu(MainActivity.this, setting);
                               madelandObject.getMenuInflater().inflate(R.menu.madetanks, madelandObject.getMenu());
                               madelandObject.show();
                               madelandObject.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                                   @Override
                                   public boolean onMenuItemClick(MenuItem Item) {
                                       return true;
                                   }
                               });

                           }

                           if (item.getItemId() == R.id.item03) {

                           }


                           return true;
                       }
                   });

               }else{

               }

           } else if(constantStateDrawableFriendlyFactoryA.equals(constantStateNumber)) {
               // do something
               if(constantStateDrawableGridA == constantStateNumber){

                   final PopupMenu popup = new PopupMenu(this, setting);

                   popup.getMenuInflater().inflate(R.menu.capitalpopup, popup.getMenu());
                   popup.show();
                   popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                       public boolean onMenuItemClick(MenuItem item) {

                           if (item.getItemId() == R.id.item01) {

                           }

                           if (item.getItemId() == R.id.item02) {
                               madelandObject = new PopupMenu(MainActivity.this, setting);

                               madelandObject.getMenuInflater().inflate(R.menu.madetanks, madelandObject.getMenu());
                               madelandObject.show();
                               madelandObject.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                                   @Override
                                   public boolean onMenuItemClick(MenuItem Item) {
                                       return true;
                                   }
                               });

                           }

                           if (item.getItemId() == R.id.item03) {

                           }

                           return true;
                       }
                   });

               }else{

               }

           } else if(constantStateDrawableGridA.equals(constantStateNumberBack)){

               if(constantStateDrawableGridA.equals(constantStateNumberBack)){

                   PopupMenu popup = new PopupMenu(this,setting);

                   popup.getMenuInflater().inflate(R.menu.gridpopup, popup.getMenu());
                   popup.show();
                   popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                       public boolean onMenuItemClick(MenuItem item) {

                           if(item.getItemId() == R.id.item01){

                           }

                           if(item.getItemId() == R.id.item02){

                           }
                           return true;
                       }
                   });
               }

           }

           //i++;

       }
       if(doing == 1){

            moveObject = new PopupMenu(MainActivity.this,setting);
            moveObject.getMenuInflater().inflate(R.menu.move,moveObject.getMenu());
            moveObject.show();

            moveObject.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                @Override
                public boolean onMenuItemClick(MenuItem item) {
                    if (item.getItemId() == R.id.item01) {
                        if (objectID == 0) {
                            int seigenY = 0;
                            int seigenX = 0;
                            seigenY = numberY - beforeY;
                            seigenX = numberX - beforeX;

                            if(seigenY <= 0){
                                seigenY = seigenY * (-1);
                            }
                            if(seigenX <= 0){
                                seigenX = seigenX * (-1);
                            }

                            if (seigenY + seigenX <= 3) {
                                if(constantStateNumber.equals(constantStateDrawableGridA)) {
                                    imageViews[beforeY][beforeX].setBackgroundResource(R.drawable.grid);
                                    imageViews[numberY][numberX].setBackgroundResource(R.drawable.friendly_lighttank);
                                }else{
                                    Toast.makeText(getApplicationContext(), "ユニットと被っています", Toast.LENGTH_SHORT).show();
                                }

                            } else {
                                Toast.makeText(getApplicationContext(), "移動できる距離を超えています!", Toast.LENGTH_SHORT).show();
                            }
                        }

                        if (item.getItemId() == R.id.item02) {

                        }
                    }
                    return true;
                }
            });

            // モード変更
            doing = 0;
            for (int tate = 0; tate < 3; tate++) {
                for (int yoko = 0; yoko < 7; yoko++) {
                    imageViews[tate][yoko].setImageDrawable(null);
                }
            }

            imageViews[numberY][numberX].setImageDrawable(cursorDrawable);

        }
    }
}



