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
 * テーブルクリエイトレシーバー
 * Tableを作成するためのレシーバ
 * ※要Table作成権限
 * 
 * @author moremagic
 */
public class TableCreateReceiver implements Receiver{
    private Connection m_con = null;
    private String m_sTableName = null;
    private String[] m_sCols = null;
    private String m_sSqlQuery = null;
    
    public TableCreateReceiver(Connection con, String tableName, String[] cols){
        this.m_con = con;
        this.m_sTableName = tableName;
        this.m_sCols = cols;
        
        m_sSqlQuery = createQuery();
    }
    
    private String createQuery(){
        StringBuilder query = new StringBuilder();
        query.append("CREATE TABLE " + m_sTableName + " (");
        
        for(int i = 0 ; i < m_sCols.length ; i++){
            query.append(m_sCols[i]);
            if(i+1 != m_sCols.length){
                query.append(",");
            }
        }
        query.append(")");
        
        return query.toString();
    }
    
    public void action() throws ReceiverException{
        try{
            Statement stmt = m_con.createStatement();
            stmt.execute(m_sSqlQuery);
        }catch(Exception err){
            throw new ReceiverException(m_sSqlQuery);
        }
    }
}
