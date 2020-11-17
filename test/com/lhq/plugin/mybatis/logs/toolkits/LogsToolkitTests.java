package com.lhq.plugin.mybatis.logs.toolkits;

import org.junit.Test;

import com.lhq.plugin.mybatis.logs.console.SystemOutput;

public class LogsToolkitTests {

    /**
     * SQL参数中包含null的情况
     */
    @Test
    public void test01() {
        StringBuffer buffer = new StringBuffer();
        buffer.append("[debug:159] ==>  Preparing: UPDATE T_TAX_WAYBILL_EXPRESS SET EXPRESS_URL = ?, EXPRESS_NO = ?, EXPRESS_COMPANY = ?, EXPRESS_CHECK_STATE = ?, FAIL_MSG = ?, EXPRESS_UPLOAD_TIME = SYSDATE, LAST_MODIFIED_TIME = SYSDATE WHERE TAX_WAYBILL_ID = ?").append(System.getProperty("line.separator"));
        buffer.append("[debug:159] ==> Parameters: http://abc.png(String), 123456(String), 顺丰快递(String), 1(String), null, 4100400(String)").append(System.getProperty("line.separator"));
        buffer.append("[debug:27] {pstm-100001} Executing Statement:        insert into t_teacher (id, name) values(?, ?) ").append(System.getProperty("line.separator"));
        buffer.append("[debug:27] {pstm-100001} Parameters: [1000000, null]").append(System.getProperty("line.separator"));
        
        LogsToolkit.setOutput(SystemOutput.output);
        LogsToolkit.parseText(buffer.toString());
    }
    
    /**
     * SQL没有参数的情况
     */
    @Test
    public void test02() {
        StringBuffer buffer = new StringBuffer();
        buffer.append("[debug:159] ==>  Preparing: UPDATE T_TAX_WAYBILL_EXPRESS SET EXPRESS_URL = 1 WHERE TAX_WAYBILL_ID = 2").append(System.getProperty("line.separator"));
        buffer.append("[debug:159] ==> Parameters: ").append(System.getProperty("line.separator"));
        buffer.append("[debug:27] {pstm-100001} Executing Statement:        insert into t_teacher (id, name) values(1, 1) ").append(System.getProperty("line.separator"));
        buffer.append("[debug:27] {pstm-100001} Parameters: []").append(System.getProperty("line.separator"));
        
        LogsToolkit.setOutput(SystemOutput.output);
        LogsToolkit.parseText(buffer.toString());
    }
    
    
    /**
     * SQL参数中包含“, ”的情况
     */
    @Test
    public void test03() {
        StringBuffer buffer = new StringBuffer();
        buffer.append("[debug:159] ==>  Preparing: UPDATE T_TAX_WAYBILL_EXPRESS SET EXPRESS_URL = ?, EXPRESS_NO = ?, EXPRESS_COMPANY = ?, EXPRESS_CHECK_STATE = ?, FAIL_MSG = ?, EXPRESS_UPLOAD_TIME = SYSDATE, LAST_MODIFIED_TIME = SYSDATE WHERE TAX_WAYBILL_ID = ?").append(System.getProperty("line.separator"));
        buffer.append("[debug:159] ==> Parameters: http:(a), //abc.png(String), 123456(String), 顺丰快递(String), 1(String), null, 4100400(String)").append(System.getProperty("line.separator"));
        buffer.append("[debug:27] {pstm-100001} Executing Statement:        insert into t_teacher (id, name) values(?, ?) ").append(System.getProperty("line.separator"));
        buffer.append("[debug:27] {pstm-100001} Parameters: [100, 0000, null]").append(System.getProperty("line.separator"));
        
        LogsToolkit.setOutput(SystemOutput.output);
        LogsToolkit.parseText(buffer.toString());
    }
    

}
