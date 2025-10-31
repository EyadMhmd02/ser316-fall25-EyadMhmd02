/**
 * Represents a table in the restaurant.
 * Tables have a number, party size, and can have orders associated with them.
 */
public class Table {
    
    private int tableNumber;
    private int partySize;
    private boolean occupied;
    private String serverName;
    
    /**
     * Creates a new table
     * @param tableNumber table number
     * @param partySize number of guests
     */
    public Table(int tableNumber, int partySize) {
        this.tableNumber = tableNumber;
        this.partySize = partySize;
        this.occupied = false;
        this.serverName = "";
    }
    
    /**
     * Gets table number
     * @return table number
     */
    public int getTableNumber() {
        return tableNumber;
    }
    
    /**
     * Gets party size
     * @return party size
     */
    public int getPartySize() {
        return partySize;
    }
    
    /**
     * Sets party size
     * @param partySize new party size
     */
    public void setPartySize(int partySize) {
        this.partySize = partySize;
    }
    
    /**
     * Checks if table is occupied
     * @return true if occupied
     */
    public boolean isOccupied() {
        return occupied;
    }
    
    /**
     * Sets occupied status
     * @param occupied occupation status
     */
    public void setOccupied(boolean occupied) {
        this.occupied = occupied;
    }
    
    /**
     * Gets server name
     * @return server name
     */
    public String getServerName() {
        return serverName;
    }
    
    /**
     * Sets server name
     * @param serverName name of server
     */
    public void setServerName(String serverName) {
        this.serverName = serverName;
    }
}
