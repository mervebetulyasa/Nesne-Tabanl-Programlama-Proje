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
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;

import Helper.helper;
import Model.Makine;
import Model.Teknisyen;

public class TeknisyenGUI extends JFrame {

    private static final long serialVersionUID = 1L;
    private JPanel contentPane;
    static Teknisyen teknisyen = new Teknisyen();
    
    private DefaultTableModel makineModel;
    private Object[] makineData;
    private JTable table_makine;
    
    private JTextField fld_id;
    private JComboBox<String> cmb_islem;

    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    TeknisyenGUI frame = new TeknisyenGUI(new Teknisyen());
                    frame.setVisible(true);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    public TeknisyenGUI(Teknisyen teknisyen) throws SQLException {
        this.teknisyen = teknisyen;
        
        Color renkArkaPlan = Color.decode("#F2EEE7");
        Color renkButon = Color.decode("#8E5953");
        Color renkBeyaz = Color.WHITE;
        
        setResizable(false);
        setTitle("Teknisyen Paneli");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100, 100, 700, 450);
        contentPane = new JPanel();
        contentPane.setBackground(renkArkaPlan);
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        setContentPane(contentPane);
        contentPane.setLayout(null);
        
        JLabel lblWelcome = new JLabel("Hoşgeldiniz: " + teknisyen.getNameSurname());
        lblWelcome.setFont(new Font("Yu Gothic UI Semibold", Font.PLAIN, 16));
        lblWelcome.setForeground(renkButon);
        lblWelcome.setBounds(10, 10, 400, 30);
        contentPane.add(lblWelcome);
        
        JButton btnCikis = new JButton("Çıkış");
        btnCikis.addActionListener(e -> dispose());
        btnCikis.setBounds(580, 15, 90, 25);
        btnCikis.setBackground(renkButon);
        btnCikis.setForeground(renkBeyaz);
        contentPane.add(btnCikis);
        
        JLabel lblBaslik = new JLabel("Arızalı Makineler Listesi");
        lblBaslik.setFont(new Font("Yu Gothic UI Semibold", Font.PLAIN, 14));
        lblBaslik.setBounds(10, 50, 200, 20);
        contentPane.add(lblBaslik);
        
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
                }
            }
        });

        JScrollPane scrollPane = new JScrollPane(table_makine);
        scrollPane.setBounds(10, 80, 400, 300);
        contentPane.add(scrollPane);
        
        JLabel lblSecilen = new JLabel("Seçilen Makine ID:");
        lblSecilen.setBounds(430, 80, 120, 20);
        contentPane.add(lblSecilen);
        
        fld_id = new JTextField();
        fld_id.setEditable(false);
        fld_id.setBounds(430, 105, 100, 30);
        contentPane.add(fld_id);
        
        JLabel lblIslem = new JLabel("Yapılacak İşlem:");
        lblIslem.setBounds(430, 150, 120, 20);
        contentPane.add(lblIslem);
        
        String[] islemler = {"Musait", "Bakimda", "Hurda"};
        cmb_islem = new JComboBox<>(islemler);
        cmb_islem.setBackground(Color.WHITE);
        cmb_islem.setBounds(430, 175, 200, 30);
        contentPane.add(cmb_islem);
        
        JButton btnUygula = new JButton("Durumu Güncelle");
        btnUygula.setBackground(renkButon);
        btnUygula.setForeground(renkBeyaz);
        btnUygula.setBounds(430, 230, 200, 40);
        
        btnUygula.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if(fld_id.getText().length() == 0) {
                    helper.showMsg("Lütfen arızalı bir makine seçiniz.");
                } else {
                    if(helper.confirm("sure")) {
                        try {
                            int id = Integer.parseInt(fld_id.getText());
                            String yeniDurum = cmb_islem.getSelectedItem().toString();
                            
                            if(teknisyen.updateMachineStatus(id, yeniDurum)) {
                                helper.showMsg("success");
                                updateMakineModel(); 
                                fld_id.setText("");
                            }
                        } catch (SQLException e1) {
                            e1.printStackTrace();
                        }
                    }
                }
            }
        });
        contentPane.add(btnUygula);
        
        updateMakineModel();
    }
    
    public void updateMakineModel() throws SQLException {
        DefaultTableModel clearModel = (DefaultTableModel) table_makine.getModel();
        clearModel.setRowCount(0);

        for (Makine obj : teknisyen.getBrokenMachineList()) {
            makineData[0] = obj.getId();
            makineData[1] = obj.getTip();
            makineData[2] = obj.getDurum();
            makineModel.addRow(makineData);
        }
    }
}