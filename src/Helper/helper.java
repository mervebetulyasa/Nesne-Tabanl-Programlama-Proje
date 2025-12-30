package Helper;

import java.awt.Color;
import java.awt.Font;
import javax.swing.JOptionPane;
import javax.swing.UIManager;
import javax.swing.plaf.ColorUIResource;

public class helper {

    static Color renkArkaPlan = new Color(242, 238, 231); 
    static Color renkAnaRenk = new Color(142, 89, 83);  
    static Color renkBeyaz = Color.WHITE;

    public static void optionPaneChange() {
    	
        UIManager.put("OptionPane.background", new ColorUIResource(renkArkaPlan));
        UIManager.put("Panel.background", new ColorUIResource(renkArkaPlan));
        
        UIManager.put("OptionPane.messageForeground", new ColorUIResource(renkAnaRenk));
        UIManager.put("OptionPane.messageFont", new Font("Yu Gothic UI Semibold", Font.PLAIN, 14));
        
        UIManager.put("Button.background", new ColorUIResource(renkAnaRenk));
        
        UIManager.put("Button.foreground", new ColorUIResource(renkBeyaz));
        
        UIManager.put("Button.font", new Font("Yu Gothic UI Semibold", Font.BOLD, 12));
        
        UIManager.put("Button.focus", new ColorUIResource(new Color(0, 0, 0, 0))); 
        UIManager.put("Button.select", new ColorUIResource(renkAnaRenk));
        
        UIManager.put("Button.defaultButtonFollowsFocus", Boolean.FALSE);
    }

    public static void showMsg(String str) {
        optionPaneChange(); 
        
        String msg;
        String title;

        switch (str) {
            case "fill":
                msg = "Lütfen tüm alanları doldurunuz!";
                title = "Eksik Bilgi";
                break;
            case "success":
                msg = "İşlem Başarıyla Gerçekleşti!";
                title = "Başarılı";
                break;
            case "error":
                msg = "Bir hata oluştu!";
                title = "Hata";
                break;
            default:
                msg = str;
                title = "Mesaj";
        }

        JOptionPane.showMessageDialog(null, msg, title, JOptionPane.PLAIN_MESSAGE);
    }

    public static boolean confirm(String str) {
        optionPaneChange();
        
        String msg;
        switch(str){
            case "sure":
                msg = "Bu işlemi gerçekleştirmek istediğinize emin misiniz?";
                break;
            default:
                msg = str;
        }
        
        Object[] options = {"Evet", "Hayır"};
        
        int res = JOptionPane.showOptionDialog(null, msg, "Onay", 
                JOptionPane.YES_NO_OPTION, 
                JOptionPane.PLAIN_MESSAGE, 
                null, options, options[0]);
        
        return res == 0;
    }
}