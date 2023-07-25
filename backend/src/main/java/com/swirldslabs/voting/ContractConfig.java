package com.swirldslabs.voting;

import com.hedera.hashgraph.sdk.AccountId;
import com.hedera.hashgraph.sdk.Client;
import com.hedera.hashgraph.sdk.ContractId;
import com.hedera.hashgraph.sdk.PrivateKey;
import com.swirldslabs.util.HederaNode;
import com.swirldslabs.util.HederaUtils;
import com.swirldslabs.voting.contract.generated.MessageStoreContract;
import io.github.cdimascio.dotenv.Dotenv;
import java.math.BigInteger;
import java.util.Objects;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.context.annotation.ApplicationScope;
import org.web3j.crypto.Credentials;
import org.web3j.protocol.Web3j;
import org.web3j.tx.Contract;
import org.web3j.tx.gas.ContractGasProvider;
import org.web3j.tx.gas.StaticGasProvider;

@Configuration
public class ContractConfig {

    private final static Logger logger = LoggerFactory.getLogger(ContractConfig.class);

    private final static HederaNode NODE = HederaNode.TESTNET;

    @Value("${hedera.contracts.messageContract.address}")
    private String messageContractAddress;

    @Bean
    @ApplicationScope
    public Web3j web3j() {
        return HederaUtils.createWeb3j(NODE);
    }

    @Bean
    public Credentials credentials() {
        final PrivateKey privateKey = PrivateKey.fromString(Dotenv.load().get("HEDERA_PRIVATE_KEY"));
        return HederaUtils.toCredentials(privateKey);
    }

    @Bean
    public Client client() {
        final AccountId accountId = AccountId.fromString(Dotenv.load().get("HEDERA_ACCOUNT_ID"));
        final PrivateKey privateKey = PrivateKey.fromString(Dotenv.load().get("HEDERA_PRIVATE_KEY"));

        return Client.forName(NODE.getName()).setOperator(accountId, privateKey);
    }

    @Bean
    @ApplicationScope
    public MessageStoreContract helloWorldContract(final Web3j web3j,
            final Credentials credentials) {
        try {
            final ContractId contractId = ContractId.fromString(messageContractAddress);
            return createContractWrapper(web3j, credentials,
                    MessageStoreContract.class, contractId);
        } catch (final Exception e) {
            throw new RuntimeException("Error while creating contract wrapper", e);
        }
    }

    private <T extends Contract> T createContractWrapper(final Web3j web3j,
            final Credentials credentials,
            final Class<T> contractClass,
            final ContractId contractId) throws Exception {
        Objects.requireNonNull(web3j);
        Objects.requireNonNull(credentials);
        Objects.requireNonNull(contractClass);
        Objects.requireNonNull(contractId);
        final BigInteger gasPrice = BigInteger.valueOf(20_000_000_000_000L);
        final BigInteger gasLimit = BigInteger.valueOf(500_000L);
        final ContractGasProvider staticGasProvider = new StaticGasProvider(gasPrice, gasLimit);

        logger.info("Creating contract wrapper for '{}'", contractClass.getSimpleName());
        logger.info("Account address used for '{}': {}", contractClass.getSimpleName(), credentials.getAddress());
        logger.info("Contract address used for '{}': {}", contractClass.getSimpleName(), contractId);
        logger.info("Chain Id used for '{}': {}", contractClass.getSimpleName(), NODE.getChainId());
        logger.info("Gas limit used for '{}': {}", contractClass.getSimpleName(), gasLimit);

        final T contractWrapper = HederaUtils.createContractWrapper(NODE, web3j, credentials, contractClass, contractId,
                staticGasProvider);
        return contractWrapper;
    }
}
