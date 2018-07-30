package com.example.suguoqing.historyisnow;

import java.util.List;

public class History {
    /* "error_code": 0, 返回码
             "reason": "请求成功！",
             "result": [
    {
        "day": 1,  日
            "des": "1907年11月1日 电影导演吴永刚诞生 　　吴永刚，1907年11月1日生于江苏吴县。1932年后参加影片《三个摩登女性》、《母性之光》的拍摄工作。1934年在联华影片公司编导处女作《神女》，一举成名，...",  描述
            "id": 9000,  事件ID
            "lunar": "丁未年九月廿六",
            "month": 11,  月份
            "pic": "",  图片
            "title": "电影导演吴永刚诞生",  事件标题
            "year": 1907  年份
    },*/

    private String error_code;
    private String reason;
    private List<Detail> result;

    public String getError_code() {
        return error_code;
    }

    public void setError_code(String error_code) {
        this.error_code = error_code;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public List<Detail> getResult() {
        return result;
    }

    public void setResult(List<Detail> result) {
        this.result = result;
    }

    @Override
    public String toString() {
        return "History{" +
                "error_code='" + error_code + '\'' +
                ", reason='" + reason + '\'' +
                ", result=" + result +
                '}';
    }
}
