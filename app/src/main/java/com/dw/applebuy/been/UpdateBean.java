package com.dw.applebuy.been;

import com.wlj.base.util.MathUtil;

/**
 * Created by Administrator on 2017/2/28.
 */
public class UpdateBean {

    /**
     * version :
     * download_url :
     * title : 更新提醒
     * message : 发现有新，版本是否更新？
     */

    private String version;
    private String download_url;
    private String title;
    private String message;

    public String getVersion() {
        return version;
    }

    /**
     * 去点,/补位数
     */
    public int codeHandle(String code) {
        code = code.replaceAll("\\.", "");
        int length = code.length();
        if (length > 5) {
            code = code.substring(0, 5);
        } else if (length < 5) {
            code += "00000";
            code = code.substring(0, 5);
        }
        return MathUtil.parseInteger(code);
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public String getDownload_url() {
        return download_url;
    }

    public void setDownload_url(String download_url) {
        this.download_url = download_url;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
