package View;
import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;

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
import javax.swing.table.DefaultTableModel;

import Helper.helper;
import Model.Makine;
import Model.Ogrenci;
import Model.Randevu;

public class OgrenciGUI extends JFrame {

    private static final long serialVersionUID = 1L;
    private JPanel contentPane;
    
    static Ogrenci ogrenci = new Ogrenci();
    
    private DefaultTableModel makineModel;
    private Object[] makineData;
    private JTable table_makine;
    
    private DefaultTableModel randevuModel;
    private Object[] randevuData;
    
    private JTable table_randevu; 
    
    private JTextField fld_makineId;
    private JTextField fld_tarih;
    private JComboBox<String> cmb_saat;

    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    OgrenciGUI frame = new OgrenciGUI(new Ogrenci());
                    frame.setVisible(true);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    public OgrenciGUI(Ogrenci ogrenci) throws SQLException {
        this.ogrenci = ogrenci;
        
        Color renkArkaPlan = Color.decode("#F2EEE7");
        Color renkButon = Color.decode("#8E5953");
        Color renkBeyaz = Color.WHITE;
        
        setResizable(false);
        setTitle("Öğrenci Paneli");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100,100,750,500);
        contentPane = new JPanel();
        contentPane.setBackground(renkArkaPlan);
        contentPane.setBorder(new EmptyBorder(5,5,5,5));
        setContentPane(contentPane);
        contentPane.setLayout(null);
        
        JLabel lblbWelcome = new JLabel("Hoşgeldiniz: " + ogrenci.getNameSurname());
        lblbWelcome.setFont(new Font("Yu Gothic UI Semibold", Font.PLAIN, 16));
        lblbWelcome.setForeground(renkButon);
        lblbWelcome.setBounds(10,10,400,30);
        contentPane.add(lblbWelcome);
        
        JButton btnCikis = new JButton("Çıkış");
        btnCikis.addActionListener(e -> dispose());
        btnCikis.setBounds(630,15,90,25);
        btnCikis.setBackground(renkButon);
        btnCikis.setForeground(renkBeyaz);
        contentPane.add(btnCikis);
        
        JTabbedPane tabbedPane = new JTabbedPane(JTabbedPane.TOP);
        tabbedPane.setBounds(10,50,716,403);
        contentPane.add(tabbedPane);

        JPanel pnlRandevuAl = new JPanel();
        pnlRandevuAl.setBackground(Color.WHITE);
        pnlRandevuAl.setLayout(null);
        tabbedPane.addTab("Randevu Al",null, pnlRandevuAl , null);
        
        makineModel = new DefaultTableModel();
        makineModel.setColumnIdentifiers(new Object[] {"ID", "Makine Tipi", "Durum"});
        makineData = new Object[3];
        
        table_makine = new JTable(makineModel);
        JScrollPane scrollMakine = new JScrollPane(table_makine);
        scrollMakine.setBounds(10,10,400,350);
        pnlRandevuAl.add(scrollMakine);
        
        table_makine.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                int selRow = table_makine.getSelectedRow();

