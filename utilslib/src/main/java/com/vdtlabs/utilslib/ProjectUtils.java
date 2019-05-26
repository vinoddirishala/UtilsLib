package com.vdtlabs.utilslib;

import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;

/**
 * Created by Vinod Dirishala on 24,May,2019
 */
public class ProjectUtils {


    /**
     * @param applicationContext
     * @param linkType
     * @param linktobeParsed
     *  linkType - 1          -> to redirect to webbrowser to open webLinks
     *  linkType - 2          -> to redirect to play store
     *  linkType - 3          -> to open external apps available on mobile
     *  linkType - 4          -> to open email clients to send emails
     */
    public static boolean handleExternalLinks(Activity applicationContext, int linkType, String linktobeParsed,String contentDescrption,String targetPackage) {
        boolean isOperationDone = false;
        try {
            switch (linkType) {
                case 1:
                    isOperationDone =   openBrowser(applicationContext,linktobeParsed);
                    break;
                case 2:
                    isOperationDone =  openPlayStore(applicationContext,linktobeParsed);
                    break;
                case 3:
                    isOperationDone = shareInformation(applicationContext,contentDescrption,targetPackage);
                    break;
                case 4:
                    isOperationDone = openEmailClient(applicationContext,linktobeParsed,targetPackage,contentDescrption);
                    break;
            }
            return isOperationDone;
        }
        catch (Exception e){
            e.printStackTrace();
            return isOperationDone;
        }
    }

    /**
     *
     * @param activityContext
     * @param webURL
     */
    public static boolean openBrowser(Activity activityContext, String webURL) {
        try {
            Uri urlParserUri = Uri.parse(webURL);
            Intent browserIntent = new Intent(Intent.ACTION_VIEW, urlParserUri);
            activityContext.startActivity(browserIntent);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * @param activityContext
     * @param appPackageName
     */
    public static boolean openPlayStore(Activity activityContext, String appPackageName) {
        try {
            activityContext.startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=" + appPackageName)));
            return true;
        } catch (ActivityNotFoundException anfe) {
            activityContext.startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/apps/details?id=" + appPackageName)));
            anfe.printStackTrace();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * @param activityContext
     * @param contentTobeShared
     * @param targetPackage
     */
    public static boolean shareInformation(Activity activityContext, String contentTobeShared,String targetPackage) {
        try {
        Intent whatsappIntent = new Intent(Intent.ACTION_SEND);
        whatsappIntent.setType("text/plain");
        whatsappIntent.setPackage(targetPackage.equalsIgnoreCase("") ? null:targetPackage);
        whatsappIntent.putExtra(Intent.EXTRA_TEXT, contentTobeShared);
        activityContext.startActivity(whatsappIntent);
        return true;
        } catch (ActivityNotFoundException ex) {
            ex.printStackTrace();
            return false;
        }catch (Exception e){
            e.printStackTrace();
            return false;
        }
    }

    /**
     *
     * @param applicationContext
     * @param receipentMailID
     * @param mailSubject
     * @param mailBody
     * @return
     */
    public static boolean openEmailClient(Activity applicationContext,String receipentMailID,String mailSubject,String mailBody){
        try {
            Intent intent = new Intent(Intent.ACTION_SEND);
            intent.setType("plain/text");
            intent.putExtra(Intent.EXTRA_EMAIL, new String[] { receipentMailID });
            intent.putExtra(Intent.EXTRA_SUBJECT, mailSubject);
            intent.putExtra(Intent.EXTRA_TEXT, mailBody+""+getDeviceInfo(applicationContext));
            applicationContext.startActivity(Intent.createChooser(intent, "Choose email client"));
            return true;
        }catch (ActivityNotFoundException anf){
            anf.printStackTrace();
            return false;
        }catch (Exception e){
            e.printStackTrace();
            return false;
        }
    }

    /**
     *
     * @return
     */
    public static String getAppInfo() {
        try {
            String appVersionInfo = "App Version: \t"+BuildConfig.VERSION_NAME+"\n"+"App Version Code:  \t"+BuildConfig.VERSION_CODE+"\n";
            return appVersionInfo.equalsIgnoreCase("") ? "":appVersionInfo;
        }catch (Exception e){
            e.printStackTrace();
            return "";
        }
    }

    /**
     *
     * @return
     */
    public static String getDeviceInfo(Context mContext) {
        try {
            String deviceInfo =
                    "OS Version:  \t"+System.getProperty("os.version")    +"\n"+
                            "SDK Version:  \t"+android.os.Build.VERSION.SDK       +"\n"+
                            "Device Type:  \t"+android.os.Build.DEVICE            +"\n"+
                            "Device Model: \t "+android.os.Build.MODEL            +"\n"+
                            "Product Model:  \t"+android.os.Build.PRODUCT         +"\n";
            return deviceInfo.equalsIgnoreCase("") ? "":mContext.getResources().getString(R.string.please_do_not_remove)+"\n\n"+getAppInfo()+deviceInfo;
        }catch (Exception e){
            e.printStackTrace();
            return "";
        }
    }


}
