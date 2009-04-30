/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package commander.command;

import commander.core.Command;
import commander.core.Command.CMD_TYPE;
import commander.core.Receiver;
import commander.core.SQLCommand;
import commander.reciver.TableSaveReceiver;
import java.sql.Connection;



/**
 * テーブルセーブコマンドクラス
 * 
 * @author moremagic
 */
public class TableSaveCommand implements SQLCommand{
    private Receiver rec = null;
    private String m_sTableName = null;
    private String m_encoding = null;
    private String m_sPath = null;
    
    public TableSaveCommand(String tableName, String sPath){
        this(tableName, sPath, "UTF-8");
    }
    
    public TableSaveCommand(String tableName, String sPath, String encoding){
        m_sTableName = tableName;
        m_sPath = sPath;
        m_encoding = encoding;
    }
    
    public CMD_TYPE execute(Connection conn){
        try{
            rec = new TableSaveReceiver(conn, m_sTableName, m_sPath, m_encoding);
            rec.action();
            return Command.CMD_TYPE.SUCCESS;
        }catch(Exception err){
            err.printStackTrace();
            return Command.CMD_TYPE.FALSE;
        }
    }

    public CMD_TYPE execute() {
        throw new UnsupportedOperationException("Not supported yet.");
    }
    
    public String toString(){
        return this.getClass().getName() + ":テーブルデータのCSV出力を実行します。";
    }
}
