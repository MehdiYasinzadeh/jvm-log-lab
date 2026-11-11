package ir.mehdiyasinzadeh.statistics;

/**
 * @author Mehdi Yasinzadeh
 * @since 11/11/2026
 */
public class LogCounter {
    int count = 0;

    public void increment(){
        this.count++;
    }
    public int getCount(){
        return this.count;
    }
}
