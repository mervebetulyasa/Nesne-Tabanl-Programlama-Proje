package View;
import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Font;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTabbedPane;
import javax.swing.JTextField;
import javax.swing.UIManager;
import javax.swing.border.EmptyBorder;
import javax.swing.plaf.ColorUIResource;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.awt.event.ActionEvent;

import Helper.DBConnection;
import Helper.helper;
import Model.Gorevli;
import Model.Mudur;
import Model.Ogrenci;
import Model.Teknisyen;

public class GUI extends JFrame {

    private static final long serialVersionUID = 1L;
    private JPanel contentPane;
    
    private JTextField textField_3;
    private JPasswordField passwordField;
    private JTextField textField;
    private JPasswordField passwordField_1;
    private JTextField textField_1;
    private JPasswordField passwordField_2;
    private JTextField textField_2;
    private JPasswordField passwordField_3;
    private DBConnection conn = new DBConnection();

    /**
     * Uygulamayı Başlat.
     */
    public static void main(String[] args) {
        try {
            Color porcelain = Color.decode("#F2EEE7");
            Color honey = Color.decode("#D8C595");

            UIManager.put("TabbedPane.selected", honey); 
            UIManager.put("TabbedPane.background", porcelain);
            UIManager.put("TabbedPane.focus", new ColorUIResource(new Color(0, 0, 0, 0))); 
            UIManager.put("TabbedPane.contentAreaColor", porcelain);
            UIManager.put("TabbedPane.unselectedBackground", porcelain);
        } catch (Exception e) {
            e.printStackTrace();
        }
        
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    GUI frame = new GUI();
                    frame.setVisible(true);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    /**
     * Arayüzü Oluştur.
     */
    public GUI() {

        Color renkSage = new Color(157, 170, 133); 
        Color renkPorcelain = Color.decode("#F2EEE7"); 
        Color renkPlum = Color.decode("#8E5953"); 
        Color renkCoral = Color.decode("#E6AEA4"); 
        Color renkHoney = Color.decode("#D8C595");
        Color renkYazi = renkPlum; 
        Color renkBeyaz = Color.WHITE;

        setTitle("Çamaşırhane Otomasyonu");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100, 100, 500, 280); 
        
        contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        contentPane.setBackground(renkPorcelain); 
        setContentPane(contentPane);
        contentPane.setLayout(null);
        
        JPanel baslikPaneli = new JPanel();
        baslikPaneli.setBounds(0, 0, 500, 50); 
        baslikPaneli.setBackground(renkSage); 
        baslikPaneli.setLayout(null);
        contentPane.add(baslikPaneli);
        
        JLabel lblBaslik = new JLabel("Yurt Çamaşırhane Sistemine Hoşgeldiniz");
        lblBaslik.setFont(new Font("Yu Gothic UI Semibold", Font.PLAIN, 16));
        lblBaslik.setForeground(Color.WHITE);
        lblBaslik.setBounds(85, 12, 350, 25);
        baslikPaneli.add(lblBaslik); 
        
        JTabbedPane tabbedPane = new JTabbedPane(JTabbedPane.TOP);
        tabbedPane.setBounds(24, 60, 427, 170);
        tabbedPane.setBackground(renkPorcelain);
        contentPane.add(tabbedPane);
        
        
        JPanel ogrencigirisi = new JPanel();
        ogrencigirisi.setBackground(renkPorcelain); 
        tabbedPane.addTab("Öğrenci Girişi", null, ogrencigirisi, null);
        ogrencigirisi.setLayout(null);
        
        JLabel lblOgrenciKadi = new JLabel("Kullanıcı Adı:");
        lblOgrenciKadi.setFont(new Font("Yu Gothic UI Semibold", Font.PLAIN, 12));
        lblOgrenciKadi.setForeground(renkYazi);
        lblOgrenciKadi.setBounds(72, 12, 85, 13);
        ogrencigirisi.add(lblOgrenciKadi);
        
        JLabel lblOgrenciSifre = new JLabel("Şifre:");
        lblOgrenciSifre.setFont(new Font("Yu Gothic UI Semibold", Font.PLAIN, 13));
        lblOgrenciSifre.setForeground(renkYazi);
        lblOgrenciSifre.setBounds(72, 35, 85, 12);
        ogrencigirisi.add(lblOgrenciSifre);
        
        textField_3 = new JTextField();
        textField_3.setText("Kullanıcı adı giriniz");
        textField_3.setFont(new Font("Yu Gothic UI Semilight", Font.ITALIC, 10));
        textField_3.setBounds(167, 10, 120, 20);
        textField_3.setBorder(javax.swing.BorderFactory.createLineBorder(renkCoral));
        ogrencigirisi.add(textField_3);
        
        passwordField = new JPasswordField();
        passwordField.setFont(new Font("Yu Gothic UI Semilight", Font.ITALIC, 10));
        passwordField.setBounds(167, 33, 120, 20);
        passwordField.setBorder(javax.swing.BorderFactory.createLineBorder(renkCoral));
        ogrencigirisi.add(passwordField);
        
        JButton btnOgrenciGiris = new JButton("Giriş Yap");
        btnOgrenciGiris.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent e) {
        		if(textField_3.getText().length() == 0 || passwordField.getText().length() == 0) {
        			helper.showMsg("fill");

        		} else {
        			Connection con = conn.connDb();
        			try {
						Statement st = con.createStatement();
						ResultSet rs = st.executeQuery("SELECT * FROM user");
						boolean girisYapildi = false;
						while(rs.next()) {
							if(textField_3.getText().equals(rs.getString("username")) && passwordField.getText().equals(rs.getString("userpassword"))){
								if(rs.getString("usertype").equals("ogrenci")) {
								Ogrenci ogrenci = new Ogrenci();
								
								ogrenci.setUsername(rs.getString("username"));
								ogrenci.setUserpassword("userpassword");
								ogrenci.setUsertype(rs.getString("usertype"));
								ogrenci.setIduser(rs.getInt("userid"));
								ogrenci.setNameSurname(rs.getString("name_surname"));
								
								
								
								OgrenciGUI oGUI = new OgrenciGUI(ogrenci);
                                oGUI.setVisible(true);
                                dispose(); 
                                girisYapildi = true;
                                return;
								}	
        	
        }
						}
					} catch (SQLException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
        		}
        		
        		
        	}
        });
        btnOgrenciGiris.setFont(new Font("Yu Gothic UI Semibold", Font.PLAIN, 14));
        btnOgrenciGiris.setBackground(renkPlum); // Aynı renk
        btnOgrenciGiris.setForeground(renkBeyaz);
        btnOgrenciGiris.setOpaque(true); 
        btnOgrenciGiris.setBorderPainted(false);
        btnOgrenciGiris.setBounds(72, 75, 215, 29);
        ogrencigirisi.add(btnOgrenciGiris);
        
        JPanel gorevligirisi = new JPanel();
        gorevligirisi.setBackground(renkPorcelain);
        tabbedPane.addTab("Görevli Girişi", null, gorevligirisi, null);
        gorevligirisi.setLayout(null);
        
        JLabel lblGorevliKadi = new JLabel("Kullanıcı Adı:");
        lblGorevliKadi.setFont(new Font("Yu Gothic UI Semibold", Font.PLAIN, 12));
        lblGorevliKadi.setForeground(renkYazi);
        lblGorevliKadi.setBounds(72, 12, 85, 13);
        gorevligirisi.add(lblGorevliKadi);
        
        JLabel lblGorevliSifre = new JLabel("Şifre:");
        lblGorevliSifre.setFont(new Font("Yu Gothic UI Semibold", Font.PLAIN, 13));
        lblGorevliSifre.setForeground(renkYazi);
        lblGorevliSifre.setBounds(72, 38, 85, 12);
        gorevligirisi.add(lblGorevliSifre);
        
        textField = new JTextField();
        textField.setText("Kullanıcı adı giriniz");
        textField.setFont(new Font("Yu Gothic UI Semilight", Font.ITALIC, 10));
        textField.setBounds(167, 9, 120, 20);
        textField.setBorder(javax.swing.BorderFactory.createLineBorder(renkCoral));
        gorevligirisi.add(textField);
        
        passwordField_1 = new JPasswordField();
        passwordField_1.setFont(new Font("Yu Gothic UI Semilight", Font.ITALIC, 10));
        passwordField_1.setBounds(167, 35, 120, 20);
        passwordField_1.setBorder(javax.swing.BorderFactory.createLineBorder(renkCoral));
        gorevligirisi.add(passwordField_1);
        
        JButton btnGorevliGiris = new JButton("Giriş Yap");
        btnGorevliGiris.setFont(new Font("Yu Gothic UI Semibold", Font.PLAIN, 14));
        btnGorevliGiris.setBackground(renkPlum);
        btnGorevliGiris.setForeground(renkBeyaz);
        btnGorevliGiris.setOpaque(true);
        btnGorevliGiris.setBorderPainted(false);
        btnGorevliGiris.setBounds(72, 75, 215, 29);
        
        	btnGorevliGiris.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {

                    if(textField.getText().length() == 0 || passwordField_1.getText().length() == 0) {
                        helper.showMsg("fill");
                    } else {
                        Connection con = conn.connDb();
                        try {
                            Statement st = con.createStatement();
                            ResultSet rs = st.executeQuery("SELECT * FROM user");
                            
                            boolean girisBasarili = false;
                            
                            while(rs.next()) {
                            	
                                if(textField.getText().equals(rs.getString("username")) && 
                                   passwordField_1.getText().equals(rs.getString("userpassword"))) {
                                    
                                    if(rs.getString("usertype").equals("gorevli")) {
                                        
                                        Gorevli gorevli = new Gorevli();
                                        gorevli.setIduser(rs.getInt("userid"));
                                        gorevli.setNameSurname(rs.getString("name_surname"));
                                        gorevli.setUsername(rs.getString("username"));
                                        gorevli.setUserpassword(rs.getString("userpassword"));
                                        gorevli.setUsertype(rs.getString("usertype"));
                                        
                                        GorevliGUI gGUI = new GorevliGUI(gorevli);
                                        gGUI.setVisible(true);
                                        dispose();
                                        girisBasarili = true;
                                        break;
                                    }
                                }
                            }
                            
                            if(!girisBasarili) {
                                helper.showMsg("Kullanıcı bulunamadı veya yetkisiz giriş.");
                            }
                            
                        } catch (SQLException e1) {
                            e1.printStackTrace();
                        }
                    }
                }
            });
            gorevligirisi.add(btnGorevliGiris);
        
        
        
            JPanel teknisyengirisi = new JPanel();
            teknisyengirisi.setBackground(renkPorcelain);
            tabbedPane.addTab("Teknisyen Girişi", null, teknisyengirisi, null);
            teknisyengirisi.setLayout(null);
            
            JLabel lblTeknisyenKadi = new JLabel("Kullanıcı Adı:");
            lblTeknisyenKadi.setFont(new Font("Yu Gothic UI Semibold", Font.PLAIN, 12));
            lblTeknisyenKadi.setForeground(renkYazi);
            lblTeknisyenKadi.setBounds(72, 12, 85, 13);
            teknisyengirisi.add(lblTeknisyenKadi);
            
            JLabel lblTeknisyenSifre = new JLabel("Şifre:");
            lblTeknisyenSifre.setFont(new Font("Yu Gothic UI Semibold", Font.PLAIN, 13));
            lblTeknisyenSifre.setForeground(renkYazi);
            lblTeknisyenSifre.setBounds(72, 35, 85, 12);
            teknisyengirisi.add(lblTeknisyenSifre);
            
            textField_1 = new JTextField();
            textField_1.setText("Kullanıcı adı giriniz");
            textField_1.setFont(new Font("Yu Gothic UI Semilight", Font.ITALIC, 10));
            textField_1.setBounds(167, 9, 120, 20);
            textField_1.setBorder(javax.swing.BorderFactory.createLineBorder(renkCoral));
            teknisyengirisi.add(textField_1);
            
            passwordField_2 = new JPasswordField();
            passwordField_2.setFont(new Font("Yu Gothic UI Semilight", Font.ITALIC, 10));
            passwordField_2.setBounds(167, 32, 120, 20);
            passwordField_2.setBorder(javax.swing.BorderFactory.createLineBorder(renkCoral));
            teknisyengirisi.add(passwordField_2);
            
            JButton btnTeknisyenGiris = new JButton("Giriş Yap");
            btnTeknisyenGiris.setFont(new Font("Yu Gothic UI Semibold", Font.PLAIN, 14));
            btnTeknisyenGiris.setBackground(renkPlum);
            btnTeknisyenGiris.setForeground(renkBeyaz);
            btnTeknisyenGiris.setOpaque(true);
            btnTeknisyenGiris.setBorderPainted(false);
            btnTeknisyenGiris.setBounds(72, 75, 215, 29);
            
            btnTeknisyenGiris.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
 
                    if(textField_1.getText().length() == 0 || passwordField_2.getText().length() == 0) {
                        helper.showMsg("fill");
                    } else {
                        Connection con = conn.connDb();
                        try {
                            Statement st = con.createStatement();
                            ResultSet rs = st.executeQuery("SELECT * FROM user");
                            
                            boolean girisBasarili = false;
                            
                            while(rs.next()) {
                              
                                if(textField_1.getText().equals(rs.getString("username")) && 
                                   passwordField_2.getText().equals(rs.getString("userpassword"))) {
                                    
                                    if(rs.getString("usertype").equals("teknisyen")) {
                                        
                                        Teknisyen t = new Teknisyen();
                                        t.setIduser(rs.getInt("userid"));
                                        t.setNameSurname(rs.getString("name_surname"));
                                        t.setUsername(rs.getString("username"));
                                        t.setUsertype(rs.getString("usertype"));
                                        
                                        TeknisyenGUI tGUI = new TeknisyenGUI(t);
                                        tGUI.setVisible(true);
                                        dispose();
                                        girisBasarili = true;
                                        break;
                                    }
                                }
                            }
                            if(!girisBasarili) {
                                helper.showMsg("Kullanıcı bulunamadı veya yetkisiz giriş.");
                            }
                        } catch (SQLException e1) {
                            e1.printStackTrace();
                        }
                    }
                }
            });

            teknisyengirisi.add(btnTeknisyenGiris);

        JPanel mudurgirisi = new JPanel();
        mudurgirisi.setBackground(renkPorcelain);
        tabbedPane.addTab("Müdür Girişi", null, mudurgirisi, null);
        mudurgirisi.setLayout(null);
        
        JLabel lblMudurKadi = new JLabel("Kullanıcı Adı:");
        lblMudurKadi.setFont(new Font("Yu Gothic UI Semibold", Font.PLAIN, 12));
        lblMudurKadi.setForeground(renkYazi);
        lblMudurKadi.setBounds(72, 12, 85, 13);
        mudurgirisi.add(lblMudurKadi);
        
        JLabel lblMudurSifre = new JLabel("Şifre:");
        lblMudurSifre.setFont(new Font("Yu Gothic UI Semibold", Font.PLAIN, 13));
        lblMudurSifre.setForeground(renkYazi);
        lblMudurSifre.setBounds(72, 35, 85, 12);
        mudurgirisi.add(lblMudurSifre);
        
        textField_2 = new JTextField();
        textField_2.setText("Kullanıcı adı giriniz");
        textField_2.setFont(new Font("Yu Gothic UI Semilight", Font.ITALIC, 10));
        textField_2.setBounds(167, 9, 120, 20);
        textField_2.setBorder(javax.swing.BorderFactory.createLineBorder(renkCoral));
        mudurgirisi.add(textField_2);
        
        passwordField_3 = new JPasswordField();
        passwordField_3.setFont(new Font("Yu Gothic UI Semilight", Font.ITALIC, 10));
        passwordField_3.setBounds(167, 32, 120, 20);
        passwordField_3.setBorder(javax.swing.BorderFactory.createLineBorder(renkCoral));
        mudurgirisi.add(passwordField_3);
        
        JButton btnMudurGiris = new JButton("Giriş Yap");
        btnMudurGiris.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent e) {
        		if(textField_2.getText().length() == 0 || passwordField_3.getText().length() == 0) {
        			helper.showMsg("fill");
        			//JOptionPane.showMessageDialog(null, "Lütfen tüm alanları doldurunuz");
        		} else {
        			Connection con = conn.connDb();
        			try {
						Statement st = con.createStatement();
						ResultSet rs = st.executeQuery("SELECT * FROM user");
						while(rs.next()) {
							if(textField_2.getText().equals(rs.getString("username")) && passwordField_3.getText().equals(rs.getString("userpassword"))){
								Mudur mudur = new Mudur();
								
								mudur.setUsername(rs.getString("username"));
								mudur.setUserpassword("userpassword");
								mudur.setUsertype(rs.getString("usertype"));
								mudur.setIduser(rs.getInt("userid"));
								mudur.setNameSurname(rs.getString("name_surname"));
								MudurGUI mGUI = new MudurGUI(mudur);
								mGUI.setVisible(true);
								dispose();
								
        }
						}
					} catch (SQLException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
        		}
        		
        		
        	}
        });
        btnMudurGiris.setFont(new Font("Yu Gothic UI Semibold", Font.PLAIN, 14));
        btnMudurGiris.setBackground(renkPlum);
        btnMudurGiris.setForeground(renkBeyaz);
        btnMudurGiris.setOpaque(true);
        btnMudurGiris.setBorderPainted(false);
        btnMudurGiris.setBounds(72, 75, 215, 29);
        mudurgirisi.add(btnMudurGiris);
    }
}