# XEth Library

Java - Ethereum library

## Easy ERC20 Token API

This is a small library wrapped around web3j to make it easier.

Features:
- get token name, symbol, totalSupply
- getting the balance of an account
- getting the ETH balance
- listening for events 

## How to use it

Determine what node you'll use for interaction:

Example:  "wss://rinkeby.infura.io/v3/1231231231231231231231231232";  //wss stands for WebSockets over SSL/TLS

If you plan to use infura.io, go to https://infura.io make an account there and you'll get an API Key

## Examples

Take a look at the tests: 
https://gitlab.com/AndreiDD/miniblockchainapijava/tree/master/src/test/java/com/dan/xeth


### Getting the balance of ETH

~~~~
Web3j web3j = Web3j.build(new HttpService("wss://rinkeby.infura.io/v3/YOUR_API_KEY"));

BigInteger weiBalance = EthBalance.getWeiBalance(web3j, RINKEBY_ADDRESS);
System.out.println("EthBalance in wei: " + weiBalance);
       
// Convert it to eth
BigDecimal balanceETH = Convert.fromWei(new BigDecimal(weiBalance), Convert.Unit.ETHER);
System.out.println("EthBalance in ETH: " + balanceETH);
~~~~

### Getting the balance of ERC20 Token

There are two ways you can do it. Via a read only mode or read / write mode if you plan to do transactions too

~~~~
ERC20Token  erc20Token = ERC20Token.load(TOKEN_CONTRACT_ADDRESS, web3j, credentials);
BigInteger balanceBig = erc20Token.balanceOf(OWNERS_ADDRESS).send();
BigDecimal balanceDecimals = new BigDecimal(balanceBig).setScale(18, BigDecimal.ROUND_DOWN).divide(BigDecimal.TEN.pow(18), BigDecimal.ROUND_DOWN).stripTrailingZeros();
System.out.println("Got the balance of " + balanceDecimals.toString() + " tokens");
~~~~
or
~~~~
ERC20Token erc20TokenReadOnly = ERC20Token.loadTokenReadOnly(TOKEN_CONTRACT_ADDRESS, web3j, AN_ADDRESS);
BigInteger balanceBig = erc20TokenReadOnly.balanceOf(OWNERS_ADDRESS).sendAsync().get();
BigDecimal balanceDecimals = new BigDecimal(balanceBig).setScale(18, BigDecimal.ROUND_DOWN).divide(BigDecimal.TEN.pow(18), BigDecimal.ROUND_DOWN).stripTrailingZeros();
System.out.println("Got the balance of " + balanceDecimals.toString() + " tokens");
~~~~

### Listening for events

~~~~
EthFilter filter = new EthFilter(DefaultBlockParameterName.EARLIEST, DefaultBlockParameterName.LATEST, TOKEN_CONTRACT_ADDRESS);
Disposable disposable = web3j.ethLogFlowable(filter).subscribe(log -> System.out.println(log.toString()), Throwable::printStackTrace);
TimeUnit.SECONDS.sleep(10);
disposable.dispose();
~~~~
or 
~~~~
Flowable<ERC20Token.TransferEventResponse> flowable = erc20Token.transferEventFlowable(filter);
flowable.subscribe(new Consumer<ERC20Token.TransferEventResponse>() {
    @Override
    public void accept(ERC20Token.TransferEventResponse transferEventResponse) throws Exception {
        System.out.println(transferEventResponse.toString());
    }
});
~~~~