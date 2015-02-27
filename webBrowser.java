/* WikiRacers - Web Browser Activity
 *
 *
 *
 * */
package wikiracers.wikiracers;

import android.app.Activity;
import android.graphics.Bitmap;
import android.os.Bundle;
//import android.view.Menu;
//import android.view.MenuItem;
import android.util.Log;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;


/////////////////////////////////////////////
//test with git


//webBrowser class

public class webBrowser extends Activity {

    private WebView mWebView;  //New Webview Element
    static int pageCount = 0;
    static String currentURL = "";
    static String startingURL = "";
    static String target_URL = "";
    static String target_URL_full = "";
    static List<String> list_URL = new ArrayList<String>();
    static Boolean gameStart = false;
    static Boolean gameRun = false; // might be the same as gameStart
    static Boolean peekMode = false; // toggles when the user is playing or looking at target
    static Boolean backSwitch = true; //acts as a switch for the back button (prevents spamming)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web_browser);
        final TextView countText = (TextView) findViewById(R.id.textView2);


        //Links Activity Element to refrencable object
        mWebView = (WebView) findViewById(R.id.browser_webView_Window);
        //Sets internal JavaScript to ON
        mWebView.getSettings().setJavaScriptEnabled(true);
        mWebView.getSettings().setLoadsImagesAutomatically(false);
        //Sets Starting URL
        mWebView.loadUrl("http://en.wikipedia.org/wiki/Special:Random");
        mWebView.setWebViewClient(new mWebViewClient(){

            //counts when a page is loaded completely
            //used instead of onPageStarted for counting reasons (redirections, etc.)
            //TODO: make sure the startup doesn't start past 0
            //TODO: make sure the count doesn't go up when page load fails
            @Override
            public void onPageFinished(WebView view, String url){
                super.onPageFinished(view, url);
                if(peekMode){

                }
                else{backSwitch = true;
                if (!url.equals(currentURL)) {
                    currentURL = url;
                    remove_html_elements();
                    if(gameRun) {
                        pageCount++;
                    }
                    Log.d("game", url + " ~ " + String.valueOf(pageCount) + "target:" + target_URL + " start:" + startingURL);
                    countText.setText(String.valueOf(pageCount));
                    backSwitch = true;
                    if (get_page_title(url).equals(target_URL)){
                        list_URL.add(url);
                        TextView url_target = (TextView) findViewById(R.id.browser_webView_Text);
                        url_target.setText("Winner");
                        int i = 0;
                        for (;i<list_URL.size();++i){
                            Log.d("victory", list_URL.get(i));
                        }
                        Log.d("path", "url: " + url);
                        gameRun = gameStart = false; //allows player to browse around post game without messing with stats
                    }
                    if(startingURL.equals("") && !url.equals("http://en.m.wikipedia.org/wiki/Special:Random")){
                        mWebView.loadUrl("http://en.m.wikipedia.org/wiki/Special:Random");
                        startingURL = url;
                    }
                    else if(target_URL.equals("") && !url.equals("http://en.m.wikipedia.org/wiki/Special:Random")){
                        TextView url_target;
                        url_target = (TextView)findViewById(R.id.browser_webView_Text);
                        url_target.setText(get_page_title(url));
                        target_URL = get_page_title(url);
                        target_URL_full = url;
                        mWebView.loadUrl(startingURL);
                        pageCount = 0;
                        gameStart = gameRun = true;
                    }else if (gameStart){
                        //Todo: test this (play the game all the way through)
                        list_URL.add(url);
                    }
                    }
                }
            }
            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon){
                backSwitch = false; //to prevent cancelling a load
                super.onPageStarted(view,url,favicon);
                //remove_html_elements();
            }
        });
        // Back button functionality
        final Button webBack = (Button)findViewById(R.id.browser_webView_Back_Button);
        final TextView targetPageText = (TextView)findViewById(R.id.browser_webView_Text);
        View.OnClickListener listen = new View.OnClickListener(){
            @Override
            public void onClick(View view){
                if (view == webBack){
                    Log.d("game", "back clicked");
                    if(backSwitch){Log.d("game","backswitch true");}
                    if(mWebView.canGoBack()){Log.d("game","GoBack true");}
                    if(mWebView.canGoBack() && backSwitch){
                        Log.d("game", "going back");
                        backSwitch = false;
                        mWebView.goBack();
                    }
                }
                else if (view == targetPageText){
                    if(peekMode){
                        peekMode = false;
                        backSwitch = true;
                        mWebView.goBack();
                    }else {
                        Log.d("game", "text clicked");
                        peekMode = true;
                        mWebView.loadUrl(target_URL_full);
                    }
                }
            }
        };
        webBack.setOnClickListener(listen);
        targetPageText.setOnClickListener(listen);
    }

    //Removes Web Client default buttons and bounds the browser space to
    //our WebView activity.

    private class mWebViewClient extends WebViewClient {
        @Override
        public boolean shouldOverrideUrlLoading(WebView webview, String url)
        {
            Log.d("game","override view loading");
            if (!peekMode) {
                webview.loadUrl(url);
            }
            return true;
        }
    }


    public String get_page_title(String url){
        //Gets everything after the final / in the Url aka the page_title
        int get_last_slash = url.lastIndexOf('/');
        String page_title = url.substring(get_last_slash+1);
        return page_title;
    }

    public void remove_html_elements(){
        //Removes Search bar
        mWebView.loadUrl("javascript:(function() { " + "document.getElementsByClassName('header')[0].style.display = 'none'; " + "})()");
        //Removes last edit info
        mWebView.loadUrl("javascript:(function() { " + "document.getElementsByClassName('last-modified-bar truncated-text')[0].style.display = 'none'; " + "})()");
        //Removes Contents Box
        mWebView.loadUrl("javascript:(function() { " + "document.getElementsByClassName('toc-mobile view-border-box')[0].style.display = 'none'; " + "})()");

        //mWebView.loadUrl("javascript:(function() { " + "document.getElementById('References').style.display = 'none'; " + "})()");

        //Removes Images
        mWebView.loadUrl("javascript:(function() { " + "var image = document.getElementsByClassName('image'); " +
                "for (int i = 0; i<image.length; i++){image[i].style.display = 'none';" + "})()");

        mWebView.loadUrl("javascript:(function() { " + "var sections = document.getElementsByClassName('section-heading collapsible-heading open-block');" +
                "for (block in sections){" +
                "string content= block.aria-controls;" +
                "if (block.id == 'References' || block.id == 'Further reading' || block.id == 'External links'){" +
                "content.style.display = 'none';" +
                "}else{" +
                "document.getElementById(content).aria-pressed = true;" +
                "}" +
                "}"  + "})()"
        );
        //Removes "Read in another language bar"
        mWebView.loadUrl("javascript:(function() { " + "document.getElementById('page-secondary-actions').style.display = 'none';" + "})()");
        //Removes footer at the bottom of the page
        mWebView.loadUrl("javascript:(function() { " + "document.getElementById('footer').style.display = 'none';" + "})()");
        //Removes the edit and watch page icons at the top of the page
        mWebView.loadUrl("javascript:(function() { " + "document.getElementById('page-actions').style.display = 'none';" + "})()");
        //Removes the edit icons throughout the page
        mWebView.loadUrl("javascript:(function() { " + "document.getElementsByClassName('icon icon-edit-enabled edit-page icon-32px').style.display = 'none'; " + "})()");

    }
}
