package com.swirldslabs.util;

public enum HederaNode {

    PREVIEWNET("previewnet", 297, "https://previewnet.hashio.io/api"),
    TESTNET("testnet", 296, "https://testnet.hashio.io/api"),
    MAINNET("mainnet", 295, "https://mainnet.hashio.io/api");

    /**
     * See https://docs.web3j.io/4.8.7/smart_contracts/interacting_with_smart_contract/#specifying-the-chain-id-on-transactions-eip-155
     */
    private final long chainId;

    /**
     * URL for the Hash.io relay service. See https://swirldslabs.com/hashio/ for more information.
     */
    private final String relayUrl;

    /**
     * Name of the Hedera node. Compatible with {@link com.hedera.hashgraph.sdk.Client#forName(String)} and similar
     * methods.
     */
    private final String name;

    HederaNode(final String name, final long chainId, final String relayUrl) {
        this.name = name;
        this.chainId = chainId;
        this.relayUrl = relayUrl;
    }

    public long getChainId() {
        return chainId;
    }

    public String getRelayUrl() {
        return relayUrl;
    }

    public String getName() {
        return name;
    }
}
