package servlet;

import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

import java.lang.reflect.Method;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.junit.Before;
import org.junit.Test;
import org.mockito.MockedConstruction;

import dao.OrganizationDomainDao;

/**
 * DomainRegisterCheckServlet バリデーションテスト
 */
public class DomainRegisterCheckServletTest {

    private DomainRegisterCheckServlet servlet;
    private HttpServletRequest         mockReq;
    private HttpServletResponse        mockResp;
    private RequestDispatcher          mockDispatcher;

    @Before
    public void setUp() {
        servlet        = new DomainRegisterCheckServlet();
        mockReq        = mock(HttpServletRequest.class);
        mockResp       = mock(HttpServletResponse.class);
        mockDispatcher = mock(RequestDispatcher.class);
    }

    // ============================================================
    // doPost テスト
    // ============================================================

    /**
     * バリデーションエラーあり → 入力画面にforward
     */
    @Test
    public void testDoPost_バリデーションエラー_入力画面へforward() throws Exception {
        // ホストアドレスが不正形式
        when(mockReq.getParameter("host1")).thenReturn("abc");
        when(mockReq.getParameter("host2")).thenReturn("");
        when(mockReq.getParameter("host3")).thenReturn("");
        when(mockReq.getParameter("host4")).thenReturn("");
        when(mockReq.getParameter("host5")).thenReturn("");
        for (int i = 1; i <= 5; i++) {
            when(mockReq.getParameter("ip" + i + "_from")).thenReturn("");
            when(mockReq.getParameter("ip" + i + "_to")).thenReturn("");
        }
        when(mockReq.getParameter("corporateNumber")).thenReturn("1234567890123");
        when(mockReq.getParameter("attributeType")).thenReturn("co.jp");
        when(mockReq.getParameter("domainName")).thenReturn("example.co.jp");
        when(mockReq.getRequestDispatcher("/domainRegisterInput.jsp")).thenReturn(mockDispatcher);

        try (MockedConstruction<OrganizationDomainDao> mocked =
                mockConstruction(OrganizationDomainDao.class, (mock, context) -> {
                    when(mock.existsActiveDomainForOrg(anyString(), anyString())).thenReturn(false);
                })) {
            servlet.doPost(mockReq, mockResp);
        }

        verify(mockDispatcher).forward(mockReq, mockResp);
    }

    /**
     * バリデーションOK・既存ドメインなし → 確認画面にforward
     */
    @Test
    public void testDoPost_バリデーションOK_確認画面へforward() throws Exception {
        for (int i = 1; i <= 5; i++) {
            when(mockReq.getParameter("host" + i)).thenReturn("");
        }
        for (int i = 1; i <= 5; i++) {
            when(mockReq.getParameter("ip" + i + "_from")).thenReturn("");
            when(mockReq.getParameter("ip" + i + "_to")).thenReturn("");
        }
        when(mockReq.getParameter("corporateNumber")).thenReturn("1234567890123");
        when(mockReq.getParameter("attributeType")).thenReturn("co.jp");
        when(mockReq.getParameter("domainName")).thenReturn("example.co.jp");
        when(mockReq.getRequestDispatcher("/domainRegisterConfirm.jsp")).thenReturn(mockDispatcher);

        try (MockedConstruction<OrganizationDomainDao> mocked =
                mockConstruction(OrganizationDomainDao.class, (mock, context) -> {
                    when(mock.existsActiveDomainForOrg(anyString(), anyString())).thenReturn(false);
                })) {
            servlet.doPost(mockReq, mockResp);
        }

        verify(mockDispatcher).forward(mockReq, mockResp);
    }

