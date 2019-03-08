package xyz.whllhw.pay;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import xyz.whllhw.util.CommonResponseForm;
import xyz.whllhw.util.SessionUtil;

import javax.servlet.http.HttpSession;

@RestController
public class WalletController {

    @Autowired
    private WalletService walletService;
    @Autowired
    private HttpSession httpSession;

    @GetMapping("/wallet/get")
    public CommonResponseForm get() {
        return CommonResponseForm.of200("got wallet", walletService.getWalletEntity(
                SessionUtil.getUserName(httpSession)
        ));
    }

    @PostMapping("/wallet/withdraw")
    public CommonResponseForm withdraw() throws Exception {
        throw new Exception("not impl");
    }

    @GetMapping("/wallet/log")
    public CommonResponseForm getLog() {
        return CommonResponseForm.of200("got log",
                walletService.getLog(SessionUtil.getUserName(httpSession))
        );
    }
}
