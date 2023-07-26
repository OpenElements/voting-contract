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

    }

    /**
     * @dev Get number of proposals
     *
     * @return number of proposals
     */
    function proposalsCount() public view returns (uint) {
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
    }

    /**
     * @dev Calculate the winning proposal
     *
     * @return result winning proposal
     */
    function winner() public view returns (bytes32 result) {
        result = "";
    }
}