package com.example.kritsana_c.nfcreader2;

import android.app.Activity;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.kritsana_c.nfcreader2.model.History;
import com.example.kritsana_c.nfcreader2.utils.DBHelper;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class UserHistory extends Activity {
    public static final String TAG = "CheckNFC";
    private static final DateFormat TIME_FORMAT = SimpleDateFormat.getDateTimeInstance();

    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;

    DBHelper mHelper;
    List<String> historie;
    String viewHistorie;

    EditText editTextValue;
    TextView textViewHistory;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.history_user);

        mHelper = new DBHelper(this);

        mRecyclerView = (RecyclerView) findViewById(R.id.my_recycler_view);

        // use this setting to improve performance if you know that changes
        // in content do not change the layout size of the RecyclerView
        mRecyclerView.setHasFixedSize(true);

        // use a linear layout manager
        mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);


        String[] myDataset = inintHistory();
        mAdapter = new MyAdapter(myDataset);
        mRecyclerView.setAdapter(mAdapter);

        editTextValue = findViewById(R.id.editTextValue);

        //textViewHistory = findViewById(R.id.textViewHistory);



        Button button_record = findViewById(R.id.button_record);
        button_record.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                AlertDialog.Builder builder =
                        new AlertDialog.Builder(UserHistory.this);
                builder.setTitle(getString(R.string.record_value) +" "+ editTextValue.getText() +" "+ getString(R.string.baht));

                builder.setPositiveButton(getString(android.R.string.ok),
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                History history = new History();

                                String nfcTag_tmp = getIntent().getStringExtra("NFC_TAG").toString().replace(" ", "");
                                String date = TIME_FORMAT.format(new Date()).toString();
                                history.setTagId(editTextValue.getText().toString());
                                history.setValue(nfcTag_tmp);
                                history.setDate(date);

                                editTextValue.setText("");
                                mHelper.addHistory(history);

                                String[] myDataset = inintHistory();

                                mAdapter = new MyAdapter(myDataset);
                                mRecyclerView.setAdapter(mAdapter);
                            }
                        });

                builder.setNegativeButton(getString(android.R.string.cancel),
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.cancel();
                            }
                        });


                builder.show();

            }

        });



        //inintHistory();
    }


    private String[] inintHistory(){
        List<String> historylist = mHelper.getHistoryList();
        List<String> tmp = new ArrayList<String>();
        for(int j = historylist.size()-1; j >= 0; j--){
            String i = historylist.get(j);
            String[] str = i.split("/");
//            Log.d(TAG, "inintHistory "+ j +":"+ historylist.get(j));
//            Log.d(TAG, "inintHistory "+ j +":"+ str.length);
            if( str[1].equals(getIntent().getStringExtra("NFC_TAG").toString().replace(" ", ""))) {
                tmp.add("ลำดับที่ " + str[0] + " จำนวน " + str[2] + " บาท "+ str[3]);
            }
        }


        String[] myDataset = new String[tmp.size()];

        myDataset = tmp.toArray(myDataset);
        mAdapter = new MyAdapter(myDataset);
        mRecyclerView.setAdapter(mAdapter);

        return myDataset;
    }
}
