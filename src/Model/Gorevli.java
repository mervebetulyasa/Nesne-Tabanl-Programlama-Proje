package Model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import Helper.DBConnection;

public class Gorevli extends User {
	
	public Gorevli() {
		super();
		// TODO Auto-generated constructor stub
	}
	public Gorevli(int iduser, String nameSurname, String username, String userpassword, String usertype) {
		super(iduser, nameSurname, username, userpassword, usertype);
		// TODO Auto-generated constructor stub
	}
	
	DBConnection conn = new DBConnection();
	Connection con = null;
	Statement st = null;
	ResultSet rs = null;
	PreparedStatement preparedStatement = null; 
	
	
	public ArrayList<Makine> getMakineList() throws SQLException {
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
	
	public boolean updateMakineDurum(int id, String yeniDurum) throws SQLException {
		String query = "UPDATE makineler SET makine_durum = ? WHERE id_makine =?";
		boolean key = false;
		try {
			con = conn.connDb();
			preparedStatement = con.prepareStatement(query);
            preparedStatement.setString(1, yeniDurum);
            preparedStatement.setInt(2, id);
            preparedStatement.executeUpdate();
            key = true;
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return key;
	}
	
	public ArrayList<Randevu> getAllRandevuList() throws SQLException {
		ArrayList<Randevu> list = new ArrayList<>();
		Randevu obj;
		try {
			con = conn.connDb();
			st = con.createStatement();
			rs = st.executeQuery("SELECT * FROM randevu WHERE aktif = 1");
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
	
	public boolean deleteRandevu(int id,int makineId) throws SQLException {
		String query = "DELETE FROM randevu WHERE id = ?";
		boolean key = false;
		try {
			con = conn.connDb();
			preparedStatement = con.prepareStatement(query);
            preparedStatement.setInt(1, id);
            preparedStatement.executeUpdate();
            
            String query2 = "UPDATE makineler SET makine_durum = 'Musait' WHERE id_makine = ?";
            preparedStatement = con.prepareStatement(query2);
            preparedStatement.setInt(1, makineId);
            preparedStatement.executeUpdate();
            key = true;
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return key;
	}
	
	

}
