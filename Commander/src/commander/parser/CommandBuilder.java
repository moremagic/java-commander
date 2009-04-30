/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package commander.parser;

import commander.core.*;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * コマンドオブジェクトを実体化するビルダクラス
 * 
 * @author moremagic
 */
public class CommandBuilder {    
    /**
     * 
     * @param strClass クラス名
     * @param attr 引数となるクラス名, データのマップ
     * @return
     * @throws java.lang.ClassNotFoundException
     * @throws java.lang.InstantiationException
     * @throws java.lang.IllegalAccessException
     */
    public static Command buildCommand(String strClass, List<CommanderParser._Argument> attr) throws ClassNotFoundException,
                                                                                        InstantiationException,
                                                                                        IllegalAccessException,
                                                                                        NoSuchMethodException,
                                                                                        IllegalArgumentException,
                                                                                        InvocationTargetException{
        Class clz = Class.forName(strClass);
        
        ArrayList<Class> clzArry = new ArrayList<Class>();
        ArrayList<Object> objArry = new ArrayList<Object>();
        Iterator<CommanderParser._Argument> ite = attr.iterator();
        while(ite.hasNext()){
            CommanderParser._Argument arg = ite.next();
            
            //Class
            Class argClz = Class.forName(arg.className);
            clzArry.add( argClz );
            
            //Instance
            Constructor argCon = argClz.getConstructor( new Class[]{String.class} );
            objArry.add( argCon.newInstance(new Object[]{arg.value}) );
        }
        
        Constructor con = clz.getConstructor( clzArry.toArray(new Class[]{}) );
        return (Command)con.newInstance( objArry.toArray() );
    }
    
    public static Command buildCommand(String strClass) throws ClassNotFoundException, InstantiationException, IllegalAccessException{
        Class clz = Class.forName(strClass);
        return (Command)clz.newInstance();
    }
    
    
}
