package com.dan.xeth.token;

import io.reactivex.Flowable;
import org.web3j.abi.TypeReference;
import org.web3j.abi.datatypes.Address;
import org.web3j.abi.datatypes.Event;
import org.web3j.abi.datatypes.Function;
import org.web3j.abi.datatypes.Utf8String;
import org.web3j.abi.datatypes.generated.Uint256;
import org.web3j.abi.datatypes.generated.Uint8;
import org.web3j.crypto.Credentials;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.core.RemoteCall;
import org.web3j.protocol.core.methods.request.EthFilter;
import org.web3j.protocol.core.methods.response.Log;
import org.web3j.protocol.core.methods.response.TransactionReceipt;
import org.web3j.tx.Contract;
import org.web3j.tx.ReadonlyTransactionManager;
import org.web3j.tx.TransactionManager;
import org.web3j.tx.gas.DefaultGasProvider;

import java.math.BigInteger;
import java.util.Arrays;
import java.util.Collections;


public class ERC20Token extends Contract {
    private static final String BINARY = "0x608060405234801561001057600080fd5b50600436106100cf5760003560e01c8063313ce5671161008c57806395d89b411161006657806395d89b4114610385578063a457c2d714610408578063a9059cbb1461046e578063dd62ed3e146104d4576100cf565b8063313ce567146102a357806339509351146102c757806370a082311461032d576100cf565b806306fdde03146100d4578063095ea7b31461015757806318160ddd146101bd57806323b872dd146101db5780632e0f2625146102615780632ff2e9dc14610285575b600080fd5b6100dc61054c565b6040518080602001828103825283818151815260200191508051906020019080838360005b8381101561011c578082015181840152602081019050610101565b50505050905090810190601f1680156101495780820380516001836020036101000a031916815260200191505b509250505060405180910390f35b6101a36004803603604081101561016d57600080fd5b81019080803573ffffffffffffffffffffffffffffffffffffffff169060200190929190803590602001909291905050506105ee565b604051808215151515815260200191505060405180910390f35b6101c5610605565b6040518082815260200191505060405180910390f35b610247600480360360608110156101f157600080fd5b81019080803573ffffffffffffffffffffffffffffffffffffffff169060200190929190803573ffffffffffffffffffffffffffffffffffffffff1690602001909291908035906020019092919050505061060f565b604051808215151515815260200191505060405180910390f35b6102696106c0565b604051808260ff1660ff16815260200191505060405180910390f35b61028d6106c5565b6040518082815260200191505060405180910390f35b6102ab6106d6565b604051808260ff1660ff16815260200191505060405180910390f35b610313600480360360408110156102dd57600080fd5b81019080803573ffffffffffffffffffffffffffffffffffffffff169060200190929190803590602001909291905050506106ed565b604051808215151515815260200191505060405180910390f35b61036f6004803603602081101561034357600080fd5b81019080803573ffffffffffffffffffffffffffffffffffffffff169060200190929190505050610792565b6040518082815260200191505060405180910390f35b61038d6107da565b6040518080602001828103825283818151815260200191508051906020019080838360005b838110156103cd5780820151818401526020810190506103b2565b50505050905090810190601f1680156103fa5780820380516001836020036101000a031916815260200191505b509250505060405180910390f35b6104546004803603604081101561041e57600080fd5b81019080803573ffffffffffffffffffffffffffffffffffffffff1690602001909291908035906020019092919050505061087c565b604051808215151515815260200191505060405180910390f35b6104ba6004803603604081101561048457600080fd5b81019080803573ffffffffffffffffffffffffffffffffffffffff16906020019092919080359060200190929190505050610921565b604051808215151515815260200191505060405180910390f35b610536600480360360408110156104ea57600080fd5b81019080803573ffffffffffffffffffffffffffffffffffffffff169060200190929190803573ffffffffffffffffffffffffffffffffffffffff169060200190929190505050610938565b6040518082815260200191505060405180910390f35b606060038054600181600116156101000203166002900480601f0160208091040260200160405190810160405280929190818152602001828054600181600116156101000203166002900480156105e45780601f106105b9576101008083540402835291602001916105e4565b820191906000526020600020905b8154815290600101906020018083116105c757829003601f168201915b5050505050905090565b60006105fb3384846109bf565b6001905092915050565b6000600254905090565b600061061c848484610b22565b6106b584336106b085600160008a73ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff16815260200190815260200160002060003373ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff16815260200190815260200160002054610cee90919063ffffffff16565b6109bf565b600190509392505050565b601281565b601260ff16600a0a633b9aca000281565b6000600560009054906101000a900460ff16905090565b6000610788338461078385600160003373ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff16815260200190815260200160002060008973ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff16815260200190815260200160002054610d1090919063ffffffff16565b6109bf565b6001905092915050565b60008060008373ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff168152602001908152602001600020549050919050565b606060048054600181600116156101000203166002900480601f0160208091040260200160405190810160405280929190818152602001828054600181600116156101000203166002900480156108725780601f1061084757610100808354040283529160200191610872565b820191906000526020600020905b81548152906001019060200180831161085557829003601f168201915b5050505050905090565b6000610917338461091285600160003373ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff16815260200190815260200160002060008973ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff16815260200190815260200160002054610cee90919063ffffffff16565b6109bf565b6001905092915050565b600061092e338484610b22565b6001905092915050565b6000600160008473ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff16815260200190815260200160002060008373ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff16815260200190815260200160002054905092915050565b600073ffffffffffffffffffffffffffffffffffffffff168273ffffffffffffffffffffffffffffffffffffffff16141515156109fb57600080fd5b600073ffffffffffffffffffffffffffffffffffffffff168373ffffffffffffffffffffffffffffffffffffffff1614151515610a3757600080fd5b80600160008573ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff16815260200190815260200160002060008473ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff168152602001908152602001600020819055508173ffffffffffffffffffffffffffffffffffffffff168373ffffffffffffffffffffffffffffffffffffffff167f8c5be1e5ebec7d5bd14f71427d1e84f3dd0314c0f7b2291e5b200ac8c7c3b925836040518082815260200191505060405180910390a3505050565b600073ffffffffffffffffffffffffffffffffffffffff168273ffffffffffffffffffffffffffffffffffffffff1614151515610b5e57600080fd5b610baf816000808673ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff16815260200190815260200160002054610cee90919063ffffffff16565b6000808573ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff16815260200190815260200160002081905550610c42816000808573ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff16815260200190815260200160002054610d1090919063ffffffff16565b6000808473ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff168152602001908152602001600020819055508173ffffffffffffffffffffffffffffffffffffffff168373ffffffffffffffffffffffffffffffffffffffff167fddf252ad1be2c89b69c2b068fc378daa952ba7f163c4a11628f55a4df523b3ef836040518082815260200191505060405180910390a3505050565b6000828211151515610cff57600080fd5b600082840390508091505092915050565b6000808284019050838110151515610d2757600080fd5b809150509291505056fea165627a7a723058206a3278f655fb57f414551f6b32419adb1328738a85f60a1d389b6b1d63fa3a0d0029";

