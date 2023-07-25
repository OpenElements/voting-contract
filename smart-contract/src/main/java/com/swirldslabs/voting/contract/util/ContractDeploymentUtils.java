package com.swirldslabs.voting.contract.util;

import com.hedera.hashgraph.sdk.AccountId;
import com.hedera.hashgraph.sdk.Client;
import com.hedera.hashgraph.sdk.ContractCreateFlow;
import com.hedera.hashgraph.sdk.ContractFunctionParameters;
import com.hedera.hashgraph.sdk.ContractId;
import com.hedera.hashgraph.sdk.PrivateKey;
import com.hedera.hashgraph.sdk.TransactionReceipt;
import com.hedera.hashgraph.sdk.TransactionResponse;
import com.swirldslabs.util.HederaNode;
import java.io.IOException;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import org.bouncycastle.util.encoders.Hex;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ContractDeploymentUtils {

    private final static Logger logger = LoggerFactory.getLogger(ContractDeploymentUtils.class);

    public static byte[] readBin(final String contractName) throws IOException {
        final URL resource = ContractDeploymentUtils.class.getClassLoader().getResource(
                "com/swirldslabs/voting/contract/generated/" + contractName + ".bin");
        if (resource == null) {
            throw new IllegalStateException(
                    "contract bytecode file not found. Please execute the 'web3j:generate-sources' maven goal first.");
        }
        return Files.readString(Path.of(resource.getPath())).getBytes(StandardCharsets.UTF_8);
    }

    private static ContractId createContract(final Client client, final byte[] contractCode,
            final ContractFunctionParameters constructorParameters)
            throws Exception {
        final String content = new String(contractCode, StandardCharsets.UTF_8);
        final byte[] bytecodeInHex = Hex.decode(content);
        final ContractCreateFlow contractCreateFlow = new ContractCreateFlow()
                .setGas(1_000_000)
                .setBytecode(bytecodeInHex);
        if (constructorParameters != null) {
            contractCreateFlow.setConstructorParameters(constructorParameters);
        }
        final TransactionResponse transactionResponse = contractCreateFlow.execute(client);
        final TransactionReceipt contractReceipt = transactionResponse.getReceipt(client);
        return contractReceipt.contractId;
    }

    public static void deploy(final HederaNode node, final AccountId accountId, final PrivateKey privateKey,
            final byte[] contractCode) throws Exception {
        deploy(node, accountId, privateKey, contractCode, null);
    }

    public static void deploy(final HederaNode node, final AccountId accountId, final PrivateKey privateKey,
            final byte[] contractCode, final ContractFunctionParameters constructorParameters) throws Exception {

        final Client client = Client.forName(node.getName())
                .setOperator(accountId, privateKey);

        final ContractId contractId = createContract(client, contractCode, constructorParameters);
        logger.info("contract successfully created with ContractId '{}'", contractId);
    }
}
