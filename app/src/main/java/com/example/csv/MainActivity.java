package com.example.csv;
import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;

import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;


import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.opencsv.CSVReader;
import com.opencsv.CSVWriter;

import org.apache.commons.beanutils.Converter;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import java.util.ArrayList;
import java.util.List;


public class MainActivity extends AppCompatActivity {


    TextView Id,Name,Number;
    Button Add,Next,Browse;
    Intent myFileIntent;
    String Fpath="",dir="";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
      /*  Id=findViewById(R.id.txtId);
        Name=findViewById(R.id.txtName);
        Number=findViewById(R.id.txtNumber);
        Add=findViewById(R.id.btnAdd);*/
        Next=findViewById(R.id.btnNext);
        Browse=findViewById(R.id.btnBrowse);

       /* Add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(ContextCompat.checkSelfPermission(MainActivity.this, Manifest.permission.WRITE_EXTERNAL_STORAGE)!= PackageManager.PERMISSION_GRANTED)
                {
                    ActivityCompat.requestPermissions(MainActivity.this,new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},0);
                }
                else
                {
                    String ID1= Id.getText().toString();
                    String Name1= Name.getText().toString();
                    String Number1=Number.getText().toString();
                    File file =new File("/sdcard/hridoy");
                    file.mkdir();
                    String csv="/sdcard/hridoy/a.csv";
                    CSVWriter csvWriter= null;
                    try {
                        csvWriter = new CSVWriter(new FileWriter(csv,true));
                        String row[]=new String[]{ID1,Name1,Number1};
                        csvWriter.writeNext(row);
                        csvWriter.close();
                        Toast.makeText(MainActivity.this,"Successfully Created ",Toast.LENGTH_LONG).show();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        });*/

        Next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CSVReader reader = null;
                CSVWriter csvWriter= null;
                String[] row = null;
                String[] FileEx=new String[]{"a","RFD850017166523022161_2019-11-24_15-08-31.954","RFD850017173523020271_2019-12-29_14-53-42.620","RFD850017173523020271_2019-12-29_15-13-35.272"};
                if(ContextCompat.checkSelfPermission(MainActivity.this, Manifest.permission.WRITE_EXTERNAL_STORAGE)!= PackageManager.PERMISSION_GRANTED)
                {
                    ActivityCompat.requestPermissions(MainActivity.this,new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},0);
                }
                else {

                    File f = new File(dir);
                    File file1[] = f.listFiles();

                    for (int i=0; i < file1.length; i++)
                    {
                        //here populate your listview
                        String ttt="FileName:" + file1[i].getName();
                    }
                    for (int i = 0; i < file1.length; i++) {
                        try {
                            reader = new CSVReader(new FileReader(file1[i]));//(dir+"/" + FileEx[i] + ".csv"));  //"/sdcard/hridoy/"
                            List myEntries = reader.readAll();
                            reader.close();

                            File file = new File(dir+"/RetailCSV");   //"/sdcard/hridoy"
                            file.mkdir();
                            String csv =dir+"/RetailCSV/Retail.csv"; // "/sdcard/hridoy/e.csv";
                            try {
                                csvWriter = new CSVWriter(new FileWriter(csv, true));

                                String element[] = null;
                                // int i=0;
                                //while((myEntries.get(i)) != null)
                                {
                                    for (Object object : myEntries) {
                                        row = (String[]) object;

                                        csvWriter.writeNext(row);

                                    }

                                    csvWriter.close();
                                    reader.close();
                                    // i++;
                                    // Toast.makeText(MainActivity.this,   row[0]+""+myEntries.get(i)+""+reader.readNext()+" "+reader.peek(), Toast.LENGTH_LONG).show();
                                }
                            } catch (IOException e) {
                                e.printStackTrace();
                            }


                        } catch (FileNotFoundException e) {
                            e.printStackTrace();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }
                Toast.makeText(MainActivity.this, "Data Added Successfully...", Toast.LENGTH_LONG).show();

            }
        });

        Browse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                myFileIntent = new Intent(Intent.ACTION_GET_CONTENT);
                myFileIntent.setType("*/*");
                startActivityForResult(myFileIntent, 10);

            }
        });
    }
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
       switch (requestCode)
       {

           case 10:
               if(resultCode==RESULT_OK)
               {
                   Uri selectedImage = data.getData();
                   String[] filePathColumn = { MediaStore.Images.Media.DATA };

                   Cursor cursor = getContentResolver().query(selectedImage,filePathColumn, null, null, null);
                   cursor.moveToFirst();

                   int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
                   String picturePath = cursor.getString(columnIndex);

                   cursor.close();
                   System.out.println("picturePath +"+ picturePath );

                   Fpath = data.getDataString();
                   File f=new File(picturePath,"/");
                   String files[]=   f.list();
                   File file = new File(picturePath);
                   file = new File(file.getAbsolutePath());
                   dir = file.getParent();
                   Toast.makeText(MainActivity.this, dir, Toast.LENGTH_LONG).show();

               }
               break;

       }

    }

}