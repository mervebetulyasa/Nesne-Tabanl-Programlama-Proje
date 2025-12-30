package Model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import Helper.DBConnection;

public class Ogrenci extends User {
    
    DBConnection conn = new DBConnection();
    Connection con = null;
    Statement st = null;
    ResultSet rs = null;
    PreparedStatement preparedStatement = null;

   
    public Ogrenci() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Ogrenci(int iduser, String nameSurname, String username, String userpassword, String usertype) {
		super(iduser, nameSurname, username, userpassword, usertype);
		// TODO Auto-generated constructor stub
	}

	public ArrayList<Makine> getMachineList() throws SQLException {
        ArrayList<Makine> list = new ArrayList<>();
        Makine obj;
        try {
            con = conn.connDb();
            st = con.createStatement();
            rs = st.executeQuery("SELECT * FROM makineler");
            while (rs.next()) {
                obj = new Makine();
                obj.setId(rs.getInt("id_makine"));
                obj.setTip(rs.getString("makine_tip"));
                obj.setDurum(rs.getString("makine_durum"));
                list.add(obj);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    public String addRandevu(int makineId, int ogrenciId, String tarih, String saat) throws SQLException {
        String sonuc = "hata";
        
        try {
            con = conn.connDb();
            String makineTipi = "";
            String makineDurumu = ""; 

            String typeQuery = "SELECT makine_tip, makine_durum FROM makineler WHERE id_makine = ?";
            preparedStatement = con.prepareStatement(typeQuery);
            preparedStatement.setInt(1, makineId);
            rs = preparedStatement.executeQuery();
            
            if(rs.next()) {
                makineTipi = rs.getString("makine_tip");
                makineDurumu = rs.getString("makine_durum"); // Durumu aldık
            }

            if (!makineDurumu.equalsIgnoreCase("musait")) {
                return "makine_arizali_veya_bakimda"; 
            }

            String countQuery = "SELECT COUNT(*) as sayi FROM randevu r " + 
                                "LEFT JOIN makineler m ON r.makine_id = m.id_makine " + 
                                "WHERE r.ogrenci_id = ? " + 
                                "AND m.makine_tip = ? " + 
                                "AND r.aktif = 1 " + 
                                "AND YEARWEEK(STR_TO_DATE(r.tarih, '%d.%m.%Y'), 1) = YEARWEEK(STR_TO_DATE(?, '%d.%m.%Y'), 1)";
            
            preparedStatement = con.prepareStatement(countQuery);
            preparedStatement.setInt(1, ogrenciId);
            preparedStatement.setString(2, makineTipi);
            preparedStatement.setString(3, tarih);
            
            rs = preparedStatement.executeQuery();
            int mevcutRandevuSayisi = 0;
            
            if(rs.next()) {
                mevcutRandevuSayisi = rs.getInt("sayi");
            }

            if(makineTipi.contains("Yıkama") || makineTipi.contains("camasir") || makineTipi.contains("Yikama")) {
                if(mevcutRandevuSayisi >= 2) {
                    return "limit_yikama";
                }
            } else if (makineTipi.contains("kurutma")) {
                if(mevcutRandevuSayisi >= 1) {
                    return "limit_kurutma";
                }
            }

            String queryCheck = "SELECT * FROM randevu WHERE makine_id = ? AND tarih = ? AND saat = ?";
            preparedStatement = con.prepareStatement(queryCheck);
            preparedStatement.setInt(1, makineId);
            preparedStatement.setString(2, tarih);
            preparedStatement.setString(3, saat);
            ResultSet rsCheck = preparedStatement.executeQuery();
            
            if(rsCheck.next()) {
                return "dolu"; 
            }

            String queryInsert = "INSERT INTO randevu (makine_id, ogrenci_id, tarih, saat, aktif) VALUES (?, ?, ?, ?, ?)";
            preparedStatement = con.prepareStatement(queryInsert);
            preparedStatement.setInt(1, makineId);
            preparedStatement.setInt(2, ogrenciId);
            preparedStatement.setString(3, tarih);
            preparedStatement.setString(4, saat);
            preparedStatement.setInt(5, 1);
            
            preparedStatement.executeUpdate();
            
            String queryUpdate = "UPDATE makineler SET makine_durum = 'Dolu', kullanim_sayisi = kullanim_sayisi + 1 WHERE id_makine = ?";
            preparedStatement = con.prepareStatement(queryUpdate);
            preparedStatement.setInt(1, makineId);
            preparedStatement.executeUpdate();
            
            sonuc = "success";
            
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return sonuc;
    }

    public ArrayList<Randevu> getRandevuList(int ogrenciId) throws SQLException{
        ArrayList<Randevu> list = new ArrayList<>();
        Randevu obj;
        try {
            con = conn.connDb();
            String sql = "SELECT * FROM randevu WHERE aktif = 1 AND ogrenci_id = ?";
            preparedStatement = con.prepareStatement(sql);
            preparedStatement.setInt(1, ogrenciId);
            rs = preparedStatement.executeQuery();
            
            while(rs.next()) {
                obj = new Randevu();
                obj.setId(rs.getInt("id"));
                obj.setOgrenciId(rs.getInt("ogrenci_id"));
                obj.setMakineId(rs.getInt("makine_id"));
                obj.setTarih(rs.getString("tarih"));
                obj.setSaat(rs.getString("saat"));
                obj.setAktif(rs.getInt("aktif"));
                list.add(obj);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    public boolean deleteRandevu(int randevuId) throws SQLException{
        boolean key = false;
        int makineId = 0;
        
        try {
            con = conn.connDb();

            String findQuery = "SELECT makine_id FROM randevu WHERE id = ?";
            preparedStatement = con.prepareStatement(findQuery);
            preparedStatement.setInt(1, randevuId);
            ResultSet rsFind = preparedStatement.executeQuery();
            
            if(rsFind.next()) {
                makineId = rsFind.getInt("makine_id");
            }

            String deleteQuery = "DELETE FROM randevu WHERE id = ?";
            preparedStatement = con.prepareStatement(deleteQuery);
            preparedStatement.setInt(1, randevuId);
            preparedStatement.executeUpdate();

            if(makineId > 0) {
            	
                String updateQuery = "UPDATE makineler SET makine_durum = 'Musait' WHERE id_makine = ?";
                preparedStatement = con.prepareStatement(updateQuery);
                preparedStatement.setInt(1, makineId);
                preparedStatement.executeUpdate();
            }
            key = true;
            
        } catch(SQLException e) {
            e.printStackTrace();
        }
        return key;
    }
}