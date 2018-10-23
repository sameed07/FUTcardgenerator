package com.example.sameedshah.futcardgenerator;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Typeface;
import android.media.MediaScannerConnection;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.InputType;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.InterstitialAd;
import com.google.android.gms.ads.MobileAds;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Random;

public class MainActivity extends AppCompatActivity {

    TextView txtPlayerName, txtPos, txtPac, txtSho, txtPas, txtDri, txtDef, txtPhy, txtpos2;
    ImageView playerImage, flagImage, leagueImage,downloadImage,addCard,shareCard,testImage;
    private static final int REQUEST = 1;
    private static final int FLAG_REQUEST = 21;
    private static final int LEAGUE_REQUEST = 31;

    LinearLayout layout;
    LinearLayout cardLayout;


    Bitmap b;
    Uri uri;

    private AdView mAdView;
    InterstitialAd interstitialAd;

    private String CardName;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        txtPlayerName = findViewById(R.id.playerName);
        txtPos = findViewById(R.id.txtPos);
        txtPac = findViewById(R.id.txtPac);
        txtSho = findViewById(R.id.txtSho);
        txtPas = findViewById(R.id.txtPas);
        txtpos2 = findViewById(R.id.pos);
        txtDri = findViewById(R.id.txtDri);
        txtDef = findViewById(R.id.txtDef);
        txtPhy = findViewById(R.id.txtPhy);

        playerImage = findViewById(R.id.profileImage);
        flagImage = findViewById(R.id.flag);
        leagueImage = findViewById(R.id.league);
        downloadImage = findViewById(R.id.downloadImage);
        addCard = findViewById(R.id.addCard);
        shareCard = findViewById(R.id.shareImage);

        cardLayout = findViewById(R.id.card_layout);

        interstitialAd = new InterstitialAd(MainActivity.this);
        interstitialAd.setAdUnitId("ca-app-pub-8355474163260431/3950732736");
        interstitialAd.loadAd(new AdRequest.Builder().addTestDevice("8248E48BF711EBFDDBA9861C50FF85B7").build());


        fonts();
        //cardDialog();
        ads();


        shareCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                interstitialAd.setAdListener(new AdListener() {
                                                 @Override
                                                 public void onAdClosed() {
                                                     interstitialAd.loadAd(new AdRequest.Builder().build());
                                                     shareImage();
                                                 }
                                             }

                );

