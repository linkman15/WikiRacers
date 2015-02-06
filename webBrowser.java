/* WikiRacers - Web Browser Activity
 *
 *
 *
 * */
package wikiracers.wikiracers;

import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
//import android.view.Menu;
//import android.view.MenuItem;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.TextView;


import java.io.IOException;
import java.io.InputStream;
import java.io.StringReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

/////////////////////////////////////////////



//webBrowser class

public class webBrowser extends ActionBarActivity {

    private WebView mWebView;  //New Webview Element
    private String webview_target_url; //Target for the player
    //private random_url title_grab = new random_url();
    private String page_url; //Currently unused
    private String curr_url; //Currently unused
    private boolean have_target = false;
    private TextView url_target; //Element for url text
    //private TextView url_target; //Used to create target

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web_browser);

        //title_grab.execute("http://en.wikipedia.org/wiki/Special:Random"); //Calls doInBackground for getting Random Page


        //Links Activity Element to refrencable object
        mWebView = (WebView) findViewById(R.id.browser_webView_Window);

        //Sets internal JavaScript to ON
        mWebView.getSettings().setJavaScriptEnabled(true);
        mWebView.setWebViewClient(new mWebViewClient());

        //mWebView.loadUrl("http://en.wikipedia.org/wiki/Main_Page");
        //page_url = "http://en.wikipedia.org/wiki/Special:Random";

        //mWebView.loadUrl("http://en.wikipedia.org/wiki/Special:Random"); //This is a placeholder
        //page_url = mWebView.getUrl();
        //Java Class to create a random start and end page
        //webview_target_url = title_grab.random_target();

        //
        webview_target_url = "http://en.wikipedia.org/wiki/California_State_Route_67"; //Temporary for before random elements
        String target_name = get_page_title(webview_target_url);
        //Sets Starting URL
        mWebView.loadUrl("http://en.wikipedia.org/wiki/Special:Random");
        //mWebView.loadUrl("http://en.wikipedia.org/wiki/Main_Page"); //For testing purposes

        url_target = (TextView) findViewById(R.id.browser_webView_Text);
        url_target.setText("Target Page\n" + target_name); //Puts Title of target into webview



    }


     public void onPageStarted(WebView view, String url, Bitmap favicon){


         //Checks if use h
         if(url.equals(webview_target_url)){
             url_target = (TextView) findViewById(R.id.browser_webView_Text);
             url_target.setText("You Win!");
         }
      }


    public void onPageFinished(WebView view, String url){



    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event)
    {
        if ((keyCode == KeyEvent.KEYCODE_BACK) && mWebView.canGoBack())
        {
            mWebView.goBack();
            return true;
        }

        return super.onKeyDown(keyCode, event);
    }

    public void webBackButton(View view) {




    }


    //Removes Web Client default buttons and bounds the browser space to
    //our WebView activity.

    private class mWebViewClient extends WebViewClient {

        @Override
        public boolean shouldOverrideUrlLoading(WebView webview, String url)
        {
            webview.loadUrl(url);
            return true;
        }
    }

    public void get_current_page(){
        page_url = mWebView.getUrl();
        curr_url = get_page_title(page_url);
    }

    public String get_page_title(String url){
        //Gets everything after the final / in the Url aka the page_title
        int get_last_slash = url.lastIndexOf('/');
        String page_title = url.substring(get_last_slash+1);
        return page_title;
    }

/////////////// JUNK I DON'T WANT TO REMOVE JUST YET////////////////////////////
////////////////////////////////////////////////////////////////////////////////
    //Code meant to get random URL currently having
    public class random_url extends AsyncTask<String, String, String> {
        @Override
        //public String random_target(){
        protected String doInBackground(String... urls) {

            String target_title = "This is a test";
            //String target_title;

            try {
                URL url = new URL(urls[0]);

                HttpURLConnection connection = (HttpURLConnection) url.openConnection();


                url = new URL (connection.getHeaderField("Location"));



                target_title = url.toString();

            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }

            page_url = target_title;

            return target_title;
        }
        @Override
        protected void onPostExecute(String result) {
            curr_url = result;
        }
    }

/*
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_web_browser, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    */

}
