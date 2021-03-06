package com.puxtech.ybk.jiaoyi.page;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import com.puxtech.ybk.BaseActivity;
import com.puxtech.ybk.R;
import com.puxtech.ybk.helper.TextSizeHelper;
import com.puxtech.ybk.jiaoyi.TradeHelper;
import com.puxtech.ybk.jiaoyi.adapter.ChangeAccountAdapter;
import com.puxtech.ybk.util.ActivityUtils;
import com.puxtech.ybk.util.SharedPreferenceManager;

import java.util.Set;

public class ChangeAccount extends BaseTradeActivity {
    public static final String BANK_TO_TRADE = "BANK_TO_TRADE";
    public static final String TRADE_TO_BANK = "TRADE_TO_BANK";

    public static final String INIT_FUND_PASSWORD = "INIT_FUND_PASSWORD";
    public static final int BANK = 1;

    //    TextView tv_tradetobank, tv_banktotrade, tv_history;
    ListView listview;
    Button bt_add;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        TextSizeHelper.setTextSize(context);
        setContentView(R.layout.activity_change_account);
        TradeHelper.initToolBarWithSubTitleAndFinishIcon(this, "切换交易账号", (Toolbar) findViewById(R.id.toolbar), (TextView) findViewById(R.id.tv_toolbar));
        initWidget();

    }

    private void initWidget() {
        listview = (ListView) findViewById(R.id.listview);
        SharedPreferenceManager spf_WX = new SharedPreferenceManager(context, SharedPreferenceManager.LOGIN);
        Set<String> siteno = spf_WX.getStringSet(SharedPreferenceManager.LOGIN_ACCOUNT_ARRAY);
        String[] arr = siteno.toArray(new String[siteno.size()]);
        final ChangeAccountAdapter accountAdapter = new ChangeAccountAdapter(context, arr);
        listview.setAdapter(accountAdapter);
        listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view,final int position, long id) {
                if(accountAdapter.getItem(position).equals(myApplication.getTradeEntity().getOtherData().getFirmId())){

                    return;
                }



                ActivityUtils.showAlertWithConfirmText(context, "是否切换至账号"+accountAdapter.getItem(position), "确定", new Runnable() {
                    @Override
                    public void run() {

                        TradeHelper.logoutWithAccountId(context, accountAdapter.getItem(position));


                    }
                });

            }
        });

        bt_add = (Button) findViewById(R.id.bt_add);
        bt_add.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                ActivityUtils.showAlertWithConfirmText(context, "添加账号需先退出当前登录账号", "确定", new Runnable() {
                    @Override
                    public void run() {
                        TradeHelper.logoutOnly(context);

                    }
                });
            }
        });

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_home, menu);

        return false;
    }


}
