package assessment.data.domain;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.Date;

public class Transaction<T extends Serializable, E extends Serializable> {

    @SerializedName("transaction_id")
    private T transactionId;
    @SerializedName("user_id")
    private E userId;
    private double amount;
    private String description;
    private Date date;

    public T getTransactionId() {
        return transactionId;
    }

    public void setTransactionId(T transactionId) {
        this.transactionId = transactionId;
    }

    public E getUserId() {
        return userId;
    }

    public void setUserId(E userId) {
        this.userId = userId;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }
}
