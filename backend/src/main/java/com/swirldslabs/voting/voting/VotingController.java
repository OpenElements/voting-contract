package com.swirldslabs.voting.voting;

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
    public String onAuthorizeUserPost(final Model model, String admin, String account) {
        Objects.requireNonNull(model);

        if(admin == null || admin.trim().isBlank()) {
            showMessage("Please specify admin account", model);
        } else if(account == null || account.trim().isBlank()) {
            showMessage("Please specify user account", model);
            model.addAttribute("admin", admin);
        } else {
            votingService.authorizeUser(admin, account);
            showMessage("Account '" + account + "' authorized", model);
        }
        return "authorize-user";
    }


    @RequestMapping(value = "/winner", method = RequestMethod.GET)
    public String winnerView(final Model model) {
        Objects.requireNonNull(model);
        final String name = votingService.getWinner().getName();
        model.addAttribute("winner", name);
        return "winner";
    }


    @RequestMapping(value = "/vote", method = RequestMethod.GET)
    public String voteView(final Model model) {
        Objects.requireNonNull(model);

        model.addAttribute("proposals", Proposal.values());
        return "vote";
    }

    @RequestMapping(value = "/vote", method = RequestMethod.POST)
    public String onVote(final Model model, final String account, final int selected) {
        Objects.requireNonNull(model);

        if(account == null || account.trim().isBlank()) {
            showMessage("Please specify user account", model);
            model.addAttribute("proposals", Proposal.values());
        } else  if(selected < 0 || selected >= Proposal.values().length) {
            showMessage("Please select a valid proposal", model);
            model.addAttribute("proposals", Proposal.values());
            model.addAttribute("account", account);
        } else {
            votingService.vote(account, Proposal.values()[selected]);
            showMessage("Selected: " + Proposal.values()[selected].getName(), model);
        }
        return "vote";
    }

    private void showMessage(final String message, final Model model) {
        Objects.requireNonNull(model);

        model.addAttribute("message", message);
        System.out.println(message);
    }
}
