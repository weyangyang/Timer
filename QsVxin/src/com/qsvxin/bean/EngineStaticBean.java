package com.qsvxin.bean;

import java.io.Serializable;
/**
 * 在用完后，释放内存
 * @author liyusheng
 *
 */
public class EngineStaticBean implements Serializable {
    private static final long serialVersionUID = -7973319872716191766L;
    private static final EngineStaticBean mTestBean =new EngineStaticBean();
    private EngineStaticBean(){};
    
    public static final synchronized EngineStaticBean getInstance(){
        return mTestBean;
    }
    public MemberBean mMemberBean;
    public GroupBuysListBean groupBuysListBean;
    public GetUserMsgListBean userMsgListBean;
    
    
    /**
	 * @return the userMsgListBean
	 */
	public GetUserMsgListBean getUserMsgListBean() {
		return userMsgListBean;
	}

	/**
	 * @param userMsgListBean the userMsgListBean to set
	 */
	public void setUserMsgListBean(GetUserMsgListBean userMsgListBean) {
		this.userMsgListBean = userMsgListBean;
	}

	/**
	 * @return the groupBuysListBean
	 */
	public GroupBuysListBean getGroupBuysListBean() {
		return groupBuysListBean;
	}

	/**
	 * @param groupBuysListBean the groupBuysListBean to set
	 */
	public void setGroupBuysListBean(GroupBuysListBean groupBuysListBean) {
		this.groupBuysListBean = groupBuysListBean;
	}

	public MemberBean getmMemberBean() {
        return mMemberBean;
    }

    public void setmMemberBean(MemberBean mMemberBean) {
        this.mMemberBean = mMemberBean;
    }
}
