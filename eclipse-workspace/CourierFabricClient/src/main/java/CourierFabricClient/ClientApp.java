package CourierFabricClient;

import java.nio.file.Path;
import java.nio.file.Paths; 
import org.hyperledger.fabric.gateway.Contract;
import org.hyperledger.fabric.gateway.Gateway;
import org.hyperledger.fabric.gateway.Network;
import org.hyperledger.fabric.gateway.Wallet;
import org.hyperledger.fabric.gateway.Wallets;

public class ClientApp {
	static {
		System.setProperty("org.hyperledger.fabric.sdk.service_discovery.as_localhost", 	  "true");
	}
	public static void main(String[] args) throws Exception {
		// Load a file system based wallet for managing identities.
		Path walletPath = Paths.get("wallet");
		Wallet wallet = Wallets.newFileSystemWallet(walletPath);
		// load a CCP
		Path networkConfigPath = Paths.get("..", "..","fabric-samples", "test-network", "organizations", "peerOrganizations", "org1.example.com", "connection-org1.yaml");
 
		Gateway.Builder builder = Gateway.createBuilder();
		builder.identity(wallet, "appUser").networkConfig(networkConfigPath).discovery(true);
 
		// create a gateway connection
		try (Gateway gateway = builder.connect()) {
 
			// get the network and contract
			Network network = gateway.getNetwork("mychannel");
			Contract contract = network.getContract("CourierChaincode");
 
			byte[] result;
			contract.submitTransaction("addNewParcel", "8", "Riya", "Sharma", "Intransit");
			result = contract.evaluateTransaction("queryParcelById", "8");
			System.out.println(new String(result));
 
			contract.submitTransaction("updateParcelStatus", "8", "Shipped");
			result = contract.evaluateTransaction("queryParcelById", "8");
			System.out.println(new String(result));
		}
	}

}

