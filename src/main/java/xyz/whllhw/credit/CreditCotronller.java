package xyz.whllhw.credit;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import xyz.whllhw.util.CommonResponseForm;
import xyz.whllhw.util.SessionUtil;

import javax.servlet.http.HttpSession;

@RestController
public class CreditCotronller {

    @Autowired
    private CreditService creditService;
    @Autowired
    private HttpSession httpSession;

    @GetMapping("/credit/log")
    public CommonResponseForm getLog() {
        return CommonResponseForm.of200("got",
                creditService.getLog(SessionUtil.getUserName(httpSession)));
    }

    @GetMapping("/credit/get")
    public CommonResponseForm getCredit() {
        return CommonResponseForm.of200("got",
                creditService.getCredit(SessionUtil.getUserName(httpSession)));
    }
}
