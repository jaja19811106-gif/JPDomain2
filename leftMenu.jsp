<%@ page contentType="text/html; charset=UTF-8"%>

<div class="sidebar">
    <h3>メニュー</h3>
    <ul>
        <li><a href="menu.jsp">ホーム</a></li>

        <!-- 既存 -->
        <li><a href="domainRegister.jsp">ドメイン登録（旧）</a></li>
        <li><a href="domainRegisterInput.jsp">ドメイン登録（新）</a></li>

        <!-- ★ 新しい一覧画面へのリンク -->
        <li><a href="domain2List">ドメイン一覧（新）</a></li>

        <!-- ★ 認証コード照会 -->
        <li><a href="domainAuthCodeSearch">認証コード照会</a></li>

        <!-- ★ コンタクト登録 -->
        <li><a href="contactRegisterInput.jsp">コンタクト登録</a></li>
        <li><a href="hostRegisterInput.jsp">ホスト登録</a></li>
        <li><a href="domainUpdateSearch">ドメイン情報変更</a></li>

        <!-- ★ Transfer -->
        <li><a href="transferOutSearch">Transfer Out（Lock解除）</a></li>
        <li><a href="transferInInput.jsp">Transfer In（移管申請）</a></li>

        <li><a href="domainInfo">Domain Info</a></li>
        <li><a href="logout">ログアウト</a></li>
    </ul>
</div>
