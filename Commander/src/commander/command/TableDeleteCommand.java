/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package commander.command;

import commander.command.oracle.*;
import commander.core.Command;
import commander.core.Command.CMD_TYPE;
import commander.core.Receiver;
import commander.core.SQLCommand;
import commander.reciver.TableDeleteReceiver;
import java.sql.Connection;

/**
 * テーブルデリートコマンドクラス
 * 
 * @author moremagic
 */
public class TableDeleteCommand implements SQLCommand{
    private Receiver rec = null;
    private String m_sTableName = null;
    
    public TableDeleteCommand(String sTableName){
        this.m_sTableName = sTableName;
    }
    
    public CMD_TYPE execute(Connection conn){
        try{
            rec = new TableDeleteReceiver(conn, m_sTableName);
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
        return this.getClass().getName() + ":テーブルデータを削除します。";
    }
}
