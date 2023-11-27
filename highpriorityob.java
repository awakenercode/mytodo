
package mytodo;


public class highpriorityob extends obligation {
    /**
     * Προθεσμία: Ώρα λήξης (αριθμός από 0 μέχρι 23)
     */
    int hour;
    /**
     * Προθεσμία: λεπτό λήξης (αριθμός από 0 μέχρι 59)
     */
    int minute;
    
    /**
     * Κατασκευαστής υποχρέωσης υψηλής προτεραιότητας
     * @param title Τίτλος
     * @param desc Περιγραφή
     * @param deadline Ημερομηνία λήξης
     * @param done Ολοκληρώθηκε (true ολοκληρωμένη, false μη ολοκληρωμένη)
     * @param hour Ώρα λήξης (0-23)
     * @param minute λεπτό λήξης (0-59)
     */
    public highpriorityob(String title, String desc, day deadline, boolean done, int hour, int minute) {
        super(title, desc, deadline, done);
        this.hour = hour;
        this.minute = minute;
    }

    @Override
    boolean highpriority() {
        return true;
    }
    
    
    
}
