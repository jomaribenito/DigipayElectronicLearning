package ph.digipay.digipayelectroniclearning.ui.common;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.os.Handler;
import android.view.KeyEvent;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.FrameLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import ph.digipay.digipayelectroniclearning.R;
import ph.digipay.digipayelectroniclearning.common.base.BaseActivity;
import ph.digipay.digipayelectroniclearning.common.constants.StringConstants;
import ph.digipay.digipayelectroniclearning.common.utils.DigipayWebBrowserClient;

public class PDFBrowserActivity extends BaseActivity {

    private WebView webView;
    private ProgressBar progressBar;
    private TextView progressText;
    private FrameLayout progressLayout;

    private Handler hdlr = new Handler();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pdfbrowser);

        webView = findViewById(R.id.web_browser_wv);
        progressBar = findViewById(R.id.progressBar);
        progressText = findViewById(R.id.progress_bar_tv);
        progressLayout = findViewById(R.id.progress_fl);

        initWebView();

        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            String url = bundle.getString(StringConstants.PDF_URL);
            assert url != null;
            String prefix_url = "https://docs.google.com/viewer?url=";
            webView.loadUrl(prefix_url + url);
        }
    }

    @SuppressLint("SetJavaScriptEnabled")
    private void initWebView() {
        webView.setWebViewClient(new DigipayWebBrowserClient());
        WebSettings webSettings = webView.getSettings();
        webSettings.setJavaScriptEnabled(true);
        webSettings.setDomStorageEnabled(true);
        webSettings.setSaveFormData(true);
        webSettings.setSupportZoom(true);
        webSettings.setJavaScriptCanOpenWindowsAutomatically(true);
        webSettings.setAllowFileAccess(true);
        webSettings.setAllowFileAccessFromFileURLs(true);
        webSettings.setAllowUniversalAccessFromFileURLs(true);
        webSettings.setUserAgentString("Android WebView");

        webView.setWebChromeClient(new WebChromeClient() {
            @Override
            public void onProgressChanged(WebView view, final int newProgress) {
                new Thread(() -> hdlr.post(() -> {
                    progressBar.setProgress(newProgress);
                    String progress = "Loading: %s/100";
                    progressText.setText(String.format(progress, String.valueOf(newProgress)));
                    if (newProgress == 100) {
                        progressLayout.setVisibility(View.GONE);
                    }
                })).start();

            }
        });
        webView.setHorizontalScrollBarEnabled(false);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (event.getAction() == KeyEvent.ACTION_DOWN) {
            switch (keyCode) {
                case KeyEvent.KEYCODE_BACK:
                    if (webView.canGoBack()) {
                        webView.goBack();
                    } else {
                        finish();
                    }
                    return true;
            }

        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    public void setUpToolbar() {
        super.setUpToolbar();
        assert getSupportActionBar() != null;
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setHomeButtonEnabled(true);
    }

}
