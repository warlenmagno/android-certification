package com.google.developer.bugmaster;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.google.developer.bugmaster.data.DatabaseManager;
import com.google.developer.bugmaster.data.Insect;
import com.google.developer.bugmaster.data.InsectContract;
import com.google.developer.bugmaster.data.InsectRecyclerAdapter;

import java.util.ArrayList;

import static com.google.developer.bugmaster.QuizActivity.EXTRA_ANSWER;
import static com.google.developer.bugmaster.QuizActivity.EXTRA_INSECTS;

public class MainActivity extends AppCompatActivity implements
        View.OnClickListener {

    private RecyclerView mRecyclerView;
    private InsectRecyclerAdapter mAdapter;
    private ArrayList<Insect> mInsects = new ArrayList<Insect>();
    private Cursor mCursor;
    private int mOrder;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(this);

        mRecyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
        mRecyclerView.setHasFixedSize(true);

        if (null != savedInstanceState){
            mOrder = savedInstanceState.getInt("sort");
        } else {
            mOrder = 0;
        }

        if (mOrder == 0){
            mCursor =
                    DatabaseManager.getInstance(this)
                            .queryAllInsects(InsectContract.InsectEntry.COLUMN_DANGER_LEVEL + " ASC ");
        } else {
            mCursor = DatabaseManager.getInstance(this)
                    .queryAllInsects(InsectContract.InsectEntry.COLUMN_DANGER_LEVEL + " DESC ");
        }
        mAdapter = new InsectRecyclerAdapter(this, mCursor);
        mRecyclerView.setAdapter(mAdapter);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        outState.putInt("sort", mOrder);
        super.onSaveInstanceState(outState);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_sort:
                //TODO: Implement the sort action
                if (mOrder == 0){
                    mCursor =
                            DatabaseManager.getInstance(this)
                                           .queryAllInsects(InsectContract.InsectEntry.COLUMN_DANGER_LEVEL + " DESC ");
                    mOrder = 1;
                } else {
                    mCursor = DatabaseManager.getInstance(this)
                            .queryAllInsects(InsectContract.InsectEntry.COLUMN_DANGER_LEVEL + " ASC ");
                    mOrder = 0;
                }
                mAdapter.setIsectsData(mCursor);
                return true;
            case R.id.action_settings:
                Intent intent = new Intent(this, SettingsActivity.class);
                startActivity(intent);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    /* Click events in Floating Action Button */
    @Override
    public void onClick(View v) {
        //TODO: Launch the quiz activity
        Intent intent = new Intent(this, QuizActivity.class);
//        intent.putParcelableArrayListExtra(EXTRA_INSECTS, mInsects);
//        intent.putExtra(EXTRA_ANSWER, mInsects.get(14)); //troca essa posiçao fisica aqui.
        intent.putParcelableArrayListExtra(EXTRA_INSECTS, null);
        intent.putExtra(EXTRA_ANSWER, 0); //troca essa posiçao fisica aqui.
        startActivity(intent);
    }
}
