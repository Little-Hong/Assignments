import java.io.*;
import java.util.*;

public class Banker {

    private static Map<Integer, BankAccount> accounts;
    private static List<Transaction> transactions;

    public final static String helpString =
            "EXIT exit from application\n" +
                    "COMMANDS display the command list\n\n" +
                    "LIST ACCOUNTS displays all accounts in system\n" +
                    "LIST TRANSACTIONS displays all transactions in system\n\n" +
                    "DETAILS <accno> displays all details about bank account\n" +
                    "BALANCE <accno> displays the current balance of bank account\n\n" +
                    "HISTORY <accno> displays all transactions involving an account\n" +
                    "OUTGOING <accno> displays all transactions paid by account\n" +
                    "INCOMING <accno> displays all transactions received by account\n\n" +
                    "CREATE <first> <last> [<balance>] creates a bank account\n" +
                    "RENAME <accno> <first> <last> renames a bank account\n\n" +
                    "PAY <sender> <receiver> <amount> transfers money between account\n" +
                    "TRANSACTION <id> displays the transaction details\n" +
                    "CANCEL <id> makes a copy of the transaction with receiver/sender swapped\n\n" +
                    "ARCHIVE <ledgerFile> <accountFile> stores the transaction history as a ledger\n" +
                    "RECOVER <ledgerFile> <accountFile> restores a ledger\n\n" +
                    "MERGE <accno …> transfers all funds from listed accounts into the first account\n\n" +
                    "MAX displays the highest balance from all accounts\n" +
                    "MIN displays the lowest balance from all accounts\n" +
                    "MEAN displays the average balance\n" +
                    "MEDIAN displays the median balance\n" +
                    "TOTAL displays the amount of money stored by bank";

    /**
     * Constructor for the Banker administrative system.
     */
    public Banker() {
        accounts = new HashMap<>();
        transactions = new ArrayList<>();
    }

    /**
     * Displays the list of commands.
     */
    public static void commands() {
        System.out.println(Banker.helpString);
    }

    /**
     * Displays the closing statement.
     */
    public static void exit() {
        System.out.println("bye");
    }

    /**s
     * Prints out all account numbers within system in numerical order.
     */
    public void listAccounts() {
        if (accounts == null || accounts.size() == 0) {
            System.out.println("no accounts");
        } else {
            List<Integer> out = new ArrayList<>(accounts.keySet());
            Collections.sort(out);
            for (Integer i: out) {
                System.out.println(i);
            }
        }
    }

    /**
     * Prints out all transaction details within system in chronological order.
     */
    public void listTransactions() {
        if (transactions == null || transactions.size() == 0) {
            System.out.println("no transactions");
        } else {
            for (Transaction attribute : transactions) {
                System.out.println(attribute.get());
            }
        }
    }

    /**
     * Displays the account number, name and balance of an account.
     *
     * @param accNo		the account number
     */
    public void details(int accNo) {
        boolean t = false;
        if (accounts == null || accounts.size() == 0) {
            System.out.println("no such account");
            return;
        } else {
            for (Map.Entry<Integer, BankAccount> entry : accounts.entrySet()) {
                if (entry.getKey().equals(accNo)) {
                    System.out.println(entry.getValue().details());
                    t = true;
                    break;
                }
            }
        }
        if (!t) {
            System.out.println("no such account");
        }
    }

    /**
     * Displays the current balance for a specified account.
     *
     * @param accNo		the account number
     */
    public void balance(int accNo) {
        boolean t = false;
        if (accounts == null || accounts.size() == 0) {
            System.out.println("no such account");
            return;
        } else {
            for (Map.Entry<Integer, BankAccount> entry : accounts.entrySet()) {
                if (entry.getKey().equals(accNo)) {
                    System.out.println("$" + entry.getValue().balance);
                    t = true;
                    break;
                }
            }
        }
        if (!t) {
            System.out.println("no such account");
        }
    }

    /**
     * Displays the entire transaction history for a specified account.
     *
     * @param accNo		the account number
     */
    public void history(int accNo) {
        if (accounts == null || accounts.size() == 0) {
            System.out.println("no such account");
            return;
        }
        try {
            List<Transaction> history = accounts.get(accNo).history();
            try {
                for (Transaction t: history) {
                    System.out.println(t.get());
                }
            } catch (java.lang.NullPointerException e) {
                System.out.println("no history");
            }
        } catch (java.lang.NullPointerException e) {
            System.out.println("no such account");
        }
    }

    /**
     * Displays the outgoing transaction history for a specified account.
     *
     * @param accNo		the account number
     */
    public void outgoing(int accNo) {
        if (accounts == null || accounts.size() == 0) {
            System.out.println("no such account");
            return;
        }
        try {
            List<Transaction> outgoing = accounts.get(accNo).outgoing();
            try {
                for (Transaction t: outgoing) {
                    System.out.println(t.get());
                }
            } catch (java.lang.NullPointerException e) {
                System.out.println("no outgoing");
            }
        } catch (java.lang.NullPointerException e) {
            System.out.println("no such account");
        }
    }