    /**
     * バリデーションOK・既存ドメインあり → 入力画面にforward
     */
    @Test
    public void testDoPost_既存ドメインあり_入力画面へforward() throws Exception {
        for (int i = 1; i <= 5; i++) {
            when(mockReq.getParameter("host" + i)).thenReturn("");
        }
        for (int i = 1; i <= 5; i++) {
            when(mockReq.getParameter("ip" + i + "_from")).thenReturn("");
            when(mockReq.getParameter("ip" + i + "_to")).thenReturn("");
        }
        when(mockReq.getParameter("corporateNumber")).thenReturn("1234567890123");
        when(mockReq.getParameter("attributeType")).thenReturn("co.jp");
        when(mockReq.getParameter("domainName")).thenReturn("example.co.jp");
        when(mockReq.getRequestDispatcher("/domainRegisterInput.jsp")).thenReturn(mockDispatcher);

        try (MockedConstruction<OrganizationDomainDao> mocked =
                mockConstruction(OrganizationDomainDao.class, (mock, context) -> {
                    when(mock.existsActiveDomainForOrg(anyString(), anyString())).thenReturn(true);
                })) {
            servlet.doPost(mockReq, mockResp);
        }

        verify(mockDispatcher).forward(mockReq, mockResp);
    }

    // ============================================================
    // validateHosts テスト
    // ============================================================

    @Test
    public void testValidateHosts_全て空欄_エラーなし() throws Exception {
        for (int i = 1; i <= 5; i++) {
            when(mockReq.getParameter("host" + i)).thenReturn("");
        }
        List<String> errors = invokeValidateHosts(mockReq);
        assertTrue(errors.isEmpty());
    }

    @Test
    public void testValidateHosts_正常なIPv4_エラーなし() throws Exception {
        when(mockReq.getParameter("host1")).thenReturn("192.168.0.1");
        when(mockReq.getParameter("host2")).thenReturn("10.0.0.1");
        when(mockReq.getParameter("host3")).thenReturn("");
        when(mockReq.getParameter("host4")).thenReturn("");
        when(mockReq.getParameter("host5")).thenReturn("");
        List<String> errors = invokeValidateHosts(mockReq);
        assertTrue(errors.isEmpty());
    }

    @Test
    public void testValidateHosts_不正な形式_エラーあり() throws Exception {
        when(mockReq.getParameter("host1")).thenReturn("abc");
        when(mockReq.getParameter("host2")).thenReturn("");
        when(mockReq.getParameter("host3")).thenReturn("");
        when(mockReq.getParameter("host4")).thenReturn("");
        when(mockReq.getParameter("host5")).thenReturn("");
        List<String> errors = invokeValidateHosts(mockReq);
        assertEquals(1, errors.size());
        assertTrue(errors.get(0).contains("ホストアドレス1"));
        assertTrue(errors.get(0).contains("IPv4形式ではありません"));
    }

    @Test
    public void testValidateHosts_256以上の値_エラーあり() throws Exception {
        when(mockReq.getParameter("host1")).thenReturn("256.0.0.1");
        when(mockReq.getParameter("host2")).thenReturn("");
        when(mockReq.getParameter("host3")).thenReturn("");
        when(mockReq.getParameter("host4")).thenReturn("");
        when(mockReq.getParameter("host5")).thenReturn("");
        List<String> errors = invokeValidateHosts(mockReq);
        assertEquals(1, errors.size());
    }

    // ============================================================
    // validateIpRanges テスト
    // ============================================================

    @Test
    public void testValidateIpRanges_全て空欄_エラーなし() throws Exception {
        for (int i = 1; i <= 5; i++) {
            when(mockReq.getParameter("ip" + i + "_from")).thenReturn("");
            when(mockReq.getParameter("ip" + i + "_to")).thenReturn("");
        }
        List<String> errors = invokeValidateIpRanges(mockReq);
        assertTrue(errors.isEmpty());
    }

    @Test
    public void testValidateIpRanges_FROM正常TO正常_エラーなし() throws Exception {
        when(mockReq.getParameter("ip1_from")).thenReturn("192.168.0.1");
        when(mockReq.getParameter("ip1_to")).thenReturn("192.168.0.255");
        for (int i = 2; i <= 5; i++) {
            when(mockReq.getParameter("ip" + i + "_from")).thenReturn("");
            when(mockReq.getParameter("ip" + i + "_to")).thenReturn("");
        }
        List<String> errors = invokeValidateIpRanges(mockReq);
        assertTrue(errors.isEmpty());
    }

