
package mytodo;

import java.io.EOFException;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class MyTodo {
    static List<obligation> obs = new ArrayList<>();
    static JFrame frame;
    static JPanel activePanel = null;
    String filename = "mytodo.dat";
    obligation activeobligation = null;
    
    public MyTodo(JFrame frame) throws ClassNotFoundException{
        this.frame = frame;
        readFile();
    }
    /**
     * Διαβάζει το αρχείο των υποχρεώσεων
     */
    private void readFile(){
        System.out.println("Start Reading File!");
        try{
            FileInputStream fi = new FileInputStream(filename);
            ObjectInputStream ois = new ObjectInputStream(fi);
            obligation ob;
            while ((ob=(obligation)ois.readObject())!=null){
                obs.add(ob);
                System.out.println("Obligation imported!");
            }
            ois.close();
            fi.close();
        } catch(FileNotFoundException fnf) {
            System.out.println("File not found!");
        } catch (EOFException exc){
            System.out.println("End of File!");
        } catch(IOException e){
            System.out.println("Unknown IO Error!");
            System.out.println(e.getMessage());
        } catch(ClassNotFoundException e){
            System.out.println("Class not found Error! The file contains incompatible data!");
        };
    }
    
     /**
     * Αποθηκεύει το αρχείο των υποχρεώσεων
     */
    private void saveFile(){
        try{
            FileOutputStream fo = new FileOutputStream(filename);
            ObjectOutputStream oos = new ObjectOutputStream(fo);
            for (var ob : obs){
                oos.writeObject(ob);
            }
            oos.close();
            fo.close();
            System.out.println("File write OK!");
        } catch(FileNotFoundException fnf) {
            System.out.println("File not found!");
        } catch(IOException e){
            System.out.println("IO Error!");
        }
    }
    
    /**
     * Προσθέτει υποχρέωση υψηλής προτεραιότητας
     * @param title Τίτλος υποχρέωσης
     * @param desc Περιγραφή
     * @param date ημέρα λήξης
     * @param done Ολοκληρώθηκε (true) - Δεν ολοκληρώθηκε (false)
     * @param hour Ώρα λήξης
     * @param minute λεπτό λήξης
     * @return true εάν η υποχρέωση προστεθεί με επιτυχία false αν υπάρχει ήδη υποχρέωση με ίδιο τίτλο και ημερομηνία λήξης
     */  
    public boolean AddObbligation(String title, String desc, day date, boolean done, int hour, int minute){
        if (ObligationExists(title, date)!=null){
            return false;
        }
        obligation a = new highpriorityob(title, desc, date, done, hour, minute);
        obs.add(a);
        saveFile();
        return true;
        
    }
    
    /**
     * Προσθέτει υποχρέωση 
     * @param title Τίτλος υποχρέωσης
     * @param desc Περιγραφή
     * @param date ημέρα λήξης
     * @param done Ολοκληρώθηκε (true) - Δεν ολοκληρώθηκε (false)
     * @return true εάν η υποχρέωση προστεθεί με επιτυχία false αν υπάρχει ήδη υποχρέωση με ίδιο τίτλο και ημερομηνία λήξης
     */      
    public boolean AddObbligation(String title, String desc, day date, boolean done){
        //System.out.println(title +" "+ date.toString());
        if (ObligationExists(title, date)!=null){
            return false;
        }
        obligation a = new obligation(title, desc, date, done);
        obs.add(a);
        saveFile();
        return true;
    }

    /**
     * Αλλάζει τα στοιχεία τις επιλεγμένης υποχρέωσης. Η διορθωμενη υποχρέωση είναι υψηλής προτεραιότητας
     * @param title Τίτλος υποχρέωσης
     * @param desc Περιγραφή
     * @param date ημέρα λήξης
     * @param done Ολοκληρώθηκε (true) - Δεν ολοκληρώθηκε (false)
     * @param hour Ώρα λήξης
     * @param minute λεπτό λήξης
     * @return true εάν η υποχρέωση αλλάξει με επιτυχία, false αν αποτύχει η αλλαγή. (πχ.αλλαγή ημερομηνίας λήξης συμπίμπτει με άλλη υποχρέωση με το ίδιο όνομα)
     */  
    public boolean ChangeObligation(String title, String desc, day date, boolean done, int hour, int minute){
        if (ObligationExists(title, date, activeobligation)!=null){
            return false;
        }
        activeobligation.title = title;
        activeobligation.desc = desc;
        activeobligation.deadline = date;
        activeobligation.done = done;
        ((highpriorityob)activeobligation).hour = hour;
        ((highpriorityob)activeobligation).minute = minute;
        saveFile();
        return true;    
    }

    /**
     * Αλλάζει τα στοιχεία τις επιλεγμένης υποχρέωσης. Η διορθωμενη υποχρέωση είναι κανονικής προτεραιότητας.
     * @param title Τίτλος υποχρέωσης
     * @param desc Περιγραφή
     * @param date ημέρα λήξης
     * @param done Ολοκληρώθηκε (true) - Δεν ολοκληρώθηκε (false)
     * @return true εάν η υποχρέωση αλλάξει με επιτυχία, false αν αποτύχει η αλλαγή. (πχ.αλλαγή ημερομηνίας λήξης συμπίμπτει με άλλη υποχρέωση με το ίδιο όνομα)
     */  
    public boolean ChangeObligation(String title, String desc, day date, boolean done){
        if (ObligationExists(title, date, activeobligation)!=null){
            return false;
        }
        activeobligation.title = title;
        activeobligation.desc = desc;
        activeobligation.deadline = date;
        activeobligation.done = done;
        saveFile();
        return true;
    }

    /**
     * Ελέγει αν υπάρχει υποχρέωση με αυτά τα στοιχεία
     * @param title Τίλος της υποχρέωσης
     * @param date ημερομηνία λήξης
     * @return την υποχρέωση εφ' όσον βρεθεί. Διαφορτικά null.
     */
    obligation ObligationExists(String title, day date){
        for (var ob: obs){
           System.out.println(title +" "+ob.title+" "+ob.getDeadline()+" "+ date.toString());
            if((ob.getTitle() == null ? title == null : ob.getTitle().equals(title)) && 
               (date.toString() == null ? ob.getDeadline() == null : date.toString().equals(ob.getDeadline()))) return ob;
        }
        return null;
    }
    
     /**
     * Ελέγει αν υπάρχει υποχρέωση με αυτά τα στοιχεία εξαιρόντας από τη λίστα μία υποχρέωση
     * @param title Τίλος της υποχρέωσης
     * @param date ημερομηνία λήξης
     * @param except Η υποχρέωση που πρέπει να εξαιρεθει από την αναζήτηση.
     * @return την υποχρέωση εφ' όσον βρεθεί. Διαφορτικά null.
     */
    obligation ObligationExists(String title, day date, obligation except){
        for (var ob: obs){
           System.out.println(title +" "+ob.title+" "+ob.getDeadline()+" "+ date.toString());
            if((ob.getTitle() == null ? title == null : ob.getTitle().equals(title)) && 
               (date.toString() == null ? ob.getDeadline() == null : date.toString().equals(ob.getDeadline()))) {
                 if (except != ob)  return ob; 
            }
        }
        return null;
    }    
    
    /**
     *
     * @return το  Πλήθος στοιχείων στη λίστα υποχρεώσεων
     */
    public int getObsCount(){
        return obs.size();
    }
    /**
     * 
     * @param i Θέση στη λίστα
     * @return τίτλος υποχρέωσης
     */
    public String getObTitle(int i){
        return obs.get(i).getTitle();
    }
    
    /**
     * 
     * @param i θέση στη λίστα
     * @return ημερομηνία λήξης υποχρέωσης (αλφαριθμητικό σε μορφή η-Μ-εεεε)
     */
    public String getDeadline(int i){
        return obs.get(i).getDeadline();
    }
}
