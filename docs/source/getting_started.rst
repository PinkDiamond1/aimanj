Getting Started
===============

Add the latest aiManj version to your project build configuration.

Maven
-----

Java 8:

.. code-block:: xml

   <dependency>
     <groupId>org.aimanj</groupId>
     <artifactId>core</artifactId>
     <version>4.2.0</version>
   </dependency>

Android:

.. code-block:: xml

   <dependency>
     <groupId>org.aimanj</groupId>
     <artifactId>core</artifactId>
     <version>4.2.0-android</version>
   </dependency>

Gradle
------

Java 8:

.. code-block:: groovy

   compile ('org.aimanj:core:4.2.0')

Android:

.. code-block:: groovy

   compile ('org.aimanj:core:4.2.0-android')


Start a client
--------------

Start up an Matrix client if you don't already have one running, such as
`Gman <https://github.com/Matrix/go-Matrix/wiki/gman>`_:

.. code-block:: bash

   $ GMAN --rpcapi personal,db,man,net,aiman --rpc --rinkeby

Or `Parity <https://github.com/paritytech/parity>`_:

.. code-block:: bash

   $ parity --chain testnet

Or use `Infura <https://infura.io/>`_, which provides **free clients** running in the cloud:

.. code-block:: java

   AiManj aiManj = AiManj.build(new HttpService("https://morden.infura.io/your-token"));

For further information refer to :doc:`infura`.

Instructions on obtaining Man to transact on the network can be found in the
:ref:`testnet section of the docs <Matrix-testnets>`.

When you no longer need a `AiManj` instance you need to call the `shutdown` method to close resources used by it.

.. code-block:: java

   aiManj.shutdown()


Start sending requests
----------------------

To send synchronous requests::

   AiManj aiManj = AiManj.build(new HttpService());  // defaults to http://localhost:8545/
   AiManjClientVersion aiManjClientVersion = aiManj.aiManjClientVersion().send();
   String clientVersion = aiManjClientVersion.getAiManjClientVersion();

To send asynchronous requests using a CompletableFuture (Future on Android)::

   AiManj aiManj = AiManj.build(new HttpService());  // defaults to http://localhost:8545/
   AiManjClientVersion aiManjClientVersion = aiManj.aiManjClientVersion().sendAsync().get();
   String clientVersion = aiManjClientVersion.getAiManjClientVersion();

To use an RxJava Flowable::

   AiManj aiManj = AiManj.build(new HttpService());  // defaults to http://localhost:8545/
   aiManj.aiManjClientVersion().flowable().subscribe(x -> {
       String clientVersion = x.getAiManjClientVersion();
       ...
   });


IPC
---

aiManj also supports fast inter-process communication (IPC) via file sockets to clients running on
the same host as aiManj. To connect simply use the relevant *IpcService* implementation instead of
*HttpService* when you create your service:

.. code-block:: java

   // OS X/Linux/Unix:
   AiManj aiManj = AiManj.build(new UnixIpcService("/path/to/socketfile"));
   ...

   // Windows
   AiManj aiManj = AiManj.build(new WindowsIpcService("/path/to/namedpipefile"));
   ...

**Note:** IPC is not available on *aiManj-android*.


.. _smart-contract-wrappers-summary:

Working with smart contracts with Java smart contract wrappers
--------------------------------------------------------------

aiManj can auto-generate smart contract wrapper code to deploy and interact with smart contracts
without leaving the JVM.

To generate the wrapper code, compile your smart contract:

.. code-block:: bash

   $ solc <contract>.sol --bin --abi --optimize -o <output-dir>/

Then generate the wrapper code using aiManj's :doc:`command_line`:

.. code-block:: bash

   aiManj solidity generate -b /path/to/<smart-contract>.bin -a /path/to/<smart-contract>.abi -o /path/to/src/main/java -p com.your.organisation.name

