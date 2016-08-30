package scripts;
 
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.io.IOException;
import java.net.URL;
 
import javax.imageio.ImageIO;
 
import org.tribot.api.Timing;
import org.tribot.api.input.Mouse;
import org.tribot.api.util.ABCUtil;
import org.tribot.api2007.Banking;
import org.tribot.api2007.Inventory;
import org.tribot.api2007.Login;
import org.tribot.api2007.Magic;
import org.tribot.api2007.Skills;
import org.tribot.api2007.Login.STATE;
import org.tribot.api2007.Skills.SKILLS;
import org.tribot.api2007.types.RSItem;
import org.tribot.script.Script;
import org.tribot.script.ScriptManifest;
import org.tribot.script.interfaces.Painting;


@ScriptManifest(authors = { "Genka" }, category = "Magic", name = "GEnchant")
public class main extends Script implements Painting{
        ABCUtil abc = new ABCUtil();
        boolean alive = true;
        private int startXp = Skills.getXP(SKILLS.MAGIC);
        private long lastBusyTime = System.currentTimeMillis();
        static String spell = "";
        static int itemToEnchant = 1639;
        int cosmicID = 564;
        GUI gui = new GUI();
       
        @Override
        public void run() {
                gui.setVisible(true);
                while(gui.isVisible()){
                        sleep(300);
                }
                while(alive){
                        if(!Banking.isBankScreenOpen()){
                                abc.performTimedActions(SKILLS.MAGIC);
                        }
                        if(Inventory.find(itemToEnchant).length > 0 && Inventory.find(cosmicID).length > 0){
                                if(Banking.isBankScreenOpen()){
                                        Banking.close();
                                }
                                if(!Magic.isSpellSelected()){
                                        Magic.selectSpell(spell);
                                }
                                if(Magic.isSpellSelected()){
                                        if(!Magic.getSelectedSpellName().equals(spell)){
                                                println(Magic.getSelectedSpellName());
                                                Mouse.click(1);
                                        }
                                        if(Inventory.find(itemToEnchant).length > 0){
                                                if(Inventory.getAll().length > 1 && Inventory.getAll()[Inventory.getAll().length - 1].getID() == itemToEnchant && Inventory.getAll()[Inventory.getAll().length -2 ].getID() == 2552){
                                                Inventory.find(itemToEnchant)[0].click("Cast");
                                                sleep(1000, 2000);
                                                } else {
                                                        Inventory.find(itemToEnchant)[0].click("Cast");
                                                        abc.waitNewOrSwitchDelay(lastBusyTime, false);
                                                }
                                        }
                                }
                        } else {
                                if(Banking.isBankScreenOpen()){
                                        if(Inventory.getAll().length > 1){
                                                Banking.depositAllExcept(cosmicID);
                                                long t = System.currentTimeMillis();
                                                while(Inventory.getAll().length > 1 && Timing.timeFromMark(t) < 2000){
                                                        sleep(10,300);
                                                }
                                        } else {
                                                if(Magic.isSpellSelected()){
                                                        Mouse.click(1);
                                                }
                                                if(Inventory.find(cosmicID).length == 0){
                                                        RSItem[] cosmics = Banking.find(cosmicID);
                                                        RSItem[] enchant = Banking.find(itemToEnchant);
                                                        if((cosmics.length == 0 || enchant.length == 0) && Banking.getAll().length > 0){
                                                                println("Out of supplies");
                                                                long t = System.currentTimeMillis();
                                                                while(Login.getLoginState() == STATE.INGAME && Timing.timeFromMark(t) < 20000){
                                                                        sleep(100,500);
                                                                        Banking.close();
                                                                        Login.logout();
                                                                }
                                                                if(Login.getLoginState() == STATE.LOGINSCREEN){
                                                                        alive = false;
                                                                }
                                                        }
                                                        Banking.withdraw(1000, cosmicID);
                                                }
                                                Banking.withdraw(0, itemToEnchant);
                                                long t = System.currentTimeMillis();
                                                while(Inventory.find(itemToEnchant).length == 0 && Timing.timeFromMark(t) < 3000){
                                                        sleep(10,300);
                                                }
                                        }
                                } else if(!Banking.isBankScreenOpen()){
                                        Banking.openBank();
                                        long t = System.currentTimeMillis();
                                        while(!Banking.isBankScreenOpen() && Timing.timeFromMark(t) < 2000){
                                                sleep(10,300);
                                        }
                                }                              
                        }
                        sleep(10, 50);
                }
               
        }
       
    //START: Code generated using Enfilade's Easel
    private Image getImage(String url) {
        try {
            return ImageIO.read(new URL(url));
        } catch(IOException e) {
            return null;
        }
    }
 
    private final Color color1 = new Color(255, 255, 255);
 
    private final Font font1 = new Font("Arial", 0, 10);
 
    private final Image img1 = getImage("http://i.imgur.com/zwBUJ0i.png");
 
    public void onPaint(Graphics g1) {
        double multiplier = getRunningTime() / 3600000.0D;
            int xpPerHour = (int) ((Skills.getXP(SKILLS.MAGIC) - startXp) / multiplier);               
                Graphics2D g = (Graphics2D)g1;
        g.drawImage(img1, 277, 345, null);
        g.setFont(font1);
        g.setColor(color1);
        g.drawString("Runtime: " + Timing.msToString(getRunningTime()), 366, 409);
        g.drawString("RC level: " + Skills.getActualLevel(SKILLS.MAGIC), 366, 421);
        g.drawString("XP: "  + (Skills.getXP(SKILLS.MAGIC) - startXp) + " (" + xpPerHour + ")", 366, 433);
    }
    //END: Code generated using Enfilade's Easel
 
}