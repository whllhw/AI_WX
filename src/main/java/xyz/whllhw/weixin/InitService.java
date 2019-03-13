package xyz.whllhw.weixin;

import lombok.NonNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import xyz.whllhw.credit.CreditService;
import xyz.whllhw.pay.WalletService;

@Service
public class InitService {
    @Autowired
    private WalletService walletService;
    @Autowired
    private CreditService creditService;

    @Transactional(rollbackFor = Exception.class)
    public void initUser(@NonNull String user) {
        creditService.initCredit(user, 500);
        walletService.initWallet(user);
    }
}
