package com.dan.xeth.token;

import org.web3j.protocol.Web3j;
import org.web3j.protocol.core.DefaultBlockParameterName;
import org.web3j.protocol.core.methods.response.EthGetBalance;

import java.math.BigInteger;
import java.util.concurrent.ExecutionException;

public class EthBalance {

    /**
     * Gets the balance of ETH of an address in wei
     * To convert it to ETH call:
     *
     * @param web3j - the web3j object
     * @param owner - the address of the owner
     * @return
     * @throws ExecutionException
     * @throws InterruptedException
     */

    // only static methods
    private EthBalance() {
    }

    public static BigInteger getWeiBalance(Web3j web3j, String owner) throws ExecutionException, InterruptedException {
        EthGetBalance ethGetBalance =
                web3j.ethGetBalance(owner, DefaultBlockParameterName.LATEST).sendAsync().get();
        BigInteger wei = ethGetBalance.getBalance();
        return wei;
    }

}
