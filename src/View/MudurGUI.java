package View;


import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.SQLException;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;
import javax.swing.border.TitledBorder;
import javax.swing.table.DefaultTableModel;

import Helper.helper;
import Model.Makine;
import Model.Mudur;

public class MudurGUI extends JFrame {

    private static final long serialVersionUID = 1L;
    private JPanel contentPane;

    static Mudur mudur = new Mudur();

    private DefaultTableModel userModel = null;
    private Object[] userData = null;
    private JTable table_user;
    private JTextField fld_userName; 
    private JTextField fld_userKadi; 
    private JTextField fld_userPass; 
    private JTextField fld_userId;   
    private JComboBox<String> cmb_userType; 

    private DefaultTableModel machineModel = null;
    private Object[] machineData = null;
    private JTable table_machine;
    private JTextField fld_machineType;
    private JTextField fld_machineId;
    private JLabel lblStat; 

    /**
     * Main Metodu
     */
    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    MudurGUI frame = new MudurGUI(new Mudur());
                    frame.setVisible(true);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    /**
     * Constructor
     */
    public MudurGUI(Mudur mudur) throws SQLException {
        this.mudur = mudur;


        Color renkSage = new Color(157, 170, 133);
        Color renkPorcelain = Color.decode("#F2EEE7");
        Color renkPlum = Color.decode("#8E5953");
        Color renkBeyaz = Color.WHITE;

        userModel = new DefaultTableModel();
        Object[] colUser = new Object[5];
        colUser[0] = "ID";
        colUser[1] = "Ad Soyad";
        colUser[2] = "Kullanıcı Adı";
        colUser[3] = "Şifre";
        colUser[4] = "Rol"; 
        userModel.setColumnIdentifiers(colUser);
        userData = new Object[5];

        machineModel = new DefaultTableModel();
        Object[] colMachine = new Object[3];
        colMachine[0] = "ID";
        colMachine[1] = "Türü";
        colMachine[2] = "Durum";
        machineModel.setColumnIdentifiers(colMachine);
        machineData = new Object[3];

        setTitle("Yurt Yönetim Paneli - Müdür");
        setResizable(false);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100, 100, 750, 520); 

        contentPane = new JPanel();
        contentPane.setBackground(renkPorcelain);
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        setContentPane(contentPane);
        contentPane.setLayout(null);

        JLabel lblWelcome = new JLabel("Hoşgeldiniz, Sayın " + mudur.getNameSurname());
        lblWelcome.setFont(new Font("Yu Gothic UI Semibold", Font.PLAIN, 16));
        lblWelcome.setForeground(renkPlum);
        lblWelcome.setBounds(10, 10, 300, 30);
        contentPane.add(lblWelcome);

        JButton btnCikis = new JButton("Çıkış Yap");
        btnCikis.setFont(new Font("Yu Gothic UI Semibold", Font.PLAIN, 11));
        btnCikis.setBackground(renkPlum);
        btnCikis.setForeground(renkBeyaz);
        btnCikis.setBounds(610, 15, 110, 25);
        btnCikis.addActionListener(e -> dispose());
        contentPane.add(btnCikis);

        JTabbedPane tabbedPane = new JTabbedPane(JTabbedPane.TOP);
        tabbedPane.setBounds(10, 50, 716, 420);
        contentPane.add(tabbedPane);

        JPanel panelUser = new JPanel();
        panelUser.setBackground(Color.WHITE);
        panelUser.setLayout(null);
        tabbedPane.addTab("Kullanıcı Yönetimi", null, panelUser, null);

        JScrollPane scrollUser = new JScrollPane();
        scrollUser.setBounds(10, 10, 500, 360);
        panelUser.add(scrollUser);

        table_user = new JTable(userModel);
        scrollUser.setViewportView(table_user);

