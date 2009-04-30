/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package commander.command;

import commander.core.Command;
import commander.core.Command.CMD_TYPE;
import commander.core.Receiver;
import commander.core.SQLCommand;
import commander.reciver.TableCreateReceiver;
import java.sql.Connection;



/**
 * サンプルコマンドクラス
 * 
 * @author moremagic
 */
public class TableCreateCommand implements SQLCommand{
    private Receiver rec = null;
    private String m_sTableName = null;
    private String[] m_Cols = null;
    
    public TableCreateCommand(String tableName, String cols){
        m_sTableName = tableName;
        m_Cols = cols.split(",");
    }
    
    public CMD_TYPE execute(Connection conn){
        try{
            rec = new TableCreateReceiver(conn, m_sTableName, m_Cols);
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
        return this.getClass().getName() + ":テーブルを作成します。";
    }
}
