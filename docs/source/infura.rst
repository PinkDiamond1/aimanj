Using Infura with aiManj
=======================

Signing up
----------

The `Infura <https://infura.io/>`_ service by `ConsenSys <https://consensys.net/>`_, provides
Matrix clients running in the cloud, so you don't have to run one yourself to work with Matrix.

When you sign up to the service you are provided with a token you can use to connect to the
relevant Matrix network:

Main Matrix Network:
  https://mainnet.infura.io/<your-token>

Test Matrix Network (Rinkeby):
  https://rinkeby.infura.io/<your-token>

Test Matrix Network (Kovan):
  https://kovan.infura.io/<your-token>

Test Matrix Network (Ropsten):
  https://ropsten.infura.io/<your-token>


For obtaining man to use in these networks, you can refer to :ref:`Matrix-testnets`


InfuraHttpClient
----------------

The aiManj infura module provides an Infura HTTP client
(`InfuraHttpService <https://github.com/aiManj/aiManj/blob/master/infura/src/main/java/org/aiManj/protocol/infura/InfuraHttpService.java>`_)
which provides support for the Infura specific *Infura-Matrix-Preferred-Client* header. This
allows you to specify whether you want a Gman or Parity client to respond to your request. You
can create the client just like the regular HTTPClient::

   AiManj aiManj = AiManj.build(new HttpService("https://testnet.matrix.io"));
   AiManjClientVersion aiManjClientVersion = aiManj.AiManj3ClientVersion().send();
   System.out.println(aiManjClientVersion.getAiManjClientVersion());

.. code-block:: bash

   Gman/v1.7.2-stable-1db4ecdc/darwin-amd64/go1.9.1

If you want to test a number of the JSON-RPC calls against Infura, update the integration test
`CoreIT <https://github.com/aiManj/aiManj/blob/master/integration-tests/src/test/java/org/aiManj/protocol/core/CoreIT.java>`_
with your Infura URL & run it.

For further information, refer to the
`Infura docs <https://github.com/INFURA/infura/blob/master/docs/source/index.html.md#choosing-a-client-to-handle-your-request>`_.


Transactions
------------

In order to transact with Infura nodes, you will need to create and sign transactions offline
before sending them, as Infura nodes have no visibility of your encrypted Matrix key files, which
are required to unlock accounts via the Personal Gman/Parity admin commands.

Refer to the :ref:`offline-signing` and :doc:`management_apis` sections for further details.
