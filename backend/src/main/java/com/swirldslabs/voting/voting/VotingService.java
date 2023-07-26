package com.swirldslabs.voting.voting;

import static com.swirldslabs.voting.contract.ContractConstants.VOTING_CONTRACT_ID;

import com.hedera.hashgraph.sdk.PrivateKey;
import com.swirldslabs.util.HederaNode;
import com.swirldslabs.util.HederaUtils;
import com.swirldslabs.voting.contract.generated.VotingContract;
import io.github.cdimascio.dotenv.Dotenv;
import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.IntStream;
import org.springframework.stereotype.Service;
import org.web3j.crypto.Credentials;
import org.web3j.protocol.Web3j;
import org.web3j.tuples.generated.Tuple2;
import org.web3j.tx.gas.StaticGasProvider;

@Service
public class VotingService {

    public List<Proposal> getAllProposals() {
        try {
            final String hederaPrivateKey = getPrivateKeyFromEnv();
            final int count = getContract(hederaPrivateKey).proposalsCount().send().intValue();
            return IntStream.range(0, count)
                    .mapToObj(i -> {
                        try {
                            final Tuple2<byte[], BigInteger> tuple = getContract(hederaPrivateKey).proposals(
                                    BigInteger.valueOf(i)).send();
                            final String name = new String(tuple.component1(), StandardCharsets.UTF_8);
                            return new Proposal(i, name);
                        } catch (final Exception e) {
                            throw new RuntimeException("Error in calling contract", e);
                        }
                    })
                    .toList();
        } catch (final Exception e) {
            throw new RuntimeException("Error in calling contract", e);
        }
    }

    public void authorizeUser(final String userAccount) {
        try {
            final String hederaPrivateKey = getPrivateKeyFromEnv();
            getContract(hederaPrivateKey).authorize(userAccount).send();
        } catch (final Exception e) {
            throw new RuntimeException("Error in calling contract", e);
        }
    }

    public void vote(final String privateKey, final Proposal proposal) {
        try {
            getContract(privateKey).vote(BigInteger.valueOf(proposal.id())).send();
        } catch (final Exception e) {
            throw new RuntimeException("Error in calling contract", e);
        }
    }

    public void vote(final Proposal proposal) {
        try {
            getContract(getPrivateKeyFromEnv()).vote(BigInteger.valueOf(proposal.id())).send();
        } catch (final Exception e) {
            throw new RuntimeException("Error in calling contract", e);
        }
    }

    public String getWinner() {
        try {
            final String hederaPrivateKey = getPrivateKeyFromEnv();
            final byte[] send = getContract(hederaPrivateKey).winner().send();
            return new String(send, StandardCharsets.UTF_8);
        } catch (final Exception e) {
            throw new RuntimeException("Error in calling contract", e);
        }
    }

    private VotingContract getContract(final String privateKeyValue) {
        final HederaNode node = HederaNode.TESTNET;
        final PrivateKey privateKey = PrivateKey.fromString(privateKeyValue);

        final Web3j web3j = HederaUtils.createWeb3j(node);
        final Credentials credentials = HederaUtils.toCredentials(privateKey);

        final BigInteger gasPrice = BigInteger.valueOf(20_000_000_000_000L);
        final BigInteger gasLimit = BigInteger.valueOf(500_000L);
        final StaticGasProvider staticGasProvider = new StaticGasProvider(gasPrice, gasLimit);

        try {
            return HederaUtils.createContractWrapper(node,
                    web3j,
                    credentials,
                    VotingContract.class,
                    VOTING_CONTRACT_ID, staticGasProvider);
        } catch (final Exception e) {
            throw new RuntimeException("Error in creating contract wrapper", e);
        }
    }

    private String getPrivateKeyFromEnv() {
        return Dotenv.load().get("HEDERA_PRIVATE_KEY");
    }
}
