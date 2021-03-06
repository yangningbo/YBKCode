package com.puxtech.ybk.hangqing.detail;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.puxtech.ybk.R;
import com.puxtech.ybk.hangqing.BaseHangQingFragment;
import com.puxtech.ybk.hangqing.charts.FenShiChartModel;
import com.puxtech.ybk.hangqing.charts.MiniFenShiView;
import com.puxtech.ybk.hangqing.charts.TradeTimeManager;
import com.puxtech.ybk.hangqing.jsondata.getpriceforcommodity.GetPriceForCommodityRepBody;
import com.puxtech.ybk.hangqing.jsondata.getpriceforcommodity.WuDangBuy;
import com.puxtech.ybk.hangqing.jsondata.getpriceforcommodity.WuDangSell;
import com.puxtech.ybk.hangqing.util.PriceUtil;

import java.util.List;

/**
 * Created by fanshuo on 16/5/5.
 */
public class PanKouFragment extends BaseHangQingFragment {

    private int commodityId;
    private WuDangMingXiViewController wuDangMingXiViewController;//五档明细view控制器
    private MiniFenShiView miniFenShiView;
    private TextView tvWeiBi,tvXianLiang,tvNeiPan,tvJinE,tvWeiCha,tvLiangBi,tvWaiPan,tvZhenFu;
    private LinearLayout layoutPanKou,layoutMingXi;//五档和明细layout
    private Button btnPanKou,btnMingXi;//切换五档明细的按钮

    public static PanKouFragment getFragment(int commodityId){
        Bundle bundle = new Bundle();
        bundle.putInt("commodityId", commodityId);
        PanKouFragment fragment = new PanKouFragment();
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        commodityId = getArguments().getInt("commodityId");
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view  = inflater.inflate(R.layout.hangqing_fragment_pankou, container, false);
        wuDangMingXiViewController = new WuDangMingXiViewController(context, view, myApplication.getHangQingData().getCommodityById(commodityId));
        miniFenShiView = (MiniFenShiView) view.findViewById(R.id.fenshi_view);
        miniFenShiView.setTradeTimeManager(new TradeTimeManager(myApplication.getHangQingData().getTradeTime().getStat(), myApplication.getHangQingData().getTradeTime().getEndt()));
        miniFenShiView.invalidate();
        initWidget(view);
        return view;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        wuDangMingXiViewController.onDestroy();
    }

