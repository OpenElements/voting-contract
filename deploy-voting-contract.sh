./mvnw clean install
./mvnw exec:java -pl smart-contract -Dexec.mainClass="com.swirldslabs.voting.contract.VotingContractDeployment"