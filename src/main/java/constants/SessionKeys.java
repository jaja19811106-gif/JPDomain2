package constants;

import javax.servlet.http.HttpSession;

/**
 * セッションキー定数クラス
 */
public class SessionKeys {

    // ドメイン情報変更
    public static final String DOMAIN_UPDATE_FORM    = "domainUpdateForm";
    public static final String UPDATE_TARGET_DOMAIN  = "updateTargetDomain";
    public static final String UPDATED_DOMAIN_NAME   = "updatedDomainName";

    // Transfer Out
    public static final String TRANSFER_OUT_DOMAIN   = "transferOutDomain";

    // Transfer In
    public static final String TRANSFER_IN_FORM      = "transferInForm";
    public static final String TRANSFER_IN_DOMAIN    = "transferInDomain";

    // ドメイン情報変更セッションクリア
    public static void clearDomainUpdateSession(HttpSession session) {
        session.removeAttribute(DOMAIN_UPDATE_FORM);
        session.removeAttribute(UPDATE_TARGET_DOMAIN);
        session.removeAttribute(UPDATED_DOMAIN_NAME);
    }

    // Transfer Outセッションクリア
    public static void clearTransferOutSession(HttpSession session) {
        session.removeAttribute(TRANSFER_OUT_DOMAIN);
    }

    // Transfer Inセッションクリア
    public static void clearTransferInSession(HttpSession session) {
        session.removeAttribute(TRANSFER_IN_FORM);
        session.removeAttribute(TRANSFER_IN_DOMAIN);
    }
}