    //private methods
    private void initWidget(View contentView){
        //底部价格参数
        tvWeiBi = (TextView) contentView.findViewById(R.id.tv_weibi);
        tvXianLiang = (TextView) contentView.findViewById(R.id.tv_xianliang);
        tvNeiPan = (TextView) contentView.findViewById(R.id.tv_neipan);
        tvJinE = (TextView) contentView.findViewById(R.id.tv_jine);
        tvWeiCha = (TextView) contentView.findViewById(R.id.tv_weicha);
        tvLiangBi = (TextView) contentView.findViewById(R.id.tv_liangbi);
        tvWaiPan = (TextView) contentView.findViewById(R.id.tv_waipan);
        tvZhenFu = (TextView) contentView.findViewById(R.id.tv_zhenfu);
        //盘口明细
        layoutPanKou = (LinearLayout) contentView.findViewById(R.id.layout_pankou);
        layoutMingXi = (LinearLayout) contentView.findViewById(R.id.layout_mingxi);
        btnPanKou = (Button) contentView.findViewById(R.id.btn_pankou);
        btnMingXi = (Button) contentView.findViewById(R.id.btn_mingxi);
        btnPanKou.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btnPanKou.setBackgroundResource(R.drawable.hangqing_pankoumingxi_selectedl_bgd);
                btnMingXi.setBackgroundResource(R.drawable.hangqing_pan_kou_r_selector);
                layoutPanKou.setVisibility(View.VISIBLE);
                layoutMingXi.setVisibility(View.GONE);
            }
        });
        btnMingXi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btnPanKou.setBackgroundResource(R.drawable.hangqing_pan_kou_l_selector);
                btnMingXi.setBackgroundResource(R.drawable.hangqing_pankoumingxi_selectedr_bgd);
                layoutPanKou.setVisibility(View.GONE);
                layoutMingXi.setVisibility(View.VISIBLE);
            }
        });
    }

    private void refreshBottomPrice(GetPriceForCommodityRepBody rep){
        //委比和委差
        String weiBiString = "--";
        String weiChaString = "--";
        List<WuDangBuy> wuDangBuyList = rep.getExtd().getFivd().getBuyd();
        List<WuDangSell> wuDangSellList = rep.getExtd().getFivd().getSeld();
        int buyCount = 0,sellCount=0;
        for (int i = 0; i < wuDangBuyList.size(); i++) {
            WuDangBuy wuDangBuy = wuDangBuyList.get(i);
            buyCount += wuDangBuy.getList();
        }
        for (int i = 0; i < wuDangSellList.size(); i++) {
            WuDangSell wuDangSell = wuDangSellList.get(i);
            sellCount += wuDangSell.getList();
        }
        if((buyCount+sellCount) > 0){
            int weiCha = buyCount - sellCount;
            float weibi = (buyCount-sellCount)/(buyCount+sellCount)*100;
            weiBiString = PriceUtil.formatPercentage(weibi);
            weiChaString = weiCha + "";
            if(weiCha > 0){
                tvWeiBi.setTextColor(getResources().getColor(R.color.hangqing_red_text));
                tvWeiCha.setTextColor(getResources().getColor(R.color.hangqing_red_text));
            }
            else if(weiCha < 0){
                tvWeiBi.setTextColor(getResources().getColor(R.color.hangqing_green_text));
                tvWeiCha.setTextColor(getResources().getColor(R.color.hangqing_green_text));
            }
            else{
                tvWeiBi.setTextColor(getResources().getColor(R.color.hangqing_white_text));
                tvWeiCha.setTextColor(getResources().getColor(R.color.hangqing_white_text));
            }

        }
        else{
            tvWeiBi.setTextColor(getResources().getColor(R.color.hangqing_white_text));
            tvWeiCha.setTextColor(getResources().getColor(R.color.hangqing_white_text));
        }
        tvWeiBi.setText(weiBiString);
        tvWeiCha.setText(weiChaString);
        //现量
        String xianLiangString = "--";
        xianLiangString = rep.getExtd().getCura()+"";
        tvXianLiang.setText(xianLiangString);
        //量比
        String liangBiString = "--";
        tvLiangBi.setText(liangBiString);
        //内盘
        String neiPanString = "--";
        neiPanString = rep.getExtd().getIna()+"";
        tvNeiPan.setText(neiPanString);
        //外盘
        String waiPanString = "--";
        waiPanString = rep.getExtd().getOuta() + "";
        tvWaiPan.setText(waiPanString);
        //金额
        String jinEString = "--";
        jinEString = rep.getTamt() + "";
        tvJinE.setText(jinEString);
        //振幅
        String zhenFuString = "--";
        if(rep.getHigp() > 0 && rep.getLowp() > 0){
            float zhenFu = (rep.getHigp() - rep.getLowp())/rep.getYclo()*100;
            zhenFuString = PriceUtil.keepPrecision(zhenFu,0);
        }
        tvZhenFu.setText(zhenFuString);

    }

    //public methods

    public void onReceivedPriceData(GetPriceForCommodityRepBody rep){
        wuDangMingXiViewController.refreshData(rep);
        refreshBottomPrice(rep);
    }

    public void onReceivedFenShiData(FenShiChartModel fenShiChartModel){
        try {
            miniFenShiView.setFenShiChartModel(fenShiChartModel);
        } catch (Exception e) {
            e.printStackTrace();
            return;
        }
        miniFenShiView.calPointXY();
        miniFenShiView.invalidate();
    }


}
