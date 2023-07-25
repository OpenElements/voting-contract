package com.swirldslabs.util;

import com.hedera.hashgraph.sdk.ContractId;
import com.hedera.hashgraph.sdk.Hbar;
import com.hedera.hashgraph.sdk.PrivateKey;
import java.lang.reflect.Method;
import java.math.BigInteger;
import java.time.Duration;
import java.util.Objects;
import okhttp3.OkHttpClient;
import org.web3j.crypto.Credentials;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.http.HttpService;
import org.web3j.tx.Contract;
import org.web3j.tx.RawTransactionManager;
import org.web3j.tx.TransactionManager;
import org.web3j.tx.gas.ContractGasProvider;

/**
 * Utility class for Hedera to work with web3j.
 */
public class HederaUtils {

    private static final Duration TIMEOUT = Duration.ofSeconds(20);

    public HederaUtils() {
        throw new UnsupportedOperationException("Utility class");
    }

    /**
     * Converts a hedera private key to web3j credentials.
     *
     * @param privateKey the hedera private key
     * @return the web3j credentials
     */
    public static Credentials toCredentials(final PrivateKey privateKey) {
        Objects.requireNonNull(privateKey);
        return Credentials.create(privateKey.toStringRaw());
    }

    /**
     * Web3j needs contract addresses to be prefixed with 0x.
     *
     * @param contractId the hedera contract id
     * @return the contract address with 0x prefix for web3j
     */
    public static String toSolidityAddress(final ContractId contractId) {
        Objects.requireNonNull(contractId);
        return "0x".concat(contractId.toSolidityAddress());
    }

    /**
     * Creates a web3j instance for a given hedera node.
     *
     * @param node the hedera node
     * @return the web3j instance
     */
    public static Web3j createWeb3j(final HederaNode node) {
        Objects.requireNonNull(node);
        final OkHttpClient httpClient = HttpService.getOkHttpClientBuilder()
                .connectTimeout(TIMEOUT)
                .readTimeout(TIMEOUT)
                .writeTimeout(TIMEOUT)
                .build();
        return Web3j.build(new HttpService(node.getRelayUrl(), httpClient));
    }

    /**
     * Creates a contract wrapper for a given contract id.
     *
     * @param node          the hedera node
     * @param web3j         the web3j instance
     * @param credentials   the credentials
     * @param contractClass the contract class
     * @param contractId    the contract id
     * @param gasProvider   the gas provider
     * @param <T>           the contract type
     * @return the contract wrapper
     * @throws Exception if something goes wrong
     */
    public static <T extends Contract> T createContractWrapper(final HederaNode node,
            final Web3j web3j,
            final Credentials credentials,
            final Class<T> contractClass,
            final ContractId contractId, final ContractGasProvider gasProvider) throws Exception {
        Objects.requireNonNull(node);
        Objects.requireNonNull(web3j);
        Objects.requireNonNull(credentials);
        Objects.requireNonNull(contractClass);
        Objects.requireNonNull(contractId);
        Objects.requireNonNull(gasProvider);
        final TransactionManager transactionManager = new RawTransactionManager(web3j, credentials, node.getChainId());
        final Method method = contractClass.getMethod("load", String.class, Web3j.class, TransactionManager.class,
                ContractGasProvider.class);
        return (T) method.invoke(null, toSolidityAddress(contractId),
                web3j,
                transactionManager,
                gasProvider);
    }

    public static BigInteger hBarToUint(final Hbar hBar) {
        return BigInteger.valueOf(hBar.toTinybars());
    }

    public static Hbar hBarFromUint(final BigInteger uint) {
        return Hbar.fromTinybars(uint.longValue());
    }
}
