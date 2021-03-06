/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package commander.reciver;

import commander.core.Receiver;
import commander.core.ReceiverException;
import java.sql.Connection;
import java.sql.Statement;

/**
 * テーブルドロップレシーバー
 * Tableを削除するためのレシーバ
 * ※要Table削除権限
 * 
 * @author moremagic
 */
public class TableDropReceiver implements Receiver{
    private Connection m_con = null;
    private String m_sTableName = null;
    private String m_sSqlQuery = null;
    
    public TableDropReceiver(Connection con, String tableName){
        this.m_con = con;
        this.m_sTableName = tableName;
        
        m_sSqlQuery = createQuery();
    }
    
    private String createQuery(){
        StringBuilder query = new StringBuilder();
        query.append("DROP TABLE " + m_sTableName);
        
        return query.toString();
    }
    
    public void action() throws ReceiverException{
        try{
            Statement stmt = m_con.createStatement();
            stmt.execute(m_sSqlQuery);
        }catch(Exception err){
            ReceiverException e = new ReceiverException(err.getMessage() + "\t" + m_sSqlQuery);
            e.setStackTrace(err.getStackTrace());
            
            throw e;
        }
    }
}
