/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package commander.core;

/**
 * コマンドインターフェース
 * コマンドは全てこのインターフェースを実装しているものとする
 * @author moremagic
 */
public interface Command {
    /** コマンドの成功、失敗を表すenum **/
    public enum CMD_TYPE {SUCCESS, FALSE};

    /**
     * 実行
     * 実装クラス側での作り方によっては非同期実行対応も可能
     * @return
     */
    CMD_TYPE execute();
}
