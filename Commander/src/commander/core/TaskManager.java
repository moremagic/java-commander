/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package commander.core;

import java.util.LinkedList;

/**
 * コマンドタスクを管理するタスクマネージャ
 * @author moremagic
 */
public class TaskManager {
    private boolean bStop = false;
    private LinkedList<Command> m_list = null;
    
    public TaskManager(){
        m_list = new LinkedList<Command>();
    }
    
    public void addCommand(Command cmd){
        m_list.add(cmd);
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
            switch(cmd.execute()){
                case SUCCESS:
                    break;
                case FALSE:

                    //TODO: ロールバック？
                    
                    bStop = true;
                    break;
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
