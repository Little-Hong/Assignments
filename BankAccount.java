import java.util.*;

public class BankAccount {

    public static final int DEFAULT = 10000;

    /**
     * Constructor for the BankAccount object
     *
     * @param accNo		the account number
     * @param first		the first name of the person
     * @param last		the surname of the person
     * @param balance	the starting balance of the account
     */
    int accNo;
    String first;
    String last;
    int balance;
    int initial_balance;
    List<Transaction> total_transactions;
    List<Transaction> outgoing_transactions;
    List<Transaction> incoming_transactions;

    public BankAccount(int accNo, String first, String last, int balance) {
        this.accNo = accNo;
        this.first = first;
        this.last = last;
        this.balance = balance;
        this.initial_balance = balance;
        total_transactions = new ArrayList<>();
        outgoing_transactions = new ArrayList<>();
        incoming_transactions = new ArrayList<>();
    }

    /**
     * Returns the formatted string of the account.
     *
     * @return	the formatted String
     */
    public String details() {
        return accNo + " - " + first + " " + last + " - $" + balance;
    }

    /**
     * Returns list of all transactions involving account. Returns null if no history
     *
     * @return	the transaction history
     */
    public List<Transaction> history() {
        if (total_transactions.size() == 0) {
            return null;
        } else {
            return total_transactions;
        }
    }

    /**
     * Processes transaction by deducting/adding amount. Returns true if successful,
     * false if insufficient funds.
     *
     * @param transaction	the transaction to be processed
     * @return				the result of the transaction
     */
    public boolean processTransaction(Transaction transaction) {

        if (transaction.sender.balance >= transaction.amount) {
            transaction.sender.balance -= transaction.amount;
            transaction.sender.total_transactions.add(transaction);
            transaction.sender.outgoing_transactions.add(transaction);
            transaction.receiver.balance += transaction.amount;
            transaction.receiver.total_transactions.add(transaction);
            transaction.receiver.incoming_transactions.add(transaction);
            return true;
        } else {
            return false;
        }

    }

    /**
     * Returns list of transactions where account is sender. Returns null if none.
     *
     * @return 	the list of transactions
     */
    public List<Transaction> outgoing() {
        if (outgoing_transactions.size() == 0) {
            return null;
        } else {
            return outgoing_transactions;
        }
    }

    /**
     * Returns list of transactions where account is receiver. Returns null if none.
     *
     * @return	the list of transactions
     */
    public List<Transaction> incoming() {
        if (incoming_transactions.size() == 0) {
            return null;
        } else {
            return incoming_transactions;
        }
    }

    /**
     * Renames the account with new first and last name.
     *
     * @param first		the new first name
     * @param last		the new surname
     */
    public void rename(String first, String last) {
        this.first = first;
        this.last = last;
    }

    /**
     * Returns the largest balance in list of accounts. Returns -1 if empty or null.
     *
     * @param accounts	the list of accounts
     * @return			the largest balance
     */
    public static int findMax(List<BankAccount> accounts) {
        if (accounts == null || accounts.size() == 0) {
            return -1;
        } else {
            int n = accounts.get(0).balance;
            for (BankAccount attribute: accounts) {
                if (attribute.balance > n) {
                    n = attribute.balance;
                }
            }
            return n;
        }
    }

    /**
     * Returns the smallest balance in list of accounts. Returns -1 if empty or null.
     *
     * @param accounts	the list of accounts
     * @return			the smallest balance
     */
    public static int findMin(List<BankAccount> accounts) {
        if (accounts == null || accounts.size() == 0) {
            return -1;
        } else {
            int n = accounts.get(0).balance;
            for (BankAccount attribute: accounts) {
                if (attribute.balance < n) {
                    n = attribute.balance;
                }
            }
            return n;
        }
    }

    /**
     * Returns the average balance in list of accounts (rounded down). Returns -1 if empty or null.
     *
     * @param accounts	the list of accounts
     * @return			the average balance
     */
    public static int mean(List<BankAccount> accounts) {
        if (accounts == null || accounts.size() == 0) {
            return -1;
        } else {
            int n = 0;
            for (BankAccount attribute: accounts) {
                n += attribute.balance;
            }
            int d = n/accounts.size();
            return Math.round(d);
        }
    }

    /**
     * Returns the median balance in list of accounts. Returns -1 if empty or null.
     *
     * @param accounts	the list of accounts
     * @return			the median balance
     */
    public static int median(List<BankAccount> accounts) {
        if (accounts == null || accounts.size() == 0) {
            return -1;
        } else {
            List<Integer> balance_list = new ArrayList<>();
            for (BankAccount n: accounts) {
                balance_list.add(n.balance);
            }
            Collections.sort(balance_list);
            if (balance_list.size() % 2 == 0) {
                int a = balance_list.size()/2;
                return (balance_list.get(a) + balance_list.get(a-1))/2;
            } else {
                return balance_list.get((balance_list.size()-1)/2);
            }
        }
    }

    /**
     * Returns the total balance in list of accounts. Returns -1 if empty or null.
     *
     * @param accounts	the list of accounts
     * @return			the total balance
     */
    public static int totalBalance(List<BankAccount> accounts) {
        if (accounts == null || accounts.size() == 0) {
            return -1;
        } else {
            int n = 0;
            for (BankAccount attribute: accounts) {
                n += attribute.balance;
            }
            return n;
        }
    }
}
