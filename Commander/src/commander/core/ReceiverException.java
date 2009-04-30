/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package commander.core;

/**
 * レシーバーで発行されるException
 * @author moremagic
 */
public class ReceiverException extends Exception{
    public ReceiverException(String message){
        super(message);
    }
}
