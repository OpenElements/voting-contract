package com.swirldslabs.voting.contract;

import com.hedera.hashgraph.sdk.AccountId;
import com.hedera.hashgraph.sdk.ContractFunctionParameters;
import com.hedera.hashgraph.sdk.PrivateKey;
import com.swirldslabs.util.HederaNode;
import com.swirldslabs.voting.contract.util.ContractDeploymentUtils;
import io.github.cdimascio.dotenv.Dotenv;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;

public class VotingContractDeployment {

    public static void main(final String[] args) throws Exception {

        final String contractName = "VotingContract";
        final String accountIdValue = Dotenv.load().get("HEDERA_ACCOUNT_ID");
        final String privateKeyValue = Dotenv.load().get("HEDERA_PRIVATE_KEY");

        final AccountId accountId = AccountId.fromString(accountIdValue);
        final PrivateKey privateKey = PrivateKey.fromString(privateKeyValue);
        final byte[] byteCode = ContractDeploymentUtils.readBin(contractName);

        final String[] proposalNames = new String[] {
                "Python", "C", "C++", "Java", "C#", "JavaScript", "Visual Basic", "SQL", "PHP", "MATLAB"
        };
        byte[][] proposals = Arrays.stream(proposalNames).map(s -> s.getBytes(StandardCharsets.UTF_8)).toArray(byte[][]::new);

        final ContractFunctionParameters parameters = new ContractFunctionParameters()
                .addBytes32Array(proposals);

        ContractDeploymentUtils.deploy(HederaNode.TESTNET, accountId, privateKey, byteCode, parameters);
    }
}
