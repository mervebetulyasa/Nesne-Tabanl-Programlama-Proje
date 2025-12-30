package View;
import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.SQLException;
import java.util.ArrayList;

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
import Model.Gorevli;
import Model.Makine;
import Model.Randevu;

public class GorevliGUI extends JFrame {

    private static final long serialVersionUID = 1L;
    private JPanel contentPane;
    static Gorevli gorevli = new Gorevli();
    
    private DefaultTableModel makineModel;
    private Object[] makineData;
    private JTable table_makine;
    
    private DefaultTableModel randevuModel;
    private Object[] randevuData;
    private JTable table_randevu;
    
    private ArrayList<Randevu> randevuListesi = null;
    
    private JTextField fld_id;
    private JComboBox<String> cmb_durum;

    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    GorevliGUI frame = new GorevliGUI(new Gorevli());
                    frame.setVisible(true);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    public GorevliGUI(Gorevli gorevli) throws SQLException {
        this.gorevli = gorevli;
        
        Color renkArkaPlan = Color.decode("#F2EEE7");
        Color renkButon = Color.decode("#8E5953");
        Color renkBeyaz = Color.WHITE;
        
        setResizable(false);
        setTitle("Görevli Paneli");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100, 100, 750, 500);
        contentPane = new JPanel();
        contentPane.setBackground(renkArkaPlan);
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        setContentPane(contentPane);
        contentPane.setLayout(null);
        
        JLabel lblWelcome = new JLabel("Hoşgeldiniz: " + gorevli.getNameSurname());
        lblWelcome.setFont(new Font("Yu Gothic UI Semibold", Font.PLAIN, 16));
        lblWelcome.setForeground(renkButon);
        lblWelcome.setBounds(10, 10, 400, 30);
        contentPane.add(lblWelcome);
        
        JButton btnCikis = new JButton("Çıkış");
        btnCikis.addActionListener(e -> dispose());
        btnCikis.setBounds(630, 15, 90, 25);
        btnCikis.setBackground(renkButon);
        btnCikis.setForeground(renkBeyaz);
        contentPane.add(btnCikis);
        
        JTabbedPane tabbedPane = new JTabbedPane(JTabbedPane.TOP);
        tabbedPane.setBounds(10, 50, 716, 400);
        contentPane.add(tabbedPane);
        
        JPanel pnlMakine = new JPanel();
        pnlMakine.setBackground(Color.WHITE);
        pnlMakine.setLayout(null);
        tabbedPane.addTab("Makine Durumları", null, pnlMakine, null);
        
        makineModel = new DefaultTableModel();
        makineModel.setColumnIdentifiers(new Object[] {"ID", "Makine Tipi", "Durum"});
        makineData = new Object[3];
        