Now you can create and deploy your smart contract::

   AiManj aiManj = AiManj.build(new HttpService());  // defaults to http://localhost:8545/
   Credentials credentials = WalletUtils.loadCredentials("password", "/path/to/walletfile");

   YourSmartContract contract = YourSmartContract.deploy(
           <aiManj>, <credentials>,
           GAS_PRICE, GAS_LIMIT,
           <param1>, ..., <paramN>).send();  // constructor params

Or use an existing contract::

   YourSmartContract contract = YourSmartContract.load(
           "0x<address>|<ensName>", <aiManj>, <credentials>, GAS_PRICE, GAS_LIMIT);

To transact with a smart contract::

   TransactionReceipt transactionReceipt = contract.someMethod(
                <param1>,
                ...).send();

To call a smart contract::

   Type result = contract.someMethod(<param1>, ...).send();

For more information refer to :ref:`smart-contract-wrappers`.


Filters
-------

aiManj functional-reactive nature makes it really simple to setup observers that notify subscribers
of events taking place on the blockchain.

To receive all new blocks as they are added to the blockchain::

   Subscription subscription = aiManj.blockFlowable(false).subscribe(block -> {
       ...
   });

To receive all new transactions as they are added to the blockchain::

   Subscription subscription = aiManj.transactionFlowable().subscribe(tx -> {
       ...
   });

To receive all pending transactions as they are submitted to the network (i.e. before they have
been grouped into a block together)::

   Subscription subscription = aiManj.pendingTransactionFlowable().subscribe(tx -> {
       ...
   });

Or, if you'd rather replay all blocks to the most current, and be notified of new subsequent
blocks being created::

   Subscription subscription = replayPastAndFutureBlocksFlowable(
           <startBlockNumber>, <fullTxObjects>)
           .subscribe(block -> {
               ...
   });

There are a number of other transaction and block replay Flowables described in :doc:`filters`.

Topic filters are also supported::

   ManFilter filter = new ManFilter(DefaultBlockParameterName.EARLIEST,
           DefaultBlockParameterName.LATEST, <contract-address>)
                .addSingleTopic(...)|.addOptionalTopics(..., ...)|...;
   aiManj.manLogFlowable(filter).subscribe(log -> {
       ...
   });

Subscriptions should always be cancelled when no longer required::

   subscription.unsubscribe();

**Note:** filters are not supported on Infura.

For further information refer to :doc:`filters` and the
`AiManjRx <https://github.com/aiManj/aiManj/blob/master/core/src/main/java/org/aiManj/protocol/rx/AiManjRx.java>`_
interface.


Transactions
------------

aiManj provides support for both working with Matrix wallet files (*recommended*) and Matrix
client admin commands for sending transactions.

To send Man to another party using your Matrix wallet file::

   AiManj aiManj = AiManj.build(new HttpService());  // defaults to http://localhost:8545/
   Credentials credentials = WalletUtils.loadCredentials("password", "/path/to/walletfile");
   TransactionReceipt transactionReceipt = Transfer.sendFunds(
           aiManj, credentials, "0x<address>|<ensName>",
           BigDecimal.valueOf(1.0), Convert.Unit.MAN)
           .send();

Or if you wish to create your own custom transaction::

   AiManj aiManj = AiManj.build(new HttpService());  // defaults to http://localhost:8545/
   Credentials credentials = WalletUtils.loadCredentials("password", "/path/to/walletfile");

   // get the next available nonce
   ManGetTransactionCount manGetTransactionCount = aiManj.manGetTransactionCount(
                address, DefaultBlockParameterName.LATEST).send();
   BigInteger nonce = manGetTransactionCount.getTransactionCount();

   // create our transaction
   RawTransaction rawTransaction  = RawTransaction.createManTransaction(
                nonce, <gas price>, <gas limit>, <toAddress>, <value>);

   // sign & send our transaction
   byte[] signedMessage = TransactionEncoder.signMessage(rawTransaction, credentials);
   String hexValue = Numeric.toHexString(signedMessage);
   ManSendTransaction manSendTransaction = aiManj.manSendRawTransaction(hexValue).send();
   // ...

