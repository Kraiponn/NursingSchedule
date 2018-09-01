package com.ksntechnology.nursingschedule.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

import com.ksntechnology.nursingschedule.R;
import com.ksntechnology.nursingschedule.fragment.EditingFragment;

public class EditingActivity extends AppCompatActivity
        implements EditingFragment.setOnEditItemClickListener,
                    EditingFragment.setOnReloadUpdateDataListener{

    private Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editing);

        initInstance();
        if (savedInstanceState == null) {
            intent = getIntent();
            int id = intent.getIntExtra("id", 0);
            /*Toast.makeText(this,
                "Select Id " + id,
                Toast.LENGTH_SHORT).show();*/

            getSupportFragmentManager().beginTransaction()
                    .add(R.id.contentEdit_Container,
                            EditingFragment.newInstance(id))
                    .commit();
        }
    }

    private void initInstance() {
        /*getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);*/
    }

    /*@Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
            overridePendingTransition(
                    R.anim.slide_in_left,
                    R.anim.slide_out_right
            );
            return true;
        }
        return super.onOptionsItemSelected(item);
    }*/

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
        overridePendingTransition(
                R.anim.slide_in_left,
                R.anim.slide_out_right
        );
    }


    @Override
    public void onEditItemClicked() {
        /*Toast.makeText(this,
                "On Back Success", Toast.LENGTH_SHORT).show();*/
        onBackPressed();
    }

    @Override
    public void onReloadUpdateData() {
        onBackPressed();
    }

}
