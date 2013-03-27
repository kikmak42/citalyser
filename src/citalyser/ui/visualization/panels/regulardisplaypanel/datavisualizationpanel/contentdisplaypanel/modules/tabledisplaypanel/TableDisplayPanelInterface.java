/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package citalyser.ui.visualization.panels.regulardisplaypanel.datavisualizationpanel.contentdisplaypanel.modules.tabledisplaypanel;

import java.awt.Point;

/**
 *
 * @author Tanmay Patil
 */
public interface TableDisplayPanelInterface {
    public void callLeftClickedEvent(Point point);
    public boolean isMetric();
}
