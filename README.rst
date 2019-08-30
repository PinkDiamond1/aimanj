.. To build this file locally ensure docutils Python package is installed and run:
   $ rst2html.py README.rst README.html

aiManj: AiMan Java Matrix Ðapp API
==================================

.. image:: https://readthedocs.org/projects/aiManj/badge/?version=latest
   :target: http://docs.aiManj.io
   :alt: Documentation Status

.. image:: https://travis-ci.org/aiManj/aiManj.svg?branch=master
   :target: https://travis-ci.org/aiManj/aiManj
   :alt: Build Status

.. image:: https://codecov.io/gh/aiManj/aiManj/branch/master/graph/badge.svg
   :target: https://codecov.io/gh/aiManj/aiManj
   :alt: codecov

.. image:: https://badges.gitter.im/aiManj/aiManj.svg
   :target: https://gitter.im/aiManj/aiManj?utm_source=badge&utm_medium=badge&utm_campaign=pr-badge&utm_content=badge
   :alt: Join the chat at https://gitter.im/aiManj/aiManj

aiManj is a lightweight, highly modular, reactive, type safe Java and Android library for working with
Smart Contracts and integrating with clients (nodes) on the Matrix network:

.. image:: https://raw.githubusercontent.com/aiManj/aiManj/master/docs/source/images/aiManj_network.png

This allows you to work with the `Matrix <https://www.matrix.org/>`_ blockchain, without the
additional overhead of having to write your own integration code for the platform.

The `Java and the Blockchain <https://www.youtube.com/watch?v=ea3miXs_P6Y>`_ talk provides an
overview of blockchain, Matrix and aiManj.


Features
--------

- Complete implementation of Matrix's `JSON-RPC <https://github.com/matrix/wiki/wiki/JSON-RPC>`_
  client API over HTTP and IPC
- Matrix wallet support
- Auto-generation of Java smart contract wrappers to create, deploy, transact with and call smart
  contracts from native Java code
  (`Solidity <http://solidity.readthedocs.io/en/latest/using-the-compiler.html#using-the-commandline-compiler>`_
  and
  `Truffle <https://github.com/trufflesuite/truffle-contract-schema>`_ definition formats supported)
- Reactive-functional API for working with filters
- `Matrix Name Service (ENS) <https://ens.domains/>`_ support
- Support for Parity's
  `Personal <https://github.com/paritytech/parity/wiki/JSONRPC-personal-module>`__, and Gman's
  `Personal <https://github.com/matrix/go-matrix/wiki/Management-APIs#personal>`__ client APIs
- Support for `Infura <https://infura.io/>`_, so you don't have to run an Matrix client yourself
- Comprehensive integration tests demonstrating a number of the above scenarios
- Command line tools
- Android compatible
- Support for JP Morgan's Quorum via `aiManj-quorum <https://github.com/aiManj/quorum>`_


It has five runtime dependencies:

- `RxJava <https://github.com/ReactiveX/RxJava>`_ for its reactive-functional API
- `OKHttp <https://hc.apache.org/httpcomponents-client-ga/index.html>`_ for HTTP connections
- `Jackson Core <https://github.com/FasterXML/jackson-core>`_ for fast JSON
  serialisation/deserialisation
- `Bouncy Castle <https://www.bouncycastle.org/>`_
  (`Spongy Castle <https://rtyley.github.io/spongycastle/>`_ on Android) for crypto
- `Jnr-unixsocket <https://github.com/jnr/jnr-unixsocket>`_ for \*nix IPC (not available on
  Android)

It also uses `JavaPoet <https://github.com/square/javapoet>`_ for generating smart contract
wrappers.

Full project documentation is available at
`docs.aiManj.io <http://docs.aiManj.io>`_.


Donate
------

You can help fund the development of aiManj by donating to the following wallet addresses:

+----------+--------------------------------------------+
| Matrix | 0x2dfBf35bb7c3c0A466A6C48BEBf3eF7576d3C420 |
+----------+--------------------------------------------+
| Bitcoin  | 1DfUeRWUy4VjekPmmZUNqCjcJBMwsyp61G         |
+----------+--------------------------------------------+


