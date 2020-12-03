package com.dan.xeth.account;

import org.web3j.crypto.*;
import org.web3j.utils.Numeric;

import java.math.BigInteger;
import java.security.InvalidAlgorithmParameterException;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;

public class Account {

    private String address;
    private String privateKeyHex;

    /**
     * Creates an account from a password + mnemonic
     *
     * @param password
     * @param mnemonic
     */
    public Account(String password, String mnemonic) {
        Credentials credentials = WalletUtils.loadBip39Credentials(password, mnemonic);
        address = credentials.getAddress();
        privateKeyHex = Numeric.toHexStringWithPrefix(credentials.getEcKeyPair().getPrivateKey());
    }

    /**
     * Creates a new account
     *
     * @throws InvalidAlgorithmParameterException
     * @throws NoSuchAlgorithmException
     * @throws NoSuchProviderException
     */
    public Account() throws InvalidAlgorithmParameterException, NoSuchAlgorithmException, NoSuchProviderException {

        // create new private/public key pair
        ECKeyPair keyPair = Keys.createEcKeyPair();
        BigInteger publicKey = keyPair.getPublicKey();

        BigInteger privateKey = keyPair.getPrivateKey();
        privateKeyHex = Numeric.toHexStringWithPrefix(privateKey);

        // create credentials + address from private/public key pair
        Credentials credentials = Credentials.create(new ECKeyPair(privateKey, publicKey));
        address = credentials.getAddress();

    }

    // returns the address
    public String getAddress() {
        return address;
    }

    // returns the private key
    public String getPrivateKey() {
        return privateKeyHex;
    }

    @Override
    public String toString() {
        return "Account{" +
                "address='" + address + '\'' +
                ", privateKeyHex='" + privateKeyHex + '\'' +
                '}';
    }
}
