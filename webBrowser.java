/* WikiRacers - Web Browser Activity
 *
 *
 *
 * */
package wikiracers.wikiracers;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.KeyEvent;
//import android.view.Menu;
//import android.view.MenuItem;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.TextView;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

/////////////////////////////////////////////



//webBrowser class

public class webBrowser extends ActionBarActivity {

    private WebView mWebView;  //New Webview Element
    private String webview_target_url; //Target for the player
    //private TextView url_target; //Used to create target

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web_browser);




        //Links Activity Element to refrencable object
        mWebView = (WebView) findViewById(R.id.browser_webView_Window);

        //Sets internal JavaScript to ON
        mWebView.getSettings().setJavaScriptEnabled(true);
        //Sets Starting URL
        //mWebView.loadUrl("http://en.wikipedia.org/wiki/Main_Page");
        mWebView.loadUrl("http://en.wikipedia.org/wiki/Special:Random"); //This is a placeholder
        //Java Class to create a random start and end page
        webview_target_url = random_target();
        mWebView.setWebViewClient(new mWebViewClient());

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

    private String random_target(){
        TextView url_target = (TextView) findViewById(R.id.browser_webView_Text); //Element for url text

        String target_title = "This is a test";

        try {
            URL url = new URL("http://en.wikipedia.org/wiki/Special:Random");
            DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
            DocumentBuilder db = dbf.newDocumentBuilder();
            Document doc = db.parse(new InputSource(url.openStream()));

            NodeList nodeList = doc.getElementsByTagName("item");

        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (ParserConfigurationException e) {
            e.printStackTrace();
        } catch (SAXException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }


        url_target.setText("Target Page\n" + target_title); //Puts Title of target  into webview
        return target_title;
    }

/////////////// JUNK I DON'T WANT TO REMOVE JUST YET////////////////////////////
////////////////////////////////////////////////////////////////////////////////



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

    public static class XMLconverter {

    }
}
