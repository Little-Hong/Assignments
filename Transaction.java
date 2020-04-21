import java.util.List;

public class Transaction {

    /**
     * Constructor for the Transaction class.
     *
     * @param id			the ID of the transaction
     * @param sender		the sending account
     * @param receiver	the receiving account
     * @param amount		the amount of money being transferred
     * @param prevHash	the hash of the previous transaction
     */
    int id;
    BankAccount sender;
    BankAccount receiver;
    int amount;
    String prevHash;

    public Transaction(int id, BankAccount sender, BankAccount receiver, int amount, String prevHash) {
        this.id = id;
        this.sender = sender;
        this.receiver = receiver;
        this.amount = amount;
        this.prevHash = prevHash;
    }

    /**
     * Returns the formatted string of the transaction.
     *
     * @return	the formatted String
     */
    public String get() {
        return id + ": " + sender.accNo + " -> " + receiver.accNo + " | $" + amount + " | " + generateHash(id, sender.accNo, receiver.accNo, amount, prevHash);
    }

    /**
     * Verifies a list of transaction has not been tampered with. Returns true
     * if correct, false if invalid arguments or invalid hash chain.
     *
     * @param transactions	the list of transactions
     * @return				the result of the verification
     */
    public static boolean verify(List<Transaction> transactions) {
        if (transactions  == null) {
            return false;
        }
        if (transactions.size() == 0) {
            return true;
        }
        if (transactions.size() == 1) {
            return transactions.get(0).prevHash == null;
        }
        for (int i=0; i < transactions.size()-1; i++) {
            Transaction t = transactions.get(i);
            String g = generateHash(t.id, t.sender.accNo, t.receiver.accNo, t.amount, t.prevHash);
            if (!g.equals(transactions.get(i + 1).prevHash)) {
                return false;
            }
        }
        return true;
    }

    /**
     * Returns the hash code for a transaction.
     *
     * @param id				the ID of the transaction
     * @param senderAccNo	the account number of the sender
     * @param receiverAccNo	the account number of the receiver
     * @param amount			the amount of money being transferred
     * @param prevHash		the hash of the previous transaction
     * @return				the generated hash
     */
    public static String generateHash(int id, int senderAccNo, int receiverAccNo, int amount, String prevHash) {

        String combined = id + "" + senderAccNo + "" + receiverAccNo + "" + amount + "" + prevHash;
        return combined.hashCode() + "";

    }

}
