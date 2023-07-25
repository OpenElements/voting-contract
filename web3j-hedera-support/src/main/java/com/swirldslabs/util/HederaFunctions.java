package com.swirldslabs.util;

import com.hedera.hashgraph.sdk.AccountBalanceQuery;
import com.hedera.hashgraph.sdk.AccountId;
import com.hedera.hashgraph.sdk.Client;
import com.hedera.hashgraph.sdk.ContractId;
import com.hedera.hashgraph.sdk.Hbar;
import com.hedera.hashgraph.sdk.PrecheckStatusException;
import com.hedera.hashgraph.sdk.ReceiptStatusException;
import com.hedera.hashgraph.sdk.Status;
import com.hedera.hashgraph.sdk.TransferTransaction;
import java.util.concurrent.TimeoutException;

public class HederaFunctions {

    private HederaFunctions() {
        // prevent instantiation
    }

    public static Hbar getBalanceOfAccount(final Client client, final AccountId accountId)
            throws PrecheckStatusException, TimeoutException {
        final AccountBalanceQuery query = new AccountBalanceQuery().setAccountId(accountId);
        return query.execute(client).hbars;
    }

    public static Hbar getBalanceOfContract(final Client client, final ContractId contractId)
            throws PrecheckStatusException, TimeoutException {
        final AccountBalanceQuery query = new AccountBalanceQuery().setContractId(contractId);
        return query.execute(client).hbars;
    }

    public static Status transferHbar(final Client client, final AccountId from, final AccountId to, final Hbar amount)
            throws PrecheckStatusException, TimeoutException, ReceiptStatusException {
        final TransferTransaction transaction = new TransferTransaction()
                .addHbarTransfer(from, amount.negated())
                .addHbarTransfer(to, amount);

        return transaction.execute(client).getReceipt(client).status;
    }
}
