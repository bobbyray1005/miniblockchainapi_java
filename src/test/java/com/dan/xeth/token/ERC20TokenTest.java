package com.dan.xeth.token;

import org.junit.Before;
import org.junit.Test;
import org.web3j.crypto.Credentials;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.core.RemoteCall;
import org.web3j.protocol.core.methods.response.TransactionReceipt;
import org.web3j.protocol.http.HttpService;

import java.math.BigDecimal;
import java.math.BigInteger;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class ERC20TokenTest {

    private final static String ANDY_TOKEN_CONTRACT = "0x7Ec133d17F253BF759d58882bf9ff18fddcf2155";
    private static final String RINKEBY_ADDRESS = "0x772707e8cEe9FBAB1ce4274130D0e6BaC8Fa872f";
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

    @Test
    public void symbol() throws Exception {
        assertEquals("ANDY", erc20Token.symbol().send());
    }

    @Test
    public void decimals() throws Exception {
        assertEquals(new BigInteger("18"), erc20Token.decimals().send());
    }

    @Test
    public void totalSupply() throws Exception {
        assertEquals(new BigInteger("1000000000000000000000000000"), erc20Token.totalSupply().send());
    }

    // Gets the balance using the erc20Token (read/write mode)
    @Test
    public void getBalance() throws Exception {

        BigInteger balanceBig = erc20Token.balanceOf(RINKEBY_ADDRESS).send();
        BigDecimal balanceDecimals = new BigDecimal(balanceBig).setScale(18, BigDecimal.ROUND_DOWN).divide(BigDecimal.TEN.pow(18), BigDecimal.ROUND_DOWN).stripTrailingZeros();
        System.out.println("Got the balance of " + balanceDecimals.toString() + " tokens");
        assertTrue(balanceBig.compareTo(new BigInteger("0")) > 0);
    }

    // Gets the balance using the erc20Token (read only mode)
    @Test
    public void getBalanceReadOnlyMode() throws Exception {

        ERC20Token erc20TokenReadOnly = ERC20Token.loadTokenReadOnly(ANDY_TOKEN_CONTRACT, web3j, RINKEBY_ADDRESS);
        BigInteger balanceBig = erc20TokenReadOnly.balanceOf(RINKEBY_ADDRESS).sendAsync().get();
        BigDecimal balanceDecimals = new BigDecimal(balanceBig).setScale(18, BigDecimal.ROUND_DOWN).divide(BigDecimal.TEN.pow(18), BigDecimal.ROUND_DOWN).stripTrailingZeros();
        System.out.println("Got the balance of " + balanceDecimals.toString() + " tokens");
        assertTrue(balanceBig.compareTo(new BigInteger("0")) > 0);
    }

    // make sure the above address has enough tokens for transfer & ether to pay the fees
    @Test
    public void transferTokens() throws Exception {
        RemoteCall<TransactionReceipt> receiptRemoteCall = erc20Token.transfer(RINKEBY_ADDRESS, new BigInteger("10000"));
        TransactionReceipt transactionReceipt = receiptRemoteCall.send();
        System.out.println("Got tx receipt: " + transactionReceipt.toString());
        String txHash = transactionReceipt.getTransactionHash();
        System.out.println("Got transaction hash: " + txHash);
        assertTrue(txHash.length() > 0);
    }

    //testing the event flowable
//    @Test
//    public void transferEvent() throws Exception {
//
//        EthFilter filter = new EthFilter(DefaultBlockParameterName.EARLIEST, DefaultBlockParameterName.LATEST, ANDY_TOKEN_CONTRACT);
//
//        Disposable disposable = web3j.ethLogFlowable(filter).subscribe(log -> System.out.println(log.toString()), Throwable::printStackTrace);
//        TimeUnit.SECONDS.sleep(10);
//        disposable.dispose();
//
////        Flowable<ERC20Token.TransferEventResponse> flowable = erc20Token.transferEventFlowable(filter);
////        flowable.subscribe(new Consumer<ERC20Token.TransferEventResponse>() {
////            @Override
////            public void accept(ERC20Token.TransferEventResponse transferEventResponse) throws Exception {
////                System.out.println(transferEventResponse.toString());
////            }
////        });
//
//    }

}