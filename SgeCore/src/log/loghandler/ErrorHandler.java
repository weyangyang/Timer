
package log.loghandler;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.lang.Thread.UncaughtExceptionHandler;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
public class ErrorHandler extends Activity implements UncaughtExceptionHandler {
    public static final String DEFINED_TYPE = "errors/xtcoreUnhandleCatcher";

    private static Activity myCurrentActivity;

    private static UncaughtExceptionHandler defaultHandler;
    private static String myDeveloperMailAdress;

    private static String myMailSubject = "Error in xuetangx App";

    private static final String PASSED_ERROR_TEXT_ID = "Error Text";

    private static final CharSequence ERROR_ACTIVITY_TITLE = "xuetangx error util :(";

    private static final String DEV_MAIL = "dev mail";

    private static final String TITLE_MAIL = "title mail";

    private static final String LOG_TAG = "ErrorHandler";

    LinearLayout linear;

    EditText myTextView;

    Button sendButton;

    Handler handler;

    @Deprecated
    public ErrorHandler() {
        if (defaultHandler == null)
            defaultHandler = Thread.getDefaultUncaughtExceptionHandler();
    }

    public ErrorHandler(Activity a) {
        setCurrentActivity(a);
        if (defaultHandler == null)
            defaultHandler = Thread.getDefaultUncaughtExceptionHandler();
    }

    public static void showErrorLog(Activity a, Exception errorToShow,
            boolean keepBrokenProcessRunning) {
        showErrorActivity(a, throwableToString(errorToShow), keepBrokenProcessRunning);
    }

    public static String throwableToString(Throwable t) {
        StringWriter sw = new StringWriter();
        PrintWriter p = new PrintWriter(sw);
        t.printStackTrace(p);
        String s = sw.toString();
        p.close();
        return s;
    }

    private static void showErrorActivity(final Activity activity, final String errorText,
            boolean keepBrokenProcessRunning) {
        if (activity != null) {
            myCurrentActivity = activity;
            Intent i = new Intent(Intent.ACTION_VIEW);
            i.putExtra(PASSED_ERROR_TEXT_ID, errorText);
            i.putExtra(DEV_MAIL, myDeveloperMailAdress);
            i.putExtra(TITLE_MAIL, myMailSubject);
            i.setType(DEFINED_TYPE);
            Log.e("ErrorHandler", "Starting from " + activity + " to " + ErrorHandler.class);
            activity.startActivity(i);

            if (!keepBrokenProcessRunning) {
                activity.finish();
                android.os.Process.killProcess(android.os.Process.myPid());
                System.exit(1);
            }

        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        String myErrorText = getIntent().getExtras().getString(PASSED_ERROR_TEXT_ID);
        handler = new Handler();
        myDeveloperMailAdress = getIntent().getExtras().getString(DEV_MAIL);
        myMailSubject = getIntent().getExtras().getString(TITLE_MAIL);
        loadErrorLayout(this, myErrorText);
    }

    private void loadErrorLayout(Activity a, String myErrorText) {
        linear = new LinearLayout(this);
        linear.setBackgroundColor(0xFF880000);
        linear.setOrientation(LinearLayout.VERTICAL);
        LayoutParams paramsEdit = new LayoutParams(LayoutParams.FILL_PARENT,
                LayoutParams.FILL_PARENT);
        paramsEdit.weight = 1;
        myTextView = new EditText(this);
        myTextView.setBackgroundColor(0x00000000);
        myTextView.setTextColor(0xFFFFFFFF);
        myTextView.setTextSize(17);
        myTextView.setLayoutParams(paramsEdit);
        LayoutParams paramsButton = new LayoutParams(LayoutParams.FILL_PARENT,
                LayoutParams.FILL_PARENT);
        paramsButton.weight = 5;
        sendButton = new Button(this);
        sendButton.setText("发送错误报告给开发者...");
        sendButton.setLayoutParams(paramsButton);

        linear.setOrientation(LinearLayout.VERTICAL);
        linear.addView(myTextView);
        linear.addView(sendButton);
        sendButton.setVisibility(View.GONE);
        a.setContentView(linear);
        a.setTitle(ERROR_ACTIVITY_TITLE);
        myTextView.setEnabled(false);
        myErrorText += CollectDataManager.getDebugInfosToErrorMessage(a);
        if (myErrorText != null)
            myTextView.setText(myErrorText);

        if (myDeveloperMailAdress != null) {
            enableMailButton(a, myTextView);
        } else {
            // 保存到sd卡
            handler.post(new Runnable() {
                @Override
                public void run() {
                    ErrorLogSave.cacheErrorLogToSDFile(ErrorHandler.this, myTextView.getText()
                            .toString());
                }
            });
        }
    }

    private void enableMailButton(final Activity a, final EditText myTextView) {
        sendButton.setVisibility(View.VISIBLE);
        sendButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                sendMail(a, myTextView);
            }
        });
    }

    private static void sendMail(Activity a, EditText myTextView) {
        final Intent emailIntent = new Intent(android.content.Intent.ACTION_SEND);
        emailIntent.setType("plain/text");
        emailIntent.putExtra(android.content.Intent.EXTRA_EMAIL, new String[] {
            myDeveloperMailAdress
        });
        emailIntent.putExtra(android.content.Intent.EXTRA_SUBJECT, myMailSubject);
        emailIntent.putExtra(android.content.Intent.EXTRA_TEXT, myTextView.getText());
        a.startActivity(Intent.createChooser(emailIntent, "Send mail..."));
    }

    @Override
    public void uncaughtException(final Thread thread, final Throwable ex) {
        Log.e(LOG_TAG, "A wild 'Uncaught exeption' appeares!");
        ex.printStackTrace();
        if (myCurrentActivity != null) {
            Log.e("ErrorHandler", "Starting error activity");
            showErrorActivity(myCurrentActivity, throwableToString(ex), false);
        } else {
            Log.e("ErrorHandler", "No current activity set -> error activity couldn't be started");
            // 保存到sd卡
            handler.post(new Runnable() {
                @Override
                public void run() {
                    String debugText = CollectDataManager
                            .getDebugInfosToErrorMessage(ErrorHandler.this);
                    ErrorLogSave.cacheErrorLogToSDFile(ErrorHandler.this, throwableToString(ex)
                            + debugText);
                }
            });
            defaultHandler.uncaughtException(thread, ex);
        }
    }

    public static void enableEmailReports(String developerEmailAdress, String emailTitle) {
        myDeveloperMailAdress = developerEmailAdress;
        myMailSubject = emailTitle;
    }

    public static void setCurrentActivity(Activity a) {
        myCurrentActivity = a;
    }

    public static void registerNewErrorHandler(Activity currentActivity) {
        Thread.setDefaultUncaughtExceptionHandler(new ErrorHandler(currentActivity));
    }
}
