/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package commander.command.oracle;

import commander.core.Command;
import commander.core.Command.CMD_TYPE;
import commander.core.Receiver;
import commander.core.SQLCommand;
import commander.reciver.TablePurgeReceiver;
import java.sql.Connection;

/**
 * パージコマンドクラス
 * ※Olacre10gよりサポート
 * 
 * @author moremagic
 */
public class TablePurgeCommand implements SQLCommand{
    private Receiver rec = null;
    
    public TablePurgeCommand(){
    }
    
    public CMD_TYPE execute(Connection conn){
        try{
            rec = new TablePurgeReceiver(conn);
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
        return this.getClass().getName() + ":パージコマンドを実行します。";
    }
}
