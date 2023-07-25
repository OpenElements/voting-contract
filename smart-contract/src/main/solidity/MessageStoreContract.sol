// SPDX-License-Identifier: Apache-2.0
pragma solidity ^0.8.0;

contract MessageStoreContract {
    // the message we're storing
    string message;

    constructor(string memory message_) {
        message = message_;
    }

    function set_message(string memory message_) public {
        message = message_;
    }

    // return a string
    function get_message() public view returns (string memory) {
        return message;
    }
}