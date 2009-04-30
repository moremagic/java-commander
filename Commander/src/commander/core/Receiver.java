/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package commander.core;

/**
 * レシーバーインターフェース
 * コマンドからの処理を実際に行うクラスはこのインターフェースを実装します
 * 
 * @author moremagic
 */
public interface Receiver {
    void action() throws ReceiverException;
}
