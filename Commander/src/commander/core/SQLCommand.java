/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package commander.core;

import java.sql.Connection;

/**
 * SQL文を発行するコマンドクラス
 * 
 * SQL文を発行するためにはこのコマンドインターフェースを
 * 実装しなければなりません。
 * @author moremagic
 */
public interface SQLCommand extends Command{
    public CMD_TYPE execute(Connection conn);
}
