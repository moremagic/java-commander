/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package commander.command;

import commander.core.Command;
import commander.core.Command.CMD_TYPE;
import commander.core.Receiver;
import commander.core.SQLCommand;
import commander.reciver.TableDropReceiver;
import java.sql.Connection;



/**
 * サンプルコマンドクラス
 * 
 * @author moremagic
 */
public class TableDropCommand implements SQLCommand{
    private Receiver rec = null;
    private String m_sTableName = null;
    
    public TableDropCommand(String tableName){
        m_sTableName = tableName;
    }
    
    public CMD_TYPE execute(Connection conn){
        try{
            rec = new TableDropReceiver(conn, m_sTableName);
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
        return this.getClass().getName() + ":テーブル自体の削除を実行します。";
    }
}
