package cn.itcast.domain;

/**
 * @Auther : 32725
 * @Date: 2019/2/14 11:19
 * @Description: 自定义异常处理类
 */
public class SysException extends Exception{

    private String errorMsg;

    public SysException(String errorMsg){
        //必须提供异常信息
        this.errorMsg=errorMsg;
    }


    public String getErrorMsg() {
        return errorMsg;
    }
    public void setErrorMsg(String errorMsg) {
        this.errorMsg = errorMsg;
    }
}
