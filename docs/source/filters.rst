Filters and Events
==================

Filters provide notifications of certain events taking place in the Matrix network. There are
three classes of filter supported in Matrix:

#. Block filters
#. Pending transaction filters
#. Topic filters

Block filters and pending transaction filters provide notification of the creation of new
transactions or blocks on the network.

Topic filters are more flexible. These allow you to create a filter based on specific criteria
that you provide.

Unfortunately, unless you are using a WebSocket connection to Gman, working with filters via the
JSON-RPC API is a tedious process, where you need to poll the Matrix client in order to find out
if there are any updates to your filters due to the synchronous nature of HTTP and IPC requests.
Additionally the block and transaction filters only provide the transaction or block hash, so a
further request is required to obtain the actual transaction or block referred to by the hash.

aiManj's managed `Filter <https://github.com/aiManj/aiManj/blob/master/core/src/main/java/org/aiManj/protocol/core/filters/Filter.java>`_
implementation address these issues, so you have a fully asynchronous event based API for working
with filters. It uses `RxJava <https://github.com/ReactiveX/RxJava>`_'s
`Flowables <http://reactivex.io/RxJava/2.x/javadoc/io/reactivex/Flowable.html>`_ which provides a consistent API
for working with events, which facilitates the chaining together of JSON-RPC calls via
functional composition.

**Note:** filters are not supported on Infura.


Block and transaction filters
-----------------------------

To receive all new blocks as they are added to the blockchain (the false parameter specifies that
we only want the blocks, not the embedded transactions too)::

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

Subscriptions should always be cancelled when no longer required via *unsubscribe*::

   subscription.unsubscribe();

Other callbacks are also provided which provide simply the block or transaction hashes,
for details of these refer to the
`AiManjjRx <https://github.com/aiManj/aiManj/blob/master/core/src/main/java/org/aiManj/protocol/rx/AiManjRx.java>`_
interface.


Replay filters
--------------

aiManj also provides filters for replaying block and transaction history.

To replay a range of blocks from the blockchain::

   Subscription subscription = aiManj.replayBlocksFlowable(
           <startBlockNumber>, <endBlockNumber>, <fullTxObjects>)
           .subscribe(block -> {
               ...
   });

To replay the individual transactions contained within a range of blocks::

   Subscription subscription = aiManj.replayTransactionsFlowable(
           <startBlockNumber>, <endBlockNumber>)
           .subscribe(tx -> {
               ...
   });

You can also get aiManj to replay all blocks up to the most current, and provide notification
(via the submitted Flowable) once you've caught up::

   Subscription subscription = aiManj.replayPastBlocksFlowable(
           <startBlockNumber>, <fullTxObjects>, <onCompleteFlowable>)
           .subscribe(block -> {
               ...
   });

Or, if you'd rather replay all blocks to the most current, then be notified of new subsequent
blocks being created::

   Subscription subscription = aiManj.replayPastAndFutureBlocksFlowable(
           <startBlockNumber>, <fullTxObjects>)
           .subscribe(block -> {
               ...
   });

As above, but with transactions contained within blocks::

   Subscription subscription = aiManj.replayPastAndFutureTransactionsFlowable(
           <startBlockNumber>)
           .subscribe(tx -> {
               ...
   });

All of the above filters are exported via the
`AiManjRx <https://github.com/aiManj/aiManj/blob/master/core/src/main/java/org/aiManj/protocol/rx/AiManjjRx.java>`_
interface.


.. _filters-and-events:

Topic filters and EVM events
----------------------------

Topic filters capture details of Matrix Virtual Machine (EVM) events taking place in the network.
These events are created by smart contracts and stored in the transaction log associated with a
smart contract.

The `Solidity documentation <http://solidity.readthedocs.io/en/develop/contracts.html#events>`_
provides a good overview of EVM events.

You use the
`ManFilter <https://github.com/aiManj/aiManj/blob/master/core/src/main/java/org/aiManj/protocol/core/methods/request/ManFilter.java>`_
type to specify the topics that you wish to apply to the filter. This can include the address of
the smart contract you wish to apply the filter to. You can also provide specific topics to filter
on. Where the individual topics represent indexed parameters on the smart contract::

   ManFilter filter = new ManFilter(DefaultBlockParameterName.EARLIEST,
           DefaultBlockParameterName.LATEST, <contract-address>)
                [.addSingleTopic(...) | .addOptionalTopics(..., ...) | ...];

This filter can then be created using a similar syntax to the block and transaction filters above::

   aiManj.manLogFlowable(filter).subscribe(log -> {
       ...
   });

The filter topics can only refer to the indexed Solidity event parameters. It is not possible to
filter on the non-indexed event parameters. Additionally, for any indexed event parameters that are
variable length array types such as string and bytes, the Keccak-256 hash of their value is stored
on the EVM log. It is not possible to store or filter using their full value.

If you create a filter instance with no topics associated with it, all EVM events taking place in
the network will be captured by the filter.


A note on functional composition
--------------------------------

In addition to *send()* and *sendAsync*, all JSON-RPC method implementations in man support the
*flowable()* method to create a Flowable to execute the request asynchronously. This makes it
very straight forwards to compose JSON-RPC calls together into new functions.

For instance, the
`blockFlowable <https://github.com/aiManj/aiManj/blob/master/core/src/main/java/org/aiManj/protocol/rx/JsonRpc2_0Rx.java>`_ is
itself composed of a number of separate JSON-RPC calls::

   public Flowable<ManBlock> blockFlowable(
           boolean fullTransactionObjects, long pollingInterval) {
       return this.manBlockHashFlowable(pollingInterval)
               .flatMap(blockHash ->
                       aiManj.manGetBlockByHash(blockHash, fullTransactionObjects).flowable());
   }

Here we first create a flowable that provides notifications of the block hash of each newly
created block. We then use *flatMap* to invoke a call to *manGetBlockByHash* to obtain the full
block details which is what is passed to the subscriber of the flowable.


Further examples
----------------

Please refer to the integration test
`FlowableIT <https://github.com/aiManj/aiManj/blob/master/integration-tests/src/test/java/org/aiManj/protocol/core/FlowableIT.java>`_
for further examples.

For a demonstration of using the manual filter API, you can take a look at the test
`EventFilterIT <https://github.com/aiManj/aiManj/blob/master/integration-tests/src/test/java/org/aiManj/protocol/scenarios/EventFilterIT.java>`_..