        table_makine = new JTable(makineModel);
        table_makine.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                int selRow = table_makine.getSelectedRow();
                if (selRow >= 0) {
                    fld_id.setText(makineModel.getValueAt(selRow, 0).toString());
                    cmb_durum.setSelectedItem(makineModel.getValueAt(selRow, 2).toString());
                }
            }
        });

        JScrollPane scrollMakine = new JScrollPane(table_makine);
        scrollMakine.setBounds(10, 20, 450, 320);
        pnlMakine.add(scrollMakine);
        
        JLabel lblId = new JLabel("Seçilen ID:");
        lblId.setBounds(480, 50, 100, 20);
        pnlMakine.add(lblId);
        
        fld_id = new JTextField();
        fld_id.setEditable(false);
        fld_id.setBounds(480, 75, 200, 30);
        pnlMakine.add(fld_id);
        
        JLabel lblDurum = new JLabel("Yeni Durum:");
        lblDurum.setBounds(480, 120, 100, 20);
        pnlMakine.add(lblDurum);
        
        String[] durumlar = {"Musait", "Arizali"};
        cmb_durum = new JComboBox<>(durumlar);
        cmb_durum.setBackground(Color.WHITE);
        cmb_durum.setBounds(480, 145, 200, 30);
        pnlMakine.add(cmb_durum);
        
        JButton btnGuncelle = new JButton("DURUMU GÜNCELLE");
        btnGuncelle.setBackground(renkButon);
        btnGuncelle.setForeground(renkBeyaz);
        btnGuncelle.setBounds(480, 200, 200, 40);
        btnGuncelle.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if(fld_id.getText().length() == 0) {
                    helper.showMsg("Lütfen makine seçiniz.");
                } else {
                    try {
                        int id = Integer.parseInt(fld_id.getText());
                        String yeniDurum = cmb_durum.getSelectedItem().toString();
                        if(gorevli.updateMakineDurum(id, yeniDurum)) {
                            helper.showMsg("success");
                            updateMakineModel();
                        }
                    } catch (SQLException e1) {
                        e1.printStackTrace();
                    }
                }
            }
        });
        pnlMakine.add(btnGuncelle);
        
        JPanel pnlRandevu = new JPanel();
        pnlRandevu.setBackground(Color.WHITE);
        pnlRandevu.setLayout(null);
        tabbedPane.addTab("Tüm Randevular", null, pnlRandevu, null);
        
        randevuModel = new DefaultTableModel();
        randevuModel.setColumnIdentifiers(new Object[] {"Öğrenci ID", "Makine ID", "Tarih", "Saat"});
        randevuData = new Object[4];
        
        table_randevu = new JTable(randevuModel);
        JScrollPane scrollRandevu = new JScrollPane(table_randevu);
        scrollRandevu.setBounds(10, 10, 690, 300);
        pnlRandevu.add(scrollRandevu);
        
        JButton btnRandevuIptal = new JButton("Seçili Randevuyu İptal Et");
        btnRandevuIptal.setBackground(renkButon);
        btnRandevuIptal.setForeground(renkBeyaz);
        btnRandevuIptal.setBounds(10, 320, 250, 30);
        
        btnRandevuIptal.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                int selRow = table_randevu.getSelectedRow();
                
                if(selRow == -1) {
                    helper.showMsg("Lütfen iptal edilecek randevuyu seçiniz.");
                } else {
                    if(helper.confirm("sure")) {
                        try {
                            int r_id = randevuListesi.get(selRow).getId();
                            int m_id = Integer.parseInt(table_randevu.getValueAt(selRow, 1).toString());
                            
                            boolean control = gorevli.deleteRandevu(r_id, m_id);
                            
                            if(control) {
                                helper.showMsg("success");
                                updateRandevuModel(); 
                                updateMakineModel(); 
                            } else {
                                helper.showMsg("error");
                            }
                        } catch (SQLException e1) {
                            e1.printStackTrace();
                        } catch (Exception ex) {
                                ex.printStackTrace(); 
                        }
                    }
                }
            }
        });
        pnlRandevu.add(btnRandevuIptal);

        updateMakineModel();
        updateRandevuModel();
    }
    
    public void updateMakineModel() throws SQLException {
        DefaultTableModel clearModel = (DefaultTableModel) table_makine.getModel();
        clearModel.setRowCount(0);
        for (Makine obj : gorevli.getMakineList()) {
            makineData[0] = obj.getId();
            makineData[1] = obj.getTip();
            makineData[2] = obj.getDurum();
            makineModel.addRow(makineData);
        }
    }
    
    public void updateRandevuModel() throws SQLException {
        DefaultTableModel clearModel = (DefaultTableModel) table_randevu.getModel();
        clearModel.setRowCount(0);
        randevuListesi = gorevli.getAllRandevuList();
        for (Randevu obj : randevuListesi) {
            randevuData[0] = obj.getOgrenciId();
            randevuData[1] = obj.getMakineId();
            randevuData[2] = obj.getTarih();
            randevuData[3] = obj.getSaat();
            randevuModel.addRow(randevuData);
        }
    }
}