    /**
     * Displays the incoming transaction history for a specified account.
     *
     * @param accNo		the account number
     */
    public void incoming(int accNo) {
        if (accounts == null || accounts.size() == 0) {
            System.out.println("no such account");
            return;
        }
        try {
            List<Transaction> incoming = accounts.get(accNo).incoming();
            try {
                for (Transaction t: incoming) {
                    System.out.println(t.get());
                }
            } catch (java.lang.NullPointerException e) {
                System.out.println("no incoming");
            }
        } catch (java.lang.NullPointerException e) {
            System.out.println("no such account");
        }
    }

    /**
     * Creates an account within the system.
     *
     * @param first		the first name of the person
     * @param last		the surname of the person
     * @param balance	the starting balance of the person
     */
    public void createAccount(String first, String last, int balance) {
        int n = accounts.size() + 100000;
        BankAccount acc = new BankAccount(n, first, last, balance);
        accounts.put(n, acc);
        System.out.println("success");
    }

    /**
     * Renames a specified account.
     *
     * @param accNo		the account number
     * @param first		the new first name
     * @param last		the new surname
     */
    public void rename(int accNo, String first, String last) {
        boolean t = false;
        if (accounts == null || accounts.size() == 0) {
            System.out.println("no such account");
            return;
        } else {
            for (Map.Entry<Integer, BankAccount> entry : accounts.entrySet()) {
                if (entry.getKey().equals(accNo)) {
                    entry.getValue().rename(first, last);
                    System.out.println("success");
                    t = true;
                    break;
                }
            }
        }
        if (!t) {
            System.out.println("no such account");
        }
    }

    /**
     * Creates a transaction between two bank accounts.
     *
     * @param sender		the account number of the sender
     * @param receiver	the account number of the receiver
     * @param amount		the amount to be transferred
     */
    public void pay(int sender, int receiver, int amount) {
        if (sender == receiver) {
            System.out.println("sender cannot be receiver");
        } else if (amount <= 0) {
            System.out.println("amount must be positive");
        } else {
            int n;
            String pre;
            if (transactions == null || transactions.size() == 0) {
                n = 1;
                pre = null;
            } else {
                n = transactions.size() + 1;
                Transaction last = transactions.get(transactions.size() - 1);
                pre = Transaction.generateHash(last.id, last.sender.accNo, last.receiver.accNo, last.amount, last.prevHash);
            }

            BankAccount s = accounts.get(sender);
            BankAccount r = accounts.get(receiver);
            Transaction t = new Transaction(n, s, r, amount, pre);

            try {
                if (s.processTransaction(t)) {
                    transactions.add(t);
                    System.out.println("success");
                } else {
                    System.out.println("insufficient funds");
                }
            } catch (java.lang.NullPointerException e) {
                System.out.println("no such account");
            }
        }
    }

    /**
     * Displays the details for a specified transaction.
     *
     * @param id			the transaction ID
     */
    public void transaction(int id) {
        boolean f = false;
        if (transactions == null || transactions.size() == 0) {
            System.out.println("no such transaction");
            return;
        } else {
            for (Transaction t : transactions) {
                if (t.id == id) {
                    System.out.println(t.get());
                    f = true;
                    break;
                }
            }
        }
        if (!f) {
            System.out.println("no such transaction");
        }
    }

    /**
     * Creates the reverse transaction to the one specified.
     *
     * @param id			the transaction id to be cancelled
     */
    public void cancel(int id) {
        try {
            if (transactions != null && transactions.size() != 0) {
                Transaction t = transactions.get(id - 1);
                pay(t.receiver.accNo, t.sender.accNo, t.amount);
            }
        } catch (java.lang.IndexOutOfBoundsException e) {
            System.out.println("no such transaction");
        }
    }

    /**
     * Saves ledger to file.
     *
     * @param ledgerFile		the name of the ledger file
     * @param accFile		the name of the accounts file
     */
    public void archive(String ledgerFile, String accFile) {
        try {
            FileWriter ledger = new FileWriter(ledgerFile);
            for (Transaction t: transactions) {
                ledger.write(String.format("%d, %d, %d, %d, %s\n", t.id, t.receiver.accNo, t.sender.accNo, t.amount, Transaction.generateHash(t.id, t.sender.accNo, t.receiver.accNo, t.amount, t.prevHash)));
            }
            ledger.close();
        } catch (IOException e) {
            System.out.println("no such file");
            return;
        }
        try {
            FileWriter acc = new FileWriter(accFile);
            List<Integer> no_list = new ArrayList<>(accounts.keySet());
            Collections.sort(no_list);
            for (Integer accNo: no_list) {
                BankAccount a = accounts.get(accNo);
                acc.write(String.format("%d, %s, %s, %d\n", a.accNo, a.first, a.last, a.balance));
            }
            acc.close();
        } catch (IOException e) {
            System.out.println("no such file");
            return;
        }
        System.out.println("success");
    }