                if (interstitialAd.isLoaded()) {
                    interstitialAd.show();

                } else {
                    shareImage();
                }
            }
        });

        addCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                    cardDialog();

            }
        });
        downloadImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                interstitialAd.setAdListener(new AdListener() {
                                                 @Override
                                                 public void onAdClosed() {
                                                     interstitialAd.loadAd(new AdRequest.Builder().build());
                                                     downaloadImage();
                                                 }
                                             }

                );

                if (interstitialAd.isLoaded()) {
                    interstitialAd.show();

                } else {
                    downaloadImage();
                }

            }
        });

        flagImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
            chooseFlag();
            }
        });

        leagueImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                chooseLeague();
            }
        });

        onClicks();


    }

    private void shareImage() {


        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.setType("text/plain");
        intent.putExtra(Intent.EXTRA_TEXT, "https://play.google.com/store/apps/details?id=com.futcardgenerator");
        startActivity(Intent.createChooser(intent, "Share App"));

    }

    public void alertDialog(final TextView text) {

        final AlertDialog.Builder alertDialog = new AlertDialog.Builder(this);

        final EditText edtAddress = new EditText(MainActivity.this);
        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(

                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.MATCH_PARENT

        );

        edtAddress.setLayoutParams(lp);
        edtAddress.setInputType(InputType.TYPE_CLASS_NUMBER);
        alertDialog.setView(edtAddress);


        alertDialog.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

                text.setText(edtAddress.getText().toString());

            }
        });
        alertDialog.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                text.setText(text.getText().toString());
                dialogInterface.dismiss();
            }
        });
        alertDialog.show();

    }
    public void alertNameDialog(final TextView text) {

        final AlertDialog.Builder alertDialog = new AlertDialog.Builder(this);

        final EditText edtAddress = new EditText(MainActivity.this);
        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(

                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.MATCH_PARENT

        );

        edtAddress.setLayoutParams(lp);

        alertDialog.setView(edtAddress);


        alertDialog.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

                text.setText(edtAddress.getText().toString());

            }
        });
        alertDialog.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                text.setText(text.getText().toString());
                dialogInterface.dismiss();
            }
        });
        alertDialog.show();

    }

    public void onClicks() {


        //set on click to everything

        txtPlayerName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                alertNameDialog(txtPlayerName);
            }
        });

        txtPos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                alertDialog(txtPos);
            }
        });
        txtpos2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                alertNameDialog(txtpos2);
            }
        });

        txtPac.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                alertDialog(txtPac);
            }
        });

        txtSho.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
             ;
                alertDialog(txtSho);
            }
        });

        txtPas.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                alertDialog(txtPas);
            }
        });

        txtDri.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                alertDialog(txtDri);
            }
        });

        txtDef.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                alertDialog(txtDef);
            }
        });

        txtPhy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                alertDialog(txtPhy);
            }
        });

        playerImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                chooseImage();
            }
        });


    }

    private void chooseImage() {

        Intent intGallery = new Intent(Intent.ACTION_GET_CONTENT);// get access to gallery
        intGallery.setType("image/*"); //get all images in gallery
        startActivityForResult(intGallery, REQUEST);

    }
    private void chooseFlag() {

        Intent intGallery = new Intent(Intent.ACTION_GET_CONTENT);// get access to gallery
        intGallery.setType("image/*"); //get all images in gallery
        startActivityForResult(intGallery, FLAG_REQUEST);

    }
    private void chooseLeague() {

        Intent intGallery = new Intent(Intent.ACTION_GET_CONTENT);// get access to gallery
        intGallery.setType("image/*"); //get all images in gallery
        startActivityForResult(intGallery, LEAGUE_REQUEST);

    }



    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if (data != null) { // if  request code is equl to    GAL_CON then then,
            // selected image from gallery will be set to out imageview
            if (requestCode == REQUEST && resultCode == RESULT_OK) {
                Uri path = data.getData();
                try {

                    Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), path);

                    playerImage.setImageBitmap(bitmap);

                } catch (FileNotFoundException e) {
                    Toast.makeText(this, ""+e, Toast.LENGTH_SHORT).show();
                } catch (IOException e) {
                    Toast.makeText(this, ""+e, Toast.LENGTH_SHORT).show();
                }
            } else if (requestCode == FLAG_REQUEST && resultCode == RESULT_OK) {
                Uri path = data.getData();
                try {

                    Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), path);

                    flagImage.setImageBitmap(bitmap);

                } catch (FileNotFoundException e) {
                    Toast.makeText(this, ""+e, Toast.LENGTH_SHORT).show();
                } catch (IOException e) {
                    Toast.makeText(this, ""+e, Toast.LENGTH_SHORT).show();
                }
            }else   if (requestCode == LEAGUE_REQUEST && resultCode == RESULT_OK) {
                Uri path = data.getData();
                try {

                    Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), path);

                    leagueImage.setImageBitmap(bitmap);

                } catch (FileNotFoundException e) {
                    Toast.makeText(this, ""+e, Toast.LENGTH_SHORT).show();
                } catch (IOException e) {
                    Toast.makeText(this, ""+e, Toast.LENGTH_SHORT).show();
                }
            }

        }
    }


   public void downaloadImage(){

   //  ActivityCompat.requestPermissions(MainActivity.this,new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},REQUEST);
      String fileName = "test.jpg";

    layout = (LinearLayout)findViewById(R.id.mainLayout);
    //testImage = (ImageView)findViewById(R.id.testImage);

    layout.setDrawingCacheEnabled(true);
    // this is the important code :)
    // Without it the view will have a dimension of 0,0 and the bitmap will be null

    layout.measure(View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED),
            View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED));

    layout.layout(0, 0, layout.getMeasuredWidth(), layout.getMeasuredHeight());

    layout.buildDrawingCache(true);
    b = Bitmap.createBitmap(layout.getDrawingCache());
    saveImageToExternalStorage(b);

  }

    public void saveImageToExternalStorage(Bitmap finalBitmap) {
        String root = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES).toString();
        File myDir = new File(root + "/saved_images_1");
        myDir.mkdirs();
        Random generator = new Random();
        int n = 10000;
        n = generator.nextInt(n);
        String fname = "Image-" + n + ".png";
        File file = new File(myDir, fname);
        if (file.exists())
            file.delete();
        try {
            FileOutputStream out = new FileOutputStream(file);
            finalBitmap.compress(Bitmap.CompressFormat.PNG, 90, out);
            out.flush();
            out.close();
        }
        catch (Exception e) {
            e.printStackTrace();
        }


        // Tell the media scanner about the new file so that it is
        // immediately available to the user.
        MediaScannerConnection.scanFile(this, new String[]{file.toString()}, null,
                new MediaScannerConnection.OnScanCompletedListener() {
                    public void onScanCompleted(String path, Uri uri) {
                        Log.i("ExternalStorage", "Scanned " + path + ":");
                        Log.i("ExternalStorage", "-> uri=" + uri);
                    }
                });
         Toast.makeText(this, "Saved", Toast.LENGTH_SHORT).show();

    }


    public void cardDialog(){

        final AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Select Card");
        builder.setCancelable(true);
        builder.setPositiveButton("Ok",null);

        LayoutInflater inflater = (LayoutInflater) this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.custom_alert_dialog,null);

        ListView card_list = view.findViewById(R.id.card_list);
        card_list.setAdapter(new CardAdapter(this));
        card_list.setOnItemClickListener(new AdapterView.OnItemClickListener() {


            @SuppressLint("ResourceAsColor")
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                switch (i) {

                    case 0: {


                        cardLayout.setBackgroundResource(R.drawable.gold_card);
                        txtPos.setTextColor(R.color.gold_card_color);
                        txtpos2.setTextColor(R.color.gold_card_color);
                        txtPlayerName.setTextColor(R.color.gold_card_color);
                        txtDef.setTextColor(R.color.gold_card_color);
                        txtDri.setTextColor(R.color.gold_card_color);
                        txtPac.setTextColor(R.color.gold_card_color);
                        txtPas.setTextColor(R.color.gold_card_color);
                        txtSho.setTextColor(R.color.gold_card_color);
                        txtPhy.setTextColor(R.color.gold_card_color);

                        break;


                    }
                    case 1: {


                        int silver = Color.parseColor("#4d4d4d");
                        cardLayout.setBackgroundResource(R.drawable.silver_card);
                        txtPos.setTextColor(silver);
                        txtpos2.setTextColor(silver);
                        txtPlayerName.setTextColor(silver);
                        txtDef.setTextColor(silver);
                        txtDri.setTextColor(silver);
                        txtPac.setTextColor(silver);
                        txtPas.setTextColor(silver);
                        txtSho.setTextColor(silver);
                        txtPhy.setTextColor(silver);

                        break;
                    }
                    case 2: {

                        cardLayout.setBackgroundResource(R.drawable.bronze_card);
                        txtPos.setTextColor(R.color.bronze_card_color);
                        txtpos2.setTextColor(R.color.bronze_card_color);
                        txtPlayerName.setTextColor(R.color.bronze_card_color);
                        txtDef.setTextColor(R.color.bronze_card_color);
                        txtDri.setTextColor(R.color.bronze_card_color);
                        txtPac.setTextColor(R.color.bronze_card_color);
                        txtPas.setTextColor(R.color.bronze_card_color);
                        txtSho.setTextColor(R.color.bronze_card_color);
                        txtPhy.setTextColor(R.color.bronze_card_color);

                        break;
                    }
                    case 3: {

                        int totw_gold = Color.parseColor("#f0dca0");
                        cardLayout.setBackgroundResource(R.drawable.totw_gold_card);
                        txtPos.setTextColor(totw_gold);
                        txtPlayerName.setTextColor(totw_gold);
                        txtPos.setTextColor(totw_gold);
                        txtpos2.setTextColor(totw_gold);
                        txtPlayerName.setTextColor(totw_gold);
                        txtDef.setTextColor(totw_gold);
                        txtDri.setTextColor(totw_gold);
                        txtPac.setTextColor(totw_gold);
                        txtPas.setTextColor(totw_gold);
                        txtSho.setTextColor(totw_gold);
                        txtPhy.setTextColor(totw_gold);

                        break;
                    }
                    case 4: {
                        int totw_silver = Color.parseColor("#d7d7d7");
                        cardLayout.setBackgroundResource(R.drawable.totw_silver_card);
                       txtPos.setTextColor(totw_silver);
                        txtPlayerName.setTextColor(totw_silver);
                        txtPos.setTextColor(totw_silver);
                        txtpos2.setTextColor(totw_silver);
                        txtPlayerName.setTextColor(totw_silver);
                        txtDef.setTextColor(totw_silver);
                        txtDri.setTextColor(totw_silver);
                        txtPac.setTextColor(totw_silver);
                        txtPas.setTextColor(totw_silver);
                        txtSho.setTextColor(totw_silver);
                        txtPhy.setTextColor(totw_silver);

                        break;
                    }
                    case 5: {
                         int totw_bronze = Color.parseColor("#c88c78");
                        cardLayout.setBackgroundResource(R.drawable.totw_bronze_card);

                        txtPos.setTextColor(totw_bronze);
                        txtPlayerName.setTextColor(totw_bronze);
                        txtPos.setTextColor(totw_bronze);
                        txtpos2.setTextColor(totw_bronze);
                        txtPlayerName.setTextColor(totw_bronze);
                        txtDef.setTextColor(totw_bronze);
                        txtDri.setTextColor(totw_bronze);
                        txtPac.setTextColor(totw_bronze);
                        txtPas.setTextColor(totw_bronze);
                        txtSho.setTextColor(totw_bronze);
                        txtPhy.setTextColor(totw_bronze);

                        break;
                    }
                    case 6: {

                        int totw_bronze = Color.parseColor("#FFFFFF");
                        cardLayout.setBackgroundResource(R.drawable.champions_card);

                        txtPos.setTextColor(totw_bronze);
                        txtPlayerName.setTextColor(totw_bronze);
                        txtPos.setTextColor(totw_bronze);
                        txtpos2.setTextColor(totw_bronze);
                        txtPlayerName.setTextColor(totw_bronze);
                        txtDef.setTextColor(totw_bronze);
                        txtDri.setTextColor(totw_bronze);
                        txtPac.setTextColor(totw_bronze);
                        txtPas.setTextColor(totw_bronze);
                        txtSho.setTextColor(totw_bronze);
                        txtPhy.setTextColor(totw_bronze);

                        break;
                    }
                    case 7: {

                        int totw_bronze = Color.parseColor("#544111");
                        cardLayout.setBackgroundResource(R.drawable.icon_empty_card);

                        txtPos.setTextColor(totw_bronze);
                        txtPlayerName.setTextColor(totw_bronze);
                        txtPos.setTextColor(totw_bronze);
                        txtpos2.setTextColor(totw_bronze);
                        txtPlayerName.setTextColor(totw_bronze);
                        txtDef.setTextColor(totw_bronze);
                        txtDri.setTextColor(totw_bronze);
                        txtPac.setTextColor(totw_bronze);
                        txtPas.setTextColor(totw_bronze);
                        txtSho.setTextColor(totw_bronze);
                        txtPhy.setTextColor(totw_bronze);


                        break;
                    }
                    case 8: {
                        int totw_bronze = Color.parseColor("#F72195");
                        cardLayout.setBackgroundResource(R.drawable.otw_card);

                        txtPos.setTextColor(totw_bronze);
                        txtPlayerName.setTextColor(totw_bronze);
                        txtPos.setTextColor(totw_bronze);
                        txtpos2.setTextColor(totw_bronze);
                        txtPlayerName.setTextColor(totw_bronze);
                        txtDef.setTextColor(totw_bronze);
                        txtDri.setTextColor(totw_bronze);
                        txtPac.setTextColor(totw_bronze);
                        txtPas.setTextColor(totw_bronze);
                        txtSho.setTextColor(totw_bronze);
                        txtPhy.setTextColor(totw_bronze);

                        break;
                    }
                    case 9: {

                        int totw_bronze = Color.parseColor("#FFFFFF");
                        cardLayout.setBackgroundResource(R.drawable.europa_league);

                        txtPos.setTextColor(totw_bronze);
                        txtPlayerName.setTextColor(totw_bronze);
                        txtPos.setTextColor(totw_bronze);
                        txtpos2.setTextColor(totw_bronze);
                        txtPlayerName.setTextColor(totw_bronze);
                        txtDef.setTextColor(totw_bronze);
                        txtDri.setTextColor(totw_bronze);
                        txtPac.setTextColor(totw_bronze);
                        txtPas.setTextColor(totw_bronze);
                        txtSho.setTextColor(totw_bronze);
                        txtPhy.setTextColor(totw_bronze);

                        break;
                    }
                    case 10: {

                        int totw_bronze = Color.parseColor("#9ad23a");
                        cardLayout.setBackgroundResource(R.drawable.award_winner);

                        txtPos.setTextColor(totw_bronze);
                        txtPlayerName.setTextColor(totw_bronze);
                        txtPos.setTextColor(totw_bronze);
                        txtpos2.setTextColor(totw_bronze);
                        txtPlayerName.setTextColor(totw_bronze);
                        txtDef.setTextColor(totw_bronze);
                        txtDri.setTextColor(totw_bronze);
                        txtPac.setTextColor(totw_bronze);
                        txtPas.setTextColor(totw_bronze);
                        txtSho.setTextColor(totw_bronze);
                        txtPhy.setTextColor(totw_bronze);

                        break;
                    }
                    case 11: {

                        int totw_bronze = Color.parseColor("#c0af69");
                        cardLayout.setBackgroundResource(R.drawable.fut_champ);

                        txtPos.setTextColor(totw_bronze);
                        txtPlayerName.setTextColor(totw_bronze);
                        txtPos.setTextColor(totw_bronze);
                        txtpos2.setTextColor(totw_bronze);
                        txtPlayerName.setTextColor(totw_bronze);
                        txtDef.setTextColor(totw_bronze);
                        txtDri.setTextColor(totw_bronze);
                        txtPac.setTextColor(totw_bronze);
                        txtPas.setTextColor(totw_bronze);
                        txtSho.setTextColor(totw_bronze);
                        txtPhy.setTextColor(totw_bronze);

                        break;
                    }
                    case 12: {

                        int totw_bronze = Color.parseColor("#cfbcfb");
                        cardLayout.setBackgroundResource(R.drawable.hero);

                        txtPos.setTextColor(totw_bronze);
                        txtPlayerName.setTextColor(totw_bronze);
                        txtPos.setTextColor(totw_bronze);
                        txtpos2.setTextColor(totw_bronze);
                        txtPlayerName.setTextColor(totw_bronze);
                        txtDef.setTextColor(totw_bronze);
                        txtDri.setTextColor(totw_bronze);
                        txtPac.setTextColor(totw_bronze);
                        txtPas.setTextColor(totw_bronze);
                        txtSho.setTextColor(totw_bronze);
                        txtPhy.setTextColor(totw_bronze);

                        break;
                    }
                    case 13: {

                        int totw_bronze = Color.parseColor("#86e000");
                        cardLayout.setBackgroundResource(R.drawable.record_breaker);

                        txtPos.setTextColor(totw_bronze);
                        txtPlayerName.setTextColor(totw_bronze);
                        txtPos.setTextColor(totw_bronze);
                        txtpos2.setTextColor(totw_bronze);
                        txtPlayerName.setTextColor(totw_bronze);
                        txtDef.setTextColor(totw_bronze);
                        txtDri.setTextColor(totw_bronze);
                        txtPac.setTextColor(totw_bronze);
                        txtPas.setTextColor(totw_bronze);
                        txtSho.setTextColor(totw_bronze);
                        txtPhy.setTextColor(totw_bronze);

                        break;
                    }
                    case 14: {

                        int totw_bronze = Color.parseColor("#ff62e5");
                        cardLayout.setBackgroundResource(R.drawable.premium_sbc);

                        txtPos.setTextColor(totw_bronze);
                        txtPlayerName.setTextColor(totw_bronze);
                        txtPos.setTextColor(totw_bronze);
                        txtpos2.setTextColor(totw_bronze);
                        txtPlayerName.setTextColor(totw_bronze);
                        txtDef.setTextColor(totw_bronze);
                        txtDri.setTextColor(totw_bronze);
                        txtPac.setTextColor(totw_bronze);
                        txtPas.setTextColor(totw_bronze);
                        txtSho.setTextColor(totw_bronze);
                        txtPhy.setTextColor(totw_bronze);

                        break;
                    }
                    case 15: {

                        int totw_bronze = Color.parseColor("#ffffff");
                        cardLayout.setBackgroundResource(R.drawable.pro_player);

                        txtPos.setTextColor(totw_bronze);
                        txtPlayerName.setTextColor(totw_bronze);
                        txtPos.setTextColor(totw_bronze);
                        txtpos2.setTextColor(totw_bronze);
                        txtPlayerName.setTextColor(totw_bronze);
                        txtDef.setTextColor(totw_bronze);
                        txtDri.setTextColor(totw_bronze);
                        txtPac.setTextColor(totw_bronze);
                        txtPas.setTextColor(totw_bronze);
                        txtSho.setTextColor(totw_bronze);
                        txtPhy.setTextColor(totw_bronze);

                        break;
                    }

                }
            }
        });

        builder.setView(view);

        AlertDialog dialog = builder.create();
        dialog.show();

    }

    public void popUpDialog(){

        final AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Follow us");
        builder.setCancelable(true);
        builder.setMessage("weekly updates with new cards, follow us on instagram.... to dont lose anything!");

        builder.setPositiveButton("Cancel",null);

        builder.show();

//        final AlertDialog.Builder alertDialog = new AlertDialog.Builder(this);
//
//        final TextView edtAddress = new TextView(MainActivity.this);
//        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(
//
//                LinearLayout.LayoutParams.MATCH_PARENT,
//                LinearLayout.LayoutParams.MATCH_PARENT
//
//        );
//
//        edtAddress.setLayoutParams(lp);
//        alertDialog.setView(edtAddress);
//        edtAddress.setText(text);
//
//
//        alertDialog.setPositiveButton("cancel", new DialogInterface.OnClickListener() {
//            @Override
//            public void onClick(DialogInterface dialogInterface, int i) {
//
//
//
//
//            }
//        });
//
//        alertDialog.show();
    }

    public void ads(){
        MobileAds.initialize(this, "ca-app-pub-8355474163260431~4194275317");
        mAdView = findViewById(R.id.mAdview);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);

    }



    public void fonts(){
        Typeface typeface = Typeface.createFromAsset(getAssets(), "fonts/din.ttf");
        txtPos.setTypeface(typeface);
        txtpos2.setTypeface(typeface);
        txtPlayerName.setTypeface(typeface);
        txtPac.setTypeface(typeface);
        txtSho.setTypeface(typeface);
        txtPas.setTypeface(typeface);
        txtDri.setTypeface(typeface);
        txtDef.setTypeface(typeface);
        txtPhy.setTypeface(typeface);
    }



}