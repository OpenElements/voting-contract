package com.swirldslabs.voting.hello;

import com.swirldslabs.voting.contract.generated.MessageStoreContract;
import java.util.Objects;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.web3j.protocol.core.methods.response.TransactionReceipt;

@Service
public class MessageService {

    private final static Logger logger = LoggerFactory.getLogger(MessageService.class);

    private final MessageStoreContract contract;

    public MessageService(final MessageStoreContract contract) {
        this.contract = Objects.requireNonNull(contract, "contract must not be null");
    }

    public String getMessage() {
        try {
            logger.info("Calling smart contract 'HelloWorldContract.say_hello'");
            final String message = contract.get_message().send();
            logger.info("Called smart contract 'HelloWorldContract.say_hello'");
            return message;
        } catch (final Exception e) {
            throw new RuntimeException("Error while calling smart contract", e);
        }
    }

    public void setMessage(final String message) {
        try {
            logger.info("Calling smart contract 'HelloWorldContract.set_message'");
            final TransactionReceipt receipt = contract.set_message(message).send();
            logger.info("Called smart contract 'HelloWorldContract.set_message'");
            if (!receipt.isStatusOK()) {
                throw new IllegalStateException("Status of receipt: " + receipt.getStatus());
            }
        } catch (final Exception e) {
            throw new RuntimeException("Error while calling smart contract", e);
        }
    }
}
