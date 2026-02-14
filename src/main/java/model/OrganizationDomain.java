package model;

import java.sql.Timestamp;

public class OrganizationDomain {

    private int id;
    private String corporateNumber;
    private String domainName;
    private String attributeType;
    private String authCode;
    private Timestamp authCodeExpiresAt;  // 認証コード有効期限を追加
    private int status;  // String から int に変更

    // ステータスEnum（内部クラス）
    public enum Status {
        ACTIVE(1),
        DELETED(2),
        REGISTERED(3),           // 登録
        RESERVED(4),             // 登録予約
        PENDING_APPLICATION(5),  // 申請中
        DOMAIN_ACTIVE(6),        // アクティブ（ドメイン）
        TO_BE_SUSPENDED(7),      // サスペンド予定
        SUSPENDED(8),            // サスペンド
        TO_BE_DELETED(9),        // 廃止予定
        DOMAIN_DELETED(10);      // 廃止

        private final int code;

        Status(int code) {
            this.code = code;
        }

        public int getCode() {
            return code;
        }

        public static Status fromCode(int code) {
            for (Status s : values()) {
                if (s.code == code) {
                    return s;
                }
            }
            throw new IllegalArgumentException("Invalid status code: " + code);
        }
    }

    // Getters and Setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCorporateNumber() {
        return corporateNumber;
    }

    public void setCorporateNumber(String corporateNumber) {
        this.corporateNumber = corporateNumber;
    }

    public String getDomainName() {
        return domainName;
    }

    public void setDomainName(String domainName) {
        this.domainName = domainName;
    }

    public String getAttributeType() {
        return attributeType;
    }

    public void setAttributeType(String attributeType) {
        this.attributeType = attributeType;
    }

    public String getAuthCode() {
        return authCode;
    }

    public void setAuthCode(String authCode) {
        this.authCode = authCode;
    }

    // 認証コード有効期限
    public Timestamp getAuthCodeExpiresAt() {
        return authCodeExpiresAt;
    }

    public void setAuthCodeExpiresAt(Timestamp authCodeExpiresAt) {
        this.authCodeExpiresAt = authCodeExpiresAt;
    }

    // status（int型で保存）
    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    // Enum として取得
    public Status getStatusEnum() {
        return Status.fromCode(this.status);
    }

    // Enum から設定
    public void setStatusEnum(Status status) {
        this.status = status.getCode();
    }
}