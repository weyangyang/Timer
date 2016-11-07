/*package netutils.impl;

import java.util.HashMap;
import java.util.Map;

import config.Constants;
import netutils.impl.interf.NetDataCallBackInterf;

public class UpdateApp extends BaseNet4Service {
	public void getUpdateData(){
		Map<String,Object> mMap = new HashMap<String, Object>();
		mMap.put("strUrl", "");
		mMap.put("userName", "");
		super.uploadData(mMap, new NetDataCallBackInterf() {
			
			@Override
			public void getNetErrData(int code, String strError) {
				switch(code){
				case Constants.CLIENT_PROTOCOL_EXCEPTION:
					break;
				case Constants.IO_EXCEPTION:
					break;
				case Constants.EXEPTION:
					break;
				}
				
			}

			@Override
			public void getNetData(int statusCode, String strJson) {
				// TODO Auto-generated method stub
				
			}
			

			
		});
		
	}

	


	
	
}
*/