                if(selRow >= 0) {
                    fld_makineId.setText(makineModel.getValueAt(selRow, 0).toString());
                }
            }
        });

        JLabel lblMakineId = new JLabel("Seçilen Makine ID:");
        lblMakineId.setBounds(430, 20, 120, 14);
        pnlRandevuAl.add(lblMakineId);

        fld_makineId = new JTextField();
        fld_makineId.setEditable(false);
        fld_makineId.setBounds(430, 40, 100, 25);
        pnlRandevuAl.add(fld_makineId);

        JLabel lblTarih = new JLabel("Tarih:");
        lblTarih.setBounds(430, 80, 150, 14);
        pnlRandevuAl.add(lblTarih);
        
        SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yyyy");
        fld_tarih = new JTextField(sdf.format(new Date()));
        fld_tarih.setBounds(430,100,150,25);
        pnlRandevuAl.add(fld_tarih);
        
        JLabel lblSaat = new JLabel("Saat Aralığı: ");
        lblSaat.setBounds(430,140,100,14);
        pnlRandevuAl.add(lblSaat);
        
        String[] saatler = { "08:30 - 09:00", "09:30 - 10:00", "10:30 - 11:00", "11:30 - 12:00", 
                             "13:30 - 14:00", "14.30 - 15.30" , "15.30 - 16.00", "16.30 - 17.00", "17.30 - 18.00"};
        cmb_saat = new JComboBox<>(saatler);
        cmb_saat.setBackground(Color.WHITE);
        cmb_saat.setBounds(430,160,150,25);
        pnlRandevuAl.add(cmb_saat);
        
        JButton btnRandevuAl = new JButton("Randevu Oluştur");
        btnRandevuAl.setBackground(renkButon);
        btnRandevuAl.setForeground(renkBeyaz);
        btnRandevuAl.setBounds(430, 210, 180, 35);
        
        
        btnRandevuAl.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (fld_makineId.getText().length()==0) {
                    helper.showMsg("Lütfen tablodan bir makine seçiniz: "); 
                } else {
                    try {
                        int m_id =Integer.parseInt(fld_makineId.getText());
                        String tarih = fld_tarih.getText();
                        String saat = cmb_saat.getSelectedItem().toString();
                        
                        String cevap = ogrenci.addRandevu(m_id, ogrenci.getIduser(), tarih, saat);
                        
                        switch(cevap) {
                        case "success":
                            helper.showMsg("success");
                            updateRandevuModel();
                            updateMakineModel();
                            break;
                            
                        case "dolu":
                            helper.showMsg("Seçilen tarih ve saatte bu makine dolu. Lütfen yeni bir seçim yapınız");
                            break;
                            
                        case "limit_yikama":
                            helper.showMsg("Haftalık Yıkama Hakkınız Bitmiştir");
                            break;
                            
                        case "limit_kurutma":
                            helper.showMsg("Haftalık Kurutma Hakkınız Bitmiştir");
                            break;
                        
                        case "makine_arizali_veya_bakimda":
                            helper.showMsg("Seçtiğiniz makine şu an Arızalı veya Bakımda olduğu için işlem yapılamıyor.");
                            break;
                            
                        default:
                            helper.showMsg("error");
                            break;
                        }
                    }catch(SQLException e1) {
                        e1.printStackTrace();
                    }
                }
            }
        });
        pnlRandevuAl.add(btnRandevuAl);

        JPanel pnlRandevularim = new JPanel();
        pnlRandevularim.setBackground(Color.WHITE);
        pnlRandevularim.setLayout(null);
        tabbedPane.addTab("Randevularım", null, pnlRandevularim, null);

        randevuModel = new DefaultTableModel();
        randevuModel.setColumnIdentifiers(new Object[] {"Randevu ID", "Makine ID", "Tarih", "Saat"});
        randevuData = new Object[4];

        table_randevu = new JTable(randevuModel);
        JScrollPane scrollRandevu = new JScrollPane(table_randevu);
        scrollRandevu.setBounds(10, 10, 690, 300);
        pnlRandevularim.add(scrollRandevu);

        JButton btnRandevuSil = new JButton("Randevuyu İptal ET");
        btnRandevuSil.setBackground(Color.decode("#8E5953"));
        btnRandevuSil.setForeground(Color.WHITE);
        btnRandevuSil.setBounds(10, 320, 200, 30);
        
        btnRandevuSil.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                
                int selRow = table_randevu.getSelectedRow();

                if (selRow == -1) {
                    helper.showMsg("Lütfen iptal etmek istediğiniz randevuyu tablodan seçiniz.");
                } else {
                    if (helper.confirm("sure")) {
                        try {
                            int selectId = Integer.parseInt(table_randevu.getValueAt(selRow, 0).toString());

                            boolean control = ogrenci.deleteRandevu(selectId);
                            
                            if (control) {
                                helper.showMsg("success");

                                updateRandevuModel();
                                updateMakineModel();
                            } else {
                                helper.showMsg("error");
                            }
                        } catch (SQLException e1) {
                            e1.printStackTrace();
                        }
                    }
                }
            }
        });
        
        pnlRandevularim.add(btnRandevuSil);

        updateMakineModel();
        updateRandevuModel();
    }

    public void updateMakineModel() throws SQLException {
        DefaultTableModel clearModel = (DefaultTableModel) table_makine.getModel();
        clearModel.setRowCount(0);
        for (Makine obj : ogrenci.getMachineList()) {
            makineData[0] = obj.getId();
            makineData[1] = obj.getTip();
            makineData[2] = obj.getDurum();
            makineModel.addRow(makineData);
        }
    }

    public void updateRandevuModel() throws SQLException {
        DefaultTableModel clearModel = (DefaultTableModel) table_randevu.getModel();
        clearModel.setRowCount(0);
         
        for (Randevu obj : ogrenci.getRandevuList(ogrenci.getIduser())) {
            randevuData[0] = obj.getId();
            randevuData[1] = obj.getMakineId();
            randevuData[2] = obj.getTarih();
            randevuData[3] = obj.getSaat();
            randevuModel.addRow(randevuData);
        }
    }
}