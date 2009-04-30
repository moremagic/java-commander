/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package commander.core;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * DB設定とコネクションを管理するコマンドクラス
 * @author moremagic
 */
public class DbConfCommand implements Command {

    private String m_strConnection = "";
    private String m_strUser = "";
    private String m_strPass = "";
    private Connection m_ActConn = null;

    public DbConfCommand(String strConnection, String strUser, String strPass) {
        this.m_strConnection = strConnection;
        this.m_strUser = strUser;
        this.m_strPass = strPass;

        this.m_ActConn = null;
    }

    Connection getConnection() {
        return m_ActConn;
    }

    private void open() throws ClassNotFoundException, SQLException {
        open(false);
    }

    private void open(boolean bAutoCommit) throws ClassNotFoundException, SQLException {
        try {
            // Oracle JDBC Driverのロード
            Class.forName("oracle.jdbc.driver.OracleDriver");
        } catch (ClassNotFoundException e) {}
        try {
            //mysql
            Class.forName("org.gjt.mm.mysql.Driver");
        } catch (ClassNotFoundException e) {}
        try {
            //postgresSql
            Class.forName("org.postgresql.Driver");
        } catch (ClassNotFoundException e) {}
        try {
            //SQLServer
            Class.forName("com.microsoft.jdbc.sqlserver.SQLServerDriver");
        } catch (ClassNotFoundException e) {}
        try{
            //DB2
            Class.forName("COM.ibm.db2.jdbc.app.DB2Driver");
        } catch (ClassNotFoundException e) {}
        
        // Oracleに接続
        //ex)Connection con = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:ORCL", "scott", "tiger");
        // MySQLの場合
        //ex)Connection con=DriverManager.getConnection("jdbc:mysql:///hellodb?useUnicode=true&characterEncoding=SJIS");
        // PostgreSQLの場合
        //ex)Connection con = DriverManager.getConnection("jdbc:postgresql:[//ホスト名[:ポート番号]/]hellodb", "postgres", ""); 
        // SQLServerの場合        
        //ex)Connection con = DriverManager.getConnection("jdbc:microsoft:sqlserver://localhost:1433", "userName", "password");
        // DB2の場合        
        //ex)Connection con = DriverManager.getConnection("jdbc:db2://localhost:1234/データベース名", "userName", "password");

        //MySQLの場合のみUser名、パスワードをコネクション作成時に使用しない
        if(m_strConnection.toLowerCase().startsWith("jdbc:mysql:")){
            m_ActConn = DriverManager.getConnection(m_strConnection);
        }else{
            m_ActConn = DriverManager.getConnection(m_strConnection, m_strUser, m_strPass);
        }
        
        m_ActConn.setAutoCommit(bAutoCommit);
    }

    public void commit() throws SQLException {
        m_ActConn.commit();
    }

    public void rollback() throws SQLException {
        m_ActConn.rollback();
    }

    public void close() throws SQLException {
        m_ActConn.close();
    }

    public CMD_TYPE execute() {
        try {
            open();
            return CMD_TYPE.SUCCESS;
        } catch (Exception err) {
            err.printStackTrace();
            return CMD_TYPE.FALSE;
        }
    }
}
