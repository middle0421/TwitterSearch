package com.example.user.twittersearch;

import android.content.SharedPreferences;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;

import java.util.ArrayList;


public class MainActivity extends AppCompatActivity {
    private ListView listView;
    private EditText tag;
    private EditText context;
    private ImageButton Save;
    private static final String TagQueryFile = "TagQueryFile";
    private SharedPreferences sharedFile;
    private ArrayList<String> tags;
    private ArrayAdapter<String> arrayAdapter;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        listView = (ListView) findViewById(R.id.listView);
        tag = (EditText) findViewById(R.id.tag);
        context = (EditText) findViewById(R.id.content);
        Save= (ImageButton) findViewById(R.id.Save);

        myOnItemClickListener itemClickListener = new myOnItemClickListener();
        listView.setOnItemClickListener(itemClickListener);

        OnClickHandler clickHandler =new OnClickHandler();
        Save.setOnClickListener(clickHandler);

        sharedFile = getSharedPreferences(TagQueryFile, MODE_PRIVATE);
        tags = new ArrayList<String>(sharedFile.getAll().keySet());
        arrayAdapter = new ArrayAdapter<String>(this, R.layout.list_item,tags);
        listView.setAdapter(arrayAdapter);



    }
    public void AddTaggedSearch(String query, String tag) {

        SharedPreferences.Editor editor = sharedFile.edit();
        editor.putString(tag,query);
        editor.apply();

        if(! tags.contains(tag)){
            tags.add(tag);
            arrayAdapter.notifyDataSetChanged();
        }




    }


    public class OnClickHandler implements View.OnClickListener{


        public void onClick(View v) {
            String queryStr = context.getText().toString();
            String tagStr = tag.getText().toString();

            if (queryStr.length() > 0 && tagStr.length() > 0) {
                AddTaggedSearch(queryStr, tagStr);
                tag.setText("");
                context.setText("");
            }else {
                AlertDialog.Builder builder = new 	AlertDialog.Builder(MainActivity.this);
                builder.setTitle("Missing Message");
                builder.setMessage(R.string.missingMessage);
                builder.setNegativeButton(R.string.Cancel, null);
                AlertDialog dialog = builder.create();
                dialog.show();
            }// End of if-condition 
        }
    }//end of handler

    public class myOnItemClickListener implements AdapterView.OnItemClickListener{
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

        }
    }// End of onItemClick method    }// End of myOnItemClickHandler Class
}




