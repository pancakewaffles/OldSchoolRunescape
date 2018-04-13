public class Ores
{
    int[] copper;
    int[] tin;
    int[] clay;
    int[] essence;
    int[] limestone;
    int[] iron;
    int[] silver;
    int[] coal;
    int[] sandstone;
    int[] gems;
    int[] gold;
    int[] granite;
    int[] mithril;
    int[] adamantite;
    int[] runite;
    int[][] ores;
    int[] unknownOre;
    
    public Ores() {
    	/*
    	 * How the ores work. When ores are depleted (mined) they change their id from whatever is listed below to some other id. 
    	 * The function isValid works by returning true if the ore is one of those listed below (i.e. not depleted) and returns false if the ore is not one of those listed below (i.e. depleted).
    	 */
        this.copper = new int[] { 7453, 7483, 7484 };
        this.tin = new int[] { 7485, 7486 };
        this.clay = new int[] { 7454, 7487 };
        this.essence = new int[0];
        this.limestone = new int[] { 7465 };
        this.iron = new int[] { 7455, 7488 };
        this.silver = new int[] { 7490, 7457 };
        this.coal = new int[] { 7456, 7489 };
        this.sandstone = new int[] { 8727 };
        this.gems = new int[] { 7464, 7463 };
        this.gold = new int[] { 7458, 7491 };
        this.granite = new int[] { 7467 };
        this.mithril = new int[] { 7459, 7492 };
        this.adamantite = new int[] { 7493, 7460 };
        this.runite = new int[] { 7461, 7494 };
        this.ores = new int[][] { this.copper, this.tin, this.clay, this.essence, this.limestone, this.iron, this.silver, this.coal, this.sandstone, this.gems, this.gold, this.granite, this.mithril, this.adamantite, this.runite };
        this.unknownOre = new int[] { -1 };
    }
    
    public int[] getIDs(final int id) {
        for (int i = 0; i < this.ores.length; ++i) {
            for (int j = 0; j < this.ores[i].length; ++j) {
                if (this.ores[i][j] == id) {
                    return this.ores[i];
                }
            }
        }
        return this.unknownOre;
    }
    
    public boolean isValid(final int id) {
        for (int i = 0; i < this.ores.length; ++i) {
            for (int j = 0; j < this.ores[i].length; ++j) {
                if (this.ores[i][j] == id) {
                    return true;
                }
            }
        }
        return false;
    }
}