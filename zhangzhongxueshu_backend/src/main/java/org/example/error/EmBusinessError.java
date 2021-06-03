package org.example.error;

public enum EmBusinessError implements CommonError
{

    //通用错误类型10000
    PARAMETER_VALIDATION_ERROR(10001,"参数不合法"),
    UNKNOWN_ERROR(10002,"未知错误"),

    //20000开头为用户相关信息
    USER_NOT_EXIST(20001,"用户不存在哦~"),


    //30000开头为文章相关
    ARTICLE_NOT_EXIST(30001,"文章不存在哦~"),

    //40000开头为评论相关
    COMMNENT_NOT_EXIST(40001,"还没有人评价哦~");

    private EmBusinessError(int errCode, String errMsg){
        this.errCode=errCode;
        this.errMsg=errMsg;
    }

    private int errCode;
    private String errMsg;

    @Override
    public int getErrCode() {
        return this.errCode;
    }

    @Override
    public String getErrMsg() {
        return this.errMsg;
    }


    @Override
    public CommonError setErrMsg(String errMsg){
        this.errMsg=errMsg;
        return this;
    }
}
