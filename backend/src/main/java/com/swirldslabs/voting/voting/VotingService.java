package com.swirldslabs.voting.voting;

import static com.swirldslabs.voting.contract.ContractConstants.VOTING_CONTRACT_ID;

import com.hedera.hashgraph.sdk.PrivateKey;
import com.swirldslabs.util.HederaNode;
import com.swirldslabs.util.HederaUtils;
import com.swirldslabs.voting.contract.generated.VotingContract;
import io.github.cdimascio.dotenv.Dotenv;
import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.Objects;
import org.springframework.stereotype.Service;
import org.web3j.crypto.Credentials;
import org.web3j.protocol.Web3j;
import org.web3j.tx.gas.StaticGasProvider;

@Service
public class VotingService {

    public void authorizeUser(String adminAccount, String userAccount) {
        try {
            getContract(adminAccount).authorize(userAccount).send();
        } catch (Exception e) {
            throw new RuntimeException("Error in calling contract", e);
        }
    }

    public void vote(String account, Proposal proposal) {
        try {
            getContract(account).vote(BigInteger.valueOf(proposal.getId())).send();
        } catch (Exception e) {
            throw new RuntimeException("Error in calling contract", e);
        }
    }

    public Proposal getWinner() {
        try {
            final String hederaPrivateKey = Dotenv.load().get("HEDERA_PRIVATE_KEY");
            final byte[] send = getContract(hederaPrivateKey).winningProposal().send();
            String name = new String(send, StandardCharsets.UTF_8);
            return Arrays.stream(Proposal.values()).filter(v -> Objects.equals(v.getName(), name)).findAny().orElseThrow(() -> new IllegalStateException("Propsal '" + name + "' not found"));
        } catch (Exception e) {
            throw new RuntimeException("Error in calling contract", e);
        }
    }

    private VotingContract getContract(String account) {
        final HederaNode node = HederaNode.TESTNET;
        final PrivateKey privateKey = PrivateKey.fromString(account);

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
        } catch (Exception e) {
            throw new RuntimeException("Error in creating contract wrapper", e);
        }
    }
}