Commercial support and training
-------------------------------

Commercial support and training is available from `blk.io <https://blk.io>`_.


Quickstart
----------

A `aiManj sample project <https://github.com/aiManj/sample-project-gradle>`_ is available that
demonstrates a number of core features of Matrix with aiManj, including:

- Connecting to a node on the Matrix network
- Loading an Matrix wallet file
- Sending Man from one address to another
- Deploying a smart contract to the network
- Reading a value from the deployed smart contract
- Updating a value in the deployed smart contract
- Viewing an event logged by the smart contract


Getting started
---------------

Typically your application should depend on release versions of aiManj, but you may also use snapshot dependencies
for early access to features and fixes, refer to the  `Snapshot Dependencies`_ section.

| Add the relevant dependency to your project:

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

Plugins
-------
There are also gradle and maven plugins to help you generate aiManj Java wrappers for your Solidity smart contracts,
thus allowing you to integrate such activities into your project lifecycle.

Take a look at the project homepage for the
`aiManj-gradle-plugin <https://github.com/aiManj/aiManj-gradle-plugin>`_
and `aiManj-maven-plugin <https://github.com/aiManj/aiManj-maven-plugin>`_ for details on how to use these plugins.


Start a client
--------------

Start up an Matrix client if you don't already have one running, such as
`Gman <https://github.com/matrix/go-matrix/wiki/gman>`_:

.. code-block:: bash

   $ gman --rpcapi personal,db,man,net,aiMan --rpc --testnet

Or `Parity <https://github.com/paritytech/parity>`_:

.. code-block:: bash

   $ parity --chain testnet

Or use `Infura <https://infura.io/>`_, which provides **free clients** running in the cloud:

.. code-block:: java

   AiManj aiMan = AiManj.build(new HttpService("https://ropsten.infura.io/your-token"));

For further information refer to
`Using Infura with aiManj <https://aiManj.github.io/aiManj/infura.html>`_

Instructions on obtaining Man to transact on the network can be found in the
`testnet section of the docs <http://docs.aiManj.io/transactions.html#matrix-testnets>`_.


Start sending requests
----------------------

To send synchronous requests:

.. code-block:: java

   AiManj aiMan = AiManj.build(new HttpService());  // defaults to http://localhost:8545/
   AiManClientVersion aiManClientVersion = aiMan.aiManClientVersion().send();
   String clientVersion = aiManClientVersion.getAiManClientVersion();


To send asynchronous requests using a CompletableFuture (Future on Android):

.. code-block:: java

   AiManj aiMan = AiManj.build(new HttpService());  // defaults to http://localhost:8545/
   AiManClientVersion aiManClientVersion = aiMan.aiManClientVersion().sendAsync().get();
   String clientVersion = aiManClientVersion.getAiManClientVersion();

To use an RxJava Flowable:

.. code-block:: java

   AiManj aiMan = AiManj.build(new HttpService());  // defaults to http://localhost:8545/
   aiMan.aiManClientVersion().flowable().subscribe(x -> {
       String clientVersion = x.getAiManClientVersion();
       ...
   });


IPC
---

aiManj also supports fast inter-process communication (IPC) via file sockets to clients running on
the same host as aiManj. To connect simply use the relevant *IpcService* implementation instead of
*HttpService* when you create your service:

.. code-block:: java

   // OS X/Linux/Unix:
   AiManj aiMan = AiManj.build(new UnixIpcService("/path/to/socketfile"));
   ...

   // Windows
   AiManj aiMan = AiManj.build(new WindowsIpcService("/path/to/namedpipefile"));
   ...

**Note:** IPC is not currently available on aiManj-android.


Working with smart contracts with Java smart contract wrappers
--------------------------------------------------------------

aiManj can auto-generate smart contract wrapper code to deploy and interact with smart contracts
without leaving the JVM.

To generate the wrapper code, compile your smart contract:

.. code-block:: bash

   $ solc <contract>.sol --bin --abi --optimize -o <output-dir>/

Then generate the wrapper code using aiManj's `Command line tools`_:

.. code-block:: bash

   aiManj solidity generate -b /path/to/<smart-contract>.bin -a /path/to/<smart-contract>.abi -o /path/to/src/main/java -p com.your.organisation.name

