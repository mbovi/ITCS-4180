package itcs4180.homework5;

import android.app.ProgressDialog;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.lang.reflect.Array;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Date;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    private String ITUNES_URL = "https://itunes.apple.com/us/rss/toppodcasts/limit=30/xml";

    ArrayList<Podcast> podCasts = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setTitle("iTunes Top Podcasts");
        connectToAPI();
    }


    private void generateListView(ArrayList<Podcast> podcasts){
        Log.d("Test-GenerateListView", "We got here!");
        Log.d("Test-Async", "" + podcasts.toString());
        ListView listView = (ListView)findViewById(R.id.listView);
        PodCastAdapter adapter = new PodCastAdapter(this, R.layout.podcast_viewer, podcasts);
        listView.setAdapter(adapter);

    }

    ///////////////////////////////////////////////////////////////////////////
    // Connect to API
    ///////////////////////////////////////////////////////////////////////////

    private void connectToAPI() {
        if (isConnected()) {
            Log.d("Test-ConnectToAPI", "Is connected to internet. Ready to connect yayayayayy");
            //new AsyncSource(sources, MainActivity.this).execute(SOURCE_URL);
            new AsyncItunes().execute(ITUNES_URL);
        } else {
            Log.d("Test-ConnectToAPI", "Is not connected to internet.");
            Toast.makeText(MainActivity.this, "Not Connected to Internet", Toast.LENGTH_SHORT).show();
        }
    }

    private boolean isConnected() {
        ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();

        if (networkInfo == null || !networkInfo.isConnected() ||
                (networkInfo.getType() != ConnectivityManager.TYPE_WIFI
                        && networkInfo.getType() != ConnectivityManager.TYPE_MOBILE)) {
            return false;
        }
        return true;
    }

    @Override
    public void onClick(View view) {

        if(view.getId() == R.id.btnGo){
            Log.d("Test-onClick", "Go button was clicked");
            TextView text = (TextView) findViewById(R.id.txtSearch);
            String search = text.getText().toString();
            if(!search.isEmpty()){

                ArrayList<Podcast> sortPodcast = new ArrayList<>(podCasts);
                int index = 0;
                for(int i = 0; i < sortPodcast.size(); i++){


                    if(sortPodcast.get(i).title.toLowerCase().contains(search.toLowerCase())){
                        sortPodcast.add(index,sortPodcast.remove(i));
                        sortPodcast.get(index).color = true;

                        index++;
                        Log.d("Test-sort", "We just added i to a new index ... i = " + i + " index = " + index);
                    }



                }
                generateListView(sortPodcast);

            }else{
                Toast.makeText(this, "No String Entered in Search Bar", Toast.LENGTH_SHORT).show();
            }

        }else if(view.getId() == R.id.btnClear){
            for(int i = 0; i < podCasts.size(); i++){
                podCasts.get(i).color = false;
            }

            generateListView(podCasts);
        }
    }

    public class AsyncItunes extends AsyncTask<String, Void, ArrayList<Podcast>> {

        //Context context;
        ProgressDialog loading;

        @Override
        protected void onPreExecute() {
            Log.d("Test-Async", "PreExecute method!");
            loading = new ProgressDialog(MainActivity.this);
            loading.setCancelable(false);

            loading.setMessage("Loading News ...");
            loading.setProgress(0);
            loading.show();
        }

        @Override
        protected ArrayList<Podcast> doInBackground(String... strings) {
            Log.d("Test-Async", "DoInBackground method!");
            HttpURLConnection connection = null;
            ArrayList<Podcast> podcasts = null;

            try{
                URL url = new URL(strings[0]);
                connection = (HttpURLConnection) url.openConnection();
                if(connection.getResponseCode() == HttpURLConnection.HTTP_OK){
                    podcasts = PodcastParser.PodcastsPullParser.parsePodcasts(connection.getInputStream());
                }

                //Log.d("Test-pods", "Podcasts length : " + podcasts.size());
                //Log.d("Test-pods", "Podcasts are : " + podcasts.toString());

            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (XmlPullParserException e) {
                e.printStackTrace();
            }

            return podcasts;
        }

        @Override
        protected void onPostExecute(ArrayList<Podcast> podcasts) {
            Log.d("Test-Async", "PostExecute method!");

            if (podcasts != null && podcasts.size() != 0) {
                Log.d("Test-Async", "We got our podcast info!");
                Log.d("Test-Async", "" + podcasts.toString());
                Log.d("Test-Async", "Number of podcasts: " + podcasts.size());

                loading.setMessage("Loaded!");
                loading.setProgress(100);
                loading.dismiss();
                loading.setProgress(0);

//                podcasts.sort(new Comparator<Podcast>() {
//                    @Override
//                    public int compare(Podcast podcast, Podcast t1) {
//
//                        Date one = null;
//                        Date two = null;
//                        try {
//                            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("MMM dd, yyyy");
//                            one = simpleDateFormat.parse(podcast.releaseDate);
//                            two = simpleDateFormat.parse(t1.releaseDate);
//                        } catch (ParseException e) {
//                            Log.d("test-ERROR", "one date could not be parsed");
//                        }
//
//                        return one.compareTo(two);
//                    }
//                });
                podCasts = podcasts;
                findViewById(R.id.btnGo).setOnClickListener(MainActivity.this);
                findViewById(R.id.btnClear).setOnClickListener(MainActivity.this);
                generateListView(podcasts);

            } else {
                Log.d("Test-Async", "Something's wrong!");
            }
        }
    }
}