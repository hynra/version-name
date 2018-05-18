package com.github.hynra.versionname;

import android.os.AsyncTask;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

/**
 * Created by hynra on 16/05/2018.
 */

public class VersionName {

    private static final String BEFORE_STRING = "requires";
    private static final String AFTER_STRING = "current version";

    private static final int PLAYSTORE_PROVIDER = 0;
    private static final int APKPURE_PROVIDER = 1;


    public interface Listener {
        void onVersionLoaded(String versionName, boolean isWithVaries);
    }

    public static void get(String packageName, Listener listener) {
        new GetVersionName(listener, packageName, AFTER_STRING, BEFORE_STRING, PLAYSTORE_PROVIDER).execute();
    }

    public static void get(String packageName, String afterText, String beforeText, Listener listener) {
        new GetVersionName(listener, packageName, afterText, beforeText, PLAYSTORE_PROVIDER).execute();
    }

    private static class GetVersionName extends AsyncTask<Void, String, String> {

        static String between(String value, String a, String b) {
            value = value.toLowerCase();
            a = a.toLowerCase();
            b = b.toLowerCase();

            int posA = value.indexOf(a);
            if (posA == -1) {
                return "";
            }
            int posB = value.lastIndexOf(b);
            if (posB == -1) {
                return "";
            }
            int adjustedPosA = posA + a.length();
            if (adjustedPosA >= posB) {
                return "";
            }
            return value.substring(adjustedPosA, posB);
        }

        private Listener listener;
        private String packageName;
        private String afterText;
        private String beforeText;
        private int provider;
        private final String VARIES_TEXT = "varies with device";

        GetVersionName(Listener listener, String packageName, String afterText, String beforeText, int provider) {
            super();
            this.provider = provider;
            this.listener = listener;
            this.packageName = packageName;
            this.afterText = afterText;
            this.beforeText = beforeText;
        }

        @Override
        protected String doInBackground(Void... voids) {
            String newVersion = "";
            try {
                Document doc = Jsoup.connect("https://play.google.com/store/apps/details?id=" + packageName + "&hl=en").timeout(30000)
                        .userAgent("Mozilla/5.0 (Windows; U; WindowsNT 5.1; en-US; rv1.8.1.6) Gecko/20070725 Firefox/2.0.0.6").referrer("http://www.google.com")
                        .get();
                Elements spanTags = doc.getAllElements();
                for (Element spanTag : spanTags) {
                    String text = spanTag.ownText();
                    newVersion += " " + text;
                }
                newVersion = newVersion.trim().replaceAll("[ ]{2,}", " ");
                newVersion = between(newVersion, afterText, beforeText);
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