Now you can create and deploy your smart contract:

.. code-block:: java

   AiManj aiMan = AiManj.build(new HttpService());  // defaults to http://localhost:8545/
   Credentials credentials = WalletUtils.loadCredentials("password", "/path/to/walletfile");

   YourSmartContract contract = YourSmartContract.deploy(
           <aiManj>, <credentials>,
           GAS_PRICE, GAS_LIMIT,
           <param1>, ..., <paramN>).send();  // constructor params

Alternatively, if you use `Truffle <http://truffleframework.com/>`_, you can make use of its `.json` output files:

.. code-block:: bash

   # Inside your Truffle project
   $ truffle compile
   $ truffle deploy

Then generate the wrapper code using aiManj's `Command line tools`_:

.. code-block:: bash

   $ cd /path/to/your/aiManj/java/project
   $ aiManj truffle generate /path/to/<truffle-smart-contract-output>.json -o /path/to/src/main/java -p com.your.organisation.name

Whether using `Truffle` or `solc` directly, either way you get a ready-to-use Java wrapper for your contract.

So, to use an existing contract:

.. code-block:: java

   YourSmartContract contract = YourSmartContract.load(
           "0x<address>|<ensName>", <aiManj>, <credentials>, GAS_PRICE, GAS_LIMIT);

To transact with a smart contract:

.. code-block:: java

   TransactionReceipt transactionReceipt = contract.someMethod(
                <param1>,
                ...).send();

To call a smart contract:

.. code-block:: java

   Type result = contract.someMethod(<param1>, ...).send();

To fine control your gas price:

.. code-block:: java

    contract.setGasProvider(new DefaultGasProvider() {
            ...
            });

For more information refer to `Smart Contracts <http://docs.aiManj.io/smart_contracts.html#solidity-smart-contract-wrappers>`_.


Filters
-------

aiManj functional-reactive nature makes it really simple to setup observers that notify subscribers
of events taking place on the blockchain.

To receive all new blocks as they are added to the blockchain:

.. code-block:: java

   Subscription subscription = aiManj.blockFlowable(false).subscribe(block -> {
       ...
   });

To receive all new transactions as they are added to the blockchain:

.. code-block:: java

   Subscription subscription = aiManj.transactionFlowable().subscribe(tx -> {
       ...
   });

To receive all pending transactions as they are submitted to the network (i.e. before they have
been grouped into a block together):

.. code-block:: java

   Subscription subscription = aiManj.pendingTransactionFlowable().subscribe(tx -> {
       ...
   });

Or, if you'd rather replay all blocks to the most current, and be notified of new subsequent
blocks being created:

.. code-block:: java
   Subscription subscription = replayPastAndFutureBlocksFlowable(
           <startBlockNumber>, <fullTxObjects>)
           .subscribe(block -> {
               ...
   });

There are a number of other transaction and block replay Flowables described in the
`docs <http://docs.aiManj.io/filters.html>`_.

Topic filters are also supported:

.. code-block:: java

   ManFilter filter = new ManFilter(DefaultBlockParameterName.EARLIEST,
           DefaultBlockParameterName.LATEST, <contract-address>)
                .addSingleTopic(...)|.addOptionalTopics(..., ...)|...;
   aiManj.manLogFlowable(filter).subscribe(log -> {
       ...
   });

Subscriptions should always be cancelled when no longer required:

.. code-block:: java

   subscription.unsubscribe();

**Note:** filters are not supported on Infura.

For further information refer to `Filters and Events <http://docs.aiManj.io/filters.html>`_ and the
`AiManjRx <https://github.com/aiManj/aiManj/blob/master/src/core/main/java/org/aiManj/protocol/rx/AiManjRx.java>`_
interface.


Transactions
------------

aiManj provides support for both working with Matrix wallet files (recommended) and Matrix
client admin commands for sending transactions.

To send Man to another party using your Matrix wallet file:

