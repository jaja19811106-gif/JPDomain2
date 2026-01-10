package util;

import javax.servlet.http.HttpServletRequest;

import dto.DomainDto;

public class DomainMapper {

    public static DomainDto fromRequest(HttpServletRequest request) {

        DomainDto dto = new DomainDto();

        // null チェックしつつ安全にパース
        String id = request.getParameter("id");
        if (id != null && !id.isEmpty()) {
            dto.setId(Integer.parseInt(id));
        }

        dto.setDomainName(request.getParameter("domainName"));
        dto.setRegistrant(request.getParameter("registrant"));
        dto.setNs1(request.getParameter("ns1"));
        dto.setNs2(request.getParameter("ns2"));

        String period = request.getParameter("period");
        if (period != null && !period.isEmpty()) {
            dto.setPeriod(Integer.parseInt(period));
        }

        return dto;
    }
}