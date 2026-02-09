package _CourierChaincode;

import com.owlike.genson.annotation.JsonProperty;  

import org.hyperledger.fabric.contract.annotation.DataType;  

import org.hyperledger.fabric.contract.annotation.Property;  

import java.util.Objects;  

  

@DataType() 

public final class Parcel { 

	@Property()  

	 private final String id;  

	   

	 @Property()  

	 private final String sender;  

	   

	 @Property()  

	 private final String receiver;  

	   

	 @Property()  

	 private final String status;  

	   

	 public String getId() {  

	  return id;  

	 }  

	   

	 public String getSender() {  

	  return sender;  

	 }  

	 public String getReceiver() {  

	  return receiver;  

	 }  

	   

	 public String getStatus() {  

	  return status;  

	 }  

	   
	 public Parcel(@JsonProperty("id") final String id, @JsonProperty("sender") final String sender, @JsonProperty("receiver") final String receiver, @JsonProperty("status") final String status)
	 {  

	  this.id = id;  

	  this.sender = sender;  

	  this.receiver = receiver;  

	  this.status = status;  

	 }  

	 @Override  

	 public boolean equals(final Object obj) {  

	  if (this == obj) {  

	   return true;  

	  }  

	   

	  if ((obj == null) || (getClass() != obj.getClass())) {  

	   return false;  

	  }  

	   

	  Parcel other = (Parcel) obj;  

	   

	  return Objects.deepEquals(new String[] { getId(), getSender(), getReceiver(), getStatus() },  

	    new String[] { other.getId(), other.getSender(), other.getReceiver(), other.getStatus() });  

	 }  

	  
	 @Override  

	 public int hashCode() {  

	  return Objects.hash(getId(), getSender(), getReceiver(), getStatus());  

	 }  
   

	 @Override  

	 public String toString() {  

	  return this.getClass().getSimpleName() + "@" +  

	Integer.toHexString(hashCode()) + " [id=" + id + ", sender=" + sender + ", receiver=" + receiver + ", status=" + status + "]";  

	 }  

	   

	}