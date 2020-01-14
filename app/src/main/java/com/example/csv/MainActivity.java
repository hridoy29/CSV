package com.example.csv;
import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.opencsv.CSVReader;
import com.opencsv.CSVWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;


public class MainActivity extends AppCompatActivity {


    TextView Id,Name,Number;
    Button Add,Next;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Id=findViewById(R.id.txtId);
        Name=findViewById(R.id.txtName);
        Number=findViewById(R.id.txtNumber);
        Add=findViewById(R.id.btnAdd);
        Next=findViewById(R.id.btnNext);
        Add.setOnClickListener(new View.OnClickListener() {
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
                    String csv="/sdcard/hridoy/d.csv";
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
        });

Next.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {
        CSVReader reader = null;
        try {
            reader = new CSVReader(new FileReader("/sdcard/hridoy/d.csv"));
            List myEntries = reader.readAll();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
});


    }


}