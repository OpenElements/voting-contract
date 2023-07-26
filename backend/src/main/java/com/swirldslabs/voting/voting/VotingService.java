package com.swirldslabs.voting.voting;

import com.swirldslabs.voting.contract.generated.VotingContract;
import org.springframework.stereotype.Service;

@Service
public class VotingService {
    
    public void authorizeUser(String adminAccount, String userAccount) {

    }

    public void vote(String account, Proposal proposal) {

    }

    public Proposal getWinner() {
        return Proposal.JAVA;
    }
}
