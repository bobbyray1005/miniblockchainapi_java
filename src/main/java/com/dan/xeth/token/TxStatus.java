package com.dan.xeth.token;

import org.web3j.protocol.Web3j;
import org.web3j.protocol.core.methods.response.EthGetTransactionReceipt;
import org.web3j.protocol.core.methods.response.TransactionReceipt;

import java.util.Optional;

public class TxStatus {

    /**
     * Returns the TransactionReceipt for the specified tx hash as an optional.
     */
    public static Optional<TransactionReceipt> getReceipt(Web3j web3j, String transactionHash)
            throws Exception {
        EthGetTransactionReceipt receipt = web3j
                .ethGetTransactionReceipt(transactionHash)
                .sendAsync()
                .get();

        return receipt.getTransactionReceipt();
    }


}