Although it's far simpler using aiManj's `Transfer <https://github.com/aiManj/aiManj/blob/master/core/src/main/java/org/aiManj/tx/Transfer.java>`_
for transacting with Man.

Using an Matrix client's admin commands (make sure you have your wallet in the client's
keystore)::

   Admin aiManj = Admin.build(new HttpService());  // defaults to http://localhost:8545/
   PersonalUnlockAccount personalUnlockAccount = aiManj.personalUnlockAccount("0x000...", "a password").sendAsync().get();
   if (personalUnlockAccount.accountUnlocked()) {
       // send a transaction
   }

If you want to make use of Parity's
`Personal <https://github.com/paritytech/parity/wiki/JSONRPC-personal-module>`__ or
`Trace <https://github.com/paritytech/parity/wiki/JSONRPC-trace-module>`_, or Gman's
`Personal <https://github.com/Matrix/go-Matrix/wiki/Management-APIs#personal>`__ client APIs,
you can use the *org.aimanj:parity* and *org.aimanj:gman* modules respectively.

Publish/Subscribe (pub/sub)
---------------------------

Matrix clients implement the `pub/sub <https://github.com/Matrix/go-Matrix/wiki/RPC-PUB-SUB>`_
mechanism that provides the capability to subscribe to events from the network, allowing these clients to take custom
actions as needed. In doing so it alleviates the need to use polling and is more efficient.
This is achieved by using the WebSocket protocol instead of HTTP protocol.

Pub/Sub methods are available via the `WebSocketService` class, and allows the client to:

- send an RPC call over WebSocket protocol
- subscribe to WebSocket events
- unsubscribe from a stream of events

To create an instance of the `WebSocketService` class you need to first to create an instance of
the `WebSocketClient` that connects to an Matrix client via WebSocket protocol, and then pass it
to the `WebSocketService` constructor::

   final WebSocketClient webSocketClient = new WebSocketClient(new URI("ws://localhost/"));
   final boolean includeRawResponses = false;
   final WebSocketService webSocketService = new WebSocketService(webSocketClient, includeRawResponses)


To send an RPC request using the WebSocket protocol one need to use the `sendAsync` method on
the `WebSocketService` instance::

   // Request to get a version of an Matrix client
   final Request<?, AiManjClientVersion> request = new Request<>(
        // Name of an RPC method to call
        "aiManj_clientVersion",
        // Parameters for the method. "aiManj_clientVersion" does not expect any
        Collections.<String>emptyList(),
        // Service that is used to send a request
        webSocketService,
        // Type of an RPC call to get an Matrix client version
        AiManjClientVersion.class);

   // Send an asynchronous request via WebSocket protocol
   final CompletableFuture<AiManjClientVersion> reply = webSocketService.sendAsync(
                   request,
                   AiManjClientVersion.class);

   // Get result of the reply
   final AiManjClientVersion clientVersion = reply.get();

To use synchronous communication (i.e send a request and await a response) one would need to use the `sync` method instead::

   // Send a (synchronous) request via WebSocket protocol
   final AiManjClientVersion clientVersion = webSocketService.send(
                   request,
                   AiManjClientVersion.class);

To subscribe to WebSocket events `WebSocketService` provides the `subscribe` method.
`subscribe` returns an instance of the `Flowable <http://reactivex.io/RxJava/2.x/javadoc/io/reactivex/Flowable.html>`_
interface from the RxJava library, which allows the processing of incoming events from an Matrix network as a reactive stream.