    /**
     * Restores archived ledger and accounts files to the system.
     *
     * @param ledgerFile		the name of the ledger file
     * @param accFile		the name of the accounts file
     */
    public void recover(String ledgerFile, String accFile) {
        Map<Integer, BankAccount> new_accounts = new HashMap<>();
        try {
            File f = new File(accFile);
            Scanner scan = new Scanner(f);
            while (scan.hasNextLine()) {
                String[] s = scan.nextLine().split(",");
                BankAccount a = new BankAccount(Integer.parseInt(s[0].trim()), s[1].trim(), s[2].trim(), Integer.parseInt(s[3].trim()));
                new_accounts.put(Integer.parseInt(s[0].trim()), a);
            }
        } catch (FileNotFoundException e) {
            System.out.println("no such file");
            return;
        }
        try {
            List<Transaction> new_transaction = new ArrayList<>();
            File f = new File(ledgerFile);
            Scanner scan = new Scanner(f);
            List<String> hash = new ArrayList<>();
            while (scan.hasNextLine()) {
                String[] s = scan.nextLine().split(",");
                String preHash;
                if (new_transaction.size() == 0) {
                    preHash = null;
                } else {
                    preHash = hash.get(hash.size()-1);
                }
                hash.add(s[4].trim());
                Transaction t = new Transaction(Integer.parseInt(s[0].trim()), new_accounts.get(Integer.parseInt(s[2].trim())), new_accounts.get(Integer.parseInt(s[1].trim())), Integer.parseInt(s[3].trim()), preHash);
                new_accounts.get(Integer.parseInt(s[2].trim())).total_transactions.add(t);
                new_accounts.get(Integer.parseInt(s[2].trim())).outgoing_transactions.add(t);
                new_accounts.get(Integer.parseInt(s[1].trim())).total_transactions.add(t);
                new_accounts.get(Integer.parseInt(s[1].trim())).incoming_transactions.add(t);
                new_transaction.add(t);
            }

            if (new_transaction.size() != 0) {
                Transaction p = new_transaction.get(new_transaction.size() - 1);
                if (!Transaction.generateHash(p.id, p.sender.accNo, p.receiver.accNo, p.amount, p.prevHash).equals(hash.get(hash.size() - 1))) {
                    System.out.println("invalid ledger");
                    return;
                }
            }

            if (Transaction.verify(new_transaction)) {
                transactions = new_transaction;
                accounts = new_accounts;
            } else {
                System.out.println("invalid ledger");
                return;
            }
        } catch (FileNotFoundException e) {
            System.out.println("no such file");
            return;
        }

        System.out.println("success");
    }

    /**
     * Transfers all funds into the destination account.
     *
     * @param dest		the account number for the destination account
     * @param others		the account numbers for the accounts to be merged
     */
    public void merge(int dest, int[] others) {
        try {

            BankAccount d = accounts.get(dest);

            List<BankAccount> o = new ArrayList<>();
            for (int other: others) {
                if (dest == other) {
                    System.out.println("sender cannot be receiver");
                    return;
                }
                o.add(accounts.get(other));
            }

            for (BankAccount a: o) {
                int n = 1;
                String pre;
                if (transactions == null || transactions.size() == 0) {
                    pre = null;
                } else {
                    n = transactions.size() + 1;
                    Transaction last = transactions.get(transactions.size() - 1);
                    pre = Transaction.generateHash(last.id, last.sender.accNo, last.receiver.accNo, last.amount, last.prevHash);
                }

                Transaction t = new Transaction(n, a, d, a.balance, pre);

                if (a.processTransaction(t)) {
                    transactions.add(t);
                } else {
                    System.out.println("insufficient funds");
                    return;
                }
            }
            System.out.println("success");

        } catch (java.lang.NullPointerException e) {
            System.out.println("no such account");
        }

    }

    /**
     * Displays the lowest balance in the system.
     */
    public void min() {
        List<BankAccount> list = new ArrayList<>(accounts.values());
        if (BankAccount.findMin(list) == -1) {
            System.out.println("no accounts");
        } else {
            System.out.println("$" + BankAccount.findMin(list));
        }
    }

    /**
     * Displays the highest balance in the system.
     */
    public void max() {
        List<BankAccount> list = new ArrayList<>(accounts.values());
        if (BankAccount.findMax(list) == -1) {
            System.out.println("no accounts");
        } else {
            System.out.println("$" + BankAccount.findMax(list));
        }
    }