.. code-block:: java

   AiManj aiMan = AiManj.build(new HttpService());  // defaults to http://localhost:8545/
   Credentials credentials = WalletUtils.loadCredentials("password", "/path/to/walletfile");
   TransactionReceipt transactionReceipt = Transfer.sendFunds(
           aiMan, credentials, "0x<address>|<ensName>",
           BigDecimal.valueOf(1.0), Convert.Unit.Man)
           .send();

Or if you wish to create your own custom transaction:

.. code-block:: java

   AiManj aiMan = AiManj.build(new HttpService());  // defaults to http://localhost:8545/
   Credentials credentials = WalletUtils.loadCredentials("password", "/path/to/walletfile");

   // get the next available nonce
   ManGetTransactionCount manGetTransactionCount = aiManj.manGetTransactionCount(
                address, DefaultBlockParameterName.LATEST).sendAsync().get();
   BigInteger nonce = manGetTransactionCount.getTransactionCount();

   // create our transaction
   RawTransaction rawTransaction  = RawTransaction.createManTransaction(
                nonce, <gas price>, <gas limit>, <toAddress>, <value>);

   // sign & send our transaction
   byte[] signedMessage = TransactionEncoder.signMessage(rawTransaction, credentials);
   String hexValue = Hex.toHexString(signedMessage);
   ManSendTransaction manSendTransaction = aiManj.manSendRawTransaction(hexValue).send();
   // ...

Although it's far simpler using aiManj's `Transfer <https://github.com/aiManj/aiManj/blob/master/core/src/main/java/org/aiManj/tx/Transfer.java>`_
for transacting with Man.

Using an Matrix client's admin commands (make sure you have your wallet in the client's
keystore):

.. code-block:: java

   Admin aiManj = Admin.build(new HttpService());  // defaults to http://localhost:8545/
   PersonalUnlockAccount personalUnlockAccount = aiManj.personalUnlockAccount("0x000...", "a password").sendAsync().get();
   if (personalUnlockAccount.accountUnlocked()) {
       // send a transaction
   }

If you want to make use of Parity's
`Personal <https://github.com/paritytech/parity/wiki/JSONRPC-personal-module>`__ or
`Trace <https://github.com/paritytech/parity/wiki/JSONRPC-trace-module>`_, or Gman's
`Personal <https://github.com/matrix/go-matrix/wiki/Management-APIs#personal>`__ client APIs,
you can use the *org.aimanj:parity* and *org.aimanj:gman* modules respectively.


Command line tools
------------------

A aiManj fat jar is distributed with each release providing command line tools. The command line
tools allow you to use some of the functionality of aiManj from the command line:

- Wallet creation
- Wallet password management
- Transfer of funds from one wallet to another
- Generate Solidity smart contract function wrappers

Please refer to the `documentation <http://docs.aiManj.io/command_line.html>`_ for further
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
  `Response <https://github.com/aiManj/aiManj/blob/master/src/main/java/org/aiManj/protocol/core/Response.java>`_.getResult().
- It's also possible to include the raw JSON payload in responses via the *includeRawResponse*
  parameter, present in the
  `HttpService <https://github.com/aiManj/aiManj/blob/master/core/src/main/java/org/aiManj/protocol/http/HttpService.java>`_
  and
  `IpcService <https://github.com/aiManj/aiManj/blob/master/core/src/main/java/org/aiManj/protocol/ipc/IpcService.java>`_
  classes.


Tested clients
--------------

- Gman
- Parity

You can run the integration test class
`CoreIT <https://github.com/aiManj/aiManj/blob/master/integration-tests/src/test/java/org/aiManj/protocol/core/CoreIT.java>`_
to verify clients.


Related projects
----------------

For a .NET implementation, check out `Nmatrix <https://github.com/Nmatrix/Nmatrix>`_.

For a pure Java implementation of the Matrix client, check out
`MatrixJ <https://github.com/matrix/matrixj>`_ and
`Matrix Harmony <https://github.com/man-camp/matrix-harmony>`_.


Projects using aiManj
--------------------

Please submit a pull request if you wish to include your project on the list:

- `ERC-20 RESTful Service <https://github.com/blk-io/erc20-rest-service>`_
- `Man Wallet <https://play.google.com/store/apps/details?id=org.vikulin.manwallet>`_ by
  `@vikulin <https://github.com/vikulin>`_
