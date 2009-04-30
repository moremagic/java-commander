/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package commander.command;

import commander.core.Command;
import commander.core.Receiver;
import commander.core.ReceiverException;
import commander.reciver.EchoReceiver;

/**
 * サンプルコマンドクラス
 * 
 * @author moremagic
 */
public class HelloCommand implements Command{
    private Receiver recv = null;
    
    public HelloCommand(String msg){
        recv = new EchoReceiver(msg);
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
        return this.getClass().getName() + ":標準出力に文字を出力します。";
    }
}
