// SPDX-License-Identifier: MIT
pragma solidity ^0.8.0;

contract HelloWorldContract {
    function say_hello() public pure returns (string memory) {
        return "Hello, World!";
    }
}