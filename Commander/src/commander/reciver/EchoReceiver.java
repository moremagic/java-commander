/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package commander.reciver;

import commander.core.Receiver;
import commander.core.ReceiverException;

/**
 * エコーレシーバー
 * 標準出力をするためのレシーバサンプル
 * 
 * @author moremagic
 */
public class EchoReceiver implements Receiver{
    private String message = "";
    
    public EchoReceiver(String message){
        this.message = message;
    }
    
    public void action() throws ReceiverException{
        System.out.println(this.message);
    }
}
