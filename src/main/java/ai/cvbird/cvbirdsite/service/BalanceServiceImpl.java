package ai.cvbird.cvbirdsite.service;

import ai.cvbird.cvbirdsite.dao.BalanceRepository;
import ai.cvbird.cvbirdsite.dao.CVBirdUserRepository;
import ai.cvbird.cvbirdsite.exception.NotEnoughFundsException;
import ai.cvbird.cvbirdsite.model.Balance;
import ai.cvbird.cvbirdsite.model.CVBirdUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;

@Service
public class BalanceServiceImpl implements BalanceService{

    @Autowired
    BalanceRepository balanceRepository;

    @Autowired
    TelegramService telegramService;

    @Override
    public BigDecimal getByTelegramId(String telegramId) {
        CVBirdUser cvBirdUser = telegramService.getCVBirdUser(telegramId);
        if (cvBirdUser != null) {
            return getByCVBirdUser(cvBirdUser);
        }
        return null;
    }

    @Override
    public BigDecimal getByCVBirdUser(CVBirdUser cvBirdUser) {
        Balance balance = balanceRepository.findByCvbirdUser(cvBirdUser);
        return balance.getBalance();
    }

    @Override
    @Transactional(isolation = Isolation.SERIALIZABLE)
    public BigDecimal balanceTopUp(CVBirdUser cvBirdUser, BigDecimal amount) {
        Balance balance = balanceRepository.findByCvbirdUser(cvBirdUser);
        BigDecimal currentBalance = balance.getBalance();
        balance.setBalance(currentBalance.add(amount));
        balanceRepository.save(balance);
        return balanceRepository.findByCvbirdUser(cvBirdUser).getBalance();
    }

    @Override
    @Transactional(isolation = Isolation.SERIALIZABLE)
    public BigDecimal balanceCharging(CVBirdUser cvBirdUser, BigDecimal amount) throws NotEnoughFundsException {
        Balance balance = balanceRepository.findByCvbirdUser(cvBirdUser);
        BigDecimal currentBalance = balance.getBalance();
        if (currentBalance.compareTo(amount)  >= 0 ) {
            balance.setBalance(currentBalance.subtract(amount));
            balanceRepository.save(balance);
            return balanceRepository.findByCvbirdUser(cvBirdUser).getBalance();
        } else {
            throw new NotEnoughFundsException("There are not enough funds on your balance");
        }
    }

    @Override
    public BigDecimal balanceTopUp(String telegramId, BigDecimal amount) {
        CVBirdUser cvBirdUser = telegramService.getCVBirdUser(telegramId);
        if (cvBirdUser != null) {
            return balanceTopUp(cvBirdUser, amount);
        }
        return null;
    }

    @Override
    public BigDecimal balanceCharging(String telegramId, BigDecimal amount) throws NotEnoughFundsException {
        CVBirdUser cvBirdUser = telegramService.getCVBirdUser(telegramId);
        if (cvBirdUser != null) {
            return balanceCharging(cvBirdUser, amount);
        }
        return null;
    }
}