    /**
     * Displays the average balance in the system (rounded down).
     */
    public void mean() {
        List<BankAccount> list = new ArrayList<>(accounts.values());
        if (BankAccount.mean(list) == -1) {
            System.out.println("no accounts");
        } else {
            System.out.println("$" + BankAccount.mean(list));
        }
    }

    /**
     * Displays the median balance in the system.
     */
    public void median() {
        List<BankAccount> list = new ArrayList<>(accounts.values());
        if (BankAccount.median(list) == -1) {
            System.out.println("no accounts");
        } else {
            System.out.println("$" + BankAccount.median(list));
        }
    }

    /**
     * Displays the total balance for all accounts.
     */
    public void total() {
        List<BankAccount> list = new ArrayList<>(accounts.values());
        if (BankAccount.totalBalance(list) == -1) {
            System.out.println("no accounts");
        } else {
            System.out.println("$" + BankAccount.totalBalance(list));
        }
    }

    public static void main(String[] args) {

        Banker banker = new Banker();

        System.out.print("$ ");
        Scanner scan = new Scanner(System.in);

        while (scan.hasNextLine()) {

            String s = scan.nextLine();
            String[] list = s.split(" ");
            char[] char_list = list[0].toCharArray();
            char[] return_list = new char[char_list.length];
            for (int i=0; i < char_list.length; i++) {return_list[i] = Character.toUpperCase(char_list[i]);}
            String cmd = String.valueOf(return_list);
            int len = list.length;

            if (cmd.equals("EXIT")) {
                exit();
                break;
            } else if (cmd.equals("MERGE")) {
                int dest = Integer.parseInt(list[1]);
                int[] others = new int[len - 2];
                for (int i = 0; i < len - 2; i++) {
                    others[i] = Integer.parseInt(list[i + 2]);
                }
                banker.merge(dest, others);

            } else if (len == 1) {

                switch (cmd) {
                    case "COMMANDS":
                        commands();
                        break;
                    case "MAX":
                        banker.max();
                        break;
                    case "MIN":
                        banker.min();
                        break;
                    case "MEAN":
                        banker.mean();
                        break;
                    case "MEDIAN":
                        banker.median();
                        break;
                    case "TOTAL":
                        banker.total();
                        break;
                }

            } else if (len == 2) {

                switch (cmd) {
                    case "LIST":
                        char[] a = list[1].toCharArray();
                        char[] b = new char[a.length];
                        for (int i=0; i<a.length; i++) {b[i] = Character.toUpperCase(a[i]);}
                        String d = String.valueOf(b);

                        if (d.equals("ACCOUNTS")) {
                            banker.listAccounts();
                        } else if (d.equals("TRANSACTIONS")) {
                            banker.listTransactions();
                        }
                        break;
                    case "DETAILS":
                        try {
                            banker.details(Integer.parseInt(list[1]));
                        } catch (NumberFormatException ignored) {
                        }
                        break;
                    case "BALANCE":
                        try {
                            banker.balance(Integer.parseInt(list[1]));
                        } catch (NumberFormatException ignored) {
                        }
                        break;
                    case "HISTORY":
                        try {
                            banker.history(Integer.parseInt(list[1]));
                        } catch (NumberFormatException ignored) {
                        }
                        break;
                    case "OUTGOING":
                        try {
                            banker.outgoing(Integer.parseInt(list[1]));
                        } catch (NumberFormatException ignored) {
                        }
                        break;
                    case "INCOMING":
                        try {
                            banker.incoming(Integer.parseInt(list[1]));
                        } catch (NumberFormatException ignored) {
                        }
                        break;
                    case "TRANSACTION":
                        banker.transaction(Integer.parseInt(list[1]));
                        break;
                    case "CANCEL":
                        banker.cancel(Integer.parseInt(list[1]));
                        break;
                }

            } else if (len == 3) {

                switch (cmd) {
                    case "CREATE":
                        banker.createAccount(list[1], list[2], BankAccount.DEFAULT);
                        break;
                    case "ARCHIVE":
                        banker.archive(list[1], list[2]);
                        break;
                    case "RECOVER":
                        banker.recover(list[1], list[2]);
                        break;
                }

            } else if (len ==4) {

                switch (cmd) {
                    case "CREATE":
                        banker.createAccount(list[1], list[2], Integer.parseInt(list[3]));
                        break;
                    case "RENAME":
                        banker.rename(Integer.parseInt(list[1]), list[2], list[3]);
                        break;
                    case "PAY":
                        banker.pay(Integer.parseInt(list[1]), Integer.parseInt(list[2]), Integer.parseInt(list[3]));
                        break;
                }

            }


            System.out.println();
            System.out.print("$ ");
        }

    }

}
