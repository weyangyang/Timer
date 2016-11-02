package netutils.engine;

import sgecore.utils.LogUtils;

public abstract class PostFileCallBack extends NetReqCallBack{
public  void fileNotExist(){
	LogUtils.e("上传的文件不存在");
}
}
