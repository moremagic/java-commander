/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package commander.reciver;

import commander.core.Receiver;
import commander.core.ReceiverException;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * テーブルインサートレシーバー
 * Table内容をCSV入力するためのレシーバ
 * 
 * @author moremagic
 */
public class TableInsertReceiver implements Receiver{
    private Connection m_con = null;
    private String m_sTableName = null;
    private String m_encoding = null;
    private String m_sPath = null;
    
    public TableInsertReceiver(Connection con, String tableName, String sPath, String encoding){
        this.m_con = con;
        this.m_sTableName = tableName;
        this.m_sPath = sPath;
        this.m_encoding = encoding;
    }
    
    private String createQuery() throws SQLException{
        StringBuilder query = new StringBuilder();
        
        query.append("INSERT INTO " + m_sTableName + " (");
        Statement stmt = m_con.createStatement();
        ResultSet rset = stmt.executeQuery("SELECT * FROM " + m_sTableName);
        int cols = rset.getMetaData().getColumnCount();

        for(int i = 1 ; i <= cols ; i++){
            query.append(rset.getMetaData().getColumnName(i));
            if(i != cols){
                query.append(",");
            }
        }
        query.append(") VALUES(#DATA#)");
        return query.toString();
    }
    
    public void action() throws ReceiverException{
        BufferedReader br = null;
        try{            
            String sQuery = createQuery();
            // クエリーを実行します
            Statement stmt = m_con.createStatement();
            
            br = new BufferedReader(new InputStreamReader(new FileInputStream(new File(m_sPath)), this.m_encoding));
            String line = null;
            while((line = br.readLine()) != null){
                if(line.trim().startsWith("#"))continue;

                System.out.println(sQuery.replace("#DATA#", line));                
                int result = stmt.executeUpdate(sQuery.replace("#DATA#", line));
                System.out.println(result + "行更新しました");
            }
        }catch(Exception err){
            ReceiverException e = new ReceiverException(err.getMessage());
            e.setStackTrace(err.getStackTrace());
            
            throw e;
        }finally{
            try{
                if(br != null)br.close();
            }catch(Exception err){}
        }
    }
}
