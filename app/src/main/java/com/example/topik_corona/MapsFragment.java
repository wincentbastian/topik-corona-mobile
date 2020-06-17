package com.example.topik_corona;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.net.http.SslError;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.SslErrorHandler;
import android.webkit.WebResourceError;
import android.webkit.WebResourceRequest;
import android.webkit.WebResourceResponse;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;
import android.widget.Toast;


/**
 * A simple {@link Fragment} subclass.
 */
public class MapsFragment extends Fragment {
    private WebView mWebView;
    ProgressBar progressBar;

    public MapsFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        getActivity().setTitle("Map");

        View v=inflater.inflate(R.layout.fragment_maps, container, false);
        mWebView = (WebView) v.findViewById(R.id.webview);

        mWebView.loadUrl("http://sig-1705551066.herokuapp.com/test");
        progressBar =  v.findViewById(R.id.progressBar);
        progressBar.setVisibility(View.VISIBLE);


        // Enable Javascript
        WebSettings webSettings = mWebView.getSettings();
        webSettings.setJavaScriptEnabled(true);

        // Force links and redirects to open in the WebView instead of in a browser
        mWebView.setWebViewClient(new WebViewClient(){

            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
                progressBar.setVisibility(View.GONE);

                //show webview
                mWebView.setVisibility(View.VISIBLE);
            }

            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                super.onPageStarted(view, url, favicon);
                progressBar.setVisibility(View.VISIBLE);
            }

            @Override
            public void onLoadResource(WebView view, String url) {
                super.onLoadResource(view, url);
                progressBar.setVisibility(View.VISIBLE);
            }


            @Override
            public void onReceivedError(WebView view, WebResourceRequest request, WebResourceError error) {
                super.onReceivedError(view, request, error);
                try {
                    mWebView.stopLoading();
                } catch (Exception e) {
                }
                try {
                    mWebView.clearView();
                } catch (Exception e) {
                }
                if (mWebView.canGoBack()) {
                    mWebView.goBack();
                }
                mWebView.loadUrl("about:blank");

                //Showing and creating an alet dialog
                AlertDialog alertDialog = new AlertDialog.Builder(getContext()).create();
                alertDialog.setTitle("Error");
                alertDialog.setMessage("No internet connection was found!");

                alertDialog.show();
            }

            @Override
            public void onReceivedHttpError(WebView view, WebResourceRequest request, WebResourceResponse errorResponse) {
                super.onReceivedHttpError(view, request, errorResponse);
                Toast.makeText(getContext(), "Please Check your connection", Toast.LENGTH_LONG).show();
            }
        });

        return v;
    }
}
