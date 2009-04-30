/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package commander.reciver;

import commander.core.Receiver;
import commander.core.ReceiverException;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

/**
 * テーブルセーブレシーバー
 * Table内容をCSV出力するためのレシーバ
 * 
 * @author moremagic
 */
public class TableSaveReceiver implements Receiver{
    private Connection m_con = null;
    private String m_sTableName = null;
    private String m_encoding = null;
    private String m_sPath = null;
    private String m_sSqlQuery = null;
    
    public TableSaveReceiver(Connection con, String tableName, String sPath, String encoding){
        this.m_con = con;
        this.m_sTableName = tableName;
        this.m_sPath = sPath;
        this.m_encoding = encoding;
        
        m_sSqlQuery = createQuery();
    }
    
    private String createQuery(){
        StringBuilder query = new StringBuilder();
        query.append("SELECT * FROM " + m_sTableName);
        
        return query.toString();
    }
    
    public void action() throws ReceiverException{
        BufferedWriter bw = null;
        try{
            bw = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(new File(m_sPath)), this.m_encoding));
            
            Statement stmt = m_con.createStatement();
            ResultSet rset = stmt.executeQuery(m_sSqlQuery);
            int cols = rset.getMetaData().getColumnCount();
                
            bw.write("#");
            for(int i = 1 ; i <= cols ; i++){
                bw.write("'" + rset.getMetaData().getColumnName(i) + "'");
                if(i != cols){
                    bw.write(",");
                }
            }
            bw.write("\n");
            
            while( rset.next() ){
                for(int i = 1 ; i <= cols ; i++){
                    bw.write("'" + rset.getString(i) + "'");
                    if(i != cols){
                        bw.write(",");
                    }
                }
                bw.write("\n");
            }
            
        }catch(Exception err){
            ReceiverException e = new ReceiverException(err.getMessage() + "\t" + m_sSqlQuery);
            e.setStackTrace(err.getStackTrace());
            
            throw e;
        }finally{
            try{
                if(bw != null)bw.close();
            }catch(Exception err){}
        }
    }
}
