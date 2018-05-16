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

    public static void get(String packageName, Listener listener){
        new GetVersionName(listener, packageName).execute();
    }

    private static class GetVersionName extends AsyncTask<Void, String, String> {

        private Listener listener;
        private String packageName;
        private final String VARIES_TEXT = "Varies with device";

        GetVersionName(Listener listener, String packageName) {
            super();
            this.listener = listener;
            this.packageName = packageName;
        }

        @Override
        protected String doInBackground(Void... voids) {
            String newVersion = "";
            try {
                newVersion = Jsoup.connect("https://play.google.com/store/apps/details?id=" + packageName + "&hl=en").timeout(30000)
                        .userAgent("Mozilla/5.0 (Windows; U; WindowsNT 5.1; en-US; rv1.8.1.6) Gecko/20070725 Firefox/2.0.0.6").referrer("http://www.google.com")
                        .get()
                        .select(".htlgb .htlgb")
                        .get(3)
                        .ownText();
                return newVersion;
            } catch (Exception e) {
                return null;
            }
        }

        @Override
        protected void onPostExecute(String currentVersion) {
            super.onPostExecute(currentVersion);
            boolean isVaries = currentVersion.contains(VARIES_TEXT);
            listener.onVersionLoaded(currentVersion, isVaries);
        }
    }
}
