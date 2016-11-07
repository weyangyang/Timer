
package com.qsvxin.twocode.decoding;

import android.app.Activity;
import android.content.DialogInterface;
import android.util.Log;

/**
 * Simple listener used to exit the app in a few cases.
 * 
 * @author Sean Owen
 */
public final class FinishListener implements DialogInterface.OnClickListener,
        DialogInterface.OnCancelListener, Runnable
{

    private final Activity activityToFinish;

    public FinishListener(Activity activityToFinish)
    {
        this.activityToFinish = activityToFinish;
    }

    public void onCancel(DialogInterface dialogInterface)
    {
        Log.i("MSG", "onCancel");
        run();
    }

    public void onClick(DialogInterface dialogInterface, int i)
    {
        Log.i("MSG", "onClick");
        run();
    }

    public void run()
    {
        Log.i("MSG", "activity finish!");
        activityToFinish.finish();
    }

}
