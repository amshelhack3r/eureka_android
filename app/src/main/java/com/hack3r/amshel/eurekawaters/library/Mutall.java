/**
 * <h1>Mutall libraray</h1>
 * Create a mutall class that houses associated methods and inner classes to be used by the mutall
 * data managers
 * <h1>PRE-REQUISITES</h1>
 * These are tools/libraries you will need inorder to use this library.
 *
 * <ul>
 *     <li>Android Support Design Library</li>
 *     <li>Android Volley Library</li>
 *     <li>Android Manifest configurations</li>
 *     <li>Basic knowledge of Java and Xml, knowledge in OOP is an added advantage </li>
 * </ul>
 *
 * <h4>Support Design Library</h4>
 * The android support design library or popularly reffered as material design is a library included
 * into mutall for varous reasons.
 * 1:the android snackbar which replaces the toast uses this library
 * 2:when styling your appliction UI most beautiful components eg colors, widgests floating actions
 *    make use of the design library
 * --Installation--
 * There are two ways of installing the design library.
 * The First
 * In your project structure, in android studio right click and go to open Module settings,
 *  Go to the dependancy tab and on your right there will be an option to add a new dependancy
 *  for your application
 *  search for android.support.design and add it resync /rebuild your app
 *  The Second
 *  Add this line to your build.gradle file under dependancies
 *      implementation 'com.android.support:design:26.1.0'
 * Resync your gradle
 *
 * <h4>android volley</h4>
 * Installation is pretty much the same like the design library.
 * In the module settings search for com.android.volley
 * or include this in your dependancy section in the build gradle
 *  implementation 'eu.the4thfloor.volley:com.android.volley:2015.05.28'
 *
 *  <h4>Manifest configurations</h4>
 *  For use of this library some slight changes in the android manifest are required
 *  Under the application tag addd the following
 *  android:name=".Mutall$VolleyController"
 *  so it will look somehow like
 *  <application>
 *      android:name=".Mutall$VolleyController"
 *  </application>
 *  This creates an instance of the singleton class even before the main activity is created
 *  Also include the following associated permissions for the various methods
 *      <uses-permission android:name="android.permission.SEND_SMS"/> for sending sms messages
        <uses-permission android:name="android.permission.READ_SMS"/> for reading inbox messages
        <uses-permission android:name="android.permission.INTERNET"/> For accessing the internet
 *
 * <p>Also included is an single_row.xml file for overriding(inflating) the default list view </p>
 *
 * For android Marshmallow/M(api 23) and above i have included runtime permissions for sending and
 * recieving sms.
 * Have fun
 *
 * @author amshel
 * @version 1.0.0
 * @since 27-July-2018
 */
package com.hack3r.amshel.eurekawaters.library;

import android.Manifest;
import android.app.Activity;
import android.app.PendingIntent;
import android.app.ProgressDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.telephony.SmsManager;
import android.view.View;
import android.widget.Toast;

import com.hack3r.amshel.eurekawaters.objects.Sms;

import java.util.ArrayList;

import es.dmoral.toasty.Toasty;

public class Mutall {
    private static final int SMS_PERMISSIONS_REQUEST = 0;
    private Activity activity;
    public ArrayList smsSent = new ArrayList();
    public ArrayList smsNotSent = new ArrayList();
    private ArrayList<Sms> smsMessagesList = new ArrayList<>();
    private ProgressDialog progressDialog;

    /**
     * when you create an instance of a mutall object we pass on the activity
     * The mutall object can be used by any activity in an application so we dont generalise or
     * assume that its only the mainactivity that will be using this mutall class.
     * If no activity is supplied pass on the getBaseContext() or getApplicationContext()
     * as parameters
     *
     * @param activity
     */
    public Mutall(Activity activity) {
        this.activity = activity;
    }

