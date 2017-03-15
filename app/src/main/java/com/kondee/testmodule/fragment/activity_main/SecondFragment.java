package com.kondee.testmodule.fragment.activity_main;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.databinding.DataBindingUtil;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.HandlerThread;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.AsyncTaskLoader;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.Loader;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.kondee.testmodule.Manifest;
import com.kondee.testmodule.R;
import com.kondee.testmodule.broadcastreceiver.SmsReceiver;
import com.kondee.testmodule.databinding.FragmentSecondBinding;
import com.kondee.testmodule.view.TestCircularViews;

public class SecondFragment extends Fragment implements LoaderManager.LoaderCallbacks<Object> {

    private static final String TAG = "Kondee";
    FragmentSecondBinding binding;
    private static TextView tvResult;
    Thread thread;
    Handler handler;
    private int counter;
    HandlerThread bgHandlerThread;
    Handler bgHandler;
    AsyncTest asyncTest;
    private SmsReceiver smsReceiver;

    @Override
    public Loader<Object> onCreateLoader(int id, Bundle args) {
        if (id == 1) {
            return new AdderAsynTestLoader(getActivity(), 10, 20);
        }
        return null;
    }

    @Override
    public void onLoadFinished(Loader<Object> loader, Object data) {
        if (loader.getId() == 1) {
            Integer result = (Integer) data;
            binding.tvResult.setText(String.valueOf(result));
        }
    }

    @Override
    public void onLoaderReset(Loader<Object> loader) {

    }

    public static SecondFragment newInstance() {
        return new SecondFragment();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_second, container, false);

        View rootView = binding.getRoot();

        initInstance();
        return rootView;
    }

    private void initInstance() {

        binding.test.setOnCircleClickListener(position -> {
            Log.d(TAG, "onClick: " + position);
        });

        smsReceiver = new SmsReceiver();


//        thread = new Thread(new Runnable() {
//            @Override
//            public void run() {
//                for (int i = 0; i < 100; i++) {
//                    counter++;
//                    try {
//                        Thread.sleep(1000);
//                    } catch (InterruptedException e) {
//                        return;
//                    }
//
//                    getActivity().runOnUiThread(new Runnable() {
//                        @Override
//                        public void run() {
//                            binding.tvResult.setText(counter + "");
//                        }
//                    });
//                }
//            }
//        });
//        thread.start();

//        thread = new Thread(new Runnable() {
//            @Override
//            public void run() {
//                for (int i = 0; i < 100; i++) {
//                    counter++;
//                    try {
//                        Thread.sleep(1000);
//                    } catch (InterruptedException e) {
//                        return;
//                    }
//
//                    Message message = new Message();
//                    message.arg1 = counter;
//                    handler.sendMessage(message);
//                }
//            }
//        });
//        thread.start();
//
//        handler = new Handler(Looper.getMainLooper()) {
//            @Override
//            public void handleMessage(Message msg) {
//                super.handleMessage(msg);
//                binding.tvResult.setText(msg.arg1 + "");
//            }
//        };

//        handler = new Handler(Looper.getMainLooper()) {
//            @Override
//            public void handleMessage(Message msg) {
//                super.handleMessage(msg);
//                counter++;
//                binding.tvResult.setText(counter + "");
//                if (counter < 100)
//                    sendEmptyMessageDelayed(0, 1000);
//            }
//        };
//
//        handler.sendEmptyMessageDelayed(0, 1000);

//        bgHandlerThread = new HandlerThread("Kondeelov");
//        bgHandlerThread.start();
//
//        bgHandler = new Handler(bgHandlerThread.getLooper()) {
//            @Override
//            public void handleMessage(Message msg) {
//                super.handleMessage(msg);
//
//                Message message = new Message();
//                message.arg1 = msg.arg1 + 1;
//                handler.sendMessage(message);
//            }
//        };
//
//        handler = new Handler(Looper.getMainLooper()) {
//            @Override
//            public void handleMessage(Message msg) {
//                super.handleMessage(msg);
//
//                binding.tvResult.setText(msg.arg1 + "");
//                if (msg.arg1 < 100) {
//                    Message message = new Message();
//                    message.arg1 = msg.arg1;
//                    bgHandler.sendMessageDelayed(message, 1000);
//                }
//            }
//        };
//
//        Message message = new Message();
//        message.arg1 = 0;
//        bgHandler.sendMessageDelayed(message, 1000);

//        asyncTest = new AsyncTest();
//        asyncTest.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, 0, 100);

        getActivity().getSupportLoaderManager()
                .initLoader(1, null, this);
    }

    static class AdderAsynTestLoader extends AsyncTaskLoader<Object> {

        int a;
        int b;
        Integer result;

        public AdderAsynTestLoader(Context context, int a, int b) {
            super(context);
            this.a = a;
            this.b = b;
        }

        @Override
        public Integer loadInBackground() {
            try {
                Thread.sleep(5000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            result = a + b;
            return result;
        }

        @Override
        protected void onStartLoading() {
            super.onStartLoading();
            if (result != null) {
                deliverResult(result);
            }
            forceLoad();
        }

        @Override
        protected void onStopLoading() {
            super.onStopLoading();

        }
    }

    class AsyncTest extends AsyncTask<Integer, Float, Boolean> {

        @Override
        protected Boolean doInBackground(Integer... params) {
            int start = params[0];
            int end = params[1];
            for (int i = 0; i < end; i++) {
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    return false;
                }

                publishProgress(Float.valueOf(i));
            }

            return true;
        }

        @Override
        protected void onProgressUpdate(Float... values) {
            super.onProgressUpdate(values);

            float progress = values[0];
            binding.tvResult.setText(progress + "");
        }

        @Override
        protected void onPostExecute(Boolean aBoolean) {
            super.onPostExecute(aBoolean);


        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();

//        thread.interrupt();
//        bgHandlerThread.quit();

//        asyncTest.cancel(true);
    }

    @Override
    public void onResume() {
        super.onResume();
        registerReceiver();
    }

    @Override
    public void onPause() {
        super.onPause();
        unregisterReceiver();
    }

    private void registerReceiver() {
        if (ContextCompat.checkSelfPermission(getActivity(), android.Manifest.permission.READ_SMS) == PackageManager.PERMISSION_GRANTED) {
            Log.d(TAG, "registerReceiver: ");

            IntentFilter intentFilter = new IntentFilter();
            intentFilter.addAction("android.permission.RECEIVE_SMS");
            intentFilter.addAction("android.provider.Telephony.SMS_RECEIVED");

            getActivity().registerReceiver(smsReceiver, intentFilter);
        }
    }

    private void unregisterReceiver() {
        if (ContextCompat.checkSelfPermission(getActivity(), android.Manifest.permission.READ_SMS) == PackageManager.PERMISSION_GRANTED
                || smsReceiver.isOrderedBroadcast()) {
            getActivity().unregisterReceiver(smsReceiver);
        }
    }
    /*******************
     * Listener Zone
     *******************/


}
