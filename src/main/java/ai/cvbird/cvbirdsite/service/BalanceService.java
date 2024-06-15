package ai.cvbird.cvbirdsite.service;

import ai.cvbird.cvbirdsite.exception.NotEnoughFundsException;
import ai.cvbird.cvbirdsite.model.Balance;
import ai.cvbird.cvbirdsite.model.CVBirdUser;

import java.math.BigDecimal;

public interface BalanceService {

    BigDecimal getByTelegramId(String telegramId);

    BigDecimal getByCVBirdUser(CVBirdUser cvBirdUser);

    BigDecimal balanceTopUp(CVBirdUser cvBirdUser, BigDecimal amount);

    BigDecimal balanceTopUp(String telegramId, BigDecimal amount);

    BigDecimal balanceCharging(CVBirdUser cvBirdUser, BigDecimal amount) throws NotEnoughFundsException;

    BigDecimal balanceCharging(String telegramId, BigDecimal amount) throws NotEnoughFundsException;
}
