
package mytodo;
import java.io.Serializable;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.text.SimpleDateFormat;
/**
 * Κλάση με σκοπό τη διαχείριση ημερομηνιών
 *
 */
public class day implements Serializable {
    
    Calendar aday = new GregorianCalendar();
    SimpleDateFormat frmt = new SimpleDateFormat("d-M-yyyy");
    /**
     * Κατασκευαστής της κλάσης
     * @param date Ημερομηνία 
     * @param month Μήνας
     * @param year Έτος
     */
    public day(int date, int month, int year){
            this.aday.set(year,month-1,date);
    }
     /**
     * Κατασκευαστής της κλάσης
     * Σημερινή ημερομηνία!
     */
    public day(){
    
    }
    
     /**
     * Κατασκευαστής της κλάσης
     * @param daystr Συμβολοσειρα της μορφής d-M-yyyy.
     * Σε περίπτωση αποτυχίας, σφάλματος στο daystr λαμβάνει την τρέχουσα ημερομηνία!
     */
    public day(String daystr){
        String sep="[-]";
        String[] data =  daystr.split(sep);
        this.aday.set(Integer.parseInt(data[2]),
                Integer.parseInt(data[1])-1,
                Integer.parseInt(data[0]));
        
        
    }
    /**
     * 
     * @return Επιστρέφει την ημερομηνία σαν συμβολοσειρά στη μορφή "20-2-1997"
     */
    @Override
    public String toString(){
        //this.frmt.format(date)
        return this.frmt.format(this.aday.getTime());
    }
    /**
     * 
     * @return Επιστρέφη την ημερομηνία
     */
    public int getDate(){
        return this.aday.get(Calendar.DATE);
    }
    /**
     * 
     * @return Επιστρέφει το μήνα (1-12)
     */
    public int getMonth(){
        return this.aday.get(Calendar.MONTH)+1 ;  //January is 0 
    }
    /**
     * Επιστρέφει το έτος
     * @return 
     */
    public int getYear(){
        return this.aday.get(Calendar.YEAR);
    }
    
     /**
     * Ελέγχει αν μια τριάδα αριθμών είναι αποδεκη ημερομηνία
     * @param dd  ημέρα
     * @param mm  μήνας
     * @param yy  έτος
     * @return true εαν η ημερα είναι αποδεκτή, false αν η ημέρα δεν είναι αποδεκτή
     */
    static boolean isDay(int dd,int mm, int yy){
        day d = new day(dd,mm,yy);
        boolean dok = true;
        if (dd!=d.getDate() || mm!=d.getMonth() || yy!= d.getYear()){
            dok= false;
        }
        return dok;
    }

    /**
     * Ελέγχει αν είναι ίδια η ημέρα
     * @param d η ημέρα για σύγκριση
     * @return true όταν οι ημερομηνίες είναι ίδιες, false όταν δεν είναι
     */
    public boolean equals(day d){
        String d1 = this.toString();
        String d2 = d.toString();
        boolean res = d1.equals(d2);
        return res;
    }
    /**
     * Ελέγχει αν η μέρα είναι μεταξύ των ημρομηνιών (συμπεριλαμβανομένων)
     * @param begin αρχή περιόδου
     * @param end τέλος περιόδου
     * @return true όταν η ημέρα βρίσκεται μεταξύ των ημερομηνιών, false διαφορετικά
     */
    public boolean between(day begin, day end){
        return (this.equals(begin)|| this.equals(end)|| aday.after(begin.aday) && aday.before(end.aday));
    }
}
