package Model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import Helper.DBConnection;

public class Teknisyen extends User {
    
    DBConnection conn = new DBConnection();
    Connection con = null;
    Statement st = null;
    ResultSet rs = null;
    PreparedStatement preparedStatement = null;
    
    
    public Teknisyen() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Teknisyen(int iduser, String nameSurname, String username, String userpassword, String usertype) {
		super(iduser, nameSurname, username, userpassword, usertype);
		// TODO Auto-generated constructor stub
	}

	public ArrayList<Makine> getBrokenMachineList() throws SQLException {
        ArrayList<Makine> list = new ArrayList<>();
        Makine obj;
        try {
            con = conn.connDb();
            st = con.createStatement();
            rs = st.executeQuery("SELECT * FROM makineler WHERE makine_durum = 'Arizali'");
            
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

    public boolean updateMachineStatus(int id, String yeniDurum) throws SQLException {
        String query = "UPDATE makineler SET makine_durum = ? WHERE id_makine = ?";
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
}