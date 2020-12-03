package com.dan.xeth.token;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.http.HttpService;
import org.web3j.utils.Convert;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.concurrent.ExecutionException;

public class EthBalanceTest {

    private final static String ANDY_TOKEN_CONTRACT = "0x7Ec133d17F253BF759d58882bf9ff18fddcf2155";
    private static final String RINKEBY_ADDRESS = "0x772707e8cEe9FBAB1ce4274130D0e6BaC8Fa872f";
    private Web3j web3j;

    @Before
    public void setUp() {
        final String INFURA_URL = "wss://rinkeby.infura.io/v3/17311cf39f1a4c73ad7254ec8a100018";
        web3j = Web3j.build(new HttpService(INFURA_URL));
    }

    @Test
    public void getWeiBalance() throws ExecutionException, InterruptedException {

        BigInteger weiBalance = EthBalance.getWeiBalance(web3j, RINKEBY_ADDRESS);
        System.out.println("EthBalance in wei: " + weiBalance);
        Assert.assertTrue(weiBalance.compareTo(new BigInteger("1")) > 0);

        // Convert it to eth
        BigDecimal balanceETH = Convert.fromWei(new BigDecimal(weiBalance), Convert.Unit.ETHER);
        System.out.println("EthBalance in ETH: " + balanceETH);
        Assert.assertTrue(balanceETH.compareTo(new BigDecimal("0")) > 0);

    }


}