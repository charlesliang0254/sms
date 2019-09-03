package cn.edu.chd.sms.util;

public class JsonResult {
    //我们自己设置的服务器向客户端输出的状态码
    //1-ok，0-false
    private int state=1;
    //状态码对应的具体信息
    private String message="ok";
    //业务层返回给控制层的具体数据
    private Object data;
    public JsonResult() {
    }

    public JsonResult(String message) {
        this.message = message;
    }

    public JsonResult(String message, Object data) {
        this.message = message;
        this.data = data;
    }

    public JsonResult(Object data) {
        this.data = data;
    }

    public JsonResult(Throwable e) {
        this.state=0;
        this.message=e.getMessage();
    }

    public int getState() {
        return state;
    }
    public void setState(int state) {
        this.state = state;
    }
    public String getMessage() {
        return message;
    }
    public void setMessage(String message) {
        this.message = message;
    }
    public Object getData() {
        return data;
    }
    public void setData(Object data) {
        this.data = data;
    }

}
