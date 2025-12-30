package Model;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import Helper.DBConnection;

public abstract class User {

    private int iduser;
    protected String nameSurname;
    private String username;
    private String userpassword;
    private String usertype;

    DBConnection db = new DBConnection();

    public User(int iduser, String nameSurname, String username, String userpassword, String usertype) {
        this.iduser = iduser;
        this.nameSurname = nameSurname;
        this.username = username;
        this.userpassword = userpassword;
        this.usertype = usertype;
    }

    public User() {
    }

    public int getIduser() {
        return iduser;
    }

    public void setIduser(int iduser) {
        this.iduser = iduser;
    }

    public String getNameSurname() {
        return nameSurname;
    }

    public void setNameSurname(String nameSurname) {
        this.nameSurname = nameSurname;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getUserpassword() {
        return userpassword;
    }

    public void setUserpassword(String userpassword) {
        this.userpassword = userpassword;
    }

    public String getUsertype() {
        return usertype;
    }

    public void setUsertype(String usertype) {
        this.usertype = usertype;
    }

    public ArrayList<User> getUserList() throws SQLException {

        ArrayList<User> list = new ArrayList<>();

        User obj = null; // null olarak başlatıldı
        Connection con = db.connDb();
        Statement st = null;
        ResultSet rs = null;

        try {
            st = con.createStatement();
            rs = st.executeQuery("SELECT * FROM user");

            while (rs.next()) {

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
}