- `man-contract-api <https://github.com/adridadou/man-contract-api>`_ by
  `@adridadou <https://github.com/adridadou>`_
- `Matrix Paper Wallet <https://github.com/matthiaszimmermann/matrix-paper-wallet>`_ by
  `@matthiaszimmermann <https://github.com/matthiaszimmermann>`_
- `Trust Matrix Wallet <https://github.com/TrustWallet/trust-wallet-android>`_
- `Presto Matrix <https://github.com/xiaoyao1991/presto-matrix>`_
- `Kundera-Matrix data importer and sync utility <https://github.com/impetus-opensource/Kundera/tree/trunk/src/kundera-matrix>`_ by `@impetus-opensource <https://github.com/impetus-opensource>`_
- `Matrix JDBC Connector <https://github.com/Impetus/man-jdbc-connector/>`_ by `@impetus-opensource <https://github.com/impetus-opensource>`_
- `Matrix Tool <https://github.com/e-Contract/matrix-tool>`_ for secure offline key management.
- `Matrix Java EE JCA Resource Adapter <https://github.com/e-Contract/matrix-resource-adapter>`_ provides integration of Matrix within Java EE 6+.
- `Apache Camel Matrix Component <https://github.com/apache/camel/blob/master/components/camel-aiManj/src/main/docs/aiManj-component.adoc>`_ by `@bibryam <https://github.com/bibryam/>`_.
- `Manlinker for UE4 <https://bitbucket.org/kelheor/manlinker-for-ue4>`_ - interact with Matrix blockchain from Unreal Engine 4.



Companies using aiManj
---------------------

Please submit a pull request if you wish to include your company on the list:

- `Amberdata <https://www.amberdata.io/>`_
- `blk.io <https://blk.io>`_
- `comitFS <http://www.comitfs.com/>`_
- `ConsenSys <https://consensys.net/>`_
- `ING <https://www.ing.com>`_
- `Othera <https://www.othera.io/>`_
- `Pactum <https://pactum.io/>`_
- `TrustWallet <http://trustwalletapp.com>`_
- `Impetus <http://www.impetus.com/>`_
- `Argent Labs <http://www.argent.im/>`_


Build instructions
------------------

aiManj includes integration tests for running against a live Matrix client. If you do not have a
client running, you can exclude their execution as per the below instructions.

To run a full build (excluding integration tests):

.. code-block:: bash

   $ ./gradlew check


To run the integration tests:

.. code-block:: bash

   $ ./gradlew  -Pintegration-tests=true :integration-tests:test


Snapshot Dependencies
---------------------

Snapshot versions of aiManj follow the ``<major>.<minor>.<build>-SNAPSHOT`` convention, for example: 4.2.0-SNAPSHOT.

| If you would like to use snapshots instead please add a new maven repository pointing to:

::

  https://oss.sonatype.org/content/repositories/snapshots

Please refer to the `maven <https://maven.apache.org/guides/mini/guide-multiple-repositories.html>`_ or `gradle <https://maven.apache.org/guides/mini/guide-multiple-repositories.html>`_ documentation for further detail.

Sample gradle configuration:

.. code-block:: groovy

   repositories {
      maven {
         url "https://oss.sonatype.org/content/repositories/snapshots"
      }
   }

Sample maven configuration:

.. code-block:: xml

   <repositories>
     <repository>
       <id>sonatype-snasphots</id>
       <name>Sonatype snapshots repo</name>
       <url>https://oss.sonatype.org/content/repositories/snapshots</url>
     </repository>
   </repositories>

Thanks and credits
------------------

- The `Nmatrix <https://github.com/Nmatrix/Nmatrix>`_ project for the inspiration
- `Othera <https://www.othera.com.au/>`_ for the great things they are building on the platform
- `Finhaus <http://finhaus.com.au/>`_ guys for putting me onto Nmatrix
- `bitcoinj <https://bitcoinj.github.io/>`_ for the reference Elliptic Curve crypto implementation
- Everyone involved in the Matrix project and its surrounding ecosystem
- And of course the users of the library, who've provided valuable input & feedback
