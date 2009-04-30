/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package commander.core;

import commander.Main;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.logging.Level;

/**
 * SQLコマンドを解析するためのタスクマネージャ
 * @author moremagic
 */
public class SQLTaskManager {
    private boolean bStop = false;
    private LinkedList<Command> m_list = null;
    private DbConfCommand m_ActiveConf = null;
    
    public SQLTaskManager(){
        m_list = new LinkedList<Command>();
    }
    
    public void addCommand(Command cmd){
        m_list.add(cmd);
        Main.logger.log(Level.INFO, "コマンド追加：" + cmd);
    }
    
    public void removeCommand(Command cmd){
        m_list.remove(cmd);
    }
    
    /**
     * コマンドを実行
     */
    public void invoke(){
        while(!m_list.isEmpty() && !bStop){
            Command cmd = m_list.removeFirst();
            
            //DBコネクションの設定
            if(cmd instanceof DbConfCommand){
                if(m_ActiveConf != null){
                    try{
                        m_ActiveConf.commit();
                        m_ActiveConf.close();
                    }catch(Exception err){
                        bStop = true;
                        err.printStackTrace();
                        continue;
                    }
                }
                m_ActiveConf = (DbConfCommand)cmd;
            }
            
            //コマンドの実行
            Command.CMD_TYPE result;
            if(cmd instanceof SQLCommand){
                SQLCommand sqlCmd = (SQLCommand)cmd;
                result = sqlCmd.execute(m_ActiveConf.getConnection());
            }else{
                result = cmd.execute();                
            }
            
            //実行結果判定
            switch(result){
                case FALSE:
                    try{
                        //ロールバック
                        m_ActiveConf.rollback();
                        Main.logger.log(Level.INFO, "ロールバックを行いました。");
                    }catch(SQLException err){
                        err.printStackTrace();
                    }
                    bStop = true;
                    continue;
            }
        }
        
        //最終コミット
        if(m_ActiveConf != null){
            try{
                m_ActiveConf.commit();
                m_ActiveConf.close();
            }catch(Exception err){
                err.printStackTrace();
            }
        }
    }
    
    /**
     * 途中停止しているかどうかを取得
     * @return 停止していた場合はTrue
     */
    public boolean isStop(){
        return bStop;
    }
}
