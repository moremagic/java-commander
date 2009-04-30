/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package commander.command;

import commander.core.Command;

/**
 * サンプルコマンドクラス
 * 
 * @author moremagic
 */
public class HelloCommand3 implements Command{
    private int cnt = -1;
    private String msg = "";
    
    public HelloCommand3(Integer cnt, String msg){
        this.cnt = cnt;
        this.msg = msg;
    }
    
    public CMD_TYPE execute(){
        for(int i = 0 ; i < cnt ; i++){
            System.out.println(msg + ": " + i);
        }
        return Command.CMD_TYPE.SUCCESS;
    }
    
    public String toString(){
        return this.getClass().getName() + ":標準出力に指定した文字を指定回出力します。";
    }
}
