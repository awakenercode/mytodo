
package mytodo;

import java.io.Serializable;


public class obligation implements Serializable  {
    String title;
    String desc;
    day deadline;
    //DayTime;
    boolean done;
    /**
     * Κατασκευαστής κλάσης
     * @param title Τίτλος
     * @param desc Περιγραφή
     * @param deadline Ημερομηνία λήξης
     * @param done Ολοκληρώθηκε (true ολοκληρωμένη, false μη ολοκληρωμένη)
     */
    public obligation(String title, String desc, day deadline,boolean done){
        this.title = title;
        this.desc = desc;
        this.done = done;
        this.deadline = deadline;
    }
    
    String getTitle(){
        return title;
    }
    
    String getDesc(){
        return desc;
    }    
    String getDeadline(){
        return deadline.toString();
    }
    
    boolean highpriority(){
        return false;
    };
    
}