    @Test
    public void testValidateIpRanges_FROMのみ入力_エラーあり() throws Exception {
        when(mockReq.getParameter("ip1_from")).thenReturn("192.168.0.1");
        when(mockReq.getParameter("ip1_to")).thenReturn("");
        for (int i = 2; i <= 5; i++) {
            when(mockReq.getParameter("ip" + i + "_from")).thenReturn("");
            when(mockReq.getParameter("ip" + i + "_to")).thenReturn("");
        }
        List<String> errors = invokeValidateIpRanges(mockReq);
        assertEquals(1, errors.size());
        assertTrue(errors.get(0).contains("TOが入力されていません"));
    }

    @Test
    public void testValidateIpRanges_TOのみ入力_エラーあり() throws Exception {
        when(mockReq.getParameter("ip1_from")).thenReturn("");
        when(mockReq.getParameter("ip1_to")).thenReturn("192.168.0.255");
        for (int i = 2; i <= 5; i++) {
            when(mockReq.getParameter("ip" + i + "_from")).thenReturn("");
            when(mockReq.getParameter("ip" + i + "_to")).thenReturn("");
        }
        List<String> errors = invokeValidateIpRanges(mockReq);
        assertEquals(1, errors.size());
        assertTrue(errors.get(0).contains("FROMが入力されていません"));
    }

    @Test
    public void testValidateIpRanges_FROM大TO小_エラーあり() throws Exception {
        when(mockReq.getParameter("ip1_from")).thenReturn("192.168.0.255");
        when(mockReq.getParameter("ip1_to")).thenReturn("192.168.0.1");
        for (int i = 2; i <= 5; i++) {
            when(mockReq.getParameter("ip" + i + "_from")).thenReturn("");
            when(mockReq.getParameter("ip" + i + "_to")).thenReturn("");
        }
        List<String> errors = invokeValidateIpRanges(mockReq);
        assertEquals(1, errors.size());
        assertTrue(errors.get(0).contains("FROMはTOより小さい値を入力してください"));
    }

    @Test
    public void testValidateIpRanges_FROM等TO_エラーなし() throws Exception {
        when(mockReq.getParameter("ip1_from")).thenReturn("192.168.0.1");
        when(mockReq.getParameter("ip1_to")).thenReturn("192.168.0.1");
        for (int i = 2; i <= 5; i++) {
            when(mockReq.getParameter("ip" + i + "_from")).thenReturn("");
            when(mockReq.getParameter("ip" + i + "_to")).thenReturn("");
        }
        List<String> errors = invokeValidateIpRanges(mockReq);
        assertTrue(errors.isEmpty());
    }

    @Test
    public void testValidateIpRanges_FROM不正形式_エラーあり() throws Exception {
        when(mockReq.getParameter("ip1_from")).thenReturn("abc");
        when(mockReq.getParameter("ip1_to")).thenReturn("192.168.0.255");
        for (int i = 2; i <= 5; i++) {
            when(mockReq.getParameter("ip" + i + "_from")).thenReturn("");
            when(mockReq.getParameter("ip" + i + "_to")).thenReturn("");
        }
        List<String> errors = invokeValidateIpRanges(mockReq);
        assertEquals(1, errors.size());
        assertTrue(errors.get(0).contains("FROM"));
        assertTrue(errors.get(0).contains("IPv4形式ではありません"));
    }

    // ============================================================
    // validateDuplicates テスト
    // ============================================================

    @Test
    public void testValidateDuplicates_重複なし_エラーなし() throws Exception {
        when(mockReq.getParameter("host1")).thenReturn("1.1.1.1");
        when(mockReq.getParameter("host2")).thenReturn("");
        when(mockReq.getParameter("host3")).thenReturn("");
        when(mockReq.getParameter("host4")).thenReturn("");
        when(mockReq.getParameter("host5")).thenReturn("");
        when(mockReq.getParameter("ip1_from")).thenReturn("1.1.1.2");
        when(mockReq.getParameter("ip1_to")).thenReturn("1.1.1.3");
        for (int i = 2; i <= 5; i++) {
            when(mockReq.getParameter("ip" + i + "_from")).thenReturn("");
            when(mockReq.getParameter("ip" + i + "_to")).thenReturn("");
        }
        List<String> errors = invokeValidateDuplicates(mockReq);
        assertTrue(errors.isEmpty());
    }

