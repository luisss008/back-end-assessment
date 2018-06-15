package assessment.data;

import assessment.data.domain.Transaction;
import assessment.data.exception.RecordNotFoundException;

import java.io.Serializable;
import java.util.List;

public interface TransactionDao<T extends Serializable, E extends Serializable> {

    Transaction<T,E> add(Transaction<T,E> transaction);

    Transaction<T,E> getByUserIdAndTransactionId(E userId, T transactionId) throws RecordNotFoundException;

    List<Transaction<T,E>> list(E userId);
}
