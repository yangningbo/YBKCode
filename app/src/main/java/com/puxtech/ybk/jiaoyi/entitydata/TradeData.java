package com.puxtech.ybk.jiaoyi.entitydata;

import java.util.ArrayList;

/**
 * Created by mac on 15/11/5.
 */
public class TradeData {
//    AccountInfoData accountInfoData;//账户信息
    ArrayList<CommodityData> commodityDataList;//商品列表
    ArrayList<HoldDetailData> holdDetailDataList;//持仓明细列表
    ArrayList<CommodityFeeData> commodityFeeDataList;//费用列表

//    ArrayList<HoldTotalData> holdTotalDataList;//持仓汇总
//    ArrayList<TradeTrustData> trustDataList;//委托单列表
//    ArrayList<TradeDealData> dealDataList;//今日成交
//    ArrayList<TradeReportDealData> reportDealDataList;

//    OtherFirmData otherFirmData;//对方交易员


    public ArrayList<CommodityFeeData> getCommodityFeeDataList() {
        return commodityFeeDataList;
    }

    public void setCommodityFeeDataList(ArrayList<CommodityFeeData> commodityFeeDataList) {
        this.commodityFeeDataList = commodityFeeDataList;
    }

    public ArrayList<CommodityData> getCommodityDataList() {
        return commodityDataList;
    }

    public void setCommodityDataList(ArrayList<CommodityData> commodityDataList) {
        this.commodityDataList = commodityDataList;
    }

    public ArrayList<HoldDetailData> getHoldDetailDataList() {
        return holdDetailDataList;
    }

    public void setHoldDetailDataList(ArrayList<HoldDetailData> holdDetailDataList) {
        this.holdDetailDataList = holdDetailDataList;
    }
}