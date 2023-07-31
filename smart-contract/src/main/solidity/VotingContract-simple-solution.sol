// SPDX-License-Identifier: GPL-3.0

pragma solidity 0.8.17;

/**
 * @title Voting
 * @dev Implements simple poll
 */
contract VotingContract {

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
        for (uint i = 0; i < proposalNames.length; i++) {
            Proposal memory proposal = Proposal({
                name: proposalNames[i],
                count: 0
            });
            proposals.push(proposal);
        }

    }

    /**
     * @dev Get number of proposals
     *
     * @return number of proposals
     */
    function proposalsCount() public view returns (uint) {
        return proposals.length;
        return 0;
    }

    /**
     * @dev Authorize 'voter' for this poll
     *
     * @param voter address of voter
     */
    function authorize(address voter) public {

    }

    /**
     * @dev Vote on a proposal
     *
     * @param proposal index of proposal
     */
    function vote(uint proposal) public {
        proposals[proposal].count++;
    }

    /**
     * @dev Calculate the winning proposal
     *
     * @return result winning proposal
     */
    function winner() public view returns (bytes32 result) {
        result = "";
        uint maxCount = 0;
        for (uint i = 0; i < proposals.length; i++) {
            if (proposals[i].count > maxCount) {
                maxCount = proposals[i].count;
                result = proposals[i].name;
            }
        }
    }
}
