Management APIs
===============

In addition to implementing the standard
`JSON-RPC <https://github.com/Matrix/wiki/wiki/JSON-RPC>`_ API, Matrix clients, such as
`Gman <https://github.com/Matrix/go-Matrix/wiki/gman>`__ and
`Parity <https://github.com/paritytech/parity>`__ provide additional management via JSON-RPC.

One of the key common pieces of functionality that they provide is the ability to create and
unlock Matrix accounts for transacting on the network. In Gman and Parity, this is implemented
in their Personal modules, details of which are available below:

- `Parity <https://github.com/paritytech/parity/wiki/JSONRPC-personal-module>`__
- `Gman <https://github.com/Matrix/go-Matrix/wiki/Management-APIs#personal>`__

Support for the personal modules is available in aiManj. Those methods that are common to both Gman
and Parity reside in the `Admin <https://github.com/aiManj/aiManj/blob/master/core/src/main/java/org/aiManj/protocol/admin/Admin.java>`_ module of aiManj.

You can initialise a new aiManj connector that supports this module using the factory method::

   Admin aiManj = Admin.build(new HttpService());  // defaults to http://localhost:8545/
   PersonalUnlockAccount personalUnlockAccount = admin.personalUnlockAccount("0x000...", "a password").send();
   if (personalUnlockAccount.accountUnlocked()) {
       // send a transaction
   }

For Gman specific methods, you can use the
`Gman <https://github.com/aiManj/aiManj/blob/master/Gman/src/main/java/org/aiManj/protocol/gman/Gman.java>`_
connector, and for Parity you can use the associated
`Parity <https://github.com/aiManj/aiManj/blob/master/parity/src/main/java/org/aiManj/protocol/parity/Parity.java>`_
connector. The *Parity* connector also provides support for Parity's
`Trace <https://github.com/paritytech/parity/wiki/JSONRPC-trace-module>`_ module. These connectors
are available in the aiManj *gman* and *parity* modules respectively.

You can refer to the integration test
`ParityIT <https://github.com/aiManj/aiManj/blob/master/integration-tests/src/test/java/org/aiManj/protocol/parity/ParityIT.java>`_
for further examples of working with these APIs.
