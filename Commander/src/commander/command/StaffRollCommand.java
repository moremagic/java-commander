/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package commander.command;

import commander.Main;
import commander.core.Command;
import commander.core.Receiver;
import commander.core.ReceiverException;
import commander.reciver.StaffRollReceiver;

/**
 * スタッフロールコマンドクラス
 * 
 * @author moremagic
 */
public class StaffRollCommand implements Command{
    private Receiver recv = null;
    //TODO: ソフトウェア名、バージョン管理
    private final String msg = "[" + Main.APP_NAME + "] Ver " + Main.APP_VERSION + "　\n" +
            "\n\n\n\n\n\n" +
            "\t開発者： moremagic\n" +
            "\n\n\n\n\n\n" +
            "\tプロデューサー： M.sakai\n" +
            "\n\n\n\n\n\n" +
            "\tアドバイザー： T.yamashita\n" +
            "\n\n\n\n\n\n" +
            "\tシステムテスト: Y.ishimori\n" +
            "\n\n\n\n\n\n" +
            "\n\n\n\n\n\n" +
            "\t\t待機軍団 2009.2\n\n";
    
    
    public StaffRollCommand(){
        recv = new StaffRollReceiver(this.msg);
    }
    
    public CMD_TYPE execute(){
        try{
            recv.action();
            return Command.CMD_TYPE.SUCCESS;
        }catch(ReceiverException err){
            return Command.CMD_TYPE.FALSE;
        }
    }
    
    public String toString(){
        return this.getClass().getName() + ":標準出力にスタッフロールを出力します。";
    }
}
