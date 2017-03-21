import java.util.Set;

/* CompliantNode refers to a node that follows the rules (not malicious)*/
public class CompliantNode implements Node {

    private Set<Transaction> transactions;

    public CompliantNode(double p_graph, double p_malicious, double p_txDistribution, int numRounds) {
    }

    public void setFollowees(boolean[] followees) {

    }

    public void setPendingTransaction(Set<Transaction> pendingTransactions) {
        this.transactions = pendingTransactions;
    }

    public Set<Transaction> sendToFollowers() {
        return transactions;
    }

    public void receiveFromFollowees(Set<Candidate> candidates) {
        for (Candidate candidate : candidates) {
            if (!transactions.contains(candidate.tx)) {
                transactions.add(candidate.tx);
            }
        }
    }
}
