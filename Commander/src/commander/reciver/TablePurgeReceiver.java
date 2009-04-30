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
 * テーブルパージレシーバー
 * Tableを完全削除するためのレシーバ
 * ※要Table削除権限
 * ※Olacre10gよりサポート
 * 
 * @author moremagic
 */
public class TablePurgeReceiver implements Receiver{
    private Connection m_con = null;
    private String m_sSqlQuery = null;
    
    public TablePurgeReceiver(Connection con){
        this.m_con = con;
        m_sSqlQuery = createQuery();
    }
    
    private String createQuery(){
        StringBuilder query = new StringBuilder();
        query.append("PURGE USER_RECYCLEBIN");
        
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
