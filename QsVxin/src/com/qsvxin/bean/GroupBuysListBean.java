package com.qsvxin.bean;

import java.io.Serializable;

import com.qsvxin.db.BaseDbBean;
import com.qsvxin.db.ColumnAnnotation;

/**
 * 
 * 团购列表javaBean
 */
public class GroupBuysListBean  extends BaseDbBean implements Serializable {
    private static final long serialVersionUID = 1L;
    
    public static final String TABLE_NAME = "GroupBuyList";
    
    public static final String COLUMN_GROUP_BUYS_ID = "GroupBuysID";
    public static final String COLUMN_GROUP_BUYS_TITLE = "GroupBuysTitle";
    
    @ColumnAnnotation(column = COLUMN_GROUP_BUYS_ID, info = "text unique")
    public String strGroupBuysID;
    @ColumnAnnotation(column = COLUMN_GROUP_BUYS_TITLE)
    public String strGroupBuysTitle;

    public String getStrGroupBuysID() {
        return strGroupBuysID;
    }
    public void setStrGroupBuysID(String strGroupBuysID) {
        this.strGroupBuysID = strGroupBuysID;
    }
    public String getStrGroupBuysTitle() {
        return strGroupBuysTitle;
    }

    public void setStrGroupBuysTitle(String strGroupBuysTitle) {
        this.strGroupBuysTitle = strGroupBuysTitle;
    }


    @Override
    public String toString() {
        return "GroupBuysBean [strGroupBuysID=" + strGroupBuysID + ", strGroupBuysTitle=" + strGroupBuysTitle + "]";
    }
}
