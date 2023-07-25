// SPDX-License-Identifier: MIT
pragma solidity 0.8.17;

contract HelloWorldContract {
    function say_hello() public pure returns (string memory) {
        return "Hello, World!";
    }
}