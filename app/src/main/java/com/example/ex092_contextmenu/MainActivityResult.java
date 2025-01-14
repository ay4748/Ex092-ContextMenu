package com.example.ex092_contextmenu;

import android.content.Intent;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivityResult extends AppCompatActivity implements AdapterView.OnItemLongClickListener,View.OnCreateContextMenuListener,AdapterView.OnItemClickListener {

    ListView lV;
    TextView tVPos;
    boolean isHeshbonit;
    float numFirst, numMana;
    float[] dodge = new float[20];
    String[] dodgeString = new String[20];
    int position;






    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_result);

        Intent gi = getIntent();
        isHeshbonit = gi.getBooleanExtra("isHeshbonit",false);

        numFirst = gi.getFloatExtra("numFirst",0);
        numMana = gi.getFloatExtra("numMana",0);
        tVPos = findViewById(R.id.tVPos);
        lV = findViewById(R.id.lV);

        lV.setOnItemLongClickListener(this);
        lV.setOnItemClickListener(this);

        lV.setChoiceMode(ListView.CHOICE_MODE_SINGLE);


        if(isHeshbonit)
        {
            heshboint(numFirst, numMana);
        }
        else
        {
            handasit(numFirst,numMana);
        }


        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item, dodgeString);
        lV.setAdapter(adapter);
        lV.setOnCreateContextMenuListener(this);
    }


    private void heshboint(float numFirst,float numMana)
    {
        for(int i = 1; i < 21; i++)
        {
            dodge[i-1] = numFirst + (i - 1) * numMana;

        }

        for(int i = 0; i < 20; i++)
        {
            if(dodge[i] > 1000000 || dodge[i] < -1000000)
            {
                dodgeString[i] = bigNumSimplifier(dodge[i]);
            }
            else{
                dodgeString[i] = String.format("%.4f", dodge[i]);
            }
        }
    }

    private void handasit(float numFirst,float numMana)
    {

        for(int i = 1; i < 21; i++)
        {
            dodge[i-1] = (float)(numFirst * Math.pow(numMana, i - 1));
        }

        for(int i = 0; i < 20; i++)
        {
            if(dodge[i] > 1000000 || dodge[i] < -1000000)
            {
                dodgeString[i] = bigNumSimplifier(dodge[i]);
            }
            else{
                dodgeString[i] = String.format("%.4f", dodge[i]);
            }
        }
    }




    public String bigNumSimplifier(double value)
    {
        String scientificNotation = String.format("%.4e", value);
        String[] parts = scientificNotation.split("e");
        double base = Double.parseDouble(parts[0]) / 10.0;
        int exponent = Integer.parseInt(parts[1]) + 1;

        return String.format("%.4f * 10^%d", base, exponent);
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo)
    {
        menu.add("n");
        menu.add("Sn");
    }

    private void initViews() {
        lV = findViewById(R.id.lV);
    }

    public void back(View view) {
        finish();
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int pos, long id)
    {
        position = pos ;
    }

    @Override
    public boolean onItemLongClick(AdapterView<?> parent, View view, int pos, long id) {
        position = pos ;

        return false;
    }

    public boolean onContextItemSelected (MenuItem item)
    {

        String Do = item.getTitle().toString();

        if(Do.equals("n"))
        {
            position++;
            tVPos.setText(position + "");
        }
        else if(Do.equals("Sn"))
        {
            int sum =  0;
            for(int i = 0; i <= position ; i++)
            {
                sum += dodge[i];
            }
            tVPos.setText(sum + "");
        }

        return super.onContextItemSelected(item);
    }

}