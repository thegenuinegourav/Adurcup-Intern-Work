package com.adurcup.adurcupseller.activity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.GestureDetector;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.ViewFlipper;

import com.adurcup.adurcupseller.R;
import com.adurcup.adurcupseller.misc.DownloadImageTask;
import com.adurcup.adurcupseller.misc.ProductModel;

public class ProductDescriptionActivity extends AppCompatActivity {

    private static final int SWIPE_MIN_DISTANCE = 120;
    private static final int SWIPE_THRESHOLD_VELOCITY = 200;
    private ViewFlipper mViewFlipper;
    private Context mContext;
    private final GestureDetector detector = new GestureDetector(new SwipeGestureDetector());
    private ProductModel product;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_description);

        Intent i = getIntent();
        product = (ProductModel) i.getSerializableExtra("ProductObject");

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        TextView mTitle = (TextView) toolbar.findViewById(R.id.toolbar_title);
        mTitle.setTypeface(Typeface.DEFAULT_BOLD);
        mTitle.setText(product.getProductTitle());

        ((TextView) findViewById(R.id.productDscp_title)).setText(product.getProductTitle());
        ((TextView) findViewById(R.id.productDscp_category)).setText(product.getCategory());
        ((TextView) findViewById(R.id.productDscp_volume)).setText(product.getVolume().getValue() + " " + product.getVolume().getUnit());
        ((TextView) findViewById(R.id.productDscp_weight)).setText(product.getWeight().getValue() + " " + product.getWeight().getUnit());
        ((TextView) findViewById(R.id.productDscp_top_diameter)).setText(product.getSizeTop().getValue() + " " + product.getSizeTop().getUnit());
        ((TextView) findViewById(R.id.productDscp_bottom_diameter)).setText(product.getSizeBottom().getValue() + " " + product.getSizeBottom().getUnit());
        ((TextView) findViewById(R.id.productDscp_material)).setText(product.getMaterial());
        ((TextView) findViewById(R.id.productDscp_manufacturer)).setText(product.getManufacturer());
        ((TextView) findViewById(R.id.productDscp_color)).setText(product.getColour());
        ((TextView) findViewById(R.id.productDscp_shape)).setText(product.getShape().getOverview());
        ((TextView) findViewById(R.id.productDscp_gsm)).setText(product.getGsm());
        ((TextView) findViewById(R.id.productDscp_texture)).setText(product.getSurfaceTexture());
        ((TextView) findViewById(R.id.productDscp_design)).setText(product.getSurfaceDesign());
       // ((TextView) findViewById(R.id.productDscp_price)).setText(product.getVendorPrice().toString() + " " + product.getVendorPriceUnit());
        ((TextView) findViewById(R.id.productDscp_tax)).setText(product.getTax().toString());
        ((TextView) findViewById(R.id.productDscp_unit)).setText(product.getUnitDescription().getValue() + " " + product.getUnitDescription().getUnit());
        ((TextView) findViewById(R.id.productDscp_height)).setText(product.getSizeSide().getValue() + " " + product.getSizeSide().getUnit());

        mContext = this;
        mViewFlipper = (ViewFlipper) this.findViewById(R.id.view_flipper);
        mViewFlipper.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(final View view, final MotionEvent event) {
                detector.onTouchEvent(event);
                return true;
            }
        });


        for(int j=0; j< product.getImagesURLs().size(); j++)
        {
            ImageView imageView = new ImageView(getApplicationContext());
            new DownloadImageTask(imageView)
                    .execute(getString(R.string.url_image_500_from_AWS) + product.getImagesURLs().get(j));
            mViewFlipper.addView(imageView);
        }

        mViewFlipper.setInAnimation(AnimationUtils.loadAnimation(mContext, R.anim.left_in));
        mViewFlipper.setOutAnimation(AnimationUtils.loadAnimation(mContext, R.anim.left_out));
        mViewFlipper.showNext();

        mViewFlipper.setAutoStart(true);
        mViewFlipper.setFlipInterval(7000);
        mViewFlipper.startFlipping();

    }

    class SwipeGestureDetector extends GestureDetector.SimpleOnGestureListener {
        @Override
        public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
            try {
                // right to left swipe
                if (e1.getX() - e2.getX() > SWIPE_MIN_DISTANCE && Math.abs(velocityX) > SWIPE_THRESHOLD_VELOCITY) {
                    mViewFlipper.setInAnimation(AnimationUtils.loadAnimation(mContext, R.anim.left_in));
                    mViewFlipper.setOutAnimation(AnimationUtils.loadAnimation(mContext, R.anim.left_out));
                    mViewFlipper.showNext();
                    return true;
                } else if (e2.getX() - e1.getX() > SWIPE_MIN_DISTANCE && Math.abs(velocityX) > SWIPE_THRESHOLD_VELOCITY) {
                    mViewFlipper.setInAnimation(AnimationUtils.loadAnimation(mContext, R.anim.right_in));
                    mViewFlipper.setOutAnimation(AnimationUtils.loadAnimation(mContext, R.anim.right_out));
                    mViewFlipper.showPrevious();
                    return true;
                }

            } catch (Exception e) {
                e.printStackTrace();
            }

            return false;
        }
    }

    public void UpdateProductStatus(View view) {
        Intent intent = new Intent(this, UpdateProductStatus.class);
        intent.putExtra("ProductObj", product);
        startActivity(intent);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection
        switch (item.getItemId()) {
            case R.id.edit:
                editDetails();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    public void editDetails()
    {

    }
}
