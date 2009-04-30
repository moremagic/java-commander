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
public class HelloCommand2 implements Command{
    private int test = -1;
    
    public HelloCommand2(Integer msg){
        test = msg;
    }
    
    public CMD_TYPE execute(){
        for(int i = 0 ; i < test ; i++){
            System.out.println("カウント！ " + i);
        }
        return Command.CMD_TYPE.SUCCESS;
    }
    
    public String toString(){
        return this.getClass().getName() + ":標準出力に文字を複数回出力します。";
    }
}
