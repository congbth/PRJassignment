package model;

import java.sql.Date;

public class Request {
    private int id;
    private String title;
    private Date fromDate;
    private Date toDate;
    private String reason;
    private String status;
    private int createdById;
    private int processedById;

    // ✅ Thêm các trường dùng để hiển thị
    private String createdByName;
    private String processedByName;

    // --- GET & SET ---
    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }
    public void setTitle(String title) {
        this.title = title;
    }

    public Date getFromDate() {
        return fromDate;
    }
    public void setFromDate(Date fromDate) {
        this.fromDate = fromDate;
    }

    public Date getToDate() {
        return toDate;
    }
    public void setToDate(Date toDate) {
        this.toDate = toDate;
    }

    public String getReason() {
        return reason;
    }
    public void setReason(String reason) {
        this.reason = reason;
    }

    public String getStatus() {
        return status;
    }
    public void setStatus(String status) {
        this.status = status;
    }

    public int getCreatedById() {
        return createdById;
    }
    public void setCreatedById(int createdById) {
        this.createdById = createdById;
    }

    public int getProcessedById() {
        return processedById;
    }
    public void setProcessedById(int processedById) {
        this.processedById = processedById;
    }

    public String getCreatedByName() {
        return createdByName;
    }
    public void setCreatedByName(String createdByName) {
        this.createdByName = createdByName;
    }

    public String getProcessedByName() {
        return processedByName;
    }
    public void setProcessedByName(String processedByName) {
        this.processedByName = processedByName;
    }
}
