package com.dan.xeth.account;

import org.junit.Test;
import org.web3j.crypto.WalletUtils;

import java.security.InvalidAlgorithmParameterException;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;

import static org.junit.Assert.*;

public class AccountTest {

    @Test
    public void CreateAccounts() throws InvalidAlgorithmParameterException, NoSuchAlgorithmException, NoSuchProviderException {
        for (int i = 0; i < 10; i++) {
            Account account = new Account();
            assertTrue(WalletUtils.isValidAddress(account.getAddress()));
            assertEquals(42, account.getAddress().length());
            assertTrue(account.getPrivateKey().length() > 64);
        }
    }

    @Test
    public void CreateAccountsFromMnemonicAndPassword() {
        Account account = new Account("password", "ask category insect announce file proof dumb ozone coffee idea barrel episode");
        assertEquals("0x47f40dcb69a399c026734c1db2aca8011b459af6", account.getAddress());
        assertEquals("0xc6aedf9a4fd43bcc4f3c06dc88e42e38db3516564caff280b902649444024610", account.getPrivateKey());
    }

}