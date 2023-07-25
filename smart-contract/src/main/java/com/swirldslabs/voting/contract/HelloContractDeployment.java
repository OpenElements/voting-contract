package com.swirldslabs.voting.contract;

import com.hedera.hashgraph.sdk.AccountId;
import com.hedera.hashgraph.sdk.ContractFunctionParameters;
import com.hedera.hashgraph.sdk.PrivateKey;
import com.swirldslabs.voting.contract.util.ContractDeploymentUtils;
import com.swirldslabs.util.HederaNode;
import io.github.cdimascio.dotenv.Dotenv;

public class HelloContractDeployment {

    public static void main(final String[] args) throws Exception {

        final String contractName = "HelloWorldContract";
        final String accountIdValue = Dotenv.load().get("HEDERA_ACCOUNT_ID");
        final String privateKeyValue = Dotenv.load().get("HEDERA_PRIVATE_KEY");

        final AccountId accountId = AccountId.fromString(accountIdValue);
        final PrivateKey privateKey = PrivateKey.fromString(privateKeyValue);
        final byte[] byteCode = ContractDeploymentUtils.readBin(contractName);

        final ContractFunctionParameters constructorParams = new ContractFunctionParameters()
                .addString("hello from hedera!");

        ContractDeploymentUtils.deploy(HederaNode.TESTNET, accountId, privateKey, byteCode, constructorParams);
    }
}
