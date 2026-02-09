package _CourierChaincode;

import org.hyperledger.fabric.contract.Context;
import org.hyperledger.fabric.contract.ContractInterface;
import org.hyperledger.fabric.contract.annotation.Contract;
import org.hyperledger.fabric.contract.annotation.Default;
import org.hyperledger.fabric.contract.annotation.Info;
import org.hyperledger.fabric.contract.annotation.Transaction;
import org.hyperledger.fabric.shim.ChaincodeException;
import org.hyperledger.fabric.shim.ChaincodeStub;
import com.owlike.genson.Genson;

@Contract(
    name = "CourierChaincode",
    info = @Info(
        title = "CourierChaincode contract",
        description = "A Sample Parcel Contract chaincode example",
        version = "0.0.1-SNAPSHOT"
    )
)
@Default
public final class ParcelContract implements ContractInterface {

    private final Genson genson = new Genson();

    private enum CourierChaincodeErrors {
        Parcel_NOT_FOUND,
        Parcel_ALREADY_EXISTS
    }
 
    /** Initialize ledger with one sample parcel
    * Add some initial properties to the ledger  
    *  
    * @param ctx the transaction context  
    */
    
    @Transaction()
    public void initLedger(final Context ctx) {

        ChaincodeStub stub = ctx.getStub();

        Parcel parcel = new Parcel("1", "Ravi", "Kumar", "Shipped");

        String parcelState = genson.serialize(parcel);

        stub.putStringState("1", parcelState);
    }

     /** Add new parcel 
     * Add new Parcel on the ledger.
     * @param ctx the transaction context  
     * @param id the key for the new Parcel  
     * @param sender the sender of the new Parcel 
     * @param receiver the receiver of the new Parcel 
     * @param status the status of the new Parcel  
     * @return the created Parcel
     */
    
    @Transaction()
    public Parcel addNewParcel(
            final Context ctx,
            final String id,
            final String sender,
            final String receiver,
            final String status) {

        ChaincodeStub stub = ctx.getStub();

        String parcelState = stub.getStringState(id);

        if (!parcelState.isEmpty()) {
            String errorMessage = String.format("Parcel %s already exists", id);
            throw new ChaincodeException(
                    errorMessage,
                    CourierChaincodeErrors.Parcel_ALREADY_EXISTS.toString()
            );
        }

        Parcel parcel = new Parcel(id, sender, receiver, status);

        parcelState = genson.serialize(parcel);

        stub.putStringState(id, parcelState);

        return parcel;
    }

    /** Query parcel by ID 
     * Retrieves a Parcel based upon Parcel Id from the ledger.
     * @param ctx the transaction context  
     * @param id the key  
     * @return the Parcel found on the ledger if there was one
     */
    
    @Transaction()
    public Parcel queryParcelById(final Context ctx, final String id) {

        ChaincodeStub stub = ctx.getStub();

        String parcelState = stub.getStringState(id);

        if (parcelState.isEmpty()) {
            String errorMessage = String.format("Parcel %s does not exist", id);
            throw new ChaincodeException(
                    errorMessage,
                    CourierChaincodeErrors.Parcel_NOT_FOUND.toString()
            );
        }

        return genson.deserialize(parcelState, Parcel.class);
    }

    /** Update parcel status 
     * Changes the receiver of a Parcel on the ledger.  
     * @param ctx the transaction context  
     * @param id the key  
     * @param newreceiver the new owner  
     * @return the updated Parcel
     */
    
    @Transaction()
    public Parcel updateParcelStatus(
            final Context ctx,
            final String id,
            final String newStatus) {

        ChaincodeStub stub = ctx.getStub();

        String parcelState = stub.getStringState(id);

        if (parcelState.isEmpty()) {
            String errorMessage = String.format("Parcel %s does not exist", id);
            throw new ChaincodeException(
                    errorMessage,
                    CourierChaincodeErrors.Parcel_NOT_FOUND.toString()
            );
        }

        Parcel parcel = genson.deserialize(parcelState, Parcel.class);

        Parcel updatedParcel = new Parcel(
                parcel.getId(),
                parcel.getSender(),
                parcel.getReceiver(),
                newStatus
        );

        String updatedState = genson.serialize(updatedParcel);

        stub.putStringState(id, updatedState);

        return updatedParcel;
    }
}
