/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package commander.reciver;

import commander.core.Receiver;
import commander.core.ReceiverException;

/**
 * スタッフロールレシーバー
 * 標準出力をするためのレシーバ
 * 
 * @author moremagic
 */
public class StaffRollReceiver implements Receiver {

    private String message = "";

    public StaffRollReceiver(String message) {
        this.message = message;
    }

    public void action() throws ReceiverException {
        Thread t = new Thread(new Runnable(){ 
            public void run() {
                for (int i = 0; i < message.length(); i++) {
                    System.out.print(message.substring(i, i + 1));
                    try {
                        Thread.sleep(200);
                    } catch (Exception err) {
                    }
                }
            }
        });
        
        t.start();
        
        while(t.isAlive()){
            try{
                Thread.sleep(200);
            }catch(Exception err){}
        }
    }
}
