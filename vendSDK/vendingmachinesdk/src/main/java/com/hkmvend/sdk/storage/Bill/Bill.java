package com.hkmvend.sdk.storage.Bill;

import com.hkmvend.sdk.storage.Menu.MenuEntry;

import io.realm.RealmList;
import io.realm.RealmObject;

/**
 * Created by hesk on 26/1/16.
 */
public class Bill extends RealmObject {

    private long bill_number_code;
    private RealmList<MenuEntry> orders;
    private String signed_staff;
    private String start_time;
    private String pay_time;
    private String bill_void_sign;
    private String bill_void_time;

    private String table_id;
    private String table_remark;

    private int headcount;
    private float consolidated_payment;

    public long getBill_number_code() {
        return bill_number_code;
    }

    public void setBill_number_code(long bill_number_code) {
        this.bill_number_code = bill_number_code;
    }

    public String getBill_void_sign() {
        return bill_void_sign;
    }

    public void setBill_void_sign(String bill_void_sign) {
        this.bill_void_sign = bill_void_sign;
    }

    public String getBill_void_time() {
        return bill_void_time;
    }

    public void setBill_void_time(String bill_void_time) {
        this.bill_void_time = bill_void_time;
    }

    public float getConsolidated_payment() {
        return consolidated_payment;
    }

    public void setConsolidated_payment(float consolidated_payment) {
        this.consolidated_payment = consolidated_payment;
    }

    public RealmList<MenuEntry> getOrders() {
        return orders;
    }

    public void setOrders(RealmList<MenuEntry> orders) {
        this.orders = orders;
    }

    public String getPay_time() {
        return pay_time;
    }

    public void setPay_time(String pay_time) {
        this.pay_time = pay_time;
    }

    public String getSigned_staff() {
        return signed_staff;
    }

    public void setSigned_staff(String signed_staff) {
        this.signed_staff = signed_staff;
    }

    public String getStart_time() {
        return start_time;
    }

    public int getHeadcount() {
        return headcount;
    }

    public void setHeadcount(int headcount) {
        this.headcount = headcount;
    }

    public String getTable_id() {
        return table_id;
    }

    public void setTable_id(String table_id) {
        this.table_id = table_id;
    }

    public String getTable_remark() {
        if (table_remark == null) return "";
        else
            return table_remark;
    }

    public void setTable_remark(String table_remark) {
        this.table_remark = table_remark;
    }

    public void setStart_time(String start_time) {
        this.start_time = start_time;
    }
}