        table_user.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                try {
                    int selectedRow = table_user.getSelectedRow();
                    fld_userId.setText(userModel.getValueAt(selectedRow, 0).toString());
                    fld_userName.setText(userModel.getValueAt(selectedRow, 1).toString());
                    fld_userKadi.setText(userModel.getValueAt(selectedRow, 2).toString());
                    fld_userPass.setText(userModel.getValueAt(selectedRow, 3).toString());
                    cmb_userType.setSelectedItem(userModel.getValueAt(selectedRow, 4).toString());
                } catch (Exception ex) {}
            }
        });

        JLabel lblAdSoyad = new JLabel("Ad Soyad :");
        lblAdSoyad.setFont(new Font("Yu Gothic UI Semibold", Font.PLAIN, 12));
        lblAdSoyad.setBounds(530, 15, 150, 14);
        panelUser.add(lblAdSoyad);

        fld_userName = new JTextField();
        fld_userName.setBounds(530, 35, 160, 25);
        panelUser.add(fld_userName);

        JLabel lblKadi = new JLabel("Kullanıcı Adı:");
        lblKadi.setFont(new Font("Yu Gothic UI Semibold", Font.PLAIN, 12));
        lblKadi.setBounds(530, 70, 100, 14);
        panelUser.add(lblKadi);

        fld_userKadi = new JTextField();
        fld_userKadi.setBounds(530, 90, 160, 25);
        panelUser.add(fld_userKadi);

        JLabel lblSifre = new JLabel("Şifre:");
        lblSifre.setFont(new Font("Yu Gothic UI Semibold", Font.PLAIN, 12));
        lblSifre.setBounds(530, 125, 100, 14);
        panelUser.add(lblSifre);

        fld_userPass = new JTextField();
        fld_userPass.setBounds(530, 145, 160, 25);
        panelUser.add(fld_userPass);
        
        JLabel lblRol = new JLabel("Kullanıcı Rolü:");
        lblRol.setFont(new Font("Yu Gothic UI Semibold", Font.PLAIN, 12));
        lblRol.setBounds(530, 180, 100, 14);
        panelUser.add(lblRol);
        
        String[] roller = {"ogrenci", "teknisyen", "gorevli"};
        cmb_userType = new JComboBox<>(roller);
        cmb_userType.setBounds(530, 200, 160, 25);
        cmb_userType.setBackground(Color.WHITE);
        panelUser.add(cmb_userType);

        JButton btnUserEkle = new JButton("EKLE");
        btnUserEkle.setBackground(renkSage);
        btnUserEkle.setForeground(renkBeyaz);
        btnUserEkle.setBounds(530, 240, 160, 30);
        btnUserEkle.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (fld_userKadi.getText().length() == 0 || fld_userPass.getText().length() == 0 || fld_userName.getText().length() == 0) {
                    helper.showMsg("fill");
                } else {
                    try {
                        String secilenRol = cmb_userType.getSelectedItem().toString();
                        
                        boolean control = mudur.addUser(fld_userName.getText(), fld_userKadi.getText(), fld_userPass.getText(), secilenRol);
                        
                        if (control) {
                            helper.showMsg("success");
                            fld_userKadi.setText("");
                            fld_userPass.setText("");
                            fld_userName.setText("");
                            updateUserModel(); 
                        }
                    } catch (SQLException e1) {
                         if (e1.getErrorCode() == 1062) {
                            helper.showMsg("Bu kullanıcı adı zaten kullanılıyor!");
                         } else {
                            e1.printStackTrace();
                         }
                    }
                }
            }
        });
        panelUser.add(btnUserEkle);

        JLabel lblDelId = new JLabel("Seçili ID:");
        lblDelId.setBounds(530, 300, 60, 14);
        panelUser.add(lblDelId);

        fld_userId = new JTextField();
        fld_userId.setEditable(false); 
        fld_userId.setBounds(590, 295, 100, 25);
        panelUser.add(fld_userId);

        JButton btnUserSil = new JButton("SİL");
        btnUserSil.setBackground(renkPlum);
        btnUserSil.setForeground(renkBeyaz);
        btnUserSil.setBounds(530, 330, 160, 30);
        btnUserSil.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (fld_userId.getText().length() == 0) {
                    helper.showMsg("Lütfen tablodan bir satır seçiniz.");
                } else {
                    if (helper.confirm("sure")) {
                        try {
                            boolean control = mudur.deleteUser(Integer.parseInt(fld_userId.getText()));
                            if (control) {
                                helper.showMsg("success");
                                fld_userId.setText("");
                                updateUserModel();
                            }
                        } catch (SQLException e1) {
                            e1.printStackTrace();
                        }
                    }
                }
            }
        });
        panelUser.add(btnUserSil);

        JPanel panelMachine = new JPanel();
        panelMachine.setBackground(Color.WHITE);
        panelMachine.setLayout(null);
        tabbedPane.addTab("Makine Yönetimi", null, panelMachine, null);

        JScrollPane scrollMachine = new JScrollPane();
        scrollMachine.setBounds(10, 10, 500, 300);
        panelMachine.add(scrollMachine);

        table_machine = new JTable(machineModel);
        scrollMachine.setViewportView(table_machine);

        table_machine.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                try {
                    int selectedRow = table_machine.getSelectedRow();
                    fld_machineId.setText(machineModel.getValueAt(selectedRow, 0).toString());
                } catch (Exception ex) {}
            }
        });

        JLabel lblMakineTuru = new JLabel("Türü (Yıkama/Kurutma):");
        lblMakineTuru.setFont(new Font("Yu Gothic UI Semibold", Font.PLAIN, 12));
        lblMakineTuru.setBounds(530, 35, 140, 14);
        panelMachine.add(lblMakineTuru);

        fld_machineType = new JTextField();
        fld_machineType.setBounds(530, 55, 160, 25);
        panelMachine.add(fld_machineType);

        JButton btnMakineEkle = new JButton("MAKİNE EKLE");
        btnMakineEkle.setBackground(renkSage);
        btnMakineEkle.setForeground(renkBeyaz);
        btnMakineEkle.setBounds(530, 95, 160, 30);
        btnMakineEkle.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (fld_machineType.getText().length() == 0) {
                    helper.showMsg("fill");
                } else {
                    try {
                        boolean control = mudur.addMakine(fld_machineType.getText());
                        if (control) {
                            helper.showMsg("success");
                            fld_machineType.setText("");
                            updateMachineModel();
                        }
                    } catch (SQLException e1) {
                        e1.printStackTrace();
                    }
                }
            }
        });
        panelMachine.add(btnMakineEkle);

        JLabel lblMakineId = new JLabel("Seçili ID:");
        lblMakineId.setBounds(530, 180, 60, 14);
        panelMachine.add(lblMakineId);

        fld_machineId = new JTextField();
        fld_machineId.setEditable(false);
        fld_machineId.setBounds(590, 175, 100, 25);
        panelMachine.add(fld_machineId);

        JButton btnMakineSil = new JButton("SİL / HURDA");
        btnMakineSil.setBackground(renkPlum);
        btnMakineSil.setForeground(renkBeyaz);
        btnMakineSil.setBounds(530, 210, 160, 30);
        btnMakineSil.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (fld_machineId.getText().length() == 0) {
                    helper.showMsg("Seçim yapınız");
                } else {
                    if (helper.confirm("sure")) {
                        try {
                            boolean control = mudur.deleteMakine(Integer.parseInt(fld_machineId.getText()));
                            if (control) {
                                helper.showMsg("success");
                                fld_machineId.setText("");
                                updateMachineModel();
                            }
                        } catch (SQLException e1) {
                            e1.printStackTrace();
                        }
                    }
                }
            }
        });
        panelMachine.add(btnMakineSil);

        JPanel pnlIstatistik = new JPanel();
        pnlIstatistik.setBounds(10, 320, 500, 45);
        pnlIstatistik.setBackground(renkPorcelain);
        pnlIstatistik.setBorder(new TitledBorder(null, "Günün İstatistiği", TitledBorder.LEADING, TitledBorder.TOP, null, null));
        panelMachine.add(pnlIstatistik);

        lblStat = new JLabel("Veri Yükleniyor...");
        lblStat.setFont(new Font("Yu Gothic UI Semibold", Font.PLAIN, 14));
        lblStat.setForeground(renkPlum);
        pnlIstatistik.add(lblStat);

        updateUserModel();
        updateMachineModel();
    }

    public void updateUserModel() throws SQLException {
        DefaultTableModel clearModel = (DefaultTableModel) table_user.getModel();
        clearModel.setRowCount(0); 
        
        for (int i = 0; i < mudur.getUserList().size(); i++) {
            userData[0] = mudur.getUserList().get(i).getIduser();
            userData[1] = mudur.getUserList().get(i).getNameSurname(); 
            userData[2] = mudur.getUserList().get(i).getUsername();
            userData[3] = mudur.getUserList().get(i).getUserpassword();
            userData[4] = mudur.getUserList().get(i).getUsertype(); 
            userModel.addRow(userData);
        }
    }

    public void updateMachineModel() throws SQLException {
        DefaultTableModel clearModel = (DefaultTableModel) table_machine.getModel();
        clearModel.setRowCount(0);
        
        for (Makine m : mudur.getMachineList()) {
            machineData[0] = m.getId();
            machineData[1] = m.getTip();
            machineData[2] = m.getDurum();
            machineModel.addRow(machineData);
        }
        try {
            lblStat.setText("En Çok Çalışan: " + mudur.getMostUsed());
        } catch (Exception e) {
            lblStat.setText("İstatistik alınamadı");
        }
    }
}