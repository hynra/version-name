package com.github.hynra.versionname;

import android.os.AsyncTask;

import org.jsoup.Jsoup;

/**
 * Created by hynra on 16/05/2018.
 */

public class VersionName {


    public interface Listener{
        void onVersionLoaded(String versionName, boolean isWithVaries);
    }

    public void get(String packageName, Listener listener){

    }

    private class GetVersionName extends AsyncTask<Void, String, String> {

        @Override
        protected String doInBackground(Void... voids) {
            String newVersion = "oops";
            try {
                newVersion = Jsoup.connect("https://play.google.com/store/apps/details?id=" + "app.pptik.itb.semut" + "&hl=en").timeout(30000)
                        .userAgent("Mozilla/5.0 (Windows; U; WindowsNT 5.1; en-US; rv1.8.1.6) Gecko/20070725 Firefox/2.0.0.6").referrer("http://www.google.com")
                        .get()
                        .select(".hAyfc .htlgb")
                        .get(5)
                        .ownText();
                return newVersion;
            } catch (Exception e) {
                return null;
            }
        }

        @Override
        protected void onPostExecute(String onlineVersion) {
            super.onPostExecute(onlineVersion);

        }
    }
}
