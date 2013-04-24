package net.evendanan.oversight.example;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import net.evendanan.oversight.OverSight;

public class OverSightExample extends Activity
{
    private OverSight mToolTip1;
    private OverSight mToolTip2;

    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);

        mToolTip1 = new OverSight(getApplicationContext());
        mToolTip2 = new OverSight(getApplicationContext());


        mToolTip1.attach(findViewById(R.id.hello_world), null, null);
        mToolTip1.setContentView(R.layout.tooltip_for_hello_world);

        mToolTip2.attach(findViewById(R.id.with_question_icon), findViewById(R.id.i_am_the_guy_on_the_right), new OverSight.OnOverSightVisibilityChanged() {
            ImageView icon = (ImageView) findViewById(R.id.i_am_the_guy_on_the_right);
            @Override
            public void onOverSightVisibilityChanged(OverSight overSight, boolean shown) {
                if (shown) {
                    icon.setImageResource(android.R.drawable.ic_menu_close_clear_cancel);
                } else {
                    icon.setImageResource(android.R.drawable.ic_menu_help);
                }
            }
        });

        mToolTip2.setContentView(R.layout.tooltip_second);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        //this is not a must, but it helps.
        mToolTip1.close();
        mToolTip2.close();
    }
}
