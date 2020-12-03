package com.dan.xeth.token;

import io.reactivex.disposables.Disposable;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.web3j.crypto.Credentials;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.core.RemoteCall;
import org.web3j.protocol.core.methods.response.TransactionReceipt;
import org.web3j.protocol.http.HttpService;

import java.math.BigInteger;
import java.util.NoSuchElementException;
import java.util.Optional;

public class TxStatusTest {


    private final static String ANDY_TOKEN_CONTRACT = "0x7Ec133d17F253BF759d58882bf9ff18fddcf2155";
    private static final String RINKEBY_ADDRESS = "0x772707e8cEe9FBAB1ce4274130D0e6BaC8Fa872f";
    private static String KNOWN_GOOD_TX = "0xb70a838e6214e6ec3a0cfc1f032f5c6577acba6c1f1148fdd056b4444472eb55";
    private static String KNOWN_FAILED_TX = "0x372f0cfc7c365b7449d18f858b764b71e59d6c9cb00e3bd709e718dd5d669ce3";
    private ERC20Token erc20Token;
    private Web3j web3j;

    @Before
    public void setUp() {

        final String INFURA_URL = "wss://rinkeby.infura.io/v3/17311cf39f1a4c73ad7254ec8a100018";
        final String RINKEBY_PRIVATE_KEY = "0ADB3649A09F1BE6371807FFC9EF3C701B1302D1CEBF6CD03B644B2FC237AC42";

        web3j = Web3j.build(new HttpService(INFURA_URL));
        Credentials credentials = Credentials.create(RINKEBY_PRIVATE_KEY);
        erc20Token = ERC20Token.load(ANDY_TOKEN_CONTRACT, web3j, credentials);
    }

    // we know that this tx passed
    @Test
    public void goodTx() throws Exception {
        Optional<TransactionReceipt> transactionReceiptOptional = TxStatus.getReceipt(web3j, KNOWN_GOOD_TX);
        Assert.assertEquals(transactionReceiptOptional.get().getStatus(), "0x1");

    }

    // we know that this tx faild
    @Test
    public void failedTx() throws Exception {

        Optional<TransactionReceipt> transactionReceiptOptional = TxStatus.getReceipt(web3j, KNOWN_FAILED_TX);
        Assert.assertEquals(transactionReceiptOptional.get().getStatus(), "0x0");

    }

    // testing a bad tx will throw NoSuchElementException
    @Test(expected = NoSuchElementException.class)
    public void invalidTx() throws Exception {

        Optional<TransactionReceipt> transactionReceiptOptional = TxStatus.getReceipt(web3j, "0x212");
        Assert.assertEquals(transactionReceiptOptional.get().getStatus(), "0x1");

    }

    @Test
    public void usingFlowable() {
        // send a tx
        RemoteCall<TransactionReceipt> receiptRemoteCall = erc20Token.transfer(RINKEBY_ADDRESS, new BigInteger("10000"));
        Disposable disposable = receiptRemoteCall.flowable().subscribe(transactionReceipt -> System.out.println("transactionReceipt: " + transactionReceipt.toString()), Throwable::printStackTrace);
        disposable.dispose();

    }
}