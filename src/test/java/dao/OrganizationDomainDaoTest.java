package dao;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mockito.MockedStatic;

import model.OrganizationDomain;

/**
 * OrganizationDomainDao テスト
 * 実際のDBを使用するためPostgreSQLが起動している必要があります
 */
public class OrganizationDomainDaoTest {

    private OrganizationDomainDao dao;
    private int                   testId;

    @Before
    public void setUp() throws Exception {
        dao = new OrganizationDomainDao();
        // テストデータをINSERT
        testId = insertTestData();
    }

    @After
    public void tearDown() throws Exception {
        // テストデータをDELETE
        deleteTestData(testId);
    }

    // ============================================================
    // insert テスト
    // ============================================================

    @Test
    public void testInsert_正常登録() throws Exception {
        OrganizationDomain domain = buildTestDomain("9999999999001", "insert-test.co.jp", "co.jp");
        int newId = dao.insert(domain);
        assertTrue(newId > 0);

        // 登録されたデータを確認
        OrganizationDomain saved = dao.findById(newId);
        assertNotNull(saved);
        assertEquals("9999999999001", saved.getCorporateNumber());
        assertEquals("insert-test.co.jp", saved.getDomainName());
        assertEquals("co.jp", saved.getAttributeType());
        assertNotNull(saved.getAuthCode());           // auth_codeが自動生成されていること
        assertNotNull(saved.getAuthCodeExpiresAt());  // 有効期限が設定されていること

        // クリーンアップ
        deleteTestData(newId);
    }

    // ============================================================
    // findById テスト
    // ============================================================

    @Test
    public void testFindById_存在するID_取得できる() throws Exception {
        OrganizationDomain result = dao.findById(testId);
        assertNotNull(result);
        assertEquals(testId, result.getId());
        assertEquals("9999999999999", result.getCorporateNumber());
        assertEquals("test.co.jp", result.getDomainName());
    }

    @Test
    public void testFindById_存在しないID_nullを返す() throws Exception {
        OrganizationDomain result = dao.findById(-1);
        assertNull(result);
    }

    // ============================================================
    // findById SQLException テスト
    // ============================================================

    @Test(expected = RuntimeException.class)
    public void testFindById_SQLException_RuntimeExceptionをスロー() throws Exception {
        // 存在しないポートに接続してSQLExceptionを発生させる
        try (MockedStatic<DBManager> mockedStatic = mockStatic(DBManager.class)) {
            mockedStatic.when(DBManager::getConnection)
                        .thenThrow(new SQLException("DB接続エラー"));
            dao.findById(testId);
        }
    }

    // ============================================================
    // findByIdForUpdate テスト
    // ============================================================

    @Test
    public void testFindByIdForUpdate_存在するID_取得できる() throws Exception {
        OrganizationDomain result = dao.findByIdForUpdate(testId);
        assertNotNull(result);
        assertEquals(testId, result.getId());
        assertEquals("9999999999999", result.getCorporateNumber());
        assertEquals("test.co.jp", result.getDomainName());
    }

    @Test
    public void testFindByIdForUpdate_存在しないID_nullを返す() throws Exception {
        OrganizationDomain result = dao.findByIdForUpdate(-1);
        assertNull(result);
    }

    // ============================================================
    // findAll テスト
    // ============================================================

    @Test
    public void testFindAll_1件以上取得できる() throws Exception {
        assertFalse(dao.findAll().isEmpty());
    }

    // ============================================================
    // findByDomainName テスト
    // ============================================================

    @Test
    public void testFindByDomainName_完全一致_取得できる() throws Exception {
        OrganizationDomain result = dao.findByDomainName("test.co.jp");
        assertNotNull(result);
        assertEquals("test.co.jp", result.getDomainName());
    }

    @Test
    public void testFindByDomainName_存在しないドメイン名_nullを返す() throws Exception {
        OrganizationDomain result = dao.findByDomainName("notexist.co.jp");
        assertNull(result);
    }

    // ============================================================
    // update テスト
    // ============================================================

    @Test
    public void testUpdate_正常更新() throws Exception {
        OrganizationDomain domain = dao.findById(testId);
        domain.setDomainName("updated.co.jp");
        dao.update(domain);

        OrganizationDomain updated = dao.findById(testId);
        assertEquals("updated.co.jp", updated.getDomainName());
    }

    // ============================================================
    // delete テスト
    // ============================================================

    @Test
    public void testDelete_正常削除() throws Exception {
        // 削除用のデータを別途INSERT
        int deleteId = insertTestData("9999999999998", "delete-test.co.jp", "co.jp");

        dao.delete(deleteId);

        OrganizationDomain result = dao.findById(deleteId);
        assertNull(result);
    }

    // ============================================================
    // existsActiveDomainForOrg テスト
    // ============================================================

    @Test
    public void testExistsActiveDomainForOrg_存在する_trueを返す() throws Exception {
        boolean result = dao.existsActiveDomainForOrg("9999999999999", "co.jp");
        assertTrue(result);
    }

    @Test
    public void testExistsActiveDomainForOrg_存在しない_falseを返す() throws Exception {
        boolean result = dao.existsActiveDomainForOrg("0000000000000", "co.jp");
        assertFalse(result);
    }

    // ============================================================
    // updateAuthCodeExpiresAt テスト
    // ============================================================

    @Test
    public void testUpdateAuthCodeExpiresAt_正常更新() throws Exception {
        // 更新前の有効期限を取得
        OrganizationDomain before = dao.findById(testId);
        assertNotNull(before.getAuthCodeExpiresAt());

        // 有効期限を更新
        dao.updateAuthCodeExpiresAt(testId);

        // 更新後の有効期限を取得
        OrganizationDomain after = dao.findById(testId);
        assertNotNull(after.getAuthCodeExpiresAt());
    }

    // ============================================================
    // ヘルパーメソッド
    // ============================================================

    private int insertTestData() throws Exception {
        return insertTestData("9999999999999", "test.co.jp", "co.jp");
    }

    private int insertTestData(String corporateNumber, String domainName, String attributeType)
            throws Exception {
        OrganizationDomain domain = buildTestDomain(corporateNumber, domainName, attributeType);
        return dao.insert(domain);
    }

    private OrganizationDomain buildTestDomain(String corporateNumber, String domainName,
                                                String attributeType) {
        OrganizationDomain domain = new OrganizationDomain();
        domain.setCorporateNumber(corporateNumber);
        domain.setDomainName(domainName);
        domain.setAttributeType(attributeType);
        domain.setHost1("192.168.0.1");
        domain.setHost2("192.168.0.2");
        domain.setHost3("192.168.0.3");
        domain.setHost4("192.168.0.4");
        domain.setHost5("192.168.0.5");
        return domain;
    }

    private void deleteTestData(int id) throws Exception {
        String sql = "DELETE FROM organization_domain WHERE id = ?";
        try (Connection con = DBManager.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, id);
            ps.executeUpdate();
        }
    }
}