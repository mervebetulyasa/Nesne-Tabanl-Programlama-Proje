package Model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import Helper.DBConnection;

public class Mudur extends User {

    DBConnection conn = new DBConnection();
    Connection con = null;
    Statement st = null;
    ResultSet rs = null;
    PreparedStatement preparedStatement = null;

   

    public Mudur() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Mudur(int iduser, String nameSurname, String username, String userpassword, String usertype) {
		super(iduser, nameSurname, username, userpassword, usertype);
		// TODO Auto-generated constructor stub
	}

	public ArrayList<User> getUserList() throws SQLException {
        ArrayList<User> list = new ArrayList<>();
        User obj = null;
        try {
            con = conn.connDb();
            st = con.createStatement();
            rs = st.executeQuery("SELECT * FROM user");

            while (rs.next()) {
                obj = null;
                String tip = rs.getString("usertype");

                if (tip.equals("ogrenci")) {
                    obj = new Ogrenci();
                } else if (tip.equals("gorevli")) {
                    obj = new Gorevli();
                } else if (tip.equals("teknisyen")) {
                    obj = new Teknisyen();
                } else if (tip.equals("mudur")) {
                    obj = new Mudur();
                }

                if (obj != null) {
                    obj.setIduser(rs.getInt("userid"));
                    obj.setNameSurname(rs.getString("name_surname"));
                    obj.setUsername(rs.getString("username"));
                    obj.setUserpassword(rs.getString("userpassword"));
                    obj.setUsertype(rs.getString("usertype"));
                    list.add(obj);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    public boolean addUser(String nameSurname, String username, String password, String tip) throws SQLException {
        String query = "INSERT INTO user (name_surname, username, userpassword, usertype) VALUES (?,?,?,?)";
        boolean key = false;
        try {
            con = conn.connDb();
            preparedStatement = con.prepareStatement(query);
            preparedStatement.setString(1, nameSurname);
            preparedStatement.setString(2, username);
            preparedStatement.setString(3, password);
            preparedStatement.setString(4, tip);
            preparedStatement.executeUpdate();
            key = true;
        } catch (SQLException e) {
            throw e;
        }
        return key;
    }

    public boolean deleteUser(int id) throws SQLException {
        String query = "DELETE FROM user WHERE userid = ?";
        boolean key = false;
        try {
            con = conn.connDb();
            preparedStatement = con.prepareStatement(query);
            preparedStatement.setInt(1, id);
            preparedStatement.executeUpdate();
            key = true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return key;
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

    public boolean addMakine(String tip) throws SQLException {
        String query = "INSERT INTO makineler (makine_tip, makine_durum, kullanim_sayisi) VALUES (?,?,?)";
        boolean key = false;
        try {
            con = conn.connDb();
            preparedStatement = con.prepareStatement(query);
            preparedStatement.setString(1, tip);
            preparedStatement.setString(2, "Musait");
            preparedStatement.setInt(3, 0);
            preparedStatement.executeUpdate();
            key = true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return key;
    }

    public boolean deleteMakine(int id) throws SQLException {
        String query = "DELETE FROM makineler WHERE id_makine = ?";
        boolean key = false;
        try {
            con = conn.connDb();
            preparedStatement = con.prepareStatement(query);
            preparedStatement.setInt(1, id);
            preparedStatement.executeUpdate();
            key = true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return key;
    }

    public String getMostUsed() {
        String info = "Veri yok";
        try {
            con = conn.connDb();
            st = con.createStatement();
            String sorgu = "SELECT id_makine, makine_tip, kullanim_sayisi FROM makineler ORDER BY kullanim_sayisi DESC LIMIT 1";
            rs = st.executeQuery(sorgu);

            if (rs.next()) {
                info = rs.getString("makine_tip") + " " + rs.getInt("id_makine") + " (" + rs.getInt("kullanim_sayisi") + " kez)";
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return info;
    }
}