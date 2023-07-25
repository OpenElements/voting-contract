// SPDX-License-Identifier: GPL-3.0

pragma solidity 0.8.17;

/**
 * @title Poll
 * @dev Implements simple poll
 */
contract VotingContract {

    address public admin;

    enum VoterState { NotAuthorized, Authorized, Voted }

    mapping(address => VoterState) public voters;

    struct Proposal {
        bytes32 name;
        uint count;
    }

    Proposal[] public proposals;

    /**
     * @dev Create a new poll
     *
     * @param proposalNames names of proposals
     */
    constructor(bytes32[] memory proposalNames) {
        admin = msg.sender;

        for (uint i = 0; i < proposalNames.length; i++) {
            Proposal memory proposal = Proposal({
                name: proposalNames[i],
                count: 0
            });
            proposals.push(proposal);
        }
    }

    /**
     * @dev Authorize 'voter' for this poll
     *
     * @param voter address of voter
     */
    function authorize(address voter) public {
        require(msg.sender == admin, "Only admin can authorize");
        require(voters[voter] == VoterState.NotAuthorized, "Already authorized");

        voters[voter] = VoterState.Authorized;
    }

    /**
     * @dev Vote on a proposal
     *
     * @param proposal index of proposal
     */
    function vote(uint proposal) public {
        require(voters[msg.sender] == VoterState.Authorized, "Not authorized");

        voters[msg.sender] = VoterState.Voted;
        proposals[proposal].count++;
    }

    /**
     * @dev Calculate the winning proposal
     *
     * @return winner winning proposal
     */
    function winningProposal() public view returns (bytes32 winner) {
        winner = "";
        uint maxCount = 0;
        for (uint i = 0; i < proposals.length; i++) {
            if (proposals[i].count > maxCount) {
                maxCount = proposals[i].count;
                winner = proposals[i].name;
            }
        }
    }
}