    @Test
    public void testValidateDuplicates_ホストとFROM完全一致_エラーあり() throws Exception {
        when(mockReq.getParameter("host1")).thenReturn("1.1.1.1");
        when(mockReq.getParameter("host2")).thenReturn("");
        when(mockReq.getParameter("host3")).thenReturn("");
        when(mockReq.getParameter("host4")).thenReturn("");
        when(mockReq.getParameter("host5")).thenReturn("");
        when(mockReq.getParameter("ip1_from")).thenReturn("1.1.1.1");
        when(mockReq.getParameter("ip1_to")).thenReturn("1.1.1.3");
        for (int i = 2; i <= 5; i++) {
            when(mockReq.getParameter("ip" + i + "_from")).thenReturn("");
            when(mockReq.getParameter("ip" + i + "_to")).thenReturn("");
        }
        List<String> errors = invokeValidateDuplicates(mockReq);
        assertEquals(2, errors.size());
        assertTrue(errors.get(0).contains("ホストアドレス1"));
    }

    @Test
    public void testValidateDuplicates_ホストが範囲内_エラーあり() throws Exception {
        when(mockReq.getParameter("host1")).thenReturn("1.1.1.1");
        when(mockReq.getParameter("host2")).thenReturn("");
        when(mockReq.getParameter("host3")).thenReturn("");
        when(mockReq.getParameter("host4")).thenReturn("");
        when(mockReq.getParameter("host5")).thenReturn("");
        when(mockReq.getParameter("ip1_from")).thenReturn("1.1.1.0");
        when(mockReq.getParameter("ip1_to")).thenReturn("1.1.1.2");
        for (int i = 2; i <= 5; i++) {
            when(mockReq.getParameter("ip" + i + "_from")).thenReturn("");
            when(mockReq.getParameter("ip" + i + "_to")).thenReturn("");
        }
        List<String> errors = invokeValidateDuplicates(mockReq);
        assertEquals(1, errors.size());
        assertTrue(errors.get(0).contains("範囲内に含まれています"));
    }

    @Test
    public void testValidateDuplicates_FROM同士重複_エラーあり() throws Exception {
        for (int i = 1; i <= 5; i++) {
            when(mockReq.getParameter("host" + i)).thenReturn("");
        }
        when(mockReq.getParameter("ip1_from")).thenReturn("1.1.1.1");
        when(mockReq.getParameter("ip1_to")).thenReturn("1.1.1.2");
        when(mockReq.getParameter("ip2_from")).thenReturn("1.1.1.1");
        when(mockReq.getParameter("ip2_to")).thenReturn("1.1.1.3");
        for (int i = 3; i <= 5; i++) {
            when(mockReq.getParameter("ip" + i + "_from")).thenReturn("");
            when(mockReq.getParameter("ip" + i + "_to")).thenReturn("");
        }
        List<String> errors = invokeValidateDuplicates(mockReq);
        assertEquals(1, errors.size());
        assertTrue(errors.get(0).contains("重複しています"));
    }

    // ============================================================
    // リフレクションでprivateメソッドを呼び出すヘルパー
    // ============================================================

    @SuppressWarnings("unchecked")
    private List<String> invokeValidateHosts(HttpServletRequest req) throws Exception {
        Method method = DomainRegisterCheckServlet.class
            .getDeclaredMethod("validateHosts", HttpServletRequest.class);
        method.setAccessible(true);
        return (List<String>) method.invoke(servlet, req);
    }

    @SuppressWarnings("unchecked")
    private List<String> invokeValidateIpRanges(HttpServletRequest req) throws Exception {
        Method method = DomainRegisterCheckServlet.class
            .getDeclaredMethod("validateIpRanges", HttpServletRequest.class);
        method.setAccessible(true);
        return (List<String>) method.invoke(servlet, req);
    }

    @SuppressWarnings("unchecked")
    private List<String> invokeValidateDuplicates(HttpServletRequest req) throws Exception {
        Method method = DomainRegisterCheckServlet.class
            .getDeclaredMethod("validateDuplicates", HttpServletRequest.class);
        method.setAccessible(true);
        return (List<String>) method.invoke(servlet, req);
    }
}