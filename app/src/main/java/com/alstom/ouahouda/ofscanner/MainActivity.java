package com.alstom.ouahouda.ofscanner;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.webkit.URLUtil;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.alstom.ouahouda.ofscanner.models.Product;
import com.alstom.ouahouda.ofscanner.networks.UrlUtils;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.Result;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;

import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.EnumMap;
import java.util.List;
import java.util.Map;

import me.dm7.barcodescanner.zxing.ZXingScannerView;

public class MainActivity extends AppCompatActivity {

    private ZXingScannerView scannerView;
    private static String  resultCode;

    private  ProgressBar spinner;

    private  TextView OF;
    private  TextView ref;
    private  TextView desg;
    private  TextView service;
    private  TextView week;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);




        scannerView = new ZXingScannerView(this);

        scannerView.setResultHandler(new ZXingScannerResultHandler());

        List<BarcodeFormat> myformat = new ArrayList<>();
        myformat.add(BarcodeFormat.CODE_128);

        scannerView.setFormats(myformat);

        setContentView(scannerView);
        scannerView.startCamera();


    }

    @Override
    public void onResume() {
        super.onResume();
        scannerView.setResultHandler(new ZXingScannerResultHandler()); // Register ourselves as a handler for scan results.
        scannerView.startCamera();          // Start camera on resume
    }

    @Override
    public void onPause() {
        super.onPause();
        scannerView.stopCamera();           // Stop camera on pause
    }



    class ZXingScannerResultHandler implements ZXingScannerView.ResultHandler {

        @Override
        public void handleResult(Result result) {

            resultCode = result.getText();
            Toast.makeText(MainActivity.this,resultCode,Toast.LENGTH_SHORT).show();
            if(resultCode.length()==8) {
                try {
                    Long.parseLong(resultCode);
                    setContentView(R.layout.activity_main);
                    scannerView.stopCamera();
                    Log.i("Alstom_DROID", resultCode);
                    new HttpRequestTask().execute();

                }
                catch (Exception e) {
                    scannerView.resumeCameraPreview(this);
                }
            }

            else {
                scannerView.resumeCameraPreview(this);
            }
        }

    }

    private class HttpRequestTask extends AsyncTask<Void, Void, Product> {


        @Override
        protected Product doInBackground(Void... params) {
            try {
                Log.i("Alstom_DROID",resultCode);

                String url = UrlUtils.MAIN_URL+UrlUtils.SERVICE_TAG +  UrlUtils.PRODUCT_BYOF+
                        UrlUtils.PARAMETER_SYMB+"NumberOF="+resultCode;
                Log.i("Alstom_DROID",url);
                RestTemplate restTemplate = new RestTemplate();
                restTemplate.getMessageConverters().add(new MappingJackson2HttpMessageConverter());
                Product product = restTemplate.getForObject(url, Product.class);
                if (product!= null) Log.i("Alstom_DROID","Product : " + product.toString());
                else Log.i("Alstom_DROID","Product is null");
                return product;
            } catch (Exception e) {
                Log.e("Alstom_DROID", e.getMessage(), e);
                return null;

            }
        }

        @Override
        protected void onPostExecute(Product product) {


            if(product == null) {

            }
            else {
                spinner = (ProgressBar) findViewById(R.id.progressBar);
                spinner.setVisibility(View.VISIBLE);
                OF = (TextView) findViewById(R.id.OF);
                OF.setVisibility(View.VISIBLE);
                ref = (TextView) findViewById(R.id.REF);
                ref.setVisibility(View.VISIBLE);
                desg = (TextView) findViewById(R.id.DESIGN);
                desg.setVisibility(View.VISIBLE);
                service = (TextView) findViewById(R.id.SERVICE);
                service.setVisibility(View.VISIBLE);
                week = (TextView) findViewById(R.id.WEEK);
                week.setVisibility(View.VISIBLE);

                ImageView iv = (ImageView) findViewById(R.id.barcodeView);
                Bitmap bitmap = null;

                try {

                    bitmap = encodeAsBitmap(product.getOf() + "", BarcodeFormat.CODE_128, 400, 100);
                    iv.setImageBitmap(bitmap);

                } catch (WriterException e) {
                    e.printStackTrace();
                }

                spinner.setVisibility(View.INVISIBLE);

                OF.setText(product.getOf() + "");

                ref.setText(product.getReference());

                desg.setText(product.getDesignation());

                service.setText(product.getCurrentService());

                week.setText(product.getWeek() + "");
            }

        }

        private static final int WHITE = 0xFFFFFFFF;
        private static final int BLACK = 0xFF000000;

        Bitmap encodeAsBitmap(String contents, BarcodeFormat format, int img_width, int img_height) throws WriterException {
            String contentsToEncode = contents;
            if (contentsToEncode == null) {
                return null;
            }
            Map<EncodeHintType, Object> hints = null;
            String encoding = guessAppropriateEncoding(contentsToEncode);
            if (encoding != null) {
                hints = new EnumMap<EncodeHintType, Object>(EncodeHintType.class);
                hints.put(EncodeHintType.CHARACTER_SET, encoding);
            }
            MultiFormatWriter writer = new MultiFormatWriter();
            BitMatrix result;
            try {
                result = writer.encode(contentsToEncode, format, img_width, img_height, hints);
            } catch (IllegalArgumentException iae) {
                // Unsupported format
                return null;
            }
            int width = result.getWidth();
            int height = result.getHeight();
            int[] pixels = new int[width * height];
            for (int y = 0; y < height; y++) {
                int offset = y * width;
                for (int x = 0; x < width; x++) {
                    pixels[offset + x] = result.get(x, y) ? BLACK : WHITE;
                }
            }

            Bitmap bitmap = Bitmap.createBitmap(width, height,
                    Bitmap.Config.ARGB_8888);
            bitmap.setPixels(pixels, 0, width, 0, 0, width, height);
            return bitmap;
        }

        private String guessAppropriateEncoding(CharSequence contents) {
            // Very crude at the moment
            for (int i = 0; i < contents.length(); i++) {
                if (contents.charAt(i) > 0xFF) {
                    return "UTF-8";
                }
            }
            return null;
        }



    }

}