    /**
     * Create a method for sending sms to a particular number
     * Return true if thesms is sent and false otherwise
     * Register a new broadcast reciever to capture sent intent so we can track which messages went
     * to which number, why. if a message wasnt sent we would like to resent that message to that number
     * so we would like to have a way to keep track of all numbers that were sent and those that didnt
     * @return
     */
    protected boolean send_sms(final String number, String message) {
        requestSmsPermission();
        String SENT = "SMS_SENT";
        PendingIntent sentIntent = PendingIntent.getBroadcast(activity, 0, new Intent(SENT),0);
        activity.registerReceiver(new BroadcastReceiver(){
            @Override
            public void onReceive(Context context, Intent intent) {
                switch (getResultCode()){
                    case Activity.RESULT_OK:
                        showToast("message sent", "success");
                        smsSent.add(number);
                        break;
                    case SmsManager.RESULT_ERROR_GENERIC_FAILURE:
                        showToast("Something went wrong, Top up credit", "error");
                        smsNotSent.add(number);
                        break;
                    case SmsManager.RESULT_ERROR_RADIO_OFF:
                        showToast("Network is turned off", "info");
                        smsNotSent.add(number);
                        break;
                    case SmsManager.RESULT_ERROR_NULL_PDU:
                        showToast("null pdu", "error");
                        smsNotSent.add(number);
                        break;
                    case SmsManager.RESULT_ERROR_NO_SERVICE:
                        showToast("No service", "warning");
                        smsNotSent.add(number);
                        break;
                    default:
                        showSnack("something went wrong");

                }

            }
        }, new IntentFilter(SENT));

        SmsManager smsManager = SmsManager.getDefault();
        smsManager.sendTextMessage(number, null, message, sentIntent, null);
        return true;
    }
    /**
     * Simplae method for displaying a tost notification box in the current activity
     * In future it will be replaced by a snackbar because it is more customizible
     * @param text
     * use the toasty library
     *
     */
    public void showToast(String text, String type){
        switch (type){
            case "error":
                Toasty.error(activity, text, Toast.LENGTH_LONG).show();
                break;
            case "success":
                Toasty.success(activity, text, Toast.LENGTH_LONG).show();
                break;
            case "info":
                Toasty.info(activity, text, Toast.LENGTH_LONG).show();
                break;
            case "warning":
                Toasty.warning(activity, text, Toast.LENGTH_LONG).show();
                break;
            default:
                Toasty.normal(activity, text, Toast.LENGTH_LONG).show();
        }
    }

    /**
     * write a function to display a snackbar
     * snackbar is better to use than a toast because the user has more frredom and flexibility
     *  Pass the message you want to be displayed by the snackbar
     * Remember the snackbar requires the android.support.design library
     * So import that library before proceeding.
     */
    public void showSnack(String message){
        View view = activity.findViewById(android.R.id.content).getRootView();
        final Snackbar snackbar;
        snackbar = Snackbar.make(view, message, Snackbar.LENGTH_INDEFINITE);
        snackbar.setAction("close", new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                snackbar.dismiss();
            }
        });
        snackbar.show();
    }

    /**
     * function for requesting sms permission
     * targeted for android api 23 and above
     */
    public void requestSmsPermission() {
        // Here, thisActivity is the current activity
        if (ContextCompat.checkSelfPermission(activity.getApplicationContext(),
                Manifest.permission.SEND_SMS)
                != PackageManager.PERMISSION_GRANTED) {
            //permission not granted
            //show an explanation why
            if (ActivityCompat.shouldShowRequestPermissionRationale(this.activity,
                    Manifest.permission.SEND_SMS)) {
                showToast("Please allow permission!", "error");

            } else {

                //request the permission
                ActivityCompat.requestPermissions(this.activity,
                        new String[]{Manifest.permission.SEND_SMS}, SMS_PERMISSIONS_REQUEST);

            }
        }
    }

    /**
     * Do a progress dialog for network requests and also for situations where a thread takes time
     * Also involve a function for cancelling the progress dialog
     */
    public void showProgress(String message){
        progressDialog = new ProgressDialog(activity);
        progressDialog.setMessage(message);
        progressDialog.setCancelable(false);
        progressDialog.show();
    }

    public void dismissProgress(){
        if(progressDialog.isShowing()){
            progressDialog.dismiss();
        }
    }

    /**
    *Method for returning an arraylist of all sent sms numbers
    */
    public ArrayList getAllSentNumbers(){
        return smsSent;
}
  /**
    *Method for returning an arraylist of all numbers in which the sms didnt go through not sent
    */
    public ArrayList getAllUnSentNumbers(){
        return smsNotSent;
}


}