package com.puxtech.ybk.jiaoyi.page;

import android.app.Dialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.puxtech.ybk.Constant;
import com.puxtech.ybk.R;
import com.puxtech.ybk.helper.TextSizeHelper;
import com.puxtech.ybk.jiaoyi.TradeHelper;
import com.puxtech.ybk.jiaoyi.adapter.ThreeRowGongGaoAdapter;
import com.puxtech.ybk.jiaoyi.adapter.ThreeRowLiShiJiaoAdapter;
import com.puxtech.ybk.jiaoyi.entitydata.GongGaoData;
import com.puxtech.ybk.jiaoyi.entitydata.GongGaoDetailData;
import com.puxtech.ybk.jiaoyi.entitydata.HistoryDealData;
import com.puxtech.ybk.jiaoyi.entitydata.TuoGuanListData;
import com.puxtech.ybk.jiaoyi.httpmanager.HttpManagerNotice;
import com.puxtech.ybk.jiaoyi.responsedata.BaseResponseDataTrade;
import com.puxtech.ybk.jiaoyi.responsedata.GongGaoDetailResponseData;
import com.puxtech.ybk.jiaoyi.responsedata.GongGaoResponseData;
import com.puxtech.ybk.jiaoyi.view.TradePromptDialog;
import com.puxtech.ybk.util.ActivityUtils;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.LinkedHashMap;

public class TradeChaXunGongGao extends BaseTradeActivity implements
        View.OnClickListener {
    public static final int GONG_GAO_LIST = 1;
    public static final int GONG_GAO_DETAIL = 2;
    ListView listView;
    TextView tv_start, tv_end;
    Button bt_commit;
    ThreeRowGongGaoAdapter adapter;
    ArrayList<GongGaoData> threeRowDatas;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        TextSizeHelper.setTextSize(context);
        setContentView(R.layout.cha_xun_gonggao);
        TradeHelper.initToolBarWithSubTitleAndFinishIcon(this, getString(R.string.chaxun_gonggao), (Toolbar) findViewById(R.id.toolbar), (TextView) findViewById(R.id.tv_toolbar));

        initWidget();
        setClickListener();
    }

    private void initWidget() {
        listView = (ListView) findViewById(R.id.listView);


//        View headView = LayoutInflater.from(context).inflate(R.layout.bank_history, null);

//        recyclerView.addHeaderView(headView);
        tv_start = (TextView) findViewById(R.id.tv_start);
        tv_end = (TextView) findViewById(R.id.tv_end);
        bt_commit = (Button) findViewById(R.id.bt_commit);
        String date = ActivityUtils.getYYYYMMDDforTimeMillis(System.currentTimeMillis());
        tv_start.setText(date);
        tv_end.setText(date);
        tv_start.setOnClickListener(this);
        tv_end.setOnClickListener(this);


        threeRowDatas = new ArrayList<>();
        adapter = new ThreeRowGongGaoAdapter(context, threeRowDatas);
        listView.setAdapter(adapter);

    }


    @Override
    public void onClick(View v) {
        TradeHelper.selectData(context, v, true);
    }

    private void setClickListener() {
        bt_commit.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Calendar startCalendar = TradeHelper.getCalendarOfString(tv_start.getText().toString());
                Calendar endCalendar = TradeHelper.getCalendarOfString(tv_end.getText().toString());
                if (TradeHelper.isDateOneAfterDateTwo(startCalendar, endCalendar)) {
                    //开始日期是否晚于结束日期
                    ActivityUtils.showCenterToast(context,
                            TradeHelper.STARTTIME_MORE_ENDTIME, Toast.LENGTH_LONG);
                    return;
                }
                if (TradeHelper.isDateOneBeyongMonthDateTwo(startCalendar, endCalendar)) {
                    ActivityUtils.showCenterToast(context,
                            TradeHelper.CENTER_TIME_SHORT_ONT_MONTHS, Toast.LENGTH_LONG);
                    return;
                }
//                initHaveTimeNeedData();
                request(GONG_GAO_LIST);
            }
        });
    }

    GongGaoData gongGaoData;

    private void request(final int type) {
        putAsyncTask(new AsyncTask<Void, Void, Boolean>() {
                         BaseResponseDataTrade responseData;
                         Dialog dialog;
                         String startTime, endTime;//请求的开始和结束时间

                         protected void onPreExecute() {
                             dialog = ActivityUtils.showLoadingProgressDialog(context);

                             startTime = ActivityUtils.getTimeMillisFromYYYYMMDDset000000(tv_start.getText().toString(), true);
                             endTime = ActivityUtils.getTimeMillisFromYYYYMMDDset235959(tv_end.getText().toString(), true);

                             if (type == GONG_GAO_LIST) {
                                 threeRowDatas.clear();
                             }
                             adapter.notifyDataSetChanged();
                             super.onPreExecute();
                         }

                         @SuppressWarnings("ResourceType")
                         protected Boolean doInBackground(Void... params) {
                             try {
                                 HttpManagerNotice httpManager = new HttpManagerNotice(context);

                                 if (type == GONG_GAO_LIST) {
                                     responseData = httpManager.requestGongGaoList(startTime, endTime);
                                 } else if (type == GONG_GAO_DETAIL) {
                                     responseData = httpManager.requestGongGaoDetail(gongGaoData);

                                 }

                             } catch (Exception e) {
                                 e.printStackTrace();
                                 responseData.setRetCode(Constant.CODE_ERROR_UNKNOW);
                                 responseData.setRetMessage(Constant.MESSAGE_ERROR_UNKNOW);
                             }
                             return true;
                         }

                         protected void onPostExecute(Boolean result) {
                             super.onPostExecute(result);
                             dialog.dismiss();

                             if (0 == responseData.getRetCode()) {
                                 if (type == GONG_GAO_LIST) {

                                     ArrayList<GongGaoData> inOutHistoryEntities = ((GongGaoResponseData) responseData).getDataList();
                                     threeRowDatas.addAll(inOutHistoryEntities);
                                     adapter.notifyDataSetChanged();
                                     listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                                         @Override
                                         public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                                             gongGaoData = adapter.getItem(position);
//                                             infoId = item.getID();
                                             request(GONG_GAO_DETAIL);
                                         }
                                     });
                                 } else if (type == GONG_GAO_DETAIL) {
                                     popWindows(((GongGaoDetailResponseData) responseData).getDetailData());
                                 }
                             } else {


                                 ActivityUtils.showAlert(context, responseData.getRetMessage() + "(" + responseData.getRetCode() + ")");
                             }
                         }
                     }

        );
    }

    //弹出申购框
    public void popWindows(final GongGaoDetailData item) {
        StringBuffer sb = new StringBuffer();

        sb.append(item.getCONTENT());

        final TradePromptDialog tradePromptDialog = new TradePromptDialog(context);
        tradePromptDialog.getTradePromptDialog(item.getTITLE(),
                sb.toString(),true);

        tradePromptDialog.getLl_edittext().setVisibility(View.GONE);

    }
}