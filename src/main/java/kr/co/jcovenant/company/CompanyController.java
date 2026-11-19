package kr.co.jcovenant.company;

import java.util.Map;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class CompanyController {

    private final int port;

    public CompanyController(@Value("${server.port}") int port) {
        this.port = port;
    }

    @GetMapping("/health")
    public Map<String, Object> health() {
        return Map.of(
                "status", "UP",
                "service", "jcovenant-homepage",
                "port", port
        );
    }

    @GetMapping("/company")
    public CompanyProfile company() {
        return new CompanyProfile(
                "주식회사 제이커버넌트",
                "제이커버넌트",
                "이진",
                "2023-04-20",
                "791-87-03142",
                "충청남도 천안시 서북구 불당36길 63, 3층 303-02호",
                "충남천안아산강소특구 제3캠퍼스(불당동)",
                "요양시설 야간 낙상 예방 시스템 개발·공급",
                "케어가드 선행 알리미 시스템",
                "IMD-2000"
        );
    }
}
