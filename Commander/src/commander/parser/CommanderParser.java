/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package commander.parser;

import commander.Main;
import commander.core.Command;
import commander.core.DbConfCommand;
import commander.core.SQLTaskManager;
import java.io.File;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Map;
import java.util.logging.Level;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import org.xml.sax.Attributes;
import org.xml.sax.ErrorHandler;
import org.xml.sax.SAXException;
import org.xml.sax.SAXParseException;
import org.xml.sax.XMLReader;
import org.xml.sax.helpers.DefaultHandler;

/**
 * コマンドを解析するパーサ
 * @author moremagic
 */
public class CommanderParser extends DefaultHandler {

    private static final boolean _DEBUG_MODE_ = false;
    public static final String _TAG_ARGUMENT_ = "Argument";
    public static final String _TAG_COMMAND_ = "Command";
    public static final String _TAG_DBCONF_ = "DBConfig";
    public static final String _ATTR_CLASS_ = "class";
    private SQLTaskManager m_task = null;
    private StringBuilder m_buf = null;
    private LinkedList<_Argument> m_ArgMap = null;
    private LinkedList<Map<String, String>> m_Attr = null;

    /**
     * Commandクラスコンストラクタの引数管理クラス
     */
    public static class _Argument {
        String className;
        String value;
    }

    public static void parse(File f, SQLTaskManager task) {
        Main.logger.log(Level.INFO, "XML解析開始");
        
        try {
            // SAXパーサーファクトリを生成
            SAXParserFactory spfactory = SAXParserFactory.newInstance();
            spfactory.setValidating(true);

            if (!_DEBUG_MODE_) {
                Main.logger.log(Level.FINE, "正常モードでのパースを行います。\n正常モードでのパースはDTD整合性を無視しません。");
                
                //Validation
                XMLReader reader = spfactory.newSAXParser().getXMLReader();
                reader.setErrorHandler(new ErrorHandler() {
                    public void warning(SAXParseException exception) throws SAXException {
                        exception.printStackTrace();
                        System.exit(-1);
                    }

                    public void error(SAXParseException exception) throws SAXException {
                        exception.printStackTrace();
                        System.exit(-1);
                    }

                    public void fatalError(SAXParseException exception) throws SAXException {
                        exception.printStackTrace();
                        System.exit(-1);
                    }
                });
                
                reader.parse(f.toURI().toString());
            }

            // SAXパーサーを生成
            SAXParser parser = spfactory.newSAXParser();

            // XMLファイルを指定されたデフォルトハンドラーで処理します
            parser.parse(f, new CommanderParser(task));
            
        } catch (Exception e) {
            Main.logger.log(Level.SEVERE, "XML解析中にエラーが発生しました。");
            Main.logger.log(Level.FINER, e.getMessage());
        }
        Main.logger.log(Level.INFO, "XML解析終了");
    }

    private void init() {
        //バッファの初期化
        m_buf = new StringBuilder();
        m_ArgMap = new LinkedList<_Argument>();
        m_Attr = new LinkedList<Map<String, String>>();
    }

    /**
     * コンストラクタ
     */
    public CommanderParser(SQLTaskManager task) {
        m_task = task;
        init();
    }

    /**
     * ドキュメント開始時
     */
    @Override
    public void startDocument() {
        Main.logger.log(Level.FINEST,"startDocument");
        init();
    }

    /**
     * 要素の開始タグ読み込み時
     */
    @Override
    public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
        Main.logger.log(Level.FINEST,("startElement:" + qName));

        //アトリビュートスタックへの登録
        HashMap<String, String> attrMap = new HashMap<String, String>();
        for (int j = 0; j < attributes.getLength(); j++) {
            String key = attributes.getQName(j);
            String value = attributes.getValue(attributes.getQName(j));

            attrMap.put(key, value);
        }
        m_Attr.add(attrMap);

        //DB設定コマンドの登録
        if (qName.equals(_TAG_DBCONF_)) {
            Command cmd = new DbConfCommand(
                    attributes.getValue("connection"),
                    attributes.getValue("user"),
                    attributes.getValue("pass"));
            m_task.addCommand(cmd);
        }

    }

    /**
     * テキストデータ読み込み時
     */
    @Override
    public void characters(char[] ch, int offset, int length) {
        m_buf.append(new String(ch, offset, length));
    }

    /**
     * 要素の終了タグ読み込み時
     */
    @Override
    public void endElement(String uri, String localName, String qName) throws SAXException {
        Main.logger.log(Level.FINEST,("endElement:" + qName));
        viewAttrStack();

        //アトリビュートスタックから削除
        Map<String, String> attr = m_Attr.removeLast();

        //ArgumentMapへの追加
        if (qName.equals(_TAG_ARGUMENT_)) {
            _Argument buf = new _Argument();
            buf.className = attr.get(_ATTR_CLASS_);
            buf.value = m_buf.toString();

            m_ArgMap.add(buf);
        }

        if (qName.equals(_TAG_COMMAND_)) {
            try {
                //コマンドのビルド
                Command cmd = CommandBuilder.buildCommand(attr.get(_ATTR_CLASS_), m_ArgMap);
                m_task.addCommand(cmd);
            } catch (Exception err) {
                SAXException e = new SAXException(err);
                e.setStackTrace(err.getStackTrace());
                throw e;
            }

            //ArgumentMapのクリア
            m_ArgMap.clear();
        }

        //バッファの初期化
        m_buf = new StringBuilder();
    }

    /**
     * ドキュメント終了時
     */
    @Override
    public void endDocument() {
        Main.logger.log(Level.FINEST,"ドキュメント終了");
    }

    /**
     * スタックの内容を表示
     */
    private void viewAttrStack() {
        for (int i = 1; i < m_Attr.size(); i++) {
            Map<String, String> buf = m_Attr.get(i);

            Iterator<String> ite = buf.keySet().iterator();
            while (ite.hasNext()) {
                String key = ite.next();
                Main.logger.log(Level.FINEST,"\t" + key + "=" + buf.get(key));
            }
            Main.logger.log(Level.FINEST,"\t---------");
        }
    }
}
