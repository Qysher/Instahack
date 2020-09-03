package moritz.instahack.utils;

import java.awt.*;
import java.awt.datatransfer.DataFlavor;

public class ToolBox {

    public static String valueOfSystemClipboardAsString() {
        try {
            return (String) Toolkit.getDefaultToolkit().getSystemClipboard().getData(DataFlavor.stringFlavor);
        } catch (Exception e) {
        }
        return "";
    }

}
