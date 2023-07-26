package com.swirldslabs.voting.voting;

import java.util.List;
import java.util.Objects;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class VotingController {

    private final VotingService votingService;

    @Autowired
    public VotingController(VotingService votingService) {
        this.votingService = Objects.requireNonNull(votingService);
    }

    @RequestMapping(value = "/authorize-user", method = RequestMethod.GET)
    public String authorizeUserView(final Model model) {
        Objects.requireNonNull(model);
        return "authorize-user";
    }

    @RequestMapping(value = "/authorize-user", method = RequestMethod.POST)
    public String onAuthorizeUserPost(final Model model, String evmAddress) {
        Objects.requireNonNull(model);

        if(evmAddress == null || evmAddress.trim().isBlank()) {
            showMessage("Please specify user account", model);
        } else {
            votingService.authorizeUser(evmAddress);
            showMessage("Account '" + evmAddress + "' authorized", model);
        }
        return "authorize-user";
    }

    @RequestMapping(value = "/winner", method = RequestMethod.GET)
    public String winnerView(final Model model) {
        Objects.requireNonNull(model);
        final String name = votingService.getWinner();
        model.addAttribute("winner", name);
        return "winner";
    }

    @RequestMapping(value = "/vote", method = RequestMethod.GET)
    public String voteView(final Model model) {
        Objects.requireNonNull(model);

        model.addAttribute("proposals", votingService.getAllProposals());
        return "vote";
    }

    @RequestMapping(value = "/vote", method = RequestMethod.POST)
    public String onVote(final Model model, final String privateKey, final int selected) {
        Objects.requireNonNull(model);

        final List<Proposal> allProposals = votingService.getAllProposals();
        if(privateKey == null || privateKey.trim().isBlank()) {
            showMessage("Please specify user account", model);
            model.addAttribute("proposals", allProposals);
        } else  if(selected < 0 || selected >= allProposals.size()) {
            showMessage("Please select a valid proposal", model);
            model.addAttribute("proposals", allProposals);
            model.addAttribute("privateKey", privateKey);
        } else {
            Proposal proposal = allProposals.get(selected);
            votingService.vote(privateKey,  proposal);
            showMessage("Selected: " + proposal.name(), model);
        }
        return "vote";
    }

    @RequestMapping(value = "/simple-vote", method = RequestMethod.GET)
    public String simpleVoteView(final Model model) {
        Objects.requireNonNull(model);

        model.addAttribute("proposals", votingService.getAllProposals());
        return "simple-vote";
    }

    @RequestMapping(value = "/simple-vote", method = RequestMethod.POST)
    public String onSimpleVote(final Model model, final int selected) {
        Objects.requireNonNull(model);

        final List<Proposal> allProposals = votingService.getAllProposals();
        if(selected < 0 || selected >= allProposals.size()) {
            showMessage("Please select a valid proposal", model);
            model.addAttribute("proposals", allProposals);
        } else {
            Proposal proposal = allProposals.get(selected);
            votingService.vote(proposal);
            showMessage("Selected: " + proposal.name(), model);
        }
        return "simple-vote";
    }

    private void showMessage(final String message, final Model model) {
        Objects.requireNonNull(model);

        model.addAttribute("message", message);
        System.out.println(message);
    }
}