To subscribe to a stream of events you should use `WebSocketService` to send an RPC method via WebSocket; this
is usually man_subscribe`. Events that it subscribes to depend on parameters to the man_subscribe`
method. You can find more in the `RPC documentation <https://github.com/Matrix/go-Matrix/wiki/RPC-PUB-SUB#supported-subscriptions>`_::

   // A request to subscribe to a stream of events
   final Request<?, ManSubscribe> subscribeRequest = new Request<>(
       // RPC method to subscribe to events
       man_subscribe",
       // Parameters that specify what events to subscribe to
       Arrays.asList("newHeads", Collections.emptyMap()),
       // Service that is used to send a request
       webSocketService,
       ManSubscribe.class);

   final Flowable<NewHeadsNotification> events = webSocketService.subscribe(
        subscribeRequest,
        // RPC method that should be used to unsubscribe from events
        man_unsubscribe",
        // Type of events returned by a request
        NewHeadsNotification.class
   );

   // Subscribe to incoming events and process incoming events
   final Disposable disposable = events.subscribe(event -> {
       // Process new heads event
   });


Notice that we need to provide a name of a method to `WebSocketService` that needs to be called to unsubscribe from
a stream of events. This is because different Matrix clients may have different methods to unsubscribe from
particular events. For example, the Parity client requires use of the `parity_unsubscribe` method to unsubscribe
from `pub/sub events <https://wiki.parity.io/JSONRPCman_pubsub-module>`_.

To unsubscribe from a stream of events one needs to use a `Flowable` instance for a particular events stream::

   final Flowable<NewHeadsNotification> events = ...
   final Disposable disposable = events.subscribe(...)
   disposable.dispose();

The methods described above are quite low-level, so we can use `AiManj` implementation instead::

   final WebSocketService webSocketService = ...
   final AiManj aiManj = AiManj.build(webSocketService)
   final Flowable<NewHeadsNotification> notifications = aiManj.newHeadsNotifications()

Command line tools
------------------

A aiManj fat jar is distributed with each release providing command line tools. The command line
tools allow you to use some of the functionality of aiManj from the command line:

- Wallet creation
- Wallet password management
- Transfer of funds from one wallet to another
- Generate Solidity smart contract function wrappers

Please refer to the :doc:`documentation <command_line>` for further
information.


Further details
---------------
In the Java 8 build:

- aiManj provides type safe access to all responses. Optional or null responses
  are wrapped in Java 8's
  `Optional <https://docs.oracle.com/javase/8/docs/api/java/util/Optional.html>`_ type.
- Asynchronous requests are wrapped in a Java 8
  `CompletableFutures <https://docs.oracle.com/javase/8/docs/api/java/util/concurrent/CompletableFuture.html>`_.
  aiManj provides a wrapper around all async requests to ensure that any exceptions during
  execution will be captured rather then silently discarded. This is due to the lack of support
  in *CompletableFutures* for checked exceptions, which are often rethrown as unchecked exception
  causing problems with detection. See the
  `Async.run() <https://github.com/aiManj/aiManj/blob/master/core/src/main/java/org/aiManj/utils/Async.java>`_ and its associated
  `test <https://github.com/aiManj/aiManj/blob/master/core/src/test/java/org/aiManj/utils/AsyncTest.java>`_ for details.

In both the Java 8 and Android builds:

- Quantity payload types are returned as `BigIntegers <https://docs.oracle.com/javase/8/docs/api/java/math/BigInteger.html>`_.
  For simple results, you can obtain the quantity as a String via
  `Response <https://github.com/aiManj/aiManj/blob/master/core/src/main/java/org/aiManj/protocol/core/Response.java>`_.getResult().
- It's also possible to include the raw JSON payload in responses via the *includeRawResponse*
  parameter, present in the
  `HttpService <https://github.com/aiManj/aiManj/blob/master/core/src/main/java/org/aiManj/protocol/http/HttpService.java>`_
  and
  `IpcService <https://github.com/aiManj/aiManj/blob/master/core/src/main/java/org/aiManj/protocol/ipc/IpcService.java>`_
  classes.