    private static final Event TRANSFER_EVENT = new Event("Transfer",
            Arrays.asList(new TypeReference<Address>(true) {
            }, new TypeReference<Address>(true) {
            }, new TypeReference<Uint256>() {
            }));

    private ERC20Token(String contractAddress, Web3j web3j, Credentials credentials) {
        super(BINARY, contractAddress, web3j, credentials, new DefaultGasProvider());
    }

    private ERC20Token(String contractAddress, Web3j web3j, TransactionManager transactionManager) {
        super(BINARY, contractAddress, web3j, transactionManager, new DefaultGasProvider());
    }


    public RemoteCall<BigInteger> totalSupply() {
        Function function = new Function("totalSupply",
                Collections.emptyList(),
                Collections.singletonList(new TypeReference<Uint256>() {
                }));
        return executeRemoteCallSingleValueReturn(function, BigInteger.class);
    }

    public RemoteCall<BigInteger> decimals() {
        Function function = new Function("decimals",
                Collections.emptyList(),
                Collections.singletonList(new TypeReference<Uint8>() {
                }));
        return executeRemoteCallSingleValueReturn(function, BigInteger.class);
    }

    /**
     * This will return the balance in BigInteger of the tokens. To get the value with decimals you have to divide it
     * ex:  BigDecimal balanceDecimals = new BigDecimal(balanceBig).setScale(18, BigDecimal.ROUND_DOWN).divide(BigDecimal.TEN.pow(18), BigDecimal.ROUND_DOWN).stripTrailingZeros();
     *
     * @param _owner
     * @return
     */
    public RemoteCall<BigInteger> balanceOf(String _owner) {
        Function function = new Function("balanceOf",
                Collections.singletonList(new Address(_owner)),
                Collections.singletonList(new TypeReference<Uint256>() {
                }));
        return executeRemoteCallSingleValueReturn(function, BigInteger.class);
    }


    public RemoteCall<String> symbol() {
        Function function = new Function("symbol",
                Collections.emptyList(),
                Collections.singletonList(new TypeReference<Utf8String>() {
                }));
        return executeRemoteCallSingleValueReturn(function, String.class);
    }

    public RemoteCall<TransactionReceipt> transfer(String _to, BigInteger _value) {
        Function function = new Function(
                "transfer",
                Arrays.asList(new org.web3j.abi.datatypes.Address(_to),
                        new org.web3j.abi.datatypes.generated.Uint256(_value)),
                Collections.emptyList());
        return executeRemoteCallTransaction(function);
    }

    public static ERC20Token load(String contractAddress, Web3j web3j, Credentials credentials) {
        return new ERC20Token(contractAddress, web3j, credentials);
    }

    //
    public static ERC20Token loadTokenReadOnly(String tokenContractAddress, Web3j web3j, String fromAddress) {
        return new ERC20Token(tokenContractAddress, web3j, new ReadonlyTransactionManager(web3j, fromAddress));
    }


    public Flowable<TransferEventResponse> transferEventFlowable(EthFilter filter) {
        return web3j.ethLogFlowable(filter).map(log -> {
            EventValuesWithLog eventValues = extractEventParametersWithLog(TRANSFER_EVENT, log);
            TransferEventResponse typedResponse = new TransferEventResponse();
            typedResponse.log = log;
            typedResponse.from = (String) eventValues.getIndexedValues().get(0).getValue();
            typedResponse.to = (String) eventValues.getIndexedValues().get(1).getValue();
            typedResponse.tokens = (BigInteger) eventValues.getNonIndexedValues().get(0).getValue();
            return typedResponse;
        });

    }

    public static class TransferEventResponse {
        public Log log;
        public String from;
        public String to;
        public BigInteger tokens;
    }

}