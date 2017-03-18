package com.adurcup.adurcupseller.activity;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.adurcup.adurcupseller.R;
import com.adurcup.adurcupseller.adapter.OrderProductAdapter;
import com.adurcup.adurcupseller.adapter.ProductAdapter;
import com.adurcup.adurcupseller.misc.DownloadImageTask;
import com.adurcup.adurcupseller.misc.OrderPendingModel;
import com.adurcup.adurcupseller.misc.ProductModel;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

import static android.R.attr.format;
import static com.adurcup.adurcupseller.R.id.order;
import static com.adurcup.adurcupseller.R.id.product;

public class OrderDescriptionActivity extends AppCompatActivity {

    private OrderPendingModel order;
    private TextView timerTextView;
    private CounterClass timer;
    long remainMilli = 0;
    boolean isRunning=false;
    private RecyclerView recyclerView;
    private OrderProductAdapter adapter;
    private List<ProductModel> productList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_description);

        Intent i = getIntent();
        order = (OrderPendingModel) i.getSerializableExtra("OrderObject");

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        toolbar.setTitle("");
        toolbar.setSubtitle("");
        TextView mTitle = (TextView) toolbar.findViewById(R.id.toolbar_title);
        mTitle.setTypeface(Typeface.DEFAULT_BOLD);
        mTitle.setText(order.getOrder_number());

        ((TextView)findViewById(R.id.order_dscp_date)).setText(order.getOrder_date());
        ((TextView)findViewById(R.id.order_dscp_from)).setText(order.getOrder_from());
        ((TextView)findViewById(R.id.order_dscp_number)).setText(order.getOrder_number());

        for(int j=0; j< order.getProducts().size(); j++)
            order.getProducts().get(j).setChecked(false);

        SimpleDateFormat format = new SimpleDateFormat("HH:mm:ss");
        String time1 = order.getOrder_time();
        String time2 = format.format(Calendar.getInstance().getTime());


        Date date1 = null;
        try {
            date1 = format.parse(time1);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        Date date2 = null;
        try {
            date2 = format.parse(time2);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        long diff = Math.max(0,(date1.getTime()+ 1800000) - date2.getTime());

        long timeInSeconds = diff / 1000;
        long hours, minutes, seconds;
        hours = timeInSeconds / 3600;
        timeInSeconds = timeInSeconds - (hours * 3600);
        minutes = timeInSeconds / 60;
        timeInSeconds = timeInSeconds - (minutes * 60);
        seconds = timeInSeconds;

        //TODO Just for testing remove below line
        //diff = 18000;
        timerTextView = (TextView)findViewById(R.id.order_dscp_time);
        timer = new CounterClass(diff,1000);
        timer.start();

        recyclerView = (RecyclerView) findViewById(R.id.recycler_view);

        productList = new ArrayList<>();

        productList = order.getProducts();

        adapter = new OrderProductAdapter(OrderDescriptionActivity.this, productList);

        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(mLayoutManager);

        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(adapter);

        (findViewById(R.id.modify_order_button)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Dialog dialog = new Dialog(OrderDescriptionActivity.this);
                dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                dialog.setContentView(R.layout.dialog_box_modify_order);
                Button dialogButtonCancel = (Button) dialog.findViewById(R.id.cancel_modification);
                dialogButtonCancel.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();
                    }
                });
                Button dialogButtonOk = (Button) dialog.findViewById(R.id.confirm_modification);
                dialogButtonOk.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent i = new Intent(OrderDescriptionActivity.this,ProductModificationActivity.class);
                        i.putExtra("OrdersList",order);
                        startActivity(i);
                        dialog.dismiss();
                    }
                });
                dialog.show();
            }
        });

        (findViewById(R.id.confirm_order_button)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(checkAllCheckboxes()) {
                    final Dialog dialog = new Dialog(OrderDescriptionActivity.this);
                    dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                    dialog.setContentView(R.layout.dialog_box_confirm_order);
                    getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
                    Button dialogButtonCancel = (Button) dialog.findViewById(R.id.cancel_pickup);
                    dialogButtonCancel.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            dialog.dismiss();
                        }
                    });
                    Button dialogButtonOk = (Button) dialog.findViewById(R.id.confirm_pickup);
                    dialogButtonOk.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            //TODO on Confirm what to do?
                            dialog.dismiss();
                        }
                    });
                    dialog.show();
                }
                else {
                    Toast.makeText(OrderDescriptionActivity.this, "Check all orders!", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    public boolean checkAllCheckboxes()
    {
        for(int j=0;j < order.getProducts().size(); j++)
        {
            if(order.getProducts().get(j).getChecked() == false)
                return false;
        }
        return true;
    }


    public class CounterClass extends CountDownTimer {

        //All three methods (constructor) need to be overridden to use this class

        //Default Constructor
        public CounterClass(long millisInFuture, long countDownInterval) {
            super(millisInFuture, countDownInterval);

        }

        //When timer is ticking, what should happen at that duration; will go in this method
        @Override
        public void onTick(long millisUntilFinished) {
            remainMilli = millisUntilFinished;

            //Format to display the timer
            String hms = String.format("%02d min %02d sec", TimeUnit.MILLISECONDS.toMinutes(remainMilli)-TimeUnit.HOURS.toMinutes(TimeUnit.MILLISECONDS.toHours(remainMilli)),
                    TimeUnit.MILLISECONDS.toSeconds(remainMilli)- TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(remainMilli)));

            timerTextView.setText(hms);
        }

        //When time is finished, what should happen: will go in this method
        @Override
        public void onFinish() {
            // reset all variables
            timerTextView.setText("Time Up!");
            isRunning=false;
            remainMilli=0;
        }
    }
}
