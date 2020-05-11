package com.nfplus.bean;

import javax.persistence.*;
import java.util.Date;

@Table(name = "sys_op_log")
public class SysOpLog {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String operation;

    private String url;

    private String method;

    @Column(name = "mac_addr")
    private String macAddr;

    @Column(name = "ip_addr")
    private String ipAddr;

    private Integer status;

    @Column(name = "create_time")
    private Date createTime;

    @Column(name = "requ_params")
    private String requParams;

    @Column(name = "resp_params")
    private String respParams;

    /**
     * @return id
     */
    public Integer getId() {
        return id;
    }

    /**
     * @param id
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * @return operation
     */
    public String getOperation() {
        return operation;
    }

    /**
     * @param operation
     */
    public void setOperation(String operation) {
        this.operation = operation;
    }

    /**
     * @return url
     */
    public String getUrl() {
        return url;
    }

    /**
     * @param url
     */
    public void setUrl(String url) {
        this.url = url;
    }

    /**
     * @return method
     */
    public String getMethod() {
        return method;
    }

    /**
     * @param method
     */
    public void setMethod(String method) {
        this.method = method;
    }

    /**
     * @return mac_addr
     */
    public String getMacAddr() {
        return macAddr;
    }

    /**
     * @param macAddr
     */
    public void setMacAddr(String macAddr) {
        this.macAddr = macAddr;
    }

    /**
     * @return ip_addr
     */
    public String getIpAddr() {
        return ipAddr;
    }

    /**
     * @param ipAddr
     */
    public void setIpAddr(String ipAddr) {
        this.ipAddr = ipAddr;
    }

    /**
     * @return status
     */
    public Integer getStatus() {
        return status;
    }

    /**
     * @param status
     */
    public void setStatus(Integer status) {
        this.status = status;
    }

    /**
     * @return create_time
     */
    public Date getCreateTime() {
        return createTime;
    }

    /**
     * @param createTime
     */
    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    /**
     * @return requ_params
     */
    public String getRequParams() {
        return requParams;
    }

    /**
     * @param requParams
     */
    public void setRequParams(String requParams) {
        this.requParams = requParams;
    }

    /**
     * @return resp_params
     */
    public String getRespParams() {
        return respParams;
    }

    /**
     * @param respParams
     */
    public void setRespParams(String respParams) {
        this.respParams = respParams;
    }
}