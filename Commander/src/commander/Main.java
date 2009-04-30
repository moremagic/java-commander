/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package commander;

import commander.command.StaffRollCommand;
import commander.core.SQLTaskManager;
import commander.parser.CommanderParser;
import java.io.File;
import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

/**
 * アプリケーションのメインクラス
 * @author moremagic
 */
public class Main {
    public static final String APP_NAME = "commander";
    public static final String APP_VERSION = "0.06";
    
    public static Logger logger = Logger.getLogger(APP_NAME);
    static{
        // ログの出力レベルを設定（ここではすべて出力するように設定)
        logger.setLevel(Level.ALL);
        
        // FileHandlerを生成
        try{
            FileHandler fh = new FileHandler(APP_NAME+".log");
            fh.setFormatter(new SimpleFormatter());
            logger.addHandler(fh);
            
//            ConsoleHandler ch = new ConsoleHandler();
//            ch.setFormatter(new SimpleFormatter());
//            logger.addHandler(ch);
        }catch(Exception err){
            err.printStackTrace();
        }
    }

    public static final String sShowMsg = "\n" +
            "-----------------\n" +
            " " + APP_NAME + "    Ver " + APP_VERSION + "\n" +
            "\n" +
            "           引数：処理するXMLファイルフルパス";

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
        //File f = new File("C:/Documents and Settings/moremagic/デスクトップ/Sample.xml");


        File f = null;
        if (args.length == 1) {
            if(args[0].equals("wwwwwwwwww")){
                showEgg();
            }

            f = new File(args[0]);
            if (!f.exists()) {
                Main.logger.log(Level.SEVERE, "ファイルが存在しません。処理を中断します。");
                System.exit(-1);
            }

            SQLTaskManager task = new SQLTaskManager();
            CommanderParser.parse(f, task);
            task.invoke();
        } else {
            showInfo();
        }

    }

    public static void showInfo() {
        System.out.println(sShowMsg);
    }

    public static void showEgg() {
        try {
            Main.logger.log(Level.FINE, "egg call!");

            SQLTaskManager task = new SQLTaskManager();
            task.addCommand(new StaffRollCommand());
            
            task.invoke();
        } catch (Exception err) {
            err.printStackTrace();
        }
    }
}
